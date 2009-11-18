<%@ page import="com.netpace.aims.util.AimsConstants"%>
<%@ page import="com.netpace.aims.util.AimsPrivilegesConstants"%>
<%@ page import="com.netpace.aims.bo.security.AimsSecurityManager"%>
<%
    boolean isPage1=false; //basic info
    boolean isPage2=false; //additional info
    boolean isPage3=false; //additional info
    boolean isProcessingInfo=false; //processing info
    boolean isVZAppZoneJournal=false; //Journal

    boolean isVerizonUser = false, isAllianceUser = false;

    //status
    boolean statusSaved=false;
    boolean statusSubmitted=false;

    boolean statusUnderTesting = false,
            statusInitialDenied = false,
            statusTestingPassed = false,
            statusOTATestPassed = false,
            statusInProduction = false,
            statusSunset = false;

    boolean isEqualOrAboveSubmitted = false;
    boolean isEqualOrAboveInitialApprovalOrDenied = false;
    boolean isEqualOrAboveTesting = false;
    boolean isEqualOrAboveTestingPassed = false;
    boolean isEqualOrAboveOTATestPassed = false;
    boolean isEqualOrAboveProduction = false;
    boolean isSunset = false;

    //access
    boolean hasAccessInitialApproval = false;
    boolean hasAccessBaseTesting = false;    
    boolean hasAccessApplicationManagement = false;
    boolean hasAccessOTATesting = false;
    boolean hasAccessMoveToStaging = false;
    boolean hasAccessMoveToProduction = false;
    boolean hasAccessMoveToSunset = false;

    boolean hasAccessJournalSubmit = false;
    boolean hasAccessJournalView = false;

    boolean isLocked = false;
%>

<logic:present name="VZAppZoneApplicationUpdateForm" >
	<logic:equal name="VZAppZoneApplicationUpdateForm" property="currentPage" value="page1" scope="request"	>
		<% isPage1 = true; %>
	</logic:equal>
	<logic:equal name="VZAppZoneApplicationUpdateForm" property="currentPage" value="page2" scope="request"	>
		<% isPage2 = true; %>
	</logic:equal>
    <logic:equal name="VZAppZoneApplicationUpdateForm" property="currentPage" value="page3" scope="request"	>
		<% isPage3 = true; %>
	</logic:equal>
    <logic:equal name="VZAppZoneApplicationUpdateForm" property="currentPage" value="processingInfo" scope="request"	>
		<% isProcessingInfo = true; %>
	</logic:equal>
    <logic:equal name="VZAppZoneApplicationUpdateForm" property="currentPage" value="vzAppZoneJournal" scope="request"	>
        <% isVZAppZoneJournal = true; %>
	</logic:equal>
    <logic:equal name="VZAppZoneApplicationUpdateForm" property="currentPage" value="vzAppZoneJournalView" scope="request"	>
        <% isVZAppZoneJournal = true; %>
	</logic:equal>


    <%-- Status --%>
    <logic:equal name="VZAppZoneApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SAVED_ID.toString()%>">
        <% statusSaved = true;%>
    </logic:equal>
    <logic:equal name="VZAppZoneApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SUBMISSION_ID.toString()%>">
        <% statusSubmitted = true;%>
    </logic:equal>

    <%-- statusUnderTesting means Initial approval granted --%>
    <logic:equal name="VZAppZoneApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.TESTING_ID.toString()%>">
        <% statusUnderTesting = true; %>
    </logic:equal>

    <logic:equal name="VZAppZoneApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_INITIAL_DENIED_ID.toString()%>">
        <% statusInitialDenied = true; %>
    </logic:equal>

    <logic:equal name="VZAppZoneApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_TEST_PASSED_ID.toString()%>">
        <% statusTestingPassed = true;%>
    </logic:equal>

    <logic:equal name="VZAppZoneApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_OTA_TEST_PASSED_ID.toString()%>">
        <% statusOTATestPassed = true;%>
    </logic:equal>

    <logic:equal name="VZAppZoneApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_IN_PRODUCTION_ID.toString()%>">
        <% statusInProduction = true;%>
    </logic:equal>

    <logic:equal name="VZAppZoneApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SUNSET_ID.toString()%>">
        <% statusSunset = true;%>
    </logic:equal>

</logic:present>

<% isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE); %>
<% isAllianceUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE); %>

<%-- access for sections --%>
<% hasAccessInitialApproval = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_INITIAL_APPROVAL, AimsSecurityManager.UPDATE); %>
<% hasAccessBaseTesting = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_DEVICE_TESTING, AimsSecurityManager.UPDATE); %>
<% hasAccessApplicationManagement = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_APPLICATION_MANAGEMENT, AimsSecurityManager.UPDATE); %>
<% hasAccessOTATesting = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_OTA_TESTING, AimsSecurityManager.UPDATE); %>
<% hasAccessMoveToStaging = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_MOVE_TO_STAGING, AimsSecurityManager.UPDATE); %>
<% hasAccessMoveToProduction = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_MOVE_TO_PRODUCTION, AimsSecurityManager.UPDATE); %>
<% hasAccessMoveToSunset = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_SECTION_SUNSET, AimsSecurityManager.UPDATE); %>
<% hasAccessJournalSubmit = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_JOURNAL_ENTRIES, AimsSecurityManager.UPDATE); %>
<% hasAccessJournalView = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.VZAPPZONE_APP_JOURNAL_ENTRIES, AimsSecurityManager.SELECT); %>

<%
    isSunset = statusSunset;
    isEqualOrAboveProduction = statusInProduction || isSunset;
    isEqualOrAboveOTATestPassed = statusOTATestPassed || isEqualOrAboveProduction;    
    isEqualOrAboveTestingPassed = statusTestingPassed || isEqualOrAboveOTATestPassed;
    isEqualOrAboveInitialApprovalOrDenied = statusUnderTesting || statusInitialDenied || isEqualOrAboveTestingPassed;
    isEqualOrAboveTesting = statusUnderTesting || isEqualOrAboveTestingPassed;
    isEqualOrAboveSubmitted = statusSubmitted || isEqualOrAboveTesting || isEqualOrAboveInitialApprovalOrDenied;
%>

<logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="isAppLocked">
    <logic:equal name="VZAppZoneApplicationUpdateForm" property="isAppLocked" value="<%=AimsConstants.VZAPPZONE_APP_LOCK_APPLICATION[0]%>">
        <%
            isLocked = true;
        %>
    </logic:equal>
</logic:notEmpty>
