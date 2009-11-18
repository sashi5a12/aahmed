<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

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

	<%int index	=	0;%>
	<logic:iterate id="formContacts" name="EntApplicationUpdateForm"	property="allContacts" scope="request">
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

			document.forms[0].contactFirstName.value = "<bean:write	name="EntApplicationUpdateForm" property="contactFirstName" scope="request"/>";
			document.forms[0].contactLastName.value	=	"<bean:write name="EntApplicationUpdateForm"	property="contactLastName" scope="request"/>";
			document.forms[0].contactTitle.value = "<bean:write	name="EntApplicationUpdateForm" property="contactTitle" scope="request"/>";
			document.forms[0].contactEmail.value = "<bean:write	name="EntApplicationUpdateForm" property="contactEmail" scope="request"/>";
			document.forms[0].contactPhone.value = "<bean:write	name="EntApplicationUpdateForm" property="contactPhone" scope="request"/>";
			document.forms[0].contactMobile.value	=	"<bean:write name="EntApplicationUpdateForm"	property="contactMobile" scope="request"/>";
		}

	}

	function trackCountForTextAreas()
	{
		TrackCount(document.forms[0].shortDesc,'textCountShortDesc',200);
		TrackCount(document.forms[0].longDesc,'textCountLongDesc',500);
	}


	function truncateLocalTextAreas()
	{
        if (typeof document.forms[0].appDeployments != "undefined")
            if (typeof document.forms[0].appDeployments.value != "undefined")
                TruncateText(document.forms[0].appDeployments,1000);

        if (typeof document.forms[0].fortuneCustomers != "undefined")
            if (typeof document.forms[0].fortuneCustomers.value != "undefined")
                TruncateText(document.forms[0].fortuneCustomers,1000);

        if (typeof document.forms[0].customerBenefits != "undefined")
            if (typeof document.forms[0].customerBenefits.value != "undefined")
                TruncateText(document.forms[0].customerBenefits,1000);
        
         if (typeof document.forms[0].productExclusiveToVzw != "undefined")
            if (typeof document.forms[0].productExclusiveToVzw.value != "undefined")
                TruncateText(document.forms[0].productExclusiveToVzw,1000);
              
        if (typeof document.forms[0].additionalInformation != "undefined")
            if (typeof document.forms[0].additionalInformation.value != "undefined")
                TruncateText(document.forms[0].additionalInformation,1000);                
	}

	function checkNationalRegion(obj)
	{
		var bRegionMidWest, bRegionNorthEast, bRegionSouth, bRegionWest, bRegionNational;

		for (var i=0; i<document.forms[0].elements.length; i++)
		{
			if (document.forms[0].elements[i].name == "regionId")
			{
				if (document.forms[0].elements[i].value == <%=AimsConstants.REGION_MIDWEST_ID.toString()%>)
					bRegionMidWest = document.forms[0].elements[i].checked;
				if (document.forms[0].elements[i].value == <%=AimsConstants.REGION_NORTHEAST_ID.toString()%>)
					bRegionNorthEast = document.forms[0].elements[i].checked;
				if (document.forms[0].elements[i].value == <%=AimsConstants.REGION_SOUTH_ID.toString()%>)
					bRegionSouth = document.forms[0].elements[i].checked;
				if (document.forms[0].elements[i].value == <%=AimsConstants.REGION_WEST_ID.toString()%>)
					bRegionWest = document.forms[0].elements[i].checked;
				if (document.forms[0].elements[i].value == <%=AimsConstants.REGION_NATIONAL_ID.toString()%>)
					bRegionNational = document.forms[0].elements[i].checked;
			}
		}

		if ( (bRegionNational) || ((bRegionMidWest) && (bRegionNorthEast) && (bRegionSouth) && (bRegionWest)) )
			selectNationalRegionOnly();
	}

	function selectNationalRegionOnly()
	{
		for (var i=0; i<document.forms[0].elements.length; i++)
		{
			if (document.forms[0].elements[i].name == "regionId")
			{
				if (document.forms[0].elements[i].value == <%=AimsConstants.REGION_MIDWEST_ID.toString()%>)
					document.forms[0].elements[i].checked=false;
				if (document.forms[0].elements[i].value == <%=AimsConstants.REGION_NORTHEAST_ID.toString()%>)
					document.forms[0].elements[i].checked=false;
				if (document.forms[0].elements[i].value == <%=AimsConstants.REGION_SOUTH_ID.toString()%>)
					document.forms[0].elements[i].checked=false;
				if (document.forms[0].elements[i].value == <%=AimsConstants.REGION_WEST_ID.toString()%>)
					document.forms[0].elements[i].checked=false;
				if (document.forms[0].elements[i].value == <%=AimsConstants.REGION_NATIONAL_ID.toString()%>)
					document.forms[0].elements[i].checked=true;
			}
		}
		alert("<bean:message key="EntApplicationForm.check.national.region.text" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>");
	}


	<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
		function disableForVzw()
		{
			document.forms[0].faqDoc.disabled = true;
			document.forms[0].userGuide.disabled = true;
			document.forms[0].flashDemo.disabled = true;
			document.forms[0].testPlanResults.disabled = true;
			document.forms[0].presentationFile.disabled = true;

			document.forms[0].aimsContactId.disabled	=	true;
			document.forms[0].contactFirstName.disabled	=	true;
			document.forms[0].contactLastName.disabled = true;
			document.forms[0].contactTitle.disabled	=	true;
			document.forms[0].contactEmail.disabled	=	true;
			document.forms[0].contactPhone.disabled	=	true;
			document.forms[0].contactMobile.disabled = true;

		  document.forms[0].appDeployments.disabled = true;
		  document.forms[0].fortuneCustomers.disabled = true;
          document.forms[0].customerBenefits.disabled = true;
		  document.forms[0].otherIndFocusValue.disabled = true;


		  for (var i=0; i<document.forms[0].elements.length; i++) {
		    if (document.forms[0].elements[i].name == "industryFocusId") {
		      document.forms[0].elements[i].disabled = true;
		    }
		  }

		  for (var i=0; i<document.forms[0].elements.length; i++) {
		    if (document.forms[0].elements[i].name == "regionId") {
		      document.forms[0].elements[i].disabled = true;
		    }
		  }

		  for (var i=0; i<document.forms[0].elements.length; i++) {
		    if (document.forms[0].elements[i].name == "solutionComponentId") {
		      document.forms[0].elements[i].disabled = true;
		    }
		  }


		}
	<% } else {}%>
	
	function enableNationalonly()
	{
		var targerMarket=document.forms[0].regionId;

		for (var i=0; i<targerMarket.length; i++)
		{
			if(targerMarket[i].value=="25")//if national
			{
				targerMarket[i].disabled=false;
			}
		}
	}
	
	function enableDisableDependentField(chkBox, dependentField) {
    if (chkBox.checked) {
        dependentField.disabled = false;
    }
    else {
        dependentField.disabled = true;
        dependentField.value = "";
    }
}

