<%@page import="org.apache.commons.lang.StringUtils" %>
<%@page import="com.netpace.device.utils.VAPConstants" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty MESSAGE_KEY}">
    <div class="success">
        <p>
            <c:choose>

                <c:when test="${not empty COMPANY_NAME}">

                    <fmt:message key="${MESSAGE_KEY}" var="messageKey" >
                        <fmt:param>
                            <strong>${COMPANY_NAME}</strong>
                        </fmt:param>
                    </fmt:message>

                </c:when>

                <c:otherwise>

                    <fmt:message key="${MESSAGE_KEY}" var="messageKey" />

                </c:otherwise>

            </c:choose>
            ${messageKey}
        </p>
    </div>
</c:if>
