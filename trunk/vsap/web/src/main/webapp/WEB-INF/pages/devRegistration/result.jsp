<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${companyExists}">
    Join the company
</c:if>
<c:if test="${!companyExists}">
    Account activated
</c:if>