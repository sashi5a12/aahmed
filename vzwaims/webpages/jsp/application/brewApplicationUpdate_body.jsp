<%@ page language="java"
         import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<jsp:useBean id="task" class="java.lang.String" scope="request"/>
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm"
             scope="request"/>
<% boolean isConcept = false; %>
<% ApplicationUpdateForm.setCurrentPage("page1"); %>
<% int applicationURLIndex = 0;%>

<logic:equal name="BrewApplicationUpdateForm" property="isConcept" scope="request" value="Y">
    <% isConcept = true; %>
</logic:equal>
<script language="javascript">

var subCategoriesArray = new Array();
var devicesArray = new Array();
var geoServicesArray = new Array();

var helpLBSApplicationType = "<bean:message key="BrewApplicationForm.help.lbsApplicationType" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>";
var helpLBSGeoServices = "<bean:message key="BrewApplicationForm.help.lbsGeoServices" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>";

function AimsAppSubCategory()
{
    this.subCategoryId = "";
    this.subCategoryName = "";
    this.aimsAppCategoryId = "";
}

function AimsDevices()
{
    this.deviceId = "";
    this.deviceModel = "";
    this.deviceStatus = "";
    this.deviceAlert = "";
    this.deviceAlertMessage = "";
    this.deviceLbsSupported = "";
}

function AimsLbsGeoService()
{
    this.geoServiceId = "";
    this.geoServiceName = "";
    this.status = "";
    this.initiatedFrom = "";
}

<%
    int	index	=	0;
    %>

<logic:iterate id="formSubCategories"	name="BrewApplicationUpdateForm" property="allSubCategories" scope="request">
aimsAppSubCategory = new AimsAppSubCategory();
aimsAppSubCategory.subCategoryId = "<bean:write	name="formSubCategories" property="subCategoryId"	/>";
aimsAppSubCategory.subCategoryName = "<bean:write	name="formSubCategories" property="subCategoryName"	filter="false"/>";
aimsAppSubCategory.aimsAppCategoryId = "<bean:write	name="formSubCategories" property="aimsAppCategoryId"	/>";
subCategoriesArray[<%=index%>] = aimsAppSubCategory;
<%index++;%>
</logic:iterate>

<%index	=	0;%>
<logic:iterate id="formDevices"	name="BrewApplicationUpdateForm" property="availableDevices" scope="request">
aimsDevices = new AimsDevices();
aimsDevices.deviceId = "<bean:write	name="formDevices" property="deviceId" />";
aimsDevices.deviceModel = "<bean:write	name="formDevices" property="deviceModel" />";
aimsDevices.deviceStatus = "<bean:write   name="formDevices" property="status" />";
aimsDevices.deviceAlert = "<bean:write   name="formDevices" property="deviceAlert" />";
aimsDevices.deviceAlertMessage = "<bean:write   name="formDevices" property="deviceAlertMessage" />";
aimsDevices.deviceLbsSupported = "<bean:write   name="formDevices" property="lbsSupported" />";
devicesArray[<%=index%>] = aimsDevices;
<%index++;%>
</logic:iterate>

<%index =   0;%>
<logic:iterate id="formGeoServices"   name="BrewApplicationUpdateForm" property="allGeoServices" scope="request">
aimsLbsGeoService = new AimsLbsGeoService();
aimsLbsGeoService.geoServiceId = "<bean:write name="formGeoServices" property="geoServiceId"   />";
aimsLbsGeoService.geoServiceName = "<bean:write   name="formGeoServices" property="geoServiceName" />";
aimsLbsGeoService.status = "<bean:write name="formGeoServices" property="status"   />";
aimsLbsGeoService.initiatedFrom = "<bean:write   name="formGeoServices" property="initiatedFrom" />";
geoServicesArray[<%=index%>] = aimsLbsGeoService;
<%index++;%>
</logic:iterate>

var supported = (window.Option) ? 1 : 0;


