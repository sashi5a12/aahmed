<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-logic" prefix="logic" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <logic:notPresent name="AIMS_USER" scope="session">
        <tiles:put name="menubar" value="/public/menubar.jsp"/>
    </logic:notPresent>

  <tiles:put name="headingText" value="Become a Content Provider" />

    <tiles:put name="menu" value="/public/landingpages/landing-music-beprovider.jsp"/>

  <tiles:put name="body" value="/alliance/allianceMusicRegistrationResult_body.jsp"/>


</tiles:insert>  


