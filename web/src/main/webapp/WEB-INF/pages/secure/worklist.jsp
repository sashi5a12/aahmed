<%@page import="com.netpace.device.utils.ServletContextAttributes"%>
<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@page import="org.apache.commons.lang.StringUtils, java.util.Calendar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" 
    var="JS_TYPE"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).PAGEMODEL" 
    var="PAGEMODEL"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_WORKLIST_COMPANY_NAME_TRUNCATION_LENGTH" 
    var="DEFAULT_WORKLIST_COMPANY_NAME_TRUNCATION_LENGTH"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_WORKLIST_PRODUCT_NAME_TRUNCATION_LENGTH" 
    var="DEFAULT_WORKLIST_PRODUCT_NAME_TRUNCATION_LENGTH"/>

<style>
    workflowButton{}
</style>
<spring:eval expression="T(com.netpace.device.utils.enums.WorkflowType).ProductWorkflow.getLabel()" var="ProductWorkflow"/>
<spring:eval expression="T(com.netpace.device.utils.enums.WorkflowType).PartnerWorkflow.getLabel()" var="PartnerWorkflow"/>

<% 
   Calendar cal = Calendar.getInstance();
   Integer workitemDelayInDays = (Integer) application.getAttribute(ServletContextAttributes.WORKITEM_DELAY_IN_DAYS);
   cal.add(Calendar.DAY_OF_YEAR, -workitemDelayInDays);
   pageContext.setAttribute("dateTwoWeeksAgo", cal.getTime()); 
%>

<!-- sucess message starts -->
<c:if test="${param.actionsExecuted}">
    <div class="success"><p>Work items executed successfully.</p></div>
</c:if>
<!-- sucess message ends -->

