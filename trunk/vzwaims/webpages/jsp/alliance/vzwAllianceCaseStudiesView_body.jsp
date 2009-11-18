<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.alliance.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<jsp:useBean id="alliance_id" class="java.lang.Long" scope="request"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
			<span class="aimsmasterheading">
                <bean:message key="AllianceProfile.header" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                -
			    Manage Case Studies
            </span>
		</td>
	</tr>
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
        <aims:getVZWAllianceTab attributeName="Case Studies" allianceId="<%=alliance_id.toString()%>"/>
           
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
		<td width="100%" colspan="2"> 
			<table class="sectable" width="100%">
				<tr class="sectitle"><td class="aimssecheading">Case Studies</td></tr>
                <tr> 
                    <td align="center" valign="middle"> 
						<table width="100%" class="tabletop" cellspacing="0" cellpadding="5">
							<tr bgcolor="#BBBBBB"> 
								<td class="firstcell" align="center">						
									<span class="modFormFieldLbl">Case Study Name</span>
								</td>
								<td class="cell" align="center">
									<span class="modFormFieldLbl">Case Study Document</span>
								</td>
								<td class="cell" align="center">
									<span class="modFormFieldLbl">Created By</span>
								</td>
								<td class="cell" align="center">
									<span class="modFormFieldLbl">Created Date</span>
								</td>
								<td class="cell" align="center">
									<span class="modFormFieldLbl">Status</span>
								</td>
								<td class="cell" align="center">
									<span class="modFormFieldLbl">Accept</span>
								</td>
								<td class="cell" align="center">
									<span class="modFormFieldLbl">Reject</span>
								</td>
							</tr>
							<logic:iterate id="casestudy" name="AimsCaseStudies">
							<tr>
								<td class="firstcell" align="left">					
									<bean:write name="casestudy" property="caseStudyName" />
								</td>
								<td class="cell" align="left">	
									<a class="a" target="_blank" href='/aims/caseStudyResourceAction.do?resource=caseStudyDoc&resourceId=<bean:write name="casestudy" property="caseStudyId"/>' >
										<bean:write name="casestudy" property="caseStudyDocFileName" />
									</a>
									&nbsp;
								</td>
								<td class="cell" align="left">
									<bean:write name="casestudy" property="createdContactFirstName" /> 
									<bean:write name="casestudy" property="createdContactLastName" />
									&nbsp;
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
                                    <logic:equal name="casestudy" property="status" value="NEW">
                                        <a href='/aims/allianceCaseStudies.do?task=changeStatus&alliance_id=<%=alliance_id.toString()%>&vzwStatus=ACCEPT&case_study_id=<bean:write name="casestudy" property="caseStudyId"/>' class="modulecontentlink">
                                            <img src="images/accept_b.gif" border="0" alt="Accept"/>
                                        </a>
                                    </logic:equal>
                                    <logic:notEqual name="casestudy" property="status" value="NEW">
                                       &nbsp;
                                    </logic:notEqual>
								</td>	
								<td class="cell" align="center">
                                    <logic:equal name="casestudy" property="status" value="NEW">
                                        <a href='/aims/allianceCaseStudies.do?task=changeStatus&alliance_id=<%=alliance_id.toString()%>&vzwStatus=REJECT&caseStudyId=<bean:write name="casestudy" property="caseStudyId"/>' class="modulecontentlink">
                                            <img src="images/reject_b.gif" border="0" alt="Reject"/>
                                        </a>
                                    </logic:equal>
                                    <logic:notEqual name="casestudy" property="status" value="NEW">
                                       &nbsp;
                                    </logic:notEqual>
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