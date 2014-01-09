<%@page import="org.apache.commons.lang.StringUtils"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags"%>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" 
    var="JS_TYPE"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).PAGEMODEL" 
    var="PAGEMODEL"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_PRODUCT_PRODUCT_NAME_TRUNCATION_LENGTH" 
    var="DEFAULT_PRODUCT_PRODUCT_NAME_TRUNCATION_LENGTH"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_PRODUCT_COMPANY_NAME_TRUNCATION_LENGTH" 
    var="DEFAULT_PRODUCT_COMPANY_NAME_TRUNCATION_LENGTH"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_PRODUCT_PRODUCT_TYPE_TRUNCATION_LENGTH" 
    var="DEFAULT_PRODUCT_PRODUCT_TYPE_TRUNCATION_LENGTH"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_PRODUCT_CATEGORY_TRUNCATION_LENGTH" 
    var="DEFAULT_PRODUCT_CATEGORY_TRUNCATION_LENGTH"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_PRODUCT_WORKFLOW_PHASE_STATUS_TRUNCATION_LENGTH" 
    var="DEFAULT_PRODUCT_WORKFLOW_PHASE_STATUS_TRUNCATION_LENGTH"/>

<form:form name="pageModelForm" id="pageModelForm" action="list.do" method="POST" commandName="listForm">

    <form:hidden path="pageModel.sortBy" />
    <form:hidden path="pageModel.ascending" />

	<spring:hasBindErrors name="listForm">
		<div class="error">
			<ul>
				<form:errors path="*" element="li" delimiter="</li><li>" id="" />
			</ul>
		</div>
	</spring:hasBindErrors>
	<c:if test="${not empty SUCCESS_MESSAGE}">
		<div class="success">
			<fmt:message key="${SUCCESS_MESSAGE}" />
		</div>
	</c:if>	
	<h1 class="redheading floatLeft">Manage Products</h1>

	<!-- Search Bar Starts-->
	<div class="searchBar">
		<a class="button floatRight marginLeft10" href="javascript:;"><span class="red">Search</span></a> 
		<form:select path="pageModel.searchBy"  class="selct floatRight marginLeft10"  style="width:200px">
			<form:option value="">Search Criteria</form:option>
			<form:option value="vp.productName">Product Name</form:option>
			<c:if test="${displayCompanyColumn eq true }">
			<form:option value="vp.company.name">Company Name</form:option>
			</c:if>
			<form:option value="vp.productType">Product Type</form:option>			
		</form:select> 
		<form:input path="pageModel.searchValue" cssClass="input floatRight" cssStyle="width:200px"/>
		<div class="clearboth"></div>
	</div>
	<!-- Search Bar Ends -->
	
	<c:if test="${displayCompanyColumn eq false }">
	<div class="floatRight">
		<a class="button large floatRight marginLeft10" href="${pageContext.request.contextPath}/secure/product/new.do">
			<span class="red" style="padding: 0 10px;"><tg:img src="/images/device.png" width="15" height="16" border="0" />Submit Concept / Product</span>
		</a>
	</div>
	<div class="clearboth"></div>
	</c:if>
	<!-- Table Grid Starts -->
	<table width="100%" border="0" class="datagrid-gray" cellpadding="0" cellspacing="0">       
		<tr>
			<th>
				<tg:sort sortable="true" columnTitle="Product Name" columnField="vp.productName" pageModelSortBy="${listForm.pageModel.sortBy}" pageModelIsAscending="${listForm.pageModel.ascending}"/>
			</th>
			<c:if test="${displayCompanyColumn eq true }">
			<th style="width:110px">
				<tg:sort sortable="true" columnTitle="Partner Name" columnField="vp.company.name" pageModelSortBy="${listForm.pageModel.sortBy}" pageModelIsAscending="${listForm.pageModel.ascending}"/>
			</th>
			</c:if>
			<th style="width:56px">
				<tg:sort sortable="true" columnTitle="Type" columnField="vp.productType" pageModelSortBy="${listForm.pageModel.sortBy}" pageModelIsAscending="${listForm.pageModel.ascending}"/>
			</th>
			<th style="width:86px">
				<tg:sort sortable="true" columnTitle="Category" columnField="vp.productCategory" pageModelSortBy="${listForm.pageModel.sortBy}" pageModelIsAscending="${listForm.pageModel.ascending}"/>
             <tg:filter columnTitle="Category" columnField="vp.productCategory" pageModel="${listForm.pageModel}" filterIndex="0" filterValues="${categories}" filterLabel="Category" />
			</th>
			<th style="width:96px">
				<tg:sort sortable="true" columnTitle="Submit Date" columnField="vp.submittedDate" pageModelSortBy="${listForm.pageModel.sortBy}" pageModelIsAscending="${listForm.pageModel.ascending}"/>
			</th>
			<th style="width:125px">				
				<tg:sort sortable="false" columnTitle="Workflow Phase-Status" />
    <tg:filter columnTitle="Workflow Phase" columnField="vp.productCategory" pageModel="${listForm.pageModel}" filterIndex="1" filterLabel="Workflow Phase"
				filterValues="Device Marketing Review,Device Marketing Review RFI,Device Marketing Review Denied,Export Compliance,Product Info Upload,Export Compliance Review,Export Compliance Review RFI,Export Compliance Review Denied,Requirements Group Review,Requirements Group Review RFI,Requirements Group Review Denied,Device Compliance Review,Device Compliance Review RFI,Device Compliance Review Denied,Device Compliance Review EOT,Device Compliance Review EOT RFI,Device Compliance Review EOT Denied,Upload PDF of Sample Product,Device Marketing Final Review,Device Marketing Final Review RFI,Device Marketing Final Review Denied,Approved" />				
			</th>
			<th class="center-text" style="width:37px">Edit</th>
			<th class="center-text" style="width:37px">Delete</th>
		</tr>
		<c:forEach var="vo" items="${listForm.pageModel.records}">
            <tr>
                <td>
                    <c:set var="truncatedProductName" value="${fn:substring(vo.productName, 0, DEFAULT_PRODUCT_PRODUCT_NAME_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(vo.productName) gt DEFAULT_PRODUCT_PRODUCT_NAME_TRUNCATION_LENGTH}">
                        <c:set var="truncatedProductName" value="${truncatedProductName}..." />
                    </c:if>
                    <a href='view.do?productId=${vo.productId}' title='<c:out value="${vo.productName}"/>'><c:out value="${truncatedProductName}" /></a>
                </td>
                <c:if test="${displayCompanyColumn eq true }">
                <td>
                    <c:set var="truncatedCompanyName" value="${fn:substring(vo.companyName, 0, DEFAULT_PRODUCT_COMPANY_NAME_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(vo.companyName) gt DEFAULT_PRODUCT_COMPANY_NAME_TRUNCATION_LENGTH}">
                        <c:set var="truncatedCompanyName" >
                            <label class="truncated-text" title="${vo.companyName}">${truncatedCompanyName}...</label>
                        </c:set>
                    </c:if>
                    <c:out value="${truncatedCompanyName}" escapeXml="false" />
                </td>
                </c:if>
                <td><c:out value="${vo.productType}"/></td>
                <td>
                    <c:set var="truncatedCategory" value="${fn:substring(vo.productCategory, 0, DEFAULT_PRODUCT_CATEGORY_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(vo.productCategory) gt DEFAULT_PRODUCT_CATEGORY_TRUNCATION_LENGTH}">
                        <c:set var="truncatedCategory" >
                            <label class="truncated-text" title="${vo.productCategory}">${truncatedCategory}...</label>
                        </c:set>
                    </c:if>
                    
                    <c:out value="${truncatedCategory}" escapeXml="false" />
                </td>
                <td><fmt:formatDate value="${vo.submittedDate}" pattern="MM-dd-yyyy"/></td>
                <td>
                    <c:set var="truncatedProductWorkflowPhaseStatus" value="${fn:substring(vo.workFlowSteps, 0, DEFAULT_PRODUCT_WORKFLOW_PHASE_STATUS_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(vo.workFlowSteps) gt DEFAULT_PRODUCT_WORKFLOW_PHASE_STATUS_TRUNCATION_LENGTH}">
                        <c:set var="truncatedProductWorkflowPhaseStatus" >
                            <label class="truncated-text" title="${vo.workFlowSteps}">${truncatedProductWorkflowPhaseStatus}...</label>
                        </c:set>
                        <c:set var="truncatedProductWorkflowPhaseStatus" value="${fn:replace(truncatedProductWorkflowPhaseStatus, '<br/> ', ' ')}" />
                    </c:if>
                    <c:out value="${truncatedProductWorkflowPhaseStatus}" escapeXml="false"/>
                </td>
                <td class="center-text">
                    <c:choose>
                	<c:when test="${vo.allowEdit eq true}">
                            <a rel="${vo.productId}" class="editProduct" href="#"><tg:img src="/images/table-grid-edit-icon.png" alt="" width="13" height="14" border="0" /></a>
                	</c:when>
                	<c:otherwise>
                            <tg:img src="/images/table-grid-edit-icon-disable.png" width="13" height="14" border="0" />
                	</c:otherwise>
                    </c:choose>                
                </td>
                <td class="center-text">
                    <c:choose>
                	<c:when test="${vo.allowDelete eq true}">
                            <a rel="${vo.productId}" class="deleteProduct" href="#"><tg:img src="/images/table-grid-delete-icon.png" width="14" height="16" border="0" /></a>
                	</c:when>
                	<c:otherwise>
                            <tg:img src="/images/table-grid-delete-icon-disable.png" width="14" height="16" border="0" />
                	</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
	</table>
	<!--Table Grid ends -->
	<div class="clearboth"></div>

	<c:if test="${displayCompanyColumn eq false }">
	<div class="morebuttons">
		<a class="button large floatRight marginLeft10" href="${pageContext.request.contextPath}/secure/product/new.do">
			<span class="red" style="padding: 0 10px;"><tg:img src="/images/device.png" width="15" height="16" border="0" />Submit Concept / Product</span>
		</a>
	</div>
	<div class="clearboth"></div>
	</c:if>
	
	<div class="clearboth marginTop40"></div>

	<tg:paging pageModel="${listForm.pageModel}" />
	<div class="clearboth"></div>
        <input type="hidden" id="pageModel.prevSearchValue" />
</form:form>
<tg:includestatic type="${JS_TYPE}" paramName="${PAGEMODEL}" />
<script type="text/javascript">
	$("a.deleteProduct").click(function(e) {
		e.preventDefault();
		if (confirm('Are you sure you want to delete this product?')) { 
        	$('form').attr('action', 'delete.do?productId='+$(this).attr('rel'));
        	$('form').submit();
        }
        return false;
    });
    $("a.editProduct").click(function(e) {
    	e.preventDefault();
        $('form').attr('action', 'edit.do?productId='+$(this).attr('rel'));
        $('form').submit();
        return false;
    });
    
</script>