<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>jQuery Example Home Page</title>
  </head>
  
  <body>
  	<h3>Day One</h3>
    <ul>
    	<li><a href="<%=request.getContextPath()%>/1/1.jsp">Fade out</a></li>
    </ul>
   <h3>Day Two</h3>
    <ul>
    	<li><a href="<%=request.getContextPath()%>/2/1.jsp">Fade In</a></li>
    	<li><a href="<%=request.getContextPath()%>/2/2.jsp">Slide Down</a></li>
    	<li><a href="<%=request.getContextPath()%>/2/3.jsp">Slide In</a></li>
    	<li><a href="<%=request.getContextPath()%>/2/4.jsp">Show</a></li>
    	<li><a href="<%=request.getContextPath()%>/2/5.jsp">Hide</a></li>
    	<li><a href="<%=request.getContextPath()%>/2/6.jsp">CSS Selector</a></li>
    </ul>
   <h3>Day Three</h3>
    <ul>
    	<li><a href="<%=request.getContextPath()%>/3/1.jsp">Animated</a></li>
    </ul>
   <h3>Day Four</h3>
    <ul>
    	<li><a href="<%=request.getContextPath()%>/4/1.jsp">Advanced CSS Selector</a></li>
    </ul>    
   <h3>Day Five</h3>
    <ul>
    	<li><a href="<%=request.getContextPath()%>/5/1.jsp">appendTo, size and remove methods</a></li>
    </ul>    
   <h3>Day Six</h3>
    <ul>
    	<li><a href="<%=request.getContextPath()%>/6/1.jsp">The toggle() and toggleClass() Methods</a></li>
    </ul>    
  </body>
</html>
