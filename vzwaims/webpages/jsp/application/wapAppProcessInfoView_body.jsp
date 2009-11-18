<%@ page language="java"
         import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<jsp:useBean id="WapApplicationUpdateForm" class="com.netpace.aims.controller.application.WapApplicationUpdateForm"
             scope="request"/>
<%WapApplicationUpdateForm.setCurrentPage("page3");%>

<%@ include file="include/wapAppVariables.jsp" %>

<script language="javascript">

    <%@ include  file="include/wapAppJScript.jsp" %>
    function callToggleDatePicker(eltName, formElt)
    {
        toggleDatePicker(eltName, document.forms[0].name + '.' + formElt);
    }
</script>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<%@ include file="include/wapAppViewTabs.jsp" %>

<logic:notEmpty name="WapApplicationUpdateForm" property="clonedFromTitle">
    <div>&nbsp;</div>
    <DIV class="contentbox">
        <table width="100%" style="border:solid 1px #CCCCCC" cellpadding="5" cellspacing="0">
            <tr>
                <td width="50%">Clone Reference:&nbsp
                    <a href="/aims/wapApplicationSetup.do?task=view&appsId=<bean:write name="WapApplicationUpdateForm" property="clonedFromId" />"
                       class="a" target="_blank">
                        <bean:write name="WapApplicationUpdateForm" property="clonedFromTitle"/>
                    </a>
                </td>
                <td width="50%">
                    <a href="/aims/wapVersionDifference.do?wapAppsId=<%=WapApplicationUpdateForm.getAppsId()%>&clonedFromId=<bean:write name="WapApplicationUpdateForm" property="clonedFromId" />"
                       class="a" target="_blank">
                        (Click Here to See The Clone Difference)
                    </a>
                </td>
            </tr>
        </table>
    </DIV>
    <div>&nbsp;</div>
</logic:notEmpty>





<DIV class="headLeftCurveblk"></DIV>
<H1>Initial Approval / Denial</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">

<table width="100%" border="0" cellspacing="0" cellpadding="0">

<html:form action="/wapApplicationUpdate" enctype="multipart/form-data">

