<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/masters/dolLoanedDevicesList_body.jsp"/>
   <tiles:put name="headingText" value="Devices on Loan"/>
   <tiles:put name="filterHolder" value="/masters/dolLoanedDevicedListFilter.jsp"/>
</tiles:insert>