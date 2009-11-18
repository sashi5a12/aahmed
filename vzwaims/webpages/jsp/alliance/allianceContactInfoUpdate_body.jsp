<%@ page import="com.netpace.aims.bo.security.AimsSecurityManager , com.netpace.aims.util.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<jsp:useBean id="AllianceContactInfoForm" class="com.netpace.aims.controller.alliance.AllianceContactInfoForm" scope="request" />
<bean:define id="alliance_id" type="java.lang.Long" name="AllianceContactInfoForm" property="allianceId"  scope="request"/>
<%
	boolean isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE);
	boolean isAllianceUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE); 
%>
<script language="javascript">

var contactsArray = new Array();

function AimsContacts()
{
    this.contactId = "";
    this.firstName = "";
    this.lastName = "";
    this.title = "";
    this.emailAddress = "";
    this.fax = "";
    this.phone = "";
    this.mobile = "";
}

    <%
    int	index	=	0;
    %>

<logic:iterate id="formContacts" name="AllianceContactInfoForm"	property="allContacts" scope="request">
aimsContacts = new AimsContacts();
aimsContacts.contactId = "<bean:write	name="formContacts"	property="contactId" />";
aimsContacts.firstName = "<bean:write	name="formContacts"	property="firstName" />";
;
aimsContacts.lastName = "<bean:write name="formContacts" property="lastName" />";
aimsContacts.title = "<bean:write	name="formContacts"	property="title" />";
aimsContacts.emailAddress = "<bean:write name="formContacts" property="emailAddress" />";
aimsContacts.fax = "<bean:write name="formContacts" property="fax" />"
aimsContacts.phone = "<bean:write	name="formContacts"	property="phone" />";
aimsContacts.mobile = "<bean:write name="formContacts" property="mobile" />";
contactsArray[<%=index%>] = aimsContacts;
<%index++;%>
</logic:iterate>

<%index	=	0;%>

var supported = (window.Option) ? 1 : 0;

if (!supported) {
    alert("Feature not supported");
}


function displayExecContactInformation()
{
    if ((document.forms[0].execContactId.options[document.forms[0].execContactId.selectedIndex].value) == -1) {
        document.forms[0].execContactId.selectedIndex = 0;
        addContact('executive');
        return false;
    }
    if ((document.forms[0].execContactId.options[document.forms[0].execContactId.selectedIndex].value) != "0")
    {
        for (var j = 0; j < contactsArray.length; j++)
        {
            if (contactsArray[j].contactId == document.forms[0].execContactId.options[document.forms[0].execContactId.selectedIndex].value)
            {
                document.forms[0].execContactFirstName.value = contactsArray[j].firstName;
                document.forms[0].execContactLastName.value = contactsArray[j].lastName;
                document.forms[0].execContactTitle.value = contactsArray[j].title;
                document.forms[0].execContactPhone.value = contactsArray[j].phone;
                document.forms[0].execContactEmailAddress.value = contactsArray[j].emailAddress;
                document.forms[0].execContactFax.value = contactsArray[j].fax;
                document.forms[0].execContactMobile.value = contactsArray[j].mobile;
            }
        }
    }
    else
    {
        document.forms[0].execContactFirstName.value = "";
        document.forms[0].execContactLastName.value = "";
        document.forms[0].execContactTitle.value = "";
        document.forms[0].execContactPhone.value = "";
        document.forms[0].execContactEmailAddress.value = "";
        document.forms[0].execContactFax.value = "";
        document.forms[0].execContactMobile.value = "";
    }
}

function displayBusContactInformation()
{
    if ((document.forms[0].busContactId.options[document.forms[0].busContactId.selectedIndex].value) == -1) {
        document.forms[0].busContactId.selectedIndex = 0;
        addContact('business');
        return false;
    }
    if ((document.forms[0].busContactId.options[document.forms[0].busContactId.selectedIndex].value) != "0")
    {
        for (var j = 0; j < contactsArray.length; j++)
        {
            if (contactsArray[j].contactId == document.forms[0].busContactId.options[document.forms[0].busContactId.selectedIndex].value)
            {
                document.forms[0].busContactFirstName.value = contactsArray[j].firstName;
                document.forms[0].busContactLastName.value = contactsArray[j].lastName;
                document.forms[0].busContactTitle.value = contactsArray[j].title;
                document.forms[0].busContactPhone.value = contactsArray[j].phone;
                document.forms[0].busContactEmailAddress.value = contactsArray[j].emailAddress;
                document.forms[0].busContactFax.value = contactsArray[j].fax;
                document.forms[0].busContactMobile.value = contactsArray[j].mobile;
            }
        }
    }
    else
    {
        document.forms[0].busContactFirstName.value = "";
        document.forms[0].busContactLastName.value = "";
        document.forms[0].busContactTitle.value = "";
        document.forms[0].busContactPhone.value = "";
        document.forms[0].busContactEmailAddress.value = "";
        document.forms[0].busContactFax.value = "";
        document.forms[0].busContactMobile.value = "";
    }
}


