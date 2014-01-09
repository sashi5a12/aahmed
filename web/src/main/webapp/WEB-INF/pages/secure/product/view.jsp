<%@page import="com.netpace.device.utils.enums.ProductSubmissionType"%>
<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>



<spring:eval expression="T(com.netpace.device.utils.VAPConstants).CSS_TYPE" var="CSS_TYPE"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" var="JS_TYPE"/>

<spring:eval expression="T(com.netpace.device.utils.VAPConstants).PRODUCT" var="PRODUCT"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).FINEUPLOAD" var="FINEUPLOAD"/>

<tg:includestatic type="${JS_TYPE}" paramName="${PRODUCT}" />
<tg:includestatic type="${JS_TYPE}" paramName="${FINEUPLOAD}" />
<tg:includestatic type="${CSS_TYPE}" paramName="${FINEUPLOAD}" />

<form:form name="productForm" id="productForm" action="create.do" method="POST" commandName="productVO" >

<h1 class="redheading"><fmt:message key="productVO.view.title"/></h1>

<div class="emptyBoxLayouts" style="padding:0px; margin-top:-10px; margin-bottom:10px;">
	<div class="threeColmnLayout">
		<div class="columnOne">
			<label class="inputlabelbold"><fmt:message key="companyForm.companyName"/></label><span class="label-value"><c:out value="${productVO.companyName}"></c:out></span>
			<div class="clearboth"></div>
		</div>
		<div class="columnTwo" style="width:500px">
			<label class="inputlabelbold"><fmt:message key="productVO.workflow.status.label"/></label><span class="label-value"><c:out escapeXml="false" value="${productVO.worklflowStep}"/></span>
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
<c:if test="${productVO.productStatus ne 'Saved' }">
<div class="clearboth"></div>
<a href="#" class="bigTab"><span><span><fmt:message key="label.tab.one"/></span></span></a>
<a href="#" class="bigTabGray"><span><span><fmt:message key="label.tab.two"/></span></span></a> 
<div class="clearboth"></div> 
</c:if>
<div class="anchor-nav">
	<ul>
		<li><a href="#basicInfo"><fmt:message key="productVO.section.name.basicInfo"/></a></li>
		<li><a href="#productDetails"><fmt:message key="productVO.section.name.productDetails"/></a></li>
<c:if test="${productVO.productType eq 'Product (existing physical product)'}">
		<li id="hide_for_concept"><a href="#highlightedFeatures"><fmt:message key="productVO.section.name.highlightedFeatures"/></a></li>
		<li id="hide_for_concept"><a href="#productContact"><fmt:message key="productVO.section.name.productContact"/></a></li>
</c:if>		
		<li><a href="#images"><fmt:message key="productVO.section.name.images"/></a></li>
		<li><a href="#video"><fmt:message key="productVO.section.name.video"/></a></li>
		<li><a href="#documents"><fmt:message key="productVO.section.name.documents"/></a></li>
	</ul>
</div>
<form:hidden path="id" id="id"/>

<div class="grayBoxLayouts">
	<div class="twoColmnLayout width500">
		<div class="columnOne">
			<label class="inputlabelbold width400"><fmt:message key="productVO.productType"/></label> 
			<c:out value="${productVO.productType}"></c:out>
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
<br />

<c:import url="basicInformationInc_view.jsp"/>
<c:import url="productDetailInc_view.jsp"/>
<c:if test="${productVO.productType eq 'Product (existing physical product)'}">
	<c:import url="highlightFeaturesInc_view.jsp"/>
	<c:import url="productContactInc_view.jsp"/>
</c:if>
<c:import url="productImagesInc_view.jsp"/>
<c:import url="appImagesInc_view.jsp"/>
<c:import url="videosInc_view.jsp"/>
<c:import url="documentsInc_view.jsp"/>
</form:form>
<a class="button floatRight marginTop20 marginLeft10" href="#" id='btn_cancel'><span class="gray"><fmt:message key="btn.cancel"/></span></a>

<script type="text/javascript">
    $("#btn_cancel").click(function(e) {
        e.preventDefault();
        window.location='list.do';
        return false;
    });   
    $("a.bigTabGray").click(function(e) {
        e.preventDefault();
        window.location='processInfoEdit.do?productId='+$('#id').val();
        return false;
    });   
    $(function() {
	    <c:if test="${productVO.productType eq 'Concept (no physical product available at this time)'}">
			$("div#hide_for_concept").hide();
			$("div#attachments_require_for_product").hide();
		</c:if>
	}); 
</script>