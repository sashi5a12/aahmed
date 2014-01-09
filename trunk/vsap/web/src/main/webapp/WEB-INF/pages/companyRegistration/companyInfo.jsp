<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
    function submitForm(frmName) {
        $('a.js-single-click-link').removeAttr("href");
        document.forms[frmName].submit();
    }
</script>

<form:form name="frm" action="registercompany.do" method="post" modelAttribute="companyForm">    
    <form:hidden path="user.id" />
    <spring:hasBindErrors name="companyForm">
        <div class="error">
            <ul>
                <form:errors path="*" element="li" delimiter="</li><li>" id="" />
            </ul>
        </div>
    </spring:hasBindErrors>

    <c:if test="${activationSuccessful}">
        <div class="success"><p>Your account has been activated. Please complete the form below to continue with Company Registration</p></div>
    </c:if>

    <!-- Error message starts
    
    <div class="error">Your account has been activated. Please complete the form below to continue with Company Registration</div>
    
    Error message ends -->

    <h1 class="redheading">Company Registration</h1>
    <!-- Column one Starts -->
    <div style="float:left; width:300px; border:solid 0px;">
        <div class="redstar">*</div><label class="inputlabel">Company Name</label>
        <input class="input" type="text" value="${fn:escapeXml(companyForm.companyName)}" name="companyName" maxlength="200" />
        <div class="clearboth"></div>
        <div class="redstar">*</div><label class="inputlabel">Company Legal Name</label>
        <input class="input" type="text" value="${fn:escapeXml(companyForm.companyLegalName)}" name="companyLegalName" maxlength="200" />
        <div class="clearboth"></div>
        <div class="redstar">*</div><label class="inputlabel">Company Web Site</label>
        <input class="input" type="text" value="${fn:escapeXml(companyForm.website)}" name="website" maxlength="200" />
        <div class="clearboth"></div>
        <div class="redstar">*</div><label class="inputlabel">Company Domain</label>
        <strong>${companyForm.domainName}</strong>
        <input class="input" type="hidden" value="${fn:escapeXml(companyForm.domainName)}" name="domainName" maxlength="250" />
        <div class="clearboth"></div>
    </div>
    <!-- Column one ends -->
    <!-- Column two Starts -->
    <div style="float:left; width:300px; border:solid 0px; margin-left:35px;">
        <label class="inputlabel">Main Phone Number</label>
        <input class="input" type="text" value="${fn:escapeXml(companyForm.mainPhoneNumber)}" name="mainPhoneNumber" maxlength="20" />
        <div class="clearboth"></div>
        <div class="redstar">*</div><label class="inputlabel">Main Company Street Address</label>
        <textarea class="textfield" cols="45" rows="5" name="mainCompanyStreetAddress" maxlength="250">${fn:escapeXml(companyForm.mainCompanyStreetAddress)}</textarea>
        <div class="marginTop5"></div>
        <div class="clearboth"></div>
        <div class="redstar">*</div><label class="inputlabel">City/Town</label>
        <input class="input" type="text" value="${fn:escapeXml(companyForm.city)}" name="city" maxlength="100" />

        <div class="clearboth"></div>
        <div class="redstar">*</div><label class="inputlabel">State/Province</label>
        <input class="input" type="text" value="${fn:escapeXml(companyForm.state)}" name="state" maxlength="100" />
        <div class="clearboth"></div>

        <div class="redstar">*</div><label class="inputlabel"><fmt:message key="companyForm.country"/></label> <label for="select"></label>
        <form:select cssClass="selct" path="country" id="country">
            <form:option value="" label="--- Select ---" />
            <form:options items="${populatedFormElements['countryList']}" />
        </form:select>

        <div class="clearboth"></div>
        <div class="redstar">*</div><label class="inputlabel">Zip Code/Postal Code</label>
        <input class="input" type="text" value="${fn:escapeXml(companyForm.zip)}" name="zip" maxlength="20" />
    </div>
    <!-- Column two ends -->
    <!-- Column three Starts -->
    <div style="float:left; width:305px; border:solid 0px; margin-left:35px; margin-top: -36px;">
        <h1 class="salesContactHeading">Sales Contact Info</h1>
        <div class="redstar">*</div><label class="inputlabel">Full Name</label>
        <input class="input" type="text" value="${fn:escapeXml(companyForm.salesContact.name)}" name="salesContact.name" maxlength="70" />
        <div class="clearboth"></div>
        <div class="redstar">*</div><label class="inputlabel">Phone Number</label>
        <input class="input" type="text" value="${fn:escapeXml(companyForm.salesContact.phone)}" name="salesContact.phone" maxlength="20" />
        <div class="clearboth"></div>
        <label class="inputlabel">Mobile Number</label>
        <input class="input" type="text" value="${fn:escapeXml(companyForm.salesContact.mobile)}" name="salesContact.mobile" maxlength="20" />
        <div class="clearboth"></div>
        <div class="redstar">*</div><label class="inputlabel">Email Address</label>
        <input class="input" type="text" value="${fn:escapeXml(companyForm.salesContact.emailAddress)}" name="salesContact.emailAddress" maxlength="100" />
        <div class="clearboth"></div>
    </div>
    <!-- Column three ends -->
    <div class="clearboth"></div>
    <br />

    <!-- buttons starts --> 
    <a class="button floatRight js-single-click-link" href="javascript:void(0);" onclick="javascript:submitForm('frm')" ><span class="red">Submit</span></a>
    <!-- buttons ends -->

</form:form>
<div class="clearboth"></div>
<br />
<br />