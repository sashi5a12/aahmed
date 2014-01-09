<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="headerwapper">
    <div class="header">
        <!-- Top Bar Starts -->
        <div class="topbar">
            <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                <tg:img src="/images/topBarLogoutIcon.jpg" width="12" height="15" alt="" />
                <a href="${pageContext.request.contextPath}/logout.do">Logout</a>
                <p>|</p>
                <a href="${pageContext.request.contextPath}/secure/profile.do">${pageContext.request.userPrincipal.name}</a>
            </sec:authorize>
            <sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
                <tg:img src="/images/topBarIcon.gif" width="12" height="15" alt="" />
                <a href="${pageContext.request.contextPath}/signin.do">Sign In / Register</a>
            </sec:authorize>
        </div>
        <!-- Top Bar Ends -->
        <!-- Logo Starts-->
        <div class="logo"><a href="/content/vsap/en.html"><tg:img src="/images/vz_logo.png" width="137" height="53" alt="" /></a></div>
        <!-- Logo Ends -->
        <!-- Main Top Nav Starts -->
        <div class="maintopnav">
            <ul>
                <li><a href="/content/vsap/en/overview.html">Overview</a></li>                
                <li><a href="/content/vsap/en/certification.html">Certification</a></li>
                <li><a href="${pageContext.request.contextPath}/secure/worklist.do">My Account</a></li>
                <li><a href="/content/vsap/en/support.html">Support</a></li>
            </ul>
        </div>
        <!-- Main Top Nav Ends -->        
        <div class="clearboth"></div>
    </div>

</div>