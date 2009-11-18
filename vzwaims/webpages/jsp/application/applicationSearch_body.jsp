<%@ page language="java" import="com.netpace.aims.util.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<jsp:useBean id="task" class="java.lang.String" scope="request"/>

<script language="javascript">
var categoriesArray = new Array();
var subCategoriesArray = new Array();
var devicesArray = new Array();

function AimsAppCategory()
{
    this.categoryId = "";
    this.categoryName = "";
    this.aimsPlatformId = "";
}

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
}
<%
    int	index	=	0;
    %>

<%index	=	0;%>
<logic:iterate id="formCategories"	name="ApplicationSearchForm" property="allCategories" scope="request">
aimsAppCategory = new AimsAppCategory();
aimsAppCategory.categoryId = "<bean:write	name="formCategories" property="categoryId"	/>";
aimsAppCategory.categoryName = "<bean:write	name="formCategories" property="categoryName"	/>";
aimsAppCategory.aimsPlatformId = "<bean:write	name="formCategories" property="aimsPlatformId"	/>";
categoriesArray[<%=index%>] = aimsAppCategory;
<%index++;%>
</logic:iterate>

<%index	=	0;%>
<logic:iterate id="formSubCategories"	name="ApplicationSearchForm" property="allSubCategories" scope="request">
aimsAppSubCategory = new AimsAppSubCategory();
aimsAppSubCategory.subCategoryId = "<bean:write	name="formSubCategories" property="subCategoryId"	/>";
aimsAppSubCategory.subCategoryName = "<bean:write	name="formSubCategories" property="subCategoryName"	/>";
aimsAppSubCategory.aimsAppCategoryId = "<bean:write	name="formSubCategories" property="aimsAppCategoryId"	/>";
subCategoriesArray[<%=index%>] = aimsAppSubCategory;
<%index++;%>
</logic:iterate>

<%index	=	0;%>
<logic:iterate id="formDevices"	name="ApplicationSearchForm" property="availableDevices" scope="request">
aimsDevices = new AimsDevices();
aimsDevices.deviceId = "<bean:write	name="formDevices" property="deviceId" />";
aimsDevices.deviceModel = "<bean:write	name="formDevices" property="deviceModel"	/>";
devicesArray[<%=index%>] = aimsDevices;
<%index++;%>
</logic:iterate>

var supported = (window.Option) ? 1 : 0;


function changeCategories() {
    if (!supported) {
        alert("Feature	not	supported");
    }
    var options = document.forms[0].aimsAppCategoryId.options;
    for (var i = options.length - 1; i > 0; i--) {
        options[i] = null;
    }

    options[0] = new Option("<bean:message key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>", "0");
    var k = 1;
    var m = 0;

    for (var j = 0; j < categoriesArray.length; j++) {
        if (trim(categoriesArray[j].aimsPlatformId) == trim(document.forms[0].aimsPlatformId.options[document.forms[0].aimsPlatformId.selectedIndex].value))
        {
            options[k] = new Option(categoriesArray[j].categoryName, categoriesArray[j].categoryId);
            if (categoriesArray[j].categoryId == "<bean:write	name="ApplicationSearchForm" property="aimsAppCategoryId" scope="request"/>")
                m = k;
            k++;
        }
    }
    options[m].selected = true;

    changeSubCategories();

}

