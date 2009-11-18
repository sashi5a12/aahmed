<%@ page import="com.netpace.aims.util.AimsConstants" %>
<%@ page import="com.netpace.aims.bo.security.AimsSecurityManager" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<script language="javascript">

function checkNationalRegion()
{
    var bRegionMidWest, bRegionNorthEast, bRegionSouth, bRegionWest, bRegionNational;

    for (var i = 0; i < document.forms[0].assignedArrRegions.length; i++)
    {
        if (document.forms[0].assignedArrRegions[i].value == <%=AimsConstants.REGION_MIDWEST_ID.toString()%>)
            bRegionMidWest = true;
        if (document.forms[0].assignedArrRegions[i].value == <%=AimsConstants.REGION_NORTHEAST_ID.toString()%>)
            bRegionNorthEast = true;
        if (document.forms[0].assignedArrRegions[i].value == <%=AimsConstants.REGION_SOUTH_ID.toString()%>)
            bRegionSouth = true;
        if (document.forms[0].assignedArrRegions[i].value == <%=AimsConstants.REGION_WEST_ID.toString()%>)
            bRegionWest = true;
        if (document.forms[0].assignedArrRegions[i].value == <%=AimsConstants.REGION_NATIONAL_ID.toString()%>)
            bRegionNational = true;
    }

    if ((bRegionNational) || ((bRegionMidWest) && (bRegionNorthEast) && (bRegionSouth) && (bRegionWest)))
        selectNationalRegionOnly();
}

function selectNationalRegionOnly()
{
    if (document.forms[0].assignedArrRegions.length > 0)
    {
        for (var i = 0; i < document.forms[0].assignedArrRegions.length; i++)
        {
            document.forms[0].assignedArrRegions[i].selected = true;
        }
        copyToList('assignedArrRegions', 'availableArrRegions');
    }

    for (var i = 0; i < document.forms[0].availableArrRegions.length; i++)
    {
        if (document.forms[0].availableArrRegions[i].value == <%=AimsConstants.REGION_NATIONAL_ID.toString()%>)
            document.forms[0].availableArrRegions[i].selected = true;
    }
    copyToList('availableArrRegions', 'assignedArrRegions');

    alert("<bean:message key="EntApplicationForm.check.national.region.text" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>");
}
function enableDisableDependentField(chkBox, dependentField) {
    if (chkBox.checked) {
        dependentField.disabled = false;
    }
    else {
        dependentField.disabled = true;
        dependentField.value = "";
    }
}

var websiteURLHint = "<bean:message key="AllianceInformation.webSiteUrl.Hint" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>";

</script>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab ">

<aims:getAllianceTab attributeName="Company Info"/>

<div>&nbsp;</div>

<html:form action="/allianceCompInfoEdit">

<html:hidden property="task" value="edit"/>
<html:hidden property="vendorId"/>
<html:hidden property="status"/>

<logic:notEqual name="AllianceCompInfoForm" property="status"
                value="<%=AimsConstants.ALLIANCE_STATUS_SUBMITTED.toString()%>">
    <html:hidden property="companyName"/>
    <html:hidden property="companyLegalName"/>
</logic:notEqual>

