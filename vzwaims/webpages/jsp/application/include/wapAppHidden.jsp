<html:hidden property="tempForRadioIssue" />
<html:hidden property="task" />
<html:hidden property="orignalTask" />
<html:hidden property="aimsLifecyclePhaseId"/>
<html:hidden property="appsId" />
<html:hidden property="appOwnerAllianceId" />
<html:hidden property="applicationStatus" />
<html:hidden property="allianceName" />
<html:hidden property="vendorId" />
<html:hidden property="submittedDate" />
<html:hidden property="appSubmitType"	/>
<html:hidden property="currentPage"	/>
<html:hidden property="clonedFromId" />
<html:hidden property="clonedFromTitle" />
<html:hidden property="rolledBackToPendingDcr" />

<html:hidden property="productLogoFileName"  />
<html:hidden property="productIconFileName"  />
<html:hidden property="screenJpegFileName"	/>
<html:hidden property="userGuideFileName"	/>
<html:hidden property="faqDocFileName"	/>
<html:hidden property="presentationFileName"  />
<html:hidden property="appMediumLargeImageFileName"  />
<html:hidden property="appQVGAPotraitImageFileName"  />
<html:hidden property="appQVGALandscapeImageFileName"  />


<html:hidden property="productLogoTempFileId"  />
<html:hidden property="productIconTempFileId"  />
<html:hidden property="screenJpegTempFileId"	/>
<html:hidden property="userGuideTempFileId"	/>
<html:hidden property="faqDocTempFileId"	/>
<html:hidden property="presentationTempFileId"  />
<html:hidden property="appMediumLargeImageTempFileId"  />
<html:hidden property="appQVGAPotraitImageTempFileId"  />
<html:hidden property="appQVGALandscapeImageTempFileId"  />

<%if (isVerizonUser) {%>
    <%if (hasAccessPendingDcr) {%>
        <html:hidden property="pendingDcrVersion"  />
        <logic:notEmpty name="WapApplicationUpdateForm" property="wapAppVersions">
            <logic:iterate id="wapAppVersionIds" name="WapApplicationUpdateForm" property="wapAppVersions" indexId="ctr">
                <input type="hidden" name="wapAppVersions" value="<bean:write name="wapAppVersionIds"/>"/>                     
            </logic:iterate>
        </logic:notEmpty>
    <% }%>
    
    <%if (hasAccessContentTesting) {%>
        <logic:notEmpty name="WapApplicationUpdateForm" property="testId">
             <logic:iterate id="ids" name="WapApplicationUpdateForm" property="testId" indexId="ctr">
                 <input type="hidden" name="testId" value="<bean:write name="ids"/>"/>                     
             </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="WapApplicationUpdateForm" property="testName">
            <logic:iterate id="names" name="WapApplicationUpdateForm" property="testName" indexId="ctr">
                <input type="hidden" name="testName" value="<bean:write name="names"/>"/>                     
            </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="WapApplicationUpdateForm" property="testResultFileName">
            <logic:iterate id="fileNames" name="WapApplicationUpdateForm" property="testResultFileName" indexId="ctr">
                <input type="hidden" name="testResultFileName" value="<bean:write name="fileNames"/>"/>                     
            </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="WapApplicationUpdateForm" property="testResultFileId">
            <logic:iterate id="fileIds" name="WapApplicationUpdateForm" property="testResultFileId" indexId="ctr">
                <input type="hidden" name="testResultFileId" value="<bean:write name="fileIds"/>"/>                     
            </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="WapApplicationUpdateForm" property="testStatus2">
            <logic:iterate id="statuses" name="WapApplicationUpdateForm" property="testStatus2" indexId="ctr">
                <input type="hidden" name="testStatus2" value="<bean:write name="statuses"/>"/>                     
            </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="WapApplicationUpdateForm" property="testedDate2">
            <logic:iterate id="dates" name="WapApplicationUpdateForm" property="testedDate2" indexId="ctr">
                <input type="hidden" name="testedDate2" value="<bean:write name="dates"/>"/>                     
            </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="WapApplicationUpdateForm" property="testComments2">
            <logic:iterate id="comments" name="WapApplicationUpdateForm" property="testComments2" indexId="ctr">
                <input type="hidden" name="testComments2" value="<bean:write name="comments"/>"/>                     
            </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="WapApplicationUpdateForm" property="testUpdatedBy">
            <logic:iterate id="updatesBy" name="WapApplicationUpdateForm" property="testUpdatedBy" indexId="ctr">
                <input type="hidden" name="testUpdatedBy" value="<bean:write name="updatesBy"/>"/>                     
            </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="WapApplicationUpdateForm" property="testUpdatedDate">
            <logic:iterate id="updatesDate" name="WapApplicationUpdateForm" property="testUpdatedDate" indexId="ctr">
                <input type="hidden" name="testUpdatedDate" value="<bean:write name="updatesDate"/>"/>                     
            </logic:iterate>
        </logic:notEmpty>
    <% }%>    
<% }%>



