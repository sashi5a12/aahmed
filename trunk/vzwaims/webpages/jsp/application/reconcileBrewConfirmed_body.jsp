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
                <html:form action="reconcileBrew.do">
                  <TABLE class=sectable height="100%" width="100%">
                    <TBODY>
                    <TR class=sectitle>
                      <TD class=aimssecheading><bean:message key="ReconciliationBrew.tableHeadingHistory"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></TD>
                    </TR>
                    <TR>
                      <TD vAlign=center align=middle bgColor=#ebebeb>
                      	<table class=tabletop width="100%" border=0 cellpadding=0 cellspacing=0>
                      		<tr align=center bgcolor=bbbbbb>
                      			<td class="firstcell" colspan=9><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.dataFileDate"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span>
                      				<html:select property="brewNstlUploadId"	size="1" >
												<logic:iterate id ="entry" name="DataFiles" type="com.netpace.aims.model.application.AimsBrewNstlUpload">
												<html:option value="<%=entry.getBrewNstlId().toString()%>"><%=sdf.format(entry.getDataDateStamp())%></html:option>
												</logic:iterate>
											</html:select> <input type=image src="images/go_b.gif" border=0>
                      			</td>
                      		</tr>
                      		<tr align=center>
                      			<td class="firstcell" colspan=4><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.brewData"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span></td>
                      			<td class="cell" colspan=5><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.aimsData"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span></td>
                      		</tr>
                      		<tr align=center bgcolor=bbbbbb>
                      			<td class="firstcell"><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.developer"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span></td>
                      			<td class="cell"><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.application"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span></td>
                      			<td class="cell"><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.handset"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span></td>
                      			<td class="cell"><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.version"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span></td>
                      			<td class="cell"><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.alliance"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span></td>
                      			<td class="cell"><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.application"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span></td>
                      			<td class="cell"><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.device"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span></td>
                      			<td class="cell"><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.version"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span></td>
                      			<td class="cell"><span class="modFormFieldLbl"><input type=checkbox checked disabled=true></span></td>
                      		</tr>
                      		<%int iCount=0;%>
									<logic:iterate id="dEnteries" name="BrewNstlExt" type="com.netpace.aims.controller.application.BrewNstlDataExt" scope="request">
                      		<tr bgcolor=white>
                      			<td class="firstcell"><bean:write name="dEnteries" property="developerName"/></td>
                      			<td class="cell"><bean:write name="dEnteries" property="applicationName"/></td>
                      			<td class="cell"><bean:write name="dEnteries" property="handset"/></td>
                      			<td class="cell"><bean:write name="dEnteries" property="version"/></td>
                      			<logic:present name="dEnteries" property="brewAppsId">
                      			<td class="cell"><bean:write name="dEnteries" property="companyName" /></td>
                      			<td class="cell"><bean:write name="dEnteries" property="appTitle" /></td>
                      			<td class="cell"><bean:write name="dEnteries" property="deviceModel" /></td>
                      			<td class="cell"><bean:write name="dEnteries" property="appVersion" /></td>
                      			<td class="cell"><input type=checkbox name="selectedCombo" value="<%=iCount++%>" checked disabled="true"></td>
                      			</logic:present>
                      			<logic:notPresent name="dEnteries" property="brewAppsId">
                      			<td class="cell" colspan="5">&nbsp;</td>
                      			</logic:notPresent>
                      		</tr>
                      		</logic:iterate>
                      	</table>
					  </TD>
					</TR>
					</TBODY>
				  </TABLE>
				  </html:form>
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