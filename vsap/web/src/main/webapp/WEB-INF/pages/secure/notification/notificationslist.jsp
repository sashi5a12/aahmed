<%@page import="org.apache.commons.lang.StringUtils"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_NOTIFICATION_MANAGE_TITLE_TRUNCATION_LENGTH" 
    var="DEFAULT_NOTIFICATION_MANAGE_TITLE_TRUNCATION_LENGTH"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).DEFAULT_NOTIFICATION_MANAGE_DESCRIPTION_TRUNCATION_LENGTH" 
    var="DEFAULT_NOTIFICATION_MANAGE_DESCRIPTION_TRUNCATION_LENGTH"/>

<h1 class="redheading floatLeft">Notifications</h1>

<div class="clearboth"></div>

<!-- Table Grid Starts -->
<table width="100%" border="0" class="datagrid-gray" cellpadding="0" cellspacing="0">
    <tr>
        <th>
            <input type="checkbox" name="checkbox2" id="checkbox2" />
        </th>
        <th style="white-space: nowrap; " >
            Notification Title
        </th>        
    </tr>

    <form:form name="frm" action="${pageContext.request.contextPath}/secure/deletenotification.do" method="post">
        <c:forEach items="${notificationsList}" var="notification">
            <tr>
                <td>
                    <input type="checkbox" name="notificationIds" value="${notification.id}" />
                </td>
                <td style="white-space: nowrap;">
                    <c:set var="truncatedNotificationTitle" value="${fn:substring(notification.title, 0, DEFAULT_NOTIFICATION_MANAGE_TITLE_TRUNCATION_LENGTH)}" />
                    <c:if test="${fn:length(notification.title) gt DEFAULT_NOTIFICATION_MANAGE_TITLE_TRUNCATION_LENGTH}">
                        <c:set var="truncatedNotificationTitle" value="${truncatedNotificationTitle}..." />
                    </c:if>
                    <a href="${pageContext.request.contextPath}/secure/viewnotification.do?notificationId=${notification.id}" title="${notification.title}"><c:out value="${notification.title}" /></a>

                </td>
            </tr>
        </c:forEach>
    </form:form>
</table>
<!--Table Grid ends -->

<div class="clearboth"></div><br />

<!-- buttons starts -->
<a class="button floatRight marginLeft10" href="javascript:void(0);" onclick="javascript:deleteNotifications();"><span class="gray">Delete</span></a>
<a class="button floatRight" href="${pageContext.request.contextPath}/secure/viewnotification.do"><span class="red">Create</span></a> 
<!-- buttons ends -->

<div class="clearboth"></div>

<div class="clearboth"></div><br /><br /><br />
<!-- Content Ends -->

<script type="text/javascript">

    function deleteNotifications() {

        if ($('input[name="notificationIds"]:checked').length > 0) {
            if (confirm('Are you sure, want to delete selected notification(s) ?')) {
                document.forms['frm'].submit();
            }
        } else {
            alert("Please select notification(s) to be deleted");
        }
    }

</script>
