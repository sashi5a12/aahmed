<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<script language="Javascript">
<!--
	function submitReq(iTask)
	{
		if (iTask == 1)
		{
			document.forms[0].task.value='clone';
			document.forms[0].submit();
		}
		else if (iTask == 2)
		{
			document.forms[0].task.value='edit';
			document.forms[0].submit();
		}
		else if (iTask == 3)
		{
			document.forms[0].task.value='cancel';
			document.forms[0].submit();
		}
	}
//-->
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
       <td width="20">&nbsp;</td>
       <td width="100%">
       	<span class="aimsmasterheading">
       		Content Management - 
       		View Request
       	</span>               
       </td>
  </tr>
  <%@ include  file="/common/error.jsp" %>
  <tr> 
      <td width="20">&nbsp;</td>
      <td align="center" valign="middle"> 
			<html:form action="/newMktContReqCreateEdit.do">
			<html:hidden property="crequestId"/>
			<html:hidden property="task"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td width="100%">
					<table class="sectable" width="100%">
						<tr class="sectitle"><td colspan="2" class="aimssecheading">Content Request Details</td></tr>
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Program Name
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Program Start Date
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<bean:write name="ContentRequestDetail" property="programName"/>&nbsp;
							</td>
							<td class="text">
								<bean:write name="ContentRequestDetail" property="projectStartDate" formatKey="date.format" filter="true"/>&nbsp;
							</td>
						</tr>
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Vehicle for Communication
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Agency Developing Creative
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<bean:write name="ContentRequestDetail" property="vehicleComm"/>&nbsp;
							</td>
							<td class="text">
								<bean:write name="ContentRequestDetail" property="creativeAgency"/>&nbsp;
							</td>
						</tr>
					
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Area/Region Contact Name
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Area/Region Telephone Number
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<bean:write name="ContentRequestDetail" property="areaContactName"/>&nbsp;
							</td>
							<td class="text">
								<bean:write name="ContentRequestDetail" property="areaContactTel"/>&nbsp;
							</td>
						</tr>
						
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Area/Markets to be Targeted with Element
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Reach Exposure
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<bean:write name="ContentRequestDetail" property="areaTargeted"/>&nbsp;
							</td>
							<td class="text">
							   <bean:write name="ContentRequestDetail" property="exposure"/>&nbsp;
							</td>
						</tr>
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Program Highlights
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Objective of Program/Primary Message
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<textarea name="temp" rows="5" cols="30" readonly ><bean:write name="ContentRequestDetail" property="programHighlights"/></textarea>
							</td>
							<td class="text">
							   <textarea name="temp" rows="5" cols="30" readonly ><bean:write name="ContentRequestDetail" property="objectiveOfProgram"/></textarea>
							</td>
						</tr>
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Placement of Disclaimer
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Length of Promotion
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<bean:write name="ContentRequestDetail" property="placementDisclaimer"/>&nbsp;
							</td>
							<td class="text">
							   <bean:write name="ContentRequestDetail" property="promotionLength"/>&nbsp;
							</td>
						</tr>
						
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Anticipated Take Rate of Program
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Program Mail/Distribution Date
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<bean:write name="ContentRequestDetail" property="expProgramTakeRate"/>&nbsp;
							</td>
							<td class="text">
								<bean:write name="ContentRequestDetail" property="programDistributionDate" formatKey="date.format" filter="true"/>&nbsp;
							</td>
						</tr>
						
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Timeline for exposure of Vehicle
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Request Status
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<bean:write name="ContentRequestDetail" property="timelineExposure"/>&nbsp;
							</td>
							<td class="text">
							   <bean:write name="ContentRequestDetail" property="status" />&nbsp;
							</td>
						</tr>
					</table>	
				</td></tr>
				<tr><td width="100%">&nbsp;</td></tr>
				<tr><td width="100%">
					<table class="sectable" width="100%">
						<tr class="sectitle">
						 <td colspan="2" class="aimssecheading"> Content Creative Requested						 
						 </td></tr>
						
						<tr>
							<td class="text">
									<logic:notEmpty name="ApplicationNamesList">
										<logic:iterate id="appsName" name="ApplicationNamesList">
										
											<span class="modFormFieldLbl">
											<logic:present name="appsName" property="status"> 
												<logic:equal name="appsName" property="status" value="ACCEPTED">
													<a href='newMktProvApps.do?task=view&applicationId=<bean:write name="appsName" property="applicationId"/>'>
												</logic:equal>
											</logic:present><bean:write name="appsName" property="appTitle" /><logic:present name="appsName" property="status"><logic:equal name="appsName" property="status" value="ACCEPTED"></a> 
										  		</logic:equal>	
											</logic:present>
												 (<bean:write name="appsName" property="developerName"/>)</span> <logic:present name="appsName" property="status"><span class="text">[Current Status: <bean:write name="appsName" property="status" />]</span></logic:present><input type="hidden" name="appsIds" value="<bean:write name="appsName" property="applicationId"/>"><br/>
												 
										</logic:iterate>
									</logic:notEmpty>
							</td>
						</tr>
					</table>	
				</td></tr>
			<tr><td width="100%">&nbsp;</td></tr>
				<tr><td width="100%">
					<table class="sectable" width="100%">
						<tr class="sectitle"><td colspan="2" class="aimssecheading">Draft Creative with Applications Creative Placement</td></tr>
						<tr>
							<td class="modFormFieldLbl">
								<table width="100%" border="0" class="tabletop" cellspacing="0" cellpadding="2" >
       							<tr bgcolor="#BBBBBB">
                                <td class="firstcell" align="left" width="40%">
                                    <span class="modFormFieldLbl">File Name</span>
                                </td>
										  <td class="cell" align="left" width="60%">
                                    <span class="modFormFieldLbl">Comments</span>
                                </td>
                           </tr>
									<logic:notEmpty name="FilesList">
										<logic:iterate id="fd" name="FilesList">
									<tr>
											 <td class="firstcell" align="left">
											<a href="newMktResView.do?task=view&fileId=<bean:write name="fd" property="fileId"/>&fileType=<bean:write name="fd" property="fileType"/>" ><bean:write name="fd" property="fileName"/>&nbsp;
											</td>
										<td class="cell" align="left"><bean:write name="fd" property="comments"/>&nbsp;</td>
									</tr>	
										</logic:iterate>
									</logic:notEmpty>
									<logic:empty name="FilesList">
									<tr><td class="firstcell" align="center" colspan="2">No File Uploaded.</td><tr>
									</logic:empty>
								</table>
							</td>
						</tr>
					</table>	
				</td></tr>
			
				<tr><td width="100%">&nbsp;</td></tr>
				<tr><td width="100%" align="right">
					<a href="javascript:submitReq(1);//"><img src="images/clone_b.gif" width="52" height="15" 
					border="0"></a>&nbsp;<logic:equal name="ContentRequestDetail" property="status" value="SAVED"><a href="javascript:submitReq(2);//"><img src="images/edit_b.gif" width="52" 
					height="15" border="0"></a>&nbsp;</logic:equal><a href="javascript:submitReq(3);//"><img src="images/back_b.gif" width="52" height="15" border="0"></a>
				</td></tr>
			</table>
		</td>
	</tr>
</table>
</html:form>

