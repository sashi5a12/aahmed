<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:useBean id="VZAppZoneApplicationUpdateForm" class="com.netpace.aims.controller.application.VZAppZoneApplicationUpdateForm" scope="request" />
<%VZAppZoneApplicationUpdateForm.setCurrentPage("processingInfo");%>
<%@ include  file="include/vzAppZoneVariables.jsp" %>

<script language="javascript">
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
</script>

<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
    <div class="homeColumnTab ">
        <%@ include  file="include/vzAppZoneViewTabs.jsp" %>
	    <html:form action="/vzAppZoneApplicationUpdate.do"    enctype="multipart/form-data">
            <div class="contentbox">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <%-- Initial Approval--%>
                    <% if(hasAccessInitialApproval) { %>
                        <tr>
                            <td><div class="lBox">
                                <div class="headLeftCurveblk"></div>
                                <h1>Initial Approval / Denial</h1>
                                <div class="headRightCurveblk"></div>
                            </div></td>
                        </tr>
                        <tr>
                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td>
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                        <tr>
                                            <th colspan="2"><strong><u>Application Billing</u></strong></th>
                                        </tr>
                                        <tr>
                                            <td><strong>Subscription Billing (Monthly):</strong></td>
                                            <td><strong>Onetime Billing:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText">
                                                <logic:equal name="VZAppZoneApplicationUpdateForm" property="subscriptionBillingMonthly" value="Y">
                                                    <bean:message key="ManageApplicationForm.radio.label.yes"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                                </logic:equal>
                                                <logic:equal name="VZAppZoneApplicationUpdateForm" property="subscriptionBillingMonthly" value="N">
                                                    <bean:message key="ManageApplicationForm.radio.label.no"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                                </logic:equal>
                                            </td>
                                            <td class="viewText">
                                                <logic:equal name="VZAppZoneApplicationUpdateForm" property="oneTimeBilling" value="Y">
                                                    <bean:message key="ManageApplicationForm.radio.label.yes"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                                </logic:equal>
                                                <logic:equal name="VZAppZoneApplicationUpdateForm" property="oneTimeBilling" value="N">
                                                    <bean:message key="ManageApplicationForm.radio.label.no"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                                </logic:equal>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><strong>Subscription Billing Pricepoint:</strong></td>
                                            <td><strong>Onetime Billing Pricepoint:</strong></td>

                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="VZAppZoneApplicationUpdateForm" property="subscriptionBillingPricePoint" />&nbsp;</td>
                                            <td class="viewText"><bean:write name="VZAppZoneApplicationUpdateForm" property="oneTimeBillingPricePoint" />&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Verizon Recommended Price:</strong></td>
                                            <td><strong>Verizon Recommended Price:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="VZAppZoneApplicationUpdateForm" property="subsVZWRecommendedPrice" />&nbsp;</td>
                                            <td class="viewText"><bean:write name="VZAppZoneApplicationUpdateForm" property="oneTimeVZWRecommendedPrice" />&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Vendor Split Percentage:</strong></td>
                                            <td><strong>Vendor Split Percentage:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="VZAppZoneApplicationUpdateForm" property="subsVendorSplitPercentage" />&nbsp;</td>
                                            <td class="viewText"><bean:write name="VZAppZoneApplicationUpdateForm" property="oneTimeVendorSplitPercentage" />&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td><strong>Vendor Product Display:</strong></td>
                                            <td><strong>Vendor Product Display:</strong></td>
                                        </tr>
                                        <tr>
                                            <td>(Information entered here will be displayed on the subscriber's bill)</td>
                                            <td>(Information entered here will be displayed on the subscriber's bill)</td>
                                        </tr>
                                        <tr>
                                            <td><html:textarea styleClass="textareaField" property="subsVendorProductDisplay"
                                                               rows="4" cols="50" readonly="true"></html:textarea></td>
                                            <td><html:textarea styleClass="textareaField" property="oneTimeVendorProductDisplay"
                                                               rows="4" cols="50" readonly="true"></html:textarea></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <%-- mPortal Alliance Name --%>
                            <tr><td class="viewText"><hr style="display:block;"/></td></tr>
                            <tr>
                                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                    <tr>
                                        <td width="15%" nowrap style="vertical-align:top" ><strong>MPortal Alliance Name:</strong></td>
                                        <td class="viewText" style="vertical-align:top" ><c:out value='${VZAppZoneApplicationUpdateForm.mportalAllianceName}'/>&nbsp;</td>
                                    </tr>
                                    <tr><td colspan="2">&nbsp;</td></tr>
                                </table></td>
                            </tr>
                            <%-- End mPortal Alliance Name --%>

                            <tr><td class="viewText"><hr style="display:block;"/></td></tr>
                            <tr>
                                <td>
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                        <tr>
                                            <td width="33%"><strong><bean:message   key="ManageApplicationForm.appCategory"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                            <td width="33%"><strong><bean:message   key="ManageApplicationForm.appSubCategory"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                            <%-- commented, scmVendorId, no need to show
                                                <td width="34%"><strong>SCM Vendor ID:</strong></td>
                                            --%>
                                            <td width="34%">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td class="viewText">
                                                <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="categoryId1">
                                                    <logic:iterate id="formCategories" name="VZAppZoneApplicationUpdateForm" property="allCategories" scope="request">
                                                        <logic:equal name="formCategories" property="categoryId" value="<%=VZAppZoneApplicationUpdateForm.getCategoryId1().toString()%>">
                                                            <bean:write name="formCategories" property="categoryName" />
                                                        </logic:equal>
                                                    </logic:iterate>
                                                </logic:notEmpty>
                                                &nbsp;
                                            </td>
                                            <td class="viewText">
                                                <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="subCategoryId1">
                                                    <logic:iterate id="formSubCategories" name="VZAppZoneApplicationUpdateForm" property="allSubCategories" scope="request">
                                                        <logic:equal name="formSubCategories" property="subCategoryId" value="<%=VZAppZoneApplicationUpdateForm.getSubCategoryId1().toString()%>">
                                                            <bean:write name="formSubCategories" property="subCategoryName" />
                                                        </logic:equal>
                                                    </logic:iterate>
                                                </logic:notEmpty>
                                                &nbsp;
                                            </td>
                                            <%-- commented, scmVendorId, no need to show
                                                <td class="viewText">
                                                    <bean:write name="VZAppZoneApplicationUpdateForm" property="scmVendorId" />
                                                </td>
                                            --%>
                                            <td class="viewText">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td class="viewText">
                                                <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="categoryId2">
                                                    <logic:iterate id="formCategories" name="VZAppZoneApplicationUpdateForm" property="allCategories" scope="request">
                                                        <logic:equal name="formCategories" property="categoryId" value="<%=VZAppZoneApplicationUpdateForm.getCategoryId2().toString()%>">
                                                            <bean:write name="formCategories" property="categoryName" />
                                                        </logic:equal>
                                                    </logic:iterate>
                                                </logic:notEmpty>
                                                &nbsp;
                                            </td>
                                            <td class="viewText">
                                                <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="subCategoryId2">
                                                    <logic:iterate id="formSubCategories" name="VZAppZoneApplicationUpdateForm" property="allSubCategories" scope="request">
                                                        <logic:equal name="formSubCategories" property="subCategoryId" value="<%=VZAppZoneApplicationUpdateForm.getSubCategoryId2().toString()%>">
                                                            <bean:write name="formSubCategories" property="subCategoryName" />
                                                        </logic:equal>
                                                    </logic:iterate>
                                                </logic:notEmpty>
                                                &nbsp;
                                            </td>
                                            <td class="viewText">
                                                <strong>VZW Projected Live Date:</strong>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="viewText">
                                                <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="categoryId3">
                                                    <logic:iterate id="formCategories" name="VZAppZoneApplicationUpdateForm" property="allCategories" scope="request">
                                                        <logic:equal name="formCategories" property="categoryId" value="<%=VZAppZoneApplicationUpdateForm.getCategoryId3().toString()%>">
                                                            <bean:write name="formCategories" property="categoryName" />
                                                        </logic:equal>
                                                    </logic:iterate>
                                                </logic:notEmpty>
                                                &nbsp;
                                            </td>
                                            <td class="viewText">
                                                <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="subCategoryId3">
                                                    <logic:iterate id="formSubCategories" name="VZAppZoneApplicationUpdateForm" property="allSubCategories" scope="request">
                                                        <logic:equal name="formSubCategories" property="subCategoryId" value="<%=VZAppZoneApplicationUpdateForm.getSubCategoryId3().toString()%>">
                                                            <bean:write name="formSubCategories" property="subCategoryName" />
                                                        </logic:equal>
                                                    </logic:iterate>
                                                </logic:notEmpty>
                                                &nbsp;
                                            </td>
                                            <td class="viewText">
                                                <bean:write name="VZAppZoneApplicationUpdateForm" property="vzwLiveDate" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="33%"><strong>Tax Category Code:</strong></td>
                                            <td width="33%"><strong>Notes:</strong></td>
                                            <td width="34%"><strong>Action:</strong></td>
                                        </tr>
                                        <tr>
                                            <td style="vertical-align:top">
                                                <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="taxCategoryCodeId">
                                                    <logic:iterate id="taxCategoryCodes" name="VZAppZoneApplicationUpdateForm" property="allTaxCategoryCodes" scope="request">
                                                        <logic:equal name="taxCategoryCodes" property="taxCategoryCodeId" value="<%=VZAppZoneApplicationUpdateForm.getTaxCategoryCodeId().toString()%>">
                                                            <bean:write name="taxCategoryCodes" property="taxCategoryCode" /> - <bean:write name="taxCategoryCodes" property="taxCategoryCodeDesc" />
                                                        </logic:equal>
                                                    </logic:iterate>
                                                </logic:notEmpty><br /><br/>
                                                <strong>Content Type:</strong><br/>
                                                <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="contentType">
                                                <logic:iterate id="contentType" name="VZAppZoneApplicationUpdateForm" property="allContentTypes" type="com.netpace.aims.model.core.AimsTypes">
                                                        <logic:equal name="contentType" property="typeId" value="<%=VZAppZoneApplicationUpdateForm.getContentType().toString()%>">
                                                            <bean:write name="contentType" property="typeValue"/>
                                                        </logic:equal>
                                                </logic:iterate>
                                            </logic:notEmpty>
                                            </td>
                                            <td>
                                                <html:textarea styleClass="textareaField"  property="initialApprovalNotes" rows="4"   cols="50" readonly="true"></html:textarea>
                                            </td>
                                            <td style="vertical-align:top">
                                                <logic:equal name="VZAppZoneApplicationUpdateForm" property="initialApprovalAction" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_APPROVE[0]%>">
                                                    <%=AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_APPROVE[1]%>
                                                </logic:equal>
                                                <logic:equal name="VZAppZoneApplicationUpdateForm" property="initialApprovalAction" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_DENY[0]%>">
                                                    <%=AimsConstants.VZAPPZONE_APP_RADIO_INITIAL_DENY[1]%>
                                                </logic:equal>
                                            </td>
                                        </tr>

                                    </table>
                                </td>
                            </tr>
                        </table></td>
                      </tr>
                        <tr><td>&nbsp;</td></tr>
                    <% }//end if hasAccessInitialApproval  %>
                    <%-- End Initial Approval --%>

                    <%-- If application is approved, show Application Management and Device Testing sections --%>
                    <% if(isEqualOrAboveTesting) { %>
                    
                        <% if(hasAccessApplicationManagement) { %>
                            <%-- Application Management Section --%>
                            <tr>
                                <td><div class="lBox">
                                    <div class="headLeftCurveblk"></div>
                                    <h1>Application Management</h1>
                                    <div class="headRightCurveblk"></div>
                                </div></td>
                            </tr>
                            <tr>
                                <td><table width="100%" cellspacing="0" cellpadding="0" class="GridGradient">
                                    <tr>
                                        <th width="100%">
                                            <strong><%=AimsConstants.VZAPPZONE_APP_LOCK_APPLICATION[1]%>:&nbsp;</strong>
                                            <logic:equal name="VZAppZoneApplicationUpdateForm" property="isLocked" value="<%=AimsConstants.VZAPPZONE_APP_LOCK_APPLICATION[0]%>">
                                                <bean:message key="ManageApplicationForm.radio.label.yes"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                            </logic:equal>
                                            <logic:notEqual name="VZAppZoneApplicationUpdateForm" property="isLocked" value="<%=AimsConstants.VZAPPZONE_APP_LOCK_APPLICATION[0]%>">
                                                <bean:message key="ManageApplicationForm.radio.label.no"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                            </logic:notEqual>
                                        </th>
                                    </tr>
                                </table></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <%-- End Application Management Section --%>
                        <% }//end hasAccessApplicationManagement %>

                        <% if(hasAccessBaseTesting) { %>
                            <%-- Device Testing --%>
                            <tr>
                                <td><div class="lBox">
                                    <div class="headLeftCurveblk"></div>
                                    <h1><bean:message key="ApplicationForm.table.head.testing"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></h1>
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
                                            %>
                                            <logic:iterate id="VZAppBaseTest" name="VZAppZoneApplicationUpdateForm" property="VZAppBaseTests" indexId="baseIdx">
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

                                                        <td style="vertical-align:top">                                                            
                                                            <c:out value="${VZAppBaseTest.baseTestedDate}"/>&nbsp;
                                                        </td>
                                                        <td style="vertical-align:top" nowrap>
                                                            <logic:equal name="VZAppBaseTest" property="baseTestStatus" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[0]%>">
                                                                <%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_PASSED[1]%>
                                                            </logic:equal>
                                                            <logic:equal name="VZAppBaseTest" property="baseTestStatus" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[0]%>">
                                                                <%=AimsConstants.VZAPPZONE_APP_RADIO_TEST_FAILED[1]%>
                                                            </logic:equal>
                                                        </td>

                                                        <td nowrap style="vertical-align:top">
                                                            <span id="spnExpandCollapse<%=sectionName%><c:out value='${VZAppBaseTest.binaryFirmwareId}'/>" style="cursor:pointer;">
                                                                <a class="a" onclick="javascript:expandRow('<%=sectionName%>', <c:out value='${VZAppBaseTest.binaryFirmwareId}'/>)">[+]</a>
                                                            </span>
                                                        </td>
                                                    </tr>
                                                    <tr id="row<%=sectionName%><c:out value='${VZAppBaseTest.binaryFirmwareId}'/>" style="display:none;">
                                                        <td colspan="9" style="border: 1px solid black;">
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                <tr >
                                                                    <td style="vertical-align:top; padding-left:0px;"><strong>Comments:</strong></td>

                                                                        <td style="vertical-align:top">
                                                                            <html:textarea readonly="true" styleClass="textareaField" property='<%="VZAppBaseTest["+baseIdx+"].baseComments"%>' onkeyup="LimitText(this,200)" onkeypress="LimitText(this,200)" rows="4" cols="70"></html:textarea>
                                                                            <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].baseComments"%>' />
                                                                        </td>

                                                                    <td style="vertical-align:top"><strong>Results File:</strong></td>
                                                                    <td style="vertical-align:top">
                                                                        <logic:notEmpty	name="VZAppBaseTest"	property="baseResultsFileFileName">
                                                                            <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBaseResultFile&vzAppBinaryFirmwareId=<c:out value="${VZAppBaseTest.binaryFirmwareId}"/>&app_id=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[baseIdx].vzAppZoneAppsId}"/>"	class="a"	target="_blank">
                                                                                <c:out value="${VZAppBaseTest.baseResultsFileFileName}" escapeXml="false"/>
                                                                            </a>
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
                                    <%-- todo add binary firmware phases --%>
                                </table></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                        <%  }//end if hasAccessDeviceTesting    %>
                        <%-- End Device Testing--%>
                    <%  } //end isEqualOrAboveTesting    %>

                    <%-- show OTA section only when atleast device test passed means application is in testing passed state --%>
                    <% if(isEqualOrAboveTestingPassed) { %>

                        <% if(hasAccessOTATesting) { %>
                            <%-- OTA Testing Section --%>
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
                                            %>
                                            <logic:iterate id="VZAppZoneOTATest" name="VZAppZoneApplicationUpdateForm" property="VZAppZoneOTATests" indexId="otaIdx">
                                                <c:if test="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].binaryFirmwareId==VZAppZoneOTATest.binaryFirmwareId}">
                                                    <tr>
                                                        <td style="vertical-align:top" onmouseover="return Tip('<b>Firmware:</b> <c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].firmwareName}"/><br/><b>Updated By:</b><br/><c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].lastUpdatedBy}"/><br/>(<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].lastUpdatedDate}"/>)');">
                                                            <b><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].phoneModel}'/></b>
                                                        </td>
                                                        <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].mrNumber}'/></td>
                                                        <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].binaryId}'/></td>
                                                        <td style="vertical-align:top"><c:out value='${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].binaryFirmwareStatusValue}'/></td>
                                                        <td style="vertical-align:top" nowrap>
                                                            <c:out value="${VZAppZoneOTATest.otaTestedDate}"/>&nbsp;
                                                        </td>
                                                        <td style="vertical-align:top" nowrap>
                                                            <logic:equal name="VZAppZoneOTATest" property="otaTestStatus" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_PASSED[0]%>">
                                                                <%=AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_PASSED[1]%>
                                                            </logic:equal>
                                                            <logic:equal name="VZAppZoneOTATest" property="otaTestStatus" value="<%=AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_FAILED[0]%>">
                                                                <%=AimsConstants.VZAPPZONE_APP_RADIO_OTA_TEST_FAILED[1]%>
                                                            </logic:equal>
                                                        </td>
                                                        <td nowrap style="vertical-align:top">
                                                            <span id="spnExpandCollapse<%=sectionName%><c:out value='${VZAppZoneOTATest.binaryFirmwareId}'/>" style="cursor:pointer;">
                                                                <a class="a" onclick="javascript:expandRow('<%=sectionName%>', <c:out value='${VZAppZoneOTATest.binaryFirmwareId}'/>)">[+]</a>
                                                            </span>
                                                        </td>
                                                    </tr>
                                                    <tr id="row<%=sectionName%><c:out value='${VZAppZoneOTATest.binaryFirmwareId}'/>" style="display:none;">
                                                        <td colspan="7" style="border: 1px solid black;">
                                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                <tr >
                                                                    <td style="vertical-align:top; padding-left:0px;"><strong>Comments:</strong></td>
                                                                    <td style="vertical-align:top">
                                                                        <html:textarea readonly="true" styleClass="textareaField" property='<%="VZAppZoneOTATest["+otaIdx+"].otaComments"%>' onkeyup="LimitText(this,200)" onkeypress="LimitText(this,200)" rows="4" cols="70"></html:textarea>
                                                                        <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].otaComments"%>' />
                                                                    </td>
                                                                    <td style="vertical-align:top"><strong>Results File:</strong></td>
                                                                    <td style="vertical-align:top">
                                                                        <logic:notEmpty	name="VZAppZoneOTATest"	property="otaResultsFileFileName">
                                                                            <logic:equal name="VZAppZoneOTATest" property="otaResultsFileTempFileId"	value="0">
                                                                                <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppOTAResultFile&vzAppBinaryFirmwareId=<c:out value="${VZAppZoneOTATest.binaryFirmwareId}"/>&app_id=<c:out value="${VZAppZoneApplicationUpdateForm.VZAppBinaryFirmwareInfoVOs[otaIdx].vzAppZoneAppsId}"/>"	class="a"	target="_blank">
                                                                                    <c:out value="${VZAppZoneOTATest.otaResultsFileFileName}" escapeXml="false"/>
                                                                                </a>
                                                                            </logic:equal>
                                                                        </logic:notEmpty>
                                                                        &nbsp;
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr><td colspan="7">&nbsp;</td></tr>
                                                </c:if>
                                            </logic:iterate>
                                        </logic:notEmpty>
                                    </logic:notEmpty>
                                </table></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <%-- End OTA Testing Section --%>
                        <% }//end hasAccessOTATesting %>
                    <%  }//end if isEqualOrAboveTestingPassed %>

                    <%  if(isEqualOrAboveOTATestPassed) {  %>
                        <%  if(hasAccessMoveToStaging) {  %>
                            <%-- Start Move to Staging Section --%>
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
                                        <th><strong>Device Name:</strong></th>
                                        <th><strong>MR Number:</strong></th>
                                        <th nowrap><strong>File Name and Version:</strong></th>
                                        <th><strong>Binary ID:</strong></th>
                                        <th><strong>Status:</strong></th>
                                        <th><strong>Date Moved:</strong></th>
                                    </tr>
                                    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppZoneStageInfoVOs">
                                        <logic:iterate id="VZAppZoneStageInfo" name="VZAppZoneApplicationUpdateForm" property="VZAppZoneStageInfoVOs" indexId="stgIdx">
                                            <tr>
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
                                                    <c:out value="${VZAppZoneStageInfo.stageMovedDate}"/>
                                                </td>
                                            </tr>
                                            <tr><td colspan="4">&nbsp;</td></tr>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </table></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                        <% }//end hasAccessMoveToStaging    %>
                        <%-- End Move to Staging Section --%>

                        <%  if(hasAccessMoveToProduction) {  %>
                            <%-- Start Move to Production Section --%>
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
                                        <th><strong>Device Name:</strong></th>
                                        <th><strong>MR Number:</strong></th>
                                        <th nowrap><strong>File Name and Version:</strong></th>
                                        <th><strong>Binary ID:</strong></th>
                                        <th><strong>Status:</strong></th>
                                        <th><strong>Date Moved:</strong></th>
                                    </tr>
                                    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppZoneProdInfoVOs">
                                        <logic:iterate id="VZAppZoneProdInfo" name="VZAppZoneApplicationUpdateForm" property="VZAppZoneProdInfoVOs" indexId="prodIdx">
                                            <tr>
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
                                                    <c:out value="${VZAppZoneProdInfo.prodMovedDate}"/>
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
                    <% }//end isEqualOrAboveOTATestPassed    %>

                    <%  if(isEqualOrAboveProduction) {  %>
                        <%  if(hasAccessMoveToSunset) {  %>
                            <%-- Move to Sunset --%>
                            <tr>
                                <td><div class="lBox">
                                    <div class="headLeftCurveblk"></div>
                                    <h1>Sunset</h1>
                                    <div class="headRightCurveblk"></div>
                                </div></td>
                            </tr>
                            <tr>
                                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                    <tr>
                                        <th class="text" valign="top">
                                            <strong>Sunset</strong>:&nbsp;
                                            <%  if(isSunset) { %> Yes
                                            <%  } else { %> No  <%  }%>
                                        </th>
                                    </tr>
                                </table></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                        <%}//end hasAccessMoveToSunset%>
                    <%}//end isEqualOrAboveProduction%>

                    <tr>
                        <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                                <td><%@ include  file="include/vzAppZoneViewButtons.jsp" %></td>
                            </tr>
                          </table></td>
                    </tr>
                </table>
            </div>
        </html:form>
    </div>
</div>
