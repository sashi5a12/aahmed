<%@ page language="java" import="com.netpace.aims.bo.security.*, com.netpace.aims.util.*" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<jsp:useBean id="loginInfo" class="com.netpace.aims.controller.login.LoginInfo" scope="session"/>

<%
    boolean isMarketingUser = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.NEW_PROVISIONED_CONTENTS, AimsSecurityManager.UPDATE);
    boolean hasAccessToAllAlliances = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.ALL_ALLIANCES, AimsSecurityManager.UPDATE);
    boolean hasAccessToAllApplications = AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.MANAGE_ALL_APPLICATIONS, AimsSecurityManager.UPDATE);
%>

<!-- html error -->
<logic:present name="<%=com.netpace.aims.util.AimsConstants.SHOW_POPUP%>" scope="request">
    <script language="Javascript">
        <!--
        cwInfo = window.open("/aims/login/vzwLoginPopup.jsp", "popUp", "menubar=no,location=no,resizable=no,toolbar=no,scrollbars=yes,width=550,height=400");
        cwInfo.focus();
        //-->
    </script>
</logic:present>

<%@ include  file="/common/error.jsp" %>

<div id="contentBox">
<!--todo: implement bulletin board message -->
<%-- Bulletin Success Message --%>
<logic:present name="<%=AimsConstants.REQUEST_SHOW_BULLETIN_SUCCESS%>">
    <!--
    <table width="100%" height="100%">
        <tr>
            <td >Message</td>
        </tr>
        <tr>
            <td> <b>
    -->
        <div class="alertMsg">
                <bean:write name="<%=AimsConstants.REQUEST_BULLETIN_SUCCESS_MESSAGE%>" scope="request"/>
        </div>                
        <%--
            </b> </td>
        </tr>
    </table>--%>
</logic:present>
<%-- End Bulletin Success Message --%>

<%-- If bulletin present --%>
<logic:present name="<%=com.netpace.aims.util.AimsConstants.SESSION_BULLETIN_TO_READ%>" scope="session">
<%-- Bulletin Error Message --%>
<logic:present name="<%=AimsConstants.REQUEST_SHOW_BULLETIN_ERROR%>">
    <!--<table width="100%" height="100%">
        <tr>
            <td>Error</td>
        </tr>
        <tr>
            <td>-->
        <div class="errorMsg">
            <bean:write name="<%=AimsConstants.REQUEST_BULLETIN_ERROR_MESSAGE%>" scope="request"/>
        </div>
            <!--</td>
        </tr>
    </table>-->
</logic:present>

<%-- Bulletin Text --%>
<DIV class="homeColumn lBox floatL">
<DIV class="headLeftCurveblk"></DIV>
<H1><bean:write name="<%=AimsConstants.REQUEST_BULLETIN_HEADER%>" filter="false" scope="request"/></H1>
<DIV class="headRightCurveblk"></DIV>
<div class="contentbox">
    <table width="100%" height="100%">
    <tr >
        <td>
           <bean:write name="<%=AimsConstants.REQUEST_BULLETIN_TEXT%>" filter="false" scope="request"/> 
        </td>
    </tr>
    <tr>
        <td >
             <div class="redBtn" id="Accept" title="Accept">
                <div>
                    <div>
                        <bean:write name="<%=AimsConstants.REQUEST_BULLETIN_POST_ACTION%>" filter="false" scope="request"/>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>
</div>
</div>
</logic:present>
<%-- End if bulletin present --%>