<% if (statusSubmitted || statusInitialApproval || statusInitialDenied || statusBusinessApprovalGranted ||
        statusBusinessApprovalDenied || statusPendingArm || statusPendingDcr || statusSubmittedDcr ||
        statusTestingPassed || statusTestingFailed || statusPublicationReady || statusCompletedInProduction || statusSunset) { %>
<%if (hasAccessInitialApproval) {%>
<tr>
<td width="100%">
<table width="100%" cellpadding="0" cellspacing="0" class="GridGradient">
<tr>
    <th width="33%"><strong>Application Category:</strong></th>
    <th width="33%"><strong>Application Subcategory:</strong></th>
    <th width="34%"><strong>Link Order:</strong></th>
</tr>
<tr>
    <td valign="top">
        <logic:notEmpty name="WapApplicationUpdateForm" property="categoryId1">
            <logic:iterate id="formCategories" name="WapApplicationUpdateForm" property="allCategories" scope="request">
                <logic:equal name="formCategories" property="categoryId"
                             value="<%=WapApplicationUpdateForm.getCategoryId1().toString()%>">
                    <bean:write name="formCategories" property="categoryName"/>
                </logic:equal>
            </logic:iterate>
        </logic:notEmpty>
        &nbsp;
    </td>
    <td>
        <logic:notEmpty name="WapApplicationUpdateForm" property="subCategoryId1">
            <logic:iterate id="formSubCategories" name="WapApplicationUpdateForm" property="allSubCategories"
                           scope="request">
                <logic:equal name="formSubCategories" property="subCategoryId"
                             value="<%=WapApplicationUpdateForm.getSubCategoryId1().toString()%>">
                    <bean:write name="formSubCategories" property="subCategoryName"/>
                </logic:equal>
            </logic:iterate>
        </logic:notEmpty>
        &nbsp;
    </td>
    <td>
        <bean:write name="WapApplicationUpdateForm" property="linkOrder1"/>
        &nbsp;
    </td>
</tr>
<tr>
    <td valign="top">
        <logic:notEmpty name="WapApplicationUpdateForm" property="categoryId2">
            <logic:iterate id="formCategories" name="WapApplicationUpdateForm" property="allCategories" scope="request">
                <logic:equal name="formCategories" property="categoryId"
                             value="<%=WapApplicationUpdateForm.getCategoryId2().toString()%>">
                    <bean:write name="formCategories" property="categoryName"/>
                </logic:equal>
            </logic:iterate>
        </logic:notEmpty>
        &nbsp;
    </td>
    <td>
        <logic:notEmpty name="WapApplicationUpdateForm" property="subCategoryId2">
            <logic:iterate id="formSubCategories" name="WapApplicationUpdateForm" property="allSubCategories"
                           scope="request">
                <logic:equal name="formSubCategories" property="subCategoryId"
                             value="<%=WapApplicationUpdateForm.getSubCategoryId2().toString()%>">
                    <bean:write name="formSubCategories" property="subCategoryName"/>
                </logic:equal>
            </logic:iterate>
        </logic:notEmpty>
        &nbsp;
    </td>
    <td>
        <bean:write name="WapApplicationUpdateForm" property="linkOrder2"/>
        &nbsp;
    </td>
</tr>
<tr>
    <td valign="top">
        <logic:notEmpty name="WapApplicationUpdateForm" property="categoryId3">
            <logic:iterate id="formCategories" name="WapApplicationUpdateForm" property="allCategories" scope="request">
                <logic:equal name="formCategories" property="categoryId"
                             value="<%=WapApplicationUpdateForm.getCategoryId3().toString()%>">
                    <bean:write name="formCategories" property="categoryName"/>
                </logic:equal>
            </logic:iterate>
        </logic:notEmpty>
        &nbsp;
    </td>
    <td>
        <logic:notEmpty name="WapApplicationUpdateForm" property="subCategoryId3">
            <logic:iterate id="formSubCategories" name="WapApplicationUpdateForm" property="allSubCategories"
                           scope="request">
                <logic:equal name="formSubCategories" property="subCategoryId"
                             value="<%=WapApplicationUpdateForm.getSubCategoryId3().toString()%>">
                    <bean:write name="formSubCategories" property="subCategoryName"/>
                </logic:equal>
            </logic:iterate>
        </logic:notEmpty>
        &nbsp;
    </td>
    <td>
        <bean:write name="WapApplicationUpdateForm" property="linkOrder3"/>
        &nbsp;
    </td>
</tr>
<tr>
    <td width="100%" colspan="3">&nbsp;</td>
</tr>
<tr>
    <td width="33%"><strong>Content Type:</strong></td>
    <td width="33%"><strong>Notes:</strong></td>
    <td width="34%"><strong>Action:</strong></td>
</tr>
<tr>
    <td valign="top">
        <logic:equal name="WapApplicationUpdateForm" property="contentType"
                     value="<%=AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[0]%>">
            <%=AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[1]%>
        </logic:equal>
        <logic:equal name="WapApplicationUpdateForm" property="contentType"
                     value="<%=AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[0]%>">
            <%=AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[1]%>
        </logic:equal>
        &nbsp;
    </td>
    <td rowspan="3" valign="top">
        <html:textarea property="initialApprovalNotes" rows="3" cols="30" readonly="true" styleClass="textareaField"></html:textarea>
    </td>
    <td rowspan="3" valign="top">
        <logic:equal name="WapApplicationUpdateForm" property="initialApprovalAction"
                     value="<%=AimsConstants.WAP_APP_RADIO_INITIAL_APPROVE[0]%>">
            <%=AimsConstants.WAP_APP_RADIO_INITIAL_APPROVE[1]%>
        </logic:equal>
        <logic:equal name="WapApplicationUpdateForm" property="initialApprovalAction"
                     value="<%=AimsConstants.WAP_APP_RADIO_INITIAL_DENY[0]%>">
            <%=AimsConstants.WAP_APP_RADIO_INITIAL_DENY[1]%>
        </logic:equal>
        &nbsp;
    </td>
</tr>
<tr>
    <td width="33%" valign="top"><strong>VZW Projected Live Date:</strong></td>
</tr>
<tr>
    <td valign="top">
        <bean:write name="WapApplicationUpdateForm" property="vzwLiveDate"/>
        &nbsp;
    </td>
</tr>
</table>
</td>
</tr>

<tr>
    <td width="100%">&nbsp;</td>
</tr>
<% } %>
<% } %>

