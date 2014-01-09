<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Single Column Starts -->
<div class="content">

<h1 class="redheading">Account Registration</h1>
<p>An account activation email for <b><c:out value="${param.userName}" /></b> has been sent. To activate this account, follow the instructions we've sent to your <b><c:out value="${param.userEmail}" /></b> email address.
<br /><br />If you did not receive the email, please check your spam folder for an email from <a href="mailto:noreply@smart.verizon.com">noreply@smart.verizon.com</a>.</p>

<div class="clearboth"></div> 

</div>