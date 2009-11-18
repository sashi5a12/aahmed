<%@ page import="com.netpace.aims.util.AimsConstants"%>
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

    regEx = / {1,}$/;

    if (objFrm.companyName.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='AllianceRegistrationForm.companyName' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n";
        if (errObject == null)
            errObject = objFrm.companyName;
    }

    if (objFrm.webSiteUrl.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='AllianceRegistrationForm.webSiteUrl' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.webSiteUrl;
    }
    else if (!validateURL(objFrm.webSiteUrl.value)) {
        errors = errors + "Please Enter URL pattern for <bean:message key='AllianceRegistrationForm.webSiteUrl' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n";
        if (errObject == null)
            errObject = objFrm.webSiteUrl;
    }







    /*else if (!isInteger(objFrm.phone.value))
    {
        errors = errors + "Please Enter Valid <bean:message key='AllianceRegistrationForm.phone' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.phone;
    }*/
    if (objFrm.yearFounded.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='AllianceRegistrationForm.yearFounded' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.yearFounded;
    }
    else if(!validateYear(objFrm.yearFounded.value))
    {
        errors = errors + "Please Enter Valid <bean:message key='AllianceRegistrationForm.yearFounded' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.yearFounded;
    }
    

    if (objFrm.companyAddress.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='AllianceRegistrationForm.companyAddress' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.companyAddress;
    }
    
    if (objFrm.city.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='AllianceRegistrationForm.city' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.city;
    }
    
    if (objFrm.stateProvince.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='AllianceRegistrationForm.stateProvince' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.stateProvince;
    }

    if (objFrm.zipCode.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='AllianceRegistrationForm.zipCode' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.zipCode;
    }
/*    else if (!isInteger(objFrm.zipCode.value))
    {
        errors = errors + "Please Enter Valid <bean:message key='AllianceRegistrationForm.zipCode' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.zipCode;
    }
*/    
    if (objFrm.country.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='AllianceRegistrationForm.country' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.country;
    }
    /*
    Bug Fix: 7845
    This field has been removed.
    if (objFrm.isAccepted.checked)
        termsAccepted = true;

    if (!termsAccepted)
    {
        errors = errors + "You cannot register until you accept the Terms and Conditions.\n"
        if (errObject == null)
            errObject = objFrm.isAccepted;
    }
*/
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

function validateEmail(emailString) {
    var regEx = /^\w[\w\d\-\+]*(\.[\w\d\-\+]+)*@\w[\w\d\-]+(\.[\w\d\-]+)*\.[a-z]{2,7}$/i ;
    return regEx.test(emailString);
}

function validateURL(urlString) {
    var regEx = new RegExp("^((http|https|ftp)://)?(((www\\.)?[^ ]+\\.[com|org|net|edu|gov|us])|([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+))([^ ]+)?$");
    return regEx.test(urlString);
}

function validateYear(yearValue) {
	var d = new Date();	
    var regEx = new RegExp("<%=AimsConstants.PATTERN_4_DIGIT_YEAR%>");
    return (regEx.test(yearValue) && (parseInt(yearValue) <= d.getFullYear()));    
}

