<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- Single Column Starts -->
<h1 class="redheading" >My Profile</h1>
<!-- Gray Box Layout - Two column Starts -->
<div class="grayBoxLayouts">
    <div class="twoColmnLayout">	
        <form:form name="myProfileFrm" action="updateprofile.do" method="post" commandName="myProfileForm">
            <input type="hidden" name="userName" value="${myProfileForm.userName}" />            
            <input type="hidden" name="companyDomain" value="${myProfileForm.companyDomain}" />
            <spring:hasBindErrors name="myProfileForm">
                <div class="error">
                    <ul>
                        <form:errors path="*" element="li" delimiter="</li><li>" id="" />
                    </ul>
                </div>
            </spring:hasBindErrors>
            <c:if test="${not empty PROFILE_UPDATED}">
                <div class="success"><p><fmt:message key="msg.profile.update" /></p></div>
                    </c:if>
                    <c:if test="${expired}">
                <div class="error"><ul><li>Your account activation link is invalid or expired. Please complete the account registration form to re-send the account activation link.</li></ul></div>
                        </c:if>
            <div class="columnOne">
                <div class="redstar">*</div><label class="inputlabel">Full Name</label>
                <input class="input" type="text" name="fullName" value="${fn:escapeXml(myProfileForm.fullName)}" maxlength="70"/>
                <div class="clearboth"></div>

                <div class="redstar">*</div>
                <label class="inputlabel">Email Address</label>
                <input class="input" type="text" name="emailAddress" value="${fn:escapeXml(myProfileForm.emailAddress)}" readonly="readonly" maxlength="100" />
                <sec:authorize ifAllGranted="ROLE_PARTNER_USER">
                    <div class="marginTop5"></div>
                    <span class="font12">Your email address must match your company domain
                        in order to register for the Verizon Smart Accessory
                        Portal</span>
                </sec:authorize>

                <div class="clearboth"></div>

                <div class="redstar">*</div><label class="inputlabel">Phone Number</label>
                <input class="input" type="text" name="phoneNumber" value="${fn:escapeXml(myProfileForm.phoneNumber)}" maxlength="20"/>

                <div class="clearboth"></div>    

                <div class="redstar">*</div><label class="inputlabel">Street Address</label>
                <textarea class="textfield" name="address" style="width:290px; height:78px;" maxlength="250">${myProfileForm.address}</textarea>
                <div class="clearboth"></div>

                <div class="redstar">*</div><label class="inputlabel">City/town</label>
                <input class="input" type="text" name="city" value="${fn:escapeXml(myProfileForm.city)}" maxlength="100"/>
                <div class="clearboth"></div>

            </div>
            <div class="columnTwo">
                <div class="spacebox1"> 
                    <div style="display:block;">
                        <sec:authorize ifAllGranted="ROLE_PARTNER_USER">
                            <label class="inputlabel">Company Domain</label>
                            <div class="label-value">${myProfileForm.companyDomain}</div>
                            <div class="clearboth"></div> 
                        </sec:authorize>
                    </div>
                </div>
                <label class="inputlabel">Mobile Phone Number</label>
                <input class="input" type="text" name="mobilePhoneNumber" value="${fn:escapeXml(myProfileForm.mobilePhoneNumber)}" maxlength="20"/>
                <div class="clearboth"></div>

                <div class="redstar">*</div><label class="inputlabel">State/Province</label>
                <input class="input" type="text" name="state" value="${fn:escapeXml(myProfileForm.state)}" maxlength="100"/>
                <div class="clearboth"></div>

                <div class="redstar">*</div>
                <label class="inputlabel">Zip Code/Postal Code</label>
                <input class="input" type="text" name="zip" value="${fn:escapeXml(myProfileForm.zip)}" maxlength="20"/>
                <div class="clearboth"></div>
                
            <label class="inputlabel"><fmt:message key="myProfileForm.country"/></label> <label for="select"></label>
            <form:select cssClass="selct" path="country" id="country">
                <form:option value="" label="--- Select ---" />
                <form:options items="${populatedFormElements['countryList']}" />
            </form:select>
                
                <div class="clearboth"></div>     

                <a class="button floatRight marginTop20 highlightbutton" style="margin-right:62px;" href="javascript:submitForm('myProfileFrm')"><span class="red">Update</span></a>                
                <div class="clearboth"></div>

                <div class="clearboth"></div>
            </div>
            <div class="clearboth"></div>
        </form:form>
    </div>
