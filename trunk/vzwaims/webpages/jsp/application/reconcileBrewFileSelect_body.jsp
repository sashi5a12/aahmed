<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<%!
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
%>
<table width="100%"	border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td	width="30">&nbsp;</td>
		<td	width="496"> <span	class="pageHeadline"><bean:message key="ReconciliationBrew.pageTitleHistory"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span> 
		 </td>
		<td	width="247"> </td>
	</tr>
	
	<%@	include	 file="/common/error.jsp"	%>
	
	<tr> 
		<td	width="30">	&nbsp;</td>

		<td	colspan="2"> 
			<table width="100%"	border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td	bgcolor="#999999"	height="1">	 </td>
				</tr>
				<tr>
				 <td> 
				
				<!-- Table Starts -->
				
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD vAlign="top" width="100%">
                  <TABLE class=sectable height="100%" width="100%">
                    <TBODY>
                    <TR class=sectitle>
                      <TD class=aimssecheading><bean:message key="ReconciliationBrew.tableHeading"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></TD>
                    </TR>
                	  <html:form action="reconcileBrewFileRecord.do">
                    <TR>
                      <TD vAlign=center align=middle bgColor=#ebebeb>
                      	<table class=tabletop width="100%" border=0 cellpadding=0 cellspacing=0>
                      		<tr align=center bgcolor=bbbbbb>
                      			<td class="firstcell" colspan=9><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.reconcileDataFileDate"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span>
                      				<html:select property="brewNstlUploadId"	size="1" >
												<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
												<logic:iterate id ="entry" name="DataFiles" type="com.netpace.aims.model.application.AimsBrewNstlUpload">
												<html:option value="<%=entry.getBrewNstlId().toString()%>"><%=sdf.format(entry.getDataDateStamp())%></html:option>
												</logic:iterate>
											</html:select> <input type=image src="images/go_b.gif" border=0>
                      			</td>
                      		</tr>
                      	</table>
					       </TD>
					     </TR>
					    </html:form>
                    <TR>
                      <TD vAlign=center align=middle bgColor=#ebebeb>&nbsp;
                      </TD>
					     </TR>
                	  <html:form action="reconcileBrew.do">
                    <TR>
                      <TD vAlign=center align=middle bgColor=#ebebeb>
                      	<table class=tabletop width="100%" border=0 cellpadding=0 cellspacing=0>
                      		<tr align=center bgcolor=bbbbbb>
                      			<td class="firstcell" colspan=9><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.reconciledDataFileDate"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span>
                      				<html:select property="brewNstlUploadId"	size="1" >
												<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
												<logic:iterate id ="entry" name="DataFilesReconciled" type="com.netpace.aims.model.application.AimsBrewNstlUpload">
												<html:option value="<%=entry.getBrewNstlId().toString()%>"><%=sdf.format(entry.getDataDateStamp())%></html:option>
												</logic:iterate>
											</html:select> <input type=image src="images/go_b.gif" border=0>
                      			</td>
                      		</tr>
                      	</table>
					       </TD>
					     </TR>
					    </html:form>
					</TBODY>
				  </TABLE>
				</TD>
			  </TR>
			  </TBODY>
			</TABLE>
			
			<!-- Table Ends -->
					</td>
				 </tr>
				</table>
			</td>
		</tr> 
</table>