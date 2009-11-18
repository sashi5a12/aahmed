<%
boolean isPage1=false, isPage2=false,  isPage3=false, isPage4=false, isPage5=false;
boolean isVerizonUser = false, isAllianceUser = false;
boolean statusSaved = false, statusSubmitted = false, statusInitialApproval = false, statusInitialRejected = false;
boolean statusContentInReview=false, statusContentApproved=false, statusContentRejected=false, statusPendingProduction=false;
boolean statusInProduction=false, statusChannelRejected=false, statusSunset=false;
boolean hasAccessInitialApproval=false, hasAccessContentZip=false, hasAccessTracking=false;

boolean isEqualOrAboveInitialApproval = false;
boolean isEqualOrAboveContentApproval = false;
boolean isEqualOrAboveMoveToProduction = false;
boolean isEqualSunset = false;

%>
<logic:present name="DashboardApplicationUpdateForm" >

	<logic:equal name="DashboardApplicationUpdateForm" property="currentPage" value="page1" scope="request"	>
		<% isPage1 = true; %>
	</logic:equal>
	<logic:equal name="DashboardApplicationUpdateForm" property="currentPage" value="page2" scope="request"	>
		<% isPage2 = true; %>
	</logic:equal>
	<logic:equal name="DashboardApplicationUpdateForm" property="currentPage" value="page3" scope="request"	>
		<% isPage3 = true; %>
	</logic:equal>
	<logic:equal name="DashboardApplicationUpdateForm" property="currentPage" value="page4" scope="request"	>
		<% isPage4 = true; %>
	</logic:equal>
	<logic:equal name="DashboardApplicationUpdateForm" property="currentPage" value="page5" scope="request"	>
		<% isPage5 = true; %>
	</logic:equal>
	    
    <logic:equal name="DashboardApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SAVED_ID.toString()%>">    
        <%statusSaved = true;%>
    </logic:equal>
    <logic:equal name="DashboardApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SUBMISSION_ID.toString()%>">    
        <%statusSubmitted = true;%>
    </logic:equal>
    
    <logic:equal name="DashboardApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL.toString()%>">    
        <%statusInitialApproval = true;%>
    </logic:equal>
    <logic:equal name="DashboardApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_INITIAL_REJECTED.toString()%>">    
        <%statusInitialRejected = true;%>
    </logic:equal>
    <logic:equal name="DashboardApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_CONTENT_IN_REVIEW.toString()%>">    
        <%statusContentInReview = true;%>
    </logic:equal>
    <logic:equal name="DashboardApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_CONTENT_APPROVED.toString()%>">    
        <%statusContentApproved = true;%>
    </logic:equal>
    <logic:equal name="DashboardApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_CONTENT_REJECTED.toString()%>">    
        <%statusContentRejected = true;%>
    </logic:equal>
    <logic:equal name="DashboardApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_PENDING_PRODUCTION.toString()%>">    
        <%statusPendingProduction = true;%>
    </logic:equal>
    <logic:equal name="DashboardApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_CHANNEL_REJECTED.toString()%>">    
        <%statusChannelRejected = true;%>
    </logic:equal>
    <logic:equal name="DashboardApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_IN_PRODUCTION.toString()%>">    
        <%statusInProduction = true;%>
    </logic:equal>
    <logic:equal name="DashboardApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SUNSET_ID.toString()%>">    
        <%statusSunset = true;%>
    </logic:equal>
    
</logic:present>

<% isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE); %>
<% isAllianceUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE); %>

<% isEqualOrAboveInitialApproval = statusInitialApproval || statusInitialRejected || statusContentInReview || 
								   statusContentApproved || statusContentRejected || statusPendingProduction || 
								   statusInProduction || statusChannelRejected || statusSunset; %>
<% isEqualOrAboveContentApproval = statusContentApproved || statusContentRejected || statusPendingProduction ||	
								   statusInProduction || statusChannelRejected || statusSunset; %>
<% isEqualOrAboveMoveToProduction = statusInProduction || statusChannelRejected || statusSunset; %>
<% isEqualSunset =  statusSunset;%>								   								   

<% hasAccessInitialApproval = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.DASHBOARD_INITIAL_APPROVAL, AimsSecurityManager.UPDATE); %>
<% hasAccessContentZip = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.DASHBOARD_CONTENT_ZIP_FILE, AimsSecurityManager.UPDATE); %>
<% hasAccessTracking = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.DASHBOARD_TRACKING, AimsSecurityManager.UPDATE); %>