function displayMktgContactInformation()
{
    if ((document.forms[0].mktgContactId.options[document.forms[0].mktgContactId.selectedIndex].value) == -1) {
        document.forms[0].mktgContactId.selectedIndex = 0;
        addContact('marketing');
        return false;
    }
    if ((document.forms[0].mktgContactId.options[document.forms[0].mktgContactId.selectedIndex].value) != "0")
    {
        for (var j = 0; j < contactsArray.length; j++)
        {
            if (contactsArray[j].contactId == document.forms[0].mktgContactId.options[document.forms[0].mktgContactId.selectedIndex].value)
            {
                document.forms[0].mktgContactFirstName.value = contactsArray[j].firstName;
                document.forms[0].mktgContactLastName.value = contactsArray[j].lastName;
                document.forms[0].mktgContactTitle.value = contactsArray[j].title;
                document.forms[0].mktgContactPhone.value = contactsArray[j].phone;
                document.forms[0].mktgContactEmailAddress.value = contactsArray[j].emailAddress;
                document.forms[0].mktgContactFax.value = contactsArray[j].fax;
                document.forms[0].mktgContactMobile.value = contactsArray[j].mobile;
            }
        }
    }
    else
    {
        document.forms[0].mktgContactFirstName.value = "";
        document.forms[0].mktgContactLastName.value = "";
        document.forms[0].mktgContactTitle.value = "";
        document.forms[0].mktgContactPhone.value = "";
        document.forms[0].mktgContactEmailAddress.value = "";
        document.forms[0].mktgContactFax.value = "";
        document.forms[0].mktgContactMobile.value = "";
    }
}

function displayTechContactInformation()
{
    if ((document.forms[0].techContactId.options[document.forms[0].techContactId.selectedIndex].value) == -1) {
        document.forms[0].techContactId.selectedIndex = 0;
        addContact('technical');
        return false;
    }
    if ((document.forms[0].techContactId.options[document.forms[0].techContactId.selectedIndex].value) != "0")
    {
        for (var j = 0; j < contactsArray.length; j++)
        {
            if (contactsArray[j].contactId == document.forms[0].techContactId.options[document.forms[0].techContactId.selectedIndex].value)
            {
                document.forms[0].techContactFirstName.value = contactsArray[j].firstName;
                document.forms[0].techContactLastName.value = contactsArray[j].lastName;
                document.forms[0].techContactTitle.value = contactsArray[j].title;
                document.forms[0].techContactPhone.value = contactsArray[j].phone;
                document.forms[0].techContactEmailAddress.value = contactsArray[j].emailAddress;
                document.forms[0].techContactFax.value = contactsArray[j].fax;
                document.forms[0].techContactMobile.value = contactsArray[j].mobile;
            }
        }
    }
    else
    {
        document.forms[0].techContactFirstName.value = "";
        document.forms[0].techContactLastName.value = "";
        document.forms[0].techContactTitle.value = "";
        document.forms[0].techContactPhone.value = "";
        document.forms[0].techContactEmailAddress.value = "";
        document.forms[0].techContactFax.value = "";
        document.forms[0].techContactMobile.value = "";
    }
}

function enableAllContactInformation() {

    document.forms[0].execContactFirstName.disabled = false;
    document.forms[0].execContactLastName.disabled = false;
    document.forms[0].execContactTitle.disabled = false;
    document.forms[0].execContactPhone.disabled = false;
    document.forms[0].execContactEmailAddress.disabled = false;
    document.forms[0].execContactFax.disabled = false;
    document.forms[0].execContactMobile.disabled = false;

    document.forms[0].busContactFirstName.disabled = false;
    document.forms[0].busContactLastName.disabled = false;
    document.forms[0].busContactTitle.disabled = false;
    document.forms[0].busContactPhone.disabled = false;
    document.forms[0].busContactEmailAddress.disabled = false;
    document.forms[0].busContactFax.disabled = false;
    document.forms[0].busContactMobile.disabled = false;

    document.forms[0].mktgContactFirstName.disabled = false;
    document.forms[0].mktgContactLastName.disabled = false;
    document.forms[0].mktgContactTitle.disabled = false;
    document.forms[0].mktgContactPhone.disabled = false;
    document.forms[0].mktgContactEmailAddress.disabled = false;
    document.forms[0].mktgContactFax.disabled = false;
    document.forms[0].mktgContactMobile.disabled = false;

    document.forms[0].techContactFirstName.disabled = false;
    document.forms[0].techContactLastName.disabled = false;
    document.forms[0].techContactTitle.disabled = false;
    document.forms[0].techContactPhone.disabled = false;
    document.forms[0].techContactEmailAddress.disabled = false;
    document.forms[0].techContactFax.disabled = false;
    document.forms[0].techContactMobile.disabled = false;
}

