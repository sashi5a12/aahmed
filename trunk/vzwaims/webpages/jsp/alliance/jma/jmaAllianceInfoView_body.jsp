<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<script language="javascript">

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

function onPageLoad()
{

	var tech=document.JMAAllianceCompleteRegistrationForm.isTechincalInfo;
	var sales=document.JMAAllianceCompleteRegistrationForm.isSalesSupport;
	var optionToGoVzw=document.JMAAllianceCompleteRegistrationForm.isOptionToGoWithVZW;
	var salesEngagument=document.JMAAllianceCompleteRegistrationForm.isSalesEngaugementWithVZW
	
	if(tech[1].checked)
		is24X7TechnicalSupport("No");
	if(sales[1].checked)	
		is24X7SalesSupport("No");
	if(optionToGoVzw[1].checked)
		enableDisableTextField(document.JMAAllianceCompleteRegistrationForm.optionToGoWithVZW,'No');
	if(salesEngagument[1].checked)
		enableDisableTextField(document.JMAAllianceCompleteRegistrationForm.salesEngaugementWithVZW,'No');		
	
		
}

//Calling onload metjod
//window.onload=onPageLoad;	

</script>
<%@ include file="/common/error.jsp" %>

<DIV class="homeColumnTab lBox">

<bean:define id="alliance_id" type="java.lang.Long" name="JMAAllianceCompleteRegistrationForm" property="jmaAllianceId"/>

<aims:getVZWAllianceTab attributeName="JMA Info" allianceId="<%=alliance_id.toString()%>"/>
<div>&nbsp;</div>
	
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


<DIV class="contentbox">
<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient" bordercolor="black">
<tr>
	<th>
		 <strong><bean:message key="JMAAllianceRegistrationForm.companyShortDescription" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span>(Max 200 words)</strong><br/>
         <html:textarea styleClass="textareaField" property="shortDescription" rows="7" cols="50" disabled="true"/>
	</th>
	<th>
		 <strong><bean:message key="JMAAllianceRegistrationForm.faq" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span></strong><br/>
         <html:textarea styleClass="textareaField" property="faq" rows="7" cols="50" disabled="true"/>
	
	</th>
</tr>

<tr>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.industryVerticals" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span></strong><br/>
		<logic:iterate id="industryVertical" name="JMAAllianceCompleteRegistrationForm" property="allIndustryVerticals">
            <html:multibox property="allianceIndustryVerticals" onclick="" disabled="true">
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
            <html:multibox property="allianceTopIndustryVerticals" onclick="" disabled="true">
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
         <html:textarea styleClass="textareaField" property="reasonOfReleation" rows="7" cols="50" disabled="true"/>
	</td>
	<td>
		 <strong><bean:message key="JMAAllianceRegistrationForm.contractualAgreements" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span></strong><br/>
         <html:textarea styleClass="textareaField" property="contractAgreements" rows="7" cols="50" disabled="true"/>
	
	</td>

</tr>

<tr>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.allianceWithOtherCarriers" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span></strong><br/>
		<logic:iterate id="allianceWithOtherCarriers" name="JMAAllianceCompleteRegistrationForm" property="allAllianceWithOtherCarriers">
            <html:multibox property="alliancesWithOtherCarriers" disabled="true" onclick="javascript:enableDisableDependentField(document.JMAAllianceCompleteRegistrationForm.alliancesWithOtherCarriers[document.JMAAllianceCompleteRegistrationForm.alliancesWithOtherCarriers.length - 1], document.JMAAllianceCompleteRegistrationForm.allianceWithOtherCarriers);">
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
          	<html:radio disabled="true" property="isOptionToGoWithVZW" value="Y" onclick="avascript: enableDisableTextField(document.forms[0].optionToGoWithVZW,'Yes');" />
            	Yes
              	&nbsp;
            <html:radio disabled="true" property="isOptionToGoWithVZW" value="N" onclick="avascript: enableDisableTextField(document.forms[0].optionToGoWithVZW,'No');" />
            	 No
            <br/>
            <html:textarea  styleClass="textareaField" property="optionToGoWithVZW" rows="7" cols="50" disabled="true"/>	 
       </td>
</tr>

