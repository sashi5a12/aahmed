<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="content">
    <h1 class="redheading" >Confirm User Activation</h1>
    <form:form name="frm" action="${pageContext.request.contextPath}/activateadminconfirm.do" method="post" modelAttribute="activateAdminVO">

        <spring:hasBindErrors name="activateAdminVO">
            <div class="error"><form:errors path="*"/></div>
        </spring:hasBindErrors>

        <input type="hidden" name="activationCode" value="${activateAdminVO.activationCode}"/>
        <input type="hidden" name="activationType" value="${activateAdminVO.activationType}"/>
        <input type="hidden" name="fullName" value="${activateAdminVO.fullName}"/>
        <input type="hidden" name="phoneNumber" value="${activateAdminVO.phoneNumber}"/>
        <input type="hidden" name="mobilePhoneNumber" value="${activateAdminVO.mobilePhoneNumber}"/>
        <input type="hidden" name="emailAddress" value="${activateAdminVO.emailAddress}"/>
        <input type="hidden" name="userName" value="${activateAdminVO.userName}"/>
        <input type="hidden" name="password" value="${activateAdminVO.password}"/>
        <input type="hidden" name="confirmPassword" value="${activateAdminVO.confirmPassword}"/>
        <input type="hidden" name="confirm" value="false"/>

        <div class="clearboth"></div>
        <br>
        <p>This user account already exists in the Verizon Smart Accessories portal. If you continue the current data associated with this account will be lost.</p>
        <br>
        <br>
        <p>Click on Continue to proceed forward.</p>
        <br>
        <br>
    </form:form>
    <a class="button mLeftBtn" href="javascript:void(0);" style="float:right;" onclick="javascript:document.forms['frm'].confirm.value='false';document.forms['frm'].submit()"><span class="gray">Cancel</span></a>
    <a class="button floatRight" href="javascript:void(0);" onclick="javascript:document.forms['frm'].confirm.value='true';document.forms['frm'].submit()"><span class="red">Continue</span></a>
    <div class="clearboth"></div>
    <br />
    <br />
</div>
