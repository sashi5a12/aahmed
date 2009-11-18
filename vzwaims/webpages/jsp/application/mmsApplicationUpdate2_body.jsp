<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<jsp:useBean id="task" class="java.lang.String"	scope="request"/><jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" /><%ApplicationUpdateForm.setCurrentPage("page2");%>
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

	<logic:iterate id="formContacts" name="MmsApplicationUpdateForm"	property="allContacts" scope="request">
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

			document.forms[0].contactFirstName.value = "<bean:write	name="MmsApplicationUpdateForm" property="contactFirstName" scope="request"/>";
			document.forms[0].contactLastName.value	=	"<bean:write name="MmsApplicationUpdateForm"	property="contactLastName" scope="request"/>";
			document.forms[0].contactTitle.value = "<bean:write	name="MmsApplicationUpdateForm" property="contactTitle" scope="request"/>";
			document.forms[0].contactEmail.value = "<bean:write	name="MmsApplicationUpdateForm" property="contactEmail" scope="request"/>";
			document.forms[0].contactPhone.value = "<bean:write	name="MmsApplicationUpdateForm" property="contactPhone" scope="request"/>";
			document.forms[0].contactMobile.value	=	"<bean:write name="MmsApplicationUpdateForm"	property="contactMobile" scope="request"/>";
		}
	}
	
	
	function truncateLocalTextAreas()
	{
        if (typeof document.forms[0].appDeployments != "undefined")
            if (typeof document.forms[0].appDeployments.value != "undefined") 
                TruncateText(document.forms[0].appDeployments,1000);
                
        if (typeof document.forms[0].programPromoInfo != "undefined")
            if (typeof document.forms[0].programPromoInfo.value != "undefined") 
                TruncateText(document.forms[0].programPromoInfo,500);		
	}

	<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
		function disableForVzw()
		{
			document.forms[0].splashScreenEps.disabled = true;		
			document.forms[0].activeScreenEps.disabled = true;
			document.forms[0].screenJpeg.disabled = true;
			document.forms[0].faqDoc.disabled = true;		
			document.forms[0].userGuide.disabled = true;
			document.forms[0].flashDemo.disabled = true;		
			document.forms[0].testPlanResults.disabled = true;
			document.forms[0].sampleContent.disabled = true;
			
			document.forms[0].appDeployments.disabled = true;
			document.forms[0].programPromoInfo.disabled = true;		

			document.forms[0].aimsContactId.disabled	=	true;
			document.forms[0].contactFirstName.disabled	=	true;
			document.forms[0].contactLastName.disabled = true;
			document.forms[0].contactTitle.disabled	=	true;
			document.forms[0].contactEmail.disabled	=	true;
			document.forms[0].contactPhone.disabled	=	true;
			document.forms[0].contactMobile.disabled = true;
			
		}
	<% } else {}%>		
	
	<%@ include  file="include/appJScript.jsp" %>
	
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
    <%@ include  file="include/appTabs.jsp" %>
    <div class="contentbox">
      <html:form action="/mmsApplicationUpdate.do"	enctype="multipart/form-data">
        <%@ include  file="include/appHidden.jsp" %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr width="100%">
            <td width="50%" valign="top"><table width="375" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message	key="MmsApplicationForm.table.head.campaign.files"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th><strong><bean:message	key="ManageApplicationForm.appSplashScreen"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="splashScreenEps"/><br/>
						<logic:notEmpty	name="MmsApplicationUpdateForm"	property="splashScreenEpsFileName" scope="request">
							<logic:equal name="MmsApplicationUpdateForm" property="splashScreenEpsTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SplashScreenEps&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="MmsApplicationUpdateForm"	property="splashScreenEpsTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="splashScreenEpsTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="MmsApplicationUpdateForm"	property="splashScreenEpsFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appActiveScreen"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="activeScreenEps"/><br/>
						<logic:notEmpty	name="MmsApplicationUpdateForm"	property="activeScreenEpsFileName" scope="request">
							<logic:equal name="MmsApplicationUpdateForm" property="activeScreenEpsTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ActiveScreenEps&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="MmsApplicationUpdateForm"	property="activeScreenEpsTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="activeScreenEpsTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="MmsApplicationUpdateForm"	property="activeScreenEpsFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appScreenShot"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="screenJpeg"/><br/>
						<logic:notEmpty	name="MmsApplicationUpdateForm"	property="screenJpegFileName" scope="request">
							<logic:equal name="MmsApplicationUpdateForm" property="screenJpegTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="MmsApplicationUpdateForm"	property="screenJpegTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="screenJpegTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="MmsApplicationUpdateForm"	property="screenJpegFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appFAQ"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="faqDoc"/><br/>
						<logic:notEmpty	name="MmsApplicationUpdateForm"	property="faqDocFileName" scope="request">

							<logic:equal name="MmsApplicationUpdateForm" property="faqDocTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FaqDoc&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="MmsApplicationUpdateForm"	property="faqDocTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="faqDocTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="MmsApplicationUpdateForm"	property="faqDocFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appUserGuide"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="userGuide"/><br/>
						<logic:notEmpty	name="MmsApplicationUpdateForm"	property="userGuideFileName" scope="request">
							<logic:equal name="MmsApplicationUpdateForm" property="userGuideTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=UserGuide&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="MmsApplicationUpdateForm"	property="userGuideTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="userGuideTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="MmsApplicationUpdateForm"	property="userGuideFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appFlashDemo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="flashDemo"/><br/>
						<logic:notEmpty	name="MmsApplicationUpdateForm"	property="flashDemoFileName" scope="request">
							<logic:equal name="MmsApplicationUpdateForm" property="flashDemoTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FlashDemo&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="MmsApplicationUpdateForm"	property="flashDemoTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="flashDemoTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="MmsApplicationUpdateForm"	property="flashDemoFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="ManageApplicationForm.appTestPlan"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="testPlanResults"/><br/>
							<logic:notEmpty	name="MmsApplicationUpdateForm"	property="testPlanResultsFileName" scope="request">
							<logic:equal name="MmsApplicationUpdateForm" property="testPlanResultsTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TestPlanResults&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="MmsApplicationUpdateForm"	property="testPlanResultsTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="testPlanResultsTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="MmsApplicationUpdateForm"	property="testPlanResultsFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="MmsApplicationForm.sampleContentFile"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="sampleContent"/><br/>
							<logic:notEmpty	name="MmsApplicationUpdateForm"	property="sampleContentFileName" scope="request">
							<logic:equal name="MmsApplicationUpdateForm" property="sampleContentTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SampleContent&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="MmsApplicationUpdateForm"	property="testPlanResultsTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="MmsApplicationUpdateForm"	property="sampleContentTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="MmsApplicationUpdateForm"	property="sampleContentFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
              </table></td>
            <td width="50%" valign=top><table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message	key="ManageApplicationForm.appTechContact"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th>
						<html:select styleClass="selectField" property="aimsContactId"	size="1" onchange="displayContactsInformation()">
						<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
						<logic:iterate id="formContacts" name="MmsApplicationUpdateForm"	property="allContacts" type="com.netpace.aims.model.core.AimsContact"	scope="request">
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
              </table></td>
          </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message	key="ApplicationForm.table.head.other.details"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
		  <tr>
		  	<td>
				<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<th>
						<strong><bean:message	key="ManageApplicationForm.appDeployments"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
						<br/>
						<html:textarea styleClass="textareaField" property="appDeployments" onkeyup="LimitText(this,1000)" onkeypress="LimitText(this,1000)" rows="3" cols="50"></html:textarea>
					</th>
					<th>
						<strong><bean:message	key="MmsApplicationForm.programPromoInfo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
						<br/>
						<html:textarea styleClass="textareaField" property="programPromoInfo" onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)" rows="3" cols="50"></html:textarea>
					</th>
				  </tr>
		  		</table>
			</td>
		  </tr>
          <tr>
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
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
						<html:hidden property="appDeployments"	/>
						<html:hidden property="programPromoInfo"/>
						<html:hidden property="aimsContactId"	/>						
				  	<% } else {}%>

				  </td>
                </tr>
              </table></td>
          </tr>
        </table>
      </html:form>
    </div>
  </div>
</div>
