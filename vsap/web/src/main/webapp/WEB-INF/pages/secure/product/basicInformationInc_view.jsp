<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<a name="basicInfo" id="basicInfo"></a>
<div class="product-backtotop">
	<a href="#top" class="product-nav">Top</a>
</div>
<div class="grayBoxLayouts">
	<h1><fmt:message key="productVO.section.name.basicInfo"/></h1>
	<div class="twoColmnLayout">
		<div class="columnOne">
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.productName"/></label> 
			<div class="label-value"><c:out value="${productVO.productName}"></c:out></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.description"/></label>
			<div class="label-value"><c:out value="${productVO.description}"></c:out></div>
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.modelNumber"/></label> 
			<div class="label-value"><c:out value="${productVO.modelNumber}"></c:out></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.productCategory"/></label> 
			<span class="columnOne"> 
				<div class="label-value"><c:out value="${productVO.productCategory}"></c:out></div>
			</span>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.partNumber"/></label> 
			<div class="label-value"><c:out value="${productVO.partNumber}"></c:out></div>
			<div id="hide_for_concept">
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.sampleTracking"/></label> 
			<div class="label-value"><c:out value="${productVO.sampleTracking}"></c:out></div>
			</div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.availabilityDate"/></label> 
			<div class="label-value"><c:out value="${productVO.availabilityDate}"></c:out></div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.vzwExclusive"/></label>
			<table width="200" border="0" cellpadding="0" cellspacing="0" style="float:left;">
				<tr>
					<td width="81" valign="bottom">
						<c:out value="${productVO.vzwExclusive}"></c:out>
						<!-- <label class="checkboxlabel font13" style="margin-left:4px; margin-top:2px;">Yes</label> -->
					</td>
					<td width="119" valign="bottom">
						&nbsp;
						<!-- <label class="checkboxlabel font13" style="margin-left:4px; margin-top:2px;">No</label> -->
					</td>
				</tr>
			</table>
			<div class="clearboth"></div>
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
<br />