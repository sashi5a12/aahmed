<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>helloworld.jsp</title>
		<s:head/>
	</head>

	<body>
		<s:property value="customGreeting" />
		<s:set name="greeting" value="customGreeting"></s:set>
		<br />
		<s:bean name="org.apache.struts2.util.Counter" id="counter">
			<s:param name="last" value="7" />
		</s:bean>
		<s:iterator value="#counter">
			<li><s:property /></li>
		</s:iterator>
		<s:bean name="helloworld.HelloWorldAction"  id="hello">
			
		</s:bean>
		<s:property value="#hello.add(10,10)"/>
		<s:property value="#hello.addition()"/>
	</body>
</html>
