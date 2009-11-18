<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/content/faqsView_body.jsp"/>

    <tiles:put name="headingText">
        <bean:message key="FAQForm.FAQs" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
    </tiles:put>
</tiles:insert>

