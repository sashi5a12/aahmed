<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<html> 
	<body>
		<c:out value="${requestScope.value}"></c:out>
		<br/>
		<html:link action="admin/admin?method=add">Add Admin</html:link>
	</body>
</html>

