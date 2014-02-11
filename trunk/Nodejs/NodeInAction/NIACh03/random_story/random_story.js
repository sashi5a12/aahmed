var fs = require('fs');
var request = require('request');
var htmlparser = require('htmlparser');
var configFilename = "./rss_feeds.txt";

//Add each task to be performed to an array in execution order.
var tasks = [checkForRSSFile, readRSSFile, downloadRSSFeed, parseRSSFeed];

//A function called next executes each task.
function next(err, result){
	if(err) throw err;
	
	//next comes from array of tasks
	var currentTask = tasks.shift();
	console.log(currentTask);
	if(currentTask){
		//execute current task
		currentTask(result);
	}
}

next();

function checkForRSSFile(){
	console.log('checkForRSSFile');

	fs.exists(configFilename, function(exists){
		if(!exists){
			return next(new Error('Missing RSS file: '+configFilename));
		}
		
		next(null, configFilename);
	});
}

function readRSSFile(configFilename){
	console.log('readRSSFile');

	fs.readFile(configFilename, function(err, feedList){
		if(err) return next(err);
		
		feedList = feedList.toString().replace(/^\s+|\s+$/g,'').split("\n");
		var random = Math.floor(Math.random()*feedList.length);
		next(null, feedList[random]);
	});
}

function downloadRSSFeed(feedUrl){
	console.log('downloadRSSFeed');

	request({uri: feedUrl}, function(err, res, body){
		if(err) throw next(err);
		
		if(res.statusCode != 200){
			console.log("statuscode: "+res.statusCode);
			return next(new Error('Abnormal response status code'))
		}
		
		next(null, body);
	});
}

function parseRSSFeed(rss){
	console.log('parseRSSFeed');

	var handler = new htmlparser.RssHandler();
	var parser = new htmlparser.Parser(handler);
	
	parser.parseComplete(rss);
	if(!handler.dom.items.length)
		return next(new Error('No RSS items found'));
	var item = handler.dom.items.shift();
	console.log(item.title);
	console.log(item.link);
}