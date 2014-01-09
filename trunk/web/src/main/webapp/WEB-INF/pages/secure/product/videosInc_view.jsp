<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<a name="video" id="video"></a>
<div class="product-backtotop">
	<a href="#top" class="product-nav">Top</a>
</div>
<div class="grayBoxLayouts">
	<div class="threeColmnLayout">

		<div class="columnOne">
			<h1><fmt:message key="productVO.video.title"/></h1>
			<div class="redstar" id="attachments_require_for_product">*</div>
			<label class="inputlabelbold"><fmt:message key="productVO.video.title"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.appVideo.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_PRODUCT_VIDEO%>' <c:if test="${empty productVO.product_video or empty productVO.product_video.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_PRODUCT_VIDEO%>"
				title="${productVO.product_video.fileFullName}" 
				class="<c:if test="${not empty productVO.product_video and not empty productVO.product_video.mediaId}">${productVO.product_video.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.product_video and not empty productVO.product_video.fileName}">${productVO.product_video.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<h1><fmt:message key="productVO.appVideo.title"/></h1>
			<label class="inputlabelbold width100"><fmt:message key="productVO.appVideo.phone"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.appVideo.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_PHONE_APP_VIDEO%>' <c:if test="${empty productVO.phone_app_video or empty productVO.phone_app_video.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_PHONE_APP_VIDEO%>"
				title="${productVO.phone_app_video.fileFullName}" 
				class="<c:if test="${not empty productVO.phone_app_video and not empty productVO.phone_app_video.mediaId}">${productVO.phone_app_video.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.phone_app_video and not empty productVO.phone_app_video.fileName}">${productVO.phone_app_video.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnThree">
			<h1>&nbsp;</h1>
			<label class="inputlabelbold width100"><fmt:message key="productVO.appVideo.tablet"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.appVideo.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_TABLET_APP_VIDEO%>' <c:if test="${empty productVO.tablet_app_video or empty productVO.tablet_app_video.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_TABLET_APP_VIDEO%>"
				title="${productVO.tablet_app_video.fileFullName}" 
				class="<c:if test="${not empty productVO.tablet_app_video and not empty productVO.tablet_app_video.mediaId}">${productVO.tablet_app_video.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.tablet_app_video and not empty productVO.tablet_app_video.fileName}">${productVO.tablet_app_video.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>

<br />