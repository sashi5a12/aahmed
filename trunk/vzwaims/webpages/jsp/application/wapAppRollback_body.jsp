<%@ page language="java"
         import="com.netpace.aims.bo.security.*, com.netpace.aims.bo.application.*, com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="sort_field" class="java.lang.String" scope="request"/>
<jsp:useBean id="sort_order" class="java.lang.String" scope="request"/>
<jsp:useBean id="filter_text" class="java.lang.String" scope="request"/>
<jsp:useBean id="filter_field" class="java.lang.String" scope="request"/>

<script language="javascript">
    function createApplication(selectedIndex)
    {
        if (document.forms[2].createApp.options[document.forms[2].createApp.selectedIndex].value == 0)
            return false;
        document.forms[2].action = document.forms[2].createApp.options[document.forms[2].createApp.selectedIndex].value;
    }
</script>

<%@ include file="/common/error.jsp"%>

<DIV class="homeColumnTab lBox">

<DIV class="headLeftCurveblk"></DIV>
<H1>WAP Applications in Submitted DCR Status</H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">

<table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
    <tr>
        <th>
            <a href='<bean:message key="ManageApplicationForm.wap.app.rollback.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=2&sort_order=asc&filter_field=<bean:write	name="filter_field"/>&filter_text=<bean:write	name="filter_text"/>'>
                <strong>
                    <bean:message key="ManageApplicationForm.view.companyName"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </strong>
            </a>
        </th>
        <th>
            <a href='<bean:message key="ManageApplicationForm.wap.app.rollback.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=4&sort_order=asc&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>'>
                <strong>
                    <bean:message key="ManageApplicationForm.view.applicationTitle"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </strong>
            </a>
        </th>
        <th>
            <a href='<bean:message key="ManageApplicationForm.wap.app.rollback.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=7&sort_order=asc&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write	name="filter_text"/>'>
                <strong>
                    <bean:message key="ManageApplicationForm.view.dateSubmitted"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </strong>
            </a>
        </th>
        <th>
            <a href='<bean:message key="ManageApplicationForm.wap.app.rollback.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=9&sort_order=asc&filter_field=<bean:write	name="filter_field"/>&filter_text=<bean:write	name="filter_text"/>'>
                <strong>
                    <bean:message key="ManageApplicationForm.view.currentStatus"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </strong>
            </a>
        </th>
        <th>
            <strong>
                Move to Pending DCR
            </strong>
        </th>
    </tr>

    <logic:empty name="AimsApplicationsInformation" scope="request">
        <tr>
            <td colspan="3" width="100%">
                <bean:message key="error.generic.no.records.for.view"/>
            </td>
        </tr>
    </logic:empty>

    <logic:iterate id="appsApp" name="AimsApplicationsInformation" scope="request"
                   type="com.netpace.aims.bo.application.WapRollbackAppInformation">
        <tr>
            <td align="left">
                <a href="/aims/allianceStatus.do?task=view&alliance_id=<bean:write	name="appsApp" property="allianceId" />"
                   class="a">
                    <bean:write name="appsApp" property="companyName"/>
                </a>
            </td>
            <td align="left">
                <a href="<bean:write name="appsApp"	property="urlSetupAction" />?task=view&appsId=<bean:write name="appsApp" property="appsId" />"
                   class="a">
                    <bean:write name="appsApp" property="title"/>
                </a>
            </td>
            <td align="left">
                <bean:write name="appsApp" property="createdDate" formatKey="date.format" filter="true"/>
                &nbsp;
            </td>
            <td align="left">
                <bean:write name="appsApp" property="phaseName"/>
            </td>
            <td align="center" valign="middle">
                <a href="rollbackWapApp.do?task=move&appsId=<bean:write name="appsApp" property="appsId"/>&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&page_id=<bean:write name="page_id"/>"
                   class="a"
                   onClick="javascript:if (window.confirm('Are you sure you want to move the application \'<bean:write name="appsApp" property="title"/>\', belonging to alliance \'<bean:write name="appsApp" property="companyName"/>\', to Pending DCR?')) { if (window.confirm('About to move application \'<bean:write name="appsApp" property="title"/>\', of alliance \'<bean:write name="appsApp" property="companyName"/>\', to Pending DCR.')) { return true;} else { return false;}} else { return false;}">
                    <bean:message key="images.rollback.icon"/>    
                </a>
            </td>
        </tr>
    </logic:iterate>