function changeSubCategories() {
    if (!supported) {
        alert("Feature	not	supported");
    }
    var options = document.forms[0].aimsAppSubCategoryId.options;
    for (var i = options.length - 1; i > 0; i--) {
        options[i] = null;
    }

    options[0] = new Option("<bean:message	key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>", "0");
    var k = 1;
    var m = 0;

    for (var j = 0; j < subCategoriesArray.length; j++) {

        if (subCategoriesArray[j].aimsAppCategoryId == document.forms[0].aimsAppCategoryId.options[document.forms[0].aimsAppCategoryId.selectedIndex].value)
        {
            options[k] = new Option(subCategoriesArray[j].subCategoryName, subCategoriesArray[j].subCategoryId);
            if (subCategoriesArray[j].subCategoryId == "<bean:write	name="BrewApplicationUpdateForm" property="aimsAppSubCategoryId" scope="request"/>")
                m = k;
            k++;
        }
    }

    options[m].selected = true;
}


function setDevices()
{
    if (!supported)
    {
        alert("Feature	not	supported");
    }
    var optionsAvailable = document.forms[0].listAvailableDevices.options;
    var optionsSelected = document.forms[0].listSelectedDevices.options;
    var availableIndex = 0;
    var selectedIndex = 0;
    var selectedNotFound;

<logic:empty name="BrewApplicationUpdateForm"	property="listSelectedDevices" scope="request">
    for (var j = 0; j < devicesArray.length; j++)
    {
        if (devicesArray[j].deviceStatus.toUpperCase() == "ACTIVE")
        {
            optionsAvailable[availableIndex] = new Option(devicesArray[j].deviceModel, devicesArray[j].deviceId);
            availableIndex++;
        }
    }
</logic:empty>

<logic:notEmpty	name="BrewApplicationUpdateForm" property="listSelectedDevices"	scope="request">
    for (var j = 0; j < devicesArray.length; j++)
    {
        selectedNotFound = true;
    <logic:iterate id="devices"	name="BrewApplicationUpdateForm" property="listSelectedDevices"	scope="request">
        if (devicesArray[j].deviceId == "<bean:write name="devices"/>")
        {
            optionsSelected[selectedIndex] = new Option(devicesArray[j].deviceModel, devicesArray[j].deviceId);
            selectedIndex++;
            selectedNotFound = false;
        }
    </logic:iterate>
        if ((selectedNotFound) && (devicesArray[j].deviceStatus.toUpperCase() == "ACTIVE"))
        {
            optionsAvailable[availableIndex] = new Option(devicesArray[j].deviceModel, devicesArray[j].deviceId);
            availableIndex++;
        }
    }
</logic:notEmpty>
    updateDeviceAlertMessage();
}

function truncateLocalTextAreas()
{
    TruncateTextWithCount(document.forms[0].shortDesc, 'textCountShortDesc', 200);
    TruncateTextWithCount(document.forms[0].longDesc, 'textCountLongDesc', 500);    
}

function trackCountForTextAreas()
{
    TrackCount(document.forms[0].shortDesc, 'textCountShortDesc', 200);
    TrackCount(document.forms[0].longDesc, 'textCountLongDesc', 500);
}

function updateDeviceAlertMessage()
{
    document.forms[0].selectedDevicesAlertMessage.value = "";

    for (var j = 0; j < devicesArray.length; j++)
    {
        if (devicesArray[j].deviceAlert == "Y")
        {
            for (var k = 0; k < document.forms[0].listSelectedDevices.length; k++)
            {
                if (document.forms[0].listSelectedDevices.options[k].value == devicesArray[j].deviceId)
                    document.forms[0].selectedDevicesAlertMessage.value = document.forms[0].selectedDevicesAlertMessage.value +
                                                                          "You have selected " + devicesArray[j].deviceModel + " - " +
                                                                          devicesArray[j].deviceAlertMessage + "\n";
            }
        }
    }

    if (document.forms[0].selectedDevicesAlertMessage.value.length > 0)
        document.forms[0].selectedDevicesAlertMessage.value = document.forms[0].selectedDevicesAlertMessage.value + "\nAre you sure you want to submit the application?";
}

<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
function disableForVzw()
{
    if (typeof document.forms[0].imgAddDevice != "undefined")
        document.forms[0].imgAddDevice.disabled = true;
    if (typeof document.forms[0].imgRemoveDevice != "undefined")
        document.forms[0].imgRemoveDevice.disabled = true;

    document.forms[0].appSize.disabled = true;
    document.forms[0].aimsAppCategoryId.disabled = true;
    document.forms[0].aimsAppSubCategoryId.disabled = true;

    for (var i = 0; i < document.forms[0].elements.length; i++) {
        if (document.forms[0].elements[i].name == "language") {
            document.forms[0].elements[i].disabled = true;
        }
    }

    for (var i = 0; i < document.forms[0].elements.length; i++) {
        if (document.forms[0].elements[i].name == "ifPrRelease") {
            document.forms[0].elements[i].disabled = true;
        }
    }

}
<% } else {}%>

