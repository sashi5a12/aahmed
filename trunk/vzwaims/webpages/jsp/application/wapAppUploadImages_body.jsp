<%@	page language="java"  %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<%-- if upload images successfully then close this window, and refresh parent --%>
<logic:messagesPresent message="true">
        <script language="javascript">
            var parent = window.opener;
            //if this screen is opened in a pop up window
            if(parent && !parent.closed) {
                    parent.location.reload(true);
                    window.close();
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
    <html:form action="/wapAppImageUpload.do?task=save" enctype="multipart/form-data">
        <html:hidden property="appsId" />
        <html:hidden property="appMediumLargeImageFileName"  />
        <html:hidden property="appQVGAPotraitImageFileName"  />
        <html:hidden property="appQVGALandscapeImageFileName"  />

        <html:hidden property="appMediumLargeImageTempFileId"  />
        <html:hidden property="appQVGAPotraitImageTempFileId"  />
        <html:hidden property="appQVGALandscapeImageTempFileId"  />
        <html:hidden property="task" value="save"  />

        <html:hidden property="title"/>
        <html:hidden property="applicationStatus"/>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <logic:messagesNotPresent message="true">
                <logic:notPresent name="applicationAccessDenied">
                <%-- width of H1 is 20px less than width of parent div--%>
                <tr>
                    <td>
                        <DIV class="headLeftCurveblk"></DIV>
                        <H1 style="width:445px;"><bean:message key="ApplicationForm.table.head.app.files"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                        <DIV class="headRightCurveblk"></DIV>
                    </td>
                </tr>
                <tr><td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                        <tr>
                            <th width="100%"><bean:message key="WapApplicationForm.label.appMediumLargeImage" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><span class="requiredText">*</span>:</th>
                        </tr>
                        <tr>
                            <td	valign="top">
                                <html:file property="appMediumLargeImage"/> <bean:message key="WapApplicationForm.label.appMediumLargeImage.filesize"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> <br/>
                                <logic:notEmpty	name="WapAppImageUploadForm" property="appMediumLargeImageFileName" scope="request">
                                    <logic:equal name="WapAppImageUploadForm" property="appMediumLargeImageTempFileId"	scope="request"	value="0">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapAppMediumLargeImage&app_id=<bean:write	name="WapAppImageUploadForm" property="appsId" />"	class="a" target="_blank">
                                    </logic:equal>
                                    <logic:notEqual	name="WapAppImageUploadForm" property="appMediumLargeImageTempFileId" scope="request" value="0">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write name="WapAppImageUploadForm"	property="appMediumLargeImageTempFileId" />" class="a" target="_blank">
                                    </logic:notEqual>
                                        <bean:write	name="WapAppImageUploadForm" property="appMediumLargeImageFileName"/>
                                        </a>
                                </logic:notEmpty>
                            </td>
                        </tr>
                        <tr>
                            <td><strong><bean:message key="WapApplicationForm.label.appQVGAPotraitImage" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><span class="requiredText">*</span>:</strong></td>
                        </tr>
                        <tr>
                            <td	valign="top">
                                <html:file property="appQVGAPotraitImage"/> <bean:message key="WapApplicationForm.label.appQVGAPotraitImage.filesize"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> <br/>
                                <logic:notEmpty	name="WapAppImageUploadForm"	property="appQVGAPotraitImageFileName" scope="request">
                                    <logic:equal name="WapAppImageUploadForm" property="appQVGAPotraitImageTempFileId"	scope="request"	value="0">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapAppQVGAPotraitImage&app_id=<bean:write	name="WapAppImageUploadForm"	property="appsId"	/>"	class="a"	target="_blank">
                                    </logic:equal>
                                    <logic:notEqual	name="WapAppImageUploadForm"	property="appQVGAPotraitImageTempFileId" scope="request" value="0">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="WapAppImageUploadForm"	property="appQVGAPotraitImageTempFileId" />" class="a" target="_blank">
                                    </logic:notEqual>
                                        <bean:write	name="WapAppImageUploadForm"	property="appQVGAPotraitImageFileName"/>
                                        </a>
                                </logic:notEmpty>
                            </td>
                        </tr>
                        <tr>
                            <td><strong><bean:message key="WapApplicationForm.label.appQVGALandscapeImage" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><span class="requiredText">*</span>:</strong></td>
                        </tr>
                        <tr>
                            <td	valign="top">
                                <html:file property="appQVGALandscapeImage"/> <bean:message key="WapApplicationForm.label.appQVGALandscapeImage.filesize"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> <br/>
                                <logic:notEmpty	name="WapAppImageUploadForm"	property="appQVGALandscapeImageFileName" scope="request">
                                    <logic:equal name="WapAppImageUploadForm" property="appQVGALandscapeImageTempFileId"	scope="request"	value="0">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapQVGALandscapeImage&app_id=<bean:write	name="WapAppImageUploadForm"	property="appsId"	/>"	class="a"	target="_blank">
                                    </logic:equal>
                                    <logic:notEqual	name="WapAppImageUploadForm"	property="appQVGALandscapeImageTempFileId" scope="request" value="0">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="WapAppImageUploadForm"	property="appQVGALandscapeImageTempFileId" />" class="a" target="_blank">
                                    </logic:notEqual>
                                        <bean:write	name="WapAppImageUploadForm"	property="appQVGALandscapeImageFileName"/>
                                        </a>
                                </logic:notEmpty>
                            </td>
                        </tr>
                    </table>
                </td></tr>
                <tr><td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td colspan="2" height="25" align="right" valign="middle">
                                <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Save" title="Submit">
                                    <div>
                                        <div>
                                            <div onClick="document.forms[0].submit();">Submit</div>
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