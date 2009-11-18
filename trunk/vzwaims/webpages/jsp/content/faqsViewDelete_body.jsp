<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

    <DIV class="headLeftCurveblk"></DIV>
    <H1>
        <bean:message key="FAQForm.ListHeading" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
    </H1>

    <DIV class="headRightCurveblk"></DIV>

    <DIV class="contentbox">

        <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
            <tr>
                <th>
                    <strong>
                        <bean:message key="FAQForm.Category" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
                    </strong>
                </th>
                <th>
                    <strong>
                        <bean:message key="FAQForm.Question" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
                    </strong>
                </th>
                <th>
                    <strong>
                        <bean:message key="FAQForm.Edit" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
                    </strong>
                </th>
                <th>
                    <strong>
                        <bean:message key="FAQForm.Delete" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
                    </strong>
                </th>
            </tr>
            <logic:iterate id="faqTopics" name="aimsFAQTopics">
                <tr>
                    <td align="left">
                        <bean:write name="faqTopics" property="aimsFaqCategory.faqCategoryName"/>
                    </td>
                    <td align="left">
                        <a class="a"
                           href='FAQsDetailView.do?task=detailView&faq_topic_id=<bean:write name="faqTopics" property="faqTopicId"/>'>
                            <bean:write name="faqTopics" property="faqTopicQuestion"/>
                        </a></td>
                    <td align="left">
                        <div align="center">
                            <a href='FAQsCreateEdit.do?task=editForm&faq_topic_id=<bean:write name="faqTopics" property="faqTopicId"/>'>
                                <img src="images/icon-edit.gif" alt="Edit" width="18" height="13" border="0"/>
                            </a>
                        </div>
                    </td>
                    <td align="center">
                        <div align="center">
                            <a href='FAQsViewDelete.do?task=delete&faq_topic_id=<bean:write name="faqTopics" property="faqTopicId"/>'
                               onClick="javascript:if (window.confirm('Are you sure you want to delete this FAQ Question?')) { return true;} else { return false;}">
                                <img src="images/icon-delete.gif" alt="Delete" width="15" height="14" border="0"/>
                            </a>
                        </div>
                    </td>
                </tr>
            </logic:iterate>
        </table>

        <table width="100%" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td height="30" align="right">
                    <logic:greaterThan name="page_id" value="1">
                        <a href='/aims/FAQsViewDelete.do?task=view&page_id=<%=page_id.intValue() - 1%>' class="a"><img src="images/greyRndArrowL.gif" align="absbottom"/></a>
                    </logic:greaterThan>
                    <logic:greaterThan name="page_max" value="1">
                        <b>
                            <img alt="" src="images/spacer.gif" width="3" height="1"/>Page
                            <img alt="" src="images/spacer.gif" width="3" height="1"/><%=page_id.toString()%>
                            <img alt="" src="images/spacer.gif" width="3" height="1"/>of
                            <img alt="" src="images/spacer.gif" width="3" height="1"/><%=page_max.toString()%>
                            <img alt="" src="images/spacer.gif" width="3" height="1"/>
                        </b>
                    </logic:greaterThan>
                    <logic:lessThan name="page_id" value="<%=page_max.toString()%>">
                        <a href='/aims/FAQsViewDelete.do?task=view&page_id=<%=page_id.intValue() + 1%>' class="a"><img src="images/greyRndArrow.gif" align="absbottom"/></a>
                    </logic:lessThan>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Create"
                         title="Create">
                        <div>
                            <div>
                                <div onclick="window.location='FAQsCreateEdit.do?task=createForm'">Create</div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>
</div>

