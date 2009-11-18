<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
    <div class="homeColumnTab lBox">
        <div class="headLeftCurveblk"></DIV>
        <H1>Contact Information <span class="requiredText">*</span></H1>
        <div class="headRightCurveblk"></DIV>

        <html:form action="/contactEdit">
            <div class="contentbox">
                <input type="hidden" name="alliance_id" value="<c:out value='${requestScope.alliance_id}'/>"/>
                <input type="hidden" name="isPopup" value="<c:out value='${requestScope.isPopup}'/>"/>
                <input type="hidden" name="cType" value="<c:out value='${requestScope.cType}'/>"/>
                <input type="hidden" name="parentPageType" value="<c:out value='${requestScope.parentPageType}'/>"/>
                <input type="hidden" name="parentPath" value="<c:out value='${requestScope.parentPath}'/>"/>

                <html:hidden property="task"/>
                <html:hidden property="contactId"/>

                <table width="100%" border="0" cellpadding="5" cellspacing="0" class="GridGradient">
                    <tr>
                        <th width="50%">
                            <strong><bean:message key="ContactForm.email" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
                            <logic:match name="ContactForm" property="task" value="create" scope="request">
                                <html:text property="contactEmailAddress" maxlength="50" size="40" styleClass="inputField"/>
                            </logic:match>
                            <logic:match name="ContactForm" property="task" value="edit" scope="request">
                                <html:hidden property="contactEmailAddress"/>
                                <html:text disabled="true" property="contactEmailAddress" maxlength="50" size="40" styleClass="inputField"/>
                            </logic:match>
                        </th>
                        <th width="50%">&nbsp;</th>
                    </tr>

                    <tr>
                        <td>
                            <strong><bean:message key="ContactForm.firstName" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
                            <html:text property="contactFirstName" maxlength="50" size="40" styleClass="inputField"/>
                        </td>
                        <td>
                            <strong><bean:message key="ContactForm.lastName" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
                            <html:text property="contactLastName" maxlength="50" size="40" styleClass="inputField"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <strong><bean:message key="ContactForm.title" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
                            <html:text property="contactTitle" maxlength="50" size="40" styleClass="inputField"/>
                        </td>
                        <td>
                            <strong><bean:message key="ContactForm.phone" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>:</strong> <br/>
                            <html:text property="contactPhone" maxlength="50" size="40" styleClass="inputField"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <strong><bean:message key="ContactForm.mobile" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>:</strong> <br/>
                            <html:text property="contactMobile" maxlength="50" size="40" styleClass="inputField"/>
                        </td>
                        <td>
                            <strong><bean:message key="ContactForm.fax" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>:</strong> <br/>
                            <html:text property="contactFax" maxlength="50" size="40" styleClass="inputField"/>
                        </td>
                    </tr>
                    <tr>
						<td colspan="2" align="right">
                            <div class="divButtons" style="float:right;">
                                <c:if test='${requestScope.isPopup != "true"}'>
                                    <div class="blackBtn" id="btnCancel" title="Cancel" style="float:left;">
                                        <div><div><div onclick="window.location='/aims/contacts.do?task=viewList'">Cancel</div></div></div>
                                    </div>
                                </c:if>
                                <div class="redBtn" style="float: left;" id="btnSubmit" title="Submit">
                                    <div><div><div onclick="document.forms[0].submit();">Submit</div></div></div>
                                </div>
                            </div>
                        </td>
					</tr>
                </table>
            </div>
            <%-- end contentBox div for form--%>
        </html:form>
    </div>
    <%-- end homecolumn div--%>
</div>