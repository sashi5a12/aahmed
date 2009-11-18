<%@ page import="com.netpace.aims.util.ConfigEnvProperties"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
	ConfigEnvProperties envProps=ConfigEnvProperties.getInstance();
	String footerURL = envProps.getProperty("footer.url");
%>

<!-- BEGIN GLOBAL NAVIGATION -->
<c:import url="<%=footerURL%>" ></c:import>
<script language="javascript" type="text/javascript" src="/aims/js/wz_tooltip.js"></script>
<!-- FOOTER END HERE -->


