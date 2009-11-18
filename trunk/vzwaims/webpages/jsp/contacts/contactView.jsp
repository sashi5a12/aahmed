<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ include file="/common/tile-def.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/contacts/contactView_body.jsp"/>
    <tiles:put name="headingText">
        <bean:message key="ContactForm.viewContact" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
    </tiles:put>
</tiles:insert>