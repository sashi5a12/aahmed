<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
       		<span class="aimsmasterheading">
				<bean:message key="WhitePaperForm.allianceWelcomeScreen" />
			</span>
       	</td>
  	</tr>
  	<tr> 
       	<td width="20">&nbsp;</td>
       	<td width="100%">
			&nbsp;
       	</td>
  	</tr>
	<%@ include  file="/common/error.jsp" %>
	<logic:messagesPresent>
	<tr>
       	<td width="20">&nbsp;</td>
       	<td width="100%">
			<html:messages id="message"	message="true" header="messages.header"	footer="messages.footer">
				<bean:write	name="message"/><br	/>
			</html:messages>
		</td>
	</tr>
	</logic:messagesPresent>
  	<tr> 
    	<td width="20">&nbsp;</td>
    	<td align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td width="100%">
					<html:form action="/whitePaperCreate.do"	enctype="multipart/form-data">
					<html:hidden property="whitePaperId" />
					<table width="100%" border="0" cellspacing="1" cellpadding="0">
						<tr>
							<td width="100%" valign=top">
								<table class="sectable" width="100%" height="100%">
									<tr class="sectitle"><td class="aimssecheading">White Paper</td></tr>
									<tr>
										<td	class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperName"	/><span class="mainReqAstrx">*</span></td>
									</tr>
									<tr>
										<td	class="text">
											<html:text	property="whitePaperName" size="40"/>
										</td>
									</tr>
									<tr>
										<td	class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperFileName"/><span class="mainReqAstrx">*</span></td>
									</tr>
									<tr>
										<td	class="text">
											<html:file property="whitePaperFile"/>
										</td>
									</tr>
									<tr>
										<td	class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperDesc"/><span class="mainReqAstrx">*</span></td>
									</tr>
									<tr>
										<td	class="text">
											<html:textarea	property="whitePaperDesc" rows="5"	cols="60" style="text"></html:textarea>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td	height="25" valign="middle" align="right"><input type="image"	src="images/submit_b.gif" border="0" /> <a href="#"><html:img	src="images/cancel_b.gif" border="0" width="52" height="15" /></a></td>
						</tr>
					</table>
					</html:form>
				</td></tr>
			</table>
		</td>
	</tr>
</table>




