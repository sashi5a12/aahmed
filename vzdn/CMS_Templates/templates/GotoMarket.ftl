<#ftl ns_prefixes={"D":"http://www.netpace.com/vzdn"}>

<#assign spotlight = root.spotlight_html>

<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=en-us xml:lang="en-us" xmlns="http://www.w3.org/1999/xhtml">
<!-- ${root.version} -->
<jsp:include page="/jsps/layout/header.jsp">
	<jsp:param name="title" value="${root.page_title}" />
	<jsp:param name="keywords" value="${root.keywords}" />
</jsp:include>
<style type="text/css">
	.jcarousel-skin-tango .jcarousel-container-horizontal {
		width: 904px;
		padding: 1px 40px;
	}

	.jcarousel-skin-tango .jcarousel-clip-horizontal {
		width: 100%;
		height: 180px;		
	}
	.jcarousel-skin-tango .jcarousel-item {
		width: 170px;
		height: 180px;	
	}
	.jcarousel-list li, .jcarousel-item {
		height: 180px;
	}
	.jcarousel-skin-tango .jcarousel-next-horizontal {
		
	}
</style>
<BODY>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/wz_tooltip.js${r'${requestScope.RESOURCE_VERSION}'}"></script>
<DIV id="wideLayout">
	<!-- Begin Header Container -->
	<div id="headerContainer">
		<!-- BEGIN GLOBAL NAVIGATION -->		
		<c:import url="${r'${requestScope.header}'}">
			<c:param name="ALFRESCO_VIRTUAL_SERVER_URL" value="${r'${requestScope.ALFRESCO_VIRTUAL_SERVER_URL}'}"/>
		</c:import>		
        <!-- GLOBAL NAVIGATION END -->
    </div>
    <!-- End Header Container -->

	<span id="for_title" style='display: none; visibility: hidden'>$TITLE_START$ ${root.page_title} $TITLE_END$</span>

	<!-- BODY START -->
	<div id=homepageWrapper>
		<div id=bodyWrapper>
			<div id=homepageContainer>
				<a name=content></a>
	
				<!-- SPOTLIGHT START HERE -->
				<div id="page_title"><p>${root.heading}</p></div>
				<#if spotlight?? && spotlight?length != 0>
					<#--<#include "${spotlight}">-->
					<%@ include file="${spotlight}" %>
				</#if>
	
				<!-- SPOTLIGHT END HERE -->
	
				<div class="clear14"></div>
				<div id="contentCol">
					<!-- POD1 START HERE -->
					<div class="box984">
						<div class="box2">
							<div class="boxTop box984"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
							<div class="boxContent">
								<!-- POD1 CONTENT START HERE -->
				                <div class=""> 
				                  <div class="clear14"></div>
				                  <c:set var='isPublic' scope='page' value='false'/>
								  <#assign i=1>
								  <#list root.soft_dev_contents as boxes>
								  	<#if boxes.dev_center_logged_in=='false' || boxes.dev_center_logged_in?length == 0>
								  		<c:set var='isPublic' scope='page' value='true'/>
									<#else>
										<c:set var='isPublic' scope='page' value='false'/>
								  	</#if>											
				                  
								  <div class="podwDev" <#if (i==2 || i==5 || i==8 || i==11 || i==14 || i==17 || i==20)> style="border-right:solid 1px #ccc; border-left:solid 1px #ccc;" </#if>> 
				                    <div class="podwDevBox"> 
				                      <div class="podwDevTitle">
				                      	<c:choose>
											<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
												<#if boxes.dev_center_target=='true'>
													<a class="blackLink" href="${r'${pageContext.request.contextPath}'}${boxes.dev_center_link}" target="_blank">${boxes.title_text}</a>
												<#else>
													<a class="blackLink" href="${r'${pageContext.request.contextPath}'}${boxes.dev_center_link}">${boxes.title_text}</a>
												</#if>
											</c:when>													
											<c:otherwise>
												<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${boxes.dev_center_link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${boxes.title_text}</a>
												<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
													<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
												</span>
											</c:otherwise>
										</c:choose>				                      
				                      </div> 
				                      <div class="clear10"></div>
				                      <div class="podwDevImg">
				                      	<c:choose>
											<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
												<#if boxes.dev_center_target=='true'>
													<a href="${r'${pageContext.request.contextPath}'}${boxes.dev_center_link}"  target="_blank"><img src="${r'${pageContext.request.contextPath}'}${boxes.image}${r'${requestScope.RESOURCE_VERSION}'}" width="109" height="104" alt="${boxes.image_alt_text?html}"/></a>
												<#else>
													<a href="${r'${pageContext.request.contextPath}'}${boxes.dev_center_link}" ><img src="${r'${pageContext.request.contextPath}'}${boxes.image}${r'${requestScope.RESOURCE_VERSION}'}" width="109" height="104" alt="${boxes.image_alt_text?html}"/></a>
												</#if>
											</c:when>													
											<c:otherwise>
												<img src="${r'${pageContext.request.contextPath}'}${boxes.image}${r'${requestScope.RESOURCE_VERSION}'}" width="109" height="104" alt="${boxes.image_alt_text?html}"/>
											</c:otherwise>
										</c:choose>				                      
				                      </div> 
				                      <div class="clear10"></div>
				                      <div class="podwDevTxt">
										${boxes.short_description}
										<div class="clear10"></div>
										<#if (boxes.learn_more_text!?size > 0 && boxes.learn_more_text?length >0)>
										<img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/arrow_button.gif${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="7" align="absmiddle"/>
										<#if boxes.learn_more_logged_in=='false' || boxes.learn_more_logged_in?length == 0>
										<c:set var='isPublic' scope='page' value='true'/>
										<#else>
										<c:set var='isPublic' scope='page' value='false'/>
										</#if>											
										<c:choose>
											<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
												<#if boxes.learn_more_target=='true'>
													<a href="${r'${pageContext.request.contextPath}'}${boxes.learn_more_link}" target="_blank">${boxes.learn_more_text}</a>																
												<#else>
													<a href="${r'${pageContext.request.contextPath}'}${boxes.learn_more_link}">${boxes.learn_more_text}</a>
												</#if>
											</c:when>													
											<c:otherwise>${boxes.learn_more_text}</c:otherwise>
										</c:choose>
										<br />
										</#if>
										
										<#if (boxes.get_sdk_text!?size > 0 && boxes.get_sdk_text?length >0)>
										<img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/arrow_button.gif${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="7" align="absmiddle"/>
										<#if boxes.logged_in=='true'>
										<c:set var='isPublic' scope='page' value='true'/>
										<#else>
										<c:set var='isPublic' scope='page' value='false'/>
										</#if>

										<c:choose>
											<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED)}"}">
												<a href="${r'${pageContext.request.contextPath}'}${boxes.get_sdk_link}">${boxes.get_sdk_text}</a>
											</c:when>
											<c:when test="${r"${not empty isPublic and isPublic eq 'true'}"}">
												<a href="${r'${pageContext.request.contextPath}'}${boxes.get_sdk_link}">${boxes.get_sdk_text}</a>
											</c:when>
											<c:otherwise>
												<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${boxes.get_sdk_link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${boxes.get_sdk_text}</a>
												<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
													<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
												</span>
											</c:otherwise>
										</c:choose>										
										<br />
										</#if>
				                      </div> 
				                    </div> 
				                  </div> 
				                  <#if (i%3==0) && boxes_has_next>
				                  <div class="clear14"></div>
				                  <div class="podwDev"><div class="podwDevBox bTgray"></div></div>
				                  <div class="podwDev"><div class="podwDevBox bTgray"></div></div>
				                  <div class="podwDev"><div class="podwDevBox bTgray"></div></div>
				                  <div class="clear5"></div>
				                  <div class="clear14"></div>
				                  </#if>
				                  <#assign i=i+1>
				                  </#list>
				                  
								  
				                  <div class="clear14"></div> 
								  
				                </div> 				                									
								<!-- POD1 CONTENT END HERE -->
                				<div class="clear10"></div> 
							</div>
							<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
						</div>
					</div>
					<!-- POD1 END HERE -->
          			<div class="clear10"></div>
				</div>
				<div class="clear14"></div>
				<!-- VIDEO SLIDER START -->
				<div id="vod_container">
				  <div id="maincontenttop">
				    <div id="maincontenttop2">
				      <div id="fillertop"></div>
				    </div>
				  </div>
				  <div id="maincontentleft" >
				    <div id="maincontentright">
				      <div class="details_content" style="height: 214px;" align="center">
				        
				        <p class="slide_title" style=" padding-top:7px; padding-left:10px;">
				        	<#list root.deviceSliderPod as dt>${dt.device_title}<#if dt_has_next><span>|</span></#if></#list>
				        </p>
				        <ul id="mycarousel" class="jcarousel-skin-tango"><!-- The content will be dynamically loaded in here --></ul>				        
				      </div>
				    </div>
				    <!-- End Div -->
				    </div>
				    <div id="maincontentbottom">
				      <div id="maincontentbottom2">
				        <div id="fillerbottom"></div>
				      </div>
				    </div>
				  </div>
				</div>
				<!-- VIDEO SLIDER END -->
			</div>
		</div>
	</div>
	<!-- BODY END -->

    <!-- BEGIN FOOTER -->
    <c:import url="${r'${requestScope.footer}'}" ></c:import>
    <!-- FOOTER END -->

</DIV>

</BODY>
</HTML>
<script type="text/javascript">
	var mycarousel_itemList = [
		<#list root.deviceSliderPod as dt>													
			<#list dt.images as img>
				{url: '${r'${pageContext.request.contextPath}'}${img.deviceImage}${r'${requestScope.RESOURCE_VERSION}'}', title: '${img.deviceDisplayName}' , download_link: '${r'${pageContext.request.contextPath}'}${img.link}', alt:"${img.deviceImageAlt?html}", target:<#if img.target=='true'>'true'<#else>'false'</#if>}<#if img_has_next == true>,</#if>			
			</#list>
		</#list>     	
	];
</script>
<%@ include file="/jsps/includes/big_carousel.jsp" %>