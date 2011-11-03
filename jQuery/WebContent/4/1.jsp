<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>Advanced CSS Selector</title>
    
    <style type="text/css">
    	.alert {
    		color: red;
    	}
    </style>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.3.2.min.js"></script>
  	
  	<script type="text/javascript">
  		$(function (){
  			$('#one li:first').addClass("alert");
  			$('#two li:even').addClass("alert");
  			$('#three li:nth-child(4)').addClass("alert");
  			$('#four li:eq(4)').addClass("alert");
  			$('#five li a[title=title]').addClass("alert");
  			$('#six li>a').addClass("alert");
  		});
  	</script>    
  </head>
  
  <body>
  <a href="#" title="no title">No Title</a>
  	$('#one li:first')
    <ul id="one">
    	<li>Alert 1</li>
    	<li>Alert 2</li>
    	<li>Alert 3</li>
    	<li>Alert 4</li>
    	<li>Alert 5</li>
    </ul>	
    
    $('#two li:even')
    <ul id="two">
    	<li>Alert 1</li>
    	<li>Alert 2</li>
    	<li>Alert 3</li>
    	<li>Alert 4</li>
    	<li>Alert 5</li>
    </ul>
    
    $('#three li:nth-child(4)')
    <ul id="three">
    	<li>Alert 1</li>
    	<li>Alert 2</li>
    	<li>Alert 3</li>
    	<li>Alert 4</li>
    	<li>Alert 5</li>
    </ul>

	$('#four li:eq(4)')
    <ul id="four">
    	<li>Alert 1</li>
    	<li>Alert 2</li>
    	<li>Alert 3</li>
    	<li>Alert 4</li>
    	<li>Alert 5</li>
    </ul>
    
    $('#five li a[title=title]')
    <ul id="five">
    	<li>Alert 1</li>
    	<li><a href="#" title="title">Alert 2</a></li>
    	<li>Alert 3</li>
    	<li>Alert 4</li>
    	<li><a href="#" title="no title">Alert 5</a></li>
    </ul>

	$('#six li>a')
    <ul id="six">
    	<li>Alert 1</li>
    	<li><a href="#" title="title">Alert 2</a></li>
    	<li>Alert 3</li>
    	<li>Alert 4</li>
    	<li><a href="#" title="no title">Alert 5</a></li>
    </ul>
	<br/><br/><br/><a href="<%=path%>">Home</a>
  </body>
</html>



