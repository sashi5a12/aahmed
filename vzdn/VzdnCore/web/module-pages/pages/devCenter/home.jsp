<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<!-- saved from url=(0045)http://www.verizonwireless.com/b2c/index.html -->
<HTML lang=en-us xml:lang="en-us" xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<TITLE>Cell Phones, Cell Phone Plans, Cell Phone Accessories -
			Verizon Wireless</TITLE>
		<META
			content="Verizon, Wireless, Cell, Cell Phone, Mobile, Cellular, Phone, Mobile Phone"
			name=Keywords>
		<META
			content="Verizon Wireless offers cell phones, pdas, devices, cell phone plans, data plans, and accessories with affordable, reliable wireless service for your personal needs."
			name=description>
		<META http-equiv=Content-Type content="text/html; charset=ISO-8859-1">
		<META http-equiv=Content-Style-Type content=text/css>
		<META http-equiv=imagetoolbar content=no>
		<LINK href="styles/layout.css" type=text/css rel=stylesheet>
		<LINK href="styles/fonts.css" type=text/css rel=stylesheet>
		<LINK href="styles/code_standards.css" type=text/css rel=stylesheet>
		<LINK media=screen,projection href="styles/globalnav.css"
			type=text/css rel=stylesheet>
		<LINK media=print href="styles/accessiblePrint.css" type=text/css
			rel=stylesheet>


		<script type="text/javascript" src="scripts/gn_engine.js"></script>
		<script type="text/javascript" src="scripts/togglers.js"></script>

		<SCRIPT type=text/javascript>gn_iframe = 'scripts/blank.html';</SCRIPT>
		<SCRIPT src="scripts/gn_engine.js" type=text/javascript></SCRIPT>
		<SCRIPT src="scripts/hbxVariables.js" type=text/javascript> </SCRIPT>
		<SCRIPT src="scripts/hbxFunctions.js" type=text/javascript> </SCRIPT>
		<SCRIPT type=text/javaScript>
	 var webserver="";
	  var appServer="";
	  var vzserve="";
	  var bizServer="";
	  var loginForm="";
	  var logoutURL="";
	  var secureServer="";
	  var spanishLink ='';
	  var tabCookie =''; 
	  var loginState ='';
	</SCRIPT>
		<META content="MSHTML 6.00.2900.5726" name=GENERATOR>
	</HEAD>
	<BODY>
		<div id=wideLayout>
			<!-- HEADER START -->

			<!-- TOP NAVIGATION START -->

			<!-- TOP NAVIGATION END -->
			<!-- HEADER END -->
			<!-- BODY START -->
			<div id=homepageWrapper>
				<div id=bodyWrapper>
					<div id=homepageContainer>
						<div>
							<div id="page_title2">
								My Account
							</div>
						</div>


						<div id="contentCol">



							<div class="box321 float_left mR10">
								<!-- POD START HERE -->
								<div class="box321">
									<div class="box2">
										<div class="boxTop box321">
											<img src="images/shared/elements/boxCLT.jpg" width="6"
												height="6">
										</div>
										<div class="boxContent">
											<!-- POD CONTENT START HERE -->
											<div class="boxContBM">
												<div class="subHeadBlack">
													<img src="images/devNetwork/icon_myInfo.gif" width="16"
														height="16" class="mR5">
													My Info
												</div>
												<div class="txtContent">
													<br />
													<ul class="arrow boldText">
													
													This user has the following roles:
													 <%
													 //must use jstl ... later
											                        java.util.Set<com.netpace.vzdn.model.VzdnSysRoles> roles = (java.util.Set)session.getAttribute("userRoles");
											                        //com.netpace.vzdn.model.VzdnSysPrivileges roles =(com.netpace.vzdn.model.VzdnSysPrivileges)session.getAttribute("userRoles");
											                        for(com.netpace.vzdn.model.VzdnSysRoles r : roles){
											                     %>
											                         <li><a href="#">
											                      <%
											                        	out.println(r.getRoleDescription());
											                      %> 
											                        </a></li>
								                     <%}%> 								                        

													</ul>
													<br />
													<br />

													<br />


												</div>
											</div>
											<!-- POD CONTENT END HERE -->
										</div>
										<div class="boxBottom">
											<img src="images/shared/elements/boxCLB.jpg" width="6"
												height="6">
										</div>
									</div>
								</div>
								<!-- POD END HERE -->
								<div class="clear14"></div>




							</div>
							<div class="box321 float_left mR10">
								<!-- POD START HERE -->
								<div class="box321">
									<div class="box2">
										<div class="boxTop box321">
											<img src="images/shared/elements/boxCLT.jpg" width="6"
												height="6">
										</div>
										<div class="boxContent">
											<!-- POD CONTENT START HERE -->
											<div class="boxContBM">
												<div class="subHeadBlack">
													<img src="images/devNetwork/icon_community.gif" width="16"
														height="16" class="mR5">
													Community
												</div>
												<div class="txtContent">
													<br />
													<ul class="arrow boldText">
														<li>
															<a href="#">Blog</a>
														</li>
														<li>
															<a href="#">Forum</a>
														</li>

													</ul>
													<br />
													<br />

													<br />


												</div>
											</div>
											<!-- POD CONTENT END HERE -->
										</div>
										<div class="boxBottom">
											<img src="images/shared/elements/boxCLB.jpg" width="6"
												height="6">
										</div>
									</div>
								</div>
								<!-- POD END HERE -->
								<div class="clear14"></div>




							</div>
							<div class="box321 float_left">
								<!-- POD START HERE -->
								<div class="box321">
									<div class="box2">
										<div class="boxTop box321">
											<img src="images/shared/elements/boxCLT.jpg" width="6"
												height="6">
										</div>
										<div class="boxContent">
											<!-- POD CONTENT START HERE -->
											<div class="boxContBM">
												<div class="subHeadBlack">
													<img src="images/devNetwork/icon_gotoMarket.gif" width="16"
														height="16" class="mR5">
													Go to Market
												</div>
												<div class="txtContent">
													<br />
													<ul class="arrow boldText">
														<li>
															<a href="#">Applications</a>
														</li>
														<li>
															<a href="#">Alliances</a>
														</li>

													</ul>
													<br />
													<br />

													<br />


												</div>
											</div>
											<!-- POD CONTENT END HERE -->
										</div>
										<div class="boxBottom">
											<img src="images/shared/elements/boxCLB.jpg" width="6"
												height="6">
										</div>
									</div>
								</div>
								<!-- POD END HERE -->
								<div class="clear14"></div>




							</div>

							<div class="box653 mR10">
								<div class="box2">
									<div class="boxTop box653">
										<img src="images/shared/elements/boxCLT.jpg" width="6"
											height="6">
									</div>
									<div class="boxContent">
										<!-- POD CONTENT START HERE -->
										<div class="boxContBM">
											<div class="subHeadBlack">
												<img src="images/devNetwork/icon_rss2.gif" width="16"
													height="16" class="mR5">
												Verizion Developer Network News
											</div>
											<div class="txtContent">
												<br />
												<a href="#"><strong>consectetur adipiscing
														elit. Integer gravida risus quis nisi. Fusce molestie
														eleifend orci.</strong>
												</a>
												<br />
												March 31 - April 3, 2009
												<br />
												<br />
												<a href="#"><strong>Nullam id sem. Nullam neque
														ipsum, dapibus in, vestibulum sit amet, lacinia et, metus</strong>
												</a>
												<br />
												November 16 - 19, 2008
												<br />
												<br />
												<img src="images/shared/elements/arrow_button.gif" width="6"
													height="7" align="absmiddle">
												<a href="#"><strong>Learn more</strong>
												</a>
												<br />





											</div>
										</div>
										<!-- POD CONTENT END HERE -->
									</div>
									<div class="boxBottom">
										<img src="images/shared/elements/boxCLB.jpg" width="6"
											height="6">
									</div>
								</div>
							</div>

							<div class="box321 float_left">
								<!-- POD START HERE -->
								<div class="box321">
									<div class="box2">
										<div class="boxTop box321">
											<img src="images/shared/elements/boxCLT.jpg" width="6"
												height="6">
										</div>
										<div class="boxContent">
											<!-- POD CONTENT START HERE -->
											<div class="boxContBM">
												<div class="subHeadBlack">													
													<img src="images/devNetwork/icon_management.gif" width="16"
														height="16" class="mR5">
													Management
												</div>
												<div class="txtContent">
													<br />
													<ul class="arrow boldText">
														<li>
															<a href="#">Notifications</a>
														</li>
														<li>
															<a href="#">Content Publishing</a>
														</li>
														<li>
															<a href="#">Reports</a>
														</li>
														<li>
															<a href="#">Ticket Management</a>
														</li>
													</ul>
													<br />
													<br />

													<br />


												</div>
											</div>
											<!-- POD CONTENT END HERE -->
										</div>
										<div class="boxBottom">
											<img src="images/shared/elements/boxCLB.jpg" width="6"
												height="6">
										</div>
									</div>
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
		</div>
		<!-- BODY END -->
		<div class="clear18"></div>
		<!-- FOOTER START -->

		<!-- FOOTER END -->
		</div>
	</BODY>
</HTML>
