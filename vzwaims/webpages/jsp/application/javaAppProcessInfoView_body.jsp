<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<jsp:useBean id="javaApplicationUpdateForm" class="com.netpace.aims.controller.application.JavaApplicationUpdateForm" scope="request"/>
<%javaApplicationUpdateForm.setCurrentPage("page3");%>
<%@ include  file="include/javaAppVariables.jsp" %>

<%@ include file="/common/error.jsp"%>

<script language="javascript">

	
	var subCategoriesArray = new Array();

	function AimsAppSubCategory()
	{
	    this.subCategoryId = "";
	    this.subCategoryName = "";
	    this.aimsAppCategoryId = "";
	}
	<%
	    int	index	=	0;
    %>
	
	<logic:iterate id="formSubCategories"	name="javaApplicationUpdateForm" property="allSubCategories" scope="request">
	aimsAppSubCategory = new AimsAppSubCategory();
	aimsAppSubCategory.subCategoryId = "<bean:write	name="formSubCategories" property="subCategoryId"	/>";
	aimsAppSubCategory.subCategoryName = "<bean:write	name="formSubCategories" property="subCategoryName"	filter="false"/>";
	aimsAppSubCategory.aimsAppCategoryId = "<bean:write	name="formSubCategories" property="aimsAppCategoryId"	/>";
	subCategoriesArray[<%=index%>] = aimsAppSubCategory;
	<%index++;%>
	</logic:iterate>
	var supported = (window.Option) ? 1 : 0;


	function changeSubCategories() {
	    if (!supported) {
	        alert("Feature	not	supported");
	    }
	    var options = document.forms[0].aimsAppSubCategoryId.options;
	    for (var i = options.length - 1; i > 0; i--) {
	        options[i] = null;
	    }
	
	    options[0] = new Option("<bean:message	key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>", "0");
	    var k = 1;
	    var m = 0;
	
	    for (var j = 0; j < subCategoriesArray.length; j++) {
	
	        if (subCategoriesArray[j].aimsAppCategoryId == document.forms[0].aimsAppCategoryId.options[document.forms[0].aimsAppCategoryId.selectedIndex].value)
	        {
	            options[k] = new Option(subCategoriesArray[j].subCategoryName, subCategoriesArray[j].subCategoryId);
	            if (subCategoriesArray[j].subCategoryId == "<bean:write	name="javaApplicationUpdateForm" property="aimsAppSubCategoryId" scope="request"/>")
	                m = k;
	            k++;
	        }
	    }
	
	    options[m].selected = true;
	}
	
	function changeSubCategories1() {
	    if (!supported) {
	        alert("Feature	not	supported");
	    }
	    
	    var options = document.forms[0].appSubCategory1.options;
	    for (var i = options.length - 1; i > 0; i--) {
	        options[i] = null;
	    }
	
	    options[0] = new Option("<bean:message	key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>", "0");
	    var k = 1;
	    var m = 0;								
		
	    for (var j = 0; j < subCategoriesArray.length; j++) {
	
	        if (subCategoriesArray[j].aimsAppCategoryId == document.forms[0].appCategory1.options[document.forms[0].appCategory1.selectedIndex].value)
	        {
	            options[k] = new Option(subCategoriesArray[j].subCategoryName, subCategoriesArray[j].subCategoryId);
	            if (subCategoriesArray[j].subCategoryId == "<bean:write	name="javaApplicationUpdateForm" property="appSubCategory1" scope="request"/>")
	                m = k;
	            k++;
	        }
	    }
	
	    options[m].selected = true;
	}
		
	function changeSubCategories2() {
	    if (!supported) {
	        alert("Feature	not	supported");
	    }
	    var options = document.forms[0].appSubCategory2.options;
	    for (var i = options.length - 1; i > 0; i--) {
	        options[i] = null;
	    }
	
	    options[0] = new Option("<bean:message	key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>", "0");
	    var k = 1;
	    var m = 0;
	
	    for (var j = 0; j < subCategoriesArray.length; j++) {
	
	        if (subCategoriesArray[j].aimsAppCategoryId == document.forms[0].appCategory2.options[document.forms[0].appCategory2.selectedIndex].value)
	        {
	            options[k] = new Option(subCategoriesArray[j].subCategoryName, subCategoriesArray[j].subCategoryId);
	            if (subCategoriesArray[j].subCategoryId == "<bean:write	name="javaApplicationUpdateForm" property="appSubCategory2" scope="request"/>")
	                m = k;
	            k++;
	        }
	    }
	
	    options[m].selected = true;
	}
	
	
	function truncateLocalTextAreas(){
	
		if (typeof document.forms[0].initialApprovalNotes != "undefined")
			if (typeof document.forms[0].initialApprovalNotes.value != "undefined")
				TruncateText(document.forms[0].initialApprovalNotes,500);
		
		if (typeof document.forms[0].contentZipFileNotes != "undefined")
			if (typeof document.forms[0].contentZipFileNotes.value != "undefined")
				TruncateText(document.forms[0].contentZipFileNotes,500);								
	}
