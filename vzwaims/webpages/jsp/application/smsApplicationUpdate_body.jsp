<%@ page language="java"
         import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<jsp:useBean id="task" class="java.lang.String" scope="request"/>
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm"
             scope="request"/>

<%ApplicationUpdateForm.setCurrentPage("page1");%>


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

<logic:iterate id="formSubCategories"	name="SmsApplicationUpdateForm"	property="allSubCategories"	scope="request">
aimsAppSubCategory = new AimsAppSubCategory();
aimsAppSubCategory.subCategoryId = "<bean:write	name="formSubCategories" property="subCategoryId"	/>";
aimsAppSubCategory.subCategoryName = "<bean:write	name="formSubCategories" property="subCategoryName"	/>";
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
            if (subCategoriesArray[j].subCategoryId == "<bean:write	name="SmsApplicationUpdateForm"	property="aimsAppSubCategoryId"	scope="request"/>")
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

function trackCountForTextAreas()
{
    TrackCount(document.forms[0].shortDesc, 'textCountShortDesc', 200);
    TrackCount(document.forms[0].longDesc, 'textCountLongDesc', 500);
}

function disableClassification()
{
    alert(document.forms[0].campaignClassification.value);
    if (document.forms[0].campaignClassification.value = "N")
    {
        document.forms[0].wholesalePrice.disabled = true;
        document.forms[0].retailPrice.disabled = true;
        alert("all Disabled");
    }
    else
    {
        document.forms[0].wholesalePrice.disabled = false;
        document.forms[0].retailPrice.disabled = false;
        alert("all Enabled");
    }
}

<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
function disableForVzw()
{
    document.forms[0].campaignStartDate.disabled = true;
    document.forms[0].campaignEndDate.disabled = true;
    document.forms[0].avgMsgsPerSec.disabled = true;
    document.forms[0].peakMsgsPerSec.disabled = true;
    document.forms[0].wholesalePrice.disabled = true;
    document.forms[0].retailPrice.disabled = true;
    document.forms[0].aimsAppCategoryId.disabled = true;
    document.forms[0].aimsAppSubCategoryId.disabled = true;

    document.forms[0].daysOfMonthPos.disabled = true;
    document.forms[0].daysOfMonth2Pos.disabled = true;

    for (var i = 0; i < document.forms[0].elements.length; i++) {
        if (document.forms[0].elements[i].name == "campaignClassification") {
            document.forms[0].elements[i].disabled = true;
        }
    }

    for (var i = 0; i < document.forms[0].elements.length; i++) {
        if (document.forms[0].elements[i].name == "ifPrRelease") {
            document.forms[0].elements[i].disabled = true;
        }
    }
}
<% } else {}%>

<%@ include  file="include/appJScript.jsp" %>

