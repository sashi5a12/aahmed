<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>


<jsp:useBean id="task" class="java.lang.String"	scope="request"/>
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" />

<%ApplicationUpdateForm.setCurrentPage("page2");%>


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

	<logic:iterate id="formContacts" name="SmsApplicationUpdateForm"	property="allContacts" scope="request">
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

			document.forms[0].contactFirstName.value = "<bean:write	name="SmsApplicationUpdateForm"	property="contactFirstName"	scope="request"/>";
			document.forms[0].contactLastName.value	=	"<bean:write name="SmsApplicationUpdateForm"	property="contactLastName" scope="request"/>";
			document.forms[0].contactTitle.value = "<bean:write	name="SmsApplicationUpdateForm"	property="contactTitle"	scope="request"/>";
			document.forms[0].contactEmail.value = "<bean:write	name="SmsApplicationUpdateForm"	property="contactEmail"	scope="request"/>";
			document.forms[0].contactPhone.value = "<bean:write	name="SmsApplicationUpdateForm"	property="contactPhone"	scope="request"/>";
			document.forms[0].contactMobile.value	=	"<bean:write name="SmsApplicationUpdateForm"	property="contactMobile" scope="request"/>";
		}

	}

	function displayNoMessage()
	{
		if (document.forms[0].ifVzwDirectConn.value="N")
			alert('<bean:message	key="SmsApplicationForm.no.vzw.direct.conn.text"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>');
	}

	function truncateLocalTextAreas()
	{
        if (typeof document.forms[0].appDeployments != "undefined")
            if (typeof document.forms[0].appDeployments.value != "undefined") 
                TruncateText(document.forms[0].appDeployments,1000);
                
        if (typeof document.forms[0].campaignPromoInfo != "undefined")
            if (typeof document.forms[0].campaignPromoInfo.value != "undefined") 
                TruncateText(document.forms[0].campaignPromoInfo,500);
	}

	<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
		function disableForVzw()
		{
			document.forms[0].faqDoc.disabled = true;		
			document.forms[0].userGuide.disabled = true;
			document.forms[0].testPlanResults.disabled = true;
			document.forms[0].messageFlow.disabled = true;
			
			document.forms[0].connType.disabled = true;
			document.forms[0].shortCodesReqd.disabled = true;		
			document.forms[0].appDeployments.disabled = true;
			document.forms[0].campaignPromoInfo.disabled = true;		

			document.forms[0].aimsContactId.disabled	=	true;
			document.forms[0].contactFirstName.disabled	=	true;
			document.forms[0].contactLastName.disabled = true;
			document.forms[0].contactTitle.disabled	=	true;
			document.forms[0].contactEmail.disabled	=	true;
			document.forms[0].contactPhone.disabled	=	true;
			document.forms[0].contactMobile.disabled = true;
					
						
			for (var i=0; i<document.forms[0].elements.length; i++) {
		    if (document.forms[0].elements[i].name == "carrierSupport") {
		      document.forms[0].elements[i].disabled = true;
		    }
		  }
		  
		  for (var i=0; i<document.forms[0].elements.length; i++) {
		    if (document.forms[0].elements[i].name == "ifVzwDirectConn") {
		      document.forms[0].elements[i].disabled = true;
		    }
		  }
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
      <html:form action="/smsApplicationUpdate.do"	enctype="multipart/form-data">
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
                      <H1><bean:message	key="SmsApplicationForm.table.head.campaign.files"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th><strong><bean:message	key="ManageApplicationForm.appFAQ"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="faqDoc"/><br/>
						<logic:notEmpty	name="SmsApplicationUpdateForm"	property="faqDocFileName" scope="request">
							<logic:equal name="SmsApplicationUpdateForm" property="faqDocTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FaqDoc&app_id=<bean:write	name="SmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="SmsApplicationUpdateForm"	property="faqDocTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="SmsApplicationUpdateForm"	property="faqDocTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="SmsApplicationUpdateForm"	property="faqDocFileName"/>
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
						<logic:notEmpty	name="SmsApplicationUpdateForm"	property="userGuideFileName" scope="request">
							<logic:equal name="SmsApplicationUpdateForm" property="userGuideTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=UserGuide&app_id=<bean:write	name="SmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="SmsApplicationUpdateForm"	property="userGuideTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="SmsApplicationUpdateForm"	property="userGuideTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="SmsApplicationUpdateForm"	property="userGuideFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td	><strong><bean:message	key="ManageApplicationForm.appTestPlan"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="testPlanResults"/><br/>
							<logic:notEmpty	name="SmsApplicationUpdateForm"	property="testPlanResultsFileName" scope="request">
							<logic:equal name="SmsApplicationUpdateForm" property="testPlanResultsTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TestPlanResults&app_id=<bean:write	name="SmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="SmsApplicationUpdateForm"	property="testPlanResultsTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="SmsApplicationUpdateForm"	property="testPlanResultsTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="SmsApplicationUpdateForm"	property="testPlanResultsFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td	><strong><bean:message	key="SmsApplicationForm.messageFlow"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" property="messageFlow"/><br/>
						<logic:notEmpty	name="SmsApplicationUpdateForm"	property="messageFlowFileName" scope="request">
							<logic:equal name="SmsApplicationUpdateForm" property="messageFlowTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=MessageFlow&app_id=<bean:write	name="SmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="SmsApplicationUpdateForm"	property="messageFlowTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="SmsApplicationUpdateForm"	property="messageFlowTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
									<bean:write	name="SmsApplicationUpdateForm"	property="messageFlowFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message	key="SmsApplicationForm.table.head.launch.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th>
						<strong><bean:message	key="SmsApplicationForm.carrierSupport"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
						<br/>
						<span style="font-weight:normal; font-variant:normal">
						<html:radio	property="carrierSupport" value="V"/><bean:message	key="ManageApplicationForm.radio.label.vzw"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
						<html:radio	property="carrierSupport" value="M"/><bean:message	key="ManageApplicationForm.radio.label.multicarrier"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
						<html:radio	property="carrierSupport" value="C"/><bean:message	key="ManageApplicationForm.radio.label.csc"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
						</span>
					</th>
				</tr>
				<tr>
					<td><strong><bean:message	key="SmsApplicationForm.ifVzwDirectConn"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:radio	property="ifVzwDirectConn" value="Y"/><bean:message	key="ManageApplicationForm.radio.label.yes"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
						<html:radio	property="ifVzwDirectConn" value="N" onclick="displayNoMessage()"/><bean:message	key="ManageApplicationForm.radio.label.no"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="SmsApplicationForm.connType"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:text styleClass="inputField"	property="connType"	size="40" maxlength="50" />
					</td>
				</tr>
				<tr>
					<td><strong><bean:message	key="SmsApplicationForm.shortCodesReqd"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td>
						<html:text styleClass="inputField"	property="shortCodesReqd"	size="40" maxlength="100"/>
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
						<logic:iterate id="formContacts" name="SmsApplicationUpdateForm"	property="allContacts" type="com.netpace.aims.model.core.AimsContact"	scope="request">
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
                <H1><bean:message	key="SmsApplicationForm.table.head.other.details"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
		  <tr>
		  	<td>
				<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
				 <tr>
					<th><strong><bean:message	key="SmsApplicationForm.campaignDeployments"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
					<th><strong><bean:message	key="SmsApplicationForm.campaignPromoInfo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
				</tr>
				<tr>
					<td>
						<html:textarea styleClass="textareaField" property="appDeployments" onkeyup="LimitText(this,1000)" onkeypress="LimitText(this,1000)" rows="3" cols="50"></html:textarea>
					</td>
					<td>
						 <html:textarea styleClass="textareaField" property="campaignPromoInfo" onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)" rows="3" cols="50"></html:textarea>
					</td>
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
						<html:hidden property="carrierSupport"	/>
						<html:hidden property="ifVzwDirectConn"	/>
						<html:hidden property="connType"	/>
						<html:hidden property="shortCodesReqd"	/>
						<html:hidden property="appDeployments"	/>
						<html:hidden property="campaignPromoInfo"/>
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
