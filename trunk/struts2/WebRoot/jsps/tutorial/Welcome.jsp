<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>Welcome</title>
  </head>
  
  <body>
  	<h3>Commands</h3>
  	<ul>
  		<li><a href="<s:url action="Register"/>">Register</a> </li>
  		<li><a href="<s:url action="Logon"/>">Sign On</a>
  	</ul>
  </body>
</html>
