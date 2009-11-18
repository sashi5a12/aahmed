<html:hidden property="tempForRadioIssue" />
<html:hidden property="task" />
<html:hidden property="aimsPlatformId"/>
<html:hidden property="aimsLifecyclePhaseId"/>
<html:hidden property="applicationStatus"/>
<html:hidden property="appsId" />
<html:hidden property="submittedDate" />
<html:hidden property="appSubmitType"	/>
<html:hidden property="currentPage"	/>
<html:hidden property="setupURL"	/>
<html:hidden property="updateURL"	/>

<html:hidden property="splashScreenEpsFileName"	/>
<html:hidden property="activeScreenEpsFileName"	/>
<html:hidden property="screenJpegFileName"	/>
<html:hidden property="screenJpeg2FileName"	/>
<html:hidden property="screenJpeg3FileName"	/>
<html:hidden property="screenJpeg4FileName"	/>
<html:hidden property="screenJpeg5FileName"	/>
<html:hidden property="flashDemoFileName"	/>
<html:hidden property="userGuideFileName"	/>
<html:hidden property="faqDocFileName"	/>

<html:hidden property="splashScreenEpsTempFileId"	/>
<html:hidden property="activeScreenEpsTempFileId"	/>
<html:hidden property="screenJpegTempFileId"	/>
<html:hidden property="screenJpeg2TempFileId"	/>
<html:hidden property="screenJpeg3TempFileId"	/>
<html:hidden property="screenJpeg4TempFileId"	/>
<html:hidden property="screenJpeg5TempFileId"	/>
<html:hidden property="flashDemoTempFileId"	/>
<html:hidden property="userGuideTempFileId"	/>
<html:hidden property="faqDocTempFileId"	/>


<%if (isBrewApp) {%>
	<html:hidden property="appTitleNameFileName"	/>
    <html:hidden property="progGuideFileName"  />
    <html:hidden property="companyLogoFileName"  />
    <html:hidden property="titleShotFileName"  />

    <html:hidden property="highResActiveFileName"  />
    <html:hidden property="highResSplashFileName"  />
    <html:hidden property="splashScreenFileName"  />
    <html:hidden property="activeScreen1FileName"  />
    <html:hidden property="activeScreen2FileName"  />
    <html:hidden property="smallSplashFileName"  />    
    <html:hidden property="smlActiveScreenFileName"  />
    <html:hidden property="mediaStoreFileName"  />
    <html:hidden property="appGifActionFileName"  />
    <html:hidden property="appActiionFlashFileName"  />
    <html:hidden property="userGuidePdfFileName"  />
	<html:hidden property="flashDemoMovieFileName"	/>
	<html:hidden property="dashboardScrImgFileName"	/>

	<html:hidden property="appTitleNameTempFileId"	/>
    <html:hidden property="progGuideTempFileId"  />
    <html:hidden property="companyLogoTempFileId"  />
    <html:hidden property="titleShotTempFileId"  />

    <html:hidden property="highResSplashTempFileId"	/>    
    <html:hidden property="highResActiveTempFileId"	/>
    <html:hidden property="splashScreenTempFileId"	/>
    <html:hidden property="smallSplashTempFileId"	/>
    <html:hidden property="activeScreen1TempFileId"	/>
    <html:hidden property="activeScreen2TempFileId"	/>
    <html:hidden property="smlActiveScreenTempFileId"	/>
    <html:hidden property="appActiionFlashTempFileId"	/>
    <html:hidden property="appGifActionTempFileId"	/>
    <html:hidden property="mediaStoreTempFileId"	/>
    <html:hidden property="flashDemoMovieTempFileId"	/>
    <html:hidden property="dashboardScrImgTempFileId"	/>
    
    
    <html:hidden property="appType" />
    <html:hidden property="appTypeFullName" />
    
    <html:hidden property="conceptId"  />
    <html:hidden property="conceptTitle"  />
    <html:hidden property="conceptEvaluationDate"  />
    
    <html:hidden property="isConcept"  />
    <html:hidden property="selectedDevicesAlertMessage"  />
    
    <html:hidden property="lbsClientId"  />
    <html:hidden property="lbsSecretKey"  />
    <html:hidden property="lbsAutodeskPhaseId"  />
    <html:hidden property="lbsAutodeskPhaseName"  />
    
    <html:hidden property="disclaimerText"	/>
	<html:hidden property="usingApplication"	/>
	<html:hidden property="tipsAndTricks"	/>
	<html:hidden property="faq"	/>
	<html:hidden property="troubleshooting"	/>
	<html:hidden property="devCompanyDisclaimer"	/>
	<html:hidden property="additionalInformation"	/>
	
	<html:hidden property="usingApplicationLen"	/>
	<html:hidden property="tipsAndTricksLen"	/>
	<html:hidden property="faqLen"	/>
	<html:hidden property="troubleshootingLen"	/>
	<html:hidden property="devCompanyDisclaimerLen"	/>
	<html:hidden property="additionalInformationLen"	/>
	<html:hidden property="airTimeLen"	/>

    
<% } else {%>
	<html:hidden property="testPlanResultsFileName"	/>
	<html:hidden property="testPlanResultsTempFileId"	/>
<%} %>

