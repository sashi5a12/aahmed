<%@ page import="com.netpace.aims.util.AimsConstants"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
                            
<%@ include  file="include/vzAppZoneVariables.jsp" %>

<script	language="javascript">
    <% 	int	index	=	0;	%>
    var	supported	=	(window.Option)	?	1	:	0;

    <%  if(hasAccessInitialApproval) {  %>

        var	subCategoriesArray = new Array();

        function AimsAppSubCategory() {
            this.subCategoryId = "";
            this.subCategoryName = "";
            this.aimsAppCategoryId = "";
        }//end AimsAppSubCategory

        <logic:iterate id="formSubCategories"	name="VZAppZoneApplicationUpdateForm" property="allSubCategories" scope="request">
            aimsAppSubCategory = new AimsAppSubCategory();
            aimsAppSubCategory.subCategoryId = "<bean:write	name="formSubCategories" property="subCategoryId"	/>";
            aimsAppSubCategory.subCategoryName = "<bean:write	name="formSubCategories" property="subCategoryName"	/>";
            aimsAppSubCategory.aimsAppCategoryId = "<bean:write	name="formSubCategories" property="aimsAppCategoryId"	/>";
            subCategoriesArray[<%=index%>] = aimsAppSubCategory;
            <%index++;%>
        </logic:iterate>

        function changeSubCategories(elemNumber) {
            if  (!supported) {
                alert("Feature  not supported");
            }

            var categoryElem = document.forms[document.forms[0].name].elements['categoryId' + elemNumber];
            var subCategoryElem = document.forms[document.forms[0].name].elements['subCategoryId' + elemNumber];

            var options = subCategoryElem.options;
            if(options && options.length) {
                for (var i = options.length - 1; i > 0; i--) {
                    options[i]  =   null;
                }

                options[0]= new Option("<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>", "0");
                var k=1;
                var m = 0;

                for (var j = 0; j < subCategoriesArray.length; j++) {
                    if  (subCategoriesArray[j].aimsAppCategoryId == categoryElem.options[categoryElem.selectedIndex].value) {
                        options[k] = new Option(subCategoriesArray[j].subCategoryName, subCategoriesArray[j].subCategoryId);
                        if (elemNumber == 1)
                            if (subCategoriesArray[j].subCategoryId=="<bean:write   name="VZAppZoneApplicationUpdateForm" property="subCategoryId1" scope="request"/>")
                                m=k;
                        if (elemNumber == 2)
                            if (subCategoriesArray[j].subCategoryId=="<bean:write   name="VZAppZoneApplicationUpdateForm" property="subCategoryId2" scope="request"/>")
                                m=k;
                        if (elemNumber == 3)
                            if (subCategoriesArray[j].subCategoryId=="<bean:write   name="VZAppZoneApplicationUpdateForm" property="subCategoryId3" scope="request"/>")
                                m=k;
                        k++;
                    }
                }
                options[m].selected = true;
            }//end if options.length
        }//end changeSubCategories(elemNumber)

        function enableDisableSubscriptionFields(disableFields) {
            document.forms[0].subscriptionBillingPricePoint.disabled = disableFields;
            document.forms[0].subsVZWRecommendedPrice.disabled = disableFields;
            document.forms[0].subsVendorSplitPercentage.disabled = disableFields;
            document.forms[0].subsVendorProductDisplay.disabled = disableFields;
        }//end enableDisableSubscriptionFields
        function enableDisableOnetimeFields(disableFields) {
            document.forms[0].oneTimeBillingPricePoint.disabled = disableFields;
            document.forms[0].oneTimeVZWRecommendedPrice.disabled = disableFields;
            document.forms[0].oneTimeVendorSplitPercentage.disabled = disableFields;
            document.forms[0].oneTimeVendorProductDisplay.disabled = disableFields;
        }//end enableDisableOnetimeFields
    <%  }//end if hasAccessInitialApproval   %>

    function callToggleDatePicker(eltName,formElt) {
		toggleDatePicker(eltName, document.forms[0].name + '.' + formElt);
	}

    function populateCurrentDateForTest(elemName)
    {
        var testedDateElem = document.forms[document.forms[0].name].elements[elemName];
        testedDateElem.value = new Date().getMonth()+1 + '/' + new Date().getDate() + '/' +  new Date().getFullYear();
    }

    function disableForStatus() {
        var frm = document.forms[0];
        <%  if (isEqualOrAboveInitialApprovalOrDenied) { %>
                for (var i=0; i<document.forms[0].elements.length; i++) {
                    if (frm.elements[i].name == "initialApprovalAction") {
                        frm.elements[i].disabled = true;
                    }
                    <%  if (isSunset) { %>
                            if (frm.elements[i].name == "moveToSunset") {
                                frm.elements[i].disabled = true;
                            }
                    <%  }//end isSunset %>
                }//end for
        <%  }//end isEqualOrAboveInitialApprovalOrDenied %>

    }//end disableForStatus

    function collapseRow(sectionRow, binaryFirmwareId) {
        document.getElementById("row"+sectionRow+binaryFirmwareId).style.display='none';
        document.getElementById("spnExpandCollapse"+sectionRow+binaryFirmwareId).innerHTML=
                "<a class='a' onclick='javascript:expandRow(\""+sectionRow+"\", "+binaryFirmwareId+");'>[+]</a>";
        return false;
    }//end collapseBinariesRow
    function expandRow(sectionRow, binaryFirmwareId) {
        document.getElementById("row"+sectionRow+binaryFirmwareId).style.display='';
        document.getElementById("spnExpandCollapse"+sectionRow+binaryFirmwareId).innerHTML=
                "<a class='a' onclick='javascript:collapseRow(\""+sectionRow+"\", "+binaryFirmwareId+");'>[-]</a>";
        return false;
    }//end collapseBinariesRow

    function truncateLocalTextAreas() {

         <%  if(hasAccessInitialApproval) {  %>
            if (typeof document.forms[0].initialApprovalNotes != "undefined")
                if (typeof document.forms[0].initialApprovalNotes.value != "undefined")
                    TruncateTextToMaxChars(document.forms[0].initialApprovalNotes, 500);

            if (typeof document.forms[0].subsVendorProductDisplay != "undefined")
                if (typeof document.forms[0].subsVendorProductDisplay.value != "undefined")
                    TruncateTextToMaxChars(document.forms[0].subsVendorProductDisplay, 30);

            if (typeof document.forms[0].oneTimeVendorProductDisplay != "undefined")
                if (typeof document.forms[0].oneTimeVendorProductDisplay.value != "undefined")
                    TruncateTextToMaxChars(document.forms[0].oneTimeVendorProductDisplay, 30);
        <%  }//end hasAccessInitialApproval   %>
    }

    <%  if(isVerizonUser && hasAccessInitialApproval)   {    %>
            function gotoMportalAllianceEdit(allianceId) {
                var popupURL = "/aims/mportalAllianceUpdate.do?task=edit&allianceId="+allianceId;
                var childWindow = window.open(popupURL,"wndMportalAllianceUpdate","menubar=no,location=no,resizable=no,toolbar=no,width=550,height=435,scrollbars=yes");
                if (childWindow.opener == null) childWindow.opener = self;
                document.cookie = 'scrollTop=' + document.body.scrollTop;
                childWindow.focus();
            }

            function refreshPage() {
                showProcessingInfo();
                document.forms[0].appSubmitType.value='paging';
                document.forms[0].task.value='processingInfo';
                document.forms[0].submit();
            }
    <%  }//end if initial approval    %>