<tr>
	<td>
		 <strong><bean:message key="JMAAllianceRegistrationForm.productMenu" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
		 <span class="requiredText">*</span></strong><br/>
         <html:textarea styleClass="textareaField" property="productMenu" rows="7" cols="50" disabled="true"/>
	</td>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.salesEngaguments" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
          	<html:radio disabled="true" property="isSalesEngaugementWithVZW" value="Y" onclick="avascript: enableDisableTextField(document.forms[0].salesEngaugementWithVZW,'Yes');" />
            	Yes
              	&nbsp;
            <html:radio disabled="true" property="isSalesEngaugementWithVZW" value="N" onclick="avascript: enableDisableTextField(document.forms[0].salesEngaugementWithVZW,'No');" />
            	 No
            <br/>
            <html:textarea styleClass="textareaField" property="salesEngaugementWithVZW" rows="7" cols="50" disabled="true"/>	 
       </td>
</tr>

<tr>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.productuseVZWVZNetwork" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
          	<br/>
          	<html:radio disabled="true" property="isProductUseVzwVzNt" value="Y" />
            	Yes
              	&nbsp;
            <html:radio disabled="true"property="isProductUseVzwVzNt" value="N" />
            	 No
            <br/><br/>
            <strong><bean:message key="JMAAllianceRegistrationForm.productCertified" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
          	<br/>
          	<html:radio disabled="true" property="isProductCertifiedVZW" value="Y" />
            	Yes
              	&nbsp;
            <html:radio disabled="true" property="isProductCertifiedVZW" value="N" />
            	 No
           		 &nbsp;
            <html:radio disabled="true" property="isProductCertifiedVZW" value="C" />
            	In Certification  	 
            <br/><br/>
            <strong><bean:message key="JMAAllianceRegistrationForm.certifiedODI" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
          	<a href="#" onclick="hintODI(event); return false;">[?]</a>
            <br/>
          	<html:radio disabled="true" property="isProductCertifiedODI" value="Y" />
            	Yes
              	&nbsp;
            <html:radio disabled="true" property="isProductCertifiedODI" value="N" />
            	 No
	</td>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.winOpportunities" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong><br/>
		
		  <br/>
		     <logic:notEmpty name="JMAAllianceCompleteRegistrationForm" property="winOpportunityFileName" scope="request">
            <logic:equal name="JMAAllianceCompleteRegistrationForm" property="winOpportunityTempFileId" scope="request"
                         value="0">
                <a class="a" target="_blank"
                   href="/aims/jmaResourceAction.do?resource=winOpportunities&object=AimsAllianc&alliance_id=<%=alliance_id%>">
                    <bean:write name="JMAAllianceCompleteRegistrationForm" property="winOpportunityFileName"/>
                </a>
            </logic:equal>
            <logic:notEqual name="JMAAllianceCompleteRegistrationForm" property="winOpportunityTempFileId" scope="request"
                            value="0">
                <a class="a" target="_blank"
                   href="/aims/jmaResourceAction.do?resource=tempFile&object=AimsAllianc&tempFileId=<bean:write name='JMAAllianceCompleteRegistrationForm' property='winOpportunityTempFileId' />&alliance_id=<%=alliance_id%>">
                    <bean:write name="JMAAllianceCompleteRegistrationForm" property="winOpportunityFileName"/>
                </a>
            </logic:notEqual>

        </logic:notEmpty> 
	</td>
</tr>
	<td>
		 <strong><bean:message key="JMAAllianceRegistrationForm.briefDescription" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
         <html:textarea styleClass="textareaField" property="briefDescription" rows="7" cols="50" disabled="true"/>
	</td>
	<td>
		 <strong><bean:message key="JMAAllianceRegistrationForm.productInfo" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><br/>
         <html:textarea styleClass="textareaField" property="productInformation" rows="7" cols="50" disabled="true"/>
	
	</td>
<tr>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.sloutionReside" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong>
		<br>
		<html:radio disabled="true" property="solutionReside" value="1" />Behind customer firewall
        <br/>
        <html:radio disabled="true" property="solutionReside" value="2" />Inside carrier network
        <br/> 
        <html:radio disabled="true" property="solutionReside" value="3" />Managed/hosted offsite
	</td>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.LBS" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
		 <a href="#" onclick="openZonHelpWindow(event, '<bean:message key="JMA.LBS.Hint" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>'); return false;">[?]</a>
			<br/>
			<html:radio disabled="true" property="isProductRequiredLBS" value="Y" />
            	Yes
              	&nbsp;
          	<html:radio disabled="true" property="isProductRequiredLBS" value="N" />
            	 No
         <br/><br/>
         <strong><bean:message key="JMAAllianceRegistrationForm.BOBO" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/><span class="requiredText">*</span></strong>
		  <a href="#" onclick="boboHelp(event); return false;">[?]</a>
			<br/>
			<html:radio disabled="true" property="isOfferBOBOServices" value="Y" />
            	Yes
              	&nbsp;
          	<html:radio disabled="true" property="isOfferBOBOServices" value="N" />
            	 No   	 
	</td>
