<%! %><%@ page language="java" %>

<%@ page
        import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.alliance.* " %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<%@ include file="/common/error.jsp" %>

<html:form action="/contractOfferEdit">
    <bean:parameter id="alliance_id" name="alliance_id" value="0"/>
    <html:hidden property="allianceId"/>
    <html:hidden property="task" value="saveCreate"/>
    <html:hidden property="alliance_id" value="<%=alliance_id%>"/>


    <DIV id="contentBox" style="float:left">
    <DIV class="homeColumnTab lBox">

        <aims:getVZWAllianceTab attributeName="Contracts" allianceId="<%=alliance_id.toString()%>"/>

            <div>&nbsp;</div>


    <DIV class="headLeftCurveblk"></DIV>
    <H1>Alliance Available Contracts List</H1>
    <DIV class="headRightCurveblk"></DIV>

    <DIV class="contentbox">
    <table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
        <tr>
            <th align="center">
                <span>Title</span>
            </th>
            <th align="center">
                <span>Version</span>
            </th>
            <th align="center">
                <span>Document</span>
            </th>
            <th align="center">
                <span>Status</span>
            </th>
            <th align="center">
                <span>Expiry Date</span>
            </th>
            <th align="center">
                <span>No. of <br/>Amendments </span>
            </th>
            <th align="center">
                <span>Select</span>
            </th>
        </tr>
        <logic:iterate id="contract" name="AvailableAllianceContracts"
                       type="com.netpace.aims.controller.contracts.ContractOfferForm">
            <tr>
                <td align="left">
                    <a href='/aims/contractsSetup.do?task=editViewForm&contract_id=<bean:write name="contract" property="contractId" />'
                       class="a">
                        <bean:write name="contract" property="contractTitle"/>
                    </a>
                </td>
                <td align="left">
                    <bean:write name="contract" property="contractVersion"/>
                </td>
                <td align="left">
                    <a class="standardLink" target="_blank"
                       href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="contract" property="contractId"/>'>
                        <bean:write name="contract" property="contractDocumentFileName" filter="false"/>
                    </a> &nbsp;
                </td>
                <td align="left">
                    <bean:write name="contract" property="contractStatus"/>
                </td>
                <td align="left">
                    <bean:write name="contract" property="contractExpiryDate" formatKey="date.format" filter="true"/>
                    &nbsp;
                </td>
                <td align="center">
                    <bean:write name="contract" property="numContractAmendments"/>
                </td>
                <td align="center">
                    <%--<a href='/aims/contractSetup.do?task=editForm&contractId=<bean:write name="contract" property="contractId"/>'
                       class="a">--%>
                        <html:radio name="contract" property="selContractId" value="<%=contract.getContractId().toString()%>"/>
                    <!--</a>-->
                </td>
            </tr>
        </logic:iterate>
    </table>

    <div>&nbsp;</div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="100%" align="right">
                <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Offer" title="Offer">
                    <div>
                        <div>
                            <div onClick="document.forms[0].submit()">Offer</div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </table>
    </DIV>

</html:form>