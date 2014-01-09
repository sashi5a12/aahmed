<%@ tag import="org.springframework.util.StringUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="pageModel" required="true" type="com.netpace.device.vo.PageModel" %>

<%
float nrOfPages = (float) pageModel.getTotalRecords() / pageModel.getPageSize();
int maxLinkedPages = 5;
int pageCount = (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages);
request.setAttribute("pageCount", pageCount);
int pageIndex = Integer.parseInt(pageModel.getPage());
request.setAttribute("pageIndex", pageIndex);
int firstLinkedPage = Math.max(0, pageIndex - (maxLinkedPages / 2));
request.setAttribute("firstLinkedPage", firstLinkedPage);
int lastLinkedPage = Math.min(firstLinkedPage + maxLinkedPages - 1, pageCount - 1);
request.setAttribute("lastLinkedPage", lastLinkedPage);
%>

<c:if test="${pageCount > 1}">
    
 <div class="clearboth"></div>

 <!-- pagination starts -->
 <div class="pagination">
    
    <div>
        
        <c:choose>
            <c:when test="${pageIndex > firstLinkedPage}">
                <div class="prev"><a href="javascript:void(0)" onclick="gotoPage(<%=String.valueOf(pageIndex-1)%>)">Previous</a></div>
            </c:when>
            <c:otherwise>
                <div class="prev"><a href="javascript:void(0)">Previous</a></div>
            </c:otherwise>
        </c:choose>
        <c:forEach begin="${firstLinkedPage}" end="${lastLinkedPage}" var="i">
            <c:choose>
                <c:when test="${pageIndex == i}">
                    <a class="button small marginLeft5" href="javascript:void(0)" ><span class="red">${i+1}</span></a>
                </c:when>
                <c:otherwise>
                    <a class="button small marginLeft5" href="javascript:void(0)" onclick="gotoPage(<%=String.valueOf(jspContext.getAttribute("i"))%>)"><span class="gray">${i+1}</span></a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${pageIndex < lastLinkedPage}">
                <div class="next"><a href="javascript:void(0)" onclick="gotoPage(<%=String.valueOf(pageIndex+1)%>)">Next</a></div>
            </c:when>
            <c:otherwise>
                <div class="next"><a href="javascript:void(0)">Next</a></div>
            </c:otherwise>
        </c:choose>
        
        <div class="clearboth"></div>
        
    </div>
    <div class="marginTop20" id="jumpToPageSection">
        <div class="floatLeft marginTop3" style="margin-left:40px;">Jump to page</div>
        <input class="input floatLeft marginLeft5" id="jumpToPage" type="text" value="${pageIndex + 1}" />
        <div class="floatLeft marginLeft5 marginTop3">of ${pageCount}</div>
        <a class="button small marginLeft5" href="#" id="gotoPage"><span class="red">Go</span></a>
    </div>

 </div>
 <!-- pagination ends -->
 
<input type="hidden" name="pageModel.page" id="page" value="${pageModel.page}">
<input type="hidden" name="totalPageCount" id="totalPageCount" value="${pageCount}"/>
<div class="clearboth"></div>
 
</c:if>