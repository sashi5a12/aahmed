<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Single Column Starts -->
<div class="content">
    <form:form name="frm" action="${pageContext.request.contextPath}/user/acceptinvitation.do" method="post" modelAttribute="accountForm">

        <spring:hasBindErrors name="accountForm">
            <div class="error">
                <ul>
                    <form:errors path="*" element="li" delimiter="</li><li>" id="" />
                </ul>
            </div>
        </spring:hasBindErrors>

        <c:if test="${expired}">
            <div class="error"><u><li>Your account activation link is invalid or expired. Please complete the account registration form to re-send the account activation link.</li></ul></div>
                    </c:if>

        <input class="input" type="hidden" id="activationCode" name="activationCode" value="${accountForm.activationCode}" />

        <h1 class="redheading">Account Registration</h1>
        <!-- Column one Starts -->
        <div style="float:left; width:300px; border:solid 0px;">
            <div class="redstar">*</div><label class="inputlabel">Full Name</label>
            <input class="input" type="text" name="fullName" value="${accountForm.fullName}"/>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Phone Number</label>
            <input class="input" type="text" name="phoneNumber" value="${accountForm.phoneNumber}"/>
            <div class="clearboth"></div><label class="inputlabel">Mobile Phone Number</label>
            <input class="input" type="text" name="mobilePhoneNumber" value="${accountForm.mobilePhoneNumber}"/>
        </div>
        <!-- Column one ends -->
        <!-- Column two Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">Email Address</label>
            <label class="activate-partner-space">${accountForm.emailAddress}</label>
            <input type="hidden" id="emailAddress" name="emailAddress" value="${accountForm.emailAddress}"/>
            <input type="hidden" id="confirmEmailAddress" name="confirmEmailAddress" value="${accountForm.emailAddress}"/>
            <div class="marginTop5"></div>

            <div class="clearboth"></div>

            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Company Domain</label>
            <label  class="activate-partner-space" >${accountForm.companyDomain}</label>
            <input type="hidden" id="companyDomain" name="companyDomain" value="${accountForm.companyDomain}"/>
        </div>
        <!-- Column two ends -->
        <!-- Column three Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">User Name</label>
            <input class="input" type="text" name="userName" value="${accountForm.userName}"/>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Create Password</label>
            <input class="input" type="password" name="password" />
            <div class="marginTop5"></div>
            <span class="font12 ">Password is case sensitive and can have numbers & letters. It should be  6 to 25 characters in length.</span>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Confirm Password</label>
            <input class="input" type="password" name="confirmPassword" />
        </div>

        <div class="clearboth"></div>
        <h1 class="redheading2">Personal Contact</h1>

        <!-- Column one Starts -->
        <div style="float:left; width:300px; border:solid 0px;">
            <div class="redstar">*</div><label class="inputlabel">Street Address</label>
            <input class="input" type="text" name="address" value="${accountForm.address}"/>
            <div class="clearboth"></div>

            <div class="redstar">*</div><label class="inputlabel">City/Town</label>
            <input class="input" type="text" name="city" value="${accountForm.city}"/>
        </div>
        <!-- Column one ends -->
        <!-- Column two Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">State/Province</label>
            <input class="input" type="text" name="state" value="${accountForm.state}"/>
            <div class="clearboth"></div>

            <div class="redstar">*</div><label class="inputlabel"><fmt:message key="accountForm.country"/></label> <label for="select"></label>
            <form:select cssClass="selct" path="country" id="country">
                <form:option value="" label="--- Select ---" />
                <form:options items="${populatedFormElements['countryList']}" />
            </form:select>
        </div>
        <!-- Column two ends -->
        <!-- Column three Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">Zip Code/Postal Code</label>
            <input class="input" type="text" name="zip" value="${accountForm.zip}"/>
        </div>
        <!-- Column three ends -->
    </form:form>
    <div class="clearboth"></div>
    <br />

    <a class="button floatRight" href="javascript:void(0);" onclick="javascript:document.forms['frm'].submit()" ><span class="red">Submit</span></a>
    <div class="clearboth"></div>
    <br />
    <br />
</div>
