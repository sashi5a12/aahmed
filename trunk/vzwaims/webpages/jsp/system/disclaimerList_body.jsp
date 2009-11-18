<%@ page language="java"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ include file="/common/error.jsp"%>

<DIV class="homeColumnTab lBox">
	<DIV class="headLeftCurveblk"></DIV>
	<H1><bean:message key="DisclaimerForm.heading.list" /></H1>

	<DIV class="headRightCurveblk"></DIV>
	<DIV class="contentbox">

		<table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
			<tr>
				<th><bean:message key="DisclaimerForm.label.disclaimerName" /></th>
				<th><bean:message key="DisclaimerForm.label.modifiedDate" /></th>
				<th><bean:message key="DisclaimerForm.label.edit" /></th>
			</tr>
			<logic:iterate id="disclaimer" name="disclaimerList" type="com.netpace.aims.model.system.AimsDisclaimers">
				<tr>
					<td align="left">
						<html:link action="disclaimerSetup.do?taskName=view"
							paramId="disclaimerId" paramProperty="disclaimerId"
							paramName="disclaimer">
							<bean:write name="disclaimer" property="disclaimerName" />
						</html:link>&nbsp;
					</td>
					<td align="left">
						<bean:write name="disclaimer" property="modifiedDate" formatKey="date.format" filter="true" />&nbsp;
					</td>
					<td align="center">
						<html:link action="disclaimerSetup.do?taskName=edit"
							paramId="disclaimerId" paramProperty="disclaimerId"
							paramName="disclaimer">
							<html:img src="images/edit_icon.gif" border="0" alt="Edit" />
						</html:link>&nbsp;
					</td>
				</tr>
			</logic:iterate>
		</table>
	</DIV>
</DIV>
