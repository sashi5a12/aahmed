<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*,com.netpace.aims.model.core.*,com.netpace.aims.controller.application.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:useBean id="DashboardApplicationUpdateForm" class="com.netpace.aims.controller.application.DashboardApplicationUpdateForm" scope="request"/>
<%DashboardApplicationUpdateForm.setCurrentPage("page3");%>

<%@ include  file="include/dashboardAppVariables.jsp" %>

<script language="javascript">
	<%@ include  file="include/dashboardJScript.jsp" %>	
</script>

<%@ include file="/common/error.jsp"%>
<div id="contentBox" style="float: left">
	<DIV class="homeColumnTab ">
		<%@ include file="include/dashboardViewTabs.jsp"%>
		<html:form action="/dashboardApplicationUpdate"	enctype="multipart/form-data">
			<div class="contentbox">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					<%--Start Initial Approval/Denial --%>
					<% if (statusSubmitted || statusInitialApproval || statusInitialRejected || statusContentInReview ||
						statusContentApproved || statusContentRejected || statusPendingProduction ||
						statusInProduction || statusChannelRejected || statusSunset ) { %>
						<% if (hasAccessInitialApproval) {%>
							<tr>
								<td>
									<div class="lBox">
										<DIV class="headLeftCurveblk"></DIV>
										<H1>Initial Approval/Denial</H1>
										<DIV class="headRightCurveblk"></DIV>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" bordercolor="black" cellspacing="0" cellpadding="0" class="GridGradient" style="border: 0px black solid">
										<tr>
											<th width="30%">
												<strong>Notes:</strong>
												<br />
												<html:textarea styleClass="textareaField" property="initialApprovalNotes" rows="4" cols="50" readonly="true"/>
											</th>
											<th style="vertical-align: top">
												<strong>Action:</strong>
												<br />
												<logic:equal name="DashboardApplicationUpdateForm" property="initialApprovalAction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_APPROVE[0]%>">
													<%=AimsConstants.DASHBOARD_APP_RADIO_APPROVE[1]%>
												</logic:equal>
												<logic:equal name="DashboardApplicationUpdateForm" property="initialApprovalAction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_DENY[0]%>">
													<%=AimsConstants.DASHBOARD_APP_RADIO_DENY[1]%>
												</logic:equal>
											</th>
										</tr>
									</table>
								</td>
							</tr>
							<tr><td>&nbsp;</td></tr>
						<% } %>
					<% } %>
					<%--End Initial Approval/Denial --%>
					
					<%-- Start Content File --%>
					<% if (statusContentInReview || statusContentApproved || 
						statusContentRejected || statusPendingProduction ||	statusInProduction || 
						statusChannelRejected || statusSunset ) { %>
						<% if (hasAccessContentZip) {%>									
							<tr>
								<td>
									<div class="lBox">
										<DIV class="headLeftCurveblk"></DIV>
										<H1>Content Zip</H1>
										<DIV class="headRightCurveblk"></DIV>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
										<tr>
											<th width="30%">
												<strong>Content ZIP File:</strong>
												<br />
												<logic:notEmpty name="DashboardApplicationUpdateForm" property="contentZipFileFileName" scope="request">
													<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashContentZipFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
														class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="contentZipFileFileName" /></a>
												</logic:notEmpty>
											</th>
											<th width="22%" style="vertical-align: top">
												<strong><html:checkbox property="isContentZipFileLock" value="<%=AimsConstants.DASHBOARD_APP_CHECKBOX_LOCK_ZIP_FILE[0]%>" disabled="true"/><%=AimsConstants.DASHBOARD_APP_CHECKBOX_LOCK_ZIP_FILE[1]%></strong>
											</th>
											<th style="vertical-align: top">
												<% if (statusContentApproved || statusPendingProduction ||	statusInProduction || statusSunset ) { %>	
													<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" title="FTP Content ZIP File">
														<div><div><div onClick="javascript:document.forms[0].appSubmitType.value='sendZipFile';document.forms[0].task.value='sendZipFile';submitForm();document.forms[0].submit();">FTP Content ZIP File</div></div></div>
													</div>
												<% } %>&nbsp;
											</th>
										</tr>
										<tr>
											<td>
												<strong>Notes:</strong>
												<br />
												<html:textarea styleClass="textareaField" property="contentZipFileNotes" rows="4" cols="50" readonly="true" />
											</td>
											<td colspan="2" style="vertical-align: top">
												<strong>Action:</strong>
												<br />
												<logic:equal name="DashboardApplicationUpdateForm" property="initialApprovalAction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_APPROVE[0]%>">
													<%=AimsConstants.DASHBOARD_APP_RADIO_APPROVE[1]%>
												</logic:equal>
												<logic:equal name="DashboardApplicationUpdateForm" property="initialApprovalAction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_DENY[0]%>">
													<%=AimsConstants.DASHBOARD_APP_RADIO_DENY[1]%>
												</logic:equal>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr><td>&nbsp;</td></tr>
						<% } %>
					<% } %>
					<%-- End Content File --%>
					
					<%-- Start Tracking --%>
					<% if ( statusPendingProduction || statusInProduction || statusChannelRejected || statusSunset ) { %>	
						<% if (hasAccessTracking) {%>										
						<tr>
							<td>
								<div class="lBox">
									<DIV class="headLeftCurveblk"></DIV>
									<H1>Tracking</H1>
									<DIV class="headRightCurveblk"></DIV>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">										
									<tr>
										<th>
											<strong>Move to Production:</strong>
											<br />
											<logic:equal name="DashboardApplicationUpdateForm" property="initialApprovalAction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_ACCEPT[0]%>">
												<%=AimsConstants.DASHBOARD_APP_RADIO_ACCEPT[1]%>
											</logic:equal>
											<logic:equal name="DashboardApplicationUpdateForm" property="initialApprovalAction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_REJECT[0]%>">
												<%=AimsConstants.DASHBOARD_APP_RADIO_REJECT[1]%>
											</logic:equal>
										</th>
									</tr>	
									<tr>
										<td>&nbsp;</td>
									</tr>
									<% if ( statusInProduction || statusSunset ) { %>															
										<tr>
											<td>
												<strong>Sunset:</strong>
												<br />
												<html:checkbox property="remove" value="<%=AimsConstants.DASHBOARD_APP_CHECKBOX_REMOVE[0]%>" disabled="true"/><%=AimsConstants.DASHBOARD_APP_CHECKBOX_REMOVE[1]%>
											</td>
										</tr>
									<% } %>
								</table>
							</td>
						</tr>
						<% } %>						
					<% } %>
					<%-- End Tracking --%>
					<tr>
						<td width="100%">
							<%@ include file="include/dashboardAppViewButtons.jsp"%>
						</td>
					</tr>
				</table>
			</div>
		</html:form>
	</div>
</div>