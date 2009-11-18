<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<%--
    -- This page has alliance contract accept/reject sections for both click through and non click through contracts
    -- alliance contract details for accepted/rejected contracts are moved to allianceContractDetail.jsp 
--%>

<html:form action="/allianceContractAcceptReject">

    <html:hidden property="task" value="edit"/>
    <html:hidden property="selDocType"/>
    <html:hidden property="selDocId"/>
    <html:hidden property="selDocStatus"/>
    <html:hidden property="selContractId"/>
    <html:hidden property="allianceId"/>
    <html:hidden property="contractRequestedPage"/>

    <%
        boolean isClickThroughContract = false;
        String acceptRejectText = "Accept/Reject";
    %>


    <logic:notEmpty name="allianceContractInfo" property="clickThroughContract" scope="request">
        <logic:equal name="allianceContractInfo" property="clickThroughContract" value="Y"  scope="request">
            <%
                isClickThroughContract = true;
                //for click through contract, acceptRejectText is 'Accept', because user can not reject click through contract
                acceptRejectText = "Accept";
            %>
        </logic:equal>
    </logic:notEmpty>


    <logic:present name="allianceContractInfo">
        <div style="width:810px;">
            <table width="100%" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td>
                        <%@ include file="/common/error.jsp" %>
                    </td>
                </tr>

                <%-- if error messages are present, add blank row--%>
                <logic:messagesPresent>
                    <tr><td>&nbsp;</td></tr>
                </logic:messagesPresent>

                <tr>
                    <td>
                        <strong>Please read this contract and scroll down to <%=acceptRejectText%>.</strong>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td><div class="btnTopLine" style="padding-top:10px;padding-bottom:10px;"></div></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>

                <tr>
                    <td>
                        <DIV style="color:#000000;font-family:arial;font-size:1.8em;font-weight:bold;"><bean:write name="allianceContractInfo" property="contractTitle" scope="request"/>&nbsp; (Ver: <bean:write name="allianceContractInfo" property="contractVersion" scope="request"/>)</DIV>
                    </td>
                </tr>
                <tr>
                    <td width="100%" style="padding:10px 0px 10px 0px;">
                        <%--<div style="border: 1px solid #CCCCCC; border-collapse:collapse; padding:10px 20px 10px 20px;"></div>--%>
                        <!-- start contract html -->
                        <bean:write name="AllianceContractAcceptRejectForm" property="contractHTML" scope="request" filter="false"/>
                        <!-- end contract html -->
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>

                <%-- start accept reject section --%>

                <%-- start contract document section --%>
                <tr>
                    <td>
                        <DIV class="headLeftCurveblk"></DIV>
                        <DIV class="noLayout_h1_table_header">Contract Document</DIV>
                        <DIV class="headRightCurveblk"></DIV>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" cellspacing="0" cellpadding="0" class="noLayout_GridGradient">
                            <tr>
                                <td style="padding:5px;">
                                    <a class="a" target="_blank"
                                       href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="allianceContractInfo" property="contractId"/>'>
                                        <bean:write name="allianceContractInfo" property="completeDocumentFileName" scope="request"/>
                                    </a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <%-- end contract document section--%>

                <tr>
                    <td>
                        <DIV class="headLeftCurveblk"></DIV>
                        <DIV class="noLayout_h1_table_header"><%=acceptRejectText%></DIV>
                        <DIV class="headRightCurveblk"></DIV>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" cellspacing="0" cellpadding="5" class="noLayout_GridGradient" border="0">
                            <tr>
                                <th width="5%" nowrap>
                                    <strong>Initials&nbsp;<span class="noLayout_requiredText">*</span>:</strong>
                                </th>
                                <th style="vertical-align:top;" width="95%"><html:text property="selDocInitial" size="15" styleClass="noLayout_inputField" maxlength="50"/></th>
                            </tr>
                            <tr>
                                <td nowrap><strong>Title&nbsp;<span class="noLayout_requiredText">*</span>:</strong></td>
                                <td style="vertical-align:top;"><html:text property="selDocTitle" size="15" styleClass="noLayout_inputField" maxlength="50"/></td>
                            </tr>

                            <tr>
                                <td style="vertical-align:top;"><strong>Comments</strong>:</td>
                                <td>
                                    <html:textarea styleClass="textareaField" property="acceptRejectComments" rows="4"   cols="50"
                                                   onkeyup="TrackCountToMaxChars(this,'textCountAcceptRejectComments',200);TruncateTextToMaxChars(this, 200);"
                                                   onkeypress="TruncateTextToMaxChars(this,200);TruncateTextToMaxChars(this,200);"></html:textarea>
                                    <br/><bean:message key="ManageApplicationForm.textarea.char.remaining"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                    <input type="text" name="textCountAcceptRejectComments" size="3" value="200" disabled="true" />
                                </td>
                            </tr>

                            <tr>
                                <td colspan="2" align="right">
                                    <div class="divButtons" style="float:right;">
                                        <%  if(isClickThroughContract)  {   %>
                                            <div class="blackBtn" title="Cancel" style="float:left;">
                                                <div>
                                                    <div>
                                                        <div onclick="document.forms[0].task.value='cancel';document.forms[0].submit();">
                                                            Cancel
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        <%
                                            }//end isClickThroughContract
                                            else {
                                       %>
                                            <div class="blackBtn" title="Reject" style="float:left;">
                                                <div>
                                                    <div>
                                                        <div onclick="document.forms[0].selDocStatus.value='R';document.forms[0].submit();">
                                                            Reject
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                       <%   }//end else   %>
                                            <div class="redBtn" title="Accept" style="float:left;">
                                                <div>
                                                    <div>
                                                        <div onclick="document.forms[0].selDocStatus.value='A';document.forms[0].submit();">
                                                            Accept
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
                <%-- end accept reject section --%>
            </table>
        </div>
        <%-- end content box --%>

        <script	language="javascript">
            function TruncateTextToMaxChars(fieldObj, maxChars) {
                if ((maxChars - fieldObj.value.length) < 0) {
                    fieldObj.value = fieldObj.value.substring(0, maxChars);
                }
            }

            //Start of functions to limit text in TextArea
            function TrackCountToMaxChars(fieldObj, countFieldName, maxChars) {
                var countField = eval("fieldObj.form." + countFieldName);
                var diff = maxChars - fieldObj.value.length;

                // Need to check & enforce limit here also in case user pastes data
                if (diff < 0) {
                    fieldObj.value = fieldObj.value.substring(0, maxChars );
                    diff = maxChars - fieldObj.value.length;
                }
                countField.value = diff;
            }
            function truncateLocalTextAreas() {
                if (typeof document.forms[0].acceptRejectComments != "undefined") {
                    if (typeof document.forms[0].acceptRejectComments.value != "undefined") {
                        TruncateTextToMaxChars(document.forms[0].acceptRejectComments, 200);
                        TrackCountToMaxChars(document.forms[0].acceptRejectComments,'textCountAcceptRejectComments',200);

                    }
                }
            }//end truncateLocalTextAreas
            truncateLocalTextAreas();
        </script>
    </logic:present>
</html:form>