<%if (isEnterpriseApp) {%>
	<html:hidden property="presentationFileName"	/>
	<html:hidden property="presentationTempFileId"	/>
<% } %>

<%if (isMmsApp) {%>
	<html:hidden property="sampleContentFileName"	/>
	<html:hidden property="sampleContentTempFileId"	/>
<% } %>

<%if (isSmsApp) {%>
	<html:hidden property="messageFlowFileName"	/>
	<html:hidden property="messageFlowTempFileId"	/>
<% } %>





<%if (!isPage1) {%>
	<%if ( (!isBrewApp) || ((isBrewApp) && (!isPage5)) ){%>
		<html:hidden property="shortDesc"	/>
		<html:hidden property="longDesc"	/>
	<% } %>
	<%if ( (!isBrewApp) || ((isBrewApp) && (!isPage5) && (!isPage7)) ){%>
		<html:hidden property="title"	/>
	<% } %>	
    <html:hidden property="networkUsage"/>
    <logic:notEmpty name="ApplicationUpdateForm" property="applicationURLs">
        <logic:iterate id="applicationURL" name="ApplicationUpdateForm" property="applicationURLs">
            <input type="hidden" name="applicationURLs" value="<bean:write name='applicationURL'/>">
        </logic:iterate>
    </logic:notEmpty>
    <html:hidden property="aimsAppCategoryId"	/>
	<html:hidden property="aimsAppSubCategoryId"	/>
	<html:hidden property="ifPrRelease"	/>
	<html:hidden property="version"/>
	<html:hidden property="language"/>	

	<%if (isBrewApp) {%>
        <html:hidden property="contentRating"/>
        <html:hidden property="appSize"/>
		<html:hidden property="isLbs"/>
        <html:hidden property="lbsAppType"  />
        <logic:notEmpty name="BrewApplicationUpdateForm" property="listSelectedGeoServices">
            <logic:iterate id="selGeoServiceIds" name="BrewApplicationUpdateForm" property="listSelectedGeoServices" indexId="ctr">
                <input type="hidden" name="listSelectedGeoServices" value="<bean:write name="selGeoServiceIds"/>"/>                     
            </logic:iterate>
        </logic:notEmpty>
	<% } %>
	
	<%if (isEnterpriseApp) {%>
		<html:hidden property="custSupportPhone"	/>
		<html:hidden property="custSupportEmail"	/>
		<html:hidden property="custSupportHours"	/>
		<html:hidden property="totalEndUsers"	/>
		<html:hidden property="noOfUsersAccess"	/>
		<html:hidden property="platformDepMode"	/>		
	<% } %>
	
	<%if (isMmsApp) {%>
		<html:hidden property="contentProvider"	/>
		<html:hidden property="expectedMsgTraffic"	/>
		<html:hidden property="targetedLaunchDate"	/>	
	<% } %>
	
	<%if (isSmsApp) {%>
		<html:hidden property="contentProvider"	/>
		<html:hidden property="campaignStartDate"/>
		<html:hidden property="campaignEndDate"	/>
		<html:hidden property="avgMsgsPerSec"	/>
		<html:hidden property="peakMsgsPerSec"	/>
		<html:hidden property="campaignClassification"	/>
		<html:hidden property="wholesalePrice"	/>
		<html:hidden property="retailPrice"	/>		
	<% } %>
	
	<%if (isWapApp) {%>
		<html:hidden property="wapUrl"	/>		
	<% } %>
	
<% } %>



