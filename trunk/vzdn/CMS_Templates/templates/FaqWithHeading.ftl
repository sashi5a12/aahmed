<#ftl ns_prefixes={"D":"http://www.netpace.com/vzdn"}>

<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=en-us xml:lang="en-us" xmlns="http://www.w3.org/1999/xhtml">
<!-- ${root.version} -->
<jsp:include page="/jsps/layout/header.jsp">
	<jsp:param name="title" value="${root.page_title}" />
	<jsp:param name="title" value="${root.keywords}" />
</jsp:include>
<#assign sidebar = root.sidebar>
<#assign index =0>
<style type="text/css"> 
	<!-- 
		a.faq:link {text-decoration: none; color:#000} 
		a.faq:visited {text-decoration: none; color:#000} 
		a.faq:hover {text-decoration:none; color:#000} 
	--> 
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

	<span id="for_title" style='display: none; visibility: hidden'>$TITLE_START$ ${root.page_title} $TITLE_END$</span>

	<!-- BODY START -->
	<div id=homepageWrapper>
		<div id=bodyWrapper>
			<div id=homepageContainer>
				<a name=content></a>
				<div><div id="page_title2"><p>${root.heading}</p></div>
				</div>
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
								<!-- POD CONTENT START HERE -->
								<div class="boxContBM pR10">
									<div class="txtContent">
										<ul>
										<#list root.faqs_type as ft>
											<#list ft.faqs as f>
											<li><a <#if f_index==0>name="Questions"</#if> href="#${index}">${f.question}</a></li>
											<#assign index =index+1>
											</#list>
										</#list>								
										</ul>
										
										<#assign index =0>
										<#list root.faqs_type as ft>
											<#if (ft.heading!?size > 0 && ft.heading?length >0)>
											<div class="sepratorGray"><img src="${r'${pageContext.request.contextPath}'}/images/s.gif${r'${requestScope.RESOURCE_VERSION}'}" width="1" height="1"/></div>
											<div class="clear14"></div>
											<div class="subHeadBlack">${ft.heading}</div>
											<div class="clear14"></div>
											</#if>
											<#list ft.faqs as f>
												<b><a class="faq" name="${index}">${f.question}</a></b><br/>${f.answer?replace("<p> </p>"," ","rifm")}
												<a href="#Questions">Back To Top</a>
												<#if f_has_next>
												<div class="sepratorGray"><img src="${r'${pageContext.request.contextPath}'}/images/s.gif${r'${requestScope.RESOURCE_VERSION}'}" width="1" height="1"/></div>
												</#if>
												<#assign index =index+1>
											</#list>
										</#list>									
									</div>
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
	<!-- BODY END -->
    <!-- BEGIN FOOTER -->
    <c:import url="${r'${requestScope.footer}'}" ></c:import>
    <!-- FOOTER END -->

</DIV>

</BODY>
</HTML>