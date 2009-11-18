<%@	page language="java" import="com.netpace.aims.bo.security.*, com.netpace.aims.bo.application.*, com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<jsp:useBean id="task" class="java.lang.String"	scope="request"/><jsp:useBean id="BrewApplicationUpdateForm" class="com.netpace.aims.controller.application.BrewApplicationUpdateForm" scope="request" /><jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" /><script	language="javascript">
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
    <%@ include  file="include/appViewTabs.jsp" %>
    <html:form action="/brewApplicationUpdate.do"	enctype="multipart/form-data">
      <div class="contentbox">
        <%@ include  file="include/appViewHidden.jsp" %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.app.details"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
					<tr>
						<th width="50%">
							<strong><bean:message key="ManageApplicationForm.appTitle"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
							<br/>
							<span style="font-weight:normal; font-variant:normal"><bean:write name="BrewApplicationUpdateForm" property="title"/></span>
						</th>
						<th width="50%">
							<strong><bean:message key="ManageApplicationForm.appVersion"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
							<br/>
							<span style="font-weight:normal; font-variant:normal"><bean:write name="BrewApplicationUpdateForm" property="version"/></span>
						</th>
					</tr>
					<tr>
						<td width="50%">
							<strong><bean:message key="ManageApplicationForm.appLanguage"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
						</td>
						<td width="50%">
							<strong><bean:message key="BrewApplicationForm.appSize"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
						</td>
					</tr>
					<tr>
						<td class="viewText">
							<logic:equal name="BrewApplicationUpdateForm" property="language" value="EN">
								<bean:message key="ManageApplicationForm.language.english"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
							</logic:equal>
							<logic:equal name="BrewApplicationUpdateForm" property="language" value="SP">
								<bean:message key="ManageApplicationForm.language.spanish"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
							</logic:equal>
						</td>
						<td class="viewText">
							 <bean:write name="BrewApplicationUpdateForm" property="appSize"/>
						</td>
					</tr>
					<tr>
						<td width="50%">
							<strong><bean:message key="ManageApplicationForm.appShortDesc"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
						</td>
						<td width="50%">
							<strong><bean:message key="ManageApplicationForm.appLongDesc"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
						</td>
					</tr>
					<tr>
						<td>
							<html:textarea styleClass="textareaField"	property="shortDesc" rows="4"	cols="50" readonly="true"/>
						</td>
						<td>
							 <html:textarea styleClass="textareaField"	property="longDesc" rows="4"	cols="50" readonly="true"/>
						</td>
					</tr>
					<tr>
						<td>
							<strong><bean:message key="ManageApplicationForm.appType"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
						</td>
                        <td>
							<strong><bean:message key="ManageApplicationForm.contentRating"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
						</td>
                    </tr>
					<tr>
						<td>
							<bean:write name="BrewApplicationUpdateForm" property="appTypeFullName"/>
						</td>
                        <td>
							<logic:notEmpty name="BrewApplicationUpdateForm" property="contentRating">
                                <logic:iterate id="contentRatings" name="BrewApplicationUpdateForm" property="allBrewContentRatings" type="com.netpace.aims.model.core.AimsTypes">
                                    <logic:equal name="contentRatings" property="typeId" value="<%=BrewApplicationUpdateForm.getContentRating().toString()%>">
                                        <bean:write name="contentRatings" property="typeValue"/>
                                    </logic:equal>
                                </logic:iterate>
						    </logic:notEmpty>
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
                <H1><bean:message key="ApplicationForm.table.head.app.contentFilter"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>          
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
              <tr>
                <th width="40%">
					<strong><bean:message	key="ManageApplicationForm.appNetworkUsage"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
					<span style="font-weight:normal; font-variant:normal">
					<logic:equal name="ApplicationUpdateForm" property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">
						Yes
					</logic:equal>
					<logic:notEqual name="ApplicationUpdateForm" property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">
						No
					</logic:notEqual>
					</span>				
				</th>
                <th colspan="2">
                    <logic:equal name="ApplicationUpdateForm" property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">
                        <div class="redBtn" style="float:left;" id="AllEnable" title="Disable Network Usage">
                            <div><div>
                                    <a href="/aims/applicationUrlsHelper.do?task=enableDisableNetworkUsage&networkUsage=<%=AimsConstants.AIMS_APP_DISABLE_NETWORK_USAGE%>&appsId=<bean:write	name="ApplicationUpdateForm"	property="appsId"	/>"
                                       onclick="javascript:return confirm('You are about to disable Network Usage, All your URLs will be Deleted! Are you sure you want to proceed?');"
                                       class="a" title="Disable Network Usage">Disable</a>
                            </div></div>
                        </div>
					</logic:equal>
					<logic:notEqual name="ApplicationUpdateForm" property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">
                        <div class="redBtn" style="float:left;" id="AllDisable" title="Enable Network Usage">
                            <div><div>
                                <a href="/aims/applicationUrlsHelper.do?task=enableDisableNetworkUsage&networkUsage=<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>&appsId=<bean:write	name="ApplicationUpdateForm"	property="appsId"	/>"	class="a" title="Enable Network Usage">Enable</a>
                            </div></div>
                        </div>
					</logic:notEqual>
                </th>
              </tr>
			  <tr>
				<td colspan="3">
					<logic:notEmpty name="ApplicationUpdateForm" property="applicationURLs">
						<strong><bean:message	key="ManageApplicationForm.applicationURLs"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
					</logic:notEmpty>&nbsp;
				</td>
			 </tr>
			 <%int applicationURLIndex = 0;%>
			 <logic:equal name="ApplicationUpdateForm" property="networkUsage" value="<%=AimsConstants.AIMS_APP_ENABLE_NETWORK_USAGE%>">
				<logic:notEmpty name="ApplicationUpdateForm" property="applicationURLs">
					<tr>
						<td colspan="3">
							 <table id="tblApplicationURLs" width="100%" border="0" cellspacing="5" cellpadding="0">
								<tbody>
									<logic:iterate id="applicationURL" name="ApplicationUpdateForm" property="applicationURLs">
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
                                        <a onclick="javascript:showAddRemoveURLWindow(<bean:write	name="ApplicationUpdateForm" property="appsId" />);" class="a" title="Add/Remove Application URLs">Add/Remove</a>
                                    </span>
                                </div></div>
                            </div>
						</td>
                        <td>
                            <logic:present name="exportUrlAllowed">
								<logic:equal name="exportUrlAllowed" value="true">
									<div class="redBtn" style="padding-top:0px; float:left;" id="exportUrls" title="Export URLs">
										<div><div>
											<a href="/aims/applicationUrlsHelper.do?task=exportApplicationUrl&appsId=<bean:write	name="ApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank" title="Export URLs">Export URLs</a>
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
          <tr>
            <td>&nbsp;</td>
          </tr>

          <logic:equal name="BrewApplicationUpdateForm" property="isLbs" value="<%=AimsConstants.BREW_APP_CHECKBOX_IS_LBS[0]%>">
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>LBS Application</H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
		  	<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                    <tr>
                        <th>
                            <strong><%=AimsConstants.BREW_APP_CHECKBOX_IS_LBS[1]%>:</strong>
                            <br/>
                            <span style="font-weight:normal; font-variant:normal">
                                <logic:equal name="BrewApplicationUpdateForm" property="isLbs" value="<%=AimsConstants.BREW_APP_CHECKBOX_IS_LBS[0]%>">
                                    Yes
                                </logic:equal>
                                <logic:notEqual name="BrewApplicationUpdateForm" property="isLbs" value="<%=AimsConstants.BREW_APP_CHECKBOX_IS_LBS[0]%>">
                                    No
                                </logic:notEqual>
                            </span>
                        </th>
                        <th>
                            <strong>Application Type:</strong>
                            <br/>
                             <span style="font-weight:normal; font-variant:normal">
                                <logic:equal name="BrewApplicationUpdateForm" property="lbsAppType" value="<%=AimsConstants.LBS_APP_TYPE_ACTIVE[0]%>">
                                    <%=AimsConstants.LBS_APP_TYPE_ACTIVE[1]%>
                                </logic:equal>
                                <logic:equal name="BrewApplicationUpdateForm" property="lbsAppType" value="<%=AimsConstants.LBS_APP_TYPE_PASSIVE[0]%>">
                                    <%=AimsConstants.LBS_APP_TYPE_PASSIVE[1]%>
                                </logic:equal>
                                <logic:equal name="BrewApplicationUpdateForm" property="lbsAppType" value="<%=AimsConstants.LBS_APP_TYPE_BOTH[0]%>">
                                    <%=AimsConstants.LBS_APP_TYPE_BOTH[1]%>
                                </logic:equal>
                            </span>
                        </th>
                    </tr>                   
                    <tr>
                        <td><strong>Client ID:</strong></td>
                        <td><strong>Geo Services Selected:</strong></td>
                    </tr>
                    <tr>
                        <td class="viewText"><bean:write name="BrewApplicationUpdateForm" property="lbsClientId"/>&nbsp;</td>
                        <td rowspan="5" >
                            <logic:notEmpty name="BrewApplicationUpdateForm" property="listSelectedGeoServices" scope="request">
                                <logic:iterate id="formGeoServices" name="BrewApplicationUpdateForm" property="allGeoServices" scope="request">
                                    <%for (int i=0; i<BrewApplicationUpdateForm.getListSelectedGeoServices().length; i++) {%>
                                        <logic:equal name="formGeoServices" property="geoServiceId" value="<%=(BrewApplicationUpdateForm.getListSelectedGeoServices())[i].toString()%>">
                                            <bean:write name="formGeoServices" property="initiatedFrom" /> - <bean:write name="formGeoServices" property="geoServiceName" /><br/>
                                        </logic:equal>
                                    <% } %>
                                </logic:iterate>
                            </logic:notEmpty>
                        </td>
                    </tr>
                    <tr>
                        <td><strong>Secret Key:</strong></td>
                    </tr>
                    <tr>
                        <td class="viewText"><bean:write name="BrewApplicationUpdateForm" property="lbsSecretKey"/>&nbsp;</td>
                    </tr>
                    <tr>
                        <td><strong>Current Status:</strong></td>
                    </tr>
                    <tr>
                        <td class="viewText"><bean:write name="BrewApplicationUpdateForm" property="lbsAutodeskPhaseName"/>&nbsp;</td>
                    </tr>
                </table>
			</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          </logic:equal>

          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.app.classification"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
              <tr>
                <th width="50%">
                    <strong><bean:message key="ManageApplicationForm.appCategory"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>
                    <br/>
                    <span style="font-weight:normal">
                    <logic:iterate id="formCategories" name="BrewApplicationUpdateForm" property="allCategories" scope="request">
                        <logic:equal name="formCategories" property="categoryId" value="<%=BrewApplicationUpdateForm.getAimsAppCategoryId().toString()%>">
                            <bean:write name="formCategories" property="categoryName" />
                        </logic:equal>
                    </logic:iterate></span>
                </th>
                <th width="50%">
                    <strong><bean:message key="ManageApplicationForm.appSubCategory"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>
                    <br/>
                    <span style="font-weight:normal">
                    <logic:iterate id="formSubCategories" name="BrewApplicationUpdateForm" property="allSubCategories" scope="request">
                        <logic:equal name="formSubCategories" property="subCategoryId" value="<%=BrewApplicationUpdateForm.getAimsAppSubCategoryId().toString()%>">
                            <bean:write name="formSubCategories" property="subCategoryName" />
                        </logic:equal>
                    </logic:iterate></span>
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
                                  <H1><bean:message key="ApplicationForm.table.head.app.files"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <%if (!(AimsSecurityManager.checkAccess(request, ManageApplicationsConstants.PRIV_BREW_LEGAL_USER, AimsSecurityManager.SELECT))) {%>                                
                                <tr>
                                    <td>
                                        <strong><bean:message key="BrewApplicationForm.appAppTitleName"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="viewText">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=AppTitleName&app_type=<bean:message key="ManageApplicationForm.brew.app.label"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                            <bean:write	name="BrewApplicationUpdateForm" property="appTitleNameFileName"/>
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <strong><bean:message	key="ManageApplicationForm.appScreenShot"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="viewText">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                            <bean:write	name="BrewApplicationUpdateForm" property="screenJpegFileName"/>
                                        </a>
                                        <logic:notEmpty name="BrewApplicationUpdateForm" property="screenJpeg2FileName">
                                            <br/>
                                            <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                                <bean:write	name="BrewApplicationUpdateForm" property="screenJpeg2FileName"/>
                                            </a>
                                        </logic:notEmpty>
                                        <logic:notEmpty name="BrewApplicationUpdateForm" property="screenJpeg3FileName">
                                            <br/>
                                            <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                                <bean:write	name="BrewApplicationUpdateForm" property="screenJpeg3FileName"/>
                                            </a>
                                        </logic:notEmpty>
                                        <logic:notEmpty name="BrewApplicationUpdateForm" property="screenJpeg4FileName">
                                            <br/>
                                            <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                                <bean:write	name="BrewApplicationUpdateForm" property="screenJpeg4FileName"/>
                                            </a>
                                        </logic:notEmpty>
                                        <logic:notEmpty name="BrewApplicationUpdateForm" property="screenJpeg5FileName">
                                            <br/>
                                            <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                                <bean:write	name="BrewApplicationUpdateForm" property="screenJpeg5FileName"/>
                                            </a>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <strong><bean:message key="ManageApplicationForm.appFAQ"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="viewText">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FaqDoc&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                            <bean:write	name="BrewApplicationUpdateForm" property="faqDocFileName"/>
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <strong><bean:message key="ManageApplicationForm.appUserGuide"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="viewText">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=UserGuide&app_type=<bean:message key="ManageApplicationForm.brew.app.label"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
                                            <bean:write	name="BrewApplicationUpdateForm" property="userGuideFileName"/>
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <strong><bean:message key="ManageApplicationForm.appFlashDemo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="viewText">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FlashDemo&app_type=<bean:message key="ManageApplicationForm.brew.app.label"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
                                            <bean:write	name="BrewApplicationUpdateForm" property="flashDemoFileName"/>
                                        </a>
                                    </td>
                                </tr> 
                                
                                
                                <tr>
									<td><strong>High Resolution Splash Screen Image (EPS):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=HighResSplash&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="highResSplashFileName"/>
										</a>
									</td>
								</tr>
								
								<tr>
									<td><strong>High Resolution Active Screen Image (EPS):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=HighResActive&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="highResActiveFileName"/>
										</a>
									</td>
								</tr>
								<tr>
									<td><strong>Splash Screen Image (Title screen w/name in image)(JPEG/PNG):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SplashScreen&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="splashScreenFileName"/>
										</a>
									</td>
								</tr>
								<tr>
									<td><strong>Small Splash Screen Image (JPG):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SmallSplash&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="smallSplashFileName"/>
										</a>
									</td>
								</tr>
								<tr>
									<td><strong>Active Screen Image 1 (In game shot)(JPEG/PNG):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ActiveScreen1&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="activeScreen1FileName"/>
										</a>
									</td>
								</tr>
								<tr>
									<td><strong>Small Active Screen Image 1 (JPG):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=SmlActiveScreen&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="smlActiveScreenFileName"/>
										</a>
									</td>
								</tr>
								<tr>
									<td><strong>Active Screen Image 2 (In game shot)(JPEG/PNG):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ActiveScreen2&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="activeScreen2FileName"/>
										</a>										
									</td>
								</tr>
								<tr>
									<td><strong>Flash Animation of Application in Action (SWF):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=AppActiionFlash&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="appActiionFlashFileName"/>
										</a>
									</td>
								</tr>
								<tr>
									<td><strong>Animated .gif of Application in Action (GIF):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=AppGifAction&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="appGifActionFileName"/>
										</a>
									</td>
								</tr>
								<tr>
									<td><strong>Media Store File (JPG):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=MediaStore&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="mediaStoreFileName"/>
										</a>
									</td>
								</tr>                               
								<tr>
									<td><strong>Flash Demo Movie 1 File (Supported on Media Storefront)(SWF):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FlashDemoMovie&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="flashDemoMovieFileName"/>
										</a>
									</td>
								</tr>                               
								<tr>
									<td><strong>Dashboard Screen Image 1 (Graphic Asset) (JPEG):</strong></td>
								</tr>
								<tr>
									<td class="viewText">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=DashboardScrImg&app_id=<bean:write	name="BrewApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="BrewApplicationUpdateForm"	property="dashboardScrImgFileName"/>
										</a>
									</td>
								</tr>                               
                            <% } else {}%>
                          </table></td>
                      </tr>
                    </table></td>
                  <td width="50%" valign="top"><table width="100%"  cellspacing="0" cellpadding="1">
                      <tr>
                        <td><table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message key="ManageApplicationForm.appTechContact"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <%if (!(AimsSecurityManager.checkAccess(request, ManageApplicationsConstants.PRIV_BREW_LEGAL_USER, AimsSecurityManager.SELECT))) {%>
                                <logic:notEmpty name="BrewApplicationUpdateForm" property="aimsContactId" scope="request">
		                  		    <logic:iterate id="formContacts" name="BrewApplicationUpdateForm" property="allContacts" scope="request">
								        <logic:equal name="formContacts" property="contactId" value="<%=BrewApplicationUpdateForm.getAimsContactId().toString()%>">
                                            <tr>
                                                <th>
                                                    <strong><bean:message	key="ManageApplicationForm.appContactFirstName"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                                    <br/>
                                                    <span style="font-weight:normal;"><bean:write name="formContacts" property="firstName" /></span>
                                                </th>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <strong><bean:message	key="ManageApplicationForm.appContactLastName"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="viewText"><bean:write name="formContacts" property="lastName" /></td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <strong><bean:message	key="ManageApplicationForm.appContactTitle"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="viewText"><bean:write name="formContacts" property="title" /></td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <strong><bean:message	key="ManageApplicationForm.appContactEmail"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="viewText"><bean:write name="formContacts" property="emailAddress" /></td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <strong><bean:message	key="ManageApplicationForm.appContactTelephone"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="viewText"><bean:write name="formContacts" property="phone" /></td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <strong><bean:message	key="ManageApplicationForm.appContactMobile"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="viewText"><bean:write name="formContacts" property="mobile" /></td>
                                            </tr>
                                        </logic:equal>
                                    </logic:iterate>
                                </logic:notEmpty>
                            <% } else {}%>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message	key="BrewApplicationForm.table.head.devices.supported"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <%if (!(AimsSecurityManager.checkAccess(request, ManageApplicationsConstants.PRIV_BREW_LEGAL_USER, AimsSecurityManager.SELECT))) {%>
                                <tr><th>
                                <span style="font-weight:normal;">
                                <logic:notEmpty name="BrewApplicationUpdateForm" property="listSelectedDevices" scope="request">
                                    <logic:iterate id="formDevices" name="BrewApplicationUpdateForm" property="availableDevices" scope="request">
                                        <%for (int i=0; i<BrewApplicationUpdateForm.getListSelectedDevices().length; i++) {%>
                                        <logic:equal name="formDevices" property="deviceId" value="<%=(BrewApplicationUpdateForm.getListSelectedDevices())[i].toString()%>">
                                                <bean:write name="formDevices" property="deviceModel" /><br/>
                                            </logic:equal>
                                        <% } %>
                                    </logic:iterate>
                                </logic:notEmpty></span>
                                </th></tr>
                            <% } else {}%>
							
							<tr><td>&nbsp;</td></tr>
			                <tr>
			                  <td class="noPad"><div class="mmBox">
			                      <DIV class="headLeftCurveblk"></DIV>
			                      <H1>Messaging Details</H1>
			                      <DIV class="headRightCurveblk"></DIV>
			                    </div></td>
			                </tr>
			                <tr>
			                	<td style="padding: 0px">
			                		<table width="100%" class="GridGradient" border="0" cellpadding="5" cellspacing="0">
						                <tr>
						                  <th>
						                  	<strong>Publisher Short Code:</strong>
						                  	<bean:write name="BrewApplicationUpdateForm" property="shortCode"/>
						                  </th>
						                </tr>
						                <tr>
						                  <td>
						                  	<strong>Short Code SMS Keywords:</strong>
						                  	<bean:write name="BrewApplicationUpdateForm" property="keyword"/>
						                  </td>
						                </tr>                
						                <tr>
						                  <td>
						                  	<strong>Short Code Aggregator:</strong>
						                  		<logic:notEmpty name="BrewApplicationUpdateForm" property="aggregator">
												<logic:iterate id="agg" name="BrewApplicationUpdateForm" property="allAggregators" scope="request">
							                        <logic:equal name="agg" property="typeId" value="<%=BrewApplicationUpdateForm.getAggregator().toString()%>">
							                            <bean:write name="agg" property="typeValue" />
							                        </logic:equal>
							                    </logic:iterate>
							                    </logic:notEmpty>
						                  </td>
						                </tr>
			                		</table>
			                	</td>
			                </tr>                            
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1>Deployments</H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <%if (!(AimsSecurityManager.checkAccess(request, ManageApplicationsConstants.PRIV_BREW_LEGAL_USER, AimsSecurityManager.SELECT))) {%>
                                <tr><th>
                                    <html:textarea	property="appDeployments" rows="4"	cols="50" readonly="true"/>
                                </th></tr>
                            <% } else {}%>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message key="ApplicationForm.table.head.prrelease"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <bean:message key="ManageApplicationForm.prrelease"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:
                                    <br/>
                                    <span style="font-weight:normal">
                                         <logic:equal name="BrewApplicationUpdateForm" property="ifPrRelease" value="Y">
                                            <bean:message key="ManageApplicationForm.radio.label.yes"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                        </logic:equal>
                                        <logic:equal name="BrewApplicationUpdateForm" property="ifPrRelease" value="N">
                                            <bean:message key="ManageApplicationForm.radio.label.no"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                        </logic:equal>
                                    </span>
                                </th>
                            </tr>
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
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <%@ include  file="include/appViewButtons.jsp" %>
              </table></td>
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
