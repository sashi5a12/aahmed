<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%! Map hashMap = new HashMap();%>
<%!
    private boolean matchPageURL(HttpServletRequest request, String subMenuUrl) {
        //System.out.println(request.getRequestURI());
        String urlStrng = request.getRequestURL().toString();
        Map pramMap = request.getParameterMap();
        boolean finalCondition = false;
        try {
            String[] subMenuUrlParts = subMenuUrl.split("\\?");
            if (urlStrng.indexOf(subMenuUrlParts[0]) > 0) {
                if (subMenuUrlParts.length > 1) {
                    String[] parameterArry = subMenuUrlParts[1].split(String.valueOf('&'));
                    for (int i = 0; i < parameterArry.length; i++) {
                        finalCondition = "".equals(((String[]) pramMap.get(parameterArry[i].split("=")[0]))[0])
                                || (((String[]) pramMap.get((parameterArry[i].split("="))[0]))[0]).equals((parameterArry[i].split("="))[1]);
                        if (!finalCondition) break;
                    }
                } else {
                    finalCondition = true;
                }
            }
        } catch (Exception e) {
            //  e.printStackTrace();// eating all exceptions
        }

        /*
        if (finalCondition)
            System.out.println(finalCondition);
        */

        return finalCondition;
    }
%>