<% if (statusInitialApproval || statusBusinessApprovalGranted || statusBusinessApprovalDenied ||
        statusPendingArm || statusPendingDcr || statusSubmittedDcr || statusTestingPassed ||
        statusTestingFailed || statusPublicationReady || statusCompletedInProduction || statusSunset) { %>
<%if (hasAccessInitialBusinessApproval) {%>
<tr><td>
<DIV class="headLeftCurveblk"></DIV>
<H1>Initial Business Approval / Denial</H1>
<DIV class="headRightCurveblk"></DIV>
</td></tr>
<tr>
    <td >
        <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">            
            <tr>
                <th width="50%"><strong>Action:</strong></th>
                <th width="50%"><strong>Notes:</strong></th>
            </tr>
            <tr>
                <td valign="top">
                    <logic:equal name="WapApplicationUpdateForm" property="businessApprovalAction"
                                 value="<%=AimsConstants.WAP_APP_RADIO_BUSINESS_OK[0]%>">
                        <%=AimsConstants.WAP_APP_RADIO_BUSINESS_OK[1]%>
                    </logic:equal>
                    <logic:equal name="WapApplicationUpdateForm" property="businessApprovalAction"
                                 value="<%=AimsConstants.WAP_APP_RADIO_BUSINESS_ARM[0]%>">
                        <%=AimsConstants.WAP_APP_RADIO_BUSINESS_ARM[1]%>
                    </logic:equal>
                    <logic:equal name="WapApplicationUpdateForm" property="businessApprovalAction"
                                 value="<%=AimsConstants.WAP_APP_RADIO_BUSINESS_DENY[0]%>">
                        <%=AimsConstants.WAP_APP_RADIO_BUSINESS_DENY[1]%>
                    </logic:equal>
                    &nbsp;
                </td>
                <td>
                    <html:textarea property="businessApprovalNotes" rows="3" cols="30" readonly="true" styleClass="textareaField"></html:textarea>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="100%">&nbsp;</td>
</tr>
<% } else {
}%>
<% } else {
}%>

<% if (statusBusinessApprovalGranted || statusPendingArm || statusPendingDcr || statusSubmittedDcr ||
        statusTestingPassed || statusTestingFailed || statusPublicationReady || statusCompletedInProduction || statusSunset) { %>
<%if (hasAccessMoveToPendingDcr) {%>

<tr>
<td>
    <DIV class="headLeftCurveblk"></DIV>
        <H1>Move To Pending DCR</H1>
        <DIV class="headRightCurveblk"></DIV>
</td>
</tr>
<tr>
    <td width="100%">
        <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
            <tr>
                <th width="50%"><strong>WAP Demo/Test URL:</strong></th>
                <th width="50%"><strong>Production URL:</strong></th>
            </tr>
            <tr>
                <td valign="top">
                    <bean:write name="WapApplicationUpdateForm" property="demoUrl"/>
                    &nbsp;
                </td>
                <td valign="top">
                    <bean:write name="WapApplicationUpdateForm" property="productionUrl"/>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td width="50%"><strong>Vendor Split Percentage (%):</strong></td>
                <td width="50%"></td>
            </tr>
            <tr>
                <td valign="top">
                    <bean:write name="WapApplicationUpdateForm" property="vendorSplitPercentage"/>
                    &nbsp;
                </td>
                <td valign="top"></td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="100%">&nbsp;</td>
</tr>
<% } else {
}%>
<% } else {
}%>

