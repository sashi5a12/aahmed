<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/alliance/allianceSalesLeadCreate_body.jsp"/>

    <tiles:put name="headingText">
        <bean:message key="SalesLeadForm.allianceWelcome" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>
    </tiles:put>

</tiles:insert>



  