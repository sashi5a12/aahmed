<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/masters/indFocusCreate_body.jsp"/>
</tiles:insert>  