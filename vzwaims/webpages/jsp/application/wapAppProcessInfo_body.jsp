<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<jsp:useBean id="WapApplicationUpdateForm" class="com.netpace.aims.controller.application.WapApplicationUpdateForm" scope="request" />
<%WapApplicationUpdateForm.setCurrentPage("page3");%>
<%@ include  file="include/wapAppVariables.jsp" %>

<script	language="javascript">

var subCategoriesArray = new Array();

function AimsAppSubCategory()
{
    this.subCategoryId = "";
    this.subCategoryName = "";
    this.aimsAppCategoryId = "";
}

<%
int index   =   0;
%>

<logic:iterate id="formSubCategories"   name="WapApplicationUpdateForm" property="allSubCategories" scope="request">
    aimsAppSubCategory = new AimsAppSubCategory();
    aimsAppSubCategory.subCategoryId = "<bean:write name="formSubCategories" property="subCategoryId"   />";
    aimsAppSubCategory.subCategoryName = "<bean:write   name="formSubCategories" property="subCategoryName" />";
    aimsAppSubCategory.aimsAppCategoryId = "<bean:write name="formSubCategories" property="aimsAppCategoryId"   />";
    subCategoriesArray[<%=index%>] = aimsAppSubCategory;
    <%index++;%>
</logic:iterate>

