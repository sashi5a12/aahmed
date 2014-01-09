<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- Content Starts -->
<div class="content">
    <!-- Single Column Starts -->
    <div class="singlecolumn">
        <h1 class="redheading">${userInfoResend.messageText}</h1>
        <div style="margin-bottom:20px; font-size:13px;">Your User Name has been sent to your <b>${receiver_email_address}</b> email address.<br />
            <br />
            If you did not receive the username email, please check your spam folder for an email from <b>${applicationScope.NO_REPLY_FROM_ADDRESS}</b>. If you can not find the email, please try again.
            <div class="clearboth" style="margin-bottom:18px;"></div>         
        </div>
        <!-- Single Column Ends -->
        <div class="clearboth"></div>
    </div>
</div>
<!-- Content Ends -->