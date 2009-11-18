<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.accounts.* " %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/error.jsp" %>

<bean:parameter id="taskForThisPage" name="task" value="Nopes"/>


<html:form action="/accountsVZWEdit">

<html:hidden property="userId"/>
<logic:match name="taskForThisPage" scope="page" value="create">
    <html:hidden property="task" value="create"/>
</logic:match>
<logic:match name="taskForThisPage" scope="page" value="edit">
    <html:hidden property="task" value="edit"/>
</logic:match>


<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<DIV class="headLeftCurveblk"></DIV>
<H1>User Information</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
<tr>
    <th>
        <strong><bean:message key="AccountForm.email" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        <span class="requiredText">*</span></strong>
    <br/>
        <html:hidden property="username"/>
        <logic:match name="taskForThisPage" scope="page" value="create">
            <html:text property="emailAddress" maxlength="50" size="40" styleClass="inputField"/>
        </logic:match>
        <logic:match name="taskForThisPage" scope="page" value="edit">
            <html:hidden property="emailAddress"/>
            <html:text disabled="true" property="emailAddress" maxlength="50" size="40" styleClass="inputField"/>
        </logic:match>
    </th>
    <th>
        <logic:match name="taskForThisPage" scope="page" value="create">
	        <strong><bean:message key="AccountForm.password" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
	        <span class="requiredText">*</span></strong>
        	<br/>
            <html:password property="userPassword" maxlength="50" size="40" styleClass="inputField"/>
        </logic:match>
        <logic:match name="taskForThisPage" scope="page" value="edit">
        	&nbsp;
        </logic:match>
    </th>
</tr>
<tr>
    <td>
        <strong><bean:message key="AccountForm.firstName" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        <span class="requiredText">*</span></strong>
    </td>
    <td>
        <strong><bean:message key="AccountForm.lastName" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        <span class="requiredText">*</span></strong>
    </td>
</tr>
<tr>
    <td>
        <html:text property="firstName" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <html:text property="lastName" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="AccountForm.title" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
            <span class="requiredText">*</span></strong>
    </td>
    <td>
        <strong><bean:message key="AccountForm.phone" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/></strong>
    </td>
</tr>
<tr>
    <td>
        <html:text property="title" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <html:text property="phone" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>

<tr>
    <td>
        <strong><bean:message key="AccountForm.mobile"
                            bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/></strong>
    </td>
    <td>
        <strong><bean:message key="AccountForm.fax"
                            bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/></strong>
    </td>
</tr>
<tr>
    <td>
        <html:text property="mobile" maxlength="50" size="40" styleClass="inputField"/>
    </td>
    <td>
        <html:text property="fax" maxlength="50" size="40" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="AccountForm.accountStatus" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        <span class="requiredText">*</span></strong></td>
    <td></td>
</tr>
<tr>
    <td>
        <logic:match name="taskForThisPage" scope="page" value="create">
            <html:select property="userAccountStatus" size="1" value="" styleClass="selectField">
                <html:option value="ACTIVE">Active</html:option>
                <html:option value="ONHOLD">OnHold</html:option>
                <html:option value="DELETED">Deleted</html:option>
            </html:select>
        </logic:match>
        <logic:match name="taskForThisPage" scope="page" value="edit">
            <bean:define id="accStatus" type="java.lang.String" name="AccountVZWForm" property="userAccountStatus"/>
            <html:select property="userAccountStatus" size="1" value="<%=accStatus%>" styleClass="inputField">
                <html:option value="ACTIVE">Active</html:option>
                <html:option value="ONHOLD">OnHold</html:option>
                <html:option value="DELETED">Deleted</html:option>
            </html:select>
        </logic:match>
    </td>
    <td></td>
</tr>
</table>
</DIV>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Roles</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient" align="left">
    <tr>
        <th style="width:200px">
            <strong>Available Roles</strong> <br/>
            <html:select property="availableRoles" size="10" style="width:250px" multiple="true" styleClass="selectField">
                <logic:iterate id="role" name="AimsAvailableRoles" type="com.netpace.aims.model.security.AimsSysRole">
                    <html:option value="<%=role.getRoleId().toString()%>">
                        <bean:write name="role" property="roleName"/>
                    </html:option>
                </logic:iterate>
            </html:select>
        </th>
        <th width=50 align="center" style="vertical-align:middle;">
            <table cellpadding="0" cellspacing="0" border="0" height="100">
                <tr>
                    <td align="center" valign="bottom">
                        <img src="images/greyRndArrow.gif" border="0" alt="Add"
                             onClick="copyToList('availableRoles', 'selectedRoles')"/>
                    </td>
                </tr>
                <tr>
                    <td align="center" valign="middle">
                        <img src="images/greyRndArrowL.gif" border="0" alt="Remove"
                             onClick="copyToList('selectedRoles',	'availableRoles')"/>
                    </td>
                </tr>
            </table>
        </th>
        <th align="left">
            <strong>Selected Roles</strong> <br/>
            <html:select property="selectedRoles" size="10" style="width:250px" multiple="true" styleClass="selectField" >
                <logic:match name="taskForThisPage" scope="page" value="edit">
                    <logic:iterate id="assRole" name="AimsAssignedRoles"
                                   type="com.netpace.aims.model.security.AimsSysRole">
                        <html:option value="<%=assRole.getRoleId().toString()%>">
                            <bean:write name="assRole" property="roleName"/>
                        </html:option>
                    </logic:iterate>
                </logic:match>
            </html:select>
        </th>
    </tr>
    <tr>
        <td colspan="3">
            <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Save" title="Save">
                <div>
                    <div>
                        <div onclick="select_all(document.forms[0].selectedRoles); document.forms[0].submit();">Save</div>
                    </div>
                </div>
            </div>
            <logic:match name="taskForThisPage" scope="page" value="edit">
                <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="btnChangePassword" title="Change Password">
                    <div>
                        <div>
                            <div onclick="window.location='/aims/accountsChangePasswordSetup.do?accountId=<bean:write name="AccountVZWForm" property="userId"/>'">Change Password</div>
                        </div>
                    </div>
                </div>
            </logic:match>
        </td>
    </tr>
</table>




</DIV>


</div>
</div>
</html:form>