var supported   =   (window.Option) ?   1   :   0;

    function changeSubCategories(elemNumber) 
    {
        if  (!supported) 
        {
            alert("Feature  not supported");
        }
        
        var categoryElem = document.forms[document.forms[0].name].elements['categoryId' + elemNumber];
        var subCategoryElem = document.forms[document.forms[0].name].elements['subCategoryId' + elemNumber];
                  
        var options = subCategoryElem.options;
        for (var i = options.length - 1; i > 0; i--)    
        {
            options[i]  =   null;
        }
    
        options[0]= new Option("<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>", "0");
        var k=1;
        var m = 0;
    
        for (var j = 0; j < subCategoriesArray.length; j++) 
        {
            if  (subCategoriesArray[j].aimsAppCategoryId == categoryElem.options[categoryElem.selectedIndex].value)
            {
                options[k] = new Option(subCategoriesArray[j].subCategoryName, subCategoriesArray[j].subCategoryId);
                if (elemNumber == 1)
                    if (subCategoriesArray[j].subCategoryId=="<bean:write   name="WapApplicationUpdateForm" property="subCategoryId1" scope="request"/>")
                        m=k;
                if (elemNumber == 2)
                    if (subCategoriesArray[j].subCategoryId=="<bean:write   name="WapApplicationUpdateForm" property="subCategoryId2" scope="request"/>")
                        m=k;
                if (elemNumber == 3)
                    if (subCategoriesArray[j].subCategoryId=="<bean:write   name="WapApplicationUpdateForm" property="subCategoryId3" scope="request"/>")
                        m=k;                    
                k++;
            }
        }
        options[m].selected = true;    
    }

    <%@ include  file="include/wapAppJScript.jsp" %>
    
	function callToggleDatePicker(eltName,formElt)
	{
		toggleDatePicker(eltName, document.forms[0].name + '.' + formElt);
	}
    
    function populateCurrentDateForTest(elemNumber)
    {
        var testedDateElem = document.forms[document.forms[0].name].elements['testedDate[' + elemNumber + ']'];
        testedDateElem.value = new Date().getMonth()+1 + '/' + new Date().getDate() + '/' +  new Date().getFullYear();        
    }
            
	
	function truncateLocalTextAreas()
	{
        if (typeof document.forms[0].initialApprovalNotes != "undefined")
            if (typeof document.forms[0].initialApprovalNotes.value != "undefined") 
                TruncateText(document.forms[0].initialApprovalNotes, 500);        

        if (typeof document.forms[0].businessApprovalNotes != "undefined") 
            if (typeof document.forms[0].businessApprovalNotes.value != "undefined")
                TruncateText(document.forms[0].businessApprovalNotes, 500);        
        
        if (typeof document.forms[0].vendorProductDisplay != "undefined")
            if (typeof document.forms[0].vendorProductDisplay.value != "undefined") 
                TruncateText(document.forms[0].vendorProductDisplay, 30);        

        if (typeof document.forms[0].pendingDcrNotes != "undefined")
            if (typeof document.forms[0].pendingDcrNotes.value != "undefined")
                TruncateText(document.forms[0].pendingDcrNotes, 500);        
        
        for (var i=0; i<document.forms[0].elements.length; i++)
		{
		    if (document.forms[0].elements[i].name.indexOf("testComments") != -1)
                if (document.forms[0].elements[i].type.indexOf("textarea") != -1)
                    TruncateText(document.forms[0].elements[i], 200);
        }
	}
    
    function disableForStatus()
    {
        
        <%if (isEqualOrAboveInitialApprovalDenied){%>
            for (var i=0; i<document.forms[0].elements.length; i++) {
                if (document.forms[0].elements[i].name == "initialApprovalAction")
                    document.forms[0].elements[i].disabled = true;
            }
        <% } else {}%>
        
        <%if (isEqualOrAboveBusinessApprovalGrantedDenied){%>
            for (var i=0; i<document.forms[0].elements.length; i++) {
                if (document.forms[0].elements[i].name == "businessApprovalAction")
                    document.forms[0].elements[i].disabled = true;
            }
        <% } else {}%>
            
        <%if (isEqualOrAboveSubmittedDCR){%>
            document.forms[0].categoryId1.disabled = true;
            document.forms[0].subCategoryId1.disabled = true;
            document.forms[0].linkOrder1.disabled = true;
            document.forms[0].categoryId2.disabled = true;
            document.forms[0].subCategoryId2.disabled = true;
            document.forms[0].linkOrder2.disabled = true;
            document.forms[0].categoryId3.disabled = true;
            document.forms[0].subCategoryId3.disabled = true;
            document.forms[0].linkOrder3.disabled = true;
            document.forms[0].initialApprovalNotes.disabled = true;
            document.forms[0].businessApprovalNotes.disabled = true;
            <%-- Test url is replaced by demo(Demo / Test URL) 
                document.forms[0].testUrl.disabled = true;
            --%>
            document.forms[0].demoUrl.disabled = true;
            document.forms[0].productionUrl.disabled = true;
            document.forms[0].vendorSplitPercentage.disabled = true;
            document.forms[0].taxCategoryCodeId.disabled = true;
            document.forms[0].pendingDcrNotes.disabled = true;
            document.forms[0].vzwRetailPrice.disabled    =   true;
            document.forms[0].vendorProductDisplay.disabled = true;
            document.forms[0].pageViewRate.disabled = true;
            
            for (var i=0; i<document.forms[0].elements.length; i++) {
                if (document.forms[0].elements[i].name == "contentType")
                    document.forms[0].elements[i].disabled = true;
                if (document.forms[0].elements[i].name == "moveToPendingDcr")
                    document.forms[0].elements[i].disabled = true;
                if (document.forms[0].elements[i].name == "listSelectedLicenseTypes")
                    document.forms[0].elements[i].disabled = true;
            }
        <% } else {}%>
    }

    function disableForContentType()
    {
        for (var i = 0; i < document.forms[0].elements.length; i++)
        {
            if (document.forms[0].elements[i].name == "contentType")
            {
                if (document.forms[0].elements[i].value == '<%=AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[0].toString()%>')
                {
                    try
                    {
                        if (document.forms[0].elements[i].checked)
                        {
                            document.forms[0].vendorSplitPercentage.disabled = true;
                            document.forms[0].taxCategoryCodeId.disabled = true;
                            document.forms[0].vzwRetailPrice.disabled    =   true;
                            document.forms[0].vendorProductDisplay.disabled = true;
                            for (var j=0; j<document.forms[0].elements.length; j++) 
                            {
                                if (document.forms[0].elements[j].name == "listSelectedLicenseTypes")
                                    document.forms[0].elements[j].disabled = true;
                            }
                        }
                        else
                        {
                            document.forms[0].vendorSplitPercentage.disabled = false;
                            document.forms[0].taxCategoryCodeId.disabled = false;
                            document.forms[0].vzwRetailPrice.disabled    =   false;
                            document.forms[0].vendorProductDisplay.disabled = false;
                            for (var j=0; j<document.forms[0].elements.length; j++) 
                            {
                                if (document.forms[0].elements[j].name == "listSelectedLicenseTypes")
                                {
                                    if (document.forms[0].elements[j].value == "1")
                                        document.forms[0].elements[j].disabled = false;
                                    else //Disabling temporarily License Types: Event & Fixed Price
                                        document.forms[0].elements[j].disabled = true;
                                }
                            }
                        }
                    }
                    catch(er)
                    {}
                }
            }
        }
    }
        <%
            if (ftpWapImagesAllowed) {%>
                    function gotoFTPTransfer()
                    {                           
                        var gotoURL = '/aims/wapFTPTransfer.do?appsId=<bean:write name="WapApplicationUpdateForm" property="appsId"/>';
                        var message = 'FTP Images in progress, please wait...';
                        var transferURL = '/aims/common/redirectPage.jsp?gotoURL='+gotoURL+'&message='+message;
                        var childWindow = window.open(transferURL,"wndFTPTransfer","menubar=no,location=no,resizable=no,toolbar=no,width=400,height=125,scrollbars=no");
                        if (childWindow.opener == null) childWindow.opener = self;
                        childWindow.focus();
                    }
        <%} %>
	
