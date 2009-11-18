<table cellpadding="0" cellspacing="0" width="100%"><tr><td>
<div id="divTabs" class="tab">

	<%if (isPage1) {%>
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	<% } else { %>
		<div class="tabinActive">
	<% } %>
            <a href='/aims/javaApplicationSetup.do?task=view&appsId=<bean:write name="javaApplicationUpdateForm" property="appsId" />'>
                <bean:message key="ManageApplicationForm.java.tab.application.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
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
                <a href='/aims/javaApplicationSetup.do?task=view&viewPageToView=processingInfo&appsId=<bean:write name="javaApplicationUpdateForm" property="appsId" />'>
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
            <a  href='/aims/javaApplicationSetup.do?task=view&viewPageToView=page5View&appsId=<bean:write name="javaApplicationUpdateForm" property="appsId" />'>
                <bean:message key="ManageApplicationForm.java.tab.userguide.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
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
            <a  href='/aims/javaApplicationSetup.do?task=view&viewPageToView=journal&appsId=<bean:write name="javaApplicationUpdateForm" property="appsId" />'>
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
		<td width="28%">
			<strong>Application Name: </strong><bean:write	name="javaApplicationUpdateForm"	property="title"	/>
		</td>
		<td width="21%">
			<strong>By: </strong><bean:write  name="javaApplicationUpdateForm"    property="allianceName"    />
		</td>
		<td width="23%">
			<strong>Vendor ID: </strong><bean:write  name="javaApplicationUpdateForm"    property="vendorId"    />
		</td>
		<td width="28%">
			<strong>Status: </strong><bean:write  name="javaApplicationUpdateForm"    property="applicationStatus"    />
		</td>
	</tr>

</table>
<div>&nbsp;</div>
