<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<a name="productDetails" id="productDetails"></a>
<div class="product-backtotop">
	<a href="#top" class="product-nav">Top</a>
</div>
<div class="grayBoxLayouts">
	<h1><fmt:message key="productVO.section.name.productDetails"/></h1>
	<div class="twoColmnLayout">
		<div class="columnOne">
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.targetSegment"/></label>
			<form:textarea path="targetSegment" cssClass="textfield font width290 height150"></form:textarea>
			<tg:charsleft fieldName="targetSegment" charsLimit="1000" />
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.positioningStatement"/></label>
			<form:textarea path="positioningStatement" cssClass="textfield font width290 height150"></form:textarea>
			<tg:charsleft fieldName="positioningStatement" charsLimit="1000" />
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.mainCompetition"/></label> 
			<label class="inputlabel" style="margin-top:0px;">Who are your main competitors?</label>
			<form:textarea path="mainCompetition" cssClass="textfield font width290 height150"></form:textarea>
			<tg:charsleft fieldName="mainCompetition" charsLimit="1000" />
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.deviceNeed"/></label> 
			<span class="columnOne"> 
				<form:textarea path="deviceNeed" cssClass="textfield font width290 height150"></form:textarea>
				<tg:charsleft fieldName="deviceNeed" charsLimit="1000" />
			</span>
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.uniqueFunctionality"/></label>
			<span class="columnOne"> 
				<form:textarea path="uniqueFunctionality" cssClass="textfield font width290 height150"></form:textarea>
				<tg:charsleft fieldName="uniqueFunctionality" charsLimit="1000" />
			</span>
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
<br />