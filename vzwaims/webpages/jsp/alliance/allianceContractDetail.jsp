<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">

    <tiles:put name="body" value="/alliance/allianceContractDetail_body.jsp"/>

    <tiles:put name="headingText" value="Alliance Contract Detail"/>

</tiles:insert>