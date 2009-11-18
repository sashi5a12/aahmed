<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<jsp:useBean id="VcastApplicationUpdateForm" class="com.netpace.aims.controller.application.VcastApplicationUpdateForm" scope="request" />
<%VcastApplicationUpdateForm.setCurrentPage("page1");%>
<%@ include  file="include/vcastAppVariables.jsp" %>

<script language="javascript">

    var supported   =   (window.Option) ?   1   :   0;

    function truncateLocalTextAreas()
    {
        if (typeof document.forms[0].shortDesc != "undefined")
            if (typeof document.forms[0].shortDesc.value != "undefined") 
                TruncateTextWithCount(document.forms[0].shortDesc,'textCountShortDesc',200);
                
        if (typeof document.forms[0].longDesc != "undefined")
            if (typeof document.forms[0].longDesc.value != "undefined") 
                TruncateTextWithCount(document.forms[0].longDesc,'textCountLongDesc',500);
    }

    function trackCountForTextAreas()
    {
        TrackCount(document.forms[0].shortDesc,'textCountShortDesc',200);
        TrackCount(document.forms[0].longDesc,'textCountLongDesc',500);
    }

    function disable1()
    {
        document.forms[0].title.disabled = true;
        document.forms[0].shortDesc.disabled = true;
        document.forms[0].longDesc.disabled = true;
        document.forms[0].categoryId.disabled = true;
        document.forms[0].subCategoryId.disabled = true;
    }
    
    <%@ include  file="include/vcastAppJScript.jsp" %>
    
</script>

<%@ include  file="/common/error.jsp" %>
<div id="contentBox" style="float:left" onmousemove="truncateLocalTextAreas();">
  <div class="homeColumnTab">
    <%@ include  file="include/vcastAppTabs.jsp" %>
    <div class="contentbox">
      <html:form action="/vcastApplicationUpdate.do"    enctype="multipart/form-data">
        <%@ include  file="include/vcastAppHidden.jsp" %>        
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Clip Details</H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
		  <tr>
		  	<td>
				<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th width="50%"><strong>Clip Title&nbsp;<span class="requiredText">*</span>:</strong></th>
						<th width="50%"><strong>Language&nbsp;<span class="requiredText">*</span>:</strong></th>
					</tr>
					<tr>
						<td><html:text styleClass="inputField"  property="title" size="40" maxlength="100" /></td>
						<td>
							<html:select styleClass="selectField" property="language"   size="1">
								<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
								<html:optionsCollection property="allLanguages" label="languageName"    value="languageId"/>
							</html:select>
						</td>
					</tr>
											
					<tr>
						<td width="50%"><strong>Clip Short Description&nbsp;<span class="requiredText">*</span>: (Max: 200 chars)</strong></td>
						<td width="50%"><strong>Clip Long Description&nbsp;<span class="requiredText">*</span>: (Max: 500 chars)</strong></td>
					</tr>
					<tr>
						<td>
							<html:textarea styleClass="textareaField"  property="shortDesc" rows="3"   cols="50" onkeyup="TrackCount(this,'textCountShortDesc',200)" onkeypress="LimitText(this,200)"></html:textarea>
							<br/>
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td style="vertical-align:top;padding:0">
                                        <bean:message key="ManageApplicationForm.textarea.char.remaining"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                    </td>
                                    <td>
                                        <input type="text" name="textCountShortDesc" size="3" value="200" disabled="true" />
                                    </td>
                                </tr>
                            </table>


						</td>
						<td>
							<html:textarea styleClass="textareaField"  property="longDesc" rows="3"    cols="50" onkeyup="TrackCount(this,'textCountLongDesc',500)" onkeypress="LimitText(this,500)" ></html:textarea>
							<br/>
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td style="vertical-align:top;padding:0">
                                        <bean:message key="ManageApplicationForm.textarea.char.remaining"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                    </td>
                                    <td>
                                        <input type="text" name="textCountLongDesc" size="3" value="500" disabled="true" />
                                    </td>
                                </tr>
                            </table>
						</td>
					</tr>
											
					<tr style="padding-top:5px">
						<td width="50%"><strong>Frequency of Clip Updates&nbsp;<span class="requiredText">*</span>:</strong></td>
						<td width="50%">&nbsp;</td>
					</tr>
					<tr>
						<td>
							<html:select styleClass="selectField" property="updateFrequency"   size="1">
								<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
								<html:optionsCollection property="allFrequencies" label="frequencyName"    value="frequencyId"/>
							</html:select>                                        
						</td>
						<td>&nbsp;</td>
					</tr>					
		  		</table>
			</td>
		  </tr>
		  <tr><td>&nbsp;</td></tr>
		  <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Clip Classification&nbsp;<span class="requiredText">*</span></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
		  <tr>
		  	<td>
				<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th width="50%"><strong>Clip Category:</strong></th>
						<th width="50%"></th>
					</tr>
					<tr>
						<td>
							<html:select styleClass="selectField" property="subCategoryId"   size="1">
								<html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
								<html:optionsCollection property="allSubCategories" label="subCategoryName"    value="subCategoryId"/>
							</html:select>
						</td>
						<td>&nbsp;</td>
					</tr>
		  		</table>
			</td>
		  </tr>
		  <tr><td>&nbsp;</td></tr>		  
          <tr>
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td>
					<%@ include  file="include/vcastAppEditButtons.jsp" %>
											
					<script language="javascript">
							trackCountForTextAreas();               
					</script>
				  </td>
                </tr>
              </table></td>
          </tr>
        </table>
      </html:form>
    </div>
  </div>
</div>
