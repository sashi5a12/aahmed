<table cellpadding="0" cellspacing="0" width="100%"><tr><td>
<div id="divTabs" class="tab">

	<%if (isPage1) {%>
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	<% } else { %>
		<div class="tabinActive">
	<% } %>
            <a href='/aims/wapApplicationSetup.do?task=view&appsId=<bean:write name="WapApplicationUpdateForm" property="appsId" />'>
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
                <a href='/aims/wapApplicationSetup.do?task=view&viewPageToView=processingInfo&appsId=<bean:write name="WapApplicationUpdateForm" property="appsId" />'>
                    <bean:message key="ManageApplicationForm.tab.processing.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </a>
            </div>
		<%if (isPage3 == false) {%>
			<div class="divider"> </div>
		<%}%>
	<% } %>
    <%if (isPage4) {%>
        <div class="tabActiveBegin"> </div>
        <div class="tabActive">
    <% } else { %>
        <div class="tabinActive">
    <% } %>
            <a  href='/aims/wapApplicationSetup.do?task=view&viewPageToView=journal&appsId=<bean:write name="WapApplicationUpdateForm" property="appsId" />'>
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
		<td width="25%">
			<strong>Short Product Name: </strong><bean:write	name="WapApplicationUpdateForm"	property="title"	/>
		</td>
		<td width="25%">
			<strong>By: </strong><bean:write  name="WapApplicationUpdateForm"    property="allianceName"    />
		</td>
		<td width="25%">
			(<strong>Vendor ID: </strong><bean:write  name="WapApplicationUpdateForm"    property="vendorId"    />)
		</td>
		<td width="25%">
			<strong>Status: </strong><bean:write  name="WapApplicationUpdateForm"    property="applicationStatus"    />
		</td>
	</tr>
	<tr><td colspan="4">&nbsp;</td></tr>
</table>
