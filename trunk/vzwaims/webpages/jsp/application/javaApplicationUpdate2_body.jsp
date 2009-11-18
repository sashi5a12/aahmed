<%@	page language="java"
	import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<%@ include  file="include/javaAppVariables.jsp" %>
<c:set value="${requestScope.javaApplicationUpdateForm}" var="form"></c:set>

<script language="javascript">

	var	contactsArray	=	new	Array();
	
	function AimsContacts()	{						
		this.contactId = "";
		this.firstName = "";
		this.lastName	=	"";
		this.title = "";
		this.emailAddress	=	"";
		this.phone = "";
		this.mobile	=	"";
	}

	<% int	index	=	0; %>

	<logic:iterate id="formContacts" name="javaApplicationUpdateForm"	property="allContacts" scope="request">
		aimsContacts = new AimsContacts();
		aimsContacts.contactId = "<bean:write	name="formContacts"	property="contactId" />";
		aimsContacts.firstName = "<bean:write	name="formContacts"	property="firstName" />";;
		aimsContacts.lastName	=	"<bean:write name="formContacts" property="lastName" />";
		aimsContacts.title = "<bean:write	name="formContacts"	property="title" />";
		aimsContacts.emailAddress	=	"<bean:write name="formContacts" property="emailAddress" />";
		aimsContacts.phone = "<bean:write	name="formContacts"	property="phone" />";
		aimsContacts.mobile	=	"<bean:write name="formContacts" property="mobile" />";
		contactsArray[<%=index%>]	=	aimsContacts;
		<%index++;%>
	</logic:iterate>


	var	supported	=	(window.Option)	?	1	:	0;


	function displayContactsInformation()	{
		 if	(!supported) {
			 alert("Feature	not	supported");
		 }
		 if ((document.forms[0].aimsContactId.options[document.forms[0].aimsContactId.selectedIndex].value) !=	"0") {		 	
			for	(var j = 0;	j	<	contactsArray.length;	j++) {
				if (contactsArray[j].contactId ==	document.forms[0].aimsContactId.options[document.forms[0].aimsContactId.selectedIndex].value) {
					document.forms[0].contactFirstName.value = contactsArray[j].firstName;
					document.forms[0].contactLastName.value	=	contactsArray[j].lastName;
					document.forms[0].contactTitle.value = contactsArray[j].title;
					document.forms[0].contactEmail.value = contactsArray[j].emailAddress;
					document.forms[0].contactPhone.value = contactsArray[j].phone;
					document.forms[0].contactMobile.value	=	contactsArray[j].mobile;
					document.forms[0].contactFirstName.disabled	=	true;
					document.forms[0].contactLastName.disabled = true;
					document.forms[0].contactTitle.disabled	=	true;
					document.forms[0].contactEmail.disabled	=	true;
					document.forms[0].contactPhone.disabled	=	true;
					document.forms[0].contactMobile.disabled = true;
				}
			}
		}
		else {
			document.forms[0].contactFirstName.disabled	=	false;
			document.forms[0].contactLastName.disabled = false;
			document.forms[0].contactTitle.disabled	=	false;
			document.forms[0].contactEmail.disabled	=	false;
			document.forms[0].contactPhone.disabled	=	false;
			document.forms[0].contactMobile.disabled = false;

			document.forms[0].contactFirstName.value = "<bean:write	name="javaApplicationUpdateForm" property="contactFirstName" scope="request"/>";
			document.forms[0].contactLastName.value	=	"<bean:write name="javaApplicationUpdateForm"	property="contactLastName" scope="request"/>";
			document.forms[0].contactTitle.value = "<bean:write	name="javaApplicationUpdateForm" property="contactTitle" scope="request"/>";
			document.forms[0].contactEmail.value = "<bean:write	name="javaApplicationUpdateForm" property="contactEmail" scope="request"/>";
			document.forms[0].contactPhone.value = "<bean:write	name="javaApplicationUpdateForm" property="contactPhone" scope="request"/>";
			document.forms[0].contactMobile.value	=	"<bean:write name="javaApplicationUpdateForm"	property="contactMobile" scope="request"/>";
		}

	}

	function truncateLocalTextAreas() {
        if (typeof document.forms[0].channelDeployments != "undefined")
            if (typeof document.forms[0].channelDeployments.value != "undefined")
			 TruncateText(document.forms[0].channelDeployments,1000);
             
        if (typeof document.forms[0].sellingPoints != "undefined")
            if (typeof document.forms[0].sellingPoints.value != "undefined")
			 TruncateText(document.forms[0].sellingPoints,2000);
	}

	<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
		function disableForVzw() {			           
			document.forms[0].channelDeployments.disabled	=	true;
			document.forms[0].aimsContactId.disabled	=	true;
			document.forms[0].contactFirstName.disabled	=	true;
			document.forms[0].contactLastName.disabled = true;
			document.forms[0].contactTitle.disabled	=	true;
			document.forms[0].contactEmail.disabled	=	true;
			document.forms[0].contactPhone.disabled	=	true;
			document.forms[0].contactMobile.disabled = true;
		}
	<% } else {}%>
	<%@ include  file="include/javaJScript.jsp" %>
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%">
			<%@ include file="include/javaMessageHeader.jsp" %>
		</td>
	</tr>
	<tr>
		<td width="100%">
			<aims:getFileSizeMessage
				message="<%=(String) session.getAttribute(AimsConstants.FILE_MSG)%>"
				imgSrc="images/sign.gif" userType="<%=AimsConstants.VZW_USERTYPE%>" />
			&nbsp;
		</td>
	</tr>
