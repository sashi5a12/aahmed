<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set value="<a href='mailto:${VERIZON_ADMIN_EMAIL_ADDRESS}'>Verizon Admin</a>" 
       var="mailToLink" />

<fmt:message 
    key="${COMPANY_ON_HOLD_OR_SUSPENDED}" var="msgCompanyOnHoldOrSuspended" >

    <fmt:param value="${mailToLink}" />
</fmt:message>

<!-- Single Column Starts -->
<div class="content">
    <p>${msgCompanyOnHoldOrSuspended}</p>
    <div class="clearboth"></div>
</div>
<!-- Single Column End -->