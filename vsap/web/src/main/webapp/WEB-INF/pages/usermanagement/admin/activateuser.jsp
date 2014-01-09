<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content">
    <h1 class="redheading" >Activate User</h1>
    <form:form name="frm" action="${pageContext.request.contextPath}/admin/acceptinvitation.do" method="post" modelAttribute="activateAdminVO">
        <input class="input" type="hidden" name="activationCode" value="${activateAdminVO.activationCode}"/>

        <spring:hasBindErrors name="activateAdminVO">
            <div class="error">
                <ul>
                    <form:errors path="*" element="li" delimiter="</li><li>" id="" />
                </ul>
            </div>
        </spring:hasBindErrors>

        <!-- Column one Starts -->
        <div style="float:left; width:300px; border:solid 0px;">
            <div class="redstar">*</div><label class="inputlabel">Full Name</label>
            <input class="input" type="text" name="fullName" value="${activateAdminVO.fullName}"/>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Phone Number</label>
            <input class="input" type="text" name="phoneNumber" value="${activateAdminVO.phoneNumber}"/>
            <div class="clearboth"></div><label class="inputlabel">Mobile Phone Number</label>
            <input class="input" type="text" name="mobilePhoneNumber" value="${activateAdminVO.mobilePhoneNumber}"/>
        </div>
        <!-- Column one ends -->
        <!-- Column two Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">Email Address</label>
            <label style="" class="activate-admin-space" >${activateAdminVO.emailAddress}</label>
            <input type="hidden" id="emailAddress" name="emailAddress" value="${activateAdminVO.emailAddress}"/>
            <input type="hidden" id="confirmEmailAddress" name="confirmEmailAddress" value="${activateAdminVO.emailAddress}"/>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">User Name</label>
            <input class="input" type="text" name="userName" value="${activateAdminVO.userName}"/>
            <div class="clearboth"></div>
        </div>
        <!-- Column two ends -->
        <!-- Column three Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">Create Password</label>
            <input class="input" type="password" name="password" />
            <div class="marginTop5"></div>
            <span class="font12 ">Password is case sensitive and can have numbers & letters. It should be  6 to 25 characters in length.</span>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Confirm Password</label>
            <input class="input" type="password" name="confirmPassword" />
        </div>
        <!-- Column three ends -->

        <div class="clearboth"></div><br /><br />
        <h1 class="redheading2">Personal Contact</h1>

        <!-- Column one Starts -->
        <div style="float:left; width:300px; border:solid 0px;">
            <div class="redstar">*</div><label class="inputlabel">Street Address</label>
            <input class="input" type="text" name="address" value="${activateAdminVO.address}"/>
            <div class="clearboth"></div>

            <div class="redstar">*</div><label class="inputlabel">City/Town</label>
            <input class="input" type="text" name="city" value="${activateAdminVO.city}"/>
        </div>
        <!-- Column one ends -->
        <!-- Column two Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">State/Province</label>
            <input class="input" type="text" name="state" value="${activateAdminVO.state}"/>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel"><fmt:message key="activateAdminVO.country"/></label> <label for="select"></label>
            <form:select cssClass="selct" path="country" id="country">
                <form:option value="" label="--- Select ---" />
                <form:options items="${populatedFormElements['countryList']}" />
            </form:select>
            
            <div class="clearboth"></div>
        </div>
        <!-- Column two ends -->
        <!-- Column three Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">Zip Code/Postal Code</label>
            <input class="input" type="text" name="zip" value="${activateAdminVO.zip}"/>
        </div>
        <!-- Column three ends -->
    </form:form>
    <!-- Column three ends -->
    <div class="clearboth"></div>
    <br />

    <a class="button floatRight" href="javascript:void(0);" onclick="javascript:document.forms['frm'].submit()" ><span class="red">Submit</span></a>
    <div class="clearboth"></div>
    <br />
    <br />
</div>
