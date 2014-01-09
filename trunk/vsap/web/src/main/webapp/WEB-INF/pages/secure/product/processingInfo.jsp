<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<spring:eval expression="T(com.netpace.device.utils.VAPConstants).CSS_TYPE" var="CSS_TYPE"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" var="JS_TYPE"/>

<spring:eval expression="T(com.netpace.device.utils.VAPConstants).PRODUCT" var="PRODUCT"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).FINEUPLOAD" var="FINEUPLOAD"/>

<tg:includestatic type="${JS_TYPE}" paramName="${PRODUCT}" />
<tg:includestatic type="${JS_TYPE}" paramName="${FINEUPLOAD}" />

<tg:includestatic type="${CSS_TYPE}" paramName="${JQUERY_UI}" />
<tg:includestatic type="${CSS_TYPE}" paramName="${FINEUPLOAD}" />

<style>
	.qq-upload-list {
		text-align: left;
	}
	.qq-upload-button {
	    background: none repeat scroll 0 0 #EEEEEE;
	    border-bottom: 0px solid #DDDDDD;
	    color: #333333;
	    display: block;
	    padding: 0px 0;
	    text-align: LEFT;    	
	}	
	
	li.alert-success{
		background-color: #EEEEEE;
	}
	li.alert-error {
		background-color: #F2DEDE;
	}
	.alert-error .qq-upload-failed-text {
		display: inline;
	}
	.qq-upload-list li {
		background-color: #EEEEEE;
	}
</style>
<form:form name="processInfoForm" id="processInfoForm" action="processInfoProcess.do" method="POST" commandName="processingInfoVO">

<spring:hasBindErrors name="processingInfoVO">
    <div class="error">
        <ul>
            <form:errors path="*" element="li" delimiter="</li><li>" id="" />
        </ul>
    </div>
</spring:hasBindErrors>
<c:if test="${not empty SUCCESS_MESSAGE}">
	<div class="success"><fmt:message key="${SUCCESS_MESSAGE}" /> </div>
</c:if>

<sec:authentication  property="principal" var="principal" />

<form:hidden path="btnType" id="btnType"/>
<form:hidden path="processId" id="processId"/>
<form:hidden path="sectionName" id="sectionName"/>
<form:hidden path="id" id="id"/>
<form:hidden path="companyId" id="companyId"/>
<form:hidden path="productStatus" id="productStatus"/>
<form:hidden path="productType" id="productType"/>
<form:hidden path="worklflowStep" id="worklflowStep"/>

<form:hidden path="testing_spreadsheet.mediaId" id="testing_spreadsheet.mediaId"/>
<form:hidden path="testing_spreadsheet.fileName" id="testing_spreadsheet.fileName"/>
<form:hidden path="testing_spreadsheet.fileType" id="testing_spreadsheet.fileType"/>
<form:hidden path="testing_spreadsheet.fileFullName" id="testing_spreadsheet.fileFullName"/>

<form:hidden path="product_label.mediaId" id="product_label.mediaId"/>
<form:hidden path="product_label.fileName" id="product_label.fileName"/>
<form:hidden path="product_label.fileType" id="product_label.fileType"/>
<form:hidden path="product_label.fileFullName" id="product_label.fileFullName"/>

<form:hidden path="sustainability_disclosure.mediaId" id="sustainability_disclosure.mediaId"/>
<form:hidden path="sustainability_disclosure.fileName" id="sustainability_disclosure.fileName"/>
<form:hidden path="sustainability_disclosure.fileType" id="sustainability_disclosure.fileType"/>
<form:hidden path="sustainability_disclosure.fileFullName" id="sustainability_disclosure.fileFullName"/>

<form:hidden path="packaging_label.mediaId" id="packaging_label.mediaId"/>
<form:hidden path="packaging_label.fileName" id="packaging_label.fileName"/>
<form:hidden path="packaging_label.fileType" id="packaging_label.fileType"/>
<form:hidden path="packaging_label.fileFullName" id="packaging_label.fileFullName"/>

<form:hidden path="pdf_sample_product.mediaId" id="pdf_sample_product.mediaId"/>
<form:hidden path="pdf_sample_product.fileName" id="pdf_sample_product.fileName"/>
<form:hidden path="pdf_sample_product.fileType" id="pdf_sample_product.fileType"/>
<form:hidden path="pdf_sample_product.fileFullName" id="pdf_sample_product.fileFullName"/>