</script>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left" onmousemove="truncateLocalTextAreas();">
<div class="homeColumnTab">
<%@ include file="include/appTabs.jsp" %>
<div class="contentbox">
<html:form action="/smsApplicationUpdate.do" enctype="multipart/form-data">
<%@ include file="include/appHidden.jsp" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="SmsApplicationForm.table.head.campaign.details"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th width="50%">
                    <strong><bean:message key="SmsApplicationForm.campTitle"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:text styleClass="inputField" property="title" size="40" maxlength="200"/>
                </th>
                <th width="50%">
                    <strong><bean:message key="SmsApplicationForm.contentProvider"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:text styleClass="inputField" property="contentProvider" size="40" maxlength="100"/>
                </th>
            </tr>
            <tr>
                <td width="50%">
                    <strong><bean:message key="ManageApplicationForm.appShortDesc"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:textarea styleClass="textareaField" property="shortDesc" rows="3" cols="50"
                                   onkeyup="TrackCount(this,'textCountShortDesc',200)"
                                   onkeypress="LimitText(this,200)"></html:textarea>
                    <br/>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td style="vertical-align:top;padding:0">
                                <bean:message key="ManageApplicationForm.textarea.char.remaining"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </td>
                            <td>
                                <input type="text" name="textCountShortDesc" size="3" value="200" disabled="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
                <td width="50%">
                    <strong><bean:message key="ManageApplicationForm.appLongDesc"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:textarea styleClass="textareaField" property="longDesc" rows="3" cols="50"
                                   onkeyup="TrackCount(this,'textCountLongDesc',500)"
                                   onkeypress="LimitText(this,500)"></html:textarea>
                    <br/>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td style="vertical-align:top;padding:0">
                                <bean:message key="ManageApplicationForm.textarea.char.remaining"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </td>
                            <td>
                                <input type="text" name="textCountLongDesc" size="3" value="500" disabled="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td width="50%">
                    <strong><bean:message key="SmsApplicationForm.campaignStartDate"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:text styleClass="inputField" property="campaignStartDate" size="15"/><img
                        name="daysOfMonthPos"
                        onclick="toggleDatePicker('daysOfMonth','SmsApplicationUpdateForm.campaignStartDate')"
                        id="daysOfMonthPos" <bean:message key="images.calendar.button.lite"/> />

                    <div style="position:absolute;" id="daysOfMonth"/>
                </td>
                <td width="50%">
                    <strong><bean:message key="SmsApplicationForm.campaignEndDate"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:text styleClass="inputField" property="campaignEndDate" size="15"/><img name="daysOfMonth2Pos"
                                                                                                  onclick="toggleDatePicker('daysOfMonth2','SmsApplicationUpdateForm.campaignEndDate')"
                                                                                                  id="daysOfMonth2Pos"
                        <bean:message key="images.calendar.button.lite"/> />

                    <div style="position:absolute;" id="daysOfMonth2"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="SmsApplicationForm.table.head.msg.traffic"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th width="50%">
                    <strong><bean:message key="SmsApplicationForm.avgMsgsPerSec"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:text styleClass="inputField" property="avgMsgsPerSec" size="25"/>
                </th>
                <th width="50%">
                    <strong><bean:message key="SmsApplicationForm.peakMsgsPerSec"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:text styleClass="inputField" property="peakMsgsPerSec" size="25"/>
                </th>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="SmsApplicationForm.table.head.campaign.class.price"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th width="50%">
                    <strong><bean:message key="SmsApplicationForm.campaignClassification"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
							<span style="font-weight:normal; font-variant:normal">
							<html:radio property="campaignClassification" value="P"/><bean:message
                                    key="ManageApplicationForm.radio.label.premium"
                                    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
							<html:radio property="campaignClassification" value="N"/><bean:message
                                    key="ManageApplicationForm.radio.label.nonpremium"
                                    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
							</span>
                </th>
                <th width="50%">&nbsp;</th>
            </tr>
            <tr>
                <td width="50%">
                    <strong><bean:message key="SmsApplicationForm.wholesalePrice"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:text styleClass="inputField" property="wholesalePrice" size="25"/>
                </td>
                <td width="50%">
                    <strong><bean:message key="SmsApplicationForm.campType"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:select styleClass="selectField" property="aimsAppCategoryId" size="1"
                                 onchange="changeSubCategories();">
                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"
                                                             bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                        <html:optionsCollection property="allCategories" label="categoryName" value="categoryId"/>
                    </html:select>
                </td>
            </tr>
            <tr>
                <td width="50%">
                    <strong><bean:message key="SmsApplicationForm.retailPrice"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:text styleClass="inputField" property="retailPrice" size="25"/>
                </td>
                <td width="50%">
                    <strong><bean:message key="SmsApplicationForm.campSubType"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:select styleClass="selectField" property="aimsAppSubCategoryId" size="1">
                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"
                                                             bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                    </html:select>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="ApplicationForm.table.head.prrelease"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th>
                    <strong><bean:message key="ManageApplicationForm.prrelease"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                </th>
            </tr>
            <tr>
                <td>
                    <html:radio property="ifPrRelease" value="Y"/><bean:message
                        key="ManageApplicationForm.radio.label.accept"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                    <html:radio property="ifPrRelease" value="N"/><bean:message
                        key="ManageApplicationForm.radio.label.reject"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td>
                    <%@ include file="include/appEditButtons.jsp" %>

                    <script language="javascript">
                        changeSubCategories();
                        trackCountForTextAreas();
                        <%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
                        disableForVzw();
                        <% } else {}%>
                    </script>

                    <%if (((com.netpace.aims.model.core.AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE)) {%>
                    <html:hidden property="campaignStartDate"/>
                    <html:hidden property="campaignEndDate"/>
                    <html:hidden property="avgMsgsPerSec"/>
                    <html:hidden property="peakMsgsPerSec"/>
                    <html:hidden property="campaignClassification"/>
                    <html:hidden property="aimsAppCategoryId"/>
                    <html:hidden property="aimsAppSubCategoryId"/>
                    <html:hidden property="wholesalePrice"/>
                    <html:hidden property="retailPrice"/>
                    <html:hidden property="ifPrRelease"/>
                    <% } else {
                    }%>

                </td>
            </tr>
        </table>
    </td>
</tr>
</table>
</html:form>
</div>
</div>
</div>