<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="headingText">Device Firmware Management</tiles:put>
    <tiles:put name="body" value="/system/firmwareList_body.jsp"/>
</tiles:insert>