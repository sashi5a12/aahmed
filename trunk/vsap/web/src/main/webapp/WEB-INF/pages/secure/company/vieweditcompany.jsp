<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.netpace.device.entities.enums.Role"%>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).ROLE_SUPER_ADMIN" 
    var="ROLE_SUPER_ADMIN" />

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).ROLE_VERIZON_ADMIN" 
    var="ROLE_VERIZON_SYSTEM_ADMIN" />

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).ROLE_OFAC" 
    var="ROLE_OFAC" />

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).ACTION_SUSPEND" 
    var="ActionSuspend" />

<h1 class="redheading" >Edit Company</h1>

<form:form name="frm" action="${pageContext.request.contextPath}/secure/updatecompany.do" method="post" commandName="companyProfile">
    <spring:hasBindErrors name="companyProfile">
        <div class="error">
            <ul>
                <form:errors path="*" element="li" delimiter="</li><li>" id="" />
            </ul>
        </div>
    </spring:hasBindErrors>
    <c:if test="${not empty COMPANY_UPDATED}">
        <div class="success"><p><fmt:message key="msg.company.update" /></p></div>
    </c:if>
    <c:if test="${not empty COMPANY_SUSPENDED}">
        <div class="success"><p><fmt:message key="msg.company.suspend" /></p></div>
    </c:if>
    <c:if test="${not empty COMPANY_UNSUSPENDED}">
        <div class="success"><p><fmt:message key="msg.company.unsuspend" /></p></div>
    </c:if>
    <a href="javascript:void(0);" class="bigTab"><span><span>Partner Info</span></span></a>
    <a href="${pageContext.request.contextPath}/secure/company/process.do?companyid=${companyProfile.company.id}" class="bigTabGray"><span><span>Workflow Processing</span></span></a>
    <div class="clearboth"></div> 
    <br />
    <div class="grayBoxLayouts">
        <div class="twoColmnLayout">
            <div class="columnOne">
                <form:hidden path="company.id" />
                <div class="redstar">*</div><label class="inputlabel">Company Name</label>
                <form:input path="company.companyName" class="input width320" value="${company.companyName}" type="text" maxlength="200" />
                <div class="clearboth"></div>
                <div class="redstar">*</div><label class="inputlabel">Company Legal Name</label>
                <form:input path="company.companyLegalName" class="input width320" value="${company.companyLegalName}" type="text" maxlength="200" />
                <div class="clearboth"></div>
                <label class="inputlabel">Main Phone Number</label>
                <form:input path="company.mainPhoneNumber" class="input width320" value="${company.mainPhoneNumber}" type="text" />
                <div class="clearboth"></div>
                <div class="redstar">*</div><label class="inputlabel">Main Company Street Address</label>
                <form:input path="company.mainCompanyStreetAddress" class="input width320" value="${company.mainCompanyStreetAddress}" type="text" />
                <div class="clearboth"></div>
                <div class="redstar">*</div><label class="inputlabel">City/Town</label>
                <form:input path="company.city" class="input width320" value="${company.city}" type="text" />
                <div class="clearboth"></div>
                <div class="redstar">*</div><label class="inputlabel">State/Province</label>
                <form:input path="company.state" class="input width320" value="${company.state}" type="text" />
                <div class="clearboth"></div>
            </div>
            <div class="columnTwo">
                <div class="redstar">*</div><label class="inputlabel">Company Website</label>
                <form:input path="company.website" class="input width320" value="${company.website}" type="text" maxlength="200" />
                <div class="clearboth"></div>
                <div class="redstar">*</div><label class="inputlabel">Company Domain</label>
                <div class="value"></div>
                <div>${companyProfile.company.domainName}</div>
                <div class="clearboth"></div>

                <div class="redstar">*</div><label class="inputlabel"><fmt:message key="companyProfile.company.country"/></label> <label for="select"></label>
                <form:select cssClass="selct" path="company.country" >
                    <form:option value="" label="--- Select ---" />
                    <form:options items="${populatedFormElements['countryList']}" />
                </form:select>

                <div class="clearboth"></div>
                <div class="redstar">*</div><label class="inputlabel">Zip Code/Postal Code</label>
                <form:input path="company.zip" class="input width320" value="${company.zip}" type="text" />
                <div class="clearboth"></div>
            </div>
            <div class="clearboth"></div>
        </div>
    </div>
    <!-- Gray Box Layout - Two column ends-->
    <br />

    <div class="grayBoxLayouts">
        <div class="twoColmnLayout">
            <form:hidden path="mainContactId" />
            <c:forEach items="${companyProfile.userInfoList}" var="userInfo" varStatus="loopStatus">
                <c:set var="rolesString" value="" />
                <c:forEach items="${userInfo.roles}" var="role">
                    <c:set var="rolesString" value='${rolesString}${role.roleName}'/>                        
                </c:forEach>
                <c:if test="${fn:contains(rolesString, 'ROLE_MPOC')}">                        
                    <div class="columnOne">
                        <h1>Main Contact Information</h1>
                        <div class="redstar">*</div><label class="inputlabel">Full name </label>
                        <select class="selct" onchange="javascript:changeMainContact(this);" id="userFullName" name="userFullName">
                            <c:forEach items="${companyProfile.userInfoList}" var="userInfo1">
                                <option 
                                    <c:if test="${userInfo1.id eq userInfo.id}">selected</c:if> 
                                    value="${userInfo1.id}" >${userInfo1.fullName}
                                </option>
                            </c:forEach>
                        </select>                        
                        <form:hidden path="userInfoList[${loopStatus.index}].id" />
                        <form:hidden path="userInfoList[${loopStatus.index}].userName" />
                        <div class="clearboth"></div>            
                        <label class="inputlabel">Phone Number</label>
                        <div id="phoneNumber" class="label-value">${userInfo.phoneNumber}</div>                    
                        <div class="clearboth"></div>
                        <label class="inputlabel">Mobile Phone Number</label>
                        <div id="mobilePhoneNumber" class="label-value">${userInfo.mobilePhoneNumber}</div>                    
                        <div class="clearboth"></div>
                        <label class="inputlabel">Email Address</label>
                        <div id="emailAddress" class="label-value">${userInfo.emailAddress}</div>
                        <div class="clearboth"></div>
                    </div>
                    <div class="columnTwo">
                        <h1>&nbsp;</h1>
                        <label class="inputlabel">Street Address</label>
                        <div id="address" class="label-value">${userInfo.address}</div>
                        <div class="clearboth"></div>
                        <label class="inputlabel">City/Town</label>
                        <div id="city" class="label-value">${userInfo.city}</div>                    
                        <div class="clearboth"></div>
                        <label class="inputlabel">State/Province</label>
                        <div id="state" class="label-value">${userInfo.state}</div>                    
                        <div class="clearboth"></div>
                        <label class="inputlabel">Country</label>
                        <div id="country" class="label-value">
                            <c:forEach items="${populatedFormElements['countryList']}" var="country1">
                                <c:set var="country" value="${fn:split(country1, '=')}" />
                                <c:if test="${country[0] eq userInfo.country}">${country[0]}</c:if>
                            </c:forEach>
                        </div>

                        <div class="clearboth"></div>
                        <label class="inputlabel">Zip Code/Postal Code</label>
                        <div id="zip" class="label-value">${userInfo.zip}</div>                    
                        <div class="clearboth"></div>
                    </div>
                    <div class="clearboth"></div>
                </c:if>

            </c:forEach>
            <div class="clearboth"></div>
        </div>
    </div>
    <br />
    <div class="grayBoxLayouts">
        <div class="twoColmnLayout">	
            <div class="columnOne">
                <h1>Sales Contact Info</h1>
                <div class="redstar">*</div><label class="inputlabel">Full name </label>
                <form:input path="company.salesContact.name" class="input width320" value="${company.salesContact.name}" type="text" />
                <div class="clearboth"></div>            
                <div class="redstar">*</div><label class="inputlabel">Phone Number</label>
                <form:input path="company.salesContact.phone" class="input width320" value="${company.salesContact.phone}" type="text" />
                <div class="clearboth"></div>            
            </div>
            <div class="columnTwo">
                <h1>&nbsp;</h1>
                <label class="inputlabel">Mobile Phone Number</label>
                <form:input path="company.salesContact.mobile" class="input width320" value="${company.salesContact.mobile}" type="text" />
                <div class="clearboth"></div>
                <div class="redstar">*</div><label class="inputlabel">Email Address</label>
                <form:input path="company.salesContact.emailAddress" class="input width320" value="${company.salesContact.emailAddress}" type="text" />
                <div class="clearboth"></div>
            </div>
            <div class="clearboth"></div>
        </div>
    </div>

    <!-- buttons starts --> 
    <a class="button floatRight marginLeft10" href="javascript:;" onclick="javascript:document.forms['frm'].submit();" ><span class="red">Update</span></a>
    <c:if test="${isCompanyApproved}">
        <sec:authorize ifAnyGranted="${ROLE_SUPER_ADMIN},${ROLE_VERIZON_SYSTEM_ADMIN},${ROLE_OFAC}">
            <c:set var="companySuspendOrNotLabel" value="Suspend" />
            <c:set var="companySuspendOrNotLink" value="${pageContext.request.contextPath}/secure/suspendcompany.do?companyid=${companyProfile.company.id}" />
            <c:if test="${companyProfile.company.suspended}">
                <c:set var="companySuspendOrNotLabel" value="UnSuspend" />
                <c:set var="companySuspendOrNotLink" value="${pageContext.request.contextPath}/secure/unsuspendcompany.do?companyid=${companyProfile.company.id}" />
            </c:if>
            <a class="button floatRight" href="${companySuspendOrNotLink}"><span class="${companySuspendOrNotLabel eq ActionSuspend ? 'red' : 'red'}">${companySuspendOrNotLabel}</span></a>
        </sec:authorize>
    </c:if>
    <!-- buttons ends -->
</form:form>

<script>
    function changeMainContact(name) {
        $("#mainContactId").val(name.value);
        <c:forEach items="${companyProfile.userInfoList}" var="userInfo" varStatus="loopStatus">
            if (${userInfo.id} == name.value) {
                $("#phoneNumber").text("${userInfo.phoneNumber}");
                $("#mobilePhoneNumber").text("${userInfo.mobilePhoneNumber}");
                $("#emailAddress").text("${userInfo.emailAddress}");
                $("#address").text("${userInfo.address}");
                $("#city").text("${userInfo.city}");
                $("#state").text("${userInfo.state}");
                $("#zip").text("${userInfo.zip}");
            <c:forEach items="${populatedFormElements['countryList']}" var="country1">
                <c:set var="country" value="${fn:split(country1, '=')}" />
                <c:if test="${country[0] eq userInfo.country}">
                        $("#country").html("${country[0]}");
                </c:if>
            </c:forEach>
        }
        </c:forEach>
    }
    
    var defaultMPOCUser = $("#userFullName").val();
    $("#mainContactId").val(defaultMPOCUser);
</script>