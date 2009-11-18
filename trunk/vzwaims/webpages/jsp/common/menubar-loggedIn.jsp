<%@ page isELIgnored="false"%>
<%@ page import="com.netpace.aims.util.ConfigEnvProperties"%>
<%@page import="com.netpace.aims.util.AimsConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
	ConfigEnvProperties envProps=ConfigEnvProperties.getInstance();
	String headerURL = envProps.getProperty("header.url");
%>

<c:set var="var_global_nav_url"><%=headerURL%></c:set>

	<c:set var="var_global_nav_head" scope="session">
		<c:import url="<%=headerURL%>" >
		     <c:param name="firstName" value ="<%=(String)session.getAttribute(AimsConstants.SESSION_FIRST_NAME)%>"/>
		     <c:param name="lastName" value ="<%=(String)session.getAttribute(AimsConstants.SESSION_LAST_NAME)%>"/>
		</c:import>
	</c:set>

<c:out value="${var_global_nav_head}"  escapeXml="false"  />



