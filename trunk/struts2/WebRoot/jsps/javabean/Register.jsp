<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s"  uri="/struts-tags" %>
<html>
  <head>
    <title>My JSP 'Register.jsp' starting page</title>
  </head>
  <body>
    <s:form action="TransferData">
    	<s:textfield name="user.userName" label="User Name"></s:textfield>
    	<s:password name="user.password" label="Password"></s:password>
    	<s:submit/>
    </s:form>
  </body>
</html>
