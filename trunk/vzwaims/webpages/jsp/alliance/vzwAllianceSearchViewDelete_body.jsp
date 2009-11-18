<%@ page language="java" %>

<%@ page import="com.netpace.aims.controller.alliance.*, com.netpace.aims.bo.alliance.*  " %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>


<%@ include file="/common/error.jsp" %>


<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">


<DIV class="headLeftCurveblk"></DIV>
<H1>Alliances List</H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">

<table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
    <tr>
        <th align="center">
            <a href='/aims/vzwAllianceSearch.do?task=in_search&sort_field=1&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
                <strong>
                    Alliance Name
                </strong>
            </a>
        </th>
        <th align="center">
            <a href='/aims/vzwAllianceSearch.do?task=in_search&sort_field=9&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
                <strong>
                    Alliance Administrator
                </strong>
            </a>
        </th>
        <th align="center">
            <a href='/aims/vzwAllianceSearch.do?task=in_search&sort_field=3&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
                <strong>
                    Alliance Created Date
                </strong>
            </a>
        </th>
        <th align="center">
            <a href='/aims/vzwAllianceSearch.do?task=in_search&sort_field=5&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
                <strong>
                    VZW Account Manager
                </strong>
            </a>
        </th>
        <th align="center">
            <a href='/aims/vzwAllianceSearch.do?task=in_search&sort_field=12&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
                <strong>
                    On/Off Hold
                </strong>
            </a>
        </th>
        <th align="center">
            <strong>
                <bean:message key="ManageApplicationForm.view.delete"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
            </strong>
        </th>
    </tr>
    <logic:iterate id="vzwAlliance" name="VZWAlliances" scope="request">
        <tr>
            <td class="firstcell" align="left">
                <a href='/aims/allianceStatus.do?task=view&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />'
                   class="a">
                    <bean:write name="vzwAlliance" property="companyName"/>
                </a>
            </td>
            <td align="left">
                <bean:write name="vzwAlliance" property="allianceAdminFirstName"/>
                <bean:write name="vzwAlliance" property="allianceAdminLastName"/>
                &nbsp;
            </td>
            <td align="center">
                <bean:write name="vzwAlliance" property="dateEstablished" formatKey="date.format" filter="true"/>
                &nbsp;
            </td>
            <td align="left">
                <bean:write name="vzwAlliance" property="vzwAccMgrFirstName"/>
                <bean:write name="vzwAlliance" property="vzwAccMgrLastName"/>
                &nbsp;
            </td>            
            <td align="center">
                <logic:equal name="vzwAlliance" property="isOnHold" value="Y">
                    <a href='/aims/vzwAllianceSearch.do?task=in_search&sub_task=onhold&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&on_hold=N&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue()%>'
                       class="a">
                        On Hold
                    </a>
                </logic:equal>
                <logic:equal name="vzwAlliance" property="isOnHold" value="N">
                    <a href='/aims/vzwAllianceSearch.do?task=in_search&sub_task=onhold&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&on_hold=Y&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue()%>'
                       class="a">
                        Off Hold
                    </a>
                </logic:equal>
            </td>
            <td align="center">
                <a href='/aims/vzwAllianceSearch.do?task=in_search&sub_task=delete&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'
                class="a"
                onClick="javascript:if (window.confirm('Are you sure you want to delete this Alliance and ALL its related records? \n\r By clicking OK you acknowledge that all the applications, users \n\r related to this alliance will be deleted?')) { return true;} else { return false;}">
                <img src="images/icon-delete.gif" alt="Delete" width="15" height="14" border="0"/>
                </a>
                <a href="#" class="a"
                onClick="javascript:if (window.confirm('Are you sure you want to delete this Alliance and ALL its related records? \n\r By clicking OK you acknowledge that all the applications, users \n\r related to this alliance will be deleted.?')) { return true;} else { return false;}">
                </a>
            </td>
        </tr>
    </logic:iterate>
</table>


<DIV class="homeColumn lBox marginT">
    <DIV>
        <table width="100%" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td height="30" align="right" >
                    <logic:greaterThan name="page_id" value="1">
                        <a href='/aims/vzwAllianceSearch.do?task=in_search&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue() - 1%>' class="a"><img src="images/greyRndArrowL.gif" align="absbottom"/></a>
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
                        <a href='/aims/vzwAllianceSearch.do?task=in_search&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue() + 1%>' class="a"><img src="images/greyRndArrow.gif" align="absbottom"/></a>
                    </logic:lessThan>
                </td>
            </tr>
        </table>
    </DIV>
</DIV>


</DIV>
</DIV>
</DIV>


