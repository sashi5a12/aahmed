<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta name="mainTitle" content="List Users" />

	<title>List Users</title>

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
		
									<s:form name="searchUsers" action="searchUsersAction" method="post" theme="simple" >
									
									<td>
										<table width="100%" border="0" cellspacing="1" cellpadding="0">
											<tr>
		
												<td>
													<table width="100%" border="0" cellpadding="3"
														cellspacing="0">
														<tr>
															<td class="label boldText">
		
																Search On:
																<br />
																
																
																<s:select name="searchOn.searchableId" list="searchers" listValue="displayedValue" label="Search On" listKey="searchableId" cssClass="inputFieldForm"/>
															</td>
		
															<td class="label boldText" colspan="2">
																Search Value:
																<br />
																<s:textfield name="searchOn.contentToSearch" maxlength="50" size="25" cssClass="inputFieldForm"/>
															</td>
															
															
															<td width="34%" valign="bottom">
															
																<button class="input_primary" type="submit">
																	<span><span><span>Search</span>
																	</span>
																	</span>
																</button>
															</td>
		
														</tr>
														
														<%-- 
		
														<tr>
															<td width="35%" class="label boldText">
		
																First Name :
																<table width="210" border="0" cellspacing="0"
																	cellpadding="0">
																	<tr>
																		<td>
																			<s:textfield name="userToSearch.firstName" maxlength="20" size="25"  id="firstName" cssClass="inputFieldForm"/>
																		</td>
																	</tr>
																</table>
															</td>
		
		
															<td width="31%" class="label boldText">
																Last Name:
																<table width="210" border="0" cellspacing="0"
																	cellpadding="0">
		
																	<tr>
																		<td>
																			<s:textfield name="userToSearch.lastName" maxlength="20" size="25" id="lastName" cssClass="inputFieldForm"/>
																		</td>
																	</tr>
																</table>
															</td>
		
		
															
														</tr>
														
														--%>
														
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
				</td>
		
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


		    

        		
        	
        </div>

            <div class="box2">
                <div class="boxTop box984"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
                <div class="boxContent">
                <!-- POD CONTENT START HERE -->
                <div class="pR10">
                
				
				
				
		
				
				
				
				
				
				
				
				
				
				
                
                
                <table align="center" width="100%">
		<tr>
		
		<td>
		
			
		<display:table style="text-align: left; border:0px" name="userlist"  requestURI="" pagesize="${sessionScope.loggedInUser.pageSize}"  class="table_content" id="row"> 
		
		  <display:column style="text-align: left; width: 9%" sortable="true" title="User Name">
			<c:out value='${row.userName}'/>
		  </display:column>
		  
		  <display:column sortable="true" style="text-align: left; width: 9%" title="User Type">
			<c:out value='${row.userType.typeValue}'/>
		  </display:column>
		  
		  <display:column sortable="true" style="text-align: left; width: 9%" title="First Name">
			<c:out value='${row.firstName}'/>
		  </display:column>
		  
		  <display:column sortable="true"	style="text-align: left; width: 9%" title="Last Name" >
			<c:out value='${row.lastName}'/>
		  </display:column>
		 
		  <display:column style="text-align: center; width: 3%" title="Active" headerClass="columnHeader">
			<c:if test="${row.statusType.typeId == 5}">
		  		<img src="images/devNetwork/icon_active_green.gif"/>
		  	</c:if>	
		  	
			<c:if test="${row.statusType.typeId == 6}">
				<img src="images/devNetwork/icon_notactive.gif"/>
			</c:if>			  		  	
		  </display:column>
		  
		  <display:column style="text-align: center; width: 3%" title="Edit" headerClass="columnHeader">
		  	<a href="edituser.action?id=<c:out value='${row.userId}'/>">
		  		<img src="images/devNetwork/icon_edit.gif"/>
		  	</a>
			
		  </display:column>
		  
		</display:table>
		</td></tr>
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
    


<script type="text/javascript">

function clearSuccessMessages(){
 <%
 session.setAttribute("successMessage",null);
 %>
}
clearSuccessMessages();

	
</script>

</body>
</html>  