function boboHelp(event)
{
	openZonHelpWindow(event, '<bean:message key="JMA.BOBO.Hint" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>'); 
	return false;
}
function hintODI(event)
{
	openZonHelpWindow(event, "<bean:message key='JMA.ODI.Hint' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>"); 
	return false;
}


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
	<%@ include  file="include/entAppTabs.jsp" %>
    <div class="contentbox">
      <html:form action="/entApplicationUpdate.do"	enctype="multipart/form-data">
	  	<%@ include  file="include/entAppHidden.jsp" %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
          <tr>
            <td colspan="2">&nbsp;</td>
          </tr>
          <tr width="100%">
            <td width="50%" valign="top">
				<table width="375" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message key="ApplicationForm.table.head.app.files"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
                
				<tr>
					<th><strong><bean:message	key="EntApplicationForm.presentationUpload"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></th>
				</tr>
				<tr>
					<td>
						<html:file size="30" styleClass="inputField" property="presentationFile"/><br/>
						<logic:notEmpty	name="EntApplicationUpdateForm"	property="presentationFileName" scope="request">

							<logic:equal name="EntApplicationUpdateForm" property="presentationTempFileId"	scope="request"	value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=PresentationFile&app_id=<bean:write	name="EntApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
							</logic:equal>
							<logic:notEqual	name="EntApplicationUpdateForm"	property="presentationTempFileId" scope="request" value="0">
								<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="EntApplicationUpdateForm"	property="presentationTempFileId" />" class="a" target="_blank">
							</logic:notEqual>
								<bean:write	name="EntApplicationUpdateForm"	property="presentationFileName"/>
								</a>
						</logic:notEmpty>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr>
                  <td colspan="2" class="noPad"><div class="mmBox">
                      <DIV class="headLeftCurveblk"></DIV>
                      <H1><bean:message	key="EntApplicationForm.table.head.industry.focus"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span></H1>
                      <DIV class="headRightCurveblk"></DIV>
                    </div></td>
                </tr>
				<tr><th style="font-style:normal;font-weight:normal;font-variant:normal;">
                    <logic:iterate id="industryFocus" name="EntApplicationUpdateForm" property="allIndustryFocus">
                        <html:multibox property="industryFocusId">
                           <bean:write name="industryFocus" property="industryId" />
                        </html:multibox>
                           <bean:write name="industryFocus" property="industryName" /><br/>
                    </logic:iterate>
                    
				</th></tr>
              </table>
            </td>
            <td width="50%" valign=top>
                <table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0" >
                    <tr>
                      <td class="noPad"><div class="mmBox">
                          <DIV class="headLeftCurveblk"></DIV>
                          <H1><bean:message	key="ManageApplicationForm.appSolutionContact" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                          <DIV class="headRightCurveblk"></DIV>
                        </div></td>
                    </tr>
                    <tr>
                        <th>
                            <html:select styleClass="selectField" property="aimsContactId"	size="1" onchange="displayContactsInformation()">
                            <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                            <logic:iterate id="formContacts" name="EntApplicationUpdateForm"	property="allContacts" type="com.netpace.aims.model.core.AimsContact"	scope="request">
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
                        <td><strong><bean:message	key="ManageApplicationForm.appContactFirstName"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
                    </tr>
                    <tr>
                        <td><html:text styleClass="inputField" property="contactFirstName" size="35" maxlength="50"	/></td>
                    </tr>
                    <tr>
                        <td><strong><bean:message	key="ManageApplicationForm.appContactLastName"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
                    </tr>
                    <tr>
                        <td><html:text styleClass="inputField" property="contactLastName"	size="35" maxlength="50"	 /></td>
                    </tr>
                    <tr>
                        <td><strong><bean:message	key="ManageApplicationForm.appContactTitle"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
                    </tr>
                    <tr>
                        <td><html:text styleClass="inputField" property="contactTitle"	size="35" maxlength="50"	 /></td>
                    </tr>
                    <tr>
                        <td><strong><bean:message	key="ManageApplicationForm.appContactEmail"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
                    </tr>
                    <tr>
                        <td><html:text styleClass="inputField" property="contactEmail"	size="35" maxlength="50"	 /></td>
                    </tr>
                    <tr>
                        <td><strong><bean:message	key="ManageApplicationForm.appContactTelephone"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
                    </tr>
                    <tr>
                        <td><html:text styleClass="inputField" property="contactPhone"	size="35" maxlength="50"	 /></td>
                    </tr>
                    <tr>
                        <td><strong><bean:message	key="ManageApplicationForm.appContactMobile"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
                    </tr>
                    <tr>
                        <td><html:text styleClass="inputField" property="contactMobile"	size="35" maxlength="50"	 /></td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                </table>
			    <table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0" >
                    <tr>
                      <td colspan="3" class="noPad"><div class="mmBox">
                          <DIV class="headLeftCurveblk"></DIV>
                          <H1><bean:message	key="EntApplicationForm.table.head.regions"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span></H1>
                          <DIV class="headRightCurveblk"></DIV>
                        </div></td>
                    </tr>
                    <tr>
                        <th style="font-style:normal;font-weight:normal;font-variant:normal;">
                            <logic:iterate id="region" name="EntApplicationUpdateForm" property="allRegions">
                                <html:multibox property="regionId" disabled="true" onclick="">
                                   <bean:write name="region" property="regionId" />
                                </html:multibox>
                                   <bean:write name="region" property="regionName" /><br/>
                            </logic:iterate>
                        </th>
                    </tr>
                    <script language="javascript"> javascript: enableNationalonly();</script>
                    <tr><td>&nbsp;</td></tr>
                </table>
                
            </td>
         </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr><td>&nbsp;</td></tr>

          
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message	key="ApplicationForm.table.head.other.details"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                    <tr>
                        <th width="50%">
                            <strong><bean:message key="EntApplicationForm.fortuneCustomers.forview"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:textarea styleClass="textareaField" property="fortuneCustomers" onkeyup="TruncateText(this,1000);" onkeypress="TruncateText(this,1000);" rows="3" cols="50" ></html:textarea>
                        </th>
                        <th width="50%">
                            <strong><bean:message key="ManageApplicationForm.appDeployments"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:textarea styleClass="textareaField" property="appDeployments" onkeyup="TruncateText(this,1000);" onkeypress="TruncateText(this,1000);" rows="3" cols="50" ></html:textarea>
                        </th>
                    </tr>
                     <tr>
                    	<td>
                    		<strong><bean:message key="EntApplicationForm.productInfo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                          		<logic:iterate id="obj" name="EntApplicationUpdateForm" property="allProductinfo">
						            <html:multibox property="entProductInfo" onclick="">
						                <bean:write name="obj" property="typeId"/>
						            </html:multibox>
					            <bean:write name="obj" property="typeValue"/>
					            <br/>
					        </logic:iterate> 
                          <html:hidden property="productInformation"/>
                      </td>
                    	<td>
                    		<strong><bean:message key="EntApplicationForm.briefDescription"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:textarea styleClass="textareaField" property="briefDescription" onkeyup="TruncateText(this,1000);" onkeypress="TruncateText(this,1000);" rows="3" cols="50" ></html:textarea>
                    	</td>
                    </tr>
                    
                    <tr>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productExclusiveToVZW"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:radio property="isProductExeclusiveToVZW" value="Y" onclick="javascript: enableDisableDependentField(document.forms[0].isProductExeclusiveToVZW[1],document.forms[0].productExclusiveToVzw);"/>
            					Yes	
            				<html:radio property="isProductExeclusiveToVZW" value="N" onclick="javascript: enableDisableDependentField(document.forms[0].isProductExeclusiveToVZW[1],document.forms[0].productExclusiveToVzw);"/>
            					 No
            				<br/>	 
                            <html:textarea styleClass="textareaField" property="productExclusiveToVzw" onkeyup="TruncateText(this,1000);" onkeypress="TruncateText(this,1000);" rows="3" cols="50" ></html:textarea>
                    		
                    		<script language="javascript">enableDisableDependentField(document.forms[0].isProductExeclusiveToVZW[1],document.forms[0].productExclusiveToVzw)</script>
                    	</td>
                    	<td>
                    		<strong><bean:message key="EntApplicationForm.additionalInfo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:textarea styleClass="textareaField" property="additionalInformation" onkeyup="TruncateText(this,1000);" onkeypress="TruncateText(this,1000);" rows="3" cols="50" ></html:textarea>
                    	</td>
                    </tr>
                    
                    <tr>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.optionToGoExclusiveWithVZW"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
          					<html:radio property="isGoExclusiveWithVZW" value="Y" />
            					Yes	
            				<html:radio property="isGoExclusiveWithVZW" value="N" />
            					 No
                    	</td>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productUseVzwVzNt"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:radio property="isProductUseVzwVzNt" value="Y" />
            					Yes	
            				<html:radio property="isProductUseVzwVzNt" value="N" />
            					 No
                    	</td>
                    </tr>
                    <tr>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productCertifiedWithVZW"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:radio property="isProductCertifiedVZW" value="Y" />
            					Yes	
            				<html:radio property="isProductCertifiedVZW" value="N" />
            					 No
                    	</td>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productCertifiedODIProcess"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <a href="#" onclick="hintODI(event); return false;">[?]</a>
                           <br/>
                            <html:radio property="isProductCertifiedODIProcess" value="Y" />
            					Yes	
            				<html:radio property="isProductCertifiedODIProcess" value="N" />
            					 No
                    	</td>
                    </tr>
                    
                     <tr>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productRequiredLBS"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <a href="#" onclick="openZonHelpWindow(event, '<bean:message key="JMA.LBS.Hint" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>'); return false;">[?]</a><br/>
                            <html:radio property="isProductRequiedLBS" value="Y" />
            					Yes	
            				<html:radio property="isProductRequiedLBS" value="N" />
            					 No
                    	</td>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productOfferBOBO"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <a href="#" onclick="boboHelp(event); return false;">[?]</a><br/>
                            <html:radio property="isOfferBoboServices" value="Y" />
            					Yes	
            				<html:radio property="isOfferBoboServices" value="N" />
            					 No
                    	</td>
                    </tr>
                    <tr>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.marketSegment"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                          		<logic:iterate id="obj" name="EntApplicationUpdateForm" property="allMarketSegInfo">
						            <html:multibox property="entMarketSegInfo" onclick="">
						                <bean:write name="obj" property="typeId"/>
						            </html:multibox>
					            <bean:write name="obj" property="typeValue"/>
					            <br/>
					        </logic:iterate> 
                      </td>
                    	
                    	<td>
                    	</td>
                    </tr>
                  
                </table>
            </td>
          </tr>
          <tr>
            <td width="100%">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td>
							<%@ include  file="include/entAppEditButtons.jsp" %>
							<script	language="javascript">
								displayContactsInformation();
								<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
									//disableForVzw();
								<% } else {}%>
						    </script>

							<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
                                <html:hidden property="aimsContactId"	/>
                                <html:hidden property="appDeployments"	/>
                                <html:hidden property="fortuneCustomers"	/>
                                <html:hidden property="customerBenefits"    />
                               
                               
                                <html:hidden property="otherIndFocusValue"	/>
                                <html:hidden property="solutionComponentId"	/>
				  	        <% } else {}%>
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