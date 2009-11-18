<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.masters.*, com.netpace.aims.model.alliance.*, com.netpace.aims.util.*, com.netpace.aims.controller.alliance.*, com.netpace.aims.bo.security.*;"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<%@ include  file="allianceSpotlightInclude.jsp" %>

<bean:parameter id="alliance_id" name="alliance_id" value="0"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">

	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
			<span class="aimsmasterheading">
                <bean:message key="AllianceProfile.header" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            - Sales Presentations
            </span>

		</td>
	</tr>
    <tr>
        <td	width="20">
            &nbsp;
        </td>
        <td	width="100%">
            <aims:getVZWAllianceTab attributeName="Tools" allianceId="<%=request.getParameter("alliance_id")%>"/>
        </td>
    </tr>
    <tr>
		<td	width="20">
			&nbsp;
		</td>
		<td	width="100%">
            &nbsp;
        </td>
    </tr>
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
            <aims:getSpotlightsTab attributeName="<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>" allianceId="<%=request.getParameter("alliance_id")%>" />	
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
		<td colspan="2"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                    <td bgcolor="#999999" height="1">
                        <img src="images/spacer.gif" width="20" height="1" />
                    </td>
                </tr> 
                <tr> 
                    <td align="center" valign="middle" bgcolor="#EBEBEB"> 
						<table width="100%" class="tabletop" cellspacing="0" cellpadding="5">
							<tr bgcolor="#BBBBBB"> 
								<td class="firstcell" align="center">						
									<strong>Sales Presentation Name</strong>
								</td>
								<td class="cell" align="center">
									<strong>Sales Presentation Description</strong>
								</td>
								<td class="cell" align="center">
									<strong>Sales Presentation Document</strong>
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
				<%
				if (AimsSecurityManager.checkAccess(request, "ALL_ALLIANCES", AimsSecurityManager.UPDATE)) {
				%>
								<td class="cell" align="center">
									<strong>Accept</strong>
								</td>
								<td class="cell" align="center">
									<strong>Reject</strong>
								</td>
               <%
                }
               %>
							</tr>
							<logic:iterate id="spotlight" name="AimsSpotLights">
							<tr>
								<td class="firstcell" align="left">
									<bean:write name="spotlight" property="spotlightName" />
								</td>						
								<td class="cell" align="left">
									<bean:write name="spotlight" property="spotlightDesc" />
									&nbsp;
								</td>
								<td class="cell" align="left">	
									<a class="a" target="_blank" href='/aims/spotLightResourceAction.do?resource=spotlightFile&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>&resourceId=<bean:write name="spotlight" property="spotlightId"/>' >
										<bean:write name="spotlight" property="spotlightFileFileName" />
									</a>
									&nbsp;
								</td>
								<td class="cell" align="left">
									<bean:write name="spotlight" property="createdBy" />
									&nbsp;
								</td>
								<td class="cell" align="left">
									<bean:write name="spotlight" property="createdDate" formatKey="date.format" filter="true" />
									&nbsp;
								</td>
								<td class="cell" align="left">
									<bean:write name="spotlight" property="status" />
									&nbsp;
								</td>
				<%
				if (AimsSecurityManager.checkAccess(request, "ALL_ALLIANCES", AimsSecurityManager.UPDATE)) {
				%>
								<td class="cell" align="center">
                                    <logic:equal name="spotlight" property="status" value="NEW">
                                        <a href='/aims/allianceSpotlights.do?task=changeStatus&alliance_id=<%=alliance_id%>&vzwStatus=ACCEPT&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>&spotlightId=<bean:write name="spotlight" property="spotlightId"/>' class="modulecontentlink">
                                            <img src="images/accept_b.gif" border="0" alt="Accept"/>
                                        </a>
                                    </logic:equal>
                                    <logic:notEqual name="spotlight" property="status" value="NEW">
                                       &nbsp;
                                    </logic:notEqual>		
								</td>	
								<td class="cell" align="center">
                                    <logic:equal name="spotlight" property="status" value="NEW">
                                        <a href='/aims/allianceSpotlights.do?task=changeStatus&alliance_id=<%=alliance_id%>&vzwStatus=REJECT&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>&spotlightId=<bean:write name="spotlight" property="spotlightId"/>' class="modulecontentlink">
                                            <img src="images/reject_b.gif" border="0" alt="Accept"/>
                                        </a>
                                    </logic:equal>
                                    <logic:notEqual name="spotlight" property="status" value="NEW">
                                       &nbsp;
                                    </logic:notEqual>			
								</td> 
               <%
                }
               %>                                
							</tr>
							</logic:iterate>
						</table>
                    </td>
                </tr>       
            </table>           
		</td>
	</tr>
</table>