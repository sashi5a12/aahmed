<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>


<%@ include file="/common/error.jsp" %>

<html:form action="/contractOfferEdit.do" enctype="multipart/form-data">
<bean:define id="alliance_id" name="ContractOfferForm" property="allianceId"/>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<aims:getVZWAllianceTab attributeName="Contracts" allianceId="<%=alliance_id.toString()%>"/>

<div>&nbsp;</div>

<html:hidden property="contractDocumentFileName"/>
<logic:equal parameter="task" value="saveCreate">
    <html:hidden property="task" value="create"/>
</logic:equal>
<logic:equal parameter="task" value="saveEdit">
    <html:hidden property="task" value="edit"/>
</logic:equal>

<html:hidden property="contractId"/>
<html:hidden property="allianceId"/>
<input type="hidden" name="alliance_id" value="<%=alliance_id.toString()%>"/>

<DIV class="headLeftCurveblk"></DIV>
<H1>Contract Details</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th  width="50%"><strong>Contract Name:</strong></th>
        <th  width="50%"><strong>Contract Version:</strong></th>
    </tr>
    <tr>
        <td  valign="top">
            <bean:write name="ContractOfferForm" property="contractTitle"/>
        </td>
        <td >
            <bean:write name="ContractOfferForm" property="contractVersion"/>
        </td>
    </tr>
    <tr>
        <td ><strong>Contract Status:</strong></td>
        <td ><strong>Contract Document:</strong></td>
    </tr>
    <tr>
        <td  valign="top">
            <bean:write name="ContractOfferForm" property="contractStatus"/>
        </td>
        <td>
            <a class="standardLink" target="_blank"
               href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="ContractOfferForm" property="contractId"/>'>
                <bean:write name="ContractOfferForm" property="contractDocumentFileName"/>
            </a>
        </td>
    </tr>
    <tr>
        <td ><strong>Platform:</strong></td>
        <td ><strong>Contract Document (HTML):</strong></td>
    </tr>
    <tr>
        <td  valign="top">
            <bean:write name="ContractOfferForm" property="platformName"/>
        </td>
        <td  valign="top">
            <a class="standardLink" target="_blank"
               href='/aims/resourceContractAction.do?resource=htmlFile&object=AimsAllianc&resourceId=<bean:write name="ContractOfferForm" property="contractId"/>'>
                <bean:write name="ContractOfferForm" property="contractHtmlDocumentFileName"/>
            </a>
        </td>
    </tr>
    <tr>
        <td ><strong>Auto-Offered Click Through Contract:</strong></td>
        <td ><strong>Contract Expiry Date:</strong></td>
    </tr>
    <tr>
        <td  valign="top">
            <logic:notEmpty name="ContractOfferForm" property="clickThroughContract">
                <logic:equal name="ContractOfferForm" property="clickThroughContract" value="Y">
                    Yes
                </logic:equal>
                <logic:equal name="ContractOfferForm" property="clickThroughContract" value="N">
                    No
                </logic:equal>
            </logic:notEmpty>
        </td>
        <td  valign="top">
            <bean:write name="ContractOfferForm" property="contractExpiryDate" formatKey="date.format" filter="true"/>
        </td>
    </tr>
    <tr>
        <td ><strong>Comments:</strong></td>
        <td >&nbsp;</td>
    </tr>
    <tr>
        <td >
            <bean:write name="ContractOfferForm" property="comments"/>
        </td>
        <td >
            &nbsp;
        </td>
    </tr>
</table>

</div>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Contract Level Amendment Information</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th><strong>Amendment Name:</strong></th>
        <th><strong>Amendment Version:</strong></th>
        <th><strong>Amendment Document:</strong></th>
        <th><strong>Amendment Expirty Date:</strong></th>
    </tr>
    <logic:iterate id="amendment" name="ContractOfferForm" property="contractAmendments"
                   type="com.netpace.aims.controller.contracts.AmendmentForm">
        <tr>
            <td >
                <bean:write name="amendment" property="amendmentTitle"/>
            </td>
            <td >
                <bean:write name="amendment" property="amendmentVersion"/>
            </td>
            <td >
                <a class="standardLink" target="_blank"
                   href='/aims/amendmentsResourceAction.do?resource=document&object=Amendtment&resourceId=<bean:write name="amendment" property="amendmentId"/>'>
                    <bean:write name="amendment" property="amendmentDocumentFileName"/>
                </a>
            </td>
            <td >
                <bean:write name="amendment" property="amendmentExpiryDate" formatKey="date.format" filter="true"/>
            </td>
        </tr>
    </logic:iterate>
