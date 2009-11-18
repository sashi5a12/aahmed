<%@ page import="com.netpace.aims.util.AimsConstants"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<html:form action="/allianceContracts">

<html:hidden property="task" value="edit"/>
<html:hidden property="selDocType"/>
<html:hidden property="selDocId"/>
<html:hidden property="selContractId"/>
<html:hidden property="selDocStatus"/>
<html:hidden property="selDocInitial"/>
<html:hidden property="selDocTitle"/>


<script language="javascript">
    function submit_click_event(selectedId, statusValue, docTypeValue, totalCount, selContractId)
    {
        document.forms[0].selDocType.value = docTypeValue;
        document.forms[0].selDocId.value = selectedId;
        document.forms[0].selContractId.value = selContractId;
        if(docTypeValue && docTypeValue=='AA') {
            <%-- pass initial, title and status for amendments --%>
            <%-- pass taks value will be 'edit' for amendments --%>
            var tempInitialField = eval("document.forms[0].acceptDeclineInitials" + totalCount);
            var tempTitleField = eval("document.forms[0].acceptDeclineTitles" + totalCount);
            document.forms[0].selDocStatus.value = statusValue;
            document.forms[0].selDocInitial.value = tempInitialField.value;
            document.forms[0].selDocTitle.value = tempTitleField.value;
        }
        else {
            <%-- pass initial, title and status for contracts as null --%>
            <%-- pass taks value as 'viewAcceptRejectContract' to show accept/reject contract page --%>
            document.forms[0].task.value = 'viewAcceptRejectContract';
            document.forms[0].selDocStatus.disabled = true;
            document.forms[0].selDocInitial.disabled = true;
            document.forms[0].selDocTitle.disabled = true;
        }
    }
</script>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<div class="homeColumnTab lBox">
<aims:getAllianceTab attributeName="Contracts"/>

<div style="padding-top:10px;padding-bottom:10px">
    <strong>Company Name:
        <bean:write name="AllianceForm" property="companyName"/>
    </strong>
</div>

