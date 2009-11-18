<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*,com.netpace.aims.model.core.*,com.netpace.aims.controller.application.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ include  file="include/javaAppVariables.jsp" %>

<jsp:useBean id="javaApplicationUpdateForm" class="com.netpace.aims.controller.application.JavaApplicationUpdateForm" scope="request" />

<%@ include file="/common/error.jsp"%>


<script language="javascript">

	var	contactsArray	=	new	Array();
	
	function AimsContacts()	{						
		this.contactId = "";
		this.firstName = "";
		this.lastName	=	"";
		this.title = "";
		this.emailAddress	=	"";
		this.phone = "";
		this.mobile	=	"";
	}

	<% int	index	=	0; %>

	<logic:iterate id="formContacts" name="javaApplicationUpdateForm"	property="allContacts" scope="request">
		aimsContacts = new AimsContacts();
		aimsContacts.contactId = "<bean:write	name="formContacts"	property="contactId" />";
		aimsContacts.firstName = "<bean:write	name="formContacts"	property="firstName" />";;
		aimsContacts.lastName	=	"<bean:write name="formContacts" property="lastName" />";
		aimsContacts.title = "<bean:write	name="formContacts"	property="title" />";
		aimsContacts.emailAddress	=	"<bean:write name="formContacts" property="emailAddress" />";
		aimsContacts.phone = "<bean:write	name="formContacts"	property="phone" />";
		aimsContacts.mobile	=	"<bean:write name="formContacts" property="mobile" />";
		contactsArray[<%=index%>]	=	aimsContacts;
		<%index++;%>
	</logic:iterate>


	var	supported	=	(window.Option)	?	1	:	0;


	function displayContactsInformation()	{
		 if	(!supported) {
			 alert("Feature	not	supported");
		 }
		 if ((document.forms[0].aimsContactId.options[document.forms[0].aimsContactId.selectedIndex].value) !=	"0") {		 	
			for	(var j = 0;	j	<	contactsArray.length;	j++) {
				if (contactsArray[j].contactId ==	document.forms[0].aimsContactId.options[document.forms[0].aimsContactId.selectedIndex].value) {
					document.forms[0].contactFirstName.value = contactsArray[j].firstName;
					document.forms[0].contactLastName.value	=	contactsArray[j].lastName;
					document.forms[0].contactTitle.value = contactsArray[j].title;
					document.forms[0].contactEmail.value = contactsArray[j].emailAddress;
					document.forms[0].contactPhone.value = contactsArray[j].phone;
					document.forms[0].contactMobile.value	=	contactsArray[j].mobile;
					document.forms[0].contactFirstName.disabled	=	true;
					document.forms[0].contactLastName.disabled = true;
					document.forms[0].contactTitle.disabled	=	true;
					document.forms[0].contactEmail.disabled	=	true;
					document.forms[0].contactPhone.disabled	=	true;
					document.forms[0].contactMobile.disabled = true;
				}
			}
		}
		else {
			document.forms[0].contactFirstName.disabled	=	false;
			document.forms[0].contactLastName.disabled = false;
			document.forms[0].contactTitle.disabled	=	false;
			document.forms[0].contactEmail.disabled	=	false;
			document.forms[0].contactPhone.disabled	=	false;
			document.forms[0].contactMobile.disabled = false;

			document.forms[0].contactFirstName.value = "<bean:write	name="javaApplicationUpdateForm" property="contactFirstName" scope="request"/>";
			document.forms[0].contactLastName.value	=	"<bean:write name="javaApplicationUpdateForm"	property="contactLastName" scope="request"/>";
			document.forms[0].contactTitle.value = "<bean:write	name="javaApplicationUpdateForm" property="contactTitle" scope="request"/>";
			document.forms[0].contactEmail.value = "<bean:write	name="javaApplicationUpdateForm" property="contactEmail" scope="request"/>";
			document.forms[0].contactPhone.value = "<bean:write	name="javaApplicationUpdateForm" property="contactPhone" scope="request"/>";
			document.forms[0].contactMobile.value	=	"<bean:write name="javaApplicationUpdateForm"	property="contactMobile" scope="request"/>";
		}

	}

	function truncateLocalTextAreas() {
        if (typeof document.forms[0].channelDeployments != "undefined")
            if (typeof document.forms[0].channelDeployments.value != "undefined")
			 TruncateText(document.forms[0].channelDeployments,1000);
             
        if (typeof document.forms[0].sellingPoints != "undefined")
            if (typeof document.forms[0].sellingPoints.value != "undefined")
			 TruncateText(document.forms[0].sellingPoints,2000);
	}

	<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
		function disableForVzw() {			           
			document.forms[0].channelDeployments.disabled	=	true;
			document.forms[0].aimsContactId.disabled	=	true;
			document.forms[0].contactFirstName.disabled	=	true;
			document.forms[0].contactLastName.disabled = true;
			document.forms[0].contactTitle.disabled	=	true;
			document.forms[0].contactEmail.disabled	=	true;
			document.forms[0].contactPhone.disabled	=	true;
			document.forms[0].contactMobile.disabled = true;
		}
	<% } else {}%>
	<%@ include  file="include/javaJScript.jsp" %>
