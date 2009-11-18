<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def-SimplePopUp.jsp" %>
<tiles:insert beanName="popUpLayout" beanScope="request">
    <tiles:put name="body" value="/application/wapFTPTransferResponse_body.jsp"/>
</tiles:insert>