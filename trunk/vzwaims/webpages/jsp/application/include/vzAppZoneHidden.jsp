<html:hidden property="tempForRadioIssue" />
<html:hidden property="task" />
<html:hidden property="orignalTask" />
<html:hidden property="currentPage"	/>

<html:hidden property="appSubmitType"	/>
<html:hidden property="appsId" />
<html:hidden property="appTitleToView" />
<html:hidden property="isAppLocked" />
<html:hidden property="appOwnerAllianceId" />

<%-- clone --%>
<html:hidden property="clonedFromId" />
<html:hidden property="clonedFromTitle" />

<html:hidden property="aimsLifecyclePhaseId"/>
<%-- header variables --%>
<html:hidden property="applicationStatus" />
<html:hidden property="allianceName" />
<html:hidden property="vendorId" />

<%-- commented, contentLandingScreenShot no need to show
    <html:hidden property="contentLandingScreenShotFileName"  />
    <html:hidden property="contentLandingScreenShotTempFileId"  />
--%>

<html:hidden property="presentationFileName"  />
<html:hidden property="presentationTempFileId"  />

<html:hidden property="binaryFileFileName"  />
<html:hidden property="binaryFileTempFileId"  />

<html:hidden property="previewFileFileName"  />
<html:hidden property="previewFileTempFileId"  />

<html:hidden property="documentFileFileName"  />
<html:hidden property="documentFileTempFileId"  />


<%--page1 basic info fields--%>
<%if(!isPage1 || (isPage1 && isLocked)) {  %>
    <html:hidden property="appTitle"/>
    <html:hidden property="appVersion"/>
    <%-- commented, catalog name and product code, no need to show
        <html:hidden property="appCatalogName"/>
        <html:hidden property="appProductCode"/>
    --%>
    <html:hidden property="appShortDesc"/>
    <html:hidden property="appLongDesc"/>
    <html:hidden property="goLiveDate"/>
    <html:hidden property="expirationDate"/>
    <html:hidden property="contentRating"/>
    <html:hidden property="ifPrRelease"/>
<% }//end if !page1 %>

<%if (!isPage2 || (isPage2 && isLocked)) {%>
    <html:hidden property="aimsContactId"	/>
	<html:hidden property="contactFirstName"	/>
	<html:hidden property="contactLastName"	/>
	<html:hidden property="contactTitle"	/>
	<html:hidden property="contactEmail"	/>
	<html:hidden property="contactPhone"	/>
	<html:hidden property="contactMobile"	/>

    <html:hidden property="communityChatUgc"	/>
	<html:hidden property="contentSweekstakes"	/>
<% }//end if !page2 %>

<%-- binary page --%>
<%
    if (!isPage3) {
%>
    <html:hidden property="binaryId"/>
    <html:hidden property="binaryVersion"/>
    <html:hidden property="binaryFileSize"/>
    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="vzAppDeviceIds">
        <logic:iterate name="VZAppZoneApplicationUpdateForm" id="vzAppDeviceId" property="vzAppDeviceIds">
            <input type="hidden" name="vzAppDeviceIds" value="<bean:write	name='vzAppDeviceId'/>"/>
        </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="vzAppFirmwareIds">
        <logic:iterate name="VZAppZoneApplicationUpdateForm" id="firmwareId" property="vzAppFirmwareIds">
            <input type="hidden" name="vzAppFirmwareIds" value="<bean:write	name='firmwareId'/>"/>
        </logic:iterate>
    </logic:notEmpty>
<%
    }//end page 3
%>
<%-- end binary page--%>


<%if (!isProcessingInfo) { %>
    <% if(isVerizonUser) { %>
        <% if(hasAccessInitialApproval) { %>
            <html:hidden property="subsVZWRecommendedPrice"/>
            <html:hidden property="subsVendorSplitPercentage"/>
            <html:hidden property="subsVendorProductDisplay"/>
            <html:hidden property="oneTimeVZWRecommendedPrice"/>
            <html:hidden property="oneTimeVendorSplitPercentage"/>
            <html:hidden property="oneTimeVendorProductDisplay"/>

            <html:hidden property="categoryId2"/>
            <html:hidden property="subCategoryId2"/>
            <html:hidden property="categoryId3"/>
            <html:hidden property="subCategoryId3"/>

            <%-- commented, scmVendorId, no need to show
                <html:hidden property="scmVendorId"  />
            --%>
            <html:hidden property="vzwLiveDate"  />
            <html:hidden property="taxCategoryCodeId"  />
            <html:hidden property="initialApprovalNotes"  />
            <html:hidden property="initialApprovalAction"  />
            <html:hidden property="contentType"/>
        <% } %>
                
    <% }//end if verizonuser %>
<% }//end if !processing Info %>

