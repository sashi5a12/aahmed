<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>Fade In Example</title>
    <script type="text/javascript" src="<%=path%>/scripts/jquery-1.3.2.min.js"></script>
    <style type="text/css">
    	#box{
    		background: red;
    		height: 200;
    		width: 500;
    		display: none;
    	}
    </style>
    <script type="text/javascript">
    	$(function (){
    		$('a').click(function (){
    			$('#box').fadeIn(4000);
    		});
    	});    	
    </script>
  </head>
      
  <body>
    <div id="box"></div>
    <a href="javascript:void(0);" onclick="return false;">Click Me!</a>
    
    <br/><br/><br/><a href="<%=path%>">Home</a>
  </body>
</html>
