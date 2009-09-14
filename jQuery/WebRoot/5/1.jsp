<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>appendTo, size and remove method</title>
    
  	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.3.2.min.js"></script>
  	
  	<script type="text/javascript">
  		$(function (){
  			var i = $('li').size() + 1;
  			$('a#add').click(function (){
  				$('<li>'+i+'</li>').appendTo('ul');
  				i++;
  			});
  			
  			$('a#remove').click(function (){
  				$('li:last').remove();
  				i--;
  			});
  		});
  	</script>    
  </head>
  
  <body>
  	Today, we're going to learn how to dynamically create and remove elements. The appendTo(), size(), and remove() methods will be reviewed  	
    <ul>
    	<li>1</li>
    	<li>2</li>
    	<li>3</li>
    	<li>4</li>
    	<li>5</li>
    </ul>	
    <a href="javascript:void(0);" id="add">Add Item</a><br/>
    <a href="javascript:void(0);" id="remove">Remove Item</a>
    
    <br/><br/><br/><a href="<%=path%>">Home</a>
  </body>
</html>



