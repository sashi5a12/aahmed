<%@ page isELIgnored='false' pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import = "org.apache.lucene.document.*, org.apache.lucene.search.ScoreDoc" %>
<%@ include file="/commons/taglibs.jsp"%>


<div><div id="page_title2">
	<p>Search Results</p>
</div>
</div>
<div id="contentCol">
	<!-- POD START HERE -->
	<div class="box984 float_left">
		<div class="box2">
			<div class="boxTop box984"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
			<div class="boxContent">
				<!-- POD CONTENT START HERE -->
				<div class="boxContBM pR10">
					<div class="txtContent">
						<c:choose>
							<c:when test="${empty requestScope.results}"><strong>No Result Found.</strong></c:when>
						</c:choose>
						<c:forEach var="i" items="${requestScope.results}" >
							<h3><a href="${i.path}">${i.title}</a></h3><br/>
							<c:if test="${not empty i.searchStr}">${i.searchStr}<br/></c:if>
							${i.path}									
							<div class="sepratorGray"><img src="<%=request.getContextPath()%>/images/s.gif" width="1" height="1"></div>	
						</c:forEach>
						
					</div>
				</div>
				<c:if test="${not empty requestScope.results and requestScope.links gt 0}">
					<div style="text-align: center">
						<c:if test="${param.page gt 0}">
							<a href="<%=request.getContextPath()%>/search?q=<%=URLEncoder.encode(request.getParameter("q"))%>&page=${param.page-1}"><img height="15" width="15" align="absbottom" src="images/shared/elements/left_button_small.gif"/></a>
						</c:if>
						<c:forEach var="i"	begin="0" end="${requestScope.links}">
							<c:choose>
								<c:when test="${i eq param.page}"><span>${i+1}</span></c:when>
								<c:otherwise>
									<a href='<%=request.getContextPath()%>/search?q=<%=URLEncoder.encode(request.getParameter("q"))%>&page=${i}'>${i+1}</a>
								</c:otherwise>
							</c:choose>&nbsp;&nbsp;
						</c:forEach>
						
						<c:if test="${(requestScope.links gt 0) && (requestScope.links ne param.page)}">
							<a href="<%=request.getContextPath()%>/search?q=<%=URLEncoder.encode(request.getParameter("q"))%>&page=${param.page+1}"><img height="15" width="15" align="absbottom" src="images/shared/elements/right_button_small.gif"/></a>
						</c:if>
					</div>
				</c:if>
				<!-- POD CONTENT END HERE -->
			</div>
			<div class="boxBottom"><img src="<%=request.getContextPath()%>/images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
		</div>
	</div>
	<br/>
	<br/>	
	<strong>Search Further In<strong>
	
	<strong><a href="<c:out value="${requestScope.forumQuery}"/>">Forums</a></strong> | 
	<strong><a href="<c:out value="${requestScope.blogQuery}"/>">Blogs</a></strong>
	<!-- POD END HERE -->
</div>
