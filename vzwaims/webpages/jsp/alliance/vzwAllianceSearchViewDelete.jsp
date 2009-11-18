<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="body" value="/alliance/vzwAllianceSearchViewDelete_body.jsp"/>
    <tiles:put name="headingText">
        <logic:equal name="VZWAllianceForm" property="allianceType" value="ALL" scope="request">
            All Alliances
        </logic:equal>
        <logic:equal name="VZWAllianceForm" property="allianceType" value="MY" scope="request">
            My Alliances
        </logic:equal>
        <logic:equal name="VZWAllianceForm" property="allianceType" value="NEW" scope="request">
            New Alliances
        </logic:equal>
    </tiles:put>
    <tiles:put name="filterHolder" value="/alliance/searchViewFilter.jsp" />
</tiles:insert>
