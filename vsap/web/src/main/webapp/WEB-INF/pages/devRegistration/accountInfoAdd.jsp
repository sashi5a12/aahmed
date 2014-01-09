<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Single Column Starts -->
<script>
    function submitForm(frmName) {
        $('a.js-single-click-link').removeAttr("href");
        document.forms[frmName].submit();
    }
</script>
<div class="content">
    <form:form name="frm" action="processRegistration.do" method="post" commandName="accountForm">
        <!--        <content tag="error">-->
        <spring:hasBindErrors name="accountForm">
            <div class="error">
                <ul>
                    <form:errors path="*" element="li" delimiter="</li><li>" id="" />
                </ul>
            </div>
        </spring:hasBindErrors>
        
        <c:if test="${expired}">
            <div class="error"><ul><li>Your account activation link is invalid or expired. Please complete the account registration form to re-send the account activation link.</li></ul></div>
                    </c:if>
        <!--        </content>-->
        <!-- Error message starts
        
        <div class="error">Your account has been activated. Please complete the form below to continue with Compnay Registration</div>
        
        Error message ends -->

        <!-- sucess message starts
        <div class="success">Your account has been activated. Please complete the form below to continue with Compnay Registration</div>
        sucess message ends -->


        <h1 class="redheading" >Account Registration</h1>
        <!-- Column one Starts -->
        <div style="float:left; width:300px; border:solid 0px;">
            <div class="redstar">*</div><label class="inputlabel">Full Name</label>
            <input class="input" type="text" name="fullName" value="${fn:escapeXml(accountForm.fullName)}" maxlength="70"/>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Phone Number</label>
            <input class="input" type="text" name="phoneNumber" value="${fn:escapeXml(accountForm.phoneNumber)}" maxlength="20"/>
            <div class="clearboth"></div><label class="inputlabel">Mobile Phone Number</label>
            <input class="input" type="text" name="mobilePhoneNumber" value="${fn:escapeXml(accountForm.mobilePhoneNumber)}" maxlength="20"/>
        </div>
        <!-- Column one ends -->
        <!-- Column two Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">Email Address</label>
            <input class="input" type="text"  name="emailAddress" value="${fn:escapeXml(accountForm.emailAddress)}" maxlength="100"/>
            <div class="marginTop5"></div>
            <span class="font12">Your email address must match your company domain
                in order to register for the Verizon Smart Accessory
                Portal</span>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Confirm Email Address</label>
            <input class="input" type="text" name="confirmEmailAddress" value="${fn:escapeXml(accountForm.confirmEmailAddress)}" maxlength="100"/>

            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Company Domain</label>
            <input class="input" type="text" id="companyDomain" name="companyDomain" value="${fn:escapeXml(accountForm.companyDomain)}" maxlength="250"/>
            <label class="inputlabel"><span class="font12">Company Domain e.g. mycompany.com</span></label>
        </div>
        <!-- Column two ends -->
        <!-- Column three Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">User Name</label>
            <input class="input" type="text" name="userName" value="${fn:escapeXml(accountForm.userName)}" maxlength="25"/>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Create Password</label>
            <input class="input" type="password" name="password" maxlength="25"/>
            <div class="marginTop5"></div>
            <span class="font12 ">Password is case sensitive and can have numbers & letters. It should be  6 to 25 characters in length.</span>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">Confirm Password</label>
            <input class="input" type="password" name="confirmPassword" maxlength="25"/>
        </div>

        <div class="clearboth"></div>
        <h1 class="redheading2">Personal Contact</h1>

        <!-- Column one Starts -->
        <div style="float:left; width:300px; border:solid 0px;">
            <div class="redstar">*</div><label class="inputlabel">Street Address</label>
            <input class="input" type="text" name="address" value="${fn:escapeXml(accountForm.address)}" maxlength="250"/>
            <div class="clearboth"></div>
            <div class="redstar">*</div><label class="inputlabel">City/Town</label>
            <input class="input" type="text" name="city" value="${fn:escapeXml(accountForm.city)}" maxlength="100"/>
        </div>
        <!-- Column one ends -->

        <!-- Column two Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">State/Province</label>
            <input class="input" type="text" name="state" value="${fn:escapeXml(accountForm.state)}" maxlength="100"/>
            <div class="clearboth"></div>

            <div class="redstar">*</div><label class="inputlabel">Country</label> <label for="select"></label>
            <form:select cssClass="selct" path="country" id="country">
                <form:option value="" label="--- Select ---" />
                <form:options items="${populatedFormElements['countryList']}" />
            </form:select>

        </div>
        <!-- Column two ends -->
        <!-- Column three Starts -->
        <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
            <div class="redstar">*</div><label class="inputlabel">Zip Code/Postal Code</label>
            <input class="input" type="text" name="zip" value="${fn:escapeXml(accountForm.zip)}" maxlength="20"/>
        </div>
        <!-- Column three ends -->
    </form:form>
    <div class="clearboth"></div>
    <br />

    <a class="button next floatRight marginRight15 highlightbutton js-single-click-link" href="javascript:submitForm('frm')"><span class="red"><span>Next</span></span></a>
    <div class="clearboth"></div>
    <br />
    <br />
</div>
