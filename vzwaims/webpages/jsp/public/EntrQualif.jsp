<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ include file="/common/tile-def.jsp" %>

<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="menu" value="/public/landingpages/landing-enp-entrqualif.jsp"/>
    <logic:notPresent name="AIMS_USER" scope="session">
        <tiles:put name="menubar" value="/public/menubar.jsp"/>
    </logic:notPresent>
    <tiles:put name="body" value="/public/EntrQualif_body.jsp"/>
    <tiles:put name="userbar" value="/common/userbar.jsp"/>
    <tiles:put name="headingText" value="JMA"/>
</tiles:insert>