<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.AimsUser, com.netpace.aims.util.AimsConstants  " %>

<%--
    -- this page will be used by both alliance(seconday user) and verizon user to view alliance Business info
	-- show vzwAlliance tabs if user is verizon otherwise show alliance tabs
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<html:form action="/allianceBusInfoEdit">
<bean:define id="alliance_id" type="java.lang.Long" name="AllianceBusInfoForm" property="allianceId"/>

<%@ include file="/common/error.jsp" %>

<%  boolean isVerizonUser = ((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE); %>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab">

<%-- for verizon user show verizon tabs, otherwise show alliance tabs--%>
<%  if(isVerizonUser) { %>
    <aims:getVZWAllianceTab attributeName="Business Info" allianceId="<%=alliance_id.toString()%>"/>
<%
    }
    else {
%>
    <aims:getAllianceTab attributeName="Business Info"/>
<%  }   %>

<div>&nbsp;</div>

<div style="padding-bottom:10px">
    <strong>Company Name:
        <bean:write name="AllianceBusInfoForm" property="companyName"/>
    </strong>
</div>

<div class="lBox">
<DIV class="headLeftCurveblk"></DIV>
<H1>Establishment Information</H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th><strong>
                <bean:message key="AllianceBusInfoForm.dateEstablished"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong>
            </th>
        </tr>
        <tr>
            <td>
                <bean:write name="AllianceBusInfoForm" property="dateEstablished"/>
            </td>
        </tr>
        <tr>
            <td><strong>
                <bean:message key="AllianceBusInfoForm.countryOfOrigin"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong>
            </td>
        </tr>
        <tr>
            <td>
                <bean:write name="AllianceBusInfoForm" property="countryOfOrigin"/>
            </td>
        </tr>
    </table>
</DIV>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Business Documentation</H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th width="50%"><strong>
                <bean:message key="AllianceBusInfoForm.companyPresentation"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong>
            </th>
            <th width="50%"><strong>
                <bean:message key="AllianceBusInfoForm.companyLogo"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </strong>
            </th>
        </tr>
        <tr>
            <td width="50%">
                <a class="a" target="_blank"
                   href="/aims/resourceAction.do?resource=companyPresentation&object=AimsAllianc&alliance_id=<%=alliance_id%>">
                    <bean:write name="AllianceBusInfoForm" property="companyPresentationFileName"/>
                </a>
            </td>
            <td width="50%">
                <a class="a" target="_blank"
                   href="/aims/resourceAction.do?resource=companyLogo&object=AimsAllianc&alliance_id=<%=alliance_id%>">
                    <bean:write name="AllianceBusInfoForm" property="companyLogoFileName"/>
                </a>
            </td>
        </tr>
        <tr>
            <td width="50%"><strong>
                <bean:message key="BrewApplicationForm.appProgGuide"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
            </strong>
            </td>
            <td width="50%">&nbsp;</td>
        </tr>
    </table>
</div>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Employment Information</H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th width="100%">
                <strong>
                    <bean:message key="AllianceBusInfoForm.numOfEmployees"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                </strong>
                <br/>
                <bean:write name="AllianceBusInfoForm" property="employeesRange"/>
            </th>
        </tr>
    </table>
</DIV>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1><strong>Competitive Information</strong></H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th width="50%">
                <strong>
                    <bean:message key="AllianceBusInfoForm.comptetitiveAdvantages"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                </strong>
                <br/>
                <html:textarea property="competitiveAdvantages" rows="3" cols="50" readonly="true"
                               styleClass="inputField"/>
            </th>
            <th width="50%">
                <strong>
                    <bean:message key="AllianceBusInfoForm.partner"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                </strong>
                <br/>
                <html:textarea property="partner" rows="3" cols="50" readonly="true" styleClass="inputField"/>
            </th>
        </tr>
        <tr>
            <td width="50%">
                <strong>
                    <bean:message key="AllianceBusInfoForm.financing"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                </strong>
                <br/>
                <logic:iterate id="assignedAllianceFinancingOption" name="AllianceBusInfoForm"
                               property="assignedAllianceFinancing">
                    <bean:write name="assignedAllianceFinancingOption" property="financingOptions"/>
                    <br/>
                </logic:iterate>
            </td>
            <td width="50%">
                <strong>
                    <bean:message key="AllianceBusInfoForm.development"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                </strong>
                <br/>
                <logic:iterate id="assignedAllianceDevelopment" name="AllianceBusInfoForm"
                               property="assignedAllianceDevelopments">
                    <bean:write name="assignedAllianceDevelopment" property="developmentType"/>
                    <br/>
                </logic:iterate>
                <br/>
                <bean:write name="AllianceBusInfoForm" property="outsourceDevelopmentPublisherName"/>
            </td>
        </tr>
    </table>
</DIV>

</div>

<div>&nbsp;</div>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
        <td valign="top">
            <div class="mmBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Partnership Possibilities</H1>

                <DIV class="headRightCurveblk"></DIV>
            </DIV>
        </td>
        <td valign="top">
            <div class="mmBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Development Technologies</H1>

                <DIV class="headRightCurveblk"></DIV>
            </div>
        </td>
    </tr>
    <tr>
        <td height="100%" valign="top">
            <table width="395" height="100%" cellpadding="0" cellspacing="0" class="GridGradient">
                <tr>
                    <th>
                        <logic:iterate id="assRoles" name="AllianceBusInfoForm" property="assignedRoles"
                                       type="com.netpace.aims.model.masters.AimsRolesOfAlliance">
                            <strong><bean:write name="assRoles" property="roleName"/></strong>
                        </logic:iterate>
                        &nbsp;
                    </th>
                </tr>
            </table>
        </td>
        <td height="100%" valign="top">
            <table width="395" height="100%" cellpadding="0" cellspacing="0" class="GridGradient">
                <tr>
                    <th>
                        <logic:iterate id="assDevTechs" name="AllianceBusInfoForm"
                                       property="assignedDevTechnologies"
                                       type="com.netpace.aims.model.masters.AimsDevTechnology">
                            <strong><bean:write name="assDevTechs" property="devTechnologyName"/></strong>
                            <br/>
                        </logic:iterate>
                        &nbsp;
                    </th>
                </tr>
            </table>
        </td>
    </tr>
</table>

</html:form>