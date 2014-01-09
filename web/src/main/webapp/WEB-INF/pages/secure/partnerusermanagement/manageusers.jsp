<%@page import="org.apache.commons.lang.StringUtils"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" 
    var="JS_TYPE"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).PAGEMODEL" 
    var="PAGEMODEL"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_PARTNER_EMAIL_TRUNCATION_LENGTH" 
    var="DEFAULT_PARTNER_EMAIL_TRUNCATION_LENGTH"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_PARTNER_FULL_NAME_TRUNCATION_LENGTH" 
    var="DEFAULT_PARTNER_FULL_NAME_TRUNCATION_LENGTH"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_USER_USER_ROLE_TRUNCATION_LENGTH" 
    var="DEFAULT_USER_USER_ROLE_TRUNCATION_LENGTH"/>

<c:if test="${not empty PARTNER_USER_INVITATION_SENT and not empty EMAIL_ADDRESS}">
    <fmt:message key="msg.partner.user.invitation.send" var="msgPartnerUserInvitationSend" >
        <fmt:param value="${EMAIL_ADDRESS}" />
    </fmt:message>
    <div class="success">
        <p>
            ${msgPartnerUserInvitationSend}
        </p>
    </div>
</c:if>

<c:if test="${not empty PARTNER_USER_UPDATED}">
    <div class="success">
        <p>
            <fmt:message key="msg.partner.user.update" />
        </p>
    </div>
</c:if>

<c:if test="${not empty PARTNER_USER_CANNOT_DELETE_MPOC and not empty MPOC_FULL_NAME}">
    <fmt:message key="msg.partner.user.cannot.delete.mpoc" var="msgPartnerUserCannotDeleteMpoc" >
        <fmt:param value="${MPOC_FULL_NAME}" />
    </fmt:message>
    <div class="error">
        <p>
            ${msgPartnerUserCannotDeleteMpoc}
        </p>
    </div>
</c:if>

<c:if test="${not empty PARTNER_USER_CANNOT_EDIT_MPOC and not empty MPOC_FULL_NAME}">
    <fmt:message key="msg.partner.user.cannot.edit.mpoc" var="msgPartnerUserCannotEditMpoc" >
        <fmt:param value="${MPOC_FULL_NAME}" />
    </fmt:message>
    <div class="error">
        <p>
            ${msgPartnerUserCannotEditMpoc}
        </p>
    </div>
</c:if>

<c:if test="${not empty PARTNER_USER_DELETED}">
    <div class="success">
        <p>
            <fmt:message key="msg.partner.user.delete" />
        </p>
    </div>
</c:if>

