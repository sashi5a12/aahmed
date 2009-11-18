<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<jsp:useBean id="VZAppZoneApplicationUpdateForm" class="com.netpace.aims.controller.application.VZAppZoneApplicationUpdateForm" scope="request" />
<%VZAppZoneApplicationUpdateForm.setCurrentPage("page1");%>
<%@ include  file="include/vzAppZoneVariables.jsp" %>

<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/vzAppZoneViewTabs.jsp" %>
    <html:form action="/vzAppZoneApplicationUpdate.do"    enctype="multipart/form-data">
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
					<th width="50%"><strong>Application Title:</strong></th>
					<th width="50%"><strong>Application Version:</strong></th>
				</tr>
				<tr>					
                    <td><bean:write name="VZAppZoneApplicationUpdateForm" property="appTitle"/>&nbsp;</td>
                    <td><bean:write name="VZAppZoneApplicationUpdateForm" property="appVersion"/>&nbsp;</td>
				</tr>
				<%-- commented, catalog name and product code, no need to show
                    <tr>
                        <td width="50%" style="padding-top:5px"><strong>Application Catalog Name:</strong></td>
                        <td width="50%" style="padding-top:5px"><strong>Application Product Code:</strong></td>
                    </tr>
                    <tr>
                        <td class="viewText"><bean:write name="VZAppZoneApplicationUpdateForm" property="appCatalogName"/>&nbsp;</td>
                        <td class="viewText"><bean:write name="VZAppZoneApplicationUpdateForm" property="appProductCode"/>&nbsp;</td>
                    </tr>
				--%>
				<tr>
					<td width="50%"><strong><bean:message key="ManageApplicationForm.appShortDesc"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
					<td width="50%"><strong><bean:message key="ManageApplicationForm.appLongDesc"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
				</tr>
				<tr>
					<td><html:textarea styleClass="textareaField" property="appShortDesc" rows="4"   cols="50" readonly="true"></html:textarea></td>
					<td><html:textarea styleClass="textareaField" property="appLongDesc" rows="4"    cols="50" readonly="true"></html:textarea></td>
				</tr>
				<tr>
					<td width="50%"><strong>Go Live Date:</strong></td>
					<td width="50%"><strong>Expiration Date:</strong></td>
				</tr>
				<tr>
					<td class="viewText"><bean:write name="VZAppZoneApplicationUpdateForm" property="goLiveDate"/>&nbsp;</td>
					<td class="viewText"><bean:write name="VZAppZoneApplicationUpdateForm" property="expirationDate"/>&nbsp;</td>
				</tr>
                <tr>
					<td width="50%"><strong>Content Rating:</strong></td>
					<td width="50%"><strong>&nbsp;</strong></td>
				</tr>
				<tr>
					<td class="viewText">
                        <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="contentRating">
                            <logic:iterate id="contentRating" name="VZAppZoneApplicationUpdateForm" property="allContentRatings" type="com.netpace.aims.model.core.AimsTypes">
                                <logic:equal name="contentRating" property="typeId" value="<%=VZAppZoneApplicationUpdateForm.getContentRating().toString()%>">
                                    <bean:write name="contentRating" property="typeValue"/>
                                </logic:equal>
                            </logic:iterate>
                        </logic:notEmpty>&nbsp;
                    </td>
					<td class="viewText">&nbsp;</td>
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
					<th width="50%"><strong><bean:message   key="ManageApplicationForm.appCategory"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
					<th width="50%"><strong><bean:message   key="ManageApplicationForm.appSubCategory"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
				</tr>
				<tr>
					<td>
						<logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="categoryId1">
							<logic:iterate id="formCategories" name="VZAppZoneApplicationUpdateForm" property="allCategories" scope="request">
								<logic:equal name="formCategories" property="categoryId" value="<%=VZAppZoneApplicationUpdateForm.getCategoryId1().toString()%>">
									<bean:write name="formCategories" property="categoryName" />
								</logic:equal>
							</logic:iterate>
						</logic:notEmpty>
						&nbsp;
					</td>
					<td>
						<logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="subCategoryId1">
							<logic:iterate id="formSubCategories" name="VZAppZoneApplicationUpdateForm" property="allSubCategories" scope="request">
								<logic:equal name="formSubCategories" property="subCategoryId" value="<%=VZAppZoneApplicationUpdateForm.getSubCategoryId1().toString()%>">
									<bean:write name="formSubCategories" property="subCategoryName" />
								</logic:equal>
							</logic:iterate>
						</logic:notEmpty>
						&nbsp;
					</td>
				</tr>
            </table></td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.prrelease"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
				<tr>
					<th><strong><bean:message key="ManageApplicationForm.prrelease"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
				</tr>
				<tr>
					<td>
						<logic:equal name="VZAppZoneApplicationUpdateForm" property="ifPrRelease" value="Y">
							<bean:message key="ManageApplicationForm.radio.label.yes"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
						</logic:equal>
						<logic:equal name="VZAppZoneApplicationUpdateForm" property="ifPrRelease" value="N">
							<bean:message key="ManageApplicationForm.radio.label.no"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
						</logic:equal>
					</td>
				</tr>
            </table></td>
          </tr>		  
          <tr><td>&nbsp;</td></tr>          
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
							<%-- commented, contentLandingScreenShot no need to show
                                <tr>
                                    <th width="50%"><strong>Content Landing Page Sample Screen Shot (JPEG/GIF):</strong></th>
                                </tr>
                                <tr>
                                    <td>
                                        <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="contentLandingScreenShotFileName" scope="request">
                                            <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppZoneContentLandingPage&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
                                                <bean:write	name="VZAppZoneApplicationUpdateForm"	property="contentLandingScreenShotFileName"/>
                                            </a>
                                        </logic:notEmpty>
                                        &nbsp;
                                    </td>
                                </tr>
							--%>
							<tr>
								<th width="50%" style="padding-top:5px"><strong>Presentation:</strong></th>
							</tr>
							<tr>
								<td>
									<logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="presentationFileName" scope="request">
										<a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppZonePresentation&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
											<bean:write	name="VZAppZoneApplicationUpdateForm"	property="presentationFileName"/>
										</a>
									</logic:notEmpty>
									&nbsp;
								</td>
							</tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message key="ManageApplicationForm.appTechContact"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
							<tr><td width="100%"style="padding:0px; margin:0px"><table width="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
							<logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="aimsContactId" scope="request">
								<logic:iterate id="formContacts" name="VZAppZoneApplicationUpdateForm" property="allContacts" scope="request">
									<logic:equal name="formContacts" property="contactId" value="<%=VZAppZoneApplicationUpdateForm.getAimsContactId().toString()%>">
										<tr>
											<th width="50%"><strong><bean:message key="ManageApplicationForm.appContactFirstName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
											<th width="50%"><strong><bean:message key="ManageApplicationForm.appContactLastName"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></th>
										</tr>
										<tr>
											<td width="50%"><bean:write name="formContacts" property="firstName" /></td>
											<td width="50%"><bean:write name="formContacts" property="lastName" /></td>
										</tr>
										<tr>
											<td width="50%" style="padding-top:5px"><strong><bean:message key="ManageApplicationForm.appContactTitle" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
											<td width="50%" style="padding-top:5px"><strong><bean:message key="ManageApplicationForm.appContactEmail" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
										</tr>
										<tr>
											<td width="50%"><bean:write name="formContacts" property="title" /></td>
											<td width="50%"><bean:write name="formContacts" property="emailAddress" /></td>
										</tr>
										<tr>
											<td width="50%" style="padding-top:5px"><strong><bean:message key="ManageApplicationForm.appContactTelephone" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
											<td width="50%" style="padding-top:5px"><strong><bean:message key="ManageApplicationForm.appContactMobile"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
										</tr>
										<tr>
											<td width="50%"><bean:write name="formContacts" property="phone" /></td>
											<td width="50%"><bean:write name="formContacts" property="mobile" /></td>
										</tr>
									</logic:equal>
								</logic:iterate>
							</logic:notEmpty>
							</table></td></tr>
                          </table></td>
                      </tr>
                    </table></td>
                  <td width="50%" valign="top"><table width="100%"  cellspacing="0" cellpadding="1">
                      <tr>
                        <td><table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="noPad" colspan="4"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1>Information For Premium Content Only</H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
							<tr>
								<th width="50%"><strong>Subscription Billing (Monthly):</strong></th>
								<th width="50%"><strong>Onetime Billing:</strong></th>
							</tr>
							<tr>
								<td>
									<logic:equal name="VZAppZoneApplicationUpdateForm" property="subscriptionBillingMonthly" value="Y">
										<bean:message key="ManageApplicationForm.radio.label.yes"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
									</logic:equal>
									<logic:equal name="VZAppZoneApplicationUpdateForm" property="subscriptionBillingMonthly" value="N">
										<bean:message key="ManageApplicationForm.radio.label.no"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
									</logic:equal>
								</td>
								<td>
									<logic:equal name="VZAppZoneApplicationUpdateForm" property="oneTimeBilling" value="Y">
										<bean:message key="ManageApplicationForm.radio.label.yes"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
									</logic:equal>
									<logic:equal name="VZAppZoneApplicationUpdateForm" property="oneTimeBilling" value="N">
										<bean:message key="ManageApplicationForm.radio.label.no"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
									</logic:equal>
								</td>
							</tr>
							<tr>
								<td width="50%"><strong>Subscription Billing Pricepoint:</strong></td>
								<td width="50%"><strong>Onetime Billing Pricepoint:</strong></td>
							</tr>
							<tr>
								<td class="viewText">
									<bean:write name="VZAppZoneApplicationUpdateForm" property="subscriptionBillingPricePoint" />&nbsp;
								</td>
								<td class="viewText">
									<bean:write name="VZAppZoneApplicationUpdateForm" property="oneTimeBillingPricePoint" />&nbsp;
								</td>
							</tr>
                            <tr><td colspan="2"><b><i>Note: Subscription Billing is not being offered at this time</i></b></td></tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad" colspan="4"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1>Additional Information</H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
								<th width="50%"><strong>Community/Chat/UGC:</strong></h>
								<th class="viewText">
									<logic:equal name="VZAppZoneApplicationUpdateForm" property="communityChatUgc" value="Y">
										<bean:message key="ManageApplicationForm.radio.label.yes"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
									</logic:equal>
									<logic:equal name="VZAppZoneApplicationUpdateForm" property="communityChatUgc" value="N">
										<bean:message key="ManageApplicationForm.radio.label.no"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
									</logic:equal>
								</th>
							</tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                                <td width="50%"><strong>Contest/Sweepstakes:</strong></td>
								<td class="viewText">
									<logic:equal name="VZAppZoneApplicationUpdateForm" property="contentSweekstakes" value="Y">
										<bean:message key="ManageApplicationForm.radio.label.yes"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
									</logic:equal>
									<logic:equal name="VZAppZoneApplicationUpdateForm" property="contentSweekstakes" value="N">
										<bean:message key="ManageApplicationForm.radio.label.no"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
									</logic:equal>
								</td>
							</tr>
                          </table></td>
                      </tr>
                    </table></td>
                </tr>
              </table></td>
          </tr>         
          <tr>
            <td width="100%">
                <%@ include  file="include/vzAppZoneViewButtons.jsp" %>			
			</td>
          </tr>
        </table>
      </div>
    </html:form>
  </div>
</div>