</table>

<div id="contentBox" style="float: left" onmousemove="truncateLocalTextAreas();">
	<div class="homeColumnTab">
		<%@ include file="include/javaTabs.jsp"%>
		<div class="contentbox">
			<html:form action="/javaApplicationUpdate" enctype="multipart/form-data">
				<html:hidden property="currentPage" value="page2"/>
				<%@ include file="include/javaAppHidden.jsp"%>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr width="100%">
						<td width="50%" valign="top" style="padding-right:5px;" >
							<table width="395" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="noPad">
										<div class="mmBox">
											<DIV class="headLeftCurveblk"></DIV>
											<H1>Channel Files</H1>
											<DIV class="headRightCurveblk"></DIV>
										</div>
									</td>
								</tr>
								<tr>
									<th>
										<strong>Minimum 300 dpi resolution is required for all EPS files.</strong>
									</th>
								</tr>
								<tr>
									<td>
										<strong>High Resolution Publisher Logo (JPEG/GIF/EPS):</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="clrPubLogo" />
										<br />										
										<logic:notEmpty name="javaApplicationUpdateForm" property="clrPubLogoFileName" scope="request">
											<logic:equal name="javaApplicationUpdateForm" property="clrPubLogoTempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashClrPubLogo&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="javaApplicationUpdateForm" property="clrPubLogoTempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="clrPubLogoTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="javaApplicationUpdateForm" property="clrPubLogoFileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<strong>Channel Title Graphic/Icon (JPEG/GIF/EPS):</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="appTitleName" />
										<br />
										<logic:notEmpty name="javaApplicationUpdateForm" property="appTitleNameFileName" scope="request">
											<logic:equal name="javaApplicationUpdateForm" property="appTitleNameTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashAppTitleName&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="javaApplicationUpdateForm" property="appTitleNameTempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appTitleNameTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="javaApplicationUpdateForm" property="appTitleNameFileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td> 
										<strong>Splash Screen Shot (JPEG/GIF/EPS)&nbsp;<span class="requiredText"><logic:match value="true" name="javaApplicationUpdateForm" property="ring2App">*</logic:match></span>:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="splashScreenEps" />
										<br />
										<logic:notEmpty name="javaApplicationUpdateForm" property="splashScreenEpsFileName" scope="request">
											<logic:equal name="javaApplicationUpdateForm" property="splashScreenEpsTempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SplashScreenEps&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="javaApplicationUpdateForm" property="splashScreenEpsTempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="splashScreenEpsTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="javaApplicationUpdateForm" property="splashScreenEpsFileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<strong>Active Screen Shot (JPEG/GIF/EPS):</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="activeScreenEps" />
										<br />
										<logic:notEmpty name="javaApplicationUpdateForm" property="activeScreenEpsFileName" scope="request">
											<logic:equal name="javaApplicationUpdateForm" property="activeScreenEpsTempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ActiveScreenEps&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="javaApplicationUpdateForm" property="activeScreenEpsTempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="activeScreenEpsTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="javaApplicationUpdateForm" property="activeScreenEpsFileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appScreenShot" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span class="requiredText"><logic:equal value="true" name="javaApplicationUpdateForm" property="ring2App">*</logic:equal></span>:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="screenJpeg" />
										<br />
										<logic:notEmpty name="javaApplicationUpdateForm" property="screenJpegFileName" scope="request">
											<logic:equal name="javaApplicationUpdateForm" property="screenJpegTempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="javaApplicationUpdateForm" property="screenJpegTempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="screenJpegTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<bean:write name="javaApplicationUpdateForm" property="screenJpegFileName" />
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="screenJpeg2" />
										<br />
										<logic:notEmpty name="javaApplicationUpdateForm" property="screenJpeg2FileName" scope="request">
											<logic:equal name="javaApplicationUpdateForm" property="screenJpeg2TempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="javaApplicationUpdateForm" property="screenJpeg2TempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg2TempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<bean:write name="javaApplicationUpdateForm" property="screenJpeg2FileName" />
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="screenJpeg3" />
										<br />
										<logic:notEmpty name="javaApplicationUpdateForm" property="screenJpeg3FileName" scope="request">
											<logic:equal name="javaApplicationUpdateForm" property="screenJpeg3TempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="javaApplicationUpdateForm" property="screenJpeg3TempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg3TempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<bean:write name="javaApplicationUpdateForm" property="screenJpeg3FileName" />
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="screenJpeg4" />
										<br />
										<logic:notEmpty name="javaApplicationUpdateForm" property="screenJpeg4FileName" scope="request">
											<logic:equal name="javaApplicationUpdateForm" property="screenJpeg4TempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="javaApplicationUpdateForm" property="screenJpeg4TempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg4TempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<bean:write name="javaApplicationUpdateForm" property="screenJpeg4FileName" />
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="screenJpeg5" />
										<br />
										<logic:notEmpty name="javaApplicationUpdateForm" property="screenJpeg5FileName" scope="request">
											<logic:equal name="javaApplicationUpdateForm" property="screenJpeg5TempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="javaApplicationUpdateForm" property="screenJpeg5TempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="screenJpeg5TempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="javaApplicationUpdateForm" property="screenJpeg5FileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appFAQ" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
									</td>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="faqDoc" />
										<br />
										<logic:notEmpty name="javaApplicationUpdateForm" property="faqDocFileName" scope="request">
											<logic:equal name="javaApplicationUpdateForm" property="faqDocTempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FaqDoc&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="javaApplicationUpdateForm" property="faqDocTempFileId" scope="request" value="0">
												<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="javaApplicationUpdateForm"	property="faqDocTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="javaApplicationUpdateForm" property="faqDocFileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>
							</table>
						</td>
						<td width="50%" valign="top" style="padding-left:5px;" >							
							<table width="395" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="noPad">
										<div class="mmBox">
											<DIV class="headLeftCurveblk"></DIV>
											<H1>24x7 Technical Contact &nbsp;<span class="requiredText"><logic:equal value="true" name="javaApplicationUpdateForm" property="ring3App">*</logic:equal></span></H1>
											<DIV class="headRightCurveblk"></DIV>
										</div>
									</td>
								</tr>
								<tr>
									<th>
										<html:select styleClass="selectField" property="aimsContactId" size="1" onchange="displayContactsInformation()" style="width:250px">
											<html:option value="0">
												<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
											</html:option>
											<logic:iterate id="formContacts" name="javaApplicationUpdateForm" property="allContacts" type="com.netpace.aims.model.core.AimsContact" scope="request">
												<html:option value="<%=formContacts.getContactId().toString()%>">
								                	<bean:write	name="formContacts"	property="firstName" />
								                	<bean:write	name="formContacts"	property="lastName" /> 
								                </html:option>
											</logic:iterate>
										</html:select>
									</th>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.label.orEnter" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></strong>
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appContactFirstName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:text styleClass="inputField" property="contactFirstName" size="35" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appContactLastName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:text styleClass="inputField" property="contactLastName" size="35" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appContactTitle" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:text styleClass="inputField" property="contactTitle" size="35" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appContactEmail" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:text styleClass="inputField" property="contactEmail" size="35" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appContactTelephone" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:text styleClass="inputField" property="contactPhone" size="35" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appContactMobile" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:text styleClass="inputField" property="contactMobile" size="35" maxlength="50" />
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<%  if(isRFI && !isVerizonUser) { %>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>
									Comments
								</H1>

								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<table width="100%" border="0" cellspacing="0" cellpadding="5"
								class="GridGradient">
								<tr>
									<td width=50%>
										<html:textarea property="comments" rows="4" cols="50" styleClass="textareaField" onkeypress="LimitText(this,500)"></html:textarea>
										<br />
										<span id="airTime" style="display: none"><c:out value="${requestScope.AirTimeText}" escapeXml="false"></c:out>
										</span>										
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<% } %>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td width="100%" colspan="2">
							<table width="100%" cellpadding="0" cellspacing="0" border="0">
								<%@ include file="include/javaAppEditButtons.jsp"%>
								<tr><td><%@ include file="include/javaMessageFooter.jsp" %></td> </tr>
									<script language="javascript">
										displayContactsInformation();
									</script>
							</table>
						</td>
					</tr>
				</table>
				
				<script>
						<%
							if ( isLocked ) 
							{
						%>		
								function lockApplicationPage2() {			
									var frm = document.forms[0];
				                        frm.clrPubLogo.disabled = true;
				                        frm.appTitleName.disabled = true;
				                        frm.splashScreenEps.disabled = true;
				                        frm.splashScreenEps.disabled = true;
				                        frm.activeScreenEps.disabled = true;
				                        frm.screenJpeg.disabled = true;
				                        frm.screenJpeg2.disabled = true;
				                        frm.screenJpeg3.disabled = true;
				                        frm.screenJpeg4.disabled = true;
				                        frm.screenJpeg5.disabled = true;
				                        frm.faqDoc.disabled = true;
				                        
				                        frm.aimsContactId.disabled = true;
				                        frm.contactFirstName.disabled = true;
				                        frm.contactLastName.disabled = true;
				                        frm.contactTitle.disabled = true;
				                        frm.contactEmail.disabled = true;
				                        frm.contactPhone.disabled = true;
				                        frm.contactMobile.disabled = true;				                        
								}//end lockApplicationPage1
								lockApplicationPage2();
						<% 
							}
						%>				
				</script>
				
				<% 
					if (isLocked) 
					{
				%>
						<html:hidden property="aimsContactId"/>    
						<html:hidden property="contactFirstName"/>
						<html:hidden property="contactLastName"/>
						<html:hidden property="contactTitle"/>
						<html:hidden property="contactEmail"/>
						<html:hidden property="contactPhone"/>
						<html:hidden property="contactMobile"/>					
				<%
					}
				%>
				
			</html:form>
		</div>
	</div>
</div>
