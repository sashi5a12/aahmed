<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ include file="/common/error.jsp"%>

<jsp:useBean id="ToolsForm" class="com.netpace.aims.controller.tools.ToolsForm" scope="request" />
<html:form action="/toolUpdate">
	<html:hidden property="taskPerform" />
	<html:hidden property="toolId" />
	<DIV class="homeColumn lBox floatL">
		<DIV class="headLeftCurveblk"></DIV>
		<H1><bean:message key="ToolsForm.welcomeDetail" /></H1>
		<DIV class="headRightCurveblk"></DIV>
		<DIV class="contentbox">
			<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
				<tr>
					<td width="30%"><strong><bean:message key="ToolsForm.toolName" /> :</strong></td>
					<td width="70%">
						<bean:write name="ToolsForm" property="toolName" />
					</td>
				</tr>
				<tr>
					<td>
						<strong><bean:message key="ToolsForm.toolDescription" /> :</strong>
					</td>
					<td>
						<bean:write name="ToolsForm" property="description" />
					</td>
				</tr>
				<tr>
					<td>
						<strong>
							<c:choose>
								<c:when test="${ToolsForm.docType eq '1'}"><bean:message key="ToolsForm.filename" /> :</c:when>
								<c:otherwise><bean:message key="ToolsForm.url" /> :</c:otherwise>
							</c:choose>							
						</strong>						
					</td>
					<td>
						<c:choose>
							<c:when test="${ToolsForm.docType eq '1'}">
								<a href="toolsResource.do?toolResource=toolFile&toolId=<bean:write name="ToolsForm" property="toolId" />"
									class="a" target="_blank"><bean:write name="ToolsForm" property="displayFilename" /></a>								
							</c:when>
							<c:otherwise>
								<a href="<bean:write name="ToolsForm" property="downloadURL" />"
									class="a" target="_blank"><bean:write name="ToolsForm" property="downloadURL" /></a>															
							</c:otherwise>
						</c:choose>								
					</td>
				</tr>
				<tr>
					<td><strong><bean:message key="ToolsForm.category" /> :</strong></td>
					<td>
						<logic:iterate id="category" name="ToolCategories" scope="request">
							-<bean:write name="category" property="categoryName" />
							<br />
						</logic:iterate>
					</td>
				</tr>
				<tr>
					<td>
						<c:choose>
							<c:when test="${ToolsForm.shareType eq '1'}">
								<strong>Share with all alliances regardless of platform(s) or contract(s) :</strong>
							</c:when>
							<c:when test="${ToolsForm.shareType eq '2'}">
								<strong>Platform Supported :</strong>  
							</c:when>
							<c:when test="${ToolsForm.shareType eq '3'}">
								<strong><bean:message key="ToolsForm.contractName" /> :</strong>
							</c:when>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${ToolsForm.shareType eq '1'}">
								Yes
							</c:when>
							<c:when test="${ToolsForm.shareType eq '2'}">
								<logic:iterate id="platform" name="ToolPlatforms" scope="request">
		                			-<bean:write name="platform" property="platformName" />
									<br />
								</logic:iterate>
  							</c:when>
							<c:when test="${ToolsForm.shareType eq '3'}">
								<logic:iterate id="contract" name="ToolsForm" property="addedContracts">
			               			-<bean:write name="contract" property="contractTitle" />
									<br />
								</logic:iterate> 
							</c:when>
						</c:choose>					
					</td>
				</tr>
			</table>
			<div>&nbsp;</div>
			<table align="right" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<div class="blackBtn" style="padding-left: 10px; float: right;" id="Back" title="Back">
							<div><div><div onClick="window.location='toolSetup.do'">Back</div></div></div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</html:form>