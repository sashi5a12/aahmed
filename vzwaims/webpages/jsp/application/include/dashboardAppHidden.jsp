<html:hidden property="appsId"/>
<html:hidden property="submittedDate"/>
<html:hidden property="aimsUserAppCreatedById"/>
<html:hidden property="aimsLifecyclePhaseId"/>
<html:hidden property="applicationStatus"/>
<html:hidden property="aimsAllianceId"/>
<html:hidden property="allianceName"/>
<html:hidden property="task"/>
<html:hidden property="originalTask"/>
<html:hidden property="appSubmitType"/>
<html:hidden property="currentPage"/>
<html:hidden property="setupURL"/>
<html:hidden property="updateURL"/>
    
<html:hidden property="clrPubLogoFileName"/>
<html:hidden property="appTitleNameFileName"/>
<html:hidden property="splashScreenEpsFileName"/>    
<html:hidden property="activeScreenEpsFileName"/>
<html:hidden property="screenJpegFileName"/>
<html:hidden property="screenJpeg2FileName"/>
<html:hidden property="screenJpeg3FileName"/>
<html:hidden property="screenJpeg4FileName"/>
<html:hidden property="screenJpeg5FileName"/>
<html:hidden property="faqDocFileName"/>
<html:hidden property="userGuideFileName"/>
<html:hidden property="contentZipFileFileName"/>
<html:hidden property="companyLogoFileName"  />
<html:hidden property="titleImageFileName"  />

<html:hidden property="clrPubLogoTempFileId"/>
<html:hidden property="appTitleNameTempFileId"/>
<html:hidden property="splashScreenEpsTempFileId"/>
<html:hidden property="activeScreenEpsTempFileId"/>
<html:hidden property="screenJpegTempFileId"/>
<html:hidden property="screenJpeg2TempFileId"/>
<html:hidden property="screenJpeg3TempFileId"/>
<html:hidden property="screenJpeg4TempFileId"/>
<html:hidden property="screenJpeg5TempFileId"/>
<html:hidden property="faqDocTempFileId"/>
<html:hidden property="userGuideTempFileId"/>
<html:hidden property="contentZipFileTempFileId"/>
<html:hidden property="companyLogoTempFileId"  />
<html:hidden property="titleImageTempFileId"  />

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
<html:hidden property="isNewContentZipFileUploaded"	/>

<html:hidden property="selectedDevicesAlertMessage"/>


<%if (!isPage1) {%>
	<%if (!isPage5){%>
		<html:hidden property="title"	/>
	<% } %>
	<html:hidden property="channelVersion"/>
	<html:hidden property="channelType"/>
	<html:hidden property="language"/>
	<html:hidden property="channelSize"/>
	<html:hidden property="shortDesc"/>
	<html:hidden property="longDesc"/>
	<html:hidden property="vendorProductDisplay"/>	
	<html:hidden property="vzwRetailPrice"/>
	<html:hidden property="aimsAppCategoryId"/>	
	<html:hidden property="aimsAppSubCategoryId"/>
		
	<logic:notEmpty name="DashboardApplicationUpdateForm" property="listSelectedDevices">
        <logic:iterate id="device" name="DashboardApplicationUpdateForm" property="listSelectedDevices">
            <input type="hidden" name="listSelectedDevices" value="<bean:write name='device'/>">
        </logic:iterate>
    </logic:notEmpty>	
    
<% } %>

<%if (!isPage2) {%>
	
	<html:hidden property="channelDeployments"/>
	<html:hidden property="aimsContactId"/>    
	<html:hidden property="contactFirstName"/>
	<html:hidden property="contactLastName"/>
	<html:hidden property="contactTitle"/>
	<html:hidden property="contactEmail"/>
	<html:hidden property="contactPhone"/>
	<html:hidden property="contactMobile"/>	
	<html:hidden property="developerName"/>
	<html:hidden property="publisherName"/>	
	<html:hidden property="sellingPoints"/>	
	<html:hidden property="plannedDevStartDate"/>	
    
<% } %>

<%if (isVerizonUser) {%>
	
	<%if (!isPage3) {%>
		<html:hidden property="initialApprovalNotes"/>
		<html:hidden property="initialApprovalAction"/>
		<html:hidden property="channelId"/>
		<html:hidden property="contentZipFileNotes"/>
		<html:hidden property="contentZipFileAction"/>
		<html:hidden property="moveToProduction"/>
		<html:hidden property="remove"/>
	<% }%>
	
	<%if (!isPage4) {%>
		<html:hidden property="journalType"/>
		<html:hidden property="journalText"/>
		<html:hidden property="journalCombinedText"/>	
	<% }%>
		
<% } %>

<%if (!isPage3) {%>
	<html:hidden property="isContentZipFileLock"/>
<% } %>

<%if (!isPage5) {%>	
	<html:hidden property="productDescription"	/>
<%} %>