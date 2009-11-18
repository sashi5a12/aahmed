<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<script language="JavaScript">

    function truncateLocalTextAreas()
    {
        TruncateTextWithCount(document.forms[0].faqCategoryDesc, 'textCountFaqCategoryDesc', 500);
    }

    function trackCountForTextAreas()
    {
        TrackCount(document.forms[0].faqCategoryDesc, 'textCountFaqCategoryDesc', 500);
    }

</script>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
    <DIV class="homeColumnTab lBox">

        <html:form action="/faqCategoryInsertUpdate">

            <DIV class="headLeftCurveblk"></DIV>
            <H1>
                <logic:match name="FAQCategoryForm" property="task" scope="request" value="create">
                    <html:hidden property="task" value="create"/>
                    <bean:message key="FAQCategoryForm.CreateHeading" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
                </logic:match>
                <logic:match name="FAQCategoryForm" property="task" scope="request" value="edit">
                    <bean:message key="FAQCategoryForm.UpdateHeading" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
                    <html:hidden property="task" value="edit"/>
                </logic:match>
            </H1>

            <DIV class="headRightCurveblk"></DIV>

            <html:hidden property="faqCategoryId"/>
            <DIV class="contentbox">

                <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
                    <tr>
                        <th width="20%">
                            <strong>
                                <bean:message key="FAQCategoryForm.Name"
                                              bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
                                <span class="requiredText">*</span>:</strong>
                        </th>
                        <th>
                            <html:text property="faqCategoryName" size="30" maxlength="50" styleClass="inputField"/>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <div align="left">
                                <strong>
                                    <bean:message key="FAQCategoryForm.Desc"
                                                  bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
                                    :
                                </strong>
                            </div>
                        </td>
                        <td>
                            <html:textarea property="faqCategoryDesc" rows="5" cols="60"
                                           onkeyup="TrackCount(this,'textCountFaqCategoryDesc',500)"
                                           onkeypress="LimitText(this,500)" styleClass="textareaField"/>
                            <br/>
                            <bean:message key="message.textarea.char.remaining"/>
                            <input type="text" name="textCountFaqCategoryDesc" size="4" value="500" disabled="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td valign="bottom">
                            <strong>
                                <bean:message key="FAQCategoryForm.Platform"
                                              bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
                                <span class="requiredText">*</span>:
                            </strong>
                        </td>
                        <td>
                            <html:select property="platformId" styleClass="selectField">
                                <html:optionsCollection property="platformList" label="platformName"
                                                        value="platformId"/>
                            </html:select>
                        </td>
                    </tr>
                </table>

                <table width="100%">
                    <tr>
                        <td align="right">
                            <div class="redBtn" style="float:right;padding-left:10px" id="Save" title="Save">
                                <div>
                                    <div>
                                        <div onclick="document.forms[0].submit()">Save</div>
                                    </div>
                                </div>
                            </div>
                            <div class="blackBtn" style="float:right; padding-left:10px;" id="Back" title="Back">
                                <div>
                                    <div>
                                        <div onclick="window.location='/aims/faqCategory.do?task=view'">Back</div>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </DIV>
        </html:form>
    </DIV>
</DIV>

<script language="javascript">
    trackCountForTextAreas();
</script>