<%if (!isPage2) {%>
	<html:hidden property="appDeployments"	/>
	<html:hidden property="aimsContactId"	/>
	<html:hidden property="contactFirstName"	/>
	<html:hidden property="contactLastName"	/>
	<html:hidden property="contactTitle"	/>
	<html:hidden property="contactEmail"	/>
	<html:hidden property="contactPhone"	/>
	<html:hidden property="contactMobile"	/>
	
	<%if (isBrewApp) {%>
		<html:hidden property="shortCode"	/>
		<html:hidden property="keyword"	/>
		<html:hidden property="aggregator"	/>
		<%if (!isPage5) {%>
			<html:hidden property="developerName"/>
			<html:hidden property="publisherName"/>
			<html:hidden property="sellingPoints"/>
			<html:hidden property="plannedDevStartDate"/>
			<html:hidden property="plannedEntryIntoNstl"/>
			<html:hidden property="plannedCompletionByNstl"	/>
            <html:hidden property="prizes"/>
            <html:hidden property="ugcChat"/>
            <html:hidden property="chatPrize"/>
			<html:hidden property="networkUse"/>
			<html:hidden property="singleMultiPlayer"/>				
		<% } %>		
	<% } %>
	
	<%if (isEnterpriseApp) {%>
		<html:hidden property="fortuneCustomers"	/>
        <html:hidden property="customerBenefits"    />
		<html:hidden property="otherIndFocusValue"	/>
	<% } %>
	
	<%if (isMmsApp) {%>
		<html:hidden property="programPromoInfo"	/>
	<% } %>
	
	<%if (isSmsApp) {%>
		<html:hidden property="carrierSupport"	/>
		<html:hidden property="ifVzwDirectConn"	/>
		<html:hidden property="connType"	/>
		<html:hidden property="shortCodesReqd"	/>
		<html:hidden property="campaignPromoInfo"/>
		<html:hidden property="campaignDesc"	/>
	<% } %>
	
	<%if (isWapApp) {%>
		<html:hidden property="launchDate"	/>
		<html:hidden property="numSuppResources"	/>
	<% } %>
	
<% } %>


<%if (isVerizonUser) {%>
	
	<%if (!isPage3) {%>
				
		<%if (hasAccessAppProcessAssign) {%>
			<html:hidden property="aimsVzwAppsContactId"	/>
			<html:hidden property="aimsProductGroupId"	/>

			<%if (isEnterpriseApp) {%>
				<html:hidden property="allianceSponsor"	/>				
			<% } %>
			
		<% }%>
		
		<%if (hasAccessAppProcessPrioritization) {%>
			<html:hidden property="appPriority"	/>
			<html:hidden property="targetedProductionDate"	/>
		<% }%>
							
	<% }%>

	<%if ( (isBrewApp) && (!isPage5) ){%>				
		<html:hidden property="deckPlacement"	/>
		<html:hidden property="anticipatedDaps"	/>	
	<% } %>			
	
	<%if (!isPage4) {%>
		<html:hidden property="journalCombinedText"	/>
		<html:hidden property="journalText"	/>
		<html:hidden property="journalType"	/>
	<% }%>
	
	<%if (!isPage5) {%>
		<%if (isBrewApp) {%>
			<html:hidden property="plannedEntryIntoVzw"	/>
			<html:hidden property="plannedCompletionByVzw"	/>
			<html:hidden property="evaluation"	/>
			<html:hidden property="deckLaunchDate"	/>
		<% }%>
	<% }%>
	
<% } %>



<%if (!isPage7) {%>
	<%if (isBrewApp) {%>		
		<html:hidden property="multiPlayer"	/>
		<html:hidden property="productDescription"	/>
	<%} %>
<%} %>