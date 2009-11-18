
<%@page import="com.netpace.aims.controller.application.ApplicationHelper"%>
<%@page import="com.netpace.aims.util.AimsConstants"%>
<%@page import="com.netpace.aims.util.AimsPrivilegesConstants"%><%
boolean hasAccessAppProcessAssign, hasAccessAppProcessPrioritization, hasAccessAppProcessBrewRelatedInfo;
boolean hasAccessPrivBrewLegalUser, hasAccessPrivBrewEvaluationInfo;
boolean isVerizonUser, isAllianceUser;
String userType;
%>


<% hasAccessAppProcessAssign = com.netpace.aims.bo.security.AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_ASSIGN, com.netpace.aims.bo.security.AimsSecurityManager.UPDATE); %>
<% hasAccessAppProcessPrioritization = com.netpace.aims.bo.security.AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_PRIORITIZATION, com.netpace.aims.bo.security.AimsSecurityManager.UPDATE); %>
<% hasAccessAppProcessBrewRelatedInfo = com.netpace.aims.bo.security.AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.SECTION_APP_PROCESS_INFO_BREW_RELATED_INFO, com.netpace.aims.bo.security.AimsSecurityManager.UPDATE); %>
<% hasAccessPrivBrewLegalUser = com.netpace.aims.bo.security.AimsSecurityManager.checkAccess(request, com.netpace.aims.bo.application.ManageApplicationsConstants.PRIV_BREW_LEGAL_USER, com.netpace.aims.bo.security.AimsSecurityManager.UPDATE); %>
<% hasAccessPrivBrewEvaluationInfo = com.netpace.aims.bo.security.AimsSecurityManager.checkAccess(request, com.netpace.aims.bo.application.ManageApplicationsConstants.PRIV_BREW_EVALUATION_INFO, com.netpace.aims.bo.security.AimsSecurityManager.UPDATE); %>
<% isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE); %>
<% userType = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType();%>
<table cellpadding="0" cellspacing="0" width="100%"><tr><td>
<div id="divTabs" class="tab">		
	<div class="tabinActive">
		<a class="a" href="entAppsSLRedirect.do?appsId=<%=enterpriseAppsId%>&pageToView=page1" >
			<bean:message key="ManageApplicationForm.tab.basic.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</a>
	</div>
	<div class="divider"> </div>
	
	<%if (!hasAccessPrivBrewLegalUser) {%>
		<div class="tabinActive">
			<a class="a" href="entAppsSLRedirect.do?appsId=<%=enterpriseAppsId%>&pageToView=page2" >
				<bean:message key="ManageApplicationForm.tab.additional.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div>
		<div class="divider"> </div>
	<% } %>
	
	<%-- 
	<div class="tabinActive">
		<a class="a" href="entAppsSLRedirect.do?appsId=<%=enterpriseAppsId%>&pageToView=page5" >
			<bean:message key="ManageApplicationForm.tab.case.studies.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</a>
	</div>
	<div class="divider"> </div>		
	--%>
	<%if(com.netpace.aims.controller.application.JMAApplicationHelper.displayTabBOBO(userType,enterpriseAppsId)) {%>
		<div class="tabinActive">
			<a class="a" href="entAppsSLRedirect.do?appsId=<%=enterpriseAppsId%>&pageToView=pageEntBOBO" >
				<bean:message key="ManageApplicationForm.tab.BOBO.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div>
		<div class="divider"> </div>
	<%} %>
	
	<%if(com.netpace.aims.controller.application.JMAApplicationHelper.displayTabLBS(userType,enterpriseAppsId)){ %>	
		<div class="tabinActive">
			<a class="a" href="entAppsSLRedirect.do?appsId=<%=enterpriseAppsId%>&pageToView=pageEntLBS" >
				<bean:message key="ManageApplicationForm.tab.LBS.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div>
		<div class="divider"> </div>
	<% }%>
	 	
	<div class="tabActiveBegin"> </div>
	<div class="tabActive">
		<a class="a" href="entAppsSpotlights.do?task=view&enterpriseAppsId=<%=enterpriseAppsId%>" >Spotlight Documents</a>
	</div>
	
	<%if (isVerizonUser) {%>		
		<div class="tabinActive">
			<a class="standardSmallLink" href="entAppsSLRedirect.do?appsId=<%=enterpriseAppsId%>&pageToView=page3" >
				<bean:message key="ManageApplicationForm.tab.processing.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div>
		<div class="divider"> </div>
		
		<div class="tabinActive">
			<a class="standardSmallLink" href="entAppsSLRedirect.do?appsId=<%=enterpriseAppsId%>&pageToView=page4" >
				<bean:message key="ManageApplicationForm.tab.journal"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div>
		<div class="divider"> </div>	
	<% } %>	
</div>
</td></tr>

<%
if(ApplicationHelper.checkAccess(request, AimsConstants.VIEW_TASK, AimsPrivilegesConstants.JMA_APPLICATION_SPOTLIGHT_VIEW)){
if(com.netpace.aims.controller.application.JMAApplicationHelper.displaySporlight(enterpriseAppsId)){
 
 %>		
<tr>
	<td>
		<div class="redBtn" style="float: left; margin-top: 10px; margin-bottom: 10px;" title="View Spotlight">
			<div><div><div onclick="javascript: viewSpotlight();">View Spotlight</div></div></div>
		</div>
	</td>
</tr>
<%} 
} %>	
</table>
<div>&nbsp;</div>

<script language="javascript">

function viewSpotlight()
{
	 var url = "/aims/entAppSetupSpotlight.do?task=view&partnerId=<%=request.getParameter("alliance_id")%>&solutionId=<%=enterpriseAppsId%>&isPublic=true";
     var win = window.open(url, "viewSpotlight", "resizable=0,width=870,height=575,scrollbars=1,screenX=100,left=150,screenY=30,top=30,status=0,titlebar=0");
     win.focus();
}
</script>