<h1 class="redheading floatLeft">Manage Users</h1>
<form:form name="pageModelForm" action="${pageContext.request.contextPath}/secure/company/manageusers.do" method="post" modelAttribute="genericListForm">
    <!-- Search Bar Starts-->
    <div class="searchBar">
        <a class="button floatRight marginLeft10" href="javascript:;"><span class="red">Search</span></a>
        <form:select path="pageModel.searchBy" cssClass="selct floatRight marginLeft10" cssStyle="width:200px">
            <form:option value="">Search Criteria</form:option>
            <form:option value="u.email_address">Email Address</form:option>
            <form:option value="u.full_name">Full Name</form:option>
        </form:select>
        <form:input path="pageModel.searchValue" cssClass="input floatRight" cssStyle="width:200px"/>
        <div class="clearboth"></div>
    </div>
    <!-- Search Bar Ends -->
    <div class="floatRight">
        <a class="button floatRight marginLeft10" href="${pageContext.request.contextPath}/secure/company/inviteuserview.do" ><span class="red">Invite User</span></a></div>
    <div class="clearboth"></div>

    <!-- Table Grid Starts -->
    <table width="100%" border="0" class="datagrid-gray" cellpadding="0" cellspacing="0">
        <tr>
            <th style="width:190px">
                <tg:sort sortable="true" columnTitle="Email Address" columnField="u.email_address" pageModelSortBy="${genericListForm.pageModel.sortBy}" pageModelIsAscending="${genericListForm.pageModel.ascending}"/>
            </th>
            <th style="width:120px">
                <tg:sort sortable="true" columnTitle="Full Name" columnField="u.full_name" pageModelSortBy="${genericListForm.pageModel.sortBy}" pageModelIsAscending="${genericListForm.pageModel.ascending}"/>
            </th>
            <th style="width:116px">
                <tg:sort sortable="true" columnTitle="User Status" columnField="u.is_active" pageModelSortBy="${genericListForm.pageModel.sortBy}" pageModelIsAscending="${genericListForm.pageModel.ascending}"/>
            </th>
            <th style="width:136px;">
                Approval
            </th>
            <th style="width:72px">
                <tg:sort sortable="true" columnTitle="Created Date" columnField="u.created_date" pageModelSortBy="${genericListForm.pageModel.sortBy}" pageModelIsAscending="${genericListForm.pageModel.ascending}"/>
            </th>
            <th class="center-text">Edit</th>
            <th class="center-text">Delete</th>
        </tr>
        <sec:authentication property="principal.id" var="loggedInUserId" />
        <%--<c:forEach items="${companyUsers}" var="companyUser">--%>
        <c:forEach items="${genericListForm.pageModel.records}" var="record" varStatus="status">
            <tr>
                <td>
                    <c:set var="truncatedEmail" value="${fn:substring(record.emailAddress, 0, DEFAULT_PARTNER_EMAIL_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(record.emailAddress) gt DEFAULT_PARTNER_EMAIL_TRUNCATION_LENGTH}">
                        <c:set var="truncatedEmail" value="${truncatedEmail}..." />
                    </c:if>
                    <a href="mailto:${record.emailAddress}" title="${record.emailAddress}">${truncatedEmail}</a></td>
                </td>
                <td>
                    <c:set var="truncatedFullName" value="${fn:substring(record.fullName, 0, DEFAULT_PARTNER_FULL_NAME_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(record.fullName) gt DEFAULT_PARTNER_FULL_NAME_TRUNCATION_LENGTH}">
                        <c:set var="truncatedFullName" >
                            <label class="truncated-text" href="javascript:;" title="${record.fullName}">${truncatedFullName}...</label>
                        </c:set>
                    </c:if>
                    ${truncatedFullName}
                </td>
                <td>
                    <c:out value="${record.status eq 'PENDING' ? 'Pending Approval' : (record.isEnable ? 'Active':'Inactive')}" />
                </td>
                <td>
                    <c:if test="${record.status eq 'PENDING'}" >
                        <div style="width:136px;">
                            <a class="button" href="${pageContext.request.contextPath}/secure/company/acceptrequesttojoincompany.do?id=${record.offerId}" ><span class="red">Accept</span></a><a class="button marginLeft5" href="${pageContext.request.contextPath}/secure/company/rejectrequesttojoincompany.do?id=${record.offerId}" ><span class="gray">Reject</span></a>
                        </div>
                    </c:if>
                </td>
                <td><fmt:formatDate pattern="MM-dd-yyyy" value="${record.createdDate}" />  </td>
                <%-- Edit button --%>
                <c:set var="editBtn">
                    <a href="${pageContext.request.contextPath}/secure/company/edituserview.do?id=${record.userId}" title="" 
                       ><tg:img 
                            src="/images/table-grid-edit-icon.png" alt="" width="13" height="14" border="0" /></a>
                </c:set>
            
                <%-- Delete button --%>
                <c:set var="deleteBtn">
                    <a href="${pageContext.request.contextPath}/secure/company/deleteuser.do?id=${record.userId}"
                        <c:if test='${not (not record.isActive or record.userId eq loggedInUserId or record.status eq "PENDING")}'> onclick="return confirm('<fmt:message key="msg.partner.user.delete.confirm" />');" </c:if>
                    ><tg:img src="/images/table-grid-delete-icon.png" alt="" width="14" height="16" border="0" /></a>
                </c:set>
                
                <%-- 
                    Note: Adding functionality to facilitate the partner to 
                          view the profile of user who requested to join the 
                          company as per UAT requirement.
                --%>
                <%-- 
                    *******************
                    Functionality start 
                    *******************
                --%>
                <%--<c:if test='${record.userId eq loggedInUserId or record.status eq "PENDING"}'>--%>
                <c:if test='${record.userId eq loggedInUserId }'>
                    <%--
                        Disable edit and delete icons and void the links for 
                        logged in user to restrict editing and deleting itself.
                        Also showing the tooltip to edit itself from My Profile 
                        menu.
                    --%>
                    
                    <%-- Edit button --%>
                    <c:set var="editBtn">
                        <tg:img src="/images/table-grid-edit-icon-disable.png" alt="" width="13" height="14" border="0" />
                    </c:set>

                    <%-- Delete button --%>
                    <c:set var="deleteBtn">
                        <tg:img src="/images/table-grid-delete-icon-disable.png" alt="" width="14" height="16" border="0" />
                    </c:set>
                    
                    <c:if test="${record.userId eq loggedInUserId}" >
                        <%-- Tooltip --%>
                        <fmt:message key="tooltip.partner.edit.profile" 
                                     var="tooltipPartnerEditProfile" />
                        <c:set var="editBtn">
                            <label title="${tooltipPartnerEditProfile}"><tg:img src="/images/table-grid-edit-icon-disable.png" alt="" width="13" height="14" border="0" /></label>
                        </c:set>
                    </c:if>
                </c:if>
                <c:if test='${record.userId eq loggedInUserId or record.status eq "PENDING"}'>

                    <%-- Delete button --%>
                    <c:set var="deleteBtn">
                        <tg:img src="/images/table-grid-delete-icon-disable.png" alt="" width="14" height="16" border="0" />
                    </c:set>
                    
                </c:if>
                <%-- 
                    *****************
                    Functionality end 
                    *****************
                --%>
                <c:if test='${not record.isActive}'>
                    <%-- Delete --%>
                    <c:if test='${not record.isActive or record.userId eq loggedInUserId or record.status eq "PENDING"}'>
                        <c:set var="deleteUrl" value="javascript:;" />
                        <c:set var="deleteIconUrl" 
                               value="/images/table-grid-delete-icon-disable.png" />
                    </c:if>
                </c:if>
                <td class="center-text"><c:out value="${editBtn}" escapeXml="false" /></td>
                <td class="center-text"><c:out value="${deleteBtn}" escapeXml="false" /></td>
            </tr>
        </c:forEach>
    </table>
    <!--Table Grid ends -->
    <div class="clearboth"></div>

    <div class="morebuttons">
        <a class="button floatRight marginLeft10" href="${pageContext.request.contextPath}/secure/company/inviteuserview.do" ><span class="red">Invite User</span></a></div><div class="clearboth"></div>

    <div class="clearboth marginTop40"></div>

    <!-- pagination starts -->

    <!-- pagination ends -->

    <input type="hidden" id="pageModel.prevSearchValue" />
    <input type="hidden" name="pageModel.sortBy" value="${genericListForm.pageModel.sortBy}">
    <input type="hidden" name="pageModel.ascending" value="${genericListForm.pageModel.ascending}" >
</form:form>
<div class="clearboth"></div>
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