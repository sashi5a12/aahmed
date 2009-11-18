<table cellpadding="0" cellspacing="0" width="100%"><tr><td>
<div id="divTabs" class="tab">	
	
	<%if (isPage1) {%>
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	<% } else { %>
		<div class="tabinActive">
	<% } %>
			<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page1';document.forms[0].submit();" >
				<bean:message key="ManageApplicationForm.tab.basic.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div>
	<%if (isPage1 == false) {%>
		<div class="divider"> </div>
	<%}%>
	

	<%if (isPage2) {%>
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	<% } else { %>
		<div class="tabinActive">
	<% } %>
			<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page2';document.forms[0].submit();" >
				<bean:message key="ManageApplicationForm.tab.additional.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div>
	<%if (isPage2 == false) {%>
		<div class="divider"> </div>
	<%}%>

	<%if (isPage5) {%>
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	<% } else { %>
		<div class="tabinActive">
	<% } %>
			<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page5';document.forms[0].submit();" >
				<bean:message key="ManageApplicationForm.tab.userguide.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</a>
		</div>
	<%if (isPage5 == false) {%>
		<div class="divider"> </div>
	<%}%>
	
	<%if (isVerizonUser) {%>									
		<%if (isPage3) {%>
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		<% } else { %>
			<div class="tabinActive">
		<% } %>
				<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page3';document.forms[0].submit();" >
					<bean:message key="ManageApplicationForm.tab.processing.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				</a>
			</div>
		<%if (isPage3 == false) {%>
			<div class="divider"> </div>
		<%}%>
		
					
		<%if (isPage4) {%>
			<div class="tabActiveBegin"> </div>
			<div class="tabActive">
		<% } else { %>
			<div class="tabinActive">
		<% } %>
				<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page4';document.forms[0].submit();" >
					<bean:message key="ManageApplicationForm.tab.journal"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				</a>
			</div>
		<%if (isPage4 == false) {%>
			<div class="divider"> </div>
		<%}%>			
	<% } %>	
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

