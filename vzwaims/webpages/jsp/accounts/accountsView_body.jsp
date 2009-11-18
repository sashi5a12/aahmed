<%@ page language="java" %>

<%@ page import="com.netpace.aims.util.AimsConstants  " %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<script type="text/javascript">
    function submitForm(url) {
        //alert(url);
        document.forms[2].action = url;
        document.forms[2].submit();
    }
    function jumpTo() {
        var url = "<c:out value='${pageContext.request.contextPath}'/>/accounts.do?task=<c:out value='${requestScope.task}'/>"
        url += "&filter_field=<c:out value='${filter_field}'/>&filter_text=<c:out value='${filter_text}'/>"
        submitForm(url);
    }
	function clearCurrentStatus(){
		var cb=document.getElementsByName("selectedFilterValue");
		for(i=0; i<cb.length; i++){
			cb[i].checked=true;
		}	
	}
	function setFilterDisableState(){
		var cb=document.getElementsByName('selectedFilterValue');
		if (cb[0].value=="<%=AimsConstants.FILTER_SHOW_ALL %>" && cb[0].checked==true){
			for(i=1; i<cb.length; i++){
				cb[i].checked=false;
				cb[i].disabled=true;			
			}
		}
		else {
			var count=0;
			for(i=1; i<cb.length; i++){
				if (cb[i].checked==true){
					count++;
				}
			}
			if (count==3){
				cb[0].checked=true;
				for(i=1; i<cb.length; i++){
					cb[i].checked=false;
					cb[i].disabled=true;
				}				
			}			
		}
	}	    
</script>
<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>

<html:form action="/accounts.do">
<div class="btnTopLine" style="padding-top: 10px; padding-bottom: 10px">
    <c:if test="${isAccountManager eq 'true'}">
        <table cellspacing="0" border="0" cellpadding="0" width="100%">
            <tr>
                <td align="right" width="89%" style="padding-right: 3px">
                    <html:select styleClass="selectField" property="accountManager">
                        <html:option value="0">Please Select One...</html:option>
                        <html:optionsCollection name="activeVzwAccounts" label="username" value="userId"/>
                    </html:select>
                </td>
                <td align="right" width="11%">
                    <div class="redBtn" style=" margin-left:5px;float:right" id="AddToList1" title="Add To List">
                        <div>
                            <div>
                                <div onclick="document.forms[1].action='/aims/accounts.do?task=addAccountManager';document.forms[1].submit();">Add
                                    To List
                                </div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </c:if>
    <c:if test="${empty isAccountManager}">
        <div class="redBtn" style=" margin-left:5px;float:right;padding-bottom: 10px" id="inviteUser1" title="Invite User">
            <div>
                <div><div onclick="window.location='/aims/accountsInvite.do?task=inviteUser'">Invite</div></div>
            </div>
        </div>
    </c:if>
</div>
</html:form>

<c:choose>
	<c:when test="${requestScope.filterValue eq 'Showing All'}">
		<c:set var="statusIcon" value="images/icon_filter.gif" scope="page"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="statusIcon" value="images/icon_filter2.gif" scope="page"></c:set>
	</c:otherwise>
</c:choose>

<html:form action="/accounts.do?task=view">
<html:hidden property="filterText"/>
<html:hidden property="filterField"/>