<!-- CONTENT START HERE -->
<logic:notPresent name="<%=com.netpace.aims.util.AimsConstants.SESSION_BULLETIN_TO_READ%>" scope="session">
<!-- IF ALLIANCES ADMIN PRESENT -->
<logic:present name="loginInfo" property="allianceAdmin">
    <DIV class="homeColumn lBox floatL">
        <DIV class="headLeftCurveblk"></DIV>
        <H1>Alliances Summary</H1>
        <DIV class="headRightCurveblk"></DIV>
        <DIV class="contentbox">
            <table width="100%" border="0" cellspacing="0" cellpadding="3">                
                <tr>
                    <td width="35%">VZW Admin for your Alliance</td>
                    <td width="65%"><strong>
                        <bean:write name="loginInfo" property="vzwManager"/>
                    </strong></td>
                </tr>
            </table>
        </DIV>
        <div>&nbsp;</div>
        <DIV class="headLeftCurveblk"></DIV>
        <H1>Applications Summary</H1>
        <DIV class="headRightCurveblk"></DIV>
        <DIV class="contentbox">
        <table width="100%" border="0" cellspacing="0" cellpadding="3">
                <tr>
                    <td width="35%">Submitted Applications</td>
                    <td width="65%"><strong>
                        <bean:write name="loginInfo" property="noOfSubmittedApps" />
                    </strong></td>
                </tr>
                <tr>
                    <td width="35%">Applications saved and yet to be submitted</td>
                    <td width="65%"><strong>
                        <bean:write name="loginInfo" property="noOfSavedApps"/>
                    </strong></td>
                </tr>
                <tr>
                    <td width="35%">Accepted Applications</td>
                    <td width="65%"><strong>
                        <bean:write name="loginInfo" property="noOfAcceptedApps"/>
                    </strong></td>
                </tr>
                <tr>
                    <td width="35%">Rejected Applications</td>
                    <td width="65%"><strong>
                        <bean:write name="loginInfo" property="noOfRejectedApps"/>
                    </strong></td>
                </tr>
            </table>
        </DIV>
    </DIV>

</logic:present>
<!-- END IF ALLIANCES ADMIN PRESENT -->

<!-- IF ALLIANCES USER PRESENT -->
<logic:present name="loginInfo" property="allianceUser">
    <DIV class="homeColumn lBox floatL">
        <DIV class="headLeftCurveblk"></DIV>
        <H1>Applications Summary</H1>

        <DIV class="headRightCurveblk"></DIV>
        <DIV class="contentbox">
            <table width="100%" border="0" cellspacing="0" cellpadding="3">
                <tr>
                    <td width="35%">Submitted Applications</td>
                    <td width="65%"><strong>:
                        <bean:write name="loginInfo" property="noOfSubmittedApps"/>
                    </strong></td>
                </tr>
                <tr>
                    <td width="35%">Applications saved and yet to be submitted</td>
                    <td width="65%"><strong>:
                        <bean:write name="loginInfo" property="noOfSavedApps"/>
                    </strong></td>
                </tr>
                <tr>
                    <td width="35%">Accepted Applications</td>
                    <td width="65%"><strong>:
                        <bean:write name="loginInfo" property="noOfAcceptedApps"/>
                    </strong></td>
                </tr>
                <tr>
                    <td width="35%">Rejected Applications</td>
                    <td width="65%"><strong>:
                        <bean:write name="loginInfo" property="noOfRejectedApps"/>
                    </strong></td>
                </tr>
            </table>
        </DIV>
    </DIV>
</logic:present>
<!-- END IF ALLIANCES USER PRESENT -->

