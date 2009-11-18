<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<jsp:useBean id="task" class="java.lang.String"	scope="request"/><jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" /><% boolean isConcept = false; %>
<% ApplicationUpdateForm.setCurrentPage("page2"); %>
<logic:equal name="BrewApplicationUpdateForm" property="isConcept" scope="request"   value="Y">
  <% isConcept = true; %>
</logic:equal>
<script	language="javascript">

	var	contactsArray	=	new	Array();
	var helpKeyword = "<bean:message key="BrewApplicationForm.help.keyword" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>";

	function AimsContacts()
	{
		this.contactId = "";
		this.firstName = "";
		this.lastName	=	"";
		this.title = "";
		this.emailAddress	=	"";
		this.phone = "";
		this.mobile	=	"";
	}

	<%
	int	index	=	0;
	%>

	<logic:iterate id="formContacts" name="BrewApplicationUpdateForm"	property="allContacts" scope="request">
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

		if ((document.forms[0].aimsContactId.options[document.forms[0].aimsContactId.selectedIndex].value) !=	"0")
		{
			for	(var j = 0;	j	<	contactsArray.length;	j++)
			{
				if (contactsArray[j].contactId ==	document.forms[0].aimsContactId.options[document.forms[0].aimsContactId.selectedIndex].value)
				{
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
		else
		{
			document.forms[0].contactFirstName.disabled	=	false;
			document.forms[0].contactLastName.disabled = false;
			document.forms[0].contactTitle.disabled	=	false;
			document.forms[0].contactEmail.disabled	=	false;
			document.forms[0].contactPhone.disabled	=	false;
			document.forms[0].contactMobile.disabled = false;

			document.forms[0].contactFirstName.value = "<bean:write	name="BrewApplicationUpdateForm" property="contactFirstName" scope="request"/>";
			document.forms[0].contactLastName.value	=	"<bean:write name="BrewApplicationUpdateForm"	property="contactLastName" scope="request"/>";
			document.forms[0].contactTitle.value = "<bean:write	name="BrewApplicationUpdateForm" property="contactTitle" scope="request"/>";
			document.forms[0].contactEmail.value = "<bean:write	name="BrewApplicationUpdateForm" property="contactEmail" scope="request"/>";
			document.forms[0].contactPhone.value = "<bean:write	name="BrewApplicationUpdateForm" property="contactPhone" scope="request"/>";
			document.forms[0].contactMobile.value	=	"<bean:write name="BrewApplicationUpdateForm"	property="contactMobile" scope="request"/>";
		}

	}

	function truncateLocalTextAreas()
	{
        if (typeof document.forms[0].appDeployments != "undefined")
            if (typeof document.forms[0].appDeployments.value != "undefined")
			 TruncateText(document.forms[0].appDeployments,1000);
             
        if (typeof document.forms[0].sellingPoints != "undefined")
            if (typeof document.forms[0].sellingPoints.value != "undefined")
			 TruncateText(document.forms[0].sellingPoints,2000);
	}

	<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
		function disableForVzw()
		{
			/*
            document.forms[0].splashScreenEps.disabled = true;
			document.forms[0].activeScreenEps.disabled = true;
			document.forms[0].screenJpeg.disabled = true;
			document.forms[0].screenJpeg2.disabled = true;
			document.forms[0].screenJpeg3.disabled = true;
			document.forms[0].screenJpeg4.disabled = true;
			document.forms[0].screenJpeg5.disabled = true;
			document.forms[0].faqDoc.disabled = true;
			document.forms[0].userGuide.disabled = true;
			document.forms[0].flashDemo.disabled = true;
			document.forms[0].appTitleName.disabled = true;
            */
            
			document.forms[0].appDeployments.disabled = true;

			document.forms[0].aimsContactId.disabled	=	true;
			document.forms[0].contactFirstName.disabled	=	true;
			document.forms[0].contactLastName.disabled = true;
			document.forms[0].contactTitle.disabled	=	true;
			document.forms[0].contactEmail.disabled	=	true;
			document.forms[0].contactPhone.disabled	=	true;
			document.forms[0].contactMobile.disabled = true;

			document.forms[0].developerName.disabled = true;
			document.forms[0].publisherName.disabled = true;
			document.forms[0].sellingPoints.disabled = true;
			document.forms[0].plannedDevStartDate.disabled = true;
			document.forms[0].plannedEntryIntoNstl.disabled = true;
			document.forms[0].plannedCompletionByNstl.disabled = true;
			
			document.forms[0].daysOfMonth1Pos.disabled = true;
			document.forms[0].daysOfMonth2Pos.disabled = true;
			document.forms[0].daysOfMonth3Pos.disabled = true;

			for (var i=0; i<document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == "prizes") {
                document.forms[0].elements[i].disabled = true;
            }
		    if (document.forms[0].elements[i].name == "networkUse") {
		      document.forms[0].elements[i].disabled = true;
		    }
		    if (document.forms[0].elements[i].name == "singleMultiPlayer") {
		      document.forms[0].elements[i].disabled = true;
		    }
		  }

		}
	<% } else {}%>

	<%@ include  file="include/appJScript.jsp" %>

