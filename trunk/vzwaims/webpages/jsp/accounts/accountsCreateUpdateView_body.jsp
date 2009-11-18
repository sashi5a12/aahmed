<%@ page import="com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.accounts.* "%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ include file="/common/error.jsp" %>

<bean:parameter id="taskForThisPage" name="task" value="Nopes"/>

<DIV class="homeColumnTab lBox floatL">
<DIV class="headLeftCurveblk"></DIV>
<H1>User Account</H1>

<DIV class="headRightCurveblk"></DIV>

<html:form action="/accountsEdit">

<html:hidden property="userId"/>
<logic:match name="taskForThisPage" scope="page" value="create">
    <html:hidden property="task" value="create"/>
</logic:match>
<logic:match name="taskForThisPage" scope="page" value="edit">
    <html:hidden property="task" value="edit"/>
</logic:match>
<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" border="0" class="GridGradient">
        <tr>
            <th width="50%">
                <strong>
                    <bean:message key="AccountForm.email"
                                  bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                </strong> <br/>
                <bean:write name="AccountForm" property="emailAddress"/>
            </th>
            <th width="50%">
                &nbsp;                
            </th>
        </tr>

        <tr>
            <td>
                <strong>
                    <bean:message key="AccountForm.firstName"
                                  bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                </strong> <br/>
                <bean:write name="AccountForm" property="firstName"/>
            </td>
            <td>
                <strong>
                    <bean:message key="AccountForm.lastName"
                                  bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                </strong> <br/>
                <bean:write name="AccountForm" property="lastName"/>
            </td>
        </tr>

        <tr>
            <td>
                <strong>
                    <bean:message key="AccountForm.title"
                                  bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                </strong> <br/>
                <bean:write name="AccountForm" property="title"/>
            </td>
            <td>
                <strong>
                    <bean:message key="AccountForm.phone"
                                  bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                </strong> <br/>
                <bean:write name="AccountForm" property="phone"/>
            </td>
        </tr>

        <tr>
            <td>
                <strong>
                    <bean:message key="AccountForm.mobile"
                                  bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                </strong> <br/>
                <bean:write name="AccountForm" property="mobile"/>
            </td>
            <td>
                <strong>
                    <bean:message key="AccountForm.fax"
                                  bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                </strong> <br/>
                <bean:write name="AccountForm" property="fax"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <strong>
                    <bean:message key="AccountForm.accountStatus" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                </strong> <br/>
                <logic:match name="taskForThisPage" scope="page" value="create">
                    <html:select disabled="true" property="userAccountStatus" size="1" value="" styleClass="inputField">
                        <html:option value="ACTIVE">Active</html:option>
                        <html:option value="ONHOLD">OnHold</html:option>
                        <html:option value="DELETED">Deleted</html:option>
                    </html:select>
                </logic:match>
                <logic:match name="taskForThisPage" scope="page" value="edit">
                    <bean:define id="accStatus" type="java.lang.String" name="AccountForm"
                                 property="userAccountStatus"/>
                    <html:select disabled="true" property="userAccountStatus" size="1" value="<%=accStatus%>"
                                 styleClass="inputField">
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
<H1>Role Assigned</H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">

    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th colspan="2">
                <strong>Role</strong>
            </th>
        </tr>
        <tr>
            <td colspan="2">
                <html:select disabled="true" property="aimsRoleId" size="1" onchange="displayRolesInformation()">
                    <html:option value="0">
                        <bean:message key="ManageApplicationForm.label.selectOne"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    </html:option>
                    <logic:iterate id="formRoles" name="AccountForm" property="allAllianceRoles"
                                   type="com.netpace.aims.model.security.AimsSysRole" scope="request">
                        <html:option value="<%=formRoles.getRoleId().toString()%>"><%=formRoles.getRoleName()%>
                        </html:option>
                    </logic:iterate>
                </html:select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <strong>Role Description</strong>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <textarea name="roleDescription" rows="6" cols="65" readonly="true">&nbsp</textarea>
            </td>
        </tr>
        <bean:parameter id="account_id" name="accountId" value="0"/>
        <tr>
            <td>
                <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Edit" title="Edit">
                    <div>
                        <div>
                            <div onclick="window.location='/aims/accountsSetup.do?task=editForm&accountId=<bean:write name="account_id" />'">Edit</div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</DIV>

</html:form>

</div>
<logic:notEmpty name="AccountForm" property="aimsRoleId">
    <bean:define id="accRoleId" type="java.lang.Long" name="AccountForm" property="aimsRoleId"/>
    <logic:iterate id="formRoles" name="AccountForm" property="allAllianceRoles"
                   type="com.netpace.aims.model.security.AimsSysRole" scope="request">
        <logic:equal name="formRoles" property="roleId" value="<%=accRoleId.toString()%>">
            <script language="javascript">
                document.forms[0].roleDescription.value = "<%=StringFuncs.crlfEscape(formRoles.getRoleDescription())%>";
            </script>
        </logic:equal>
    </logic:iterate>
</logic:notEmpty>