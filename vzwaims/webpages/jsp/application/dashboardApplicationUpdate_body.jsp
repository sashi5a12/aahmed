<%@ page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ include  file="include/dashboardAppVariables.jsp" %>
<c:set value="${requestScope.DashboardApplicationUpdateForm}" var="form"></c:set>

<script language="javascript">
	var subCategoriesArray = new Array();
	var devicesArray = new Array();

	function AimsAppSubCategory() {
    	this.subCategoryId = "";
    	this.subCategoryName = "";
    	this.aimsAppCategoryId = "";
	}

	function AimsDevices() {
    	this.deviceId = "";
    	this.deviceModel = "";
    	this.deviceStatus = "";
    	this.deviceAlert = "";
    	this.deviceAlertMessage = "";
    	this.deviceLbsSupported = "";
	}

	<%int index	= 0;%>
	<logic:iterate id="formSubCategories"	name="DashboardApplicationUpdateForm" property="allSubCategories" scope="request">
		aimsAppSubCategory = new AimsAppSubCategory();
		aimsAppSubCategory.subCategoryId = "<bean:write	name="formSubCategories" property="subCategoryId"	/>";
		aimsAppSubCategory.subCategoryName = "<bean:write	name="formSubCategories" property="subCategoryName"	filter="false"/>";
		aimsAppSubCategory.aimsAppCategoryId = "<bean:write	name="formSubCategories" property="aimsAppCategoryId"	/>";
		subCategoriesArray[<%=index%>] = aimsAppSubCategory;
		<%index++;%>
	</logic:iterate>

	<%index	=	0;%>
	<logic:iterate id="formDevices"	name="DashboardApplicationUpdateForm" property="availableDevices" scope="request">
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

        	if (subCategoriesArray[j].aimsAppCategoryId == document.forms[0].aimsAppCategoryId.options[document.forms[0].aimsAppCategoryId.selectedIndex].value){
            	options[k] = new Option(subCategoriesArray[j].subCategoryName, subCategoriesArray[j].subCategoryId);
            	if (subCategoriesArray[j].subCategoryId == "<bean:write	name="DashboardApplicationUpdateForm" property="aimsAppSubCategoryId" scope="request"/>")
                	m = k;
            	k++;
        	}
    	}
    	options[m].selected = true;
	}

	function setDevices() {
    	if (!supported) {
        	alert("Feature	not	supported");
    	}
    	var optionsAvailable = document.forms[0].listAvailableDevices.options;
    	var optionsSelected = document.forms[0].listSelectedDevices.options;
    	var availableIndex = 0;
    	var selectedIndex = 0;
    	var selectedNotFound;
		<logic:empty name="DashboardApplicationUpdateForm"	property="listSelectedDevices" scope="request">
    		for (var j = 0; j < devicesArray.length; j++) {
        		if (devicesArray[j].deviceStatus.toUpperCase() == "ACTIVE") {
            		optionsAvailable[availableIndex] = new Option(devicesArray[j].deviceModel, devicesArray[j].deviceId);
            		availableIndex++;
        		}
    		}
		</logic:empty>

		<logic:notEmpty	name="DashboardApplicationUpdateForm" property="listSelectedDevices"	scope="request">
    		for (var j = 0; j < devicesArray.length; j++) {
        		selectedNotFound = true;
	    		<logic:iterate id="devices"	name="DashboardApplicationUpdateForm" property="listSelectedDevices"	scope="request">
	        	if (devicesArray[j].deviceId == "<bean:write name="devices"/>") {
	            	optionsSelected[selectedIndex] = new Option(devicesArray[j].deviceModel, devicesArray[j].deviceId);
	            	selectedIndex++;
	            	selectedNotFound = false;
	        	}
	    		</logic:iterate>
    	
		        if ((selectedNotFound) && (devicesArray[j].deviceStatus.toUpperCase() == "ACTIVE")) {
		            optionsAvailable[availableIndex] = new Option(devicesArray[j].deviceModel, devicesArray[j].deviceId);
		            availableIndex++;
		        }
    		}
		</logic:notEmpty>
    	updateDeviceAlertMessage();
	}

	function truncateLocalTextAreas() {
	    TruncateTextWithCount(document.forms[0].shortDesc, 'textCountShortDesc', 200);
	    TruncateTextWithCount(document.forms[0].longDesc, 'textCountLongDesc', 500);

        if (typeof document.forms[0].vendorProductDisplay != "undefined")
            if (typeof document.forms[0].vendorProductDisplay.value != "undefined")
			 TruncateText(document.forms[0].vendorProductDisplay,500);	    
	}

	function trackCountForTextAreas() {
	    TrackCount(document.forms[0].shortDesc, 'textCountShortDesc', 200);
	    TrackCount(document.forms[0].longDesc, 'textCountLongDesc', 500);
	}
	function updateDeviceAlertMessage() {
    	document.forms[0].selectedDevicesAlertMessage.value = "";

    	for (var j = 0; j < devicesArray.length; j++) {
        	if (devicesArray[j].deviceAlert == "Y") {
            	for (var k = 0; k < document.forms[0].listSelectedDevices.length; k++) {
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
	function disableFields(){
		<c:if test="${not empty form.isContentZipFileLock and form.isContentZipFileLock eq 'Y'}">
			document.forms[0].contentZipFile.disabled=true;
		</c:if>		
	}		
	<%@ include  file="include/dashboardJScript.jsp" %>

</script>
<%@ include file="include/dashboardMessageHeader.jsp" %>

<div id="contentBox" style="float: left" onMouseMove="truncateLocalTextAreas();">
	<DIV class="homeColumnTab lBox">
		<%@ include file="include/dashboardTabs.jsp"%>
		<html:form action="/dashboardApplicationUpdate" enctype="multipart/form-data">
		<html:hidden property="currentPage" value="page1"/>
			<div class="contentbox">
				<%@ include file="include/dashboardAppHidden.jsp"%>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>Channel Details</H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" bordercolor="black" cellspacing="0" cellpadding="5" class="GridGradient" style="border: 0px black solid">
								<tr>
									<th width="50%">
										<strong>Channel Title&nbsp;<span class="requiredText">*</span>:</strong>
										<br />
										<html:text property="title" size="30" maxlength="200" styleClass="inputField" /> <i>(Underscore '_' is not allowed)</i>
									</th>
									<th>
										<strong>Channel Version&nbsp;<span class="requiredText">*</span>:</strong>
										<br />
										<html:text property="channelVersion" size="30" maxlength="30" styleClass="inputField" />
									</th>
								</tr>
								<tr>
									<td width="50%" colspan="2">
										<strong>Channel Type&nbsp;<span class="requiredText">*</span>:</strong>
										<br />
										<html:radio property="channelType" value="<%=AimsConstants.DASHBOARD_APP_RADIO_CHANNEL_TYPE_BASIC[0]%>"/><%=AimsConstants.DASHBOARD_APP_RADIO_CHANNEL_TYPE_BASIC[1]%>&nbsp;
										<html:radio property="channelType" value="<%=AimsConstants.DASHBOARD_APP_RADIO_CHANNEL_TYPE_PREMIUM[0]%>"/><%=AimsConstants.DASHBOARD_APP_RADIO_CHANNEL_TYPE_PREMIUM[1]%>										
									</td>
								</tr>
								<tr>
									<td width="50%">
										<strong>Language&nbsp;<span	class="requiredText">*</span>:</strong>
										<br />
										<html:radio property="language" value="EN" />
										<bean:message key="ManageApplicationForm.language.english" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
										&nbsp;
										<html:radio property="language" value="SP" />
										<bean:message key="ManageApplicationForm.language.spanish" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
										&nbsp;
									</td>
									<td>
										<strong>Channel Size:</strong>
										<br />
										<html:text property="channelSize" size="30" maxlength="30" styleClass="inputField" />
									</td>
								</tr>
								<tr>
									<td width="50%">
										<strong>
											<bean:message key="ManageApplicationForm.appShortDesc" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
											<span class="requiredText">*</span>:</strong>
										<br />
										<html:textarea property="shortDesc" rows="4" cols="50" styleClass="textareaField" onkeyup="TrackCount(this,'textCountShortDesc',200)" onkeypress="LimitText(this,200)"></html:textarea>
										<br />
										<table border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td style="vertical-align: middle; padding: 0">
													<bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
												</td>
												<td>
													<input class="inputFieldNoPad" type="text" name="textCountShortDesc" size="1" value="200" disabled="true" />
												</td>
											</tr>
										</table>
									</td>
									<td>
										<strong>
											<bean:message key="ManageApplicationForm.appLongDesc" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
											<span class="requiredText">*</span>:</strong>
										<br />
										<html:textarea property="longDesc" rows="4" cols="50" styleClass="textareaField" onkeyup="TrackCount(this,'textCountLongDesc',500)"	onkeypress="LimitText(this,500)"></html:textarea>
										<br />
										<table border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td style="vertical-align: middle; padding: 0">
													<bean:message key="ManageApplicationForm.textarea.char.remaining" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
												</td>
												<td>
													<input class="inputFieldNoPad" type="text" name="textCountLongDesc" size="1" value="500" disabled="true" />
												</td>
											</tr>
										</table>
									</td>
								</tr>																
							</table>
						</td>
					</tr>									
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>Billing Information (if Premium)</H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" bordercolor="black" cellspacing="0" cellpadding="5" class="GridGradient" style="border: 0px solid black">
								<tr>
									<th width="50%" style="vertical-align: top;">
										<strong>VZW Suggested Retail Price (USD):</strong><br/>										
										<html:text styleClass="inputField" property="vzwRetailPrice" size="35" maxlength="10" />								
									</th>
									<th style="vertical-align: top;">
										<strong>Vendor Product Display:</strong><br/>
										(Information entered here will be displayed on the subscriber's bill)
										<br />
										<html:textarea property="vendorProductDisplay"  rows="4" cols="50" styleClass="textareaField"  onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)"></html:textarea>
									</th>
								</tr>
							</table>
						</td>
					</tr>					
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>Channel Classification</H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
								<tr>
									<th width="50%">
										<strong>Channel Category&nbsp;<span class="requiredText">*</span>:</strong>
										<br />
										<html:select property="aimsAppCategoryId" size="1" onchange="changeSubCategories();" styleClass="selectField">
											<html:option value="0">
												<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
											</html:option>
											<html:optionsCollection property="allCategories" label="categoryName" value="categoryId" />
										</html:select>
									</th>
									<th>
										<strong>Channel Subcategory&nbsp;<span class="requiredText">*</span>:</strong>
										<br />
										<html:select property="aimsAppSubCategoryId" size="1" styleClass="selectField">
											<html:option value="0">
												<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
											</html:option>
										</html:select>
									</th>
								</tr>
							</table>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1><bean:message key="BrewApplicationForm.table.head.devices.supported" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
								</H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					<tr>
						<td align="center">
							<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
								<tr>
									<th style="text-align: right">
										<strong>Devices Available</strong>
										<br />
										<html:select property="listAvailableDevices" style="width:200px" size="6" multiple="true" styleClass="selectField"/>
									</th>
									<th style="vertical-align: middle; text-align: center">
									<br/>
									<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
										<tr><td><img name="imgAddDevice" <bean:message key="images.add.button.lite"/>
											onClick="copyToList('listAvailableDevices', 'listSelectedDevices');updateDeviceAlertMessage();" /></td></tr>
										<tr><td><img name="imgRemoveDevice" <bean:message key="images.remove.button.lite"/>
											onClick="copyToList('listSelectedDevices', 'listAvailableDevices');updateDeviceAlertMessage();" /></td></tr>
										<tr><td><img alt="Add All" name="imgAddAllDevice" src="images/greyRAllArrow.gif" border="0" 
											onClick="select_all(document.forms[0].listAvailableDevices);copyToList('listAvailableDevices', 'listSelectedDevices',false);updateDeviceAlertMessage();" /></td></tr>
										<tr><td><img alt="Remove All" name="imgRemoveAllDevice" src="images/greyLAllArrow.gif" border="0" 
											onClick="select_all(document.forms[0].listSelectedDevices);copyToList('listSelectedDevices', 'listAvailableDevices',false);updateDeviceAlertMessage();" /></td></tr>
									</table>
									</th>
									<th style="text-align: left">
										<strong>Devices Supported&nbsp;<span class="requiredText">*</span>:</strong>
										<br />
										<html:select property="listSelectedDevices" style="width:200px" size="6" multiple="true" styleClass="selectField"/>
									</th>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>Content ZIP</H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
								<tr>
									<th>
										<strong>Content ZIP File:</strong>
									</th>
								</tr>								
								<tr>
									<td>
										<html:file size="30" styleClass="inputField" property="contentZipFile" />
										<br />
										<logic:notEmpty name="DashboardApplicationUpdateForm" property="contentZipFileFileName" scope="request">
											<logic:equal name="DashboardApplicationUpdateForm" property="contentZipFileTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashContentZipFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
													class="a" target="_blank">
											</logic:equal>
											<logic:notEqual name="DashboardApplicationUpdateForm" property="contentZipFileTempFileId" scope="request" value="0">
												<a
													href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="contentZipFileTempFileId" />"
													class="a" target="_blank">
											</logic:notEqual>
											<bean:write name="DashboardApplicationUpdateForm" property="contentZipFileFileName" />
											</a>
										</logic:notEmpty>
									</td>
								</tr>	
							</table>
						</td>
					</tr>
					<%@ include file="include/dashboardAppEditButtons.jsp"%>
					<tr><td><%@ include file="include/dashboardMessageFooter.jsp" %></td> </tr> 
					<script language="javascript">
						changeSubCategories();
						setDevices();
						trackCountForTextAreas();
						<%if (isAllianceUser) {%>
							disableFields(); 
						<% } %>						
					</script>
				</table>
			</div>
		</html:form>
	</div>
</div>