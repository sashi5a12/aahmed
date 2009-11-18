<%@ page import="com.netpace.aims.bo.security.AimsSecurityManager" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<script language="javascript">
    <!--
    function enableDisableDependentField(chkBox, dependentField) {
        if (chkBox.checked) {
            dependentField.disabled = false;
        }
        else {
            dependentField.disabled = true;
            dependentField.value = "";
        }
    }
    //-->
</script>


<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<div class="homeColumnTab lBox">

<aims:getAllianceTab attributeName="Business Info"/>

<html:form action="/allianceBusInfoEdit" enctype="multipart/form-data">
<html:hidden property="task" value="edit"/>
<html:hidden property="companyPresentationFileName"/>
<html:hidden property="companyLogoFileName"/>
<html:hidden property="progGuideFileName"/>
<html:hidden property="companyPresentationTempFileId"/>
<html:hidden property="companyLogoTempFileId"/>
<html:hidden property="progGuideTempFileId"/>
<html:hidden property="companyName"/>

<div style="padding-top:10px;padding-bottom:10px;">
    <strong>Company Name:
        <bean:write name="AllianceBusInfoForm" property="companyName"/>
    </strong>
</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Establishment Information</H1>

<DIV class="headRightCurveblk"></DIV>
<div class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient" border="0">
        <tr>
            <td class="gradient">
                <strong>
                    <bean:message key="AllianceBusInfoForm.dateEstablished"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                 <span class="requiredText">*</span>:</strong><br/>
                <html:text property="dateEstablished" size="35" maxlength="15" styleClass="inputField"/>
                <img name="daysOfMonthPos"
                     onclick="toggleDatePicker('daysOfMonth','AllianceBusInfoForm.dateEstablished')"
                     id="daysOfMonthPos" <bean:message key="images.calendar.button.lite"/> />
                <div style="position:absolute;" id="daysOfMonth"/>
            </td>
        </tr>        
        <tr>
            <td>
                <strong>
                    <bean:message key="AllianceBusInfoForm.countryOfOrigin"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                 <span class="requiredText">*</span>:</strong><br/>
                <html:text property="countryOfOrigin" size="35" maxlength="50" styleClass="inputField"/>
            </td>
        </tr>
    </table>
</div>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Business Documentation</H1>
<DIV class="headRightCurveblk"></DIV>
<div class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient" border="0">
        <tr>
            <th class="blulink">
                <strong>
                    <bean:message key="AllianceBusInfoForm.companyPresentation"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                <span class="requiredText">*</span>:</strong><br/>
                <html:file property="companyPresentation" styleClass="inputField"/>
                <br/>
                <logic:notEmpty name="AllianceBusInfoForm" property="companyPresentationFileName" scope="request">
                    <logic:equal name="AllianceBusInfoForm" property="companyPresentationTempFileId" scope="request"
                                 value="0">
                        <a class="a" target="_blank"
                           href="/aims/resourceAction.do?resource=companyPresentation&object=AimsAllianc">
                            <bean:write name="AllianceBusInfoForm" property="companyPresentationFileName"/>
                        </a>
                    </logic:equal>
                    <logic:notEqual name="AllianceBusInfoForm" property="companyPresentationTempFileId" scope="request"
                                    value="0">
                        <a class="a" target="_blank"
                           href="/aims/resourceAction.do?resource=tempFile&object=AimsAllianc&tempFileId=<bean:write name="AllianceBusInfoForm" property="companyPresentationTempFileId" />">
                            <bean:write name="AllianceBusInfoForm" property="companyPresentationFileName"/>
                        </a>
                    </logic:notEqual>
                </logic:notEmpty>
            </th>
            <th class="blulink">
                <strong>
                    <bean:message key="AllianceBusInfoForm.companyLogo"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                <span class="requiredText">*</span>:</strong><br/>
                <html:file property="companyLogo" styleClass="inputField"/>
                <br/>
                <logic:notEmpty name="AllianceBusInfoForm" property="companyLogoFileName" scope="request">
                <logic:equal name="AllianceBusInfoForm" property="companyLogoTempFileId" scope="request" value="0">
                <a class="a" target="_blank" href="/aims/resourceAction.do?resource=companyLogo&object=AimsAllianc">
                    </logic:equal>
                    <logic:notEqual name="AllianceBusInfoForm" property="companyLogoTempFileId" scope="request"
                                    value="0">
                    <a class="a" target="_blank"
                       href="/aims/resourceAction.do?resource=tempFile&object=AimsAllianc&tempFileId=<bean:write name="AllianceBusInfoForm" property="companyLogoTempFileId" />">
                        </logic:notEqual>
                        <bean:write name="AllianceBusInfoForm" property="companyLogoFileName"/>
                    </a>
                    </logic:notEmpty>
            </th>
        </tr>
    </table>