function isOKWithLBS()
{
    if (
            (document.forms[0].isLbs.checked) &&
            (!window.confirm('You have selected LBS. All non-supported devices will be lost. Is that OK?'))
            )
    {
        return false;
    }
    else
        return true;
}

function updateLBSDevices()
{
<%if (!(AimsSecurityManager.checkAccess(request, ManageApplicationsConstants.PRIV_BREW_LEGAL_USER, AimsSecurityManager.UPDATE))) {%>
    var optionsAvailable = document.forms[0].listAvailableDevices.options;
    var optionsSelected = document.forms[0].listSelectedDevices.options;
    var availableIndex = optionsAvailable.length;
    var selectedNotFound = true;
    if (document.forms[0].isLbs.checked)
    {
        for (var i = optionsAvailable.length - 1; i >= 0; i--)
        {
            for (var j = 0; j < devicesArray.length; j++)
            {
                if (devicesArray[j].deviceId == optionsAvailable[i].value)
                {
                    if (devicesArray[j].deviceLbsSupported.toUpperCase() != "Y")
                    {
                        optionsAvailable[i] = null;
                        break;
                    }
                }
            }
        }

        for (var i = optionsSelected.length - 1; i >= 0; i--)
        {
            for (var j = 0; j < devicesArray.length; j++)
            {
                if (devicesArray[j].deviceId == optionsSelected[i].value)
                {
                    if (devicesArray[j].deviceLbsSupported.toUpperCase() != "Y")
                    {
                        optionsSelected[i] = null;
                        break;
                    }
                }
            }
        }
    }
    else
    {
        for (var j = 0; j < devicesArray.length; j++)
        {
            selectedNotFound = true;

            for (var k = 0; k < optionsAvailable.length; k++)
            {
                if (devicesArray[j].deviceId == optionsAvailable[k].value)
                    selectedNotFound = false;
            }
            for (var k = 0; k < optionsSelected.length; k++)
            {
                if (devicesArray[j].deviceId == optionsSelected[k].value)
                    selectedNotFound = false;
            }

            if ((selectedNotFound) && (devicesArray[j].deviceStatus.toUpperCase() == "ACTIVE"))
            {
                optionsAvailable[availableIndex] = new Option(devicesArray[j].deviceModel, devicesArray[j].deviceId);
                availableIndex++;
            }
        }
    }
<% } else {}%>
}


function setGeoServices()
{
    var bFound = false;

    if (!supported)
    {
        alert("Feature not supported");
    }

    var optionsAvailable = document.forms[0].listAvailableGeoServices.options;
    var optionsSelected = document.forms[0].listSelectedGeoServices.options;
    var availableIndex = 0;
    var selectedIndex = 0;
    var selectedNotFound;

<logic:empty name="BrewApplicationUpdateForm"   property="listSelectedGeoServices" scope="request">
    for (var j = 0; j < geoServicesArray.length; j++)
    {
        if (geoServicesArray[j].status.toLowerCase() == "a")
        {
            optionsAvailable[availableIndex] = new Option(geoServicesArray[j].initiatedFrom + " - " + geoServicesArray[j].geoServiceName, geoServicesArray[j].geoServiceId);
            availableIndex++;
        }
    }
</logic:empty>

<logic:notEmpty name="BrewApplicationUpdateForm" property="listSelectedGeoServices" scope="request">
    for (var j = 0; j < geoServicesArray.length; j++)
    {
        selectedNotFound = true;
    <logic:iterate id="geoServices" name="BrewApplicationUpdateForm" property="listSelectedGeoServices" scope="request">
        if (geoServicesArray[j].geoServiceId == "<bean:write name="geoServices"/>")
        {
            optionsSelected[selectedIndex] = new Option(geoServicesArray[j].initiatedFrom + " - " + geoServicesArray[j].geoServiceName, geoServicesArray[j].geoServiceId);
            selectedIndex++;
            selectedNotFound = false;
        }
    </logic:iterate>
        if ((selectedNotFound) && (geoServicesArray[j].status.toLowerCase() == "a"))
        {
            optionsAvailable[availableIndex] = new Option(geoServicesArray[j].initiatedFrom + " - " + geoServicesArray[j].geoServiceName, geoServicesArray[j].geoServiceId);
            availableIndex++;
        }
    }
</logic:notEmpty>

}

