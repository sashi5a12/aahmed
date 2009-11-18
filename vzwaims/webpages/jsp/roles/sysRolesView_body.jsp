<%@ page language="java" %>

<%--<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.bo.security.*; " %>--%>
<%@ page import="com.netpace.aims.bo.security.AimsSecurityManager" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

    <DIV class="headLeftCurveblk"></DIV>
    <H1>Role List</H1>

    <DIV class="headRightCurveblk"></DIV>
    <DIV class="contentbox">

        <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
            <tr>
                <th align="center">
                    <span>Role Name</span>
                </th>
                <th class="cell" align="center">
                    <span>Role Description</span>
                </th>
                <%
                    if (AimsSecurityManager.checkAccess(request, "ROLES",
                            AimsSecurityManager.UPDATE)) {
                %>
                <th class="cell" align="center">
                    <span>Edit</span>
                </th>
                <%
                    }
                    if (AimsSecurityManager.checkAccess(request, "ROLES",
                            AimsSecurityManager.DELETE)) {
                %>
                <th class="cell" align="center">
                    <span>Delete</span>
                </th>
                <%
                    }
                %>
            </tr>
            <logic:iterate id="role" name="AimsSysRoles">
                <tr>
                    <td align="left">
                        <a href='/aims/sysRolesSetup.do?task=editFormView&roleId=<bean:write name="role" property="roleId"/>'
                           class="a">
                            <bean:write name="role" property="roleName"/>
                        </a>
                    </td>
                    <td class="cell" align="left">
                        <bean:write name="role" property="roleDescription"/>
                        &nbsp;
                    </td>
                    <%
                        if (AimsSecurityManager.checkAccess(request, "ROLES",
                                AimsSecurityManager.UPDATE)) {
                    %>
                    <td class="cell" align="center">
                        <a href="/aims/sysRolesSetup.do?task=editForm&roleId=<bean:write name="role" property="roleId"/>"
                           class="a">
                            <img src="images/icon-edit.gif" alt="Edit" width="18" height="13" border="0"/>
                        </a>
                    </td>
                    <%
                        }
                        if (AimsSecurityManager.checkAccess(request, "ROLES",
                                AimsSecurityManager.DELETE)) {
                    %>
                    <td class="cell" align="center">
                        <a href="/aims/sysRoles.do?task=delete&roleId=<bean:write name="role" property="roleId"/>"
                           class="a"
                           onClick="javascript:if (window.confirm('Are you sure you want to delete this role?')) { return true;} else { return false;}">
                            <img src="images/icon-delete.gif" alt="Delete" width="15" height="14" border="0"/>
                        </a>
                    </td>
                    <%
                        }
                    %>
                </tr>
            </logic:iterate>
        </table>

        <% if (AimsSecurityManager.checkAccess(request, "ROLES", AimsSecurityManager.INSERT)) { %>
        <div>&nbsp;</div>
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td align="left">
                    <div class="redBtn" style="float:right;padding-left:10px" id="Create" title="Create">
                        <div>
                            <div>
                                <div onclick="window.location='/aims/sysRolesSetup.do?task=createForm'">
                                    Create
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--
                    <a href="">
                        <img src="images/create_b.gif" border="0" alt="Create">
                    </a>
                    -->
                </td>
            </tr>
        </table>
        <% } %>

    </div>
</div>
</div>


