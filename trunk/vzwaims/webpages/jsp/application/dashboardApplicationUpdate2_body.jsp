<%@	page language="java"
	import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<%@ include  file="include/dashboardAppVariables.jsp" %>
<c:set value="${requestScope.DashboardApplicationUpdateForm}" var="form"></c:set>

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

	<logic:iterate id="formContacts" name="DashboardApplicationUpdateForm"	property="allContacts" scope="request">
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

			document.forms[0].contactFirstName.value = "<bean:write	name="DashboardApplicationUpdateForm" property="contactFirstName" scope="request"/>";
			document.forms[0].contactLastName.value	=	"<bean:write name="DashboardApplicationUpdateForm"	property="contactLastName" scope="request"/>";
			document.forms[0].contactTitle.value = "<bean:write	name="DashboardApplicationUpdateForm" property="contactTitle" scope="request"/>";
			document.forms[0].contactEmail.value = "<bean:write	name="DashboardApplicationUpdateForm" property="contactEmail" scope="request"/>";
			document.forms[0].contactPhone.value = "<bean:write	name="DashboardApplicationUpdateForm" property="contactPhone" scope="request"/>";
			document.forms[0].contactMobile.value	=	"<bean:write name="DashboardApplicationUpdateForm"	property="contactMobile" scope="request"/>";
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

	<%@ include  file="include/dashboardJScript.jsp" %>

