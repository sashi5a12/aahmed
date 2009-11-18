<#ftl ns_prefixes={"D":"http://www.netpace.com/vzdn"}>

<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=en-us xml:lang="en-us" xmlns="http://www.w3.org/1999/xhtml">
<!-- ${root.version} -->
<jsp:include page="/jsps/layout/header.jsp" >
	<jsp:param name="title" value="${root.page_title}" />
	<jsp:param name="title" value="${root.keywords}" />
</jsp:include>

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
					<div class="box190 float_left mR9">
						<!-- POD START HERE -->
						<div class="box190">
							<div class="box2">
								<div class="boxTop box190"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
								<div class="boxContent">
									<!-- POD CONTENT START HERE -->
									<div class="boxContBM pR10">
										<div class="subHeadBlack">${root.quick_links_title}</div>
										<div class="txtContent">
											<br />
											<ul class="arrow">
												<c:set var='isPublic' scope='page' value='false'/>
												<#list root.quick_links as ql>
												<li>
												<#if ql.logged_in=='false' || ql.logged_in?length == 0>
												<c:set var='isPublic' scope='page' value='true'/>
												<#else>
												<c:set var='isPublic' scope='page' value='false'/>
												</#if>
												<c:choose>
													<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
														<#if ql.target=='true'>
															<a href="${r'${pageContext.request.contextPath}'}${ql.link}" target="blank">${ql.link_title}</a>
														<#else>
															<a href="${r'${pageContext.request.contextPath}'}${ql.link}">${ql.link_title}</a>										
														</#if>
													</c:when>													
													<c:otherwise>
														<div>
															<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${ql.link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${ql.link_text}</a>
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
									</div>
									<!-- POD CONTENT END HERE -->
								</div>
								<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
							</div>
						</div>
						<!-- POD END HERE -->
					</div>
	
					<!-- POD START HERE -->
					<div class="box784 float_left">
						<div class="box2">
							<div class="boxTop box784"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
							<div class="boxContent">
								<!-- POD CONTENT START HERE -->
								<div class="boxContBM pR10">
									<c:set var='isPublic' scope='page' value='false'/>
	
									<#list root.documents as doc>
									<div class="subHeadBlack">${doc.title}</div>
									<div class="txtContent">
										${doc.version}
										<br/>
										${doc.date}
										<br/>
										<br/>
										${doc.text}
										<br/>
										
										<#if doc.logged_in=='false' || doc.logged_in?length == 0>
										<c:set var='isPublic' scope='page' value='true'/>
										<#else>
										<c:set var='isPublic' scope='page' value='false'/>
										</#if>
										
										<#if doc_has_next==false>
										<br/>
										</#if>
										<c:choose>
											<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
												<#if doc.target=='true'>
													<img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/arrow_down.gif${r'${requestScope.RESOURCE_VERSION}'}" width="18" height="18" align="absmiddle"/>									
													<a href="${r'${pageContext.request.contextPath}'}${doc.download_location}" target="blank">${doc.download_link_text}</a>
													<img src="${r'${pageContext.request.contextPath}'}/images/pdf-icon.gif${r'${requestScope.RESOURCE_VERSION}'}" width="16" height="16" align="absmiddle"/>
												<#else>
													<img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/arrow_down.gif${r'${requestScope.RESOURCE_VERSION}'}" width="18" height="18" align="absmiddle"/>									
													<a href="${r'${pageContext.request.contextPath}'}${doc.download_location}">${doc.download_link_text}</a>
													<img src="${r'${pageContext.request.contextPath}'}/images/pdf-icon.gif${r'${requestScope.RESOURCE_VERSION}'}" width="16" height="16" align="absmiddle"/>
												</#if>
											</c:when>													
											<c:otherwise>
												<img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/arrow_down.gif${r'${requestScope.RESOURCE_VERSION}'}" width="18" height="18" align="absmiddle"/>
												<div>
													<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${doc.download_location}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${doc.download_link_text}</a>
													<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
														<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
													</span>
												</div>
												<img src="${r'${pageContext.request.contextPath}'}/images/pdf-icon.gif${r'${requestScope.RESOURCE_VERSION}'}" width="16" height="16" align="absmiddle"/>
											</c:otherwise>
										</c:choose>
										
										<#if doc_has_next>									
										<div class="sepratorGray"><img src="${r'${pageContext.request.contextPath}'}/images/s.gif${r'${requestScope.RESOURCE_VERSION}'}" width="1" height="1"/></div>
										</#if>
										
									</div>
									</#list>
								</div>
								<!-- POD CONTENT END HERE -->
							</div>
							<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
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