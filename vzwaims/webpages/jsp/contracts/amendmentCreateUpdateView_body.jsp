<%@ page language="java" %>

<%@ page import="com.netpace.aims.controller.contracts.* " %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<html:form action="/amendmentsEdit">
    <DIV class="headLeftCurveblk"></DIV>
    <H1>Amendment Details</H1>
    <DIV class="headRightCurveblk"></DIV>
    <DIV class="contentbox">
        <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th width="50%"><strong>Amendment Name&nbsp;:</strong></th>
            <th width="50%"><strong>Amendment Version&nbsp;:</strong></th>
        </tr>
        <tr>
            <td valign="top">
                <bean:write name="AmendmentForm" property="amendmentTitle"/>
            </td>
            <td>
                <bean:write name="AmendmentForm" property="amendmentVersion"/>
            </td>
        </tr>
        <tr>
            <td><strong>Amendment Status&nbsp;:</strong></td>
            <td><strong>Amendment Document&nbsp;:</strong></td>
        </tr>
        <tr>
            <td valign="top">
                <bean:write name="AmendmentForm" property="amendmentStatus"/>
            </td>
            <td>
                <a class="a" target="_blank"
                   href='/aims/amendmentsResourceAction.do?resource=document&object=Amendtment&resourceId=<bean:write name="AmendmentForm" property="amendmentId"/>'>
                    <bean:write name="AmendmentForm" property="amendmentDocumentFileName"/>
                </a>
            </td>
        </tr>
        <tr>
            <td><strong>Amendment Expiry Date&nbsp;:</strong></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td valign="top">
                <bean:write name="AmendmentForm" property="amendmentExpiryDate"/>
            </td>
            <td>
                &nbsp;
            </td>
        </tr>
        <tr>
            <td><strong>Comments</strong></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>
                <bean:write name="AmendmentForm" property="comments"/>
            </td>
            <td valign="top">
                &nbsp;
            </td>
        </tr>
    </table>
    </div>

    <div>&nbsp;</div>
    
    <DIV class="headLeftCurveblk"></DIV>
    <H1>Contract(s) associated with the amendment</H1>
    <DIV class="headRightCurveblk"></DIV>
    <DIV class="contentbox">
        <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
        <tr>
            <th>Contract Title</th>
            <th>Contract Version</th>
            <th>Contract Status</th>
            <th>Contract Document</th>
        </tr>
        <logic:iterate id="contract" name="AmendmentForm" property="amendmentContracts"
                       type="com.netpace.aims.controller.contracts.ContractForm">
            <tr>
                <td valign="top">
                    <a href='/aims/contractsSetup.do?task=editViewForm&contract_id=<bean:write name="contract" property="contractId"/>'
                       class="a">
                        <bean:write name="contract" property="contractTitle"/>
                    </a>
                </td>
                <td>
                    <bean:write name="contract" property="contractVersion"/>
                </td>
                <td valign="top">
                    <bean:write name="contract" property="contractStatus"/>
                </td>
                <td>
                    <a class="a" target="_blank"
                       href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="contract" property="contractId"/>'>
                        <bean:write name="contract" property="contractDocumentFileName"/>
                    </a> &nbsp;
                </td>
            </tr>
        </logic:iterate>
    </table>
    </DIV>
</html:form>