<%@	page language="java" %>

<%@ page import="com.netpace.aims.model.masters.*, com.netpace.aims.model.alliance.*, com.netpace.aims.util.*;"%>

<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@	taglib uri="/WEB-INF/struts-template.tld" prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<aims:getSpotlightsTab attributeName="<%=request.getParameter("spotLightTypeId")%>"/>