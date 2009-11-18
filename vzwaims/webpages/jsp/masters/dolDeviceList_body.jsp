<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<jsp:useBean id="pageNo" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="pageMax" class="java.lang.Integer" scope="request"/>

<%@ include file="/common/error.jsp" %>


<div id="contentBox" style="float:left">
    <DIV class="homeColumnTab lBox">

        <DIV class="headLeftCurveblk"></DIV>
        <H1>Devices List</H1>

        <DIV class="headRightCurveblk"></DIV>
        <DIV class="contentbox">
            <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
                <tr>
                    <th align="center">
                        <strong>
                            <bean:message key="DeviceOnLoan.Device"
                                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                        </strong>
                    </th>
                    <th align="center">
                        <strong>
                            <bean:message key="DeviceOnLoan.Manufacturer"
                                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                        </strong>
                    </th>
                    <th align="center">
                        <strong>
                            <bean:message key="DeviceOnLoan.Status"
                                          bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/>
                        </strong>
                    </th>
                </tr>
                <logic:iterate id="deviceonloan" name="AimsDevicesOnLoan"
                               type="com.netpace.aims.controller.devices.AimsLoanDevicExt">
                    <tr>
                        <td align="left"><a
                                href="devicesonloan.do?task=viewdevices&deviceId=<bean:write name="deviceonloan" property="deviceId"/>">
                            <bean:write name="deviceonloan" property="deviceModel"/>
                        </a>
                            &nbsp;
                        </td>
                        <td align="left">
                            <bean:write name="deviceonloan" property="deviceMfgBy"/>
                            &nbsp;
                        </td>
                        <td align="center">
                            <bean:write name="deviceonloan" property="status"/>
                            &nbsp;
                        </td>
                    </tr>
                </logic:iterate>
            </table>

            <div>&nbsp;</div>

            <table width="100%" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="30" align="right">
                        <logic:greaterThan name="pageNo" value="1">
                            <a href='devicesonloan.do?pageNo=<%=pageNo.intValue()-1%>' class="a"><img src="images/greyRndArrowL.gif" align="absbottom"/></a>
                        </logic:greaterThan>

                        <logic:greaterThan name="pageMax" value="1">
                            <b>
                                <img alt="" src="images/spacer.gif" width="3" height="1"/>Page
                                <img alt="" src="images/spacer.gif" width="3" height="1"/><%=pageNo.toString()%>
                                <img alt="" src="images/spacer.gif" width="3" height="1"/>of
                                <img alt="" src="images/spacer.gif" width="3" height="1"/><%=pageMax.toString()%>
                                <img alt="" src="images/spacer.gif" width="3" height="1"/>
                            </b>
                        </logic:greaterThan>

                        <logic:lessThan name="page_id" value="<%=pageNo.toString()%>">
                            <a href='devicesonloan.do?pageNo=<%=pageNo.intValue() + 1%>' class="a"><img src="images/greyRndArrow.gif" align="absbottom"/></a>
                        </logic:lessThan>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td width="100%" align="right">
                        <a href="devicesonloan.do?task=Export">Export To Excel</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