<%-- binaryFirmwareInfoList and all other xxxSectionVOList must have same size --%>
<%-- hidden fields for binaryFirmwareInfo to show binary firmeware info labels in testing section --%>
<% if (isVerizonUser && isEqualOrAboveTesting) {   %>
    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppBinaryFirmwareInfoVOs">
        <logic:iterate name="VZAppZoneApplicationUpdateForm" id="VZAppBinaryFirmwareInfo" property="VZAppBinaryFirmwareInfoVOs" indexId="binaryFirmwareIdx">
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].vzAppZoneAppsId"%>'/>
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].binaryFirmwareId"%>'/>
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].phoneModel"%>'/>
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].firmwareName"%>'/>
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].mrNumber"%>'/>
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].binaryFileFileName"%>'/>
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].binaryVersion"%>'/>
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].isPaid"%>'/>
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].binaryId"%>'/>
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].binaryFirmwareStatusValue"%>'/>
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].lastUpdatedBy"%>'/>
            <html:hidden property='<%="VZAppBinaryFirmwareInfo["+binaryFirmwareIdx+"].lastUpdatedDate"%>'/>
        </logic:iterate>
    </logic:notEmpty>
<% }//end isVerizonUser && isEqualOrAboveTesting%>

<%-- Testing section info --%>
<% if(hasAccessBaseTesting) { %>
<logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppBaseTests">
    <logic:iterate name="VZAppZoneApplicationUpdateForm" id="VZAppBaseTest" property="VZAppBaseTests" indexId="baseIdx">
        <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].binaryFirmwareId"%>'/>
        <%
            if (!isProcessingInfo) {
        %>
            <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].baseTestedDate"%>'/>
            <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].baseTestStatus"%>'/>
            <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].baseComments"%>'/>
        <%	}//end if !isProcessingInfo %>
        <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].baseResultsFileFileName"%>'/>
        <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].baseResultsFileTempFileId"%>'/>
        <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].baseResultsFileContentType"%>'/>
        <html:hidden property='<%="VZAppBaseTest["+baseIdx+"].disableBaseTest"%>'/>
    </logic:iterate>
</logic:notEmpty>
<% }//end hasAccessBaseTesting %>

<%-- OTA Testing section info --%>
<% if(isEqualOrAboveTestingPassed && hasAccessOTATesting) { %>
<logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppZoneOTATests">
    <logic:iterate name="VZAppZoneApplicationUpdateForm" id="VZAppZoneOTATest" property="VZAppZoneOTATests" indexId="otaIdx">
        <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].binaryFirmwareId"%>'/>
        <%
            if (!isProcessingInfo) {
        %>
            <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].otaTestedDate"%>'/>
            <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].otaTestStatus"%>'/>
            <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].otaComments"%>'/>
            <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].moveToOTATesting"%>'/>
        <%	}//end if !isProcessingInfo %>

        <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].otaResultsFileFileName"%>'/>
        <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].otaResultsFileTempFileId"%>'/>
        <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].otaResultsFileContentType"%>'/>
        <%--<html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].inOTATesting"%>'/>--%>
        <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].disableMoveToOTATesting"%>'/>
        <html:hidden property='<%="VZAppZoneOTATest["+otaIdx+"].disableOTATest"%>'/>
    </logic:iterate>
</logic:notEmpty>
<% }//end hasAccessBaseTesting %>

