<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">

  <tiles:put name="body" value="/alliance/vzwAllianceContractsSaveView_body.jsp"/>
  <tiles:put name="headingTest" value="Manage Contracts" />

</tiles:insert>