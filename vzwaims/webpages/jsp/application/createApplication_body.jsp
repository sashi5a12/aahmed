<%@ page language="java"
         import="com.netpace.aims.bo.security.*, com.netpace.aims.bo.application.*, com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@page import="com.netpace.aims.dataaccess.valueobjects.AimsPlatformVO"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<script language="javascript">
    function createApplication(selectedIndex)
    {
        if (document.forms[0].createApp.options[document.forms[0].createApp.selectedIndex].value == 0)
            return false;
        document.forms[0].action = document.forms[0].createApp.options[document.forms[0].createApp.selectedIndex].value;
    }
    
    function renderNotes()
    {	
    	document.getElementById('addNotesDiv').style.visibility = 'hidden';
    	
    	if ( document.forms[0].createApp.options[document.forms[0].createApp.selectedIndex].value == '<bean:message key="ManageApplicationForm.java.ondeck.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create' )
    	{
    		document.getElementById('addNotesDiv').innerHTML = document.getElementById('addNotesVCASTTxt').innerHTML;
    		document.getElementById('addNotesDiv').style.visibility = '';
    	}  
    	else if ( document.forms[0].createApp.options[document.forms[0].createApp.selectedIndex].value == '<bean:message key="ManageApplicationForm.java.offdeck.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create' )
    	{
    		document.getElementById('addNotesDiv').innerHTML = document.getElementById('addNotesVCASTMoreTxt').innerHTML;
    		document.getElementById('addNotesDiv').style.visibility = '';
    	}
    }
</script>


<%@ include file="/common/error.jsp" %>


