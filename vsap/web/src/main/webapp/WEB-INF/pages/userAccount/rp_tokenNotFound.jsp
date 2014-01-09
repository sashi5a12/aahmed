<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

  <div class="content">
    <h1 class="redheading">Reset Password</h1>
               <!-- Column one Starts -->
    <p><br/><br/>
        The password reset link is invalid or expired.<br/><br/>
        To request a new one <a href="${pageContext.request.contextPath}/forgotPassword.do">click here</a>
    </p>
    <div class="clearboth"></div>
  </div>
