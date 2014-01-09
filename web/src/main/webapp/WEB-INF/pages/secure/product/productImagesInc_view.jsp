<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
<a name="images" id="images"></a>
<div class="product-backtotop">
	<a href="#top" class="product-nav">Top</a>
</div>
<div class="grayBoxLayouts">
	<h1><fmt:message key="productVO.productImage.title"/></h1>
	<div class="threeColmnLayout">
		<label class="inputlabelbold"><fmt:message key="productVO.productImage.out.title"/></label>
		<div class="clearboth"></div>
		<div class="columnOne">
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.productImage.out.forntView"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>
			
			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_OUT_FRONT_VIEW%>' <c:if test="${empty productVO.out_front_view or empty productVO.out_front_view.mediaId}">style="display:none;"</c:if>>
				<a id="download_<%=VAPConstants.PRODUCT_OUT_FRONT_VIEW%>"
				title="${productVO.out_front_view.fileFullName}" 
				class="<c:if test="${not empty productVO.out_front_view and not empty productVO.out_front_view.mediaId}">${productVO.out_front_view.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.out_front_view and not empty productVO.out_front_view.fileName}">${productVO.out_front_view.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>
			
			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<div class="redstar">*</div>
			<label class="inputlabelbold width100"><fmt:message key="productVO.productImage.out.angeledView"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_OUT_ANGELED_VIEW%>' <c:if test="${empty productVO.out_angeled_view or empty productVO.out_angeled_view.mediaId}">style="display:none;"</c:if>>
				<a id="download_<%=VAPConstants.PRODUCT_OUT_ANGELED_VIEW%>"
				title="${productVO.out_angeled_view.fileFullName}" 
				class="<c:if test="${not empty productVO.out_angeled_view and not empty productVO.out_angeled_view.mediaId}">${productVO.out_angeled_view.mediaId}</c:if>" 
				href="#"><c:if test="${not empty productVO.out_angeled_view and not empty productVO.out_angeled_view.fileName}">${productVO.out_angeled_view.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnThree">
			<label class="inputlabelbold width100"><fmt:message key="productVO.productImage.out.otherObject"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_OUT_ANOTHER_OBJECT%>' <c:if test="${empty productVO.out_another_object or empty productVO.out_another_object.mediaId}">style="display:none;"</c:if>>
				<a id="download_<%=VAPConstants.PRODUCT_OUT_ANOTHER_OBJECT%>"
				title="${productVO.out_another_object.fileFullName}" 
				class="<c:if test="${not empty productVO.out_another_object and not empty productVO.out_another_object.mediaId}">${productVO.out_another_object.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.out_another_object and not empty productVO.out_another_object.fileName}">${productVO.out_another_object.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>
						
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
	<br />

<%--Removed on Alice request. Bug 16034 --%>
<%--	<div class="threeColmnLayout">

		<div class="columnOne">
			<label class="inputlabelbold"><fmt:message key="productVO.productImage.out.otherView"><fmt:param value="1"/></fmt:message></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_OUT_OTHER_VIEW1%>' <c:if test="${empty productVO.out_other_view1 or empty productVO.out_other_view1.mediaId}">style="display:none;"</c:if>>
				<a id="download_<%=VAPConstants.PRODUCT_OUT_OTHER_VIEW1%>"
				title="${productVO.out_other_view1.fileFullName}" 
				class="<c:if test="${not empty productVO.out_other_view1 and not empty productVO.out_other_view1.mediaId}">${productVO.out_other_view1.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.out_other_view1 and not empty productVO.out_other_view1.fileName}">${productVO.out_other_view1.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<label class="inputlabelbold width100"><fmt:message key="productVO.productImage.out.otherView"><fmt:param value="2"/></fmt:message></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_OUT_OTHER_VIEW2%>' <c:if test="${empty productVO.out_other_view2 or empty productVO.out_other_view2.mediaId}">style="display:none;"</c:if>>
				<a id="download_<%=VAPConstants.PRODUCT_OUT_OTHER_VIEW2%>"
				title="${productVO.out_other_view2.fileFullName}" 
				class="<c:if test="${not empty productVO.out_other_view2 and not empty productVO.out_other_view2.mediaId}">${productVO.out_other_view2.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.out_other_view2 and not empty productVO.out_other_view2.fileName}">${productVO.out_other_view2.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnThree">
			<label class="inputlabelbold width100"><fmt:message key="productVO.productImage.out.otherView"><fmt:param value="3"/></fmt:message></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_OUT_OTHER_VIEW3%>' <c:if test="${empty productVO.out_other_view3 or empty productVO.out_other_view3.mediaId}">style="display:none;"</c:if>>
				<a id="download_<%=VAPConstants.PRODUCT_OUT_OTHER_VIEW3%>"
				title="${productVO.out_other_view3.fileFullName}"  
				class="<c:if test="${not empty productVO.out_other_view3 and not empty productVO.out_other_view3.mediaId}">${productVO.out_other_view3.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.out_other_view3 and not empty productVO.out_other_view3.fileName}">${productVO.out_other_view3.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>
			
			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
	<br />
	<div class="threeColmnLayout">

		<div class="columnOne">
			<label class="inputlabelbold width100"><fmt:message key="productVO.productImage.out.otherView"><fmt:param value="4"/></fmt:message></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_OUT_OTHER_VIEW4%>' <c:if test="${empty productVO.out_other_view4 or empty productVO.out_other_view4.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_OUT_OTHER_VIEW4%>"
				title="${productVO.out_other_view4.fileFullName}"
				class="<c:if test="${not empty productVO.out_other_view4 and not empty productVO.out_other_view4.mediaId}">${productVO.out_other_view4.mediaId}</c:if>"   
				href="#"><c:if test="${not empty productVO.out_other_view4 and not empty productVO.out_other_view4.fileName}">${productVO.out_other_view4.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
	<br />
--%>
	<div class="threeColmnLayout">
		<label class="inputlabelbold"><fmt:message key="productVO.productImage.in.title"/></label>
		<div class="clearboth"></div>
		<div class="columnOne">
			<div class="redstar">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.productImage.in.frontView"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>
			
			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_IN_FRONT_VIEW%>' <c:if test="${empty productVO.in_front_view or empty productVO.in_front_view.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_IN_FRONT_VIEW%>"
				title="${productVO.in_front_view.fileFullName}" 
				class="<c:if test="${not empty productVO.in_front_view and not empty productVO.in_front_view.mediaId}">${productVO.in_front_view.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.in_front_view and not empty productVO.in_front_view.fileName}">${productVO.in_front_view.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>
			
			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<div class="redstar">*</div>
			<label class="inputlabelbold width100"><fmt:message key="productVO.productImage.in.angeledView"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_IN_ANGELED_VIEW%>' <c:if test="${empty productVO.in_angeled_view or empty productVO.in_angeled_view.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_IN_ANGELED_VIEW%>"
				title="${productVO.in_angeled_view.fileFullName}"  
				class="<c:if test="${not empty productVO.in_angeled_view and not empty productVO.in_angeled_view.mediaId}">${productVO.in_angeled_view.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.in_angeled_view and not empty productVO.in_angeled_view.fileName}">${productVO.in_angeled_view.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnThree">
			<label class="inputlabelbold width100"><fmt:message key="productVO.productImage.in.otherObject"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_IN_ANOTHER_OBJECT%>' <c:if test="${empty productVO.in_another_object or empty productVO.in_another_object.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_IN_ANOTHER_OBJECT%>"
				title="${productVO.in_another_object.fileFullName}"   
				class="<c:if test="${not empty productVO.in_another_object and not empty productVO.in_another_object.mediaId}">${productVO.in_another_object.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.in_another_object and not empty productVO.in_another_object.fileName}">${productVO.in_another_object.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
	<br />

	<div class="threeColmnLayout">

		<div class="columnOne">
			<label class="inputlabelbold"><fmt:message key="productVO.productImage.screenShot.title"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_SCREEN_SHOT1%>' <c:if test="${empty productVO.screen_shot1 or empty productVO.screen_shot1.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_SCREEN_SHOT1%>"
				title="${productVO.screen_shot1.fileFullName}" 
				class="<c:if test="${not empty productVO.screen_shot1 and not empty productVO.screen_shot1.mediaId}">${productVO.screen_shot1.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.screen_shot1 and not empty productVO.screen_shot1.fileName}">${productVO.screen_shot1.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<label class="inputlabelbold width100">&nbsp;</label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_SCREEN_SHOT2%>' <c:if test="${empty productVO.screen_shot2 or empty productVO.screen_shot2.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_SCREEN_SHOT2%>"
				title="${productVO.screen_shot2.fileFullName}"  
				class="<c:if test="${not empty productVO.screen_shot2 and not empty productVO.screen_shot2.mediaId}">${productVO.screen_shot2.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.screen_shot2 and not empty productVO.screen_shot2.fileName}">${productVO.screen_shot2.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnThree">
			<label class="inputlabelbold width100">&nbsp;</label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_SCREEN_SHOT3%>' <c:if test="${empty productVO.screen_shot3 or empty productVO.screen_shot3.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_SCREEN_SHOT3%>"
				title="${productVO.screen_shot3.fileFullName}" 
				class="<c:if test="${not empty productVO.screen_shot3 and not empty productVO.screen_shot3.mediaId}">${productVO.screen_shot3.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.screen_shot3 and not empty productVO.screen_shot3.fileName}">${productVO.screen_shot3.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
	<br />

	<div class="threeColmnLayout">

		<div class="columnOne">
			<label class="inputlabelbold"><fmt:message key="productVO.productImage.lifeStyle.title"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_LIFESTYLE_IMAGE1%>' <c:if test="${empty productVO.lifestyle_image1 or empty productVO.lifestyle_image1.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_LIFESTYLE_IMAGE1%>"
				title="${productVO.lifestyle_image1.fileFullName}"  
				class="<c:if test="${not empty productVO.lifestyle_image1 and not empty productVO.lifestyle_image1.mediaId}">${productVO.lifestyle_image1.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.lifestyle_image1 and not empty productVO.lifestyle_image1.fileName}">${productVO.lifestyle_image1.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<label class="inputlabelbold width100">&nbsp;</label>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_LIFESTYLE_IMAGE2%>' <c:if test="${empty productVO.lifestyle_image2 or empty productVO.lifestyle_image2.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_LIFESTYLE_IMAGE2%>"
				title="${productVO.lifestyle_image2.fileFullName}" 
				class="<c:if test="${not empty productVO.lifestyle_image2 and not empty productVO.lifestyle_image2.mediaId}">${productVO.lifestyle_image2.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.lifestyle_image2 and not empty productVO.lifestyle_image2.fileName}">${productVO.lifestyle_image2.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnThree">
			<label class="inputlabelbold width100">&nbsp;</label>
			<span class="label-info-small" ><fmt:message key="productVO.productImage.format"/></span>
			<div class="clearboth"></div>
	
			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_LIFESTYLE_IMAGE3%>' <c:if test="${empty productVO.lifestyle_image3 or empty productVO.lifestyle_image3.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_LIFESTYLE_IMAGE3%>"
				title="${productVO.lifestyle_image3.fileFullName}" 
				class="<c:if test="${not empty productVO.lifestyle_image3 and not empty productVO.lifestyle_image3.mediaId}">${productVO.lifestyle_image3.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.lifestyle_image3 and not empty productVO.lifestyle_image3.fileName}">${productVO.lifestyle_image3.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
<br />
