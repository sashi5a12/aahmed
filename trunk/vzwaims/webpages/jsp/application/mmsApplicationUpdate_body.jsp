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

    <logic:iterate id="formSubCategories"	name="MmsApplicationUpdateForm" property="allSubCategories" scope="request">
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
                if (subCategoriesArray[j].subCategoryId == "<bean:write	name="MmsApplicationUpdateForm" property="aimsAppSubCategoryId" scope="request"/>")
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

    <%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
    function disableForVzw()
    {
        document.forms[0].expectedMsgTraffic.disabled = true;
        document.forms[0].targetedLaunchDate.disabled = true;
        document.forms[0].aimsAppCategoryId.disabled = true;
        document.forms[0].aimsAppSubCategoryId.disabled = true;

        document.forms[0].daysOfMonthPos.disabled = true;

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
<DIV class="homeColumnTab ">
<%@ include file="include/appTabs.jsp" %>
<html:form action="/mmsApplicationUpdate.do" enctype="multipart/form-data">
<div class="contentbox">
<%@ include file="include/appHidden.jsp" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="MmsApplicationForm.table.head.campaign.details"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
            <tr>
                <th width="50%">
                    <strong><bean:message key="MmsApplicationForm.campTitle"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:text styleClass="inputField" property="title" size="40" maxlength="200"/>
                </th>
                <th width="50%">
                    <strong><bean:message key="MmsApplicationForm.contentProvider"
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
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                            </td>
                            <td><input type="text" name="textCountShortDesc" size="3" value="200" disabled="true"/>
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
                            <td><input type="text" name="textCountLongDesc" size="3" value="500" disabled="true"/>
                            </td>
                        </tr>
                    </table>
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
            <H1><bean:message key="MmsApplicationForm.table.head.campaign.classification"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="1" cellspacing="0" cellpadding="0" class="GridGradient">
            <tr>
            <tr>
                <th width="50%">
                    <strong><bean:message key="MmsApplicationForm.contentCategory"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:select styleClass="selectField" property="aimsAppCategoryId" size="1"
                                 onchange="changeSubCategories();">
                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"
                                                             bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                        <html:optionsCollection property="allCategories" label="categoryName" value="categoryId"/>
                    </html:select>
                </th>
                <th width="50%">
                    <strong><bean:message key="MmsApplicationForm.contentSubcategory"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:select styleClass="selectField" property="aimsAppSubCategoryId" size="1">
                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"
                                                             bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                    </html:select>
                </th>
            </tr>            
        </table>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td width="100%">
        <table width="100%" cellspacing="0" cellpadding="1">
            <tr>
                <td width="50%" valign="top">
                    <table width="100%" cellspacing="0" cellpadding="1">
                        <tr>
                            <td>
                                <table width="375" height="100%" class="GridGradient" border="0" cellpadding="0"
                                       cellspacing="0">
                                    <tr>
                                        <td class="noPad">
                                            <div class="mmBox">
                                                <DIV class="headLeftCurveblk"></DIV>
                                                <H1><bean:message key="MmsApplicationForm.table.head.msg.traffic"
                                                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

                                                <DIV class="headRightCurveblk"></DIV>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>
                                            <strong><bean:message key="MmsApplicationForm.expectedMsgTraffic"
                                                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                            <br/>
                                            <html:text styleClass="inputField" property="expectedMsgTraffic" size="25"/>
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
                                <table width="375" height="100%" class="GridGradient" border="0" cellpadding="0"
                                       cellspacing="0">
                                    <tr>
                                        <td class="noPad">
                                            <div class="mmBox">
                                                <DIV class="headLeftCurveblk"></DIV>
                                                <H1><bean:message key="MmsApplicationForm.table.head.launch.info"
                                                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

                                                <DIV class="headRightCurveblk"></DIV>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="gradient">
                                            <strong><bean:message key="MmsApplicationForm.targetedLaunchDate"
                                                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                            <br/>
                                            <html:text styleClass="inputField" property="targetedLaunchDate" size="15"/><img
                                                name="daysOfMonthPos"
                                                onclick="toggleDatePicker('daysOfMonth','MmsApplicationUpdateForm.targetedLaunchDate')"
                                                id="daysOfMonthPos" <bean:message key="images.calendar.button.lite"/> />
                                            <div style="position:absolute;" id="daysOfMonth"/>
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
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
            <tr>
                <th>
                    <strong><bean:message key="ManageApplicationForm.prrelease"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                </th>
            </tr>
            <tr>
                <td style="padding-top:0px">
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
                    <html:hidden property="expectedMsgTraffic"/>
                    <html:hidden property="targetedLaunchDate"/>
                    <html:hidden property="aimsAppCategoryId"/>
                    <html:hidden property="aimsAppSubCategoryId"/>
                    <html:hidden property="ifPrRelease"/>
                    <% } else {
                    }%>
                </td>
            </tr>
        </table>
    </td>
</tr>
</table>
</div>
</html:form>
</div>
</div>
