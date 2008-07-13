<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>inputname.jsp</title>
		<s:head/>
	</head>

	<body>
		<s:form action="helloworld">
			<s:textfield name="name" key="helloWorld.yourName" required="true" />
			<s:submit />
		</s:form>
	</body>
</html>
