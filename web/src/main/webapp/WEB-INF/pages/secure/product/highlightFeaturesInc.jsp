<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<a name="highlightedFeatures" id="highlightedFeatures"></a>
<div class="product-backtotop">
	<a href="#top" class="product-nav">Top</a>
</div>
<div class="grayBoxLayouts">
	<h1><fmt:message key="productVO.section.name.highlightedFeatures"/></h1>
	<div class="twoColmnLayout">
		<div class="columnOne">
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.paltformSupported"/></label> 
			<form:input path="paltformSupported" cssClass="input" size="250" maxlength="250"/>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.cloudSupported"/></label>
			<table width="200" border="0" cellpadding="0" cellspacing="0" style="float:left;">
				<tr>
					<td width="81" valign="bottom">
						<form:radiobutton path="cloudSupported" value="Yes" cssClass="checkbox"/> 
						<label class="checkboxlabel font13" style="margin-left:4px; margin-top:2px;">Yes</label>
					</td>
					<td width="119" valign="bottom">
						<form:radiobutton path="cloudSupported" value="No" cssClass="checkbox"/> 
						<label class="checkboxlabel font13" style="margin-left:4px; margin-top:2px;">No</label>
					</td>
				</tr>
			</table>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.productDimensions"/></label> 
			<form:input path="productDimensions" cssClass="input" maxlength="250"/>
			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.connectivityType"/></label> 
			<form:input path="connectivityType" cssClass="input" maxlength="250"/>
			<div class="clearboth"></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.formRequirements"/></label> 
			<form:input path="formRequirements" cssClass="input" maxlength="250"/>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.itemIncluded"/></label>
			<form:input path="itemIncluded" cssClass="input" maxlength="1000"/>
			<div class="clearboth"></div>
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
<br />