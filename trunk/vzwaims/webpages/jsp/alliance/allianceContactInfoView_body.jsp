<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.AimsUser, com.netpace.aims.util.AimsConstants  " %>

<%--
    -- this page will be used by both alliance(seconday user) and verizon user to view alliance Contact info
	-- show vzwAlliance tabs if user is verizon otherwise show alliance tabs
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<html:form action="/allianceContactInfoEdit">
<bean:define id="alliance_id" type="java.lang.Long" name="AllianceContactInfoForm" property="allianceId"/>

<%@ include file="/common/error.jsp" %>

<%  boolean isVerizonUser = ((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE); %>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab">

<%-- for verizon user show verizon tabs, otherwise show alliance tabs--%>
<%  if(isVerizonUser) { %>
    <aims:getVZWAllianceTab attributeName="Contact Info" allianceId="<%=alliance_id.toString()%>"/>
<%
    }
    else {
%>
    <aims:getAllianceTab attributeName="Contact Info"/>
<%  }   %>

<div>&nbsp;</div>

<div style="padding-bottom:10px">
    <strong>Company Name:
        <bean:write name="AllianceContactInfoForm" property="companyName"/>
    </strong>
</div>

<html:hidden property="task" value="edit"/>


<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
    <td>
        <div class="mmBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>
                <bean:message key="AllianceContactInfoForm.execContact"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
    <td>
        <div class="mmBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>
                <bean:message key="AllianceContactInfoForm.businessContact"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
<td valign="top">
    <table width="395" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th>
                <strong>
                    <bean:message key="AllianceContactInfoForm.firstName"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </th>
            <th>
                <bean:write name="AllianceContactInfoForm" property="execContactFirstName"/>
            </th>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.lastName"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="execContactLastName"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.title"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="execContactTitle"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.email"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="execContactEmailAddress"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.phone"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="execContactPhone"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.mobile"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="execContactMobile"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.fax"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="execContactFax"/>
            </td>
        </tr>
    </table>
</td>
<td valign="top">
    <table width="395" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th>
                <strong>
                    <bean:message key="AllianceContactInfoForm.firstName"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </th>
            <th>
                <bean:write name="AllianceContactInfoForm" property="busContactFirstName"/>
            </th>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.lastName"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="busContactLastName"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.title"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="busContactTitle"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.email"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="busContactEmailAddress"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.phone"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="busContactPhone"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.mobile"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="busContactMobile"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.fax"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="busContactFax"/>
            </td>
        </tr>
    </table>
</td>
</tr>
<tr>
    <td colspan="2"> &nbsp; </td>
</tr>

<tr>
    <td>
        <div class="mmBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>
                <bean:message key="AllianceContactInfoForm.mktgContact"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
    <td>
        <div class="mmBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>
                <bean:message key="AllianceContactInfoForm.techContact"
                              bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            </H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>

<tr>
<td valign="top">
    <table width="395" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th>
                <strong>
                    <bean:message key="AllianceContactInfoForm.firstName"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </th>
            <th>
                <bean:write name="AllianceContactInfoForm" property="mktgContactFirstName"/>
            </th>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.lastName"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="mktgContactLastName"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.title"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="mktgContactTitle"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.email"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="mktgContactEmailAddress"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.phone"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="mktgContactPhone"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.mobile"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="mktgContactMobile"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.fax"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="mktgContactFax"/>
            </td>
        </tr>
    </table>
</td>
<td valign="top">
    <table width="395" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th>
                <strong>
                    <bean:message key="AllianceContactInfoForm.firstName"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </th>
            <th>
                <bean:write name="AllianceContactInfoForm" property="techContactFirstName"/>
            </th>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.lastName"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="techContactLastName"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.title"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="techContactTitle"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.email"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="techContactEmailAddress"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.phone"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="techContactPhone"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.mobile"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="techContactMobile"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceContactInfoForm.fax"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    :</strong>
            </td>
            <td>
                <bean:write name="AllianceContactInfoForm" property="techContactFax"/>
            </td>
        </tr>
    </table>
</td>
</tr>
</table>


<div> &nbsp; </div>

<div class="lBox">
    <DIV class="headLeftCurveblk"></DIV>
    <H1>
        <bean:message key="AllianceContactInfoForm.escalationProcedure"
                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
    </H1>

    <DIV class="headRightCurveblk"></DIV>
</div>
<div class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th>
            <html:textarea property="escalationInstructions" rows="5" cols="75" readonly="true" styleClass="textareaField"/>
        </th>
    </tr>
</table>
</div>

</html:form>
