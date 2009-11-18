<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ include file="/common/tile-def.jsp" %>

<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="menu" value="/common/blank.jsp"/>

    <tiles:put name="body" value="/public/password_body.jsp"/>

    <logic:notPresent name="AIMS_USER" scope="session">
        <tiles:put name="menubar" value="/public/menubar.jsp"/>
    </logic:notPresent>

    <tiles:put name="headingText" value="Forgot Your Password?"/>
</tiles:insert>



  