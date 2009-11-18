<%@ page import="com.netpace.aims.bo.security.AimsSecurityManager" %>
<%@ page import="com.netpace.aims.util.AimsPrivilegesConstants" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<%@ include file="/common/error.jsp" %>

<html:form action="/allianceStatus">

<bean:define id="alliance_id" type="java.lang.Long" name="AllianceForm" property="allianceId"/>
<html:hidden property="alliance_id" value="<%=alliance_id.toString()%>"/>
<html:hidden property="task" value="editSCandAM"/>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<aims:getVZWAllianceTab attributeName="Alliance Summary" allianceId="<%=alliance_id.toString()%>"/>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Basic Information</H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellpadding="0" cellspacing="0" class="GridGradient">
        <tr>
            <th align="left" valign="top">
                <strong>
                <bean:message key="AllianceForm.companyName"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</strong>
            </th>
            <th align="left"><strong>Company Logo&nbsp;:</strong></th>
        </tr>
        <tr>
            <td align="left" style="vertical-align:top">
                <bean:write name="AllianceForm" property="companyName"/>
            </td>
            <td align="left" valign="top">
                <a class="a" target="_blank"
                   href="/aims/resourceAction.do?resource=companyLogo&object=AimsAllianc&alliance_id=<%=alliance_id%>">
                    <img src="/aims/resourceAction.do?resource=companyLogo&object=AimsAllianc&alliance_id=<%=alliance_id%>"
                         width="100" height="75" border="0"/>
                </a>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceForm.allianceMemberSince"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</strong>
                <br/>
                <bean:write name="AllianceForm" property="dateEstablished" formatKey="date.format" filter="true"/>
            </td>
            <td>
                &nbsp;
            </td>
        </tr>
    </table>
</div>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>
    <bean:message key="AllianceForm.listOfContractsAccepted"
                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
</H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
        <tr>
            <th align="center"><strong>
                <bean:message key="AllianceForm.contractTitle"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong></th>
            <th align="center"><strong>
                <bean:message key="AllianceForm.version"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong></th>
            <th align="center"><strong>
                <bean:message key="AllianceForm.datePresentedByVZW"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong></th>
            <th align="center"><strong>
                <bean:message key="AllianceForm.acceptedDeclinedDate"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong></th>
            <th align="center"><strong>
                <bean:message key="AllianceForm.acceptedDeclinedBy"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong></th>
        </tr>
        <logic:iterate id="contract" name="AllianceForm" property="contracts">
            <tr>
                <td align="left">
                    <a class="a"
                       href='/aims/contractsSetup.do?task=editViewForm&contract_id=<bean:write name="contract" property="contractId"/>'>
                        <bean:write name="contract" property="contractTitle"/>
                    </a>
                </td>
                <td align="left">
                    <bean:write name="contract" property="contractVersion"/>
                    &nbsp;
                </td>
                <td align="left">
                    <bean:write name="contract" property="contractPresentDate" formatKey="date.format"
                                filter="true"/>
                    &nbsp;
                </td>
                <td align="left">
                    <bean:write name="contract" property="contractAcceptDeclineDate"
                                formatKey="date.format" filter="true"/>
                    &nbsp;
                </td>
                <td align="left">
                    <bean:write name="contract" property="acceptDeclineFirstName"/>
                    <bean:write name="contract" property="acceptDeclineLastName"/>
                    &nbsp;
                </td>
            </tr>
        </logic:iterate>
    </table>
</div>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Alliance Administrator Information</H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellpadding="0" cellspacing="0" class="GridGradient">
        <tr>
            <th><strong>Alliance Admin Name</strong></th>
            <th width="33%">
                <strong><bean:message key="AllianceForm.phoneNumber"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>
            </th>
            <th width="33%">
                <strong><bean:message key="AllianceForm.email"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>
            </th>
        </tr>
        <tr>
            <td >
                <bean:write name="AllianceForm" property="allianceAdminFirstName"/>
                <bean:write name="AllianceForm" property="allianceAdminLastName"/>
                &nbsp;
            </td>
            <td >
                <bean:write name="AllianceForm" property="allianceAdminPhone"/>
                &nbsp;
            </td>
            <td >
                <bean:write name="AllianceForm" property="allianceAdminEmailAddress"/>
                &nbsp;
            </td>
        </tr>
        <logic:equal name="AllianceForm" property="status" value="Accepted">
	        <tr>
	            <td colspan="3">&nbsp;</td>
	        </tr>
	        <tr>
	            <td colspan="3" align="right">
	                <div class="redBtn" style="float:right;margin-left:10px" id="sendMailToAdobe" title="Send Mail To Adobe">
	                    <div>
	                        <div>
	                            <div onClick="window.location='/aims/allianceStatus.do?task=sendMailToAdobe&alliance_id=<%=alliance_id%>'">Send Mail To Adobe</div>
	                        </div>
	                    </div>
	                </div>
	            </td>
	        </tr>
        </logic:equal>
    </table>
