<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<jsp:useBean id="SalesLeadForm" class="com.netpace.aims.controller.alliance.SalesLeadForm"	scope="request"/>


<script  language="JavaScript">

	function truncateLocalTextAreas()
	{
		TruncateTextWithCount(document.forms[0].salesLeadDesc,'textCountSalesLeadDesc',1000);
		TruncateTextWithCount(document.forms[0].companySolution,'textCountCompanySolution',1000);
		TruncateTextWithCount(document.forms[0].leadQualification,'textCountLeadQualification',1000);
		TruncateTextWithCount(document.forms[0].comments,'textCountComments',1000);
	}

	function trackCountForTextAreas()
	{
		TrackCount(document.forms[0].salesLeadDesc,'textCountSalesLeadDesc',1000);
		TrackCount(document.forms[0].companySolution,'textCountCompanySolution',1000);
		TrackCount(document.forms[0].leadQualification,'textCountLeadQualification',1000);
		TrackCount(document.forms[0].comments,'textCountComments',1000);
	}
	
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
        	<span class="aimsmasterheading">
				<bean:message key="SalesLeadForm.vzwWelcome" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/> - <bean:message	key="SalesLeadForm.vzwEditSalesLead"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>
			</span>           
		</td>
	</tr>
	<tr>
	    <html:form action="/SLAdminUpdate.do?task=update">
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
									<tr class="sectitle"><td class="aimssecheading"><bean:message key="SalesLeadForm.welcome"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/></td></tr>
									<tr>
										<td	align="center" valign="middle">
											<table width="100%"	border="0" cellspacing="10"	cellpadding="0">
												
												<html:hidden property="salesLeadId"/>
												<tr valign="top">
													<td	class="modFormFieldLbl" width="55%"><bean:message key="SalesLeadForm.allianceName"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write name="SalesLeadForm" property="allianceName" />
														<input type='hidden' name='allianceName' value='<bean:write name="SalesLeadForm" property="allianceName" />'>
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.submittedBy"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write name="SalesLeadForm" property="submittedBy" />

														<br/>
														<a href="mailto:<bean:write name="SalesLeadForm" property="submittedByEmailAddress" />"><bean:write name="SalesLeadForm" property="submittedByEmailAddress" /></a>
														<br>
														<bean:write name="SalesLeadForm" property="submittedByPhone" />
														
														<input type='hidden' name='submittedBy' value='<bean:write name="SalesLeadForm" property="submittedBy" />'>

													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.submittedDate"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<bean:write name="SalesLeadForm" property="submittedDate" formatKey="date.format" filter="true"/>	
														<html:hidden name="hidden" property="submittedDate" value="<%=SalesLeadForm.getSubmittedDate()%>" />

													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl">Alliance solutions for this lead:</td>
													<td	class="text">
														<logic:iterate id="salesLeadSubApp" name="appList" scope="request">
				                       						<bean:write name="salesLeadSubApp" property="title" /><br/>
				                       					</logic:iterate>														
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.salesLeadDesc"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/><span class="mainReqAstrx">*</span>:</td>
													<td	class="text">
															<html:textarea	property="salesLeadDesc" rows="5" cols="40" onkeyup="TrackCount(this,'textCountSalesLeadDesc',1000)" onkeypress="LimitText(this,1000)"/>
															<bean:message key="message.textarea.char.remaining"/>
															<input type="text" name="textCountSalesLeadDesc" size="4" value="1000" disabled="true" /> 
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.leadQualification" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/><span class="mainReqAstrx">*</span>:</td>
													<td	class="text">
															<html:textarea	property="leadQualification" rows="5"	cols="40" style="text" onkeyup="TrackCount(this,'textCountLeadQualification',1000)" onkeypress="LimitText(this,1000)"/>
															<bean:message key="message.textarea.char.remaining"/>
															<input type="text" name="textCountLeadQualification" size="4" value="1000" disabled="true" /> 
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.estimatedSize"	bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/><span class="mainReqAstrx">*</span>:</td>
													<td	class="text">
														<html:text	property="estimatedSize" size="10" maxlength="8" />
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.purschaseDescisionDate" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/><span class="mainReqAstrx">*</span>:</td>
													<td	class="text">
														<html:text	property="purchaseDecision"	size="20"/>
														<img name="daysOfMonth2Pos" onclick="toggleDatePicker('daysOfMonth2','SalesLeadForm.purchaseDecision')" id="daysOfMonth2Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth2"/></td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.status" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
														<html:select property="status" size="1">
														   <html:option value="N">New</html:option>
															<html:option value="I">In Progress</html:option>
															<html:option value="C">Closed</html:option>
														</html:select>
													</td>
												</tr>
												<tr valign="top">
													<td	class="modFormFieldLbl"><bean:message key="SalesLeadForm.comments" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:</td>
													<td	class="text">
															<html:textarea	property="comments" rows="5" cols="40" onkeyup="TrackCount(this,'textCountComments',1000)" onkeypress="LimitText(this,1000)"/>
															<bean:message key="message.textarea.char.remaining"/>
															<input type="text" name="textCountComments" size="4" value="1000" disabled="true" /> 
				    								</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td></tr>
						</table>
					</td>
				</tr>
				<tr>
					<td	height="27" align="right" valign="bottom"><input type="image"	src="images/submit_b.gif" border="0" /> <a href="SLAdminSetup.do"><html:img	src="images/cancel_b.gif" border="0" width="52" height="15" /></a></td>
				</tr>
			</table>
			</html:form>
		</td>
	</tr>
</table>

<script	language="javascript">
   trackCountForTextAreas();				
</script>



													