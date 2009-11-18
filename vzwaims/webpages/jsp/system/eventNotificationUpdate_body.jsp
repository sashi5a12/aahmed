 <%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<script  language="JavaScript">

	function truncateLocalTextAreas()
	{
		TruncateTextWithCount(document.forms[0].notificationDesc,'textCountNotificationDesc',500);
	}

	function trackCountForTextAreas()
	{
		TrackCount(document.forms[0].notificationDesc,'textCountNotificationDesc',500);
	}

</script>

<html:form action="/eventNotificationInsertUpdate" >	
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
       		<span class="aimsmasterheading">
       			<bean:message key="EventNotificationForm.Welcome" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
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
			
	    		<logic:match  parameter="task" value="create"> 
    			   <html:hidden property="task" value="create"/>
		        </logic:match>  
			    <logic:match  parameter="task" value="edit"> 
				   <html:hidden  property="notificationId"/>
       			   <html:hidden property="task" value="edit"/>
		        </logic:match>  
		  
				  <html:hidden  property="createdBy"/>
				  <html:hidden  property="createdDate"/>
			
						
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                    <td width="100%">
					    <table class="sectable" width="100%">
                            <tr class="sectitle">
                                <td colspan="2" class="aimssecheading">
								   
								       <logic:match parameter="task"	 value="create"> 
											<bean:message key="EventNotificationForm.CreateHeading"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
									   </logic:match>  
			   
									   <logic:match parameter="task"	 value="edit"> 
											<bean:message key="EventNotificationForm.EditHeading"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
									   </logic:match>  							
								
								</td>
                            </tr>
                            <tr>
                                <td class="modFormFieldLbl">
							      <bean:message key="EventNotificationForm.Name"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/><span class="mainReqAstrx">*</span>:
					 			</td>
                                <td class="text"><html:text property="notificationName" size="30" maxlength="50"  /></td>
                            </tr>
                            <tr>
								<td class="modFormFieldLbl" valign="top">
									<bean:message key="EventNotificationForm.Event"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/><span class="mainReqAstrx">*</span>:
								</td>
								<td class="text">
									  <html:select  property="eventId" size="1">
									        <html:option value=""><bean:message key="message.lable.selectOne"/></html:option>
											<html:optionsCollection  name="EventNotificationForm"  property="eventList" label="eventName"  value="eventId" /> 
									 </html:select>								
								</td>
                            </tr>
							<tr>
								<td class="modFormFieldLbl" valign="top">
									<bean:message key="EventNotificationForm.Email"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/><span class="mainReqAstrx">*</span>:
								</td>
								<td class="text">
									  <html:select  property="emailMessageId" size="1">
									        <html:option value=""><bean:message key="message.lable.selectOne"/></html:option>
											<html:optionsCollection name="EventNotificationForm" property="emailMessageList" label="emailTitle"  value="emailMessageId" />              
									 </html:select>								
								</td>
						    </tr>
    						<tr>
								<td class="modFormFieldLbl" valign="top">
									<bean:message key="EventNotificationForm.Status"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/><span class="mainReqAstrx">*</span>:
								</td>
								<td class="text">
									<html:select  property="status" size="1">
									    <html:option value=""><bean:message key="message.lable.selectOne"/></html:option>
								 		<html:option value="A">Active</html:option>
								 		<html:option value="I">InActive</html:option>   		
									</html:select>								
								</td>
						    </tr>
                           	<tr>
                                <td class="modFormFieldLbl"> <bean:message key="EventNotificationForm.Desc"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:</td>
                                <td class="text"> 
								<html:textarea property="notificationDesc" rows="5" cols="30" onkeyup="TrackCount(this,'textCountNotificationDesc',500)" onkeypress="LimitText(this,500)"/><br>
								<bean:message key="message.textarea.char.remaining"/>
								<input type="text" name="textCountNotificationDesc" size="3" value="500" disabled="true" />
								</td>
                            </tr>
							<tr> 
								<td class="modFormFieldLbl"><bean:message key="EventNotificationForm.EmailGroup" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:</td>
							    <td  valign=top">
										<table width="80%" border="0" cellspacing="10" cellpadding="0">
                  						  <tr>		
												<td  width="35%" class="text" align="left">
									 		         <html:select property="roleGroupList" size="5" multiple="true">
													     <logic:iterate id="roleGroup" name="EventNotificationForm" property="roleGroupList" type="com.netpace.aims.model.masters.AimsSysRol">
                                                            <html:option value="<%=roleGroup.getRoleId().toString()%>"><bean:write name="roleGroup" property="roleName" /></html:option> 
                                                            <BR/>	
                                                          </logic:iterate> 
											  	     </html:select>
												</td>
												<td	width="10%" align="center">
		                                            <img src="images/next_icon.gif" border="0" onClick="copyToList('roleGroupList', 'roleGroupAssigned')"/>
        		                                    <br>
                		                            <img src="images/previous_icon.gif" border="0" onClick="copyToList('roleGroupAssigned', 'roleGroupList')"/>
												</td>
												<td  width="35%" class="text" align="left">
													<html:select name="EventNotificationForm" property="roleGroupAssigned" size="5" multiple="true">
													   <logic:notEmpty	name="EventNotificationForm" property="allRoleGroups" scope="request">
     										               <logic:iterate id="roleGroup" name="EventNotificationForm" property="allRoleGroups" type="com.netpace.aims.model.masters.AimsSysRol">
                                                                   <html:option value="<%=roleGroup.getRoleId().toString()%>"><bean:write name="roleGroup" property="roleName" /></html:option> 
                                                                    <BR/>	
														   </logic:iterate>	 												
                                                       </logic:notEmpty> 
    												</html:select>
												</td>
											</tr>
										</table>
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
               <img src="images/spacer.gif" width="10" height="1">
                    <input type="image" src="images/save_b.gif" border="0" alt="save"  onClick="select_all(document.forms[0].roleGroupAssigned);">
             </td>
        </tr>
	    </table>
	</td>
	
   </tr>
</table>
</html:form>
<script	language="javascript">
   trackCountForTextAreas();
</script>


	

