<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<div class="homepage">
    <!-- Header Starts -->
    <div class="homeheader">
        <tg:img src="/images/header.jpg" width="980" height="548" alt=""/>
    </div>
    <!-- Header Ends -->
    <!-- 2nd Container Starts -->
    <div class="homeLeftPod">
        <a class="leftNavPod" href="${pageContext.request.contextPath}/registration.do">
            <tg:img src="/images/register.jpg" width="39" height="39" alt=""/>
            <h2>Register:</h2>
            <h3>Start today!</h3>
        </a>
        <a class="leftNavPod" href="javascript:void(0);">
            <tg:img src="/images/deploymentProcess.jpg" width="39" height="39" alt=""/>
            <h2>Deployment Process</h2>
            <h3>Good to have in development</h3>
        </a>
        <a class="leftNavPod" href="javascript:void(0);">
            <tg:img src="/images/complainceRequirement.jpg" width="39" height="39" alt=""/>
            <h2>Compliance Requirements</h2>
            <h3>Test your app on a simulator</h3>
        </a>
    </div>
    <div class="homeRightPod">
        <h1>Why Verizon?</h1>
        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint.</p>
        <a href="javascript:void(0);">Read More</a>
    </div>
    <!-- 2nd Container Ends -->
    <div class="clearboth mtop25"></div>
    <!-- Bottom Container Starts -->
    <div class="homeBottomPod noMLeft">
        <tg:img src="/images/developer.jpg" width="66" height="60" alt=""/>
        <div class="rightCont">
            <h2><a href="javascript:void(0);">Developer Community</a></h2>
            <p><a href="javascript:void(0);">Take part in the Netpace Development Portal by attending events, participating in support forums, and joining the conversation in Netpace Development Portal.</a>
            </p>
        </div>
    </div>
    <div class="homeBottomPod">
        <tg:img src="/images/accessory.jpg" width="66" height="60" alt=""/>
        <div class="rightCont">
            <h2><a href="javascript:void(0);">Accessory World</a></h2>
            <p><a href="javascript:void(0);">Reach over 75 million active NDP subscribers and leverage services such as Advertising and Payment to fulfill your app's full potential.</a>
            </p>
        </div>
    </div>
    <div class="homeBottomPod">
        <tg:img src="/images/news.jpg" width="66" height="60" alt=""/>
        <div class="rightCont">
            <h2><a href="#">News &amp; Events</a></h2>
            <ul>
                <li><a href="javascript:void(0);">Verizon is coming to your city</a></li>
                <li><a href="javascript:void(0);">Focus your creativity at Phone   Hack Day 2 in New York</a></li>
                <li><a href="javascript:void(0);">Make free advertising banners for your app</a></li>
            </ul>
        </div>
    </div>
    <!-- Bottom Container Ends -->
    <div class="clearboth"></div>  
</div>