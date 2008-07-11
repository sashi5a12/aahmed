<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
	URL = <s:url value="IteratorTag.action"/>
	<a href='<s:url value="IteratorTag.action" />'> Click Me </a>
	<br/>
	URL = <s:url action="IteratorTag" id="myUrl">
			<s:param name="id" value="2"/>
		  </s:url>
	<a href='<s:property value="#myUrl" />'> Click Me </a>	  
</body>
</html>
