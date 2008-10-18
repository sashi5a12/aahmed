<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html> 
	<head>
		<title>JSP for ItemForm form</title>
	</head>
	<body>
		<c:out value="${requestScope.value}"></c:out>
		<br/>
		<html:link action="item?method=add">Add Item</html:link>
		<br/>
		<html:link action="item?method=update">Edit Item</html:link>
		<br/>
		<html:link action="item?method=view">View Item</html:link>
	</body>
</html>

