<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" 
    var="JS_TYPE"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).PAGEMODEL" 
    var="PAGEMODEL"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_USER_EMAIL_ADDRESS_TRUNCATION_LENGTH" 
    var="DEFAULT_USER_EMAIL_ADDRESS_TRUNCATION_LENGTH"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_USER_FULL_NAME_TRUNCATION_LENGTH" 
    var="DEFAULT_USER_FULL_NAME_TRUNCATION_LENGTH"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_USER_USER_ROLE_TRUNCATION_LENGTH" 
    var="DEFAULT_USER_USER_ROLE_TRUNCATION_LENGTH"/>

<c:if test="${not empty ADMIN_USER_INVITATION_SENT and not empty EMAIL_ADDRESS}">
    <fmt:message key="msg.admin.user.invitation.send" var="msgAdminUserInvitationSend" >
        <fmt:param value="${EMAIL_ADDRESS}" />
    </fmt:message>
    <div class="success">
        <p>
            ${msgAdminUserInvitationSend}
        </p>
    </div>
</c:if>

<c:if test="${not empty ADMIN_USER_UPDATED}">
    <div class="success">
        <p>
            <fmt:message key="msg.admin.user.update" />
        </p>
    </div>
</c:if>

<c:if test="${not empty ADMIN_USER_DELETED}">
    <div class="success">
        <p>
            <fmt:message key="msg.admin.user.delete" />
        </p>
    </div>
</c:if>

<h1 class="redheading floatLeft">Manage Users</h1>

