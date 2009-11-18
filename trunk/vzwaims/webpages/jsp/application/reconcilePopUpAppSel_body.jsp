<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<script language="javascript">
    <!--
    function getSelectedValue()
    {
        if (eval(document.forms[0].combineId))
            if (eval(document.forms[0].combineId.length))
            {
                for (iCount = 0; iCount < document.forms[0].combineId.length; iCount++)
                    if (document.forms[0].combineId[iCount].checked)
                        return document.forms[0].combineId[iCount].value;
            }
            else
            {
                if (document.forms[0].combineId.checked)
                    return document.forms[0].combineId.value;
            }
        return null;
    }

    function assignValuesToParent()
    {
        var strResult = getSelectedValue();

        if (strResult != null && eval(opener.document.forms[0]))
        {
            opener.document.forms[0].choseCombineId.value = strResult;
            opener.document.forms[0].task.value = "updateSelection";
            opener.document.forms[0].submit();
            self.close();
        }
        else
        {
            if (eval(opener))
                alert("<bean:message key="ReconciliationCatalog.selectApplication" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>");
            else
            {
                alert("<bean:message key="ReconciliationCatalog.noParentPresent" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>");
                window.close();
            }
        }
    }
    //-->
</script>

<%@ include file="/common/error.jsp" %>

<DIV class="homeColumnTab" style="width:490px;">

<html:form action="/reconcileCompAppsSelect">
    <html:hidden property="catalogDataId"/>
    <html:hidden property="task"/>

    <table border=0 cellpadding=5 cellspacing=0 align=center>
        <tr>
            <td>
                <strong>
                <bean:message key="ReconciliationCatalog.select"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                <bean:message key="ReconciliationCatalog.alliance"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </strong>

            </td>
            <td>
                <html:select property="allianceId" size="1" onchange="javascript:document.forms[0].submit();" styleClass="selectField">
                    <html:option value="0"> <bean:message key="ManageApplicationForm.label.selectOne"
                                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                    <html:optionsCollection name="Alliances" label="companyName" value="allianceId"/>
                </html:select>
            </td>
            <td>
                <div class="redBtn" title="Go">
                    <div>
                        <div>
                            <div onclick="document.from[0].submit()">Go</div>
                        </div>
                    </div>
                </div>
                <!--<input type=image src="images/go_b.gif" border=0>-->
            </td>
        </tr>
    </table>

<DIV class="headLeftCurveblk"></DIV>
<H1 style="width:470px;"> <bean:message key="ReconciliationCatalog.tableHeading" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/> - <bean:message key="ReconciliationCatalog.selectApplication" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
        <tr>
            <th><strong>
                <bean:message key="ReconciliationCatalog.application" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
            <th><strong>
                <bean:message key="ReconciliationCatalog.version" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
            <th><strong>
                <bean:message key="ReconciliationCatalog.device" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
            <th><strong>
                <bean:message key="ReconciliationCatalog.status" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
            <th><strong>
                <bean:message key="ReconciliationCatalog.select" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
        </tr>
        <logic:present name="Applications" scope="request">
            <logic:iterate id="app" name="Applications">
                <tr>
                    <td ><bean:write name="app" property="title"/></td>
                    <td ><bean:write name="app" property="version"/></td>
                    <td ><bean:write name="app" property="deviceModel"/></td>
                    <td ><bean:write name="app" property="appStatusString"/></td>
                    <td ><input type="radio" name="combineId"
                                            value="<bean:write name="app" property="brewAppsId" />-<bean:write name="ReconcileAppSelectForm" property="catalogDataId" />-<bean:write name="app" property="deviceId" />">
                    </td>
                </tr>
            </logic:iterate>
        </logic:present>
        <logic:notPresent name="Applications" scope="request">
            <tr>
                <td  colspan="5" align="center"><bean:message
                        key="ReconciliationCatalog.noApplication"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
            </tr>
        </logic:notPresent>
    </table>
    <table align="right" style="margin-top:5px;">
        <tr><td>
            <div class="redBtn" title="Go">
                <div>
                    <div>
                        <div onclick="assignValuesToParent();">Submit</div>
                    </div>
                </div>
            </div>
        </td></tr>
    </table>
</DIV>
    <!--<a href="javascript:assignValuesToParent();//"><img src="images/submit_b.gif" border="0"></a>-->
</html:form>
</DIV>