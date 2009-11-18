<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/application/smsApplicationView_body.jsp"/>
    <tiles:put name="headingText">
        <%--<bean:message key="SmsApplicationForm.welcome"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> - --%> 
       	<bean:message	key="SmsApplicationForm.viewApp"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
    </tiles:put>      
</tiles:insert>  
