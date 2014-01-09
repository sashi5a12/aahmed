<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="product-backtotop">
	<a href="#top" class="product-nav">Top</a>
</div>
<div class="grayBoxLayouts">
	<div class="threeColmnLayout">
		<h1><fmt:message key="prodcutVO.appImage.title"/></h1>
		<table width="270" border="0" cellspacing="0" cellpadding="0"
			class="checkBoxTable ">
			<tbody>
				<tr>
					<td><fmt:message key="productVO.supportedDevices"/></td>
					<c:forEach items="${populatedFormElements['supportedDevicesList']}" var="i">
						<td><form:checkbox disabled="true" path="supportedDevices" value="${i.value}"/></td><td>${i.value}</td>
					</c:forEach>
				</tr>
			</tbody>
		</table>	
		<c:set var="Phone_Tablet" />	
		<div class="columnOne">
			<c:forEach items="${productVO.supportedDevices}" var="i">
			<c:if test="${i eq 'Phone' }">
				<c:set var="Phone_Tablet" value="true"/>
				<div class="redstar">*</div>
			</c:if>
			</c:forEach>
			<label class="inputlabelbold"><fmt:message key="productVO.appImages.phoneSplashScreen"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.appImages.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_PHONE_SPLASH_SCREEN%>' <c:if test="${empty productVO.phone_splash_screen or empty productVO.phone_splash_screen.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_PHONE_SPLASH_SCREEN%>"
				title="${productVO.phone_splash_screen.fileFullName}" 
				class="<c:if test="${not empty productVO.phone_splash_screen and not empty productVO.phone_splash_screen.mediaId}">${productVO.phone_splash_screen.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.phone_splash_screen and not empty productVO.phone_splash_screen.fileName}">${productVO.phone_splash_screen.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<c:forEach items="${productVO.supportedDevices}" var="i">
			<c:if test="${i eq 'Tablet' }">
				<c:set var="Phone_Tablet" value="true"/>
				<div class="redstar">*</div>
			</c:if>
			</c:forEach>			
			<label class="inputlabelbold width100"><fmt:message key="productVO.appImages.tabletSplashScreen"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.appImages.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_TABLET_SPLASH_SCREEN%>' <c:if test="${empty productVO.tablet_splash_screen or empty productVO.tablet_splash_screen.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_TABLET_SPLASH_SCREEN%>"
				title="${productVO.tablet_splash_screen.fileFullName}" 
				class="<c:if test="${not empty productVO.tablet_splash_screen and not empty productVO.tablet_splash_screen.mediaId}">${productVO.tablet_splash_screen.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.tablet_splash_screen and not empty productVO.tablet_splash_screen.fileName}">${productVO.tablet_splash_screen.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnThree">
			<c:if test="${Phone_Tablet eq true}"><div class="redstar">*</div></c:if>
			<label class="inputlabelbold width100"><fmt:message key="productVO.appImages.appIcon"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.appImages.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_APPLICATION%>' <c:if test="${empty productVO.application_icon or empty productVO.application_icon.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_APPLICATION%>"
				title="${productVO.application_icon.fileFullName}"  
				class="<c:if test="${not empty productVO.application_icon and not empty productVO.application_icon.mediaId}">${productVO.application_icon.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.application_icon and not empty productVO.application_icon.fileName}">${productVO.application_icon.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="clearboth"></div>
	</div>
	<div class="threeColmnLayout">
		<div class="columnOne">
			<label class="inputlabelbold"><fmt:message key="productVO.appImages.phoneInAppScreen"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.appImages.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_PHONE_IN_APP_SCREEN%>' <c:if test="${empty productVO.phone_in_app_screen or empty productVO.phone_in_app_screen.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_PHONE_IN_APP_SCREEN%>"
				title="${productVO.phone_in_app_screen.fileFullName}"   
				class="<c:if test="${not empty productVO.phone_in_app_screen and not empty productVO.phone_in_app_screen.mediaId}">${productVO.phone_in_app_screen.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.phone_in_app_screen and not empty productVO.phone_in_app_screen.fileName}">${productVO.phone_in_app_screen.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>
		<div class="columnTwo">
			<label class="inputlabelbold width100"><fmt:message key="productVO.appImages.tabletInAppScreen"/></label><br/>
			<span class="label-info-small" ><fmt:message key="productVO.appImages.format"/></span>
			<div class="clearboth"></div>

			<div class="uploadeded-File" id='uploaded_<%=VAPConstants.PRODUCT_TABLET_IN_APP_SCREEN%>' <c:if test="${empty productVO.tablet_in_app_screen or empty productVO.tablet_in_app_screen.mediaId}">style="display:none;"</c:if>>
				<a
				id="download_<%=VAPConstants.PRODUCT_TABLET_IN_APP_SCREEN%>"
				title="${productVO.tablet_in_app_screen.fileFullName}"    
				class="<c:if test="${not empty productVO.tablet_in_app_screen and not empty productVO.tablet_in_app_screen.mediaId}">${productVO.tablet_in_app_screen.mediaId}</c:if>"
				href="#"><c:if test="${not empty productVO.tablet_in_app_screen and not empty productVO.tablet_in_app_screen.fileName}">${productVO.tablet_in_app_screen.fileName}</c:if></a> 
				<div class="clearboth"></div>
			</div>

			<div class="clearboth"></div>
		</div>

		<div class="clearboth"></div>
	</div>
</div>

<br />
