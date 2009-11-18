<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.masters.*, com.netpace.aims.model.alliance.*, com.netpace.aims.util.*, com.netpace.aims.controller.alliance.*, com.netpace.aims.bo.security.*"%>

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
	<aims:getToolsTab attributeName="Spotlight Page" typeName="tabs" />
	<div style="padding:5px"></div>	
	<aims:getEntSpotlightsTab attributeName="<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>" enterpriseAppsId="<%=enterpriseAppsId%>" />
	<div style="margin:10px">
		<strong>
			<bean:message key="EntAppsSpotlights.applicationTitle" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
		</strong>
		<bean:write name="companyName" scope="request" />
	</div>
	
	<DIV class="headLeftCurveblk"></DIV>
	<H1>Manage Sales Presentations</H1>
	<DIV class="headRightCurveblk"></DIV>
	
	<div class="contentbox">
		<table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
			<tr>
				<th align="center"><strong>Sales Presentation Name</strong></th>
				<th align="center"><strong>Sales Presentation Description</strong></th>
				<th align="center"><strong>Sales Presentation Document</strong></th>
				<th align="center"><strong>Created By</strong></th>
				<th align="center"><strong>Created Date</strong></th>
				<th align="center"><strong>Status</strong></th>
				<%if (AimsSecurityManager.checkAccess(request, "ALL_ALLIANCES", AimsSecurityManager.UPDATE)) { %>
					<th align="center">
						<strong>Accept</strong>
					</th>
					<th align="center">				
						<strong>Reject</strong>
					</th>
				<%}%>
			</tr>
			<logic:iterate id="spotlight" name="AimsSpotLights">
				<tr>
					<td align="left" style="width: 100px;"><bean:write name="spotlight" property="spotlightName" /></td>
					<td align="left" style="width: 100px;"><bean:write name="spotlight" property="spotlightDesc" />&nbsp;</td>
					<td align="left">
						<a class="a" target="_blank" href='/aims/entAppsSLResourceAction.do?resource=spotlightFile&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>&resourceId=<bean:write name="spotlight" property="spotlightId"/>' >
							<bean:write name="spotlight" property="spotlightFileFileName" filter="false" />
						</a>
						&nbsp;					
					</td>
					<td align="left"><bean:write name="spotlight" property="createdBy" />&nbsp;</td>
					<td align="left"><bean:write name="spotlight" property="createdDate" formatKey="date.format" filter="true" />&nbsp;</td>
					<td align="left"><bean:write name="spotlight" property="status" />&nbsp;</td>
					<%if (AimsSecurityManager.checkAccess(request, "ALL_ALLIANCES", AimsSecurityManager.UPDATE)) {%>
						<td align="center">
							<logic:equal name="spotlight" property="status" value="NEW">
								<div class="redSmallBtn" id="Create" title="Accept">
									<div><div>
										<div
											onclick="window.location='/aims/entAppsSpotlights.do?task=changeStatus&enterpriseAppsId=<%=enterpriseAppsId%>&vzwStatus=ACCEPT&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>&spotlightId=<bean:write name="spotlight" property="spotlightId"/>'">
											Accept
										</div>
									</div></div>
								</div>
							</logic:equal>	
							<logic:notEqual name="spotlight" property="status" value="NEW">&nbsp;</logic:notEqual>
						</td>
						<td align="center">
							<logic:equal name="spotlight" property="status" value="NEW">
								<div class="redSmallBtn" id="Create" title="Reject">
									<div>
										<div>
											<div
												onclick="window.location='/aims/entAppsSpotlights.do?task=changeStatus&enterpriseAppsId=<%=enterpriseAppsId%>&vzwStatus=REJECT&spotLightTypeId=<%=AimsConstants.SPOTLIGHT_SALES_PRESENTATION%>&spotlightId=<bean:write name="spotlight" property="spotlightId"/>'">
												Reject
											</div>
										</div>
									</div>
								</div>
							</logic:equal>
							<logic:notEqual name="spotlight" property="status" value="NEW">&nbsp;</logic:notEqual>
						</td>
					<%}%>
				</tr>
			</logic:iterate>
		</table>
	</div>
</div>
