<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/masters/devicesView_body.jsp"/>

    <tiles:put name="headingText">
        <bean:message key="DeviceForm.welcome" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
    </tiles:put>

    <%--<tiles:put name="filterHolder" value="/masters/filterPage.jsp"/>--%>
    
</tiles:insert>
