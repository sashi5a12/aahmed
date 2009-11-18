<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
    <DIV class="homeColumnTab lBox">


<DIV class="headLeftCurveblk"></DIV>
<H1>
    <bean:message key="FAQForm.FAQDetails" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
</H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">

<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th width="15%">
            <strong><bean:message key="FAQForm.Category" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>&nbsp;:</strong>
        </th>
        <th>
            <bean:write name="aimsDetailViewFAQ" property="aimsFaqCategory.faqCategoryName"/>
        </th>
    </tr>
    <tr>
        <td>
            <strong><bean:message key="FAQForm.Question" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>&nbsp;:</strong>
        </td>
        <td>
            <bean:write name="aimsDetailViewFAQ" property="faqTopicQuestion"/>
        </td>
    </tr>
    <tr>
        <td>
            <strong ><bean:message key="FAQForm.Answer" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>&nbsp;:</strong>
        </td>
        <td>
            <bean:write name="aimsDetailViewFAQ" property="faqTopicAnswer"/>
        </td>
    </tr>
</table>

<div>&nbsp;</div>

<table width="100%">
    <tr>
        <td>
            <div class="redBtn" style="padding-left:10px" id="Back" title="Back">
                <div>
                    <div><div onclick="window.location='FAQsViewDelete.do?task=view'">Back</div></div>
                </div>
            </div>
            <%--<a href='FAQsViewDelete.do?task=view'> <img src="images/back_b.gif" border="0" alt="Back"></a> &nbsp;--%>
        </td>
        <td>
            <div class="redBtn" style="float:right;padding-right:10px" id="Delete" title="Delete">
                <div>
                    <div><div onclick="window.location='FAQsViewDelete.do?task=delete&faq_topic_id=<bean:write name="aimsDetailViewFAQ" property="faqTopicId"/>'">Delete</div></div>
                </div>
            </div>

            <div class="blackBtn" style="float:right;padding-right:10px" id="Edit" title="Edit">
                <div>
                    <div><div onclick="window.location='FAQsCreateEdit.do?task=editForm&faq_topic_id=<bean:write name="aimsDetailViewFAQ" property="faqTopicId"/>'">Edit</div></div>
                </div>
            </div>
        </td>
    </tr>
</table>
</div>
</div>
</div>