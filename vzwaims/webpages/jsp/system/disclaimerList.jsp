<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="headingText"><bean:message key="DisclaimerForm.headingText"/></tiles:put>
    <tiles:put name="body" value="/system/disclaimerList_body.jsp"/>
</tiles:insert>