</table>

</DIV>

<logic:notEmpty name="AimsApplicationsInformation" scope="request">
    <logic:greaterThan name="page_max" value="1">
        <DIV class="contentbox" style="margin-top:10px">
            <table width="100%" cellpadding="0" cellspacing="0" border="0" >
            <tr valign="middle" align="right">
                <td>
                    <%
                        int startPageCount = 0;
                        if (page_id.intValue() % 10 == 0)
                            startPageCount = page_id.intValue() - 10 + 1;
                        else
                            startPageCount = page_id.intValue() - (page_id.intValue() % 10) + 1;
                    %>

                    <logic:greaterThan name="page_id" value="1">
                        <a href='<bean:message key="ManageApplicationForm.wap.app.rollback.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&page_id=<%=page_id.intValue() - 1%>'><strong>Previous</strong></a>
                        <img src="images/spacer.gif" width="10" height="1"/>
                        <%if (startPageCount - 10 > 0) {%>
                        <a href='<bean:message key="ManageApplicationForm.wap.app.rollback.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&page_id=<%=startPageCount - 10%>'><img src="images/greyRndArrowL.gif" height="15" border="0" align="absbottom"/></a>
                        <img src="images/spacer.gif" width="3" height="1"/>
                        <% } %>
                    </logic:greaterThan>

                    <% for (int pageCount = startPageCount; pageCount < startPageCount + 10; pageCount++) {%>
                    <%if (pageCount > 0 && pageCount <= page_max.intValue()) {%>
                    <%if (pageCount == page_id.intValue()) {%>
                    <b><%=pageCount%><img src="images/spacer.gif" width="1" height="1"/></b>
                    <% } else { %>
                    <a href='<bean:message key="ManageApplicationForm.wap.app.rollback.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&page_id=<%=pageCount%>'><%=pageCount%>
                    </a><img src="images/spacer.gif" width="1" height="1"/>
                    <% } %>
                    <% } %>
                    <% } %>

                    <logic:lessThan name="page_id" value="<%=page_max.toString()%>">
                        <%if (startPageCount + 10 <= page_max.intValue()) {%>
                        <img src="images/spacer.gif" width="3" height="1"/><a
                            href='<bean:message key="ManageApplicationForm.wap.app.rollback.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&page_id=<%=startPageCount + 10%>'><img
                            src="images/greyRndArrow.gif" height="15" border="0" align="absbottom"/></a>
                        <% } %>
                        <img src="images/spacer.gif" width="10" height="1"/><a
                            href='<bean:message key="ManageApplicationForm.wap.app.rollback.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&page_id=<%=page_id.intValue() + 1%>'><strong>Next</strong></a>
                    </logic:lessThan>
                </td>
            </tr>
        </table>
        </DIV>
        <DIV class="contentBox2">
            <table cellpadding="0" cellspacing="0"  align="right" style="margin-top:10px">
            <html:form action="/rollbackWapApp">
                <input type="hidden" name="task" value="view"/>
                <input type="hidden" name="sort_field" value="<bean:write name="sort_field"/>"/>
                <input type="hidden" name="sort_order" value="<bean:write name="sort_order"/>"/>
                <input type="hidden" name="filter_field" value="<bean:write name="filter_field"/>"/>
                <input type="hidden" name="filter_text" value="<bean:write name="filter_text"/>"/>
                <tr>
                    <td>
                        <strong>Jump to page&nbsp;</strong>
                    </td>
                    <td>
                        <input type="text" name="page_id" size="4" value="<%=page_id.toString()%>">
                    </td>
                    <td>
                        <strong>&nbsp;of <%=page_max.toString()%>
                        </strong>
                    </td>
                    <td>
                        <div class="redBtn" style=" margin-left:5px;float:right; margin-top:3px" title="Filter">
                            <div>
                                <div>
                                    <div onclick='document.forms[1].submit();'>Go</div>
                                </div>
                            </div>
                        </div>
                    </td>
                </tr>
            </html:form>
        </table>
        </DIV>
    </logic:greaterThan>
</logic:notEmpty>
</DIV>
</DIV>
</DIV>