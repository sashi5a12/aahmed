<table cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>
			<div id="divTabs" class="tab">
				<%
				if (isPage1) 
				{
					out.print("<div class=\"tabActiveBegin\"></div>");
					out.print("<div class=\"tabActive\">");
				} 
				else 
					out.print("<div class=\"tabinActive\">");
				%>
						<a href='/aims/javaApplicationSetup.do?task=view&appsId=<bean:write name="javaApplicationUpdateForm" property="appsId" />'>
							<bean:message key="ManageApplicationForm.tab.java.application.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
											   
						</a>
					</div>

				<%
				if (isPage1 == false) 
					out.print("<div class=\"divider\"></div>");
				%>


				<%
				if (isPage5) 
				{
					out.print("<div class=\"tabActiveBegin\"></div>");
					out.print("<div class=\"tabActive\">");
				} 
				else 
					out.print("<div class=\"tabinActive\">");
				%>
				
						<a href='/aims/javaApplicationSetup.do?task=view&viewPageToView=page5View&appsId=<bean:write name="javaApplicationUpdateForm" property="appsId" />'>
							<bean:message key="ManageApplicationForm.tab.java.userguide.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
						</a>
					</div>
				
				<%
				if (isPage5 == false) 
					out.print("<div class=\"divider\"></div>");
				%>

<%-- 
				<%
				if (isPage2) 
				{
					out.print("<div class=\"tabActiveBegin\"></div>");
					out.print("<div class=\"tabActive\">");
				} 
				else 
					out.print("<div class=\"tabinActive\">");
				%>
						<a href='/aims/javaApplicationSetup.do?task=view&viewPageToView=page5View&appsId=<bean:write name="javaApplicationUpdateForm" property="appsId" />'>
							<bean:message key="ManageApplicationForm.tab.additional.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /> 
						</a>
					</div>

				<%
				if (isPage2 == false) 
					out.print("<div class=\"divider\"></div>");
				%>
--%>

				<%
				if (isVerizonUser) 
				{
				%>
					<%
					if (isPage3) 
					{
						out.print("<div class=\"tabActiveBegin\"></div>");
						out.print("<div class=\"tabActive\">");
					} 
					else 
						out.print("<div class=\"tabinActive\">");
					%>
							<a href='/aims/javaApplicationSetup.do?task=view&viewPageToView=processingInfo&appsId=<bean:write name="javaApplicationUpdateForm" property="appsId" />'>
								<bean:message key="ManageApplicationForm.tab.adminview.processing.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
							</a>
						</div>
					<%
					if (isPage3 == false) 
						out.print("<div class=\"divider\"></div>");
					%>
				<%
				}
				%>
				
				
				<%
				if (isPage4) 
				{
					out.print("<div class=\"tabActiveBegin\"></div>");
					out.print("<div class=\"tabActive\">");
				} 
				else 
					out.print("<div class=\"tabinActive\">");
				%>
						<a href='/aims/javaApplicationSetup.do?task=view&viewPageToView=journal&appsId=<bean:write name="javaApplicationUpdateForm" property="appsId" />'>
							<%
							if (isVerizonUser) 
							{
							%>
								<bean:message key="ManageApplicationForm.tab.admin.journal" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
							<%
							}else{							 
							%>
								<bean:message key="ManageApplicationForm.tab.alliance.journal" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
							<%
							}
							 %>
						</a>
					</div>
				<%
				if (isPage4 == false) 
					out.print("<div class=\"divider\"></div>");
				%>			
				
		</td>
	</tr>
</table>
<div>
	&nbsp;
</div>
<table width="100%" style="float:left" border="0" cellpadding="0"
	cellspacing="0">
	<tr>
		<td width="40%">
			<strong>Application Name: </strong>
			<%=Utility.getEllipseString(28,javaApplicationUpdateForm.getTitle())%>
		</td>
		<td width="20%">
			<strong>By: </strong>
			<bean:write name="javaApplicationUpdateForm" property="allianceName" />
		</td>
		<td width="20%">
			<strong>Vendor ID: </strong>
			<bean:write name="javaApplicationUpdateForm" property="vendorId" />
		</td>
		<td width="20%">
			<strong>Status: </strong>
			<bean:write name="javaApplicationUpdateForm" property="applicationStatus" />
		</td>
	</tr>

</table>
<div>
	&nbsp;
</div>
