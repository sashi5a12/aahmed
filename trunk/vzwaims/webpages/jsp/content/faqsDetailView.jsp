<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/content/faqsDetailView_body.jsp"/>
    <tiles:put name="headingText">
        <bean:message key="FAQForm.Welcome"	bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
    </tiles:put>
</tiles:insert>