<%if (((AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE)) {%>

<DIV class="homeColumn lBox floatL">
        <DIV class="headLeftCurveblk"></DIV>
        <H1>Create New Application</H1>

        <DIV class="headRightCurveblk"></DIV>
        <DIV class="contentbox">

<form name="createSubmitForm" method="post">
	<table width="100%" cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td align="left" width="37%">
                <strong>
                    <bean:message key="ManageApplicationForm.view.createNewApp"
                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </Strong>
                <img alt="" src="images/spacer.gif" width="3" height="1"/>
                
              
                    
                <select name="createApp" onchange="renderNotes(this);">
                    <option value="0" selected>
                        <bean:message key="ManageApplicationForm.label.selectOne"
                                      bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    </option>                    
                    
              	  	<logic:iterate id="platform"	name="ApplicationsFilterForm" property="platforms" scope="request">
                    	<bean:define id="platformId"><bean:write name="platform" property="platformId"/></bean:define>                    	                         
                     
                    	<%if (ApplicationHelper.checkPlatformAccess(request, "create", Long.valueOf(platformId)) ) { %>
                    	
                    		<%if (AimsConstants.BREW_PLATFORM_ID.toString().equals( platformId ) )  {%>
                    			<option value="<bean:message key="ManageApplicationForm.brew.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			BREW
                    			</option>
                    		<%} %>
                    		
                    		<%if (AimsConstants.DASHBOARD_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.dashboard.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
					    			Dashboard 
								</option>
                    		<%} %>
                    		<%if (AimsConstants.ENTERPRISE_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.enterprise.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			JMA
                    			</option>
                    		<%} %>
                    		<%if (AimsConstants.MMS_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.mms.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			MMS
                    			</option>
                    		<%} %>
                    		<%if (AimsConstants.SMS_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.sms.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			SMS
                    			</option>	
                    		<%} %>
                    		<%if (AimsConstants.VCAST_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.vcast.app.setup.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			V CAST Video
                    			</option>	
                    		<%} %>
                    		<%if (AimsConstants.VZAPPZONE_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.vzAppZone.app.setup.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			VZAppZone
                    			</option>
                    		<%} %>
                    		<%if (AimsConstants.WAP_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.wap.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			WAP
                    			</option>	
                    		<%} %>
                    		<%if (AimsConstants.JAVA_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.java.ondeck.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			V CAST Apps
                    			</option>
                    			<option value="<bean:message key="ManageApplicationForm.java.offdeck.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			V CAST Apps More
                    			</option>
                    		<%} %>
                    		
                    	<%} %>
                    </logic:iterate>
                    
                    <%-- 
                    <%if (ApplicationHelper.checkPlatformAccess(request, "create", AimsConstants.BREW_PLATFORM_ID)) {%>
                    <option value="<bean:message key="ManageApplicationForm.brew.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        BREW
                    </option>
                    <% } else {
                    }%>
                    <%if (ApplicationHelper.checkPlatformAccess(request, "create", AimsConstants.DASHBOARD_PLATFORM_ID)) {%>
					<option value="<bean:message key="ManageApplicationForm.dashboard.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
					    Dashboard 
					</option>
					<% } else {
					}%>
                    <%if (ApplicationHelper.checkPlatformAccess(request, "create", AimsConstants.ENTERPRISE_PLATFORM_ID)) {%>
                    <option value="<bean:message key="ManageApplicationForm.enterprise.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        JMA
                    </option>
                    <% } else {
                    }%>
                    <%if (ApplicationHelper.checkPlatformAccess(request, "create", AimsConstants.MMS_PLATFORM_ID)) {%>
                    <option value="<bean:message key="ManageApplicationForm.mms.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        MMS
                    </option>
                    <% } else {
                    }%>
                    <%if (ApplicationHelper.checkPlatformAccess(request, "create", AimsConstants.SMS_PLATFORM_ID)) {%>
                    <option value="<bean:message key="ManageApplicationForm.sms.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        SMS
                    </option>
                    <% } else {
                    }%>
                    <%if (ApplicationHelper.checkPlatformAccess(request, "create", AimsConstants.VCAST_PLATFORM_ID)) {%>
                    <option value="<bean:message key="ManageApplicationForm.vcast.app.setup.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        V CAST Video
                    </option>
                    <% } else {
                    }%>
                    <%if (ApplicationHelper.checkPlatformAccess(request, "create", AimsConstants.VZAPPZONE_PLATFORM_ID)) {%>
                    <option value="<bean:message key="ManageApplicationForm.vzAppZone.app.setup.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        VZAppZone
                    </option>
                    <% } else {}%>
                    <%if (ApplicationHelper.checkPlatformAccess(request, "create", AimsConstants.WAP_PLATFORM_ID)) {%>
                    <option value="<bean:message key="ManageApplicationForm.wap.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        WAP
                    </option>
                    <% } else {}%>
                    <%if (ApplicationHelper.checkPlatformAccess(request, "create", AimsConstants.JAVA_PLATFORM_ID)) {%>
                    <option value="<bean:message key="ManageApplicationForm.java.ondeck.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        V CAST Apps
                    </option>
                    <% } else {}%>
                    <%if (ApplicationHelper.checkPlatformAccess(request, "create", AimsConstants.JAVA_PLATFORM_ID)) {%>
                    <option value="<bean:message key="ManageApplicationForm.java.offdeck.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        V CAST Apps More
                    </option>
                    <% } else {}%>
                    --%>
                </select>
            </td>
            <td align="left" width="67%">
                <div class="redBtn" id="Create " title="Create Application">
                	<div>
                    	<div><div onClick="createApplication(document.forms[0].createApp.selectedIndex); document.forms.createSubmitForm.submit();">Create</div></div>
                	</div>
            	</div>
            </td>
        </tr>
        <tr>
        	<td colspan="2" width="100%">
        		<div id="addNotesDiv" class="alertMsgYlw" style="visibility: hidden; width: 750px;">
        		</div>				
				<span id="addNotesVCASTTxt" style="visibility: hidden;">
				<p>By selecting "V CAST Apps" you are requesting premium placement in the V CAST Apps Store.  Your application will go through a more detailed evaluation and approval process prior to launch.  This approval process does not guarantee placement within the V CAST Apps mobile and web storefronts.

				<br><br>For additional information on testing and certification of applications, please see the SmartPhones Dev Center under Go To Market.
				
				<br><br>To set up your account to receive payment for your V CAST App, please go to the important information section on the left navigation bar and download the VCAST Apps vendor setup instruction file.  Please follow all instructions carefully.</p>				
				</span>
				<span id="addNotesVCASTMoreTxt" style="visibility: hidden;">
				<p>By selecting "V CAST Apps More", your application will go through a standard review and approval process and be placed in the "More" section of the appropriate storefront category.

				<br><br>For additional information on testing and certification of applications, please see the SmartPhones Dev Center under Go To Market.
				
				<br><br>To set up your account to receive payment for your V CAST App, please go to the important information section on the left navigation bar and download the VCAST Apps vendor setup instruction file.  Please follow all instructions carefully.</p>				
				</span>
        	</td>
        </tr>
    </table>
</form>

<% }%>
            
</div>
</div>








