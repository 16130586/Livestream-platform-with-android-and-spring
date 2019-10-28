var express = require("express");
var app = express();
app.use(express.static("public"));
app.set("view engine", "ejs");
app.set("views", "./views");
 
var server = require("http").Server(app);
var io = require("socket.io")(server);


app.get('/', function(req, res){
	res.send('server working');
})

// tạo kết nối giữa client và server
io.on("connection", function(socket)
	{
		socket.on("disconnect", function()
			{
			});
         //server lắng nghe dữ liệu từ client
		socket.on("Client-sent-data", function(data)
			{
				//sau khi lắng nghe dữ liệu, server phát lại dữ liệu ny đến các 
				// client khác
				console.log(data);
                socket.emit("Server-sent-data", data);
			});
	});


server.listen(3000);