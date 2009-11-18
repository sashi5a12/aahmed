<%@ page import="com.netpace.aims.util.AimsConstants"%>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<script language="javascript">
<!--
function validateFrm(objFrm)
{
    var errors = new String();
    var errObject = null;

    regEx = / {1,}$/;

    if (trim(objFrm.companyName.value).length < 2)
    {
        errors = errors + "Company Name must be at least 2 characters.\n";
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
        errors = errors + "Please Enter a valid Web Site URL in http:// or https:// format.\n";
        if (errObject == null)
            errObject = objFrm.webSiteUrl;
    }

    
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
    if (objFrm.city.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='AllianceRegistrationForm.city' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.city;
    }

    if (objFrm.country.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='AllianceRegistrationForm.country' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.country;
    }

    
    
    if (objFrm.employeesRange.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='AllianceRegistrationForm.numberOfEmployees' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.numberOfEmployees;
    }

    

    

    

    if (objFrm.techEmail.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='error.AllianceRegistrationForm.techEmail' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.techEmail;
    }
    else if (!validateEmail(objFrm.techEmail.value)) {
        errors = errors + "Please Enter a valid <bean:message key='error.AllianceRegistrationForm.techEmail' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n";
        if (errObject == null)
            errObject = objFrm.techEmail;
    }

    

 	if (objFrm.techFirstName.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='error.AllianceRegistrationForm.techFirstName' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.techFirstName;
    }

    if (objFrm.techLastName.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='error.AllianceRegistrationForm.techLastName' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.techLastName;
    }

    if (objFrm.techTitle.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='error.AllianceRegistrationForm.techTitle' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.techTitle;
    }

    if (objFrm.techPhone.value.replace(regEx, "").length <= 0)
    {
        errors = errors + "Please Enter <bean:message key='error.AllianceRegistrationForm.techPhone' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>.\n"
        if (errObject == null)
            errObject = objFrm.techPhone;
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

function truncateLocalTextAreas()
{

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

function validateCheckBoxes(checkBoxes) {
    var validated = false;
    if (checkBoxes && checkBoxes.length) {
        for (var cbIndex = 0; cbIndex < checkBoxes.length; cbIndex++) {
            if (checkBoxes[cbIndex].checked) {
                validated = true;
                break;
            }
        }
    }
    return validated;
}

function validateEmail(emailString) {
    var regEx = /^\w[\w\d\-\+]*(\.[\w\d\-\+]+)*@\w[\w\d\-]+(\.[\w\d\-]+)*\.[a-z]{2,7}$/i ;
    return regEx.test(emailString);
}

function validateURL(urlString) {
    <%--var regEx = new RegExp("^((http|https|ftp)://)?(((www\\.)?[^ ]+\\.[com|org|net|edu|gov|us])|([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+))([^ ]+)?$");--%>
    var regEx = new RegExp("^http(s?)://(((www\\.)?[^ ]+\\.[com|org|net|edu|gov|us])|([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+))([^ ]+)?$");    
    return regEx.test(urlString);
}

function validateYear(yearValue) {
	var d = new Date();	
    var regEx = new RegExp("<%=AimsConstants.PATTERN_4_DIGIT_YEAR%>");	
	return (regEx.test(yearValue) && (parseInt(yearValue) <= d.getFullYear()));
}

function validateFile(fileString, fileType) {
    var validated = false;
    var ext = fileString.substring(fileString.lastIndexOf(".") + 1, fileString.length).toLowerCase();
    if (fileType == "Power Point") {
        if (ext == "ppt") {
            validated = true;
        }
    }
    else if (fileType == "Image") {
        if (ext == "jpg" || ext == "jpeg" || ext == "bmp" || ext == "gif") {
            validated = true;
        }
    }
    return validated;
}

//-->

var websiteURLHint = "<bean:message key="AllianceInformation.webSiteUrl.Hint" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>";

</script>


<%@ include file="/common/error.jsp" %>


<DIV class="homeColumnTab xlBox">

<DIV class="contentbox">
<table width="100%" border="0" cellspacing="0" cellpadding="10">
    <tr>
        <td>
            <br/><strong>Please Read This First:</strong><br/><br/>

            This registration screen is solely for the purpose of establishing or
            continuing a dialogue with Verizon Wireless about a business
            relationship for the supply of some product or service to or through
            Verizon Wireless. Please do not register here if you have some other
            purpose, unless you have been instructed to do so by a Verizon Wireless
            representative. For example, if you are a Verizon Wireless cell-phone
            customer with billing or product inquiries, please do not register here;
            instead, you should visit <a href="http://www.verizonwireless.com/">
            http://www.verizonwireless.com</a> and register at My Account.<br/><br/>

            When you do register here, if there is not enough basic information in
            the registration to determine who you are, how to reach you by email and
            phone, what organization or company you are with, and the basic purpose
            for your registration, then your registration will be deleted.
            Should you have any questions regarding the registration process,
            please direct them to <a href="mailto:developerrelations@verizonwireless.com">
            developerrelations@verizonwireless.com.</a>
            <br/><br/>
            <br/>
            <b>For Enterprise JMA or BOBO registration, please <a href="/aims/jmaAllianceRegistrationSetup.do">click here</a><b>
            <br/><br/>
        </td>
    </tr>
</table>
</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Basic Information</H1>
<DIV class="headRightCurveblk"></DIV>

<html:form action="/allianceRegistrationUpdate" enctype="multipart/form-data" focus="companyName"
           onsubmit="return validateFrm(this);">
<html:hidden property="task" value="create"/>

<html:hidden property="companyPresentationFileName"/>
<html:hidden property="companyLogoFileName"/>
<html:hidden property="companyPresentationTempFileId"/>
<html:hidden property="companyLogoTempFileId"/>
<html:hidden property="vzdnManagerRoles"/>
<html:hidden property="loginId"/>
<html:hidden property="firstName"/>
<html:hidden property="lastName"/>
<html:hidden property="title"/>
<html:hidden property="phone"/>
<html:hidden property="fax"/>
<html:hidden property="mobile"/>



<DIV class="contentbox">
<table width="100%" border="0" cellspacing="0" cellpadding="0" bordercolor="black">
    <tr>
        <td width="50%" style="vertical-align:top;">
            <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
                <tr>
                    <th class="noPad">
                        <strong>
                            <bean:message key="AllianceRegistrationForm.companyName"
                                          bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                            <span class="requiredText">*</span></strong> <br/>
                        <html:text styleClass="inputField" property="companyName" size="40" maxlength="50"/>
                    </th>
                </tr>
                <tr>
                    <td width="50%">
                        <strong>
                            <bean:message key="AllianceRegistrationForm.webSiteUrl"
                                          bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                        <span class="requiredText">*</span></strong>&nbsp;
                        <a onClick="openZonHelpWindow(event, websiteURLHint); return false;" href="#">[?]</a><br/>
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

            </table>
        </td>
        
        <td style="vertical-align:top;">

            <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
                <tr>
                    <th>
                        <strong>
                            <bean:message key="AllianceRegistrationForm.stateProvince"
                                          bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                        <span class="requiredText">*</span></strong><br/>
                        <html:text styleClass="inputField" property="stateProvince" size="10" maxlength="100"/>
                    </th>
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
                <tr>
                    <td style="vertical-align:top">
                        <strong>
                            <bean:message key="AllianceRegistrationForm.numberOfEmployees"
                                          bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                        <span class="requiredText">*</span></strong><br/>
                        <html:select property="employeesRange" styleClass="selectField">
                            <html:options collection="numberOfEmployeesList" property="value" labelProperty="value"></html:options>
                        </html:select>
                    </td>
                </tr>
            </table>
        </td>

    </tr>
    <tr><td colspan="2">&nbsp;</td></tr>
    <tr>
        <td colspan="2">
        <DIV class="headLeftCurveblk"></DIV>
        <H1>Developer 24x7 Technical Contact Information</H1>
        <DIV class="headRightCurveblk"></DIV>
        </td>
    </tr>
    <%-- start 24x7 tech contact --%>
    <tr>
        <td colspan="2">
        <table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th class="noPad" width="50%">
                        <strong><bean:message key="AllianceRegistrationForm.techEmail" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                        <span class="requiredText">*</span></strong><br/>
                        <html:text property="techEmail" maxlength="50" size="40" styleClass="inputField"/>
                    </th>
                    <th>&nbsp;</th>
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
        </td>
    </tr>
    <%-- end 24x7 tech contact --%>
    
    <tr><td colspan="2">&nbsp;</td></tr>
    
    <%-- end 24x7 tech contact --%>
    
    <tr>
        <td colspan="2">
        <DIV class="headLeftCurveblk"></DIV>
        <H1>Remittance Information</H1>
        <DIV class="headRightCurveblk"></DIV>
        </td>
    </tr>	   
    <tr>
        <td colspan="2">
        <table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
			<tr>
        		<th class="noPad" width="100%" colspan="2">
            		Please note: If you are submitting a V CAST App, please fill out the fields within the Remittance Information section to ensure proper payment.
        		</th>
    		</tr>        
                <tr>
                    <td width="50%">
                        <strong><bean:message key="AllianceRegistrationForm.remitTo" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                        </strong><br/>
                        <html:text property="remitTo" maxlength="50" size="40" styleClass="inputField"/>
                    </td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <strong><bean:message key="AllianceRegistrationForm.Address1" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                        </strong><br/>
                        <html:text property="remitAddress1" maxlength="200" size="40" styleClass="inputField"/>
                    </td>
					<td>
                        <strong><bean:message key="AllianceRegistrationForm.address2" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                        </strong><br/>
                        <html:text property="remitAddress2" maxlength="200" size="40" styleClass="inputField"/>
                    </td>

                </tr>
                <tr>
                    <td>
                        <strong><bean:message key="AllianceRegistrationForm.City" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
                        <html:text property="remitCity" maxlength="50" size="40" styleClass="inputField"/>
                    </td>
                    <td>
                        <strong><bean:message key="AllianceRegistrationForm.State" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
                        <html:text property="remitState" maxlength="50" size="40" styleClass="inputField"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <strong><bean:message key="AllianceRegistrationForm.PostalCode" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
                        <html:text property="remitPostalCode" maxlength="9" size="40" styleClass="inputField"/>
                    </td>
                    <td>
                        <strong><bean:message key="AllianceRegistrationForm.Country" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
                        <html:select property="remitCountry" styleClass="selectField">
                            <html:options collection="countryList" property="countryName" labelProperty="countryName"></html:options>
                        </html:select>                        
                    </td>
                </tr>
                <%-- 
                //Commented for Bug#7845 Fix.
                <tr>
                    <td><a class="a" href="/aims/jsp/public/TermsNCondition.jsp" target="_blank">
                        <bean:message key="AllianceRegistrationForm.termsNCondition" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                    </a><span class="requiredText">*</span></td>
                    <td>&nbsp;</td>
                </tr>
                 --%>
                
                <tr>
                    <td>
                        &nbsp;
                    </td>
                    <td>
                        <div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Submit" title="Submit">
                            <div><div><div onclick="submitForm();return false;">Submit</div></div></div>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    
    
</table>
</DIV>



</div>

</html:form>
<script>
    function submitForm() {
        if (document.AllianceRegistrationForm && validateFrm(document.AllianceRegistrationForm)) {
            document.AllianceRegistrationForm.submit();
        }
    }
</script>