<form:hidden path="workflowId"/>
<form:hidden path="emailText" id="emailText"/>
<form:hidden path="deviceMarketingInputStatus" id="deviceMarketingInputStatus"/>
<form:hidden path="productInfoInputStatus" id="productInfoInputStatus"/>
<form:hidden path="exportComplianceInputStatus" id="exportComplianceInputStatus"/>
<form:hidden path="uploadPdfInputStatus" id="uploadPdfInputStatus"/>


	<h1 class="redheading"><fmt:message key="processingInfo.heading"/> </h1>
	<div class="emptyBoxLayouts" style="padding:0px; margin-top:-10px; margin-bottom:10px;">
		<div class="threeColmnLayout">
			<div class="columnOne">
				<label class="inputlabel"><fmt:message key="companyForm.companyName"/></label><span class="label-value"><c:out value="${processingInfoVO.companyName}"></c:out></span>
				<div class="clearboth"></div>
			</div>
			<div class="columnTwo" style="width:500px">
				<label class="inputlabel"><fmt:message key="productVO.workflow.status.label" /></label><span class="label-value"><c:out value="${processingInfoVO.worklflowStep}" escapeXml="false"/></span>
				<div class="clearboth"></div>
			</div>
			<div class="clearboth"></div>
		</div>
	</div>

	<a href="#" class="bigTabGray"><span><span><fmt:message key="label.tab.one"/></span></span></a> 
	<a href="#" class="bigTab"><span><span><fmt:message key="label.tab.two"/></span></span></a>
	<div class="clearboth"></div>
	<br />

	<c:forEach items="${processingInfoVO.workItems}" var="vo" varStatus="status">
		<c:if test="${vo.title eq 'DeviceMarketingReview' }">
                        <a id="${vo.title}" name="${vo.title}"></a>
			<div class="grayBoxLayouts">
				<label class="inputlabel redColor"><c:out value="${vo.displayName}"/></label>
				<div class="twoColmnLayout width500">
					<div class="columnOne">
						<label class="inputlabel"><fmt:message key="processingInfo.ring"/></label>
						<form:select cssClass="selct" path="ringAssociation">
							<form:options items="${populatedFormElements['ringsList']}" />
						</form:select>
						<div class="clearboth"></div>
					</div>
					<div class="clearboth"></div>
				</div>
				<div class="clearboth"></div>
				<textarea style="margin-top:10px; height:33px;" class="textfield font fullwidth" name="<c:out value='${status.index}'/>" id="<c:out value='${status.index}'/>"><%=VAPConstants.EMAIL_TEXT_BOX_DEFAULT_TEXT%></textarea>
				<table cellspacing="0" cellpadding="0" border="0" style="float: right;">
					<tr>
					<c:forEach items="${vo.nextActions}" var="i">
						<c:choose>
							<c:when test="${i eq 'Approve'}">
								<td><a class="button floatRight marginTop20" href="#" rel="${i}" name="${vo.title}" textFieldName="<c:out value='${status.index}'/>"><span class="red">${i}</span></a></td>
							</c:when>
							<c:when test="${i eq 'UnDeny'}">
								<td><a class="button floatRight marginTop20" href="#" rel="${i}" name="${vo.title}" textFieldName="<c:out value='${status.index}'/>"><span class="red">${i}</span></a></td>
							</c:when>
							<c:when test="${i eq 'Deny'}">
								<td><a class="button marginLeft10 floatRight marginTop20" href="#" rel="${i}" name="${vo.title}" textFieldName="<c:out value='${status.index}'/>"><span class="gray">${i}</span></a></td>
							</c:when>
							<c:when test="${i eq 'RFI'}">
								<td><a class="button floatRight marginTop20 marginLeft10" href="#" rel="${i}" name="${vo.title}"textFieldName="<c:out value='${status.index}'/>" ><span class="red">${i}</span></a></td>		
							</c:when>
						</c:choose>
					</c:forEach>
					</tr>
				</table>
				<div class="clearboth"></div>
			</div>
			<br />
		</c:if>
		
		<c:if test="${vo.title eq 'ProductInfoUpload' }">
                <a id="${vo.title}" name="${vo.title}"></a>
		<div class="grayBoxLayouts">
			<div class="threeColmnLayout">
				<h1 class="noBold"><c:out value="${vo.displayName}"/></h1>
				<div class="columnOne" style="width:250px;">
					<div class="redstar">*</div>
					<label class="inputlabel"><fmt:message key="processingInfo.testing.spreadsheet"/></label><br/>
					<span class="label-info-small" ><fmt:message key="processingInfo.testing.format"/></span>
					<div class="clearboth"></div>
					
					<div id="upload_<%=VAPConstants.TESTING_SPREADSHEET%>" <c:if test="${not empty processingInfoVO.testing_spreadsheet and not empty processingInfoVO.testing_spreadsheet.mediaId}">style="display:none;"</c:if>></div>
					<div class="uploadeded-File" id='uploaded_<%=VAPConstants.TESTING_SPREADSHEET%>' <c:if test="${empty processingInfoVO.testing_spreadsheet or empty processingInfoVO.testing_spreadsheet.mediaId}">style="display:none;"</c:if>>
						<a
						id="download_<%=VAPConstants.TESTING_SPREADSHEET%>"
						title="${processingInfoVO.testing_spreadsheet.fileFullName}" 
						class="<c:if test="${not empty processingInfoVO.testing_spreadsheet and not empty processingInfoVO.testing_spreadsheet.mediaId}">${processingInfoVO.testing_spreadsheet.mediaId}</c:if>"
						href="#"><c:if test="${not empty processingInfoVO.testing_spreadsheet and not empty processingInfoVO.testing_spreadsheet.fileName}">${processingInfoVO.testing_spreadsheet.fileName}</c:if></a> 
						<span><a 
							id="delete_<%=VAPConstants.TESTING_SPREADSHEET%>" 
							rel="<c:if test="${not empty processingInfoVO.testing_spreadsheet and not empty processingInfoVO.testing_spreadsheet.fileType}">${processingInfoVO.testing_spreadsheet.fileType}</c:if>"
							name="<c:if test="${not empty processingInfoVO.testing_spreadsheet and not empty processingInfoVO.testing_spreadsheet.fileName}">${processingInfoVO.testing_spreadsheet.fileName}</c:if>"
							class="<c:if test="${not empty processingInfoVO.testing_spreadsheet and not empty processingInfoVO.testing_spreadsheet.mediaId}">${processingInfoVO.testing_spreadsheet.mediaId}</c:if>"
							href="#" 
							title="Delete" ><tg:img src="/images/uploadedFile-icon.png" width="16" height="16" alt="Delete" border="0" /></a></span>
						<div class="clearboth"></div>
					</div>
					
					<div class="clearboth"></div>
				</div>
				<div class="columnTwo">
					<div class="redstar">*</div>
					<label class="inputlabel width100"><fmt:message key="processingInfo.product.label"/></label><br/>
					<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
					<div class="clearboth"></div>
	
					<div id="upload_<%=VAPConstants.PRODUCT_LABEL%>" <c:if test="${not empty processingInfoVO.product_label and not empty processingInfoVO.product_label.mediaId}">style="display:none;"</c:if>></div>
					<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_LABEL%>' <c:if test="${empty processingInfoVO.product_label or empty processingInfoVO.product_label.mediaId}">style="display:none;"</c:if>>
						<a
						id="download_<%=VAPConstants.PRODUCT_LABEL%>"
						title="${processingInfoVO.product_label.fileFullName}" 
						class="<c:if test="${not empty processingInfoVO.product_label and not empty processingInfoVO.product_label.mediaId}">${processingInfoVO.product_label.mediaId}</c:if>"
						href="#"><c:if test="${not empty processingInfoVO.product_label and not empty processingInfoVO.product_label.fileName}">${processingInfoVO.product_label.fileName}</c:if></a> 
						<span><a 
							id="delete_<%=VAPConstants.PRODUCT_LABEL%>" 
							rel="<c:if test="${not empty processingInfoVO.product_label and not empty processingInfoVO.product_label.fileType}">${processingInfoVO.product_label.fileType}</c:if>"
							name="<c:if test="${not empty processingInfoVO.product_label and not empty processingInfoVO.product_label.fileName}">${processingInfoVO.product_label.fileName}</c:if>"
							class="<c:if test="${not empty processingInfoVO.product_label and not empty processingInfoVO.product_label.mediaId}">${processingInfoVO.product_label.mediaId}</c:if>"
							href="#" 
							title="Delete" ><tg:img src="/images/uploadedFile-icon.png" width="16" height="16" alt="Delete" border="0" /></a></span>
						<div class="clearboth"></div>
					</div>
	
					<div class="clearboth"></div>
				</div>
	
				<div class="columnThree">
					<div class="clearboth"></div>
				</div>
				<div class="clearboth"></div>
			</div>
			<br />
	
			<div class="threeColmnLayout">
				<div class="columnOne" style="width:250px;">
					<div class="redstar">*</div>
					<label class="inputlabel"><fmt:message key="processingInfo.disclosure"/></label><br/>
					<span class="label-info-small" ><fmt:message key="processingInfo.testing.format"/></span>
					<div class="clearboth"></div>
	
					<div id="upload_<%=VAPConstants.SUSTAINABILITY_DISCLOSURE%>" <c:if test="${not empty processingInfoVO.sustainability_disclosure and not empty processingInfoVO.sustainability_disclosure.mediaId}">style="display:none;"</c:if>></div>
					<div class="uploadeded-File" id='uploaded_<%=VAPConstants.SUSTAINABILITY_DISCLOSURE%>' <c:if test="${empty processingInfoVO.sustainability_disclosure or empty processingInfoVO.sustainability_disclosure.mediaId}">style="display:none;"</c:if>>
						<a
						id="download_<%=VAPConstants.SUSTAINABILITY_DISCLOSURE%>"
						title="${processingInfoVO.sustainability_disclosure.fileFullName}" 
						class="<c:if test="${not empty processingInfoVO.sustainability_disclosure and not empty processingInfoVO.sustainability_disclosure.mediaId}">${processingInfoVO.sustainability_disclosure.mediaId}</c:if>"
						href="#"><c:if test="${not empty processingInfoVO.sustainability_disclosure and not empty processingInfoVO.sustainability_disclosure.fileName}">${processingInfoVO.sustainability_disclosure.fileName}</c:if></a> 
						<span><a 
							id="delete_<%=VAPConstants.SUSTAINABILITY_DISCLOSURE%>" 
							rel="<c:if test="${not empty processingInfoVO.sustainability_disclosure and not empty processingInfoVO.sustainability_disclosure.fileType}">${processingInfoVO.sustainability_disclosure.fileType}</c:if>"
							name="<c:if test="${not empty processingInfoVO.sustainability_disclosure and not empty processingInfoVO.sustainability_disclosure.fileName}">${processingInfoVO.sustainability_disclosure.fileName}</c:if>"
							class="<c:if test="${not empty processingInfoVO.sustainability_disclosure and not empty processingInfoVO.sustainability_disclosure.mediaId}">${processingInfoVO.sustainability_disclosure.mediaId}</c:if>"
							href="#" 
							title="Delete" ><tg:img src="/images/uploadedFile-icon.png" width="16" height="16" alt="Delete" border="0" /></a></span>
						<div class="clearboth"></div>
					</div>
	
					<div class="clearboth"></div>
				</div>
				<div class="columnTwo">
					<div class="redstar">*</div>
					<label class="inputlabel width100"><fmt:message key="processingInfo.image.packaging"/></label><br/>
					<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
					<div class="clearboth"></div>
	
					<div id="upload_<%=VAPConstants.PACKAGING_LABEL%>" <c:if test="${not empty processingInfoVO.packaging_label and not empty processingInfoVO.packaging_label.mediaId}">style="display:none;"</c:if>></div>
					<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PACKAGING_LABEL%>' <c:if test="${empty processingInfoVO.packaging_label or empty processingInfoVO.packaging_label.mediaId}">style="display:none;"</c:if>>
						<a
						id="download_<%=VAPConstants.PACKAGING_LABEL%>"
						title="${processingInfoVO.packaging_label.fileFullName}" 
						class="<c:if test="${not empty processingInfoVO.packaging_label and not empty processingInfoVO.packaging_label.mediaId}">${processingInfoVO.packaging_label.mediaId}</c:if>"
						href="#"><c:if test="${not empty processingInfoVO.packaging_label and not empty processingInfoVO.packaging_label.fileName}">${processingInfoVO.packaging_label.fileName}</c:if></a> 
						<span><a 
							id="delete_<%=VAPConstants.PACKAGING_LABEL%>" 
							rel="<c:if test="${not empty processingInfoVO.packaging_label and not empty processingInfoVO.packaging_label.fileType}">${processingInfoVO.packaging_label.fileType}</c:if>"
							name="<c:if test="${not empty processingInfoVO.packaging_label and not empty processingInfoVO.packaging_label.fileName}">${processingInfoVO.packaging_label.fileName}</c:if>"
							class="<c:if test="${not empty processingInfoVO.packaging_label and not empty processingInfoVO.packaging_label.mediaId}">${processingInfoVO.packaging_label.mediaId}</c:if>"
							href="#" 
							title="Delete" ><tg:img src="/images/uploadedFile-icon.png" width="16" height="16" alt="Delete" border="0" /></a></span>
						<div class="clearboth"></div>
					</div>
	
					<div class="clearboth"></div>
				</div>
				<div class="columnThree">
					<div class="clearboth"></div>
				</div>
				<div class="clearboth"></div>
			</div>
			
			<textarea style="margin-top:20px; height:33px;" class="textfield font fullwidth" name="<c:out value='${status.index}'/>" id="<c:out value='${status.index}'/>"><%=VAPConstants.EMAIL_TEXT_BOX_DEFAULT_TEXT%></textarea>
			<c:forEach items="${vo.nextActions}" var="i">
				<a class="button floatRight marginTop20" href="#" rel="${i}" name="${vo.title}" textFieldName="<c:out value='${status.index}'/>"><span class="red">${i}</span></a>
			</c:forEach>
			<div class="clearboth"></div>
		</div>
		<br />
		</c:if>
	
		<c:if test="${vo.title eq 'ExportCompliance' }">
                <a id="${vo.title}" name="${vo.title}"></a>
		<div class="grayBoxLayouts">
			<div class="threeColmnLayout">
				<h1 class="noBold"><c:out value="${vo.displayName}"/></h1>
				<div class="marginTop5"></div>
				<span class="font12">You've developed your accessory and/or	application and are ready to take it to market - so why do you need	to now be addressing U.S. Export Control regulations? First,
					Verizon Wireless is a U.S. company, and the United States government considers it an export when someone outside the U.S. downloads software from our servers or purchases/moves products
					outside the U.S. Because of this, your product or application may be subject to U.S. export laws, which looks at the components in the product as well as whether the software uses encryption (among
					other things). <br /> <br /> While Verizon Wireless cannot provide legal advice on how to address export control compliance, we are providing the links below can give you some general
					background about export controls and more information that can help you with your compliance obligations. The URLs below are a good place to start educating yourself on your export control
					obligations as well as contact information for the experts at the Department of Commerce:<br/><br/> 
					<a href="http://www.bis.doc.gov/index.htm" target="_blank">http://www.bis.doc.gov/index.htm</a><br />
					<a href="http://www.bis.doc.gov/index.php/policy-guidance/encryption/encryption-faqs" target="_blank">http://www.bis.doc.gov/index.php/policy-guidance/encryption/encryption-faqs</a><br/><br/>
					Even if you're using open source in your application or product, it may still be subject to the export regulations especially if you are using encryption in any way
					(regardless of whether your application contains a crypto library, it may call up crypto functionality in another program). So under the U.S. Commerce Department's regulations, there are different
					categories of encryption software (like publicly available, authentication, digital signature, mass market, and ancillary), and	there are different rules that apply to each type of encryption.<br />
				</span>
				<div class="clearboth"></div>
				<div class="columnOne" style="width:250px;">
					<div class="redstar">*</div>
					<label class="inputlabel">ECCN#/ERN</label>
					<div class="clearboth"></div>
					<form:input path="eccn" cssClass="input" cssStyle="width:225px;" maxlength="10"/>
					<div class="clearboth"></div>
				</div>
				<div class="columnTwo">
					<label class="inputlabel width100">CCATS</label>
					<div class="clearboth"></div>
					<form:input path="ccats" cssClass="input" cssStyle="width:225px;" maxlength="10"/>				
					<div class="clearboth"></div>
				</div>
				<div class="clearboth"></div>
				<textarea style="margin-top:20px; height:33px;" class="textfield font fullwidth" name="<c:out value='${status.index}'/>" id="<c:out value='${status.index}'/>"><%=VAPConstants.EMAIL_TEXT_BOX_DEFAULT_TEXT%></textarea>
				<c:forEach items="${vo.nextActions}" var="i">
					<a class="button floatRight marginTop20" href="#" rel="${i}" name="${vo.title}" textFieldName="<c:out value='${status.index}'/>"><span class="red">${i}</span></a>
				</c:forEach>
				<div class="clearboth"></div>
			</div>
		</div>
		<br />
		</c:if>
		
		<c:if test="${vo.title eq 'UploadPDFofSampleProduct' }">
                <a id="${vo.title}" name="${vo.title}"></a>
		<div class="grayBoxLayouts">
			<div class="threeColmnLayout">
				<h1 class="noBold"><fmt:message key="processingInfo.pdf.section.title"/></h1>
				<div class="columnOne" style="width:250px;">
					<div class="redstar">*</div>
					<label class="inputlabel width200"><fmt:message key="processingInfo.final.pdf"/></label><br/>
					<span class="label-info-small" ><fmt:message key="processingInfo.final.pdf.format"/></span>
					<div class="clearboth"></div>
					
					<div id="upload_<%=VAPConstants.PDF_SAMPLE_PRODUCT%>" <c:if test="${not empty processingInfoVO.pdf_sample_product and not empty processingInfoVO.pdf_sample_product.mediaId}">style="display:none;"</c:if>></div>
						<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PDF_SAMPLE_PRODUCT%>' <c:if test="${empty processingInfoVO.pdf_sample_product or empty processingInfoVO.pdf_sample_product.mediaId}">style="display:none;"</c:if>>
						<a
						id="download_<%=VAPConstants.PDF_SAMPLE_PRODUCT%>"
						title="${processingInfoVO.pdf_sample_product.fileFullName}" 
						class="<c:if test="${not empty processingInfoVO.pdf_sample_product and not empty processingInfoVO.pdf_sample_product.mediaId}">${processingInfoVO.pdf_sample_product.mediaId}</c:if>"
						href="#"><c:if test="${not empty processingInfoVO.pdf_sample_product and not empty processingInfoVO.pdf_sample_product.fileName}">${processingInfoVO.pdf_sample_product.fileName}</c:if></a> 
						<span><a 
							id="delete_<%=VAPConstants.PDF_SAMPLE_PRODUCT%>" 
							rel="<c:if test="${not empty processingInfoVO.pdf_sample_product and not empty processingInfoVO.pdf_sample_product.fileType}">${processingInfoVO.pdf_sample_product.fileType}</c:if>"
							name="<c:if test="${not empty processingInfoVO.pdf_sample_product and not empty processingInfoVO.pdf_sample_product.fileName}">${processingInfoVO.pdf_sample_product.fileName}</c:if>"
							class="<c:if test="${not empty processingInfoVO.pdf_sample_product and not empty processingInfoVO.pdf_sample_product.mediaId}">${processingInfoVO.pdf_sample_product.mediaId}</c:if>"
							href="#" 
							title="Delete" ><tg:img src="/images/uploadedFile-icon.png" width="16" height="16" alt="Delete" border="0" /></a></span>
						<div class="clearboth"></div>
					</div>
				</div>
				<div class="clearboth"></div>
				<div class="clearboth"></div>
				<textarea style="margin-top:20px; height:33px;" class="textfield font fullwidth" name="<c:out value='${status.index}'/>" id="<c:out value='${status.index}'/>"><%=VAPConstants.EMAIL_TEXT_BOX_DEFAULT_TEXT%></textarea>
				<c:forEach items="${vo.nextActions}" var="i">
					<a class="button floatRight marginTop20" href="#" rel="${i}" name="${vo.title}" textFieldName="<c:out value='${status.index}'/>"><span class="red">${i}</span></a>
				</c:forEach>
				<div class="clearboth"></div>
			</div>
		</div>
		<br />
		</c:if>
		<c:if test="${ vo.title ne 'DeviceMarketingReview' and vo.title ne 'ProductInfoUpload' and vo.title ne 'ExportCompliance' and vo.title ne 'UploadPDFofSampleProduct' and vo.title ne 'Approved'}">
			<div class="grayBoxLayouts">
				<label class="inputlabel redColor"><c:out value="${vo.displayName}"/></label>
				<div class="clearboth"></div>
				<c:if test="${vo.title eq 'ExportComplianceReview' }">
					<div class="twoColmnLayout">						
						<div class="columnOne" style="width:250px;">
							<div class="redstar">*</div>
							<label class="inputlabel">ECCN#/ERN</label>
							<div class="clearboth"></div>
							<c:out value="${processingInfoVO.eccn}"/>
							<div class="clearboth"></div>
						</div>
						<div class="columnTwo">
							<label class="inputlabel width100">CCATS</label>
							<div class="clearboth"></div>
							<c:out value="${processingInfoVO.ccats}"/>	
							<div class="clearboth"></div>
						</div>
						<div class="clearboth"></div>			
					</div>
				</c:if>
				<textarea style="margin-top:10px; height:33px;" class="textfield font fullwidth" name="<c:out value='${status.index}'/>" id="<c:out value='${status.index}'/>"><%=VAPConstants.EMAIL_TEXT_BOX_DEFAULT_TEXT%></textarea>
				<table cellspacing="0" cellpadding="0" border="0" style="float: right;">
					<tr>
					<c:forEach items="${vo.nextActions}" var="i">
						<c:choose>
							<c:when test="${i eq 'Approve' or i eq 'Start Testing' or i eq 'End Testing' or i eq 'UnDeny' or i eq 'UnRFI'}">
								<td><a class="button floatRight marginTop20" href="#" rel="${i}" name="${vo.title}" textFieldName="<c:out value='${status.index}'/>"><span class="red">${i}</span></a></td>
							</c:when>
							<c:when test="${i eq 'Deny'}">
								<td><a class="button marginLeft10 floatRight marginTop20" href="#" rel="${i}" name="${vo.title}" textFieldName="<c:out value='${status.index}'/>"><span class="gray">${i}</span></a></td>
							</c:when>
							<c:when test="${i eq 'RFI'}">
								<td><a class="button floatRight marginTop20 marginLeft10" href="#" rel="${i}" name="${vo.title}" textFieldName="<c:out value='${status.index}'/>"><span class="red">${i}</span></a></td>		
							</c:when>
						</c:choose>
					</c:forEach>
					</tr>
				</table>
				<div class="clearboth"></div>
			</div>
			<br/>
		</c:if>
	</c:forEach>
	<div class="clearboth"></div>
	<c:if test="${processingInfoVO.deviceMarketingInputStatus eq 'PROCESSED' and principal.partner eq 'false'}">
	<div class="grayBoxLayouts">
		<label class="inputlabel redColor">Device Marketing Review</label>
		<div class="twoColmnLayout width500">
			<div class="columnOne">
				<label class="inputlabel"><fmt:message key="processingInfo.ring"/></label>
				<c:out value="${processingInfoVO.ringAssociation}"/>
				<form:hidden path="ringAssociation"/>
				<div class="clearboth"></div>
			</div>
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
	<br />
	</c:if>
	<c:if test="${processingInfoVO.exportComplianceInputStatus eq 'PROCESSED' }">
	<div class="grayBoxLayouts">
		<div class="threeColmnLayout">
			<h1 class="noBold">Export Compliance</h1>
			<div class="marginTop5"></div>
			<span class="font12">You've developed your accessory and/or	application and are ready to take it to market - so why do you need	to now be addressing U.S. Export Control regulations? First,
				Verizon Wireless is a U.S. company, and the United States government considers it an export when someone outside the U.S. downloads software from our servers or purchases/moves products
				outside the U.S. Because of this, your product or application may be subject to U.S. export laws, which looks at the components in the product as well as whether the software uses encryption (among
				other things). <br /> <br /> While Verizon Wireless cannot provide legal advice on how to address export control compliance, we are providing the links below can give you some general
				background about export controls and more information that can help you with your compliance obligations. The URLs below are a good place to start educating yourself on your export control
				obligations as well as contact information for the experts at the Department of Commerce:<br/><br/> 
				<a href="http://www.bis.doc.gov/index.htm" target="_blank">http://www.bis.doc.gov/index.htm</a><br />
				<a href="http://www.bis.doc.gov/index.php/policy-guidance/encryption/encryption-faqs" target="_blank">http://www.bis.doc.gov/index.php/policy-guidance/encryption/encryption-faqs</a><br/><br/>
				Even if you're using open source in your application or product, it may still be subject to the export regulations especially if you are using encryption in any way
				(regardless of whether your application contains a crypto library, it may call up crypto functionality in another program). So under the U.S. Commerce Department's regulations, there are different
				categories of encryption software (like publicly available, authentication, digital signature, mass market, and ancillary), and	there are different rules that apply to each type of encryption.<br />
			</span>
			<div class="clearboth"></div>
			<div class="columnOne" style="width:250px;">
				<div class="redstar">*</div>
				<label class="inputlabel">ECCN#/ERN</label>
				<div class="clearboth"></div>
				<c:out value="${processingInfoVO.eccn}"/>
				<form:hidden path="eccn"/>
				<div class="clearboth"></div>
			</div>
			<div class="columnTwo">
				<label class="inputlabel width100">CCATS</label>
				<div class="clearboth"></div>
				<c:out value="${processingInfoVO.ccats}"/>	
				<form:hidden path="ccats"/>			
				<div class="clearboth"></div>
			</div>
			<div class="clearboth"></div>			
		</div>
	</div>
	<br />
	</c:if>
	<c:if test="${processingInfoVO.productInfoInputStatus eq 'PROCESSED' }">
		<div class="grayBoxLayouts">
			<div class="threeColmnLayout">
				<h1 class="noBold">Product Info Upload</h1>
				<div class="columnOne" style="width:250px;">
					<div class="redstar">*</div>
					<label class="inputlabel"><fmt:message key="processingInfo.testing.spreadsheet"/></label><br/>
					<span class="label-info-small" ><fmt:message key="processingInfo.testing.format"/></span>
					<div class="clearboth"></div>
					
					<div class="uploadeded-File" id='uploaded_<%=VAPConstants.TESTING_SPREADSHEET%>' <c:if test="${empty processingInfoVO.testing_spreadsheet or empty processingInfoVO.testing_spreadsheet.mediaId}">style="display:none;"</c:if>>
						<a
						id="download_<%=VAPConstants.TESTING_SPREADSHEET%>" 
						title="${processingInfoVO.testing_spreadsheet.fileFullName}"
						class="<c:if test="${not empty processingInfoVO.testing_spreadsheet and not empty processingInfoVO.testing_spreadsheet.mediaId}">${processingInfoVO.testing_spreadsheet.mediaId}</c:if>"
						href="#"><c:if test="${not empty processingInfoVO.testing_spreadsheet and not empty processingInfoVO.testing_spreadsheet.fileName}">${processingInfoVO.testing_spreadsheet.fileName}</c:if></a> 
						<div class="clearboth"></div>
					</div>
					
					<div class="clearboth"></div>
				</div>
				<div class="columnTwo">
					<div class="redstar">*</div>
					<label class="inputlabel width100"><fmt:message key="processingInfo.product.label"/></label><br/>
					<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
					<div class="clearboth"></div>
					
					<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_LABEL%>' <c:if test="${empty processingInfoVO.product_label or empty processingInfoVO.product_label.mediaId}">style="display:none;"</c:if>>
						<a
						id="download_<%=VAPConstants.PRODUCT_LABEL%>" 
						title="${processingInfoVO.product_label.fileFullName}"
						class="<c:if test="${not empty processingInfoVO.product_label and not empty processingInfoVO.product_label.mediaId}">${processingInfoVO.product_label.mediaId}</c:if>"
						href="#"><c:if test="${not empty processingInfoVO.product_label and not empty processingInfoVO.product_label.fileName}">${processingInfoVO.product_label.fileName}</c:if></a> 
						<div class="clearboth"></div>
					</div>
					
					<div class="clearboth"></div>
				</div>
	
				<div class="columnThree">
					<div class="clearboth"></div>
				</div>
				<div class="clearboth"></div>
			</div>
			<br />
	
			<div class="threeColmnLayout">
				<div class="columnOne" style="width:250px;">
					<div class="redstar">*</div>
					<label class="inputlabel"><fmt:message key="processingInfo.disclosure"/></label><br/>
					<span class="label-info-small" ><fmt:message key="processingInfo.testing.format"/></span>
					<div class="clearboth"></div>
	
					<div class="uploadeded-File" id='uploaded_<%=VAPConstants.SUSTAINABILITY_DISCLOSURE%>' <c:if test="${empty processingInfoVO.sustainability_disclosure or empty processingInfoVO.sustainability_disclosure.mediaId}">style="display:none;"</c:if>>
						<a
						id="download_<%=VAPConstants.SUSTAINABILITY_DISCLOSURE%>" 
						title="${processingInfoVO.sustainability_disclosure.fileFullName}"
						class="<c:if test="${not empty processingInfoVO.sustainability_disclosure and not empty processingInfoVO.sustainability_disclosure.mediaId}">${processingInfoVO.sustainability_disclosure.mediaId}</c:if>"
						href="#"><c:if test="${not empty processingInfoVO.sustainability_disclosure and not empty processingInfoVO.sustainability_disclosure.fileName}">${processingInfoVO.sustainability_disclosure.fileName}</c:if></a> 
						<div class="clearboth"></div>
					</div>
	
					<div class="clearboth"></div>
				</div>
				<div class="columnTwo">
					<div class="redstar">*</div>
					<label class="inputlabel width100"><fmt:message key="processingInfo.image.packaging"/></label><br/>
					<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
					<div class="clearboth"></div>
	
					<div id="upload_<%=VAPConstants.PACKAGING_LABEL%>" <c:if test="${not empty processingInfoVO.packaging_label and not empty processingInfoVO.packaging_label.mediaId}">style="display:none;"</c:if>></div>
					<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PACKAGING_LABEL%>' <c:if test="${empty processingInfoVO.packaging_label or empty processingInfoVO.packaging_label.mediaId}">style="display:none;"</c:if>>
						<a
						id="download_<%=VAPConstants.PACKAGING_LABEL%>" 
						title="${processingInfoVO.packaging_label.fileFullName}"
						class="<c:if test="${not empty processingInfoVO.packaging_label and not empty processingInfoVO.packaging_label.mediaId}">${processingInfoVO.packaging_label.mediaId}</c:if>"
						href="#"><c:if test="${not empty processingInfoVO.packaging_label and not empty processingInfoVO.packaging_label.fileName}">${processingInfoVO.packaging_label.fileName}</c:if></a> 
						<div class="clearboth"></div>
					</div>
	
					<div class="clearboth"></div>
				</div>
				<div class="columnThree">
					<div class="clearboth"></div>
				</div>
				<div class="clearboth"></div>
			</div>
			<div class="clearboth"></div>
		</div>
		<br />
	</c:if>
	<c:if test="${processingInfoVO.uploadPdfInputStatus eq 'PROCESSED' }">
	<div class="grayBoxLayouts">
		<div class="threeColmnLayout">
			<h1 class="noBold"><fmt:message key="processingInfo.pdf.section.title"/></h1>
			<div class="columnOne" style="width:250px;">
				<div class="redstar">*</div>
				<label class="inputlabel width200"><fmt:message key="processingInfo.final.pdf"/></label><br/>
				<span class="label-info-small" ><fmt:message key="processingInfo.final.pdf.format"/></span>
				<div class="clearboth"></div>				
				<a id="download_<%=VAPConstants.PDF_SAMPLE_PRODUCT%>" 
				title="${processingInfoVO.pdf_sample_product.fileFullName}"
				class="<c:if test="${not empty processingInfoVO.pdf_sample_product and not empty processingInfoVO.pdf_sample_product.mediaId}">${processingInfoVO.pdf_sample_product.mediaId}</c:if>"
				href="#"><c:if test="${not empty processingInfoVO.pdf_sample_product and not empty processingInfoVO.pdf_sample_product.fileName}">${processingInfoVO.pdf_sample_product.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>
			<div class="clearboth"></div>
		</div>
	</div>
	<br />
	<br />
	</c:if>
</form:form>	
<tg:wfcomments companyId="${processingInfoVO.companyId}" productId="${processingInfoVO.id }" comments="${processingInfoVO.comments}" redirectUrl="/secure/product/processInfoEdit.do" />	
<script type="text/javascript">
	$("a.bigTabGray").click(function(e) {
        e.preventDefault();
        window.location='edit.do?productId='+$('#id').val();
        return false;
    });  
 	$("a.button").click(function (e){
 		e.preventDefault();
 		var rel=$(this).attr('rel');
 		if (rel){
	 		$('#btnType').val($(this).attr('rel'));
	 		$('#sectionName').val($(this).attr('name'));
	 		var emailFieldName=$(this).attr('textFieldName');
	 		$('#emailText').val($('#'+emailFieldName).val());
	 		//alert($('#emailText').val());
	 		$('#processInfoForm').submit();
 		}
 		else {
 			$('#addCommentForm').submit();
 		}
        return false;
 	});
</script>
<script type="text/javascript">
    var defaultEmailText='<%=VAPConstants.EMAIL_TEXT_BOX_DEFAULT_TEXT%>';
    var defaultCommentText='<%=VAPConstants.COMMENT_DEFAULT_TEXT%>';
    
    $('div.grayBoxLayouts textarea.wfCommentBox').focus(function(e) {
        if ($(this).val() == defaultEmailText || $(this).val() == defaultCommentText) {
            $(this).val("");
        }
    }); 
    
    $('div.grayBoxLayouts textarea.wfCommentBox').blur(function(e) {
        if ($(this).val() == null || $(this).val() == '') {
            $(this).val(defaultCommentText);
        }
    });

    $('div.grayBoxLayouts textarea').focus(function(e) {
        if ($(this).val() == defaultEmailText || $(this).val() == defaultCommentText) {
            $(this).val("");
        }
    }); 
    
    $('div.grayBoxLayouts textarea').blur(function(e) {
        if ($(this).val() == null || $(this).val() == '') {
            $(this).val(defaultEmailText);
        }
    });
</script> 