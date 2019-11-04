var express = require("express");
var app = express();
app.use(express.static("public"));
app.set("view engine", "ejs");
app.set("views", "./views");

var server = require("http").Server(app);
var io = require("socket.io")(server);

const { fork } = require('child_process');
const config = require('./broker_config');
const serverPort = config.server.port;
const socketCheckTime = config.server.socketCheckTime;

server.listen(serverPort);

// workers and sockets
var workers = [];
var sockets = [];
var socketCounter = 1;

// setInterval(removeDisconnectedSockets, socketCheckTime);

// router index to know if the server working
app.get('/', function(req, res){
	res.send('server working');
})

// =============================================================================
//
// HANDLE KAFKA MESSAGE
//
// =============================================================================

const kafka = require('kafka-node');
const ConsumerGroup = kafka.ConsumerGroup;

try {
	var options = {
		kafkaHost: config.kafka.kafkaHost, // connect directly to kafka broker (instantiates a KafkaClient)
		batch: undefined, // put client batch settings if you need them
		ssl: false, // optional (defaults to false) or tls options hash
		groupId: config.kafka.groupId,
		sessionTimeout: config.kafka.sessionTimeout,
		// An array of partition assignment protocols ordered by preference.
		// 'roundrobin' or 'range' string for built ins (see below to pass in custom assignment protocol)
		protocol: ['roundrobin'],
		encoding: 'utf8', // default is utf8, use 'buffer' for binary data

		// Offsets to use for new groups other options could be 'earliest' or 'none' (none will emit an error if no offsets were saved)
		// equivalent to Java client's auto.offset.reset
		fromOffset: config.kafka.fromOffset, // default
		commitOffsetsOnFirstJoin: true, // on the very first time this consumer group subscribes to a topic, record the offset returned in fromOffset (latest/earliest)
		// how to recover from OutOfRangeOffset error (where save offset is past server retention) accepts same value as fromOffset
		outOfRangeOffset: config.kafka.outOfRangeOffset, // default
		// Callback to allow consumers with autoCommit false a chance to commit before a rebalance finishes
		// isAlreadyMember will be false on the first connection, and true on rebalances triggered after that
	};


	// Or for a single topic pass in a string

	var consumerGroup = new ConsumerGroup(options, 'M_EVENT');
	consumerGroup.on('message' , message => {
		try {
			let msg = JSON.parse(message.value);
			kafkaMessageBroker(msg);
		} catch (error) {
			console.log(message);
			console.log('parsing error');
		}
		// create
		//  -> event loop / 2s check 1
		// 	+ if (checkTime <= 3){
		// 		if(dc reply roi) -> checkTime = 0 , reply = false
		// 		else 
		// 			checkTime++;
		// 	}
		// 	+ else {					
		// 		kill process loop
		// 		dung socketio -> gui ma loi
		// 	}
		// // delete
		// 	kill process loop
		// 	->? lay socket io -> gui thong tin delete ve
		// // reply
		// 	-> lay cai gi do len , lq cai message nay
		// 	-> reply = true,
		// 	-> if(message.reply != null || len(message.reply) > 0)
		// 	-> lay socket io -> gui reply ve cho client
	})
} catch (e) {
	console.log(e);
}

function kafkaMessageBroker(msg) {
	console.log(msg);
	switch(msg.data.eventType) {
		case 'CREATE':
			onCreate(msg.data.id);
			break;
		case 'DELETE':
			onDelete(msg.data.id);
			break;
		case 'REPLY':
			onReply(msg);
			break;
		default:
			console.log('error kafka event', msg);
			break;
	}
}

function onCreate(id) {
	createWorker(id);
}

function onDelete(id) {
	deleteWorker(id);
	
	// var socket = getSocketById(id);
	// // socket.emit('broker-send-delete', {

	// // })
	listWorkers('onDelete');
}

function onReply(msg) {
	sendReplyToWorker(msg.data.stream_id, msg);
}

// =============================================================================
//
// SENDING KAFKA MESSAGE
//
// =============================================================================
var producer;

try {
	const Producer = kafka.Producer;
	const client = new kafka.Client(config.kafka_server);
	producer = new Producer(client);
	const kafka_topic = 'M_EVENT';
	//console.log(kafka_topic);
	let payloads = [
	  {
		topic: kafka_topic,
		event: kafka_topic
	  }
	];
  
	producer.on('ready', async function() {
		console.log('producer is ready');
	});
  
	producer.on('error', function(err) {
	  console.log(err);
	  console.log('[kafka-producer -> '+kafka_topic+']: connection errored');
	  throw err;
	});
  }
  catch(e) {
	console.log(e);
  }

function sendMessageToKafka(message) {
	console.log('hereeeee', message);
	let payloads = [message];
	producer.send(payloads, (err) => {
		if (err) {
			console.log('[kafka-producer -> ]: broker update failed');
		  } else {
			console.log('[kafka-producer -> ]: broker update success');
		  }
	})
}

