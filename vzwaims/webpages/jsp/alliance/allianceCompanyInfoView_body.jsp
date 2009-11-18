<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.AimsUser, com.netpace.aims.util.AimsConstants  " %>

<%--
    -- this page will be used by both alliance(seconday user) and verizon user to view alliance Company info
	-- show vzwAlliance tabs if user is verizon otherwise show alliance tabs
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<%@ include file="/common/error.jsp" %>

<%  boolean isVerizonUser = ((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE); %>

<html:form action="/allianceCompInfoEdit">
<bean:define id="alliance_id" type="java.lang.Long" name="AllianceCompInfoForm" property="allianceId"/>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<%-- for verizon user show verizon tabs, otherwise show alliance tabs--%>
<%  if(isVerizonUser) { %>
    <aims:getVZWAllianceTab attributeName="Company Info" allianceId="<%=alliance_id.toString()%>"/>
<%
    }
    else {
%>
    <aims:getAllianceTab attributeName="Company Info"/>
<%  }   %>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Basic Company Information</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table  width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th width="50%">
            <strong><bean:message key="AllianceCompInfoForm.companyName"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            :</strong>
        </th>
        <th  width="50%">
            <strong>
            <bean:message key="AllianceCompInfoForm.companyLegalName"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            :</strong>
        </th>
    </tr>
    <tr>
        <td valign="top">
            <bean:write name="AllianceCompInfoForm" property="companyName"/>
        </td>
        <td >
            <bean:write name="AllianceCompInfoForm" property="companyLegalName"/>
        </td>
    </tr>
    <tr>
        <td >
            <strong><bean:message key="AllianceCompInfoForm.websiteURL"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            :</strong>
        </td>
        <td >
            <strong><bean:message key="AllianceCompInfoForm.prevRevenues"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            :</strong>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <bean:write name="AllianceCompInfoForm" property="webSiteUrl"/>
        </td>
        <td >
            <bean:write name="AllianceCompInfoForm" property="prevYearRevenues"/>
        </td>
    </tr>
    <tr>
        <td ><strong>Is Company Public?
            :</strong></td>
        <td >&nbsp;</td>
    </tr>
    <tr>
        <td valign="top">
            <bean:write name="AllianceCompInfoForm" property="isFinanceInfoPublic"/>
        </td>
        <td >
            &nbsp;
        </td>
    </tr>
    <tr>
        <td >
            <strong><bean:message key="AllianceCompInfoForm.stateOfInc"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</strong>
        </td>
        <td >
            <strong><bean:message key="AllianceCompInfoForm.authorizedRepresentative"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</strong>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <bean:write name="AllianceCompInfoForm" property="stateOfInc"/>
        </td>
        <td >
            <bean:write name="AllianceCompInfoForm" property="authRepName"/>
        </td>
    </tr>
    <tr>
        <td >
            <strong>
                    <bean:message key="AllianceCompInfoForm.address"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</strong>
        </td>
        <td >
            <strong>
                    <bean:message key="AllianceCompInfoForm.city"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</strong>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <bean:write name="AllianceCompInfoForm" property="streetAddress1"/>
        </td>
        <td >
            <bean:write name="AllianceCompInfoForm" property="city"/>
        </td>
    </tr>
    <tr>
        <td >
            <strong>
                <bean:message key="AllianceCompInfoForm.state"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</strong>
        </td>
        <td >
            <strong>
                <bean:message key="AllianceCompInfoForm.zipcode"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</strong>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <bean:write name="AllianceCompInfoForm" property="state"/>
        </td>
        <td >
            <bean:write name="AllianceCompInfoForm" property="zipCode"/>
        </td>
    </tr>
    <tr>
        <td >
            <strong>
                <bean:message key="AllianceCompInfoForm.country"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</strong>
        </td>
        <td >
            <strong>
                <bean:message key="AllianceCompInfoForm.vendorId"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                :</strong>
        </td>
    </tr>
    <tr>
        <td >
            <bean:write name="AllianceCompInfoForm" property="country"/>
        </td>
        <td valign="top">
            <bean:write name="AllianceCompInfoForm" property="vendorId"/>
        </td>
    </tr>
</table>
</DIV>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Alliance Information</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table  width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th><strong>Alliance with other carriers&nbsp;:</strong></th>
    </tr>
    <tr>        
        <td valign="top">
            <logic:iterate id="assigendAllianceWithOtherCarrier" name="AllianceCompInfoForm"
                           property="assigendAlliancesWithOtherCarriers">
                <bean:write name="assigendAllianceWithOtherCarrier" property="carrierName"/>
                <br/>
            </logic:iterate>
            <br/>
            <bean:write name="AllianceCompInfoForm" property="allianceWithOtherCarriers"/>
        </td>
    </tr>
</table>
</DIV>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Remittance Information</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table  width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th colspan="2" width="50%">
            <strong><bean:message key="AllianceRegistrationForm.remitTo" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
            :</strong>
        </th>        
    </tr>
    <tr>
        <td colspan="2" valign="top">
            <bean:write name="AllianceCompInfoForm" property="remitTo"/>
        </td>        
    </tr>
    <tr>
        <td >
            <strong><bean:message key="AllianceRegistrationForm.Address1" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
            :</strong>
        </td>
        <td >
            <strong><bean:message key="AllianceRegistrationForm.address2" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
            :</strong>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <bean:write name="AllianceCompInfoForm" property="remitAddress1"/>
        </td>
        <td >
            <bean:write name="AllianceCompInfoForm" property="remitAddress2"/>
        </td>
    </tr>
    
    <tr>
        <td >
            <strong><bean:message key="AllianceRegistrationForm.City" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
            :</strong>
        </td>
        <td >
            <strong><bean:message key="AllianceRegistrationForm.State" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
            :</strong>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <bean:write name="AllianceCompInfoForm" property="remitCity"/>
        </td>
        <td >
            <bean:write name="AllianceCompInfoForm" property="remitState"/>
        </td>
    </tr>

    <tr>
        <td >
            <strong><bean:message key="AllianceRegistrationForm.PostalCode" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
            :</strong>
        </td>
        <td >
            <strong><bean:message key="AllianceRegistrationForm.Country" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
            :</strong>
        </td>
    </tr>
    <tr>
        <td valign="top">
            <bean:write name="AllianceCompInfoForm" property="remitPostalCode"/>
        </td>
        <td >
            <bean:write name="AllianceCompInfoForm" property="remitCountry"/>
        </td>
    </tr>

</table>
</DIV>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Industry Focus</H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
<table  width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr height="100%">
        <th>            
            <logic:iterate id="assIndFocus" name="AllianceCompInfoForm" property="assignedIndustryFocus"
                           type="com.netpace.aims.model.masters.AimsIndustryFocu">
                <bean:write name="assIndFocus" property="industryName"/>
                <br/>
            </logic:iterate>&nbsp;
        </th>
    </tr>
    <tr>
        <td valign="top">
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td >Other</td>
                    <td >
                        <bean:write name="AllianceCompInfoForm" property="otherIndustryFocus"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</DIV>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Regions Present in</H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
<table  width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr height="100%">
        <th valign="top" height="100%">
            <logic:iterate id="assRegion" name="AllianceCompInfoForm" property="assignedRegions"
                           type="com.netpace.aims.model.masters.AimsRegion">
                <bean:write name="assRegion" property="regionName"/>
                <br/>
            </logic:iterate> &nbsp;
        </th>
    </tr>
</table>
</DIV>
</html:form>





