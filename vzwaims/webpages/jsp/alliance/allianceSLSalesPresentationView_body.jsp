<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.masters.*, com.netpace.aims.model.alliance.*, com.netpace.aims.util.*, com.netpace.aims.controller.alliance.*, com.netpace.aims.bo.security.*;"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<%@ include  file="allianceSpotlightInclude.jsp" %>
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
    	<td width="20">&nbsp;</td>
       	<td width="100%">
            <aims:getAllianceTab attributeName="Tools"/>
       	</td>
  	</tr>
  	<tr> 
       	<td width="20">&nbsp;</td>
       	<td width="100%">
			&nbsp;
       	</td>
  	</tr>
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
            <aims:getSpotlightsTab attributeName="<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>"/>	
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
       	<td width="20">&nbsp;</td>
       	<td width="100%">
			&nbsp;
       	</td>
  	</tr>
	<%@ include  file="/common/error.jsp" %>
  	<tr> 
    	<td width="20">&nbsp;</td>
    	<td align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td width="100%">
					<table class="sectable" width="100%" height="100%">
						<tr class="sectitle"><td class="aimssecheading">Sales Presentations</td></tr>
						<tr><td>
							<table width="100%" class="tabletop" cellspacing="0" cellpadding="0">
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
				if (AimsSecurityManager.checkAccess(request, "SPOTLIGHT_PAGE", AimsSecurityManager.UPDATE)) {
				%>
									<td class="cell" align="center">
										<strong><bean:message key="AccountForm.edit" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/></strong>
									</td>
                <%
                }
				if (AimsSecurityManager.checkAccess(request, "SPOTLIGHT_PAGE", AimsSecurityManager.DELETE)) {
				%>
									<td class="cell" align="center">
										<strong><bean:message key="AccountForm.delete" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/></strong>
									</td>
                <%
                }				
				%>
								</tr>
								<logic:iterate id="spotlight" name="AimsSpotLights">
								<tr>
									<td class="firstcell" align="left">
										<a href='/aims/allianceSpotLightSetup.do?task=editForm&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>&spotlightId=<bean:write name="spotlight" property="spotlightId"/>' class="modulecontentlink">
											<bean:write name="spotlight" property="spotlightName" />
										</a>
									</td>						
									<td class="cell" align="left">
										<bean:write name="spotlight" property="spotlightDesc" />
										&nbsp;
									</td>

                                    <td class="cell" align="left">	
										<a class="a" target="_blank"    href='/aims/spotLightResourceAction.do?resource=spotlightFile&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>&resourceId=<bean:write name="spotlight" property="spotlightId"/>' >
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
				if (AimsSecurityManager.checkAccess(request, "SPOTLIGHT_PAGE", AimsSecurityManager.UPDATE)) {
				%>                                    
									<td class="cell" align="center">
										<a href='/aims/allianceSpotLightSetup.do?task=editForm&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>&spotlightId=<bean:write name="spotlight" property="spotlightId"/>' class="modulecontentlink">
											<bean:message key="images.edit.icon" />
										</a>
									</td>
                <%
                }
				if (AimsSecurityManager.checkAccess(request, "SPOTLIGHT_PAGE", AimsSecurityManager.DELETE)) {
				%>                                    
									<td class="cell" align="center">
										<a href='/aims/allianceSpotlights.do?task=delete&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>&spotlightId=<bean:write name="spotlight" property="spotlightId"/>' class="modulecontentlink">
											<bean:message key="images.delete.icon" />
										</a>
									</td>		
                <%
                }				
				%>
								</tr>
								</logic:iterate>
							</table>
						</td></tr>
					</table>
				</td></tr>
                <tr> 
                    <td height="25" align="right" valign="middle"> 			
						<a href="/aims/allianceSpotLightSetup.do?task=createForm&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>">
							<bean:message key="images.create.button" />	
						</a>
                    </td>
                </tr>
            </table>           
		</td>
	</tr>
</table>