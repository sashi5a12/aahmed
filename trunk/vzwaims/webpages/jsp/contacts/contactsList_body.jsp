<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script type="text/javascript">
    function submitForm(url) {
        document.forms[2].action = url;
        document.forms[2].submit();
    }
    function jumpTo() {
        var url = "<c:out value='${pageContext.request.contextPath}'/>/contacts.do?task=<c:out value='${requestScope.task}'/>"
        url += "&filter_field=<c:out value='${filter_field}'/>&filter_text=<c:out value='${filter_text}'/>"
        submitForm(url);
    }    
</script>
<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>

<html:form action="/contacts.do">
    <div class="btnTopLine" style="padding-top: 10px; padding-bottom: 10px">
        <div class="redBtn" style=" margin-left:5px;float:right;padding-bottom: 10px" id="Create1" title="Create">
            <div>
                <div><div onclick="window.location='/aims/contactsSetup.do?task=create'">Create</div></div>
            </div>
        </div>
    </div>
</html:form>


<html:form action="/contacts.do?task=viewList">
    <html:hidden property="filterText"/>
    <html:hidden property="filterField"/>

    <div id="contentBox">
    <%@ include file="/common/error.jsp" %>
    <!-- DATA GRID START HERE -->
    <DIV class="homeColumnTab lBox">
        <DIV class="headLeftCurveblk"></DIV>
        <H1>
            <bean:message key="ContactForm.viewHeading" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
        </H1>

        <DIV class="headRightCurveblk"></DIV>
        <DIV class="contentbox">
            <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
                <tr>
                    <th>Email Address</th>
                    <th>
                        <bean:message key="ContactForm.firstName" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                    </th>
                    <th>
                        <bean:message key="ContactForm.lastName" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                    </th>
                    <th>
                        <bean:message key="ContactForm.createdDate" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                    </th>
                    <th>
                        <bean:message key="ContactForm.edit" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                    </th>
                </tr>
                <logic:iterate id="contact" name="AimsContacts">
                    <tr>
                        <td align="left">
                            <a href='/aims/contactsSetup.do?task=view&contactId=<bean:write name="contact" property="contactId"/>'
                               class="a">
                                <bean:write name="contact" property="emailAddress"/>
                            </a>
                        </td>
                        <td align="left">
                            <bean:write name="contact" property="firstName"/>
                            &nbsp;</td>
                        <td align="left">
                            <bean:write name="contact" property="lastName"/>
                            &nbsp;</td>
                        <td align="left">
                            <bean:write name="contact" property="createdDate" formatKey="date.format" filter="true"/>
                            &nbsp;</td>
                        <td align="center">
                            <a href='/aims/contactsSetup.do?task=edit&contactId=<bean:write name="contact" property="contactId"/>' class="a">
                                <bean:message key="images.edit.icon"/>
                            </a>
                        </td>
                    </tr>
                </logic:iterate>
            </table>
        </DIV>
    </DIV>
    <!-- DATA GRID END HERE -->

    <!-- PAGER START HERE -->

    <DIV class="homeColumn lBox marginT">
        <DIV>
            <table width="100%" align="center" cellpadding="0" cellspacing="0">
                <logic:greaterThan name="page_max" value="1">
                    <tr>
                        <td height="30" align="right">

                            <%
                                int startPageCount = 0;
                                if (page_id.intValue() % 10 == 0)
                                    startPageCount = page_id.intValue() - 10 + 1;
                                else
                                    startPageCount = page_id.intValue() - (page_id.intValue() % 10) + 1;
                            %>

                            <logic:greaterThan name="page_id" value="1">
                                <a href='#'
                                   onclick='submitForm("/aims/contacts.do?task=<c:out value="${requestScope.task}"/>&page_id=<%=page_id.intValue() - 1%>&filter_field=<bean:write name="ContactForm" property="filterField" />&filter_text=<bean:write name="ContactForm" property="filterText" />");return false;'
                                   class="a"><strong>Previous</strong></a><img src="images/spacer.gif" width="10"
                                                                               height="1"/>
                                <%if (startPageCount - 10 > 0) {%>
                                <a href='#'
                                   onclick='submitForm("/aims/contacts.do?task=<c:out value="${requestScope.task}"/>&page_id=<%=startPageCount - 10%>&filter_field=<bean:write name="ContactForm" property="filterField" />&filter_text=<bean:write name="ContactForm" property="filterText" />");return false'
                                   class="a"><img src="images/greyRndArrowL.gif" height="17" border="0" align="absbottom"/></a><img
                                    src="images/spacer.gif" width="3" height="1"/>
                                <% } %>
                            </logic:greaterThan>

                            <% for (int pageCount = startPageCount; pageCount < startPageCount + 10; pageCount++) {%>
                            <%if (pageCount > 0 && pageCount <= page_max.intValue()) {%>
                            <%if (pageCount == page_id.intValue()) {%>
                            <b><%=pageCount%><img src="images/spacer.gif" width="1" height="1"/></b>
                            <% } else { %>
                            <a href='#'
                               onclick='submitForm("/aims/contacts.do?task=<c:out value="${requestScope.task}"/>&page_id=<%=pageCount%>&filter_field=<bean:write name="ContactForm" property="filterField" />&filter_text=<bean:write name="ContactForm" property="filterText" />");return false;'
                               class="a"><%=pageCount%>
                            </a><img src="images/spacer.gif" width="1" height="1"/>
                            <% } %>
                            <% } %>
                            <% } %>

                            <logic:lessThan name="page_id" value="<%=page_max.toString()%>">
                                <%if (startPageCount + 10 <= page_max.intValue()) {%>
                                <img src="images/spacer.gif" width="3" height="1"/><a href='#'
                                                                                      onclick='submitForm("/aims/contacts.do?task=<c:out value="${requestScope.task}"/>&page_id=<%=startPageCount + 10%>&filter_field=<bean:write name="ContactForm" property="filterField" />&filter_text=<bean:write name="ContactForm" property="filterText" />");return false;'
                                                                                      class="a"><img
                                    src="images/greyRndArrow.gif" height="17" border="0" align="absbottom"/></a>
                                <% } %>
                                <img src="images/spacer.gif" width="10" height="1"/><a href='#'
                                                                                       onclick='submitForm("/aims/contacts.do?task=<c:out value="${requestScope.task}"/>&page_id=<%=page_id.intValue() + 1%>&filter_field=<bean:write name="ContactForm" property="filterField" />&filter_text=<bean:write name="ContactForm" property="filterText" />");return false;'
                                                                                       class="a"><strong>Next</strong></a>
                            </logic:lessThan>
                        </td>
                    </tr>

                    <tr>
                        <td  align="right">
                            <table cellpadding="0" cellspacing="0" style="margin-top:10px" align="right">
                                <tr>
                                    <td>
                                        <strong>Jump to page&nbsp;</strong>
                                    </td>
                                    <td>
                                        <input type="text" name="page_id" size="4" value="<%=page_id.toString()%>">
                                    </td>
                                    <td>
                                        <strong>&nbsp;of <%=page_max.toString()%>
                                        </strong>
                                    </td>
                                    <td>
                                        <div class="redBtn" style="float:right; margin-left:10px" id="Go" title="Go">
                                            <div>
                                                <div>
                                                    <div onclick="jumpTo();">Go</div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                </logic:greaterThan>
                <tr>
                    <td width="100%" align="right">&nbsp;</td>
                </tr>

                <tr>
                    <td width="100%" align="right">
                        <div class="redBtn" style=" margin-left:5px;float:right; margin-top:3px" id="Create"
                             title="Create">
                            <div>
                                <div><div onclick="window.location='/aims/contactsSetup.do?task=create'">Create</div></div>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </DIV>
    </DIV>

<!-- PAGER END HERE -->
</div>
</html:form>