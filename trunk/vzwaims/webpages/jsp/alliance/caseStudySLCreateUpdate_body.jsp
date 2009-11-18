<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.alliance.*, com.netpace.aims.util.*; "%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>





<html:form action="/allianceSLCaseStudiesEdit.do" enctype="multipart/form-data">	

<html:hidden  property="spotLightTypeId" value="<%=AimsConstants.SPOTLIGHT_CASE_STUDY%>" />
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="30">&nbsp;</td>
		<td width="603">
            <aims:getAllianceTab attributeName=""/>      
		</td>
	</tr>  
	<tr> 
		<td width="30">&nbsp;</td>
		<td width="603">
			<strong><font color="#333333" size="+1" face="Verdana, Arial, Helvetica, sans-serif"><bean:message key="AllianceProfile.header" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></font></strong> 
            -
			<strong><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif">
				<bean:parameter id="taskForThisPage" name="task" value="Nopes"/>
				<logic:equal name="taskForThisPage" scope="page" value="createForm">
					Create Case Study
				</logic:equal>
				<logic:equal name="taskForThisPage" scope="page" value="editForm">
					Edit Case Study
				</logic:equal>
			</font></strong> 
		</td>
	</tr>
	<tr> 
		<td width="30">&nbsp;</td>
		<td width="603">
            <aims:getSpotlightsTab attributeName="<%=AimsConstants.SPOTLIGHT_CASE_STUDY%>"/>       
		</td>
	</tr>  
	<logic:match name="taskForThisPage" scope="page" value="create">
		<html:hidden  property="task" value="create" />
	</logic:match>
	<logic:match name="taskForThisPage" scope="page" value="edit">
		<html:hidden  property="task" value="edit" />
		<html:hidden  property="caseStudyId" />
	</logic:match>	
	<tr>
		<td width="30">&nbsp;</td>
		<td width="603">	
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td bgcolor="#999999" height="1"><img src="images/spacer.gif" width="20" height="1" /></td>
				</tr>
				<tr> 
					<td align="center" valign="middle" bgcolor="#EBEBEB"> 
          				<table width="100%" border="0" cellspacing="10" cellpadding="0">
							<tr> 
								<td class="text" align="right">
									<strong>Case Study Name</strong>
								</td>		
								<td class="body" align="left">
									<html:text styleClass="text" property="caseStudyName" size="60"
									 maxlength="100"/>
								</td>
							</tr>
							<tr> 
								<td class="text" align="right">
									<strong>Case Study Description</strong>
								</td>
								<td class="body" align="left">
									<html:text  styleClass="text" property="caseStudyDesc" size="60" maxlength="500"/>
								</td>
							</tr>
							<tr> 
								<td class="text" align="right">
									<strong>Comments</strong>
								</td>
								<td class="body" align="left">
									<html:text  styleClass="text" property="comments" size="60"/>
								</td>
							</tr>
							<tr> 
								<td class="text" align="right">
									<strong>Case Study Document</strong>
								</td>
								<td class="body" align="left">
									<html:file property="caseStudyDoc"/>
									<br/>
									<a class="a" target="_blank" href='/aims/caseStudyResourceAction.do?resource=caseStudyDoc&resourceId=<bean:write name="AllianceCaseStudyForm" property="caseStudyId"/>' >
										<bean:write name="AllianceCaseStudyForm" property="caseStudyDocFileName" />
									</a>									
								</td>
							</tr> 
               
						</table>
					</td>
				</tr>
				<tr> 
					<td height="1" bgcolor="#999999"><img src="images/spacer.gif" width="20" height="1" /></td>
				</tr>
				<tr> 
					<td height="25" align="right" valign="middle">
						<input type="image" src="images/submit_b.gif" width="52" height="15" border="0" />
					</td>
				</tr>
				<tr> 
					<td height="1" bgcolor="#999999"><img src="images/spacer.gif" width="20" height="1" /></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</html:form>