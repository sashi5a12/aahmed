<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>The toggle() and toggleClass() Methods</title>
    <style type="text/css">
    	#box{
    		background: red;
    		height: 300px;
    		width: 300px;    		
    	}
    	.border{
    		border: 5px solid black;
    	}
    	.highlight{
    		background: yellow;
    	}
    </style>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.3.2.min.js"></script>
  	
  	<script type="text/javascript">
  		$(function (){
  			$('a').click(function (){
  				//$('#box').toggle();
  				$('#box').toggleClass('border');
  			});
  			$('p').click(function(){
  				$(this).toggleClass('highlight');
  			});
  		});
  	</script>
  	</head>  
  <body>
		<p>We're going to take a look at the toggle() and toggleClass() methods
		and how they can be used to essentially turn "off and on" elements or
		classes.</p>
		<p>We're going to take a look at the toggle() and toggleClass() methods
		and how they can be used to essentially turn "off and on" elements or
		classes.</p>
		<p>We're going to take a look at the toggle() and toggleClass() methods
		and how they can be used to essentially turn "off and on" elements or
		classes.</p>

		<br/><div id="box"></div>
    	<a href="javascript:void(0);" onclick="return false;">Click Me!</a>
    
    	<br/><br/><br/><a href="<%=path%>">Home</a>
  </body>
</html>
