<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
	  
	  <html:form action="/devicesonloansend.do">
	  <html:hidden property="loanDeviceId" />
     <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width=0>&nbsp;</TD>
          <TD vAlign=top width="100%">
            <P><SPAN class=aimsmasterheading><bean:message key="DeviceOnLoan.DeviceOnLoanManagement" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></SPAN></P>
            <TABLE cellSpacing=0 cellPadding=0 width=100% border=0>
              <TBODY>
						<logic:messagesPresent>
		              <TR>
		                <TD>
								<table class="errtable" width="100%" height="100%">
									<tr class="errtitle"><td class="aimssecheading">Error</td></tr>
					      			<tr><td>
										<html:messages id="errord"	header="errors.header" footer="errors.footer">
											<span class="errorText"><bean:write	name="errord"/></span><br/>
										</html:messages>
									</td></tr>
								</table>
		                </TD>
		              </TR>
						</logic:messagesPresent>
						<logic:messagesPresent message="true">
		              <TR>
		                <TD>
								<table class="messagetable" width="100%" height="100%">
									<tr class="messagetitle"><td class="aimssecheading">Message</td></tr>
					      			<tr><td>
										<html:messages id="message"	message="true" header="messages.header"	footer="messages.footer">
											<span class="messageText"><bean:write	name="message"/></span><br/>
										</html:messages>
									</td></tr>
								</table>
		                </TD>
		              </TR>
						</logic:messagesPresent>
				 <TR><TD>
				 
					   <table class="sectable" width="100%">
						  <tr class="sectitle"><td class="aimssecheading"><bean:message key="DeviceOnLoan.EmailDeviceOnLoan" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></td></tr>
						  <tr><td>
							<table width="100%">
							  <tr> 
								<td class="modFormFieldLbl"><bean:message key="DeviceOnLoan.From" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></td>
								<td class="text">
									 <bean:write name="AIMS_USER" property="username"/><input type="hidden" name="from" value="<bean:write name="AIMS_USER" property="username"/>" />&nbsp;
								</td>
							  </tr>
							  
							  <tr> 
								<td class="modFormFieldLbl"><bean:message key="DeviceOnLoan.To" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></td>
								<td class="text">
									 <html:text property="to" size="82"/>&nbsp;
								</td>
							  </tr>
							  
							  <tr> 
								<td class="modFormFieldLbl"><bean:message key="DeviceOnLoan.Subject" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></td>
								<td class="text">
									 <html:text property="subject" size="82"/>&nbsp;
								</td>
							  </tr>
							  
							  <tr> 
								<td class="modFormFieldLbl" colspan="2"><bean:message key="DeviceOnLoan.Message" bundle="com.netpace.aims.action.DEVICE_ON_LOAN_MESSAGE"/></td>
							  </tr>
							  <tr> 
								<td class="text" colspan="2">
									 <html:textarea property="message" cols="62" rows="10" />&nbsp;
								</td>
							  </tr>
							  
							</table>
							</td></tr></table>
				 
				 </TD></TR>
              <TR>
                <TD><IMG height=5 
                              src="images/spacer.gif" 
                            width=20></TD></TR>
              <TR>
                <TD vAlign=center align=right>
        		  	       <input type="image" img border=0 src="images/send_b.gif" name="send" alt="Send Email"></a>&nbsp;
                      <a href="devicesonloan.do?task=viewdetails&loanDeviceId=<bean:write name="LoanDeviceEmailForm" property="loanDeviceId" />"><img height=15 width=52 
                      src="images/cancel_b.gif" 
                      border=0 name=Cancel alt="Cancel"></a>
              </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
              </html:form>