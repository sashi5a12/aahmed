<%@ page language="java" %>

<%@ page
        import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.alliance.*, com.netpace.aims.util.*" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>
<%@ include file="allianceSpotlightInclude.jsp" %>

<script language="JavaScript">

    function truncateLocalTextAreas()
    {
        TruncateTextWithCount(document.forms[0].spotlightDesc, 'textCountSpotlightDesc', 500);
    }

    function trackCountForTextAreas()
    {
        TrackCount(document.forms[0].spotlightDesc, 'textCountSpotlightDesc', 500);
    }

</script>

<%@ include file="/common/error.jsp" %>

<DIV class="homeColumnTab lBox">

<html:form action="/entQrgEdit.do" enctype="multipart/form-data" onsubmit="showProcessingInfoPopup()">
    <html:hidden property="spotlightTypeId" value="<%=AimsConstants.SPOTLIGHT_QRG%>"/>
    <html:hidden property="enterpriseAppsId"/>

    <%@ include file="include/entAppSpotlightsTabs.jsp" %>

    <aims:getEntSpotlightsTab attributeName="<%=AimsConstants.SPOTLIGHT_QRG%>" enterpriseAppsId="<%=enterpriseAppsId%>"/>


    <DIV class="headLeftCurveblk"></DIV>
    <H1>
    <bean:parameter id="taskForThisPage" name="task" value=""/>
    <logic:notEmpty name="taskForThisPage" scope="page">
        <bean:define id="value" name="taskForThisPage" type="java.lang.String" toScope="session"/>
    </logic:notEmpty>

    <logic:equal name="value" value="createForm">
        Create Quick Reference Guide
    </logic:equal>
    <logic:equal name="value" value="editForm">
        Edit Quick Reference Guide
    </logic:equal>
    <logic:match name="value" value="create">
        <html:hidden property="task" value="create"/>
    </logic:match>
    <logic:match name="value" value="edit">
        <html:hidden property="task" value="edit"/>
        <html:hidden property="spotlightId"/>
    </logic:match>
    <html:hidden property="spotlightFileFileName"/>
    </H1>
    <DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellspacing="0" cellpadding="5" class="GridGradient">
        <tr>
            <th width="36%" style="text-align:right;" >
                <strong>Quick Reference Guide Name&nbsp;<span class="requiredText">*</span>:</strong>
            </th>
            <th width="64%" align="left" class="body">
                <html:text styleClass="inputField" property="spotlightName" size="60" maxlength="100"/>
            </th>
        </tr>
        <tr>
            <td  align="right">
                <strong>Quick Reference Guide Description :</strong>
            </td>
            <td  align="left">
                <html:textarea styleClass="textareaField" property="spotlightDesc" rows="4" cols="50"
                               onkeyup="TrackCount(this,'textCountSpotlightDesc',500)"
                               onkeypress="LimitText(this,500)"></html:textarea>
                <br/>
                <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td style="vertical-align:top;padding:0">
                            Characters remaining :
                        </td>
                        <td>
                            <input type="text" name="textCountSpotlightDesc" size="3" value="500" disabled="true"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td  align="right">
                <strong>Quick Reference Guide Document&nbsp;<span class="requiredText">*</span>:</strong>
            </td>
            <td  align="left">
                <html:file property="spotlightFile" styleClass="inputField" size="30"/>
                <br/>
                <a class="a" target="_blank" href='/aims/entAppsSLResourceAction.do?resource=spotlightFile&resourceId=<bean:write name="EntAppsSpotLightForm" property="spotlightId"/>'>
                    <bean:write name="EntAppsSpotLightForm" property="spotlightFileFileName"/>
                </a>
            </td>
        </tr>
    </table>

    <div class="redBtn" id="Create" style="float:right;margin-top:10px;" title="Submit">
        <div>
            <div>
                <div onclick="document.forms[0].submit()">Submit</div>
            </div>
        </div>
    </div>
    </DIV>
</DIV>

</html:form>
<script language="javascript">
    trackCountForTextAreas();
</script>