<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<DIV class="headLeftCurveblk"></DIV>
<H1>
    <bean:message key="FAQForm.EditHeading" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
</H1>
<DIV class="headRightCurveblk"></DIV>

<html:form action="/FAQsEditSubmit?task=edit">
<input type="hidden" name="faqTopicId" value='<bean:write name="aimsFAQTopic" property="faqTopicId"/>'/>
<DIV class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th  width="15%">
            <strong><bean:message key="FAQForm.Category" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
            <span class="requiredText">*</span>:</strong>
        </th>
        <th >
            <html:select property="faqCategoryId" styleClass="selectField">
                <html:optionsCollection property="categoryList" name="aimsFAQTopic" label="faqCategoryName" value="faqCategoryId"/>
            </html:select>
        </th>
    </tr>
    <tr>
        <td  >
            <strong><bean:message key="FAQForm.Question" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
            <span class="requiredText">*</span>:</strong>
        </td>
        <td >
            <html:textarea property="question" rows="10" cols="70" styleClass="textareaField"/>
        </td>
    </tr>
    <tr>
        <td >
            <strong><bean:message key="FAQForm.Answer" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
            <span class="requiredText">*</span>:</strong>
        </td>
        <td >
            <html:textarea property="answer" rows="10" cols="70" styleClass="textareaField"/>
        </td>
    </tr>
</table>

<table width="100%">
    <tr>
        <td>
            <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Submit" title="Submit">
                <div>
                    <div>
                        <div onclick="document.forms[0].submit()">Submit</div>
                    </div>
                </div>
            </div>
            <div class="blackBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Back" title="Back">
                <div>
                    <div>
                        <div onclick="window.location='FAQsViewDelete.do?task=view'">Back</div>
                    </div>
                </div>
            </div>            
        </td>
    </tr>
</table>

</div>
</html:form>

</div>
</div>