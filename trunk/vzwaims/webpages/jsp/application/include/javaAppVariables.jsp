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

boolean isPhaseJavaRfiContentProg = false,
		isPhaseSubmitted = false,
		isPhaseJavaRfiLegalContent = false,
		isPhaseJavaRfiTaxReview = false,
		
		isPhaseJavaContentApproved = false,
		isPhaseJavaLegalApproved = false,
		isPhaseJavaPendingTaxApproval = false,
		isPhaseJavaRejected = false,
		isPhaseJavaApproved = false;
				
boolean isRFI = false;		

boolean isLocked = false;		
boolean isSumitted = false;		
boolean isEqualorAboveProgTaxReview = false;		
boolean isEqualorAboveContentStandardReview = false;
boolean isAboveProgTaxReview = false;
	
boolean showExecute = false;		
boolean lockContentRating = false;
	
boolean isOnDeckApp = false;	
boolean isOffDeckApp = false;	
%>
<logic:present name="javaApplicationUpdateForm" >

	<%-- updates requested before initial approval --%>
	<logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_JAVA_SUBMITTED.toString()%>">
        <% isPhaseSubmitted = true; %>
    </logic:equal>
	<logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SUBMISSION_ID.toString()%>">
        <% isPhaseSubmitted = true; %>
    </logic:equal>

    <%-- updates requested before initial approval --%>
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_JAVA_RFI_CONTENT_PROG.toString()%>">
        <% isPhaseJavaRfiContentProg = true; %>
    </logic:equal>

    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_JAVA_RFI_LEGAL_CONTENT.toString()%>">
        <% isPhaseJavaRfiLegalContent = true; %>
    </logic:equal>

    <%-- updates requested On Content Approval --%>
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_JAVA_RFI_TAX_REVIEW.toString()%>">
        <% isPhaseJavaRfiTaxReview = true; %>
    </logic:equal>
    
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_JAVA_CONTENT_APPROVED.toString()%>">
        <% isPhaseJavaContentApproved = true; %>
    </logic:equal>    
    
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_JAVA_LEGAL_APPROVED.toString()%>">
        <% isPhaseJavaLegalApproved = true; %>
    </logic:equal>
    
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_JAVA_PENDING_TAX_APPROVAL.toString()%>">
        <% isPhaseJavaPendingTaxApproval = true; %>
    </logic:equal>

    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_JAVA_REJECTED.toString()%>">
        <% isPhaseJavaRejected = true; %>
    </logic:equal>

    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_JAVA_APPROVED.toString()%>">
        <% isPhaseJavaApproved = true; %>
    </logic:equal>

	<logic:equal name="javaApplicationUpdateForm" property="currentPage" value="page1" scope="request"	>
		<% isPage1 = true; %>
	</logic:equal>
	<logic:equal name="javaApplicationUpdateForm" property="currentPage" value="page2" scope="request"	>
		<% isPage2 = true; %>
	</logic:equal>
	<logic:equal name="javaApplicationUpdateForm" property="currentPage" value="page3" scope="request"	>
		<% isPage3 = true; %>
	</logic:equal>
	<logic:equal name="javaApplicationUpdateForm" property="currentPage" value="page4" scope="request"	>
		<% isPage4 = true; %>
	</logic:equal>
	<logic:equal name="javaApplicationUpdateForm" property="currentPage" value="page5" scope="request"	>
		<% isPage5 = true; %>
	</logic:equal>
	    
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SAVED_ID.toString()%>">    
        <%statusSaved = true;%>
    </logic:equal>
    
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SUBMISSION_ID.toString()%>">    
        <%statusSubmitted = true;%>    
    </logic:equal>
    
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_JAVA_SUBMITTED.toString()%>">    
        <%statusSubmitted = true;%>    
    </logic:equal>  
    
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_DASHBOARD_INITIAL_APPROVAL.toString()%>">    
        <%statusInitialApproval = true;%>
    </logic:equal>
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_INITIAL_REJECTED.toString()%>">    
        <%statusInitialRejected = true;%>
    </logic:equal>
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_CONTENT_IN_REVIEW.toString()%>">    
        <%statusContentInReview = true;%>
    </logic:equal>
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_CONTENT_APPROVED.toString()%>">    
        <%statusContentApproved = true;%>
    </logic:equal>
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_CONTENT_REJECTED.toString()%>">    
        <%statusContentRejected = true;%>
    </logic:equal>
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_PENDING_PRODUCTION.toString()%>">    
        <%statusPendingProduction = true;%>
    </logic:equal>
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_CHANNEL_REJECTED.toString()%>">    
        <%statusChannelRejected = true;%>
    </logic:equal>
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.PHASE_IN_PRODUCTION.toString()%>">    
        <%statusInProduction = true;%>
    </logic:equal>
    <logic:equal name="javaApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SUNSET_ID.toString()%>">    
        <%statusSunset = true;%>
    </logic:equal>
    
    <logic:equal name="javaApplicationUpdateForm" property="showExecute" scope="request" value="true">    
        <% showExecute = true;%>
    </logic:equal>    
    
    <logic:equal name="javaApplicationUpdateForm" property="lockContentRating" scope="request" value="true">    
        <% lockContentRating = true;%>
    </logic:equal>
    
    <logic:equal name="javaApplicationUpdateForm" property="ring2App" scope="request" value="true">    
        <% isOnDeckApp = true;%>
    </logic:equal>
        
    <logic:equal name="javaApplicationUpdateForm" property="ring3App" scope="request" value="true">    
        <% isOffDeckApp = true;%>
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

