<%
boolean isPage1=false, isPage2=false, isPage3=false, isPage4=false, isPage5=false, isPage6 = false , isPage7 = false; 
boolean hasAccessAppProcessAssign, hasAccessAppProcessPrioritization;
boolean hasAccessPrivBrewLegalUser, hasAccessPrivBrewEvaluationInfo;
boolean isVerizonUser, isAllianceUser;
boolean statusSaved = false;
boolean isBrewApp=false, isEnterpriseApp=false, isMmsApp=false, isSmsApp=false, isWapApp=false, isJavaApp=false;

String enterpriseAppsId = null;
com.netpace.aims.controller.application.EntApplicationUpdateForm Entfrm = (com.netpace.aims.controller.application.EntApplicationUpdateForm) request.getAttribute("EntApplicationUpdateForm");
if ( Entfrm == null ) enterpriseAppsId = (String) request.getParameter("enterpriseAppsId");
else enterpriseAppsId = Entfrm.getAppsId().toString();
%>

<logic:present name="ApplicationUpdateForm" >
	<logic:equal name="ApplicationUpdateForm" property="currentPage" value="page1" scope="request"	>
		<% isPage1 = true; %>
	</logic:equal>
	
	<logic:equal name="ApplicationUpdateForm" property="currentPage" value="page2" scope="request"	>
		<% isPage2 = true; %>
	</logic:equal>
	
	<logic:equal name="ApplicationUpdateForm" property="currentPage" value="page3" scope="request"	>
		<% isPage3 = true; %>
	</logic:equal>
	
	<logic:equal name="ApplicationUpdateForm" property="currentPage" value="page4" scope="request"	>
		<% isPage4 = true; %>
	</logic:equal>
	
	<logic:equal name="ApplicationUpdateForm" property="currentPage" value="page5" scope="request"	>
		<% isPage5 = true; %>
	</logic:equal>
	
	<logic:equal name="ApplicationUpdateForm" property="currentPage" value="page7" scope="request"	>
		<% isPage7 = true; %>
	</logic:equal>

	<logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.BREW_PLATFORM_ID.toString()%>" scope="request"	>
		<% isBrewApp = true; %>
	</logic:equal>
	
	<logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.ENTERPRISE_PLATFORM_ID.toString()%>" scope="request"	>
		<% isEnterpriseApp = true; %>
	</logic:equal>
	
	<logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.MMS_PLATFORM_ID.toString()%>" scope="request"	>
		<% isMmsApp = true; %>
	</logic:equal>
	
	<logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.SMS_PLATFORM_ID.toString()%>" scope="request"	>
		<% isSmsApp = true; %>
	</logic:equal>
	
	<logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.WAP_PLATFORM_ID.toString()%>" scope="request"	>
		<% isWapApp = true; %>
	</logic:equal>

	<logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.JAVA_PLATFORM_ID.toString()%>" scope="request"	>
		<% isJavaApp = true; %>
	</logic:equal>

    <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" value="<%=AimsConstants.SAVED_ID.toString()%>">    
        <% statusSaved = true; %>
    </logic:equal>
	
</logic:present>
<logic:notPresent name="ApplicationUpdateForm" >
	<% isEnterpriseApp = true; %>
	<% isPage6 = true; %>
</logic:notPresent>

<% hasAccessAppProcessAssign = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_ASSIGN, AimsSecurityManager.UPDATE); %>
<% hasAccessAppProcessPrioritization = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_PRIORITIZATION, AimsSecurityManager.UPDATE); %>
<% hasAccessPrivBrewLegalUser = AimsSecurityManager.checkAccess(request, ManageApplicationsConstants.PRIV_BREW_LEGAL_USER, AimsSecurityManager.UPDATE); %>
<% hasAccessPrivBrewEvaluationInfo = AimsSecurityManager.checkAccess(request, ManageApplicationsConstants.PRIV_BREW_EVALUATION_INFO, AimsSecurityManager.UPDATE); %>



<% isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE); %>
<% isAllianceUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE); %>

