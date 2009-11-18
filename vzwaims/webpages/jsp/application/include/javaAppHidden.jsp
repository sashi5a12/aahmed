<html:hidden property="appsId"/>
<html:hidden property="cloneFromAppId"/>
<html:hidden property="ringNumber"/>
<html:hidden property="ring2App"/>
<html:hidden property="ring3App"/>

<html:hidden property="stepToValidate"/>

<html:hidden property="submittedDate"/>
<html:hidden property="aimsUserAppCreatedById"/>
<html:hidden property="aimsLifecyclePhaseId"/>
<html:hidden property="applicationStatus"/>
<html:hidden property="aimsAllianceId"/>
<html:hidden property="vendorId"/>

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

<html:hidden property="enterpriseApp"/>
<%
System.out.println("===========================================================");
System.out.println("===========================================================");
System.out.println("===========================================================");
System.out.println("1" + isPage1);
System.out.println("2" + isPage2);
System.out.println("3" + isPage3);
System.out.println("4" + isPage4);
System.out.println("5" + isPage5);
System.out.println("6" + isVerizonUser);
System.out.println("===========================================================");
System.out.println("===========================================================");
System.out.println("===========================================================");

if (!isPage1) 
{
%>
	<%
		if (!isPage5)
		{
	%>
		<html:hidden property="title"	/>
	<% 
		} 
	%>

	<html:hidden property="version"/>
	<html:hidden property="infoURL"/>
	<html:hidden property="ifPrRelease"/>
	<html:hidden property="shortDesc"/>
	<html:hidden property="longDesc"/>
	
	<%
		if (!isPage3) 
		{
	%>
		<html:hidden property="aimsAppCategoryId"/>	
		<html:hidden property="aimsAppSubCategoryId"/>
		<html:hidden property="javaAppContractId"/>
		<html:hidden property="appKeyword"/>
		<html:hidden property="contentRating"/>		
		
		<html:hidden property="actionComments"/>
				
	<%
		} 
	%>	    
<% 
} 
%>

<%
if (!isPage2) 
{
%>
	
	<html:hidden property="aimsContactId"/>    
	<html:hidden property="contactFirstName"/>
	<html:hidden property="contactLastName"/>
	<html:hidden property="contactTitle"/>
	<html:hidden property="contactEmail"/>
	<html:hidden property="contactPhone"/>
	<html:hidden property="contactMobile"/>	
    
<% 
} 
%>

<%
if (isAllianceUser) 
{
%>
	<%
		if (!isPage3) 
		{
	%>
		<html:hidden property="contentType"/>
	<% 
		} 
	%>	
<% 
} 
%>

<%
if (isVerizonUser) 
{
%>	
	<%
		if (!isPage3) 
		{
	%>
			<html:hidden property="appCategory1"/>
			<html:hidden property="appSubCategory1"/>
			<html:hidden property="appCategory2"/>
			<html:hidden property="appSubCategory2"/>
			<html:hidden property="contentType"/>
			<html:hidden property="projectedLiveDate"/>
			<html:hidden property="enterpriseId"/>
			<html:hidden property="initialApprovalNotes"/>
			<html:hidden property="initialApprovalAction"/>
			<html:hidden property="contentZipFileNotes"/>
			<html:hidden property="contentZipFileAction"/>
			<html:hidden property="moveToProduction"/>
			<html:hidden property="remove"/>
			
			<html:hidden property="aimsTaxCategoryCodeId"/>		
			<html:hidden property="selectedAction"/>
			
			<html:hidden property="javaAppContractId"/>
	<% 
		}
	%>
	
	<%
		if (!isPage4) 
		{
	%>
			<html:hidden property="journalType"/>
			<html:hidden property="journalText"/>
			<html:hidden property="journalCombinedText"/>	
	<% 
		}
	%>
		
<% 
} 
%>

<%
	if (!isPage3) 
	{
%>
		<html:hidden property="isContentZipFileLock"/>
<% 
	} 
%>

<%
	if (!isPage5) 
	{
%>	
		<html:hidden property="productDescription"	/>
<%
	} 
%>