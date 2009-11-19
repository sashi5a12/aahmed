<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<html>
 
 <%@ include file="../../../commons/taglibs.jsp"%>
 
 
<head>

<meta name="mainTitle" content="My Notifications" />

	<title>My Notifications</title>



</head>

 
 
 
<body>



<div id=homepageWrapper>
	<div id=bodyWrapper>
		<div id=homepageContainer>
			<a name=content></a>

			
			
<div id="contentCol">

		<!-- POD START HERE -->
		
		   <div class="box984">
        <div id="searchUsers">
        	
        	


<div class="box2">
                <div class="boxTop box984"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
                
			
			 <div class="boxContent">
			<!-- POD CONTENT START HERE -->
			<div class="subHead">
				Searching Filter
			</div>
			<div class="pR10">
		
		
		
		
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
		
					<tr>
						<td colspan="2">
		
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
		
								<tr>
		
									<s:form name="searchNotifications" action="myNotification" method="post" theme="simple" >
									
									<td>
										<table width="100%" border="0" cellspacing="1" cellpadding="0">
											<tr>
		
												<td>
													<table width="100%" border="0" cellpadding="3"
														cellspacing="0">
														<tr>
															<td class="boldText" colspan="2">
																Notification Title:
																<br />
																<s:textfield name="notificationToSearch.notificationTitle" maxlength="50" size="25" cssClass="inputFieldForm"/>
															</td>
															
															<td class="boldText">
		
																Event:
																<br />
																<s:select name="notificationToSearch.event.eventId" list="events" listValue="eventName" label="Event" listKey="eventId" headerValue="All" headerKey="-1" cssClass="inputFieldForm"/>
															</td>
															
															
															<td width="34%" valign="bottom">

															<button class="input_primary" type="submit">
																	<span><span><span>Search</span>
																	</span>
																	</span>
																</button>
															</td>
		
														</tr>
		
														
													</table>
												</td>
											</tr>
		
										</table>
									</td>
		
									</s:form>
		
		
		
		
								</tr>
							</table>
						</td>
					</tr>
		
				</table>
				<!-- ends here--->
				<td></td>
		
				</tr>
		
		
		
				</table>
		
		
				<div class="clear10"></div>
		
			</div>
			<!-- POD CONTENT END HERE -->
		</div>


 <div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
            </div>
        </div>
	
		<!-- POD END HERE -->
		<div class="clear14"></div>
		
		
		 <div class="clear14"></div>


	<%if(session.getAttribute("successMessage") != null) { %>
		<div class="infoBox">
					<tr>
						<td colspan="2" class="whiteText">
							&nbsp; ${sessionScope.successMessage}
						</td>
					</tr>
		</div>
		
	<div class="clear14"></div>
	
			<%} %>			
		
		

		</div>
		</div>
		
		
		
	
		      <div id="contentCol">
			  <s:form name="notificationsForm" action="saveMyNotification" method="post" theme="simple">
			  
			
      		<!-- POD START HERE -->
            
                    <!-- POD CONTENT START HERE -->
                                <div class="box984 float_left">
                <div class="box2">
                    <div class="boxTop box984"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
                  <div class="boxContent">
                    <div class="boxContBM">
                    <!-- POD CONTENT START HERE -->
					
					
         
                     <div class="clear10"></div>
					
					
						
			<!-- POD CONTENT START HERE -->
			<div class="pR10">
			
			
			<div><strong>Choose the notification you would like to Opt-out from.</strong></div>		
			<div class="clear10"></div>


				<table align="center" width="100%">
					<tr>

						<td>


							<display:table style="border:0px" name="myNotifications"
								requestURI="" pagesize="${sessionScope.loggedInUser.pageSize}"
								class="table_content" id="row">

								<display:column property="notification.notificationTitle" escapeXml="true"
									style="width: 88%" sortable="true" title="Notification Title" />
									
															
								<display:column style="width: 12%" title="Opt-Out">
									
									<c:if test="${row.isOptOut == true}">
										<input id="myNotification_notif" type="checkbox" checked="checked" value="<c:out value='${row.notification.notificationId}'/>" name="checkedNotifications"/>
									</c:if>
									<c:if test="${row.isOptOut == false}">
										<input id="myNotification_notif" type="checkbox" value="<c:out value='${row.notification.notificationId}'/>" name="checkedNotifications"/>
									</c:if>
									

									

								</display:column>
							</display:table>
						</td>
					</tr>
				</table>
				
				<div class="clear10"></div>
				
				<div>
					<c:if test="${newsLetterStatus == true}">
						<s:checkbox id="opt_out_newsletter" value="true" name="optOutNewsLetter"/>
					</c:if>
					
					<c:if test="${newsLetterStatus == false}">
						<s:checkbox id="opt_out_newsletter" value="false" name="optOutNewsLetter"/>
					</c:if>
									
				<strong>I want to opt-out from receiving newsletters.</strong></div>		
				<div class="clear10"></div>


			
			<!-- POD CONTENT END HERE -->
		</div>
					
					
				
 
						  <tr>
						    <td align="right">&nbsp;</td>
						    <td>
							    <div>							
									<BUTTON class="input_primary mR5" onClick="postNotificationForm();return false;"><SPAN><SPAN><SPAN>Submit</SPAN></SPAN></SPAN></BUTTON>
									<BUTTON class="input_secondary" onClick="javascript:cancel(); return false;"><SPAN><SPAN><SPAN>Cancel</SPAN></SPAN></SPAN></BUTTON>
				                </div>
			                </td>
						  </tr>
					
					
                   </table>
				 
                    <div class="clear10"></div>
                    <!-- POD CONTENT END HERE -->
                    </div>
                    </div>
                    <div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
                </div>
            </div>
         
                    
               
                   
                    
            <!-- POD END HERE -->
            <div class="clear14"></div>
            
      	</div>
		
		
		
			
			
			
			</s:form>

		</div>
	</div>
</div>


<script type="text/javascript">


  function postNotificationForm(){
  
	document.notificationsForm.action="saveMyNotification.action";
	
	document.notificationsForm.submit();
	

	  }


 function cancel(){
	document.notificationsForm.action="home.action";
	document.notificationsForm.submit();
	}
	


 function clearSuccessMessages(){
 <%
 session.setAttribute("successMessage",null);
 %>
}

clearSuccessMessages();
	
</script>
</body>
</html>