</script>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td width="100%">
	  <%@ include file="include/brewMessageHeader.jsp" %>
	</td></tr>
</table>
<div id="contentBox" style="float:left" onmousemove="truncateLocalTextAreas();">
  <div class="homeColumnTab">
	<%@ include  file="include/appTabs.jsp" %>
    <div class="contentbox">
      <html:form action="/brewApplicationUpdate.do"	enctype="multipart/form-data">
	  	<%@ include  file="include/appHidden.jsp" %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr width="100%">
            <td width="50%" valign="top">
				<table width="395" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message key="ApplicationForm.table.head.app.files" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th>
						<strong><bean:message key="BrewApplicationForm.info.label.eps.dpi.format" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>
					</th>
				</tr>
                <tr>
                  <td>				  	
				  	<strong><bean:message key="BrewApplicationForm.appAppTitleName"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
				  </td>
				</tr>  
				<tr>
					<td> 					
						<html:file size="30" styleClass="inputField" property="appTitleName"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="appTitleNameFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="appTitleNameTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=AppTitleName&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="appTitleNameTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appTitleNameTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="appTitleNameFileName"/>
								</a>
						</logic:notEmpty>
					</td>				  
                </tr> 
				<tr>
                  <td>
				  	<strong><bean:message	key="ManageApplicationForm.appScreenShot"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
				  </td>
				</tr>  
				<tr>
					<td> 					
					<html:file size="30" styleClass="inputField" property="screenJpeg"/><br/>
					<logic:notEmpty	name="BrewApplicationUpdateForm"	property="screenJpegFileName" scope="request">
						<logic:equal name="BrewApplicationUpdateForm" property="screenJpegTempFileId"	scope="request"	value="0">
							<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
						</logic:equal>
						<logic:notEqual	name="BrewApplicationUpdateForm"	property="screenJpegTempFileId" scope="request" value="0">
							<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="screenJpegTempFileId" />" class="a" target="_blank">
						</logic:notEqual>
							<bean:write	name="BrewApplicationUpdateForm"	property="screenJpegFileName"/>
							</a>
					</logic:notEmpty>					
                  </td>
                </tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="screenJpeg2"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="screenJpeg2FileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="screenJpeg2TempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="screenJpeg2TempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg2TempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg2FileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField"  property="screenJpeg3"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="screenJpeg3FileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="screenJpeg3TempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="screenJpeg3TempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg3TempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg3FileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="screenJpeg4"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="screenJpeg4FileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="screenJpeg4TempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="screenJpeg4TempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg4TempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg4FileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="screenJpeg5"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="screenJpeg5FileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="screenJpeg5TempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="screenJpeg5TempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg5TempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="screenJpeg5FileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appFAQ"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
				  </td>
				<tr>
					<td>					
						<html:file size="30" styleClass="inputField" property="faqDoc"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="faqDocFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="faqDocTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FaqDoc&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="faqDocTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="faqDocTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="faqDocFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>								
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appFlashDemo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> (SWF):</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="flashDemo"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="flashDemoFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="flashDemoTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FlashDemo&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="flashDemoTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="flashDemoTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="flashDemoFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>High Resolution Splash Screen Image (EPS)&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="highResSplash"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="highResSplashFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="highResSplashTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=HighResSplash&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="highResSplashTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="highResSplashTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="highResSplashFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				
				<tr>
					<td><strong>High Resolution Active Screen Image (EPS)&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="highResActive"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="highResActiveFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="highResActiveTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=HighResActive&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="highResActiveTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="highResActiveTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="highResActiveFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Splash Screen Image (Title screen w/name in image)(JPEG/PNG & 150x200)&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="splashScreen"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="splashScreenFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="splashScreenTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SplashScreen&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="splashScreenTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="splashScreenTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="splashScreenFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Small Splash Screen Image (JPG & 75x100)&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="smallSplash"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="smallSplashFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="smallSplashTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SmallSplash&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="smallSplashTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="smallSplashTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="smallSplashFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Active Screen Image 1 (In game shot)(JPEG/PNG & 150x200)&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="activeScreen1"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="activeScreen1FileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="activeScreen1TempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ActiveScreen1&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="activeScreen1TempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="activeScreen1TempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="activeScreen1FileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Small Active Screen Image 1 (JPG & 75x100)&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="smlActiveScreen"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="smlActiveScreenFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="smlActiveScreenTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SmlActiveScreen&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="smlActiveScreenTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="smlActiveScreenTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="smlActiveScreenFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Active Screen Image 2 (In game shot) (JPEG/PNG & 150x200)&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="activeScreen2"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="activeScreen2FileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="activeScreen2TempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ActiveScreen2&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="activeScreen2TempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="activeScreen2TempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="activeScreen2FileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Flash Animation of Application in Action (SWF):</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="appActiionFlash"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="appActiionFlashFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="appActiionFlashTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=AppActiionFlash&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="appActiionFlashTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appActiionFlashTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="appActiionFlashFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Animated .gif of Application in Action (GIF):</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="appGifAction"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="appGifActionFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="appGifActionTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=AppGifAction&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="appGifActionTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appGifActionTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="appGifActionFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Media Store File (JPG & 80x80)&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="mediaStore"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="mediaStoreFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="mediaStoreTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=MediaStore&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="mediaStoreTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="mediaStoreTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="mediaStoreFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Flash Demo Movie 1 File (Supported on Media Storefront)(SWF):</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="flashDemoMovie"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="flashDemoMovieFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="flashDemoMovieTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FlashDemoMovie&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="flashDemoMovieTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="flashDemoMovieTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="flashDemoMovieFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Dashboard Screen Image 1 (Graphic Asset) (JPEG & 228x179)&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="dashboardScrImg"/><br/>
						<logic:notEmpty	name="BrewApplicationUpdateForm"	property="dashboardScrImgFileName" scope="request">
							<logic:equal name="BrewApplicationUpdateForm" property="dashboardScrImgTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashboardScrImg&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="BrewApplicationUpdateForm"	property="dashboardScrImgTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="dashboardScrImgTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="BrewApplicationUpdateForm"	property="dashboardScrImgFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1>Messaging Details</H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
                <tr>
                  <th>
                  	<strong>Publisher Short Code (Enter 2777 (APPS), if you don't have one)&nbsp;<span class="requiredText">*</span>:</strong>
                  	<br/>
                  	<html:text styleClass="inputField" property="shortCode" size="35" maxlength="50"	/>
                  </th>
                </tr>
                <tr>
                  <td>
                  	<strong>Short Code SMS Keywords (Enter Desired Keyword to be used)&nbsp;<span class="requiredText">*</span>:&nbsp;<a onClick="openZonHelpWindow(event, helpKeyword); return false;" href="#">[?]</a></strong>
                  	<br/>
                  	<html:text styleClass="inputField" property="keyword" size="35" maxlength="100"	/>
                  </td>
                </tr>                
                <tr>
                  <td>
                  	<strong>Short Code Aggregator&nbsp;<span class="requiredText">*</span>:</strong>
                  	<br/>
                  	<html:select styleClass="selectField" property="aggregator"	size="1">
						<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>						
						<html:optionsCollection name="BrewApplicationUpdateForm" property="allAggregators" label="typeValue" value="typeId"/>
					</html:select>
                  </td>
                </tr>  				
				<tr><td>&nbsp;</td></tr>
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message	key="BrewApplicationForm.table.head.deployments"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
                <tr>
                  <th><strong><bean:message	key="ManageApplicationForm.appDeployments"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>: (Max: 1000 chars)</strong></th>
                </tr>
                <tr>
                  <td><html:textarea styleClass="textareaField" property="appDeployments" onkeyup="LimitText(this,1000)" onkeypress="LimitText(this,1000)" rows="4" cols="50"></html:textarea></td>
                </tr>
                              
              </table></td>
            <td width="50%" valign=top>            
			  <table width="395" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message	key="BrewApplicationForm.table.head.24x7contact"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
                <tr>
					<th>
						<html:select styleClass="selectField" property="aimsContactId"	size="1" onchange="displayContactsInformation()">
						<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
						<logic:iterate id="formContacts" name="BrewApplicationUpdateForm"	property="allContacts" type="com.netpace.aims.model.core.AimsContact"	scope="request">
							<html:option value="<%=formContacts.getContactId().toString()%>">
			                	<bean:write	name="formContacts"	property="firstName" />
			                	<bean:write	name="formContacts"	property="lastName" /> 
			                </html:option>
						</logic:iterate>
						</html:select>
					</th>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.label.orEnter"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appContactFirstName"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactFirstName" size="35" maxlength="50"	/></td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appContactLastName"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactLastName"	size="35" maxlength="50"	 /></td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appContactTitle"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactTitle"	size="35" maxlength="50"	 /></td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appContactEmail"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactEmail"	size="35" maxlength="50"	 /></td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appContactTelephone"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactPhone"	size="35" maxlength="50"	 /></td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appContactMobile"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactMobile"	size="35" maxlength="50"	 /></td>
				</tr>
				<tr><td>&nbsp;</td></tr>
              </table>
			  <table width="395" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message	key="BrewApplicationForm.table.head.other.details"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
           		<tr>
					<th><strong><bean:message	key="BrewApplicationForm.developerName"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
				</tr>
				<tr>
					<td>
						<html:text styleClass="inputField" property="developerName"	size="35" maxlength="150"	 />
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="BrewApplicationForm.publisherName"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:text styleClass="inputField" property="publisherName"	size="35" maxlength="150"	 />
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="BrewApplicationForm.sellingPoints"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>: (Max: 2000 chars)</strong></td>
				</tr>
				<tr>
					<td>
						<html:textarea styleClass="textareaField" property="sellingPoints" onkeyup="LimitText(this,2000)" onkeypress="LimitText(this,2000)" rows="3" cols="30"></html:textarea>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="BrewApplicationForm.plannedDevStartDate"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
					<html:text styleClass="inputField" property="plannedDevStartDate"	size="15"/><img name="daysOfMonth1Pos" onclick="toggleDatePicker('daysOfMonth1','BrewApplicationUpdateForm.plannedDevStartDate')" id="daysOfMonth1Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth1"/></td>
				</tr>
				<tr>
					<td><strong><bean:message	key="BrewApplicationForm.plannedEntryIntoNstl"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:text styleClass="inputField" property="plannedEntryIntoNstl"	size="15"/><img name="daysOfMonth2Pos" onclick="toggleDatePicker('daysOfMonth2','BrewApplicationUpdateForm.plannedEntryIntoNstl')" id="daysOfMonth2Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth2"/></td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ApplicationForm.brewPlannedCompletionByNstl"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:text styleClass="inputField" property="plannedCompletionByNstl"	size="15"/><img name="daysOfMonth3Pos" onclick="toggleDatePicker('daysOfMonth3','BrewApplicationUpdateForm.plannedCompletionByNstl')" id="daysOfMonth3Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth3"/></td>
				</tr>
				<tr>
					<td><strong><bean:message	key="BrewApplicationForm.networkUse"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td style="padding: 0px">
						<table cellpadding="0" cellspacing="0" border="0" >
							<tr>
								<td><html:radio	property="networkUse" value="S"/><bean:message key="ManageApplicationForm.radio.label.againstServer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
								<td><html:radio	property="networkUse" value="P"/><bean:message key="ManageApplicationForm.radio.label.againstPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
							</tr>
							<tr>
								<td colspan="2"><html:radio	property="networkUse" value="N"/><bean:message key="ManageApplicationForm.radio.label.noNetworkUsage"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>														
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="BrewApplicationForm.singleMultiPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td style="padding: 0px">
						<table>
							<tr>
								<td width="50%"><html:radio	property="singleMultiPlayer" value="S"/><bean:message key="ManageApplicationForm.radio.label.singlePlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
								<td width="50%"><html:radio	property="singleMultiPlayer" value="M"/><bean:message key="ManageApplicationForm.radio.label.multiPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
							</tr>
							<tr>
								<td><html:radio	property="singleMultiPlayer" value="B"/><bean:message key="ManageApplicationForm.radio.label.bothPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
								<td><html:radio	property="singleMultiPlayer" value="N"/><bean:message key="ManageApplicationForm.radio.label.noPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message   key="BrewApplicationForm.prizes"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:radio property="prizes" value="Y"/><bean:message key="ManageApplicationForm.radio.label.yes"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
						<html:radio property="prizes" value="N"/><bean:message key="ManageApplicationForm.radio.label.no"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
					</td>
				</tr>
				<tr>
					<td><strong>UGC/Chat &nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:radio property="ugcChat" value="Y"/><bean:message key="ManageApplicationForm.radio.label.yes"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
						<html:radio property="ugcChat" value="N"/><bean:message key="ManageApplicationForm.radio.label.no"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
					</td>
				</tr>
				<tr>
					<td><strong>Chat/Prize:</td>
				</tr>
				<tr>
					<td>
						<html:radio property="chatPrize" value="Y"/><bean:message key="ManageApplicationForm.radio.label.yes"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
						<html:radio property="chatPrize" value="N"/><bean:message key="ManageApplicationForm.radio.label.no"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
					</td>
				</tr>
              </table></td>
          <tr>
            <td width="100%" colspan="2">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>
							<%@ include  file="include/appEditButtons.jsp" %>
							<script	language="javascript">
									displayContactsInformation();
									<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
										disableForVzw();
									<% } else {}%>
							</script>
	
							<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
								<html:hidden property="aimsContactId"	/>
								<html:hidden property="appDeployments"	/>
								<html:hidden property="developerName"/>
								<html:hidden property="publisherName"/>
								<html:hidden property="sellingPoints"/>
								<html:hidden property="plannedDevStartDate"/>
								<html:hidden property="plannedEntryIntoNstl"/>
								<html:hidden property="plannedCompletionByNstl"/>
								<html:hidden property="prizes"/>
								<html:hidden property="networkUse"/>
								<html:hidden property="singleMultiPlayer"/>
						<% } else {}%>
						<%@ include file="include/brewMessageFooter.jsp" %>
						</td>
					</tr>					
				</table>
			</td>
          </tr>		  
        </table>
      </html:form>
    </div>
  </div>
</div>
