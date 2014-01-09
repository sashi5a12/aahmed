<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<a name="basicInfo" id="basicInfo"></a>
<div class="product-backtotop">
	<a href="#top" class="product-nav">Top</a>
</div>
<sec:authentication  property="principal" var="principal" />
<div class="grayBoxLayouts">
	<h1><fmt:message key="productVO.section.name.basicInfo"/></h1>
	<div class="twoColmnLayout">
		<div class="columnOne">
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.productName"/></label>
			<c:choose>
				<c:when test="${principal.partner eq 'true'}">
					<form:input path="productName" cssClass="input" size="250" maxlength="250" />
				</c:when>
				<c:otherwise>
					<div class="label-value"><c:out value="${productVO.productName}"></c:out></div>
					<form:hidden path="productName"/>
				</c:otherwise>
			</c:choose> 			
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.description"/></label>
			<form:textarea path="description" cssClass="textfield font width290 height215"></form:textarea>
			<tg:charsleft fieldName="description" charsLimit="2000" />			
			<div class="marginTop5"></div>
			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.modelNumber"/></label> 
			<form:input path="modelNumber" cssClass="input" size="250" maxlength="250"/>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.productCategory"/></label> 
			<span class="columnOne"> 
				<form:select cssClass="selct" path="productCategory">
					<form:option value="" label="--- Select ---" />
					<form:options items="${populatedFormElements['categoryList']}" />
				</form:select> 
			</span>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.partNumber"/></label> 
			<form:input path="partNumber" cssClass="input" size="250" maxlength="250"/>
			<div id="hide_for_concept">
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.sampleTracking"/></label> 
			<form:input path="sampleTracking" cssClass="input" size="30" maxlength="30"/>
			</div>
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.availabilityDate"/></label> 
			<form:input path="availabilityDate" cssClass="input" id="availabilityDate" />
			<div class="clearboth"></div>
			<div class="redstar">*</div>
			<label class="inputlabel"><fmt:message key="productVO.vzwExclusive"/></label>
			<table width="200" border="0" cellpadding="0" cellspacing="0" style="float:left;">
				<tr>
					<td width="81" valign="bottom">
						<form:radiobutton path="vzwExclusive" value="Yes" cssClass="checkbox"/> 
						<label class="checkboxlabel font13" style="margin-left:4px; margin-top:2px;">Yes</label>
					</td>
					<td width="119" valign="bottom">
						<form:radiobutton path="vzwExclusive" value="No" cssClass="checkbox"/>
						<label class="checkboxlabel font13" style="margin-left:4px; margin-top:2px;">No</label>
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