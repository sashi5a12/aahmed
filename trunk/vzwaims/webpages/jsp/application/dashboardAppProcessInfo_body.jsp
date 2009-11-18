<%@	page language="java"
	import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ include  file="include/dashboardAppVariables.jsp" %>

<script language="javascript">
	<%@ include  file="include/dashboardJScript.jsp" %>
	
	function truncateLocalTextAreas(){
	
		if (typeof document.forms[0].initialApprovalNotes != "undefined")
			if (typeof document.forms[0].initialApprovalNotes.value != "undefined")
				TruncateText(document.forms[0].initialApprovalNotes,500);
		
		if (typeof document.forms[0].contentZipFileNotes != "undefined")
			if (typeof document.forms[0].contentZipFileNotes.value != "undefined")
				TruncateText(document.forms[0].contentZipFileNotes,500);								
	}
	
	function disableAfterProcess(){
		
		<%if (isEqualOrAboveInitialApproval){%>			
            for (var i=0; i<document.forms[0].elements.length; i++) {
                if (document.forms[0].elements[i].name == "initialApprovalAction")
                    document.forms[0].elements[i].disabled = true;
            }
        <% } %>
		
		<%if (isEqualOrAboveContentApproval){%>			
            for (var i=0; i<document.forms[0].elements.length; i++) {
                if (document.forms[0].elements[i].name == "contentZipFileAction")
                    document.forms[0].elements[i].disabled = true;
            }
        <% } %>

		<%if (isEqualOrAboveMoveToProduction){%>			
            for (var i=0; i<document.forms[0].elements.length; i++) {
                if (document.forms[0].elements[i].name == "moveToProduction")
                    document.forms[0].elements[i].disabled = true;
            }
        <% } %>

		<%if (isEqualSunset){%>			
            document.forms[0].remove.disabled = true;
        <% } %>
	}
	
	function showChannels() {
        var popupURL = "/aims/dashboardChannels.do";
        var childWindow = window.open(popupURL,"channels","resizable=0,width=870,height=575,scrollbars=1,screenX=100,left=150,screenY=30,top=30,status=0,titlebar=0");
        if (childWindow.opener == null) childWindow.opener = self;
        childWindow.focus();
    }
	
</script>

<%@ include file="include/dashboardMessageHeader.jsp" %>
<div id="contentBox" style="float: left" onmousemove="truncateLocalTextAreas();">
	<DIV class="homeColumnTab ">
		<%@ include file="include/dashboardTabs.jsp"%>
		<html:form action="/dashboardApplicationUpdate"	enctype="multipart/form-data">
			<html:hidden property="currentPage" value="page3" />
			<div class="contentbox">
				<%@ include file="include/dashboardAppHidden.jsp"%>
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
												<html:textarea styleClass="textareaField" property="initialApprovalNotes" onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)" rows="4" cols="50"></html:textarea>
											</th>
											<th style="vertical-align: top">
												<strong>Action:</strong>
												<br />
					                            <html:radio property="initialApprovalAction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_APPROVE[0]%>"/><%=AimsConstants.DASHBOARD_APP_RADIO_APPROVE[1]%>&nbsp;
					                            <html:radio property="initialApprovalAction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_DENY[0]%>"/><%=AimsConstants.DASHBOARD_APP_RADIO_DENY[1]%>										
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
													<logic:equal name="DashboardApplicationUpdateForm" property="contentZipFileTempFileId" scope="request" value="0">
														<a
															href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashContentZipFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
															class="a" target="_blank">
													</logic:equal>
														<logic:notEqual name="DashboardApplicationUpdateForm" property="contentZipFileTempFileId" scope="request" value="0">
														<a
															href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="contentZipFileTempFileId" />"
															class="a" target="_blank">
													</logic:notEqual>
													<bean:write name="DashboardApplicationUpdateForm" property="contentZipFileFileName" />
													</a>
												</logic:notEmpty>										
											</th>
											<th width="22%" style="vertical-align: top">
												<strong><html:checkbox property="isContentZipFileLock" value="<%=AimsConstants.DASHBOARD_APP_CHECKBOX_LOCK_ZIP_FILE[0]%>"/><%=AimsConstants.DASHBOARD_APP_CHECKBOX_LOCK_ZIP_FILE[1]%></strong>
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
											<td colspan="3" style="padding: 0px">
												<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 0px black solid">
													<tr>
														<td width="10%">
															<strong>Channel ID:</strong>
															<br />
															<html:text property="channelId" size="15" maxlength="5" styleClass="inputField" />&nbsp;
														</td>
														<td><br/>
															<a class="a" href="javascript:showChannels();">See Assigned Channel ID</a>
														</td>
													</tr>
												</table>																							
											</td>
										</tr>
										<tr>
											<td>
												<strong>Notes:</strong>
												<br />
												<html:textarea styleClass="textareaField" property="contentZipFileNotes" onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)" rows="4" cols="50"></html:textarea>
											</td>
											<td colspan="2" style="vertical-align: top">
												<strong>Action:</strong>
												<br />
												<html:radio property="contentZipFileAction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_APPROVE[0]%>"/><%=AimsConstants.DASHBOARD_APP_RADIO_APPROVE[1]%>&nbsp;
					                            <html:radio property="contentZipFileAction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_DENY[0]%>"/><%=AimsConstants.DASHBOARD_APP_RADIO_DENY[1]%>
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
												<html:radio property="moveToProduction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_ACCEPT[0]%>"/><%=AimsConstants.DASHBOARD_APP_RADIO_ACCEPT[1]%>&nbsp;
					                            <html:radio property="moveToProduction" value="<%=AimsConstants.DASHBOARD_APP_RADIO_REJECT[0]%>"/><%=AimsConstants.DASHBOARD_APP_RADIO_REJECT[1]%>										
											</th>
										</tr>
										<% if ( statusInProduction || statusChannelRejected || statusSunset ) { %>	
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td>
													<strong>Sunset:</strong>
													<br />
													<html:checkbox property="remove" value="<%=AimsConstants.DASHBOARD_APP_CHECKBOX_REMOVE[0]%>"/><%=AimsConstants.DASHBOARD_APP_CHECKBOX_REMOVE[1]%>
												</td>
											</tr>
										<%} %>
									</table>
								</td>
							</tr>
						<% } %>
					<% } %>
					<%-- End Tracking --%>
					<tr>
						<td width="100%">
							<table width="100%" cellpadding="0" cellspacing="0" border="0">
								<%@ include file="include/dashboardAppEditButtons.jsp"%>
								<tr><td><%@ include file="include/dashboardMessageFooter.jsp" %></td> </tr>
							</table>
							<script language="javascript">
								disableAfterProcess();
               				</script>

                			<%if (isEqualOrAboveInitialApproval){%>
                    			<html:hidden property="initialApprovalAction"  />
                			<% } %>
                			<%if (isEqualOrAboveContentApproval){%>
                    			<html:hidden property="contentZipFileAction"  />
                			<% } %>
                			<%if (isEqualOrAboveMoveToProduction){%>
                    			<html:hidden property="moveToProduction"  />                    			
                			<% } %>
                			<%if (isEqualSunset){%>
                    			<html:hidden property="remove"  />                    			
                			<% } %>
						</td>
					</tr>
				</table>
			</div>
		</html:form>
	</div>
</div>
