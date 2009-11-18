<#ftl ns_prefixes={"D":"http://www.netpace.com/vzdn"}>

<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=en-us xml:lang="en-us" xmlns="http://www.w3.org/1999/xhtml">
<!-- ${root.version} -->
<jsp:include page="/jsps/layout/header.jsp" >
	<jsp:param name="title" value="${root.page_title}" />
	<jsp:param name="keywords" value="${root.keywords}" />
</jsp:include>

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
							<div class="boxTop box784"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg" width="6" height="6"/></div>
							<div class="boxContent">
								<div class="boxContBM">
									<!-- POD CONTENT START HERE -->									
									<div class="subHeadBlack">${root.main_heading}</div>
									<div class="txtContent pR10">
										<div class="clear"></div>										
										<c:set var='isPublic' scope='page' value='false'/>
										<#if root.main_content_logged_in=='false' || root.main_content_logged_in?length == 0>
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
												<span class="mainLoggedInLink" ><div class="clear14"></div><c:out value="${r'${requestScope.HIDE_MAIN_CONTENT}'}"/></span>
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
																				
										<#list root.contents as con>									
											
											<#list con.sub_contents as contents>
												<#if contents.logged_in=='false' || contents.logged_in?length == 0>										
												<c:set var='isPublic' scope='page' value='true'/>
												<#else>
												<c:set var='isPublic' scope='page' value='false'/>
												</#if>
												
												<c:choose>
													<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) or (not empty isPublic and isPublic eq 'true')}"}">
														<#if (contents.heading!?size > 0)>														
														<strong>${contents.heading}</strong><br/>
														</#if>
														<#if (contents.text!?size > 0)>
														<div class="vzdn_table_content">${contents.text}</div>
														<div class="clear"></div>
														</#if>
														<#list contents.content_links as links>
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
											
											<!-- START CODE CONTENT -->
											<#list con.codes as code>
												<#if code.logged_in=='false' || code.logged_in?length == 0>										
												<c:set var='isPublic2' scope='page' value='true'/>
												<#else>
												<c:set var='isPublic' scope='page' value='false'/>
												</#if>
												<c:choose>
													<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) or (not empty isPublic2 and isPublic2 eq 'true')}"}">
														<#if (code.heading!?size > 0)>														
														<strong>${code.heading}</strong>
														</#if>
														<#if (code.text!?size > 0)>
														<div class="code">${code.text}</div>														
														</#if>														
													</c:when>											
												</c:choose>																						
												<div class="clear14"></div>
											</#list>
											<!-- END CODE CONTENT -->
										</#list>
										<!-- END SUB HEADING CONTENT -->
									</div>
									<!-- POD CONTENT END HERE -->
								</div>
							</div>
							<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg" width="6" height="6"/></div>
						</div>
					</div>
					<!-- POD END HERE -->
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