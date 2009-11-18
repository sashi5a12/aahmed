<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>


<%@ include file="/common/tile-def-noLeftPane.jsp" %>

<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="menu" value="/common/blank.jsp"/>
  <tiles:put name="body" value="/vzdn/showoffer_body.jsp"/>
  <tiles:put name="userbar" value="/common/userbar.jsp"/>

  <tiles:put name="headingText" value="Offers available for User" />

</tiles:insert>
