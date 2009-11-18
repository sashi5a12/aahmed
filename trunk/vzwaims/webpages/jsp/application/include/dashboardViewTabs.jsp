<table cellpadding="0" cellspacing="0" width="100%"><tr><td>
<div id="divTabs" class="tab">

	<%if (isPage1) {%>
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	<% } else { %>
		<div class="tabinActive">
	<% } %>
            <a href='/aims/dashboardApplicationSetup.do?task=view&appsId=<bean:write name="DashboardApplicationUpdateForm" property="appsId" />'>
                <bean:message key="ManageApplicationForm.tab.application.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
            </a>
		</div>
	<%if (isPage1 == false) {%>
		<div class="divider"> </div>
	<%}%>


	<%if (isVerizonUser) {%>
		<%if (isPage3) {%>
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		<% } else { %>
			<div class="tabinActive">
		<% } %>
                <a href='/aims/dashboardApplicationSetup.do?task=view&viewPageToView=processingInfo&appsId=<bean:write name="DashboardApplicationUpdateForm" property="appsId" />'>
                    <bean:message key="ManageApplicationForm.tab.processing.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </a>
            </div>
		<%if (isPage3 == false) {%>
			<div class="divider"> </div>
		<%}%>
	<% } %>
    <%if (isPage5) {%>
        <div class="tabActiveBegin"> </div>
        <div class="tabActive">
    <% } else { %>
        <div class="tabinActive">
    <% } %>
            <a  href='/aims/dashboardApplicationSetup.do?task=view&viewPageToView=page5View&appsId=<bean:write name="DashboardApplicationUpdateForm" property="appsId" />'>
                <bean:message key="ManageApplicationForm.tab.userguide.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
            </a>
        </div>
    <%if (isPage5 == false) {%>
        <div class="divider"> </div>
    <%}%>
    <%if (isPage4) {%>
        <div class="tabActiveBegin"> </div>
        <div class="tabActive">
    <% } else { %>
        <div class="tabinActive">
    <% } %>
            <a  href='/aims/dashboardApplicationSetup.do?task=view&viewPageToView=journal&appsId=<bean:write name="DashboardApplicationUpdateForm" property="appsId" />'>
                <bean:message key="ManageApplicationForm.tab.journal" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
            </a>
        </div>
    <%if (isPage4 == false) {%>
        <div class="divider"> </div>
    <%}%>

</div>
</td></tr></table>
<div>&nbsp;</div>
<table width="100%"  style="float:left" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="33%">
			<strong>Channel Title: </strong><bean:write	name="DashboardApplicationUpdateForm"	property="title"	/>
		</td>
		<td width="33%">
			<strong>By: </strong><bean:write  name="DashboardApplicationUpdateForm"    property="allianceName"    />
		</td>
		<td width="33%">
			<strong>Status: </strong><bean:write  name="DashboardApplicationUpdateForm"    property="applicationStatus"    />
		</td>
	</tr>

</table>
<div>&nbsp;</div>