</DIV>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1> Verizon Wireless Sales Contact Information </H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellpadding="0" cellspacing="0" class="GridGradient">
        <tr>
            <th width="33%">
                <strong><bean:message key="AllianceForm.sales"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>
            </th >
            <th width="33%">
                <strong><bean:message key="AllianceForm.phoneNumber"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>
            </th>
            <th>
                <strong><bean:message key="AllianceForm.email"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>
            </th>
        </tr>
        <tr>
            <td >
                <bean:write name="AllianceForm" property="salesContactFirstName"/>
                <bean:write name="AllianceForm" property="salesContactLastName"/>
                &nbsp;
            </td>
            <td>
                <bean:write name="AllianceForm" property="salesContactPhone"/>
                &nbsp;
            </td>
            <td >
                <bean:write name="AllianceForm" property="salesContactEmailAddress"/>
                &nbsp;
            </td>
        </tr>
    </table>
</DIV>

<%
    if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.UPDATE)) {
%>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1> Edit VZW Account Manager </H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellpadding="0" cellspacing="0" class="GridGradient">
        <tr>
            <th width=150><strong>VZW Account Manager</strong></th>
            <th width=150>
                <html:select property="selAccManager" size="1" styleClass="selectField">
                    <html:option value="0">Select One</html:option>
                    <logic:iterate id="accountManager" name="AllianceForm" property="accountManagers"
                                   type="com.netpace.aims.model.core.AimsUser">
                        <html:option value="<%=accountManager.getUserId().toString()%>">
                            <%=accountManager.getAimsContact().getFirstName()%>
                            &nbsp;
                            <%=accountManager.getAimsContact().getLastName()%>
                        </html:option>
                    </logic:iterate>
                </html:select>
            </th>
            <th width=150>
                <%--<html:image src="images/save_b.gif"/>--%>
                <div class="redBtn"  id="save" title="save">
                    <div>
                        <div>
                            <div onclick="document.forms[0].submit()">Save</div>
                        </div>
                    </div>
                </div>
            </th>
            <th > &nbsp; </th>
        </tr>
    </table>
</DIV>

<%
    }
%>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1> Applications Summary </H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
        <tr>
            <th align="center"><strong>
                <bean:message key="AllianceForm.appTitle"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong></th>
            <th align="center"><strong>
                <bean:message key="AllianceForm.dateSubmitted"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong></th>
            <th align="center"><strong>
                <bean:message key="AllianceForm.submittedBy"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong></th>
            <th align="center"><strong>
                <bean:message key="AllianceForm.currentStatus"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong></th>
        </tr>
        <logic:iterate id="app" name="AllianceForm" property="applications">
            <tr>
                <td align="left">
                <c:choose>
                	<c:when test="${not empty app.urlSetupAction and app.phaseId ne 8}">                		
                		<a href="<bean:write name="app"	property="urlSetupAction" />?task=view&appsId=<bean:write name="app" property="appsId" />" class="a"><bean:write name="app" property="appTitle" /></a>
                	</c:when>
                	<c:otherwise>
							<bean:write name="app" property="appTitle"/>                		
                	</c:otherwise>
                </c:choose>&nbsp;
                </td>
                <td align="left">
                    <bean:write name="app" property="submittedDate" formatKey="date.format"
                                filter="true"/>
                    &nbsp;
                </td>
                <td align="left">
                    <bean:write name="app" property="submittedFirstName"/>
                    <bean:write name="app" property="submittedLastName"/>
                    &nbsp;
                </td>
                <td align="left">
                    <bean:write name="app" property="appPphaseName"/>
                </td>
            </tr>
        </logic:iterate>
    </table>
</DIV>

    <DIV>&nbsp;</DIV>

    <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
            <td align="right">
                <logic:equal name="AllianceForm" property="showAcceptRejectButton" value="Y">
                <div class="redBtn" style="float:right;margin-left:10px" id="Accept" title="Accept">
                    <div>
                        <div>
                            <div onClick="window.location='/aims/allianceStatus.do?task=changeStatus&alliance_id=<%=alliance_id%>&vzwStatus=A'">Accept</div>
                        </div>
                    </div>
                </div>
                </logic:equal>
                <logic:equal name="AllianceForm" property="showAcceptRejectButton" value="Y">
                <div class="blackBtn" style="float:right;margin-left:10px" id="Reject" title="Reject">
                    <div>
                        <div>
                            <div onClick="window.location='/aims/allianceStatus.do?task=changeStatus&alliance_id=<%=alliance_id%>&vzwStatus=R'">Reject</div>
                        </div>
                    </div>
                </div>
                </logic:equal>
            </td>
        </tr>
    </table>

</div>
</div>
</html:form>