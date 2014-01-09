<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script>
    function submitForm(frmName){
        document.forms[frmName].submit();
    }
</script>

<form:form name="frm" action="submitPasswordReset.do" method="post" modelAttribute="userInfoResend">

<div class="content">
        <spring:hasBindErrors name="userInfoResend">
            <div class="error">
                <ul>
                    <form:errors path="*" element="li" delimiter="</li><li>" id="" />
                </ul>
            </div>
        </spring:hasBindErrors>
    
        <!-- Sign In Column Starts -->
        
        <div class="signincolumn">
            <h1 class="redheading">Reset Password</h1>
                       <!-- Column one Starts -->
            <div style="float:left; width:300px; border:solid 0px;">
            <label class="inputlabel">Email: ${userInfoResend.emailAddress}</label>
            <label class="inputlabel">Enter new password:</label>
            
            <input class="input" type="password" name="newPassword" />
            <input class="input" type="hidden" name="field" value="passwordReset"/>
            <input class="input" type="hidden" name="resetPasswordToken" value="${userInfoResend.resetPasswordToken}"/>
            <input class="input" type="hidden" name="emailAddress" value="${userInfoResend.emailAddress}"/>
            <label class="inputlabel">Confirm password:</label>
            <input class="input" type="password" name="confirmPassword" />
            <div class="clearboth"></div>
                <br />
                <a class="button floatRight" href="javascript:null(0);" onclick="submitForm('frm')">
                    <span class="red">Reset Password</span></a>
            </div>
            <div class="clearboth" style="margin-bottom:18px;"></div>         
            <div class="clearboth"></div>
    </div>
</div>
    
</form:form>