function disable1()
{
    for (var i = 0; i < document.forms[0].elements.length; i++) {
        if (document.forms[0].elements[i].name == "isLbs") {
            document.forms[0].elements[i].disabled = true;
        }
    }
}

function submitFrm() {
    var frm = document.forms[0];
    if (!frm.networkUsage.checked) {
        removeAllApplicationURLs();
    }
    else {
        removeEmptyApplicationURLs(0);
    }
    return true;
}

function doEnableDisable(checkboxField) {
    var msg = 'You are about to disable Network Usage, All your URLs will be Deleted! Are you sure you want to proceed?';
    if (checkboxField.checked) {
        enableDisableNetworkUsage(checkboxField);
    }
    else {
        if (confirm(msg)) {
            enableDisableNetworkUsage(checkboxField);
        }
        else {
            checkboxField.checked = true;
        }
    }
}
function setAirTimeLength(){
	var airTime=document.getElementById('airTime');
	var form = document.forms[0];
	if (airTime.innerHTML.length > 0){
		textCount=0;
		form.airTimeLen.value=countNodeText(airTime);
	}
	
	/*form.textCountShortDesc.value = form.textCountShortDesc.value - form.airTimeLen.value; 
    form.textCountLongDesc.value = form.textCountLongDesc.value -  form.airTimeLen.value;*/
	
}

<%@ include  file="include/appJScript.jsp" %>

</script>
<%@ include file="include/applicationUrlJScript.jsp" %>

<%@ include file="include/brewMessageHeader.jsp" %>

