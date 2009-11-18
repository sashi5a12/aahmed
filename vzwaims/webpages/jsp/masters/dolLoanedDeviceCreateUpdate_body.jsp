<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<script language="javascript">
<!--

function refreshPage()
{
    if ((document.forms[0].allianceAllianceId.options[document.forms[0].allianceAllianceId.selectedIndex].value) != "0")
        document.forms[0].allianceCompanyName.value = document.forms[0].allianceAllianceId.options[document.forms[0].allianceAllianceId.selectedIndex].text;
    document.forms["LoanedDeviceListForm"].task.value = "refresh";
    document.forms["LoanedDeviceListForm"].action = "devicesonloan.do";
    document.forms["LoanedDeviceListForm"].submit();
}

var contactsArray = new Array();
var devicesArray = new Array();

function AimsContacts()
{
    this.contactId = "";
    this.firstName = "";
    this.lastName = "";
    this.emailAddress = "";
    this.phone = "";
}

function AimsDeviceToLoan()
{
    this.deviceToLoanId = ""
    this.esnDec = "";
    this.esn = "";
    this.mtn = "";
}

<logic:present name="AimsAllianceContacts">
<%int index	=	0;%>
<logic:iterate id="formContacts" name="AimsAllianceContacts" scope="session">
aimsContacts = new AimsContacts();
aimsContacts.contactId = "<bean:write	name="formContacts"	property="contactId" />";
aimsContacts.firstName = "<bean:write	name="formContacts"	property="firstName" />";
;
aimsContacts.lastName = "<bean:write name="formContacts" property="lastName" />";
aimsContacts.emailAddress = "<bean:write name="formContacts" property="emailAddress" />";
aimsContacts.phone = "<bean:write	name="formContacts"	property="phone" />";
contactsArray[<%=index%>] = aimsContacts;
<%index++;%>
</logic:iterate>
</logic:present>

<logic:present name="AimsDevices2Loan">
<% int index	=	0;%>
<logic:iterate id="device2Loan" name="AimsDevices2Loan" scope="session">
aimsDevice2Loan = new AimsDeviceToLoan();
aimsDevice2Loan.deviceToLoanId = "<bean:write	name="device2Loan"	property="deviceToLoanId" />";
aimsDevice2Loan.esnDec = "<bean:write	name="device2Loan"	property="esnDec" />";
aimsDevice2Loan.esn = "<bean:write	name="device2Loan"	property="esn" />";
;
aimsDevice2Loan.mtn = "<bean:write name="device2Loan" property="mtn" />";
devicesArray[<%=index%>] = aimsDevice2Loan;
<%index++;%>
</logic:iterate>
</logic:present>

var supported = (window.Option) ? 1 : 0;

function displayContactsInformation() {
    if (!supported) {
        alert("Feature	not	supported");
    }

    if ((document.forms[0].aimsContactId.options[document.forms[0].aimsContactId.selectedIndex].value) != "0")
    {
        for (var j = 0; j < contactsArray.length; j++)
        {
            if (contactsArray[j].contactId == document.forms[0].aimsContactId.options[document.forms[0].aimsContactId.selectedIndex].value)
            {
                document.forms[0].allianceMemberName.value = contactsArray[j].lastName + " " + contactsArray[j].firstName;
                document.forms[0].allianceMemberEmail.value = contactsArray[j].emailAddress;
                document.forms[0].allianceMemberTelephone.value = contactsArray[j].phone;

                /*document.forms[0].allianceMemberName.disabled	=	true;
                        document.forms[0].allianceMemberEmail.disabled	=	true;
                        document.forms[0].allianceMemberTelephone.disabled	=	true;*/
            }
        }
    }
    else
    {
        /*document.forms[0].allianceMemberName.disabled	=	false;
              document.forms[0].allianceMemberEmail.disabled	=	false;
              document.forms[0].allianceMemberTelephone.disabled	=	false;*/

        document.forms[0].allianceMemberName.value = "<bean:write name="AimsDeviceDetails" property="allianceMemberName" />"
        document.forms[0].allianceMemberEmail.value = "<bean:write name="AimsDeviceDetails" property="allianceMemberEmail" />"
        document.forms[0].allianceMemberTelephone.value = "<bean:write name="AimsDeviceDetails" property="allianceMemberTelephone" />"
    }
}

/* */
function displayDeviceToLoanInfo() {
    if (!supported) {
        alert("Feature	not	supported");
    }

    if ((document.forms[0].aimsDeviceToLoanId.options[document.forms[0].aimsDeviceToLoanId.selectedIndex].value) != "0")
    {
        for (var j = 0; j < devicesArray.length; j++)
        {
            if (devicesArray[j].deviceToLoanId == document.forms[0].aimsDeviceToLoanId.options[document.forms[0].aimsDeviceToLoanId.selectedIndex].value)
            {
                document.forms[0].esnDec.value = devicesArray[j].esnDec;
                document.forms[0].esn.value = devicesArray[j].esn;
                document.forms[0].mtn.value = devicesArray[j].mtn;
            }
        }

    }
    else
    {
        document.forms[0].esnDec.value = "<bean:write name="AimsDeviceDetails" property="esnDec" />";
        document.forms[0].esn.value = "<bean:write name="AimsDeviceDetails" property="esn" />";
        document.forms[0].mtn.value = "<bean:write name="AimsDeviceDetails" property="mtn" />";
    }
}
//-->
</script>


