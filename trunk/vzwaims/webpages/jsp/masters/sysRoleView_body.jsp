<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<html:errors/>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="0">&nbsp;</td>
		<td width="100%" valign="top"> 
			<p><span class="aimsmasterheading"><bean:message key="SysRoleForm.PageHeading" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/></span></p>
			<table width="550" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td bgcolor="#999999" height="1"><img src="images/spacer.gif" width="20" height="1" /></td>
				</tr>
				<tr> 
					<td align="center" valign="middle" bgcolor="#EBEBEB"> 
						<table width="100%" border="0" cellspacing="10" cellpadding="0">
							<tr> 
								<td colspan=2 class="text" align="center" class="aimsmasterheading"> <bean:message key="SysRoleForm.TableHeading" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/></td>
							</tr>
							<tr> 
								<td colspan=2 > 
									<table width="100%" class="tabletop" cellspacing="0" cellpadding="5" >
										<tr bgcolor="#BBBBBB"> 
											<td class="firstcell" align="center"><b><bean:message key="SysRoleForm.RoleName" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/></b></td>
											<td class="firstcell" align="center"><strong><bean:message key="SysRoleForm.RoleDescription" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/></strong></td>
											<td class="firstcell" align="center"><strong><bean:message key="SysRoleForm.Edit" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/></strong></td>
											<td class="firstcell" align="center"><strong><bean:message key="SysRoleForm.Delete" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/></strong></td>
										</tr>
										<logic:iterate id="sysRoles" name="AimsSysRoles">
										<tr bgcolor="#BBBBBB">					 
											<td class="cell" align="left"><bean:write name="sysRoles" property="roleName" />&nbsp;</td>
											<td class="cell" align="left"><bean:write name="sysRoles" property="roleDescription" />&nbsp;</td>
											<td class="cell" align="center">
												<a href='/aims/sysRolesEdit.do?task=editForm&role_id=<bean:write name="sysRoles" property="roleId"/>' class="modulecontentlink">Edit</a>
											</td>
											<td class="cell" align="center">
												<a href='/aims/sysRoles.do?task=delete&role_id=<bean:write name="sysRoles" property="roleId"/>' class="modulecontentlink">Delete</a>
											</td>
										</tr>
										</logic:iterate>
									</table>
								</td>
							</tr>
							<tr> 
								<td colspan="2" align="left">
									<a href="/aims/sysRolesEdit.do?task=createForm">
										<img src="images/create_b.gif" width="52" height="15" border="0" alt="Create">
									</a>
								</td>                      
							</tr>
						</table>
					</td>
				</tr>
				<tr> 
					<td height="1" bgcolor="#999999"><img src="images/spacer.gif" width="20" height="1" /></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