function disableAllContactInformation() {

    document.forms[0].execContactFirstName.disabled = true;
    document.forms[0].execContactLastName.disabled = true;
    document.forms[0].execContactTitle.disabled = true;
    document.forms[0].execContactPhone.disabled = true;
    document.forms[0].execContactEmailAddress.disabled = true;
    document.forms[0].execContactFax.disabled = true;
    document.forms[0].execContactMobile.disabled = true;

    document.forms[0].busContactFirstName.disabled = true;
    document.forms[0].busContactLastName.disabled = true;
    document.forms[0].busContactTitle.disabled = true;
    document.forms[0].busContactPhone.disabled = true;
    document.forms[0].busContactEmailAddress.disabled = true;
    document.forms[0].busContactFax.disabled = true;
    document.forms[0].busContactMobile.disabled = true;

    document.forms[0].mktgContactFirstName.disabled = true;
    document.forms[0].mktgContactLastName.disabled = true;
    document.forms[0].mktgContactTitle.disabled = true;
    document.forms[0].mktgContactPhone.disabled = true;
    document.forms[0].mktgContactEmailAddress.disabled = true;
    document.forms[0].mktgContactFax.disabled = true;
    document.forms[0].mktgContactMobile.disabled = true;

    document.forms[0].techContactFirstName.disabled = true;
    document.forms[0].techContactLastName.disabled = true;
    document.forms[0].techContactTitle.disabled = true;
    document.forms[0].techContactPhone.disabled = true;
    document.forms[0].techContactEmailAddress.disabled = true;
    document.forms[0].techContactFax.disabled = true;
    document.forms[0].techContactMobile.disabled = true;
}

function truncateLocalTextAreas()
{
    if (typeof document.forms[0].escalationInstructions != "undefined")
        if (typeof document.forms[0].escalationInstructions.value != "undefined")
            TruncateText(document.forms[0].escalationInstructions, 500);
}
function addContact(type)
{
    var url = "<c:out value='${pageContext.request.contextPath}'/>/contactsSetup.do?alliance_id=<%=alliance_id%>&task=createFormPopup&isPopup=true&cType=" + type
            +"&parentPageType=<%=AimsConstants.PAGE_TYPE_ALLIANCE_CONTACT_UPDATE%>" +
              "&parentPath=allianceContactInfoSetup.do?task=editForm";
    var win = window.open(url, "addContact", "resizable=0,width=870,height=450,scrollbars=1,screenX=100,left=150,screenY=30,top=30,status=0,titlebar=0");
    win.focus();
}
</script>


<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<div class="homeColumnTab">

<c:choose>
	<c:when test="${sessionScope.AIMS_USER.userType eq 'A'}">
		<aims:getAllianceTab attributeName="Contact Info"/>
	</c:when>
	<c:otherwise>
		<aims:getVZWAllianceTab attributeName="Contact Info" allianceId="<%=alliance_id.toString()%>"/>
	</c:otherwise>
</c:choose>

<div class="contentbox">

<table width="100%" height="36">
    <tr>
        <td valign="middle">
            <strong>Company Name:
                <bean:write name="AllianceContactInfoForm" property="companyName"/>
            </strong>
        </td>
    </tr>
</table>


<html:form action="/allianceContactInfoEdit">
<html:hidden property="task" value="edit"/>
<input type="hidden" name="allianceId" value="<%=alliance_id %>"/>
<input type="hidden" name="alliance_id" value="<%=alliance_id %>"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0" onmousemove="truncateLocalTextAreas();">
<tr>
    <td width="100%" colspan="3">
        Please use the drop down boxes to select contacts.
        <br/>
    </td>
</tr>
<tr>
    <td colspan=3> &nbsp;</td>
</tr>

<tr width="100%">
<td width=50% valign="top">
<table width=320 height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
<tr>
    <td colspan="2" class="noPad">
        <div class="mmBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>
                <bean:message key="AllianceContactInfoForm.execContact"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                <span class="requiredText">*</span>
            </H1>
            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
    <td> &nbsp; </td>
