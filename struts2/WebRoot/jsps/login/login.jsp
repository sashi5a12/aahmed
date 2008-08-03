<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s"  uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
    <s:form action="/login/submitLogin.action">
    	<s:textfield name="userId" label="UserId"></s:textfield><br/>
    	<s:password  name="pwd" label="Password"></s:password><br/>
    	<s:submit/>
    </s:form>
    
  </body>
</html>
