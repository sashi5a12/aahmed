<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<script language="javascript">

function validateFrm(objFrm)
{
    var errors = new String();
    var errObject = null;
    var termsAccepted = false;
	var tech=document.JMAAllianceCompleteRegistrationForm.isTechincalInfo;
	var sales=document.JMAAllianceCompleteRegistrationForm.isSalesSupport;
	
    regEx = / {1,}$/;
    
    if(tech[0].checked)
    {
	   if (objFrm.techEmail.value.replace(regEx, "").length <= 0)
	    {
	        errors = errors + "Please Enter <bean:message key='error.AllianceRegistrationForm.techEmail' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
	        if (errObject == null)
	            errObject = objFrm.techEmail;
	    }
	    else if (!validateEmail(objFrm.techEmail.value)) {
	        errors = errors + "Please Enter valid email pattern for <bean:message key='error.AllianceRegistrationForm.techEmail' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n";
	        if (errObject == null)
	            errObject = objFrm.techEmail;
	    }
	}
	
	 if(sales[0].checked)
    {
	   if (objFrm.salesEmail.value.replace(regEx, "").length <= 0)
	    {
	        errors = errors + "Please Enter <bean:message key='error.JMAAllianceRegistrationForm.salesEmail' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
	        if (errObject == null)
	            errObject = objFrm.techEmail;
	    }
	    else if (!validateEmail(objFrm.salesEmail.value)) {
	        errors = errors + "Please Enter valid email pattern for <bean:message key='error.JMAAllianceRegistrationForm.salesEmail' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n";
	        if (errObject == null)
	            errObject = objFrm.techEmail;
	    }
	}
	
  if (errors.length != 0)
    {
        alert(errors);
        if (errObject && !errObject.length) {
            errObject.focus();
        }
        return false;
    }
    else
    {
        return true;
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
function enableDisableTextField(txtBox,option){
	if(option=="Yes"){
		txtBox.disabled = false;
		}
	else if(option=="No"){
		txtBox.disabled = true;
		txtBox.value = "";
	}	 
}

function is24X7TechnicalSupport(option)
{
	if(option=="Yes"){
		document.JMAAllianceCompleteRegistrationForm.techEmail.disabled= false;
		//document.JMAAllianceCompleteRegistrationForm.techPassword.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.techFirstName.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.techLastName.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.techTitle.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.techPhone.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.techMobile.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.techFax.disabled= false;
	}
	else if(option=="No"){
		document.JMAAllianceCompleteRegistrationForm.techEmail.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.techEmail.value= "";
		
		//document.JMAAllianceCompleteRegistrationForm.techPassword.disabled= true;
		//document.JMAAllianceCompleteRegistrationForm.techPassword.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.techFirstName.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.techFirstName.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.techLastName.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.techLastName.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.techTitle.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.techTitle.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.techPhone.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.techPhone.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.techMobile.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.techMobile.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.techFax.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.techFax.value= "";
	}
}

function is24X7SalesSupport(option)
{
	if(option=="Yes"){
		document.JMAAllianceCompleteRegistrationForm.salesEmail.disabled= false;
		//document.JMAAllianceCompleteRegistrationForm.salesPassword.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.salesFirstName.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.salesLastName.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.salesTitle.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.salesPhone.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.salesMobile.disabled= false;
		document.JMAAllianceCompleteRegistrationForm.salesFax.disabled= false;
	}
	else if(option=="No"){
		document.JMAAllianceCompleteRegistrationForm.salesEmail.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.salesEmail.value= "";
		
		//document.JMAAllianceCompleteRegistrationForm.salesPassword.disabled= true;
		//document.JMAAllianceCompleteRegistrationForm.salesPassword.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.salesFirstName.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.salesFirstName.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.salesLastName.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.salesLastName.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.salesTitle.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.salesTitle.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.salesPhone.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.salesPhone.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.salesMobile.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.salesMobile.value= "";
		
		document.JMAAllianceCompleteRegistrationForm.salesFax.disabled= true;
		document.JMAAllianceCompleteRegistrationForm.salesFax.value= "";
	}
}
function validateEmail(emailString) {
    var regEx = /^\w[\w\d\-\+]*(\.[\w\d\-\+]+)*@\w[\w\d\-]+(\.[\w\d\-]+)*\.[a-z]{2,7}$/i ;
    return regEx.test(emailString);
}
function enableDisableDependentChkBox(chkBox, dependentField) {
    if (chkBox.checked) {
        dependentField.disabled = false;
    }
    else {
        dependentField.disabled = true;
        dependentField.value = "";
    }
}
function help(event)
{
	openZonHelpWindow(event, '<bean:message key="JMA.BOBO.Hint" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>'); 
	return false;
}
function hintODI(event)
{
	openZonHelpWindow(event, "<bean:message key='JMA.ODI.Hint' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>"); 
	return false;
}

function onPageLoad()
{

	var tech=document.JMAAllianceCompleteRegistrationForm.isTechincalInfo;
	var sales=document.JMAAllianceCompleteRegistrationForm.isSalesSupport;
	var optionToGoVzw=document.JMAAllianceCompleteRegistrationForm.isOptionToGoWithVZW;
	var salesEngagument=document.JMAAllianceCompleteRegistrationForm.isSalesEngaugementWithVZW
	var carriers=document.JMAAllianceCompleteRegistrationForm.alliancesWithOtherCarriers;
	if(tech[1].checked)
		is24X7TechnicalSupport("No");
	if(sales[1].checked)	
		is24X7SalesSupport("No");
	//if(optionToGoVzw[1].checked)
	//	enableDisableTextField(document.JMAAllianceCompleteRegistrationForm.optionToGoWithVZW,'No');
	if(salesEngagument[1].checked)
		enableDisableTextField(document.JMAAllianceCompleteRegistrationForm.salesEngaugementWithVZW,'No');		
	/*if(document.JMAAllianceCompleteRegistrationForm.status.value=="A"){
		document.JMAAllianceCompleteRegistrationForm.isAccepted.checked="checked";
		document.JMAAllianceCompleteRegistrationForm.isAccepted.value=true;
		document.getElementById("isAcceptedDiv").style.display="none"
		}
	else
	{
		document.getElementById("isAcceptedDiv").style.display=""
	}*/
	
	if(carriers[carriers.length-1].checked)
	{
		enableDisableTextField(document.JMAAllianceCompleteRegistrationForm.allianceWithOtherCarriers,'Yes');
	}	
		
}

//Calling onload metjod
//window.onload=onPageLoad;	

</script>
<%@ include file="/common/error.jsp" %>

<DIV class="homeColumnTab lBox">

<logic:notEmpty name="JMAAllianceCompleteRegistrationForm" property="isJmaInfoComplete" scope="request">
	<logic:equal name="JMAAllianceCompleteRegistrationForm" property="isJmaInfoComplete" scope="request" value="Y">
		<aims:getAllianceTab attributeName="JMA Info"/>
		<div>&nbsp;</div>
	</logic:equal>
</logic:notEmpty>			

<DIV class="headLeftCurveblk"></DIV>
<H1>Complete Registration</H1>
<DIV class="headRightCurveblk"></DIV>

<html:form action="/jmaAllianceCompleteRegistrationUpdate" enctype="multipart/form-data" focus="shortDescription">
<html:hidden property="task" value="save"/>

<html:hidden property="productPresentationFileName"/>
<html:hidden property="productPresentationTempFileId"/>

<html:hidden property="winOpportunityFileName"/>
<html:hidden property="winOpportunityTempFileId"/>
<html:hidden property="isJmaInfoComplete"/>
<html:hidden property="status"/>


<DIV class="contentbox">
<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient" bordercolor="black">
<tr>
	<th>
		 <strong><bean:message key="JMAAllianceRegistrationForm.companyShortDescription" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span>(Max: 1000 chars)</strong><br/>
         <html:textarea styleClass="textareaField" property="shortDescription" rows="7" cols="50" disabled="false"/>
	</th>
	<th>
		 <strong><bean:message key="JMAAllianceRegistrationForm.faq" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span></strong><br/>
         <html:textarea styleClass="textareaField" property="faq" rows="7" cols="50" disabled="false"/>
	
	</th>
</tr>

<tr>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.industryVerticals" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span></strong><br/>
		<logic:iterate id="industryVertical" name="JMAAllianceCompleteRegistrationForm" property="allIndustryVerticals">
            <html:multibox property="allianceIndustryVerticals" onclick="">
                <bean:write name="industryVertical" property="industryId"/>
            </html:multibox>
            <bean:write name="industryVertical" property="industryName"/>
            <br/>
        </logic:iterate>
			
	</td>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.industryTopVerticals" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong>
		 <br/>
		<logic:iterate id="industryVertical" name="JMAAllianceCompleteRegistrationForm" property="allIndustryVerticals">
            <html:multibox property="allianceTopIndustryVerticals" onclick="">
                <bean:write name="industryVertical" property="industryId"/>
            </html:multibox>
            <bean:write name="industryVertical" property="industryName"/>
            <br/>
        </logic:iterate>
	</td>
</tr>

<tr>
	<td>
		 <strong><bean:message key="JMAAllianceRegistrationForm.reasonForRelationship" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span></strong><br/>
         <html:textarea styleClass="textareaField" property="reasonOfReleation" rows="7" cols="50" disabled="false"/>
	</td>
	<td>
		 <strong><bean:message key="JMAAllianceRegistrationForm.contractualAgreements" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span></strong><br/>
         <html:textarea styleClass="textareaField" property="contractAgreements" rows="7" cols="50" disabled="false"/>
	
	</td>

</tr>

<tr>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.allianceWithOtherCarriers" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span></strong><br/>
		<logic:iterate id="allianceWithOtherCarriers" name="JMAAllianceCompleteRegistrationForm" property="allAllianceWithOtherCarriers">
            <html:multibox property="alliancesWithOtherCarriers" onclick="javascript:enableDisableDependentChkBox(document.JMAAllianceCompleteRegistrationForm.alliancesWithOtherCarriers[document.JMAAllianceCompleteRegistrationForm.alliancesWithOtherCarriers.length - 1], document.JMAAllianceCompleteRegistrationForm.allianceWithOtherCarriers);">
                <bean:write name="allianceWithOtherCarriers" property="carrierId"/>
            </html:multibox>
            <bean:write name="allianceWithOtherCarriers" property="carrierName"/>
            <br/>
        </logic:iterate>
        <html:textarea styleClass="textareaField" property="allianceWithOtherCarriers" rows="7" cols="50"
                       disabled="true"/>
	</td>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.optionToGoWithVzw" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
          	<html:radio property="isOptionToGoWithVZW" value="Y" onclick="" />
            	Yes
              	&nbsp;
            <html:radio property="isOptionToGoWithVZW" value="N" onclick="" />
            	 No
            <br/>
            <html:textarea styleClass="textareaField" property="optionToGoWithVZW" rows="7" cols="50" disabled="false"/>	 
       </td>
</tr>

<tr>
	<td>
		 <strong><bean:message key="JMAAllianceRegistrationForm.productMenu" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span></strong><br/>
         <html:textarea styleClass="textareaField" property="productMenu" rows="7" cols="50" disabled="false"/>
	</td>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.salesEngaguments" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
          	<html:radio property="isSalesEngaugementWithVZW" value="Y" onclick="avascript: enableDisableTextField(document.forms[0].salesEngaugementWithVZW,'Yes');" />
            	Yes
              	&nbsp;
            <html:radio property="isSalesEngaugementWithVZW" value="N" onclick="avascript: enableDisableTextField(document.forms[0].salesEngaugementWithVZW,'No');" />
            	 No
            <br/>
            <html:textarea styleClass="textareaField" property="salesEngaugementWithVZW" rows="7" cols="50" disabled="false"/>	 
       </td>
</tr>

<tr>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.productuseVZWVZNetwork" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
          	<br/>
          	<html:radio property="isProductUseVzwVzNt" value="Y" />
            	Yes
              	&nbsp;
            <html:radio property="isProductUseVzwVzNt" value="N" />
            	 No
            <br/><br/>
            <strong><bean:message key="JMAAllianceRegistrationForm.productCertified" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
          	<br/>
          	<html:radio property="isProductCertifiedVZW" value="Y" />
            	Yes
              	&nbsp;
            <html:radio property="isProductCertifiedVZW" value="N" />
            	 No
            	 &nbsp;
            <html:radio property="isProductCertifiedVZW" value="C" />
            	In Certification 	 
            <br/><br/>
            <strong><bean:message key="JMAAllianceRegistrationForm.certifiedODI" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
          	<a href="#" onclick="hintODI(event); return false;">[?]</a>
            <br/>
          	<html:radio property="isProductCertifiedODI" value="Y" />
            	Yes
              	&nbsp;
            <html:radio property="isProductCertifiedODI" value="N" />
            	 No
	</td>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.winOpportunities" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong><br/>
		 <html:file property="winOpportunity" styleClass="inputField"/>
		  <br/>
		     <logic:notEmpty name="JMAAllianceCompleteRegistrationForm" property="winOpportunityFileName" scope="request">
            <logic:equal name="JMAAllianceCompleteRegistrationForm" property="winOpportunityTempFileId" scope="request"
                         value="0">
                <a class="a" target="_blank"
                   href="/aims/jmaResourceAction.do?resource=winOpportunities&object=AimsAllianc">
                    <bean:write name="JMAAllianceCompleteRegistrationForm" property="winOpportunityFileName"/>
                </a>
            </logic:equal>
            <logic:notEqual name="JMAAllianceCompleteRegistrationForm" property="winOpportunityTempFileId" scope="request"
                            value="0">
                <a class="a" target="_blank"
                   href="/aims/jmaResourceAction.do?resource=tempFile&object=AimsAllianc&tempFileId=<bean:write name='JMAAllianceCompleteRegistrationForm' property='winOpportunityTempFileId' />">
                    <bean:write name="JMAAllianceCompleteRegistrationForm" property="winOpportunityFileName"/>
                </a>
            </logic:notEqual>

        </logic:notEmpty> 
	</td>
</tr>
	<td>
		 <strong><bean:message key="JMAAllianceRegistrationForm.briefDescription" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
         <html:textarea styleClass="textareaField" property="briefDescription" rows="7" cols="50" disabled="false"/>
	</td>
	<td>
		 <strong><bean:message key="JMAAllianceRegistrationForm.productInfo" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><br/>
         <html:textarea styleClass="textareaField" property="productInformation" rows="7" cols="50" disabled="false"/>
	
	</td>
<tr>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.sloutionReside" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong>    
        <br/>
        <logic:iterate id="solutionReside" name="JMAAllianceCompleteRegistrationForm" property="solutionsReside">
        <bean:define id="type_Id">
             <bean:write name="solutionReside" property="typeId"/>
         </bean:define>
        
            <html:radio property="solutionReside" value="<%=type_Id%>" />
            <bean:write name="solutionReside" property="typeValue"/>
            <br/>
        </logic:iterate>
        
	</td>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.LBS" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
		 <a href="#" onclick="openZonHelpWindow(event, '<bean:message key="JMA.LBS.Hint" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>'); return false;">[?]</a>
			<br/>
			<html:radio property="isProductRequiredLBS" value="Y" />
            	Yes
              	&nbsp;
          	<html:radio property="isProductRequiredLBS" value="N" />
            	 No
         <br/><br/>
         <strong><bean:message key="JMAAllianceRegistrationForm.BOBO" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
		  <a href='#' onclick="help(event); return false;">[?]</a>
			<br/>
			<html:radio property="isOfferBOBOServices" value="Y" />
            	Yes
              	&nbsp;
          	<html:radio property="isOfferBOBOServices" value="N" />
            	 No   	 
	</td>
</tr>
<tr>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.presentation" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br>
		 <html:file property="productPresentation" styleClass="inputField"/>
		      <br/>
        <logic:notEmpty name="JMAAllianceCompleteRegistrationForm" property="productPresentationFileName" scope="request">
            <logic:equal name="JMAAllianceCompleteRegistrationForm" property="productPresentationTempFileId" scope="request"
                         value="0">
                <a class="a" target="_blank"
                   href="/aims/jmaResourceAction.do?resource=prodPresentation&object=AimsAllianc">
                    <bean:write name="JMAAllianceCompleteRegistrationForm" property="productPresentationFileName"/>
                </a>
            </logic:equal>
            <logic:notEqual name="JMAAllianceCompleteRegistrationForm" property="productPresentationTempFileId" scope="request"
                            value="0">
                <a class="a" target="_blank"
                   href="/aims/jmaResourceAction.do?resource=tempFile&object=AimsAllianc&tempFileId=<bean:write name='JMAAllianceCompleteRegistrationForm' property='productPresentationTempFileId' />">
                    <bean:write name="JMAAllianceCompleteRegistrationForm" property="productPresentationFileName"/>
                </a>
            </logic:notEqual>

        </logic:notEmpty>    
	</td>
	<td>&nbsp;</td>
</tr>

</table>
</DIV>
<br/>
<DIV class="headLeftCurveblk"></DIV>
<H1>Alliance 24x7 Technical Contact Information</H1>
<DIV class="headRightCurveblk"></DIV>

<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">   				
<tr>
	<th class="noPad" width="50%">
		<strong>24X7 Technical Contact Information</strong>
			<html:radio property="isTechincalInfo" value="Y" onclick="javascript: is24X7TechnicalSupport('Yes')"/>
            	Yes
              	&nbsp;
          	<html:radio property="isTechincalInfo" value="N" onclick="javascript: is24X7TechnicalSupport('No')"/>
            	 No
	</th>
	<th width="50%">
		&nbsp;
	</th>
</tr>
<tr>
    <td> 
        <strong><bean:message key="AllianceRegistrationForm.techEmail" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text property="techEmail" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td> 
        &nbsp;
    </td>
</tr>	
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techFirstName" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text property="techFirstName" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techLastName" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text property="techLastName" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.title" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text property="techTitle" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techPhone" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text property="techPhone" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techMobileNumber" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
        <html:text property="techMobile" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techFax" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
        <html:text property="techFax" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>    
</table>
<br/>
<DIV class="headLeftCurveblk"></DIV>
<H1>Full Sales Support Contact Information</H1>
<DIV class="headRightCurveblk"></DIV>

<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
<tr>
	<th class="noPad" width="50%">
		<strong>Is the Sales Support 24X7 </strong>
			<html:radio property="isSalesSupport" value="Y" onclick="javascript: is24X7SalesSupport('Yes')"/>
            	Yes
              	&nbsp;
          	<html:radio property="isSalesSupport" value="N" onclick="javascript: is24X7SalesSupport('No')"/>
            	 No
	</th>
	<th width="50%">
		&nbsp;
	</th>
</tr>   				
<tr>
    <td> 
        <strong><bean:message key="AllianceRegistrationForm.techEmail" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text property="salesEmail" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td> 
        &nbsp;
    </td>
</tr>	
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techFirstName" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text property="salesFirstName" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techLastName" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text property="salesLastName" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.title" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text property="salesTitle" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techPhone" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text property="salesPhone" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techMobileNumber" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
        <html:text property="salesMobile" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techFax" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
        <html:text property="salesFax" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
     
	    </td>
	    <td>
	    <logic:notEmpty name="JMAAllianceCompleteRegistrationForm" property="status" scope="request">
			<logic:equal name="JMAAllianceCompleteRegistrationForm" property="status" scope="request" value="R">
				<strong><bean:message key="JMAAllianceRegistrationForm.resubmit" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
			 	<span class="requiredText">*</span></strong><br/>
	         		<html:textarea styleClass="textareaField" property="resubmitDescription" rows="7" cols="50" disabled="false"/>
				<br/>
		    	<div class="redBtn" style=" margin-left:10px;float:right; margin-top:10px; margin-bottom:10px" id="Submit" title="Submit">
		            <div><div><div onclick="resubmitForm('<bean:message key="JMAAllianceRegistrationForm.resubmit.message" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>');return false;">Resubmit</div></div></div>
		        </div>
	        </logic:equal>
	        
	        <logic:notEqual name="JMAAllianceCompleteRegistrationForm" property="status" scope="request" value="R">
	    	<div class="redBtn" style=" margin-left:10px;float:right; margin-top:10px; margin-bottom:10px" id="Submit" title="Submit">
	            <div><div><div onclick="submitForm();return false;">Submit</div></div></div>
	        </div>
	        </logic:notEqual>
	     </logic:notEmpty>   
	        
	        <logic:notEmpty name="JMAAllianceCompleteRegistrationForm" property="isJmaInfoComplete" scope="request">
			<logic:equal name="JMAAllianceCompleteRegistrationForm" property="isJmaInfoComplete" scope="request" value="N">
	        
	        <div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Save" title="Save">
	            <div><div><div onclick="saveForm();return false;">Save</div></div></div>
	        </div>
	        </logic:equal>
	        </logic:notEmpty>
	        
	    </td>
	</tr>
</table>


</div>

</html:form>
<script>
    function submitForm() {
    	if(validateFrm(document.JMAAllianceCompleteRegistrationForm)){
    		document.JMAAllianceCompleteRegistrationForm.task.value="submit";
    		document.forms[0].submit();
    	}else
    		return false;	
    }
    function resubmitForm(txt) {
    	
    	if(confirm(txt))
    	{
    		//alert(txt);
    		document.JMAAllianceCompleteRegistrationForm.task.value="resubmit";
    		document.forms[0].submit();
    	}
    	
    }
    function saveForm() {
    	document.JMAAllianceCompleteRegistrationForm.task.value="save";
    	document.forms[0].submit();
    }
    
    //Calling on page load function
    onPageLoad();
</script>