<div id="contentBox" style="float:left" onMouseMove="truncateLocalTextAreas();">
<DIV class="homeColumnTab lBox">
<%@ include file="include/appTabs.jsp" %>
<html:form action="/brewApplicationUpdate" enctype="multipart/form-data" onsubmit="javascript:return submitFrm();">
<div class="contentbox">
<%@ include file="include/appHidden.jsp" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="ApplicationForm.table.head.app.details"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <th width=50%>
                    <strong><bean:message key="ManageApplicationForm.appTitle"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong><br/>
                    <html:text property="title" size="40" maxlength="200" styleClass="inputField"/>
                </th>
                <th>
                    <strong><bean:message key="ManageApplicationForm.appVersion"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong><br/>
                    <html:text property="version" size="30" maxlength="30" styleClass="inputField"/>
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
                    <strong><bean:message key="ManageApplicationForm.appLanguage"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong><br/>
                    <html:radio property="language" value="EN"/>
                    <bean:message key="ManageApplicationForm.language.english"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                    <html:radio property="language" value="SP"/>
                    <bean:message key="ManageApplicationForm.language.spanish"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                </td>
                <td>
                    <strong><bean:message key="BrewApplicationForm.appSize"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong><br/>
                    <html:text property="appSize" size="30" maxlength="30" styleClass="inputField"/>
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
                    <strong><bean:message key="ManageApplicationForm.appShortDesc"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong><br/>
                    <html:textarea property="shortDesc" rows="4" cols="50" styleClass="textareaField"
                                   onkeyup="TrackCount(this,'textCountShortDesc',200)"
                                   onkeypress="LimitText(this,200)"></html:textarea>
                    <br/>
                    <span id="airTime" style="display: none"><c:out value="${requestScope.AirTimeText}" escapeXml="false"></c:out></span>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td style="vertical-align:top;padding:0">
                                <bean:message key="ManageApplicationForm.textarea.char.remaining"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </td>
                            <td>
                                <input type="text" name="textCountShortDesc" size="3" value="200" disabled="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    <strong><bean:message key="ManageApplicationForm.appLongDesc"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong><br/>
                    <html:textarea property="longDesc" rows="4" cols="40" styleClass="textareaField"
                                   onkeyup="TrackCount(this,'textCountLongDesc',500)"
                                   onkeypress="LimitText(this,500)"></html:textarea>
                    <br/>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td style="vertical-align:top;padding:0">
                                <bean:message key="ManageApplicationForm.textarea.char.remaining"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </td>
                            <td>
                                <input type="text" name="textCountLongDesc" size="3" value="500" disabled="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <td width="50%">
                    <strong><bean:message key="ManageApplicationForm.appType"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong><br/>
                    <bean:write name="BrewApplicationUpdateForm" property="appTypeFullName"/>
                </td>
                <td>
                    <strong><bean:message key="ManageApplicationForm.contentRating"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <bean:message  key="BrewApplicationForm.info.label.contentRating"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br/>
                    <html:select styleClass="selectField" property="contentRating" size="1">
						<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
						<html:optionsCollection property="allBrewContentRatings" label="typeValue" value="typeId"/>
					</html:select>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="ApplicationForm.table.head.app.contentFilter"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
    <%-- start content filter --%>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
            <tr>
                <th width="60%">
                    <html:checkbox property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>"
                                   onclick="javascript:doEnableDisable(this);//">
                        &nbsp;<strong><bean:message key="ManageApplicationForm.appNetworkUsage"
                                            bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>
                    </html:checkbox>
                </th>
                <th>&nbsp;</th>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
            <tr>
                <td width="100%">
                    <div id="divApplicationURLLbl">
                        <strong><bean:message key="ManageApplicationForm.applicationURLs"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    </div>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="100%">
        <div id="divApplicationURLs">
            <table id="tblApplicationURLs" width="100%" border="0" cellspacing="5" cellpadding="0" class="GridGradient">
                <tbody>
                    <logic:equal name="ApplicationUpdateForm" property="networkUsage"
                                 value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">
                        <logic:notEmpty name="ApplicationUpdateForm" property="applicationURLs">
                            <logic:iterate id="applicationURL" name="ApplicationUpdateForm" property="applicationURLs">
                                <tr id="<%=("row"+applicationURLIndex)%>" valign="top">
                                    <td vAlign="top">
                                        <input type="text" class="inputField" name="applicationURLs" size="60" 
                                               maxlength="200" value="<bean:write name='applicationURL'/>"
                                               style="margin-bottom:0px; width:375px;">&nbsp;
                                        <a href="javascript:removeApplicationURLRow('<%=("row"+(applicationURLIndex))%>', false);">
                                            <bean:message key='images.delete.icon'/>
                                        </a>
                                        <%applicationURLIndex++;%>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </logic:notEmpty>
                    </logic:equal>
                </tbody>
            </table>
        </div>
    </td>
