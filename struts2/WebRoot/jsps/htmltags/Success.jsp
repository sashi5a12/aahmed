<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
    Select Result: <s:property value="selectResult"/><br/>
    Mutli Select Result:<s:iterator  value="multiSelectResult">
    	<li><s:property/></li>
    </s:iterator>
    <br/>
    Checkbox Result:
    <s:iterator  value="checkboxResult">
    	<li><s:property/></li>
    </s:iterator>
    <br/>
    Radio Result:
    <s:property value="radioResult"/>
    <br/>
    Double list Result:<br/>
    Country: <s:property value="country"/><br/>
    City: <s:property value="city"/>
  </body>
</html>
