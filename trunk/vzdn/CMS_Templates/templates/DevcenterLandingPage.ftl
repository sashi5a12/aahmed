<#ftl ns_prefixes={"D":"http://www.netpace.com/vzdn"}>

<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=en-us xml:lang="en-us" xmlns="http://www.w3.org/1999/xhtml">
<#if root.verizon?has_content>
<!-- ${root.version} -->
</#if>
<jsp:include page="/jsps/layout/header.jsp" >
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
<#assign sidebar = root.sidebar>

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
				<div><div id="page_title2"><p>${root.heading}</p></div></div>
				<div id="contentCol">
					<#if sidebar?? && sidebar?length != 0>
						<#--<#include "${sidebar}">-->
						<jsp:include page="${sidebar}" />
					</#if>
					<!-- POD START HERE -->
					<div class="box784 float_left">
						<div class="box2">
							<div class="boxTop box784"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
							<div class="boxContent">
								<div class="boxContBM">
									<!-- POD CONTENT START HERE -->									
									<div class="subHeadBlack">${root.main_heading}</div>
									<div class="txtContent pR10">
										<div class="clear"></div>
										
										
										<#if root.main_content_logged_in?has_content==false || root.main_content_logged_in=='false' || root.main_content_logged_in?length == 0>
										<c:set var='isPublic' scope='page' value='true'/>
										<#else>
										<c:set var='isPublic' scope='page' value='false'/>
										</#if>			
																		
										<c:choose>
											<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
												<#if (root.main_content!?size > 0 && root.main_content?length >0)>
												<div class="vzdn_table_content">
												${root.main_content}
												</div>										
												</#if>
											</c:when>
											<c:otherwise>
												<span class="mainLoggedInLink" ><div class="clear14"></div><c:out value="${r'${requestScope.HIDE_MAIN_CONTENT}'}"/> </span>
											</c:otherwise>
										</c:choose>
										
										<#if (root.main_content_links!?size > 0)>
										<div class="clear"></div>
										</#if>
										<!-- START MAIN CONTENT LINKS -->
										<c:set var='isPublic' scope='page' value='false'/>
										<#list root.main_content_links as links>											
											<#if links.logged_in=='false' || links.logged_in?length == 0>
											<c:set var='isPublic' scope='page' value='true'/>
											<#else>
											<c:set var='isPublic' scope='page' value='false'/>
											</#if>											
											<c:choose>
												<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
													<#if links.target=='true'>																					
														<a href="${links.link}" target="_blank">${links.link_text}</a>
													<#else>
														<a href="${r'${pageContext.request.contextPath}'}${links.link}">${links.link_text}</a>
													</#if>
												</c:when>
												<c:otherwise>
													<div>
														<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${links.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${links.link_text}</a>														
														<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
															<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
														</span>
													</div>																
												</c:otherwise>
											</c:choose>
											<#if links_has_next == true>
											<div class="clear"></div>
											</#if>
										</#list>
										<!-- END MAIN CONTENT LINKS -->
										<div class="clear14"></div>										
										<!-- START SUB HEADING CONTENT -->
										<c:set var='isPublic' scope='page' value='false'/>
										<c:set var='isPublic2' scope='page' value='false'/>
																					
										<#list root.sub_contents as sh>
										
										<#if sh.logged_in=='false' || sh.logged_in?length == 0>										
										<c:set var='isPublic2' scope='page' value='true'/>
										<#else>
										<c:set var='isPublic2' scope='page' value='false'/>
										</#if>
										
										<c:choose>
											<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) or (not empty isPublic2 and isPublic2 eq 'true')}"}">
												<#if (sh.heading!?size > 0)>
												<strong>${sh.heading}</strong><br/>
												</#if>
												<#if (sh.text!?size > 0)>
												<div class="vzdn_table_content">${sh.text}</div>
												<div class="clear"></div>
												</#if>
												<#list sh.content_links as links>
												<#if links.logged_in=='false' || links.logged_in?length == 0>
												<c:set var='isPublic' scope='page' value='true'/>
												<#else>
												<c:set var='isPublic' scope='page' value='false'/>
												</#if>											
												<c:choose>
													<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) or (not empty isPublic and isPublic eq 'true')}"}">
														<#if links.target=='true'>																					
															<a href="${links.link}" target="_blank">${links.link_text}</a>
														<#else>
															<a href="${r'${pageContext.request.contextPath}'}${links.link}">${links.link_text}</a>
														</#if>
													</c:when>
													<c:otherwise>
														<div>
															<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${links.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${links.link_text}</a>															
															<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
																<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
															</span>
														</div>																
													</c:otherwise>
												</c:choose>
												<#if links_has_next == true>
												<div class="clear"></div>
												</#if>
												</#list>
											</c:when>											
										</c:choose>
										<div class="clear14"></div>
										</#list>
										<!-- END SUB HEADING CONTENT -->
										
										<!-- START POD CONTENT -->
										<c:set var='isPublic' scope='page' value='false'/>
										<c:set var='isPublic2' scope='page' value='false'/>

										<#list root.pod_contents as pc>

										<#if pc.logged_in=='false' || pc.logged_in?length == 0>										
										<c:set var='isPublic2' scope='page' value='true'/>
										<#else>
										<c:set var='isPublic2' scope='page' value='false'/>
										</#if>

										<c:choose>
											<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) or (not empty isPublic2 and isPublic2 eq 'true')}"}">
												<div class="vzdn_box762">
													<div class="vzdn_blueBox">
														<div class="vzdn_blueBoxTop vzdn_box762"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxBlue_CLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
														<div class="vzdn_blueBoxContent">
															<div class="pR10">
																<#if (pc.heading!?size > 0)>
																<p class="slide_title">${pc.heading}</p>
																<div class="clear"></div>
																</#if>
																<#if (pc.text!?size > 0)>
																<div class="vzdn_table_content">${pc.text}</div>
																<div class="clear10"></div>
																</#if>									
																<#list pc.content_links as links>
																<#if links.logged_in=='false' || links.logged_in?length == 0>
																<c:set var='isPublic' scope='page' value='true'/>
																<#else>
																<c:set var='isPublic' scope='page' value='false'/>
																</#if>
													
																<c:choose>
																	<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) or (not empty isPublic and isPublic eq 'true')}"}">
																		<#if links.target=='true'>																					
																			<a href="${links.link}" target="_blank">${links.link_text}</a>
																		<#else>
																			<a href="${r'${pageContext.request.contextPath}'}${links.link}">${links.link_text}</a>
																		</#if>
																	</c:when>
																	<c:otherwise>
																		<div>
																			<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${links.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${links.link_text}</a>																			
																			<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
																				<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
																			</span>
																		</div>
																	</c:otherwise>
																</c:choose>													
																<#if links_has_next == true>
																<div class="clear"></div>
																</#if>
																</#list>
															</div>
														</div>	
														<div class="vzdn_blueBoxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxBlue_CLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
													</div>
												</div>
											</c:when>											
										</c:choose>																				
										<div class="clear14"></div>
										</#list>
										<!-- END POD CONTENT -->
										
										
										<!-- START DOC CONTENT -->
										<c:set var='isPublic' scope='page' value='false'/>
										<c:set var='isPublic2' scope='page' value='false'/>
																				
										<#list root.doc_pod_contents as doc>

										<#if doc.logged_in=='false' || doc.logged_in?length == 0>										
										<c:set var='isPublic2' scope='page' value='true'/>
										<#else>
										<c:set var='isPublic2' scope='page' value='false'/>
										</#if>
										
										<c:choose>
											<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) or (not empty isPublic2 and isPublic2 eq 'true')}"}">
												<div class="vzdn_box762">
													<div class="vzdn_blueBox">
														<div class="vzdn_blueBoxTop vzdn_box762"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxBlue_CLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
														<div class="vzdn_blueBoxContent">
															<div class="pR10">
																<#if (doc.heading!?size > 0)>	
																<p class="slide_title">${doc.heading}</p>
																<div class="clear10"></div>
																</#if>
																<#if (doc.text!?size > 0)>
																${doc.text}
																<div class="clear10"></div>
																</#if>
															</div>
														</div>	
														<div class="vzdn_blueBoxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxBlue_CLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
													</div>
												</div>
											</c:when>											
										</c:choose>
										<div class="clear14"></div>
										</#list>
										<!-- END DOC CONTENT -->
																				
									</div>
									<!-- POD CONTENT END HERE -->
								</div>
							</div>
							<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
						</div>
					</div>
					<!-- POD END HERE -->
				<#if (root.deviceSliderPod!?size > 0)  && (root.deviceSliderPod.device_title!?size > 0)>	
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
				</#if>									
				</div>
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
				{url: '${r'${pageContext.request.contextPath}'}${img.deviceImage}${r'${requestScope.RESOURCE_VERSION}'}', title: '${img.deviceDisplayName}' , download_link: '${r'${pageContext.request.contextPath}'}${img.link}', alt:"${img.deviceImageAlt}", target:<#if img.target=='true'>'true'<#else>'false'</#if>}<#if img_has_next == true>,</#if>			
			</#list>
		</#list>     	
	];
</script>
<%@ include file="/jsps/includes/big_carousel.jsp" %>