<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ include file="/common/tile-def-noLeftPane.jsp" %>

<tiles:insert beanName="classicLayout" beanScope="request">
    <logic:notPresent name="AIMS_USER" scope="session">
        <tiles:put name="menubar" value="/public/menubar.jsp"/>
    </logic:notPresent>
    <tiles:put name="menu" value="/public/landingpages/landing-music-menu.jsp"/>    
    <tiles:put name="body" value="/alliance/allianceMusicRegistrationUpdate_body.jsp"/>
    <tiles:put name="headingText" value="Become a Content Provider" />    
</tiles:insert>