<form:form name="pageModelForm" action="worklist.do" method="post" modelAttribute="worklistForm" cssStyle="margin:0px; padding:0px; display:inline;">
    <h1 class="redheading floatLeft">Worklist</h1>

    <!-- Search Bar Starts-->
    <div class="searchBar">
        <a class="button floatRight marginLeft10" href="javascript:void(0)"><span class="red">Search</span></a>
        <form:select path="pageModel.searchBy" id="pageModel.searchBy" cssClass="selct floatRight marginLeft10" cssStyle="width:200px">
            <form:option value="">Search Criteria</form:option>
            <form:option value="workitem.workflow.company.name">Company Title</form:option>
            <form:option value="workitem.workflow.product.productName">Product Title</form:option>
        </form:select>
	<form:input path="pageModel.searchValue" cssClass="input floatRight" cssStyle="width:200px"/>
        <div class="clearboth"></div>
    </div>
    <!-- Search Bar Ends -->

    <!-- buttons starts -->
    <a class="button floatRight workflowButton" href="javascript:void(0);" onclick="executeActions();"><span class="red">Execute Action(s)</span></a> 
    <!-- buttons ends -->

    <div class="clearboth"></div>

    <!-- Table Grid Starts -->
    <table width="100%" border="0" class="datagrid-gray" cellpadding="0" cellspacing="0">
        <tr>
            <th>
                <tg:sort sortable="true" columnTitle="Work Item" columnField="workitem.displayTitle" pageModelSortBy="${worklistForm.pageModel.sortBy}" pageModelIsAscending="${worklistForm.pageModel.ascending}"/>
                <tg:filter columnTitle="Work Item" columnField="workitem.displayTitle" pageModel="${worklistForm.pageModel}" filterIndex="0" filterLabel="Partner Workflow" filterSeparators="8:Product Workflow"
                           filterValues="<%= VAPConstants.VAP_WORKFLOW_COMPANY_STEP_DISPLAY_TITLES+','+VAPConstants.VAP_WORKFLOW_PRODUCT_STEP_DISPLAY_TITLES%>" />
            </th>
            <th style="width:89px">
                <tg:sort sortable="true" columnTitle="Type" columnField="workitem.workflow.workflowType" pageModelSortBy="${worklistForm.pageModel.sortBy}" pageModelIsAscending="${worklistForm.pageModel.ascending}"/>
                <tg:filter columnTitle="Type" columnField="workflow.workflowType" pageModel="${worklistForm.pageModel}" filterIndex="1" filterLabel="Type"
                           filterValues="Partner Workflow,Product Workflow" />
            </th>
            <th style="width:100px">
                <tg:sort sortable="true" columnTitle="Company" columnField="company.name" pageModelSortBy="${worklistForm.pageModel.sortBy}" pageModelIsAscending="${worklistForm.pageModel.ascending}"/>
            </th>
            <th style="width:100px">
                <tg:sort sortable="true" columnTitle="Product" columnField="product.productName" pageModelSortBy="${worklistForm.pageModel.sortBy}" pageModelIsAscending="${worklistForm.pageModel.ascending}"/>
            </th>
            <th style="width:85px">
                <tg:sort sortable="true" columnTitle="Work Item <br> Start Date" columnField="workitem.startDate" pageModelSortBy="${worklistForm.pageModel.sortBy}" pageModelIsAscending="${worklistForm.pageModel.ascending}"/>
            </th>
            <th style="width:75px">
                <tg:sort sortable="true" columnTitle="Submit <br> Date" columnField="workflow.startDate" pageModelSortBy="${worklistForm.pageModel.sortBy}" pageModelIsAscending="${worklistForm.pageModel.ascending}"/>
            </th>
            <th style="width:72px">
                <tg:sort sortable="false" columnTitle="Workflow Action(s)" />
            </th>
        </tr>
        <c:forEach items="${worklistForm.pageModel.records}" var="workItem" varStatus="status">
            <tr>
                <td>
                    <input type="hidden" name="items[${status.index}].workflow.id" value="${workItem.workflow.id}"/>
                    <input type="hidden" name="items[${status.index}].title" value="${workItem.title}"/>
                    ${workItem.displayName}
                </td>
                <td>
                    ${workItem.workflow.workflowType}
                </td>
                <td>
                    <c:set var="truncatedCompanyName" value="${fn:substring(workItem.workflow.companyName, 0, DEFAULT_WORKLIST_COMPANY_NAME_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(workItem.workflow.companyName) gt DEFAULT_WORKLIST_COMPANY_NAME_TRUNCATION_LENGTH}">
                        <c:set var="truncatedCompanyName" value="${truncatedCompanyName}..." />
                    </c:if>
                    <a href="${pageContext.request.contextPath}/secure/company.do?companyid=${workItem.workflow.companyId}" title="${workItem.workflow.companyName}">${truncatedCompanyName}</a>
                </td>
                <td>
                    <c:if test="${workItem.workflow.workflowType==ProductWorkflow}">
                        <c:set var="truncatedProductName" value="${fn:substring(workItem.workflow.productName, 0, DEFAULT_WORKLIST_PRODUCT_NAME_TRUNCATION_LENGTH)}" />
                        <c:if test="${fn:length(workItem.workflow.productName) gt DEFAULT_WORKLIST_PRODUCT_NAME_TRUNCATION_LENGTH}">
                            <c:set var="truncatedProductName" value="${truncatedProductName}..." />
                        </c:if>
                        <a href="${pageContext.request.contextPath}/secure/product/edit.do?productId=${workItem.workflow.productId}" title="<c:out value="${workItem.workflow.productName}"/>"><c:out value="${truncatedProductName}"/></a>

                    </c:if>
                </td>
                <td <c:if test="${workItem.startDate lt dateTwoWeeksAgo}"> class="red-color" </c:if> >
                    <fmt:formatDate value="${workItem.startDate}" type="date" pattern="yyyy-MM-dd" />
                </td>
                <td>
                    <fmt:formatDate value="${workItem.workflow.startDate}" type="date" pattern="yyyy-MM-dd" />
                </td>
                <td>
                    <c:choose>
                        <c:when test="${workItem.requireInput == true}">
                            <c:if test="${workItem.workflow.workflowType==PartnerWorkflow}">
                                <a href="${pageContext.request.contextPath}/secure/company/process.do?companyid=${workItem.workflow.companyId}#${workItem.title}">Fill out data and execute</a>
                            </c:if>
                            <c:if test="${workItem.workflow.workflowType==ProductWorkflow}">
                                <a href="${pageContext.request.contextPath}/secure/product/processInfoEdit.do?productId=${workItem.workflow.productId}#${workItem.title}">Fill out data and execute</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <select class="smallselect" style="width: 100px;" name="items[${status.index}].decision">
                                <option value="">Choose Action</option>
                                <c:forEach items="${workItem.nextActions}" var="nextAction">
                                    <option value="${nextAction}">${nextAction}</option>
                                </c:forEach>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
    <!--Table Grid ends -->

    <div class="clearboth"></div>

    <!-- buttons starts -->
    <a class="button floatRight workflowButton" href="javascript:void(0);" onclick="executeActions();"><span class="red">Execute Action(s)</span></a> 
    <!-- buttons ends -->

    <div class="clearboth"></div>

    <!-- pagination starts -->
    <tg:paging pageModel="${worklistForm.pageModel}" />
    <!-- pagination ends -->

    <input type="hidden" id="pageModel.prevSearchValue" />
    <input type="hidden" name="pageModel.sortBy" value="${worklistForm.pageModel.sortBy}">
    <input type="hidden" name="pageModel.ascending" value="${worklistForm.pageModel.ascending}" >

</form:form>
<div class="clearboth"></div><br /><br /><br />
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

            function executeActions() {
                document.forms['worklistForm'].action = 'processworklist.do';
                $('a.workflowButton').removeAttr("onclick");
                document.forms['worklistForm'].submit();
            }
            
</script>
