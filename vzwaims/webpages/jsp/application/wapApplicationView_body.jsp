<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<jsp:useBean id="WapApplicationUpdateForm" class="com.netpace.aims.controller.application.WapApplicationUpdateForm" scope="request" />
<%WapApplicationUpdateForm.setCurrentPage("page1");%>
<%@ include  file="include/wapAppVariables.jsp" %>

<script	language="javascript">
	<%@ include  file="include/wapAppJScript.jsp" %>
</script>
<script language="javascript">
    function gotowapAppImageUpload(appsId) {
        var popupURL = "/aims/wapAppImageUpload.do?task=edit&appsId="+appsId;
        var childWindow = window.open(popupURL,"wndWapAppImageUpload","menubar=no,location=no,resizable=no,toolbar=no,width=550,height=385,scrollbars=yes");
        if (childWindow.opener == null) childWindow.opener = self;
        document.cookie = 'scrollTop=' + document.body.scrollTop;
        childWindow.focus();
    }
    
    function reScroll() 
    {
        var scrollTop = parseInt(document.cookie.replace(/scrollTop=([0-9]+)/, '$1'));
        if (!isNaN(scrollTop))
        {
            document.body.scrollTop = scrollTop;
            document.cookie = 'scrollTop=0';
        }
    }
        
</script>
<script	language="javascript">
    function showAddRemoveURLWindow(appsId) {
        var popupURL = "/aims/addRemoveApplicationUrls.do?task=edit&appsId="+appsId;
        var childWindow = window.open(popupURL,"addRemoveURLWnd","menubar=no,location=no,resizable=no,toolbar=no,width=525,height=400,scrollbars=yes");
        if (childWindow.opener == null) childWindow.opener = self;
        childWindow.focus();
    }
