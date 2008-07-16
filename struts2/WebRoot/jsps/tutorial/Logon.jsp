<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>Logon</title>
  </head>
  
  <body>
  	<s:form action="Logon">
  		<s:textfield name="username" label="User Name"></s:textfield>
  		<s:textfield name="password" label="Password"/>
  		<s:submit/>
  	</s:form>
  </body>
</html>
