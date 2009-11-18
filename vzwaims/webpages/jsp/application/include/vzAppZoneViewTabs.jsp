<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
<table cellpadding="0" cellspacing="0" width="100%"><tr><td>
<div id="divTabs" class="tab">	
	
	<%if (isPage1) {%>
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	<% } else { %>
		<div class="tabinActive">
	<% } %>
			<a class="a" href='/aims/vzAppZoneApplicationSetup.do?task=view&appsId=<bean:write name="VZAppZoneApplicationUpdateForm" property="appsId" />' >
                <bean:message key="ManageApplicationForm.tab.application.info"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div>
	<%if (isPage1 == false) {%>
		<div class="divider"> </div>
	<%}%>
		
	<%if (isPage3) {%>
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	<% } else { %>
		<div class="tabinActive">
	<% } %>			
           <a class="a" href='/aims/vzAppZoneApplicationSetup.do?task=view&viewPageToView=binariesView&appsId=<bean:write name="VZAppZoneApplicationUpdateForm" property="appsId" />' >
               Binaries
           </a>
		</div>
	<%if (isPage3 == false) {%>
		<div class="divider"> </div>
	<%}%>
			
	<%if (isVerizonUser) {%>		
		<%if (isProcessingInfo) {%>
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		<% } else { %>
			<div class="tabinActive">
		<% } %>				
              <a class="a" href='/aims/vzAppZoneApplicationSetup.do?task=view&viewPageToView=processingInfo&appsId=<bean:write name="VZAppZoneApplicationUpdateForm" property="appsId" />' >
                  <bean:message key="ManageApplicationForm.tab.processing.info"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
              </a>
			</div>
		<%if (isProcessingInfo == false) {%>
			<div class="divider"> </div>
		<%}%>					
    <% }//end if Verizon User %>

    <%  if(hasAccessJournalView) { %>
        <%if (isVZAppZoneJournal) { %>
            <div class="tabActiveBegin"> </div>
            <div class="tabActive">
        <% } else { %>
            <div class="tabinActive">
        <% } %>
                <a class="standardSmallLink" href='/aims/vzAppZoneApplicationSetup.do?task=view&viewPageToView=journal&appsId=<bean:write name="VZAppZoneApplicationUpdateForm" property="appsId" />' >
                    <bean:message key="ManageApplicationForm.tab.journal"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </a>
            </div>
        <%if (isVZAppZoneJournal == false) {%>
            <div class="divider"> </div>
        <%}%>
    <%}//end hasAccessJournalView%>

</div>
</td></tr></table>
<div>&nbsp;</div>
<table width="100%"  style="float:left" border="0" cellpadding="0" cellspacing="0">
	<tr>
        <td><strong>Application Name:&nbsp;</strong><aims:getTruncatedString name="VZAppZoneApplicationUpdateForm" property="appTitle" maxLength="50" fullTextVarName="title_fullText_view"/></td>
        <td><strong>By:&nbsp;</strong><bean:write  name="VZAppZoneApplicationUpdateForm"    property="allianceName"    /></td>
		<td><strong>Vendor ID:&nbsp;</strong><bean:write  name="VZAppZoneApplicationUpdateForm"    property="vendorId"    /></td>
		<td><strong>Status:&nbsp;</strong><bean:write  name="VZAppZoneApplicationUpdateForm"    property="applicationStatus"    /></td>
	</tr>
</table>
<div>&nbsp;</div>
