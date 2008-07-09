<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<body>
		<s:form action="UserDataTransferSubmit">

			<s:textfield name="users[0].name" label="Name" />
			<s:textfield name="users[0].age" label="Age" />
			<s:textfield name="users[0].dob" label="DOB" />
			<s:textfield name="users[0].weight" label="Weight" />
			<br/>
			<s:textfield name="users[1].name" label="Name" />
			<s:textfield name="users[1].age" label="Age" />
			<s:textfield name="users[1].dob" label="DOB" />
			<s:textfield name="users[1].weight" label="Weight" />
			
			<!--<s:textfield name="users.name" label="Name" />
			<s:textfield name="users.age" label="Age" />
			<s:textfield name="users.name" label="Name" />
			<s:textfield name="users.age" label="Age" />
			<s:textfield name="users.name" label="Name" />
			<s:textfield name="users.age" label="Age" />
			<s:textfield name="users.name" label="Name" />
			<s:textfield name="users.age" label="Age" />
			<s:textfield name="users.name" label="Name" />
			<s:textfield name="users.age" label="Age" />-->
			<s:submit />
		</s:form>
	</body>
</html>
