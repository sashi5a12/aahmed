<%@ page language="java" %>

<%@ page
        import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.accounts.* " %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/error.jsp" %>

<bean:parameter id="account_id" name="accountId" value="0"/>

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
<H1>User Account</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th><strong>Email Address:</strong></th>
        <th>&nbsp;</th>
    </tr>
    <tr>
        <td align="left">
            <html:text disabled="true" property="emailAddress" size="40" styleClass="inputField"/>
        </td>
        <td align="left">
            &nbsp;            
        </td>
    </tr>
    <tr>
        <td><strong>First Name:</strong></td>
        <td><strong>Last Name:</strong></td>
    </tr>
    <tr>
        <td align="left">
            <html:text disabled="true" property="firstName" size="40" styleClass="inputField"/>
        </td>
        <td align="left">
            <html:text disabled="true" property="lastName" size="40" styleClass="inputField"/>
        </td>
    </tr>
    <tr>
        <td>
            <strong>
                <bean:message key="AccountForm.title"
                              bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
            </strong>
        </td>
        <td>
            <strong>
                <bean:message key="AccountForm.phone"
                              bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
            </strong>
        </td>
    </tr>
    <tr>
        <td>
            <html:text disabled="true" property="title" size="40" styleClass="inputField"/>
        </td>
        <td>
            <html:text disabled="true" property="phone" size="40" styleClass="inputField"/>
        </td>
    </tr>
    <tr>
        <td>
            <strong>
                <bean:message key="AccountForm.mobile"
                              bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
            </strong>
        </td>
        <td>
            <strong>
                <bean:message key="AccountForm.fax"
                              bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
            </strong>
        </td>
    </tr>
    <tr>
        <td>
            <html:text disabled="true" property="mobile" size="40" styleClass="inputField"/>
        </td>
        <td>
            <html:text disabled="true" property="fax" size="40" styleClass="inputField"/>
        </td>
    </tr>
    <tr>
        <td><strong>Account Status :</strong></td>
        <td></td>
    </tr>
    <tr>
        <td align="left">
            <logic:match name="taskForThisPage" scope="page" value="create">
                <html:select disabled="true" property="userAccountStatus" size="1" value="">
                    <html:option value="ACTIVE">Active</html:option>
                    <html:option value="ONHOLD">OnHold</html:option>
                    <html:option value="DELETED">Deleted</html:option>
                </html:select>
            </logic:match>
            <logic:match name="taskForThisPage" scope="page" value="edit">
                <bean:define id="accStatus" type="java.lang.String" name="AccountVZWForm"
                             property="userAccountStatus"/>
                <html:select disabled="true" property="userAccountStatus" size="1" value="<%=accStatus%>">
                    <html:option value="ACTIVE">Active</html:option>
                    <html:option value="ONHOLD">OnHold</html:option>
                    <html:option value="DELETED">Deleted</html:option>
                </html:select>
            </logic:match>
        </td>
    </tr>
</table>
</DIV>


<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Roles</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th align="right">
            <strong>Available Roles</strong>
            <br/>
            <html:select property="availableRoles" size="10" style="width:250px" multiple="true">
                <logic:iterate id="role" name="AimsAvailableRoles"
                               type="com.netpace.aims.model.security.AimsSysRole">
                    <html:option value="<%=role.getRoleId().toString()%>">
                        <bean:write name="role" property="roleName"/>
                    </html:option>
                </logic:iterate>
            </html:select>
        </th>
        <th align="center" valign="middle">
            &nbsp;
        </th>
        <th align="left">
            <strong>Selected Roles</strong>
            <br/>
            <html:select property="selectedRoles" size="10" style="width:250px" multiple="true">
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
</table>
</DIV>

<table width="100%">
    <tr>
        <td align="right" valign="bottom">
             <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Save" title="Save">
                    <div>
                        <div>
                            <div onclick="window.location='/aims/accountsSetup.do?task=editForm&accountId=<bean:write name="account_id" />'">Edit</div>
                        </div>
                    </div>
            </div>            
        </td>
    </tr>
</table>

</div>
</div>
</html:form>
