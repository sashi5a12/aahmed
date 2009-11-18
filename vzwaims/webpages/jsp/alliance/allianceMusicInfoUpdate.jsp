<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/alliance/allianceMusicInfoUpdate_body.jsp"/>
  <tiles:put name="headingText" value="VCAST Music Information"/>
</tiles:insert> 