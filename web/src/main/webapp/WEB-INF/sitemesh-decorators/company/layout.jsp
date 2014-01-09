<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"  %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).FAVICON_TYPE" 
    var="FAVICON_TYPE"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" 
    var="JS_TYPE"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).CSS_TYPE" 
    var="CSS_TYPE"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).STYLE" 
    var="STYLE"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).JQUERY" 
    var="JQUERY"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
    	<script>
    		var STATICS_URL="${applicationScope.STATICS_URL}";
    	</script>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Verizon Device Portal</title>
        
        <tg:includestatic type="${FAVICON_TYPE}" paramName="" />
        <tg:includestatic type="${CSS_TYPE}" paramName="${STYLE}" />
        <tg:includestatic type="${JS_TYPE}" paramName="${JQUERY}" />
    </head>
    <body>
        <!-- Company -->
        <!-- Header Starts -->
        <c:import url="${applicationScope.HEADER_URL}?loggedInUserName=${pageContext.request.userPrincipal.name}&includecss=false&includejquery=false" />
        <!-- Header Ends -->
        <!-- Content Starts -->
        <div class="contentwapper">
            <div class="content">
                <decorator:getProperty property="page.error" />
                <decorator:body />
            </div>
        </div>
        <!-- Content Ends -->
        <!-- Footer Starts -->        
        <c:import url="${applicationScope.FOOTER_URL}"/>
        <!-- Footer Ends -->
    </body>
</html>