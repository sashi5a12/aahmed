<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/contracts/contractsInfoView_body.jsp"/>
    <tiles:put name="headingText" value="Manage Contracts"/>
    <tiles:put name="filterHolder" value="/contracts/contractsInfoViewFilter.jsp"/>
</tiles:insert>