<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<a name="documents" id="documents"></a>
<div class="product-backtotop">
	<a href="#top" class="product-nav">Top</a>
</div>
<div class="grayBoxLayouts">
	<div class="threeColmnLayout">
		<h1><fmt:message key="productVO.doc.title"/></h1>
		<div class="columnOne">
			<label class="inputlabel"><fmt:message key="productVO.doc.productCopy"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.doc.format"/></span>
			<div class="clearboth"></div>

			<div id="upload_<%=VAPConstants.PRODUCT_PRODUCT_COPY_DOC%>" <c:if test="${not empty productVO.product_copy_doc and not empty productVO.product_copy_doc.mediaId}">style="display:none;"</c:if>></div>
			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_PRODUCT_COPY_DOC%>' <c:if test="${empty productVO.product_copy_doc or empty productVO.product_copy_doc.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_PRODUCT_COPY_DOC%>"  
				title="${productVO.product_copy_doc.fileFullName}"
				class="<c:if test="${not empty productVO.product_copy_doc and not empty productVO.product_copy_doc.mediaId}">${productVO.product_copy_doc.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.product_copy_doc and not empty productVO.product_copy_doc.fileName}">${productVO.product_copy_doc.fileName}</c:if></a> 
				<span><a 
					id="delete_<%=VAPConstants.PRODUCT_PRODUCT_COPY_DOC%>" 
					rel="<c:if test="${not empty productVO.product_copy_doc and not empty productVO.product_copy_doc.fileType}">${productVO.product_copy_doc.fileType}</c:if>"
					name="<c:if test="${not empty productVO.product_copy_doc and not empty productVO.product_copy_doc.fileName}">${productVO.product_copy_doc.fileName}</c:if>"
					class="<c:if test="${not empty productVO.product_copy_doc and not empty productVO.product_copy_doc.mediaId}">${productVO.product_copy_doc.mediaId}</c:if>"
					href="#" 
					title="Delete" ><tg:img src="/images/uploadedFile-icon.png" width="16" height="16" alt="Delete" border="0" /></a></span>
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<label class="inputlabel width100"><fmt:message key="productVO.doc.appCopy"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.doc.format"/></span>
			<div class="clearboth"></div>

			<div id="upload_<%=VAPConstants.PRODUCT_APP_COPY_DOC%>" <c:if test="${not empty productVO.app_copy_doc and not empty productVO.app_copy_doc.mediaId}">style="display:none;"</c:if>></div>
			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_APP_COPY_DOC%>' <c:if test="${empty productVO.app_copy_doc or empty productVO.app_copy_doc.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_APP_COPY_DOC%>"   
				title="${productVO.app_copy_doc.fileFullName}"
				class="<c:if test="${not empty productVO.app_copy_doc and not empty productVO.app_copy_doc.mediaId}">${productVO.app_copy_doc.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.app_copy_doc and not empty productVO.app_copy_doc.fileName}">${productVO.app_copy_doc.fileName}</c:if></a> 
				<span><a 
					id="delete_<%=VAPConstants.PRODUCT_APP_COPY_DOC%>" 
					rel="<c:if test="${not empty productVO.app_copy_doc and not empty productVO.app_copy_doc.fileType}">${productVO.app_copy_doc.fileType}</c:if>"
					name="<c:if test="${not empty productVO.app_copy_doc and not empty productVO.app_copy_doc.fileName}">${productVO.app_copy_doc.fileName}</c:if>"
					class="<c:if test="${not empty productVO.app_copy_doc and not empty productVO.app_copy_doc.mediaId}">${productVO.app_copy_doc.mediaId}</c:if>"
					href="#" 
					title="Delete" ><tg:img src="/images/uploadedFile-icon.png" width="16" height="16" alt="Delete" border="0" /></a></span>
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnThree">
			<label class="inputlabel width100"><fmt:message key="productVO.doc.launchPresentation"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.ppt.format"/></span>
			<div class="clearboth"></div>

			<div id="upload_<%=VAPConstants.PRODUCT_LAUNCH_PRESENTATION_VIDEO%>" <c:if test="${not empty productVO.launch_presentation_video and not empty productVO.launch_presentation_video.mediaId}">style="display:none;"</c:if>></div>
			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_LAUNCH_PRESENTATION_VIDEO%>' <c:if test="${empty productVO.launch_presentation_video or empty productVO.launch_presentation_video.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_LAUNCH_PRESENTATION_VIDEO%>"
				title="${productVO.launch_presentation_video.fileFullName}"  
				class="<c:if test="${not empty productVO.launch_presentation_video and not empty productVO.launch_presentation_video.mediaId}">${productVO.launch_presentation_video.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.launch_presentation_video and not empty productVO.launch_presentation_video.fileName}">${productVO.launch_presentation_video.fileName}</c:if></a> 
				<span><a 
					id="delete_<%=VAPConstants.PRODUCT_LAUNCH_PRESENTATION_VIDEO%>" 
					rel="<c:if test="${not empty productVO.launch_presentation_video and not empty productVO.launch_presentation_video.fileType}">${productVO.launch_presentation_video.fileType}</c:if>"
					name="<c:if test="${not empty productVO.launch_presentation_video and not empty productVO.launch_presentation_video.fileName}">${productVO.launch_presentation_video.fileName}</c:if>"
					class="<c:if test="${not empty productVO.launch_presentation_video and not empty productVO.launch_presentation_video.mediaId}">${productVO.launch_presentation_video.mediaId}</c:if>"
					href="#" 
					title="Delete" ><tg:img src="/images/uploadedFile-icon.png" width="16" height="16" alt="Delete" border="0" /></a></span>
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
</div>