<!-- IF VZW ADMIN-->
<logic:present name="loginInfo" property="vzwAdmin">
    <% if (isMarketingUser) { %>
    <!--Welcome Marketing User-->
    <!--todo: why is this not implemented.-->
    <% } %>

    <!-- ALLIANCES SUMMARY START HERE -->
    <% if (hasAccessToAllAlliances) { %>
    <DIV class="homeColumn lBox floatL">
        <DIV class="headLeftCurveblk"></DIV>
        <H1>Alliances Summary</H1>

        <DIV class="headRightCurveblk"></DIV>
        <DIV class="contentbox">
            <table width="100%" border="0" cellspacing="0" cellpadding="3">
                <tr>
                    <td width="35%">No. of Alliances in the System</td>
                    <td width="65%"><strong>
                        <bean:write name="loginInfo" property="noOfAlliances"/>
                    </strong></td>
                </tr>
                <tr>
                    <td>No. of New Alliances</td>
                    <td><strong>
                        <bean:write name="loginInfo" property="noOfNewAlliances"/>
                    </strong></td>
                </tr>
                <tr>
                    <td>No. of Rejected Alliances</td>
                    <td><strong>
                        <bean:write name="loginInfo" property="noOfRejectedAlliances"/>
                    </strong></td>
                </tr>
                <tr>
                    <td><img src="images/greyRndArrow.gif" width="17" height="17" align="absmiddle"> <a
                            href="/aims/vzwAlliance.do?task=view&all_type=ALL"><strong>All
                        Alliances</strong></a></td>
                    <td>&nbsp;</td>
                </tr>
            </table>
        </DIV>
    </DIV>
    <% } %>
    <!-- ALLIANCES SUMMARY END HERE -->

    <!-- APPLICATIONS SUMMARY START HERE -->
    <% if (hasAccessToAllApplications) { %>
    <DIV class="homeColumn lBox marginT">
    <DIV class="headLeftCurveblk"></DIV>
    <H1>Applications Summary</H1>

    <DIV class="headRightCurveblk"></DIV>
    <DIV class="contentbox">
    <table width="100%" border="0" cellspacing="0" cellpadding="3" bordercolor="black">
    <tr>
        <td>&nbsp;</td>
        <td>BREW</td>
        <td align="right">SMS</td>
        <td align="right">MMS</td>
        <td align="right">WAP</td>
        <td align="right">JMA</td>
        <td align="right">VZAPPZONE</td>
        <td align="right">DASHBOARD</td>
        <td align="right">TOTAL</td>
    </tr>
    <tr>
        <td width="33%">No. of New Applications</td>
        <td width="8%"><strong>
            <bean:write name="loginInfo" property="noOfBrewSubApps"/>
        </strong></td>
        <td width="7%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfSMSSubApps"/>
        </strong></td>
        <td width="8%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfMMSSubApps"/>
        </strong></td>
        <td width="7%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfWAPSubApps"/>
        </strong></td>
        <td width="7%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfEntSubApps"/>
        </strong></td>
        <td width="13%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfVZAppZoneSubApps"/>
        </strong></td>
        <td width="13%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfDashSubApps"/>
        </strong></td>
        <td width="10%" align="right"><strong>
            <bean:write name="loginInfo" property="totalSubApps"/>
        </strong></td>
    </tr>
    <tr>
        <td>No. of Applications Evaluated</td>
        <td><strong>
            <bean:write name="loginInfo" property="noOfBrewEvaluatedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfSMSEvaluatedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfMMSEvaluatedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfWAPEvaluatedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfEntEvaluatedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfVZAppZoneEvaluatedApps"/>
        </strong></td>
        <td align="right"><strong>
            0
        </strong></td> 
        <td align="right"><strong>
            <bean:write name="loginInfo" property="totalEvaluatedApps"/>
        </strong></td>
    </tr>
    <tr>
        <td>No. of Applications Under Testing</td>
        <td><strong>
            <bean:write name="loginInfo" property="noOfBrewTestingApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfSMSTestingApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfMMSTestingApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfWAPTestingApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfEntTestingApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfVZAppZoneTestingApps"/>
        </strong></td>
        <td align="right"><strong>
            0
        </strong></td> 
        <td align="right"><strong>
            <bean:write name="loginInfo" property="totalTestedApps"/>
        </strong></td>
    </tr>
    <tr>
        <td>No. of Accepted Applications</td>
        <td><strong>
            <bean:write name="loginInfo" property="noOfBrewAcceptedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfSMSAcceptedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfMMSAcceptedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfWAPAcceptedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfEntAcceptedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfVZAppZoneAcceptedApps"/>
        </strong></td>
        <td align="right"><strong>
            0
        </strong></td> 
        <td align="right"><strong>
            <bean:write name="loginInfo" property="totalAcceptedApps"/>
        </strong></td>
    </tr>
    <tr>
        <td>No. of Rejected Applications</td>
        <td><strong>
            <bean:write name="loginInfo" property="noOfBrewRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfSMSRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfMMSRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfWAPRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfEntRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfVZAppZoneRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            0
        </strong></td> 
        <td align="right"><strong>
            <bean:write name="loginInfo" property="totalRejectedApps"/>
        </strong></td>
    </tr>
    <tr>
        <td>
            <img src="images/greyRndArrow.gif" width="17" height="17" align="absmiddle">
            <a href="/aims/applicationsViewDelete.do?task=view&app_type=all_apps&cancel_search"><strong>All Applications</strong></a>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
		<td>&nbsp;</td>
    </tr>
    </table>
    </DIV>
    </DIV>
    <% } %>
    <!-- APPLICATIONS SUMMARY END HERE -->
</logic:present>
<!-- END IF VZW ADMIN-->

