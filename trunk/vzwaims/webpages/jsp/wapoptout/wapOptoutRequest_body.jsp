<%@	page language="java" import="com.netpace.aims.util.*"%>
<%@page import="com.netpace.aims.controller.wapoptout.WapOptoutForm"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ include  file="wapoptoutJScript.jsp" %>
<%@ include file="/common/error.jsp"%>
<%int rowCount=0; %>
<div id="contentBox" style="float: left">
  <div class="homeColumnTab">
    <div class="contentbox">
      <html:form styleId="WapOptoutForm" action="/wapOptout" method="post">
        <html:hidden property="task" value="submit"/>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="WapOptoutForm.secHeading.optoutPage" bundle="com.netpace.aims.action.WAP_OPTOUT"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <th width="100%" colspan="3" style="text-align: justify;"> <strong>Please Read This First:</strong>
                    <p>
                        Verizon Wireless has designed this process to allow website owners to affirmatively opt-out from having their 
                        webpage(s) optimized for enhanced user viewing of Mobile Web 2.0 or the HTML Browser on Verizon Wireless authorized 
                        devices. To initiate the opt-out process, please complete the form below in its entirety, then, click the Submit button. 
                        A confirmation email will be sent to the address you provide to notify you that your request has been received. 
                    </p>
                    <p><b>
                        By submitting an opt-out request to Verizon Wireless you represent and warrant that you are the website owner or are legally 
                        authorized to act on the website owner's behalf. You understand and agree that you shall be liable for any injury, damage, 
                        losses, or costs (including reasonable attorneys fees) caused to or incurred by Verizon Wireless, the website owner, and any 
                        other person arising out of or due to any misrepresentation or any fraudulent act or omission in the completion of this form 
                        or in the submission of an opt-out request.   Verizon Wireless may verify and validate your request by contacting sources such 
                        as the Domain Name Registrar, the Registrant of record, and/or the Registrant's Administrative Contact of record. 
                        <br/><br/>Thank you for your cooperation. 
                    </b></p>
                  </th>
                </tr>
                <tr>
                  <td width="100%" colspan="3">
                  	<span class="requiredText">* Required fields</span>
                    <br/><br/>
                  	<strong><bean:message key="WapOptoutForm.companyName" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="companyName" size="40" maxlength="255"/>
                  </td>
                </tr>
                <tr>
                  <td width="40%" colspan="2">
                  	<strong><bean:message key="WapOptoutForm.firstName" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="reqFirstName" size="40" maxlength="100"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.lastName" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="reqLastName" size="40" maxlength="100"/>
                  </td>
                </tr>
                <tr>
                  <td width="40%" colspan="2">
                  	<strong><bean:message key="WapOptoutForm.streetAddress" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="streetAddress" size="40" maxlength="255"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.reqEmailAddress" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="reqEmailAddress" size="40" maxlength="200"/>
                  </td>
                </tr>
                <tr>
                  <td width="100%" colspan="3">
                  	<strong><bean:message key="WapOptoutForm.city" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="city" size="40" maxlength="100"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%">
                  	<strong><bean:message key="WapOptoutForm.state" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="state" size="15" maxlength="100"/>
                  </td>
                  <td width="25%">
                  	<strong><bean:message key="WapOptoutForm.zipCode" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="zipCode" size="15" maxlength="20"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.country" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:select property="country" styleClass="selectField">
                      <html:options collection="countryList" property="countryName" labelProperty="countryName"></html:options>
                    </html:select>
                  </td>
                </tr>
                <tr>
                  <td width="100%" colspan="3">
                  	<strong><bean:message key="WapOptoutForm.phoneNumber" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="reqPhoneNumber" size="40" maxlength="50"/>
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
                <H1><bean:message key="WapOptoutForm.secHeading.bypassUrls" bundle="com.netpace.aims.action.WAP_OPTOUT"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr><td>
          <table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
            <tr valign="top">
              <th vAlign="top">
              	<strong>
              		<bean:message key="WapOptoutForm.bypassUrls" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
              		<span class="requiredText">*</span>
              		<%--<a onClick="openTooltip(); return false;" href="#" style="text-decoration: none">[?]</a>--%>
                    <html:link href="/aims/downloads/wapoptout/Tool_Tips.pdf" target="_blank">[?]</html:link>
                  </strong>
              	<br/>
              </th>
            </tr>
            <tr>
              <td width="100%" >
				<div id="divBypassURLs">
					<table id="tblBypassURLs" width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
						<tbody>
						<c:choose>
							<c:when test="${not empty requestScope.WapOptoutForm.bypassUrls}">								
								<c:forEach var="url" items="${requestScope.WapOptoutForm.bypassUrls}" varStatus="status">
									<c:if test="${not empty url}">
										<%rowCount++; %>
										<tr id='row<%=rowCount%>' >
											<td style="padding:0px;" width="550px">																	
												<input class="inputField" 
													  onblur="validateUrl(this);" 
													   type="text" name="bypassUrls" 
													   size="60" maxlength="255" 
													   style="width:550px;" 
													   value="<c:out value="${url}"/>" >
											</td>
											<td style="padding: 0px 0px 0px 5px;vertical-align: middle; text-align: left;">
												<a href="javascript:removeURLRow('tblBypassURLs','row<%=rowCount%>', false);"><bean:message key='images.delete.icon'/></a>
											</td>
										</tr>
									</c:if>
								</c:forEach>																	
							</c:when>
							<c:otherwise>
								<tr  id="row0" >
								<td style="padding:0px;" width="550px" id="row0">
									<input class="inputField" type="text"  onblur="validateUrl(this);"  name="bypassUrls" size="60" maxlength="255" style="width:550px;" >
								</td>
								<td style="padding: 0px 0px 0px 5px;vertical-align: middle; text-align: left;">
									<a href="javascript:removeURLRow('tblBypassURLs','row0', false);"><bean:message key='images.delete.icon'/></a>
								</td></tr>																
							</c:otherwise>
						</c:choose>
						</tbody>
					</table>
				</div>              
              </td>
            </tr>
            <tr><td width="100%"><br/>
				<div id="divAddRow">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td style="padding: 0px" width="350px">
								<div class="redBtn" style="float:left;" title="Do not Optimize the following Additional URL(s)">
									<div><div><div onClick="javascript:addURLRow('tblBypassURLs', 'bypassUrls', '');//">Do not Optimize the following Additional URL(s)</div></div></div>
								</div>
							</td>						
							<td align="left">&nbsp;<span id="divExpandCollapse"></span></td>
				      	</tr>
					</table>
				</div>           
            </td></tr>
          </table>
          </td></tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="WapOptoutForm.secHeading.adminContact" bundle="com.netpace.aims.action.WAP_OPTOUT"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
				<tr>
                  <th width="100%" colspan="2">
                  	<strong><bean:message key="WapOptoutForm.companyName" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="adminCompanyName" size="40" maxlength="255"/>
                  </th>                 
                </tr>            
                <tr>
                  <td width="40%">
                  	<strong><bean:message key="WapOptoutForm.adminFirstName" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="adminFirstName" size="40" maxlength="100"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.adminLastName" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="adminLastName" size="40" maxlength="100"/>
                  </td>
                </tr>
                <tr>
                  <td width="40%">
                  	<strong><bean:message key="WapOptoutForm.adminPhoneNumber" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="adminPhoneNumber" size="40" maxlength="50"/>
                  </td>
                  <td width="60%"><strong>
                    <bean:message key="WapOptoutForm.adminEmailAddress" bundle="com.netpace.aims.action.WAP_OPTOUT"/>
                    <span class="requiredText">*</span></strong><br/>
                    <html:text styleClass="inputField" property="adminEmailAddress" size="40" maxlength="200"/>
                  </td>
                </tr>                
              </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td><div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Submit" title="Continue">
                      <div>
                        <div>
                          <div onclick="submitForm('WapOptoutForm')">Continue</div>
                        </div>
                      </div>
                    </div></td>
                </tr>                
              </table></td>
          </tr>
          
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><div class="lBox">
                <div class="headLeftCurveblk"></div>
                <h1>Opt-in to Optimized View</h1>
                <div class="headRightCurveblk"></div>
              </div></td>
          </tr>          
          
          <tr>
            <td><table class="GridGradient" border="0" cellpadding="0" cellspacing="0" width="100%">
				<tbody><tr>
                  <th colspan="2" width="100%">
                  	<p>
                        If you believe that an opt-out request to remove your URL from optimization through Optimized View for Mobile Web has been fraudulently 
                        or inadvertently submitted, please send a request to reactivate your URL for optimization. Upon validation of this request with 
                        your domain Administrative Contact your URL will be removed from the opt-out list and made available to Verizon Wireless subscribers 
                        through Optimized View for Mobile Web.  
                    </p>
                    <p>
                        Verizon Wireless will maintain a list of submissions and reserves the right to deny any request for any reason. 
                    </p>
                    <p>
                        In your request to Verizon Wireless, you must include your contact information as well as the registered Administrative Contact for the 
                        domain(s) you wish to have included in Optimized View for Mobile Web.  
                    </p>
                    <p>
                        Thank you.
                    </p>
                    <p>
                        Request to opt-in to Optimized View for Mobile Web:
                        <br/>
                        <a href="mailto:OptimizedViewOpt-in@VerizonWireless.com">OptimizedViewOpt-in@VerizonWireless.com<a/>
                    </p>
                  </th>                 
                </tr>                            
              </tbody></table></td>
          </tr>
          
        </table>
      </html:form>
    </div>
  </div>
</div>
<script	language="javascript">
    collapseURLTable();
    tableSize=<%=rowCount%>;
</script>
