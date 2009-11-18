<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/masters/devicesUpdate_body.jsp"/>

    <tiles:put name="headingText" value="Device Management"/>

</tiles:insert>