<div class="lBox">
<DIV class="headLeftCurveblk"></DIV>
<H1>Basic Company Information</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <th width=50%>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.companyName"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    <span class="requiredText">*</span>:</strong><br/>
                    <logic:equal name="AllianceCompInfoForm" property="status"
                                 value="<%=AimsConstants.ALLIANCE_STATUS_SUBMITTED.toString()%>">
                        <html:text property="companyName" size="35" maxlength="200" styleClass="inputField"/>
                    </logic:equal>
                    <logic:notEqual name="AllianceCompInfoForm" property="status"
                                    value="<%=AimsConstants.ALLIANCE_STATUS_SUBMITTED.toString()%>">
                        <bean:write name="AllianceCompInfoForm" property="companyName"/>
                    </logic:notEqual>
                </th>
                <th>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.companyLegalName"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    <span class="requiredText">*</span>:</strong><br/>
                    <logic:equal name="AllianceCompInfoForm" property="status"
                                 value="<%=AimsConstants.ALLIANCE_STATUS_SUBMITTED.toString()%>">
                        <html:text property="companyLegalName" size="35" maxlength="200" styleClass="inputField"/>
                    </logic:equal>
                    <logic:notEqual name="AllianceCompInfoForm" property="status"
                                    value="<%=AimsConstants.ALLIANCE_STATUS_SUBMITTED.toString()%>">
                        <bean:write name="AllianceCompInfoForm" property="companyLegalName"/>
                    </logic:notEqual>
                </th>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <td width=50%>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.websiteURL"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    <span class="requiredText">*</span>:</strong>&nbsp;
                    <a onClick="openZonHelpWindow(event, websiteURLHint); return false;" href="#">[?]</a><br/>
                    <html:text property="webSiteUrl" size="35" maxlength="200" styleClass="inputField"/>
                </td>
                <td>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.prevRevenues"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
                    </strong>
                    <br/>
                    <html:text property="prevYearRevenues" size="35" maxlength="200" styleClass="inputField"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <td><strong>Is Company Public? <span class="requiredText">*</span>:</strong><br/>
                    <html:radio property="isFinanceInfoPublic" value="Y"/>
                    Yes
                    &nbsp;
                    <html:radio property="isFinanceInfoPublic" value="N"/>
                    No
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <td width=50%>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.stateOfInc"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    <span class="requiredText">*</span>:</strong><br/>
                    <html:select property="stateOfInc" size="1" styleClass="selectField">
                        <html:option value="AL">AL</html:option>
                        <html:option value="AK">AK</html:option>
                        <html:option value="AS">AS</html:option>
                        <html:option value="AZ">AZ</html:option>
                        <html:option value="AR">AR</html:option>
                        <html:option value="CA">CA</html:option>
                        <html:option value="CO">CO</html:option>
                        <html:option value="CT">CT</html:option>
                        <html:option value="DE">DE</html:option>
                        <html:option value="DC">DC</html:option>
                        <html:option value="FM">FM</html:option>
                        <html:option value="FL">FL</html:option>
                        <html:option value="GA">GA</html:option>
                        <html:option value="GU">GU</html:option>
                        <html:option value="HI">HI</html:option>
                        <html:option value="ID">ID</html:option>
                        <html:option value="IL">IL</html:option>
                        <html:option value="IN">IN</html:option>
                        <html:option value="IA">IA</html:option>
                        <html:option value="KS">KS</html:option>
                        <html:option value="KY">KY</html:option>
                        <html:option value="LA">LA</html:option>
                        <html:option value="ME">ME</html:option>
                        <html:option value="MH">MH</html:option>
                        <html:option value="MD">MD</html:option>
                        <html:option value="MA">MA</html:option>
                        <html:option value="MI">MI</html:option>
                        <html:option value="MN">MN</html:option>
                        <html:option value="MS">MS</html:option>
                        <html:option value="MO">MO</html:option>
                        <html:option value="MT">MT</html:option>
                        <html:option value="NE">NE</html:option>
                        <html:option value="NV">NV</html:option>
                        <html:option value="NH">NH</html:option>
                        <html:option value="NJ">NJ</html:option>
                        <html:option value="NM">NM</html:option>
                        <html:option value="NY">NY</html:option>
                        <html:option value="NC">NC</html:option>
                        <html:option value="ND">ND</html:option>
                        <html:option value="MP">MP</html:option>
                        <html:option value="OH">OH</html:option>
                        <html:option value="OK">OK</html:option>
                        <html:option value="OR">OR</html:option>
                        <html:option value="PW">PW</html:option>
                        <html:option value="PA">PA</html:option>
                        <html:option value="PR">PRO</html:option>
                        <html:option value="RI">RI</html:option>
                        <html:option value="SC">SC</html:option>
                        <html:option value="SD">SD</html:option>
                        <html:option value="TN">TN</html:option>
                        <html:option value="TX">TX</html:option>
                        <html:option value="UT">UT</html:option>
                        <html:option value="VT">VT</html:option>
                        <html:option value="VI">VI</html:option>
                        <html:option value="VA">VA</html:option>
                        <html:option value="WA">WA</html:option>
                        <html:option value="WV">WV</html:option>
                        <html:option value="WI">WI</html:option>
                        <html:option value="WY">WY</html:option>
                    </html:select>
                </td>

                <td>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.authorizedRepresentative"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    <span class="requiredText">*</span>:</strong><br/>
                    <html:text property="authRepName" size="35" maxlength="200" styleClass="inputField"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <td width=50%>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.address"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    <span class="requiredText">*</span>:</strong><br/>
                    <html:text property="streetAddress1" size="35" maxlength="200" styleClass="inputField"/>
                </td>
                <td>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.city"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :<br/>
                    </strong>
                    <html:text property="city" size="35" maxlength="50" styleClass="inputField"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <td width=50%>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.state"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                    <span class="requiredText">*</span>:</strong><br/>
                    <html:text property="state" size="20" maxlength="100" styleClass="inputField"/>
                </td>
                <td>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.zipcode"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
                    </strong>
                    <br/>
                    <html:text property="zipCode" size="11" maxlength="10" styleClass="inputField"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <td width=50%>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.country"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
                    </strong>
                    <br/>
                    <html:select property="country" styleClass="selectField">
                        <html:options collection="countryList" property="countryName"
                                      labelProperty="countryName"></html:options>
                    </html:select>
                </td>
                <td>
                    <strong>
                        <bean:message key="AllianceCompInfoForm.vendorId"
                                      bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> :
                    </strong>
                    <br/>
                    <bean:write name="AllianceCompInfoForm" property="vendorId"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
