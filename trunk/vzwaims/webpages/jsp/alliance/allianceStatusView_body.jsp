<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<bean:define id="alliance_id" type="java.lang.Long" name="AllianceForm" property="allianceId"/>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<aims:getAllianceTab attributeName="Alliance Summary"/>
<div>&nbsp;</div>

<html:form action="/allianceStatus">

<DIV class="headLeftCurveblk"></DIV>
<H1>Basic Information</H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th>
            <b>
                <bean:message key="AllianceForm.companyName"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</b>
        </th>
        <th><b>Company Logo:</b></th>
    </tr>
    <tr>
        <td>
            <bean:write name="AllianceForm" property="companyName"/>
        </td>
        <td>
            <a class="a" target="_blank"
               href="/aims/resourceAction.do?resource=companyLogo&object=AimsAllianc&alliance_id=<%=alliance_id%>">
                <img src="/aims/resourceAction.do?resource=companyLogo&object=AimsAllianc" width="100"
                     height="75" border="0"/>
            </a>
        </td>
    </tr>
    <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>
            <b>
                <bean:message key="AllianceForm.allianceMemberSince"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</b>
        </td>
        <td>&nbsp;</td>
    </tr>
    <tr>
        <td>
            <bean:write name="AllianceForm" property="dateEstablished" formatKey="date.format" filter="true"/>
        </td>
        <td>&nbsp;</td>
    </tr>
</table>
</div>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>
    <bean:message key="AllianceForm.listOfContractsAccepted" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">    
    <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
        <tr>
            <th align="center">
                <strong>
                    <bean:message key="AllianceForm.contractTitle"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                </strong>
            </th>
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
<H1> Alliance Administrator Information </H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th><b>Alliance Admin Name</b></th>
            <th>
                <b>
                    <bean:message key="AllianceForm.phoneNumber"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                </b>
            </th>
            <th>
                <b>
                    <bean:message key="AllianceForm.email"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                </b>
            </th>
        </tr>
        <tr>
            <td>
                <bean:write name="AllianceForm" property="allianceAdminFirstName"/>
                <bean:write name="AllianceForm" property="allianceAdminLastName"/>
                &nbsp;
            </td>
            <td>
                <bean:write name="AllianceForm" property="allianceAdminPhone"/>
                &nbsp;
            </td>
            <td>
                <bean:write name="AllianceForm" property="allianceAdminEmailAddress"/>
                &nbsp;
            </td>
        </tr>
    </table>
</div>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1> Verizon Wireless Account Manager Information </H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th><b>
                <bean:message key="AllianceForm.vzwAccountManager"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </b>
            </th>
            <th><b>
                <bean:message key="AllianceForm.phoneNumber"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </b>
            </th>
            <th><b>
                <bean:message key="AllianceForm.email"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </b>
            </th>
        </tr>
        <tr>
            <td>
                <bean:write name="AllianceForm" property="vzwAccMgrFirstName"/>
                <bean:write name="AllianceForm" property="vzwAccMgrLastName"/>
                &nbsp;
            </td>
            <td>
                <bean:write name="AllianceForm" property="vzwAccMgrPhone"/>
                &nbsp;
            </td>
            <td>
                <bean:write name="AllianceForm" property="vzwAccMgrEmailAddress"/>
                &nbsp;
            </td>
        </tr>
    </table>
</div>

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
            <th align="center">
                <strong>
                    <bean:message key="AllianceForm.currentStatus"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                </strong>
            </th>
        </tr>
        <logic:iterate id="app" name="AllianceForm" property="applications">
            <tr>
                <td align="left">
                    <c:choose>
	                	<c:when test="${not empty app.urlSetupAction}">                		
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
                </td>
                <td align="left">
                    <bean:write name="app" property="appPphaseName"/>
                </td>
            </tr>
        </logic:iterate>
    </table>
</div>

</html:form>
</div>
</div>
