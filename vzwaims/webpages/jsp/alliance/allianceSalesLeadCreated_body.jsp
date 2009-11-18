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
				<bean:message key="SalesLeadForm.allianceWelcome" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>
			</span>
       	</td>
  	</tr>
	<tr> 
       	<td width="20">&nbsp;</td>
       	<td width="100%">       
            <aims:getAllianceTab attributeName="Submit Sales Lead"/>
       	</td>
  	</tr>
  	<tr> 
       	<td width="20">&nbsp;</td>
       	<td width="100%">&nbsp;
			
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
					<table width="100%" border="0" cellspacing="1" cellpadding="0">
						<tr>
							<td width="100%" valign=top">
								<table class="sectable" width="100%" height="100%">
									<tr class="sectitle"><td class="aimssecheading">Sales Lead Submitted</td></tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td	class="text" align="left" ><bean:message key="SalesLeadForm.salesLeadSubmitted" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td></tr>
			</table>
		</td>
	</tr>
</table>




