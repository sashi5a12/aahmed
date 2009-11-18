<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/alliance/allianceContactInfoUpdate_body.jsp"/>
    <tiles:put name="headingText">
        <bean:message key="AllianceContactInfoForm.welcome"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
    </tiles:put>
</tiles:insert>