</tr>
<tr>
	<td>
		<strong><bean:message key="JMAAllianceRegistrationForm.presentation" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br>
		     
        <logic:notEmpty name="JMAAllianceCompleteRegistrationForm" property="productPresentationFileName" scope="request">
            <logic:equal name="JMAAllianceCompleteRegistrationForm" property="productPresentationTempFileId" scope="request"
                         value="0">
                <a class="a" target="_blank"
                   href="/aims/jmaResourceAction.do?resource=prodPresentation&object=AimsAllianc&alliance_id=<%=alliance_id%>">
                    <bean:write name="JMAAllianceCompleteRegistrationForm" property="productPresentationFileName"/>
                </a>
            </logic:equal>
            <logic:notEqual name="JMAAllianceCompleteRegistrationForm" property="productPresentationTempFileId" scope="request"
                            value="0">
                <a class="a" target="_blank"
                   href="/aims/jmaResourceAction.do?resource=tempFile&object=AimsAllianc&tempFileId=<bean:write name='JMAAllianceCompleteRegistrationForm' property='productPresentationTempFileId' />&alliance_id=<%=alliance_id%>">
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
			<html:radio disabled="true" property="isTechincalInfo" value="Y" onclick="javascript: is24X7TechnicalSupport('Yes')"/>
            	Yes
              	&nbsp;
          	<html:radio disabled="true" property="isTechincalInfo" value="N" onclick="javascript: is24X7TechnicalSupport('No')"/>
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
        <html:text disabled="true" property="techEmail" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td> 
        &nbsp;
    </td>
</tr>	
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techFirstName" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text disabled="true" property="techFirstName" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techLastName" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text disabled="true" property="techLastName" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.title" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text disabled="true" property="techTitle" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techPhone" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text disabled="true" property="techPhone" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techMobileNumber" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
        <html:text disabled="true" property="techMobile" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techFax" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
        <html:text disabled="true" property="techFax" maxlength="50" size="40" styleClass="inputField"/>
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
			<html:radio disabled="true" property="isSalesSupport" value="Y" onclick="javascript: is24X7SalesSupport('Yes')"/>
            	Yes
              	&nbsp;
          	<html:radio disabled="true" property="isSalesSupport" value="N" onclick="javascript: is24X7SalesSupport('No')"/>
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
        <html:text disabled="true" property="salesEmail" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td> 
        &nbsp;
    </td>
</tr>	
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techFirstName" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text disabled="true" property="salesFirstName" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techLastName" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text disabled="true" property="salesLastName" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.title" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text disabled="true" property="salesTitle" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techPhone" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
        <span class="requiredText">*</span></strong><br/>
        <html:text disabled="true" property="salesPhone" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techMobileNumber" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
        <html:text disabled="true" property="salesMobile" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <strong><bean:message key="AllianceRegistrationForm.techFax" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
        <html:text disabled="true" property="salesFax" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
</table>

 <DIV>&nbsp;</DIV>

    <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
            <td align="right">
                <logic:equal name="JMAAllianceCompleteRegistrationForm" property="showAcceptRejectButton" value="Y">
                <div class="redBtn" style="float:right;margin-left:10px" id="Accept" title="Accept">
                    <div>
                        <div>
                            <div onClick="window.location='/aims/allianceStatus.do?task=changeStatus&alliance_id=<%=alliance_id%>&vzwStatus=A'">Accept</div>
                        </div>
                    </div>
                </div>
                </logic:equal>
                <logic:equal name="JMAAllianceCompleteRegistrationForm" property="showAcceptRejectButton" value="Y">
                <div class="blackBtn" style="float:right;margin-left:10px" id="Reject" title="Reject">
                    <div>
                        <div>
                            <div onClick="window.location='/aims/allianceStatus.do?task=changeStatus&alliance_id=<%=alliance_id%>&vzwStatus=R'">Reject</div>
                        </div>
                    </div>
                </div>
                </logic:equal>
            </td>
        </tr>
    </table>
    

</div>

</html:form>
<script>

    //Calling on page load function
    onPageLoad();
</script>