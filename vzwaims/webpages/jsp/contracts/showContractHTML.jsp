<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def-SimpleLayout.jsp" %>
<tiles:insert beanName="simpleLayout" beanScope="request">

    <tiles:put name="body" value="/contracts/showContractHTML_body.jsp"/>

</tiles:insert>