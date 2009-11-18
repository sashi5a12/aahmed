<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*,com.netpace.aims.model.core.*,com.netpace.aims.controller.application.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ include  file="include/dashboardAppVariables.jsp" %>

<jsp:useBean id="DashboardApplicationUpdateForm" class="com.netpace.aims.controller.application.DashboardApplicationUpdateForm" scope="request" />

<%@ include file="/common/error.jsp"%>

<div id="contentBox" style="float: left">
	<DIV class="homeColumnTab ">
		<%@ include file="include/dashboardViewTabs.jsp"%>
		<html:form action="/dashboardApplicationUpdate.do" enctype="multipart/form-data">
			<html:hidden property="currentPage" value="page1"/>
			<div class="contentbox">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>Channel Details</H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
								<tr>
									<th width="50%">
										<strong>Channel Title:</strong>
										<br />
										<span style="font-weight: normal; font-variant: normal"><bean:write name="DashboardApplicationUpdateForm" property="title" /></span>
									</th>
									<th width="50%">
										<strong>Channel Version:</strong>
										<br />
										<span style="font-weight: normal; font-variant: normal"><bean:write name="DashboardApplicationUpdateForm" property="channelVersion" /></span>
									</th>
								</tr>
								<tr>
									<td width="100%" colspan="2">
										<strong>Channel Type:</strong>
										<br/>
										<logic:equal name="DashboardApplicationUpdateForm" property="channelType" value="<%=AimsConstants.DASHBOARD_APP_RADIO_CHANNEL_TYPE_BASIC[0]%>"><%=AimsConstants.DASHBOARD_APP_RADIO_CHANNEL_TYPE_BASIC[1]%></logic:equal>
										<logic:equal name="DashboardApplicationUpdateForm" property="channelType" value="<%=AimsConstants.DASHBOARD_APP_RADIO_CHANNEL_TYPE_PREMIUM[0]%>"><%=AimsConstants.DASHBOARD_APP_RADIO_CHANNEL_TYPE_PREMIUM[1]%></logic:equal>										
									</td>
								</tr>
								<tr>
									<td>
										<strong>Language:</strong>
										<br />										
										<logic:equal name="DashboardApplicationUpdateForm" property="language" value="EN">
											<bean:message key="ManageApplicationForm.language.english" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
										</logic:equal>
										<logic:equal name="DashboardApplicationUpdateForm" property="language" value="SP">
											<bean:message key="ManageApplicationForm.language.spanish" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
										</logic:equal>
									</td>
									<td>
										<strong>Channel Size:</strong>
										<br />										
										<bean:write name="DashboardApplicationUpdateForm" property="channelSize" />
									</td>
								</tr>
								<tr>
									<td width="50%">
										<strong><bean:message key="ManageApplicationForm.appShortDesc" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
										<br/>
										<html:textarea styleClass="textareaField" property="shortDesc" rows="4" cols="50" readonly="true" />	
									</td>
									<td width="50%">
										<strong><bean:message key="ManageApplicationForm.appLongDesc" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
										<br/>
										<html:textarea styleClass="textareaField" property="longDesc" rows="4" cols="50" readonly="true" />	
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>Billing Information (if Premium)</H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" bordercolor="black" cellspacing="0" cellpadding="5" class="GridGradient" style="border: 0px solid black">
								<tr>
									<th width="50%" style="vertical-align: top;">
										<strong>VZW Suggested Retail Price (USD):</strong><br/>
										<bean:write name="DashboardApplicationUpdateForm" property="vzwRetailPrice" />								
									</th>
									<th style="vertical-align: top;">
										<strong>Vendor Product Display:</strong><br/>
										<html:textarea styleClass="textareaField" property="vendorProductDisplay" rows="4" cols="50" readonly="true" />
									</th>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr><td>&nbsp;</td></tr>
					
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>Channel Classification</H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
								<tr>
									<th width="50%">
										<strong>Channel Category:</strong>
										<br />
										<span style="font-weight: normal"> 
											<logic:iterate id="formCategories" name="DashboardApplicationUpdateForm" property="allCategories" scope="request">
												<logic:equal name="formCategories" property="categoryId" value="<%=DashboardApplicationUpdateForm.getAimsAppCategoryId().toString()%>">
													<bean:write name="formCategories" property="categoryName" />
												</logic:equal>
											</logic:iterate>
										</span>
									</th>
									<th width="50%">
										<strong>Channel Subcategory:</strong>
										<br />
										<span style="font-weight: normal"> 
											<logic:iterate id="formSubCategories" name="DashboardApplicationUpdateForm" property="allSubCategories" scope="request">
												<logic:equal name="formSubCategories" property="subCategoryId" value="<%=DashboardApplicationUpdateForm.getAimsAppSubCategoryId().toString()%>">
													<bean:write name="formSubCategories" property="subCategoryName" />
												</logic:equal>
											</logic:iterate>
										</span>
									</th>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr><td>&nbsp;</td></tr>
					
					<tr>
						<td width="100%">
							<table width="100%" cellspacing="0" cellpadding="1">
								<tr>
									<td width="50%" valign="top">
										<table width="100%" cellspacing="0" cellpadding="1">
											<tr>
												<td>
													<table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td class="noPad">
																<div class="mmBox">
																	<DIV class="headLeftCurveblk"></DIV>
																	<H1>Channel Files</H1>
																	<DIV class="headRightCurveblk"></DIV>
																</div>
															</td>
														</tr>
														<tr>
															<td style="padding: 0px">
																<table width="100%" height="100%" class="GridGradient" border="0" cellpadding="5" cellspacing="0">
																	<tr>
																		<th>
																			<strong>High Resolution Publisher Logo (EPS):</strong>
																			<br/>
																			<logic:notEmpty name="DashboardApplicationUpdateForm" property="clrPubLogoFileName" scope="request">																
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashClrPubLogo&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																					 class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="clrPubLogoFileName" /></a>
																			</logic:notEmpty>
																		</th>
																	</tr>
																	<tr>
																		<td>
																			<strong>Channel Title Graphic/Icon (EPS):</strong>
																			<br />
																			<logic:notEmpty name="DashboardApplicationUpdateForm" property="appTitleNameFileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashAppTitleName&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																					 class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="appTitleNameFileName" /></a>
																			</logic:notEmpty>																
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<strong><bean:message key="ManageApplicationForm.appSplashScreen" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																			<br />
																			<logic:notEmpty name="DashboardApplicationUpdateForm" property="splashScreenEpsFileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SplashScreenEps&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																					 class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="splashScreenEpsFileName" /></a>
																			</logic:notEmpty>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<strong><bean:message key="ManageApplicationForm.appActiveScreen" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																			<br />
																			<logic:notEmpty name="DashboardApplicationUpdateForm" property="activeScreenEpsFileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ActiveScreenEps&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																					 class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="activeScreenEpsFileName" /></a>
																			</logic:notEmpty>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<strong><bean:message key="ManageApplicationForm.appScreenShot" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																			
																			<br />
																			<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpegFileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																					class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="screenJpegFileName" /></a>
																			</logic:notEmpty>
																			
																			<br />
																			<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg2FileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																					 class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="screenJpeg2FileName" /></a>
																			</logic:notEmpty>
			
																			<br />
																			<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg3FileName" scope="request">									
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																					class="a" target="_blank"> <bean:write name="DashboardApplicationUpdateForm" property="screenJpeg3FileName" /></a>
																			</logic:notEmpty>
			
																			<br />
																			<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg4FileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																					class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="screenJpeg4FileName" /></a>
																			</logic:notEmpty>
			
																			<br />
																			<logic:notEmpty name="DashboardApplicationUpdateForm" property="screenJpeg5FileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																					class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="screenJpeg5FileName" /></a>
																			</logic:notEmpty>
																			
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<strong><bean:message key="ManageApplicationForm.appFAQ" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																			<br />
																			<logic:notEmpty name="DashboardApplicationUpdateForm" property="faqDocFileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FaqDoc&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																					class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="faqDocFileName" /></a>
																			</logic:notEmpty>
																		</td>
																	<tr>
																		<td>
																			<strong><bean:message key="ManageApplicationForm.appUserGuide" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																			<br />
																			<logic:notEmpty name="DashboardApplicationUpdateForm" property="userGuideFileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=UserGuide&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																					class="a" target="_blank"><bean:write name="DashboardApplicationUpdateForm" property="userGuideFileName" /></a>
																			</logic:notEmpty>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>																													
														<tr><td>&nbsp;</td></tr>
														<tr>
															<td class="noPad">
																<div class="mmBox">
																	<DIV class="headLeftCurveblk"></DIV>
																	<H1>Content ZIP</H1>
																	<DIV class="headRightCurveblk"></DIV>
																</div>
															</td>
														</tr>
														<tr>
															<th>
																<strong>Content ZIP File:</strong>
																<br />
																<logic:notEmpty name="DashboardApplicationUpdateForm" property="contentZipFileFileName" scope="request">
																	<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashContentZipFile&app_id=<bean:write	name="DashboardApplicationUpdateForm"	property="appsId"	/>"
																		class="a" target="_blank"> <bean:write name="DashboardApplicationUpdateForm" property="contentZipFileFileName" /></a>
																</logic:notEmpty>
															</th>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</td>
									<td width="50%" valign="top">
										<table width="100%" cellspacing="0" cellpadding="1">
											<tr>
												<td>
													<table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
														<tr>
															<td class="noPad">
																<div class="mmBox">
																	<DIV class="headLeftCurveblk"></DIV>
																	<H1>Devices Supported</H1>
																	<DIV class="headRightCurveblk"></DIV>
																</div>
															</td>
														</tr>
														<tr>
															<th>
																<span style="font-weight: normal;">
																	<logic:notEmpty name="DashboardApplicationUpdateForm" property="listSelectedDevices" scope="request">
																		<logic:iterate id="formDevices" name="DashboardApplicationUpdateForm" property="availableDevices" scope="request">
																			<%
																				for (int i = 0; i < DashboardApplicationUpdateForm.getListSelectedDevices().length; i++) {
																			%>
																				<logic:equal name="formDevices" property="deviceId" value="<%=(DashboardApplicationUpdateForm.getListSelectedDevices())[i].toString()%>">
																					<bean:write name="formDevices" property="deviceModel" />
																					<br />
																				</logic:equal>
																			<% } %>
																		</logic:iterate>
																	</logic:notEmpty>
																</span>
															</th>
														</tr>

														<tr><td>&nbsp;</td></tr>
														<tr>
															<td class="noPad">
																<div class="mmBox">
																	<DIV class="headLeftCurveblk"></DIV>
																	<H1>24x7 Technical Contact</H1>
																	<DIV class="headRightCurveblk"></DIV>
																</div>
															</td>
														</tr>
														<tr>
															<td style="padding: 0px">
																<table width="100%" height="100%" class="GridGradient" border="0" cellpadding="5" cellspacing="0">
																	<logic:notEmpty property="aimsContactId" name="DashboardApplicationUpdateForm">
																	<logic:iterate id="formContacts" name="DashboardApplicationUpdateForm" property="allContacts" scope="request">
																		<logic:equal name="formContacts" property="contactId" value="<%=DashboardApplicationUpdateForm.getAimsContactId().toString()%>">
																			<tr>
																				<th width="50%">
																					<strong><bean:message key="ManageApplicationForm.appContactFirstName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																					<br />
																					<span style="font-weight: normal;"><bean:write name="formContacts" property="firstName" /></span>
																				</th>
																				<th width="50%">
																					<strong><bean:message key="ManageApplicationForm.appContactLastName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																					<br/>
																					<bean:write name="formContacts" property="lastName" />	
																				</th>																				
																			</tr>
																			<tr>
																				<td>
																					<strong><bean:message key="ManageApplicationForm.appContactTitle" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																					<br/>
																					<bean:write name="formContacts" property="title" />																			
																				</td>
																				<td>
																					<strong><bean:message key="ManageApplicationForm.appContactEmail" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																					<br/>
																					<bean:write name="formContacts" property="emailAddress" />																			
																				</td>																				
																			</tr>
																			<tr>
																				<td>
																					<strong><bean:message key="ManageApplicationForm.appContactTelephone" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																					<br/>
																					<bean:write name="formContacts" property="phone" />																			
																				</td>
																				<td>
																					<strong><bean:message key="ManageApplicationForm.appContactMobile" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																					<br/>
																					<bean:write name="formContacts" property="mobile" />																			
																				</td>																				
																			</tr>
																		</logic:equal>
																	</logic:iterate>
																	</logic:notEmpty>
																</table>
															</td>
														</tr>															
														
														<tr><td>&nbsp;</td></tr>																												
														
														<tr>
															<td class="noPad">
																<div class="mmBox">
																	<DIV class="headLeftCurveblk"></DIV>
																	<H1>Other Details</H1>
																	<DIV class="headRightCurveblk"></DIV>
																</div>
															</td>
														</tr>
														<tr>
															<td style="padding: 0px">
															<table width="100%" height="100%" class="GridGradient" border="0" cellpadding="5" cellspacing="0">
																<tr>
																	<th>
																		<strong>Developer Name:</strong><br/>
																		<bean:write name="DashboardApplicationUpdateForm" property="developerName" />
																	</th>
																</tr>
																<tr>
																	<td>
																		<strong>Publisher Name:</strong><br/>
																		<bean:write name="DashboardApplicationUpdateForm" property="publisherName" />
																	</td>
																</tr>
																<tr>
																	<td>
																		<strong>Selling Points:</strong><br/>
																		<html:textarea styleClass="textareaField" property="sellingPoints" rows="4" cols="50" readonly="true"/>																
																	</td>
																</tr>
																<tr>
																	<td>
																		<strong>Additional Comments:</strong><br/>
																		<html:textarea property="channelDeployments" rows="4" cols="50" readonly="true" />																
																	</td>
																</tr>
																<tr>
																	<td>
																		<strong>Planned Development Start Date:</strong><br/>
																		<bean:write name="DashboardApplicationUpdateForm" property="plannedDevStartDate" />
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
						</td>
					</tr>
					
					<tr><td>&nbsp;</td></tr>
					
					<tr>
						<td width="100%">
							<%@ include file="include/dashboardAppViewButtons.jsp"%>						
						</td>
					</tr>
				</table>
			</div>
		</html:form>
	</div>
</div>