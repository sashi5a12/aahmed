<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<table width="100%"	border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td	width="30">&nbsp;</td>
		<td	width="496"> <span	class="pageHeadline"><bean:message key="ReconciliationBrew.pageTitle"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span> 
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
            <html:form action="reconcileBrew.do">
              <TBODY>
              <TR>
                <TD vAlign="top" width="100%">
                <input type="hidden" name="task" value="update" />
                <input type="hidden" name="brewNstlUploadId" value="<bean:write name="BrewUpload" property="brewNstlId" />" />
                  <TABLE class=sectable height="100%" width="100%">
                    <TBODY>
                    <TR class=sectitle>
                      <TD class=aimssecheading><bean:message key="ReconciliationBrew.tableHeading"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></TD>
                    </TR>
                    <TR>
                      <TD vAlign=center align=middle bgColor=#ebebeb>
                      	<table class=tabletop width="100%" border=0 cellpadding=0 cellspacing=0>
                      		<tr align=center bgcolor=bbbbbb>
                      			<td class="firstcell" colspan=9><span class="modFormFieldLbl"><bean:message key="ReconciliationBrew.dataFileDate"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span>
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
                      			<!-- Is Already Reconcilied Starts -->
                      			<logic:present name="dEnteries" property="linkedDetails">
		                   			<td class="cell"><bean:write name="dEnteries" property="linkedDetails.companyName" /></td>
		                   			<td class="cell"><bean:write name="dEnteries" property="linkedDetails.applicationName" /></td>
		                   			<td class="cell"><bean:write name="dEnteries" property="linkedDetails.deviceModel" /></td>
		                   			<td class="cell"><bean:write name="dEnteries" property="linkedDetails.version" /></td>
		                   			<td class="cell"><input type=checkbox name="dummy" checked disabled /></td>
                      			</logic:present>
									   <!-- Is Already Reconcilied Ends -->
									   <!-- If not Already Reconcilied Starts -->
									   <logic:notPresent name="dEnteries" property="linkedDetails">
                      			<!-- Present Brew Apps Id Starts -->
                      			<logic:present name="dEnteries" property="brewAppsId">
		                   			<td class="cell"><bean:write name="dEnteries" property="companyName" /></td>
		                   			<td class="cell"><bean:write name="dEnteries" property="appTitle" /></td>
		                   			<td class="cell">
		                   			  <select name="reconcileCombineId">
		     									<logic:iterate id="device" name="dEnteries" property="supportedDevices">
		     									     <option value="<bean:write name="dEnteries" property="brewAppsId" />-<bean:write name="dEnteries" property="dataEntryId" />-<bean:write name="device" property="deviceId" />" ><bean:write name="device" property="aimsDevice.deviceModel" /></option>
		     									</logic:iterate>
		     								  </select>
		                   			</td>
		                   			<td class="cell"><bean:write name="dEnteries" property="appVersion" /></td>
		                   			<td class="cell"><input type=checkbox name="selectedCombo" value="<%=iCount++%>"></td>
                      			</logic:present>
                      			<!-- Present Brew Apps Id Ends -->
                      			<!-- Not Present Brew Apps Id Starts -->
                      			
										<logic:notPresent name="dEnteries" property="brewAppsId">
											<logic:present name="dEnteries" property="possibleMatch">
		  									<td class="cell" colspan="4">
		  									<select name="reconcileCombineId">
		     									<logic:iterate id="apps" name="dEnteries" property="possibleMatch" type="com.netpace.aims.controller.application.AllianceApplicationDeviceExt">
		     									     <option value="<bean:write name="apps" property="brewAppsId" />-<bean:write name="dEnteries" property="dataEntryId" />-<bean:write name="apps" property="deviceId" />" ><bean:write name="apps" property="displayValue" /></option>
		     									</logic:iterate>
		  									</select>
		  									</td>
		                   			<td class="cell"><input type=checkbox name="selectedCombo" value="<%=iCount++%>"></td>
		                   			</logic:present>
		                   			<logic:notPresent name="dEnteries" property="possibleMatch">
		                   			<td class="cell" colspan="5" ><bean:message key="ReconciliationCatalog.noDataMatch"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
		                   			</logic:notPresent>
										</logic:notPresent>
										<!-- Not Present Brew Apps Id Ends -->
										</logic:notPresent>
										<!-- If not Already Reconcilied Ends -->
                      		</tr>
                      		</logic:iterate>
                      	</table>
					  </TD>
					</TR>
					</TBODY>
				  </TABLE>
				</TD>
			  </TR>
			  <tr>
				<td align=right class="modFormFieldLbl">
					<table cellpadding="0" cellspacing="0" border="0" width="100%"><tr><td align=left class="modFormFieldLbl">&nbsp;Total Applications : <%=iCount%></td><td align=right class="modFormFieldLbl"><a href="reconcileBrewFileSelect.do">Reconcile Later</a>&nbsp;&nbsp;<input type="image" src="images/submit_b.gif" border=0></td></tr></table>
				</td>
		 	  </tr>
			  </TBODY>
			</html:form>
			</TABLE>
			
			<!-- Table Ends -->
					</td>
				 </tr>
				</table>
			</td>
		</tr> 
</table>