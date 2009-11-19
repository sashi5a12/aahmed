<html>

	<%@ include file="/commons/taglibs.jsp"%>
	<%@ taglib uri="/tld/rssutils.tld" prefix="rss"%>
	<%@ page import="com.netpace.vzdn.util.ConfigEnvProperties"%>
	<%@ page import="com.netpace.vzdn.util.GlobalNavProperties"%>



	<head>

		<meta name="mainTitle" content="My Account" />

		<title>My Account</title>


	</head>



	<BODY>
	
	<%	 ConfigEnvProperties props = ConfigEnvProperties.getInstance();		 
		 String editUserUrl = props.getProperty("opensso.editUserURL");		 
		 
		 String gtmApplicationsUrl = props.getProperty("gtm.applicationsURL"); 
		 String gtmAlliancesUrl = props.getProperty("gtm.alliancesURL");
		 String gtmContractsUrl = props.getProperty("gtm.contractsURL");
		 
		 String forumLink = props.getProperty("forum.link");
		 String forumTitle = props.getProperty("forum.title");
		 String blogsLink = props.getProperty("blogs.link");
		 String blogsTitle = props.getProperty("blogs.title");
		 		 
		 
		 
		 //GlobalNavProperties gProps = GlobalNavProperties.getInstance();
		 		  
	%>
	
	
	
		<div id=wideLayout>
			<!-- HEADER START -->

			<!-- TOP NAVIGATION START -->

			<!-- TOP NAVIGATION END -->
			<!-- HEADER END -->
			<!-- BODY START -->
			<div id=homepageWrapper>
				<div id=bodyWrapper>
					<div id=homepageContainer>



						<div id="contentCol">

						<s:if test="hasActionErrors()">
						    <div class="alertBox">						    	
						      <s:iterator value="actionErrors">						      
						          <s:iterator value="value">						          
						          	   <s:property/>						          	   
						          </s:iterator>						      
								  <br/>
						      </s:iterator>
						   </div>
						</s:if>


						<s:if test="hasFieldErrors()">
						    <div class="alertBox">						    	
						      <s:iterator value="fieldErrors">						      
						          <s:iterator value="value">						          
						          	   <s:property/>						          	   
						          </s:iterator>						      
								  <br/>
						      </s:iterator>
						   </div>
						</s:if>	



							<div class="box321 float_left mR10">
								<!-- POD START HERE -->
								<div class="box321">

									<div class="box2"><div class="boxTop box321"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
										<div class="boxContent boxH150">
											<!-- POD CONTENT START HERE -->
											<div class="boxContBM">
												<div class="subHeadBlack">
													<img src="images/devNetwork/icon_myInfo.gif" width="16"
														height="16" class="mR5">
													My Info
												</div>
												<div class="txtContent borderT boxH150"	style="margin-right: 10px; margin-top: 5px; padding-top: 5px;">

													<ul class="arrow boldText">

														<li>
															<a href="<%=editUserUrl%>">Edit Profile</a>
														</li>
														<li>
															<a href="myNotification.action">My Notifications</a>
														</li>
													</ul>
												</div>


											</div>
											<!-- POD CONTENT END HERE -->
										</div>
										<div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
									</div>
									
								</div>
								<!-- POD END HERE -->
								<div class="clear14"></div>




							</div>

							<div class="box321 float_left mR10">
								<!-- POD START HERE -->
								<div class="box321">

										<div class="box2"><div class="boxTop box321"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>

											<div class="boxContent boxH150">
												<!-- POD CONTENT START HERE -->

												<div class="boxContBM">
													<div class="subHeadBlack">
														<img 

src="images/devNetwork/icon_community.gif" width="16"
															height="16" class="mR5">
														Community
													</div>


													<div class="txtContent borderT boxH150"
														style="margin-right: 10px; margin-top: 5px; 

padding-top: 5px;">

														<ul class="arrow boldText">

															<c:forEach
																

items="${sessionScope.loggedInUser.communityMenus}"
																var="subMenu">

																<li>
																	<a href="<c:out 

value='${subMenu.subMenuUrl}' />"><c:out
																			

value="${subMenu.subMenuName}" /> </a>
																</li>

															</c:forEach>
															
															
															
															<li>
																<a href="<%=forumLink%>"><%=forumTitle%></a>
															</li>
															<li>
																<a href="<%=blogsLink%>"><%=blogsTitle%></a>
															</li>

														</ul>



													</div>

												</div>

												<!-- POD CONTENT END HERE -->
											</div>

											<div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
										</div>

								</div>
								<!-- POD END HERE -->
								<div class="clear14"></div>




							</div>
							<div class="box321 float_left">
								<!-- POD START HERE -->
								<div class="box321">
									<c:if test="${sessionScope.loggedInUser.userType.typeId == 1}">
										<c:if
											test="${!empty sessionScope.loggedInUser.gotoMarketMenus}">
											<div class="box2"><div class="boxTop box321"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>

												<div class="boxContent boxH150">
													<!-- POD CONTENT START HERE -->
													<div class="boxContBM">
														<div class="subHeadBlack">
															<img 

src="images/devNetwork/icon_gotoMarket.gif"
																width="16" height="16" 

