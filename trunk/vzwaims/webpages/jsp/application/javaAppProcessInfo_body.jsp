<%@	page language="java"
	import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ include  file="include/javaAppVariables.jsp" %>

<script language="javascript">
	<%@ include  file="include/javaJScript.jsp" %>
	
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

		if( !( document.forms[0].aimsAppSubCategoryId == null )) {

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
	}
	
	function changeSubCategories1() {
	    if (!supported) {
	        alert("Feature	not	supported");
	    }
	    if( !( document.forms[0].appSubCategory1 == null) ){
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
	}
		
	function changeSubCategories2() {
	    if (!supported) {
	        alert("Feature	not	supported");
	    }
		if(!( document.forms[0].appSubCategory2 == null )  ){
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
	}
	
	
	function truncateLocalTextAreas(){
	
		if (typeof document.forms[0].initialApprovalNotes != "undefined")
			if (typeof document.forms[0].initialApprovalNotes.value != "undefined")
				TruncateText(document.forms[0].initialApprovalNotes,500);
		
		if (typeof document.forms[0].contentZipFileNotes != "undefined")
			if (typeof document.forms[0].contentZipFileNotes.value != "undefined")
				TruncateText(document.forms[0].contentZipFileNotes,500);								
	}
	
	function disableAfterProcess(){
		var frm = document.forms[0];
		
		<%	
			if (isEqualorAboveContentStandardReview && isOnDeckApp  )
			{
		%>		frm.aimsAppCategoryId.disabled = true;
				frm.aimsAppSubCategoryId.disabled = true;
			    
			    frm.appCategory1.disabled = true;
			    frm.appSubCategory1.disabled = true;
			    
			    frm.appCategory2.disabled = true;
			    frm.appSubCategory2.disabled = true;
			    
			    frm.contentType.disabled = true;
			    frm.projectedLiveDate.disabled = true;
			    
			    <logic:equal property="enterpriseApp" name="javaApplicationUpdateForm" scope="request" value="Y">
			    	frm.enterpriseId.disabled = true;
			    </logic:equal>
			    frm.appKeyword.disabled = true;
			    				    
			    frm.initialApprovalNotes.disabled = true;
			    frm.javaAppContractId.disabled = true;
        <% 	} %>				
		
		<%	
			if (isEqualorAboveProgTaxReview || lockContentRating )
			{
		%>		
				frm.contentRating.disabled = true;				
        <%  } %>
        
        <%	
			if ( isAboveProgTaxReview )
			{
		%>		
				frm.aimsTaxCategoryCodeId.disabled = true;				
        <%  } %>		
        
	}	
	
	function showChannels() {
        var popupURL = "/aims/javaChannels.do";
        var childWindow = window.open(popupURL,"channels","resizable=0,width=870,height=575,scrollbars=1,screenX=100,left=150,screenY=30,top=30,status=0,titlebar=0");
        if (childWindow.opener == null) childWindow.opener = self;
        childWindow.focus();
    }
	
	function collapseRow(sectionRow) {
    	document.getElementById("row"+sectionRow).style.display='none';
	    document.getElementById("spnExpandCollapse"+sectionRow).innerHTML="<a class='a' onclick='javascript:expandRow(\""+sectionRow+"\");'>[+]</a>";
    	return false;
	}
	function expandRow(sectionRow) 
	{
    	document.getElementById("row"+sectionRow).style.display='';
	    document.getElementById("spnExpandCollapse"+sectionRow).innerHTML="<a class='a' onclick='javascript:collapseRow(\""+sectionRow+"\");'>[-]</a>";
	    
    	return false;
	}
</script>


<%@ include file="include/javaMessageHeader.jsp" %>
<div id="contentBox" style="float: left" onmousemove="truncateLocalTextAreas();">
	<DIV class="homeColumnTab ">

		<%@ include file="include/javaTabs.jsp"%>
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
											<td width="53%">
												<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
													<tr>
														<th width="45%">
															<strong><bean:message key="ManageApplicationForm.appCategory" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
																<span class="requiredText">*</span>:
															</strong>
															<br />
															<html:select property="aimsAppCategoryId" size="1" onchange="changeSubCategories();" styleClass="selectField">
																<html:option value="0">
																	<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
																</html:option>
																<html:optionsCollection property="allCategories" label="categoryName" value="categoryId" />
															</html:select>
														</th>
														<th width="55%">
															<strong>
																<bean:message key="ManageApplicationForm.appSubCategory" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
																<span class="requiredText">*</span>:
															</strong>
															<br />
															<html:select property="aimsAppSubCategoryId" size="1" styleClass="selectField" style="width:250px">
																<html:option value="0">
																	<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
																</html:option>
															</html:select>
														</th>
													</tr>
													
													
													
													
													<tr>
														<td > 
															<html:select property="appCategory1" size="1" onchange="changeSubCategories1();" styleClass="selectField">
																<html:option value="0">
																	<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
																</html:option>
																<html:optionsCollection property="allCategories" label="categoryName" value="categoryId" />
															</html:select>
														</td>
														<td >
															<html:select property="appSubCategory1" size="1" styleClass="selectField">
																<html:option value="0">
																	<bean:message key="ManageApplicationForm.label.selectOne"
																		bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
																</html:option>
															</html:select>
														</td>
													</tr>			
													
													
													
													
													
													<tr>
														<td >  
															<html:select property="appCategory2" size="1" onchange="changeSubCategories2();" styleClass="selectField">
																<html:option value="0">
																	<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
																</html:option>
																<html:optionsCollection property="allCategories" label="categoryName" value="categoryId" />
															</html:select>
														</td>
														<td >
															<html:select property="appSubCategory2" size="1" styleClass="selectField">
																<html:option value="0">
																	<bean:message key="ManageApplicationForm.label.selectOne"
																		bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
																</html:option>
															</html:select>
														</td>
													</tr>			
													
													
													 
													<tr>
														<td >
															<strong><bean:message key="JavaApplicationForm.label.ContentType" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
																<span class="requiredText">*</span>:
															</strong>
															<br />
															<html:select property="contentType" size="1" styleClass="selectField">
																<html:option value="0">
																	<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
																</html:option>
																<html:optionsCollection property="allContentTypes" label="typeValue" value="typeId" />
															</html:select>
														</td>
														<td >
															<strong>Projected Live Date&nbsp;<span class="requiredText">*</span>:</strong>
															<br />
															<%-- disable date picker if application is locked--%>
											                <%
											                    String onClkLivePicker = "toggleDatePicker('daysOfMonth1','javaApplicationUpdateForm.projectedLiveDate')";											                   
											                %>
											                 <html:text styleClass="inputField" property="projectedLiveDate" size="15" maxlength="10"/>
											                 <img name="daysOfMonth1Pos" onclick="<%=onClkLivePicker%>" id="daysOfMonth1Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth1"/>											                 
														</td>
													</tr>	

													<tr>
														<logic:equal property="enterpriseApp" name="javaApplicationUpdateForm" scope="request" value="Y">
															<td >																
																<strong>
																	<bean:message key="JavaApplicationForm.enterpriseId" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
																	<span class="requiredText">*</span>:
																</strong>
																<br />
																<html:text property="enterpriseId" size="15" maxlength="100" styleClass="inputField" />
															</td>
														</logic:equal>
														
														<logic:equal property="enterpriseApp" name="javaApplicationUpdateForm" scope="request" value="Y">
															<td >
														</logic:equal>
														<logic:equal property="enterpriseApp" name="javaApplicationUpdateForm" scope="request" value="N">
															<td width="100%" colspan="2">
														</logic:equal>														
															<strong>
																<bean:message key="JavaApplicationForm.appKeyword" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
																<span class="requiredText">*</span>:
															</strong>
															<br />
															<html:text property="appKeyword" size="20" maxlength="200" styleClass="inputField" />
														</td>
													</tr>
													
													<tr>
														<td valign="top">
															<strong>
																<logic:equal value="true" name="javaApplicationUpdateForm" property="ring2App">
                                                                    <bean:message key="JavaApplicationForm.table.head.javaondeck.contract" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:
                                                                </logic:equal>
																<logic:equal value="true" name="javaApplicationUpdateForm" property="ring3App">
                                                                    <bean:message key="JavaApplicationForm.table.head.javaoffdeck.contract" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:
                                                                </logic:equal>
															</strong>
															<br />
                                                            <%-- show associated contract as label --%>
                                                            <logic:notEmpty name="javaApplicationUpdateForm" property="javaAppContractId">
																<logic:iterate id="formContracts" name="javaApplicationUpdateForm" property="allJavaContracts" scope="request">
																	<logic:equal name="formContracts" property="contractId" value="<%=javaApplicationUpdateForm.getJavaAppContractId().toString()%>">
																		<bean:write name="formContracts" property="contractTitle" />
																	</logic:equal>
																</logic:iterate>
															</logic:notEmpty>
                                                            <html:hidden property="javaAppContractId"/>
                                                            <%--
                                                            <html:select property="javaAppContractId" size="1" styleClass="selectField">
																<html:optionsCollection property="allJavaContracts" label="contractTitle" value="contractId" />
															</html:select>
															--%>
														</td>
														<td width="40%" valign="top">
																<strong>Notes&nbsp;<!--<span class="requiredText">*</span>:--></strong>																
																<br />
																<html:textarea styleClass="textareaField" property="initialApprovalNotes" onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)" rows="4" cols="50"></html:textarea>
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

					<%
						}
					%>

					<%
						if (isEqualorAboveContentStandardReview && isOnDeckApp)
						{
					%>
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
												<html:select styleClass="selectField" property="contentRating" size="1">
													<html:option value="0">
														<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
													</html:option>
													<html:optionsCollection property="allJavaContentRatings" label="typeValue" value="typeId" />
												</html:select>
											</td>										
										</tr>
									</table>
								</td>
							</tr>						
						
							<tr><td>&nbsp;</td></tr>
					<%
						}
						else
						{
					%>
							<html:hidden property="contentRating"  />							
					<%
						}
					 %>
					 
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
					<%
						if (isEqualorAboveProgTaxReview)
						{
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
															<html:select property="aimsTaxCategoryCodeId" size="1" styleClass="selectField">
																<html:option value="0">
																	<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
																</html:option>
																<html:optionsCollection property="allTaxCategoryCodes" label="taxCategoryCode" value="taxCategoryCodeId" />
															</html:select>
														</th>														
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>						
							
							<tr><td>&nbsp;</td></tr>
					<%
					 	}
					 	else
					 	{
					%>
							<html:hidden property="aimsTaxCategoryCodeId"  />
					<%
						}
					 %>
						
					<logic:notEmpty name="javaApplicationUpdateForm" property="wiTitle">
						<tr>
							<td>
								<div class="lBox">
									<DIV class="headLeftCurveblk"></DIV>
									<H1>Work Item</H1>
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
														<strong>
															Name&nbsp;
															<span class="requiredText">*</span>:&nbsp;
														</strong>
														<bean:write name="javaApplicationUpdateForm" property="wiTitle"/>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

														<strong>
															Start Date&nbsp;
															<span class="requiredText">*</span>:&nbsp;
														</strong>
														<bean:write name="javaApplicationUpdateForm" property="wiStartDate"/>													
													
													
													
													</th>														
												</tr>												
												
												<tr>
													<td width="100%">
									
													</td>														
												</tr>
												<tr>
													<td width="100%">
													<strong>
															Action&nbsp;
															<span class="requiredText">*</span>:&nbsp;
														</strong>
													
													
														<html:hidden name="javaApplicationUpdateForm" property="wiWorkitemId"/>
														<html:hidden name="javaApplicationUpdateForm" property="wiStartDate"/>
														<html:hidden name="javaApplicationUpdateForm" property="wiTitle"/>
														<html:select property="selectedAction" styleClass="selectField" style="width: 200px;">
															<html:optionsCollection name="javaApplicationUpdateForm" property="wiActions" label="label" value="value"/>
														</html:select>&nbsp;&nbsp;
														<span id="spnExpandCollapse1" style="cursor:pointer;">
								                        	<a class="a" onclick="javascript:expandRow(1)">[+]</a>
								                    	</span>
													</td>														
												</tr>												
												
												<tr id="row1" style="display:none;">
								                    <td>
								                    	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 0px solid black;">
								                    		<tr>
								                    			<td style="vertical-align:middle; padding-left:0px;" width="10%">
																	<strong>Comments:&nbsp;</strong>
																</td>
																<td>
																	<html:textarea property="actionComments" name="javaApplicationUpdateForm" styleClass="textareaField" rows="4" cols="70"/>
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
					
					</logic:notEmpty>
						
							
						
					
					<%--End Initial Approval/Denial --%>					
					
					<%-- End Tracking --%>
					<tr>
						<td width="100%">
							<table width="100%" cellpadding="0" cellspacing="0" border="0">
								<%@ include file="include/javaAppEditButtons.jsp"%>
								<tr><td><%@ include file="include/javaMessageFooter.jsp" %></td> </tr>
							</table>
							<script language="javascript">
								changeSubCategories();
								changeSubCategories1();
								changeSubCategories2();
								
								disableAfterProcess();
               				</script>

                			<%if (isEqualorAboveContentStandardReview){%>
                    			<html:hidden property="aimsAppSubCategoryId"  />
                    			<html:hidden property="aimsAppCategoryId"  />
                    			<html:hidden property="appSubCategory1"  />
                    			<html:hidden property="appCategory1"  />
                    			<html:hidden property="appSubCategory2"  />
                    			<html:hidden property="appCategory2"  />
                    			<html:hidden property="projectedLiveDate"  />
                    			<html:hidden property="contentType"  />
                    			<html:hidden property="appKeyword"  />
                    			<html:hidden property="enterpriseId"  />
                    			<html:hidden property="initialApprovalNotes"  />
                    			<html:hidden property="javaAppContractId"  />
                    		<% } %>
                			<%if (isEqualorAboveProgTaxReview){%>                				
                				<html:hidden property="contentRating"  />                    			
                			<% } %>
                			<%if (isEqualOrAboveContentApproval){%>
                    			<html:hidden property="contentZipFileAction"  />
                			<% } %>
                			<%if (isEqualOrAboveMoveToProduction){%>
                    			<html:hidden property="moveToProduction"  />                    			
                			<% } %>
                			<%if (isEqualSunset){%>
                    			<html:hidden property="remove"  />                    			
                			<% } %>
                			<%if (isAboveProgTaxReview){%>
                				<html:hidden property="aimsTaxCategoryCodeId"  />
                			<%} %>
                			
						</td>
					</tr>
				</table>
			</div>
		</html:form>
	</div>
</div>
<script type="text/javascript">

</script>