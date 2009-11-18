<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/tile-def-noLeftPane.jsp" %>

<tiles:insert beanName="classicLayout" beanScope="request">
    <logic:notPresent name="AIMS_USER" scope="session">
        <tiles:put name="menubar" value="/public/menubar.jsp"/>
    </logic:notPresent>

    <tiles:put name="body" value="/public/login_body.jsp"/>

    <tiles:put name="headingText" value="Login"/>
</tiles:insert>



  