</script>
<%@ include  file="include/applicationUrlJScript.jsp" %>
<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/wapAppViewTabs.jsp" %>
    <html:form action="/wapApplicationUpdate.do"    enctype="multipart/form-data">
      <div class="contentbox">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.app.details"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                <tr>
                    <th width="50%">
                        <strong>Short Product Name:</strong>
                        <br/>
                        <span style="font-weight:normal"><bean:write name="WapApplicationUpdateForm" property="title"/>&nbsp;</span>
                    </th>
                    <th width="50%">
                        <strong>Product Version:</strong>
                        <br/>
                        <span style="font-weight:normal"><bean:write name="WapApplicationUpdateForm" property="version"/>&nbsp;</span>
                    </th>
                </tr>
                <tr>
                    <td width="50%"><strong>Long Product Name:</strong></td>
                    <td width="50%"><strong>Vendor Product Code:</strong></td>
                </tr>
                <tr>
                    <td class="viewText"><bean:write name="WapApplicationUpdateForm" property="longProductName"/>&nbsp;</td>
                    <td class="viewText"><bean:write name="WapApplicationUpdateForm" property="vendorProductCode"/>&nbsp;</td>
                </tr>
                <tr>
                    <td width="50%">
                        <strong><bean:message key="ManageApplicationForm.appShortDesc"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    </td>
                    <td width="50%">
                        <strong><bean:message key="ManageApplicationForm.appLongDesc"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    </td>
                </tr>
                <tr>
                    <td class="viewText">
                        <html:textarea styleClass="textareaField" property="shortDesc" rows="3"   cols="50" readonly="true"></html:textarea>
                    </td>
                    <td class="viewText">
                        <html:textarea styleClass="textareaField"  property="longDesc" rows="3"    cols="50" readonly="true"></html:textarea>
                    </td>
                </tr>
                <tr>
                    <td width="50%"><strong>Description of Content Offering:</strong></td>
                    <td width="50%"><strong>Content Type:</strong></td>
                </tr>
                <tr>
                    <td class="viewText" rowspan="3">
                        <html:textarea styleClass="textareaField"  property="descContentOffering" rows="3"   cols="50" readonly="true"></html:textarea>
                    </td>
                    <td class="viewText">
                        <logic:equal name="WapApplicationUpdateForm" property="contentType" value="<%=AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[0]%>">
                            <%=AimsConstants.WAP_APP_CONTENT_TYPE_STANDARD[1]%>
                        </logic:equal>
                        <logic:equal name="WapApplicationUpdateForm" property="contentType" value="<%=AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[0]%>">
                            <%=AimsConstants.WAP_APP_CONTENT_TYPE_PREMIUM[1]%>
                        </logic:equal>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td width="50%"><strong>WAP Version:</strong></td>
                </tr>
                <tr>
                    <td class="viewText">
                        <logic:equal name="WapApplicationUpdateForm" property="wapVersion" value="<%=AimsConstants.WAP_APP_VERSION_WAP_1_0[0]%>">
                            <%=AimsConstants.WAP_APP_VERSION_WAP_1_0[1]%>
                        </logic:equal>
                        <logic:equal name="WapApplicationUpdateForm" property="wapVersion" value="<%=AimsConstants.WAP_APP_VERSION_WAP_2_0[0]%>">
                            <%=AimsConstants.WAP_APP_VERSION_WAP_2_0[1]%>
                        </logic:equal>
                        &nbsp;
                    </td>
                </tr>
              </table></td>
          </tr>
          <tr><td>&nbsp;</td></tr>

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
                <th width="60%">
					<strong><bean:message	key="ManageApplicationForm.wap.appNetworkUsage"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
					<span style="font-weight:normal; font-variant:normal">
					<logic:equal name="WapApplicationUpdateForm" property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">                        
                        Yes
					</logic:equal>
					<logic:notEqual name="WapApplicationUpdateForm" property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">                        
                        No
					</logic:notEqual>
					</span>
				</th>
                <th colspan="2">
                    <logic:equal name="WapApplicationUpdateForm" property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">
                        <div class="redBtn" style="float:left;" id="AllEnable" title="Disable Network Usage">
                            <div><div>
                                    <a href="/aims/applicationUrlsHelper.do?task=enableDisableNetworkUsage&networkUsage=<%=AimsConstants.AIMS_APP_DISABLE_NETWORK_USAGE%>&appsId=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"
                                       onclick="javascript:return confirm('You are about to disable Network Usage, All your URLs will be Deleted! Are you sure you want to proceed?');"
                                       class="a" title="Disable Network Usage">Disable</a>
                            </div></div>
                        </div>
					</logic:equal>
					<logic:notEqual name="WapApplicationUpdateForm" property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">
                        <div class="redBtn" style="float:left;" id="AllDisable" title="Enable Network Usage">
                            <div><div>
                                <a href="/aims/applicationUrlsHelper.do?task=enableDisableNetworkUsage&networkUsage=<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>&appsId=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a" title="Enable Network Usage">Enable</a>                                
                            </div></div>
                        </div>
					</logic:notEqual>
                </th>
              </tr>
			  <tr>
				<td colspan="3">
					<logic:notEmpty name="WapApplicationUpdateForm" property="applicationURLs">
						<strong><bean:message	key="ManageApplicationForm.applicationURLs"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
					</logic:notEmpty>&nbsp;
				</td>
			 </tr>
			 <%int applicationURLIndex = 0;%>
			 <logic:equal name="WapApplicationUpdateForm" property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">
                <logic:notEmpty name="WapApplicationUpdateForm" property="applicationURLs">
                    <tr>
						<td colspan="3">
							 <table id="tblApplicationURLs" width="100%" border="0" cellspacing="5" cellpadding="0">
								<tbody>
									<logic:iterate id="applicationURL" name="WapApplicationUpdateForm" property="applicationURLs">
										<tr>
											<td class="text" valign="top" colspan="2">
												<bean:write name="applicationURL"/><%applicationURLIndex++;%>
											</td>
										</tr>
									</logic:iterate>
								</tbody>
							 </table>
						</td>
					</tr>
				</logic:notEmpty>
					<tr>
						<td><span id="divExpandCollapse"></span></td>
						<td width="14%">
                            <div class="redBtn" id="addRemoveUrls" title="Add/Remove">
                                <div><div>
                                    <span style=" cursor:pointer;">
                                        <a onclick="javascript:showAddRemoveURLWindow(<bean:write	name="WapApplicationUpdateForm" property="appsId" />);" class="a" title="Add/Remove Application URLs">Add/Remove</a>
                                    </span>
                                </div></div>
                            </div>
						</td>
                        <td>
                            <logic:present name="exportUrlAllowed">
								<logic:equal name="exportUrlAllowed" value="true">
									<div class="redBtn" style="padding-top:0px; float:left;" id="exportUrls" title="Export URLs">
										<div><div>
											<a href="/aims/applicationUrlsHelper.do?task=exportApplicationUrl&appsId=<bean:write	name="WapApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank" title="Export URLs">Export URLs</a>
										</div></div>
									</div>
								</logic:equal>
							</logic:present>
                            &nbsp;&nbsp;
                        </td>
                    </tr>
			</logic:equal>
            </table></td>
          </tr>
          

          <tr><td>&nbsp;</td></tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Application URLs</H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
              <tr>
                  <th width="50%">
                      <strong>WAP Demo/Test URL:</strong>
                      <br/>
                      <span style="font-weight:normal"><bean:write name="WapApplicationUpdateForm" property="demoUrl"/>&nbsp;</span>
                  </th>
                  <th width="50%">&nbsp;</th>                  
              </tr>
              <tr>
                  <td width="50%"><strong>Production URL:</strong></td>
                  <td width="50%"><strong>Website URL (For Reference):</strong></td>
              </tr>
              <tr>
                  <td class="viewText">
                        <bean:write name="WapApplicationUpdateForm" property="productionUrl"/>&nbsp;
                  </td>
                  <td class="viewText">
                        <bean:write name="WapApplicationUpdateForm" property="websiteUrl"/>&nbsp;
                  </td>
              </tr>
            </table></td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.app.classification"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                <tr>
                    <th width="50%">
                        <strong><bean:message   key="ManageApplicationForm.appCategory"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                        <br/>
                        <span style="font-weight:normal">
                        <logic:notEmpty name="WapApplicationUpdateForm" property="categoryId1">
                            <logic:iterate id="formCategories" name="WapApplicationUpdateForm" property="allCategories" scope="request">
                                <logic:equal name="formCategories" property="categoryId" value="<%=WapApplicationUpdateForm.getCategoryId1().toString()%>">
                                    <bean:write name="formCategories" property="categoryName" />
                                </logic:equal>
                            </logic:iterate>
                        </logic:notEmpty>&nbsp;</span>
                    </th>
                    <th width="50%">
                        <strong><bean:message   key="ManageApplicationForm.appSubCategory"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                        <br/>
                        <span style="font-weight:normal">
                            <logic:notEmpty name="WapApplicationUpdateForm" property="subCategoryId1">
                                <logic:iterate id="formSubCategories" name="WapApplicationUpdateForm" property="allSubCategories" scope="request">
                                    <logic:equal name="formSubCategories" property="subCategoryId" value="<%=WapApplicationUpdateForm.getSubCategoryId1().toString()%>">
                                        <bean:write name="formSubCategories" property="subCategoryName" />
                                    </logic:equal>
                                </logic:iterate>
                            </logic:notEmpty>&nbsp;</span>
                      </th>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td width="100%"><table width="100%" cellspacing="0" cellpadding="1">
                <tr>
                  <td width="50%" valign="top"><table width="100%"  cellspacing="0" cellpadding="1">
                      <tr>
                        <td><table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">                            
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message key="ApplicationForm.table.head.app.files"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong>Product Logo For PC:</strong>
                                    <br/>
                                    <logic:notEmpty name="WapApplicationUpdateForm" property="productLogoFileName">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapProductLogo&app_id=<bean:write name="WapApplicationUpdateForm" property="appsId"   />" class="a" target="_blank">
                                            <bean:write    name="WapApplicationUpdateForm"    property="productLogoFileName"/>
                                        </a>
                                    </logic:notEmpty>&nbsp;
                                </th>
                            </tr>
                            <tr>
                                <td><strong>Product Icon For Device:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="WapApplicationUpdateForm" property="productIconFileName">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapProductIcon&app_id=<bean:write name="WapApplicationUpdateForm"    property="appsId"   />" class="a"   target="_blank">
                                            <bean:write    name="WapApplicationUpdateForm"    property="productIconFileName"/>
                                        </a>
                                    </logic:notEmpty>&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Content Landing Page Sample Screen Shot (JPEG/GIF):</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="WapApplicationUpdateForm" property="screenJpegFileName">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_id=<bean:write name="WapApplicationUpdateForm"    property="appsId"   />" class="a"   target="_blank">
                                            <bean:write    name="WapApplicationUpdateForm"    property="screenJpegFileName"/>
                                        </a>
                                    </logic:notEmpty>&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <strong><bean:message  key="ManageApplicationForm.appFAQ"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                </td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="WapApplicationUpdateForm" property="faqDocFileName">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FaqDoc&app_id=<bean:write name="WapApplicationUpdateForm"    property="appsId"   />" class="a"   target="_blank">
                                            <bean:write    name="WapApplicationUpdateForm"    property="faqDocFileName"/>
                                        </a>
                                    </logic:notEmpty>&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <strong><bean:message  key="ManageApplicationForm.appUserGuide"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                </td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="WapApplicationUpdateForm" property="userGuideFileName">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=UserGuide&app_id=<bean:write  name="WapApplicationUpdateForm"    property="appsId"   />" class="a"   target="_blank">
                                            <bean:write    name="WapApplicationUpdateForm"    property="userGuideFileName"/>
                                        </a>
                                    </logic:notEmpty>&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Presentation:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="WapApplicationUpdateForm" property="presentationFileName">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapPresentation&app_id=<bean:write    name="WapApplicationUpdateForm"    property="appsId"   />" class="a"   target="_blank">
                                            <bean:write    name="WapApplicationUpdateForm"    property="presentationFileName"/>
                                        </a>
                                    </logic:notEmpty>&nbsp;
                                </td>
                            </tr>

                            <%-- 3 FTP images --%>
                            <tr>
                                <td>
                                    <strong><bean:message key="WapApplicationForm.label.appMediumLargeImage" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <logic:present name="manualImageUploadAllowed">
                                        <span style="cursor:pointer" >
                                            <a onclick="javascript:gotowapAppImageUpload(<bean:write name="WapApplicationUpdateForm" property="appsId"/>);" class="a" title="Upload WAP Icons">
                                                <bean:message key="images.upload.icon" />
                                            </a>
                                        </span>
                                    </logic:present>
                                </td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="WapApplicationUpdateForm" property="appMediumLargeImageFileName">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapAppMediumLargeImage&app_id=<bean:write    name="WapApplicationUpdateForm"    property="appsId"   />" class="a"   target="_blank">
                                            <bean:write    name="WapApplicationUpdateForm"    property="appMediumLargeImageFileName"/>
                                        </a>
                                    </logic:notEmpty>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <strong><bean:message key="WapApplicationForm.label.appQVGAPotraitImage" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <logic:present name="manualImageUploadAllowed">
                                        <span style="cursor:pointer" >
                                            <a onclick="javascript:gotowapAppImageUpload(<bean:write name="WapApplicationUpdateForm" property="appsId"/>);" class="a" title="Upload WAP Icons">
                                                <bean:message key="images.upload.icon" />
                                            </a>
                                        </span>
                                    </logic:present>

                                </td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="WapApplicationUpdateForm" property="appQVGAPotraitImageFileName">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapAppQVGAPotraitImage&app_id=<bean:write    name="WapApplicationUpdateForm"    property="appsId"   />" class="a"   target="_blank">
                                            <bean:write    name="WapApplicationUpdateForm"    property="appQVGAPotraitImageFileName"/>
                                        </a>
                                    </logic:notEmpty>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <strong><bean:message key="WapApplicationForm.label.appQVGALandscapeImage" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <logic:present name="manualImageUploadAllowed">
                                        <span style="cursor:pointer" >
                                            <a onclick="javascript:gotowapAppImageUpload(<bean:write name="WapApplicationUpdateForm" property="appsId"/>);" class="a" title="Upload WAP Icons">
                                                <bean:message key="images.upload.icon" />
                                            </a>
                                        </span>
                                    </logic:present>
                                </td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="WapApplicationUpdateForm" property="appQVGALandscapeImageFileName">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=WapQVGALandscapeImage&app_id=<bean:write    name="WapApplicationUpdateForm"    property="appsId"   />" class="a"   target="_blank">
                                            <bean:write    name="WapApplicationUpdateForm"    property="appQVGALandscapeImageFileName"/>
                                        </a>
                                    </logic:notEmpty>
                                    &nbsp;
                                </td>
                            </tr>
                            <%-- end 3 FTP images --%>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message key="ManageApplicationForm.appTechContact"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <logic:notEmpty name="WapApplicationUpdateForm" property="aimsContactId" scope="request">
                                <logic:iterate id="formContacts" name="WapApplicationUpdateForm" property="allContacts" scope="request">
                                    <logic:equal name="formContacts" property="contactId" value="<%=WapApplicationUpdateForm.getAimsContactId().toString()%>">
                                        <tr>
                                            <th>
                                                <strong><bean:message key="ManageApplicationForm.appContactFirstName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                                <br/>
                                                <span style="font-weight:normal;"><bean:write name="formContacts" property="firstName" /></span>
                                            </th>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message key="ManageApplicationForm.appContactLastName"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="formContacts" property="lastName" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message key="ManageApplicationForm.appContactTitle" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="formContacts" property="title" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message key="ManageApplicationForm.appContactEmail" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="formContacts" property="emailAddress" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message key="ManageApplicationForm.appContactTelephone" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="formContacts" property="phone" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message key="ManageApplicationForm.appContactMobile"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="formContacts" property="mobile" /></td>
                                        </tr>
                                    </logic:equal>
                                </logic:iterate>
                            </logic:notEmpty>
                            <tr><td>&nbsp;</td></tr>
                          </table></td>
                      </tr>
                    </table></td>
                  <td width="50%" valign="top"><table width="100%"  cellspacing="0" cellpadding="1">
                      <tr>
                        <td><table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message key="ApplicationForm.table.head.prrelease"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong><bean:message key="ManageApplicationForm.prrelease"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                    <span style="font-weight:normal">
                                         <logic:equal name="WapApplicationUpdateForm" property="ifPrRelease" value="Y">
                                            <bean:message key="ManageApplicationForm.radio.label.yes"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                        </logic:equal>
                                        <logic:equal name="WapApplicationUpdateForm" property="ifPrRelease" value="N">
                                            <bean:message key="ManageApplicationForm.radio.label.no"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                        </logic:equal>
                                    </span>
                                </th>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message   key="WapApplicationForm.table.head.launch.info" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong>Product Live Date:</strong>
                                    <br/>
                                    <span style="font-weight:normal"><bean:write name="WapApplicationUpdateForm" property="launchDate"/>&nbsp;</span>
                                </th>
                            </tr>
                            <tr>
                                <td><strong>Testing Effective Date:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText"><bean:write name="WapApplicationUpdateForm" property="testEffectiveDate"/>&nbsp;</td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1>Information For Premium Content Only</H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong>License Type:</strong>
                                    <br/>
                                    <span style="font-weight:normal">
                                        <logic:notEmpty name="WapApplicationUpdateForm" property="listSelectedLicenseTypes" scope="request">
                                        <logic:iterate id="licenses" name="WapApplicationUpdateForm" property="allLicenseTypes">
                                            <%for (int i=0; i<WapApplicationUpdateForm.getListSelectedLicenseTypes().length; i++) {%>
                                                <logic:equal name="licenses" property="licenseTypeId" value="<%=(WapApplicationUpdateForm.getListSelectedLicenseTypes())[i].toString()%>">
                                                    <bean:write name="licenses" property="licenseTypeName" /><br/>
                                                </logic:equal>
                                            <% } %>
                                        </logic:iterate>
                                        </logic:notEmpty>&nbsp;
                                    </span>
                                </th>
                            </tr>
                            <tr>
                                <td><strong>VZW Suggested Retail Price:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <bean:write name="WapApplicationUpdateForm" property="vzwRetailPrice"/>&nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Vendor Product Display:</strong></td>
                            </tr>
                            <tr>
                                <td>(Information entered here will be displayed on the subscriber’s bill)</td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <html:textarea property="vendorProductDisplay" rows="3" cols="50" readonly="true"></html:textarea>
                                </td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>                            
                          </table></td>
                      </tr>
                    </table></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td width="100%">
                <%@ include  file="include/wapAppViewButtons.jsp" %>
            </td>
          </tr>
        </table>
      </div>
    </html:form>
  </div>
</div>
<script	language="javascript">
    onCollapseText = '[Expand]';
    onExpandText = '[Collapse]';
    collapseURLTable();
</script>
<script language="javascript" defer="defer">

    setTimeout("reScroll()",100);
</script>