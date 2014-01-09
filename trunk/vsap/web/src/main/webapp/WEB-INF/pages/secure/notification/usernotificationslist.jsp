<%@page import="org.apache.commons.lang.StringUtils"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<c:if test="${not empty NOTIFICATION_SUBSCRIBED}">
    <div class="success"><p><fmt:message key="msg.notification.subscribe" /></p></div>
</c:if>

<c:if test="${not empty NOTIFICATION_UNSUBSCRIBED}">
    <div class="success"><p><fmt:message key="msg.notification.unsubscribe" /></p></div>
</c:if>

<c:if test="${not empty NOTIFICATION_INVALID}">
    <div class="error"><p><fmt:message key="msg.notification.invalid" /></p></div>
</c:if>

<h1 class="redheading floatLeft">Notifications</h1>

<div class="clearboth"></div>

<!-- Table Grid Starts -->
<table width="100%" border="0" class="datagrid-gray" cellpadding="0" cellspacing="0">
    <form:form name="frm" action="${pageContext.request.contextPath}/secure/deletenotification.do" method="post">
        <tr>
            <th style="width:340px">Notification Title</th>
            <th>Notification Description</th>
            <th  style="width:70px" class="center-text">Sign Up</th>
        </tr>
        <c:set var="canEdit" value="false" />
        <sec:authorize ifAnyGranted="${EDIT_NOTIFICATION_ROLES}">
            <c:set var="canEdit" value="true" />
        </sec:authorize>
        <c:forEach items="${notificationsList}" var="notification">
            <tr>
                <td>
                    <c:if test="${canEdit}">
                        <c:set var="truncatedNotificationTitleText">
                            <a href="${pageContext.request.contextPath}/secure/viewnotification.do?notificationId=${notification.notificationId}" title="${notification.title}"><c:out value="${notification.title}" /></a>
                        </c:set>
                    </c:if>
                    <c:if test="${not canEdit}">
                        <c:set var="truncatedNotificationTitleText" value="${notification.title}" />
                    </c:if>
                    ${truncatedNotificationTitleText}
                </td>
                <td>
                    <c:out value="${notification.description}" escapeXml="false" />
                </td>
                <td class="center-text">
                    <c:choose>
                        <c:when test="${notification.notificationOptOutId eq null}" >
                            <a href="${pageContext.request.contextPath}/secure/unsubscribenotification.do?id=${notification.notificationId}"><tg:img src="/images/table-grid-subscribe-icon.png" alt="" width="23" height="17" border="0" /></a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/secure/subscribenotification.do?id=${notification.notificationOptOutId}"><tg:img src="/images/table-grid-unsubscribe-icon.png" alt="" width="23" height="17" border="0" /></a>
                        </c:otherwise>
                    </c:choose>            

                </td>
            </tr>
        </c:forEach>
    </form:form>
</table>
<!--Table Grid ends -->
<div class="clearboth"></div>

<div class="clearboth"></div>

<div class="clearboth marginTop40"></div>

<div class="clearboth"></div>