</tr>
<tr>
    <th nowrap="nowrap">
        <strong><bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>
    </th>
    <th >
        <html:select property="execContactId" size="1" onchange="displayExecContactInformation()"
                     styleClass="selectField" style="width:90">
            <html:option value="0">
                <bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </html:option>
            <% if ((isAllianceUser &&  AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTACTS, AimsSecurityManager.INSERT))
            		|| (isVerizonUser && AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CREATE_ALLIANCE_USER_BY_VZW, AimsSecurityManager.INSERT))){ %>
            	<html:option value="-1">Create New Contact</html:option>
            <%} %>
            <logic:iterate id="formContacts" name="AllianceContactInfoForm" property="allContacts"
                           type="com.netpace.aims.model.core.AimsContact" scope="request">
                <html:option value="<%=formContacts.getContactId().toString()%>">
                	<bean:write	name="formContacts"	property="firstName" />
                	<bean:write	name="formContacts"	property="lastName" /> 
                </html:option>
            </logic:iterate>
        </html:select>
    </th>
</tr>
<tr>
    <td>
        <strong>
        <bean:message key="AllianceContactInfoForm.firstName"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
        <span class="requiredText">*</span>:</strong>
    </td>
    <td>
        <html:text property="execContactFirstName" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
        <bean:message key="AllianceContactInfoForm.lastName"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
        <span class="requiredText">*</span>:</strong>
    </td>
    <td>
        <html:text property="execContactLastName" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.title"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="execContactTitle" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.email"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="execContactEmailAddress" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
        <bean:message key="AllianceContactInfoForm.phone"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="execContactPhone" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
        <bean:message key="AllianceContactInfoForm.mobile"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="execContactMobile" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
        <bean:message key="AllianceContactInfoForm.fax"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="execContactFax" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
</table>
</td>
<td width="50%" valign=top>
<table width=395 height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0" >
<tr>
    <td colspan="3" class="noPad">
        <div class="mmBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>
                <bean:message key="AllianceContactInfoForm.businessContact"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                <span class="requiredText">*</span>
            </H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <th nowrap="nowrap">
        <strong>
        <bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
        </strong>
    </th>
    <th>
        <html:select property="busContactId" size="1" onchange="displayBusContactInformation()" styleClass="selectField">
            <html:option value="0">
                <bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </html:option>
            <% if ((isAllianceUser &&  AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTACTS, AimsSecurityManager.INSERT))
            		|| (isVerizonUser && AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CREATE_ALLIANCE_USER_BY_VZW, AimsSecurityManager.INSERT))){ %>            
            	<html:option value="-1">Create New Contact</html:option>
            <%} %>
            <logic:iterate id="formContacts" name="AllianceContactInfoForm" property="allContacts"
                           type="com.netpace.aims.model.core.AimsContact" scope="request">
                <html:option value="<%=formContacts.getContactId().toString()%>">
                	<bean:write	name="formContacts"	property="firstName" />
                	<bean:write	name="formContacts"	property="lastName" /> 
                </html:option>
            </logic:iterate>
        </html:select>
    </th>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.firstName"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="busContactFirstName" maxlength="50" size="25"  styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.lastName"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="busContactLastName" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.title"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="busContactTitle" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.email"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="busContactEmailAddress" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.phone"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="busContactPhone" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.mobile"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="busContactMobile" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.fax"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="busContactFax" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
</table>
</td>
</tr>

<tr>
    <td width="100%" colspan="2">&nbsp;</td>
</tr>

<tr>
<td width=50% valign="top">
<table width=395 height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
<tr>
    <td colspan="3" class="noPad">
        <div class="mmBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>
                <bean:message key="AllianceContactInfoForm.mktgContact"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                <span class="requiredText">*</span>
            </H1>
            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <th nowrap="nowrap">
        <strong>
            <bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                        bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
        </strong>
    </th>
    <th>
        <html:select property="mktgContactId" size="1" onchange="displayMktgContactInformation()"
                     styleClass="selectField">
            <html:option value="0">
                <bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </html:option>
            <% if ((isAllianceUser &&  AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTACTS, AimsSecurityManager.INSERT))
            		|| (isVerizonUser && AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CREATE_ALLIANCE_USER_BY_VZW, AimsSecurityManager.INSERT))){ %>          
            	<html:option value="-1">Create New Contact</html:option>
            <% } %>
            <logic:iterate id="formContacts" name="AllianceContactInfoForm" property="allContacts"
                           type="com.netpace.aims.model.core.AimsContact" scope="request">
                <html:option value="<%=formContacts.getContactId().toString()%>">
                	<bean:write	name="formContacts"	property="firstName" />
                	<bean:write	name="formContacts"	property="lastName" /> 
                </html:option>
            </logic:iterate>
        </html:select>
    </th>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.firstName"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="mktgContactFirstName" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.lastName" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="mktgContactLastName" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.title"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="mktgContactTitle" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.email"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="mktgContactEmailAddress" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
        <bean:message key="AllianceContactInfoForm.phone"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="mktgContactPhone" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.mobile"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="mktgContactMobile" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.fax"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="mktgContactFax" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
