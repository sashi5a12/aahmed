<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<DIV>
    &nbsp;
</DIV>

<%System.out.println(request.getRequestURL());%>

<DIV class="homeColumn sBox">
    <DIV class="headLeftCurveblk"></DIV>
    <H1>PSMS Links</H1>

    <DIV class="headRightCurveblk"></DIV>
</DIV>
<DIV class="homeColumn sBox">
    <UL id="collapsible_menu">
        <LI class="collapsible_mainMenu">
            <A href="/aims/public/psms/PsmsLanding.jsp" class="linkBG">
                Overview
            </A>
        <LI class="collapsible_mainMenu">
            <A href="CampaignSubmissionProcess.jsp" class="menuOpenBlank">
                Campaign Submission Process
            </A>
        <LI class="collapsible_mainMenu">
            <A href="../../downloads/psms/Premium_Messaging_Referral_Sheet.pdf" target="_blank" class="linkBG">
                Premium Content Aggregator Referral Sheet
            </A>
        <LI class="collapsible_mainMenu">
            <A href="http://www.usshortcodes.com" class="linkBG" target="_blank">
                CSCA and the process for obtaining shortcodes
            </A>
        <LI class="collapsible_mainMenu">
            <A href="/aims/downloads/psms/VZW_Campaign_Data_Fields.xls" class="linkBG" target="_blank">
                VZW CAT tool sample submission form
            </A>
        <LI class="collapsible_mainMenu">
            <A href="https://psmsadmin.vzw.com/vzwcampaignadmin/index.do" class="linkBG" target="_blank">
                CAT tool
            </A>
        <LI class="collapsible_mainMenu">
            <A href="/aims/downloads/psms/MMA_Consumer_Best_Practices.doc" class="linkBG" target="_blank">
                Latest MMA guidelines document
            </A>
    </UL>
</DIV>