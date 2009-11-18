<table cellpadding="0" cellspacing="0" width="100%"><tr><td>
<div id="divTabs" class="tab">	
	
	<logic:equal name="ApplicationUpdateForm" property="currentPage" scope="request"	value="page1">
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	</logic:equal>	
	<logic:notEqual name="ApplicationUpdateForm" property="currentPage" scope="request"	value="page1">
		<div class="tabinActive">
	</logic:notEqual>
			<a class="a" href="<bean:write	name="ApplicationUpdateForm"	property="setupURL"/>?task=view&appsId=<bean:write name="ApplicationUpdateForm" property="appsId" />" >
				<bean:message key="ManageApplicationForm.tab.application.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div> 
	<logic:notEqual name="ApplicationUpdateForm" property="currentPage" scope="request"	value="page1">
		<div class="divider"> </div>
	</logic:notEqual>
	
	<logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" scope="request"	value="<%=AimsConstants.BREW_PLATFORM_ID.toString()%>">
		<logic:equal name="ApplicationUpdateForm" property="currentPage" scope="request"	value="userGuideView">
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		</logic:equal>		
		<logic:notEqual name="ApplicationUpdateForm" property="currentPage" scope="request"	value="userGuideView">
			<div class="tabinActive">
		</logic:notEqual>
				<a class="a" href="<bean:write	name="ApplicationUpdateForm"	property="setupURL"/>?task=view&viewPageToView=userGuideView&appsId=<bean:write name="ApplicationUpdateForm" property="appsId" />" >
					User Guide
				</a>
			</div> 
		<logic:notEqual name="ApplicationUpdateForm" property="currentPage" scope="request"	value="userGuideView">
			<div class="divider"> </div>
		</logic:notEqual>	
	</logic:equal>
		 
	<%if (AimsSecurityManager.checkAccess(request, ManageApplicationsConstants.PRIV_BREW_EVALUATION_INFO, AimsSecurityManager.SELECT)) {%>
		<logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" scope="request"	value="<%=AimsConstants.BREW_PLATFORM_ID.toString()%>">
			<logic:equal name="ApplicationUpdateForm" property="currentPage" scope="request"	value="evaluationInfo">
				<div class="tabActiveBegin"> </div>
				<div class="tabActive">
			</logic:equal>		
			<logic:notEqual name="ApplicationUpdateForm" property="currentPage" scope="request"	value="evaluationInfo">
				<div class="tabinActive">
			</logic:notEqual>
					<a class="a" href="<bean:write	name="ApplicationUpdateForm"	property="setupURL"/>?task=view&viewPageToView=evaluationInfo&appsId=<bean:write name="ApplicationUpdateForm" property="appsId" />" >
						<bean:message key="ManageApplicationForm.tab.evaluation.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
					</a>
				</div> 
			<logic:notEqual name="ApplicationUpdateForm" property="currentPage" scope="request"	value="evaluationInfo">
				<div class="divider"> </div>
			</logic:notEqual>	
		</logic:equal>
	<% } else {}%> 
	
	<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
	            		
		<logic:equal name="ApplicationUpdateForm" property="currentPage" scope="request"	value="processingInfo">
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		</logic:equal>
		<logic:notEqual name="ApplicationUpdateForm" property="currentPage" scope="request"	value="processingInfo">
			<div class="tabinActive">
		</logic:notEqual>		
				<a class="a" href="<bean:write	name="ApplicationUpdateForm"	property="setupURL"/>?task=view&viewPageToView=processingInfo&appsId=<bean:write name="ApplicationUpdateForm" property="appsId" />" >
					<bean:message key="ManageApplicationForm.tab.processing.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				</a>			
			</div> 
		<logic:notEqual name="ApplicationUpdateForm" property="currentPage" scope="request"	value="processingInfo">
			<div class="divider"> </div>
		</logic:notEqual>		
	<% } else {}%>
	
	<logic:equal name="ApplicationUpdateForm" property="currentPage" scope="request"	value="journal">
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	</logic:equal>
	<logic:notEqual name="ApplicationUpdateForm" property="currentPage" scope="request"	value="journal">
		<div class="tabinActive">
	</logic:notEqual>	
			<a class="a" href="javascript:document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='journal';document.forms[0].submit();" >
				<bean:message key="ManageApplicationForm.tab.journal"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div> 			
	<logic:notEqual name="ApplicationUpdateForm" property="currentPage" scope="request"	value="journal">
		<div class="divider"> </div>
	</logic:notEqual>	
			
</div>
</td></tr></table>
<div>&nbsp;</div>
<logic:notEqual name="ApplicationUpdateForm" property="currentPage" scope="request"	value="page1">
	<table width="100%"  style="float:left" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="35%" nowrap="nowrap">
				<strong><bean:message	key="ManageApplicationForm.appTitle"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>: </strong>
				<bean:write	name="ApplicationUpdateForm"	property="title"	/>			
			</td>
			<td width="35%" nowrap="nowrap">
				<strong>By: </strong><%=AimsApplicationsManager.getAllianceNameOfApplication(ApplicationUpdateForm.getAppsId())%>
			</td>
			<td width="30%" nowrap="nowrap">
				<strong>Status: </strong><bean:write name="ApplicationUpdateForm" property="applicationStatus"/>
			</td>
		</tr>
	</table>
</logic:notEqual>