</table>
</div>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Alliance Information</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
    <tr>
        <th><strong>Alliance with other carriers <span class="requiredText">*</span>:</strong></th>
    </tr>
    <tr>
        <td style="vertical-align:top;">
            <logic:iterate id="allianceWithOtherCarrier" name="aimsCarriers">
                <html:multibox property="arrAlliancesWithOtherCarriers"
                               onclick="javascript:enableDisableDependentField(document.forms[0].arrAlliancesWithOtherCarriers[document.forms[0].arrAlliancesWithOtherCarriers.length-1], document.forms[0].allianceWithOtherCarriers);">
                    <bean:write name="allianceWithOtherCarrier" property="carrierId"/>
                </html:multibox>
                <bean:write name="allianceWithOtherCarrier" property="carrierName"/>
                <br/>
            </logic:iterate>
            <html:textarea property="allianceWithOtherCarriers" rows="7" cols="50" disabled="true"
                           onkeyup="TrackCount(this,'txtAllianceWithOtherCarriers',500)"
                           onkeypress="LimitText(this,500)" styleClass="textareaField"/><br/>
            <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td style="vertical-align:top;padding:0">
                    Characters remaining
                </td>
                <td><input type="text" name="txtAllianceWithOtherCarriers" size="4" value="500" disabled="true"/>
                </td>
            </tr>
            </table>
            <script language="javascript">
                <!--
                enableDisableDependentField(document.forms[0].arrAlliancesWithOtherCarriers[document.forms[0].arrAlliancesWithOtherCarriers.length - 1], document.forms[0].allianceWithOtherCarriers);
                //-->
            </script>
        </td>
    </tr>
</table>
</div>


<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Remittance Information</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
    <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
    	<tr>
            <th class="noPad" width="100%" colspan="2">
                Please note: If you are submitting a V CAST App, please fill out the fields within the Remittance Information section to ensure proper payment.
            </th>
        </tr>
        
		<tr>
            <td width="50%">
                <strong><bean:message key="AllianceRegistrationForm.remitTo" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                </strong><br/>
                <html:text property="remitTo" maxlength="50" size="40" styleClass="inputField"/>
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>
                <strong><bean:message key="AllianceRegistrationForm.Address1" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                </strong><br/>
                <html:text property="remitAddress1" maxlength="200" size="40" styleClass="inputField"/>
            </td>
			<td>
                   <strong><bean:message key="AllianceRegistrationForm.address2" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>
                </strong><br/>
                <html:text property="remitAddress2" maxlength="200" size="40" styleClass="inputField"/>
            </td>

        </tr>
        <tr>
            <td>
                <strong><bean:message key="AllianceRegistrationForm.City" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
                <html:text property="remitCity" maxlength="50" size="40" styleClass="inputField"/>
            </td>
            <td>
                <strong><bean:message key="AllianceRegistrationForm.State" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
                <html:text property="remitState" maxlength="50" size="40" styleClass="inputField"/>
            </td>
        </tr>
        <tr>
            <td>
                <strong><bean:message key="AllianceRegistrationForm.PostalCode" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
                <html:text property="remitPostalCode" maxlength="9" size="40" styleClass="inputField"/>
            </td>
            <td>
                 <strong><bean:message key="AllianceRegistrationForm.Country" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/></strong><br/>
                <html:select property="remitCountry" styleClass="selectField">
                    <html:options collection="countryList" property="countryName" labelProperty="countryName"></html:options>
                </html:select>                        
            </td>
        </tr>
	</table>
</div>

</div>

