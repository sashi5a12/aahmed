<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script>
    function submitForm(frmName){
        document.forms[frmName].submit();
    }
</script>

<form:form name="frm" action="forgotPasswordSubmit.do" method="post" modelAttribute="userInfoResend">
    <div class="content">

        <spring:hasBindErrors name="userInfoResend">
            <div class="error">
                <ul>
                    <form:errors path="*" element="li" delimiter="</li><li>" id="" />
                </ul>
            </div>
        </spring:hasBindErrors>

        <div class="signincolumn">
            <h1 class="redheading" >Forgot Password</h1>
            <label class="inputlabel">Enter the username you use to sign in</label>
            <input class="input" type="text" value="${userInfoResend.userName}"   name="userName" maxlength="25"/>
            <div class="clearboth" style="margin-bottom:18px;"></div>         
            <a class="button" href="javascript:null(0)" onclick="submitForm('frm')" style="margin-left:217px;"><span class="red">Continue</span></a>
            <div class="clearboth"></div>
        </div>
        <div class="clearboth"></div>
    </div>

</form:form>