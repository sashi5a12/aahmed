<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.netpace.device.utils.VAPConstants" %>

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

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).ROLE_PARTNER_USER" 
    var="ROLE_PARTNER_USER" />

<h1 class="redheading" >View Partner</h1>

<form:form name="frm" action="updatecompany.do" method="post" modelAttribute="companyProfile">
    <spring:hasBindErrors name="companyProfile">
        <div class="error">
            <ul>
                <form:errors path="*" element="li" delimiter="</li><li>" id="" />
            </ul>
        </div>
    </spring:hasBindErrors>
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
            <div class="columnOne"><label class="inputlabelbold">Company Name
                </label>
                <div class="label-value">${companyProfile.company.companyName}</div>
                <div class="clearboth"></div><label class="inputlabelbold">Company Legal Name</label>
                <div class="label-value">${companyProfile.company.companyLegalName}</div>
                <div class="clearboth"></div><label class="inputlabelbold">Main Phone Number</label>
                <div class="label-value">${companyProfile.company.mainPhoneNumber}</div>
                <div class="clearboth"></div><label class="inputlabelbold">Main Company Street Address</label>
                <div class="label-value">${companyProfile.company.mainCompanyStreetAddress}<br />
                </div>
                <div class="clearboth"></div><label class="inputlabelbold">City/Town</label>
                <div class="label-value">${companyProfile.company.city}</div>
                <div class="clearboth"></div><label class="inputlabelbold">State/Province</label>
                <div class="label-value">${companyProfile.company.state}</div>
                <div class="clearboth"></div>
            </div>
            <div class="columnTwo">
                <label class="inputlabelbold">Company Web Site</label>
                <div class="label-value">${companyProfile.company.website}</div>
                <div class="clearboth"></div>
                <label class="inputlabelbold">Company Domain</label>
                <div class="label-value">${companyProfile.company.domainName}</div>

                <div class="clearboth"></div>
                <label class="inputlabelbold">Country</label>
                <div class="label-value">${companyProfile.company.country}</div>
                <div class="clearboth"></div>
                <label class="inputlabelbold">Zip Code/Postal Code</label>
                <div class="label-value">${companyProfile.company.zip}</div>
                <div class="clearboth"></div>

                <div class="clearboth"></div>
            </div>
            <div class="clearboth"></div>
        </div>
    </div>
    <br />

    <div class="grayBoxLayouts">        
        <div class="twoColmnLayout">	
            <c:forEach items="${companyProfile.userInfoList}" var="userInfo" varStatus="loopStatus">
                <c:set var="rolesString" value="" />
                <c:forEach items="${userInfo.roles}" var="role">
                    <c:set var="rolesString" value='${rolesString}${role.roleName}'/>                        
                </c:forEach>
                <c:if test="${fn:contains(rolesString, 'ROLE_MPOC')}">
                    <div class="columnOne">
                        <h1>Main Point of Contact</h1>
                        <div class="redstar">*</div><label class="inputlabelbold">Full name </label>
                        <div class="label-value">${userInfo.fullName}</div>
                        <form:hidden path="userInfoList[${loopStatus.index}].id" />
                        <form:hidden path="userInfoList[${loopStatus.index}].userName" />
                        <div class="clearboth"></div>            
                        <label class="inputlabelbold">Phone Number</label>
                        <div class="label-value">${userInfo.phoneNumber}</div>
                        <div class="clearboth"></div>
                        <label class="inputlabelbold">Mobile Phone Number</label>
                        <div class="label-value">${userInfo.mobilePhoneNumber}</div>                    
                        <div class="clearboth"></div>
                        <label class="inputlabelbold">Email Address</label>
                        <div class="label-value">${userInfo.emailAddress}</div>
                        <div class="clearboth"></div>
                    </div>
                    <div class="columnTwo">
                        <h1>&nbsp;</h1>
                        <label class="inputlabelbold">Street Address</label>
                        <div class="label-value">${userInfo.address}</div>
                        <div class="clearboth"></div>
                        <label class="inputlabelbold">City/Town</label>
                        <div class="label-value">${userInfo.city}</div>                    
                        <div class="clearboth"></div>
                        <label class="inputlabelbold">State/Province</label>
                        <div class="label-value">${userInfo.state}</div>                    
                        <div class="clearboth"></div>
                        <label class="inputlabelbold">Country</label>
                        <div id="country" class="label-value">
                            ${userInfo.country}
                        </div>
                        <div class="clearboth"></div>
                        <label class="inputlabelbold">Zip Code/Postal Code</label>
                        <div class="label-value">${userInfo.zip}</div>                    
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
        <h1>Sales Contact Info</h1>
        <div class="twoColmnLayout">	
            <div class="columnOne"><label class="inputlabelbold">Full Name</label>
                <div class="label-value">${companyProfile.company.salesContact.name}</div>
                <div class="clearboth"></div><label class="inputlabelbold">Phone Number</label>
                <div class="label-value">${companyProfile.company.salesContact.phone}</div>
                <div class="clearboth"></div>
            </div>
            <div class="columnTwo">
                <label class="inputlabelbold">Mobile Phone Number</label>
                <div class="label-value">${companyProfile.company.salesContact.mobile}</div>
                <div class="clearboth"></div><label class="inputlabelbold">Email Address</label>
                <div class="label-value">${companyProfile.company.salesContact.emailAddress}</div>
                <div class="clearboth"></div>
                <div class="clearboth"></div>
            </div>
            <div class="clearboth"></div>
        </div>
    </div>
    <!-- buttons starts -->
    <sec:authorize ifNotGranted="${ROLE_PARTNER_USER}" >
        <a class="button floatRight marginLeft10" href="${pageContext.request.contextPath}/secure/managePartners.do" ><span class="gray">Cancel</span></a>
    </sec:authorize>
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
