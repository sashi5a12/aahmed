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
		width: 405px;
		padding: 15px 40px;
	}

	.jcarousel-skin-tango .jcarousel-clip-horizontal {
		width: 100%;
		height: 145px;		
	}
	.jcarousel-skin-tango .jcarousel-item {
		width: 125px;
		height: 145px;	
	}
	.jcarousel-list li, .jcarousel-item {
		height: 145px;
	}
	.jcarousel-skin-tango .jcarousel-next-horizontal {
		right: -8px;
	}
	.jcarousel-skin-tango .jcarousel-prev-horizontal {
		left: 0px;
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
	<div id="homepageWrapper">
		<div id="bodyWrapper">
			<div id="homepageContainer">
				<!-- SPOTLIGHT START HERE -->
				<div id="page_title">
					<p>${root.heading}</p>
				</div>			
				<#if spotlight?? && spotlight?length != 0>
					<#--<#include "${spotlight}">-->
					<%@ include file="${spotlight}" %>
				</#if>
				
				<!-- SPOTLIGHT END HERE -->
				<div class="clear14"></div>
				<div id="contentCol">
	
					<!-- POD START HERE -->
					<div class="box239 float_left mR9">
						<div class="box2">
							<div class="boxTop box239"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
							<div class="boxContent boxH192">
								<div class="boxContBM pR10">
									<!-- POD CONTENT START HERE -->
									<div class="subHeadBlack">${root.gettingStarted.pod_title}</div>
									<div class="txtContent">
										<br />
										<ul class="arrow">
											<c:set var='isPublic' scope='page' value='false'/>										
											<#list root.gettingStarted.links as links>
												<li>
												<#if links.logged_in=='false' || links.logged_in?length == 0>
												<c:set var='isPublic' scope='page' value='true'/>
												<#else>
												<c:set var='isPublic' scope='page' value='false'/>
												</#if>											
												<c:choose>
													<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
														<#if links.target=='true'>
															<a href="${links.link}" target="_blank">${links.display_name}</a>
														<#else>
															<a href="${r'${pageContext.request.contextPath}'}${links.link}">${links.display_name}</a>
														</#if>
													</c:when>													
													<c:otherwise>
														<div>
															<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${links.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${links.display_name}</a>
															<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
																<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
															</span>
														</div>
													</c:otherwise>
												</c:choose>
											    </li>
											</#list>
										</ul>
									</div>
									<!-- POD CONTENT END HERE -->
								</div>
							</div>
							<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
						</div>
					</div>
					<!-- POD END HERE -->
	
					<!-- POD START HERE -->
					<div class="box239 float_left mR10">
						<div class="box2">
							<div class="boxTop box239"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
							<div class="boxContent boxH192">
								<div class="boxContBM pR10">
									<!-- POD CONTENT START HERE -->
									<div class="subHeadBlack">${root.techResources.pod_title}</div>
									<div class="txtContent">
										<br />
										<ul class="arrow">
											<c:set var='isPublic' scope='page' value='false'/>
											<#list root.techResources.links as links>
												<li>
												<#if links.logged_in=='false' || links.logged_in?length == 0>
												<c:set var='isPublic' scope='page' value='true'/>
												<#else>
												<c:set var='isPublic' scope='page' value='false'/>
												</#if>											
												<c:choose>
													<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
														<#if links.target=='true'>
															<a href="${links.link}" target="_blank">${links.display_name}</a>
														<#else>
															<a href="${r'${pageContext.request.contextPath}'}${links.link}">${links.display_name}</a>
														</#if>
													</c:when>													
													<c:otherwise>
														<div>
															<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${links.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${links.display_name}</a>
															<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
																<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
															</span>
														</div>
													</c:otherwise>
												</c:choose>
											    </li>
											</#list>
										</ul>
									</div>
									<!-- POD CONTENT END HERE -->
								</div>
							</div>
							<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
						</div>
					</div>
					<!-- POD END HERE -->
	
					<!-- POD START HERE -->
					<div class="box239 float_left mR9">
						<div class="box2">
							<div class="boxTop box239"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
							<div class="boxContent boxH192">
								<div class="boxContBM pR10">
									<!-- POD CONTENT START HERE -->
									<div class="subHeadBlack">${root.downloads.pod_title}</div>
									<div class="txtContent"><br />
										<ul class="arrow">
											<c:set var='isPublic' scope='page' value='false'/>
											<#list root.downloads.links as links>
												<li>
												<#if links.logged_in=='false' || links.logged_in?length == 0>
												<c:set var='isPublic' scope='page' value='true'/>
												<#else>
												<c:set var='isPublic' scope='page' value='false'/>
												</#if>											
												<c:choose>
													<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
														<#if links.target=='true'>
															<a href="${links.link}" target="_blank">${links.display_name}</a>
														<#else>
															<a href="${r'${pageContext.request.contextPath}'}${links.link}">${links.display_name}</a>
														</#if>
													</c:when>													
													<c:otherwise>
														<div>
															<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${links.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${links.display_name}</a>															
															<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
																<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
															</span>
														</div>													
													</c:otherwise>
												</c:choose>
											    </li>
											</#list>										
										</ul>
									</div>
									<!-- POD CONTENT END HERE -->
								</div>
							</div>
							<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
						</div>
					</div>
					<!-- POD END HERE -->
	
					<!-- POD START HERE -->
					<div class="box239 float_left">
						<div class="box2">
							<div class="boxTop box239"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
							<div class="boxContent boxH192">
								<div class="boxContBM pR10">
									<!-- POD CONTENT START HERE -->
									<div class="subHeadBlack">${root.messageCenter.pod_title}</div>
									<div class="txtContent"><br />
										<ul class="arrow">
											<c:set var='isPublic' scope='page' value='false'/>
											<#list root.messageCenter.links as links>
												<li>
												<#if links.logged_in=='false' || links.logged_in?length == 0>
												<c:set var='isPublic' scope='page' value='true'/>
												<#else>
												<c:set var='isPublic' scope='page' value='false'/>
												</#if>											
												<c:choose>
													<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
														<#if links.target=='true'>
															<a href="${links.link}" target="_blank">${links.display_name}</a>
														<#else>
															<a href="${r'${pageContext.request.contextPath}'}${links.link}">${links.display_name}</a>
														</#if>
													</c:when>													
													<c:otherwise>
														<div>
															<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${links.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${links.display_name}</a>															
															<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
																<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
															</span>
														</div>													
													</c:otherwise>
												</c:choose>
											    </li>
											</#list>										
										</ul>
									</div>
									<!-- POD CONTENT END HERE -->
								</div>
							</div>
							<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
						</div>
					</div>
					<!-- POD END HERE -->
					<div class="clear14"></div>
					<!-- POD START HERE -->
					<div class="box239 float_left mR9">
						<div class="box2">
							<div class="boxTop box239"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
							<div class="boxContent boxH192">
								<div class="boxContBM pR10">
									<!-- POD CONTENT START HERE -->
									<div class="subHeadBlack">${root.communitySupport.pod_title}</div>
									<div class="txtContent"><br />
										<ul class="arrow">
											<c:set var='isPublic' scope='page' value='false'/>
											<#list root.communitySupport.links as links>
												<li>
												<#if links.logged_in=='false' || links.logged_in?length == 0>
												<c:set var='isPublic' scope='page' value='true'/>
												<#else>
												<c:set var='isPublic' scope='page' value='false'/>
												</#if>											
												<c:choose>
													<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
														<#if links.target=='true'>
															<a href="${links.link}" target="_blank">${links.display_name}</a>
														<#else>
															<a href="${r'${pageContext.request.contextPath}'}${links.link}">${links.display_name}</a>
														</#if>
													</c:when>													
													<c:otherwise>
														<div>
															<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${links.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${links.display_name}</a>															
															<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
																<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
															</span>
														</div>
													</c:otherwise>
												</c:choose>
											    </li>
											</#list>										
																				
											<!--<li><a href="#">Developer Blogs (See section 2.1.4)</a></li>
											<li><a href="#">Forums (See section 2.1.5)</a></li>
											<li><a href="#">Knowledge Base (Section 2.1.6)</a></li>-->
											
											<!--<li><a href="#">Issue Resolution System</a>
												<br />&#8226;
												<a href="#">Email Based Support</a>
												<br />&#8226;
												<a href="#">Phone Support</a>
											</li>-->
											
											<#list root.issueResolution as links>												
												<c:set var='isPublic' scope='page' value='false'/>											
												<#if links.logged_in=='false' || links.logged_in?length == 0>
												<c:set var='isPublic' scope='page' value='true'/>
												<#else>
												<c:set var='isPublic' scope='page' value='false'/>
												</#if>
												
												<#if (links.display_name!?size > 0 && links.display_name?length >0)>
												<c:choose>
													<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
														<#if links.target=='true'>
															<li><a href="${links.link}" target="_blank">${links.display_name}</a></li>
														<#else>
															<li><a href="${r'${pageContext.request.contextPath}'}${links.link}">${links.display_name}</a></li>
														</#if>
													</c:when>													
													<c:otherwise>
														<li>
															<div>
																<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${links.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${links.display_name}</a>																
																<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
																	<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
																</span>
															</div>
														</li>													
													</c:otherwise>
												 </c:choose>
												</#if>
												
												<c:set var='isPublic' scope='page' value='false'/>
												<#list root.issueResolutionLinks as links1>
													<#if (links1.display_name!?size > 0 && links1.display_name?length >0)>
													<br />&#8226;
													</#if>
													
													<#if links1.logged_in=='false' || links1.logged_in?length == 0>
													<c:set var='isPublic' scope='page' value='true'/>
													<#else>
													<c:set var='isPublic' scope='page' value='false'/>
													</#if>
													
													<#if (links1.display_name!?size > 0 && links1.display_name?length >0)>											
													<c:choose>
														<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
															<#if links1.target=='true'>
																<li><a href="${links1.link}" target="_blank">${links1.display_name}</a></li>
															<#else>
																<li><a href="${r'${pageContext.request.contextPath}'}${links1.link}">${links1.display_name}</a></li>
															</#if>
														</c:when>													
														<c:otherwise>
															<li>
																<div>
																	<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${links1.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${links1.display_name}</a>																	
																	<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
																		<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
																	</span>
																</div>																
															</li>
														</c:otherwise>
													</c:choose>
													</#if>
												</#list>
											</#list>
										</ul>
									</div>
									<!-- POD CONTENT END HERE -->
								</div>
							</div>
							<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
						</div>
					</div>
					<!-- POD END HERE -->
	
					<!-- POD START HERE -->
					<div class="box239 float_left mR10">
						<div class="box2">
							<div class="boxTop box239"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
							<div class="boxContent boxH192">
								<div class="boxContBM pR10">
									<!-- POD CONTENT START HERE -->
									<div class="subHeadBlack">${root.gotoMarket.pod_title}</div>
									<div class="txtContent"><br />
										<ul class="arrow">
											<c:set var='isPublic' scope='page' value='false'/>
											<#list root.gotoMarket.links as links>
												<li>
												<#if links.logged_in=='false' || links.logged_in?length == 0>
												<c:set var='isPublic' scope='page' value='true'/>
												<#else>
												<c:set var='isPublic' scope='page' value='false'/>
												</#if>											
												<c:choose>
													<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
														<#if links.target=='true'>
															<a href="${links.link}" target="_blank">${links.display_name}</a>
														<#else>
															<a href="${r'${pageContext.request.contextPath}'}${links.link}">${links.display_name}</a>
														</#if>
													</c:when>													
													<c:otherwise>
														<div>
															<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${links.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${links.display_name}</a>															
															<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
																<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
															</span>
														</div>													
													</c:otherwise>
												</c:choose>
											    </li>
											</#list>
										</ul>
									</div>
									<!-- POD CONTENT END HERE -->
								</div>
							</div>
							<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
						</div>
					</div>
					<!-- POD END HERE -->
	
					<div class="box487 float_left ">
						<!-- VIDEO SLIDER START -->
						<div>
							<div id="vod_container487">
								<div id="maincontenttop">
									<div id="maincontenttop2">
										<div id="fillertop"></div>
									</div>
								</div>
								<div id="maincontentleft">
									<div id="maincontentright">
										<div class="details_content" style="height: 203px;" align="center">
											<p class="slide_title" style="padding-top: 7px; padding-left: 10px;">										
											<#list root.deviceSliderPod as dt>${dt.device_title}
											<#if dt_has_next><span>|</span></#if>																						
											</#list>
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
		</div>
	</div>
	<!-- BODY END -->
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
<%@ include file="/jsps/includes/small_carousel.jsp" %>