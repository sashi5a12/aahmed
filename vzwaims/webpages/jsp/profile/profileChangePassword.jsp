<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/profile/profileChangePassword_body.jsp"/>
  <tiles:put name="headingText" >Change Password</tiles:put>
</tiles:insert>