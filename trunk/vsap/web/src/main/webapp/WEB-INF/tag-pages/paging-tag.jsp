<%-- 
    Document   : paging-tag
    Created on : Feb 8, 2013, 8:44:17 AM
    Author     : Hamza Ghayas
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<!-- Table Grid Starts -->
             <table width="100%" border="0" class="datagrid-gray" cellpadding="0" cellspacing="0">
               <tr>
                 <th><input type="checkbox" name="checkbox2" id="checkbox2" /></th>
                 <th style="white-space: nowrap; " ><a href="javascript:void(0);">
                   <span  class="sort-ascending"></span>
                 </a>Work Item</th>
                 <th style="white-space: nowrap; "><a href="javascript:void(0);">
                   <span  class="sort-ascending"></span>
                 </a>Type</th>
                 <th style="white-space: nowrap; clear: both; vertical-align: top;" >
                    <div  style="clear: both; display: inline;"  class="sort-ascending"></div>
                    <div style="clear: both; display: inline;">Company</div>
                    <div style="clear: both;padding-left: 15px;display: inline;" class="user-filter"></div>
                 </th>
                 <th style="white-space: nowrap; "><a href="javascript:void(0);">
                   <span class="sort-ascending"></span>
                 </a>Admin</th>
                 <th style="white-space: nowrap; "><a href="javascript:void(0);">
                   <span class="sort-ascending"></span>
                 </a>Details</th>
                 <th style="width:20%;white-space: nowrap;"><a href="javascript:void(0);">
                   <span class="sort-ascending"></span>
                 </a>Start Date</th>
                 <th style="white-space: nowrap; " class="center-text">RFI</th>
                 <th style="white-space: nowrap; " class="center-text">Comm.</th>
               </tr>
               
               
               <c:forEach items="${workItemPageParams.valueObjects}" var="workItem">
                   <tr>
                     <td><input type="checkbox" name="checkbox2" id="checkbox2" /></td>
                     <td style="white-space: nowrap;">${workItem.workflowPhaseName}</td>
                     <td style="white-space: nowrap;">${workItem.workflowType}</td>
                     <td style="white-space: nowrap;"><a href="javascript:void(0);">${workItem.company.companyName}</a></td>
                     <td style="white-space: nowrap;"><a href="javascript:void(0);">${workItem.assigneeUser.fullName}</a></td>
                     <td>&nbsp;</td>
                     <td style="width:20%;white-space: nowrap;">
                         <fmt:formatDate value="${workItem.startDate}" type="date" dateStyle="short" />
                     </td>
                     <td style="white-space: nowrap;" class="center-text">
                         <c:choose>
                             <c:when test="${workItem.isRfi eq 'Y'}">
                                <tg:img src="/images/table-grid-check-icon.png" width="13" height="14" alt="" />
                             </c:when>
                             <c:when test="${empty workItem.isRfi or workItem.isRfi eq 'N'}">
                                <tg:img src="/images/table-grid-edit-icon.png" width="13" height="14" alt="" />
                             </c:when>
                         </c:choose>
                     </td>
                     <td style="white-space: nowrap;" class="center-text">
                         <tg:img src="/images/table-grid-edit-icon.png" width="13" height="14" alt="" />
                     </td>
                   </tr>
               </c:forEach>
             </table>
             <!--Table Grid ends -->
            <div class="clearboth"></div><br />
             <!-- buttons starts -->
              <a class="button floatRight marginLeft10" href="javascript:void(0);" ><span class="gray">Deny</span></a>
             <a class="button floatRight" href="javascript:void(0);" ><span class="red">Approve</span></a> 
             <!-- buttons ends -->
             <div class="clearboth"></div>
             
             <!-- pagination starts -->
             <%@include file="switch-pages.jsp" %>
             <!-- pagination ends -->
             <div class="clearboth"></div>