<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/common/error.jsp" %>

<DIV class="homeColumnTab xlBox">

<table width="950px" style="margin:10px;" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td>
            <strong>Please Read This First:</strong>

            <p> Thank you for your interest in working with Verizon Wireless Music!</p>

            <p> Please only apply if you are an owner or authorized representative of a catalog of sound recordings
                for digital and mobile distribution.</p>

            <p> This submission form is solely for the purpose of establishing or
                continuing a dialogue with Verizon Wireless about a business
                relationship for the supply of some product or service to or through
                Verizon Wireless Music. Please do not submit here if you have some other
                purpose. For example, if you are a Verizon Wireless cell-phone customer
                with billing or product inquiries, please do not register here; instead,
                you should visit <a href="http://www.verizonwireless.com/">
                http://www.verizonwireless.com</a> and register at My Account.
            </p>

            <p> When you do submit here, if there is not enough basic information in
                the form to determine who you are, how to reach you by email and
                phone, what organization or company you are with, and the basic purpose
                for your submission, then your submission will not be considered. </p>
        </td>
    </tr>
</table>

<html:form action="/allianceMusicRegistrationUpdate" focus="companyName">
<html:hidden property="task" value="create"/>
<html:hidden property="vzdnManagerRoles"/>
<html:hidden property="email"/>
<html:hidden property="firstName"/>
<html:hidden property="lastName"/>
<html:hidden property="title"/>
<html:hidden property="officePhone"/>
<html:hidden property="fax"/>
<html:hidden property="mobilePhone"/>



<DIV class="headLeftCurveblk"></DIV>
<H1>Basic Information</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">

<table width="100%" cellpadding="5" class="GridGradient" border="0" cellspacing="0">
    <tr align="top">
        <th width="50%">
            <b>Firm Name <span class="requiredText">*</span></b><br/>
            <html:text property="companyName" size="45" maxlength="250" styleClass="inputField"/>
        </th>
        <th width="50%">
            <b>Web site URL <span class="requiredText">*</span></b><br/>
            <html:text styleClass="inputField" property="webSiteUrl" size="45" maxlength="250"/>
        </th>
    </tr>
</table>

<div> &nbsp; </div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Which Products are you interested in supplying content for:<span class="requiredText">*</span></H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table width="100%" class="Grid2" border="1" cellpadding="0" cellspacing="0">
        <tr>
            <th>  &nbsp;</th>
            <th >Product<br/>Type</th>
            <th >Size of<br/>Total<br/>Catalog</th>
            <th >Size of<br/>Catalog<br/>proposed<br/>for Mobile</th>
            <th >Annual<br/>Non-Mobile Income<br>for Catalog<br/>(Most Recent Year)</th>
            <th >Annual Mobile<br>Income for Catalog<br/>(Most Recent Year)</th>
            <th >Top<br/>Selling<br/>Artists</th>
        </tr>
        <logic:iterate id="producttypes" name="AllianceMusicRegistrationForm" property="allProductTypes"
                       type="com.netpace.aims.controller.alliance.AllianceMusicRegistrationProductBean">
            <tr>
                <td width="10">
                    <html:multibox property="productId">
                        <bean:write name="producttypes" property="productId"/>
                    </html:multibox>
                </td>
                <td >
                    <bean:write name="producttypes" filter="false" property="productName"/>
                    <input type="hidden" name="productTypeId"
                           value='<bean:write name="producttypes" property="productId" />'/>
                    <input type="hidden" name="productName"
                           value='<bean:write name="producttypes" property="productName" />'/>
                </td>
                <td >
                    <input type="text" class="inputField" size="20" maxlength="50" name="sizeTotalCatalog"
                           value='<bean:write name="producttypes" property="sizeTotalCatalog" />'/></td>
                <td >
                    <input type="text" class="inputField" size="20" maxlength="50" name="sizeMobileCatalog"
                           value='<bean:write name="producttypes" property="sizeMobileCatalog" />'/></td>
                <td >
                    <input type="text" class="inputField" size="25" maxlength="50" name="incomeNonMobile"
                           value='<bean:write name="producttypes" property="incomeNonMobile" />'/></td>
                <td >
                    <input type="text" class="inputField" size="25" maxlength="50" name="incomeMobile"
                           value='<bean:write name="producttypes" property="incomeMobile" />'/></td>
                <td >
                    <textarea name="topSellingArtists" rows="2" cols="40" class="textareaField"
                              onkeyup="LimitText(this,1000)" onkeypress="LimitText(this,1000)"><bean:write name="producttypes" property="topSellingArtists"/></textarea>
                </td>
            </tr>
        </logic:iterate>
    </table>