<%
	isRFI = isPhaseJavaRfiContentProg || isPhaseJavaRfiLegalContent ||  isPhaseJavaRfiTaxReview;		
	
	System.out.println("-----------------------------------------------------------");
	System.out.println("isPhaseJavaRfiLegalContent " + isPhaseJavaRfiLegalContent);
	System.out.println("isPhaseJavaRfiTaxReview " + isPhaseJavaRfiTaxReview);
	System.out.println("isPhaseJavaContentApproved " + isPhaseJavaContentApproved);
	System.out.println("isPhaseJavaLegalApproved " + isPhaseJavaLegalApproved);
	System.out.println("isPhaseJavaPendingTaxApproval " + isPhaseJavaPendingTaxApproval);
	System.out.println("isPhaseJavaRejected " + isPhaseJavaRejected);
	System.out.println("isPhaseJavaApproved " + isPhaseJavaApproved);
	
	isLocked = isPhaseJavaRfiLegalContent || isPhaseJavaRfiTaxReview || isPhaseJavaContentApproved || isPhaseJavaLegalApproved || isPhaseJavaPendingTaxApproval || isPhaseJavaRejected || isPhaseJavaApproved;
	
	System.out.println("isLocked " + isLocked);
	isSumitted = isPhaseSubmitted || isLocked ; 
	
	isEqualorAboveContentStandardReview = isPhaseJavaContentApproved || isPhaseJavaRfiLegalContent || isPhaseJavaLegalApproved || isPhaseJavaRfiTaxReview || isPhaseJavaPendingTaxApproval || isPhaseJavaRejected || isPhaseJavaApproved;
	
	isEqualorAboveProgTaxReview = isPhaseJavaPendingTaxApproval|| isPhaseJavaRfiTaxReview || isPhaseJavaRejected || isPhaseJavaApproved;
	isAboveProgTaxReview = isPhaseJavaRejected || isPhaseJavaApproved;
	
	System.out.println("isAllianceUser " + isAllianceUser);
	System.out.println("statusSubmitted " + statusSubmitted);
	System.out.println("isAllianceUser " + isAllianceUser);
	System.out.println("isPhaseJavaRfiContentProg " + isPhaseJavaRfiContentProg);
	isLocked = (isAllianceUser && statusSubmitted) || (isAllianceUser && isPhaseJavaRfiContentProg) || isLocked;
%>