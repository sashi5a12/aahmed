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
			<label class="inputlabel"><fmt:message key="productVO.name"/></label> 
			<form:input path="name" cssClass="input" maxlength="100"/>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.emailAddress"/></label> 
			<form:input path="emailAddress" cssClass="input" maxlength="250"/>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.phone"/></label> 
			<form:input path="phone" cssClass="input" maxlength="25"/>
			<div class="clearboth"></div>
			<label class="inputlabel"><fmt:message key="productVO.mobile"/></label> 
			<form:input path="mobile" cssClass="input" maxlength="25"/>
			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.streetAddress"/></label> 
			<form:input path="streetAddress" cssClass="input" maxlength="500"/>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.city"/></label> 
			<form:input path="city" cssClass="input" maxlength="100"/>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.postalCode"/></label> 
			<form:input path="postalCode" cssClass="input" maxlength="10"/>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.state"/></label> 
			<form:input path="state" cssClass="input" maxlength="250"/>
			<div class="clearboth"></div>			
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.country"/></label> <label for="select"></label>
			<form:select cssClass="selct" path="country" id="country">
                            <form:option value="" label="--- Select ---" />
                            <form:options items="${populatedFormElements['countryList']}" />
			</form:select>
			<div class="clearboth"></div>
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
<br />