</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%">
			<%@ include file="include/dashboardMessageHeader.jsp" %>
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
		<%@ include file="include/dashboardTabs.jsp"%>
		<div class="contentbox">
			<html:form action="/dashboardApplicationUpdate" enctype="multipart/form-data">
				<html:hidden property="currentPage" value="page2"/>
				<%@ include file="include/dashboardAppHidden.jsp"%>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr width="100%">
						<td width="50%" valign="top">
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
										<strong>High Resolution Publisher Logo (EPS):</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="clrPubLogo" />
										<br />
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="clrPubLogoFileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="clrPubLogoTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashClrPubLogo&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="clrPubLogoTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="clrPubLogoTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="DashboardApplicationUpdateForm" property="clrPubLogoFileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<strong>Channel Title Graphic/Icon (EPS):</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="appTitleName" />
										<br />
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="appTitleNameFileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="appTitleNameTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashAppTitleName&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="appTitleNameTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appTitleNameTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="DashboardApplicationUpdateForm" property="appTitleNameFileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appSplashScreen" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span class="requiredText">*</span>:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="splashScreenEps" />
										<br />
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="splashScreenEpsFileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="splashScreenEpsTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SplashScreenEps&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="splashScreenEpsTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="splashScreenEpsTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="DashboardApplicationUpdateForm" property="splashScreenEpsFileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appActiveScreen" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="activeScreenEps" />
										<br />
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="activeScreenEpsFileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="activeScreenEpsTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ActiveScreenEps&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="activeScreenEpsTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="activeScreenEpsTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="DashboardApplicationUpdateForm" property="activeScreenEpsFileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appScreenShot" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span class="requiredText">*</span>:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="screenJpeg" />
										<br />
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpegFileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="screenJpegTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="screenJpegTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpegTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<bean:write name="DashboardApplicationUpdateForm" property="screenJpegFileName" />
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="screenJpeg2" />
										<br />
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg2FileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="screenJpeg2TempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="screenJpeg2TempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg2TempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<bean:write name="DashboardApplicationUpdateForm" property="screenJpeg2FileName" />
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="screenJpeg3" />
										<br />
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg3FileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="screenJpeg3TempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="screenJpeg3TempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg3TempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<bean:write name="DashboardApplicationUpdateForm" property="screenJpeg3FileName" />
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="screenJpeg4" />
										<br />
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg4FileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="screenJpeg4TempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="screenJpeg4TempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg4TempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<bean:write name="DashboardApplicationUpdateForm" property="screenJpeg4FileName" />
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="screenJpeg5" />
										<br />
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg5FileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="screenJpeg5TempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="screenJpeg5TempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="screenJpeg5TempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="DashboardApplicationUpdateForm" property="screenJpeg5FileName" /></div>
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
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="faqDocFileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="faqDocTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FaqDoc&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="faqDocTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="faqDocTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="DashboardApplicationUpdateForm" property="faqDocFileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>
								<tr>
									<td>
										<strong><bean:message key="ManageApplicationForm.appUserGuide" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
									</td>
								</tr>								
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="userGuide" />
										<br />
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="userGuideFileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="userGuideTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=UserGuide&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="userGuideTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="userGuideTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<div style="padding-bottom: 5px"><bean:write name="DashboardApplicationUpdateForm" property="userGuideFileName" /></div>
											</a>
										</logic:notEmpty>
									</td>
								</tr>															
							</table>
						</td>
						<td width="50%" valign=top>							
							<table width="395" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="noPad">
										<div class="mmBox">
											<DIV class="headLeftCurveblk"></DIV>
											<H1>24x7 Technical Contact &nbsp;<span class="requiredText">*</span></H1>
											<DIV class="headRightCurveblk"></DIV>
										</div>
									</td>
								</tr>
								<tr>
									<th>
										<html:select styleClass="selectField" property="aimsContactId" size="1" onchange="displayContactsInformation()">
											<html:option value="0">
												<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
											</html:option>
											<logic:iterate id="formContacts" name="DashboardApplicationUpdateForm" property="allContacts" type="com.netpace.aims.model.core.AimsContact" scope="request">
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
							<table width="395" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td class="noPad">
										<div class="mmBox">
											<DIV class="headLeftCurveblk"></DIV>
											<H1>Other Details</H1>
											<DIV class="headRightCurveblk"></DIV>
										</div>
									</td>
								</tr>
								<tr>
									<th>
										<strong>Developer Name:</strong>
									</th>
								</tr>
								<tr>
									<td>
										<html:text styleClass="inputField" property="developerName" size="35" maxlength="150" />
									</td>
								</tr>
								<tr>
									<td>
										<strong>Publisher Name:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:text styleClass="inputField" property="publisherName" size="35" maxlength="150" />
									</td>
								</tr>
								<tr>
									<td>
										<strong>Selling Points&nbsp;<span class="requiredText">*</span>: (Max: 2000 chars)</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:textarea styleClass="textareaField" property="sellingPoints" onkeyup="LimitText(this,2000)" onkeypress="LimitText(this,2000)" rows="4" cols="50"></html:textarea>
									</td>
								</tr>
								<tr>
									<td>
										<strong>Additional Comments: (Max: 1000 chars)</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:textarea styleClass="textareaField" property="channelDeployments" onkeyup="LimitText(this,1000)" onkeypress="LimitText(this,1000)" rows="4" cols="50"></html:textarea>
									</td>
								</tr>																
								<tr>
									<td>
										<strong>Planned Development Start Date:</strong>
									</td>
								</tr>
								<tr>
									<td>
										<html:text styleClass="inputField" property="plannedDevStartDate" size="15" maxlength="10" />
										<img name="daysOfMonth1Pos" onclick="toggleDatePicker('daysOfMonth1','DashboardApplicationUpdateForm.plannedDevStartDate')" id="daysOfMonth1Pos" <bean:message key="images.calendar.button.lite"/> />
										<div style="position: absolute;" id="daysOfMonth1" />
									</td>
								</tr>								
							</table>
						</td>
					<tr>
						<td width="100%" colspan="2">
							<table width="100%" cellpadding="0" cellspacing="0" border="0">
								<%@ include file="include/dashboardAppEditButtons.jsp"%>
								<tr><td><%@ include file="include/dashboardMessageFooter.jsp" %></td> </tr>
									<script language="javascript">
										displayContactsInformation();
									</script>
							</table>
						</td>
					</tr>
				</table>
			</html:form>
		</div>
	</div>
</div>