function isInteger(s)
{   var i;
    for (i = 0; i < s.length; i++)
    {   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

</script>

<%@ include file="/common/error.jsp" %>

<div class="homeColumnTab xlBox">

<div class="contentbox">
	<table width="100%" border="0" cellspacing="0" cellpadding="10">
	    <tr>
	        <td>
	            <br/><strong>Please Read This First:</strong><br/><br/>
	
	             Thank you for your interest in working with JMA program. <br/>
	             
	             Please apply only if you are owner or authorized representative.
	            <br/><br/>
	          
	        </td>
	    </tr>
	</table>
</div>


<DIV class="headLeftCurveblk"></DIV>
<H1>Basic Information</H1>
<DIV class="headRightCurveblk"></DIV>


<html:form action="/jmaAllianceRegistrationUpdate" enctype="multipart/form-data" focus="companyName" onsubmit="return validateFrm(this);">

<html:hidden property="userType"/>
<html:hidden property="userRoles"/>
<html:hidden property="firstName"/>
<html:hidden property="lastName"/>
<html:hidden property="email"/>
<html:hidden property="title"/>

<html:hidden property="phone"/>
<html:hidden property="fax"/>
<html:hidden property="mobile"/>


<html:hidden property="task" value="create"/>

<DIV class="contentbox">
	<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">   				
    <th class="noPad" width="50%">
        <strong>
            <bean:message key="AllianceRegistrationForm.companyName" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
            <span class="requiredText">*</span></strong> <br/>
        <html:text styleClass="inputField" property="companyName" size="40" maxlength="250"/>
    </th>
</tr>
	<tr>
	    <td>
	        <strong>
	            <bean:message key="AllianceRegistrationForm.webSiteUrl"  bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
	       <span class="requiredText">*</span></strong><br/>
        <html:text styleClass="inputField" property="webSiteUrl" size="40" maxlength="250"/>
	    </td>
    </tr>
	<tr>
	    <td>
	        <strong>
	            <bean:message key="AllianceRegistrationForm.yearFounded"
	                          bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
	        <span class="requiredText">*</span></strong><br/>
	        <html:text styleClass="inputField" property="yearFounded" size="4" maxlength="4"/>
	    </td>
    </tr>
    
    <tr>
	    <td>
	        <strong>
	            <bean:message key="AllianceRegistrationForm.companyAddress"
	                          bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
	        <span class="requiredText">*</span></strong><br/>
	        <html:text styleClass="inputField" property="companyAddress" size="40" maxlength="80"/>
	    </td>
	    <td>
	        &nbsp;
	    </td>
    </tr>
    
    <tr>
	    <td>
	        <strong>
	            <bean:message key="AllianceRegistrationForm.city"
	                          bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
	        <span class="requiredText">*</span></strong><br/>
	        <html:text styleClass="inputField" property="city" size="40" maxlength="80"/>
	    </td>
	</tr>

	<tr>
	<td>
	        <strong>
	            <bean:message key="AllianceRegistrationForm.stateProvince"
	                          bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
	        <span class="requiredText">*</span></strong><br/>
	        <html:text styleClass="inputField" property="stateProvince" size="10"/>
	    </td>
	</tr>	
	
	<tr>
	    <td>
	        <strong>
	            <bean:message key="AllianceRegistrationForm.zipCode"
	                          bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
	        <span class="requiredText">*</span></strong><br/>
	        <html:text styleClass="inputField" property="zipCode" size="11" maxlength="10"/>
	    </td>
	</tr>
	
	<tr>
	<td>
	        <strong>
	            <bean:message key="AllianceRegistrationForm.country"
	                          bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
	        <span class="requiredText">*</span></strong><br/>
	        <html:select property="country" styleClass="selectField">
	            <html:options collection="countryList" property="countryName" labelProperty="countryName"></html:options>
	        </html:select>
	    </td>
	</tr>
	<%-- 
	//Commented for Bug#7845 Fix.
	<tr>
	    <td><br/><a class="a" href="/aims/jsp/public/TermsNCondition.jsp" target="_blank">
	        <bean:message key="AllianceRegistrationForm.termsNCondition" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
	    </a><span class="requiredText">*</span></td>
	    <td>&nbsp;</td>
	</tr>
	
	
	<tr>
	    <td>
	        <html:checkbox property="isAccepted"/><strong>I Accept</strong>
		    </td>
		</tr>
		--%>
		
		<tr>
	    <td>
		    </td>
		    <td>
		        <div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Submit" title="Submit">
		            <div><div><div onclick="submitForm();return false;">Submit</div></div></div>
		        </div>
		    </td>
		</tr>
		
			    
		 
 	</table>
</DIV>

</html:form>

</div>

<script>
    function submitForm() {
        if(validateFrm(document.JMAAllianceRegistrationForm))
       		document.JMAAllianceRegistrationForm.submit();
        else
        	return false;
    }
</script>