<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- Single Column Starts -->
<h1 class="redheading" >View User</h1>
<!-- Gray Box Layout - Two column Starts -->
<div class="grayBoxLayouts">
    <div class="twoColmnLayout">
        <div class="columnOne">
            <label class="inputlabelbold">Full Name</label>
            <label class="label-value">${fn:escapeXml(userInfo.fullName)}</label>
            <div class="clearboth"></div>
            
            <label class="inputlabelbold">Username</label>
            <label class="label-value">${fn:escapeXml(userInfo.userName)}</label>
            <div class="clearboth"></div>

            <label class="inputlabelbold">Email Address</label>
            <label class="label-value">${fn:escapeXml(userInfo.emailAddress)}</label>
            <sec:authorize ifAllGranted="ROLE_PARTNER_USER">
            <div class="marginTop5"></div>
            <span class="font12">Your email address must match your company domain
                in order to register for the Verizon Smart Accessory
                Portal</span>
            </sec:authorize>

            <div class="clearboth"></div>
            
            <label class="inputlabelbold">Phone Number</label>
            <label class="label-value">${fn:escapeXml(userInfo.phoneNumber)}</label>

            <div class="clearboth"></div>    

            <label class="inputlabelbold">Street Address</label>
            <label class="label-value">${userInfo.address}</label>
            <div class="clearboth"></div>

            <label class="inputlabelbold">City/town</label>
            <label class="label-value">${fn:escapeXml(userInfo.city)}</label>
            <div class="clearboth"></div>

        </div>
        <div class="columnTwo">
            <c:if test="${not empty userInfo.companyDomain}">
                <label class="inputlabelbold">Company Domain</label>
                <div class="label-value">${userInfo.companyDomain}</div>
                <div class="clearboth"></div>
            </c:if>
            <label class="inputlabelbold">Mobile Phone Number</label>
            <label class="label-value">${fn:escapeXml(userInfo.mobilePhoneNumber)}</label>
            <div class="clearboth"></div>

            <label class="inputlabelbold">State/Province</label>
            <label class="label-value">${fn:escapeXml(userInfo.state)}</label>
            <div class="clearboth"></div>

            <label class="inputlabelbold">Zip Code/Postal Code</label>
            <label class="label-value">${fn:escapeXml(userInfo.zip)}</label>
            <div class="clearboth"></div>

            <label class="inputlabelbold">Country</label>
            <label class="label-value">${fn:escapeXml(userInfo.country)}</label>

            <div class="clearboth"></div>     
        </div>
        <div class="clearboth"></div>
    </div>
</div>