</tr>
<tr>
    <td width="100%">
        <div id="divAddRow">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                <tr>
                    <td style="width:315px;" align="left">&nbsp;<span id="divExpandCollapse"></span></td>
                        <%-- show export urls button, only if privilege is set, and current screen is not create or clone --%>
                    <td align="left">
                        <div class="redBtn" style="margin-left:10px; float:left; margin-top:3px" id="addUrl"
                             title="Add URL">
                            <div>
                                <div>
                                    <div onclick="javascript:addApplicationURLRow('tblApplicationURLs', 'applicationURLs', '', true, true);//">
                                        Add URL
                                    </div>
                                </div>
                            </div>
                        </div>
                    </td>
                    <%if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.EXPORT_CONTENT_FILTER_URLS, AimsSecurityManager.UPDATE)) {%>
                    <logic:notEqual name="BrewApplicationUpdateForm" property="task" value="create">
                        <logic:notEqual name="BrewApplicationUpdateForm" property="task" value="clone">
                            <td align="left" style="padding-left: 0px">
                                <div class="redBtn" style="float:left; margin-top:3px" id="exportUrl"
                                     title="Export URLs">
                                    <div>
                                        <div>
                                            <a href="/aims/applicationUrlsHelper.do?task=exportApplicationUrl&appsId=<bean:write	name="ApplicationUpdateForm"	property="appsId"	/>"
                                               class="a" target="_blank" title="Export URLs">Export URLs</a></div>
                                    </div>
                                </div>
                            </td>
                        </logic:notEqual>
                    </logic:notEqual>
                    <%}%>
                </tr>
            </table>
        </div>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
    <%-- end content filter --%>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>LBS Application</H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <th width=50%>
                    <strong><html:checkbox property="isLbs" value="<%=AimsConstants.BREW_APP_CHECKBOX_IS_LBS[0]%>"
                                           onclick="javascript:if (isOKWithLBS()) { updateLBSDevices(); } else { return false; }">
                        <%=AimsConstants.BREW_APP_CHECKBOX_IS_LBS[1]%>
                    </html:checkbox></strong>
                </th>
                <th>&nbsp;</th>
            </tr>
            <tr>
                <td width="50%">
                    <strong>Application Type&nbsp;<span class="requiredText">*</span>: </strong><a
                        onClick="openZonHelpWindow(event, helpLBSApplicationType); return false;" href="#">[?]</a>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td width="50%">
                    <html:radio property="lbsAppType"
                                value="<%=AimsConstants.LBS_APP_TYPE_ACTIVE[0]%>"/><%=AimsConstants.LBS_APP_TYPE_ACTIVE[1]%>
                    &nbsp;
                    <html:radio property="lbsAppType"
                                value="<%=AimsConstants.LBS_APP_TYPE_PASSIVE[0]%>"/><%=AimsConstants.LBS_APP_TYPE_PASSIVE[1]%>
                    &nbsp;
                    <html:radio property="lbsAppType"
                                value="<%=AimsConstants.LBS_APP_TYPE_BOTH[0]%>"/><%=AimsConstants.LBS_APP_TYPE_BOTH[1]%>
                    &nbsp;
                </td>
                <td width="50%">&nbsp;</td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <td align="right">
                    <strong>Geo Services Available</strong><br/>
                    <html:select property="listAvailableGeoServices" style="width:350px" size="7" styleClass="selectField"
                                 multiple="true"></html:select>
                </td>
                <% if ((isVerizonUser) || (statusSaved)) {%>
                <td style="vertical-align:middle; text-align:center">
                    <img name="imgAddGeoService" <bean:message key="images.add.button.lite"/>
                         onClick="copyToList('listAvailableGeoServices', 'listSelectedGeoServices'); sortItems('listSelectedGeoServices');"/>
                    <br/>
                    <img name="imgRemoveGeoService"<bean:message key="images.remove.button.lite"/> border="0"
                         onClick="copyToList('listSelectedGeoServices', 'listAvailableGeoServices'); sortItems('listAvailableGeoServices');"/>
                </td>
                <% } %>
                <td align="left">
                    <strong>Geo Services Selected&nbsp;<span class="requiredText">*</span>:</strong> <a
                        onClick="openZonHelpWindow(event, helpLBSGeoServices); return false;" href="#">[?]</a><br/>
                    <html:select property="listSelectedGeoServices" style="width:350px" size="7" styleClass="selectField"
                                 multiple="true"></html:select>
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
                    <strong>Client ID:</strong><br/>
                    <bean:write name="BrewApplicationUpdateForm" property="lbsClientId"/>&nbsp;
                </td>
                <td>
                    <strong>Secret Key:</strong><br/>
                    <bean:write name="BrewApplicationUpdateForm" property="lbsSecretKey"/>&nbsp;
                </td>
            </tr>
            <tr>
                <td width=50%>
                    <strong>Current Status:</strong><br/>
                    <strong><bean:write name="BrewApplicationUpdateForm" property="lbsAutodeskPhaseName"/></strong>&nbsp;
                </td>
                <td>
                    <%if (((com.netpace.aims.model.core.AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE)) {%>
                    <logic:equal name="BrewApplicationUpdateForm" property="lbsAutodeskPhaseId"
                                 value="<%=AimsConstants.LBS_STATUS_STAGING.toString()%>">
                        <%--<input type="image"	name="LBSMoveToProd" src="images/lbs_move_to_prod_b.gif" height="15" border="0" alt="Send to Production" />--%>
                        <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="movToProd"
                             title="Send to Production">
                            <div>
                                <div>
                                    <div onClick="submitForm();document.forms[0].appSubmitType.value='lbs';document.forms[0].task.value='lbs_submit_toprod';document.forms[0].submit();">
                                        Move To Production
                                    </div>
                                </div>
                            </div>
                        </div>
                    </logic:equal>
                    <% } else {
                    }%>&nbsp;
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="ApplicationForm.table.head.app.classification"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <th width=50%>
                    <strong><bean:message key="ManageApplicationForm.appCategory"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong><br/>
                    <html:select property="aimsAppCategoryId" size="1" onchange="changeSubCategories();" styleClass="selectField">
                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"
                                                             bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                        <html:optionsCollection property="allCategories" label="categoryName" value="categoryId"/>
                    </html:select>
                </th>
                <th>
                    <strong><bean:message key="ManageApplicationForm.appSubCategory"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong><br/>
                    <html:select property="aimsAppSubCategoryId" size="1" styleClass="selectField">
                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"
                                                             bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                    </html:select>
                </th>
            </tr>
        </table>
    </td>
</tr>
<%if (!(AimsSecurityManager.checkAccess(request, ManageApplicationsConstants.PRIV_BREW_LEGAL_USER, AimsSecurityManager.UPDATE))) {%>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="BrewApplicationForm.table.head.devices.supported"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td align="center">
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <th style="text-align:right">
                    <strong><bean:message key="BrewApplicationForm.appDevicesAvailable"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong><br/>
                    <html:select property="listAvailableDevices" style="width:200px" size="5" multiple="true" styleClass="selectField">
                    </html:select>
                </th>
                <logic:equal name="ApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"
                             value="<%=AimsConstants.SAVED_ID.toString()%>">
                    <th style="vertical-align:middle;text-align:center">
                        <img name="imgAddDevice" <bean:message key="images.add.button.lite"/>
                             onClick="copyToList('listAvailableDevices', 'listSelectedDevices');updateDeviceAlertMessage();"/>
                        <br/><br/>
                        <img name="imgRemoveDevice"<bean:message key="images.remove.button.lite"/>
                             onClick="copyToList('listSelectedDevices', 'listAvailableDevices');updateDeviceAlertMessage();"/>
                    </th>
                </logic:equal>
                <th style="text-align:left">
                    <strong><bean:message key="BrewApplicationForm.appDevicesSupported"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong><br/>
                    <html:select property="listSelectedDevices" style="width:200px" size="5" multiple="true" styleClass="selectField">
                    </html:select>
                </th>
            </tr>
        </table>
    </td>
</tr>
<% } else {
}%>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="ApplicationForm.table.head.prrelease"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <th width="100%">
                    <strong><bean:message key="ManageApplicationForm.prrelease"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong>
                </th>
            </tr>
            <tr>
                <td style="padding-top: 0px">
                    <html:radio property="ifPrRelease" value="Y"/><bean:message
                        key="ManageApplicationForm.radio.label.accept"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                    <html:radio property="ifPrRelease" value="N"/><bean:message
                        key="ManageApplicationForm.radio.label.reject"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<%@ include file="include/appEditButtons.jsp" %>
<tr><td><%@ include file="include/brewMessageFooter.jsp" %></td> </tr> 
<script language="javascript">
    changeSubCategories();
    setGeoServices();
    trackCountForTextAreas();

    <%if (!(AimsSecurityManager.checkAccess(request, ManageApplicationsConstants.PRIV_BREW_LEGAL_USER, AimsSecurityManager.UPDATE))) {%>
    setDevices();
    <% } else {}%>

    updateLBSDevices();

    <%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
    disableForVzw();
    <% } else {}%>

    <% if (!statusSaved) {%>
    disable1();
    <% } %>
</script>
<%if (((com.netpace.aims.model.core.AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE)) {%>
<html:hidden property="language"/>
<html:hidden property="appSize"/>
<html:hidden property="aimsAppCategoryId"/>
<html:hidden property="aimsAppSubCategoryId"/>
<html:hidden property="ifPrRelease"/>
<% } else {
}%>
<% if (!statusSaved) {%>
<html:hidden property="isLbs"/>
<% } %>
</table>
</div>
</html:form>
</div>
</div>

<script language="javascript">
    enableDisableNetworkUsage(document.forms[0].networkUsage);
    onCollapseText = '[Expand]';
    onExpandText = '[Collapse]';
    collapseURLTable();
    lastRowId = <%=applicationURLIndex%>;
    setAirTimeLength();
</script>
