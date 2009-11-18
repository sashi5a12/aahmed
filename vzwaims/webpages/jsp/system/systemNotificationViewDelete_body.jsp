<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<%@ include file="/common/error.jsp" %>


<div class="btnTopLine">  <%-- #8199b8--%>
    <div class="redBtn" style="float:right; margin-top:10px; margin-bottom:10px" id="Create"
         title="Create">
        <div>
            <div>
                <div onclick="window.location='systemNotificationSetup.do?task=create'">Create</div>
            </div>
        </div>
    </div>
</div>


<DIV class="homeColumnTab lBox">
    <DIV class="headLeftCurveblk"></DIV>
    <H1>
        <bean:message key="SystemNotificationForm.ListHeading" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
    </H1>

    <DIV class="headRightCurveblk"></DIV>
    <DIV class="contentbox">
        <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
            <tr>
                <th class="sortable">
                    <bean:message key="SystemNotificationForm.Subject" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
                </th>
                <th class="sortable">
                    <bean:message key="SystemNotificationForm.Desc" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
                </th>
                <th class="sortable">
                    <bean:message key="SystemNotificationForm.Edit" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
                </th>
                <th class="sortable">
                    <bean:message key="SystemNotificationForm.Delete" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
                </th>
            </tr>

            <logic:iterate id="systemNotification" name="aimsSystemNotifications">
                <tr>
                    <td align="left">
                        <bean:write name="systemNotification" property="subject"/>
                    </td>
                    <td align="left">
                        <bean:write name="systemNotification" property="description"/>
                    </td>
                    <td align="center">
                        <a href='systemNotificationSetup.do?task=edit&systemNotificationId=<bean:write name="systemNotification" property="systemNotificationId"/>'
                           class="a">
                            <img src="images/icon-edit.gif" alt="Edit" width="18" height="13" border="0"/>
                        </a>
                    </td>
                    <td align="center">
                        <a href='systemNotificationViewDelete.do?task=delete&systemNotificationId=<bean:write name="systemNotification" property="systemNotificationId"/>'
                           class="a"
                           onClick="javascript:if (window.confirm('Are you sure you want to delete this System Notification?')) { return true;} else { return false;}"><img
                                src="images/icon-delete.gif" alt="Delete" width="15" height="14" border="0"/>
                        </a>
                        <a href="#" class="a"
                           onClick="javascript:if (window.confirm('Are you sure you want to delete this System Notification?')) { return true;} else { return false;}">
                        </a>
                    </td>
                </tr>
            </logic:iterate>
        </table>
    </DIV>

    <table  cellpadding="0" cellspacing="0" border="0" align="right">
        <tr>
            <td align="right">
                <div style="margin-top:10px; float:right">
                    <div class="redBtn" id="login" title="Create">
                        <div>
                            <div>
                                <div onclick="window.location='systemNotificationSetup.do?task=create';">Create</div>
                            </div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</DIV>

