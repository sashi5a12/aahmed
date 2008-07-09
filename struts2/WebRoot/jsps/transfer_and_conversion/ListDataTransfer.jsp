<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<body>
		<s:form action="ListDataTransferSubmit">

			<s:textfield name="middleNames[0]" label="middleNames" />
			<s:textfield name="middleNames[1]" label="middleNames" />
			<s:textfield name="middleNames[2]" label="middleNames" />

			<s:textfield name="lastNames" label="lastNames" />
			<s:textfield name="lastNames" label="lastNames" />
			<s:textfield name="lastNames" label="lastNames" />

			<s:textfield name="weights[0]" label="weights" />
			<s:textfield name="weights[1]" label="weights" />
			<s:textfield name="weights[2]" label="weights" />
			<s:submit />
		</s:form>
	</body>
</html>
