<html:hidden property="tempForRadioIssue" />
<html:hidden property="task" />
<html:hidden property="aimsPlatformId"/>
<html:hidden property="aimsLifecyclePhaseId"/>
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
<html:hidden property="testPlanResultsFileName"	/>

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
<html:hidden property="testPlanResultsTempFileId"	/>

<%if (isBrewApp) {%>
	<html:hidden property="styleGuideFileName"	/>
	<html:hidden property="mktgSlickSheetFileName"	/>
	<html:hidden property="appLogoBwSmallFileName"	/>
	<html:hidden property="appLogoBwLargeFileName"	/>
	<html:hidden property="appLogoColorSmallFileName"	/>
	<html:hidden property="appLogoColorLargeFileName"	/>
	<html:hidden property="clrPubLogoFileName"	/>
	<html:hidden property="appTitleNameFileName"	/>
	<html:hidden property="brewPresentationFileName"	/>
    <html:hidden property="progGuideFileName"  />
	
	<html:hidden property="styleGuideTempFileId"	/>
	<html:hidden property="mktgSlickSheetTempFileId"	/>
	<html:hidden property="appLogoBwSmallTempFileId"	/>
	<html:hidden property="appLogoBwLargeTempFileId"	/>
	<html:hidden property="appLogoColorSmallTempFileId"	/>
	<html:hidden property="appLogoColorLargeTempFileId"	/>
	<html:hidden property="clrPubLogoTempFileId"	/>
	<html:hidden property="appTitleNameTempFileId"	/>
	<html:hidden property="brewPresentationTempFileId"	/>
    <html:hidden property="progGuideTempFileId"  />
    
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

<% } %>

<%if (isEnterpriseApp) {%>
	<html:hidden property="presentationFileName"	/>
	<html:hidden property="presentationTempFileId"	/>
	<html:hidden property="boboLetterOfAuthFileName"	/>
	<html:hidden property="boboLetterOfAuthTempFileId"	/>
	<html:hidden property="lbsContractFileName"	/>
	<html:hidden property="lbsContractTempFileId"	/>
	<html:hidden property="displayTabBOBO"/>
	<html:hidden property="displayTabLBS"/>
	<html:hidden property="isBoboAccept"/>
	<html:hidden property="isLbsAccept"/>
	<html:hidden property="isLbsAcceptByAlliance"/>
	<html:hidden property="acceptDeclinBoboLbs"/>
	<html:hidden property="applicationStatus"/>
	<html:hidden property="resubmitSolution"/>
	<html:hidden property="resubmitSolutionText"/>	
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
		<html:hidden property="title"	/>
		<html:hidden property="shortDesc"	/>
		<html:hidden property="longDesc"	/>
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
		<html:hidden property="devices"	/>
		<html:hidden property="isInterestedInBOBO"	/>
		<html:hidden property="isInterestedInLBS"	/>	
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
		<%if (!isPage5) {%>
			<html:hidden property="developerName"/>
			<html:hidden property="publisherName"/>
			<html:hidden property="sellingPoints"/>
			<html:hidden property="plannedDevStartDate"/>
			<html:hidden property="plannedEntryIntoNstl"/>
			<html:hidden property="plannedCompletionByNstl"	/>
			<html:hidden property="plannedSound"/>
            <html:hidden property="prizes"/>
			<html:hidden property="plannedVibration"/>
			<html:hidden property="networkUse"/>
			<html:hidden property="singleMultiPlayer"/>				
		<% } %>		
	<% } %>
	
	<%if (isEnterpriseApp) {%>
		<html:hidden property="fortuneCustomers"	/>
        <html:hidden property="customerBenefits"    />
		<html:hidden property="otherIndFocusValue"	/>
		<html:hidden property="isProductExeclusiveToVZW"/>
		<html:hidden property="productExclusiveToVzw"/>
		<html:hidden property="isGoExclusiveWithVZW"/>
		<html:hidden property="isProductUseVzwVzNt"/>
		<html:hidden property="isProductCertifiedVZW"/>
		<html:hidden property="productCerifiedPhase"/>
		<html:hidden property="isProductCertifiedODIProcess"/>
		<html:hidden property="productInformation"/>
		<html:hidden property="isProductRequiedLBS"/>
		<html:hidden property="isOfferBoboServices"/>
		<html:hidden property="briefDescription"/>
		<html:hidden property="solutionReside"/>
		<html:hidden property="additionalInformation"/>
		
		
		
		<logic:notEmpty name="EntApplicationUpdateForm" property="entProductInfo">
	        <logic:iterate id="entProductInfo" name="EntApplicationUpdateForm" property="entProductInfo">
	            <input type="hidden" name="entProductInfo" value="<bean:write name='entProductInfo'/>">
	        </logic:iterate>
    	</logic:notEmpty>
    	
    	<logic:notEmpty name="EntApplicationUpdateForm" property="entMarketSegInfo">
	        <logic:iterate id="entMarketSegInfo" name="EntApplicationUpdateForm" property="entMarketSegInfo">
	            <input type="hidden" name="entMarketSegInfo" value="<bean:write name='entMarketSegInfo'/>">
	        </logic:iterate>
    	</logic:notEmpty>
		
			
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
	
		<%if (isEnterpriseApp) {%>
			<html:hidden property="isPublished"/>
			<html:hidden property="isFeatured"/>
		<% }%>
		
		<%if ( (isBrewApp) && (!isPage3) ){%>	
			<html:hidden property="provisionApp"	/>
			<html:hidden property="provExpirtyDate"	/>
			<logic:notEmpty name="BrewApplicationUpdateForm" property="listSelectedRoles">
				<logic:iterate id="selRoleIds" name="BrewApplicationUpdateForm" property="listSelectedRoles" indexId="ctr">
					<input type="hidden" name="listSelectedRoles" value="<bean:write name="selRoleIds"/>"/>            	       	
          		</logic:iterate>
			</logic:notEmpty>
		<% }%>		
		
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
		
		<%if (hasAccessAppProcessBrewRelatedInfo) {%>
			
			<%if ( (isBrewApp) && (!isPage5) ){%>				
				<html:hidden property="deckPlacement"	/>
				<html:hidden property="anticipatedDaps"	/>	
			<% } %>			
			
		<% }%>		
		
	<% }%>
	
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

<%if(isPageEntBOBO){ %>
		<html:hidden property="boboLetterOfAuthTempFileId"/>
		<html:hidden property="boboLetterOfAuthFileName"/>
<%} %>

<%if(isPageEntLBS){ %>
		<html:hidden property="lbsContractTempFileId"/>
		<html:hidden property="lbsContractFileName"/>
<%} %>