<%if (!isPage1) {%>
	<html:hidden property="title"	/>
    <html:hidden property="version"/>
    <html:hidden property="longProductName"/>
    <html:hidden property="vendorProductCode"  />
    <html:hidden property="shortDesc"	/>
	<html:hidden property="longDesc"	/>
    <html:hidden property="descContentOffering"/>    
    <html:hidden property="wapVersion"/>
	<%--<html:hidden property="demoUrl"/>--%>
    <html:hidden property="websiteUrl"/>
	<html:hidden property="ifPrRelease"	/>
    <html:hidden property="networkUsage"/>
    <logic:notEmpty name="WapApplicationUpdateForm" property="applicationURLs">
        <logic:iterate id="applicationURL" name="WapApplicationUpdateForm" property="applicationURLs">
            <input type="hidden" name="applicationURLs" value="<bean:write name='applicationURL'/>">
        </logic:iterate>
    </logic:notEmpty>
    
    <%if ( (!isPage3) || ( (isPage3) && (!hasAccessInitialApproval) ) ){%>
        <html:hidden property="categoryId1" />
        <html:hidden property="subCategoryId1" />
        <html:hidden property="contentType"/>        
    <% } %>

    <%if ( (!isPage3) || ( (isPage3) && (!hasAccessPendingDcr) ) || 
           ( statusSubmitted || statusInitialApproval || statusInitialDenied || statusBusinessApprovalDenied)
         ){%>
            <html:hidden property="testUrl"/>
            <%-- Test url is replaced by demo(Demo / Test URL), place demo url hidden field where test url is used --%> 
            <html:hidden property="demoUrl"/>
            <html:hidden property="productionUrl"/>

    <% } %>
        
<% } %>

<%if (!isPage2) {%>
	<html:hidden property="aimsContactId"	/>
	<html:hidden property="contactFirstName"	/>
	<html:hidden property="contactLastName"	/>
	<html:hidden property="contactTitle"	/>
	<html:hidden property="contactEmail"	/>
	<html:hidden property="contactPhone"	/>
	<html:hidden property="contactMobile"	/>
    <html:hidden property="launchDate"	/>
    <html:hidden property="testEffectiveDate"  />
    
    <%if ( (!isPage3) || ( (isPage3) && (!hasAccessPendingDcr) ) ||
           ( statusSubmitted || statusInitialApproval || statusInitialDenied ||
             statusBusinessApprovalGranted || statusBusinessApprovalDenied || statusPendingArm )
         ){%>
            <html:hidden property="vzwRetailPrice"  />
            <html:hidden property="vendorProductDisplay"  />
            <logic:notEmpty name="WapApplicationUpdateForm" property="listSelectedLicenseTypes">
                <logic:iterate id="selLicenseTypeIds" name="WapApplicationUpdateForm" property="listSelectedLicenseTypes" indexId="ctr">
                    <input type="hidden" name="listSelectedLicenseTypes" value="<bean:write name="selLicenseTypeIds"/>"/>                     
                </logic:iterate>
            </logic:notEmpty>
    <% } %>
<% } %>


<%if (isVerizonUser) {%>
	
	<%if (!isPage3) {%>
		<%if (hasAccessInitialApproval) {%>
            <html:hidden property="linkOrder1"  />
            <html:hidden property="categoryId2"  />
            <html:hidden property="subCategoryId2"  />
            <html:hidden property="linkOrder2"  />
            <html:hidden property="categoryId3"  />
            <html:hidden property="subCategoryId3"  />                        
            <html:hidden property="linkOrder3"  />
            <html:hidden property="initialApprovalNotes"  />
            <html:hidden property="initialApprovalAction"  />
		<% }%>
		
		<%if (hasAccessInitialBusinessApproval) {%>
            <html:hidden property="businessApprovalAction"  />
            <html:hidden property="businessApprovalNotes"  />
		<% }%>
        
        <%if (hasAccessMoveToPendingDcr) {%>
            <html:hidden property="moveToPendingDcr"  />
            <html:hidden property="vendorSplitPercentage"  />
        <% }%>
        
        <%if (hasAccessPendingDcr) {%>
            <html:hidden property="pendingDcrNotes"  />
            <html:hidden property="pageViewRate"  />
            <html:hidden property="taxCategoryCodeId"  />                                
        <% }%>
        
        <%if (hasAccessContentCompletion) {%>
            <html:hidden property="moveToCompletedInProduction"  />                                
        <% }%>
                
        <%if (hasAccessContentRemoval) {%>
            <html:hidden property="moveToSunset"  />                                
        <% }%>
        
        <%if ( (hasAccessInitialApproval) || (hasAccessContentTesting) ) {%>
            <html:hidden property="vzwLiveDate"  />
        <% }%>
    <% }%>
	
	<%if (!isPage4) {%>
		<html:hidden property="journalCombinedText"	/>
		<html:hidden property="journalText"	/>
		<html:hidden property="journalType"	/>
	<% }%>
		
<% } %>


