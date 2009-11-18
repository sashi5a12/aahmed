<%
boolean isPage1=false, isPage2=false, isPage3=false, isPage4=false;
boolean hasAccessInitialApproval = false, hasAccessInitialBusinessApproval = false;
boolean hasAccessMoveToPendingDcr = false, hasAccessPendingDcr = false;
boolean hasAccessContentTesting = false, hasAccessContentCompletion = false;
boolean hasAccessContentRemoval = false;
boolean isVerizonUser = false, isAllianceUser = false;
boolean statusSubmitted = false, statusInitialApproval = false, statusInitialDenied = false;
boolean statusBusinessApprovalGranted = false, statusBusinessApprovalDenied = false, statusPendingDcr = false;
boolean statusPendingArm = false, statusSubmittedDcr = false, statusTestingPassed = false;
boolean statusTestingFailed = false, statusPublicationReady = false, statusCompletedInProduction = false;
boolean statusSunset = false;
boolean isEqualOrAboveSubmittedDCR = false;
boolean isEqualOrAboveInitialApprovalDenied = false;
boolean isEqualOrAboveBusinessApprovalGrantedDenied = false;
boolean isRolledBackToPendingDCR = false;
boolean ftpWapImagesAllowed = false;
%>

<logic:present name="WapApplicationUpdateForm" >

	<logic:equal name="WapApplicationUpdateForm" property="currentPage" value="page1" scope="request"	>
		<% isPage1 = true; %>
	</logic:equal>
	<logic:equal name="WapApplicationUpdateForm" property="currentPage" value="page2" scope="request"	>
		<% isPage2 = true; %>
	</logic:equal>
	<logic:equal name="WapApplicationUpdateForm" property="currentPage" value="page3" scope="request"	>
		<% isPage3 = true; %>
	</logic:equal>
	<logic:equal name="WapApplicationUpdateForm" property="currentPage" value="page4" scope="request"	>
		<% isPage4 = true; %>
	</logic:equal>
	
    
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SUBMISSION_ID.toString()%>">    
        <%statusSubmitted = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_INITIAL_APPROVAL_ID.toString()%>">    
        <%statusInitialApproval = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_INITIAL_DENIED_ID.toString()%>"> 
        <%statusInitialDenied = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_BUSINESS_APPROVAL_GRANTED_ID.toString()%>">  
        <%statusBusinessApprovalGranted = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_BUSINESS_APPROVAL_DENIED_ID.toString()%>">   
        <%statusBusinessApprovalDenied = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_PENDING_DCR_ID.toString()%>">    
        <%statusPendingDcr = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_PENDING_ARM_ID.toString()%>">    
        <%statusPendingArm = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_SUBMITTED_DCR_ID.toString()%>">    
        <%statusSubmittedDcr = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_TESTING_PASSED_ID.toString()%>">    
        <%statusTestingPassed = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_TESTING_FAILED_ID.toString()%>">    
        <%statusTestingFailed = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_PUBLICATION_READY_ID.toString()%>">    
        <%statusPublicationReady = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_COMPLETED_IN_PRODUCTION_ID.toString()%>">    
        <%statusCompletedInProduction = true;%>
    </logic:equal>
    <logic:equal name="WapApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SUNSET_ID.toString()%>">    
        <%statusSunset = true;%>
    </logic:equal>

    <logic:equal name="WapApplicationUpdateForm" property="rolledBackToPendingDcr" scope="request" value="Y">
        <%isRolledBackToPendingDCR = true;%>
    </logic:equal>
    
</logic:present>

<% isEqualOrAboveSubmittedDCR = statusSubmittedDcr || statusTestingPassed || statusTestingFailed || statusPublicationReady || statusCompletedInProduction || statusSunset; %>
<% isEqualOrAboveBusinessApprovalGrantedDenied = statusBusinessApprovalGranted || statusBusinessApprovalDenied || statusPendingDcr || isEqualOrAboveSubmittedDCR; %>
<% isEqualOrAboveInitialApprovalDenied = statusInitialApproval || statusInitialDenied || statusPendingArm || isEqualOrAboveBusinessApprovalGrantedDenied; %>

<% hasAccessInitialApproval = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_INITIAL_APPROVAL, AimsSecurityManager.UPDATE); %>
<% hasAccessInitialBusinessApproval = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_INITIAL_BUSINESS_APPROVAL, AimsSecurityManager.UPDATE); %>
<% hasAccessMoveToPendingDcr = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_MOVE_TO_PENDING_DCR, AimsSecurityManager.UPDATE); %>
<% hasAccessPendingDcr = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_PENDING_DCR, AimsSecurityManager.UPDATE); %>
<% hasAccessContentTesting = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_TESTING, AimsSecurityManager.UPDATE); %>
<% hasAccessContentCompletion = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_COMPLETION, AimsSecurityManager.UPDATE); %>
<% hasAccessContentRemoval = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WAP_APP_MANAGE_SECTION_CONTENT_REMOVAL, AimsSecurityManager.UPDATE); %>
<% ftpWapImagesAllowed = (hasAccessPendingDcr && AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.WAP_FTP_IMAGE_MANUAL, AimsSecurityManager.UPDATE));
   ftpWapImagesAllowed = ftpWapImagesAllowed && (statusSubmittedDcr || statusTestingPassed 
                                                || statusPublicationReady || statusCompletedInProduction);
%>

<% isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE); %>
<% isAllianceUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE); %>

