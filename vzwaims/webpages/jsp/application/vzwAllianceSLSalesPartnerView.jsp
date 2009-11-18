<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/application/vzwAllianceSLSalesPartnerView_body.jsp"/>
    <tiles:put name="headingText"  value="Edit Enterprise Application"/>      
</tiles:insert> 