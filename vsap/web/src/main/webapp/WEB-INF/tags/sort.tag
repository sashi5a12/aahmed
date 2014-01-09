<%@ tag import="org.springframework.util.StringUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="sortable" required="true" type="java.lang.Boolean" %>
<%@ attribute name="columnTitle" required="true" type="java.lang.String" %>
<%@ attribute name="columnField" required="false" type="java.lang.String" %>
<%@ attribute name="pageModelSortBy" required="false" type="java.lang.String" %>
<%@ attribute name="pageModelIsAscending" required="false" type="java.lang.String" %>

<c:choose>
    <c:when test="${sortable}">
        <c:choose>
            <c:when test="${columnField == pageModelSortBy}">
                <c:choose>
                    <c:when test="${pageModelIsAscending}">
                        <a href="javascript:void(0)" onclick="sortByColumn('${columnField}')">${columnTitle}</a>
                        <a href="javascript:void(0)" onclick="sortByColumn('${columnField}')"><span class="sort-ascending"></span></a>
                    </c:when>
                    <c:otherwise>
                        <a href="javascript:void(0)" onclick="sortByColumn('${columnField}')">${columnTitle}</a>
                        <a href="javascript:void(0)" onclick="sortByColumn('${columnField}')"><span class="sort-descending"></span></a>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <a href="javascript:void(0)" onclick="sortByColumn('${columnField}')">${columnTitle}</a>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        ${columnTitle}
    </c:otherwise>
</c:choose>

