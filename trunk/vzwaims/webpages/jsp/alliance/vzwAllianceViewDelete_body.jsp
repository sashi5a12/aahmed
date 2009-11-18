<%@ page language="java" %>

<%@ page import="com.netpace.aims.controller.alliance.*, com.netpace.aims.bo.alliance.*  " %>

<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>

<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>

<%@ include file="/common/error.jsp" %>

<DIV class="homeColumnTab lBox">
<DIV class="headLeftCurveblk"></DIV>
<H1>
    Alliances List
</H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">

<table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
<tr>
    <th >
        <a href='/aims/vzwAlliance.do?task=view&sort_field=1&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
            <strong>
                Alliance Name
            </strong>
        </a>
    </th>
    <th >
        <a href='/aims/vzwAlliance.do?task=view&sort_field=9&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
            <strong>
                Alliance Administrator
            </strong>
        </a>
    </th>
    <th >
        <a href='/aims/vzwAlliance.do?task=view&sort_field=3&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
            <strong>
                Alliance Created Date
            </strong>
        </a>
    </th>
    <th >
        <a href='/aims/vzwAlliance.do?task=view&sort_field=5&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
            <strong>
                VZW Account Manager
            </strong>
        </a>
    </th>    
    <th >
        <a href='/aims/vzwAlliance.do?task=view&sort_field=12&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
            <strong>
                On/Off Hold
            </strong>
        </a>
    </th>
    <th >
        <strong>
            <bean:message key="ManageApplicationForm.view.delete"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </strong>
    </th>
</tr>
<logic:iterate id="vzwAlliance" name="VZWAlliances" scope="request">
    <tr>
        <td >
            <a href='/aims/allianceStatus.do?task=view&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />'
               class="a">
                <bean:write name="vzwAlliance" property="companyName"/>
            </a>
        </td>
        <td >
            <a class="a" href='mailto:<bean:write name="vzwAlliance" property="allianceAdminEmailAddress" />'>
                <bean:write name="vzwAlliance" property="allianceAdminFirstName"/>
                <bean:write name="vzwAlliance" property="allianceAdminLastName"/>
            </a> &nbsp;
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
                <a href='/aims/vzwAlliance.do?task=onhold&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&on_hold=N&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue()%>'
                   class="a">
                    On Hold
                </a>
            </logic:equal>
            <logic:equal name="vzwAlliance" property="isOnHold" value="N">
                <a href='/aims/vzwAlliance.do?task=onhold&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&on_hold=Y&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue()%>'
                   class="a">
                    Off Hold
                </a>
            </logic:equal>
        </td>
        <td align="center">
            <a href="/aims/vzwAlliance.do?task=delete&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue()%>"
               class="a"
               onClick="javascript:if (window.confirm('Are you sure you want to delete this Alliance and ALL its related records? \n\r By clicking OK you acknowledge that all the applications, users \n\r related to this alliance will be deleted?')) { return true;} else { return false;}"><img
                    src="images/icon-delete.gif" alt="Delete" width="15" height="14" border="0"/>
            </a>
            <a href="#" class="a"
               onClick="javascript:if (window.confirm('Are you sure you want to delete this Alliance and ALL its related records? \n\r By clicking OK you acknowledge that all the applications, users \n\r related to this alliance will be deleted?')) { return true;} else { return false;}">
            </a>
        </td>
    </tr>
</logic:iterate>
</table>

<logic:greaterThan name="page_max" value="1">
    <table width="100%" cellpadding="0" cellspacing="0" align="center" style="margin-top:10px">
        <tr>
            <td align="right">
                <%
                    int startPageCount = 0;
                    if (page_id.intValue() % 10 == 0)
                        startPageCount = page_id.intValue() - 10 + 1;
                    else
                        startPageCount = page_id.intValue() - (page_id.intValue() % 10) + 1;
                %>
                <logic:greaterThan name="page_id" value="1">
                    <a href='/aims/vzwAlliance.do?task=view&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue() - 1%>'
                       class="a"><strong>Previous</strong></a><img src="images/spacer.gif" width="10" height="1"/>
                    <%if (startPageCount - 10 > 0) {%>
                    <a href='/aims/vzwAlliance.do?task=view&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=startPageCount - 10%>'
                       class="a"><img src="images/greyRndArrowL.gif" align="absbottom"/></a>
                        <!--<img src="images/previous_icon.gif" height="15" border="0" align="absbottom"/>-->


                    <img src="images/spacer.gif" width="3" height="1"/>
                    <% } %>
                </logic:greaterThan>

                <% for (int pageCount = startPageCount; pageCount < startPageCount + 10; pageCount++) {%>
                <%if (pageCount > 0 && pageCount <= page_max.intValue()) {%>
                <%if (pageCount == page_id.intValue()) {%>
                <b><%=pageCount%><img src="images/spacer.gif" width="1" height="1"/></b>
                <% } else { %>
                <a href='/aims/vzwAlliance.do?task=view&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=pageCount%>'
                   class="a"><%=pageCount%>
                </a><img src="images/spacer.gif" width="1" height="1"/>
                <% } %>
                <% } %>
                <% } %>

                <logic:lessThan name="page_id" value="<%=page_max.toString()%>">
                    <%if (startPageCount + 10 <= page_max.intValue()) {%>
                    <img src="images/spacer.gif" width="3" height="1"/><a
                        href='/aims/vzwAlliance.do?task=view&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=startPageCount + 10%>'
                        class="a"><img src="images/greyRndArrow.gif" align="absbottom"/></a>
                    <% } %>
                    <img src="images/spacer.gif" width="10" height="1"/><a
                        href='/aims/vzwAlliance.do?task=view&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue() + 1%>'
                        class="a"><strong>Next</strong></a>
                </logic:lessThan>
            </td>
        </tr>
    </table>
    <html:form action="/vzwAlliance">
            <input type="hidden" name="task" value="view"/>
            <input type="hidden" name="sort_field"
            value="<bean:write name="VZWAllianceForm" property="sortField"/>"/>
            <input type="hidden" name="alliance_id"
            value="<bean:write name="vzwAlliance" property="allianceId"/>"/>
            <input type="hidden" name="filter_field"
            value="<bean:write name="VZWAllianceForm" property="filterField"/>"/>
            <input type="hidden" name="filter_text"
            value="<bean:write name="VZWAllianceForm" property="filterText"/>"/>
            <input type="hidden" name="all_type"
            value="<bean:write name="VZWAllianceForm" property="allianceType"/>"/>

        <table cellpadding="0" cellspacing="0" style="margin-top:10px" align="right">
            <tr>
                <td>
                    <strong>Jump to page&nbsp;</strong>
                </td>
                <td>
                    <input type="text" name="page_id" size="4" value="<%=page_id.toString()%>">
                </td>
                <td>
                    <strong>&nbsp;of <%=page_max.toString()%></strong>
                </td>
                <td>
                    <div class="redBtn" style="float:right; margin-left:10px" id="Go" title="Go">
                        <div>
                            <div>
                                <div onclick="document.forms[1].submit()">Go</div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </html:form>
</logic:greaterThan>
</div>
</div>
