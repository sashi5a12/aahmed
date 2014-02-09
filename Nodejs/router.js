function route(handle, pathname, request, response, postData){
	console.log("About to route a request for " + pathname);
	if(typeof handle[pathname] === 'function'){
		handle[pathname](request, response, postData);
	}
	else {
		console.log("No request handler found for "+pathname);
        response.writeHead(400, {"Content-Type": "text/plain"});
        response.write("404 Not found");
        response.end();
	}
}
exports.route=route;