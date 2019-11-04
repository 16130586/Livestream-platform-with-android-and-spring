const EventSource = require('eventsource');
const config = require('./ys-comment-getter-config');

const accessToken = config.commentSource.accessToken;
const infoParam =  config.commentSource.infoParam;
const commentRate = config.commentSource.commentRate;

const replyEventTime = config.commentGetter.replyEventTime;

var id;
var liveId;
var commentSource;

var comments = [];

process.on('message', (msg) => {
    console.log('Message from master:', msg);
    messageBroker(msg);
});

setInterval(sendReplyEvent, replyEventTime);

// -----------------------------------------------------------------------------
// MESSAGE BROKER
// -----------------------------------------------------------------------------

function messageBroker(msg) {
	switch(msg.event) {
		case 'onCreate':
			onCreate(msg.data);
			break;
			
		case 'onDelete':
			onDelete();
			break;
		
		case 'onReply':
			onReply(msg.data);
			break;

		default:
			console.log('Error: Wrong event from master');
			break;
	}
}

// -----------------------------------------------------------------------------
// ON CREATE
// -----------------------------------------------------------------------------

function onCreate(data) {
    liveId = data.liveId;
    id = data.id;
    createCommentSource();
}

// -----------------------------------------------------------------------------
// ON DELETE
// -----------------------------------------------------------------------------

function onDelete(data) {
    process.send({
        event: 'onDelete',
        
    })
}

// -----------------------------------------------------------------------------
// ON DESTROY
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

// -----------------------------------------------------------------------------
// HANDLE COMMENT SOURCE
// -----------------------------------------------------------------------------

function createCommentSource() {
    var liveStreamUrl = `https://streaming-graph.facebook.com/${liveId}/live_comments?access_token=${accessToken}&${commentRate}&${infoParam}`;
    console.log(liveStreamUrl);
    
    try {
        commentSource = new EventSource(liveStreamUrl);
        commentSource.addEventListener('message', (msg) => {
            handleCommentSourceMessage(msg);
        });
        process.send({
            event: 'finishOnCreate',
            data: {
                id: id
            }
        })
    } catch (error) {
        console.log('error', liveStreamUrl);
        onDestroy();
    }
}

function handleCommentSourceMessage(msg) {
    //console.log(msg.data);
    var data = JSON.parse(msg.data);
    //console.log(data.from.name, ':', data.message);
    comments.push(data);
}

function checkcomments() {

}

function sendReplyEvent() {
    // detail later 
    if (comments.length > 0){
        process.send({
            event: 'sendReplyEvent',
            data: {
                comments: comments
            }
        })
        comments = [];
    }
}