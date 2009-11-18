<%@ page import="com.netpace.aims.controller.application.ApplicationHelper"%>
<%@ page import="com.netpace.aims.util.AimsConstants"%>
<%@ page import="com.netpace.aims.controller.application.VZAppZoneApplicationUpdateForm"%>
<%@ page import="com.netpace.aims.model.core.AimsUser"%>
<tr><td	width="100%">&nbsp;</td></tr>
<tr><td	width="100%">
    <div id="divButtons">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="100%" >
                    <div style="float:right">
                        <div class="blackBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllCancel" title="Cancel">
                            <div><div><div onClick="document.forms[0].action='<bean:message   key="ManageApplicationForm.manage.app.cancel.url"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';document.forms[0].submit();">Cancel</div></div></div>
                        </div>

                        <%if (ApplicationHelper.checkPlatformAccess(request, "delete", AimsConstants.VZAPPZONE_PLATFORM_ID )) {%>
							<%if (ApplicationHelper.checkDeleteAccess(((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType(), VZAppZoneApplicationUpdateForm.getAimsLifecyclePhaseId())) {%>
								<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllDelete" title="Delete">
									<div><div><div onClick="javascript:if (!(window.confirm('<bean:message key="ManageApplicationForm.delete.msg" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>'))) { return false;} else { document.forms[0].action='/aims/vzAppZoneApplicationSetup.do?task=delete&appsId=<bean:write name="VZAppZoneApplicationUpdateForm" property="appsId" />';document.forms[0].submit();}">Delete</div></div></div>
								</div>							
		                    <% }else {}%>							
						<% } else {}%>
												
						<%if (ApplicationHelper.checkPlatformAccess(request, "edit", AimsConstants.VZAPPZONE_PLATFORM_ID )) {%>
							<%if (ApplicationHelper.checkEditAccess(((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType(), VZAppZoneApplicationUpdateForm.getAimsLifecyclePhaseId())) {%>
							<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AllEdit" title="Edit">
								<div><div><div onClick="document.forms[0].action='/aims/vzAppZoneApplicationSetup.do?task=edit&appsId=<bean:write name="VZAppZoneApplicationUpdateForm" property="appsId" />';document.forms[0].submit();">Edit</div></div></div>
							</div>
		                    <% }else {}%>							
						<% } else {}%>
					</div>                                                           
				</td>
			</tr>
		</table>
	</div>		
</td></tr>		