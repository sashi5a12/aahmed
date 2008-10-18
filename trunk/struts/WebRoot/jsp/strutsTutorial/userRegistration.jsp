<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<title>User Registration</title>
</head>

<body>
	<h1>
		User Registration
	</h1>
	<html:errors />
	<table>
		<html:form action="userRegistration">
			<tr>
				<td><bean:message key="userRegistration.firstName" />*</td>
				<td><html:text property="firstName" /></td>
			</tr>
			<td><bean:message key="userRegistration.lastName" />*</td>
			<td><html:text property="lastName" /></td>
			<tr>
				<td><bean:message key="userRegistration.userName" />*</td>
				<td><html:text property="userName" /></td>
			</tr>
			<tr>
				<td><bean:message key="userRegistration.email" />*</td>
				<td><html:text property="email" /></td>
			</tr>
			<tr>
				<td><bean:message key="userRegistration.phone" /></td>
				<td><html:text property="phone" /></td>
			</tr>
			<tr>
				<td><bean:message key="userRegistration.fax" /></td>
				<td><html:text property="fax" /></td>
			</tr>
			<tr>
				<td><bean:message key="userRegistration.password" />*</td>
				<td><html:password property="password" /></td>
			</tr>
			<tr>
				<td><bean:message key="userRegistration.password" />*</td>
				<td><html:password property="passwordCheck" /></td>
			</tr>
			<tr>
				<td><html:submit /></td>
				<td><html:cancel /></td>
			</tr>
			<tr>
				<td><html:link action="MyFileDownloadHandler">DownloadAction</html:link> </td>
			</tr>
		</html:form>
	</table>
</body>
</html:html>
