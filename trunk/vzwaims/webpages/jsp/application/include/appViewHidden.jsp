<html:hidden property="task" />
<html:hidden property="appsId" />
<html:hidden property="title"	/>
<html:hidden property="aimsPlatformId" />
<html:hidden property="appSubmitType"	/>
<html:hidden property="setupURL"	/>
<html:hidden property="updateURL"	/>
<html:hidden property="journalCombinedText"	/>

				  	
<logic:equal name="ApplicationUpdateForm" property="aimsPlatformId" scope="request"	value="<%=AimsConstants.BREW_PLATFORM_ID.toString()%>">						
	<html:hidden property="disclaimerText"	/>
	<html:hidden property="usingApplication"	/>
	<html:hidden property="tipsAndTricks"	/>
	<html:hidden property="faq"	/>
	<html:hidden property="troubleshooting"	/>
	<html:hidden property="devCompanyDisclaimer"	/>
	<html:hidden property="additionalInformation"	/>
</logic:equal>					