<% if (statusPendingDcr || statusSubmittedDcr || statusTestingPassed || statusTestingFailed ||
        statusPublicationReady || statusCompletedInProduction || statusSunset) { %>
<%if (hasAccessPendingDcr) {%>
<tr><td>
    <DIV class="headLeftCurveblk"></DIV>
    <H1>Pending DCR</H1>
    <DIV class="headRightCurveblk"></DIV>
</td></tr>
<tr>
    <td width="100%">
        <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
            <tr>
                <th width="50%"><strong>Vendor Product ID:</strong></th>
                <th width="50%"><strong>Pending DCR Version:</strong></th>
            </tr>
            <tr>
                <td valign="top">
                    <bean:write name="WapApplicationUpdateForm" property="vendorProductCode"/>
                    &nbsp;
                </td>
                <td valign="top">
                    <bean:write name="WapApplicationUpdateForm" property="pendingDcrVersion"/>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td width="50%"><strong>VZW Suggested Retail Price (USD):</strong></td>
                <td width="50%"><strong>Tax Category Code:</strong></td>
            </tr>
            <tr>
                <td valign="top">
                    <bean:write name="WapApplicationUpdateForm" property="vzwRetailPrice"/>
                    &nbsp;
                </td>
                <td valign="top">
                    <logic:notEmpty name="WapApplicationUpdateForm" property="taxCategoryCodeId">
                        <logic:iterate id="taxCategoryCodes" name="WapApplicationUpdateForm"
                                       property="allTaxCategoryCodes" scope="request">
                            <logic:equal name="taxCategoryCodes" property="taxCategoryCodeId"
                                         value="<%=WapApplicationUpdateForm.getTaxCategoryCodeId().toString()%>">
                                <bean:write name="taxCategoryCodes" property="taxCategoryCode"/>
                                -
                                <bean:write name="taxCategoryCodes" property="taxCategoryCodeDesc"/>
                            </logic:equal>
                        </logic:iterate>
                    </logic:notEmpty>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td width="50%"><strong>Vendor Product Display:</strong></td>
                <td width="50%"><strong>License Type:</strong></td>
            </tr>
            <tr>
                <td valign="top">
                    <html:textarea property="vendorProductDisplay" rows="3" cols="30" readonly="true"></html:textarea>
                </td>
                <td valign="top">
                    <logic:notEmpty name="WapApplicationUpdateForm" property="listSelectedLicenseTypes" scope="request">
                    <logic:iterate id="licenses" name="WapApplicationUpdateForm" property="allLicenseTypes">
                    <%for (int i = 0; i < WapApplicationUpdateForm.getListSelectedLicenseTypes().length; i++) {%>
                    <logic:equal name="licenses" property="licenseTypeId"
                                 value="<%=(WapApplicationUpdateForm.getListSelectedLicenseTypes())[i].toString()%>">
                    <bean:write name="licenses" property="licenseTypeName"/>
                <br/>
                </logic:equal>
                    <% } %>
                </logic:iterate>
                </logic:notEmpty>
                &nbsp;
    </td>
</tr>
<tr>
    <td width="50%"><strong>Page View Rate:</strong></td>
    <td width="50%"><strong>Notes:</strong></td>
</tr>
<tr>
    <td>
        <bean:write name="WapApplicationUpdateForm" property="pageViewRate"/>
        &nbsp;
    </td>
    <td valign="top">
        <html:textarea property="pendingDcrNotes" rows="3" cols="30" readonly="true"></html:textarea>
    </td>
</tr>
<tr>
    <td width="50%"><strong>Previous DCR Version Comparisions:</strong></td>
    <td width="50%"></td>
</tr>
<tr>
    <td valign="top">
        <logic:notEmpty name="WapApplicationUpdateForm" property="wapAppVersions">
            <% String newVer = WapApplicationUpdateForm.getPendingDcrVersion(); %>
            <% String oldVer = ""; %>
            <%for (int i = WapApplicationUpdateForm.getWapAppVersions().length - 1; i >= 0; i--) {%>
            <% oldVer = (WapApplicationUpdateForm.getWapAppVersions()[i]).toString();%>
            <a target="_blank"
               href="/aims/wapVersionDifference.do?wapAppsId=<%=WapApplicationUpdateForm.getAppsId()%>&newVersion=<%=newVer%>&oldVersion=<%=oldVer%>">Differences
                between Ver. <%=newVer%> and <%=oldVer%>
            </a><br/>
            <% newVer = oldVer; %>
            <% } %>
        </logic:notEmpty>
    </td>
    <td valign="top"></td>
</tr>
</table>
</td></tr>
<tr>
    <td width="100%">&nbsp;</td>
</tr>
<% } else {
}%>
<% } else {
}%>

