<%@ page language="java"%>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<script language="javascript"></script>

<%@ include file="/common/error.jsp"%>

<div id="contentBox" style="float: left">
	<div class="homeColumnTab lBox">
		<html:form action="/profileChangePassword" method="post">
			<DIV class="headLeftCurveblk"></DIV>
			<H1>Change Password</H1>
			<DIV class="headRightCurveblk"></DIV>

			<DIV class="contentbox">
				<table width="100%" border="0" cellspacing="" cellpadding="5" class="GridGradient">
					<tr>
						<td>
							<strong>Current Password&nbsp;<span class="requiredText">*</span>:</strong>
							<br />
							<html:password property="oldPwd" size="40" maxlength="50" styleClass="inputField" />
						</td>
					</tr>
					<tr>
						<td>
							<strong>New Password&nbsp;<span class="requiredText">*</span>:</strong>
							<br />
							<html:password property="newPwd" size="40" maxlength="50" styleClass="inputField" />
						</td>
					</tr>
					<tr>
						<td>
							<strong>Confirm Password&nbsp;<span class="requiredText">*</span>:</strong>
							<br />
							<html:password property="confirmPwd" size="40" maxlength="50" styleClass="inputField" />
						</td>
					</tr>
					<tr>
						<td>
							<div class="redBtn" style="margin-left: 5px; float: right; margin-top: 10px;" id="btnSave" title="Save">
								<div><div><div onclick="document.forms[0].submit()">Save</div></div></div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</html:form>
	</div>
</div>