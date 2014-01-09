<%-- 
    Document   : switch-pages
    Created on : Feb 10, 2013, 12:05:15 PM
    Author     : Hamza Ghayas
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

 <div class="clearboth"></div>

 <!-- pagination starts -->
 <div class="pagination">
    <div>
         <div class="prev">Previouse</div>
         <c:forEach begin="1" end="${workItemPageParams.totalPages}" varStatus="varStatus">
             
             <c:choose>
                 <c:when test="${varStatus.first }">
                     <a class="button small" href="javascript:null(0);" 
                        <c:if test="${not (varStatus.index eq workItemPageParams.pageNo)}" >
                              onclick="goToPageNo('${varStatus.index}');"
                        </c:if>
                        >
                 </c:when>
                 <c:otherwise>
                     <a class="button small marginLeft5" href="javascript:null(0);" 
                        <c:if test="${not (varStatus.index eq workItemPageParams.pageNo)}" >
                            onclick="goToPageNo('${varStatus.index}');"
                        </c:if>
                     >
                 </c:otherwise>
             </c:choose>
             <span  <c:choose><c:when test="${varStatus.index eq workItemPageParams.pageNo}">class="red"</c:when>
                      <c:otherwise>class="gray"</c:otherwise>
                    </c:choose>>
              ${varStatus.index}
             </span>
             </a>
        </c:forEach>
         <div class="next">Next</div>
         <div class="clearboth"></div>
    </div>
    <div class="marginTop20">
        <div class="floatLeft marginTop3" style="margin-left:40px;">Jump to page</div>
        <input class="input floatLeft marginLeft5" id="page_no" name="" type="text" />
            <div class="floatLeft marginLeft5 marginTop3">of ${workItemPageParams.totalPages}</div>
            <a class="button small marginLeft5 " href="javascript:null(0);" onclick="onClickGo();"><span class="red">Go</span></a>
    </div>
 </div>
 <!-- pagination ends -->
 <div class="clearboth"></div>

 <script lang="text/javascript">
     function onClickGo(){
         var go_link = '${pageContext.request.contextPath}/secure/admin.do?page_no='+document.getElementById('page_no').value;
//        var go_link = '${pageContext.request.contextPath}/secure/admin.do?page_no='+jQuery( 'page_no').value;

        window.location = go_link;
     }
     
     function goToPageNo(pageNo){
         var go_link = '${pageContext.request.contextPath}/secure/admin.do?page_no='+pageNo;
//        var go_link = '${pageContext.request.contextPath}/secure/admin.do?page_no='+jQuery( 'page_no').value;
        
        window.location = go_link;
     }
 </script>