</script>
<%@ include  file="include/vzAppZoneAppJScript.jsp" %>

<%@ include  file="appTabMessageHeader.jsp" %>

<div id="contentBox" style="float:left">
    <div class="homeColumnTab ">
        <%@ include  file="include/vzAppZoneTabs.jsp" %>
        <html:form action="/vzAppZoneApplicationUpdate.do" enctype="multipart/form-data">
            <%@ include  file="include/vzAppZoneHidden.jsp" %>
            <div class="contentbox">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <%-- Initial Approval--%>
                    <%  if(hasAccessInitialApproval) {  %>
                        <tr>
                            <td><div class="lBox">
                                <div class="headLeftCurveblk"></div>
                                <h1>Initial Approval / Denial</h1>
                                <div class="headRightCurveblk"></div>
                            </div></td>
                        </tr>
                        <tr>
                            <td>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                            <%-- Application Billing Starts --%>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                                <tr>
                                                    <th colspan="4"><strong><u>Application Billing</u></strong></th>
                                                </tr>
                                                <%
                                                    boolean disableSubscriptionBillingFields = true;
                                                    boolean disableOnetimeBillingFields = true;
                                                %>
                                                <tr>
                                                    <td><strong>Subscription Billing (Monthly):</strong></td>
                                                    <td>
                                                        <html:radio disabled="true" property="subscriptionBillingMonthly" value="Y" onclick="javascript:enableDisableSubscriptionFields(false);"/>Yes &nbsp;
                                                        <html:radio disabled="true" property="subscriptionBillingMonthly" value="N" onclick="javascript:enableDisableSubscriptionFields(true);"/>No
                                                        <%--    disable subscription billing fields
                                                            <logic:equal name="VZAppZoneApplicationUpdateForm" property="subscriptionBillingMonthly" value="Y">
                                                                <% disableSubscriptionBillingFields = false; %>
                                                            </logic:equal>
                                                        --%>
                                                    </td>
                                                    <td><strong>Onetime Billing:</strong></td>
                                                    <td>
                                                        <html:radio property="oneTimeBilling" value="Y" onclick="javascript:enableDisableOnetimeFields(false);"/>Yes &nbsp;
                                                        <html:radio property="oneTimeBilling" value="N" onclick="javascript:enableDisableOnetimeFields(true);"/>No
                                                        <logic:equal name="VZAppZoneApplicationUpdateForm" property="oneTimeBilling" value="Y">
                                                            <% disableOnetimeBillingFields = false; %>
                                                        </logic:equal>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Subscription Billing Pricepoint&nbsp;<span class="requiredText">*</span>:</strong></td>
                                                    <td><html:text styleClass="inputField" property="subscriptionBillingPricePoint"  size="20" maxlength="10" disabled="<%=disableSubscriptionBillingFields%>"/></td>
                                                    <td><strong>Onetime Billing Pricepoint&nbsp;<span class="requiredText">*</span>:</strong></td>
                                                    <td><html:text styleClass="inputField" property="oneTimeBillingPricePoint"  size="20" maxlength="10" disabled="<%=disableOnetimeBillingFields%>" /></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Verizon Recommended Price&nbsp;<span class="requiredText">*</span>:</strong></td>
                                                    <td><html:text styleClass="inputField" property="subsVZWRecommendedPrice"  size="20" maxlength="10" disabled="<%=disableSubscriptionBillingFields%>"/></td>
                                                    <td><strong>Verizon Recommended Price&nbsp;<span class="requiredText">*</span>:</strong></td>
                                                    <td><html:text styleClass="inputField" property="oneTimeVZWRecommendedPrice"  size="20" maxlength="10" disabled="<%=disableOnetimeBillingFields%>"/></td>
                                                </tr>
                                                <tr>
                                                    <td><strong>Vendor Split Percentage:</strong></td>
                                                    <td><html:text styleClass="inputField" property="subsVendorSplitPercentage"  size="20" maxlength="6" disabled="<%=disableSubscriptionBillingFields%>"/></td>
                                                    <td><strong>Vendor Split Percentage:</strong></td>
                                                    <td><html:text styleClass="inputField" property="oneTimeVendorSplitPercentage"  size="20" maxlength="6" disabled="<%=disableOnetimeBillingFields%>"/></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2"><strong>Vendor Product Display:</strong></td>
                                                    <td colspan="2"><strong>Vendor Product Display:</strong></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">(Information entered here will be displayed on the subscriber's bill)</td>
                                                    <td colspan="2">(Information entered here will be displayed on the subscriber's bill)</td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2"><html:textarea styleClass="textareaField" property="subsVendorProductDisplay"
                                                                       onkeyup="TruncateTextToMaxChars(this,30)" onkeypress="TruncateTextToMaxChars(this,30)"
                                                                       rows="4" cols="50" disabled="<%=disableSubscriptionBillingFields%>"></html:textarea></td>
                                                    <td colspan="2"><html:textarea styleClass="textareaField" property="oneTimeVendorProductDisplay"
                                                                       onkeyup="TruncateTextToMaxChars(this,30)" onkeypress="TruncateTextToMaxChars(this,30)"
                                                                       rows="4" cols="50" disabled="<%=disableOnetimeBillingFields%>"></html:textarea></td>
                                                </tr>
                                            </table>
                                            <%-- End Application Billing--%>
                                        </td>
                                    </tr>

                                    <%-- mPortal Alliance Name --%>
                                    <tr><td><hr style="display:block;"/></td></tr>
                                    <tr>
                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                            <tr>
                                                <td width="15%" nowrap style="vertical-align:top" ><strong>MPortal Alliance Name:</strong></td>
                                                <td class="viewText" style="vertical-align:top">
                                                    <c:out value='${VZAppZoneApplicationUpdateForm.mportalAllianceName}'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <a onclick="javascript:gotoMportalAllianceEdit(<c:out value="${VZAppZoneApplicationUpdateForm.appOwnerAllianceId}"/>);" class="a" title="Change MPortal Alliance Name" style="text-decoration:underline; cursor:pointer;">Change</a>
                                                </td>
                                            </tr>
                                            <tr><td colspan="2">&nbsp;</td></tr>
                                        </table></td>
                                    </tr>
                                    <%-- End mPortal Alliance Name --%>

                                    <tr><td><hr style="display:block;"/></td></tr>
                                    <tr>
                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                            <tr>
                                                <td width="33%"><strong><bean:message   key="ManageApplicationForm.appCategory"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></td>
                                                <td width="33%"><strong><bean:message   key="ManageApplicationForm.appSubCategory"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></td>
                                                <%-- commented, scmVendorId, no need to show
                                                    <td width="34%"><strong>SCM Vendor ID<span class="requiredText">*</span>:</strong></td>
                                                --%>
                                                <td width="34%">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <html:select styleClass="selectField" property="categoryId1"   size="1" onchange="changeSubCategories(1);">
                                                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                                        <html:optionsCollection property="allCategories" label="categoryName"    value="categoryId"/>
                                                    </html:select>
                                                </td>
                                                <td>
                                                    <html:select styleClass="selectField" property="subCategoryId1" size="1">
                                                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                                    </html:select>
                                                </td>
                                                <%-- commented, scmVendorId, no need to show
                                                    <td>
                                                        <html:text styleClass="inputField" property="scmVendorId"  size="15" maxlength="7"/>
                                                    </td>
                                                --%>
                                                <td>&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <html:select styleClass="selectField" property="categoryId2"   size="1" onchange="changeSubCategories(2);">
                                                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                                        <html:optionsCollection property="allCategories" label="categoryName"    value="categoryId"/>
                                                    </html:select>
                                                </td>
                                                <td>
                                                    <html:select styleClass="selectField" property="subCategoryId2" size="1">
                                                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                                    </html:select>
                                                </td>
                                                <td>
                                                    <strong>VZW Projected Live Date&nbsp;<span class="requiredText">*</span>:</strong>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <html:select styleClass="selectField" property="categoryId3"   size="1" onchange="changeSubCategories(3);">
                                                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                                        <html:optionsCollection property="allCategories" label="categoryName"    value="categoryId"/>
                                                    </html:select>
                                                </td>
                                                <td>
                                                    <html:select styleClass="selectField" property="subCategoryId3" size="1">
                                                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                                    </html:select>
                                                </td>
                                                <td style="vertical-align:top;">
                                                    <html:text styleClass="inputField" property="vzwLiveDate" size="15" maxlength="10"/><img name="daysOfMonth99Pos" onclick="toggleDatePicker('daysOfMonth99','VZAppZoneApplicationUpdateForm.vzwLiveDate')" id="daysOfMonth99Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth99"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td width="33%"><strong>Tax Category Code&nbsp;<span class="requiredText">*</span>:</strong></td>
                                                <td width="33%"><strong>Notes:</strong></td>
                                                <td width="34%"><strong>Action&nbsp;<span class="requiredText">*</span>:</strong></td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <html:select styleClass="selectField" property="taxCategoryCodeId" size="1">
                                                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                                        <logic:iterate id="taxCategoryCodes" name="VZAppZoneApplicationUpdateForm" property="allTaxCategoryCodes" type="com.netpace.aims.model.masters.AimsTaxCategoryCode">
                                                            <html:option value="<%=taxCategoryCodes.getTaxCategoryCodeId().toString()%>"><%=taxCategoryCodes.getTaxCategoryCode()%> - <%=taxCategoryCodes.getTaxCategoryCodeDesc()%></html:option>
                                                        </logic:iterate>
                                                    </html:select><br/>
                                                    <strong>Content Type&nbsp;<span class="requiredText">*</span>:</strong><br/>
                                                    <html:select styleClass="selectField" property="contentType" size="1">
                                                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                                        <html:optionsCollection property="allContentTypes" label="typeValue" value="typeId"/>
                                                    </html:select>
                                                </td>
                                                <td>
                                                    <html:textarea styleClass="textareaField" property="initialApprovalNotes" rows="4"   cols="50" onkeyup="TruncateTextToMaxChars(this,500)" onkeypress="TruncateTextToMaxChars(this,500)"></html:textarea>
                                                </td>
                                                <td style="vertical-align:top;">
                                                    <html:radio property="initialApprovalAction" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_APPROVE[0]%>"/><%=AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_APPROVE[1]%>&nbsp;
                                                    <html:radio property="initialApprovalAction" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_DENY[0]%>"/><%=AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_DENY[1]%>&nbsp;
                                                </td>
                                            </tr>
                                        </table></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                    <% }//end if hasAccessInitialApproval  %>
                    <%-- End Initial Approval --%>

                    <%-- If application is approved, show Application Management and Device Testing Sections --%>
                    <%  if(isEqualOrAboveTesting) { %>

                        <%  if(hasAccessApplicationManagement) {    %>
                            <%-- Application Management --%>
                            <tr>
                                <td><div class="lBox">
                                    <div class="headLeftCurveblk"></div>
                                    <h1>Application Management</h1>
                                    <div class="headRightCurveblk"></div>
                                </div></td>
                            </tr>
                            <tr>
                                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                    <tr>
                                        <th width="100%">
                                            <html:checkbox property="isLocked" value="<%=AimsConstants.VZAPPZONE_APP_LOCK_APPLICATION[0]%>">
                                                <strong><%=AimsConstants.VZAPPZONE_APP_LOCK_APPLICATION[1]%></strong>
                                            </html:checkbox>
                                        </th>
                                    </tr>
                                </table></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <%-- End Application Management --%>
                        <% }//end hasAccessApplicationManagement    %>

                        <%-- Device Testing --%>
                        <%  if(hasAccessBaseTesting) {    %>
                            <tr>
                                <td><div class="lBox">
                                    <div class="headLeftCurveblk"></div>
                                    <h1>Testing</h1>
                                    <div class="headRightCurveblk"></div>
                                </div></td>
                            </tr>
                            <tr>
                                <td><table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
                                    <tr>
                                        <th nowrap style="vertical-align:top"><strong>Device Name:</strong></th>
                                        <th style="vertical-align:top"><strong>MR Number:</strong></th>
                                        <th style="vertical-align:top"><strong>File Name and Version:</strong></th>
                                        <th style="vertical-align:top"><strong>Paid:</strong></th>
                                        <th nowrap style="vertical-align:top"><strong>Binary ID:</strong></th>
                                        <th style="vertical-align:top"><strong>Status:</strong></th>
                                        <th nowrap style="vertical-align:top"><strong>Tested Date:</strong></th>
                                        <th nowrap style="vertical-align:top"><strong>Test Status:</strong></th>
                                        <th style="vertical-align:top"><strong>&nbsp;</strong></th>
                                    </tr>
                                    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppBinaryFirmwareInfoVOs">
                                        <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppBaseTests">
                                            <%
                                                String sectionName = "baseTest";
                                                boolean disableBaseTest = false;
                                            %>
                                            <logic:iterate id="VZAppBaseTest" name="VZAppZoneApplicationUpdateForm" property="VZAppBaseTests" indexId="baseIdx">
                                                <% disableBaseTest = false; %>
                                                <logic:notEmpty name="VZAppBaseTest" property="disableBaseTest">
                                                    <logic:equal name="VZAppBaseTest" property="disableBaseTest" value="true">
                                                        <%  disableBaseTest = true; %>
                                                    </logic:equal>
                                                </logic:notEmpty>
                                                <c:if test="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].binaryFirmwareId==VZAppBaseTest.binaryFirmwareId}">
                                                    <tr>
                                                        <td style="vertical-align:top" onmouseover="return Tip('<b>Firmware:</b> <c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].firmwareName}"/><br/><b>Updated By:</b><br/><c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].lastUpdatedBy}"/><br/>(<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].lastUpdatedDate}"/>)');">
                                                            <b><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].phoneModel}'/></b>
                                                        </td>
                                                        <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].mrNumber}'/></td>
                                                        <td style="vertical-align:top">
                                                            <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryFile&vzAppBinaryId=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].binaryId}"/>&app_id=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].vzAppZoneAppsId}"/>" class="a"	target="_blank">
                                                                <c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].binaryFileFileName}' escapeXml="false"/>
                                                            </a>
                                                            <br/>(<c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].binaryVersion}' escapeXml="false"/>)
                                                        </td>
                                                        <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].isPaid}'/></td>
                                                        <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].binaryId}'/></td>
                                                        <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].binaryFirmwareStatusValue}'/></td>
                                                        <%  if(disableBaseTest) { %>
                                                            <td style="vertical-align:top" nowrap>
                                                                <html:text disabled="true" styleClass="inputField" property='<%="VZAppBaseTest["+baseIdx+"].baseTestedDate"%>' maxlength="10"/><img name="baseTestDaysOfMonth<%=baseIdx%>Pos" id="baseTestDaysOfMonth<%=baseIdx%>Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="baseTestDaysOfMonth<%=baseIdx%>"/>
                                                                <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].baseTestedDate"%>' />
                                                            </td>
                                                            <td style="vertical-align:top" nowrap>
                                                                <html:radio disabled="true" property='<%="VZAppBaseTest["+baseIdx+"].baseTestStatus"%>' value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[0]%>" onclick='<%= "populateCurrentDateForTest('VZAppBaseTest["+baseIdx+"].baseTestedDate')" %>'/><%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[1]%>&nbsp;
                                                                <html:radio disabled="true" property='<%="VZAppBaseTest["+baseIdx+"].baseTestStatus"%>' value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[0]%>" onclick='<%= "populateCurrentDateForTest('VZAppBaseTest["+baseIdx+"].baseTestedDate')" %>'/><%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[1]%>
                                                                <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].baseTestStatus"%>' />
                                                            </td>
                                                        <%
                                                            }
                                                            else {
                                                        %>
                                                            <td style="vertical-align:top" nowrap>
                                                                <html:text disabled="false" styleClass="inputField" property='<%="VZAppBaseTest["+baseIdx+"].baseTestedDate"%>' maxlength="10"/><img onclick="toggleDatePicker('baseTestDaysOfMonth<%=baseIdx%>','<%="VZAppZoneApplicationUpdateForm.VZAppBaseTest["+baseIdx+"].baseTestedDate"%>')" name="baseTestDaysOfMonth<%=baseIdx%>Pos" id="baseTestDaysOfMonth<%=baseIdx%>Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="baseTestDaysOfMonth<%=baseIdx%>"/>
                                                            </td>
                                                            <td style="vertical-align:top" nowrap>
                                                                <html:radio disabled="false" property='<%="VZAppBaseTest["+baseIdx+"].baseTestStatus"%>' value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[0]%>" onclick='<%= "populateCurrentDateForTest('VZAppBaseTest["+baseIdx+"].baseTestedDate')" %>'/><%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[1]%>&nbsp;
                                                                <html:radio disabled="false" property='<%="VZAppBaseTest["+baseIdx+"].baseTestStatus"%>' value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[0]%>" onclick='<%= "populateCurrentDateForTest('VZAppBaseTest["+baseIdx+"].baseTestedDate')" %>'/><%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[1]%>
                                                            </td>
                                                        <%  } %>

                                                        <td nowrap style="vertical-align:top">
                                                            <span id="spnExpandCollapse<%=sectionName%><c:out value='${VZAppBaseTest.binaryFirmwareId}'/>" style="cursor:pointer;">
                                                                <a class="a" onclick="javascript:expandRow('<%=sectionName%>', <c:out value='${VZAppBaseTest.binaryFirmwareId}'/>)">[+]</a>
                                                            </span>
                                                        </td>
                                                    </tr>
                                                    <tr id="row<%=sectionName%><c:out value='${VZAppBaseTest.binaryFirmwareId}'/>" style="display:none;">
                                                        <td colspan="9" style="border: 1px solid black;">
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                    <td style="vertical-align:top; padding-left:0px;"><strong>Comments:</strong></td>
                                                                    <%  if(disableBaseTest) { %>
                                                                        <td style="vertical-align:top">
                                                                            <html:textarea readonly="true" styleClass="textareaField" property='<%="VZAppBaseTest["+baseIdx+"].baseComments"%>' onkeyup="LimitText(this,200)" onkeypress="LimitText(this,200)" rows="4" cols="70"></html:textarea>
                                                                            <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].baseComments"%>' />
                                                                        </td>
                                                                    <%
                                                                        }
                                                                        else {
                                                                    %>
                                                                        <td style="vertical-align:top">
                                                                            <html:textarea readonly="false" styleClass="textareaField" property='<%="VZAppBaseTest["+baseIdx+"].baseComments"%>' onkeyup="LimitText(this,200)" onkeypress="LimitText(this,200)" rows="4" cols="70"></html:textarea>
                                                                        </td>
                                                                    <%  } %>

                                                                    <td style="vertical-align:top"><strong>Results File:</strong></td>
                                                                    <td style="vertical-align:top">
                                                                        <html:file disabled="<%=disableBaseTest%>" styleClass="inputField" size="15" property='<%="VZAppBaseTest["+baseIdx+"].baseResultsFile"%>'/><br/>
                                                                        <logic:notEmpty	name="VZAppBaseTest"	property="baseResultsFileFileName">
                                                                            <logic:equal name="VZAppBaseTest" property="baseResultsFileTempFileId"	value="0">
                                                                                <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBaseResultFile&vzAppBinaryFirmwareId=<c:out value="${VZAppBaseTest.binaryFirmwareId}"/>&app_id=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].vzAppZoneAppsId}"/>"	class="a"	target="_blank">
                                                                                    <c:out value="${VZAppBaseTest.baseResultsFileFileName}" escapeXml="false"/>
                                                                                </a>
                                                                            </logic:equal>
                                                                            <logic:notEqual	name="VZAppBaseTest"	property="baseResultsFileTempFileId" value="0">
                                                                                <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&tempFileId=<c:out value="${VZAppBaseTest.baseResultsFileTempFileId}"/>&app_id=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].vzAppZoneAppsId}"/>" class="a" target="_blank">
                                                                                    <c:out value="${VZAppBaseTest.baseResultsFileFileName}"/>
                                                                                </a>
                                                                            </logic:notEqual>
                                                                        </logic:notEmpty>
                                                                        &nbsp;
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </logic:iterate>
                                        </logic:notEmpty>
                                    </logic:notEmpty>
                                </table></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                        <%  }//end if hasAccessBaseTesting    %>
                        <%-- End Device Testing--%>
                    <%  }//end isEqualOrAboveTesting    %>

                    <%-- show OTA section only when atleast device test passed means application is in testing passed state --%>
                    <%  if(isEqualOrAboveTestingPassed) {   %>
                        <% if(hasAccessOTATesting) { %>
                            <%-- OTA Test Start --%>
                            <tr>
                                <td><div class="lBox">
                                    <div class="headLeftCurveblk"></div>
                                    <h1>Move To OTA Testing</h1>
                                    <div class="headRightCurveblk"></div>
                                </div></td>
                            </tr>
                            <tr>
                                <td><table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
                                    <tr>
                                        <th style="vertical-align:top"><strong>Move to Testing:</strong></th>
                                        <th nowrap style="vertical-align:top"><strong>Device Name:</strong></th>
                                        <th nowrap style="vertical-align:top"><strong>MR Number:</strong></th>
                                        <th nowrap style="vertical-align:top"><strong>Binary ID:</strong></th>
                                        <th style="vertical-align:top"><strong>Status:</strong></th>
                                        <th nowrap style="vertical-align:top"><strong>Tested Date:</strong></th>
                                        <th nowrap style="vertical-align:top"><strong>OTA Test Staging:</strong></th>
                                        <th style="vertical-align:top"><strong>&nbsp;</strong></th>
                                    </tr>
                                    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppBinaryFirmwareInfoVOs">
                                        <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppZoneOTATests">
                                            <%
                                                String sectionName = "otaTest";
                                                boolean disableOTATest = false;
                                                boolean disableMoveToOTATesting = false;
                                            %>
                                            <logic:iterate id="VZAppZoneOTATest" name="VZAppZoneApplicationUpdateForm" property="VZAppZoneOTATests" indexId="otaIdx">
                                                <% disableMoveToOTATesting = false; disableOTATest = false; %>
                                                <logic:notEmpty name="VZAppZoneOTATest" property="disableMoveToOTATesting">
                                                    <logic:equal name="VZAppZoneOTATest" property="disableMoveToOTATesting" value="true">
                                                        <%  disableMoveToOTATesting = true; %>
                                                    </logic:equal>
                                                </logic:notEmpty>
                                                <logic:notEmpty name="VZAppZoneOTATest" property="disableOTATest">
                                                    <logic:equal name="VZAppZoneOTATest" property="disableOTATest" value="true">
                                                        <%  disableOTATest = true; %>
                                                    </logic:equal>
                                                </logic:notEmpty>
                                                <c:if test="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].binaryFirmwareId==VZAppZoneOTATest.binaryFirmwareId}">
                                                    <tr>
                                                        <td style="vertical-align:top"><html:checkbox property='<%="VZAppZoneOTATest["+otaIdx+"].moveToOTATesting"%>' value="<%=AimsConstants.YES_CHAR%>" disabled="<%=disableMoveToOTATesting%>"/></td>
                                                        <td style="vertical-align:top" onmouseover="return Tip('<b>Firmware:</b> <c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].firmwareName}"/><br/><b>Updated By:</b><br/><c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].lastUpdatedBy}"/><br/>(<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].lastUpdatedDate}"/>)');">
                                                            <b><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].phoneModel}'/></b>
                                                        </td>
                                                        <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].mrNumber}'/></td>
                                                        <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].binaryId}'/></td>
                                                        <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].binaryFirmwareStatusValue}'/></td>
                                                        <%  if(disableOTATest) { %>
                                                            <td style="vertical-align:top" nowrap>
                                                                <html:text disabled="true" styleClass="inputField" property='<%="VZAppZoneOTATest["+otaIdx+"].otaTestedDate"%>' maxlength="10"/><img name="otaTestDaysOfMonth<%=otaIdx%>Pos" id="otaTestDaysOfMonth<%=otaIdx%>Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="otaTestDaysOfMonth<%=otaIdx%>"/>
                                                                <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].otaTestedDate"%>' />
                                                            </td>
                                                            <td style="vertical-align:top" nowrap>
                                                                <html:radio disabled="true" property='<%="VZAppZoneOTATest["+otaIdx+"].otaTestStatus"%>' value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[0]%>" onclick='<%= "populateCurrentDateForTest('VZAppZoneOTATest["+otaIdx+"].otaTestedDate')" %>'/><%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[1]%>&nbsp;
                                                                <html:radio disabled="true" property='<%="VZAppZoneOTATest["+otaIdx+"].otaTestStatus"%>' value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[0]%>" onclick='<%= "populateCurrentDateForTest('VZAppZoneOTATest["+otaIdx+"].otaTestedDate')" %>'/><%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[1]%>
                                                                <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].otaTestStatus"%>' />
                                                            </td>
                                                        <%
                                                            }
                                                            else {
                                                        %>
                                                            <td style="vertical-align:top" nowrap>
                                                                <html:text disabled="false" styleClass="inputField" property='<%="VZAppZoneOTATest["+otaIdx+"].otaTestedDate"%>' maxlength="10"/><img onclick="toggleDatePicker('otaTestDaysOfMonth<%=otaIdx%>','<%="VZAppZoneApplicationUpdateForm.VZAppZoneOTATest["+otaIdx+"].otaTestedDate"%>')" name="otaTestDaysOfMonth<%=otaIdx%>Pos" id="otaTestDaysOfMonth<%=otaIdx%>Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="otaTestDaysOfMonth<%=otaIdx%>"/>
                                                            </td>
                                                            <td style="vertical-align:top" nowrap>
                                                                <html:radio disabled="false" property='<%="VZAppZoneOTATest["+otaIdx+"].otaTestStatus"%>' value="<%=AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_PASSED[0]%>" onclick='<%= "populateCurrentDateForTest('VZAppZoneOTATest["+otaIdx+"].otaTestedDate')" %>'/><%=AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_PASSED[1]%>&nbsp;
                                                                <html:radio disabled="false" property='<%="VZAppZoneOTATest["+otaIdx+"].otaTestStatus"%>' value="<%=AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_FAILED[0]%>" onclick='<%= "populateCurrentDateForTest('VZAppZoneOTATest["+otaIdx+"].otaTestedDate')" %>'/><%=AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_FAILED[1]%>
                                                            </td>
                                                        <%  } %>

                                                        <td nowrap style="vertical-align:top">
                                                            <span id="spnExpandCollapse<%=sectionName%><c:out value='${VZAppZoneOTATest.binaryFirmwareId}'/>" style="cursor:pointer;">
                                                                <a class="a" onclick="javascript:expandRow('<%=sectionName%>', <c:out value='${VZAppZoneOTATest.binaryFirmwareId}'/>)">[+]</a>
                                                            </span>
                                                        </td>
                                                    </tr>
                                                    <tr id="row<%=sectionName%><c:out value='${VZAppZoneOTATest.binaryFirmwareId}'/>" style="display:none;">
                                                        <td colspan="9" style="border: 1px solid black;">
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                <tr >
                                                                    <td style="vertical-align:top; padding-left:0px;"><strong>Comments:</strong></td>
                                                                    <%  if(disableOTATest) { %>
                                                                        <td style="vertical-align:top">
                                                                            <html:textarea readonly="true" styleClass="textareaField" property='<%="VZAppZoneOTATest["+otaIdx+"].otaComments"%>' onkeyup="LimitText(this,200)" onkeypress="LimitText(this,200)" rows="4" cols="70"></html:textarea>
                                                                            <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].otaComments"%>' />
                                                                        </td>
                                                                    <%
                                                                        }
                                                                        else {
                                                                    %>
                                                                        <td style="vertical-align:top">
                                                                            <html:textarea readonly="false" styleClass="textareaField" property='<%="VZAppZoneOTATest["+otaIdx+"].otaComments"%>' onkeyup="LimitText(this,200)" onkeypress="LimitText(this,200)" rows="4" cols="70"></html:textarea>
                                                                        </td>
                                                                    <%  } %>

                                                                    <td style="vertical-align:top"><strong>Results File:</strong></td>
                                                                    <td style="vertical-align:top">
                                                                        <html:file disabled="<%=disableOTATest%>" styleClass="inputField" size="15" property='<%="VZAppZoneOTATest["+otaIdx+"].otaResultsFile"%>'/><br/>
                                                                        <logic:notEmpty	name="VZAppZoneOTATest"	property="otaResultsFileFileName">
                                                                            <logic:equal name="VZAppZoneOTATest" property="otaResultsFileTempFileId"	value="0">
                                                                                <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppOTAResultFile&vzAppBinaryFirmwareId=<c:out value="${VZAppZoneOTATest.binaryFirmwareId}"/>&app_id=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].vzAppZoneAppsId}"/>"	class="a"	target="_blank">
                                                                                    <c:out value="${VZAppZoneOTATest.otaResultsFileFileName}" escapeXml="false"/>
                                                                                </a>
                                                                            </logic:equal>
                                                                            <logic:notEqual	name="VZAppZoneOTATest"	property="otaResultsFileTempFileId" value="0">
                                                                                <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&tempFileId=<c:out value="${VZAppZoneOTATest.otaResultsFileTempFileId}"/>&app_id=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].vzAppZoneAppsId}"/>" class="a" target="_blank">
                                                                                    <c:out value="${VZAppZoneOTATest.otaResultsFileFileName}"/>
                                                                                </a>
                                                                            </logic:notEqual>
                                                                        </logic:notEmpty>
                                                                        &nbsp;
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </logic:iterate>
                                        </logic:notEmpty>
                                    </logic:notEmpty>
                                </table></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <%-- OTA Test End--%>
                        <% }//end hasAccessOTATesting    %>
                    <%  } //end if isEqualOrAboveTestingPassed   %>

                    <%  if(isEqualOrAboveOTATestPassed) {  %>
                        <%-- Start Move to Staging Section --%>
                        <%  if(hasAccessMoveToStaging) {    %>
                            <tr>
                                <td><div class="lBox">
                                    <div class="headLeftCurveblk"></div>
                                    <h1>Move To Staging</h1>
                                    <div class="headRightCurveblk"></div>
                                </div></td>
                            </tr>
                            <tr>
                                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                    <tr>
                                        <th><strong>Move to <br/>Staging:</strong>&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                        <th><strong>Device Name:</strong></th>
                                        <th><strong>MR Number:</strong></th>
                                        <th nowrap><strong>File Name and Version:</strong></th>
                                        <th><strong>Binary ID:</strong></th>
                                        <th><strong>Status:</strong></th>
                                        <th><strong>Date Moved:</strong></th>
                                    </tr>
                                    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppZoneStageInfoVOs">
                                        <%
                                            boolean disableMoveToStageBox = true;
                                        %>
                                        <logic:iterate id="VZAppZoneStageInfo" name="VZAppZoneApplicationUpdateForm" property="VZAppZoneStageInfoVOs" indexId="stgIdx">
                                            <%-- if binary firmware is in ota test passed state, enable move to stage fields--%>
                                            <% disableMoveToStageBox = false;  %>
                                                <logic:notEmpty name="VZAppZoneStageInfo" property="disableMoveToStaging">
                                                    <logic:equal name="VZAppZoneStageInfo" property="disableMoveToStaging" value="true">
                                                        <%  disableMoveToStageBox = true; %>
                                                    </logic:equal>
                                                </logic:notEmpty>
                                            <tr>
                                                <td  style="vertical-align:top">
                                                    <html:checkbox property='<%="VZAppZoneStageInfo["+stgIdx+"].moveToStaging"%>' value="<%=AimsConstants.YES_CHAR%>" disabled="<%=disableMoveToStageBox%>"/>
                                                </td>
                                                <td  style="vertical-align:top" onmouseover="return Tip('<b>Firmware:</b> <c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[stgIdx].firmwareName}"/><br/><b>Updated By:</b><br/><c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[stgIdx].lastUpdatedBy}"/><br/>(<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[stgIdx].lastUpdatedDate}"/>)');">
                                                    <b><c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[stgIdx].phoneModel}"/></b>
                                                </td>
                                                <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[stgIdx].mrNumber}'/></td>
                                                <td  style="vertical-align:top">
                                                    <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryFile&vzAppBinaryId=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[stgIdx].binaryId}"/>&app_id=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[stgIdx].vzAppZoneAppsId}"/>" class="a"	target="_blank">
                                                        <c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[stgIdx].binaryFileFileName}' escapeXml="false"/>
                                                    </a>
                                                    <br/>(<c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[stgIdx].binaryVersion}' escapeXml="false"/>)
                                                </td>
                                                <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[stgIdx].binaryId}'/></td>
                                                <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[stgIdx].binaryFirmwareStatusValue}'/></td>
                                                <td  style="vertical-align:top">
                                                    <c:out value='${VZAppZoneStageInfo.stageMovedDate}'/>
                                                </td>
                                            </tr>
                                            <tr><td colspan="4">&nbsp;</td></tr>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </table></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                        <% }//end end hasAccessMoveToStaging    %>
                        <%-- End Move to Staging Section --%>

                        <%-- Start Move to Production Section --%>
                        <%  if(hasAccessMoveToProduction) {  %>
                            <tr>
                                <td><div class="lBox">
                                    <div class="headLeftCurveblk"></div>
                                    <h1>Move To Production</h1>
                                    <div class="headRightCurveblk"></div>
                                </div></td>
                            </tr>
                            <tr>
                                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                    <tr>
                                        <th><strong>Move to <br/> Production:</strong></th>
                                        <th><strong>Device Name:</strong></th>
                                        <th><strong>MR Number:</strong></th>
                                        <th nowrap><strong>File Name and Version:</strong></th>
                                        <th><strong>Binary ID:</strong></th>
                                        <th><strong>Status:</strong></th>
                                        <th><strong>Date Moved:</strong></th>
                                    </tr>
                                    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppZoneProdInfoVOs">
                                        <%
                                            boolean disableMoveToProdBox = true;
                                        %>
                                        <logic:iterate id="VZAppZoneProdInfo" name="VZAppZoneApplicationUpdateForm" property="VZAppZoneProdInfoVOs" indexId="prodIdx">
                                            <%-- if binary firmware is in ota test passed state, enable move to prod fields--%>
                                            <% disableMoveToProdBox = false;  %>
                                                <logic:notEmpty name="VZAppZoneProdInfo" property="disableMoveToProd">
                                                    <logic:equal name="VZAppZoneProdInfo" property="disableMoveToProd" value="true">
                                                        <%  disableMoveToProdBox = true; %>
                                                    </logic:equal>
                                                </logic:notEmpty>
                                            <tr>
                                                <td  style="vertical-align:top">
                                                    <html:checkbox property='<%="VZAppZoneProdInfo["+prodIdx+"].moveToProd"%>' value="<%=AimsConstants.YES_CHAR%>" disabled="<%=disableMoveToProdBox%>"/>
                                                </td>
                                                <td  style="vertical-align:top" onmouseover="return Tip('<b>Firmware:</b> <c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[prodIdx].firmwareName}"/><br/><b>Updated By:</b><br/><c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[prodIdx].lastUpdatedBy}"/><br/>(<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[prodIdx].lastUpdatedDate}"/>)');">
                                                    <b><c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[prodIdx].phoneModel}"/></b>
                                                </td>
                                                <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[prodIdx].mrNumber}'/></td>
                                                <td  style="vertical-align:top">
                                                    <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryFile&vzAppBinaryId=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[prodIdx].binaryId}"/>&app_id=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[prodIdx].vzAppZoneAppsId}"/>" class="a"	target="_blank">
                                                        <c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[prodIdx].binaryFileFileName}' escapeXml="false"/>
                                                    </a>
                                                    <br/>(<c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[prodIdx].binaryVersion}' escapeXml="false"/>)
                                                </td>
                                                <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[prodIdx].binaryId}'/></td>
                                                <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[prodIdx].binaryFirmwareStatusValue}'/></td>
                                                <td  style="vertical-align:top">
                                                    <c:out value='${VZAppZoneProdInfo.prodMovedDate}'/>
                                                </td>
                                            </tr>
                                            <tr><td colspan="4">&nbsp;</td></tr>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </table></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                        <% }//end hasAccessMoveToProduction    %>
                        <%-- End Move to Production Section --%>
                    <% }//isEqualOrAboveOTATestPassed    %>

                    <%  if(isEqualOrAboveProduction) {  %>
                        <%-- Move to Sunset --%>
                        <%  if(hasAccessMoveToSunset) {   %>
                            <tr>
                                <td><div class="lBox">
                                    <div class="headLeftCurveblk"></div>
                                    <H1>Sunset</H1>
                                    <div class="headRightCurveblk"></div>
                                </div></td>
                            </tr>
                            <tr>
                                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                    <tr>
                                        <th class="text" valign="top"><strong>
                                            <html:checkbox property="moveToSunset" value="<%=AimsConstants.VZAPPZONE_APP_CHECKBOX_SUNSET[0]%>">
                                                <%=AimsConstants.VZAPPZONE_APP_CHECKBOX_SUNSET[1]%>
                                            </html:checkbox></strong>
                                        </th>
                                    </tr>
                                </table></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                        <% }//end hasAccessMoveToSunset%>
                        <%-- end Move to Sunset --%>
                    <% }//end isEqualOrAboveProduction%>

                    <tr><td>&nbsp;</td></tr>
                    <tr>
                        <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr><td>
                                <%@ include  file="include/vzAppZoneEditButtons.jsp" %>
                                <script	language="javascript">
                                    disableForStatus();
                                </script>
                                <%  if(isEqualOrAboveInitialApprovalOrDenied) { %>
                                    <%-- after initial approval/denied, this field will be disabled, hidden variable will keep its state --%>
                                    <html:hidden property="initialApprovalAction"  />
                                    <%  if(isSunset) { %>
                                        <html:hidden property="moveToSunset"  />
                                    <% }//end if sunset %>
                                <% } %>
                            </td></tr>
                            <tr><td>
                                <%@ include  file="appTabMessageFooter.jsp" %>
                            </td></tr>
                          </table></td>
                    </tr>
                </table>
            </div>
            <%-- end div contentbox--%>
        </html:form>
    </div>
    <%-- end homeColumnTab --%>
</div>

<script	language="javascript">
    <%  if(hasAccessInitialApproval) {  %>
        changeSubCategories(1);
        changeSubCategories(2);
        changeSubCategories(3);
    <%  }//end hasAccessInitialApproval%>
</script>