</div>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Employment Information</H1>

<DIV class="headRightCurveblk"></DIV>

<div class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient" border="0">
        <tr>
            <th colspan="2" width="100%">
                <strong>
                    <bean:message key="AllianceBusInfoForm.numOfEmployees"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                <span class="requiredText">*</span>:</strong>
                <br/>
                <html:select property="employeesRange" styleClass="selectField">
                    <html:options collection="numberOfEmployeesList" property="value"
                                  labelProperty="value"></html:options>
                </html:select>
            </th>
        </tr>
    </table>
</div>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Competitive Information</H1>

<DIV class="headRightCurveblk"></DIV>
<div class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" border="0" class="GridGradient">
<tr>
    <th width="50%">
        <strong>
            <bean:message key="AllianceBusInfoForm.comptetitiveAdvantages"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
        <span class="requiredText">*</span>:</strong>
        <br/>
        <html:textarea property="competitiveAdvantages" rows="7" cols="50"
                       onkeyup="TrackCount(this,'textCountCompetitiveAdvantages',4000)"
                       onkeypress="LimitText(this,4000)" styleClass="textareaField"></html:textarea>
        <br/>
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td style="vertical-align:top;padding:0">
                    Characters remaining
                </td>
                <td><input type="text" name="textCountCompetitiveAdvantages" size="4" value="4000" disabled="true"/>
                </td>
            </tr>
        </table>
    </th>
    <th width="50%">
        <strong>
            <bean:message key="AllianceBusInfoForm.partner"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
        <span class="requiredText">*</span>:</strong>
        <br/>
        <html:textarea property="partner" rows="7" cols="50"
                       onkeyup="TrackCount(this,'textPartner',1000)"
                       onkeypress="LimitText(this,1000)" styleClass="textareaField"></html:textarea>
        <br/>
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td style="vertical-align:top;padding:0">
                    Characters remaining
                </td>
                <td><input type="text" name="textPartner" size="4" value="1000" disabled="true"/>
                </td>
            </tr>
        </table>
    </th>
</tr>
<tr>
    <td width="50%" style="vertical-align:top">
        <strong>
            <bean:message key="AllianceBusInfoForm.financing"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
        <span class="requiredText">*</span>:</strong>
        <br/>
        <logic:iterate id="financingOption" name="financingList">
            <html:multibox property="arrAllianceFinancing">
                <bean:write name="financingOption" property="financingOptionsId"/>
            </html:multibox>
            <bean:write name="financingOption" property="financingOptions"/>
            <br/>
        </logic:iterate>
    </td>
    <td width="50%" valign="top">
        <strong>
            <bean:message key="AllianceBusInfoForm.development"
                          bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
        <span class="requiredText">*</span>:</strong>
        <br/>
        <html:multibox property="arrAllianceDevelopments" value="1"/>
        In-house<br/>
        <html:multibox property="arrAllianceDevelopments" value="2"
                       onclick="javascript:enableDisableDependentField(document.forms[0].arrAllianceDevelopments[document.forms[0].arrAllianceDevelopments.length-1], document.forms[0].outsourceDevelopmentPublisherName);"/>
        Outsource<br/>
        <html:textarea property="outsourceDevelopmentPublisherName" rows="7" cols="50"
                       onkeyup="TrackCount(this,'txtOutsourceDevelopmentPublisherName',1000)"
                       onkeypress="LimitText(this,1000)"/>
        <br/>
        <table border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td style="vertical-align:top;padding:0">
                    Characters remaining
                </td>
                <td><input type="text" name="txtOutsourceDevelopmentPublisherName" size="5" value="1000"
                           disabled="true"/>
                </td>
            </tr>
        </table>

        <script language="javascript">
            <!--
            enableDisableDependentField(document.forms[0].arrAllianceDevelopments[document.forms[0].arrAllianceDevelopments.length - 1], document.forms[0].outsourceDevelopmentPublisherName);
            //-->
        </script>
    </td>
