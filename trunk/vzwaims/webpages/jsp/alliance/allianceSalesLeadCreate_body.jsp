<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<script language="JavaScript">

    function truncateLocalTextAreas()
    {
        TruncateTextWithCount(document.forms[0].salesLeadDesc, 'textCountSalesLeadDesc', 1000);
        TruncateTextWithCount(document.forms[0].companySolution, 'textCountCompanySolution', 1000);
        TruncateTextWithCount(document.forms[0].leadQualification, 'textCountLeadQualification', 1000);
    }

    function trackCountForTextAreas()
    {
        TrackCount(document.forms[0].salesLeadDesc, 'textCountSalesLeadDesc', 1000);
        TrackCount(document.forms[0].companySolution, 'textCountCompanySolution', 1000);
        TrackCount(document.forms[0].leadQualification, 'textCountLeadQualification', 1000);
    }

</script>

<%@ include file="/common/error.jsp" %>

<logic:messagesPresent>
    <html:messages id="message" message="true" header="messages.header" footer="messages.footer">
        <bean:write name="message"/>
        <br/>
    </html:messages>
</logic:messagesPresent>

<div id="contentBox" style="float:left">
<div class="homeColumnTab lBox">

<aims:getAllianceTab attributeName="Submit Sales Lead"/>
<div> &nbsp; </div>

<html:form action="/salesLeadCreate.do?task=create">

<DIV class="headLeftCurveblk"></DIV>
<H1>Sales Lead</H1>
<DIV class="headRightCurveblk"></DIV>

<div class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" border="0" class="GridGradient">
        <tr>
            <th>
                <strong>
                    <bean:message key="SalesLeadForm.platform" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>
                </strong> <br/>
                <logic:iterate id="platform" name="SalesLeadForm" property="allPlatforms">
                    <html:multibox property="platformIds">
                        <bean:write name="platform" property="platformId"/>
                    </html:multibox>
                    <bean:write name="platform" property="platformName"/>
                    <br/>
                </logic:iterate>
            </th>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="SalesLeadForm.estimatedARPU"
                                  bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>
                </strong><br/>
                <html:text property="estimatedARPU" size="10" maxlength="8" styleClass="inputField"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong>
                    <bean:message key="SalesLeadForm.availableApps"
                                  bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>
                </strong>
                <br/>
                <html:select property="appsId" size="10" multiple="true" styleClass="selectField">
                    <html:options collection="appList" property="appsId" labelProperty="title"/>
                </html:select>
            </td>
        </tr>
        <tr>
            <td>
                <strong><bean:message key="SalesLeadForm.salesLeadDesc" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>
                <span class="requiredText">*</span></strong> <br/>
                <html:textarea property="salesLeadDesc" rows="5" cols="40"
                               onkeyup="TrackCount(this,'textCountSalesLeadDesc',1000)"
                               onkeypress="LimitText(this,1000)" styleClass="textareaField"/>
                <br/>
                <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td style="vertical-align:top;padding:0">
                            <bean:message key="message.textarea.char.remaining"/>
                        </td>
                        <td>
                            <input type="text" name="textCountSalesLeadDesc" size="4" value="1000"
                                   disabled="true"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <strong><bean:message key="SalesLeadForm.leadQualification"
                              bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>
                <span class="requiredText">*</strong></span>
            <br/>
            <html:textarea property="leadQualification" rows="5" cols="40" style="text"
                           onkeyup="TrackCount(this,'textCountLeadQualification',1000)"
                           onkeypress="LimitText(this,1000)" styleClass="textareaField"/>
            <br/>
            <table border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td style="vertical-align:top;padding:0">
                        <bean:message key="message.textarea.char.remaining"/>
                    </td>
                    <td>
                        <input type="text" name="textCountLeadQualification" size="4" value="1000" disabled="true"/>
                    </td>
                </tr>
            </table>
            </td>
        </tr>
        <tr>
            <td>
                <strong><bean:message key="SalesLeadForm.estimatedSize" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>
                <span class="requiredText">*</span></strong><br/>
                <html:text property="estimatedSize" size="10" maxlength="8" styleClass="inputField"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong><bean:message key="SalesLeadForm.purschaseDescisionDate"
                              bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>
                <span class="requiredText">*</span></strong><br/>
                <html:text property="purchaseDecision" size="30" styleClass="inputField"/>
                <img name="daysOfMonth2Pos" onclick="toggleDatePicker('daysOfMonth2','SalesLeadForm.purchaseDecision')"
                     id="daysOfMonth2Pos"
                        <bean:message key="images.calendar.button.lite"/>
                        />
                <div style="position:absolute;" id="daysOfMonth2"/>
            </td>
        </tr>
    </table>
</div>

<table width=100% cellpadding="0" cellspacing="0">
    <tr>
        <td valign="middle" align="right">
            <div class="redBtn" style="margin-left:10px;float:right;" id="Save" title="Submit">
                <div>
                    <div>
                        <div onClick="document.forms[0].submit();">Submit</div>
                    </div>
                </div>
            </div>

            <div class="blackBtn" style=" margin-left:10px;float:right;" id="Cancel" title="Cancel">
                <div>
                    <div>
                        <div onclick="window.location='/aims/salesLeadSetup.do?task=view'">Cancel</div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>

</html:form>
</div>
</div>





