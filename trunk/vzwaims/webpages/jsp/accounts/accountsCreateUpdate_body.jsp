<%@ page language="java" %>

<%@ page import="com.netpace.aims.util.StringFuncs" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language="javascript">

    var rolesArray = new Array();

    function AimsSysRoles()
    {
        this.roleId = "";
        this.roleName = "";
        this.roleDescription = "";
    }

    <% int	index	=	0; %>

    <logic:iterate id="formRoles" name="AccountForm" property="allAllianceRoles" scope="request"
    type="com.netpace.aims.model.security.AimsSysRole">
    aimsRoles = new AimsSysRoles();
    aimsRoles.roleId = "<bean:write	name="formRoles"	property="roleId" />";
    aimsRoles.roleName = "<bean:write	name="formRoles"	property="roleName" />";
    aimsRoles.roleDescription = "<%=StringFuncs.crlfEscape(formRoles.getRoleDescription())%>";
    rolesArray[<%=index%>] = aimsRoles;
    <%index++;%>
    </logic:iterate>

    var supported = (window.Option) ? 1 : 0;

    function displayRolesInformation() {
        if (!supported) {
            alert("Feature	not	supported");
        }
        if ((document.forms[0].aimsRoleId.options[document.forms[0].aimsRoleId.selectedIndex].value) != "0")
        {
            for (var j = 0; j < rolesArray.length; j++)
            {
                if (rolesArray[j].roleId == document.forms[0].aimsRoleId.options[document.forms[0].aimsRoleId.selectedIndex].value)
                    document.forms[0].roleDescription.value = rolesArray[j].roleDescription;
            }
        }
        else
            document.forms[0].roleDescription.value = "";
    }
</script>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<div class="homeColumnTab lBox">

<div class="headLeftCurveblk"></DIV>
<H1>User Information<span class="requiredText">*</span></H1>

<div class="headRightCurveblk"></DIV>


<html:form action="/accountsEdit">
<div class="contentbox">
<bean:parameter id="taskForThisPage" name="task" value="Nopes"/>
<html:hidden property="userId"/>
<input type="hidden" name="alliance_id" value="<c:out value='${requestScope.alliance_id}'/>"/>
<input type="hidden" name="isPopup" value="<c:out value='${requestScope.isPopup}'/>"/>
<input type="hidden" name="cType" value="<c:out value='${requestScope.cType}'/>"/>
<input type="hidden" name="parentPageType" value="<c:out value='${requestScope.parentPageType}'/>"/>
<input type="hidden" name="parentPath" value="<c:out value='${requestScope.parentPath}'/>"/>
<logic:match name="taskForThisPage" scope="page" value="create">
    <html:hidden property="task" value="create"/>
</logic:match>
<logic:match name="taskForThisPage" scope="page" value="edit">
    <html:hidden property="task" value="edit"/>
</logic:match>

<table width="100%" border="0" cellpadding="5" cellspacing="0" class="GridGradient">
<tr>
    <th width="50%">
        <strong>
            <bean:message key="AccountForm.email"
                          bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        <span class="requiredText">*</span>:</strong><br/>
        <html:hidden property="username"/>
        <logic:match name="taskForThisPage" scope="page" value="create">
            <%--<html:text property="emailAddress" maxlength="50" size="40" styleClass="inputField"/>--%>
            <bean:write name="AccountForm" property="emailAddress"/>
            <html:hidden property="emailAddress"/>
        </logic:match>
        <logic:match name="taskForThisPage" scope="page" value="edit">
            <bean:write name="AccountForm" property="emailAddress"/>
            <html:hidden property="emailAddress"/>
        </logic:match>
    </th>
    <th width="50%">
    <logic:match name="taskForThisPage" scope="page" value="create">
        <strong>
            <bean:message key="AccountForm.password"
                          bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        <span class="requiredText">*</span>:</strong><br/>
        &nbsp;
        <html:hidden property="userPassword"/>
    </logic:match>
    <logic:match name="taskForThisPage" scope="page" value="edit">
        &nbsp;
    </logic:match>
    </th>
