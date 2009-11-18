<%@ page language="java" %>

<%@ page
        import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.accounts.* " %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<bean:parameter id="taskForThisPage" name="task" value="Nopes"/>

<%@ include file="/common/error.jsp" %>


<html:form action="/sysRolesEdit">

<html:hidden property="roleId"/>
<html:hidden property="roleType"/>
<logic:match name="taskForThisPage" scope="page" value="create">
    <html:hidden property="task" value="create"/>
</logic:match>
<logic:match name="taskForThisPage" scope="page" value="edit">
    <html:hidden property="task" value="edit"/>
</logic:match>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<DIV class="headLeftCurveblk"></DIV>
<H1>Role</H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">

    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th align="right"><strong>Role Name</strong></th>
            <th align="left">
                <html:text disabled="true" property="roleName" size="40" styleClass="inputField"/>
            </th>
        </tr>
        <tr>
            <td align="left"><strong>Role Description</strong></td>
            <td align="left">
                <html:textarea readonly="true" property="roleDescription" rows="3" cols="50"
                               styleClass="textareaField"/>
            </td>
        </tr>
    </table>
    <div>&nbsp;</div>
    <table width="100%" border="1" cellspacing="0" cellpadding="5" class="Grid2">
                    <tr>
                        <th align="left">
                            <strong>Privileges available</strong>
                        </th>
                        <th align="center">
                            <strong>Select</strong>
                        </th>
                        <th align="center">
                            <strong>Create</strong>
                        </th>
                        <th align="center">
                            <strong>Update</strong>
                        </th>
                        <th align="center">
                            <strong>Delete</strong>
                        </th>
                    </tr>
                    <logic:iterate id="sp" name="AimsSysPrivileges"
                                   type="com.netpace.aims.model.security.AimsSysPrivilege">
                        <tr>
                            <td align="left">
                                <bean:write name="sp" property="privilegeName"/>
                            </td>
                            <td align="center">
                                <html:multibox disabled="true" property="selects">
                                    <bean:write name="sp" property="privilegeId"/>
                                </html:multibox>
                            </td>
                            <td align="center">
                                <html:multibox disabled="true" property="creates">
                                    <bean:write name="sp" property="privilegeId"/>
                                </html:multibox>
                            </td>
                            <td align="center">
                                <html:multibox disabled="true" property="updates">
                                    <bean:write name="sp" property="privilegeId"/>
                                </html:multibox>
                            </td>
                            <td align="center">
                                <html:multibox disabled="true" property="deletes">
                                    <bean:write name="sp" property="privilegeId"/>
                                </html:multibox>
                            </td>
                        </tr>
                    </logic:iterate>
                </table>
    <div>&nbsp;</div>
    <bean:parameter id="role_id" name="roleId" value="0"/>
    <table width="100%" cellpadding="0" cellspacing="0" border="0" >
        <tr align="right">
            <td>
                <div class="redBtn" style="float:right;padding-left:10px" id="Delete" title="Delete">
                    <div>
                        <div>
                            <div onclick="window.location='/aims/sysRoles.do?task=delete&roleId=<bean:write name="role_id"/>'">
                                Delete
                            </div>
                        </div>
                    </div>
                </div>
                <div class="blackBtn" style="float:right;" id="Edit" title="Edit">
                    <div>
                        <div>
                            <div onclick="window.location='/aims/sysRolesSetup.do?task=editForm&roleId=<bean:write name="role_id" />'">
                                Edit
                            </div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</div>
</div>
</div>
</html:form>
