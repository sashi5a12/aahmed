<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>Show</title>
    
    <style type="text/css">
    	#box{
    		background: red;
    		height: 200;
    		width: 500;    
    		display: none;		
    	}
    	p a {
    		color: red;
    	}
    </style>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.3.2.min.js"></script>
  	
  	<script type="text/javascript">
  		$(function (){
  			$('a').click(function (){
  				$('#box').show(3000);
  				$('p a').css('color', 'blue');
  			});
  		});
  	</script>    
  </head>
  
  <body>
    <div id="box"></div>
    <a href="javascript:void(0);" onclick="return false;">Click Me!</a>
	<br/>
	<p>
		Attendees will hear about <a href="#">Alfresco</a> implementations directly from
		Enterprise customers, discuss product roadmap plans with the Alfresco
		Engineering team, as well as discover new solutions Partners are
		developing for <a href="#">Enterprise</a> clients. The Meetups are designed to
		facilitate discussion about the latest developments at Alfresco and
		provide an excellent opportunity to network and participate in
		Alfresco Master Class sessions. The <a href="#">Washington</a> event will include
		sessions on Records Management.
	</p>
	<br/><br/><br/><a href="<%=path%>">Home</a>
  </body>
</html>



