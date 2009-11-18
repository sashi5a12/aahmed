<html:hidden property="tempForRadioIssue" />
<html:hidden property="task" />
<html:hidden property="orignalTask" />
<html:hidden property="aimsLifecyclePhaseId"/>
<html:hidden property="appsId" />
<html:hidden property="appOwnerAllianceId" />
<html:hidden property="applicationStatus" />
<html:hidden property="allianceName" />
<html:hidden property="submittedDate" />
<html:hidden property="appSubmitType"	/>
<html:hidden property="currentPage"	/>

<html:hidden property="sampleClip1FileName"  />
<html:hidden property="sampleClip2FileName"  />
<html:hidden property="sampleClip3FileName"	/>

<html:hidden property="sampleClip1TempFileId"  />
<html:hidden property="sampleClip2TempFileId"  />
<html:hidden property="sampleClip3TempFileId"	/>


<%if (!isPage1) {%>

	<html:hidden property="title"	/>
    <html:hidden property="language"   />
    <html:hidden property="shortDesc"	/>
	<html:hidden property="longDesc"	/>
    <html:hidden property="updateFrequency"   />
    <html:hidden property="categoryId" />
    <html:hidden property="subCategoryId" />

<% } %>

<%if (!isPage2) {%>
	
    <html:hidden property="evaluationComments" />
    <html:hidden property="aimsContactId"	/>
	<html:hidden property="contactFirstName"	/>
	<html:hidden property="contactLastName"	/>
	<html:hidden property="contactTitle"	/>
	<html:hidden property="contactEmail"	/>
	<html:hidden property="contactPhone"	/>
	<html:hidden property="contactMobile"	/>
    
    <logic:notEmpty name="VcastApplicationUpdateForm" property="listSelectedAudGenders">
        <logic:iterate id="selGenderIds" name="VcastApplicationUpdateForm" property="listSelectedAudGenders" indexId="ctr">
            <input type="hidden" name="listSelectedAudGenders" value="<bean:write name="selGenderIds"/>"/>                     
        </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="VcastApplicationUpdateForm" property="listSelectedAudAges">
        <logic:iterate id="selAgeIds" name="VcastApplicationUpdateForm" property="listSelectedAudAges" indexId="ctr">
            <input type="hidden" name="listSelectedAudAges" value="<bean:write name="selAgeIds"/>"/>                     
        </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="VcastApplicationUpdateForm" property="listSelectedAudRaces">
        <logic:iterate id="selRaceIds" name="VcastApplicationUpdateForm" property="listSelectedAudRaces" indexId="ctr">
            <input type="hidden" name="listSelectedAudRaces" value="<bean:write name="selRaceIds"/>"/>                     
        </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="VcastApplicationUpdateForm" property="listSelectedAudIncomes">
        <logic:iterate id="selIncomeIds" name="VcastApplicationUpdateForm" property="listSelectedAudIncomes" indexId="ctr">
            <input type="hidden" name="listSelectedAudIncomes" value="<bean:write name="selIncomeIds"/>"/>                     
        </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="VcastApplicationUpdateForm" property="listSelectedAudEducations">
        <logic:iterate id="selEducationIds" name="VcastApplicationUpdateForm" property="listSelectedAudEducations" indexId="ctr">
            <input type="hidden" name="listSelectedAudEducations" value="<bean:write name="selEducationIds"/>"/>                     
        </logic:iterate>
    </logic:notEmpty>
    
<% } %>


<%if (isVerizonUser) {%>
	
	<%if (!isPage4) {%>
		<html:hidden property="journalCombinedText"	/>
		<html:hidden property="journalText"	/>
		<html:hidden property="journalType"	/>
	<% }%>
		
<% } %>


