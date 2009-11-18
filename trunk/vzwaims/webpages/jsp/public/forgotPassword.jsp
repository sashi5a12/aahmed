<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def-noLeftPane.jsp" %>

<%@ taglib uri="struts-logic" prefix="logic" %>

<tiles:insert beanName="classicLayout" beanScope="request">

    <tiles:put name="headingText" value="Forgot Your Password?"/>

    <tiles:put name="menu" value="/common/blank.jsp"/>

    <logic:notPresent name="AIMS_USER" scope="session">
        <tiles:put name="menubar" value="/public/menubar.jsp"/>
    </logic:notPresent>

    <tiles:put name="body" value="/public/forgotPassword_body.jsp"/>

</tiles:insert>



  