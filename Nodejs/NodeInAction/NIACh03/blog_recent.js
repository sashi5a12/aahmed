var http=require("http");
var fs=require("fs");
var watcher = require('watch_dir');

http.createServer(function(req,res){
	console.log("Server started on port 8000");
	getTitles(res);
	watcher.start();
}).listen(8000);

function getTitles(res){
	fs.readFile("./titles.json", function(err,data){
		if(err) return hadError(err,res);
		getTemplate(JSON.parse(data.toString()),res);
	});
}

function getTemplate(titles, res){
	fs.readFile("./template.html",function(err,data){
		if(err) return hadError(err,res);
		formatHtml(titles,data.toString(),res);
	});
}

function formatHtml(titles,tmpl,res){
	var html=tmpl.replace('%',titles.join('</li><li>'));
	res.writeHead(200,{'Content-Type':'text/html'});
	res.end(html);
}

function hadError(err,res){
	console.log(err);
	res.end(err);
}