<div id="contentBox">
<%@ include file="/common/error.jsp" %>
<!-- DATA GRID START HERE -->
<DIV class="homeColumnTab lBox">
    <DIV class="headLeftCurveblk"></DIV>
    <H1>
	    <c:choose>
			<c:when test="${empty isAccountManager}">
			  	<bean:message key="AccountForm.viewHeading" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
			</c:when>
			<c:otherwise>
			  	<bean:message key="AccountForm.accountManagerHeading" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
			</c:otherwise>
		</c:choose>    	
    </H1>

    <DIV class="headRightCurveblk"></DIV>
    <DIV class="contentbox">
        <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
            <tr>
                <th>Email Address</th>
                <th>
                    <bean:message key="AccountForm.firstName" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                </th>
                <th>
                    <bean:message key="AccountForm.lastName" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                </th>
                <th>
                    <bean:message key="AccountForm.createdDate" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                </th>
                <th>
                    <bean:message key="AccountForm.accountStatus" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                    <c:if test="${empty isAccountManager}">
                        <!-- STATUS FILTER POPUP START HERE -->
                        <a href="#" onclick="return false;"><img src='<c:out value="${statusIcon}"/>' width="11" height="11"
                                                                 border="0" id="filterLink1" rel="contentDiv1"
                                                                 title='Account Status = "<c:out value="${requestScope.filterValue}"/>"'></a>

                        <DIV id="contentDiv1" class="divContent">
                            <div style="width:103px">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10" align="left"><img src="images/login_header_left_curve.gif"
                                                                         width="10" height="21"></td>
                                        <td style="background-image:url(images/login_header_bg_tile.gif); background-repeat:repeat;"
                                            align="right">
                                            <a href="#"
                                               onClick="dropdowncontent.closeFilter('contentDiv1');return false;"><img
                                                    style="margin-right:-10px" src="images/icon-close.gif" width="47"
                                                    height="18" border="0"/></a>
                                        </td>
                                        <td width="10"><img src="images/login_header_right_curve.gif" width="10"
                                                            height="21"></td>
                                    </tr>
                                </table>
                            </div>
                            <DIV class="divFormContent" style="width:100px; height:80px;">
                                <table border="0" cellspacing="0" cellpadding="0"
                                       style="border:none; background-color:none;">
                                    <c:forEach var="status" items="${AccountForm.filterStatus}">
                                        <tr>
                                            <td>
                                                <html:multibox property="selectedFilterValue">
                                                    <c:out value="${status[0]}"/>
                                                </html:multibox>
                                                <a href="#"onclick="singleSelect('selectedFilterValue','<c:out value="${status[0]}" />',document.forms[2]);return false;"><c:out value="${status[1]}"/></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </DIV>
                            <DIV class="divFormButton">
								<div class="redBtn" id="filter" title="Filter">
									<div><div><div onclick="setFilterDisableState();validateFilter('selectedFilterValue',document.forms[2]);">Filter</div></div></div>
								</div>                                
                            </DIV>
                        </DIV>
                        <!-- STATUS FILTER POPUP END HERE -->
                    </c:if>
                </th>
                <c:if test="${empty isAccountManager}">
                    <th>User Role</th>
                </c:if>
                <th>
                    <c:choose>
                        <c:when test="${isAccountManager eq 'true'}">
                            <bean:message key="AccountForm.delete" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                        </c:when>
                        <c:otherwise>
                            <bean:message key="AccountForm.edit" bundle="com.netpace.aims.action.ACCOUNTS_MESSAGE"/>
                        </c:otherwise>
                    </c:choose>
                </th>
            </tr>
            <logic:iterate id="account" name="AimsAccounts">
                <tr>
                    <td align="left">
                        <c:choose>
                            <c:when test="${isAccountManager eq 'true'}">
                                <bean:write name="account" property="username"/>
                            </c:when>
                            <c:otherwise>
                                <a href='/aims/accountsSetup.do?task=editFormView&accountId=<bean:write name="account" property="userId"/>'
                                   class="a">
                                    <bean:write name="account" property="username"/>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="left">
                        <bean:write name="account" property="aimsContact.firstName"/>
                        &nbsp;</td>
                    <td align="left">
                        <bean:write name="account" property="aimsContact.lastName"/>
                        &nbsp;</td>
                    <td align="left">
                        <bean:write name="account" property="createdDate" formatKey="date.format" filter="true"/>
                        &nbsp;</td>
                    <td align="left">
                        <bean:write name="account" property="userAccountStatus"/>
                        &nbsp;</td>
                    <%--
                        for alliance user only one role will be available to show
                        do not show role on account manager screen
                    --%>
                    <c:if test="${empty isAccountManager}">
                        <td>
                            <logic:notEmpty name="account" property="roles">
                                <logic:iterate id="role" name="account" property="roles" indexId="roleCount">
                                    <bean:write name="role" property="roleName"/><%=(roleCount.longValue()==0) ? "" : "<br/>" %>
                                </logic:iterate>
                            </logic:notEmpty>
                        </td>
                    </c:if>
                    <td align="center">
                        <c:choose>
                            <c:when test="${isAccountManager eq 'true'}">
                                <a href='#' class="a"
                                   onclick="javascript:if (window.confirm('Are you sure you want to delete this recrod?')) { submitForm('/aims/accounts.do?task=delAccountManager&accountId=<bean:write name="account" property="userId"/>');} else { return false;};return false;">
                                    <bean:message key="images.delete.icon"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href='/aims/accountsSetup.do?task=editForm&accountId=<bean:write name="account" property="userId"/>'
                                   class="a">
                                    <bean:message key="images.edit.icon"/>
                                </a>
                            </c:otherwise>
                        </c:choose>
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
                               onclick='submitForm("/aims/accounts.do?task=<c:out value="${requestScope.task}"/>&page_id=<%=page_id.intValue() - 1%>&filter_field=<bean:write name="AccountForm" property="filterField" />&filter_text=<bean:write name="AccountForm" property="filterText" />");return false;'
                               class="a"><strong>Previous</strong></a><img src="images/spacer.gif" width="10"
                                                                           height="1"/>
                            <%if (startPageCount - 10 > 0) {%>
                            <a href='#'
                               onclick='submitForm("/aims/accounts.do?task=<c:out value="${requestScope.task}"/>&page_id=<%=startPageCount - 10%>&filter_field=<bean:write name="AccountForm" property="filterField" />&filter_text=<bean:write name="AccountForm" property="filterText" />");return false'
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
                           onclick='submitForm("/aims/accounts.do?task=<c:out value="${requestScope.task}"/>&page_id=<%=pageCount%>&filter_field=<bean:write name="AccountForm" property="filterField" />&filter_text=<bean:write name="AccountForm" property="filterText" />");return false;'
                           class="a"><%=pageCount%>
                        </a><img src="images/spacer.gif" width="1" height="1"/>
                        <% } %>
                        <% } %>
                        <% } %>

                        <logic:lessThan name="page_id" value="<%=page_max.toString()%>">
                            <%if (startPageCount + 10 <= page_max.intValue()) {%>
                            <img src="images/spacer.gif" width="3" height="1"/><a href='#'
                                                                                  onclick='submitForm("/aims/accounts.do?task=<c:out value="${requestScope.task}"/>&page_id=<%=startPageCount + 10%>&filter_field=<bean:write name="AccountForm" property="filterField" />&filter_text=<bean:write name="AccountForm" property="filterText" />");return false;'
                                                                                  class="a"><img
                                src="images/greyRndArrow.gif" height="17" border="0" align="absbottom"/></a>
                            <% } %>
                            <img src="images/spacer.gif" width="10" height="1"/><a href='#'
                                                                                   onclick='submitForm("/aims/accounts.do?task=<c:out value="${requestScope.task}"/>&page_id=<%=page_id.intValue() + 1%>&filter_field=<bean:write name="AccountForm" property="filterField" />&filter_text=<bean:write name="AccountForm" property="filterText" />");return false;'
                                                                                   class="a"><strong>Next</strong></a>
                        </logic:lessThan>
                    </td>
                </tr>
                 <c:if test="${empty isAccountManager}">
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
                </c:if>
            </logic:greaterThan>
            <tr>
                <td width="100%" align="right">&nbsp;</td>
            </tr>
            <c:if test="${isAccountManager eq 'true'}">
                <tr>
                    <td>
                        <table cellspacing="0" border="0" cellpadding="0" width="100%">
                            <tr>
                                <td align="right" width="89%" style="padding-right: 3px">
                                    <html:select styleClass="selectField" property="accountManager">
                                        <html:option value="0">Please Select One...</html:option>
                                        <html:optionsCollection name="activeVzwAccounts" label="username"
                                                                value="userId"/>
                                    </html:select>
                                </td>
                                <td align="right" width="11%">
                                    <div class="redBtn" style=" margin-left:5px;float:right" id="AddToList"
                                         title="Add To List">
                                        <div>
                                            <div>
                                                <div onclick="submitForm('/aims/accounts.do?task=addAccountManager');return false;">
                                                    Add To List
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </c:if>
            <c:if test="${empty isAccountManager}">
                <tr>
                    <td width="100%" align="right">
                        <div class="redBtn" style=" margin-left:5px;float:right; margin-top:3px" id="inviteUser"
                             title="Invite User">
                            <div>
                                <div><div onclick="window.location='/aims/accountsInvite.do?task=inviteUser'">Invite</div></div>
                            </div>
                        </div>
                    </td>
                </tr>
            </c:if>
        </table>
    </DIV>
</DIV>
<!-- PAGER END HERE -->
</div>
</html:form>


        <c:if test="${empty isAccountManager}">
            <script type="text/javascript">
                dropdowncontent.init("filterLink1", "left-bottom", -1)
                window.document.onclick=onclick;
               	setFilterDisableState();
            </script>
        </c:if>