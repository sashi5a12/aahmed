<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  
  <body>
    <s:form action="/interceptor/maptoaction.action" >
    	<s:property value="name"/>
    	<br/>
    	<s:textfield name="name" />
    	<s:submit></s:submit>
    </s:form>
  </body>
</html>
