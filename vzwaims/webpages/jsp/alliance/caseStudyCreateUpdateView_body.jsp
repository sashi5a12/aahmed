<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.alliance.* "%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<html:form action="/allianceCaseStudiesEdit.do" enctype="multipart/form-data">	

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
			<span class="aimsmasterheading">
				<bean:message key="AllianceProfile.header" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> - <bean:parameter id="taskForThisPage" name="task" value="Nopes"/>
				<logic:equal name="taskForThisPage" scope="page" value="createForm">
					Create Case Study
				</logic:equal>
				<logic:equal name="taskForThisPage" scope="page" value="editForm">
					Edit Case Study
				</logic:equal>
			</span>
		</td>
	</tr>  
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
			 <aims:getAllianceTab attributeName="Case Studies"/>
		</td>
	</tr>
	<logic:match name="taskForThisPage" scope="page" value="create">
		<html:hidden  property="task" value="create" />
	</logic:match>
	<logic:match name="taskForThisPage" scope="page" value="edit">
		<html:hidden  property="task" value="edit" />
		<html:hidden  property="caseStudyId" />
	</logic:match>	
    <html:hidden property="caseStudyDocFileName" />
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">&nbsp;</td>
	</tr>     
	<%@ include  file="/common/error.jsp" %>
	<tr> 
		<td width="20">&nbsp;</td>
    	<td width="100%" align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" valign=top">
						<table class="sectable" width="100%" height="100%">
							<tr class="sectitle"><td colspan="2" class="aimssecheading">Case Study</td></tr>
							<tr> 
								<td class="text" align="right">
									<strong>Case Study Name</strong>
								</td>		
								<td class="body" align="left">
									<html:text disabled="true" styleClass="text" property="caseStudyName" size="60"
									/>
								</td>
							</tr>
							<tr> 
								<td class="text" align="right">
									<strong>Case Study Description</strong>
								</td>
								<td class="body" align="left">
									<html:text  disabled="true" styleClass="text" property="caseStudyDesc" size="60"/>
								</td>
							</tr>
							<tr> 
								<td class="text" align="right">
									<strong>Comments</strong>
								</td>
								<td class="body" align="left">
                                    <html:textarea disabled="true" property="comments" rows="3" cols="30" />
								</td>
							</tr>
							<tr> 
								<td class="text" align="right">
									<strong>Case Study Document</strong>
								</td>
								<td class="body" align="left">
									<html:file disabled="true" property="caseStudyDoc"/>
									<br/>
									<a class="a" target="_blank" href='/aims/caseStudyResourceAction.do?resource=caseStudyDoc&resourceId=<bean:write name="AllianceCaseStudyForm" property="caseStudyId"/>' >
										<bean:write name="AllianceCaseStudyForm" property="caseStudyDocFileName" />
									</a>									
								</td>
							</tr> 
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</html:form>