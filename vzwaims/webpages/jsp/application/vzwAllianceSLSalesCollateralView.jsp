<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/application/vzwAllianceSLSalesCollateralView_body.jsp"/>
   <tiles:put name="headingText" value="Edit Enterprise Application"/>
</tiles:insert> 