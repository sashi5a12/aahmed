<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<script language="javascript">
    <!--
    function createNew()
    {
        document.forms["LoanedDeviceListForm"].task.value = "create";
        document.forms["LoanedDeviceListForm"].submit();
    }

    function viewPage(pageNumber)
    {
        document.forms["LoanedDeviceListForm"].pageNumber.value = pageNumber;
        document.forms["LoanedDeviceListForm"].submit();
    }
    //-->
</script>

<jsp:useBean id="TotalPages" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="LoanedDeviceListForm" class="com.netpace.aims.controller.devices.LoanDeviceListForm" scope="request"/>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<DIV class="headLeftCurveblk"></DIV>
<H1>Device Information</H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th width="15%">
                <b>
                    <bean:message key="DeviceOnLoan.Device" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                </b>
            </th>
            <th>
                <bean:write name="AimsLoanDeviceExtz" property="deviceModel"/>
                &nbsp; </th>
        </tr>
        <tr>
            <td><b>
                <bean:message key="DeviceOnLoan.Manufacturer" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
            </b></td>
            <td><b>
                <bean:write name="AimsLoanDeviceExtz" property="deviceMfgBy"/>
                &nbsp;</b></td>
        </tr>
    </table>
</DIV>

<div>&nbsp;</div>
<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>List of Loaned Devices</H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
<html:form action="/devicesonloan">
<input type="hidden" name="task" value="viewdevices"/>
<html:hidden property="deviceId"/>
<html:hidden property="pageNumber"/>

<!-- Table Having List of Loaned Devices Starts -->
<table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
    <tr>
        <th>
            <bean:message key="DeviceOnLoan.AllianceName"
                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceOnLoan.ESNDEC"
                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceOnLoan.ESNHEX"
                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceOnLoan.MTN"
                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceOnLoan.EmailRequestSent"
                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceOnLoan.Status"
                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceOnLoan.Edit"
                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
        </th>
    </tr>
    <%String strCCode = "#ffffff";%>
    <%boolean bRecordFound = false;%>
    <logic:iterate id="devicedetail" name="AimsLoanedDevices"
                   type="com.netpace.aims.model.masters.AimsAllianceLoanDevic">
        <tr bgcolor="<%=strCCode%>">
            <td>
                <bean:write name="devicedetail" property="allianceCompanyName"/>
            </td>
            <td>
                <a href="devicesonloan.do?task=viewdetails&loanDeviceId=<bean:write name="devicedetail" property="loanDeviceId" />">
                    <bean:write name="devicedetail" property="esnDec"/>
                </a> &nbsp;
                <input type="hidden" name="loanDeviceIds"
                       value="<bean:write name="devicedetail" property="loanDeviceId" />"/>
            </td>
            <td>
                <bean:write name="devicedetail" property="esn"/>
                &nbsp;</td>
            <td>
                <bean:write name="devicedetail" property="mtn"/>
                &nbsp;</td>
            <td align="center"><input type=checkbox name="emailSents"
                                      value="<bean:write name="devicedetail" property="loanDeviceId" />"
            <logic:equal name="devicedetail" property="emailSent" value="Y"> checked</logic:equal> disabled>
            </td>
            <td> <bean:write name="devicedetail" property="status"/> &nbsp;</td>
            <td align="center" valign="middle">
                <a href="devicesonloan.do?task=edit&loanDeviceId=<bean:write name="devicedetail" property="loanDeviceId" />">
                    <img src="images/icon-edit.gif" alt="Edit" width="18" height="13" border="0"/>
                </a>
            </td>
        </tr>
        <% if (strCCode.equals("#ffffff")) strCCode = "#EBEBEB";
        else strCCode = "#ffffff";
            bRecordFound = true;%>
    </logic:iterate>
    <% if (!bRecordFound) {%>
    <tr>
        <td colspan="7" align="center">
            <bean:message key="DeviceOnLoan.NoRecords" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
        </td>
    </tr>
    <% } %>
</table>
<!-- Table Having List of Loaned Devices Ends -->

<!-- Navigation Bar For Paging Functionality Starts -->
<table width="100%">
    <tr align="right">
        <td height="30" align="right">
            <logic:greaterThan name="LoanedDeviceListForm" property="pageNumber" value="0">
                <a href='javascript:viewPage(<%=(LoanedDeviceListForm.getPageNumber().intValue() - 1)%>);//'><img src="images/greyRndArrowL.gif" align="absbottom"/></a>
            </logic:greaterThan>

                <b>
                    <img alt="" src="images/spacer.gif" width="3" height="1"/>Page
                    <img alt="" src="images/spacer.gif" width="3"
                         height="1"/><%=LoanedDeviceListForm.getPageNumber() == null ? "1" : java.lang.Integer.toString(LoanedDeviceListForm.getPageNumber().intValue() + 1)%>
                    <img alt="" src="images/spacer.gif" width="3" height="1"/>of
                    <img alt="" src="images/spacer.gif" width="3" height="1"/><%=TotalPages.toString()%>
                    <img alt="" src="images/spacer.gif" width="3" height="1"/>
                </b>

            <logic:lessThan name="LoanedDeviceListForm" property="pageNumber"
                            value="<%=java.lang.Integer.toString(TotalPages.intValue()-1)%>">
                <a href='javascript:viewPage(<%=LoanedDeviceListForm.getPageNumber() == null ? 1:(LoanedDeviceListForm.getPageNumber().intValue() + 1)%>);//'><img src="images/greyRndArrow.gif" align="absbottom"/></a>
            </logic:lessThan>
        </td>
    </tr>
</table>
<!-- Navigation Bar For Paging Functionality Ends -->

<html:hidden property="allianceName"/>
<html:hidden property="allianceMemberName"/>
<html:hidden property="esnDec"/>
<html:hidden property="esn"/>
<html:hidden property="mtn"/>
<html:hidden property="emailSent"/>
<html:hidden property="status"/>
</html:form>
</DIV>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
    <tr>
        <td width="85%" align="right">
            <a href="devicesonloan.do?task=ExportDevice&deviceId=<bean:write name="LoanedDeviceListForm" property="deviceId"/>">Export To Excel</a>
        </td>
        <td>
            <div class="blackBtn" style="margin-left:10px;" id="Back" title="Back">
                <div>
                    <div><div onclick="window.location='/aims/devicesonloan.do'">Back</div></div>
                </div>
            </div>
            <%--<img height=15 width=52 src="images/back_b.gif" border=0 name="Back">--%>
        </td>
        <td align="left">
            <div class="redBtn" id="Create" title="Create">
                <div>
                    <div><div onclick="javascript:createNew();">Create</div></div>
                </div>
            </div>
            <%--<img height=15 width=52 src="images/create_b.gif" border=0 name=Create>--%>
            <%--<a href="javascript:createNew();//"></a>--%>
        </td>
    </tr>
</table>

</DIV>
</DIV>