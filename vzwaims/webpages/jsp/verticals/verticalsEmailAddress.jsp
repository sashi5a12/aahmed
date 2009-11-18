<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/verticals/verticalsEmailAddress_body.jsp"/>
    <tiles:put name="headingText" >
        <bean:message key="verticals.welcome"	bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
    </tiles:put>

</tiles:insert>

