<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/application/dashboardAppProcessInfoView_body.jsp"/>
    <tiles:put name="headingText">
		<bean:message	key="DashboardApplicationForm.viewApp"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
    </tiles:put>
</tiles:insert>  
