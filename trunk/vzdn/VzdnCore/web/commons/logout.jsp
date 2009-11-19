<%@page import="com.netpace.vzdn.global.VzdnConstants"%>
<%
	session.removeAttribute(VzdnConstants.LOGGED_IN_USER);
    session.invalidate();
    System.out.println("**************************logout successfully from vzdncore************************");
%>
