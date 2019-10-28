// var io = require("socket.io");
// var socket = io("localhost:3000");
var io = require('socket.io-client')
var socket = io.connect('http://localhost:3000', {reconnect: true});
var socketID = '1';
 
// data
socket.on("server-send-data", function(data)
{
    console.log(data);
});


// client send to server 
socket.emit("client-send-data", "Hello world");

// handle from broker
socket.on('broker-send-create', function(data){
    console.log('broker send create to this socket', data);
    console.log('this socket id:', socketID);
    socket.emit('client-send-id', { id: socketID })
});

socket.on('broker-send-delete', function(data){
    console.log('broker send delete to this socket');
    socket.emit('client-disconnect', socketID);
    socket.disconnect();
})

socket.on('broker-send-reply', function(data){
    console.log('broker send reply to this socket', data);
})

socket.on('broker-send-abc', function(data){
    
})
