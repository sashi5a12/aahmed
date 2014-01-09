<%@page import="org.apache.commons.lang.StringUtils, com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" 
    var="JS_TYPE"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).PAGEMODEL" 
    var="PAGEMODEL"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).ROLE_SUPER_ADMIN" 
    var="ROLE_SUPER_ADMIN" />

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).ROLE_VERIZON_ADMIN" 
    var="ROLE_VERIZON_SYSTEM_ADMIN" />

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_PARTNER_MANAGE_PARTNER_NAME_TRUNCATION_LENGTH" 
    var="DEFAULT_PARTNER_MANAGE_PARTNER_NAME_TRUNCATION_LENGTH" />

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_PARTNER_MANAGE_PRTNER_POC_TRUNCATION_LENGTH" 
    var="DEFAULT_PARTNER_MANAGE_PRTNER_POC_TRUNCATION_LENGTH" />

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_PARTNER_MANAGE_WORKFLOW_PHASE_STATUS_TRUNCATION_LENGTH" 
    var="DEFAULT_PARTNER_MANAGE_WORKFLOW_PHASE_STATUS_TRUNCATION_LENGTH" />

<c:if test="${not empty COMPANY_DELETED}">
    <div class="success">
        <p>
            <fmt:message key="msg.company.delete" />
        </p>
    </div>
</c:if>

<h1 class="redheading floatLeft">Manage Partners</h1>
<!-- Search Bar Starts-->
<form:form name="pageModelForm" action="managePartners.do" method="post" modelAttribute="listForm">

    <form:hidden path="pageModel.sortBy" />
    <form:hidden path="pageModel.ascending" />

    <!-- Search Bar Starts-->
    <div class="searchBar">
        <a class="button floatRight marginLeft10" href="javascript:;"><span class="red">Search</span></a> 
        <form:select path="pageModel.searchBy"  class="selct floatRight marginLeft10"  style="width:200px">
            <form:option value="">Search Criteria</form:option>
            <form:option value="c.name">Partner Name</form:option>
        </form:select> 
        <form:input path="pageModel.searchValue" cssClass="input floatRight" cssStyle="width:200px"/>
        <div class="clearboth"></div>
    </div>
    <!-- Search Bar Ends -->

    <!-- Table Grid Starts -->
    <table width="100%" border="0" class="datagrid-gray" cellpadding="0" cellspacing="0">
        <tr>
            <th style="width:180px">
                <tg:sort sortable="true" columnTitle="Partner Name" columnField="c.name" pageModelSortBy="${listForm.pageModel.sortBy}" pageModelIsAscending="${listForm.pageModel.ascending}"/>
            </th>
            <th style="width:150px">
                <tg:sort sortable="true" columnTitle="Partner POC" columnField="user.fullName" pageModelSortBy="${listForm.pageModel.sortBy}" pageModelIsAscending="${listForm.pageModel.ascending}"/>
            </th>
            <th style="width:110px" >
                <tg:sort sortable="true" columnTitle="Created Date" columnField="c.createdDate" pageModelSortBy="${listForm.pageModel.sortBy}" pageModelIsAscending="${listForm.pageModel.ascending}"/>
            </th>
            <th style="width:130px">
                <tg:sort sortable="false" columnTitle="Workflow Phase - Status" />
                <tg:filter columnTitle="Workflow Phase" columnField="wi.title" pageModel="${listForm.pageModel}" filterIndex="0" filterLabel="Workflow Phase"
                           filterValues="<%=VAPConstants.VAP_WORKFLOW_COMPANY_STEP_DISPLAY_TITLES%>" />

            </th>
            <th style="width:61px">Suspend</th>
            <th class="center-text" style="width:37px" >Edit</th>
            <th class="center-text" style="width:37px">Delete</th>
        </tr>
        <c:forEach var="vo" items="${listForm.pageModel.records}">

            <%-- edit --%>
            <c:set var="editUrl" 
                   value="${pageContext.request.contextPath}/secure/company.do?companyid=${vo.id}" />

            <c:set var="editIconUrl" 
                   value="/images/table-grid-edit-icon.png" />

            <%-- delete button --%>
            <c:set var="deleteBtn">
                <tg:img src="/images/table-grid-delete-icon-disable.png" alt="" 
                        width="14" height="16" border="0" />
            </c:set>
            
            <%-- delete button tooltip --%>
            <fmt:message 
                key="tooltip.insufficient.delete.rights" 
                var="tooltipInsufficientDeleteRights" />
            
            <sec:authorize ifAnyGranted="${ROLE_SUPER_ADMIN},${ROLE_VERIZON_SYSTEM_ADMIN}">
                <%-- delete --%>
                <c:set var="tooltipInsufficientDeleteRights" value="" />
                <c:set var="deleteBtn">
                    <a href="${pageContext.request.contextPath}/secure/deletecompany.do?companyid=${vo.id}" 
                       title="${tooltipInsufficientDeleteRights}"
                       onclick="return confirm('<fmt:message key="msg.company.delete.confirm" />');"><tg:img 
                           src="/images/table-grid-delete-icon.png" alt="" width="14" height="16" border="0" /></a>
                </c:set>
            </sec:authorize>
            <tr>
                <td>
                    <c:set var="truncatedPartnerName" value="${fn:substring(vo.name, 0, DEFAULT_PARTNER_MANAGE_PARTNER_NAME_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(vo.name) gt DEFAULT_PARTNER_MANAGE_PARTNER_NAME_TRUNCATION_LENGTH}">
                        <c:set var="truncatedPartnerName" value="${truncatedPartnerName}..." />
                    </c:if>
                    <a href="${pageContext.request.contextPath}/secure/company.do?companyid=${vo.id}" title="${vo.name}"><c:out value="${truncatedPartnerName}" /></a>
                </td>
                <td>
                    <c:set var="truncatedPartnerPOC" value="${fn:substring(vo.poc, 0, DEFAULT_PARTNER_MANAGE_PRTNER_POC_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(vo.poc) gt DEFAULT_PARTNER_MANAGE_PRTNER_POC_TRUNCATION_LENGTH}">
                        <c:set var="truncatedPartnerPOC" >
                            <label class="truncated-text" title="${vo.poc}">${truncatedPartnerPOC}...</label>
                        </c:set>
                    </c:if>
                    <c:out value="${truncatedPartnerPOC}" escapeXml="false" />
                </td>
                <td><fmt:formatDate value="${vo.createdDate}" pattern="MM-dd-yyyy"/></td>
                <td>
                    <c:set var="truncatedPartnerWorkflowPhaseStatus" value="${fn:substring(vo.workflowSteps, 0, DEFAULT_PARTNER_MANAGE_WORKFLOW_PHASE_STATUS_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(vo.workflowSteps) gt DEFAULT_PARTNER_MANAGE_WORKFLOW_PHASE_STATUS_TRUNCATION_LENGTH}">
                        <c:set var="truncatedPartnerWorkflowPhaseStatus" >
                            <label class="truncated-text" title="${vo.workflowSteps}">${truncatedPartnerWorkflowPhaseStatus}...</label>
                        </c:set>
                        <c:set var="truncatedPartnerWorkflowPhaseStatus" value="${fn:replace(truncatedPartnerWorkflowPhaseStatus, '<br/> ', ', ')}" />
                    </c:if>
                    
                    <c:out value="${truncatedPartnerWorkflowPhaseStatus}" escapeXml="false" />
                </td>
                <td>
                	<c:if test="${vo.suspend eq true }">Yes</c:if>
                </td>
                <td class="center-text"><a href="${editUrl}"><tg:img src="${editIconUrl}" alt="" width="13" height="14" border="0" /></a></td>
                <td class="center-text"><c:out value="${deleteBtn}" escapeXml="false" /></td>
            </tr>
        </c:forEach>
    </table>
    <!--Table Grid ends -->
    <div class="clearboth"></div>
    <div class="clearboth marginTop40"></div>
    <tg:paging pageModel="${listForm.pageModel}" />
    <div class="clearboth"></div>
    <input type="hidden" id="pageModel.prevSearchValue" />
