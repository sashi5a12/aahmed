<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<html:errors/>

<html:form action="/devicesonloan">

<%--Loan Details--%>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<DIV class="headLeftCurveblk"></DIV>
<H1><bean:message key="DeviceOnLoan.DeviceOnLoanDetails" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th width="50%">
            <strong><bean:message key="DeviceOnLoan.Device" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
        </th>
        <th width="50%">
            <strong><bean:message key="DeviceOnLoan.Manufacturer" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
        </th>
    </tr>
    <tr>
        <td>
            <bean:write name="AimsDeviceOnLoan" property="aimsDevic.deviceModel"/>
            &nbsp;
        </td>
        <td>
            <bean:write name="AimsDeviceOnLoan" property="aimsDevic.deviceMfgBy"/>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td>
            <strong><bean:message key="DeviceOnLoan.ESNDEC"
                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
        </td>
        <td>
            <strong><bean:message key="DeviceOnLoan.ESNHEX"
                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
        </td>
    </tr>
    <tr>
        <td>
            <bean:write name="AimsDeviceOnLoan" property="esnDec"/>
        </td>
        <td>
            <bean:write name="AimsDeviceOnLoan" property="esn"/>
        </td>
    </tr>
    <tr>
        <td>
            <strong><bean:message key="DeviceOnLoan.MTN"
                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
        </td>
        <td></td>
    </tr>
    <tr>
        <td>
            <bean:write name="AimsDeviceOnLoan" property="mtn"/>
        </td>
        <td>
        </td>
    </tr>
</table>
</div>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Alliance Information</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
                <tr>
                    <th width="50%">
                        <strong><bean:message key="DeviceOnLoan.AllianceName"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
                    </th>
                    <th width="50%">
                        <strong><bean:message key="DeviceOnLoan.AllianceMemberName"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
                    </th>
                </tr>
                <tr>
                    <td>
                        <bean:write name="AimsDeviceOnLoan" property="allianceCompanyName"/>
                        &nbsp;
                    </td>
                    <td>
                        <bean:write name="AimsDeviceOnLoan" property="allianceMemberName"/>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <strong><bean:message key="DeviceOnLoan.AllianceMemberAddress"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
                    </td>
                    <td>
                        <strong><bean:message key="DeviceOnLoan.AllianceMemberTelephone"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
                    </td>
                </tr>
                <tr>
                    <td>
                        <bean:write name="AimsDeviceOnLoan" property="allianceMemberAddress"/>
                        &nbsp;
                    </td>
                    <td>
                        <bean:write name="AimsDeviceOnLoan" property="allianceMemberTelephone"/>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        <strong><bean:message key="DeviceOnLoan.AllianceMemberEmail"
                                      bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
                    </td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                        <bean:write name="AimsDeviceOnLoan" property="allianceMemberEmail"/>
                        &nbsp;
                    </td>
                    <td>
                        &nbsp;
                    </td>
                </tr>
            </table>
</div>
<!-- Alliance Details -->

</html:form>


<form name="frmDate" method="post"
      action="devicesonloan.do?task=delete&loanDeviceId=<bean:write name="AimsDeviceOnLoan" property="loanDeviceId" />&deviceId=<bean:write name="AimsDeviceOnLoan" property="deviceId" />"
      onSubmit="return ;">

            <div class="blackBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Cancel" title="Cancel">
                <div>
                    <div><div onclick="window.location='devicesonloan.do?task=viewdevices&deviceId=<bean:write name="AimsDeviceOnLoan" property="deviceId" />'">Cancel</div></div>
                </div>
            </div>

            <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Delete" title="Delete">
                <div>
                    <div><div onclick="javascript:if(confirm('Do you want to delete the loaned device record ?')){document.forms[0].submit();}">Delete</div></div>
                </div>
            </div>

            <div class="blackBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Edit" title="Edit">
                <div>
                    <div><div onclick="window.location='devicesonloan.do?task=edit&loanDeviceId=<bean:write name="AimsDeviceOnLoan" property="loanDeviceId" />'">Edit</div></div>
                </div>
            </div>

            <div class="blackBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Send" title="Send Email">
                <div>
                    <div><div onclick="window.location='devicesonloanemail.do?task=compose&loanDeviceId=<bean:write name="AimsDeviceOnLoan" property="loanDeviceId" />'">Send</div></div>
                </div>
            </div>

</form>

</DIV>
</DIV>