// =============================================================================
//
// HANDLE SOCKET IO
//
// =============================================================================

io.on("connection", initSocket);

function initSocket(socket) {
	socket.on('disconnect', function(data){
		console.log('a client disconnected', data);
		removeDisconnectedSockets();
	});

	// send to client
	socket.emit('broker-send-create', {
		socketId: socketCounter
	});

	// handle from client
    socket.on('client-send-data', function(data){
		console.log(data);
		socket.emit('server-send-data', data);
    });

	// socket will send its own id back -> add to sockets, create a process
	// this is just a example, later the sockerCounter replace by data.id
	socket.on('client-send-id', function(data){
		sockets.push({
			socket: socket,
			socketId: data.id
		});
		socket.emit('server-send-data', data);
	});

	socket.on('error', function(error){
		console.log(error);
	})
}

function removeDisconnectedSockets() {
	for (var i = 0; i < sockets.length; i++) {
		if (sockets[i].socket.connected === false) {
			//onDelete(sockets[i].socketId);
			console.log('remove disconnected socket', sockets[i].socketId);
			sockets.splice(i, 1);
		}
	}
	console.log('checking socket');
	listSockets();
}

function listSockets() {
	var socketsString = '';
	sockets.map((current) => {
		socketsString = socketsString
						.concat('socketId: ')
						.concat(current.socketId)
						.concat('	');
	});
	console.log(socketsString);
}

function getSocketById(id) {
	for (var i = 0; i < sockets.length; i++){
		if (sockets[i].socketId == id)
			return sockets[i].socket;
	}
	return undefined;
}

function checkSocket(socket, id) {
	if (socket == null) {
		handleSocketError(id);
	}
}

function handleSocketError(data) {
	console.log('hanle socket error');
	var payloads = {
		topic: 'M_ERROR',
		message: data.msg
	}
	sendMessageToKafka(payloads);
}

// =============================================================================
//
// HANDLE WORKER
//
// =============================================================================

function messageBroker(msg) {
	switch(msg.event) {
		case 'onCreate':
			finishOnCreate(msg.data);
			break;
			
		case 'onDelete':
			finishOnDelete(msg.data);
			break;
		
		case 'onReply':
			finishOnReply(msg.data);
			break;

		case 'onDestroy':
			finishOnDestroy(msg.data);
			break;

		case 'test':
			console.log(msg);
			break;	

		default:
			console.log('Error: Wrong event from worker');
			break;
	}
}

function finishOnCreate(msg){
	console.log('finish on create');
}

function finishOnDelete(data){
	console.log('finish on delete');
}

function finishOnDestroy(data){
	console.log('finish on destroy');
	removeWorkerById(data.id);
	listWorkers('onDestroy');
}

function finishOnReply(data){
	console.log('finish on reply', data.id, data.msg);
	var socket = getSocketById(data.id);
	if (socket == null) {
		handleSocketError(data);
	} else {
		socket.emit('broker-send-reply', data);
	}
	
}

function listWorkers(msg) {
	var workersString = '';
	if (msg) {
		workersString = workersString.concat(msg);
	}
	workers.map((current) => {
		workersString = workersString.concat('	').concat(current.workerId);
	})

	console.log(workersString);
}

function createWorker(workerId) {
	// create worker
	const worker = fork('worker.js');
	// send event to worker
	worker.send({ event: 'onCreate', value: workerId });
	// add handle message from worker 
	worker.on('message', (msg) => {
		messageBroker(msg);
	});
	// push worker to workers
	workers.push({
		worker: worker,
		workerId: workerId
	});
	// list the worker
	listWorkers('onCreate');
}

function sendReplyToWorker(workerId, reply) {
	var worker = getWorkerById(workerId);
	if (checkWorker(worker)) {
		worker.send({
			event: 'onReply',
			value: reply
		});
	} else {
		handleWorkerError(workerId, reply);
	}
}

function deleteWorker(workerId) {
	var worker = getWorkerById(workerId);
	if (checkWorker(worker)) {
		worker.send({
			event: 'onDelete'
		});
		removeWorkerById(workerId);
	} else {
		handleWorkerError(workerId);
	}
}

function removeWorkerById(id) {
	for(var i = 0; i < workers.length; i++){
		if (workers[i].workerId === id){
			workers.splice(i, 1);
			return;
		}
	}
	console.log('ERROR removeWorkerById id not found');
}

function getWorkerById(id) {
	for(var i = 0; i < workers.length; i++){
		if (workers[i].workerId == id){
			return workers[i].worker;
		}
	}
	return undefined;
}

function checkWorker(worker) {
	if (worker == null) 
		return false;
	return true;
}

function handleWorkerError(id, reply) {
	console.log('handling error', id, reply);
}

// -----------------------------------------------------------------------------
// TEST
// -----------------------------------------------------------------------------

//onCreate('1');
// onCreate('2');

// // setTimeout(function(){
// // 	onReply('1', 'asd');
// // }, 5000);

// setInterval(() => {
// 	onReply('2', 'message');
// }, 8000);