</script>

<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left" onmousemove="truncateLocalTextAreas();">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/wapAppTabs.jsp" %>
    <logic:notEmpty name="WapApplicationUpdateForm" property="clonedFromTitle">
        <table style="float:left;" width="100%" border="0" cellspacing="0" cellpading="1" >
            <tr><td><table width="100%">
                        <tr>
                            <td width="50%"><strong>Clone Reference:&nbsp;</strong>
                                <a href="/aims/wapApplicationSetup.do?task=view&appsId=<bean:write name="WapApplicationUpdateForm" property="clonedFromId" />" class="a" target="_blank">
                                    <bean:write name="WapApplicationUpdateForm" property="clonedFromTitle"/>
                                </a>
                            </td>
                            <td width="50%">
                                <a href="/aims/wapVersionDifference.do?wapAppsId=<%=WapApplicationUpdateForm.getAppsId()%>&clonedFromId=<bean:write name="WapApplicationUpdateForm" property="clonedFromId" />" class="a" target="_blank">
                                    (Click Here to See The Clone Difference)
                                </a>
                            </td>
                        </tr>
                    </table></td></tr>
            <tr><td width="100%">&nbsp;</td></tr>
        </table>
    </logic:notEmpty>
    <html:form action="/wapApplicationUpdate.do"	enctype="multipart/form-data">
      <div class="contentbox">
        <%@ include  file="include/wapAppHidden.jsp" %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <% if ( statusSubmitted || statusInitialApproval || statusInitialDenied ||  statusBusinessApprovalGranted ||
                statusBusinessApprovalDenied || statusPendingArm || statusPendingDcr || statusSubmittedDcr ||
                statusTestingPassed || statusTestingFailed || statusPublicationReady || statusCompletedInProduction || statusSunset) { %>
            <%if (hasAccessInitialApproval) {%>
              <tr>
                <td><div class="lBox">
                    <DIV class="headLeftCurveblk"></DIV>
                    <H1>Initial Approval / Denial</H1>
                    <DIV class="headRightCurveblk"></DIV>
                  </div></td>
              </tr>
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="1" class="GridGradient">
                    <tr>
                        <th width="33%">
                            <strong>Application Category:</strong>
                        </th>
                        <th width="33%">
                            <strong>Application Subcategory:</strong>
                        </th>
                        <th width="34%">
                            <strong>Link Order:</strong>
                        </th>
                    </tr>
                    <tr>
                        <td width="33%">
                            <html:select property="categoryId1"   size="1" onchange="changeSubCategories(1);">
                                <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                <html:optionsCollection property="allCategories" label="categoryName"    value="categoryId"/>
                            </html:select>
                        </td>
                        <td width="33%">
                            <html:select property="subCategoryId1" size="1">
                                <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                            </html:select>
                        </td>
                        <td width="34%">
                            <html:text property="linkOrder1" size="30" maxlength="150" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:select property="categoryId2"   size="1" onchange="changeSubCategories(2);">
                                <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                <html:optionsCollection property="allCategories" label="categoryName"    value="categoryId"/>
                            </html:select>
                        </td>
                        <td>
                            <html:select property="subCategoryId2" size="1">
                                <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                            </html:select>
                        </td>
                        <td>
                            <html:text  property="linkOrder2" size="30" maxlength="150" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:select  property="categoryId3"   size="1" onchange="changeSubCategories(3);">
                                <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                <html:optionsCollection property="allCategories" label="categoryName"    value="categoryId"/>
                            </html:select>
                        </td>
                        <td>
                            <html:select property="subCategoryId3" size="1">
                                <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                            </html:select>
                        </td>
                        <td>
                            <html:text  property="linkOrder3" size="30" maxlength="150" />
                        </td>
                    </tr>
                    <tr>
                        <td width="33%"><strong>Content Type:</strong></td>
                        <td width="33%"><strong>Notes:</strong></td>
                        <td width="34%"><strong>Action:</strong></td>
                    </tr>
                    <tr>
                        <td>
                            <html:radio property="contentType" onclick="disableForContentType()" value="<%=AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[0]%>"/><%=AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[1]%>&nbsp;
                            <html:radio property="contentType" onclick="disableForContentType()" value="<%=AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[0]%>"/> <%=AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[1]%>&nbsp;
                        </td>
                        <td rowspan="3">
                            <html:textarea property="initialApprovalNotes" rows="3"   cols="40" onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)"></html:textarea>
                        </td>
                        <td rowspan="3" style="vertical-align:top;">
                            <html:radio property="initialApprovalAction" value="<%=AimsConstants.WAP_APP_RADIO_INITIAL_APPROVE[0]%>"/><%=AimsConstants.WAP_APP_RADIO_INITIAL_APPROVE[1]%>&nbsp;
                            <html:radio property="initialApprovalAction" value="<%=AimsConstants.WAP_APP_RADIO_INITIAL_DENY[0]%>"/><%=AimsConstants.WAP_APP_RADIO_INITIAL_DENY[1]%>&nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td width="33%"><strong>VZW Projected Live Date:</strong></td>
                    </tr>
                    <tr>
                        <td>
                            <%if (isEqualOrAboveSubmittedDCR){%>
                                <bean:write name="WapApplicationUpdateForm" property="vzwLiveDate"/>&nbsp;
                            <% } else { %>
                                <html:text property="vzwLiveDate" size="15"/><img name="daysOfMonth99Pos" onclick="toggleDatePicker('daysOfMonth99','WapApplicationUpdateForm.vzwLiveDate')" id="daysOfMonth99Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth99"/>
                            <% } %>
                        </td>
                    </tr>
                  </table></td>
              </tr>
              <tr><td>&nbsp;</td></tr>
            <% } else {}%>
        <% } else {}%>
        <% if ( statusInitialApproval ||  statusBusinessApprovalGranted ||  statusBusinessApprovalDenied ||
                statusPendingArm || statusPendingDcr || statusSubmittedDcr || statusTestingPassed ||
                statusTestingFailed || statusPublicationReady || statusCompletedInProduction || statusSunset) { %>
            <%if (hasAccessInitialBusinessApproval) {%>
              <tr>
                <td><div class="lBox">
                    <DIV class="headLeftCurveblk"></DIV>
                    <H1>Initial Business Approval / Denial</H1>
                    <DIV class="headRightCurveblk"></DIV>
                  </div></td>
              </tr>
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                    <tr>
                        <th><strong>Action:</strong></th>
                        <th><strong>Notes:</strong></th>
                    </tr>
                    <tr>
                        <td style="vertical-align:top">
                            <html:radio property="businessApprovalAction" value="<%=AimsConstants.WAP_APP_RADIO_BUSINESS_OK[0]%>"/><%=AimsConstants.WAP_APP_RADIO_BUSINESS_OK[1]%>&nbsp;
                            <html:radio property="businessApprovalAction" value="<%=AimsConstants.WAP_APP_RADIO_BUSINESS_ARM[0]%>"/><%=AimsConstants.WAP_APP_RADIO_BUSINESS_ARM[1]%>&nbsp;
                            <html:radio property="businessApprovalAction" value="<%=AimsConstants.WAP_APP_RADIO_BUSINESS_DENY[0]%>"/><%=AimsConstants.WAP_APP_RADIO_BUSINESS_DENY[1]%>
                        </td>
                        <td>
                            <html:textarea  property="businessApprovalNotes" rows="3"   cols="50" onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)"></html:textarea>
                        </td>
                    </tr>
                  </table></td>
              </tr>
              <tr><td>&nbsp;</td></tr>
            <% } else {}%>
        <% } else {}%>
        <% if ( statusBusinessApprovalGranted || statusPendingArm || statusPendingDcr || statusSubmittedDcr ||
                statusTestingPassed || statusTestingFailed || statusPublicationReady || statusCompletedInProduction || statusSunset) { %>
            <%if (hasAccessMoveToPendingDcr) {%>
              <tr>
                <td><div class="lBox">
                    <DIV class="headLeftCurveblk"></DIV>
                    <H1>Move To Pending DCR</H1>
                    <DIV class="headRightCurveblk"></DIV>
                  </div></td>
              </tr>
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                    <tr>
                        <%--  Test url is replaced by demo(Demo / Test URL)
                            <th width="50%">
                                <strong>Test URL:</strong>
                                <br/>
                                <html:text styleClass="inputField"  property="testUrl" size="40" maxlength="200" />
                            </th>
                        --%>
                        <th width="50%">
                            <strong>WAP Demo/Test URL:</strong>
                            <br/>
                            <html:text styleClass="inputField"  property="demoUrl" size="40" maxlength="200" />
                        </th>
                        <th width="50%">
                            <strong>Production URL:</strong>
                            <br/>
                            <html:text styleClass="inputField" property="productionUrl" size="40" maxlength="200" />
                        </th>
                    </tr>
                    <tr>
                        <td width="50%"><strong>Vendor Split Percentage (%):</strong></td>
                        <td width="50%"><strong>Action:</strong></td>
                    </tr>
                    <tr>
                        <td><html:text styleClass="inputField" property="vendorSplitPercentage" size="30" maxlength="150" /></td>
                        <td>
                            <html:checkbox property="moveToPendingDcr" value="<%=AimsConstants.WAP_APP_CHECKBOX_PENDING_DCR[0]%>">
                                <%=AimsConstants.WAP_APP_CHECKBOX_PENDING_DCR[1]%>
                            </html:checkbox>
                        </td>
                    </tr>
                  </table></td>
              </tr>
              <tr><td>&nbsp;</td></tr>
            <% } else {}%>
        <% } else {}%>
        <% if ( statusPendingDcr || statusSubmittedDcr || statusTestingPassed || statusTestingFailed ||
                statusPublicationReady || statusCompletedInProduction || statusSunset) { %>
            <%if (hasAccessPendingDcr) {%>
              <tr>
                <td><div class="lBox">
                    <DIV class="headLeftCurveblk"></DIV>
                    <H1>Pending DCR</H1>
                    <DIV class="headRightCurveblk"></DIV>
                  </div></td>
              </tr>
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                    <tr>
                        <th width="50%">
                       <strong> Vendor Product ID: </strong>
                            <br/>
                            <span style="font-weight:normal"><bean:write name="WapApplicationUpdateForm" property="vendorProductCode"/>&nbsp;</span>
                        </th>
                        <th width="50%">
                            Pending DCR Version:
                            <br/>
                            <span style="font-weight:normal"><bean:write name="WapApplicationUpdateForm" property="pendingDcrVersion"/>&nbsp;</span>
                        </th>
                    </tr>
                    <tr>
                        <td width="50%"><strong>VZW Suggested Retail Price (USD):</strong></td>
                        <td width="50%"><strong>Tax Category Code:</strong></td>
                    </tr>
                    <tr>
                        <td><html:text styleClass="inputField" property="vzwRetailPrice" size="35" maxlength="100" /></td>
                        <td>
                            <html:select styleClass="selectField" property="taxCategoryCodeId" size="1">
                                <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                <logic:iterate id="taxCategoryCodes" name="WapApplicationUpdateForm" property="allTaxCategoryCodes" type="com.netpace.aims.model.masters.AimsTaxCategoryCode">
                                    <% if (
                                            ((taxCategoryCodes.getStatus() != null) && (taxCategoryCodes.getStatus().toLowerCase().equals("active")))
                                            ||
                                            ((WapApplicationUpdateForm.getTaxCategoryCodeId() != null) && (WapApplicationUpdateForm.getTaxCategoryCodeId().toString().equals(taxCategoryCodes.getTaxCategoryCodeId().toString())))
                                          ) { %>
                                        <html:option value="<%=taxCategoryCodes.getTaxCategoryCodeId().toString()%>"><%=taxCategoryCodes.getTaxCategoryCode()%> - <%=taxCategoryCodes.getTaxCategoryCodeDesc()%></html:option>
                                    <% } %>
                                </logic:iterate>
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%"><strong>Vendor Product Display:</strong></td>
                        <td width="50%"><strong>License Type:</strong></td>
                    </tr>
                    <tr>
                        <td>
                            <html:textarea styleClass="textareaField" property="vendorProductDisplay" onkeyup="LimitText(this,30)" onkeypress="LimitText(this,30)" rows="3" cols="50"></html:textarea>
                        </td>
                        <td>
                            <logic:iterate id="licenses" name="WapApplicationUpdateForm" property="allLicenseTypes" type="com.netpace.aims.model.application.AimsWapLicenseType">
                                <html:radio property="listSelectedLicenseTypes" value="<%=licenses.getLicenseTypeId().toString()%>"/><bean:write name="licenses" property="licenseTypeName" /><br/>
                            </logic:iterate>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%"><strong>Page View Rate:</strong></td>
                        <td width="50%"><strong>Notes:</strong></td>
                    </tr>
                    <tr>
                        <td style="vertical-align:top">
                            <html:text styleClass="inputField" property="pageViewRate" size="35" maxlength="50" />
                        </td>
                        <td>
                            <html:textarea styleClass="textareaField"  property="pendingDcrNotes" rows="3"   cols="50" onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)"></html:textarea>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%"><strong>Previous DCR Version Comparisions:</strong></td>
                        <td width="50%">
                            <%if ( ftpWapImagesAllowed ) {%>
                                 <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="FTPImages" title="FTP Images">
                                    <div><div><div onClick="javascript:gotoFTPTransfer();">FTP Images</div></div></div>
                                </div>
                            <%}%>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <logic:notEmpty name="WapApplicationUpdateForm" property="wapAppVersions">
                                <% String newVer = WapApplicationUpdateForm.getPendingDcrVersion(); %>
                                <% String oldVer = ""; %>
                                <%for (int i = WapApplicationUpdateForm.getWapAppVersions().length - 1; i >= 0; i--) {%>
                                    <% oldVer = (WapApplicationUpdateForm.getWapAppVersions()[i]).toString();%>
                                    <a target="_blank" href="/aims/wapVersionDifference.do?wapAppsId=<%=WapApplicationUpdateForm.getAppsId()%>&newVersion=<%=newVer%>&oldVersion=<%=oldVer%>">Differences between Ver. <%=newVer%> and <%=oldVer%></a><br/>
                                    <% newVer = oldVer; %>
                                <% } %>
                            </logic:notEmpty>
                        </td>
                    </tr>
                  </table></td>
              </tr>
              <tr><td>&nbsp;</td></tr>
            <% } else {}%>
        <% } else {}%>
        <% if ( statusSubmittedDcr || statusTestingPassed || statusTestingFailed ||
                statusPublicationReady || statusCompletedInProduction || statusSunset) { %>
            <%if (hasAccessContentTesting) {%>        
              <tr>
                <td><div class="lBox">
                    <DIV class="headLeftCurveblk"></DIV>
                    <H1><bean:message key="ApplicationForm.table.head.testing"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                    <DIV class="headRightCurveblk"></DIV>
                  </div></td>
              </tr>
              <tr>
                <td><table width="100%" cellspacing="0" cellpadding="2" class="Grid2"
                   style="border-color:black;border:solid 1px #CCCCCC;border-collapse:collapse;" border="1">
                    <tr>
                        <th><bean:message key="ApplicationForm.testingName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                        <th><bean:message key="ApplicationForm.testingDate" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                        <th><bean:message key="ApplicationForm.testingStatus" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                        <th><bean:message key="ApplicationForm.testingFile" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    </tr>
                    <logic:notEmpty name="WapApplicationUpdateForm" property="testId">
                        <logic:iterate id="phases" name="WapApplicationUpdateForm" property="testId" indexId="ctr">
                            <tr>
                                <td align="left"><b><bean:write name="WapApplicationUpdateForm" property='<%= "testName[" + ctr + "]" %>' /></b></td>
                                <td align="left">
                                    <html:text  property='<%= "testedDate[" + ctr + "]" %>' size="15" /><img onclick="callToggleDatePicker('daysOfMonth<%=ctr%>','testedDate[<%=ctr%>]')" name="daysOfMonth<%=ctr%>Pos" id="daysOfMonth<%=ctr%>Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth<%=ctr%>"/>
                                </td>
                                <td align="left">
                                    <html:radio property='<%= "testStatus[" + ctr + "]" %>' value="P" onclick='<%= "populateCurrentDateForTest('" + ctr + "')" %>'/><bean:message    key="ManageApplicationForm.radio.label.passed"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                                    <html:radio property='<%= "testStatus[" + ctr + "]" %>' value="F" onclick='<%= "populateCurrentDateForTest('" + ctr + "')" %>'/><bean:message    key="ManageApplicationForm.radio.label.failed"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                                </td>
                                <td align="left">
                                    <html:file styleClass="inputField" size="20" property='<%= "testResultFile[" + ctr + "]" %>'/><br/>
                                    <logic:notEmpty name="WapApplicationUpdateForm"    property='<%= "testResultFileName[" + ctr + "]" %>' scope="request">
                                        <logic:equal name="WapApplicationUpdateForm" property='<%= "testResultFileId[" + ctr + "]" %>' scope="request" value="0">
                                            <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=resultFile&app_id=<bean:write name="WapApplicationUpdateForm" property="appsId"/>&composite_id=<bean:write name="phases" />" class="a" target="_blank">
                                        </logic:equal>
                                        <logic:notEqual name="WapApplicationUpdateForm"    property='<%= "testResultFileId[" + ctr + "]" %>' scope="request" value="0">
                                            <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write name="WapApplicationUpdateForm" property='<%= "testResultFileId[" + ctr + "]" %>'  />" class="a" target="_blank">
                                        </logic:notEqual>
                                            <bean:write name="WapApplicationUpdateForm"    property='<%= "testResultFileName[" + ctr + "]" %>' />
                                        </a>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td align="left" style="vertical-align:top;">
                                    <logic:notEmpty name="WapApplicationUpdateForm"    property='<%= "testUpdatedBy[" + ctr + "]" %>'>
                                        Updated By: <br/>
                                        <bean:write name="WapApplicationUpdateForm" property='<%= "testUpdatedBy[" + ctr + "]" %>'/><br/>
                                        (<bean:write name="WapApplicationUpdateForm" property='<%= "testUpdatedDate[" + ctr + "]" %>'/>)
                                    </logic:notEmpty>
                                </td>
                                <td colspan="3" align="left">
                                    <html:textarea styleClass="textareaField" property='<%= "testComments[" + ctr + "]" %>' onkeyup="LimitText(this,200)" onkeypress="LimitText(this,200)" rows="4" cols="65"></html:textarea>
                                </td>
                            </tr>
                        </logic:iterate>
                    </logic:notEmpty>
                  </table></td>
              </tr>
              <tr><td>&nbsp;</td></tr>        
              <tr>
                <td><div class="lBox">
                    <DIV class="headLeftCurveblk"></DIV>
                    <H1>VZW Projected Live Date</H1>
                    <DIV class="headRightCurveblk"></DIV>
                  </div></td>
              </tr>
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                    <tr>
                        <td class="gradient">
                            <html:text property="vzwLiveDate" size="15"/><img name="daysOfMonth99Pos" onclick="toggleDatePicker('daysOfMonth99','WapApplicationUpdateForm.vzwLiveDate')" id="daysOfMonth99Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth99"/>
                        </td>
                    </tr>
                  </table></td>
              </tr>
              <tr><td>&nbsp;</td></tr>
            <% } else {}%>
        <% } else {}%>
        <% if ( statusPublicationReady || statusCompletedInProduction || statusSunset) { %>
            <%if (hasAccessContentCompletion) {%>
            <tr>
                <td><div class="lBox">
                    <DIV class="headLeftCurveblk"></DIV>
                    <H1>Completed - In Production</H1>
                    <DIV class="headRightCurveblk"></DIV>
                  </div></td>
              </tr>
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                    <tr>
                        <th>
                            <html:checkbox property="moveToCompletedInProduction" value="<%=AimsConstants.WAP_APP_CHECKBOX_COMPLETED_IN_PRODUCTION[0]%>">
                                <%=AimsConstants.WAP_APP_CHECKBOX_COMPLETED_IN_PRODUCTION[1]%>
                            </html:checkbox>
                        </th>
                    </tr>
                  </table></td>
              </tr>
              <tr><td>&nbsp;</td></tr>
            <% } else {}%>
        <% } else {}%>
        <% if ( statusCompletedInProduction || statusSunset) { %>
            <%if (hasAccessContentRemoval) {%>
              <tr>
                <td><div class="lBox">
                    <DIV class="headLeftCurveblk"></DIV>
                    <H1>Sunset</H1>
                    <DIV class="headRightCurveblk"></DIV>
                  </div></td>
              </tr>
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                    <tr>
                        <th class="text" valign="top">
                            <html:checkbox property="moveToSunset" value="<%=AimsConstants.WAP_APP_CHECKBOX_SUNSET[0]%>">
                                <%=AimsConstants.WAP_APP_CHECKBOX_SUNSET[1]%>
                            </html:checkbox>
                        </th>
                    </tr>
                  </table></td>
              </tr>
              <tr><td>&nbsp;</td></tr>
            <% } else {}%>
        <% } else {}%>
          <tr>
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <%@ include  file="include/wapAppEditButtons.jsp" %>

                <script language="javascript">
                    changeSubCategories(1);
                    changeSubCategories(2);
                    changeSubCategories(3);
                    disableForContentType();
                    disableForStatus();
                </script>

                <%if (isEqualOrAboveInitialApprovalDenied){%>
                    <html:hidden property="initialApprovalAction"  />
                <% } else {}%>

                <%if (isEqualOrAboveBusinessApprovalGrantedDenied){%>
                    <html:hidden property="businessApprovalAction"  />
                <% } else {}%>

                <%if (isEqualOrAboveSubmittedDCR){%>
                    <html:hidden property="categoryId1"  />
                    <html:hidden property="subCategoryId1"  />
                    <html:hidden property="linkOrder1"  />
                    <html:hidden property="categoryId2"  />
                    <html:hidden property="subCategoryId2"  />
                    <html:hidden property="linkOrder2"  />
                    <html:hidden property="categoryId3"  />
                    <html:hidden property="subCategoryId3"  />
                    <html:hidden property="linkOrder3"  />
                    <html:hidden property="contentType"  />
                    <html:hidden property="initialApprovalNotes"  />
                    <html:hidden property="businessApprovalNotes"  />
                    <html:hidden property="moveToPendingDcr"  />
                    <html:hidden property="demoUrl" />
                    <%-- Test url is replaced by demo(Demo / Test URL)
                        <html:hidden property="testUrl" />
                    --%>
                    <html:hidden property="productionUrl" />
                    <html:hidden property="vendorSplitPercentage"  />
                    <html:hidden property="taxCategoryCodeId"  />
                    <html:hidden property="pendingDcrNotes"  />
                    <html:hidden property="vzwRetailPrice"  />
                    <html:hidden property="vendorProductDisplay"   />
                    <html:hidden property="pageViewRate"  />
                    <logic:notEmpty name="WapApplicationUpdateForm" property="listSelectedLicenseTypes">
                        <logic:iterate id="selLicenseTypeIds" name="WapApplicationUpdateForm" property="listSelectedLicenseTypes" indexId="ctr">
                            <input type="hidden" name="listSelectedLicenseTypes" value="<bean:write name="selLicenseTypeIds"/>"/>
                        </logic:iterate>
                    </logic:notEmpty>
                <% } else {}%>

              </table></td>
          </tr>
        </table>
      </div>
    </html:form>
  </div>
</div>
