<%@ page language="java" %>
<%@ page import="com.netpace.aims.util.AimsUtils, com.netpace.aims.model.masters.AimsDevic" %>
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
    <bean:message key="FAQCategoryForm.ListHeading" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
</H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">

    <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
        <tr>
            <th align="center">
                <bean:message key="FAQCategoryForm.Name" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
            </th>
            <th align="center">
                <bean:message key="FAQCategoryForm.Desc" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
            </th>
            <th align="center">
                <bean:message key="FAQCategoryForm.Platform" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
            </th>
            <th align="center">
                <bean:message key="FAQCategoryForm.Edit" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
            </th>
            <th align="center">
                <bean:message key="FAQCategoryForm.Delete" bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
            </th>
        </tr>
        <logic:iterate id="faqCategories" name="FAQCategoryForm" property="faqCategories">
            <tr>
                <td class="firstcell" align="left">
                    <bean:write name="faqCategories" property="faqCategoryName"/>
                    &nbsp;
                </td>
                <td align="left">
                    <bean:write name="faqCategories" property="faqCategoryDesc"/>
                    &nbsp;
                </td>
                <td align="left">
                    <bean:write name="faqCategories" property="platformName"/>
                </td>
                <td align="center">
                    <a href='/aims/faqCategorySetup.do?task=edit&faqCategoryId=<bean:write name="faqCategories" property="faqCategoryId"/>'
                       class="standardLink">
                        <html:img src="images/icon-edit.gif" border="0" alt="Edit"/>
                    </a>
                </td>
                <td align="center">
                    <a href='/aims/faqCategory.do?task=delete&faqCategoryId=<bean:write name="faqCategories" property="faqCategoryId"/>'
                       onClick="javascript:if (window.confirm('Are you sure you want to delete this FAQ Category?')) { return true;} else { return false;}"
                       class="a">
                        <html:img src="images/icon-delete.gif" border="0" alt="Delete"/>
                    </a>
                </td>
            </tr>
        </logic:iterate>
    </table>

    <logic:greaterThan name="page_max" value="1">
        <div>&nbsp;</div>
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
            <tr align="right">
                <td>
                    <logic:greaterThan name="page_id" value="1">
                        <a href='/aims/faqCategory.do?task=view&page_id=<%=page_id.intValue() - 1%>' class="a"><img src="images/greyRndArrowL.gif" align="absbottom"/></a>
                    </logic:greaterThan>

                    <logic:greaterThan name="page_max" value="1">
                    <b>
                        <img alt="" src="images/spacer.gif" width="3" height="1"/>Page
                        <img alt="" src="images/spacer.gif" width="3" height="1"/><%=page_id.toString()%>
                        <img alt="" src="images/spacer.gif" width="3" height="1"/>of
                        <img alt="" src="images/spacer.gif" width="3" height="1"/><%=page_max.toString()%>
                        <img alt="" src="images/spacer.gif" width="3" height="1"/>
                    </b>
                        <%--<td align="center"> Page <%=page_id.toString()%> of <%=page_max.toString()%> </td>--%>
                    </logic:greaterThan>

                    <logic:lessThan name="page_id" value="<%=page_max.toString()%>">
                        <a href='/aims/faqCategory.do?task=view&page_id=<%=page_id.intValue() + 1%>' class="a"><img src="images/greyRndArrow.gif" align="absbottom"/></a>
                    </logic:lessThan>
            </tr>
        </table>
    </logic:greaterThan>

    <div>&nbsp;</div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="100%" align="right">
                <div class="redBtn" style="margin-left:5px;float:right; margin-top:10px;" id="Create" title="Create">
                    <div>
                        <div><div onclick="window.location='/aims/faqCategorySetup.do?task=create'">Create</div></div>
                    </div>
                </div>
            </td>
        </tr>
    </table>

</div>
</div>
</div>
