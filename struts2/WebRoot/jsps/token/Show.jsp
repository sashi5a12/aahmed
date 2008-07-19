<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  
  <body>
    <s:form action="Token_execute">
    	<s:token></s:token>
    	<s:textfield name="input" label="Enter some text"></s:textfield>
    	<s:submit></s:submit>
    </s:form>
    
  </body>
</html>
