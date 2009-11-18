<#ftl ns_prefixes={"D":"http://www.netpace.com/vzdn"}>

<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML lang=en-us xml:lang="en-us" xmlns="http://www.w3.org/1999/xhtml">

<jsp:include page="/jsps/layout/header.jsp" >
	<jsp:param name="title" value="${root.page_title}" />
	<jsp:param name="title" value="${root.keywords}" />
</jsp:include>

<BODY>
<DIV id="wideLayout">
	<!-- Begin Header Container -->
	<div id="headerContainer">
		<!-- BEGIN GLOBAL NAVIGATION -->		
		<c:import url="<%=(String)request.getAttribute("header")%>">
			<c:param name="ALFRESCO_VIRTUAL_SERVER_URL" value="<%=(String)request.getAttribute("ALFRESCO_VIRTUAL_SERVER_URL")%>"/>
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
								<div class="boxTop box190"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
								<div class="boxContent">
									<div class="boxContBM pR10">
										<!-- POD CONTENT START HERE -->
										<div class="subHeadBlack">${root.quick_links_title}</div>
										<div class="txtContent">
											<br />
											<ul class="arrow">
											<#list root.quick_links as ql>
												<li><a href="<%=request.getContextPath()%>${ql.link}">${ql.link_title}</a></li>											
											</#list>
											</ul>
										</div>
										<!-- POD CONTENT END HERE -->
									</div>
								</div>
								<div class="boxBottom"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
							</div>
						</div>
						<!-- POD END HERE -->
					</div>
	
					<!-- POD START HERE -->
					<div class="box784 float_left">
						<div class="box2">
							<div class="boxTop box784"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
							<div class="boxContent">
								<div class="boxContBM">
									<!-- POD CONTENT START HERE -->
									<div id="pager">
										<table width="100%" border="0" cellspacing="0" cellpadding="0" class="pR10">
											<tr>
												<td align="right">
													<img src="<%=request.getContextPath()%>/images/shared/elements/arrow_button.gif" width="6" height="7" align="absmiddle">
													<a href="javascript:window.print()">Print</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<img src="<%=request.getContextPath()%>/images/shared/elements/arrow_button.gif" width="6" height="7" align="absmiddle">
													<a href="mailto:${root.email}">Email</a>
												</td>
											</tr>
										</table>
									</div>
									<p class="sepratorGray mR10"><img src="<%=request.getContextPath()%>/images/s.gif" width="1" height="1"></p>
									<div class="subHeadBlack">${root.mainHeading}</div>
									<div class="txtContent pR10">
										<br />
										${root.mainHeadingText}
										<br/>
										<#list root.subHeadings as sh>
										<strong>${sh.subHeading}</strong>
										<br/>									
										${sh.text}
										<br>
										<#if sh_has_next>
										<div class="sepratorGray"><img src="<%=request.getContextPath()%>/images/s.gif" width="1" height="1"></div>
										</#if>
										</#list>
									</div>
									<!-- POD CONTENT END HERE -->
								</div>
							</div>
							<div class="boxBottom"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
						</div>
					</div>
					<!-- POD END HERE -->
				</div>
			</div>
		</div>
	</div>
	<!-- BODY END -->
    <!-- BEGIN FOOTER -->
    <c:import url="<%=(String)request.getAttribute("footer")%>" ></c:import>
    <!-- FOOTER END -->

</DIV>

</BODY>
</HTML>