</tr>
<tr>
    <td width="50%" style="vertical-align:top;text-align:left;">
        <table width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td colspan="3"><strong>Partnership Possibilities <span class="requiredText">*</span>:</strong></td>
            </tr>
            <tr>
                <td align="left" width=50px>
                    <html:select property="availableArrRolesOfAlliance" size="6" multiple="true"
                                 styleClass="selectField">
                        <logic:iterate id="avaRoles" name="AllianceBusInfoForm" property="availableRoles"
                                       type="com.netpace.aims.model.masters.AimsRolesOfAlliance">
                            <html:option value="<%=avaRoles.getRoleId().toString()%>">
                                <bean:write name="avaRoles" property="roleName"/>
                            </html:option>
                        </logic:iterate>
                    </html:select>
                </td>
                <td width="30px" height="100%" align="center" valign="baseline">
                    <table width=100% cellpadding="3" height="100%" cellspacing="0" border="0">
                        <tr>
                            <td align="center" style="vertical-align:bottom;">
                                <img src="images/greyRndArrow.gif" border="0"
                                     onClick="copyToList('availableArrRolesOfAlliance', 'assignedArrRolesOfAlliance')"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" style="vertical-align:top;">
                                <img src="images/greyRndArrowL.gif" border="0"
                                     onClick="copyToList('assignedArrRolesOfAlliance', 'availableArrRolesOfAlliance')"/>
                            </td>
                        </tr>
                    </table>
                </td>
                <td align="left">
                    <html:select property="assignedArrRolesOfAlliance" size="6" multiple="true"
                                 styleClass="selectField">
                        <logic:iterate id="assRoles" name="AllianceBusInfoForm" property="assignedRoles"
                                       type="com.netpace.aims.model.masters.AimsRolesOfAlliance">
                            <html:option value="<%=assRoles.getRoleId().toString()%>">
                                <bean:write name="assRoles" property="roleName"/>
                            </html:option>
                        </logic:iterate>
                    </html:select>
                </td>
            </tr>
        </table>
    </td>
    <td width="50%" style="vertical-align:top">
        <table width="100%" height="100%">
            <tr>
                <td>
                    <strong>
                    <bean:message key="AllianceBusInfoForm.developmentTechnologies"
                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                        <span class="requiredText">*</span>:</strong>
                </td>
            </tr>
            <tr>
                <td>
                    <logic:iterate id="developmentTechnology" name="AllianceBusInfoForm"
                                   property="allDevelopmentTechnologies">
                        <html:multibox property="assignedArrDevTechnologies">
                            <bean:write name="developmentTechnology" property="devTechnologyId"/>
                        </html:multibox>
                        <bean:write name="developmentTechnology" property="devTechnologyName"/>
                        <br/>
                    </logic:iterate>
                </td>
            </tr>
        </table>
    </td>
</tr>
</table>
</div>

<% if (AimsSecurityManager.checkAccess(request, "BUSINESS_INFORMATION", AimsSecurityManager.UPDATE)) { %>
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px">
    <tr>
        <td>
            <div class="redBtn" style="margin-left:10px;float:right;" id="Save" title="Submit">
                <div>
                    <div>
                        <div onClick="select_all(document.forms[0].assignedArrRolesOfAlliance);document.forms[0].task.value='edit';document.forms[0].submit();">
                            Submit
                        </div>
                    </div>
                </div>
            </div>
            <div class="blackBtn" style="margin-left:10px;float:right;" id="Cancel" title="Cancel">
                <div>
                    <div>
                        <div onclick="window.location='/aims/allianceBusInfoSetup.do?task=editForm'">Cancel</div>
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

