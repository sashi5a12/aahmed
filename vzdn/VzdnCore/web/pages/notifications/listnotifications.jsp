<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<html>

<head>

<meta name="mainTitle" content="Notifications" />

	<title>Notifications</title>



</head>
	
	

<body>


		

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
		
									<s:form name="searchNotifications" action="notifications" method="post" theme="simple" >
									
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
				<td><br><br></td>
		
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
		
			
			


		<s:form action="addNotification">
			<button class="input_primary" type="submit">
				<span><span><span>Add&nbsp;Notification</span>
				</span>
				</span>
			</button>
		</s:form>

		<div class="clear14"></div>


	</div>

	<div class="box2">
		<div class="boxTop box984">
		<img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
		<div class="boxContent">
			<!-- POD CONTENT START HERE -->
			<div class="pR10">



				<table align="center" width="100%">
					<tr>

						<td>


							<display:table style="border:0px" name="emails"
								requestURI="" pagesize="${sessionScope.loggedInUser.pageSize}"
								class="table_content" id="row">

								<display:column property="emailTitle" escapeXml="true"
									style="width: 30%" sortable="true" title="Email Title" />
									
								
								<display:column property="emailDesc" escapeXml="true"
									style="width: 62%" sortable="true" title="Email Description" />								

							
								<display:column style="width: 4%" title="Edit">
									<a
										href="editNotification.action?id=<c:out value='${row.emailMessageId}'/>">
										<img src="images/devNetwork/icon_edit.gif" /> </a>

								</display:column>
								
								<display:column style="width: 4%" title="Delete">
									<a
										onclick="return confirmDelete();"
										href="deleteNotification.action?id=<c:out value='${row.emailMessageId}'/>">
										<img src="images/devNetwork/icon_trash.gif" /> </a>

								</display:column>
								


							</display:table>
						</td>
					</tr>
				</table>
				
				<div class="clear10"></div>

			</div>
			<!-- POD CONTENT END HERE -->
		</div>
		<div class="boxBottom">
		<img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
		
		
	
		
		
	</div>
	
	
<!-- POD END HERE -->
<div class="clear14"></div>
	
		<s:form action="addNotification">
			<button class="input_primary" type="submit">
				<span><span><span>Add&nbsp;Notification</span>
				</span>
				</span>
			</button>
		</s:form>
		
</div>	

<script type="text/javascript">
	function confirmDelete(){
		var msg;
		msg="Are you sure you want to delete?";
		return confirm(msg);
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