<%-- Stage section info --%>
<% if(isEqualOrAboveOTATestPassed && hasAccessMoveToStaging) { %>
<logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppZoneStageInfoVOs">
    <logic:iterate name="VZAppZoneApplicationUpdateForm" id="VZAppZoneStageInfo" property="VZAppZoneStageInfoVOs" indexId="stageIdx">
        <html:hidden property='<%="VZAppZoneStageInfo["+stageIdx+"].binaryFirmwareId"%>'/>
        <html:hidden property='<%="VZAppZoneStageInfo["+stageIdx+"].stageMovedDate"%>'/>
        <html:hidden property='<%="VZAppZoneStageInfo["+stageIdx+"].disableMoveToStaging"%>'/>
        <%
            if (!isProcessingInfo) {
        %>
            <html:hidden property='<%="VZAppZoneStageInfo["+stageIdx+"].moveToStaging"%>'/>
        <%	}//end if !isProcessingInfo %>

    </logic:iterate>
</logic:notEmpty>
<% }//end isEqualOrAboveOTATestPassed %>

<%-- Prod section info --%>
<% if(isEqualOrAboveOTATestPassed && hasAccessMoveToProduction) { %>
<logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="VZAppZoneProdInfoVOs">
    <logic:iterate name="VZAppZoneApplicationUpdateForm" id="VZAppZoneProdInfo" property="VZAppZoneProdInfoVOs" indexId="prodIdx">
        <html:hidden property='<%="VZAppZoneProdInfo["+prodIdx+"].binaryFirmwareId"%>'/>
        <html:hidden property='<%="VZAppZoneProdInfo["+prodIdx+"].prodMovedDate"%>'/>
        <html:hidden property='<%="VZAppZoneProdInfo["+prodIdx+"].disableMoveToProd"%>'/>
        <%
            if (!isProcessingInfo) {
        %>
            <html:hidden property='<%="VZAppZoneProdInfo["+prodIdx+"].moveToProd"%>'/>
        <%	}//end if !isProcessingInfo %>

    </logic:iterate>
</logic:notEmpty>
<% }//end isEqualOrAboveOTATestPassed %>


<%-- sub category and category other than basic info and processing info pages --%>
<% if ( (!isPage1 || (isPage1 && isLocked)) &&  ( (!isProcessingInfo) || ( isProcessingInfo && (!hasAccessInitialApproval) )) ) { %>
    <html:hidden property="categoryId1"/>
    <html:hidden property="subCategoryId1"/>
<% }  %>

<%-- billing fields, additional info and processing info --%>
<% if ( (!isPage2 || (isPage2 && isLocked)) &&  ( (!isProcessingInfo) || ( isProcessingInfo && (!hasAccessInitialApproval) )) ) { %>
    <html:hidden property="subscriptionBillingMonthly"/>
    <html:hidden property="subscriptionBillingPricePoint"/>
    <html:hidden property="oneTimeBilling"/>
    <html:hidden property="oneTimeBillingPricePoint"/>
<% }  %>

<%-- for VZW Test user --%>
<%-- if (isVerizonUser &&  ((!isProcessingInfo) || ( isProcessingInfo && (!hasAccessInitialApproval) )))  { --%>
<%-- if (!isProcessingInfo && hasAccessInitialApproval )  { %>
        <html:hidden property="subsVZWRecommendedPrice"/>
        <html:hidden property="subsVendorSplitPercentage"/>
        <html:hidden property="subsVendorProductDisplay"/>
        <html:hidden property="oneTimeVZWRecommendedPrice"/>
        <html:hidden property="oneTimeVendorSplitPercentage"/>
        <html:hidden property="oneTimeVendorProductDisplay"/>

        <html:hidden property="categoryId2"/>
        <html:hidden property="subCategoryId2"/>
        <html:hidden property="categoryId3"/>
        <html:hidden property="subCategoryId3"/>

        <html:hidden property="scmVendorId"/>
        <html:hidden property="vzwLiveDate"/>
        <html:hidden property="taxCategoryCodeId"/>
        <html:hidden property="contentType"/>
<% }  --%>

<%-- mportalAllianceName is a readonly field --%>
<% if (isVerizonUser &&  hasAccessInitialApproval)  { %>
    <html:hidden property="mportalAllianceName"/>
<% }  %>


<% if ((!isProcessingInfo) || ( isProcessingInfo && (!hasAccessApplicationManagement) ))  { %>
    <html:hidden property="isLocked"/>
<% }  %>

<% if (isVerizonUser &&  ((!isProcessingInfo) || ( isProcessingInfo && (!hasAccessMoveToSunset) )))  { %>
    <html:hidden property="moveToSunset"/>
<% }  %>