<%
    /*this hashmap will help me to accociate inner page with menues*/
    /*stoplight pages*/
    hashMap.put("/aims/application/allianceSLCaseStudiesView.jsp", "All Applications");
    hashMap.put("/aims/application/caseStudyCreateUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/allianceSLDemoView.jsp", "All Applications");
    hashMap.put("/aims/application/demoCreateUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/allianceSLQRGView.jsp", "All Applications");
    hashMap.put("/aims/application/qrgCreateUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/allianceSLSalesCollateralView.jsp", "All Applications");
    hashMap.put("/aims/application/salesCollateralCreateUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/salesPartnerCreateUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/allianceSLSalesPartnerView.jsp", "All Applications");
    hashMap.put("/aims/application/allianceSLSalesPresentationView.jsp", "All Applications");
    hashMap.put("/aims/application/salesPresentationCreateUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/allianceSLSuccessStoryView.jsp", "All Applications");
    hashMap.put("/aims/application/successStoryCreateUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/allianceSLTestimonyView.jsp", "All Applications");
    hashMap.put("/aims/application/testimonyCreateUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/allianceSLWhitePaperView.jsp", "All Applications");
    hashMap.put("/aims/application/whitePaperCreateUpdate.jsp", "All Applications");

    hashMap.put("/aims/contracts/amendmentsInfoView.jsp", "Amendments");
    hashMap.put("/aims/application/vzwAllianceSLCaseStudiesView.jsp", "All Applications");
    hashMap.put("/aims/application/vzwAllianceSLDemoView.jsp", "All Applications");
    hashMap.put("/aims/application/vzwAllianceSLQRGView.jsp", "All Applications");
    hashMap.put("/aims/application/vzwAllianceSLSalesCollateralView.jsp", "All Applications");
    hashMap.put("/aims/application/vzwAllianceSLSalesPartnerView.jsp", "All Applications");
    hashMap.put("/aims/application/vzwAllianceSLSalesPresentationView.jsp", "All Applications");
    hashMap.put("/aims/application/vzwAllianceSLSuccessStoryView.jsp", "All Applications");
    hashMap.put("/aims/application/vzwAllianceSLTestimonyView.jsp", "All Applications");
    hashMap.put("/aims/application/vzwAllianceSLWhitePaperView.jsp", "All Applications");

    hashMap.put("/aims/application/reconcileCatalogUpload.jsp", "Upload Catalog Data");
    hashMap.put("/aims/application/wapJournal.jsp", "New WAP Applications");
    hashMap.put("/aims/application/vcastJournal.jsp", "New VCAST Applications");

    hashMap.put("/aims/tools/toolsList.jsp", "Tools");
    hashMap.put("/aims/masters/dolLoanedDevicesList.jsp", "View Device on Loan Data");

    hashMap.put("/aims/contracts/contractCreateUpdateView.jspeditViewForm", "Alliance Summary");

    hashMap.put("/aims/masters/devicesUpdate.jsp", "Devices");
    hashMap.put("/aims/masters/devicesView.jsp", "Devices");

    hashMap.put("/aims/tools/toolView.jsp", "Tools");
    hashMap.put("/aims/tools/toolsUpdate.jsp", "Tools");

    hashMap.put("/aims/contracts/contractCreateUpdateView.jsp", "Contracts");
    hashMap.put("/aims/contracts/contractsInfoView.jsp", "Contracts");
    hashMap.put("/aims/contracts/contractCreateUpdate.jsp", "Contracts");


    hashMap.put("/aims/contracts/amendmentCreateUpdateView.jsp", "Amendments");
    hashMap.put("/aims/contracts/amendmentCreateUpdate.jsp", "Amendments");
    hashMap.put("/aims/system/systemNotificationUpdate.jsp", "Manage Notifications");
    hashMap.put("/aims/system/disclaimerViewUpdate.jsp", "Disclaimers");

    hashMap.put("/aims/verticals/verticalsEmailAddress.jsp", "Vertical Email List");
    
    /*****************************
        //commented for vzw accounts cleanup
        hashMap.put("/aims/accounts/accountsCreateUpdateVZWView.jsp", "Account Managers");
        hashMap.put("/aims/accounts/accountsView.jsp", "Users");
        hashMap.put("/aims/accounts/accountsChangePassword.jsp", "Users");
    *****************************/

    hashMap.put("/aims/alliance/vzwAllianceContractsSaveView.jsp","All Alliances");
    hashMap.put("/aims/contracts/contractsOfferListView.jsp" , "All Alliances");
    hashMap.put("/aims/alliance/vzwAllianceStatusView.jsp", "All Alliances");
    hashMap.put("/aims/alliance/vzwAllianceCompanyInfoView.jsp", "All Alliances");
    hashMap.put("/aims/alliance/vzwAllianceBusinessInfoView.jsp", "All Alliances");
    hashMap.put("/aims/alliance/vzwAllianceContactInfoUpdate.jsp", "All Alliances");
    hashMap.put("/aims/alliance/vzwAllianceContactInfoView.jsp", "All Alliances");
    hashMap.put("/aims/alliance/vzwAllianceToolsView.jsp", "All Alliances");
    hashMap.put("/aims/alliance/journalEntryUpdate.jsp", "All Alliances");

    //alliance summary menus
    hashMap.put("/aims/alliance/allianceStatusView.jsp", "Alliance Summary");
    hashMap.put("/aims/alliance/allianceCompanyInfoUpdate1.jsp", "Company Information");
    hashMap.put("/aims/alliance/allianceBusinessInfoUpdate.jsp", "Business Information");
    hashMap.put("/aims/alliance/allianceContactInfoUpdate.jsp", "Contact Information");
    hashMap.put("/aims/alliance/allianceContractsInfoView.jsp", "Alliance Contracts");
    hashMap.put("/aims/alliance/allianceContractDetail.jsp", "Alliance Contracts");
    hashMap.put("/aims/alliance/allianceToolsView.jsp", "Important Information");

    hashMap.put("/aims/alliance/allianceClickThroughContracts.jsp", "Click Through Contracts");

    //alliance secondary user summary menus
    hashMap.put("/aims/alliance/allianceCompanyInfoView.jsp", "Company Information");
    hashMap.put("/aims/alliance/allianceBusinessInfoView.jsp", "Business Information");
    hashMap.put("/aims/alliance/allianceContactInfoView.jsp", "Contact Information");

    hashMap.put("/aims/alliance/vzwAllianceViewDelete.jsp", "All Alliances");
    hashMap.put("/aims/alliance/vzwAllianceContractsInfoView.jsp", "All Alliances");
    hashMap.put("/aims/alliance/vzwAllianceSearchViewDelete.jsp", "All Alliances");
    hashMap.put("/aims/alliance/vzwAllianceSearch.jsp", "All Alliances");


    hashMap.put("/aims/alliance/allianceMusicInfoUpdate.jsp", "All VCAST Music Alliances");
    hashMap.put("/aims/masters/reconcileDeviceOnLoanUploadResult.jsp", "Upload Device on Loan Data");

    hashMap.put("/aims/application/brewApplicationView.jsp", "All Applications");
    hashMap.put("/aims/application/brewEvaluationView.jsp", "All Applications");
    hashMap.put("/aims/application/appProcessInfoView.jsp", "All Applications");
    hashMap.put("/aims/application/appProcessInfo.jsp", "All Applications");
    hashMap.put("/aims/application/appProcessInfoView.jsp", "All Applications");
    hashMap.put("/aims/application/wapApplicationView.jsp", "All Applications");
    hashMap.put("/aims/application/wapAppProcessInfoView.jsp", "All Applications");
    hashMap.put("/aims/application/wapJournalView.jsp", "All Applications");
    hashMap.put("/aims/application/entApplicationView.jsp", "All Applications");
    hashMap.put("/aims/application/appProcessInfoView.jsp", "All Applications");
    hashMap.put("/aims/application/smsApplicationView.jsp", "All Applications");
    hashMap.put("/aims/application/mmsApplicationView.jsp", "All Applications");
    hashMap.put("/aims/application/vcastApplicationView.jsp", "All Applications");
    hashMap.put("/aims/application/journalUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/brewApplicationUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/brewApplicationUpdate2.jsp", "All Applications");
    hashMap.put("/aims/application/brewEvaluationUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/brewUserGuideUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/brewUserGuideView.jsp", "All Applications");
    hashMap.put("/aims/application/entApplicationUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/entApplicationUpdate2.jsp", "All Applications");
    hashMap.put("/aims/application/entApplicationUpdate3.jsp", "All Applications");
    hashMap.put("/aims/application/entApplicationUpdateBOBO.jsp", "All Applications");
    hashMap.put("/aims/application/entApplicationUpdateLBS.jsp", "All Applications");
    hashMap.put("/aims/application/mmsApplicationUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/mmsApplicationUpdate2.jsp", "All Applications");
    hashMap.put("/aims/application/journal.jsp", "All Applications");
    hashMap.put("/aims/application/vcastJournalView.jsp", "All Applications");

	hashMap.put("/aims/application/javaUserGuideUpdate.jsp", "All Applications");
	hashMap.put("/aims/application/javaApplicationUpdate.jsp", "All Applications");
	hashMap.put("/aims/application/javaApplicationUpdate2.jsp", "All Applications");
	hashMap.put("/aims/application/javaAppProcessInfo.jsp", "All Applications");
	hashMap.put("/aims/application/javaJournal.jsp", "All Applications");
	
	hashMap.put("/aims/application/javaApplicationView.jsp", "All Applications");
	hashMap.put("/aims/application/javaAppProcessInfoView.jsp", "All Applications");
	hashMap.put("/aims/application/javaUserGuideView.jsp", "All Applications");
	hashMap.put("/aims/application/javaJournalView.jsp", "All Applications");
	
	hashMap.put("/aims/application/approvedJavaApps.jsp", "All Applications");
	hashMap.put("/aims/application/applicationSearch.jsp", "All Applications");

    hashMap.put("/aims/application/vzAppZoneApplicationUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/vzAppZoneApplicationUpdate2.jsp", "All Applications");
    hashMap.put("/aims/application/vzAppZoneBinariesUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/vzAppZoneProcessingInfoUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/vzAppZoneJournal.jsp", "All Applications");
    hashMap.put("/aims/application/vzAppZoneApplicationView.jsp", "All Applications");
    hashMap.put("/aims/application/vzAppZoneApplicationBinariesView.jsp", "All Applications");
    hashMap.put("/aims/application/vzAppZoneProcessingInfoView.jsp", "All Applications");
    hashMap.put("/aims/application/vzAppZoneJournalView.jsp", "All Applications");

    hashMap.put("/aims/application/smsApplicationUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/smsApplicationUpdate2.jsp", "All Applications");
    hashMap.put("/aims/application/vcastApplicationUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/vcastApplicationUpdate2.jsp", "All Applications");
    hashMap.put("/aims/application/wapApplicationUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/wapApplicationUpdate2.jsp", "All Applications");
    hashMap.put("/aims/application/wapAppProcessInfo.jsp", "All Applications");
    hashMap.put("/aims/application/reconcileCatalogSelect.jsp", "All Applications");
    hashMap.put("/aims/application/applicationsViewDelete.jsp", "All Applications");

    hashMap.put("/aims/application/dashboardApplicationUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/dashboardApplicationUpdate2.jsp", "All Applications");
    hashMap.put("/aims/application/dashboardUserGuideUpdate.jsp", "All Applications");
    hashMap.put("/aims/application/dashboardUserGuideView.jsp", "All Applications");
    hashMap.put("/aims/application/dashboardAppProcessInfo.jsp", "All Applications");
    hashMap.put("/aims/application/dashboardJournal.jsp", "All Applications");
    hashMap.put("/aims/application/dashboardApplicationView.jsp", "All Applications");
    hashMap.put("/aims/application/dashboardAppProcessInfoView.jsp", "All Applications");
    hashMap.put("/aims/application/dashboardJournalView.jsp", "All Applications");
    
    hashMap.put("/aims/application/entAppPublishSolution.jsp", "Publish Solutions");
    hashMap.put("/aims/application/entAppSalesLead.jsp", "Submit Sales Lead");
    hashMap.put("/aims/application/entAppSalesLeadView.jsp", "Sales Lead Sent");
    hashMap.put("/aims/application/entAppSalesLeadView.jsp", "Sales Lead Received");

    hashMap.put("/aims/application/approvedJavaApps.jsp", "All Approved V CAST Apps");
    hashMap.put("/aims/workflow/worklist.jsp", "All Applications");

    hashMap.put("/aims/application/reconcileCatalogConfirmed.jsp", "Reconcile Catalog Data");

    hashMap.put("/aims/content/faqsView.jsp", "View FAQ");
    hashMap.put("/aims/content/faqsEdit.jsp", "Manage FAQ");
    hashMap.put("/aims/content/faqsCreate.jsp", "Manage FAQ");
    hashMap.put("/aims/content/faqsViewDelete.jsp", "Manage FAQ");
    hashMap.put("/aims/content/faqsDetailView.jsp", "Manage FAQ");
    hashMap.put("/aims/content/faqCategoryUpdate.jsp", "Manage FAQ Categories");

    /*****************************
        //commented for vzw accounts cleanup, vzw users can not create/edit/view manage roles pages
        hashMap.put("/aims/roles/sysRolesView.jsp", "Roles");
        hashMap.put("/aims/roles/sysRolesCreateUpdate.jsp", "Roles");
        hashMap.put("/aims/roles/sysRolesCreateUpdateView.jsp", "Roles");
    *****************************/

    hashMap.put("/aims/accounts/accountsView.jsplistAccountManager" , "Account Managers");
    hashMap.put("/aims/accounts/accountsView.jspdelAccountManager" , "Account Managers");
    hashMap.put("/aims/accounts/accountsView.jspaddAccountManager" , "Account Managers");

    /*****************************
        //commented for vzw accounts cleanup
        hashMap.put("/aims/accounts/accountsCreateUpdateVZWView.jsp", "Users");
        hashMap.put("/aims/accounts/accountsCreateUpdateVZW.jsp", "Users");
    *****************************/


    hashMap.put("/aims/accounts/accountsCreateUpdateView.jsp", "Manage Users");
    hashMap.put("/aims/accounts/accountsCreateUpdate.jsp", "Manage Users");
    hashMap.put("/aims/accounts/accountsInvite.jsp", "Manage Users");
    hashMap.put("/aims/accounts/accountsView.jsp", "Manage Users");

    hashMap.put("/aims/contacts/contactsList.jsp", "Manage Contacts");
    hashMap.put("/aims/contacts/contactUpdate.jsp", "Manage Contacts");

    hashMap.put("/aims/alliance/changeAllianceAdmin.jsp", "Change Alliance Administrator");

    hashMap.put("/aims/masters/reconcileDeviceOnLoanUpload.jsp","Upload Device on Loan Data");

    hashMap.put("/aims/system/systemNotificationUpdate.jsp" , "Notifications");
    hashMap.put("/aims/system/firmwareCreateUpdate.jsp" , "Firmware");
    hashMap.put("/aims/system/firmwareList.jsp" , "Firmware");