<% if (statusSubmittedDcr || statusTestingPassed || statusTestingFailed ||
        statusPublicationReady || statusCompletedInProduction || statusSunset) { %>
<%if (hasAccessContentTesting) {%>
<tr>
    <td>
        <DIV class="headLeftCurveblk"></DIV>
        <H1>
            <bean:message key="ApplicationForm.table.head.testing"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </H1>
        <DIV class="headRightCurveblk"></DIV>
    </td>
</tr>
<tr>
    <td width="100%">
        <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
            <tr>
                <th align="left">
                    <strong><bean:message key="ApplicationForm.testingName"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    :</strong>
                </th>
                <th align="left">
                    <strong><bean:message key="ApplicationForm.testingDate"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    :</strong>
                </th>
                <th align="left">
                    <strong><bean:message key="ApplicationForm.testingStatus"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    :</strong>
                </th>
                <th align="left">
                    <strong><bean:message key="ApplicationForm.testingFile"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    :</strong>
                </th>
            </tr>
            <logic:notEmpty name="WapApplicationUpdateForm" property="testId">
                <logic:iterate id="phases" name="WapApplicationUpdateForm" property="testId" indexId="ctr">
                    <tr>
                        <td align="left"><b>
                            <bean:write name="WapApplicationUpdateForm" property='<%= "testName[" + ctr + "]" %>'/>
                        </b></td>
                        <td align="left">
                            <bean:write name="WapApplicationUpdateForm" property='<%= "testedDate[" + ctr + "]" %>'/>
                            &nbsp;
                        </td>
                        <td align="left">
                            <logic:equal name="WapApplicationUpdateForm" property="businessApprovalAction" value="P">
                                <bean:message key="ManageApplicationForm.radio.label.passed"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </logic:equal>
                            <logic:equal name="WapApplicationUpdateForm" property="businessApprovalAction" value="F">
                                <bean:message key="ManageApplicationForm.radio.label.failed"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </logic:equal>
                            &nbsp;
                        </td>
                        <td align="left">
                            <logic:notEmpty name="WapApplicationUpdateForm"
                                            property='<%= "testResultFileName[" + ctr + "]" %>'>
                                <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=resultFile&app_id=<bean:write name="WapApplicationUpdateForm" property="appsId"/>&composite_id=<bean:write name="phases" />"
                                   class="a" target="_blank">
                                    <bean:write name="WapApplicationUpdateForm"
                                                property='<%= "testResultFileName[" + ctr + "]" %>'/>
                                </a>
                            </logic:notEmpty>
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td align="left" style="vertical-align:top;">
                            <logic:notEmpty name="WapApplicationUpdateForm"
                                            property='<%= "testUpdatedBy[" + ctr + "]" %>'>
                                Updated By: <br/>
                                <bean:write name="WapApplicationUpdateForm"
                                            property='<%= "testUpdatedBy[" + ctr + "]" %>'/>
                                <br/>
                                (
                                <bean:write name="WapApplicationUpdateForm"
                                            property='<%= "testUpdatedDate[" + ctr + "]" %>'/>
                                )
                            </logic:notEmpty>
                        </td>
                        <td colspan="3" align="left">
                            <html:textarea property='<%= "testComments[" + ctr + "]" %>' readonly="true" rows="4"
                                           cols="45"></html:textarea>
                        </td>
                    </tr>
                </logic:iterate>
            </logic:notEmpty>
        </table>
    </td>
</tr>
<tr>
    <td width="100%">&nbsp;</td>
</tr>
<tr>
    <td width="100%">
        <DIV class="headLeftCurveblk"></DIV>
        <H1>
            VZW Projected Live Date
        </H1>
        <DIV class="headRightCurveblk"></DIV>
    </td>
</tr>
<tr>
    <td width="100%">
        <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
            <tr>
                <th valign="top">
                    <bean:write name="WapApplicationUpdateForm" property="vzwLiveDate"/>&nbsp;
                </th>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="100%">&nbsp;</td>
</tr>
<% } %>
<% } %>
</html:form>
</table>
</DIV>
<%@ include file="include/wapAppViewButtons.jsp" %>


</DIV>
</DIV>
