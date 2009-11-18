<%@ page import="com.netpace.aims.util.AimsConstants"%>
<%@ page import="com.netpace.aims.util.StringFuncs"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@ include  file="include/vzAppZoneVariables.jsp" %>
<script	language="javascript">
    <% 	int	index	=	0;	%>
    var	supported	=	(window.Option)	?	1	:	0;

    var	subCategoriesArray = new Array();

    function AimsAppSubCategory() {
		this.subCategoryId = "";
		this.subCategoryName = "";
		this.aimsAppCategoryId = "";
	}//end AimsAppSubCategory

    <logic:iterate id="formSubCategories"	name="VZAppZoneApplicationUpdateForm" property="allSubCategories" scope="request">
		aimsAppSubCategory = new AimsAppSubCategory();
		aimsAppSubCategory.subCategoryId = "<bean:write	name="formSubCategories" property="subCategoryId"	/>";
		aimsAppSubCategory.subCategoryName = "<bean:write	name="formSubCategories" property="subCategoryName"	/>";
		aimsAppSubCategory.aimsAppCategoryId = "<bean:write	name="formSubCategories" property="aimsAppCategoryId"	/>";
		subCategoriesArray[<%=index%>] = aimsAppSubCategory;
		<%index++;%>
	</logic:iterate>

    function changeSubCategories() {
        if	(!supported)
        {
            alert("Feature	not	supported");
        }
        var options = document.forms[0].subCategoryId1.options;


        if(options && options.length) {

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
                    if (subCategoriesArray[j].subCategoryId=="<bean:write	name="VZAppZoneApplicationUpdateForm" property="subCategoryId1" scope="request"/>")
                        m=k;
                    k++;
                }
            }
            options[m].selected = true;
        }//end if Options.length
    }//end changeSubCategories

    function truncateLocalTextAreas() {
        if (typeof document.forms[0].appShortDesc != "undefined") {
            if (typeof document.forms[0].appShortDesc.value != "undefined") {
                TruncateTextToMaxChars(document.forms[0].appShortDesc, 200);
                TrackCountToMaxChars(document.forms[0].appShortDesc,'textCountShortDesc',200);

            }
        }

        if (typeof document.forms[0].appLongDesc != "undefined") {
            if (typeof document.forms[0].appLongDesc.value != "undefined") {
                TruncateTextToMaxChars(document.forms[0].appLongDesc, 500);
                TrackCountToMaxChars(document.forms[0].appLongDesc,'textCountLongDesc',500);
            }
        }
    }
</script>
<%@ include  file="include/vzAppZoneAppJScript.jsp" %>
<%@ include  file="appTabMessageHeader.jsp" %>

