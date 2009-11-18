<%@	page language="java" import="com.netpace.aims.bo.security.*, com.netpace.aims.bo.application.*, com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<jsp:useBean id="task" class="java.lang.String"	scope="request"/>
<jsp:useBean id="SmsApplicationUpdateForm" class="com.netpace.aims.controller.application.SmsApplicationUpdateForm"	scope="request"	/>
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" />

<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/appViewTabs.jsp" %>
    <html:form action="/smsApplicationUpdate.do"	enctype="multipart/form-data">
      <div class="contentbox">
        <%@ include  file="include/appViewHidden.jsp" %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message	key="SmsApplicationForm.table.head.campaign.details"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                <tr>
                <tr>
                    <th width="50%">
                        <strong><bean:message	key="SmsApplicationForm.campTitle"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                        <br/>
                        <span style="font-weight:normal"><bean:write	name="SmsApplicationUpdateForm"	property="title"/></span>
                    </th>
                    <th width="50%">
                        <strong><bean:message key="SmsApplicationForm.contentProvider"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                        <br/>
                        <span style="font-weight:normal"><bean:write name="SmsApplicationUpdateForm" property="contentProvider"/></span>
                    </th>
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
                    <td class="viewText">
                        <html:textarea	property="shortDesc" rows="4"	cols="50"	readonly="true"/>
                    </td>
                    <td class="text">
                        <html:textarea	property="longDesc"	rows="4"	cols="50"	readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td width="50%">
                        <strong><bean:message	key="SmsApplicationForm.campaignStartDate"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    </td>
                    <td width="50%">
                        <strong><bean:message	key="SmsApplicationForm.campaignEndDate"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    </td>
                </tr>
                <tr>
                    <td class="viewText">
                        <bean:write	name="SmsApplicationUpdateForm"	property="campaignStartDate"/>
                    </td>
                    <td class="text">
                        <bean:write	name="SmsApplicationUpdateForm"	property="campaignEndDate"/>
                    </td>
                </tr>
              </table></td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message	key="SmsApplicationForm.table.head.campaign.class.price"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
              <tr>
                <tr>
                    <th width="50%">
                        <strong><bean:message	key="SmsApplicationForm.campaignClassification"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                        <br/>
                        <span style="font-weight:normal">
                            <logic:equal name="SmsApplicationUpdateForm" property="campaignClassification" value="P">
                                <bean:message	key="ManageApplicationForm.radio.label.premium"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </logic:equal>
                            <logic:equal name="SmsApplicationUpdateForm" property="campaignClassification" value="N">
                                <bean:message	key="ManageApplicationForm.radio.label.nonpremium"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </logic:equal>
                        </span>
                    </th>
                    <th width="50%">&nbsp;</th>
                </tr>
                <tr>
                    <td width="50%">
                        <strong><bean:message	key="SmsApplicationForm.campType"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    </td>
                    <td width="50%"><strong>Campaign Subtype:</strong></td>
                </tr>
                <tr>
                    <td class="viewText">
                        <logic:iterate id="formCategories" name="SmsApplicationUpdateForm" property="allCategories"	scope="request">
                            <logic:equal name="formCategories" property="categoryId" value="<%=SmsApplicationUpdateForm.getAimsAppCategoryId().toString()%>">
                                <bean:write	name="formCategories"	property="categoryName"	/>
                            </logic:equal>
                        </logic:iterate>
                    </td>
                    <td class="text">
                        <logic:iterate id="formSubCategories"	name="SmsApplicationUpdateForm"	property="allSubCategories"	scope="request">
                            <logic:equal name="formSubCategories"	property="subCategoryId" value="<%=SmsApplicationUpdateForm.getAimsAppSubCategoryId().toString()%>">
                                <bean:write	name="formSubCategories" property="subCategoryName"	/>
                            </logic:equal>
                        </logic:iterate>
                    </td>
                </tr>
                <tr>
                    <td width="50%"><strong><bean:message key="SmsApplicationForm.wholesalePrice"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                    <td width="50%"><strong><bean:message key="SmsApplicationForm.retailPrice"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                </tr>
                <tr>
                    <td class="viewText">
                         <bean:write name="SmsApplicationUpdateForm" property="wholesalePrice"/>
                    </td>
                    <td class="text">
                         <bean:write name="SmsApplicationUpdateForm" property="retailPrice"/>
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
                                  <H1><bean:message	key="SmsApplicationForm.table.head.msg.traffic"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong><bean:message key="SmsApplicationForm.avgMsgsPerSec"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                    <span style="font-weight:normal"><bean:write name="SmsApplicationUpdateForm" property="avgMsgsPerSec"/></span>
                                </th>
                            </tr>
                            <tr>
                                <td><strong><bean:message key="SmsApplicationForm.peakMsgsPerSec"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                     <bean:write name="SmsApplicationUpdateForm" property="peakMsgsPerSec"/>
                                </td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message	key="SmsApplicationForm.table.head.campaign.files"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong><bean:message key="ManageApplicationForm.appFAQ"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                    <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=FaqDoc&app_type=<bean:message	key="ManageApplicationForm.sms.app.label"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="SmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
                                        <bean:write	name="SmsApplicationUpdateForm"	property="faqDocFileName"/>
                                    </a>                                    
                                </th>
                            </tr>
                            <tr>
                                <td><strong><bean:message key="ManageApplicationForm.appUserGuide"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=UserGuide&app_type=<bean:message key="ManageApplicationForm.sms.app.label"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="SmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
                                        <bean:write	name="SmsApplicationUpdateForm"	property="userGuideFileName"/>
                                    </a>
                                </td>
                            </tr>
                            <tr>
                                <td><strong><bean:message key="ManageApplicationForm.appTestPlan"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TestPlanResults&app_type=<bean:message key="ManageApplicationForm.sms.app.label"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="SmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
                                        <bean:write	name="SmsApplicationUpdateForm"	property="testPlanResultsFileName"/>
                                    </a>
                                </td>
                            </tr>
                            <tr>
                                <td><strong><bean:message	key="SmsApplicationForm.messageFlow"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=MessageFlow&app_type=<bean:message key="ManageApplicationForm.sms.app.label"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="SmsApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
                                        <bean:write	name="SmsApplicationUpdateForm"	property="messageFlowFileName"/>
                                    </a>
                                </td>
                            </tr>
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
                                    <strong><bean:message key="ManageApplicationForm.prrelease"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                    <span style="font-weight:normal">
                                        <logic:equal name="SmsApplicationUpdateForm" property="ifPrRelease"	value="Y">
                                            <bean:message key="ManageApplicationForm.radio.label.yes"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                        </logic:equal>
                                        <logic:equal name="SmsApplicationUpdateForm" property="ifPrRelease"	value="N">
                                            <bean:message key="ManageApplicationForm.radio.label.no"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                        </logic:equal>                                        
                                    </span>
                                </th>
                            </tr>
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
                                  <H1><bean:message	key="SmsApplicationForm.table.head.launch.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong><bean:message key="SmsApplicationForm.carrierSupport"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                    <span style="font-weight:normal">
                                        <logic:equal name="SmsApplicationUpdateForm" property="carrierSupport"	value="V">
                                            <bean:message key="ManageApplicationForm.radio.label.vzw"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                        </logic:equal>
                                        <logic:equal name="SmsApplicationUpdateForm" property="carrierSupport"	value="M">
                                            <bean:message key="ManageApplicationForm.radio.label.multicarrier"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                        </logic:equal>
                                        <logic:equal name="SmsApplicationUpdateForm" property="carrierSupport"	value="C">
                                            <bean:message key="ManageApplicationForm.radio.label.csc"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                        </logic:equal>
                                    </span>
                                </th>
                            </tr>
                            <tr>
                                <td><strong><bean:message key="SmsApplicationForm.ifVzwDirectConn"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:equal name="SmsApplicationUpdateForm" property="ifVzwDirectConn"	value="Y">
                                        <bean:message key="ManageApplicationForm.radio.label.yes"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                    </logic:equal>
                                    <logic:equal name="SmsApplicationUpdateForm" property="ifVzwDirectConn"	value="N">
                                        <bean:message key="ManageApplicationForm.radio.label.no"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td><strong><bean:message key="SmsApplicationForm.connType"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <bean:write	name="SmsApplicationUpdateForm"	property="connType"/>
                                </td>
                            </tr>
                            <tr>
                                <td><strong><bean:message key="SmsApplicationForm.shortCodesReqd"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <bean:write	name="SmsApplicationUpdateForm"	property="shortCodesReqd"/>
                                </td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message	key="ManageApplicationForm.appTechContact"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <logic:notEmpty	name="SmsApplicationUpdateForm"	property="aimsContactId" scope="request">
                                <logic:iterate id="formContacts" name="SmsApplicationUpdateForm" property="allContacts"	scope="request">
                                    <logic:equal name="formContacts" property="contactId"	value="<%=SmsApplicationUpdateForm.getAimsContactId().toString()%>">
                                        <tr>
                                            <th>
                                                <strong><bean:message	key="ManageApplicationForm.appContactFirstName"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>
                                                <br/>
                                                <span style="font-weight:normal"><bean:write	name="formContacts"	property="firstName" /></span>
                                            </th>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message	key="ManageApplicationForm.appContactLastName"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write	name="formContacts"	property="lastName"	/></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message	key="ManageApplicationForm.appContactTitle"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write	name="formContacts"	property="title" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message	key="ManageApplicationForm.appContactEmail"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write	name="formContacts"	property="emailAddress"	/></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message	key="ManageApplicationForm.appContactTelephone"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write	name="formContacts"	property="phone" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message	key="ManageApplicationForm.appContactMobile"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write	name="formContacts"	property="mobile"	/></td>
                                        </tr>
                                    </logic:equal>
                                </logic:iterate>
                            </logic:notEmpty>
                            <tr><td>&nbsp;</td></tr>                            
                          </table></td>
                      </tr>
                    </table></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message	key="SmsApplicationForm.table.head.other.details"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
              <tr>
                  <th width="50%">
                      <strong><bean:message key="ManageApplicationForm.appDeployments"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                      <br/>
                      <html:textarea styleClass="textareaField"	property="appDeployments"	rows="4"	cols="50"	readonly="true"/>
                  </th>
                  <th width="50%">
                      <strong><bean:message key="SmsApplicationForm.campaignPromoInfo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                      <br/>
                      <html:textarea styleClass="textareaField"	property="campaignPromoInfo"	rows="4"	cols="30"	readonly="true"/>
                  </th>
              </tr>
            </table></td>
          </tr>
          <tr><td>&nbsp;</td></tr>
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