<html:form action="/devicesonloanupdate">
<input type="hidden" name="loanDeviceId" value="<bean:write name="AimsDeviceDetails" property="loanDeviceId" />"/>
<html:hidden property="task"/>

<logic:messagesPresent>
    Error: <br/>
    <html:messages id="errord" header="errors.header" footer="errors.footer">
        <span class="errorText"><bean:write name="errord"/></span><br/>
    </html:messages>
</logic:messagesPresent>
<logic:messagesPresent message="true">
    Message <br/>
    <html:messages id="message" message="true" header="messages.header" footer="messages.footer">
        <span class="messageText"><bean:write name="message"/></span><br/>
    </html:messages>
</logic:messagesPresent>


<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<DIV class="headLeftCurveblk"></DIV>
<H1><bean:message key="DeviceOnLoan.DeviceOnLoanDetails"
                  bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></H1>

<DIV class="headRightCurveblk"></DIV>


<DIV class="contentbox">
    <!-- Device Details -->
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th width="50%">
                <strong><bean:message key="DeviceOnLoan.Device"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
            </th>
            <th width="50%">
                <strong><bean:message key="DeviceOnLoan.Manufacturer"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
            </th>
        </tr>
        <tr>
            <td>
                <bean:write name="AimsDeviceDetails" property="aimsDevic.deviceModel"/>
                &nbsp;
            </td>
            <td>
                <bean:write name="AimsDeviceDetails" property="aimsDevic.deviceMfgBy"/>
                &nbsp;
            </td>
        </tr>
        <!-- Copy Device Info Starts -->
        <logic:present name="AimsDevices2Loan">
            <tr>
                <td colspan="2">
                    <strong><bean:message key="DeviceOnLoan.CopyDeviceInfo"
                                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <html:select property="aimsDeviceToLoanId" size="1" onchange="displayDeviceToLoanInfo()"
                                 styleClass="selectField">
                        <html:option value="0">
                            <bean:message key="DeviceOnLoan.PleaseSelect"
                                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                        </html:option>
                        <logic:iterate id="devToLoan" name="AimsDevices2Loan"
                                       type="com.netpace.aims.model.masters.AimsDevicesToLoan">
                            <html:option
                                    value="<%=devToLoan.getDeviceToLoanId().toString()%>"><%= (devToLoan.getEsnDec() == null ? "" : devToLoan.getEsnDec()) + " | " + (devToLoan.getEsn() == null ? "" : devToLoan.getEsn()) %>
                            </html:option>
                        </logic:iterate>
                    </html:select>
                </td>
            </tr>
        </logic:present>
        <!-- Copy Device Info Ends -->
        <tr>
            <td>
                <strong><bean:message key="DeviceOnLoan.ESNDEC"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                    <span class="requiredText">*</span>
                    <bean:message key="DeviceOnLoan.OptionalEsnDec"
                                  bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
            </td>
            <td>
                <strong><bean:message key="DeviceOnLoan.ESNHEX"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                    <span class="requiredText">*</span>
                    <bean:message key="DeviceOnLoan.OptionalEsnHex"
                                  bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" name="esnDec" size="30" maxlength="50"
                       value="<bean:write name="AimsDeviceDetails" property="esnDec" />" class="inputField"/>
            </td>
            <td>
                <input type="text" name="esn" size="30" maxlength="50"
                       value="<bean:write name="AimsDeviceDetails" property="esn" />" class="inputField"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong><bean:message key="DeviceOnLoan.MTN" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                <span class="requiredText">*</span></strong></td>
            <td></td>
        </tr>
        <tr>
            <td>
                <input type="text" name="mtn" size="30" maxlength="50"
                       value="<bean:write name="AimsDeviceDetails" property="mtn" />" class="inputField"/>
            </td>
            <td>
            </td>
        </tr>
    </table>
    <!-- Device Details Ends -->