<table cellpadding="0" cellspacing="0" width="100%"><tr><td>
<div id="divTabs" class="tab">	
	
	<%if (isPage1) {%>
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	<% } else { %>
		<div class="tabinActive">
	<% } %>
			<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page1';document.forms[0].submit();" >
				<bean:message key="ManageApplicationForm.tab.basic.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div>
	<%if (isPage1 == false) {%>
		<div class="divider"> </div>
	<%}%>
	
	<%if (!hasAccessPrivBrewLegalUser) {%>
		<%if (isPage2) {%>
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		<% } else { %>
			<div class="tabinActive">
		<% } %>
				<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page2';document.forms[0].submit();" >
					<bean:message key="ManageApplicationForm.tab.additional.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				</a>
			</div>
		<%if (isPage2 == false) {%>
			<div class="divider"> </div>
		<%}%>	
	<% } %>

	<%if (isBrewApp) {%>
		<%if (isPage7) {%>
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		<% } else { %>
			<div class="tabinActive">
		<% } %>
				<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page7';document.forms[0].submit();" >
					<bean:message key="ManageApplicationForm.tab.userguide.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				</a>
			</div>
		<%if (isPage7 == false) {%>
			<div class="divider"> </div>
		<%}%>	
	<% } %>
		
	<%if (isJavaApp) {%>
		<%if (isPage7) {%>
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		<% } else { %>
			<div class="tabinActive">
		<% } %>
				<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page7';document.forms[0].submit();" >
					<bean:message key="ManageApplicationForm.tab.userguide.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				</a>
			</div>
		<%if (isPage7 == false) {%>
			<div class="divider"> </div>
		<%}%>	
	<% } %>
		
	<%if (isEnterpriseApp) {%>
		<%if (isPage5) {%>
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		<% } else { %>
			<div class="tabinActive">
		<% } %>
				<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page5';document.forms[0].submit();" >
					<bean:message key="ManageApplicationForm.tab.case.studies.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				</a>
			</div>
		<%if (isPage5 == false) {%>
			<div class="divider"> </div>
		<%}%>			
		<% if (AimsEntAppsManager.checkAccessToSpotlight(enterpriseAppsId)) {%>                                
			<%if (isPage6) {%>
				<div class="tabActiveBegin"> </div>
				<div class="tabActive">
			<% } else {%>
				<div class="tabinActive">
			<% } %>
					<a class="a" href="entAppsSpotlights.do?task=view&enterpriseAppsId=<%=enterpriseAppsId%>" >Spotlight</a>
				</div>
			<%if (isPage6 == false) {%>
				<div class="divider"> </div>
			<%}%>				
		<% } %>
	<% } %>
	
	<%if (isVerizonUser) {%>
		
		<%if (hasAccessPrivBrewEvaluationInfo) {%>
			<%if (isBrewApp) {%>
				<%if (isPage5) {%>
					<div class="tabActiveBegin"> </div>
					<div class="tabActive">
				<% } else { %>
					<div class="tabinActive">
				<% } %>				
						<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page5';document.forms[0].submit();" >
							<bean:message key="ManageApplicationForm.tab.evaluation.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
						</a>
					</div>
				<%if (isPage5 == false) {%>
					<div class="divider"> </div>
				<%}%>					
			<% } %>
	  	<% } %>
		
		<%if (isPage3) {%>
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		<% } else { %>
			<div class="tabinActive">
		<% } %>			
				<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page3';document.forms[0].submit();" >
					<bean:message key="ManageApplicationForm.tab.processing.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				</a>
			</div>
		<%if (isPage3 == false) {%>
			<div class="divider"> </div>
		<%}%>
	
		<%if (isPage4) {%>
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		<% } else { %>
			<div class="tabinActive">
		<% } %>
				<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page4';document.forms[0].submit();" >
					<bean:message key="ManageApplicationForm.tab.journal"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				</a>
			</div>
		<%if (isPage4 == false) {%>
			<div class="divider"> </div>
		<%}%>			
	<% } %>	
</div>
</td></tr></table>
<div>&nbsp;</div>
<%if (!isPage1) {%>
	<table width="100%"  style="float:left" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="35%" nowrap="nowrap">
				<strong><bean:message key="ManageApplicationForm.appTitle" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>: </strong>
				<bean:write name="ApplicationUpdateForm"	property="title"/>				
			</td>
			<td width="35%" nowrap="nowrap">
				<strong>By: </strong><%=AimsApplicationsManager.getAllianceNameOfApplication(ApplicationUpdateForm.getAppsId())%>
			</td>
			<td width="30%" nowrap="nowrap">
				<strong>Status: </strong><bean:write name="ApplicationUpdateForm" property="applicationStatus"/>
			</td>
		</tr>
	</table>
<% } %>