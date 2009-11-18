<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>


<jsp:useBean id="task" class="java.lang.String"	scope="request"/>

<jsp:useBean id="WapApplicationUpdateForm" class="com.netpace.aims.controller.application.WapApplicationUpdateForm" scope="request" />
<%WapApplicationUpdateForm.setCurrentPage("page2");%>
<%@ include  file="include/wapAppVariables.jsp" %>

<script	language="javascript">

	var	contactsArray	=	new	Array();

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

	<logic:iterate id="formContacts" name="WapApplicationUpdateForm"	property="allContacts" scope="request">
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

			document.forms[0].contactFirstName.value = "<bean:write	name="WapApplicationUpdateForm" property="contactFirstName" scope="request"/>";
			document.forms[0].contactLastName.value	=	"<bean:write name="WapApplicationUpdateForm"	property="contactLastName" scope="request"/>";
			document.forms[0].contactTitle.value = "<bean:write	name="WapApplicationUpdateForm" property="contactTitle" scope="request"/>";
			document.forms[0].contactEmail.value = "<bean:write	name="WapApplicationUpdateForm" property="contactEmail" scope="request"/>";
			document.forms[0].contactPhone.value = "<bean:write	name="WapApplicationUpdateForm" property="contactPhone" scope="request"/>";
			document.forms[0].contactMobile.value	=	"<bean:write name="WapApplicationUpdateForm"	property="contactMobile" scope="request"/>";
		}
	}

	function truncateLocalTextAreas()
    {
        if (typeof document.forms[0].vendorProductDisplay != "undefined")
            if (typeof document.forms[0].vendorProductDisplay.value != "undefined") 
                TruncateText(document.forms[0].vendorProductDisplay,30);        
	}

	function disable1()
	{
		document.forms[0].productLogo.disabled = true;		
		document.forms[0].productIcon.disabled = true;
		document.forms[0].screenJpeg.disabled = true;
		document.forms[0].faqDoc.disabled = true;		
		document.forms[0].userGuide.disabled = true;
		document.forms[0].presentation.disabled = true;
        document.forms[0].appMediumLargeImage.disabled = true;
        document.forms[0].appQVGAPotraitImage.disabled = true;
        document.forms[0].appQVGALandscapeImage.disabled = true;


        document.forms[0].launchDate.disabled = true;
        document.forms[0].testEffectiveDate.disabled = true;
		document.forms[0].daysOfMonth2Pos.disabled = true;
        document.forms[0].daysOfMonth3Pos.disabled = true;
				
	}
    
    function disable2()
    {
        document.forms[0].vzwRetailPrice.disabled    =   true;
        document.forms[0].vendorProductDisplay.disabled = true;
        for (var i=0; i<document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == "listSelectedLicenseTypes") {
                document.forms[0].elements[i].disabled = true;
            }
        }        
    }
    
    function disable3()
    {
        for (var i=0; i<document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == "listSelectedLicenseTypes") 
                if ((document.forms[0].elements[i].value == "2") || (document.forms[0].elements[i].value == "3"))
                    document.forms[0].elements[i].disabled = true;            
        }        
    }
			
    <%@ include  file="include/wapAppJScript.jsp" %>
	
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td width="100%">
	  <%@ include file="/common/error.jsp" %>
	</td></tr>
	<tr><td width="100%">
	  <aims:getFileSizeMessage message="<%= (String)session.getAttribute(AimsConstants.FILE_MSG) %>" imgSrc="images/sign.gif" userType="<%=AimsConstants.VZW_USERTYPE %>"/>&nbsp;
	</td></tr>
</table>


