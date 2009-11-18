<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<jsp:useBean id="WapApplicationUpdateForm" class="com.netpace.aims.controller.application.WapApplicationUpdateForm" scope="request" /><%WapApplicationUpdateForm.setCurrentPage("page1");%>
<% int applicationURLIndex=0;%>
<%@ include  file="include/wapAppVariables.jsp" %>
<script	language="javascript">

    var helpVersion = "<bean:message key="WapApplicationForm.help.Version" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>";
    var helpWebsiteURL = "<bean:message key="WapApplicationForm.help.WebsiteURL" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>";
    var helpWAPDemoURL = "<bean:message key="WapApplicationForm.help.WAPDemoURL" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>";    
    var helpProductionURL = "<bean:message key="WapApplicationForm.help.ProductionURL" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>";
    //use single quote for this variable, as its text contains double quote
    var helpVendorProductCode = '<bean:message key="WapApplicationForm.help.VendorProductCode" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';


    var	subCategoriesArray = new Array();

	function AimsAppSubCategory()
	{
		this.subCategoryId = "";
		this.subCategoryName = "";
		this.aimsAppCategoryId = "";
	}
    
	<%
	int	index	=	0;
	%>

	<logic:iterate id="formSubCategories"	name="WapApplicationUpdateForm" property="allSubCategories" scope="request">
		aimsAppSubCategory = new AimsAppSubCategory();
		aimsAppSubCategory.subCategoryId = "<bean:write	name="formSubCategories" property="subCategoryId"	/>";
		aimsAppSubCategory.subCategoryName = "<bean:write	name="formSubCategories" property="subCategoryName"	/>";
		aimsAppSubCategory.aimsAppCategoryId = "<bean:write	name="formSubCategories" property="aimsAppCategoryId"	/>";
		subCategoriesArray[<%=index%>] = aimsAppSubCategory;
		<%index++;%>
	</logic:iterate>

    <%
    index   =   0;
    %>

    

	var	supported	=	(window.Option)	?	1	:	0;


	function changeSubCategories() 
    {
        if	(!supported) 
        {
            alert("Feature	not	supported");
        }
        var options = document.forms[0].subCategoryId1.options;
        for (var i = options.length - 1; i > 0; i--)
        {
            options[i]	=	null;
        }

        options[0]= new Option("<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>", "0");
        var k=1;
        var m = 0;

        for (var j = 0; j < subCategoriesArray.length; j++)
        {
            if	(subCategoriesArray[j].aimsAppCategoryId ==	document.forms[0].categoryId1.options[document.forms[0].categoryId1.selectedIndex].value)
            {
                options[k] = new Option(subCategoriesArray[j].subCategoryName, subCategoriesArray[j].subCategoryId);
                if (subCategoriesArray[j].subCategoryId=="<bean:write	name="WapApplicationUpdateForm" property="subCategoryId1" scope="request"/>")
                    m=k;
                k++;
            }
        }
        options[m].selected = true;
    }
    
   
	function truncateLocalTextAreas()
	{
        if (typeof document.forms[0].shortDesc != "undefined")
            if (typeof document.forms[0].shortDesc.value != "undefined") 
                TruncateTextWithCount(document.forms[0].shortDesc,'textCountShortDesc',200);
                
        if (typeof document.forms[0].longDesc != "undefined")
            if (typeof document.forms[0].longDesc.value != "undefined") 
                TruncateTextWithCount(document.forms[0].longDesc,'textCountLongDesc',500);
                
        if (typeof document.forms[0].descContentOffering != "undefined")
            if (typeof document.forms[0].descContentOffering.value != "undefined") 
                TruncateText(document.forms[0].descContentOffering,500);
	}

	function trackCountForTextAreas()
	{
		TrackCount(document.forms[0].shortDesc,'textCountShortDesc',200);
		TrackCount(document.forms[0].longDesc,'textCountLongDesc',500);
	}

    function disable1()
    {
        document.forms[0].categoryId1.disabled = true;
        document.forms[0].subCategoryId1.disabled = true;
        document.forms[0].descContentOffering.disabled = true;
        document.forms[0].demoUrl.disabled = true;
        document.forms[0].testUrl.disabled = true;
        document.forms[0].productionUrl.disabled = true;
        document.forms[0].websiteUrl.disabled = true;
        
        for (var i=0; i<document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == "ifPrRelease") {
                document.forms[0].elements[i].disabled = true;
            }
        }    
    }
    
    function disable2()
    {
        document.forms[0].title.disabled = true;
        document.forms[0].version.disabled = true;
        document.forms[0].longProductName.disabled = true;
        document.forms[0].vendorProductCode.disabled =   true;
        document.forms[0].shortDesc.disabled = true;
        document.forms[0].longDesc.disabled = true;
        
        for (var i=0; i<document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == "contentType") {
                document.forms[0].elements[i].disabled = true;
            }
            if (document.forms[0].elements[i].name == "wapVersion") {
                document.forms[0].elements[i].disabled = true;
            }
        }    
    }

    function disable3()
    {
        document.forms[0].vendorProductCode.disabled =   true;
    }
         
    function setContentType()
    {
        for (var i = 0; i < document.forms[0].elements.length; i++)
            if (document.forms[0].elements[i].name == "wapVersion")
                if (document.forms[0].elements[i].value == '<%=AimsConstants.WAP_APP_VERSION_WAP_1_0[0].toString()%>')
                    if (document.forms[0].elements[i].checked)
                        for (var j = 0; j < document.forms[0].elements.length; j++)
                            if (document.forms[0].elements[j].name == "contentType")
                                if (document.forms[0].elements[j].value == '<%=AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[0].toString()%>')
                                    if (!document.forms[0].elements[j].checked)
                                    {
                                        document.forms[0].elements[j].checked = true;
                                        alert('Content Type can only be Standard for WAP 1.0');
                                    }                                
    }

    function submitFrm() {
        var frm = document.forms[0];        
        if(!frm.networkUsage.checked) {
            removeAllApplicationURLs();
        }
        else {
            removeEmptyApplicationURLs(0);
        }
        return true;
    }

    function doEnableDisable(checkboxField) {
        var msg = 'You are about to disable Network Usage, All your URLs will be Deleted! Are you sure you want to proceed?';
        if(checkboxField.checked) {
            enableDisableNetworkUsage(checkboxField);
        }
        else {
            if(confirm(msg)) {
                enableDisableNetworkUsage(checkboxField);
            }
            else {
                checkboxField.checked = true;
            }
        }
    }

    <%@ include  file="include/wapAppJScript.jsp" %>
	
