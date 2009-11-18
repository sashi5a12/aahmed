<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
    <div class="homeColumnTab">

        <div class="lBox">
            <div class="headLeftCurveblk"></div>
            <h1>FAQ Categories</h1>
            <div class="headRightCurveblk"></div>
        </div>

        <div class="contentbox">
            <table width="100%" cellspacing="0" cellpadding="5" border="0" class="GridGradient">
                <% int counter = 0;%>
                <logic:iterate id="category" name="FAQsForm" property="categoryList">
                    <% if ((counter % 2) == 0) { %>
                    <tr>
                        <% } %>
                        <% if (counter < 2) { %>
                        <th width="50%" class="blulink">
                            <a href='FAQsView.do?faqCategoryId=<bean:write name="category" property="faqCategoryId"/>&faqCategoryName=<bean:write name="category" property="faqCategoryName"/>'>
                                <bean:write name="category" property="faqCategoryName"/>
                            </a>
                        </th>
                        <%} else {%>
                        <td align="left" width="50%" class="blulink">
                            <a href='FAQsView.do?faqCategoryId=<bean:write name="category" property="faqCategoryId"/>&faqCategoryName=<bean:write name="category" property="faqCategoryName"/>' >
                                <bean:write name="category" property="faqCategoryName"/>
                            </a>
                        </td>
                        <%}%>
                        <% if ((counter % 2) != 0) { %>
                    </tr>
                    <% } %>
                    <% counter++; %>
                </logic:iterate>

                <logic:empty name="FAQsForm" property="categoryList">
                    <tr>
                        <td align="left">
                            <bean:message key="TopicsForm.NoFAQCategories"
                                          bundle="com.netpace.aims.action.CONTENT_MESSAGE"/>
                        </td>
                    </tr>
                </logic:empty>
            </table>
        </div>

    </div>
</div>
