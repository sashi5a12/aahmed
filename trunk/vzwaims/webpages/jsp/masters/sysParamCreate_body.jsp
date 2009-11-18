<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<html:errors/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="30">&nbsp;</td>
		<td width="496">
			<p>
			<span class="aimsmasterheading"><bean:message key="SysParamForm.welcome" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/></span>
			<br>
			<strong><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif"><bean:message key="SysParamForm.createHeading" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/></font></strong><br></p> </td>
		<td width="247">
			<div align="left"><font color="red" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b></div></b></font>
		</td>
	</tr>
	<tr> 
		<td width="30">&nbsp;</td>
		<td colspan="2"> 
			
			<html:form action="/sysParamsInsUpd.do" >  
				<html:hidden  property="parameterId" />
				<html:hidden  property="task" value="create" />
				<table width="498" border="0" cellspacing="0" cellpadding="0">
					<tr> 
						<td bgcolor="#999999" height="1">
							<img src="images/spacer.gif" width="20" height="1" />
						</td>
					</tr>
					<tr> 
						<td align="center" valign="middle" bgcolor="#EBEBEB"> 
							<table width="100%" border="0" cellspacing="10" cellpadding="0">
								<tr> 
									<td class="text" align="right">
										<strong><bean:message key="SysParamForm.sysParamName" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/></strong>
									</td>
									<td class="body" align="left">
										<html:text  property="parameterName" size="40"/>
									</td>
								</tr>	   
								<tr> 
									<td class="text" align="right">
										<strong><bean:message key="SysParamForm.sysParamDesc" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/></strong>
									</td>
									<td class="body" align="left">
										<html:text  property="parameterDesc" size="100"/>
									</td>
								</tr>	
								<tr> 
									<td class="text" align="right">
										<strong><bean:message key="SysParamForm.sysParamValue" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/></strong>
									</td>
									<td class="body" align="left">
										<html:text  property="parameterValue" size="40"/>
									</td>
								</tr>									
							</table>
						</td>
					</tr>
					<tr> 
						<td height="1" bgcolor="#999999">
							<img src="images/spacer.gif" width="20" height="1" />
						</td>
					</tr>
					<tr> 
						<td height="25" align="right" valign="middle"> 			
							<input type="image" name="AllSubmit" src="images/submit_b.gif" width="52" height="15" border="0" /> 
						</td>
					</tr>
					<tr> 
						<td height="1" bgcolor="#999999">
							<html:img src="images/spacer.gif" width="20" height="1" />
						</td>
					</tr>
				</table>  
			</html:form>
		</td>
	</tr>
</table>
