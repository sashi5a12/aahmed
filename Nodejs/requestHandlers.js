var exec = require("child_process").exec;
var querystring = require("querystring");
var fs = require("fs");
var formidable = require("formidable");

function start(request, response, postData) {
    console.log("Request handler 'start' was called.");
    var body = '<html>' + 
    '<head>'+
    '<meta http-equiv="Content-Type" '+ 'content="text/html; charset=UTF-8" />'+
    '</head>'+
    '<body>'+
    '<form action="/upload" enctype="multipart/form-data" '+
    'method="post">'+
    '<input type="file" name="upload">'+
    '<input type="submit" value="Upload file" />'+
    '</form>'+
    '</body>'+
    '</html>';

    response.writeHead(200, {"Content-Type": "text/html"});
    response.write(body);
    response.end();
    /*exec("ls -lah", function(error, stdout, stderr){
        response.writeHead(200, {"Content-Type": "text/plain"});
        response.write(stdout);
        response.end();
	});*/

    /*exec("find /", 
			{timeout:1000, maxBuffer: 20000*1024},
			function(error, stdout, stderr){
		        response.writeHead(200, {"Content-Type": "text/plain"});
		        response.write(stdout);
		        response.end();
			});
	*/
}

function upload(request, response, postData) {
    console.log("Request handler 'upload' was called.");
    var form = new formidable.IncomingForm();
    console.log("about to parse");
    form.parse(request, function(error,fields, files){
    	fs.rename(files.upload.path, "/tmp/test.png", function(err){
    		if(err){
    			fs.unlink("/tmp/test.png");
    			fs.rename(files.upload.path, "/tmp/test.png");
    		}
    	});
    	
    	response.writeHead(200,{"Content-Type": "text/html"});
    	response.write("received image:<br/>");
    	response.write("<img src='/show'/>");
    	response.end();
    });
    //response.writeHead(200, {"Content-Type": "text/plain"});
    //response.write("You've sent the text: "+querystring.parse(postData).text);
    //response.end();
}

function show(request, response, postData){
	console.log("Request handler 'show' was called.");
	
	fs.readFile("/tmp/test.png","binary", function(error, file){
		if(error){
			response.writeHead(500, {"Content-Type": "text/plain"});
			response.write(error+"\n");
			response.end();
		}
		else {
			response.writeHead(200, {"Content-Type": "image/png"});
			response.write(file,"binary");
			response.end();
		}
	});
}
exports.start = start;
exports.upload = upload;
exports.show = show;