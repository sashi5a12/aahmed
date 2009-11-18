<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<%@ include file="/common/error.jsp" %>


<DIV class="homeColumn lBox floatL">
        <DIV class="headLeftCurveblk"></DIV>
        <H1>Search Criterion</H1>
        <DIV class="headRightCurveblk"></DIV>

        <DIV class="contentbox">

<html:form action="/vzwAllianceSearch">
<html:hidden property="task" value="search"/>

<table width="100%" border="0" cellspacing="5" cellpadding="0">

<tr>
    <td >
        <strong>Alliance Name</strong> <br/>
        <html:text styleClass="inputField" property="companyName" maxlength="200" size="40" />
    </td>
    <td >
        &nbsp;
    </td>
</tr>
<tr>
    <td >
        <strong>All Alliances</strong>
    </td>
    <td >
        <strong>My Alliances</strong>
    </td>
</tr>
<tr>
    <td >
        <html:radio property="allianceType" value="ALL"/>
    </td>
    <td >
        <html:radio property="allianceType" value="MY"/>
    </td>
</tr>
<tr>
    <td colspan="2" >
        <strong>Contract</strong>
    </td>
</tr>
<tr>
    <td colspan="2" >
        <html:select property="contractId" size="1" styleClass="selectField">
            <html:option value="0">Select One</html:option>
            <html:optionsCollection property="contracts" label="contractTitle" value="contractId"/>
        </html:select>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td colspan="2" >
        <strong>Alliance Administrator</strong>
    </td>
</tr>
<tr>
    <td >
        First Name <br/>
        <html:text styleClass="inputField" property="allianceAdminFirstName" maxlength="100" size="40"/>
    </td>
    <td >
        Last Name <br/>
        <html:text styleClass="inputField" property="allianceAdminLastName" maxlength="100" size="40"/>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td colspan="2" >
        <strong>Account Manager</strong>
    </td>
</tr>
<tr>
    <td >
        First Name <br/>
        <html:text styleClass="inputField" property="vzwAccMgrFirstName" maxlength="100" size="40"/>
    </td>
    <td >
        Last Name  <br/>
        <html:text styleClass="inputField" property="vzwAccMgrLastName" maxlength="100" size="40"/>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td colspan="2" >
        <strong>Created Date</strong>
    </td>
</tr>
<tr>
    <td >
        From <br/>
        <html:text property="dateEstablishedFrom" maxlength="20" size="40" styleClass="inputField"/>
        <img name="daysOfMonthPos" onclick="toggleDatePicker('daysOfMonth','VZWAllianceForm.dateEstablishedFrom')"
             id="daysOfMonthPos" <bean:message key="images.calendar.button.lite"/> />

        <div style="position:absolute;" id="daysOfMonth"/>
    </td>
    <td>
        To <br/>
        <html:text property="dateEstablishedTo" maxlength="20" size="40" styleClass="inputField"/>
        <img name="daysOfMonth2Pos" onclick="toggleDatePicker('daysOfMonth2','VZWAllianceForm.dateEstablishedTo')"
             id="daysOfMonth2Pos" <bean:message key="images.calendar.button.lite"/> />
        <div style="position:absolute;" id="daysOfMonth2"/>
    </td>
</tr>
<tr>
    <td colspan="2" align="right" valign="middle">
        <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Submit" title="Submit">
            <div>
                <div><div onclick="document.forms[0].submit();">Submit</div></div>
            </div>
        </div>

        <div class="blackBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Cancel" title="Cancel">
            <div>
                <div><div onclick="window.location='/aims/vzwAllianceSearchSetUp.do?task=searchView';">Cancel</div></div>
            </div>
        </div>
    </td>
</tr>
</html:form>
</table>

</div>
</div>       


