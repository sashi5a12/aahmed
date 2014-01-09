<%-- 
    Document   : includestatic
    Created on : Jul 23, 2013, 1:27:23 PM
    Author     : Noorain
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="paramName" required="true" type="java.lang.String" %>
<%@ attribute name="type" required="true" type="java.lang.String" %>

<spring:eval expression="T(com.netpace.device.utils.VAPConstants).CSS_TYPE" var="CSS_TYPE"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" var="JS_TYPE"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).FAVICON_TYPE" var="FAVICON_TYPE"/>

<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JS_COMMON" var="JS_COMMON"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JS_CHARACTERS_COUNTER" var="JS_CHARACTERS_COUNTER"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).PRODUCT" var="PRODUCT"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).PAGEMODEL" var="PAGEMODEL"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).FINEUPLOAD" var="FINEUPLOAD"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JQUERY" var="JQUERY"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JQUERY_UI" var="JQUERY_UI"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).CKEDITOR" var="CKEDITOR"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).STYLE" var="STYLE"/>

<c:choose>
    <c:when test="${type eq CSS_TYPE}">
        <c:if test="${paramName eq JQUERY_UI}">
            <link href="${applicationScope.STATICS_URL}/jquery-ui-1.10.3.custom.css?v=${applicationScope.STATICS_VERSION}" rel="stylesheet" type="text/css" />
            <link href="${applicationScope.STATICS_URL}/jq-style.css?v=${applicationScope.STATICS_VERSION}" rel="stylesheet" type="text/css" />
        </c:if>
        <c:if test="${paramName eq FINEUPLOAD}">
            <link href="${applicationScope.STATICS_URL}/fineuploader-3.7.0/fineuploader-3.7.0.css?v=${applicationScope.STATICS_VERSION}" rel="stylesheet" type="text/css" />
        </c:if>
        <c:if test="${paramName eq STYLE}">
            <link href="${applicationScope.STATICS_URL}/style.css?v=${applicationScope.STATICS_VERSION}" rel="stylesheet" type="text/css" />
        </c:if>
    </c:when>
    
    <c:when test="${type eq JS_TYPE}">
        <c:if test="${paramName eq JQUERY}">
            <script type="text/javascript" src="${applicationScope.STATICS_URL}/scripts/jquery-1.9.1.min.js?v=${applicationScope.STATICS_VERSION}"></script>
        </c:if>
        <c:if test="${paramName eq JQUERY_UI}">
            <script type="text/javascript" src="${applicationScope.STATICS_URL}/scripts/jquery-ui.js?v=${applicationScope.STATICS_VERSION}"></script>
        </c:if>
        <c:if test="${paramName eq FINEUPLOAD}">
            <script type="text/javascript" src="${applicationScope.STATICS_URL}/fineuploader-3.7.0/jquery.fineuploader-3.7.0.js?v=${applicationScope.STATICS_VERSION}"></script>
        </c:if>
        <c:if test="${paramName eq PRODUCT}">
            <script type="text/javascript" src="${applicationScope.STATICS_URL}/scripts/product.js?v=${applicationScope.STATICS_VERSION}"></script>
        </c:if>
        <c:if test="${paramName eq PAGEMODEL}">
            <script type="text/javascript" src="${applicationScope.STATICS_URL}/scripts/pagemodel.js?v=${applicationScope.STATICS_VERSION}"></script>
        </c:if>
        <c:if test="${paramName eq CKEDITOR}">
            <script type="text/javascript" src="${applicationScope.STATICS_URL}/scripts/ckeditor/ckeditor.js?v=${applicationScope.STATICS_VERSION}"></script>
        </c:if>
        <c:if test="${paramName eq JS_COMMON}">
            <script type="text/javascript" src="${applicationScope.STATICS_URL}/scripts/common.js?v=${applicationScope.STATICS_VERSION}"></script>
        </c:if>       
         <c:if test="${paramName eq JS_CHARACTERS_COUNTER}">
            <script type="text/javascript" src="${applicationScope.STATICS_URL}/scripts/jquery.textareaCounter.plugin.js?v=${applicationScope.STATICS_VERSION}"></script>
        </c:if>
    </c:when>
    
    <c:when test="${type eq FAVICON_TYPE}">
        <link href="${applicationScope.STATICS_URL}/images/favicon.ico?v=${applicationScope.STATICS_VERSION}" rel="icon" type="image/vnd.microsoft.icon" />
        <link href="${applicationScope.STATICS_URL}/images/favicon.ico?v=${applicationScope.STATICS_VERSION}" rel="shortcut icon" type="image/vnd.microsoft.icon"  />
    </c:when>        
</c:choose>
