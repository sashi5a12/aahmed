<%@	page language="java" import="com.netpace.aims.bo.security.*, com.netpace.aims.bo.application.*, com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*, java.util.*, com.netpace.aims.model.application.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<script language=""javascript>

function boboHelp(event)
{
	openZonHelpWindow(event, '<bean:message key="JMA.BOBO.Hint" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>'); 
	return false;
}
function hintODI(event)
{
	openZonHelpWindow(event, "<bean:message key='JMA.ODI.Hint' bundle='com.netpace.aims.action.ALLIANCE_MESSAGE'/>"); 
	return false;
}

</script>

<jsp:useBean id="task" class="java.lang.String"	scope="request"/>
<jsp:useBean id="EntApplicationUpdateForm" class="com.netpace.aims.controller.application.EntApplicationUpdateForm" scope="request" />
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" />

<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/appViewTabs.jsp" %>
    <html:form action="/entApplicationUpdate.do"	enctype="multipart/form-data">
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
            <td><table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
                <tr>
                    <th width="50%">
                        <strong><bean:message key="ManageApplicationForm.appTitle"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                        <br/>
                        <span style="font-weight:normal"><bean:write	name="EntApplicationUpdateForm" property="title"/></span>
                    </th>
                    <th width="50%">
                        <strong><bean:message key="ManageApplicationForm.appVersion"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                        <br/>
                        <span style="font-weight:normal"><bean:write	name="EntApplicationUpdateForm" property="version"/></span>
                    </th>
                </tr>
                <tr>
                    <td width="50%">
                        <strong><bean:message key="ManageApplicationForm.appShortDesc"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong><br/>
                        <html:textarea styleClass="textareaField"	property="shortDesc" rows="4"	cols="50" readonly="true"/>
                    </td>
                    <td width="50%">
                        <strong><bean:message key="ManageApplicationForm.appLongDesc"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong><br/>
                        <html:textarea styleClass="textareaField"	property="longDesc" rows="4"	cols="50" readonly="true"/>
                    </td>
                </tr>                
              </table></td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.app.classification"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
              <tr><th>
                  <strong>Devices </strong><br/>
                    <html:textarea styleClass="textareaField"	property="devices" rows="4"	cols="50" readonly="true"/>
                    
                </th></tr>
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
                                  <H1><bean:message	key="EntApplicationForm.table.head.app.usage.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong><bean:message	key="EntApplicationForm.totalEndUsers"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                    <span style="font-weight:normal"><bean:write	name="EntApplicationUpdateForm" property="totalEndUsers"/></span>
                                </th>
                            </tr>
                            <tr>
                                <td><strong><bean:message	key="EntApplicationForm.noOfUserAccess"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                            </tr>
                            <tr>
                                <td  class="viewText">
                                     <bean:write	name="EntApplicationUpdateForm" property="noOfUsersAccess"/>
                                </td>
                            </tr>
                            <tr>
                                <td><strong><bean:message	key="EntApplicationForm.platformDefMode"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                            </tr>
                            <tr>
                                <td  class="viewText">
                                    <logic:equal name="EntApplicationUpdateForm" property="platformDepMode" value="1">
                                        <bean:message	key="ManageApplicationForm.radio.label.behindCustomerFirewall"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                    </logic:equal>
                                    <logic:equal name="EntApplicationUpdateForm" property="platformDepMode" value="2">
                                        <bean:message	key="ManageApplicationForm.radio.label.insideCarrierNetwork"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                    </logic:equal>
                                    <logic:equal name="EntApplicationUpdateForm" property="platformDepMode" value="3">
                                        <bean:message	key="ManageApplicationForm.radio.label.managedFirewall"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message key="ApplicationForm.table.head.app.files"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong><bean:message key="EntApplicationForm.presentationUpload.forview"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                     <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=PresentationFile&app_type=<bean:message	key="ManageApplicationForm.ent.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="EntApplicationUpdateForm" property="appsId" />" class="a" target="_blank">
                                            <bean:write	name="EntApplicationUpdateForm" property="presentationFileName"/>
                                     </a>
                                </th>
                            </tr>
                           
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message	key="EntApplicationForm.table.head.industry.focus"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <logic:notEmpty name="EntApplicationUpdateForm" property="industryFocusId" scope="request">
                                        <logic:iterate id="formIndFocuses" name="EntApplicationUpdateForm" property="allIndustryFocus" scope="request">
                                            <%for (int i=0; i<EntApplicationUpdateForm.getIndustryFocusId().length; i++) {%>
                                                <logic:equal name="formIndFocuses" property="industryId" value="<%=(EntApplicationUpdateForm.getIndustryFocusId())[i].toString()%>">
                                                    <span style="font-weight:normal"><bean:write name="formIndFocuses" property="industryName" /></span><br/>
                                                </logic:equal>
                                            <% } %>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                    <logic:notEmpty name="EntApplicationUpdateForm" property="otherIndFocusValue">
                                        <bean:message key="EntApplicationForm.otherIndustryFocus"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                                        &nbsp;-&nbsp;
                                        <span style="font-weight:normal"><bean:write name="EntApplicationUpdateForm" property="otherIndFocusValue" /></span>
                                    </logic:notEmpty>
                                </th>
                            </tr>
                          </table></td>
                      </tr>
                    </table></td>
                  <td width="50%" valign="top"><table width="100%"  cellspacing="0" cellpadding="1">
                      <tr>
                        <td><table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message	key="EntApplicationForm.table.head.cust.support.info"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong><bean:message key="ManageApplicationForm.appContactTelephone"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                    <br/>
                                    <span style="font-weight:normal"><bean:write	name="EntApplicationUpdateForm" property="custSupportPhone"/></span>
                                </th>
                            </tr>
                            <tr>
                                <td><strong><bean:message key="ManageApplicationForm.appContactEmail"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText"><bean:write	name="EntApplicationUpdateForm" property="custSupportEmail"/></td>
                            </tr>
                            <tr>
                                <td><strong><bean:message key="EntApplicationForm.custSupportHours"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText"><bean:write	name="EntApplicationUpdateForm" property="custSupportHours"/></td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message	key="ManageApplicationForm.appSolutionContact"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <logic:notEmpty name="EntApplicationUpdateForm" property="aimsContactId" scope="request">
                                <logic:iterate id="formContacts" name="EntApplicationUpdateForm" property="allContacts" scope="request">
                                    <logic:equal name="formContacts" property="contactId" value="<%=EntApplicationUpdateForm.getAimsContactId().toString()%>">
                                        <tr>
                                            <th>
                                                <strong><bean:message	key="ManageApplicationForm.appContactFirstName"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                                <br/>
                                                <span style="font-weight:normal"><bean:write name="formContacts" property="firstName" /></span>
                                            </th>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message	key="ManageApplicationForm.appContactLastName"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="formContacts" property="lastName" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message	key="ManageApplicationForm.appContactTitle"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="formContacts" property="title" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message	key="ManageApplicationForm.appContactEmail"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="formContacts" property="emailAddress" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message	key="ManageApplicationForm.appContactTelephone"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="formContacts" property="phone" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message	key="ManageApplicationForm.appContactMobile"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td class="viewText"><bean:write name="formContacts" property="mobile" /></td>
                                        </tr>
                                    </logic:equal>
                                </logic:iterate>
                            </logic:notEmpty>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message	key="EntApplicationForm.table.head.regions"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <logic:notEmpty name="EntApplicationUpdateForm" property="regionId" scope="request">
                                        <logic:iterate id="formRegions" name="EntApplicationUpdateForm" property="allRegions" scope="request">
                                            <%for (int i=0; i<EntApplicationUpdateForm.getRegionId().length; i++) {%>
                                                <logic:equal name="formRegions" property="regionId" value="<%=(EntApplicationUpdateForm.getRegionId())[i].toString()%>">
                                                    <span style="font-weight:normal"><bean:write name="formRegions" property="regionName" /></span><br/>
                                                </logic:equal>
                                            <% } %>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                </th>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                       
                          </table></td>
                      </tr>
                    </table></td>
                </tr>
              </table></td>
          </tr>
          <tr><td>&nbsp;</td></tr>

          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="ApplicationForm.table.head.prrelease"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                <tr>
                    <th>
                        <strong><bean:message key="ManageApplicationForm.prrelease"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                        <br/>
                        <span style="font-weight:normal">
                            <logic:equal name="EntApplicationUpdateForm" property="ifPrRelease" value="Y">
                                <bean:message key="ManageApplicationForm.radio.label.yes"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </logic:equal>
                            <logic:equal name="EntApplicationUpdateForm" property="ifPrRelease" value="N">
                                <bean:message key="ManageApplicationForm.radio.label.no"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </logic:equal>
                        </span>
                    </th>
                </tr>
              </table></td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message	key="ApplicationForm.table.head.other.details"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                <tr>
                    <th width="50%">
                        <strong><bean:message key="EntApplicationForm.fortuneCustomers.forview"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                        <br/>
                        <html:textarea styleClass="textareaField" property="fortuneCustomers" rows="3" cols="50" readonly="true"></html:textarea>
                    </th>
                    <th width="50%">
                        <strong><bean:message key="ManageApplicationForm.appDeployments"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                        <br/>
                        <html:textarea styleClass="textareaField" property="appDeployments" rows="3" cols="50" readonly="true"></html:textarea>
                    </th>
                </tr>
                 </tr>
                     <tr>
                    	<td>
                    		<strong><bean:message key="EntApplicationForm.productInfo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                          		<logic:iterate id="obj" name="EntApplicationUpdateForm" property="allProductinfo">
						            <html:multibox property="entProductInfo" onclick="" disabled="true">
						                <bean:write name="obj" property="typeId"/>
						            </html:multibox>
					            <bean:write name="obj" property="typeValue"/>
					            <br/>
					        </logic:iterate> 
                          <html:hidden property="productInformation"/>
                      </td>
                    	<td>
                    		<strong><bean:message key="EntApplicationForm.briefDescription"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:textarea styleClass="textareaField" property="briefDescription" onkeyup="" onkeypress="" rows="3" cols="50" readonly="true"></html:textarea>
                    	</td>
                    </tr>
                    
                    <tr>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productExclusiveToVZW"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:radio property="isProductExeclusiveToVZW" value="Y" disabled="true" onclick=""/>
            					Yes	
            				<html:radio property="isProductExeclusiveToVZW" value="N" disabled="true" onclick=""/>
            					 No
            				<br/>	 
                            <html:textarea styleClass="textareaField" property="productExclusiveToVzw" onkeyup="" onkeypress="" rows="3" cols="50" readonly="true"></html:textarea>
                    		
                    		
                    	</td>
                    	<td>
                    		<strong><bean:message key="EntApplicationForm.additionalInfo"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:textarea styleClass="textareaField" property="additionalInformation" onkeyup="" onkeypress="" rows="3" cols="50" readonly="true"></html:textarea>
                    	</td>
                    </tr>
                    
                    <tr>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.optionToGoExclusiveWithVZW"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
          					<html:radio property="isGoExclusiveWithVZW" value="Y" disabled="true"/>
            					Yes	
            				<html:radio property="isGoExclusiveWithVZW" value="N" disabled="true"/>
            					 No
                    	</td>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productUseVzwVzNt"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:radio property="isProductUseVzwVzNt" value="Y" disabled="true"/>
            					Yes	
            				<html:radio property="isProductUseVzwVzNt" value="N" disabled="true"/>
            					 No
                    	</td>
                    </tr>
                    <tr>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productCertifiedWithVZW"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                            <html:radio property="isProductCertifiedVZW" value="Y" disabled="true"/>
            					Yes	
            				<html:radio property="isProductCertifiedVZW" value="N" disabled="true"/>
            					 No
                    	</td>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productCertifiedODIProcess"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <a href="#" onclick="hintODI(event); return false;">[?]</a>
                           <br/>
                            <html:radio property="isProductCertifiedODIProcess" value="Y" disabled="true"/>
            					Yes	
            				<html:radio property="isProductCertifiedODIProcess" value="N" disabled="true"/>
            					 No
                    	</td>
                    </tr>
                    
                     <tr>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productRequiredLBS"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <a href="#" onclick="openZonHelpWindow(event,'<bean:message key="JMA.LBS.Hint" bundle="com.netpace.aims.action.ALLIANCE_MESSAGE"/>'); return false;">[?]</a><br/>
                            <html:radio property="isProductRequiedLBS" value="Y" disabled="true"/>
            					Yes	
            				<html:radio property="isProductRequiedLBS" value="N" disabled="true"/>
            					 No
                    	</td>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.productOfferBOBO"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <a href="#" onclick="boboHelp(event); return false;">[?]</a><br/>
                            <html:radio property="isOfferBoboServices" value="Y" disabled="true"/>
            					Yes	
            				<html:radio property="isOfferBoboServices" value="N" disabled="true"/>
            					 No
                    	</td>
                    </tr>
                    <tr>
                    	<td><br/>
                    		<strong><bean:message key="EntApplicationForm.marketSegment"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong>
                            <br/>
                          		<logic:iterate id="obj" name="EntApplicationUpdateForm" property="allMarketSegInfo">
						            <html:multibox property="entMarketSegInfo" onclick="" disabled="true">
						                <bean:write name="obj" property="typeId" />
						            </html:multibox>
					            <bean:write name="obj" property="typeValue"/>
					            <br/>
					        </logic:iterate> 
                      </td>
                    	
                    	<td>
                    	</td>
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