</table>
</td>
<td width="50%" valign=top>
<table width=395 height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
<tr>
    <td colspan="2" class="noPad">
        <div class="mmBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>
                <bean:message key="AllianceContactInfoForm.techContact"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                <span class="requiredText">*</span>
            </H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <th nowrap="nowrap" >
        <strong>
            <bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
        </strong>
    </th>
    <th>
        <html:select property="techContactId" size="1" onchange="displayTechContactInformation()"
                     styleClass="selectField">
            <html:option value="0">
                <bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </html:option>
            <% if ((isAllianceUser &&  AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_CONTACTS, AimsSecurityManager.INSERT))
            		|| (isVerizonUser && AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CREATE_ALLIANCE_USER_BY_VZW, AimsSecurityManager.INSERT))){ %>            
            	<html:option value="-1">Create New Contact</html:option>
            <% } %>
            <logic:iterate id="formContacts" name="AllianceContactInfoForm" property="allContacts"
                           type="com.netpace.aims.model.core.AimsContact" scope="request">
                <html:option value="<%=formContacts.getContactId().toString()%>">
                	<bean:write	name="formContacts"	property="firstName" />
                	<bean:write	name="formContacts"	property="lastName" /> 
                </html:option>
            </logic:iterate>
        </html:select>
    </th>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.firstName"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="techContactFirstName" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.lastName"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="techContactLastName" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.title"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="techContactTitle" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.email"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            <span class="requiredText">*</span>:
        </strong>
    </td>
    <td>
        <html:text property="techContactEmailAddress" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
        <bean:message key="AllianceContactInfoForm.phone"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="techContactPhone" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.mobile"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="techContactMobile" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
<tr>
    <td>
        <strong>
            <bean:message key="AllianceContactInfoForm.fax"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
        </strong>
    </td>
    <td>
        <html:text property="techContactFax" maxlength="50" size="25" styleClass="inputField"/>
    </td>
</tr>
</table>
</td>
</tr>
<tr>
    <td width="100%" colspan="2">&nbsp;</td>
</tr>
<tr>
    <td width="100%" valign=top colspan="2">
        <table width=395 height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td class="noPad">
                    <div class="lBox">
                        <DIV class="headLeftCurveblk"></DIV>
                        <H1>
                            <bean:message key="AllianceContactInfoForm.escalationProcedure"
                                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                            <span class="requiredText">*</span>
                        </H1>

                        <DIV class="headRightCurveblk"></DIV>
                    </div>
                </td>
            </tr>
            <tr>
                <th>
                    <html:textarea property="escalationInstructions" onkeyup="LimitText(this,500)"
                                   onkeypress="LimitText(this, 500)" rows="7" cols="75"
                                   styleClass="textareaField"></html:textarea>
                </th>
            </tr>
        </table>
    </td>
</tr>


<%    
    if ( (isAllianceUser && AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.CONTACT_INFORMATION, AimsSecurityManager.UPDATE)) 
    		|| (isVerizonUser && AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.UPDATE_ALLIANCE_CONTACT_INFO_BY_VZW, AimsSecurityManager.UPDATE)) ) {
%>
<tr>
    <td colspan="2" align="right" valign="middle">
        <div class="redBtn" style="margin-left:10px;margin-right:10px;float:right;" title="Submit">
            <div>
                <div>
                    <div onClick="enableAllContactInformation();document.forms[0].submit();">Submit</div>
                </div>
            </div>
        </div>
        <div class="blackBtn" style="margin-left:10px;float:right;" title="Cancel">
            <div>
                <div><div onclick="window.location='/aims/allianceContactInfoSetup.do?task=editForm&alliance_id=<%=alliance_id%>'">Cancel</div></div>
            </div>
        </div>
    </td>
</tr>
<%
    }
%>
</table>

</html:form>


<script language="javascript">
    disableAllContactInformation();
    displayExecContactInformation();
    displayBusContactInformation();
    displayMktgContactInformation();
    displayTechContactInformation();
</script>

</div>
</div>
</div>