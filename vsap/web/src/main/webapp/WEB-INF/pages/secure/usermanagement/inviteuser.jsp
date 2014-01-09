<%@page import="org.apache.commons.lang.StringUtils"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>


<h1 class="redheading" >Manage Users - Invite User</h1>

<form:form name="frm" action="inviteuser.do" method="post" modelAttribute="inviteUserVO">

    <spring:hasBindErrors name="inviteUserVO">
        <div class="error">
            <ul>
                <form:errors path="*" element="li" delimiter="</li><li>" id="" />
            </ul>
        </div>
    </spring:hasBindErrors>

    <div class="emptyBoxLayouts">
        <div class="twoColmnLayout">

            <div class="columnOne">
                <div class="redstar">*</div> <label class="inputlabel">Email Address</label>
                <input class="input" type="text" value="${inviteUserVO.emailAddress}" id="emailAddress" name="emailAddress" style="width:350px;" size="200" maxlength="100"/>
                <div class="clearboth"></div>

                <div class="redstar">*</div> <label class="inputlabel">Full Name</label>
                <input class="input" type="text" value="${inviteUserVO.fullName}" id="fullName" name="fullName" style="width:350px;" size="200" maxlength="70"/>
                <div class="clearboth"></div>
            </div>
            <div class="clearboth"></div>
        </div>
    </div>
    <div class="emptyBoxLayouts">
        <div class="twoColmnLayout">
            <div class="redstar">*</div> <label class="inputlabelBig">User Roles</label>
            <div class="columnOne">
                <c:forEach var="item" varStatus="counter" items="${systemRolesList}">
                    <c:if test="${(counter.index+1) % 2 != 0}">
                        <form:checkbox path="userRoles" label="${item.displayTitle}" value="${item.roleName}" ></form:checkbox>
                            <div class="clearboth"></div>   
                    </c:if>
                </c:forEach>
            </div>
            <div class="columnTwo">
                <c:forEach var="item" varStatus="counter" items="${systemRolesList}">
                    <c:if test="${(counter.index+1) % 2 == 0}">
                        <form:checkbox path="userRoles" label="${item.displayTitle}" value="${item.roleName}" ></form:checkbox>
                            <div class="clearboth"></div>  
                    </c:if>
                </c:forEach>
            </div>
            <div class="clearboth"></div>
        </div>
        <div class="clearboth"></div>  
    </div>


</form:form>

<!-- buttons starts -->
<a class="button floatRight marginTop20 marginLeft10" href="${pageContext.request.contextPath}/secure/admin/userslist.do" ><span class="gray">Cancel</span></a> 
<a class="button floatRight marginTop20 js-single-click-link"  href="javascript:void(0);" onclick="javascript:submitForm();" ><span class="red">Invite</span></a> 
<!-- buttons ends -->

<script type="text/javascript">
    function submitForm() {
        $('a.js-single-click-link').removeAttr("onclick");
        document.forms['frm'].submit();
    }
</script>
