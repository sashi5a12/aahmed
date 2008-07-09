<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
    <s:form action="ArrayDataTransferSubmit">
    
	    <s:textfield name="ages" label="Ages"/>
	    <s:textfield name="ages" label="Ages"/>
	    <s:textfield name="ages" label="Ages"/>
	    
	    <s:textfield name="names[0]" label="Names"/>
	    <s:textfield name="names[1]" label="Names"/>
	    <s:textfield name="names[2]" label="Names"/>
	    
	    <s:submit/>
    </s:form>
  </body>
</html>
