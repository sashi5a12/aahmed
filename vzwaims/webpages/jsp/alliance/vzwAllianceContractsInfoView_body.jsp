<%@ page language="java" %>

<%@ page import="com.netpace.aims.bo.security.AimsSecurityManager " %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<html:form action="/contractOffer">

<jsp:useBean id="alliance_id" class="java.lang.Long" scope="request"/>

<%@ include file="/common/error.jsp" %>

<DIV id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

    <aims:getVZWAllianceTab attributeName="Contracts" allianceId="<%=alliance_id.toString()%>"/>

    <DIV>&nbsp;</DIV>

    <div style="padding-bottom:10px">
        <strong>Company Name: <bean:write name="alliance_form" property="companyName" scope="request"/></strong>
     </div>

<DIV class="headLeftCurveblk"></DIV>
<H1> Alliance Contracts List </H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
<table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
<tr>
    <th colspan="2">
        <strong>Title</strong>
    </th>
    <th>
        <strong>Version</strong>
    </th>
    <th>
        <strong>Platform</strong>
    </th>
    <th>
        <strong>Offer Date</strong>
    </th>
    <th>
        <strong>Expiry Date</strong>
    </th>
    <th>
        <strong>Status</strong>
    </th>
    <th>
        <strong>Document</strong>
    </th>
    <%
        if (AimsSecurityManager.checkAccess(request, "MANAGE_CONTRACTS", AimsSecurityManager.UPDATE)) {
    %>
    <th>
        <strong>Edit</strong>
    </th>
    <%
        }
        if (AimsSecurityManager.checkAccess(request, "MANAGE_CONTRACTS", AimsSecurityManager.DELETE)) {
    %>
    <th>
        <strong>Delete</strong>
    </th>
    <%
        }
    %>
</tr>
<logic:iterate id="contract" name="AimsAllianceContracts" scope="request"
               type="com.netpace.aims.controller.alliance.AllianceContractForm" indexId="contractCount">
    <tr>
        <td colspan="2">
            <%--<a href='/aims/contractsSetup.do?task=allianceViewForm&contract_id=<bean:write name="contract" property="contractId"/>&alliance_id=<%=alliance_id%>' class="a">--%>
            <bean:write name="contract" property="contractTitle"/>
            <%--</a>--%>
        </td>
        <td >
            <bean:write name="contract" property="contractVersion"/>
        </td>
        <td >
            <bean:write name="contract" property="contractPlatformName"/>
        </td>
        <td >
            <bean:write name="contract" property="contractPresentDate" formatKey="date.format" filter="true"/>
            &nbsp;
        </td>
        <td >
            <bean:write name="contract" property="contractExpDate" formatKey="date.format" filter="true"/>
            &nbsp;
        </td>
        <td >
            <bean:write name="contract" property="contractStatus"/>
            &nbsp;
        </td>
        <td >
            <a class="a" target="_blank"
               href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="contract" property="contractId"/>'>
                <bean:write name="contract" property="documentFileName" filter="false"/>
            </a> &nbsp;
        </td>
        <%
            if (AimsSecurityManager.checkAccess(request, "MANAGE_CONTRACTS", AimsSecurityManager.UPDATE)) {
        %>
        <td align="center" valign="middle">
            <a href='/aims/contractOfferEdit.do?task=saveEdit&contract_id=<bean:write name="contract" property="contractId"/>&alliance_id=<%=alliance_id%>'
               class="a">
                <img src="images/icon-edit.gif" alt="Edit" width="18" height="13" border="0"/>
            </a>
        </td>
        <%
            }
            if (AimsSecurityManager.checkAccess(request, "MANAGE_CONTRACTS", AimsSecurityManager.DELETE)) {
        %>
        <td align="center" valign="middle">
            <a href='/aims/contractOfferEdit.do?task=delete&contract_id=<bean:write name="contract" property="contractId"/>&alliance_id=<%=alliance_id%>' class="a"
            	onClick="javascript:if (window.confirm('Are you sure you want to delete this Contract?')) { return true;} else { return false;}">
                <img src="images/icon-delete.gif" alt="Delete" width="15" height="14" border="0"/>
            </a>
        </td>
        <%
            }
        %>
    </tr>


    <logic:iterate id="allianceAmendment" name="contract" property="allianceAmendments"
                   type="com.netpace.aims.controller.contracts.AmendmentForm" indexId="allianceAmendmentCount">
        <tr>
            <td width="5">&nbsp;
            </td>
            <td>
                <!-- <a href='/aims/amendmentsSetup.do?task=editViewForm&amendment_id=<bean:write name="allianceAmendment" property="amendmentId"/>' class="a"> -->
                <bean:write name="allianceAmendment" property="amendmentTitle"/>
                <!-- </a> -->
            </td>
            <td >
                <bean:write name="allianceAmendment" property="amendmentVersion"/>
            </td>            
            <td >
                &nbsp;
            </td>
            <td >
                <bean:write name="allianceAmendment" property="amendmentOfferDate"/>
            </td>
            <td >
                <bean:write name="allianceAmendment" property="amendmentExpiryDate" formatKey="date.format"
                            filter="true"/>
                &nbsp;
            </td>
            <td >
                <bean:write name="allianceAmendment" property="amendmentStatus"/>
                &nbsp;
            </td>
            <td >
                <a class="a" target="_blank"
                   href='/aims/amendmentsResourceAction.do?resource=document&object=Amendtment&resourceId=<bean:write name="allianceAmendment" property="amendmentId"/>'>
                    <bean:write name="allianceAmendment" property="amendmentDocumentFileName" filter="false"/>
                </a> &nbsp;
            </td>
            <td>
                &nbsp;
            </td>
            <td>
                &nbsp;
            </td>
        </tr>
    </logic:iterate>
</logic:iterate>
</table>
</DIV>

<% if (AimsSecurityManager.checkAccess(request, "OFFER_CONTRACT", AimsSecurityManager.INSERT)) { %>
<DIV>&nbsp;</DIV>
<logic:equal name="alliance_form" property="status" value="A">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="100%" align="right">
                <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Offer" title="Offer">
                    <div>
                        <div>
                            <div onClick="window.location='/aims/contractOffer.do?task=view&alliance_id=<%=alliance_id%>'">Offer</div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</logic:equal>
<% } %>
</html:form>

</DIV>
</DIV>