%>


<!-- LEFT NAVIGATION START HERE -->
<DIV class="homeColumn sBox"> <%--marginT--%>
    <DIV class="headLeftCurveblk"></DIV>
    <H1>Go to Market</H1>
    <DIV class="headRightCurveblk"></DIV>
</DIV>
<DIV class="homeColumn sBox">
    <UL id="collapsible_menu">
        <%boolean menuOpen = true ;%>
        <%int mainMenuCounter = 0;%>
        <logic:iterate id="menu" name="AIMS_MENU" type="com.netpace.aims.model.security.AimsMenu">
        <logic:notEqual name="menu" property="menuUrl" value="#">
        <LI class="collapsible_mainMenu">
                <% if (matchPageURL(request, menu.getMenuUrl()) || menu.getMenuName().equals(hashMap.get(request.getRequestURI())) ) {%>
            <A href="<bean:write name="menu" property="menuUrl" />" class="menuOpenBlank">
                <bean:write name="menu" property="menuName"/>
            </A>
                <%} else {%>
            <A href="<bean:write name="menu" property="menuUrl" />" class="linkBG">
                <bean:write name="menu" property="menuName"/>
            </A>
                <%}%>
            </logic:notEqual>
            <logic:equal name="menu" property="menuUrl" value="#">
        <LI class="collapsible_mainMenu">
            <A name="mainMenu" id="txtIntl-main" class="menuClosed"
               onClick="toggleMenu(this,parentNode);"
               href="javascript:void(0);">
                <bean:write name="menu" property="menuName"/>
            </A>
            <UL id="collapsible_subMenu" class="subMenuClosed">
                <logic:iterate id="sub_menu" name="AIMS_SUB_MENU" indexId="row_no"
                               type="com.netpace.aims.model.security.AimsSubMenu">
                <logic:equal name="sub_menu" property="aimsMenu.menuName"
                             value="<%=menu.getMenuName()%>">
                    <%--
                        if the uri is present in the above maping then open
                        the desired menu else then go and find it right menu.
                    --%>
                <% if (matchPageURL(request, sub_menu.getSubMenuUrl()) ) { %>                
                <% if (menuOpen) { %>
                <script type="text/javascript">
                    var x = $("collapsible_menu").getElementsByTagName("ul");
                    x[<%=mainMenuCounter%>].className = "subMenuOpen";
                    var y = document.getElementsByName("mainMenu")[<%=mainMenuCounter%>];
                    y.className = "menuOpen";
                </script>
                <%menuOpen = false;%>
                <%}%>
                <A href="<bean:write name="sub_menu" property="subMenuUrl"/>" id="viewAll" class="subLinkActive">
                    <bean:write name="sub_menu" property="subMenuName"/>
                </A>
                <%} else {%>
                <% if( (sub_menu.getSubMenuName().equals(hashMap.get(request.getRequestURI())) && menuOpen)
                        || (sub_menu.getSubMenuName().equals(hashMap.get(request.getRequestURI()+""+request.getParameter("task"))) && menuOpen)){%>
                <%--<%menuOpen = false;%>--%>
                <script type="text/javascript">
                    var x = $("collapsible_menu").getElementsByTagName("ul");
                    x[<%=mainMenuCounter%>].className = "subMenuOpen";
                    var y = document.getElementsByName("mainMenu")[<%=mainMenuCounter%>];
                    y.className = "menuOpen";
                </script>
                <%
                    if (sub_menu.getAimsMenu().getMenuName().equals("Manage Alliances")
                            || sub_menu.getAimsMenu().getMenuName().equals("Manage Applications")) {
                %>
                <LI>
                    <A href="<bean:write name="sub_menu" property="subMenuUrl"/>" id="viewAll" class="subLink">
                        <bean:write name="sub_menu" property="subMenuName"/>                            
                    </A>
                        <%} else {%>
                <LI>
                    <A href="<bean:write name="sub_menu" property="subMenuUrl"/>" id="viewAll" class="subLinkActive">
                        <bean:write name="sub_menu" property="subMenuName"/>
                    </A>
                        <%}%>
                        <% }  else {%>
                <LI>
                    <A href="<bean:write name="sub_menu" property="subMenuUrl"/>" id="viewAll" class="subLink">
                        <bean:write name="sub_menu" property="subMenuName"/>
                    </A>
                        <%}%>
                        <%}%>
                    </logic:equal>
                    </logic:iterate>
            </UL>
                <%mainMenuCounter++;%>
            </logic:equal>
            </logic:iterate>
        <LI class="collapsible_mainMenu"><A href="/aims/editprofile.do" class="linkBG">Profile Settings</A>
    </UL>
</DIV>
<!-- LEFT NAVIGATION END HERE -->