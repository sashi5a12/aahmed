<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s"  uri="/struts-tags" %>
<html>
  <head>
    <title>My JSP 'Register.jsp' starting page</title>
  </head>
  <body>
    <s:form action="ModelDriven">
    	<s:textfield name="userName" label="User Name"></s:textfield>
    	<s:password name="password" label="Password"></s:password>
    	<s:hidden name="defaultProfileName" value="Adnan Ahmed"></s:hidden>
    	<s:submit/>
    </s:form>
  </body>
</html>