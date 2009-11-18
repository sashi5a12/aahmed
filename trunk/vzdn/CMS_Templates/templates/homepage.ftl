	<#ftl ns_prefixes={"D":"http://www.netpace.com/vzdn"}>

<#assign spotlight = root.spotlight_html>
<#assign coming_soon = root.coming_soon>

<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=en-us xml:lang="en-us" xmlns="http://www.w3.org/1999/xhtml">
<!-- ${root.version} -->
<jsp:include page="/jsps/layout/header.jsp" >
	<jsp:param name="title" value="${root.page_title}" />
	<jsp:param name="keywords" value="${root.keywords}" />
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
	<DIV id=homepageWrapper>
		<DIV id=bodyWrapper>
			<DIV id=homepageContainer>
				<!-- SPOTLIGHT START HERE -->	
				<div id="page_title">
					<p>${root.heading}</p>
				</div>
				<#if spotlight?? && spotlight?length != 0>
					<#--<#include "${spotlight}">-->
					<%@ include file="${spotlight}" %>					
				</#if>
				
				<!-- SPOTLIGHT END HERE -->
				<div class="clear10"></div>
				<c:set var='isPublic' scope='page' value='false'/>				
				<#list root.devCenter as dc>
				<!-- POD START HERE -->
				<#if dc.logged_in=='false' || dc.logged_in?length == 0>
				<c:set var='isPublic' scope='page' value='true'/>
				<#else>
				<c:set var='isPublic' scope='page' value='false'/>				
				</#if>
				<div class="box321 float_left<#if ((dc_index+1)%3)!=0> mR10</#if>">
					<div class="box2">
						<div class="boxTop box321"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLT.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
						<div class="boxContent">
							<div class="pR10 boxContBM">
								<!-- POD CONTENT START HERE -->
								<div class="subHead">${dc.title}</div>
								<div class="clear10"></div>
								<div class="imgIcon">
								<c:choose>
									<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
										<#if dc.target=='true'>
											<a href="${r'${pageContext.request.contextPath}'}${dc.learn_more_link}" target="_blank"><img src="${r'${pageContext.request.contextPath}'}${dc.image}${r'${requestScope.RESOURCE_VERSION}'}" width="195" height="126" alt="${dc.image_alt?html}"/></a>
										<#else>
											<a href="${r'${pageContext.request.contextPath}'}${dc.learn_more_link}"><img src="${r'${pageContext.request.contextPath}'}${dc.image}${r'${requestScope.RESOURCE_VERSION}'}" width="195" height="126" alt="${dc.image_alt?html}"/></a>
										</#if>
									</c:when>													
									<c:otherwise>
										<img src="${r'${pageContext.request.contextPath}'}${dc.image}${r'${requestScope.RESOURCE_VERSION}'}" width="195" height="126" alt="${dc.image_alt?html}"/>
									</c:otherwise>
								</c:choose>
								</div>
								<div class="clear10"></div>
								<div class="txtContent">
									${dc.description} <br/><br/>											
									<img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/arrow_button.gif${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="7" align="absmiddle"/>
									<c:choose>
										<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
											<#if dc.target=='true'>
												<a href="${r'${pageContext.request.contextPath}'}${dc.learn_more_link}" target="_blank">${dc.link_text}</a>
											<#else>
												<a href="${r'${pageContext.request.contextPath}'}${dc.learn_more_link}">${dc.link_text}</a>
											</#if>
										</c:when>													
										<c:otherwise>
											<div>
												<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${dc.learn_more_link}" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">${dc.link_text}</a>
												<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>${r'${requestScope.SIGNIN_REQUIRED_TEXT}'}</span>')" onmouseout="UnTip()">
													<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif${r'${requestScope.RESOURCE_VERSION}'}" border="0"/>
												</span>
											</div>										
										</c:otherwise>
									</c:choose>
								</div>
								<!-- POD CONTENT END HERE -->
							</div>
						</div>
						<div class="boxBottom"><img src="${r'${pageContext.request.contextPath}'}/images/shared/elements/boxCLB.jpg${r'${requestScope.RESOURCE_VERSION}'}" width="6" height="6"/></div>
					</div>
				</div>
				
				<#if ((dc_index+1)%3)==0>
				<div class="clear14"></div>
				</#if>
				</#list>
				<!-- POD END HERE -->
			</DIV>
		</DIV>
	</DIV>			
	<!-- BODY END -->
    <!-- BEGIN FOOTER -->
    <c:import url="${r'${requestScope.footer}'}" ></c:import>
    <!-- FOOTER END -->

</DIV>

</BODY>
</HTML>