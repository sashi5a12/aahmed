<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/alliance/allianceBusinessInfoUpdate_body.jsp"/>
    <tiles:put name="headingText">
        <bean:message key="AllianceBusInfoForm.welcome" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
    </tiles:put>
</tiles:insert>