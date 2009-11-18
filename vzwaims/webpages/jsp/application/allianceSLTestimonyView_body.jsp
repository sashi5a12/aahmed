<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.masters.*, com.netpace.aims.model.alliance.*, com.netpace.aims.util.*, com.netpace.aims.controller.alliance.*, com.netpace.aims.bo.security.*;"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<%@ include  file="allianceSpotlightInclude.jsp" %>

<%@ include file="/common/error.jsp" %>
<div class="homeColumnTab lBox">
	<%@ include file="include/entAppSpotlightsTabs.jsp"%>	
	<aims:getEntSpotlightsTab attributeName="<%=AimsConstants.SPOTLIGHT_TESTIMONY%>" enterpriseAppsId="<%=enterpriseAppsId%>"/>	
	<div style="margin:10px">
		<strong>Application Title:    </strong>
		<bean:write name="companyName" scope="request" />
	</div>
	
	<DIV class="headLeftCurveblk"></DIV>
	<H1>Manage Testimonials</H1>
	<DIV class="headRightCurveblk"></DIV>
	
	<div class="contentbox">
		<table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
			<tr>
				<th align="center"><strong>Testimonial Name</strong></th>
				<th align="center"><strong>Testimonial Description</strong></th>
				<th align="center"><strong>Testimonial Document</strong></th>
				<th align="center"><strong>Created By</strong></th>
				<th align="center"><strong>Created Date</strong></th>
				<th align="center"><strong>Status</strong></th>
                <% if (AimsSecurityManager.checkAccess(request, "SPOTLIGHT_PAGE", AimsSecurityManager.UPDATE)) { %>				
					<th align="center">
						<strong><bean:message key="AccountForm.edit" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE" /></strong>
					</th>
                <% } 
                	if (AimsSecurityManager.checkAccess(request, "SPOTLIGHT_PAGE", AimsSecurityManager.DELETE)) { %>					
					<th align="center">				
						<strong><bean:message key="AccountForm.delete" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE" /> </strong>
					</th>
                <%}%>					
			</tr>
			<logic:iterate id="spotlight" name="AimsSpotLights">
				<tr>
					<td align="left">
						<logic:notEqual name="spotlight" property="status" value="ACCEPT">
							<a href='/aims/entAppsSpotLightSetup.do?task=editForm&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_TESTIMONY%>&spotlightId=<bean:write name="spotlight" property="spotlightId"/>&enterpriseAppsId=<%=enterpriseAppsId%>' class="a">
								<bean:write name="spotlight" property="spotlightName" />
							</a>
						</logic:notEqual>
						<logic:equal name="spotlight" property="status" value="ACCEPT">
							<bean:write name="spotlight" property="spotlightName" />
						</logic:equal>						
					</td>
					<td align="left"><bean:write name="spotlight" property="spotlightDesc" />&nbsp;</td>
					<td align="left">
						<a class="a" target="_blank" href='/aims/spotLightResourceAction.do?resource=spotlightFile&resourceId=<bean:write name="spotlight" property="spotlightId"/>' >
							<bean:write name="spotlight" property="spotlightFileFileName" filter="false" />
						</a>
						&nbsp;					
					</td>
					<td align="left"><bean:write name="spotlight" property="createdBy" />&nbsp;</td>
					<td align="left"><bean:write name="spotlight" property="createdDate" formatKey="date.format" filter="true" />&nbsp;</td>
					<td align="left"><bean:write name="spotlight" property="status" />&nbsp;</td>
					<td align="center" style="vertical-align: middle">
	                <% if (AimsSecurityManager.checkAccess(request, "SPOTLIGHT_PAGE", AimsSecurityManager.UPDATE)) { %>					
						<logic:notEqual name="spotlight" property="status" value="ACCEPT">
							<a href='/aims/entAppsSpotLightSetup.do?task=editForm&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_TESTIMONY%>&spotlightId=<bean:write name="spotlight" property="spotlightId"/>&enterpriseAppsId=<%=enterpriseAppsId%>' class="a">
								<bean:message key="images.edit.icon" /></a>
						</logic:notEqual>
					<%} %>&nbsp;
					</td>
					<td align="center" style="vertical-align: middle">
	                <%if (AimsSecurityManager.checkAccess(request, "SPOTLIGHT_PAGE", AimsSecurityManager.DELETE)) {	%>					
						<a href='/aims/entAppsSpotlights.do?task=delete&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_TESTIMONY%>&spotlightId=<bean:write name="spotlight" property="spotlightId"/>&enterpriseAppsId=<%=enterpriseAppsId%>' class="a">
							<bean:message key="images.delete.icon" /></a>
					<%} %>&nbsp;
					</td>
				</tr>
			</logic:iterate>
		</table>
		<%if (AimsSecurityManager.checkAccess(request, "SPOTLIGHT_PAGE", AimsSecurityManager.INSERT)) {%>
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
			   <tr><td>&nbsp;</td></tr>	 		
	             <tr> 
	                 <td colspan="8" align="right" > 
	                 	<div class="redBtn" style=" margin-left:5px;float:right; margin-top:3px" id="Create"
	                          title="Create">
	                         <div>
	                             <div><div onclick="window.location='/aims/entAppsSpotLightSetup.do?task=createForm&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_TESTIMONY%>&enterpriseAppsId=<%=enterpriseAppsId%>'">Create</div></div>
	                         </div>
	                     </div>
	                 </td>
	             </tr>
	         </table>
         <% } %>		
	</div>
</div>