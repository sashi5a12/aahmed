<%@ tag import="org.springframework.util.StringUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="columnTitle" required="true" type="java.lang.String" %>
<%@ attribute name="columnField" required="true" type="java.lang.String" %>
<%@ attribute name="pageModel" required="true" type="com.netpace.device.vo.PageModel" %>
<%@ attribute name="filterIndex" required="true" type="java.lang.Integer" %>
<%@ attribute name="filterValues" required="true" type="java.lang.String" rtexprvalue="true"%>
<%@ attribute name="filterLabel" required="true" type="java.lang.String" %>
<%@ attribute name="filterSeparators" required="false" type="java.lang.String" %>

<input type="hidden" name="pageModel.filters[${filterIndex}].filterBy" value="${columnField}"/>
<c:set var="filterIcon" value="table-grid-filter-icon.png"/>

<div class="user-filter">
    <div id="filterPopup${filterIndex}" class="filter-popup" style="display: none;">
        <div class="filter-popup-header">
            ${columnTitle} Filter
            <div class="filter-popup-close">
                <a href="#" onclick="return false;"><tg:img src="/images/close-icon-round.png" width="16" height="16" alt="" onclick="document.getElementById('filterPopup${filterIndex}').style.display = 'none';" /></a>
            </div>
        </div>
        <div class="filter-popup-body">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="datagrid-compact" >
                <tr>
                    <td colspan="2">
                        <label style="font-weight: bold; font-size:small; padding-bottom: 5px;" class="checkbox-label2">${filterLabel}</label>
                    </td>
                </tr>
                
                <c:choose>
                    <c:when test="${pageModel.filters == null}">

                        <c:forTokens items="${filterValues}" delims="," var="filterValue" varStatus="filterStatus">
                            <tr>
                                <td>
                                    <input type="checkbox" name="pageModel.filters[${filterIndex}].filterValues[${filterStatus.index}].selected" class="checkbox" />
                                    <input type="hidden" name="pageModel.filters[${filterIndex}].filterValues[${filterStatus.index}].title" value="${filterValue}"/>
                                </td>
                                <td>
                                    <label class="checkbox-label2">${filterValue}</label>
                                </td>
                            </tr>
                            
                            <c:if test="${filterSeparators != null}">
                                <c:if test="${fn:containsIgnoreCase(filterSeparators, filterStatus.index)}">
                                <tr>
                                    <td colspan="2">
                                        <label style="font-weight: bold; font-size:small; padding-bottom: 5px;" class="checkbox-label2">${fn:split(filterSeparators, ':')[1] }</label>
                                    </td>
                                </tr>
                                </c:if>
                            </c:if>
                        </c:forTokens>

                    </c:when>
                    <c:otherwise>

                        <c:forEach items="${pageModel.filters[filterIndex].filterValues}" var="filterValue" varStatus="filterStatus">
                            <tr>
                                <td>
                                    <input type="checkbox" name="pageModel.filters[${filterIndex}].filterValues[${filterStatus.index}].selected" class="checkbox" 
                                           <c:if test="${filterValue.selected}" >
                                               checked="checked"
                                               <c:set var="filterIcon" value="table-grid-filter-over-icon.png"/>
                                           </c:if>
                                           />
                                    <input type="hidden" name="pageModel.filters[${filterIndex}].filterValues[${filterStatus.index}].title" value="${filterValue.title}"/>
                                </td>
                                <td>
                                    <label class="checkbox-label2">${filterValue.title}</label>
                                </td>
                            </tr>
                            <c:if test="${filterSeparators != null}">
                                <c:if test="${fn:containsIgnoreCase(filterSeparators, filterStatus.index)}">
                                <tr>
                                    <td colspan="2">
                                        <label style="font-weight: bold; font-size:small; padding-bottom: 5px;" class="checkbox-label2">${fn:split(filterSeparators, ':')[1] }</label>
                                    </td>
                                </tr>
                                </c:if>
                            </c:if>
                        </c:forEach>

                    </c:otherwise>
                </c:choose>
            </table>
        </div>
        <div class="filter-popup-bottom">
            <div style="font-size: 11px; color: #333333; float:left;margin-top:5px"><a style="font-size: 11px; color: #0066CC; text-decoration: underline; font-weight: normal; float:none;" href="javascript:void(0)" onclick="checkAllFilters(${filterIndex})">Select All</a> | <a style="font-size: 11px; color: #0066CC; text-decoration: underline; font-weight: normal; float:none;" href="javascript:void(0)" onclick="clearAllFilters(${filterIndex})">Clear All</a></div>
            <a class="button" href="#" style="float:right;" onclick="filter();return false;" ><span class="red">Filter</span></a> 
        </div>
    </div>   
    <a  href="#" onclick="return false;"><tg:img src="/images/${filterIcon}" width="8" height="8" alt="" style="border:none;" onclick="document.getElementById('filterPopup${filterIndex}').style.display = '';" /></a>
</div>

