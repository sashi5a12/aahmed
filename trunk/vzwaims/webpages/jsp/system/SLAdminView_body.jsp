<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<jsp:useBean id="task" class="java.lang.String"	scope="request"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
        	<span class="aimsmasterheading">
				<bean:message key="SalesLeadForm.vzwWelcome" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/> - <bean:message	key="SalesLeadForm.vzwViewSalesLead"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>
			</span>           
		</td>
	</tr>
	<tr>
		<td width="20">&nbsp;</td>
		<td height="20">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	               <td> 
	            	<logic:messagesPresent>
	      			<html:messages id="error" header="errors.header" footer="errors.footer">
	      				<bean:write name="error"/><br />
	      			</html:messages>
	            	</logic:messagesPresent>		
	         		</td>
	         	</tr>
	            <tr>
	            	<td align="center" valign="middle" bgcolor="#EBEBEB"> 
                  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr><td width="100%">
								<table class="sectable" width="100%">
									<tr class="sectitle"><td class="aimssecheading">Sales Lead</td></tr>
									<tr>
										<td	align="center" valign="middle">
											<table width="100%"	border="0" cellspacing="5"	cellpadding="0">
												<tr valign="top">
													<td	class="modFormFieldLbl" width="50%"><bean:message key="SalesLeadForm.allianceName"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write name="SalesLeadForm" property="allianceName" />
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.submittedBy"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write name="SalesLeadForm" property="submittedBy" />
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.submittedDate"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write name="SalesLeadForm" property="submittedDate" formatKey="date.format" filter="true"/>
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.salesLeadDesc"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write name="SalesLeadForm"	property="salesLeadDesc"/>
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.leadQualification" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write name="SalesLeadForm"	property="leadQualification" />
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.estimatedSize"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write name="SalesLeadForm"	property="estimatedSize" />
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.purschaseDescisionDate" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write name="SalesLeadForm"	property="purchaseDecision"/>
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.status" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write  name="SalesLeadForm" property="statusDesc"/>
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.comments" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write name="SalesLeadForm"	property="comments" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td></tr>
						</table>
					</td>
				<tr>
					<td height="27" valign="bottom" align="right"><a href="SLAdminSetup.do?task=edit&salesLeadId=<bean:write name="SalesLeadForm"	property="salesLeadId" />"><img	src="images/edit_b.gif" border="0" /></a> <a href="SLAdminSetup.do"><html:img	src="images/back_b.gif" border="0" width="52" height="15" /></a></td>
				</tr>
				</tr>
			</table>
		</td>
	</tr>
</table>




