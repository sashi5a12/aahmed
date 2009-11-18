<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/application/entAppProcessInfo_body.jsp"/>
    <tiles:put name="headingText">
    	<logic:present name="EntApplicationUpdateForm">
			<bean:message	key="EntApplicationForm.editApp"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</logic:present>
    
    </tiles:put>
</tiles:insert>  
