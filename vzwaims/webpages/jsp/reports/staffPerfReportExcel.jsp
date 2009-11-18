<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>


<%@page contentType="application/vnd.ms-excel"%> 
<% response.setHeader("Content-disposition", "inline; filename\"PerfReport.xls\""); %>




<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<form name="perfReportForm" action="/aims/perfReport.do" method="post">
	<tr> 
		<td width="100%">
			<table width="550" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left">
						<strong>
							<font size="+1" face="Verdana, Arial, Helvetica", sans-serif">AIMS Reports</font>
						</strong><br>
						<strong>
							<font size="2" face="Verdana, Arial, Helvetica, sans-serif">Staff Performance Report</font>
						</strong>
					</td>
					<td align="right">
						<strong>
							<font size="2" face="Verdana, Arial, Helvetica, sans-serif">Employee Name</font>
						</strong>
						<select class="content" name="user_id" size="1" onChange="document.perfReportForm.submit();">
							<option value="0">Select One</option>							
							<aims:getPerformanceReport attributeName="vzw_employees"/>
						</select>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</form>
<bean:parameter id="userid" name="user_id" value="0"/> 
<logic:greaterThan name="userid" scope="page" value="0"> 
	<tr>
		<td width="100%">
			<table width="550" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td bgcolor="#999999" height="1">
						<img src="images/spacer.gif" width="20" height="1" />
					</td>
				</tr>
				<tr> 
					<td bgcolor="#999999" height="1"><img src="images/spacer.gif" width="20" height="1" /></td>
				</tr>
				<tr> 
					<td align="center" valign="middle" bgcolor="#EBEBEB"> 
						<table width="100%" border="0" cellspacing="10" cellpadding="0">
							<tr>
									<td colspan=2 class="text" width="50%" align="center">
										<strong>Verizon Wireless - Alliance Information Management System</strong>
									</td>
							</tr>
							<tr>
								<td class="text" width="50%" align="right">
									<strong>Employee Name</strong>
								</td>
								<td class="text" align="left">
									<aims:getPerformanceReport attributeName="employee_name"/>
								</td>
							</tr>
							<tr>
								<td class="text" width="50%" align="right">
									<strong>Department</strong>
								</td>
								<td class="text" align="left">
									<aims:getPerformanceReport attributeName="department_name"/>
								</td>
							</tr>
							<tr>
								<td class="text" width="50%" align="right"><strong>Report Date</strong></td>
								<td class="text" align="left""><aims:getPerformanceReport attributeName="report_date"/></td>
							</tr>
								<aims:getPerformanceReport attributeName="vzw_emp_alliances"/>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</logic:greaterThan> 
</table>

  
