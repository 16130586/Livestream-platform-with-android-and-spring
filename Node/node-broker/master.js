const { fork } = require('child_process');

var workers = [];

// -----------------------------------------------------------------------------
// MESSAGE BROKER
// -----------------------------------------------------------------------------
function messageBroker(msg) {
	console.log(msg);
}

// -----------------------------------------------------------------------------
// ON CREATE
// -----------------------------------------------------------------------------

function onCreate(workerID) {
	const worker = fork('worker.js');
	worker.send({ event: 'onCreate', value: workerID });
	worker.on('message', (msg) => {
		messageBroker(msg);
	});
	workers.push({
		worker: worker,
		workerID: workerID
	});
	listWorkers('onCreate');
}

// -----------------------------------------------------------------------------
// ON DELETE
// -----------------------------------------------------------------------------
function onDelete(workerID) {
	for(var i = 0; i < workers.length; i++){
		if (workers[i].workerID === workerID){
			workers[i].worker.send({
				event: 'onDelete'
			});
			workers.splice(i, 1);
			break;
		}
	}
	listWorkers('onDelete');
}

// -----------------------------------------------------------------------------
// ON REPLY
// -----------------------------------------------------------------------------
function onReply(workerID, reply) {
	for(var i = 0; i < workers.length; i++){
		if (workers[i].workerID === workerID){
			workers[i].worker.send({
				event: 'onReply',
				value: reply
			});
			break;
		}
	}
}

// -----------------------------------------------------------------------------
// LIST WORKERS
// -----------------------------------------------------------------------------
function listWorkers(msg) {
	var workersString = '';
	workers.map((worker) => {
		workersString = workersString.concat(worker.workerID).concat('	');
	})
	if (msg) {
		console.log('----------', msg);
	}
	console.log(workersString);
}

// -----------------------------------------------------------------------------
// TEST
// -----------------------------------------------------------------------------
onCreate('1');
onCreate('2');

onReply('1', 'message');

setTimeout(function(){
	onDelete('1');
}, 5000);

// setInterval(() => {
// 	onReply('1', 'message');
// }, 4000);