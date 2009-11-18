<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def-popUp.jsp"%>
<tiles:insert beanName="popUpLayout" beanScope="request">
  <tiles:put name="body" value="/application/dashboardUserGuideRTE_body.jsp"/>
</tiles:insert> 