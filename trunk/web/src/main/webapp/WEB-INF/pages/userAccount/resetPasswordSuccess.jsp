<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- Sign In Column Starts -->
<div class="content">
    <div class="signincolumn">
        <h1 class="redheading">Reset Password</h1>
        <br/><br/>
        <div class="error">
            <p>Your password has been reset successfully</p>
        </div>
        <ul><li style="list-style: none;margin-left: 10px;">
        <a href="${pageContext.request.contextPath}/signin.do">Click here to return to the login page</a>
        </li></ul>
        <div class="clearboth" style="margin-bottom:18px;"></div>         
        <div class="clearboth"></div>
    </div>
</div>
