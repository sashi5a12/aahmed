<%@ page language="java" %>
<%--
    <%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.accounts.* "%>
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script language="javascript">
    function truncateLocalTextAreas()
    {
        if (typeof document.forms[0].roleDescription != "undefined")
            if (typeof document.forms[0].roleDescription.value != "undefined")
                TruncateText(document.forms[0].roleDescription, 500);
    }
</script>

<%@ include file="/common/error.jsp" %>

<bean:parameter id="taskForThisPage" name="task" value="Nopes"/>

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

    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient" onmousemove="truncateLocalTextAreas();">
        <tr>
            <th align="left"><strong>Role Name</strong></th>
            <th align="left">
                <html:text property="roleName" maxlength="50" size="40" styleClass="inputField"/>
            </th>
        </tr>
        <tr>
            <td align="left"><strong >Role Description</strong></td>
            <td align="left">
                <html:textarea property="roleDescription" rows="3" cols="50" onkeyup="LimitText(this,500)"
                               onkeypress="LimitText(this,500)"/>
            </td>
        </tr>
    </table>    

    <div>&nbsp;</div>
    <table width="100%"  border="1" cellspacing="0" cellpadding="5" class="Grid2">
                    <tr >
                        <th align="left">
                            <strong >Privileges available</strong>
                        </th>
                        <th align="center">
                            <strong >Select</strong>
                        </th>
                        <th align="center">
                            <strong >Create</strong>
                        </th>
                        <th  align="center">
                            <strong >Update</strong>
                        </th>
                        <th  align="center">
                            <strong >Delete</strong>
                        </th>
                    </tr>
                    <logic:iterate id="sp" name="AimsSysPrivileges"
                                   type="com.netpace.aims.model.security.AimsSysPrivilege">
                        <tr>
                            <td  align="left">
                                <bean:write name="sp" property="privilegeName"/>
                            </td>
                            <td  align="center">
                                <html:multibox property="selects">
                                    <bean:write name="sp" property="privilegeId"/>
                                </html:multibox>
                            </td>
                            <td  align="center">
                                <html:multibox property="creates">
                                    <bean:write name="sp" property="privilegeId"/>
                                </html:multibox>
                            </td>
                            <td  align="center">
                                <html:multibox property="updates">
                                    <bean:write name="sp" property="privilegeId"/>
                                </html:multibox>
                            </td>
                            <td  align="center">
                                <html:multibox property="deletes">
                                    <bean:write name="sp" property="privilegeId"/>
                                </html:multibox>
                            </td>
                        </tr>
                    </logic:iterate>
                </table>
    <div>&nbsp;</div>
    <table width="100%" cellpadding="0" cellspacing="0" border="0">
        <tr><td>&nbsp;</td></tr>
        <tr align="right">
            <td>
                <div class="redBtn" style="float:right;padding-left:10px" id="Submit" title="Submit">
                    <div>
                        <div>
                            <div onclick="document.forms[0].submit()">
                                Submit
                            </div>
                        </div>
                    </div>
                </div>
                <!--<input type="image" name="AllSubmit" src="images/submit_b.gif" width="52" height="15" border="0"/>-->
            </td>
        </tr>
    </table>
</html:form>
