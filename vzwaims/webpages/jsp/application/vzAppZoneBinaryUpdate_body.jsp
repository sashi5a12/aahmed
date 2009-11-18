<%@	page language="java"  %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<script language="javascript">
    function submitPopupForm() {
        var buttonsRow = document.getElementById('buttonRow');
        if(buttonsRow) {
            buttonsRow.style.display='none';
        }
        document.forms[0].submit(); 
    }
</script>

<%-- if upload images successfully then close this window, and refresh parent --%>
<logic:messagesPresent message="true">
        <script language="javascript">
            var parent = window.opener;
            //if this screen is opened in a pop up window
            if(parent && !parent.closed) {
                <%--
                    if parent page is binaries page (as refreshBinariesPage is available only in binaries page),
                    then refresh it
                --%>
                if(parent.refreshBinariesPage) {
                    parent.refreshBinariesPage();
                }
                self.close();
            }

        </script>
</logic:messagesPresent>

<%-- if there is any error, show here --%>

    <table cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td width="465px"><div style="height:100%; "><%@ include file="/common/popUpError.jsp" %></div></td>
        </tr>
    </table>

<%-- width is 60px less than width of window called from parent window. (to support IE6) --%>

<DIV class="homeColumnTab" style="height:100%;width:465px;">
    <html:form action="/vzAppZoneBinaryUpdate.do?task=save" enctype="multipart/form-data">
        <html:hidden property="appsId" />
        <html:hidden property="binaryId" />

        <html:hidden property="documentFileFileName"  />
        <html:hidden property="documentFileTempFileId"  />

        <html:hidden property="task" value="save"  />

        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <logic:messagesNotPresent message="true">
                <logic:notPresent name="applicationAccessDenied">
                <%-- width of H1 is 20px less than width of parent div--%>
                <tr>
                    <td>
                        <DIV class="headLeftCurveblk"></DIV>
                        <H1 style="width:445px;">Edit Binary</H1>
                        <DIV class="headRightCurveblk"></DIV>
                    </td>
                </tr>
                <tr><td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                        <tr>
                            <th><strong>Binary ID&nbsp;:</strong></th>
                            <th><strong>&nbsp;</strong></th>
                        </tr>
                        <tr>
                            <td	valign="top">
                                <c:out value="${VZAppZoneBinaryUpdateForm.binaryId}"/>
                            </td>
                            <td	valign="top">&nbsp;</td>
                        </tr>
                        <tr><td colspan="2">&nbsp;</td></tr>
                        <tr>
                            <td><strong>Binary File&nbsp;:</strong></td>
                            <td><strong>&nbsp;</strong></td>
                        </tr>
                        <tr>
                            <td	valign="top" colspan="2">
                                <logic:notEmpty	name="VZAppZoneBinaryUpdateForm"	property="binaryFileFileName" scope="request">
                                    <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryFile&vzAppBinaryId=<c:out value="${VZAppZoneBinaryUpdateForm.binaryId}"/>&app_id=<c:out value="${VZAppZoneBinaryUpdateForm.appsId}"/>" class="a"	target="_blank">
                                        <c:out value="${VZAppZoneBinaryUpdateForm.binaryFileFileName}"/>
                                    </a>
                                </logic:notEmpty>
                                &nbsp;
                            </td>
                        </tr>
                        <tr><td colspan="2">&nbsp;</td></tr>
                        <tr>
                            <td><strong>Version&nbsp;:</strong></td>
                            <td><strong>File Size&nbsp;:</strong></td>
                        </tr>
                        <tr>
                            <td	valign="top">
                                <c:out value="${VZAppZoneBinaryUpdateForm.binaryVersion}"/>
                            </td>
                            <td	valign="top">
                                <c:out value="${VZAppZoneBinaryUpdateForm.binaryFileSize}"/>
                            </td>
                        </tr>
                        <tr><td colspan="2">&nbsp;</td></tr>
                        <tr>
                            <td><strong>Preview File&nbsp;:</strong></td>
                            <td><strong>&nbsp;</strong></td>
                        </tr>
                        <tr>
                            <td	valign="top" colspan="2">
                                <logic:notEmpty	name="VZAppZoneBinaryUpdateForm"	property="binaryFileFileName" scope="request">
                                    <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryPreviewFile&vzAppBinaryId=<c:out value="${VZAppZoneBinaryUpdateForm.binaryId}"/>&app_id=<c:out value="${VZAppZoneBinaryUpdateForm.appsId}"/>" class="a"	target="_blank">
                                        <c:out value="${VZAppZoneBinaryUpdateForm.previewFileFileName}"/>
                                    </a>
                                </logic:notEmpty>
                                &nbsp;
                            </td>
                        </tr>
                        <tr><td colspan="2">&nbsp;</td></tr>
                        <tr>
                            <td><strong>ZIP Binary Document File&nbsp;:</strong></td>
                            <td><strong>&nbsp;</strong></td>
                        </tr>
                        <tr>
                            <td	valign="top" colspan="2">
                                <html:file property="documentFile"/><br/>
                                <logic:notEmpty	name="VZAppZoneBinaryUpdateForm"	property="documentFileFileName" scope="request">
                                    <logic:equal name="VZAppZoneBinaryUpdateForm" property="documentFileTempFileId"	scope="request"	value="0">
                                        <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryDocumentFile&vzAppBinaryId=<c:out value="${VZAppZoneBinaryUpdateForm.binaryId}"/>&app_id=<c:out value="${VZAppZoneBinaryUpdateForm.appsId}"/>"	class="a"	target="_blank">
                                    </logic:equal>
                                    <logic:notEqual	name="VZAppZoneBinaryUpdateForm"	property="documentFileTempFileId" scope="request" value="0">
                                        <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&tempFileId=<c:out value="${VZAppZoneBinaryUpdateForm.documentFileTempFileId}"/>&app_id=<c:out value="${VZAppZoneBinaryUpdateForm.appsId}"/>" class="a" target="_blank">
                                    </logic:notEqual>
                                        <c:out value="${VZAppZoneBinaryUpdateForm.documentFileFileName}"/>
                                        </a>
                                </logic:notEmpty>
                            </td>
                        </tr>
                    </table>
                </td></tr>
                <tr id="buttonRow"><td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td colspan="2" height="25" align="right" valign="middle">
                                <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Save" title="Submit">
                                    <div>
                                        <div>
                                            <div onClick="javascript:submitPopupForm();">Save and Close</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="blackBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Cancel" title="Cancel">
                                    <div>
                                        <div><div onclick="javascript:window.close();">Cancel</div></div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </td></tr>
                </logic:notPresent>
            </logic:messagesNotPresent>
        </table>
    </html:form>
</DIV>