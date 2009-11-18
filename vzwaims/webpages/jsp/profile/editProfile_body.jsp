<%@ page language="java" %>

<%@ page
        import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*,org.apache.struts.validator.DynaValidatorForm" %>

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

        /*if (objFrm.password.value.replace(regEx, "").length <= 0)
        {
            errors = errors + "Please Enter Password.\n"
            if (errObject == null)
                errObject = objFrm.password;
        }

        if (objFrm.password.value != objFrm.cpassword.value)
        {
            errors = errors + "Password does not match the confirm password.\n"
            if (errObject == null)
                errObject = objFrm.password;
        }*/

        if (objFrm.firstName.value.replace(regEx, "").length <= 0)
        {
            errors = errors + "Please Enter First Name.\n"
            if (errObject == null)
                errObject = objFrm.firstName;
        }

        if (objFrm.lastName.value.replace(regEx, "").length <= 0)
        {
            errors = errors + "Please Enter Last Name.\n"
            if (errObject == null)
                errObject = objFrm.lastName;
        }

        if (objFrm.title.value.replace(regEx, "").length <= 0)
        {
            errors = errors + "Please Enter Title.\n"
            if (errObject == null)
                errObject = objFrm.title;
        }

        if (objFrm.motherMaidenName.value.replace(regEx, "").length <= 0)
        {
            errors = errors + "Please Enter Mother's Maiden Name.\n"
            if (errObject == null)
                errObject = objFrm.motherMaidenName;
        }

        if (objFrm.phone.value.replace(regEx, "").length <= 0)
        {
            errors = errors + "Please Enter Phone Number.\n"
            if (errObject == null)
                errObject = objFrm.phone;
        }

        if (objFrm.fax.value.replace(regEx, "").length <= 0)
        {
            errors = errors + "Please Enter Fax Number.\n"
            if (errObject == null)
                errObject = objFrm.fax;
        }

        if (objFrm.mobile.value.replace(regEx, "").length <= 0)
        {
            errors = errors + "Please Enter Mobile Number.\n"
            if (errObject == null)
                errObject = objFrm.mobile;
        }

        if (errors.length != 0)
        {
            alert(errors);
            errObject.focus();
            return false;
        }
        else return true;
    }

    //-->
</script>

<%@ include file="/common/error.jsp" %>


<div id="contentBox" style="float:left">
<div class="homeColumnTab lBox">

<aims:getEditProfileTab selectionName="UserInfo"/>
<div> &nbsp; </div>


<html:form action="/editprofilesubmit" method="post" onsubmit="return validateFrm(this);">
<html:hidden name="EditProfileForm" property="notificationType" value="E"/>

<DIV class="headLeftCurveblk"></DIV>
<H1>
    <bean:message key="AllianceProfileForm.contactInformation"
                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
</H1>
<DIV class="headRightCurveblk"></DIV>


<DIV class="contentbox">

<table width="100%" border="0" cellspacing="" cellpadding="5" class="GridGradient">
<tr>
    <th width="50%">
        <strong>Email Address</strong><br/>
        <bean:write name="EditProfileForm" property="userName" scope="request"/>
    </th>
    <th width="50%">&nbsp;</th>
</tr>
<tr>
    <td>
        <strong>First Name</strong><br/>
        <bean:write name="EditProfileForm" property="firstName" scope="request"/>
    </td>
    <td>
        <strong>Last Name</strong><br/>
        <bean:write name="EditProfileForm" property="lastName" scope="request"/>
    </td>
</tr>

<tr>
    <td>
        <strong><bean:message key="AllianceProfileForm.title" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
        </strong><br/>
        <bean:write name="EditProfileForm" property="title" scope="request"/>
    </td>
    <td>
    	<strong>Phone</strong><br/>
    	<bean:write name="EditProfileForm" property="phone" scope="request"/>
    </td>
</tr>

<tr>
    <td>
        <strong>Mobile Number</strong><br/>
        <bean:write name="EditProfileForm" property="mobile" scope="request"/>        
    </td>
    <td>
    	&nbsp;
    </td>
</tr>
<tr>
    <td colspan="2"><strong> Personalize Your Experience </strong></td>
</tr>
<tr>
    <td colspan="2">
        Page Length (Defines the number of records displayed in a list [example "List of Applications"])
        <br/>
        <html:select name="EditProfileForm" property="rowsLength" size="1" styleClass="selectField">
            <html:option value="5">5</html:option>
            <html:option value="10">10</html:option>
            <html:option value="15">15</html:option>
            <html:option value="20">20</html:option>
            <html:option value="25">25</html:option>
            <html:option value="30">30</html:option>
            <html:option value="35">35</html:option>
            <html:option value="40">40</html:option>
        </html:select>
    </td>
</tr>
<tr>
    <td colspan="2">
        <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="filter "
             title="Submit">
            <div>
                <div>
                    <div onclick="document.forms[0].submit()">Submit</div>
                </div>
            </div>
        </div>
    </td>
</tr>
</table>



</div>

</html:form>


</div>
</div>

