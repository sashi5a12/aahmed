<#ftl ns_prefixes={"D":"http://www.netpace.com/vzdn"}>

<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--Sidebar Start Here -->
<div class="box190 float_left mR9">
	<!-- POD START HERE -->
	<div class="box190">
		<div class="box2">
			<div class="boxTop box190"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
			<div class="boxContent">
				<div class="boxContBM pR10">
					<!-- POD CONTENT START HERE -->
					<div class="subHeadBlack">${root.heading}</div>
					<div class="txtContent">
						<br />
						<ul class="arrow">
						<%
							String isPublic="";
							request.setAttribute("isPublic","false");
						%>
						<#list root.sidebar_links as ql>
							<li>
							<#if ql.logged_in=='false' || ql.logged_in?length == 0>
							<% request.setAttribute("isPublic","true");%>
							<#else>
							<% request.setAttribute("isPublic","false");%>
							</#if>
							<c:choose>
								<c:when test="${r"${(not empty sessionScope.IS_USER_AUTHENTICATED) 	or (not empty isPublic and isPublic eq 'true')}"}">
									<#if ql.target=='true'>
										<a href="<%=request.getContextPath()%>${ql.link}" target="_blank">${ql.link_title}</a>
									<#else>
										<a href="<%=request.getContextPath()%>${ql.link}">${ql.link_title}</a>										
									</#if>
								</c:when>													
								<c:otherwise>
									<a class="tooltipLink" href="${r'${pageContext.request.contextPath}'}${ql.link}" onmouseover="Tip('<span class=\'tooltipText\'>To access this resource, please sign in.</span>')" onmouseout="UnTip()">${ql.link_title}</a>
									<span class="lockImage" onmouseover="Tip('<span class=\'tooltipText\'>To access this resource, please sign in.</span>')" onmouseout="UnTip()">
										<img src="${r'${pageContext.request.contextPath}'}/images/shared/locked.gif" border="0"/>
									</span>
								</c:otherwise>
							</c:choose>
							</li>
						</#list>
						</ul>
						<#if (root.go_back_link?exists && root.go_back_link?length > 0)>
						<div class="clear14"></div>
						<ul class="arrow">
							<li><a href="<%=request.getContextPath()%>${root.go_back_link}">${root.go_back_link_text}</a></li>
						</ul>
						</#if>
					</div>
					<!-- POD CONTENT END HERE -->
				</div>
			</div>
			<div class="boxBottom"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
		</div>
	</div>
	<!-- POD END HERE -->
</div>
<!--Sidebar End Here -->