</div>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1><bean:message key="DeviceOnLoan.AllianceInformation"
                  bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <!-- Alliance Details -->
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th><strong>Select Alliance Name (Optional)</strong> <br/>
                <html:select name="AimsDeviceDetails" property="allianceAllianceId"
                             onchange="javacript:refreshPage();" styleClass="selectField">
                    <html:option value="0">
                        <bean:message key="DeviceOnLoan.PleaseSelect"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                    </html:option>
                    <html:optionsCollection name="AimsAlliances" label="companyName" value="allianceId"/>
                </html:select>
            </th>
            <th>
                <strong>Alliance Name <span class="requiredText">*</span></strong> <br/>
                <input type="text" name="allianceCompanyName" size="30" maxlength="50"
                       value="<bean:write name="AimsDeviceDetails" property="allianceCompanyName" />"
                       class="inputField"/>&nbsp;
            </th>
        </tr>

        <logic:present name="AimsAllianceContacts">
            <tr>
                <td colspan="2">
                    <strong><logic:present name="AimsAllianceContacts">
                        <bean:message key="DeviceOnLoan.CopyMemberInfo"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                    </logic:present></strong>
                    &nbsp;</td>
            </tr>
            <tr>
                <!-- Contact Information -->
                <td colspan="2">
                    <html:select property="aimsContactId" size="1" onchange="displayContactsInformation()"
                                 styleClass="selectField">
                        <html:option value="0">
                            <bean:message key="DeviceOnLoan.PleaseSelect"
                                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                        </html:option>
                        <logic:iterate id="formContacts" name="AimsAllianceContacts"
                                       type="com.netpace.aims.model.core.AimsContact" scope="session">
                            <html:option
                                    value="<%=formContacts.getContactId().toString()%>"><%=formContacts.getFirstName()%>    <%=formContacts.getLastName()%>
                            </html:option>
                        </logic:iterate>
                    </html:select>
                </td>
                <!-- Contact Information Ends -->
            </tr>
        </logic:present>

        <tr>
            <td width="50%">
                <strong> <bean:message key="DeviceOnLoan.AllianceMemberName"
                                       bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                    <span class="requiredText">*</span></strong><br/>
                <input type="text" name="allianceMemberName" size="30" maxlength="50"
                       value="<bean:write name="AimsDeviceDetails" property="allianceMemberName" />"
                       class="inputField"/>&nbsp;
            </td>
            <td>
                <strong><bean:message key="DeviceOnLoan.AllianceMemberTelephone"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong> <br/>
                <input type="text" name="allianceMemberTelephone" size="30" maxlength="50"
                       value="<bean:write name="AimsDeviceDetails" property="allianceMemberTelephone" />"
                       class="inputField"/>&nbsp;
            </td>
        </tr>
        <tr>
            <td style="vertical-align:top;">
                <strong><bean:message key="DeviceOnLoan.AllianceMemberEmail"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong> <br/>
                <input type="text" name="allianceMemberEmail" size="30" maxlength="50"
                       value="<bean:write name="AimsDeviceDetails" property="allianceMemberEmail" />"
                       class="inputField"/>
            </td>
            <td width="50%">
                <strong><bean:message key="DeviceOnLoan.AllianceMemberAddress"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong> <br/>
                <textarea name="allianceMemberAddress" rows="3" cols="35" class="textareaField"><bean:write name="AimsDeviceDetails" property="allianceMemberAddress"/></textarea>
            </td>
        </tr>
        <tr>
            <td valign="top">

            </td>
            <td>

            </td>
        </tr>
    </table>
    <!-- Alliance Details -->
</div>


<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1><bean:message key="DeviceOnLoan.StatusTracking" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <!-- Tracking Details Starts-->
    <table width="100%" cellpadding="0" cellspacing="0" border="0">
        <tr>
            <logic:present name="AimsDeviceDetails" property="loanDeviceId">
                <td valign="top" align="left">
                    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
                        <tr>
                            <th>
                                <strong><bean:message key="DeviceOnLoan.Status"
                                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                                    <span class="requiredText">*</span></strong>
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <select name="status" class="inputField">
                                    <option value="Open"
                                            <logic:equal name="AimsDeviceDetails" property="status"
                                                         value="Open">selected </logic:equal> >Open
                                    </option>
                                    <option value="Closed"
                                            <logic:equal name="AimsDeviceDetails" property="status"
                                                         value="Closed">selected </logic:equal> >Closed
                                    </option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </td>
            </logic:present>
            <td valign="top" align="left">
                <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
                    <tr>
                        <th>
                            <strong><bean:message key="DeviceOnLoan.Comments"
                                                  bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <textarea name="comments" rows="7" cols="35" class="textareaField"><bean:write
                                    name="AimsDeviceDetails" property="comments"/></textarea>
                            <logic:notPresent name="AimsDeviceDetails" property="loanDeviceId">
                                <input type="hidden" name="status" value="Open">
                            </logic:notPresent>
                            &nbsp;
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <!-- Tracking Details Ends-->

    <table width="100%" cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td>

                <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Save" title="Save">
                    <div>
                        <div>
                            <div onclick="document.forms[0].submit()">Save</div>
                        </div>
                    </div>
                </div>
                <!--<input type="image" height=15 width=52 src="images/save_b.gif" border=0 name=AllSubmit>&nbsp;-->

                <div class="blackBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Cancel" title="Cancel">
                    <div>
                        <div>
                            <div onclick="window.location='devicesonloan.do?task=viewdevices&deviceId=<bean:write name="AimsDeviceDetails" property="aimsDevic.deviceId" />'">
                                Cancel
                            </div>
                        </div>
                    </div>
                </div>
                <!--
                <a href="">
                <img height=15 width=52 src="images/cancel_b.gif" border=0 name=Cancel></a>
                -->
            </td>
        </tr>
    </table>

</div>


</html:form>