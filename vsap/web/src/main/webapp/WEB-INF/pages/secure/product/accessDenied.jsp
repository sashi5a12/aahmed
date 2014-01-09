<%@page import="com.netpace.device.utils.enums.ProductSubmissionType"%>
<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--<fmt:message key="msg.product.accessDenied"/>--%>

<c:set value="<a href='mailto:${VERIZON_ADMIN_EMAIL_ADDRESS}'>Verizon Admin</a>" 
       var="mailToLink" />

<fmt:message key="${COMPANY_STATUS}" var="msgCompanyStatus" >
    <fmt:param value="${mailToLink}" />
</fmt:message>

<p>${msgCompanyStatus}</p>
<div class="clearboth"></div>
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />