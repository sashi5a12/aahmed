<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/contracts/amendmentCreateUpdate_body.jsp"/>
  <tiles:put  name="headingText" value="Manage Amendments"/>
</tiles:insert> 