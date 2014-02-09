var http = require("http");
var url = require("url");


function start(router, handle) {
    function onRequest(request, response) {
    	var postData = "";
        var pathname = url.parse(request.url).pathname;
        console.log("Request for " + pathname + " received.");
        
    	router(handle,pathname,request,response,postData);

        //request.setEncoding("utf8");
        
        /*
        request.addListener("data", function(postDataChunk){
        	postData += postDataChunk;
        	console.log("Recieved POST data chunk '"+postDataChunk+"'.");
        });
        
        request.addListener("end", function(){
        	router(handle,pathname,response,postData);
        });
        */
    }
    http.createServer(onRequest).listen(1234);
    console.log("Server has started.");
}
exports.start = start;