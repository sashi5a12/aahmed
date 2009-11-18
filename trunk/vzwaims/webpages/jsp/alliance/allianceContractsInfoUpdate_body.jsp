<%@	page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.alliance.* "%>

<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@	taglib uri="/WEB-INF/struts-template.tld" prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<html:form action="/allianceContractInfoEdit.do" enctype="multipart/form-data">
	<html:hidden  property="task" value="edit" />
	<html:hidden  property="allianceContractId" />
	<html:hidden  property="contractId" />
	<html:hidden  property="subtask" value="" />


<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
			<span class="aimsmasterheading"><bean:message key="AllianceProfile.header" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> - Edit Contract</span>           
		</td>
	</tr>
	<tr>
		<td	width="20">&nbsp;</td>
		<td	width="100%">
            <aims:getAllianceTab attributeName="Contracts"/>
        </td>
    </tr>
    <tr> 
       <td width="20">&nbsp;</td>
       <td width="100%">
            &nbsp;
       </td>
    </tr>
	<%@ include  file="/common/error.jsp" %>
	<tr>
		<td	width="20">&nbsp;</td>
    	<td width="100%" align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" valign=top">
						<table class="sectable" width="100%" height="100%">
							<tr class="sectitle"><td colspan="2" class="aimssecheading">Contract Details</td></tr>
							<tr> 
								<td class="text" width="50%">
									<span class="modFormFieldLbl"><bean:message key="AllianceCompInfoForm.companyName" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></span>
								</td>
								<td class="text" width="50%">
									<span class="modFormFieldLbl">Contract Title</span>
								</td>
							</tr>
							<tr> 
								<td class="text" width="50%">							
									<bean:write name="AllianceContractDetailsForm" property="companyName" />
								</td>
								<td class="text" width="50%">
									<bean:write name="AllianceContractDetailsForm" property="contractTitle" /> 
								</td>
							</tr>
							<tr> 
								<td class="text" width="50%">
									<span class="modFormFieldLbl">Version</span>
								</td>
								<td class="text" width="50%">
									<span class="modFormFieldLbl">Date Presented By VZW</span>
								</td>
							</tr>
							<tr> 
								<td class="text" width="50%">
									<bean:write name="AllianceContractDetailsForm" property="version" /> 
								</td>
								<td class="text" width="50%">
									<bean:write name="AllianceContractDetailsForm" property="vzwContractPresentDate" formatKey="date.format" filter="true"/>
								</td>
							</tr>
							<tr> 
								<td class="text" width="50%">
									<span class="modFormFieldLbl">Current Status</span>
								</td>
								<td class="text" width="50%">
									&nbsp;
								</td>
							</tr>
							<tr> 
								<td class="text" width="50%">
									<bean:write name="AllianceContractDetailsForm" property="allianceContractStatus" />
								</td>
								<td class="text" width="50%">
									&nbsp;
								</td>
							</tr>
							<tr> 
								<td class="text" width="50%">
									<span class="modFormFieldLbl">Accepted/Declined Date</span>
								</td>
								<td class="text" width="50%">
									<span class="modFormFieldLbl">Accepted/Declined By</span>
								</td>
							</tr>
							<tr> 
								<td class="text"width="50%">
									<bean:write name="AllianceContractDetailsForm" property="acceptDeclineDate" formatKey="date.format" filter="true"/>&nbsp;
								</td>
								<td class="text" width="50%">
									<bean:write name="AllianceContractDetailsForm" property="acceptDeclineFirstName" />
									&nbsp;
									<bean:write name="AllianceContractDetailsForm" property="acceptDeclineLastName" />&nbsp;
								</td>
							</tr>
							<tr> 
								<td class="text" width="50%">
									<span class="modFormFieldLbl">Contract Document</span>
								</td>
							<logic:equal name="AllianceContractDetailsForm" property="showModifiedContractUpload" value="Y" scope="request">
								<td class="text">											
									<span class="modFormFieldLbl">Modified Contract Document</span>
								</td>
							</logic:equal>	
							</tr>
							<tr> 
								<td class="text" width="50%">
									<a class="a" target="_blank" href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="AllianceContractDetailsForm" property="allianceContractId"/>'>
										<bean:write	name="AllianceContractDetailsForm" property="documentFileName"/>
									</a>
								</td>
								<logic:equal name="AllianceContractDetailsForm" property="showModifiedContractUpload" value="Y" scope="request">
								<td class="body">
									<html:file property="modifiedContDoc"/>
									<br/>																	
									<a class="a" target="_blank" href='/aims/resourceContractAction.do?resource=modifiedContDoc&object=AimsAllianc&resourceId=<bean:write name="AllianceContractDetailsForm" property="allianceContractId"/>'>
										<bean:write	name="AllianceContractDetailsForm" property="modifiedContDocFileName"/>
									</a>
								</td>
								</logic:equal>	
							</tr> 
							<logic:equal name="AllianceContractDetailsForm" property="showAtleastOneButton" value="Y" scope="request">
							<tr> 
								<td height="25" align="right" valign="middle">
									<logic:equal name="AllianceContractDetailsForm" property="showAcceptRejectButton" value="Y" scope="request">
										<html:image property="subtask_image" value="accept" src="images/accept_b.gif" border="0" onclick="document.forms[0].subtask.value='ACCEPTED';"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<html:image  property="subtask_image" value="decline" src="images/reject_b.gif" border="0" onclick="document.forms[0].subtask.value='DECLINED';"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</logic:equal>
									<logic:equal name="AllianceContractDetailsForm" property="showModifiedContractUpload" value="Y" scope="request">
										<html:image  property="subtask_image" value="request_change" src="images/request_changes_b.gif"  border="0" onclick="document.forms[0].subtask.value='REQUEST_CHANGE';"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</logic:equal>
								</td>
							</tr>
							</logic:equal>
						</table>
            		</td>
         		</tr>
       		</table>
	   	</td>
   	</tr>
</table>
</html:form>