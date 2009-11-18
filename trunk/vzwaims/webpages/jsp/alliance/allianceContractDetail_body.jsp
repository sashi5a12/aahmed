<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<%--
    -- This page has details for accepted/rejected contracts    
--%>

<div id="contentBox" style="float:left">
    <div class="homeColumnTab lBox">

        <aims:getAllianceTab attributeName="Contracts"/>
        <br/>

        <logic:present name="allianceContractInfo">
            <div class="contentbox">
                <table width="100%" cellspacing="0" cellpadding="0" border="0">
                    <%-- start contract information --%>
                    <tr>
                        <td>
                            <DIV class="headLeftCurveblk"></DIV>
                            <H1>Contract Information</H1>
                            <DIV class="headRightCurveblk"></DIV>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
                                <tr>
                                    <th width="50%"><strong>Contract Name:</strong></th>
                                    <th width="50%"><strong>Contract Version:</strong></th>
                                </tr>
                                <tr>
                                    <td  valign="top">
                                        <bean:write name="allianceContractInfo" property="contractTitle" scope="request"/>
                                    </td>
                                    <td  valign="top">
                                        <bean:write name="allianceContractInfo" property="contractVersion" scope="request"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><strong>Status:</strong></td>
                                    <td><strong>Click Through Contract:</strong></td>
                                </tr>
                                <tr>
                                    <td  valign="top">
                                        <bean:write name="allianceContractInfo" property="allianceContractStatus" scope="request"/>
                                    </td>
                                    <td  valign="top">
                                        <logic:notEmpty name="allianceContractInfo" property="clickThroughContract" scope="request">
                                            <logic:equal name="allianceContractInfo" property="clickThroughContract" value="Y" scope="request">
                                                Yes
                                            </logic:equal>
                                            <logic:equal name="allianceContractInfo" property="clickThroughContract" value="N" scope="request">
                                                No
                                            </logic:equal>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                                <tr>
                                    <td ><strong>Accept/Reject By:</strong></td>
                                    <td ><strong>Accept/Reject Date:</strong></td>
                                </tr>
                                <tr>
                                    <td  valign="top">
                                        <bean:write name="allianceContractInfo" property="acceptDeclineFirstName" scope="request"/>&nbsp;<bean:write name="allianceContractInfo" property="acceptDeclineLastName" scope="request"/>
                                    </td>
                                    <td>
                                        <bean:write name="allianceContractInfo" property="allianceContractAcceptDeclineDate" formatKey="date.format" filter="true"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><strong>Sign Name:</strong></td>
                                    <td><strong>Sign Title:</strong></td>
                                </tr>
                                <tr>
                                    <td  valign="top">
                                        <bean:write name="allianceContractInfo" property="acceptDeclineSignName" scope="request"/>
                                    </td>
                                    <td >
                                        <bean:write name="allianceContractInfo" property="acceptDeclineSignTitle" scope="request"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td><strong>Comments:</strong></td>
                                    <td><strong>Contract Document:</strong></td>
                                </tr>
                                <tr>
                                    <td  style="vertical-align:top;">
                                        <textarea class="textareaField" name="allianceContractComments"
                                                       rows="4" cols="50" readonly="true"><bean:write name="allianceContractInfo" property="allianceContractComments" scope="request"/></textarea>

                                    </td>
                                    <td style="vertical-align:top;">
                                        <a class="a" target="_blank"
                                           href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="allianceContractInfo" property="contractId"/>'>
                                            <bean:write name="allianceContractInfo" property="completeDocumentFileName" scope="request"/>
                                        </a>
                                    </td>
                                </tr>

                                <tr>
                                    <td colspan="2" align="right">
                                        <div class="divButtons" style="float:right;">
                                                <div class="blackBtn" title="Back to Contracts" style="float:left;">
                                                    <div>
                                                        <div>
                                                            <div onclick="window.location='<%=request.getContextPath()%>/allianceContracts.do?task=view'">
                                                                Back to Contracts
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                            </table>
                        </td>
                    </tr>
                    <%-- end contract information--%>
                </table>
            </div>
            <%-- end content box --%>
        </logic:present>
    </div>
</div>
