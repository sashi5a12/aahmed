<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%--<%@ include file="/common/tile-def-SimplePopUp.jsp" %>--%>
<%@ include file="/common/tile-def-popUp.jsp" %>
<tiles:insert beanName="popUpLayout" beanScope="request">
    <tiles:put name="body" value="/alliance/jma/jmaNoActiveAcceptedContractPopup_body.jsp"/>
</tiles:insert>