<div>&nbsp;</div>
<table width=100% border="0" cellpadding="0" cellspacing="0">
<tr>
<td width=50%>
    <table width=100% cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td colspan="3">
                <div class="mmBox">
                    <DIV class="headLeftCurveblk"></DIV>
                    <H1>Industry Focus<span class="requiredText">*</span></H1>
                    <DIV class="headRightCurveblk"></DIV>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <table width="395" cellpadding="5" cellspacing="0" border="0" class="GridGradient">
                    <tr>
                        <th align="left" width="20">
                            <html:select property="availableArrIndustryFocus" size="4" multiple="true"
                                         styleClass="selectField">
                                <logic:iterate id="avaIndFocus" name="AllianceCompInfoForm"
                                               property="availableIndustryFocus"
                                               type="com.netpace.aims.model.masters.AimsIndustryFocu">
                                    <html:option value="<%=avaIndFocus.getIndustryId().toString()%>">
                                        <bean:write name="avaIndFocus" property="industryName"/>
                                    </html:option>
                                </logic:iterate>
                            </html:select>
                        </th>
                        <th align="center" valign="baseline" width=30px>
                            <table width=100% cellpadding="3" cellspacing="0" border="0">
                                <tr>
                                    <td align="center" valign="bottom">
                                        <img src="images/greyRndArrow.gif" border="0"
                                             onClick="copyToList('availableArrIndustryFocus', 'assignedArrIndustryFocus')"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" valign="top">
                                        <img src="images/greyRndArrowL.gif" border="0"
                                             onClick="copyToList('assignedArrIndustryFocus', 'availableArrIndustryFocus')"/>
                                    </td>
                                </tr>
                            </table>
                        </th>
                        <th align="left">
                            <html:select property="assignedArrIndustryFocus" size="4" multiple="true"
                                         styleClass="selectField">
                                <logic:iterate id="assIndFocus" name="AllianceCompInfoForm"
                                               property="assignedIndustryFocus"
                                               type="com.netpace.aims.model.masters.AimsIndustryFocu">
                                    <html:option value="<%=assIndFocus.getIndustryId().toString()%>">
                                        <bean:write name="assIndFocus" property="industryName"/>
                                    </html:option>
                                </logic:iterate>
                            </html:select>
                        </th>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</td>
<td>
    <table width=100% cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td colspan="3">
                <div class="mmBox">
                    <DIV class="headLeftCurveblk"></DIV>
                    <H1>Regions Present in<span class="requiredText">*</span></H1>
                    <DIV class="headRightCurveblk"></DIV>
                </div>
            </td>
        </tr>
        <tr>
            <td>
            <table width="395" cellpadding="5" cellspacing="0" border="0" class="GridGradient">
                <tr>
                    <th align="left" width="10">
                        <html:select property="availableArrRegions" size="4" multiple="true" styleClass="selectField">
                            <logic:iterate id="avaRegion" name="AllianceCompInfoForm"
                                           property="availableRegions"
                                           type="com.netpace.aims.model.masters.AimsRegion">
                                <html:option value="<%=avaRegion.getRegionId().toString()%>">
                                    <bean:write name="avaRegion" property="regionName"/>
                                </html:option>
                            </logic:iterate>
                        </html:select>
                    </th>
                    <th align="center" valign="middle" width=30px>
                        <table width=100% cellpadding="3" cellspacing="0" border="0">
                            <tr>
                                <td align="center" valign="bottom">
                                    <img src="images/greyRndArrow.gif" border="0"
                                         onClick="copyToList('availableArrRegions', 'assignedArrRegions');checkNationalRegion();"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" valign="top">
                                    <img src="images/greyRndArrowL.gif" border="0"
                                         onClick="copyToList('assignedArrRegions', 'availableArrRegions')"/>
                                </td>
                            </tr>
                        </table>
                    </th>
                    <th>
                        <html:select property="assignedArrRegions" size="4" multiple="true" styleClass="selectField">
                            <logic:iterate id="assRegion" name="AllianceCompInfoForm"
                                           property="assignedRegions"
                                           type="com.netpace.aims.model.masters.AimsRegion">
                                <html:option value="<%=assRegion.getRegionId().toString()%>">
                                    <bean:write name="assRegion" property="regionName"/>
                                </html:option>
                            </logic:iterate>
                        </html:select>
                    </th>
                </tr>
            </table>
    </table>
</td>
</tr>
</table>

<div style="padding:10px;">
    <div style="vertical-align:middle;"><strong>Others :</strong></div>
    <html:text property="otherIndustryFocus" size="25" maxlength="500" styleClass="inputField"/>
</div>

<% if (AimsSecurityManager.checkAccess(request, "COMPANY_INFORMATION", AimsSecurityManager.UPDATE)) { %>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
        <td width="100%" align="right" valign="middle">
            <div class="redBtn" style=" margin-left:10px;float:right;" id="Save" title="Submit">
                <div>
                    <div>
                        <div onClick="select_all(document.forms[0].assignedArrIndustryFocus); select_all(document.forms[0].assignedArrRegions);document.forms[0].submit();"> Submit </div>
                    </div>
                </div>
            </div>

            <div class="blackBtn" style=" margin-left:10px;float:right;" id="Cancel" title="Cancel">
                <div>
                    <div>
                        <div onClick="window.location='/aims/allianceCompInfoSetup.do?task=editForm'">Cancel</div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>
<% } %>
</html:form>

</div>
</div>