<div class="contentbox">
<table width="100%" cellspacing="0" cellpadding="0" border="0">
    <%--
        do not show click through contracts,
        to show click through contracts, remove this commented code 
        <logic:notEmpty name="showClickThroughContracts">
            <logic:equal name="showClickThroughContracts" value="true">
                <tr>
                    <td><%@include file="allianceClickThroughContractsGrid.jsp"%></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
            </logic:equal>
        </logic:notEmpty>
    --%>
    <tr>
        <td>
            <DIV class="headLeftCurveblk"></DIV>
            <H1>Contracts List</H1>
            <DIV class="headRightCurveblk"></DIV>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
                <tr>
                    <th colspan="2" >
                        <strong>Title</strong>
                    </th>
                    <th >
                        <strong>Ver</strong>
                    </th>
                    <th >
                        <strong>Platform</strong>
                    </th>                    
                    <th >
                        <strong>Offer Date</strong>
                    </th>
                    <th >
                        <strong>Expiry Date</strong>
                    </th>
                    <th >
                        <strong>Status</strong>
                    </th>
                    <th width="15%">
                        <strong>Accept/Reject By</strong>
                    </th>
                </tr>
                <% int totalCount = 0; %>
                <logic:iterate id="contract" name="AllianceForm" property="contracts" indexId="cntContract">
                    <% totalCount++; %>
                    <bean:define id="contract_id" type="java.lang.Long" name="contract" property="contractId"/>
                    <tr>
                        <td align="left" colspan="2">
                            <a  onclick="submit_click_event(<%=contract_id%>, '','C',<%=totalCount%>,<%=contract_id%>);document.forms[0].submit()"
                                class="a" style="cursor:pointer;text-decoration:underline;">
                                <bean:write name="contract" property="contractTitle"/>
                            </a>
                        </td>

                        <td align="left">
                            <bean:write name="contract" property="contractVersion"/>
                        </td>
                        <td align="left">
                            <bean:write name="contract" property="contractPlatformName"/>
                        </td>                        
                        <td align="center">
                            <bean:write name="contract" property="contractPresentDate" formatKey="date.format" filter="true"/>
                        </td>
                        <td align="center">
                            <bean:write name="contract" property="contractExpDate" formatKey="date.format" filter="true"/>
                        </td>
                        <td align="center">
                            <bean:write name="contract" property="contractStatus"/>
                        </td>
                        <logic:equal name="contract" property="contractStatus" value="OFFERED">
                            <td align="center">
                                <a  onclick="submit_click_event(<%=contract_id%>, '','C',<%=totalCount%>,<%=contract_id%>);document.forms[0].submit()"
                                    class="a" title="Click here to Accept/Reject Contract" style="cursor:pointer;text-decoration:underline;">
                                    Review Contract
                                </a>
                            </td>
                        </logic:equal>
                        <logic:notEqual name="contract" property="contractStatus" value="OFFERED">
                            <td align="left">
                                <bean:write name="contract" property="acceptDeclineFirstName"/>&nbsp;
                                <bean:write name="contract" property="acceptDeclineLastName"/>
                            </td>
                        </logic:notEqual>
                    </tr>

                    <logic:iterate id="allianceAmendment" name="contract" property="allianceAmendments"
                                   type="com.netpace.aims.controller.contracts.AmendmentForm" indexId="allianceAmendmentCount">
                        <% totalCount++; %>
                        <bean:define id="alliance_amendment_id" type="java.lang.String" name="allianceAmendment" property="amendmentId"/>
                        <tr>
                            <td width="5">&nbsp;</td>
                            <td>
                                <a class="a" target="_blank"
                                   href='/aims/amendmentsResourceAction.do?resource=document&object=Amendtment&resourceId=<bean:write name="allianceAmendment" property="amendmentId"/>'>
                                    <bean:write name="allianceAmendment" property="amendmentTitle"/>
                                </a>
                            </td>
                            <td >
                                <bean:write name="allianceAmendment" property="amendmentVersion"/>
                            </td>                            
                            <td align="center">
                                &nbsp;
                            </td>
                            <td align="center">
                                <bean:write name="allianceAmendment" property="amendmentOfferDate"/>
                            </td>
                            <td align="center">
                                <bean:write name="allianceAmendment" property="amendmentExpiryDate" formatKey="date.format" filter="true"/>
                            </td>
                            <td align="center">
                                <bean:write name="allianceAmendment" property="amendmentStatus"/>
                            </td>
                            <td align="left">
                                <logic:equal name="allianceAmendment" property="amendmentStatus" value="Offered">
                                    <table width="100" cellpadding="0" cellspacing="0" border="0">
                                        <tr>
                                            <td align="left">
                                                <strong>Initials:</strong>&nbsp;
                                            </td>
                                            <td><input type="text" name="acceptDeclineInitials<%=totalCount%>" size="15" value="" class="inputField"></td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <strong>Title:</strong>&nbsp;
                                            </td>
                                            <td><input type="text" name="acceptDeclineTitles<%=totalCount%>" size="15" value="" class="inputField"></td>
                                        </tr>
                                        <tr>
                                            <td width="100%" colspan="2">
                                                <div class="divButtons" style="float:right;">
                                                    <div class="redSmallBtn" title="Accept" style="float:left;">
                                                        <div>
                                                            <div>
                                                                <div onClick="submit_click_event(<%=alliance_amendment_id%>, 'A','AA',<%=totalCount%>,<%=contract_id%>);document.forms[0].submit();">
                                                                    Accept
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="blackSmallBtn" title="Reject" style="float:left;">
                                                        <div>
                                                            <div>
                                                                <div onClick="submit_click_event(<%=alliance_amendment_id%>, 'R','AA',<%=totalCount%>,<%=contract_id%>);document.forms[0].submit();">
                                                                    Reject
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </logic:equal>
                                <logic:notEqual name="allianceAmendment" property="amendmentStatus" value="Offered">
                                    <bean:write name="allianceAmendment" property="acceptDeclineFirstName"/>&nbsp;
                                    <bean:write name="allianceAmendment" property="acceptDeclineLastName"/>
                            </logic:notEqual>
                            </td>
                        </tr>
                    </logic:iterate>
                </logic:iterate>
            </table>
        </td>
    </tr>
</table>
</div>
</html:form>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</div>
