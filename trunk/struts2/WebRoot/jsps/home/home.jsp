<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
    user home page.
    <s:property value="#session['TEST']"/>
    <s:property value="%{pwd}"/>
  </body>
</html>
