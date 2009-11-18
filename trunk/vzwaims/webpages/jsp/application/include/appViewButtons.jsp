<tr><td	width="100%">&nbsp;</td></tr>
<tr><td	width="100%">
	<div id="divButtons">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" >				
					<div style="float:right">
	
						<logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" value="<%=AimsConstants.BREW_PLATFORM_ID.toString()%>" scope="request"	>
							<%if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.REPORT_APPLICATION_EVALUATION_FORM, AimsSecurityManager.SELECT)) {%>
								
							<% } %>
						</logic:equal>
						
						<%--<input type="image"	name="AllCancel" <bean:message key="images.cancel.button.lite"/> onClick="document.forms[0].action='<bean:message	key="ManageApplicationForm.manage.app.cancel.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';"/>--%>
						<div class="blackBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllCancel" title="Cancel">
							<div><div><div onClick="document.forms[0].action='<bean:message	key="ManageApplicationForm.manage.app.cancel.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';document.forms[0].submit();">Cancel</div></div></div>
						</div>
											
						<%if (ApplicationHelper.checkPlatformAccess(request, "delete", ApplicationUpdateForm.getAimsPlatformId())) {%>
							<%if (ApplicationHelper.checkDeleteAccess(((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType(), ApplicationUpdateForm.getAimsLifecyclePhaseId())) {%>
								<%--<input type="image"	name="AllDelete" <bean:message key="images.delete.button.lite"/> onClick="javascript:if (!(window.confirm('<bean:message key="ManageApplicationForm.delete.msg"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>'))) { return false;} else { document.forms[0].action='<bean:write	name="ApplicationUpdateForm"	property="setupURL"/>?task=delete&appsId=<bean:write name="ApplicationUpdateForm" property="appsId" />';}"/>--%>
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllDelete" title="Delete">
									<div><div><div onClick="javascript:if (!(window.confirm('<bean:message key="ManageApplicationForm.delete.msg"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>'))) { return false;} else { document.forms[0].action='<bean:write	name="ApplicationUpdateForm"	property="setupURL"/>?task=delete&appsId=<bean:write name="ApplicationUpdateForm" property="appsId" />';document.forms[0].submit();}">Delete</div></div></div>
								</div>								
							<% }else {}%>
						<% }else {}%>
						
						<%if (ApplicationHelper.checkPlatformAccess(request, "edit", ApplicationUpdateForm.getAimsPlatformId())) {%>
							<%if (ApplicationHelper.checkEditAccess(((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType(), ApplicationUpdateForm.getAimsLifecyclePhaseId())) {%>
								<%--<input type="image"	name="AllEdit" <bean:message key="images.edit.button.lite"/> onClick="document.forms[0].action='<bean:write	name="ApplicationUpdateForm"	property="setupURL"/>?task=edit&appsId=<bean:write name="ApplicationUpdateForm" property="appsId" />';"/>--%>							
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllEdit" title="Edit">
									<div><div><div onClick="document.forms[0].action='<bean:write	name="ApplicationUpdateForm"	property="setupURL"/>?task=edit&appsId=<bean:write name="ApplicationUpdateForm" property="appsId" />';document.forms[0].submit();">Edit</div></div></div>
								</div>								
							<% }else {}%>
						<% }else {}%>
					
					</div>                                                           
				</td>
			</tr>
		</table>
	</div>		
</td></tr>