<!-- IF VZW USER PRESENT-->
<logic:present name="loginInfo"  property="vzwUser">
    <DIV class="homeColumn lBox marginT">
    <DIV class="headLeftCurveblk"></DIV>
    <H1>Applications Summary</H1>

    <DIV class="headRightCurveblk"></DIV>
    <DIV class="contentbox">
    <table width="100%" border="0" cellspacing="0" cellpadding="3">
    <tr>
        <td>&nbsp;</td>
        <td>BREW</td>
        <td align="right">SMS</td>
        <td align="right">MMS</td>
        <td align="right">WAP</td>
        <td align="right">ENTERPRISE</td>
        <td align="right">VZAPPZONE</td>
        <td align="right">DASHBOARD</td>
        <td align="right">TOTAL</td>
    </tr>

    <tr>
        <td> No of Applications in the System: </td>
        <td align="right"><bean:write name="loginInfo" property="noOfBrewApps" /></td>
        <td align="right"><bean:write name="loginInfo" property="noOfSMSApps" /></td>
        <td align="right"><bean:write name="loginInfo" property="noOfMMSApps" /></td>
        <td align="right"><bean:write name="loginInfo" property="noOfWAPApps" /></td>
        <td align="right"><bean:write name="loginInfo" property="noOfEntApps" /></td>
        <td align="right"><bean:write name="loginInfo" property="noOfVZAppZoneApps" /></td>
        <td align="right"><bean:write name="loginInfo" property="noOfDashApps" /></td>
        <td align="right"><bean:write name="loginInfo" property="totalApps" /></td>
    </tr>

    <tr>
        <td width="33%">No. of New Applications</td>
        <td width="8%"><strong>
            <bean:write name="loginInfo" property="noOfBrewSubApps" />
        </strong></td>
        <td width="7%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfSMSSubApps" />
        </strong></td>
        <td width="8%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfMMSSubApps" />
        </strong></td>
        <td width="7%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfWAPSubApps" />
        </strong></td>
        <td width="14%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfEntSubApps" />
        </strong></td>
        <td width="13%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfVZAppZoneSubApps" />
        </strong></td>
        <td width="13%" align="right"><strong>
            <bean:write name="loginInfo" property="noOfDashSubApps" />
        </strong></td>
        <td width="10%" align="right"><strong>
            <bean:write name="loginInfo" property="totalSubApps" />
        </strong></td>
    </tr>
    <tr>
        <td>No. of assigned applications under review</td>
        <td><strong>
            <bean:write name="loginInfo" property="noOfBrewTestingApps" /> 
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfSMSTestingApps" />
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfMMSTestingApps" />
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfWAPTestingApps" />
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfEntTestingApps" />
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfVZAppZoneTestingApps" />
        </strong></td>
        <td align="right"><strong>
            0
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="totalTestedApps" />
        </strong></td>
    </tr>
    <tr>
        <td>No. of Accepted Applications</td>
        <td><strong>
            <bean:write name="loginInfo" property="noOfBrewAcceptedApps" />
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfSMSAcceptedApps" />
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfMMSAcceptedApps" />
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfWAPAcceptedApps" />
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfEntAcceptedApps" />
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfVZAppZoneAcceptedApps" />
        </strong></td>
        <td align="right"><strong>
            0
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="totalAcceptedApps" />
        </strong></td>
    </tr>
    <tr>
        <td>No. of Rejected Applications</td>
        <td><strong>
            <bean:write name="loginInfo" property="noOfBrewRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfSMSRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfMMSRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfWAPRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfEntRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="noOfVZAppZoneRejectedApps"/>
        </strong></td>
        <td align="right"><strong>
            0
        </strong></td>
        <td align="right"><strong>
            <bean:write name="loginInfo" property="totalRejectedApps"/>
        </strong></td>
    </tr>
    <tr>
        <td><img src="images/greyRndArrow.gif" width="17" height="17" align="absmiddle"><a href="/aims/applicationsViewDelete.do?task=view&app_type=all_apps&cancel_search"><strong>All Applications</strong></a></td>
        <td>&nbsp;</td>
    </tr>
    </table>
    </DIV>
    </DIV>
</logic:present>
<!-- END IF VZW USER PRESENT-->

</logic:notPresent>
</div>
<!-- CONTENT END HERE -->
