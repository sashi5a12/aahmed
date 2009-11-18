<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/contacts/contactUpdate_body.jsp"/>

    <tiles:put name="headingText">        
        <logic:match name="ContactForm" property="task" value="create" scope="request">
            <bean:message key="ContactForm.createHeading" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        </logic:match>
        <logic:match name="ContactForm" property="task" value="edit" scope="request">
            <bean:message key="ContactForm.editHeading" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        </logic:match>
    </tiles:put>
    
</tiles:insert>