</script>

<div id="contentBox" style="float: left">
	<DIV class="homeColumnTab ">
		<%@ include file="include/javaAppViewTabs.jsp"%>

		<html:form action="/javaApplicationUpdate.do" enctype="multipart/form-data">
			<html:hidden property="currentPage" value="page1"/>
			<div class="contentbox">
				<%@ include file="include/javaAppHidden.jsp"%>
				
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>Application Details</H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
								<tr>
									<th width="50%">
										<strong>Application Title:</strong>
										<br />
										<span style="font-weight: normal; font-variant: normal"><%=Utility.getEllipseString(42,javaApplicationUpdateForm.getTitle())%></span>
									</th>
									<th width="50%">
										<strong>Application Version:</strong>
										<br />
										<span style="font-weight: normal; font-variant: normal"><bean:write name="javaApplicationUpdateForm" property="version" /></span>
									</th>
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
					
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
								<tr>
									<td width="50%">
										<strong>Content Rating:</strong>
										<br />
										<span style="font-weight: normal; font-variant: normal">
											<logic:notEmpty name="javaApplicationUpdateForm" property="contentRating">
											<logic:iterate id="contentRatings" name="javaApplicationUpdateForm" property="allJavaContentRatings" type="com.netpace.aims.model.core.AimsTypes">
				                                    <logic:equal name="contentRatings" property="typeId" value="<%=javaApplicationUpdateForm.getContentRating().toString()%>">
				                                        <bean:write name="contentRatings" property="typeValue"/>
				                                    </logic:equal>
				                                </logic:iterate>
										    </logic:notEmpty>
										</span>
									</td>
									<td width="50%">
										<strong>Info URL:</strong>
										<br />
										<span style="font-weight: normal; font-variant: normal"><bean:write name="javaApplicationUpdateForm" property="infoURL" /></span>
									</td>
								</tr>																								
							</table>
						</td>
					</tr>
					
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
								<tr>									
									<td width="50%" colspan="2">
										<strong>Application Keyword:</strong>
										<br />
										<span style="font-weight: normal; font-variant: normal"><bean:write name="javaApplicationUpdateForm" property="appKeyword" /></span>
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
								<H1>Application Classification</H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
								<tr>
									<th width="50%">
										<strong>Application Category:</strong>
										<br />
										<span style="font-weight: normal"> 
											<logic:notEmpty name="javaApplicationUpdateForm" property="aimsAppCategoryId">
												<logic:iterate id="formCategories" name="javaApplicationUpdateForm" property="allCategories" scope="request">
													<logic:equal name="formCategories" property="categoryId" value="<%=javaApplicationUpdateForm.getAimsAppCategoryId().toString()%>">
														<bean:write name="formCategories" property="categoryName" />
													</logic:equal>
												</logic:iterate>
											</logic:notEmpty>
										</span>
									</th>
									<th width="50%">
										<strong>Application Subcategory:</strong>
										<br />
										<span style="font-weight: normal"> 
											<logic:notEmpty name="javaApplicationUpdateForm" property="aimsAppSubCategoryId">
												<logic:iterate id="formSubCategories" name="javaApplicationUpdateForm" property="allSubCategories" scope="request">
													<logic:equal name="formSubCategories" property="subCategoryId" value="<%=javaApplicationUpdateForm.getAimsAppSubCategoryId().toString()%>">
														<bean:write name="formSubCategories" property="subCategoryName" />
													</logic:equal>
												</logic:iterate>
											</logic:notEmpty>
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
																	<H1>Application Files</H1>
																	<DIV class="headRightCurveblk"></DIV>
																</div>
															</td>
														</tr>
														<tr>
															<td style="padding: 0px">
																<table width="100%" height="100%" class="GridGradient" border="0" cellpadding="5" cellspacing="0">
																	<tr>
																		<th>
																			<strong>High Resolution Publisher Logo (JPEG/GIF/EPS):</strong>
																			<br/>
																			<logic:notEmpty name="javaApplicationUpdateForm" property="clrPubLogoFileName" scope="request">																
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=JavaClrPubLogo&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
																					 class="a" target="_blank"><bean:write name="javaApplicationUpdateForm" property="clrPubLogoFileName" /></a>
																			</logic:notEmpty>
																		</th>
																	</tr>
																	<tr>
																		<td>
																			<strong>Channel Title Graphic/Icon (JPEG/GIF/EPS):</strong>
																			<br />
																			<logic:notEmpty name="javaApplicationUpdateForm" property="appTitleNameFileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=JavaAppTitleName&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
																					 class="a" target="_blank"><bean:write name="javaApplicationUpdateForm" property="appTitleNameFileName" /></a>
																			</logic:notEmpty>																
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<strong><bean:message key="ManageApplicationForm.java.appSplashScreen" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																			<br />
																			<logic:notEmpty name="javaApplicationUpdateForm" property="splashScreenEpsFileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SplashScreenEps&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
																					 class="a" target="_blank"><bean:write name="javaApplicationUpdateForm" property="splashScreenEpsFileName" /></a>
																			</logic:notEmpty>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<strong><bean:message key="ManageApplicationForm.java.appActiveScreen" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																			<br />
																			<logic:notEmpty name="javaApplicationUpdateForm" property="activeScreenEpsFileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ActiveScreenEps&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
																					 class="a" target="_blank"><bean:write name="javaApplicationUpdateForm" property="activeScreenEpsFileName" /></a>
																			</logic:notEmpty>
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<strong><bean:message key="ManageApplicationForm.appScreenShot" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																			
																			<br />
																			<logic:notEmpty name="javaApplicationUpdateForm" property="screenJpegFileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
																					class="a" target="_blank"><bean:write name="javaApplicationUpdateForm" property="screenJpegFileName" /></a>
																			</logic:notEmpty>
																			
																			<br />
																			<logic:notEmpty name="javaApplicationUpdateForm" property="screenJpeg2FileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
																					 class="a" target="_blank"><bean:write name="javaApplicationUpdateForm" property="screenJpeg2FileName" /></a>
																			</logic:notEmpty>
			
																			<br />
																			<logic:notEmpty name="javaApplicationUpdateForm" property="screenJpeg3FileName" scope="request">									
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
																					class="a" target="_blank"> <bean:write name="javaApplicationUpdateForm" property="screenJpeg3FileName" /></a>
																			</logic:notEmpty>
			
																			<br />
																			<logic:notEmpty name="javaApplicationUpdateForm" property="screenJpeg4FileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
																					class="a" target="_blank"><bean:write name="javaApplicationUpdateForm" property="screenJpeg4FileName" /></a>
																			</logic:notEmpty>
			
																			<br />
																			<logic:notEmpty name="javaApplicationUpdateForm" property="screenJpeg5FileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
																					class="a" target="_blank"><bean:write name="javaApplicationUpdateForm" property="screenJpeg5FileName" /></a>
																			</logic:notEmpty>
																			
																		</td>
																	</tr>
																	<tr>
																		<td>
																			<strong><bean:message key="ManageApplicationForm.appFAQ" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
																			<br />
																			<logic:notEmpty name="javaApplicationUpdateForm" property="faqDocFileName" scope="request">
																				<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FaqDoc&app_id=<bean:write	name="javaApplicationUpdateForm"	property="appsId"	/>"
																					class="a" target="_blank"><bean:write name="javaApplicationUpdateForm" property="faqDocFileName" /></a>
																			</logic:notEmpty>
																		</td>																	
																</table>
															</td>
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
																	<H1>24x7 Technical Contact</H1>
																	<DIV class="headRightCurveblk"></DIV>
																</div>
															</td>
														</tr>
														<tr>
															<td style="padding: 0px">
																<table width="100%" height="100%" class="GridGradient" border="0" cellpadding="5" cellspacing="0">
																	<logic:notEmpty property="aimsContactId" name="javaApplicationUpdateForm">
																	<logic:iterate id="formContacts" name="javaApplicationUpdateForm" property="allContacts" scope="request">
																		<logic:equal name="formContacts" property="contactId" value="<%=javaApplicationUpdateForm.getAimsContactId().toString()%>">
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
							<%@ include file="include/javaAppViewButtons.jsp"%>						
						</td>
					</tr>
						
					<tr>
						<td width="100%">
					
						</td>
					</tr>
				</table>
				
			</div>
		</html:form>

	</div>
</div>