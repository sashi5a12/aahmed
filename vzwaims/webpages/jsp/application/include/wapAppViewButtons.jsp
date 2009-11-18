<%--Feb 23 2008 hkhan@netpace.com: According to Zon Phase 2 layout --%>
<table cellpadding="5" cellspacing=0 border="0" width="100%">
    <tr align="right">
        <td>

            <%if (ApplicationHelper.checkPlatformAccess(request, "edit", AimsConstants.WAP_PLATFORM_ID)) {%>
            <%if (WapApplicationHelper.checkEditAccess(((AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType(), WapApplicationUpdateForm.getAimsLifecyclePhaseId())) {%>

            <div class="redBtn" id="Edit" style="float:right;padding-left:5px" title="Edit">
                <div>
                    <div>
                        <div onClick="document.forms[0].action='/aims/wapApplicationSetup.do?task=edit&appsId=<bean:write name="WapApplicationUpdateForm" property="appsId" />';document.forms[0].submit()">Edit</div>
                    </div>
                </div>
            </div>

        <% } %>
        <% } %>

        <%if (ApplicationHelper.checkPlatformAccess(request, "delete", AimsConstants.WAP_PLATFORM_ID)) {%>
        <%if (WapApplicationHelper.checkDeleteAccess(((AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType(), WapApplicationUpdateForm.getAimsLifecyclePhaseId())) {%>

        <div class="redBtn" style="float:right;padding-left:5px" id="Delete" title="Delete">
            <div>
                <div>
                    <div onClick="javascript:if(!(window.confirm('<bean:message key="ManageApplicationForm.delete.msg"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />'))) { return false;} else {document.forms[0].action='/aims/wapApplicationSetup.do?task=delete&appsId=<bean:write name="WapApplicationUpdateForm" property="appsId"/>';document.forms[0].submit();}">Delete</div>
                </div>
            </div>
        </div>

    <% } %>
    <% } %>

    <div class="blackBtn" style="float:right;padding-left:5px" id="Cancel" title="Cancel">
        <div>
            <div>
                <div onClick="document.forms[0].action='<bean:message key="ManageApplicationForm.manage.app.cancel.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';document.forms[0].submit();">Cancel</div>
            </div>
        </div>
    </div>
</td>
</tr>
</table>