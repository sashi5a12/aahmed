<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="js/prototype-1.6.0.3.js"></script>
	<script type="text/javascript">
		function simpleAjaxCall(){
			new Ajax.Request('test', { method:'get' });
		}
		
		function responseCallback(){
			new Ajax.Request('test', 
				{
					method:'get',
					on404: function(){alert('404')},
					onUninitialized: function(){alert('maps on created')},
					onLoading: function(){alert('maps on initialized')},
					onLoaded: function(){alert('maps on request sent')},
					onInteractive: function(){alert('on interactive')},					
					onSuccess: function(transport){
						var response = transport.responseText || "no response text";
      					alert("Success! \n\n" + response);
					},
					onFailure: function(){ alert('Something went wrong...') },
					onComplete : function(){alert('on complete')}
				});
		}
		
		function sendParamsToServer(){
			new Ajax.Request('test',
									{
										method: 'get',
										parameters: {id:1, name:'Adnan Ahmed'}
									});
		}
		
		function updateDiv(){
			new Ajax.Updater('content','test',{
				method:'get',
				insertion: Insertion.Bottom,
				onSuccess: function(response){
					alert(response.getHeader('Server'));
				} 	
			});
		}
		function updateDiv2(){
			new Ajax.Updater({success:'content', failure: 'failure'},'test',{
				method:'get',
				insertion: Insertion.Bottom,
				onSuccess: function(response){
					alert(response.getHeader('Server'));
				},
				onFailure: function(){
					alert('fail.');
				} 	
			});
		}
		function updatePeriodically(){
			new Ajax.PeriodicalUpdater('content', 'test',   
				{     
					method: 'post',     
					insertion: Insertion.Top,     
					frequency: 1,     
					decay: 2   
				});		
		}
		function basicExample(){
			var url = 'test?url=' + encodeURIComponent('http://www.google.com/search?q=Prototype'); 
			// notice the use of a proxy to circumvent the Same Origin Policy. 
			new Ajax.Request(url, {   
				method: 'get',   
				onSuccess: function(transport) {     
					var notice = $('content');
					alert(transport.responseText);     
					if (transport.responseText.match(/href="http:\/\/prototypejs.org/))       
						notice.update('Yeah! You are in the Top 10!').setStyle({ background: '#dfd' });     
					else       
						notice.update('Damn! You are beyond #10...').setStyle({ background: '#fdd' });   
				} 
			}); 		
		}		
		/*Ajax.Responders.register({   
			onCreate: function(){
				alert('a request has been initialized!');   
			},   
			onComplete: function(){     
				alert('a request completed');   
			} 
		});*/ 
	</script>
  </head>
  
  <body onload="updateDiv2();"> 
    <div id="content"></div>
    <div id="failure"></div>
  </body>
</html>
