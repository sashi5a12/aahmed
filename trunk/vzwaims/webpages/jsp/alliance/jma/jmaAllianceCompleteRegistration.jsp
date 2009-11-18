<%--
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ include file="/common/tile-def-noLeftPane.jsp" %>

<tiles:insert beanName="classicLayout" beanScope="request">

    <logic:notPresent name="AIMS_USER" scope="session">
        <tiles:put name="menubar" value="/public/menubar.jsp"/>
    </logic:notPresent>

    <tiles:put name="body" value="/alliance/jma/jmaAllianceCompleteRegistration_body.jsp"/>

    <tiles:put name="headingText">
        <bean:message key="JMAAllianceRegistrationForm.Complete" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
    </tiles:put>
</tiles:insert>
--%>

<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/alliance/jma/jmaAllianceCompleteRegistration_body.jsp"/>

    <tiles:put name="headingText">
        <bean:message key="JMAAllianceRegistrationForm.Complete" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
    </tiles:put>

</tiles:insert>