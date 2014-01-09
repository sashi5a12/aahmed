<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<a name="productContact" id="productContact"></a>
<div class="product-backtotop">
	<a href="#top" class="product-nav">Top</a>
</div>
<div class="grayBoxLayouts">
	<h1><fmt:message key="productVO.section.name.productContact"/></h1>
	<div class="twoColmnLayout">
		<div class="columnOne">
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.name"/></label> 
			<div class="label-value"><c:out value="${productVO.name}"/></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.emailAddress"/></label> 
			<div class="label-value"><c:out value="${productVO.emailAddress}"/></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.phone"/></label> 
			<div class="label-value"><c:out value="${productVO.phone}"/></div>
			<div class="clearboth"></div>
			<label class="inputlabelbold"><fmt:message key="productVO.mobile"/></label> 
			<div class="label-value"><c:out value="${productVO.mobile}"/></div>
			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.streetAddress"/></label> 
			<div class="label-value"><c:out value="${productVO.streetAddress}"/></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.city"/></label> 
			<div class="label-value"><c:out value="${productVO.city}"/></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.postalCode"/></label> 
			<div class="label-value"><c:out value="${productVO.postalCode}"/></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.state"/></label> 
			<div class="label-value"><c:out value="${productVO.state}"/></div>
			<div class="clearboth"></div>			
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.country"/></label> <label for="select"></label>
			<div class="label-value"><c:out value="${productVO.country}"/></div>
			<div class="clearboth"></div>
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
<br />