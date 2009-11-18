<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
  <tiles:put name="body" value="/profile/editProfile_body.jsp"/>

    <tiles:put name="headingText" >
        <bean:message key="AllianceProfileForm.myinfo"	bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
    </tiles:put>

</tiles:insert>