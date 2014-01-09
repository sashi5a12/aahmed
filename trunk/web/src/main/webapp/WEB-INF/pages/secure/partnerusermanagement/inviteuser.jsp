<%@page import="org.apache.commons.lang.StringUtils"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<script>
    function submitForm(frmName) {
        $('a.js-single-click-link').removeAttr("href");
        document.forms[frmName].submit();
    }
</script> 
<h1 class="redheading" >Manage Users - Invite User</h1>
<!-- Gray Box Layout - Two column Starts-->
<div class="emptyBoxLayouts">
    <div class="twoColmnLayout">
        <form:form name="inviteUserFrm" action="${pageContext.request.contextPath}/secure/company/inviteuser.do" method="post" modelAttribute="invitePartnerUserVO">
            <spring:hasBindErrors name="invitePartnerUserVO">
                <div class="error">
                    <ul>
                        <form:errors path="*" element="li" delimiter="</li><li>" id="" />
                    </ul>
                </div>
            </spring:hasBindErrors>
            <c:if test="${expired}">
                <div class="error"><ul><li>Your account activation link is invalid or expired. Please complete the account registration form to re-send the account activation link.</ul></li></div>
            </c:if>
            <div class="columnOne">
                <div class="redstar">*</div><label class="inputlabel">Email Address</label>
                <form:input path="emailAddress" class="input" value="${invitePartnerUserVO.emailAddress}" maxlength="100" />
                <div class="clearboth"></div>

                <div class="redstar">*</div><label class="inputlabel">Full Name</label>
                <form:input path="fullName" class="input" value="${invitePartnerUserVO.fullName}" maxlength="70" />
                <div class="clearboth"></div>        
            </div>
            <div class="clearboth"></div>
        </form:form>
    </div>
</div>

<br />
<br /><br /><br /><br /><br /><br />

<div class="marginBottom40"></div>
<div class="clearboth"></div>

<!-- buttons starts --> 

<a class="button floatRight marginTop20 marginLeft10" href="${pageContext.request.contextPath}/secure/company/manageusers.do" ><span class="gray">Cancel</span></a> 
<a class="button floatRight marginTop20 js-single-click-link" href="javascript:submitForm('inviteUserFrm');" ><span class="red">Invite</span></a>  

<!-- buttons ends -->