</script>

<div id="contentBox" style="float: left" onmousemove="truncateLocalTextAreas();">
	<DIV class="homeColumnTab ">
		<%@ include file="include/javaAppViewTabs.jsp"%>

		<html:form action="/javaApplicationUpdate"	enctype="multipart/form-data">
			<html:hidden property="currentPage" value="page3" />
			
			<div class="contentbox">			
				<%@ include file="include/javaAppHidden.jsp"%>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<%--Start Initial Approval/Denial --%>
						<%
						if (isOnDeckApp)
						{
					%>
							<tr>
								<td>
									<div class="lBox">
										<DIV class="headLeftCurveblk"></DIV>
										<H1>Content Programming Review</H1>
										<DIV class="headRightCurveblk"></DIV>
									</div>
								</td>
							</tr>
							
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0" class="">
										<tr>
											<td width="50%" valign="top">
												<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
													<tr>
														<th width="45%">
															<strong><bean:message key="ManageApplicationForm.appCategory" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
																<span class="requiredText">*</span>:
															</strong>
															<br />
															
															<logic:notEmpty name="javaApplicationUpdateForm" property="aimsAppCategoryId">
																<logic:iterate id="formCategories" name="javaApplicationUpdateForm" property="allCategories" scope="request">
																	<logic:equal name="formCategories" property="categoryId" value="<%=javaApplicationUpdateForm.getAimsAppCategoryId().toString()%>">
																		<bean:write name="formCategories" property="categoryName" />
																	</logic:equal>
																</logic:iterate>
														    </logic:notEmpty>															
														</th>
														<th width="55%">
															<strong>
																<bean:message key="ManageApplicationForm.appSubCategory" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
																<span class="requiredText">*</span>:
															</strong>
															<br />
															
															<logic:notEmpty name="javaApplicationUpdateForm" property="aimsAppSubCategoryId">
																<logic:iterate id="formSubCategories" name="javaApplicationUpdateForm" property="allSubCategories" scope="request">
																	<logic:equal name="formSubCategories" property="subCategoryId" value="<%=javaApplicationUpdateForm.getAimsAppSubCategoryId().toString()%>">
																		<bean:write name="formSubCategories" property="subCategoryName" />
																	</logic:equal>
																</logic:iterate>															
															</logic:notEmpty>	
														</th>
													</tr>
													
													<tr>
														<td > 
															<logic:notEmpty name="javaApplicationUpdateForm" property="appCategory1">
																<logic:iterate id="formCategories" name="javaApplicationUpdateForm" property="allCategories" scope="request">
																	<logic:equal name="formCategories" property="categoryId" value="<%=javaApplicationUpdateForm.getAppCategory1().toString()%>">
																		<bean:write name="formCategories" property="categoryName" />
																	</logic:equal>
																</logic:iterate>
														    </logic:notEmpty>															
														</td>
														<td >

															<logic:notEmpty name="javaApplicationUpdateForm" property="appSubCategory1">
																<logic:iterate id="formSubCategories" name="javaApplicationUpdateForm" property="allSubCategories" scope="request">
																	<logic:equal name="formSubCategories" property="subCategoryId" value="<%=javaApplicationUpdateForm.getAppSubCategory1().toString()%>">
																		<bean:write name="formSubCategories" property="subCategoryName" />
																	</logic:equal>
																</logic:iterate>															
															</logic:notEmpty>	
														</td>
													</tr>			
													
													<tr>
														<td >  
															<logic:notEmpty name="javaApplicationUpdateForm" property="appCategory2">
																<logic:iterate id="formCategories" name="javaApplicationUpdateForm" property="allCategories" scope="request">
																	<logic:equal name="formCategories" property="categoryId" value="<%=javaApplicationUpdateForm.getAppCategory2().toString()%>">
																		<bean:write name="formCategories" property="categoryName" />
																	</logic:equal>
																</logic:iterate>
														    </logic:notEmpty>															
														</td>
														<td >
															<logic:notEmpty name="javaApplicationUpdateForm" property="appSubCategory2">
																<logic:iterate id="formSubCategories" name="javaApplicationUpdateForm" property="allSubCategories" scope="request">
																	<logic:equal name="formSubCategories" property="subCategoryId" value="<%=javaApplicationUpdateForm.getAppSubCategory2().toString()%>">
																		<bean:write name="formSubCategories" property="subCategoryName" />
																	</logic:equal>
																</logic:iterate>															
															</logic:notEmpty>	
														</td>
													</tr>			
													
													
													 
													<tr>
														<td >
															<strong><bean:message key="JavaApplicationForm.label.ContentType" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
																<span class="requiredText">*</span>:
															</strong>
															<br />									
															
															<logic:notEmpty name="javaApplicationUpdateForm" property="contentType">
																<logic:iterate id="contentTypes" name="javaApplicationUpdateForm" property="allContentTypes" type="com.netpace.aims.model.core.AimsTypes">
								                                    <logic:equal name="contentTypes" property="typeId" value="<%=javaApplicationUpdateForm.getContentType().toString()%>">
								                                        <bean:write name="contentTypes" property="typeValue"/>
								                                    </logic:equal>
								                                </logic:iterate>
														    </logic:notEmpty>
														</td>
														<td >
															<strong>Projected Live Date&nbsp;<span class="requiredText">*</span>:</strong>
															<span style="font-weight: normal; font-variant: normal"><bean:write name="javaApplicationUpdateForm" property="projectedLiveDate" /></span>
														</td>
													</tr>	

													<tr>														
														<td >
															<strong><bean:message key="JavaApplicationForm.appKeyword" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
																<span class="requiredText">*</span>:
															</strong>
															<br />
															<span style="font-weight: normal; font-variant: normal"><bean:write name="javaApplicationUpdateForm" property="appKeyword" /></span>
														</td>
														
														<td >
															<strong>
																<logic:equal value="true" name="javaApplicationUpdateForm" property="ring2App">
                                                                    <bean:message key="JavaApplicationForm.table.head.javaondeck.contract" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:
                                                                </logic:equal>
																<logic:equal value="true" name="javaApplicationUpdateForm" property="ring3App">
                                                                    <bean:message key="JavaApplicationForm.table.head.javaoffdeck.contract" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:
                                                                </logic:equal>
															</strong>
															<br />
															<logic:notEmpty name="javaApplicationUpdateForm" property="javaAppContractId">
																<logic:iterate id="formContracts" name="javaApplicationUpdateForm" property="allJavaContracts" scope="request">												
																	<logic:equal name="formContracts" property="contractId" value="<%=javaApplicationUpdateForm.getJavaAppContractId().toString()%>">
																		<bean:write name="formContracts" property="contractTitle" />
																	</logic:equal>
																</logic:iterate>
															</logic:notEmpty>
														</td>
													</tr>
													
													<tr>														
														<td width="40%" valign="top" colspan="2">
																<strong>Notes:</strong>
																<br />
																<html:textarea styleClass="textareaField" property="initialApprovalNotes" rows="4" cols="50" readonly="true" />
														</td>
													</tr>
													
												</table>
											</td>
											
										</tr>
									</table>
								</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>

				
						<tr>
							<td>
								<div class="lBox">
									<DIV class="headLeftCurveblk"></DIV>
									<H1>Content Rating Review</H1>
									<DIV class="headRightCurveblk"></DIV>
								</div>
							</td>
						</tr>
						
						<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="5"
									class="GridGradient">
									<tr>	
										<td width="100%">
											<strong>
												<bean:message key="ManageApplicationForm.contentRating" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
												<span class="requiredText">*</span>:
											</strong>												
											<br />
											<logic:notEmpty name="javaApplicationUpdateForm" property="contentRating">
												<logic:iterate id="contentRatings" name="javaApplicationUpdateForm" property="allJavaContentRatings" type="com.netpace.aims.model.core.AimsTypes">
				                                    <logic:equal name="contentRatings" property="typeId" value="<%=javaApplicationUpdateForm.getContentRating().toString()%>">
				                                        <bean:write name="contentRatings" property="typeValue"/>
				                                    </logic:equal>
				                                </logic:iterate>
				                            </logic:notEmpty>   
										    
										</td>										
									</tr>
								</table>
							</td>
						</tr>						
					
					<%
						}
					%>
					
						<tr><td>&nbsp;</td></tr>

					 <%
						if (isOffDeckApp)
						{
					%>
							<tr>
								<td>
									<div class="lBox">
										<DIV class="headLeftCurveblk"></DIV>
										<H1>
											<bean:message key="JavaApplicationForm.table.head.javaoffdeck.contract" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />									
										</H1>
		
										<DIV class="headRightCurveblk"></DIV>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="5"
										class="GridGradient">
										<tr>
											<td>
												<strong>
                                                    <bean:message key="JavaApplicationForm.table.head.javaoffdeck.contract" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:
												</strong>
												<br />
												
												<logic:notEmpty name="javaApplicationUpdateForm" property="javaAppContractId">
													<logic:iterate id="formContracts" name="javaApplicationUpdateForm" property="allJavaContracts" scope="request">
														<logic:equal name="formContracts" property="contractId" value="<%=javaApplicationUpdateForm.getJavaAppContractId().toString()%>">
															<bean:write name="formContracts" property="contractTitle" />
														</logic:equal>
													</logic:iterate>
												</logic:notEmpty>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
							</tr>
					<%
						}
					%>



					 	<tr>
							<td>
								<div class="lBox">
									<DIV class="headLeftCurveblk"></DIV>
									<H1>Tax Review</H1>
									<DIV class="headRightCurveblk"></DIV>
								</div>
							</td>
						</tr>
					 	<tr>
							<td>
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="">
									<tr>
										<td width="50%">
											<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
												<tr>
													<th width="100%">
														<strong><bean:message key="JavaApplicationForm.label.TaxCategoryCode" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
															<span class="requiredText">*</span>:
														</strong>
														<br />
														<logic:notEmpty name="javaApplicationUpdateForm" property="aimsTaxCategoryCodeId">
															<logic:iterate id="taxCategoryCodes" name="javaApplicationUpdateForm" property="allTaxCategoryCodes">
							                                    <logic:equal name="taxCategoryCodes" property="taxCategoryCodeId" value="<%=javaApplicationUpdateForm.getAimsTaxCategoryCodeId().toString()%>">
							                                        <bean:write name="taxCategoryCodes" property="taxCategoryCode"/>
							                                    </logic:equal>
							                                </logic:iterate>
							                            </logic:notEmpty>										
													</th>														
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>						
						
						<tr><td>&nbsp;</td></tr>
					
							
						
					
					<%--End Initial Approval/Denial --%>					
					
					<%-- End Tracking --%>
					<tr>
						<td width="100%">
							<table width="100%" cellpadding="0" cellspacing="0" border="0">
								<%@ include file="include/javaAppViewButtons.jsp"%>
								<tr><td><%@ include file="include/javaMessageFooter.jsp" %></td> </tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</html:form>
	</div>
</div>
<script type="text/javascript">

</script>
