<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Sign In Column Starts -->
<script>
    function submitForm(frmName) {
        document.forms[frmName].submit();
    }
</script>
<div class="content">
    <div class="signincolumn">
        <form name="f" action="j_spring_security_check" method="post">
            <h1 class="redheading">Sign In</h1>
            <c:if test="${not empty param.error}">
                <div class="error">
                    <p>
                        <fmt:message key="msg.invalid.credentials" />
                    </p>
                </div>
            </c:if>
            <div class="redstar">*</div><label class="inputlabel">User Name or Email Address</label>
            <input class="input" type="text" id="j_username" name="j_username" />
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Password</label>
            <input class="input" type="password"  id="j_password" name="j_password" onkeypress="if (event.keyCode == 13) {
            submitForm('f');
        }" />
            <div class="clearboth" style="margin-bottom:18px;"></div>
            <a class="button" href="javascript:submitForm('f')" style="margin-left:231px;"><span class="red">Sign In</span></a>
            <div class="clearboth"></div>
        </form>
        <div style="margin-top:13px;width:300px;text-align:right;">
            <a href="${pageContext.request.contextPath}/forgotUsername.do" class="font13">Forgot Username?</a>
            &nbsp;
            <a href="${pageContext.request.contextPath}/forgotPassword.do" class="font13">Forgot Password?</a>
        </div>        
        <br />
        <br />
        <br />
    </div>
    <!-- Sign In Column Ends -->
    <!-- Registration Column Starts -->
    <div class="registrationcolumn">
        <h1 class="redheading">Registration</h1>
        <p>Verizon wireless encourages forward thinking partners to create Smart Accessories. Register and submit your products</p>
        <a class="button floatRight marginTop20" href="${pageContext.request.contextPath}/registration.do" ><span class="red">Register</span></a>
        <div class="clearboth"></div>
    </div>
    <!-- Registration In Column Ends -->
</div>