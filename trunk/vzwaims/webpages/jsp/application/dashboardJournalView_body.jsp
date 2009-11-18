<%@ page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<jsp:useBean id="DashboardApplicationUpdateForm" class="com.netpace.aims.controller.application.DashboardApplicationUpdateForm" scope="request" />

<%DashboardApplicationUpdateForm.setCurrentPage("page4");%>
<%DashboardApplicationUpdateForm.setJournalType("PR");%>
<%DashboardApplicationUpdateForm.setJournalText("");%>

<%@ include file="include/dashboardAppVariables.jsp"%>

<script language="javascript">
    <%@ include  file="include/dashboardJScript.jsp" %>
</script>


<%@ include file="/common/error.jsp"%>

<div id="contentBox" style="float: left">
	<DIV class="homeColumnTab ">
		<%@ include file="include/dashboardViewTabs.jsp"%>
		<html:form action="/dashboardApplicationUpdate" enctype="multipart/form-data">
			<div class="contentbox">
				<%@ include file="include/dashboardAppHidden.jsp"%>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1><bean:message key="JournalForm.currentEntries" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
								<tr>
									<th>
										<html:textarea styleClass="textareaField" property="journalCombinedText" readonly="true" rows="20" cols="90"></html:textarea>
									</th>
								</tr>
								<% if (isVerizonUser) {	%>
								<tr>
									<td>
										<strong><bean:message key="JournalForm.type" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
										<br />
										<html:radio property="journalType" value="PU" />
										<bean:message key="ManageApplicationForm.radio.label.public" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
										&nbsp;
										<html:radio property="journalType" value="PR" />
										<bean:message key="ManageApplicationForm.radio.label.private" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
									</td>
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr>
									<td>
										<strong><bean:message key="JournalForm.newEntry" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
										<br />
										<html:textarea styleClass="textareaField" property="journalText" rows="4" cols="90"></html:textarea>
									</td>
								</tr>
								<% } %>
							</table>
						</td>
					</tr>
					<tr>
						<td width="100%">
							<table width="100%" cellpadding="0" cellspacing="0" border="0">
								<% if (isVerizonUser) {%>
								<tr>
									<td>
										<div class="redBtn"	style="margin-left: 5px; float: right; margin-top: 10px;" id="Submit" title="Submit">
											<div><div><div onclick="document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='submitpage4View';document.forms[0].submit();">
											Submit
											</div></div></div>
										</div>
									</td>
								</tr>
								<% } %>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</html:form>
	</div>
</div>
