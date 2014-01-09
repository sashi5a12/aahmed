<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<a name="productDetails" id="productDetails"></a>
<div class="product-backtotop">
	<a href="#top" class="product-nav">Top</a>
</div>
<div class="grayBoxLayouts">
	<h1><fmt:message key="productVO.section.name.productDetails"/></h1>
	<div class="twoColmnLayout">
		<div class="columnOne">
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.targetSegment"/></label>
			<div class="label-value"><c:out value="${productVO.targetSegment}"/></div>
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.positioningStatement"/></label>
			<div class="label-value"><c:out value="${productVO.positioningStatement}"/></div>
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.mainCompetition"/></label> 			
			<label class="inputlabelbold" style="margin-top:0px;">Who are your main competitors?</label>
			<div class="label-value"><c:out value="${productVO.mainCompetition}"/></div>
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.deviceNeed"/></label> 
			<span class="columnOne"> 
				<div class="label-value"><c:out value="${productVO.deviceNeed}"/></div>
			</span>
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.uniqueFunctionality"/></label>
			<span class="columnOne"> 
				<div class="label-value"><c:out value="${productVO.uniqueFunctionality}"/></div>
			</span>
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
<br />