<form:form name="pageModelForm" action="${pageContext.request.contextPath}/secure/admin/userslist.do" method="post" modelAttribute="genericListForm">

    <!-- Search Bar Starts-->
    <div class="searchBar">
        <a class="button floatRight marginLeft10" href="javascript:;"><span class="red">Search</span></a>
        <form:select path="pageModel.searchBy" cssClass="selct floatRight marginLeft10" cssStyle="width:200px">
            <form:option value="">Search Criteria</form:option>
            <form:option value="user.emailAddress">User Email</form:option>
            <form:option value="user.fullName">Full Name</form:option>
        </form:select>
        <form:input path="pageModel.searchValue" cssClass="input floatRight" cssStyle="width:200px"/>
        <div class="clearboth"></div>
    </div>
    <!-- Search Bar Ends -->

    <div class="floatRight">
        <a class="button floatRight marginLeft10" href="${pageContext.request.contextPath}/secure/admin/inviteuserview.do" ><span class="red">Invite Verizon User</span></a></div>
    <div class="clearboth"></div>

    <!-- Table Grid Starts -->
    <table width="100%" border="0" class="datagrid-gray" cellpadding="0" cellspacing="0">
        <tr>
            <th>
                <tg:sort sortable="true" columnTitle="Email Address" columnField="user.emailAddress" pageModelSortBy="${genericListForm.pageModel.sortBy}" pageModelIsAscending="${genericListForm.pageModel.ascending}"/>
            </th>
            <th style="width:150px">
                <tg:sort sortable="true" columnTitle="Full Name" columnField="user.fullName" pageModelSortBy="${genericListForm.pageModel.sortBy}" pageModelIsAscending="${genericListForm.pageModel.ascending}"/>
            </th>
            <th style="width:150px">
                User Role
            </th>
            <th style="width:40px">
                <tg:sort sortable="true" columnTitle="User Status" columnField="user.active" pageModelSortBy="${genericListForm.pageModel.sortBy}" pageModelIsAscending="${genericListForm.pageModel.ascending}"/>
            </th>
            <th style="width:73px">
                <tg:sort sortable="true" columnTitle="Created Date" columnField="user.createdDate" pageModelSortBy="${genericListForm.pageModel.sortBy}" pageModelIsAscending="${genericListForm.pageModel.ascending}"/>
            </th>
            <th class="center-text">Edit</th>
            <th class="center-text">Delete</th>
        </tr>
        <c:set var="ROLE_PARTNER_USER" value="<%=VAPConstants.ROLE_PARTNER_USER%>" />
        <sec:authentication property="principal.id" var="loggedInAdminId" />
        <c:forEach items="${genericListForm.pageModel.records}" var="record" varStatus="status">
            <%-- edit button --%>
            <c:set var="editBtn" >
                <a href="${pageContext.request.contextPath}/secure/admin/edituserview.do?id=${record.id}" title="" 
                   ><tg:img src="/images/table-grid-edit-icon.png" alt="" width="13" height="14" border="0" /></a>
            </c:set>

            <%-- delete button --%>
            <c:set var="deleteBtn" >
                <a href="${pageContext.request.contextPath}/secure/admin/deleteuser.do?id=${record.id}" title="" 
                   <c:if test='${not (not record.isActive or record.id eq loggedInAdminId or userRole eq ROLE_PARTNER_USER)}'> onclick="return confirm('<fmt:message key="msg.admin.user.delete.confirm" />');" </c:if>
                   ><tg:img src="/images/table-grid-delete-icon.png" alt="" width="14" height="16" border="0" /></a>
            </c:set>

            <tr>
                <td>
                    <c:set var="truncatedEmail" value="${fn:substring(record.emailAddress, 0, DEFAULT_USER_EMAIL_ADDRESS_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(record.emailAddress) gt DEFAULT_USER_EMAIL_ADDRESS_TRUNCATION_LENGTH}">
                        <c:set var="truncatedEmail" value="${truncatedEmail}..." />
                    </c:if>
                    <a href="mailto:${record.emailAddress}" title="${record.emailAddress}">${truncatedEmail}</a></td>
                <td>
                    <c:set var="truncatedFullName" value="${fn:substring(record.fullName, 0, DEFAULT_USER_FULL_NAME_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(record.fullName) gt DEFAULT_USER_FULL_NAME_TRUNCATION_LENGTH}">
                        <c:set var="truncatedFullName" value="${truncatedFullName}..." />
                    </c:if>
                    <a href="${pageContext.request.contextPath}/secure/userview.do?id=${record.id}" 
                       title="${record.fullName}">${truncatedFullName}</a>
                </td>
                <td>
                    <c:set var="userRole" value="" />
                    <c:set var="userRoles" value="" />
                    <c:forEach items="${record.roles}" var="role" varStatus="status">

                        <c:if test="${role.roleName eq ROLE_PARTNER_USER}">
                            <c:set var="userRole" value="${role.roleName}" />
                            <%--
                                Disable edit and delete icons and void the links for 
                                users with role ROLE_PARTNER_USER to restrict the 
                                logged in admin editing and deleting them.
                            --%>
                            <%-- edit button --%>
                            <c:set var="editBtn" >
                                <tg:img src="/images/table-grid-edit-icon-disable.png" alt="" width="13" height="14" border="0" />
                            </c:set>

                            <%-- delete button --%>
                            <c:set var="deleteBtn" >
                                <tg:img src="/images/table-grid-delete-icon-disable.png" alt="" width="14" height="16" border="0" />
                            </c:set>
                        </c:if>
                        <c:if test='${record.id eq loggedInAdminId}'>
                            <%--
                                Disable edit and delete icons and void the links for 
                                logged in admin to restrict editing and deleting itself.
                                Also showing the tooltip to edit itself from My Profile 
                                menu.
                            --%>

                            <%-- edit button tooltip --%>
                            <fmt:message key="tooltip.admin.edit.profile" 
                                         var="tooltipAdminEditProfile" />

                            <%-- edit button --%>
                            <c:set var="editBtn" >
                                <label title="${tooltipAdminEditProfile}"><tg:img src="/images/table-grid-edit-icon-disable.png" alt="" width="13" height="14" border="0" /></label>
                            </c:set>

                            <%-- delete button --%>
                            <c:set var="deleteBtn" >
                                <tg:img src="/images/table-grid-delete-icon-disable.png" alt="" width="14" height="16" border="0" />
                            </c:set>
                        </c:if>

                        <c:if test='${not record.isActive}'>
                            <%-- delete button --%>
                            <c:set var="deleteBtn" >
                                <tg:img src="/images/table-grid-delete-icon-disable.png" alt="" width="14" height="16" border="0" />
                            </c:set>
                        </c:if>
                        <c:set var="userRoles" value="${userRoles}${role.displayTitle}" />
                        <c:if test="${!status.last}">
                            <c:set var="userRoles" value="${userRoles}, " />
                        </c:if>
                    </c:forEach>
                    <c:set var="truncatedUserRoles" value="${fn:substring(userRoles, 0, DEFAULT_USER_USER_ROLE_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(userRoles) gt DEFAULT_USER_USER_ROLE_TRUNCATION_LENGTH}">
                        <c:set var="truncatedUserRoles" >
                            <label class="truncated-text" title="${userRoles}">${truncatedUserRoles}...</label>
                        </c:set>
                    </c:if>
                    ${truncatedUserRoles}
                </td>
                <td><c:out value="${record.isEnable? 'Active' : 'Inactive'}" /></td>
                <td><fmt:formatDate pattern="MM-dd-yyyy" value="${record.createdDate}" /></td>

                <td class="center-text"><c:out value="${editBtn}" escapeXml="false" /></td>
                <td class="center-text"><c:out value="${deleteBtn}" escapeXml="false" /></td>
            </tr>
        </c:forEach>
    </table>
    <!--Table Grid ends -->
    <div class="morebuttons">
        <a class="button floatRight marginLeft10" href="${pageContext.request.contextPath}/secure/admin/inviteuserview.do" ><span class="red">Invite Verizon User</span></a></div><div class="clearboth"></div>


    <div class="clearboth marginTop40"></div>

    <!-- pagination starts -->
    <tg:paging pageModel="${genericListForm.pageModel}" />
    <!-- pagination ends -->

    <input type="hidden" id="pageModel.prevSearchValue" />
    <input type="hidden" name="pageModel.sortBy" value="${genericListForm.pageModel.sortBy}">
    <input type="hidden" name="pageModel.ascending" value="${genericListForm.pageModel.ascending}" >

</form:form>
<div class="clearboth"></div>
<!-- Content Ends -->
<tg:includestatic type="${JS_TYPE}" paramName="${PAGEMODEL}" />
<script type="text/javascript">

    var searchField = document.getElementById('searchTerm'),
            searchPlaceholder = 'Search Term';

    addEvent(searchField, 'focus', function() {
        if (this.value === searchPlaceholder)
            this.value = '';
    });
    addEvent(searchField, 'blur', function() {
        if (this.value === '')
            this.value = searchPlaceholder;
    });

    var tempPlaceholder = '';

    if(document.forms['pageModelForm'].elements['pageModel.page'] != null && 
        document.forms['pageModelForm'].elements['pageModel.page'] != undefined) {
        tempPlaceholder = document.forms['pageModelForm'].elements['pageModel.page'].value;
    }

    var jumpToPageField = document.getElementById('jumpToPage'),
            jumpToPagePlaceholder = tempPlaceholder;

    addEvent(jumpToPageField, 'focus', function() {
        if (this.value === jumpToPagePlaceholder)
            this.value = '';
    });

    addEvent(jumpToPageField, 'blur', function() {
        if (this.value === '')
            this.value = jumpToPagePlaceholder;
    });
</script>
