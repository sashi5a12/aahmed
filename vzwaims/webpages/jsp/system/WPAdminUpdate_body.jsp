<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
        	<span class="aimsmasterheading">
				<bean:message key="WhitePaperForm.adminWelcomeScreen" />
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
	            	<td align="center" valign="middle"> 
                  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr><td width="100%">
								<html:form action="/WPAdminUpdate.do?task=update"	>
								<html:hidden property="whitePaperId" />
								<table class="sectable" width="100%">
									<tr class="sectitle"><td colspan="2" class="aimssecheading">White Paper</td></tr>
									<tr>
										<td class="modFormFieldLbl" width="50%"><bean:message key="WhitePaperForm.whitePaperAllianceName"	/>:</td>
										<td	class="text">
											<bean:write	name="AimsWhitePaper" property="allianceName" />
										</td>
									</tr>
									<tr>
										<td	class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperSubmittedByName"	/>:</td>
										<td	class="text">
											<bean:write	name="AimsWhitePaper" property="submittedByName" />
										</td>
									</tr>
									<tr>
										<td	class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperName"	/>:</td>
										<td	class="text">
											<bean:write	name="AimsWhitePaper" property="whitePaperName" />
										</td>
									</tr>
									<tr>
										<td	class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperDesc"/>:</td>
										<td	class="text">
											<bean:write	name="AimsWhitePaper" property="whitePaperDesc" />
										</td>
									</tr>
									<tr>
										<td	class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperFileName"/>:</td>
										<td	class="text">
											<a href="whitePaperResource.do?whitePaperId=<bean:write	name="AimsWhitePaper" property="whitePaperId" />" target="_blank"><bean:write	name="AimsWhitePaper" property="whitePaperFilename" /></a>
										</td>
									</tr>
									<tr>
										<td	class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperStatus"/>:</td>
										<td	class="text">
												<html:select property="whitePaperStatus" size="1">
												<html:option value="S">Submitted</html:option>
												<html:option value="A">Active</html:option>
												<html:option value="O">On Hold</html:option>
											</html:select>
										</td>
									</tr>
								</table>
							</td></tr>
						</table>
					</td>
				</tr>
				<tr>
					<td	height="27" align="right" valign="bottom"><input type="image"	src="images/submit_b.gif" border="0" /> <a href="WPAdminSetup.do"><html:img	src="images/cancel_b.gif" border="0" width="52" height="15" /></a></td>
				</tr>
				</html:form>
			</table>
		</td>
	</tr>
</table>