<div id="contentBox" style="float:left" onmousemove="truncateLocalTextAreas();">
  <div class="homeColumnTab">
    <%@ include  file="include/vzAppZoneTabs.jsp" %>
    <div class="contentbox">
      <html:form action="/vzAppZoneApplicationUpdate.do" enctype="multipart/form-data">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <%-- Application Details --%>		
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
                  <th width="50%"><strong>Application Name&nbsp;<span class="requiredText">*</span>:</strong></th>
                  <th width="50%"><strong>Application Version&nbsp;<span class="requiredText">*</span>:</strong></th>
                </tr>
                <tr>
                  <td><html:text styleClass="inputField" property="appTitle" size="40" maxlength="50" /></td>
                  <td><html:text styleClass="inputField" property="appVersion"  size="35" maxlength="10"/></td>
                </tr>
                <%-- commented, catalog name and product code, no need to show
                    <tr>
                      <td><strong>Application Catalog Name&nbsp;<span class="requiredText">*</span>:</strong></td>
                      <td><strong>Application Product Code&nbsp;<span class="requiredText">*</span>:</strong></td>
                    </tr>
                    <tr>
                      <td><html:text styleClass="inputField" property="appCatalogName" size="40" maxlength="29" /> (Max: 29 chars)</td>
                      <td><html:text styleClass="inputField" property="appProductCode"  size="35" maxlength="20"     /></td>
                    </tr>
                --%>
                <tr>
                  <td><strong><bean:message key="ManageApplicationForm.appShortDesc"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>: (Max: 200 chars)</strong></td>
                  <td><strong><bean:message key="ManageApplicationForm.appLongDesc"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>: (Max: 500 chars)</strong></td>
                </tr>
                <tr>
                  <td>
					<html:textarea styleClass="textareaField" property="appShortDesc" rows="4"   cols="50" onkeyup="TrackCountToMaxChars(this,'textCountShortDesc',200)" onkeypress="TruncateTextToMaxChars(this,200)"></html:textarea>
					<br/><bean:message key="ManageApplicationForm.textarea.char.remaining"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
					<input type="text" name="textCountShortDesc" size="3" value="200" disabled="true" />				  
				  </td>
                  <td>
					<html:textarea styleClass="textareaField" property="appLongDesc" rows="4"    cols="50" onkeyup="TrackCountToMaxChars(this,'textCountLongDesc',500)" onkeypress="TruncateTextToMaxChars(this,500)" ></html:textarea>
					<br/><bean:message key="ManageApplicationForm.textarea.char.remaining"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
					<input type="text" name="textCountLongDesc" size="3" value="500" disabled="true" />				  
				  </td>
                </tr>
                <tr style="padding-top:5px">
                  <td><strong>Go Live Date&nbsp;<span class="requiredText">*</span>:</strong></td>
                  <td><strong>Expiration Date:</strong></td>
                </tr>
                <%-- disable date picker if application is locked--%>
                <%
                    String onClkLivePicker = isLocked?"":"toggleDatePicker('daysOfMonth1','VZAppZoneApplicationUpdateForm.goLiveDate')";
                    String onClkExpPicker = isLocked?"":"toggleDatePicker('daysOfMonth2','VZAppZoneApplicationUpdateForm.expirationDate')";
                %>
                <tr >
                  <td valign="top">
                      <html:text styleClass="inputField" property="goLiveDate" size="15" maxlength="10"/>
                      <img name="daysOfMonth1Pos" onclick="<%=onClkLivePicker%>" id="daysOfMonth1Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth1"/>
                  </td>
                  <td valign="top">
                      <html:text styleClass="inputField" property="expirationDate" size="15" maxlength="10"/>
                      <img name="daysOfMonth2Pos" onclick="<%=onClkExpPicker%>" id="daysOfMonth2Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth2"/>
                  </td>
                </tr>
                <tr>
                  <td><strong>Content Rating&nbsp;<span class="requiredText">*</span>:</strong></td>
                  <td><strong>&nbsp;</strong></td>
                </tr>
                <tr>
                  <td>
                    <html:select styleClass="selectField" property="contentRating" size="1">
                        <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                        <html:optionsCollection property="allContentRatings" label="typeValue" value="typeId"/>
                    </html:select>
                  </td>
                  <td>&nbsp;</td>
                </tr>
              </table></td>
          </tr>
		  <%-- End Application Details --%>
          <tr>
            <td>&nbsp;</td>
          </tr>		  
		  <%-- Application Classification --%>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.app.classification"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
                <tr >
                  <th><strong><bean:message   key="ManageApplicationForm.appCategory"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong> </th>
                  <th><strong><bean:message   key="ManageApplicationForm.appSubCategory"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong></th>
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
		  <%-- End Application Classification--%>
		  <tr>
            <td>&nbsp;</td>
          </tr>
		  <%-- Marketing Release --%>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.prrelease"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th><strong><bean:message key="ManageApplicationForm.prrelease"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
				</tr>
				<tr>
					<td>
                        <%  if(statusSaved) {   %>
                            <html:radio property="ifPrRelease" value="Y"/><bean:message key="ManageApplicationForm.radio.label.accept"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
						    <html:radio property="ifPrRelease" value="N"/><bean:message key="ManageApplicationForm.radio.label.reject"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        <%  }
                            else {
                        %>
                            <html:radio property="ifPrRelease" disabled="true" value="Y"/><bean:message key="ManageApplicationForm.radio.label.accept"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
						    <html:radio property="ifPrRelease" disabled="true" value="N"/><bean:message key="ManageApplicationForm.radio.label.reject"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            <html:hidden property="ifPrRelease"/>
                        <%  }   %>
					</td>
				</tr>						
              </table></td>
          </tr>
		  <%-- End Application Classification--%>
          <tr>
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td><%@ include  file="include/vzAppZoneEditButtons.jsp" %></td>
                </tr>
                <tr>
                  <td><%@ include  file="appTabMessageFooter.jsp" %></td>
                </tr>
              </table></td>
          </tr>
        </table>
        <script	language="javascript">
            changeSubCategories();
            TrackCountToMaxChars(document.forms[0].appShortDesc,'textCountShortDesc',200);
            TrackCountToMaxChars(document.forms[0].appLongDesc,'textCountLongDesc',500);
            <%-- if application is locked, disable all fields--%>
            <%  if(isLocked) {  %>
                    function lockApplicationPage1() {
                        var frm = document.forms[0];
                        frm.appTitle.disabled = true;
                        frm.appVersion.disabled = true;
                        <%-- commented, catalog name and product code, no need to show
                            frm.appCatalogName.disabled = true;
                            frm.appProductCode.disabled = true;
                        --%>                            
                        frm.appShortDesc.disabled = true;
                        frm.appLongDesc.disabled = true;
                        frm.contentRating.disabled = true;
                        frm.goLiveDate.disabled = true;
                        frm.expirationDate.disabled = true;
                        frm.categoryId1.disabled = true;
                        frm.subCategoryId1.disabled = true;
                        frm.ifPrRelease.disabled = true;
                    }//enf lockApplicationPage1
                    lockApplicationPage1();
            <%  }//end isLocked   %>
        </script>
        <%@ include  file="include/vzAppZoneHidden.jsp" %>
      </html:form>
    </div>
    <%-- end contentbox --%>
  </div>
</div>
