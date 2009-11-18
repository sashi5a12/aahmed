<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
       		<span class="aimsmasterheading">
       		   <bean:message key="EventNotificationForm.Welcome"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
       		</span>               
       	</td>
 	</tr>
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">&nbsp;
   	</td>
  	</tr>
  	<%@ include  file="/common/error.jsp" %>
  	<tr> 
    	<td width="20">&nbsp;</td>	
      	<td  width="100%" align="center" valign="middle" bgcolor="#FFFFFF"> 
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                    <td width="100%">
					    <table class="sectable" width="100%">
                            <tr class="sectitle">
                               <td colspan="2" class="aimssecheading">
								  <bean:message key="EventNotificationForm.ViewHeading"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
				               </td>
    				       </tr>
						 <tr>
							<td class="modFormFieldLbl"><bean:message key="EventNotificationForm.Name" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:</td>
						 </tr>	
						 <tr>
							<td class="text"><bean:write name="EventNotificationForm" property="notificationName" /></td>
						 </tr>
						  <tr>
							<td class="modFormFieldLbl"><bean:message key="EventNotificationForm.Event" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:</td>
						 </tr>	
						 <tr>
							<td class="text"><bean:write name="EventNotificationForm" property="eventName" /></td>
						 </tr> 
						  <tr>
							<td class="modFormFieldLbl"><bean:message key="EventNotificationForm.Email" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:</td>
						 </tr>	
						 <tr>
							<td class="text"><bean:write name="EventNotificationForm" property="emailMessageTitle" /></td>
						 </tr> 
						  <tr>
							<td class="modFormFieldLbl"><bean:message key="EventNotificationForm.Status" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:</td>
						 </tr>	
						 <tr>
							<td class="text"><bean:write name="EventNotificationForm" property="status" /></td>
						 </tr> 
						  <tr>
							<td class="modFormFieldLbl"><bean:message key="EventNotificationForm.Desc" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:</td>
						 </tr>	
						 <tr>
							<td class="text"><bean:write name="EventNotificationForm" property="notificationDesc" /></td>
						 </tr> 
						 <tr>
							<td class="modFormFieldLbl"><bean:message key="EventNotificationForm.EmailGroup" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:</td>
						 </tr>	
						 <tr>
							<td class="text">
							<logic:iterate id="roleGroup" name="EventNotificationForm" property="allRoleGroups" type="com.netpace.aims.model.masters.AimsSysRol">
                               <bean:write name="roleGroup" property="roleName" />
                                <BR/>	
						   </logic:iterate>
						   <logic:empty	name="EventNotificationForm" property="allRoleGroups" scope="request">
						       No Assigned Roles Found
						   </logic:empty>
						   	 												
							</td>
						 </tr>
						
		  		    </table>	
      		    </td>
                </tr>
			</table>
		</td>
	</tr>            
    <tr>
       <td width="20">&nbsp;</td>
	   <td width="100%">
	     <table  width="100%">
		 <tr>
             <td align="left">
         		 	<a href='eventNotificationViewDelete.do?task=view' class='a'>
				      	<img src="images/back_b.gif"  border="0" alt="Back">
					</a>
       		 </td>
             <td align="right">   
                  <a href='eventNotificationSetup.do?task=edit&notificationId=<bean:write name="EventNotificationForm" property="notificationId"/>' class='a'>
                     	<img src="images/edit_b.gif"  border="0" alt="Edit">
				   </a> 	
             </td>
         </tr>
	     </table>
	 </td>
    </tr>
</table>