</div>
<br />
<div class="grayBoxLayouts">
    <div class="twoColmnLayout">
        <form:form name="userNameFrm" action="updateusername.do" method="post" modelAttribute="userNameForm" id="updateUsernameSection">

            <spring:hasBindErrors name="userNameForm">
                <div class="error">
                    <ul>
                        <form:errors path="*" element="li" delimiter="</li><li>" id="" />
                    </ul>
                </div>
            </spring:hasBindErrors>
            <c:if test="${not empty USERNAME_UPDATED}">
                <div class="success"><p><fmt:message key="msg.username.update" /></p></div>
            </c:if>
            <h1>Update Username</h1>
            <div class="columnOne">

                <div class="redstar">*</div>
                <label class="inputlabel">Enter New Username</label>
                <input class="input" type="text" name="userName" value="${fn:escapeXml(userNameForm.userName)}" maxlength="25"/>
                <div class="clearboth"></div>
            </div>

            <div class="columnTwo">
                <div class="redstar">*</div>
                <label class="inputlabel">Enter Current Password</label>
                <input class="input" type="password" name="currentPassword" value="" maxlength="25" />
                <div class="clearboth"></div>

                <a class="button floatRight marginTop20 highlightbutton" style="margin-right:62px;" href="javascript:submitForm('userNameFrm')">
                    <span class="red">Update Username</span>
                </a>
                <div class="clearboth"></div>       
                <div class="clearboth"></div>
            </div>
            <div class="clearboth"></div>
        </form:form>
    </div>
</div>
<br />
<div class="grayBoxLayouts">
    <div class="twoColmnLayout">
        <form:form name="changePasswordFrm" action="updatepassword.do" method="post" modelAttribute="changePasswordForm" id="resetPasswordSection">
            <spring:hasBindErrors name="changePasswordForm">
                <div class="error">
                    <ul>
                        <form:errors path="*" element="li" delimiter="</li><li>" id="" />
                    </ul>
                </div>
            </spring:hasBindErrors>
            <c:if test="${not empty PASSWORD_UPDATED}">
                <div class="success"><p><fmt:message key="msg.password.update" /></p></div>
            </c:if>
            <h1>Reset Password</h1>	

            <div class="columnOne">
                <div class="redstar">*</div>
                <label class="inputlabel">Enter Current Password</label>
                <input class="input" type="password" name="currentPassword" value="" maxlength="25" />
                <div class="clearboth"></div></div>
            <div class="columnTwo">
                <div class="redstar">*</div>
                <label class="inputlabel">Enter New Password</label>

                <input class="input" type="password" name="createPassword" value="" maxlength="25" />
                <div class="marginTop5" style="width:291px;"><span class="font12">Password is case sensitive and can have numbers & letters. It should be  6 to 25 characters in length.</span></div>

                <div class="clearboth"></div>

                <div class="redstar">*</div>
                <label class="inputlabel">Confirm New Password</label>

                <input class="input" type="password" name="confirmPassword" value="" maxlength="25"/>
                <div class="clearboth"></div>

                <a class="button floatRight marginTop20 highlightbutton" style="margin-right:62px;" href="javascript:submitForm('changePasswordFrm')"><span class="red">Update Password</span></a>
                <div class="clearboth"></div>

                <div class="clearboth"></div>
            </div>
            <div class="clearboth"></div>
        </form:form>
    </div>
</div>

<script>
    function submitForm(frmName) {
        document.forms[frmName].submit();
    }
    $(document).ready(function(){
        <c:if test="${not empty JUMP_TO}">
            $(document).scrollTop( $("#${JUMP_TO}").offset().top );
        </c:if>
    });
</script>