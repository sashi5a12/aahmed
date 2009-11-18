<%@ page language="java"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<script language="javascript"></script>

<%@ include file="/common/error.jsp"%>

<div id="contentBox" style="float: left">
	<div class="homeColumnTab lBox">
		<html:form action="/accountsInvite" method="post">
			<html:hidden property="task" value="edit"/>
			<DIV class="headLeftCurveblk"></DIV>
			<H1>Invite User</H1>
			<DIV class="headRightCurveblk"></DIV>

			<DIV class="contentbox">
				<table width="100%" border="0" cellspacing="" cellpadding="5" class="GridGradient">
                    <th colspan="2" width="100%">
                        <strong>Note</strong>:<br/>
                        This invitation will not send an email to the invited user. Please inform the user to login into the VDC Portal and accept your invitation in the Go To Market Area. If the user does not have an account on VDC, then they will have to register as well.
                    </th>
                    <tr>
						<td width="5%" nowrap>
							<strong>User Email&nbsp;<span class="requiredText">*</span>:</strong>&nbsp;
						</td>
                        <td><html:text property="userEmail" size="40" maxlength="100" styleClass="inputField" /></td>
                    </tr>
					<tr>
						<td colspan="2" align="right">
                            <div class="divButtons" style="float:right;">
                                <div class="blackBtn" title="Cancel" style="float:left;">
                                    <div><div><div onclick="window.location='/aims/accounts.do?task=view'">Cancel</div></div></div>
                                </div>
                                <div class="redBtn" style="float: left;" id="btnInvite" title="Invite">
                                    <div><div><div onclick="document.forms[0].submit()">Invite</div></div></div>
                                </div>
                            </div>
                        </td>
					</tr>
				</table>
			</div>
		</html:form>
	</div>
</div>