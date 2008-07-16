<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<s:head/>
  </head>
  
  <body>
    <s:form action="Submit" method="post">
    	<s:textfield name="userName" label="User Name" />
    	<s:password name="password" required="true" label="Password" showPassword="true"/>
    	<s:textfield name="email" required="true" label="Email"/>
    	<s:textfield name="firstName" required="true" label="First Name"/>
    	<s:textfield name="lastName" required="true" label="Last Name"/>
    	<s:submit/>
    </s:form>
  </body>
</html>
