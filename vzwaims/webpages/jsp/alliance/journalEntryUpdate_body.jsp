<%@ page language="java" %>

<%@ page import="com.netpace.aims.bo.security.AimsSecurityManager" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<html:form action="/allianceJournalEntryUpdate">
    <bean:define id="alliance_id" type="java.lang.Long" name="AllianceJournalEntryUpdateForm"
                 property="aimsAllianceId"/>

    <%@ include file="/common/error.jsp" %>

    <div id="contentBox" style="float:left">
    <DIV class="homeColumnTab lBox">
    <aims:getVZWAllianceTab attributeName="Journal" allianceId="<%=alliance_id.toString()%>"/>
    <div>&nbsp;</div>

    <div style="padding-bottom:10px">
        <strong>Company Name:
            <bean:write name="AllianceJournalEntryUpdateForm" property="companyName" scope="request"/>
        </strong>
    </div>

    <html:hidden property="aimsAllianceId"/>
    <html:hidden property="task" value="create"/>

    <DIV class="headLeftCurveblk"></DIV>
    <H1>Journal History</H1>

    <DIV class="headRightCurveblk"></DIV>
    <DIV class="contentbox">
        <table width="100%" cellpadding="0" cellspacing="0" class="GridGradient">
            <tr>
                <th>
                    <html:textarea property="journalCombinedText" readonly="true" rows="15" cols="80"
                                   styleClass="textareaField"></html:textarea>
                </th>
            </tr>
        </table>
    </DIV>

    <% if (AimsSecurityManager.checkAccess(request, "ALL_ALLIANCES", AimsSecurityManager.UPDATE)) { %>
    <div>&nbsp;</div>

    <DIV class="headLeftCurveblk"></DIV>
    <H1>Journal Type</H1>

    <DIV class="headRightCurveblk"></DIV>
    <DIV class="contentbox">
        <table width="100%" cellpadding="0" cellspacing="0" class="GridGradient">
            <tr>
                <th>
                    <strong>
                        <bean:message key="JournalForm.type"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    </strong><br/>
                    <html:radio property="journalType" value="PU"/>
                    <bean:message key="ManageApplicationForm.radio.label.public"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    &nbsp;
                    <html:radio property="journalType" value="PR"/>
                    <bean:message key="ManageApplicationForm.radio.label.private"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </th>
            </tr>
            <tr>
                <td class="noPad">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><strong>New Entry</strong></td>
                        </tr>
                        <tr>
                            <td>
                                <html:textarea property="journalText" rows="4" cols="80"
                                               styleClass="textareaField"></html:textarea>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>

                </td>
            </tr>
        </table>
         <div class="redBtn" style="margin-left:10px;float:right; margin-top:3px; padding-right:10px" id="Submit" title="Submit">
            <div>
                <div>
                    <div onClick="document.forms[0].submit()">Submit</div>
                </div>
            </div>
        </div>
    </DIV>

    <%--<input type="image"	name="AllSubmit" <bean:message key="images.submit.button.lite"/> />--%>

    <% } %>
</html:form>
