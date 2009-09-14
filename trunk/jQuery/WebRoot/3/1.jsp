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
    		height: 300px;
    		width: 300px;
    		position: relative;
    	}
    </style>
  	<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.3.2.min.js"></script>
  	
  	<script type="text/javascript">
  		$(function (){
  			$('#box').click(function (){
  				$(this).animate({"left": "300px"}, 2000);
  				$(this).animate({"width": "50px"});
  			});
  		});
  	</script>    
  </head>
  
  <body>
    <div id="box"></div>	
	<br/><br/><br/><a href="<%=path%>">Home</a>
  </body>
</html>



