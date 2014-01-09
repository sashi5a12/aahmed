<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- Content Starts -->
<div class="contentwapper">
  <div class="content">
    	<!-- Single Column Starts -->
		<div class="singlecolumn">
        	<h1 class="redheading">${userInfoResend.messageText}</h1>
            <div style="margin-bottom:20px; font-size:13px;">An email with a link to reset your password has been sent to <b>${receiver_email_address}</b><br />
<br />
If you didn't receive the password reset email yet please check your spam folder for an email from <b>${applicationScope.NO_REPLY_FROM_ADDRESS}</b>.<br />
If you still don't see the email please try again.
<div class="clearboth" style="margin-bottom:18px;"></div>         
<a class="button" href="${pageContext.request.contextPath}/signin.do" style="float:right;"><span class="red">Sign In</span></a>
		</div>
        <!-- Single Column Ends -->
      	<!-- Registration Column Starts --><!-- Registration In Column Ends -->
    <div class="clearboth"></div>
    </div>
</div>
<!-- Content Ends -->