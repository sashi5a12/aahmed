<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@ taglib uri="struts-logic" prefix="logic" %>

<%@ include file="/common/tile-def.jsp" %>
<tiles:insert beanName="classicLayout" beanScope="request">
    <tiles:put name="filterHolder" value="/alliance/manageAlliance_filter.jsp" />

    <tiles:put name="body" value="/alliance/vzwAllianceViewDelete_body.jsp"/>
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
        <logic:equal name="VZWAllianceForm" property="allianceType" value="MUSIC_ALL" scope="request">
            ALL VCAST Music Alliances
        </logic:equal>
        <logic:equal name="VZWAllianceForm" property="allianceType" value="JMA_ALL" scope="request">
            All JMA Alliances
        </logic:equal>
        <logic:equal name="VZWAllianceForm" property="allianceType" value="JMA_Partner" scope="request">
            All JMA Partners
        </logic:equal>
    </tiles:put>    

</tiles:insert>
