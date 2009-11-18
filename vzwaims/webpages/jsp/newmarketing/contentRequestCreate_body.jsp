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
			document.forms[0].task.value='save';
			document.forms[0].submit();
		}
		else if (iTask == 2)
		{
			document.forms[0].task.value='submit';
			document.forms[0].submit();
		}
		else if (iTask == 3)
		{
			document.forms[0].task.value='cancel';
			document.forms[0].submit();
		}
		else if (iTask == 4)
		{
			document.forms[0].task.value='upload';
			showProcessingInfoPopup();
			document.forms[0].submit();
		}
	}

	function removeFile(fileId,fileType)
	{
			document.forms[0].task.value='remove';
			document.forms[0].fileId.value=fileId;
			document.forms[0].fileType.value=fileType;
			document.forms[0].submit();
	}
//-->
</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
       <td width="20">&nbsp;</td>
       <td width="100%">
       	<span class="aimsmasterheading">
       		Content Management - 
       		<logic:notEqual name="MktContentReqForm" property="status" value="NEW">Edit </logic:notEqual> 
       		<logic:equal name="MktContentReqForm" property="status" value="NEW">New </logic:equal> 
       		Content Request
       	</span>               
       </td>
  </tr>
  <%@ include  file="/common/error.jsp" %>
  <tr> 
      <td width="20">&nbsp;</td>
      <td align="center" valign="middle"> 
			<html:form action="/newMktContReqCreateEdit.do" enctype="multipart/form-data">
			<html:hidden property="crequestId"/>
			<html:hidden property="task"/>
			<html:hidden property="fileId"/>
			<html:hidden property="fileType"/>
			<html:hidden property="status"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td width="100%">
					<table class="sectable" width="100%">
						<tr class="sectitle">
       		<logic:notEqual name="MktContentReqForm" property="status" value="NEW">Edit </logic:notEqual> 
       		<logic:equal name="MktContentReqForm" property="status" value="NEW">New </logic:equal> 
						Content Request</td></tr>
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Program Name<span class="mainReqAstrx">*</span>
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Program Start Date<span class="mainReqAstrx">*</span>
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<html:text property="programName" size="45"/>
							</td>
							<td class="text">
								<table cellpadding="0" cellspacing="0" border="0">
								<tr><td><html:text property="projectStartDate" size="25"/></td>
								<td><img name="daysOfMonthPos" onclick="toggleDatePicker('daysOfMonth','MktContentReqForm.projectStartDate')" id="daysOfMonthPos" src="images/calendar.gif" border="0" alt="Click Here to Pick up the date" /><div style="position:absolute;" id="daysOfMonth"/></td></tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Vehicle for Communication<span class="mainReqAstrx">*</span>
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Agency Developing Creative<span class="mainReqAstrx">*</span>
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<html:text property="vehicleComm" size="45"/>
							</td>
							<td class="text">
								<html:text property="creativeAgency" size="45"/>
							</td>
						</tr>
					
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Area/Region Contact Name<span class="mainReqAstrx">*</span>
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Area/Region Telephone Number<span class="mainReqAstrx">*</span>
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<html:text property="areaContactName" size="45"/>
							</td>
							<td class="text">
								<html:text property="areaContactTel" size="45"/>
							</td>
						</tr>
						
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Area/Markets to be Targeted with Element<span class="mainReqAstrx">*</span>
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Reach Exposure<span class="mainReqAstrx">*</span>
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<html:text property="areaTargeted" size="45"/>
							</td>
							<td class="text">
							   <html:text property="exposure" size="45"/>
							</td>
						</tr>
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Program Highlights<span class="mainReqAstrx">*</span>
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Objective of Program/Primary Message<span class="mainReqAstrx">*</span>
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<html:textarea property="programHighlights" rows="5" cols="30"/>
							</td>
							<td class="text">
							   <html:textarea property="objectiveOfProgram" rows="5" cols="30"/>
							</td>
						</tr>
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Placement of Disclaimer<span class="mainReqAstrx">*</span>
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Length of Promotion<span class="mainReqAstrx">*</span>
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<html:text property="placementDisclaimer" size="45"/>
							</td>
							<td class="text">
							   <html:text property="promotionLength" size="45"/>
							</td>
						</tr>
						
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Anticipated Take Rate of Program<span class="mainReqAstrx">*</span>
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Program Mail/Distribution Date<span class="mainReqAstrx">*</span>
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<html:text property="expProgramTakeRate" size="45"/>
							</td>
							<td class="text">
								<table cellpadding="0" cellspacing="0" border="0">
								<tr><td><html:text property="programDistributionDate" size="25" /></td>
								<td><img name="daysOfMonthPos2" onclick="toggleDatePicker('daysOfMonth2','MktContentReqForm.programDistributionDate')" id="daysOfMonthPos2" src="images/calendar.gif" border="0" alt="Click Here to Pick up the date" /><div style="position:absolute;" id="daysOfMonth2"/></td></tr>
								</table>
							</td>
						</tr>
						
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Timeline for exposure of Vehicle<span class="mainReqAstrx">*</span>
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Request Status<span class="mainReqAstrx">*</span>
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<html:text property="timelineExposure" size="45"/>
							</td>
							<td class="text">
				       		<bean:write name="MktContentReqForm" property="status" />
							</td>
						</tr>
					</table>	
				</td></tr>
				<tr><td width="100%">&nbsp;</td></tr>
				<tr><td width="100%">
					<table class="sectable" width="100%">
						<tr class="sectitle"><td colspan="2" class="aimssecheading">Content Creative Requested</td></tr>
						
						<tr>
							<td class="modFormFieldLbl">
									<logic:notEmpty name="ApplicationNamesList">
										<logic:iterate id="appsName" name="ApplicationNamesList">
											<bean:write name="appsName" property="appTitle"/> (<bean:write name="appsName" property="developerName"/>)<input type="hidden" name="appsIds" value="<bean:write name="appsName" property="applicationId"/>"><br/>
										</logic:iterate>
									</logic:notEmpty>
							</td>
						</tr>
										</table>	
				</td></tr>
			<tr><td width="100%">&nbsp;</td></tr>
				<tr><td width="100%">
					<table class="sectable" width="100%">
						<tr class="sectitle"><td colspan="2" class="aimssecheading">Draft Creative with Content Creative Placement</td></tr>
						<tr>
							<td class="text" valign="top"><table 
									width="100%" border="0" class="tabletop" cellspacing="0" cellpadding="2" >
       							<tr bgcolor="#BBBBBB">
                                <td class="firstcell" align="left" width="40%">
                                    <span class="modFormFieldLbl">File Name</span>
                                </td>
										  <td class="cell" align="left" width="50%">
                                    <span class="modFormFieldLbl">Comments</span>
                                </td>
										  <td class="cell" align="left" width="10%">
                                    <span class="modFormFieldLbl">Remove</span>
                                </td>
                           </tr>
									<logic:notEmpty name="FilesList">
										<logic:iterate id="fd" name="FilesList">
											<tr>
											 <td class="firstcell" align="left"><a href="newMktResView.do?task=view&fileId=<bean:write name="fd" property="fileId"/>&fileType=<bean:write name="fd" property="fileType"/>" ><bean:write name="fd" property="fileName"/></a>&nbsp;</td>
											 <td class="cell" align="left"><bean:write name="fd" property="comments"/>&nbsp;</td>
				                      <td class="cell" align="left"><a href="javascript:removeFile('<bean:write name="fd" property="fileId"/>','<bean:write name="fd" property="fileType"/>');//" ><img src="images/delete_icon.gif" width="20" height="20" border="0"></a></td>
				                     </tr>
										</logic:iterate>
									</logic:notEmpty>
									<logic:empty name="FilesList">
											<tr><td class="firstcell" align="center" colspan="3">No Files Uploaded.</td></tr>
									</logic:empty>
									</table><br/>
								<table border="0" cellspacing="0" cellpadding="2">
								<tr><td class="modFormFieldLbl">File</td><td class="modFormFieldLbl">Comments (Optional)</td><td class="text">&nbsp;</td></tr>
								<tr><td class="text"><html:file property="uploadDocument"/></td><td class="text"><html:text property="comments" size="35" maxlength="5"/></td><td class="text"><input type="button" value="Upload" onClick="submitReq(4);//"></td></tr>
								</table>
								<span class="text"><img src="images/pdf_icon.gif" width="18" height="18" border="0"> upload pdf files only.</span>
							</td>
						</tr>
					</table>	
				</td></tr>
				<tr><td width="100%">&nbsp;</td></tr>
				<tr><td width="100%" align="right">
					<a href="javascript:submitReq(1);//"><img src="images/save_b.gif" width="52" height="15" border="0"></a>&nbsp;<a href="javascript:submitReq(2);//"><img src="images/submit_b.gif" width="52" height="15" 
					border="0"></a>&nbsp;<logic:notEqual name="MktContentReqForm" property="status" value="NEW"><a href="javascript:submitReq(3);//"><img src="images/cancel_b.gif" width="52" 
					height="15" border="0"></a></logic:notEqual><logic:equal name="MktContentReqForm" property="status" value="NEW"><a href="newMktProvApps.do"><img src="images/cancel_b.gif" width="52" height="15" border="0"></a></logic:equal> 
					
				</td></tr>
			</table>
		</td>
	</tr>
</table>
</html:form>

