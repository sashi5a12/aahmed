<%@ page language="java" %>
<%@ page import="com.netpace.aims.util.AimsUtils, com.netpace.aims.model.masters.AimsDevic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>

<!-- DATA GRID START HERE -->


<div class="btnTopLine">
    <div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="filter"
         title="Filter">
        <div>
            <div>
                <div onclick="window.location='devicesSetup.do?task=create'">Create</div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/common/error.jsp" %>
<DIV class="homeColumnTab lBox">
<DIV class="headLeftCurveblk"></DIV>
<H1>
    <bean:message key="DeviceForm.viewHeading" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
</H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
    <tr>
        <th>
            <bean:message key="DeviceForm.phoneModel" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceForm.phoneManufacturer" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceForm.VZWPlatformsSupported"
                          bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceForm.manufacturerURL" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceForm.status" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceForm.Edit" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
        </th>
        <th>
            <bean:message key="DeviceForm.Delete" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
        </th>
    </tr>
    <logic:iterate id="device" name="AimsDevices">
        <tr>
            <td align="left">
                <bean:write name="device" property="deviceModel"/>
            </td>
            <td align="left">
                <bean:write name="device" property="deviceMfgBy"/>
            </td>
            <td align="left">
                <logic:iterate id="pf" name="device" property="platform">
                    <bean:write name="pf" property="platformName"/>
                    <BR/>
                </logic:iterate>
            </td>
            <td align="left">
                <a class="a" href='<%= AimsUtils.appendProtocol(((AimsDevic)device).getMfgWebSiteUrl())%>'
                   target="_blank">
                    <bean:write name="device" property="mfgWebSiteUrl"/>
                </a>
            </td>
            <td align="left">
                <bean:write name="device" property="status"/>
            </td>
            <td align="center">
                <a href="/aims/devicesSetup.do?task=update&device_id=<bean:write name="device" property="deviceId"/>"
                   class="a"> <img src="images/icon-edit.gif" alt="Edit" width="18" height="13" border="0"/>
                </a>
            </td>
            <td align="center">
                <a href="/aims/devices.do?task=delete&device_id=<bean:write name="device" property="deviceId"/>"
                   class="a"
                   onClick="javascript:if (window.confirm('Are you sure you want to delete this device?')) { return true;} else { return false;}">
                    <img src="images/icon-delete.gif" alt="Delete" width="15" height="14" border="0"/>
                </a>
                    <%--
                    <a href="#" class="a"
                       onClick="javascript:if (window.confirm('Are you sure you want to delete this device?')) { return true;} else { return false;}">
                    </a>
                    --%>
            </td>
        </tr>
    </logic:iterate>
</table>
<!-- DATA GRID END HERE -->

<!-- PAGER START HERE -->
<table width="100%" cellpadding="0" cellspacing="0" border="0" style="margin-top:10px">
    <tr>
        <td align="right">
            <logic:greaterThan name="page_id" value="1">
                <a href='devices.do?task=view&page_id=<%=page_id.intValue() - 1%>' class="a"><img src="images/greyRndArrowL.gif" align="absbottom"/></a>
            </logic:greaterThan>
            <logic:greaterThan name="page_max" value="1">
                <b>
                    <img alt="" src="images/spacer.gif" width="3" height="1"/>Page
                    <img alt="" src="images/spacer.gif" width="3" height="1"/><%=page_id.toString()%>
                    <img alt="" src="images/spacer.gif" width="3" height="1"/>of
                    <img alt="" src="images/spacer.gif" width="3" height="1"/><%=page_max.toString()%>
                    <img alt="" src="images/spacer.gif" width="3" height="1"/>
                </b>
            </logic:greaterThan>
            <logic:lessThan name="page_id" value="<%=page_max.toString()%>">
                <a href='devices.do?task=view&page_id=<%=page_id.intValue() + 1%>' class="a"><img src="images/greyRndArrow.gif" align="absbottom"/> </a>
            </logic:lessThan>
        </td>
    </tr>
</table>
<!-- PAGER ENDS HERE -->

<table align="right" width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Create" title="Create">
                <div>
                    <div>
                        <div onclick="window.location='/aims/devicesSetup.do?task=create'">Create</div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>

</DIV>
</DIV>