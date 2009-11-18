<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/content/faqsViewDelete_body.jsp"/>

    <tiles:put name="headingText">
        <bean:message key="FAQForm.Welcome" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
    </tiles:put>

</tiles:insert>
