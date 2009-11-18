<%@ page language="java"
	import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>

<%@ include  file="include/javaAppVariables.jsp" %>
<jsp:useBean id="task" class="java.lang.String" scope="request" />
<jsp:useBean id="ApplicationUpdateForm"
	class="com.netpace.aims.controller.application.ApplicationUpdateForm"
	scope="request" />
<%
	boolean isConcept = false;
	ApplicationUpdateForm.setCurrentPage("page1");
	int applicationURLIndex = 0;
%>

<logic:equal name="javaApplicationUpdateForm" property="isConcept"
	scope="request" value="Y">
	<%
	isConcept = true;
	%>
</logic:equal>
<script language="javascript">

function trackCountForTextAreas(){
    TrackCount(document.forms[0].shortDesc, 'textCountShortDesc', 200);
    TrackCount(document.forms[0].longDesc, 'textCountLongDesc', 500);
}

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
function truncateLocalTextAreas()
{
    TruncateTextWithCount(document.forms[0].shortDesc, 'textCountShortDesc', 200);
    TruncateTextWithCount(document.forms[0].longDesc, 'textCountLongDesc', 500);    
}
<%@ include  file="include/javaJScript.jsp" %>

</script>

<%@ include file="include/javaMessageHeader.jsp"%>

<%
	if ( !isLocked )
	{
 %>
		<div id="contentBox" style="float:left" onMouseMove="truncateLocalTextAreas();">
<%
	}
 %>
	<DIV class="homeColumnTab lBox">	
		<%@ include file="include/javaTabs.jsp"%>
		<html:form action="/javaApplicationUpdate"
			enctype="multipart/form-data"
			onsubmit="javascript:return submitFrm();">
			<div class="contentbox">
				<%@ include file="include/javaAppHidden.jsp"%>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>
									<bean:message key="ApplicationForm.table.head.app.details"
										bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
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
									<th width=50%>
										<strong><bean:message
												key="ManageApplicationForm.appTitle"
												bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span
											class="requiredText">*</span>:</strong>
										<br />
										<html:text property="title" size="40" maxlength="200"
											styleClass="inputField" />
									</th>
									<th>
										<strong><bean:message
												key="ManageApplicationForm.appVersion"
												bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span
											class="requiredText">*</span>:</strong>
										<br />
										<html:text property="version" size="30" maxlength="30"
											styleClass="inputField" />
									</th>
								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="5"
								class="GridGradient">
								<tr>
									<td width=50%>
										<strong><bean:message
												key="ManageApplicationForm.appShortDesc"
												bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span
											class="requiredText">*</span>:</strong>
										<br />
										<html:textarea property="shortDesc" rows="4" cols="50"
											styleClass="textareaField"
											onkeyup="TrackCount(this,'textCountShortDesc',200)"
											onkeypress="LimitText(this,200)"></html:textarea>
										<br />
										<span id="airTime" style="display: none"><c:out value="${requestScope.AirTimeText}" escapeXml="false"></c:out>
										</span>
										<table border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td style="vertical-align:top;padding:0">
													<bean:message
														key="ManageApplicationForm.textarea.char.remaining"
														bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
												</td>
												<td>
													<input type="text" name="textCountShortDesc" size="3"
														value="200" disabled="true" />
												</td>
											</tr>
										</table>
									</td>
									<td>
										<strong><bean:message
												key="ManageApplicationForm.appLongDesc"
												bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span
											class="requiredText">*</span>:</strong>
										<br />
										<html:textarea property="longDesc" rows="4" cols="40"
											styleClass="textareaField"
											onkeyup="TrackCount(this,'textCountLongDesc',500)"
											onkeypress="LimitText(this,500)"></html:textarea>
										<br />
										<table border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td style="vertical-align:top;padding:0">
													<bean:message
														key="ManageApplicationForm.textarea.char.remaining"
														bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
												</td>
												<td>
													<input type="text" name="textCountLongDesc" size="3"
														value="500" disabled="true" />
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
							<table width="100%" border="0" cellspacing="0" cellpadding="5"
								class="GridGradient">
								<tr>

									<td width="50%">
										<strong>
											<bean:message key="ManageApplicationForm.contentRating" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;
											<span class="requiredText">*</span>:
										</strong>
											<bean:message key="JavaApplicationForm.info.label.contentRating" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
										<br />
										<html:select styleClass="selectField" property="contentRating" size="1">
											<html:option value="0">
												<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
											</html:option>
											<html:optionsCollection property="allJavaContentRatings" label="typeValue" value="typeId" />
										</html:select>
									</td>
									<td>
										<strong><bean:message
												key="JavaApplicationForm.infoURL"
												bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span
											class="requiredText">
												<logic:equal value="true" name="javaApplicationUpdateForm" property="ring2App">*</logic:equal></span>:</strong>
										<br />
										<html:text property="infoURL" size="40" maxlength="200"
											styleClass="inputField" />
									</td>
								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="5"
								class="GridGradient">
								<tr>
									<td colspan="2" width="50%">
										<strong><bean:message key="JavaApplicationForm.appKeyword" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span
											class="requiredText">*</span>:</strong> <bean:message key="JavaApplicationForm.appKeyword.addDesc" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
										<br />
										<logic:equal value="true" name="javaApplicationUpdateForm" property="ring2App">
											<html:text property="appKeyword" size="40" maxlength="200" styleClass="inputField"/>
										</logic:equal>
										<logic:equal value="true" name="javaApplicationUpdateForm" property="ring3App">
											<html:text property="appKeyword" size="40" maxlength="200" styleClass="inputField" readonly="true" />
										</logic:equal>
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
					<%-- end content filter --%>
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>
									<bean:message
										key="ApplicationForm.table.head.app.classification"
										bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
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
									<th width=50%>
										<strong><bean:message
												key="ManageApplicationForm.appCategory"
												bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span
											class="requiredText">
												*</span>:</strong>
										<br />

										<html:select property="aimsAppCategoryId" size="1"
											onchange="changeSubCategories();" styleClass="selectField">
											<html:option value="0">
												<bean:message key="ManageApplicationForm.label.selectOne"
													bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
											</html:option>
											<html:optionsCollection property="allCategories"
												label="categoryName" value="categoryId" />
										</html:select>
									</th>
									<th>
										<strong><bean:message
												key="ManageApplicationForm.appSubCategory"
												bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span
											class="requiredText">
												*</span>:</strong>
										<br />
										<html:select property="aimsAppSubCategoryId" size="1"
											styleClass="selectField">
											<html:option value="0">
												<bean:message key="ManageApplicationForm.label.selectOne"
													bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
											</html:option>
										</html:select>
									</th>
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
								<H1>
									<bean:message key="ApplicationForm.table.head.prrelease"
										bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
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
									<th width="100%">
										<strong><bean:message
												key="ManageApplicationForm.prrelease"
												bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />&nbsp;<span
											class="requiredText">
												<logic:equal value="true" name="javaApplicationUpdateForm" property="ring2App">*</logic:equal></span>:</strong>
									</th>
								</tr>
								<tr>
									<td style="padding-top: 0px">
										<html:radio property="ifPrRelease" value="Y" name="javaApplicationUpdateForm" />
										<bean:message key="ManageApplicationForm.radio.label.yes"
											bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
										&nbsp;
										<html:radio property="ifPrRelease" value="N" name="javaApplicationUpdateForm" />
										<bean:message key="ManageApplicationForm.radio.label.no"
											bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
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
					
					<%  if(isRFI && !isVerizonUser) { %>
					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1>
									Comments
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
									<td width=50%>
										<html:textarea property="comments" rows="4" cols="50" styleClass="textareaField" onkeypress="LimitText(this,500)"></html:textarea>
										<br />
										<span id="airTime" style="display: none"><c:out value="${requestScope.AirTimeText}" escapeXml="false"></c:out>
										</span>										
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<% } %>

					<tr>
						<td>
							&nbsp;
						</td>
					</tr>					
					
					<%@ include file="include/javaAppEditButtons.jsp"%>
					
					<tr>
						<td>
							<br/>
							<table cellspacing="0" cellpadding="0" border="0" width="100%">
								<tr>
									<td class="borderT">											
										<p class="bodySmallText">
										*For Music Applications we encourage Developer Partners to join our Music Affiliate program. Verizon will work closely with selected Affiliate partners to integrate the V CAST Music and Tones catalog into their application.  We also recommend that partners follow <a href="http://sjsvzdn.netpace.com/jsps/devCenters/MusicVideo/Landing_Pages/vcast_goto_mrkt_mus_app_stds_dtls.jsp">Industry Leading Music Content Standards</a> to offer their application to the broadest audience possible. 
										<br/>
										<br/>
										For more details on the Music Affiliate program, please email <a href="mailto:developerrelations@verizonwireless.com">developerrelations@verizonwireless.com</a>
										</p>
									</td>
								</tr>							
							</table>
						</td>
					</tr>
					
					<tr>
						<td>
							<%@ include file="include/javaMessageFooter.jsp"%>
						</td>
					</tr>
					
					<script language="javascript">
						changeSubCategories();
						trackCountForTextAreas();
					
						<%
							if ( isLocked ) 
							{
						%>		
								function lockApplicationPage1() {			
									var frm = document.forms[0];
				                        frm.title.disabled = true;
				                        frm.version.disabled = true;
				                        frm.shortDesc.disabled = true;
				                        frm.longDesc.disabled = true;
				                        frm.contentRating.disabled = true;
				                        frm.infoURL.disabled = true;
				                        frm.appKeyword.disabled = true;
				                        frm.aimsAppCategoryId.disabled = true;
				                        frm.aimsAppSubCategoryId.disabled = true;				                        
				                        frm.ifPrRelease[0].disabled = true;
				                        frm.ifPrRelease[1].disabled = true;
								}//end lockApplicationPage1
								lockApplicationPage1();
						<% 
							}
						%>
					
					</script>
					
					<% 
					if (isLocked) 
					{
					%>
						<html:hidden property="title"	/>
						<html:hidden property="version"/>
						<html:hidden property="shortDesc"/>
						<html:hidden property="longDesc"/>
						<html:hidden property="contentRating"/>
						<html:hidden property="infoURL"/>
						<html:hidden property="appKeyword"/>
						<html:hidden property="aimsAppCategoryId"/>	
						<html:hidden property="aimsAppSubCategoryId"/>										
						<html:hidden property="ifPrRelease"/>
					<%
					}
					%>
					
					<%
						if (((com.netpace.aims.model.core.AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE)) 
						{
					%>
							<html:hidden property="language" />
							<html:hidden property="appSize" />
							<html:hidden property="aimsAppCategoryId" />
							<html:hidden property="aimsAppSubCategoryId" />
							<html:hidden property="ifPrRelease" />
					<%
						} 
						else 
						{
						}
					%>
					<%
					if (!statusSaved) 
					{
					%>
						<html:hidden property="isLbs" />
					<%
					}
					%>
				</table>
			</div>
		</html:form>
	</div>
</div>