</script>
<%@ include  file="include/applicationUrlJScript.jsp" %>
<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left" onmousemove="truncateLocalTextAreas();">
  <div class="homeColumnTab">
    <%@ include  file="include/wapAppTabs.jsp" %>
    <div class="contentbox">
      <html:form action="/wapApplicationUpdate.do"    enctype="multipart/form-data" onsubmit="javascript:return submitFrm();">
        <%@ include  file="include/wapAppHidden.jsp" %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.app.details"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
                <tr>
					<th width="50%"><strong>Short Product Name&nbsp;<span class="requiredText">*</span>:</strong></th>
					<th width="50%">
                        <strong>Product Version&nbsp;<span class="requiredText">*</span>:</strong>&nbsp;
                        <a onClick="openZonHelpWindow(event, helpVersion); return false;" href="#">[?]</a>
                    </th>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="title" size="40" maxlength="13" /> (Max: 13 chars)</td>
					<td><html:text styleClass="inputField" property="version"  size="30" maxlength="10"/> <i>(Underscore '_' is not allowed)</i></td>
				</tr>
				
				<tr>
					<td><strong>Long Product Name&nbsp;<span class="requiredText">*</span>:</strong></td>
					<td>
                        <strong>Vendor Product Code&nbsp;<span class="requiredText">*</span>:</strong>&nbsp;
                        <a onClick="openZonHelpWindow(event, helpVendorProductCode); return false;" href="#">[?]</a>
                    </td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="longProductName" size="40" maxlength="20" /> (Max: 20 chars)</td>
					<td><html:text styleClass="inputField" property="vendorProductCode"  size="35" maxlength="20"     /> <i>(Underscore '_' is not allowed)</i></td>
				</tr>
				
				<tr>
					<td><strong><bean:message key="ManageApplicationForm.appShortDesc"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>: (Max: 200 chars)</strong></td>
					<td><strong><bean:message key="ManageApplicationForm.appLongDesc"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>: (Max: 500 chars)</strong></td>
				</tr>
				<tr>
					<td>
						<html:textarea styleClass="textareaField" property="shortDesc" rows="3"   cols="50" onkeyup="TrackCount(this,'textCountShortDesc',200)" onkeypress="LimitText(this,200)"></html:textarea>
						<br/><bean:message key="ManageApplicationForm.textarea.char.remaining"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
						<input type="text" name="textCountShortDesc" size="3" value="200" disabled="true" />
					</td>
					<td>
						<html:textarea styleClass="textareaField"  property="longDesc" rows="3"    cols="50" onkeyup="TrackCount(this,'textCountLongDesc',500)" onkeypress="LimitText(this,500)" ></html:textarea>
						<br/><bean:message key="ManageApplicationForm.textarea.char.remaining"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
						<input type="text" name="textCountLongDesc" size="3" value="500" disabled="true" />
					</td>
				</tr>
				
				<tr style="padding-top:5px">
					<td><strong>Description of Content Offering:</strong></td>
					<td><strong>Content Type&nbsp;<span class="requiredText">*</span>:</strong></td>
				</tr>
				<tr >
					<td rowspan="3">
						<html:textarea  property="descContentOffering" rows="3" cols="50" onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)"></html:textarea>
						<br/>                                        
					</td>
					<td>                                   
						<html:radio onclick="setContentType()" property="contentType" value="<%=AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[0]%>"/><%=AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[1]%>&nbsp;
						<html:radio onclick="setContentType()" property="contentType" value="<%=AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[0]%>"/> <%=AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[1]%>&nbsp;
					</td>
				</tr>
				
				<tr>
					<td><strong>WAP Version:</strong></td>
				</tr>
				<tr>
					<td>
						<html:radio onclick="setContentType()" property="wapVersion" value="<%=AimsConstants.WAP_APP_VERSION_WAP_1_0[0]%>"/><%=AimsConstants.WAP_APP_VERSION_WAP_1_0[1]%>&nbsp;
						<html:radio property="wapVersion" value="<%=AimsConstants.WAP_APP_VERSION_WAP_2_0[0]%>"/> <%=AimsConstants.WAP_APP_VERSION_WAP_2_0[1]%>&nbsp;                                                                                                                                                                    
					</td>
				</tr>
              </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <%-- start content filter --%>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.app.contentFilter"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
				<tr>
            		<th width="100%">
						<html:checkbox property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>" onclick="javascript:doEnableDisable(this);//">
							<strong>&nbsp;<bean:message	key="ManageApplicationForm.wap.appNetworkUsage"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>
						</html:checkbox>
					</th>
          		</tr>
				<tr>
					<td width="100%">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
							<tr>
								<td width="100%">
									<div id="divApplicationURLLbl">
										<strong><bean:message	key="ManageApplicationForm.applicationURLs"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
									</div>
								</td>
							</tr>
						</table>
					</td>

				</tr>
				<tr>
					<td width="100%">
						<div id="divApplicationURLs">
							<table id="tblApplicationURLs" width="100%" border="0" cellspacing="5" cellpadding="0" class="GridGradient">
								<tbody>
								<logic:equal name="WapApplicationUpdateForm" property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">
									<logic:notEmpty name="WapApplicationUpdateForm" property="applicationURLs">
									<logic:iterate id="applicationURL" name="WapApplicationUpdateForm" property="applicationURLs">
										<tr id="<%=("row"+applicationURLIndex)%>" valign="top">

                                            <td vAlign="top">
												<input class="inputField" type="text" name="applicationURLs" size="60" maxlength="200" value="<bean:write name='applicationURL'/>" style="margin-bottom:0px; width:375px;" >&nbsp;<a href="javascript:removeApplicationURLRow('<%=("row"+(applicationURLIndex))%>', false);"><bean:message key='images.delete.icon'/></a><%applicationURLIndex++;%>
											</td>
										</tr>
									</logic:iterate>
									</logic:notEmpty>
								</logic:equal>
								</tbody>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td width="100%">
						<div id="divAddRow">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
								<tr>
									<td align="left" width="315px" align="left">&nbsp;<span id="divExpandCollapse"></span></td>
									<%-- show export urls button, only if privilege is set, and current screen is not create or clone --%>
									<td align="left">
										<div class="redBtn" style="float:left; margin-left:10px; margin-top:3px" id="AddUrl" title="Add URL">
											<div><div><div onClick="javascript:addApplicationURLRow('tblApplicationURLs', 'applicationURLs', '', true, true);//">Add URL</div></div></div>
										</div>
									</td>
									<%if (AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.EXPORT_CONTENT_FILTER_URLS, AimsSecurityManager.UPDATE)){%>
                                        <logic:notEqual name="WapApplicationUpdateForm" property="task" value="create">
                                            <logic:notEqual name="WapApplicationUpdateForm" property="task" value="clone">
                                                <td align="left" style="padding-left: 0px">
                                                    <div class="redBtn" style="float:left; margin-top:3px" id="exportUrl" title="Export URLs">
                                                        <div><div><a href="/aims/applicationUrlsHelper.do?task=exportApplicationUrl&appsId=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank" title="Export URLs">Export URLs</a></div></div>
                                                    </div>
                                                </td>
                                            </logic:notEqual>
                                        </logic:notEqual>
                                    <%}%>
                                </tr>
							</table>
						</div>
					</td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <%-- end content filter --%>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Application URLs</H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
                <tr>
					<th width="50%">
                        <strong>WAP Demo/Test URL&nbsp;<span class="requiredText">*</span>:</strong>&nbsp;
                        <a onClick="openZonHelpWindow(event, helpWAPDemoURL); return false;" href="#">[?]</a>
                    </th>
					<th width="50%">&nbsp;</th>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="demoUrl" size="40" maxlength="200" /></td>
					<td>&nbsp;<html:hidden property="testUrl"/></td>
				</tr>
				<tr>
					<td width="50%"><strong>Production URL&nbsp;<span class="requiredText">*</span>:</strong>&nbsp;
                        <a onClick="openZonHelpWindow(event, helpProductionURL); return false;" href="#">[?]</a>                        
                    </td>
					<td width="50%">
                        <strong>Website URL (For Reference):</strong>&nbsp;
                        <a onClick="openZonHelpWindow(event, helpWebsiteURL); return false;" href="#">[?]</a>
                    </td>
				</tr>
				<tr>
					<td><html:text styleClass="inputField" property="productionUrl" size="40" maxlength="200" /></td>
					<td><html:text styleClass="inputField" property="websiteUrl" size="40" maxlength="200" /></td>
				</tr>
              </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
		  <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.app.classification"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
                <tr>
					<th width="50%"><strong><bean:message   key="ManageApplicationForm.appCategory"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
					<th width="50%"><strong><bean:message   key="ManageApplicationForm.appSubCategory"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
				</tr>
				<tr>
					<td>
						<html:select styleClass="selectField" property="categoryId1"   size="1" onchange="changeSubCategories();">
							<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
							<html:optionsCollection property="allCategories" label="categoryName"    value="categoryId"/>
						</html:select>
					</td>
					<td>
						<html:select styleClass="selectField" property="subCategoryId1" size="1">
							<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>                                         
						</html:select>
					</td>
				</tr>
              </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>		  
		  <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.prrelease"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
				<tr >
					<th>
						<strong><bean:message key="ManageApplicationForm.prrelease"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
					</th>                                            
				</tr>
				<tr>
					<td>
						<html:radio property="ifPrRelease" value="Y"/><bean:message key="ManageApplicationForm.radio.label.accept"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
						<html:radio property="ifPrRelease" value="N"/><bean:message key="ManageApplicationForm.radio.label.reject"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>						
					</td>
				</tr>
			</table></td>
          </tr>
          <tr>
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td>
				  	<%@ include  file="include/wapAppEditButtons.jsp" %>
                        
					<script language="javascript">
							changeSubCategories();
							trackCountForTextAreas();               
							<%if ((isVerizonUser) || (isEqualOrAboveSubmittedDCR)){%>
								disable1();                                    
								<%if (isEqualOrAboveSubmittedDCR){%>
									disable2();
								<% } %>
							<% } %>  
							<%if ((statusPendingDcr) && (isRolledBackToPendingDCR)){%>
								disable3();
							<% } %>
								
					</script>
					
					<%if ((isVerizonUser) || (isEqualOrAboveSubmittedDCR)){%>
						<html:hidden property="categoryId1" />
						<html:hidden property="subCategoryId1" />
						<html:hidden property="descContentOffering" />
						<html:hidden property="demoUrl" />
						<html:hidden property="testUrl" />
						<html:hidden property="productionUrl" />
						<html:hidden property="websiteUrl" />
						<html:hidden property="ifPrRelease" />
						<%if (isEqualOrAboveSubmittedDCR){%>
							<html:hidden property="title" />
							<html:hidden property="version" />
							<html:hidden property="longProductName" />
							<html:hidden property="vendorProductCode"  />
							<html:hidden property="shortDesc" />
							<html:hidden property="longDesc" />
							<html:hidden property="contentType" />
							<html:hidden property="wapVersion" />
						<% } %>
					<% } %>
					
					<%if ((statusPendingDcr) && (isRolledBackToPendingDCR)){%>
						<html:hidden property="vendorProductCode"  />                        
					<% } %> 
				  </td>
                </tr>
              </table></td>
          </tr>
        </table>
      </html:form>
    </div>
  </div>
</div>
<script	language="javascript">
    enableDisableNetworkUsage(document.forms[0].networkUsage);
    onCollapseText = '[Expand]';
    onExpandText = '[Collapse]';
    collapseURLTable();
    lastRowId = <%=applicationURLIndex%>;
</script>
