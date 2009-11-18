<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/error.jsp" %>


<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<html:form action="/devicesonloan">
<input type="hidden" name="task" value="viewdevices"/>
    <html:hidden property="deviceId"/>

<DIV class="headLeftCurveblk"></DIV>
<H1>
    <bean:message key="DeviceOnLoan.SearchCriteria" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
</H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">

    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th>
                <strong> <bean:message key="DeviceOnLoan.AllianceName"
                              bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
            </th>
            <th>
               <strong> <bean:message key="DeviceOnLoan.AllianceMember"
                              bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
            </th>
        </tr>
        <tr>
            <td>
                <html:text property="allianceName" size="40" styleClass="inputField"/>
            </td>
            <td>
                <html:text property="allianceMemberName" size="40" styleClass="inputField"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong><bean:message key="DeviceOnLoan.ESNDEC"
                              bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/> </strong>
            </td>
            <td>
                <strong><bean:message key="DeviceOnLoan.ESNHEX"
                              bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/> </strong>
            </td>
        </tr>
        <tr>
            <td>
                <html:text property="esnDec" size="25" styleClass="inputField"/>
            </td>
            <td>
                <html:text property="esn" size="25" styleClass="inputField"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong><bean:message key="DeviceOnLoan.MTN"
                              bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/> </strong>
            </td>
            <td>
                <strong><bean:message key="DeviceOnLoan.EmailRequestSent"
                              bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/> </strong>
            </td>
        </tr>
        <tr>
            <td>
                <html:text property="mtn" size="25" styleClass="inputField"/>
            </td>
            <td>
                <select name="emailSent" class="inputField">
                    <option value=""></option>
                    <option value="Y">Yes</option>
                    <option value="">No</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <strong><bean:message key="DeviceOnLoan.Status" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></strong>
            </td>
            <td> </td>
        </tr>
        <tr>
            <td>
                <select name="status" class="inputField">
                    <option value="">Any Status</option>
                    <option value="Open">Open</option>
                    <option value="Closed">Closed</option>
                </select>
            </td>
            <td>
            </td>
        </tr>
        <!-- Loaned Items and Return Items selection were here now removed -->
        <!-- -->
    </table>

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="100%" align="right">
                <div class="redBtn" style="float:right;padding-left:10px" id="Submit" title="Submit">
                    <div>
                        <div><div onclick="document.forms[0].submit()">Submit</div></div>
                    </div>
                </div>
                <%--<input type="image" name="Submit" <bean:message key="images.submit.button.lite"/> alt="Submit"/>--%>
                <div class="blackBtn" style="float:right;padding-left:10px" id="Cancel" title="Cancel">
                    <div>
                        <div><div onclick="window.location='devicesonloan.do?task=viewdevices&deviceId=<bean:write name="AimsLoanDeviceExtz" property="deviceId" />'">Cancel</div></div>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</DIV>
</html:form>