class="mR5">
															Go to Market
														</div>



														<div class="txtContent borderT boxH150"
															style="margin-right: 10px; margin-top: 6px; padding-top: 5px;">
															<ul class="arrow boldText">
																<c:forEach items="${sessionScope.loggedInUser.gotoMarketMenus}"
																	var="subMenu">
																	<li>
																		<a 

href="<c:out value='${subMenu.subMenuUrl}' />"><c:out
																				

value="${subMenu.subMenuName}" /> </a>
																	</li>

																</c:forEach>

															</ul>



														</div>

													</div>
													<!-- POD CONTENT END HERE -->
												</div><div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
											</div>
										</c:if>
									</c:if>


									<c:if test="${sessionScope.loggedInUser.userType.typeId == 2}">


										<div class="box2"><div class="boxTop box321"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>

											<div class="boxContent boxH150">
												<!-- POD CONTENT START HERE -->
												<div class="boxContBM">
													<div class="subHeadBlack">
														<img 

src="images/devNetwork/icon_gotoMarket.gif"
															width="16" height="16" class="mR5">
														Go to Market
													</div>



													<div class="txtContent borderT boxH150"
														style="margin-right: 10px; margin-top: 6px; 

padding-top: 5px;">
														<ul class="arrow boldText">


															<li>
																<a
																	href="<%=gtmApplicationsUrl%>">Application
																</a>
															</li>


															<li>
																<a
																	href="<%=gtmAlliancesUrl%>">Alliances
																</a>
															</li>


															<li>
																<a
																	href="<%=gtmContractsUrl%>">Contracts
																</a>
															</li>


														</ul>



													</div>

												</div>
												<!-- POD CONTENT END HERE -->
											</div><div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
										</div>
									</c:if>
								</div>
								<!-- POD END HERE -->
								<div class="clear14"></div>
							</div>
							
							


							<c:if test="${!empty sessionScope.loggedInUser.managementMenus}">
								<div class="box653 mR10">
							</c:if>

							<c:if test="${empty sessionScope.loggedInUser.managementMenus}">
								<div class="box">
							</c:if>
							<div class="box2"><c:if test="${!empty sessionScope.loggedInUser.managementMenus}"><div class="boxTop box653"></c:if><c:if test="${empty sessionScope.loggedInUser.managementMenus}"><div class="boxTop box984"></c:if><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
							<div class="boxContent boxH181">
								<!-- POD CONTENT START HERE -->
								<div class="boxContBM">
									<div class="subHeadBlack">

										<img src="images/devNetwork/icon_rss2.gif" width="16"
												height="16" class="mR5"> </a> Verizon Developer Community News
									</div>
									<div class="txtContent borderT boxH150"
									
										style="margin-right: 10px; margin-top: 6px; padding-top: 5px;">
										
										
										
										<%
										
										try{
										
										%>

										<rss:feed url="http://newscenter.verizon.com/system/rss/channel.jsp?feedID=29124915" feedId="29124915" />
										<rss:forEachItem feedId="29124915" startIndex="0" endIndex="1">
											<a target='_blank' href="<rss:itemLink feedId="29124915" />"><strong><rss:itemTitle feedId="29124915" />
											</strong> </a>
											<br />
											<rss:itemDescription feedId="29124915" />
											<br />
											<br />
										</rss:forEachItem>
										
										
										<img src="images/shared/elements/arrow_button.gif" width="6"
											height="7" align="absmiddle">
										<a target='_blank' href="http://newscenter.verizon.com/"><strong>Read
												All News >></strong> </a>
										<br />
										
										<%	}									
											catch(Exception e){
										%>
											<br/>
											<br/>
											<br/>
											<br/>
											<br/>
											<br/>
											<br/>
											<br/>											
											No news available for now!!!
										<%
											}
										%>
										
									</div>
								</div>
								<!-- POD CONTENT END HERE -->
							</div><div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
						</div>
					</div>

					<div class="box321 float_left">
						<!-- POD START HERE -->
						<div class="box321">
							<c:if test="${!empty sessionScope.loggedInUser.managementMenus}">

								<div class="box2"><div class="boxTop box321"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
									<div class="boxContent boxH181">
										<!-- POD CONTENT START HERE -->
										<div class="boxContBM">
											<div class="subHeadBlack">


												<img src="images/devNetwork/icon_management.gif" width="16"
													height="16" class="mR5">
												Management
											</div>



											<div class="txtContent borderT boxH150"
												style="margin-right: 10px; margin-top: 6px; padding-top: 

5px;">

												<ul class="arrow boldText">

													<c:forEach
														

items="${sessionScope.loggedInUser.managementMenus}"
														var="subMenu">
														<li>
															<a href="<c:out 

value='${subMenu.subMenuUrl}' />"><c:out
																	

value="${subMenu.subMenuName}" /> </a>
														</li>
													</c:forEach>

												</ul>



											</div>

										</div>
										<!-- POD CONTENT END HERE -->
									</div><div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
								</div>

							</c:if>
						</div>
					
						<!-- POD END HERE -->
						<div class="clear14"></div>
					</div>

				</div>



			</div>
			<!-- VIDEO SLIDER START -->

			<!-- VIDEO SLIDER END -->
		</div>
		</div>



		<!-- BODY END -->
		<div class="clear18"></div>
		<!-- FOOTER START -->


		<!-- FOOTER END -->
		</div>
	</BODY>
</HTML>