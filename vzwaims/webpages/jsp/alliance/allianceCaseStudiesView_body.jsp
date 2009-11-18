<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.alliance.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>



<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
			<span class="aimsmasterheading">
                <bean:message key="AllianceProfile.header" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> - Manage Case Studies
            </span>
		</td>
	</tr>
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
            <aims:getAllianceTab attributeName="Case Studies"/>    
		</td>
	</tr>
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">&nbsp;</td>
	</tr>     
  	<tr> 
       	<td width="20">&nbsp;</td>
        <td width="100%">
            <table class="sectable" width="100%">
                <tr>
                    <td class="text" valign="top">
                        <span class="modFormFieldLbl">Company Name: 											<bean:write name="companyName" scope="request" /></span>
                    </td>
                </tr>
            </table>
       	</td>
  	</tr>
	<tr>
		<td	width="20">&nbsp;</td>
		<td	width="100%">
			&nbsp;
		</td>
    </tr>     
	<%@ include  file="/common/error.jsp" %>
	<tr> 
		<td width="20">&nbsp;</td>
    	<td width="100%" align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" valign=top">
						<table class="sectable" width="100%" height="100%">
							<tr class="sectitle"><td colspan="2" class="aimssecheading">Case Studies List</td></tr>
                			<tr> 
                    			<td colspan="2" align="center" valign="middle" bgcolor="#FFFFFF"> 
				          	        <table width="100%" border="0" cellspacing="0" cellpadding="0">		
			      			            <tr>
			      			      	        <td bgcolor="#EBEBEB">
												<table width="100%" class="tabletop" cellspacing="0" cellpadding="5">
													<tr bgcolor="#BBBBBB"> 
														<td class="firstcell" align="center">						
															<strong>Case Study Name</strong>
														</td>
														<td class="cell" align="center">
															<strong>Case Study Document</strong>
														</td>
														<td class="cell" align="center">
															<strong>Created By</strong>
														</td>
														<td class="cell" align="center">
															<strong>Created Date</strong>
														</td>
														<td class="cell" align="center">
															<strong>Status</strong>
														</td>
														<td class="cell" align="center">
															<strong><bean:message key="AccountForm.edit" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/></strong>
														</td>
														<td class="cell" align="center">
															<strong><bean:message key="AccountForm.delete" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/></strong>
														</td>
													</tr>
													<logic:iterate id="casestudy" name="AimsCaseStudies">
													<tr>
														<td class="firstcell" align="left">
															<a href='/aims/allianceCaseStudiesSetup.do?task=editFormView&caseStudyId=<bean:write name="casestudy" property="caseStudyId"/>' class="modulecontentlink">
																<bean:write name="casestudy" property="caseStudyName" />
															</a>
														</td>
														<td class="cell" align="left">	
															<a class="a" target="_blank" href='/aims/caseStudyResourceAction.do?resource=caseStudyDoc&resourceId=<bean:write name="casestudy" property="caseStudyId"/>' >
																<bean:write name="casestudy" property="caseStudyDocFileName" />
															</a>
															&nbsp;
														</td>
														<td class="cell" align="left">
															<bean:write name="casestudy" property="createdContactFirstName" />
															&nbsp;
															<bean:write name="casestudy" property="createdContactLastName" />
														</td>
														<td class="cell" align="left">
															<bean:write name="casestudy" property="createdDate" formatKey="date.format" filter="true" />
															&nbsp;
														</td>
														<td class="cell" align="left">
															<bean:write name="casestudy" property="status" />
															&nbsp;
														</td>	
														<td class="cell" align="center">
															<a href='/aims/allianceCaseStudiesSetup.do?task=editForm&caseStudyId=<bean:write name="casestudy" property="caseStudyId"/>' class="modulecontentlink">
																<bean:message key="images.edit.icon" />
															</a>
														</td>	
														<td class="cell" align="center">
															<a href='/aims/allianceCaseStudies.do?task=delete&caseStudyId=<bean:write name="casestudy" property="caseStudyId"/>' class="modulecontentlink">
																<bean:message key="images.delete.icon" />
															</a>
														</td>		
													</tr>
													</logic:iterate>
												</table>
			                    			</td>
			                			</tr>
									</table>
                    			</td>
                			</tr>
            			</table>           
                    </td>
                </tr>
    			<tr> 
        			<td height="25" align="right" valign="middle"> 			
						<a href="/aims/allianceCaseStudiesSetup.do?task=createForm">
							<bean:message key="images.create.button" />	
						</a>
        			</td>
    			</tr>
            </table>           
		</td>
	</tr>
</table>