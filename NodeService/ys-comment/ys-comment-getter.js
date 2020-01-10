const EventSource = require('eventsource');
const config = require('./ys-comment-getter-config');

const infoParam =  config.commentSource.infoParam;
const commentRate = config.commentSource.commentRate;

const replyEventTime = config.commentGetter.replyEventTime;

var id;
var liveId;
var commentSource;
var accessToken;

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
	id = data.stream_id;
	var forwards = JSON.parse(data.forwards);
    liveId = forwards[0].forwardTargetToId;
	accessToken = forwards[0].forwardTargetToken;
	
	console.log('reeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee', forwards);
	//testing, remove later
	liveId = 770597626773150;
	
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
    var data = JSON.parse(msg.data);
	//custom kafka payload, name change later if can get name
	data.comment_id = -1;
	data.stream_id = id;
	data.owner_id = -1;
	data.owner_name = 'FB'
	data.video_time = -1;
	data.eventType = 'REPLY';
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