</div>

<div> &nbsp; </div>


<DIV class="headLeftCurveblk"></DIV>
<H1>Content Rights: <span class="requiredText">*</span></H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table width="100%" cellpadding="5" class="GridGradient" border="0" cellspacing="0">
        <tr>
            <th>
                <strong>Do you currently have rights fully cleared and documented for all content you propose for
                        mobile? <span class="requiredText">*</span></strong><br/>

                    <html:radio property="haveRightsCleared" value="Y"/>
                    Yes&nbsp;
                    <html:radio property="haveRightsCleared" value="N"/>
                    No
                <br/><br/>

                <strong>Do you have exclusive rights to ALL of the proposed content?
                    <span class="requiredText">*</span></strong><br/>

                    <html:radio property="haveExclusiveRights" value="Y"/>
                    Yes&nbsp;
                    <html:radio property="haveExclusiveRights" value="N"/>
                    No
                <br/><br/>

                <strong>If not, what IS exclusive?</strong><br/>
                <html:textarea property="whatIsExclusive" onkeyup="LimitText(this,3000)"
                               onkeypress="LimitText(this,3000)" rows="5" cols="80"
                               styleClass="textareaField"></html:textarea>
            </th>
        </tr>
    </table>
</div>

<div> &nbsp; </div>

<DIV class="headLeftCurveblk"></DIV>
<H1>&nbsp;</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">

<table width="100%" cellpadding="5" class="GridGradient" border="0" cellspacing="0">
    <tr>
        <th style="vertical-align:top;">
            <strong>Are you currently delivering your content<br/>to us through a content provider / aggregator?</strong> 
                <span class="requiredText">*</span><br/>
            <html:radio property="contentThruAggregator" value="Y"/>
            Yes&nbsp;
            <html:radio property="contentThruAggregator" value="N"/>
            No
            <br/>
            <br/>
            <strong>Who are your current digital distribution partners<br/>(mobile or otherwise)?
            <span class="requiredText">*</span></strong><br/>
            <html:textarea styleClass="textareaField" property="currentDistributionPartners"
                           onkeyup="LimitText(this,3000)"
                           onkeypress="LimitText(this,3000)" rows="5" cols="40"></html:textarea>

        </th>
        <th style="vertical-align:top;">
            <strong>What is your annual revenue for the <br/>content sales you are proposing? <br/></strong>
            <html:text styleClass="inputField" property="annualRevenue" size="45" maxlength="100 "/>
            <br/>
            <br/>
            <strong>Additional Information <span class="requiredText">*</span></strong><br/>
            <html:textarea styleClass="textareaField" property="additionalInformation" onkeyup="LimitText(this,3000)"
                           onkeypress="LimitText(this,3000)" rows="5" cols="40"></html:textarea>
        </th>
    </tr>
</table>
</div>

<table width="100%" cellpadding="5" class="GridGradient" border="0" cellspacing="0">
    <tr>
    <%-- 
    //Commented for Bug#7845 Fix.
    <td>
            <a href="/aims/jsp/public/TermsNCondition.jsp" class="a" target="_blank">Terms and Conditions</a><br/>
            <html:checkbox property="isAccepted"/>
            I Accept
        </td>
     --%>
        
        <td align=left>
            <div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px"
                 id="Submit" title="Submit">
                <div>
                    <div>
                        <div onclick="submitForm();return false;">Submit</div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>

</html:form>

</div>
</div>

<script>
    function submitForm() {
        if (document.AllianceMusicRegistrationForm) {
            document.AllianceMusicRegistrationForm.submit();
        }
    }
</script>