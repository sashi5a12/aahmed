var net=require("net");

var server=net.createServer(function(socket){
	socket.on('data',function(data){
		socket.write("Server: "+data);
	});
});

server.listen(8000);