</tr>

<tr>
    <td>
        <strong>
            <bean:message key="AccountForm.firstName"
                          bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        <span class="requiredText">*</span>:</strong><br/>
        <bean:write name="AccountForm" property="firstName"/>
        <html:hidden property="firstName"/>
    </td>
    <td>
        <strong>
            <bean:message key="AccountForm.lastName"
                          bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        <span class="requiredText">*</span>:</strong><br/>
        <bean:write name="AccountForm" property="lastName"/>
        <html:hidden property="lastName"/>
    </td>
</tr>


<tr>
    <td>
        <strong>
            <bean:message key="AccountForm.title"
                          bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        <span class="requiredText">*</span>:</strong><br/>
        <bean:write name="AccountForm" property="title"/>
        <html:hidden property="title"/>
    </td>
    <td>
        <strong>
            <bean:message key="AccountForm.phone"
                          bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        </strong> <br/>
        <bean:write name="AccountForm" property="phone"/>
        <html:hidden property="phone"/>
    </td>
</tr>

<tr>
    <td>
        <strong>
            <bean:message key="AccountForm.mobile"
                          bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/> :
        </strong> <br/>
        <bean:write name="AccountForm" property="mobile"/>
        <html:hidden property="mobile"/>
    </td>
    <td>
        <strong>
            <bean:message key="AccountForm.fax"
                          bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/> :
        </strong> <br/>
        <bean:write name="AccountForm" property="fax"/>
        <html:hidden property="fax"/>
    </td>
</tr>

<tr>
    <td colspan="2">
        <strong>
            <bean:message key="AccountForm.accountStatus"
                          bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        <span class="requiredText">*</span>:</strong><br/>

        <logic:match name="taskForThisPage" scope="page" value="create">
            <html:select property="userAccountStatus" size="1" value="" styleClass="inputField">
                <html:option value="ACTIVE">Active</html:option>
                <html:option value="DELETED">Deleted</html:option>
            </html:select>
        </logic:match>

        <logic:match name="taskForThisPage" scope="page" value="edit">
            <bean:define id="accStatus" type="java.lang.String" name="AccountForm" property="userAccountStatus"/>
            <html:select property="userAccountStatus" size="1" value="<%=accStatus%>" styleClass="selectField">
                <html:option value="ACTIVE">Active</html:option>                
                <html:option value="DELETED">Deleted</html:option>
            </html:select>
        </logic:match>
    </td>
</tr>
</table>

</div>

<div> &nbsp; </div>

<div class="headLeftCurveblk"></DIV>
<H1>Role Assigned</H1>

<div class="headRightCurveblk"></DIV>

<div class="contentbox">

    <table width="100%" border="0" cellpadding="5" cellspacing="0" class="GridGradient">

        <tr>
            <th>
                <strong>Role <span class="requiredText">*</span>:</strong>
            </th>
        </tr>
        <tr>
            <td colspan="2">
                <html:select property="aimsRoleId" size="1" onchange="displayRolesInformation()">
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
            <td>
                <strong>Role Description :</strong>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <textarea name="roleDescription" class="textareaField" rows="6" cols="65"
                          readonly="true">&nbsp;</textarea>
            </td>
        </tr>
        <tr>
            <td>
                <table cellpadding="10" border="0" cellspacing="0" width="100%">
                <tr>
                    <td>
                        <div class="divButtons" style="float:right;">
                            <div class="blackBtn" title="Cancel" style="float:left;">
                                <div><div><div onclick="window.location='/aims/accounts.do?task=view'">Cancel</div></div></div>
                            </div>
                            <div class="redBtn" style="float:left;" id="Submit" title="Submit">
                                <div><div><div onclick="document.forms[0].submit()">Submit</div></div></div>
                            </div>
                        </div>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
</html:form>

</div>
</div>

<script language="javascript">
    displayRolesInformation();
</script>