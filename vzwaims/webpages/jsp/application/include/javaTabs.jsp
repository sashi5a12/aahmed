
<%@page import="com.netpace.aims.util.CommonProperties"%><table cellpadding="0" cellspacing="0" width="100%"><tr><td>
<div id="divTabs" class="tab">
	<%if (isPage1) {%>
		<div class="tabActiveBegin"> </div>
		<div class="tabActive">
	<% } else { %>
		<div class="tabinActive">
	<% } %>
			<a class="a" href="javascript:submitForm();document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='page1';document.forms[0].submit();" >
				<bean:message key="ManageApplicationForm.java.tab.basic.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
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
				<bean:message key="ManageApplicationForm.java.tab.additional.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
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
				<bean:message key="ManageApplicationForm.java.tab.userguide.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
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
					<bean:message key="ManageApplicationForm.tab.adminedit.processing.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
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
					<bean:message key="ManageApplicationForm.tab.admin.edit.journal"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
				</a>
			</div>
		<%if (isPage4 == false) {%>
			<div class="divider"> </div>
		<%}%>			
	<% } %>	
</div>
</td></tr></table>
<div>&nbsp;</div>
<jsp:useBean id="javaApplicationUpdateForm" class="com.netpace.aims.controller.application.JavaApplicationUpdateForm" scope="request" />

<% 
	String vcastHelpUrl = CommonProperties.getInstance().getProperty("vcast.apps.help.url");
%>

<table width="100%"  style="float:left" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="32%">
			<strong>Application Name: </strong><%=Utility.getEllipseString(20,javaApplicationUpdateForm.getTitle())%>
		</td>
		<td width="21%">
			<strong>By: </strong><bean:write  name="javaApplicationUpdateForm"    property="allianceName"    />
		</td>
		<td width="19%">
			<strong>Vendor ID: </strong><bean:write  name="javaApplicationUpdateForm"    property="vendorId"    />
		</td>
		<% 
		if (isPage1){
		%>
		<td width="23%">
		<%} else {%>
		<td width="28%">
		<%} %>
			<strong>Status: </strong><bean:write  name="javaApplicationUpdateForm"    property="applicationStatus"    />
		</td>
		<%
		if (isPage1) 
		{
		%>
		
			<td width="5%">
				<div style="text-align: right;">
					<a href="<%=vcastHelpUrl%>" target="_blank" >Help</a>
				</div>
			</td>
		<%
		}
		 %>
	</tr>

</table>
<div>&nbsp;</div>