<div id="contentBox" style="float:left" onmousemove="truncateLocalTextAreas();">
  <div class="homeColumnTab">
    <%@ include  file="include/wapAppTabs.jsp" %>
    <div class="contentbox">
      <html:form action="/wapApplicationUpdate.do"	enctype="multipart/form-data">
        <%@ include  file="include/wapAppHidden.jsp" %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
          <tr width="100%">
            <td width="50%" valign="top"><table width="375" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message key="ApplicationForm.table.head.app.files"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th><strong>Product Logo For PC&nbsp;<span class="requiredText">*</span>:</strong></th>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="productLogo"/><br/>
						<logic:notEmpty	name="WapApplicationUpdateForm"	property="productLogoFileName" scope="request">
							<logic:equal name="WapApplicationUpdateForm" property="productLogoTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapProductLogo&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="WapApplicationUpdateForm"	property="productLogoTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="WapApplicationUpdateForm"	property="productLogoTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="WapApplicationUpdateForm"	property="productLogoFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Product Icon For Device:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="productIcon"/><br/>
						<logic:notEmpty	name="WapApplicationUpdateForm"	property="productIconFileName" scope="request">
							<logic:equal name="WapApplicationUpdateForm" property="productIconTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapProductIcon&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="WapApplicationUpdateForm"	property="productIconTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="WapApplicationUpdateForm"	property="productIconTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="WapApplicationUpdateForm"	property="productIconFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Content Landing Page Sample Screen Shot (JPEG/GIF):</strong></td>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="screenJpeg"/><br/>
						<logic:notEmpty	name="WapApplicationUpdateForm"	property="screenJpegFileName" scope="request">
							<logic:equal name="WapApplicationUpdateForm" property="screenJpegTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="WapApplicationUpdateForm"	property="screenJpegTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="WapApplicationUpdateForm"	property="screenJpegTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="WapApplicationUpdateForm"	property="screenJpegFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appFAQ"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="faqDoc"/><br/>
						<logic:notEmpty	name="WapApplicationUpdateForm"	property="faqDocFileName" scope="request">

							<logic:equal name="WapApplicationUpdateForm" property="faqDocTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FaqDoc&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="WapApplicationUpdateForm"	property="faqDocTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="WapApplicationUpdateForm"	property="faqDocTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="WapApplicationUpdateForm"	property="faqDocFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appUserGuide"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="userGuide"/><br/>
						<logic:notEmpty	name="WapApplicationUpdateForm"	property="userGuideFileName" scope="request">

							<logic:equal name="WapApplicationUpdateForm" property="userGuideTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=UserGuide&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="WapApplicationUpdateForm"	property="userGuideTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="WapApplicationUpdateForm"	property="userGuideTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="WapApplicationUpdateForm"	property="userGuideFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong>Presentation:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="presentation"/><br/>
						<logic:notEmpty	name="WapApplicationUpdateForm"	property="presentationFileName" scope="request">
							<logic:equal name="WapApplicationUpdateForm" property="presentationTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapPresentation&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="WapApplicationUpdateForm"	property="presentationTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="WapApplicationUpdateForm"	property="presentationTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="WapApplicationUpdateForm"	property="presentationFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>

				<%-- 3 image file for WAP FTP --%>
				<tr>
					<td><strong><bean:message key="WapApplicationForm.label.appMediumLargeImage" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="appMediumLargeImage"/> <bean:message key="WapApplicationForm.label.appMediumLargeImage.filesize"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> <br/>
						<logic:notEmpty	name="WapApplicationUpdateForm"	property="appMediumLargeImageFileName" scope="request">
							<logic:equal name="WapApplicationUpdateForm" property="appMediumLargeImageTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapAppMediumLargeImage&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="WapApplicationUpdateForm"	property="appMediumLargeImageTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appMediumLargeImageTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="WapApplicationUpdateForm"	property="appMediumLargeImageFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message key="WapApplicationForm.label.appQVGAPotraitImage" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="appQVGAPotraitImage"/> <bean:message key="WapApplicationForm.label.appQVGAPotraitImage.filesize"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> <br/>
						<logic:notEmpty	name="WapApplicationUpdateForm"	property="appQVGAPotraitImageFileName" scope="request">
							<logic:equal name="WapApplicationUpdateForm" property="appQVGAPotraitImageTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapAppQVGAPotraitImage&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="WapApplicationUpdateForm"	property="appQVGAPotraitImageTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appQVGAPotraitImageTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="WapApplicationUpdateForm"	property="appQVGAPotraitImageFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message key="WapApplicationForm.label.appQVGALandscapeImage" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="appQVGALandscapeImage"/> <bean:message key="WapApplicationForm.label.appQVGALandscapeImage.filesize" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> <br/>
						<logic:notEmpty	name="WapApplicationUpdateForm"	property="appQVGALandscapeImageFileName" scope="request">
							<logic:equal name="WapApplicationUpdateForm" property="appQVGALandscapeImageTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapQVGALandscapeImage&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="WapApplicationUpdateForm"	property="appQVGALandscapeImageTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="WapApplicationUpdateForm"	property="appQVGALandscapeImageTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="WapApplicationUpdateForm"	property="appQVGALandscapeImageFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>				
				<%-- 3 end image file for WAP FTP --%>
				<tr><td>&nbsp;</td></tr>
				<tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message   key="ManageApplicationForm.appTechContact"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th>
						<html:select styleClass="selectField" property="aimsContactId"   size="1" onchange="displayContactsInformation()">
						<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
						<logic:iterate id="formContacts" name="WapApplicationUpdateForm"   property="allContacts" type="com.netpace.aims.model.core.AimsContact"   scope="request">
							<html:option value="<%=formContacts.getContactId().toString()%>">
			                	<bean:write	name="formContacts"	property="firstName" />
			                	<bean:write	name="formContacts"	property="lastName" /> 
			                </html:option>
						</logic:iterate>
						</html:select>
					</th>
				</tr>
				<tr>
					<td><strong><bean:message   key="ManageApplicationForm.label.orEnter"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
				</tr>
				<tr>
					<td><strong><bean:message   key="ManageApplicationForm.appContactFirstName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactFirstName" size="35" maxlength="50" /></td>
				</tr>
				<tr>
					<td><strong><bean:message   key="ManageApplicationForm.appContactLastName"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactLastName"   size="35" maxlength="50"     /></td>
				</tr>
				<tr>
					<td><strong><bean:message   key="ManageApplicationForm.appContactTitle" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactTitle"  size="35" maxlength="50"     /></td>
				</tr>
				<tr>
					<td><strong><bean:message   key="ManageApplicationForm.appContactEmail" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactEmail"  size="35" maxlength="100"     /></td>
				</tr>
				<tr>
					<td><strong><bean:message   key="ManageApplicationForm.appContactTelephone" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactPhone"  size="35" maxlength="50"     /></td>
				</tr>
				<tr>
					<td><strong><bean:message   key="ManageApplicationForm.appContactMobile"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="contactMobile" size="35" maxlength="50"     /></td>
				</tr>				
              </table></td>
            <td width="50%" valign=top><table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message	key="WapApplicationForm.table.head.launch.info"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th><strong>Product Live Date&nbsp;<span class="requiredText">*</span>:</strong></th>
				</tr>
				<tr>
					<td>
						<html:text styleClass="inputField"	property="launchDate"	size="15"/><img name="daysOfMonth2Pos" onclick="toggleDatePicker('daysOfMonth2','WapApplicationUpdateForm.launchDate')" id="daysOfMonth2Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth2"/></td>
				</tr>
				<tr>
					<td><strong>Testing Effective Date:</strong></td>
				</tr>
				<tr>
					<td>
						<html:text styleClass="inputField"  property="testEffectiveDate"   size="15"/><img name="daysOfMonth3Pos" onclick="toggleDatePicker('daysOfMonth3','WapApplicationUpdateForm.testEffectiveDate')" id="daysOfMonth3Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth3"/></td>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1>Information For Premium Content Only</H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th><strong>License Type:</strong></th>
				</tr>
				<tr>
					<td class="text" valign="top">
						<logic:iterate id="licenses" name="WapApplicationUpdateForm" property="allLicenseTypes" type="com.netpace.aims.model.application.AimsWapLicenseType">
							<html:radio property="listSelectedLicenseTypes" value="<%=licenses.getLicenseTypeId().toString()%>"/><bean:write name="licenses" property="licenseTypeName" /><br/>
						</logic:iterate>
					</td>
				</tr>
				<tr>
					<td><strong>VZW Suggested Retail Price (USD):</strong></td>
				</tr>
				<tr>
					<td class="text" valign="top">
						<html:text styleClass="inputField" property="vzwRetailPrice"  size="35" maxlength="100"     />
					</td>
				</tr>                                        
				<tr>
					<td><strong>Vendor Product Display:</strong></td>
				</tr>
				<tr>
					<td>(Information entered here will be displayed on the subscriber’s bill)</td>
				</tr>
				<tr>
					<td>
						<html:textarea styleClass="textareaField" property="vendorProductDisplay" onkeyup="LimitText(this,30)" onkeypress="LimitText(this,30)" rows="3" cols="50"></html:textarea>
					</td>
				</tr>
              </table></td>
          </tr>
        </table>
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td>
					<%@ include  file="include/wapAppEditButtons.jsp" %>
					
					<script	language="javascript">
							displayContactsInformation();
							<%if ((isVerizonUser) || (isEqualOrAboveSubmittedDCR)){%>
								disable1();                                    
								<%if (isEqualOrAboveSubmittedDCR){%>
									disable2();
								<% } else {}%>                                                  
							<% } else {}%>
							disable3(); //Disabling temporarily License Types: Event & Fixed Price                                                                		
					</script>

					
					<%if ((isVerizonUser) || (isEqualOrAboveSubmittedDCR)){%>
						<html:hidden property="launchDate"	/>
						<html:hidden property="testEffectiveDate"  />
						<%if (isEqualOrAboveSubmittedDCR){%>
							<html:hidden property="vzwRetailPrice"  />
							<html:hidden property="vendorProductDisplay"   />
							<logic:notEmpty name="WapApplicationUpdateForm" property="listSelectedLicenseTypes">
								<logic:iterate id="selLicenseTypeIds" name="WapApplicationUpdateForm" property="listSelectedLicenseTypes" indexId="ctr">
									<input type="hidden" name="listSelectedLicenseTypes" value="<bean:write name="selLicenseTypeIds"/>"/>                     
								</logic:iterate>
							</logic:notEmpty>
						<% } else {}%>                            						
					<% } else {}%>
				</td>
			</tr>
		</table>
      </html:form>

    </div>
  </div>
</div>
