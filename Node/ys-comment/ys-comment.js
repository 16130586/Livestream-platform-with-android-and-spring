var express = require("express");
var app = express();
app.use(express.static("public"));
app.set("view engine", "ejs");
app.set("views", "./views");

var server = require("http").Server(app);

const { fork } = require('child_process');
const config = require('./ys-comment-config');

var commentGetters = [];

// =============================================================================
//
// HANDLE KAFKA MESSAGE
//
// =============================================================================

// const kafka = require('kafka-node');
// const ConsumerGroup = kafka.ConsumerGroup;

// try {
// 	var options = {
// 		kafkaHost: config.kafka.kafkaHost, // connect directly to kafka broker (instantiates a KafkaClient)
// 		batch: undefined, // put client batch settings if you need them
// 		ssl: false, // optional (defaults to false) or tls options hash
// 		groupId: config.kafka.groupId,
// 		sessionTimeout: config.kafka.sessionTimeout,
// 		// An array of partition assignment protocols ordered by preference.
// 		// 'roundrobin' or 'range' string for built ins (see below to pass in custom assignment protocol)
// 		protocol: ['roundrobin'],
// 		encoding: 'utf8', // default is utf8, use 'buffer' for binary data

// 		// Offsets to use for new groups other options could be 'earliest' or 'none' (none will emit an error if no offsets were saved)
// 		// equivalent to Java client's auto.offset.reset
// 		fromOffset: config.kafka.fromOffset, // default
// 		commitOffsetsOnFirstJoin: true, // on the very first time this consumer group subscribes to a topic, record the offset returned in fromOffset (latest/earliest)
// 		// how to recover from OutOfRangeOffset error (where save offset is past server retention) accepts same value as fromOffset
// 		outOfRangeOffset: config.kafka.outOfRangeOffset, // default
// 		// Callback to allow consumers with autoCommit false a chance to commit before a rebalance finishes
// 		// isAlreadyMember will be false on the first connection, and true on rebalances triggered after that
// 	};

// 	var consumerGroup = new ConsumerGroup(options, 'M_EVENT');
// 	consumerGroup.on('message' , message => {
// 		try {
// 			let msg = JSON.parse(message.value);
// 			kafkaMessageBroker(msg);
// 		} catch (error) {
// 			console.log(message);
// 			console.log('parsing error');
// 		}
// 	})
// } catch (e) {
// 	console.log(e);
// }

// function kafkaMessageBroker(msg) {
// 	switch(msg.eventType) {
// 		case 'CREATE':
// 			onCreate(msg.ownerId);
// 			break;
// 		case 'DELETE':
// 			onDelete(msg.ownerId);
// 			break;
// 		case 'REPLY':
// 			onReply(msg.ownerId, msg);
// 			break;
// 		default:
// 			console.log('error kafka event');
// 			break;
// 	}
// }

function onCreate(id) {
	createWorker(id);
}

function onDelete(id) {
	deleteWorker(id);
	listWorkers('onDelete');
}

function onReply(workerId, reply) {
	sendReplyToWorker(workerId, reply);
}

// =============================================================================
//
// HANDLE COMMENT GETTER
//
// =============================================================================

function handleCommentGetterMessage(msg) {
    switch (msg.event) {
        case 'finishOnCreate':
            console.log(msg.data);
            break;
        
        case 'finishOnDelete':
            console.log('finish on delete', msg.data);
            break;

        case 'finishOnDestroy':
            console.log('finish on destroy', msg.data);
            break;

        case 'sendReplyEvent':
            // send onReply to Kafka
            console.log('prepare to send event reply', msg.data.comments);
            break;
        
        default:
            console.log('invalid getter messsage');
            break;
    }
}

function createCommentGetter(data) {
    const commentGetter = fork('ys-comment-getter');
    commentGetter.send({ 
        event: 'onCreate',
        data: {
            id: data.id,
            liveId: data.liveId
        }
    });
    commentGetter.on('message', (msg) => {
        handleCommentGetterMessage(msg);
    });
    commentGetters.push({
        commentGetter: commentGetter,
        commentGetterId: data.id
    });
    listCommentGetters('onCreate');
}

function listCommentGetters(msg) {
    var listString = '';
    if (msg) {
        listString = listString.concat(msg);
    }
    commentGetters.map((current) => {
        listString = listString.concat('   ').concat(current.commentGetterId);
    })
    console.log(listString);
}

// =============================================================================
//
// TEST
//
// =============================================================================

var data1 = {
    id: '1',
    liveId: '602969986908374'
}

var data2 = {
    id: '2',
    liveId: '2420370241562050'
}

createCommentGetter(data1);

createCommentGetter(data2);