</form:form>
<tg:includestatic type="${JS_TYPE}" paramName="${PAGEMODEL}" />
<%--
<script type="text/javascript">
    var defaultSearchString = "Search By Partner Name";
    $(".searchBar a").click(function(e) {
        e.preventDefault();
        if ($("#searchValue").val() == null || $("#searchValue").val() == '' || $("#searchValue").val() == defaultSearchString) {
            alert("Please enter value some value for searching.");
        }
        else {
            $("#searchValue").trigger('focus');
            $('form').submit();
        }
        return false;
    });
    $(".datagrid-gray a").click(function(e) {
        e.preventDefault();
        if($(this).attr("id")=='filterIcon' || $(this).attr("id")=='filterClose'){
            return false;
        }
        $('#sortBy').val($(this).attr('rel'));
        if ($(this).siblings().length == 0) {
            $('#sortOrder').val("asc");
        }
        else {
            sortClass = $("#sortDiv").attr("class");
            if (sortClass == null) {
                $('#sortOrder').val("asc");
            }
            else if (sortClass == 'sort-ascending') {
                $('#sortOrder').val("desc");
            }
            else {
                $('#sortOrder').val("asc");
            }
        }
        $('#searchValue').val(null);
        $('form').submit();
        return false;
    });
    $("#searchValue").focus(function(e) {
        if ($("#searchValue").val() == defaultSearchString) {
            $("#searchValue").val("");
        }
    });
    $("#searchValue").blur(function(e) {
        if ($("#searchValue").val() == null || $("#searchValue").val() == '') {
            $("#searchValue").val(defaultSearchString);
        }
    });
    $(document).ready(function() {
        if ($("#searchValue").val() == null || $("#searchValue").val() == '') {
            $("#searchValue").val(defaultSearchString);
        }
    });
    $("#filterIcon").click(function(e) {
        e.preventDefault();
        $(".filter-popup").toggle();
        return false;
    });
    $("#filterClose").click(function(e) {
        e.preventDefault();
        $(".filter-popup").toggle();
        return false;
    });
    $(".filter-popup-bottom a").click(function(e){
        e.preventDefault();
        $('#sortOrder').val(null);
        $('#sortBy').val(null);
        $('#searchValue').val(null);
        $('form').submit();
        return false;
    });
</script>
--%>