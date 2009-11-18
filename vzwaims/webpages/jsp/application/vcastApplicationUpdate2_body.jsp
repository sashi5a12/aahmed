<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>


<jsp:useBean id="task" class="java.lang.String"	scope="request"/>

<jsp:useBean id="VcastApplicationUpdateForm" class="com.netpace.aims.controller.application.VcastApplicationUpdateForm" scope="request" />
<%VcastApplicationUpdateForm.setCurrentPage("page2");%>
<%@ include  file="include/vcastAppVariables.jsp" %>

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

	<logic:iterate id="formContacts" name="VcastApplicationUpdateForm"	property="allContacts" scope="request">
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

			document.forms[0].contactFirstName.value = "<bean:write	name="VcastApplicationUpdateForm" property="contactFirstName" scope="request"/>";
			document.forms[0].contactLastName.value	=	"<bean:write name="VcastApplicationUpdateForm"	property="contactLastName" scope="request"/>";
			document.forms[0].contactTitle.value = "<bean:write	name="VcastApplicationUpdateForm" property="contactTitle" scope="request"/>";
			document.forms[0].contactEmail.value = "<bean:write	name="VcastApplicationUpdateForm" property="contactEmail" scope="request"/>";
			document.forms[0].contactPhone.value = "<bean:write	name="VcastApplicationUpdateForm" property="contactPhone" scope="request"/>";
			document.forms[0].contactMobile.value	=	"<bean:write name="VcastApplicationUpdateForm"	property="contactMobile" scope="request"/>";
		}
	}

    function truncateLocalTextAreas()
    {
        if (typeof document.forms[0].evaluationComments != "undefined")
            if (typeof document.forms[0].evaluationComments.value != "undefined") 
                TruncateText(document.forms[0].evaluationComments,500);        
    }

	function disable1()
	{
		/*
        document.forms[0].sampleClip1.disabled = true;		
		
		document.forms[0].aimsContactId.disabled	=	true;
		document.forms[0].contactFirstName.disabled	=	true;
		document.forms[0].contactLastName.disabled = true;
		document.forms[0].contactTitle.disabled	=	true;
		document.forms[0].contactEmail.disabled	=	true;
		document.forms[0].contactPhone.disabled	=	true;
		document.forms[0].contactMobile.disabled = true;

        for (var i=0; i<document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == "listSelectedLicenseTypes") {
                document.forms[0].elements[i].disabled = true;
            }
        } 
        */       
    }
			
    <%@ include  file="include/vcastAppJScript.jsp" %>
	
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
    <%@ include  file="include/vcastAppTabs.jsp" %>
    <div class="contentbox">
      <html:form action="/vcastApplicationUpdate.do"	enctype="multipart/form-data">
        <%@ include  file="include/vcastAppHidden.jsp" %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
          <tr width="100%">
            <td width="50%" valign="top"><table width="375" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1>Clip Files</H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th><strong>Sample Video Clips&nbsp;<span class="requiredText">*</span>:</strong></th>
				</tr>
				<tr>
					<td>(Supported file types - avi, 3g2, wmv, mpg, mpeg)</td>
				</tr>
				<tr>
					<td >
						<html:file styleClass="inputField" style="margin-bottom:0px" size="30" property="sampleClip1"/><br/>
						<logic:notEmpty	name="VcastApplicationUpdateForm"	property="sampleClip1FileName" scope="request">
							<logic:equal name="VcastApplicationUpdateForm" property="sampleClip1TempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VcastSampleClip1&app_id=<bean:write	name="VcastApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="VcastApplicationUpdateForm"	property="sampleClip1TempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="VcastApplicationUpdateForm"	property="sampleClip1TempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="VcastApplicationUpdateForm"	property="sampleClip1FileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td >
						<html:file styleClass="inputField" style="margin-bottom:0px; margin-top:0px" size="30" property="sampleClip2"/><br/>
						<logic:notEmpty name="VcastApplicationUpdateForm"   property="sampleClip2FileName" scope="request">
							<logic:equal name="VcastApplicationUpdateForm" property="sampleClip2TempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VcastSampleClip2&app_id=<bean:write   name="VcastApplicationUpdateForm"   property="appsId"   />" class="a"   target="_blank">
							</logic:equal>
							<logic:notEqual name="VcastApplicationUpdateForm"   property="sampleClip2TempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write   name="VcastApplicationUpdateForm"   property="sampleClip2TempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write name="VcastApplicationUpdateForm"   property="sampleClip2FileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td>
						<html:file styleClass="inputField" size="30" style="margin-bottom:0px; margin-top:0px" property="sampleClip3"/><br/>
						<logic:notEmpty name="VcastApplicationUpdateForm"   property="sampleClip3FileName" scope="request">
							<logic:equal name="VcastApplicationUpdateForm" property="sampleClip3TempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VcastSampleClip3&app_id=<bean:write   name="VcastApplicationUpdateForm"   property="appsId"   />" class="a"   target="_blank">
							</logic:equal>
							<logic:notEqual name="VcastApplicationUpdateForm"   property="sampleClip3TempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write   name="VcastApplicationUpdateForm"   property="sampleClip3TempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write name="VcastApplicationUpdateForm"   property="sampleClip3FileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1>Operations Contact</H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th>
						<html:select styleClass="selectField" property="aimsContactId"   size="1" onchange="displayContactsInformation()">
						<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
						<logic:iterate id="formContacts" name="VcastApplicationUpdateForm"   property="allContacts" type="com.netpace.aims.model.core.AimsContact"   scope="request">
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
                      <H1>Target Audience</H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr>
					<th><strong>Gender:</strong></th>
				</tr>
				<tr>
					<td	>
						<logic:iterate id="genders" name="VcastApplicationUpdateForm" property="allAudGenders">
							<html:multibox property="listSelectedAudGenders">
							   <bean:write name="genders" property="audGenderId" />
							</html:multibox>
							   <bean:write name="genders" property="audGenderName" /><br/>
						</logic:iterate>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td ><strong>Age Group:</strong></td>
				</tr>
				<tr>
					<td >
						<logic:iterate id="ageGroups" name="VcastApplicationUpdateForm" property="allAudAges">
							<html:multibox property="listSelectedAudAges">
							   <bean:write name="ageGroups" property="audAgeId" />
							</html:multibox>
							   <bean:write name="ageGroups" property="audAgeName" /><br/>
						</logic:iterate>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td ><strong>Race:</strong></td>
				</tr>
				<tr>
					<td >
						<logic:iterate id="races" name="VcastApplicationUpdateForm" property="allAudRaces">
							<html:multibox property="listSelectedAudRaces">
							   <bean:write name="races" property="audRaceId" />
							</html:multibox>
							   <bean:write name="races" property="audRaceName" /><br/>
						</logic:iterate>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td ><strong>Household Income:</strong></td>
				</tr>
				<tr>
					<td >
						<logic:iterate id="incomes" name="VcastApplicationUpdateForm" property="allAudIncomes">
							<html:multibox property="listSelectedAudIncomes">
							   <bean:write name="incomes" property="audIncomeId" />
							</html:multibox>
							   <bean:write name="incomes" property="audIncomeName" /><br/>
						</logic:iterate>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td ><strong>Education:</strong></td>
				</tr>
				<tr>
					<td >
						<logic:iterate id="educations" name="VcastApplicationUpdateForm" property="allAudEducations">
							<html:multibox property="listSelectedAudEducations">
							   <bean:write name="educations" property="audEducationId" />
							</html:multibox>
							   <bean:write name="educations" property="audEducationName" /><br/>
						</logic:iterate>
					</td>
				</tr>
              </table></td>
          </tr>
        </table>
		<%if (isVerizonUser){%>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				<td>&nbsp;</td>
			  </tr>
			  <tr>
				<td><div class="lBox">
					<DIV class="headLeftCurveblk"></DIV>
					<H1>Evaluation Comments</H1>
					<DIV class="headRightCurveblk"></DIV>
				  </div></td>
			  </tr>
			  <tr>
				<td>					
					<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th width="100%">
								<html:textarea styleClass="textareaField" property="evaluationComments" onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)" rows="3" cols="50"></html:textarea>    
							</th>
						</tr>
					</table>
				</td>
			  </tr>
			 </table>		
        <% } else {}%>		  

        <table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td>
					<%@ include  file="include/vcastAppEditButtons.jsp" %>
									
					<script	language="javascript">
							displayContactsInformation();
					</script>
					
					<%if (isAllianceUser){%>
						<html:hidden property="evaluationComments"  />
					<% } else {}%>
				</td>
			</tr>
		</table>
      </html:form>
    </div>
  </div>
</div>
