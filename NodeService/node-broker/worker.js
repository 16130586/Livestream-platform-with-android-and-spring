const config = require('./worker_config');
const replyWaitTime = config.replyWaitTime;

var checkCount = config.checkCount;
var workerID = '1';
var checkingReplyInterval;

process.on('message', (msg) => {
 	//console.log('Message from master:', msg);
	messageBroker(msg);
});

// -----------------------------------------------------------------------------
// MESSAGE BROKER
// -----------------------------------------------------------------------------
function messageBroker(msg) {
	switch(msg.event) {
		case 'onCreate':
			onCreate(msg.value);
			break;
			
		case 'onDelete':
			onDelete();
			break;
		
		case 'onReply':
			onReply(msg.value);
			break;

		default:
			console.log('Error: Wrong event from master');
			break;
	}
}

// -----------------------------------------------------------------------------
// ON CREATE
// -----------------------------------------------------------------------------
function onCreate(id) {
	workerID = id;
	checkingReplyInterval = setInterval(checkingReply, replyWaitTime);
	process.send({
		event: 'onCreate',
		data: {
			id: workerID
		}
	});

	// let counter = 0;
	// setInterval(() => {
	// 	process.send({ event:'test', counter: counter++, id: workerID });
	// }, sendTime);
}

// -----------------------------------------------------------------------------
// ON DELETE
// -----------------------------------------------------------------------------
function onDelete(){
	process.send({
		event: 'onDelete',
		data: {
			id: workerID
		}
	});
	process.exit();
}

// -----------------------------------------------------------------------------
// ON REPLY
// -----------------------------------------------------------------------------
function onReply(msg) {
	setCheckCount(config.checkCount);
	// refactor the payload
	msg.data.ownerName = msg.data.owner_name;
	msg.data.commentId = msg.data.comment_id;
	process.send({
		event: 'onReply',
		data: {
			id: workerID,
			msg: msg.data
		}
	});
}

// -----------------------------------------------------------------------------
// DESTROY
// -----------------------------------------------------------------------------
function onDestroy() {
	process.send({
		event: 'onDestroy',
		data: {
			id: workerID
		}
	});
	process.exit();
}

function checkingReply() {
	if (checkCount > 0) {
		checkCount--;
		clearInterval(checkingReplyInterval);
		checkingReplyInterval = setInterval(checkingReply, replyWaitTime);
	} else {
		onDestroy();
	}
}

function setCheckCount(count) {
	checkCount = count;
}