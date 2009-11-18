<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ include file="tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">    
    <tiles:put name="menubar" value="/common/menubar-loggedIn.jsp"/>
</tiles:insert>