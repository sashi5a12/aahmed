<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
	    <td width="20">&nbsp;</td>
		<td width="100%">
            <span	class="aimsmasterheading"><bean:message key="EventNotificationForm.Welcome"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
		</td>
	</tr>
    <tr> 
       <td width="20">&nbsp;</td>
       <td width="100%">&nbsp;
            
       </td>
    </tr>
	<%@ include  file="/common/error.jsp" %>
	<tr>
		<td	width="20">&nbsp;</td>
    	<td width="100%" align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" valign=top">
						<table class="sectable" width="100%" height="100%">
						     <tr class="sectitle"><td colspan="2" class="aimssecheading"><bean:message key="EventNotificationForm.view.filter" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></td></tr>
									<tr bgcolor="#BBBBBB"> 
										<td colspan=2 align="right" class="text">
											<table cellspacing="5" cellpadding="0" >
												<html:form action="/eventNotificationViewDelete.do?task=view">
												<tr>
													<td class="modFormFieldLbl">
													       <bean:message key="EventNotificationForm.view.filterBy" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:
													</td>
													<td class="modFormFieldLbl">
  														<bean:define id="filterCol" type="java.lang.String" name="EventNotificationForm" property="filterField" />
														<html:select property="filterField" size="1" value="<%=filterCol%>" >
															<html:option value="notificationName">Notification Name</html:option>
															<html:option value="eventName">Event Name</html:option>
														</html:select>
													</td>
													<td class="modFormFieldLbl">
											           <bean:message key="EventNotificationForm.view.filterText" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:
													</td>
													<td class="inputbox">
													   <html:text property="filterText" size="15"	/>
													</td>
													<td class="body" align="right">
													   <input type="image" src="images/go_b.gif" width="27" height="18">
													</td>
												</tr>
												</html:form>
											</table>
										</td>
									</tr>
								 <tr class="sectitle">
                               		 <td colspan="2" class="aimssecheading">
                                   		 <bean:message key="EventNotificationForm.ListHeading" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
                               		 </td>
                           		 </tr>
                			<tr>
                    		  <td colspan="2" align="center" valign="middle" bgcolor="#EBEBEB"> 
				             <table width="100%" border="0" cellspacing="0" cellpadding="0">		
			      			            <tr>
			      			      	        <td bgcolor="#EBEBEB">
			      		      		            <table width="100%" border="0" class="tabletop" cellspacing="0" cellpadding="2" >
			      	      			                <tr bgcolor="#BBBBBB">
			                                            <td class="firstcell" align="center">
			                                                <span class="modFormFieldLbl"><bean:message key="EventNotificationForm.Name" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
			                                            </td>
														<td class="cell" align="center">
			                                                <span class="modFormFieldLbl"><bean:message key="EventNotificationForm.Event" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
			                                            </td>
														<td class="cell" align="center">
			                                                <span class="modFormFieldLbl"><bean:message key="EventNotificationForm.Email" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
			                                            </td>
														<td class="cell" align="center">
			                                                <span class="modFormFieldLbl"><bean:message key="EventNotificationForm.Edit"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
			                                            </td>	
														<td class="cell" align="center">
			                                                <span class="modFormFieldLbl"><bean:message key="EventNotificationForm.Delete"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
			                                            </td>
			                            	        </tr>

													<logic:iterate id="eventNotification" name="eventNotificationList">
													<tr>
														<td class="firstcell" align="left">
															<a href='eventNotificationSetup.do?task=DetailView&notificationId=<bean:write name="eventNotification" property="notificationId"/>'>
															     <bean:write name="eventNotification" property="notificationName" />
															 </a>																
														</td>
														<td class="cell" align="left">
															<bean:write name="eventNotification" property="eventName" />
														</td>
														<td class="cell" align="left">	
															<bean:write name="eventNotification" property="emailMessageTitle" formatKey="date.format" filter="true" /> 				
														</td>
														<td class="cell" align="center">
														   <a href='eventNotificationSetup.do?task=edit&notificationId=<bean:write name="eventNotification" property="notificationId"/>' class="standardLink">
  												            	<img src="images/edit_icon.gif"  border="0" alt="Edit">
											               </a>
														</td>
														<td class="cell" align="center">
														    <a href='eventNotificationViewDelete.do?task=delete&notificationId=<bean:write name="eventNotification" property="notificationId"/>' class="standardLink">
                                                                <img src="images/delete_icon.gif"  border="0" alt="Delete">
	   											             </a>
  														 </td>
												     </tr>											
													</logic:iterate>
													<logic:greaterThan  name="page_max" value="1" >
														<tr>
														<td colspan="7" align="right">
															<table >
																<tr align="right"> 
																	<td>
																	<logic:greaterThan  name="page_id" value="1" >
																		<a href='eventNotificationViewDelete.do?task=view&page_id=<%=page_id.intValue() - 1%>&filterField=<bean:write name="EventNotificationForm" property="filterField" />&filterText=<bean:write name="EventNotificationForm" property="filterText" />' class="a">
																			<bean:message key="images.previous.icon" />
																		</a>
																	</logic:greaterThan>
																	</td> 
																	<logic:greaterThan  name="page_max" value="1" >
																		<td class="text" align="center"> Page <%=page_id.toString()%> of <%=page_max.toString()%> </td>
																	</logic:greaterThan>
																	<td>
																	 <logic:lessThan name="page_id" value="<%=page_max.toString()%>" >
																		<a href='eventNotificationViewDelete.do?task=view&page_id=<%=page_id.intValue() + 1%>&filterField=<bean:write name="EventNotificationForm" property="filterField" />&filterText=<bean:write name="EventNotificationForm" property="filterText" />' class="a">
																		   <bean:message key="images.next.icon" />
																		</a>
																	 </logic:lessThan>
																	</td>
																</tr>
																</table>
																</td>
															</tr>
											 		 </logic:greaterThan>
 			                                    </table>
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
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">                        
    <tr>
        <td width="20">&nbsp;</td>
        <td width="100%" align="right">
           <img src="images/spacer.gif" width="10" height="1">
           <a href='eventNotificationSetup.do?task=create' class="a">
            	<img src="images/create_b.gif"  border="0" alt="Create">
           </a>
        </td>
    </tr>
</table>
	
			

