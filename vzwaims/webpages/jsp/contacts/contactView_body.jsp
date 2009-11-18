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
                <bean:parameter id="taskForThisPage" name="task" value="Nopes"/>
                <logic:match name="taskForThisPage" scope="page" value="create">
                    <html:hidden property="task" value="create"/>
                </logic:match>
                <logic:match name="taskForThisPage" scope="page" value="edit">
                    <html:hidden property="task" value="edit"/>
                    <html:hidden property="contactId"/>
                </logic:match>

                <table width="100%" border="0" cellpadding="5" cellspacing="0" class="GridGradient">
                    <tr>
                        <th width="50%">
                            <strong><bean:message key="ContactForm.email" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
                            <bean:write name="ContactForm" property="contactEmailAddress" scope="request"/>
                        </th>
                        <th width="50%">&nbsp;</th>
                    </tr>

                    <tr>
                        <td>
                            <strong><bean:message key="ContactForm.firstName" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
                            <bean:write name="ContactForm" property="contactFirstName" scope="request"/>
                        </td>
                        <td>
                            <strong><bean:message key="ContactForm.lastName" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
                            <bean:write name="ContactForm" property="contactLastName" scope="request"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <strong><bean:message key="ContactForm.title" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
                            <bean:write name="ContactForm" property="contactTitle" scope="request"/>
                        </td>
                        <td>
                            <strong><bean:message key="ContactForm.phone" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>:</strong> <br/>
                            <bean:write name="ContactForm" property="contactPhone" scope="request"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <strong><bean:message key="ContactForm.mobile" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>:</strong> <br/>
                            <bean:write name="ContactForm" property="contactMobile" scope="request"/>
                        </td>
                        <td>
                            <strong><bean:message key="ContactForm.fax" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>:</strong> <br/>
                            <bean:write name="ContactForm" property="contactFax" scope="request"/>
                        </td>
                    </tr>
                    <tr>
						<td colspan="2" align="right">
                            <div class="divButtons" style="float:right;">
                                <div class="redBtn" style="float: left;" id="btnEdit" title="Edit">
                                    <div><div><div onclick="window.location='/aims/contactsSetup.do?task=edit&contactId=<bean:write name="ContactForm" property="contactId" scope="request"/>'">Edit</div></div></div>                                    
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