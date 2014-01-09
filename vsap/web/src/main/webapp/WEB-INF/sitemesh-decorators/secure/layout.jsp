<%@page import="com.netpace.device.utils.VAPConstants"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"  %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).FAVICON_TYPE" 
    var="FAVICON_TYPE"/>

<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" var="JS_TYPE"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).CSS_TYPE" var="CSS_TYPE"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).JQUERY" var="JQUERY"/>
<spring:eval expression="T(com.netpace.device.utils.VAPConstants).STYLE" var="STYLE"/>

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
        <!-- Secure -->
        <!-- Header Starts -->
        <c:import url="${applicationScope.HEADER_URL}?loggedInUserName=${pageContext.request.userPrincipal.name}&includecss=false&includejquery=false" />
        <!-- Header Ends -->
        <!-- Content Starts -->
        <div class="contentwapper">
            <div class="content">
                <!-- Left Nav Starts -->
                <div class="leftNavApp">
                    <c:forEach items="${sessionScope.MENU_INFORMATION}" var="menu">
                        <div class="subnav learn" id="subnav">${menu.name}
                            <ul>
                                <c:forEach items="${menu.submenu}" var="subMenu">
                                    <c:choose>
                                        <c:when test="${subMenu.value == '/secure/mycompany.do' && pageContext.request.servletPath == '/secure/company.do'}">                                            
                                            <li class="selected"><a href="${pageContext.request.contextPath}${subMenu.value}"><span>${subMenu.key}</span></a></li>
                                        </c:when>
                                        <c:when test="${subMenu.value == pageContext.request.servletPath}">                                            
                                            <li class="selected"><a href="${pageContext.request.contextPath}${subMenu.value}"><span>${subMenu.key}</span></a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="${pageContext.request.contextPath}${subMenu.value}"><span>${subMenu.key}</span></a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:forEach>
                </div>

                <!-- Left Nav ends -->
                <!-- Right content area starts -->
                <div class="rightContentAreaApp"><decorator:body/>
                </div>
                <!-- Right content area ends -->
                <div class="clearboth marginBottom60"></div>
            </div>
        </div>
        <!-- Content Ends -->
        <!-- Footer Starts -->
        <c:import url="${applicationScope.FOOTER_URL}"/>
        <!-- Footer Ends -->
    </body>
</html>