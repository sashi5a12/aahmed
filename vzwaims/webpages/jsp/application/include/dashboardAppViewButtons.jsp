<table cellpadding="5" cellspacing=0 border="0" width="100%">
	<tr align="right">
		<td>
			<% if (ApplicationHelper.checkPlatformAccess(request, "edit", AimsConstants.DASHBOARD_PLATFORM_ID)) { %>
			<% if (ApplicationHelper.checkEditAccess(((AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType(), DashboardApplicationUpdateForm.getAimsLifecyclePhaseId())) { %>	

				<div class="redBtn" id="Edit" style="float: right; padding-left: 5px"
					title="Edit">
					<div>
						<div>
							<div
								onClick="document.forms[0].action='/aims/dashboardApplicationSetup.do?task=edit&appsId=<bean:write name="DashboardApplicationUpdateForm" property="appsId" />';document.forms[0].submit()">
								Edit
							</div>
						</div>
					</div>
				</div>

			<% } %>
			<% } %>

			<% if (ApplicationHelper.checkPlatformAccess(request, "delete", AimsConstants.DASHBOARD_PLATFORM_ID)) { %>
			<% if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_DASHBOARD_APPS, AimsSecurityManager.DELETE)) { %>

			<div class="redBtn" style="float: right; padding-left: 5px"
				id="Delete" title="Delete">
				<div>
					<div>
						<div
							onClick="javascript:if(!(window.confirm('<bean:message key="ManageApplicationForm.delete.msg"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />'))) { return false;} else {document.forms[0].action='/aims/dashboardApplicationSetup.do?task=delete&appsId=<bean:write name="DashboardApplicationUpdateForm" property="appsId"/>';document.forms[0].submit();}">
							Delete
						</div>
					</div>
				</div>
			</div>

			<% } %>
			<% }%>

			<div class="blackBtn" style="float: right; padding-left: 5px" id="Cancel" title="Cancel">
				<div>
					<div>
						<div
							onClick="document.forms[0].action='<bean:message key="ManageApplicationForm.manage.app.cancel.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';document.forms[0].submit();">
							Cancel
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>