</table>
</div>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Alliance Level Amendment Information</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
    <tr>
        <th ><strong>Amendment Name:</strong></th>
        <th ><strong>Amendment Version:</strong></th>
        <th ><strong>Amendment Document:</strong></th>
        <th ><strong>Amendment Expirty Date:</strong></th>
        <th ><strong>Add</strong></th>
    </tr>
    <logic:iterate id="amendment" name="ContractOfferForm" property="allianceAvailableAmendments"
                   type="com.netpace.aims.controller.contracts.AmendmentForm">
        <tr>
            <td >
                <bean:write name="amendment" property="amendmentTitle"/>
            </td>
            <td >
                <bean:write name="amendment" property="amendmentVersion"/>
            </td>
            <td >
                <a class="standardLink" target="_blank"
                   href='/aims/amendmentsResourceAction.do?resource=document&object=Amendtment&resourceId=<bean:write name="amendment" property="amendmentId"/>'>
                    <bean:write name="amendment" property="amendmentDocumentFileName" filter="false"/>
                </a>
            </td>
            <td >
                <bean:write name="amendment" property="amendmentExpiryDate" formatKey="date.format" filter="true"/>
            </td>
            <td >
                <html:multibox property="allianceContractAmendmentIds">
                    <bean:write name="amendment" property="amendmentId"/>
                </html:multibox>
            </td>
        </tr>
    </logic:iterate>
</table>
</div>

<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Add New Amendment</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th width="50%"><strong>Amendment Name:</strong></th>
        <th width="50%"><strong>Amendment Version:</strong></th>
    </tr>
    <tr>
        <td  valign="top">
            <html:text property="amendmentTitle" size="35" maxlength="200" styleClass="inputField"/>
        </td>
        <td >
            <html:text property="amendmentVersion" size="35" maxlength="200" styleClass="inputField"/>
        </td>
    </tr>
    <tr>
        <td><strong>Amendment Status:</strong></td>
        <td><strong>Amendment Document:</strong></td>
    </tr>
    <tr>
        <td valign="top">
            <html:select property="amendmentStatus" size="1" styleClass="selectField">
                <html:option value="A">Active</html:option>
                <html:option value="E">Expired</html:option>
                <html:option value="H">On Hold</html:option>
            </html:select>
        </td>
        <td>
            <html:file property="amendmentDocument" styleClass="inputField"/>
        </td>
    </tr>
    <tr>
        <td><strong>Comments</strong></td>
        <td><strong>Amendment Expiry Date:</strong></td>
    </tr>
    <tr>
        <td>
            <html:textarea property="amendmentComments" rows="5" cols="30" styleClass="textareaField"/>
        </td>
        <td valign="top">
            <html:text property="amendmentExpiryDate" size="35" maxlength="200" styleClass="inputField"/>
            <img name="daysOfMonthPos" onclick="toggleDatePicker('daysOfMonth','ContractOfferForm.amendmentExpiryDate')"
                 id="daysOfMonthPos" <bean:message key="images.calendar.button.lite"/> />
            <div style="position:absolute;" id="daysOfMonth"/>
        </td>
    </tr>
</table>

<table width="100%">
    <tr>
        <td align="right">
            <!--<input type="image" src="images/submit_b.gif" border="0">-->
            <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Submit" title="Submit">
                <div>
                    <div><div onclick="document.forms[0].submit();">Submit</div></div>
                </div>
            </div>
        </td>
    </tr>
</table>
</div>
</div>
</div>
</html:form>