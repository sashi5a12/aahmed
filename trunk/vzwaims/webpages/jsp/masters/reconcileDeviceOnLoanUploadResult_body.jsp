<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<jsp:useBean id="LoanedDeviceListForm" class="com.netpace.aims.controller.devices.LoanDeviceListForm" scope="request"/>

<%@ include file="/common/error.jsp" %>


<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<html:form action="/reconcileDeviceOnLoanFileUpload" enctype="multipart/form-data">

<DIV class="headLeftCurveblk"></DIV>
<H1>Device Information</H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th>
    <strong><bean:message key="DeviceOnLoan.Device" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>:</strong>&nbsp;
    <bean:write name="AimsLoanDeviceExtz" property="deviceModel"/>
            </th>
        </tr>
        <tr>
            <td >
    <strong><bean:message key="DeviceOnLoan.Manufacturer" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>:</strong>&nbsp;
    <bean:write name="AimsLoanDeviceExtz" property="deviceMfgBy"/>
            </td>
        </tr>
    </table>
</div>

<div>&nbsp;</div>    

<DIV class="headLeftCurveblk"></DIV>
<H1>List of Loaned Devices</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">

    <!-- Table Having List of Loaned Devices Starts -->
    <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
        <tr>
            <th ><strong>ESN-DEC</strong></th>
            <th ><strong>MTN</strong></th>
            <th ><strong>Developer</strong></th>
            <th ><strong>Contact Name</strong></th>
            <th ><strong>Result</strong></th>
        </tr>
        <%String strCCode = "#ffffff";%>
        <%boolean bRecordFound = false;%>
        <logic:iterate id="devicedetail" name="AimsLoanedDevices"
                       type="com.netpace.aims.model.masters.AimsAllianceLoanDevic">
            <tr bgcolor="<%=strCCode%>">
                <td class="firstcell">
                    <bean:write name="devicedetail" property="esnDec"/>
                </td>
                <td >
                    <bean:write name="devicedetail" property="mtn"/>
                </td>
                <td >
                    <bean:write name="devicedetail" property="allianceCompanyName"/>
                    &nbsp;</td>
                <td >
                    <bean:write name="devicedetail" property="allianceMemberName"/>
                    &nbsp;</td>
                <td >
                    <logic:match name="devicedetail" property="comments" value="ERROR">
                        <b><font color="red">
                            <bean:write name="devicedetail" property="comments"/>
                            &nbsp;</font></b>
                    </logic:match>
                    <logic:notMatch name="devicedetail" property="comments" value="ERROR">
                        <bean:write name="devicedetail" property="comments"/>
                        &nbsp;
                    </logic:notMatch>
                </td>
            </tr>
            <% if (strCCode.equals("#ffffff")) strCCode = "#EBEBEB";
            else strCCode = "#ffffff";
                bRecordFound = true;%>
        </logic:iterate>
        <% if (!bRecordFound) {%>
        <tr>
            <td colspan="5" align="center">
                <bean:message key="DeviceOnLoan.NoRecords" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
            </td>
        </tr>
        <% } %>
    </table>
    <!-- Table Having List of Loaned Devices Ends -->
</div>

</html:form>

</div>
</div>
