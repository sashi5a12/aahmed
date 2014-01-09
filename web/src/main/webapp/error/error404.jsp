<%@page isErrorPage="true" contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
    expression="T(com.netpace.device.utils.VAPConstants).JQUERY" 
    var="JQUERY"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).STYLE" 
    var="STYLE"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <script>
            var STATICS_URL = "${applicationScope.STATICS_URL}";
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Verizon Device Portal</title>
        <tg:includestatic type="${FAVICON_TYPE}" paramName="" />
        <tg:includestatic type="${CSS_TYPE}" paramName="${STYLE}" />
        <tg:includestatic type="${JS_TYPE}" paramName="${JQUERY}" />
    </head>
    <body>
        <!-- Public -->
        <!-- Header Starts -->
        <div class="headerwapper">

            <div class="header">
                <!-- Top Bar Starts -->
                <div class="topbar">
                    <tg:img src="/images/topBarIcon.gif" width="12" height="15" alt="" />
                    <a href="${pageContext.request.contextPath}/signin.do">Sign In / Register</a>
                </div>
                <!-- Top Bar Ends -->
                <!-- Logo Starts-->
                <div class="logo"><tg:img src="/images/vz_logo.png" width="137" height="53" alt="" /></div>
                <!-- Logo Ends -->
                <!-- Main Top Nav Starts -->
                <div class="maintopnav">
                    <ul>
                        <li><a href="javascript:void(0);">About</a></li>
                        <li><a href="javascript:void(0);">Tools &amp; API </a></li>
                        <li><a href="javascript:void(0);">Go to Market</a></li>
                        <li><a href="javascript:void(0);">Support</a></li>
                    </ul>
                </div>
                <!-- Main Top Nav Ends -->
                <!-- Search Bar Starts -->
                <div class="navSearch">
                    <label class="hideLabel" >Search for</label>
                    <input type="text" class="navSearchInput" name="navSearchInput" />
                    <a class="navSearchButton" href="javascript:gNav.search()">go search</a>
                </div>
                <!-- Search Bar Ends -->
                <div class="clearboth"></div>
            </div>

        </div>
        <!-- Header Ends -->
        <!-- Content Starts -->
        <div class="clearboth"></div>
        <div class="contentwapper">
            <div class="content">
                <div class="system-error">
                    <h1>404</h1>
                    <h2>Page not found!</h2>
                    <h3>The page you requested is not available.</h3>
                </div>
            </div>
        </div>
        <!-- Content Ends -->
        <!-- Footer Starts -->
        <div class="footerwapper">
            <div class="footer">
                <div class="copyrights">© 2013 Verizon Wireless</div>
                <div class="footerlinks">
                    <ul>
                        <li style="border-left:solid 0px"><a href="javascript:void(0);">About Us</a></li><li><a href="javascript:void(0);">Privacy</a></li><li><a href="javascript:void(0);">Legal Notices</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- Footer Ends -->
    </body>
</html>