function changeSubCategories() {
    if (!supported) {
        alert("Feature	not	supported");
    }
    var options = document.forms[0].aimsAppSubCategoryId.options;
    for (var i = options.length - 1; i > 0; i--) {
        options[i] = null;
    }

    options[0] = new Option("<bean:message key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>", "0");
    var k = 1;
    var m = 0;

    for (var j = 0; j < subCategoriesArray.length; j++) {
        if (trim(subCategoriesArray[j].aimsAppCategoryId) == trim(document.forms[0].aimsAppCategoryId.options[document.forms[0].aimsAppCategoryId.selectedIndex].value))
        {
            options[k] = new Option(subCategoriesArray[j].subCategoryName, subCategoriesArray[j].subCategoryId);
            if (subCategoriesArray[j].subCategoryId == "<bean:write	name="ApplicationSearchForm" property="aimsAppSubCategoryId" scope="request"/>")
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

<logic:empty name="ApplicationSearchForm"	property="listSelectedDevices" scope="request">
    for (var j = 0; j < devicesArray.length; j++)
    {
        optionsAvailable[j] = new Option(devicesArray[j].deviceModel, devicesArray[j].deviceId);
    }
</logic:empty>

<logic:notEmpty	name="ApplicationSearchForm" property="listSelectedDevices"	scope="request">
    for (var j = 0; j < devicesArray.length; j++)
    {
        selectedNotFound = true;
    <logic:iterate id="devices"	name="ApplicationSearchForm" property="listSelectedDevices"	scope="request">
        if (devicesArray[j].deviceId == "<bean:write name="devices"/>")
        {
            optionsSelected[selectedIndex] = new Option(devicesArray[j].deviceModel, devicesArray[j].deviceId);
            selectedIndex++;
            selectedNotFound = false;
        }
    </logic:iterate>
        if (selectedNotFound)
        {
            optionsAvailable[availableIndex] = new Option(devicesArray[j].deviceModel, devicesArray[j].deviceId);
            availableIndex++;
        }
    }
</logic:notEmpty>
}
</script>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<DIV class="homeColumn lBox">

<html:form action="/applicationSearch">
<html:hidden property="task"/>

<DIV class="headLeftCurveblk"></DIV>
<H1><bean:message key="SearchApplicationForm.table.head.search.criteria"
          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
<table width="100%" border="0" cellspacing="0" cellpadding="5">
<tr>
    <td>
        <b>
            <bean:message key="ManageApplicationForm.appTitle"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </b>
        <br/>
        <html:text property="title" size="40" styleClass="inputField"/>
    </td>
    <td>
        <b>
            <bean:message key="ManageApplicationForm.appPlatform"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>

        </b><br/>
        <html:select property="aimsPlatformId" size="1" onchange="changeCategories();" styleClass="inputField">
            <html:option value="0">
                <bean:message key="ManageApplicationForm.label.selectOne"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
            </html:option>
            <html:optionsCollection property="allPlatforms" label="platformName" value="platformId"/>
        </html:select>
    </td>
</tr>

<tr>
    <td>
        <b>
            <bean:message key="ManageApplicationForm.appStatus"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </b>
        <br/>
        <html:select property="aimsLifecyclePhaseId" size="1" styleClass="inputField">
            <html:option value="0">
                <bean:message key="ManageApplicationForm.label.selectOne"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
            </html:option>
            <html:optionsCollection property="allPhases" label="phaseName" value="phaseId"/>
        </html:select>
    </td>
    <td>&nbsp;</td>
</tr>

<tr>
    <td>
    	<html:hidden property="brewAppType" value="0"/>
        <b>
            <bean:message key="ManageApplicationForm.appCategory"
                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
        </b> <br/>
        <html:select property="aimsAppCategoryId" size="1" onchange="changeSubCategories();" styleClass="inputField">
            <html:option value="0">
                <bean:message key="ManageApplicationForm.label.selectOne"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
            </html:option>
        </html:select>    	
    </td>
    <td><b>
        <bean:message key="ManageApplicationForm.appSubCategory"
                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
    </b><br/>
        <html:select property="aimsAppSubCategoryId" size="1" styleClass="inputField">
            <html:option value="0">
                <bean:message key="ManageApplicationForm.label.selectOne"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
            </html:option>
        </html:select>
    </td>
</tr>

<tr>
    <td><b>
        <bean:message key="ManageApplicationForm.appContractSigned"
                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
    </b> <br />
        <html:select property="aimsContractId" size="1" styleClass="inputField">
            <html:option value="0">
                <bean:message key="ManageApplicationForm.label.selectOne"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
            </html:option>
            <html:optionsCollection property="allContracts" label="contractTitle" value="contractId"/>
        </html:select>
    </td>
</tr><tr>
    <td colspan="2" align="center">
        <table width="100%" border="0" cellspacing="5" cellpadding="0">
            <tr>
                <td align="right">
                    <b>
                        <bean:message key="BrewApplicationForm.appDevicesAvailable"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    </b>
                </td>
                <td>
                </td>
                <td align="left">
                    <b>
                        <bean:message key="BrewApplicationForm.appDevicesSupported"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    </b>
                </td>
            </tr>
            <tr>
                <td class="text" align="right">
                    <html:select property="listAvailableDevices" style="width:300px" size="7" multiple="true">
                    </html:select>
                </td>
                <td align="center" valign="middle" width=40>
                        <%--<bean:message key="images.add.button.lite"/>--%>
                    <img src="images/greyRndArrow.gif"
                         onClick="copyToList('listAvailableDevices',	'listSelectedDevices')"/>
                    <br/>
                    <br/>
                    <img src="images/greyRndArrowL.gif"
                         onClick="copyToList('listSelectedDevices',	'listAvailableDevices')"/>
                </td>
                <td class="text" align="left">
                    <html:select property="listSelectedDevices" style="width:300px" size="7" multiple="true">
                    </html:select>
                </td>
            </tr>
        </table>
    </td>
</tr>

<tr>
    <td colspan="2">
        <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Save" title="Submit">
            <div>
                <div>
                    <div onClick="select_all(document.forms[0].listAvailableDevices);select_all(document.forms[0].listSelectedDevices); document.forms[0].submit();">Submit</div>
                </div>
            </div>
        </div>

        <div class="blackBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Cancel" title="Cancel">
            <div>
                <div><div onclick="document.forms[0].action='/aims/applicationCancel.do';document.forms[0].submit()">Cancel</div>
                </div>
            </div>
        </div>
    </td>
</tr>
</table>

</div>
</html:form>
</div>
</div>

<script language="javascript">
    changeCategories();
    changeSubCategories();
    setDevices();
</script>