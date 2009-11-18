<%@	page language="java" import="com.netpace.aims.util.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ include  file="wapoptoutJScript.jsp" %>
<%@ include file="/common/error.jsp"%>

<div id="contentBox" style="float: left">
  <div class="homeColumnTab">
    <div class="contentbox">
      <html:form action="/wapOptout" method="post">
        <html:hidden property="task" value="confirm"/>        
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Please verify your information before submission</H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="5">
                <tr>
                  <th width="100%" colspan="3">
                  	<strong><bean:message key="WapOptoutForm.companyName" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="companyName"/>
                    <bean:write name="WapOptoutForm" property="companyName"/>
                  </th>
                </tr>
                <tr>
                  <td width="40%" colspan="2">
                  	<strong><bean:message key="WapOptoutForm.firstName" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="reqFirstName"/>
                    <bean:write name="WapOptoutForm" property="reqFirstName"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.lastName" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="reqLastName"/>
                    <bean:write name="WapOptoutForm" property="reqLastName"/>
                  </td>
                </tr>
                <tr>
                  <td width="40%" colspan="2">
                  	<strong><bean:message key="WapOptoutForm.streetAddress" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="streetAddress"/>
                    <bean:write name="WapOptoutForm" property="streetAddress"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.reqEmailAddress" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="reqEmailAddress"/>
                    <bean:write name="WapOptoutForm" property="reqEmailAddress"/>
                  </td>
                </tr>
                <tr>
                  <td width="100%" colspan="3">
                  	<strong><bean:message key="WapOptoutForm.city" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="city" />
                    <bean:write name="WapOptoutForm" property="city"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%">
                  	<strong><bean:message key="WapOptoutForm.state" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="state"/>
                    <bean:write name="WapOptoutForm" property="state"/>
                  </td>
                  <td width="25%">
                  	<strong><bean:message key="WapOptoutForm.zipCode" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="zipCode"/>
                    <bean:write name="WapOptoutForm" property="zipCode"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.country" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="country"/>
                    <bean:write name="WapOptoutForm" property="country"/>
                  </td>
                </tr>
                <tr>
                  <td width="100%" colspan="3">
                  	<strong><bean:message key="WapOptoutForm.phoneNumber" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="reqPhoneNumber"/>
                    <bean:write name="WapOptoutForm" property="reqPhoneNumber"/>
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
          <tr>
          <td>
          <table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="0">
            <tr valign="top">
              <th vAlign="top"><strong>Do not Optimize the following URL(s)</strong><br/></th>
            </tr>
            <tr>
              <td width="100%">
				<div id="divBypassURLs">
					<c:set var="count" value="0"/>
					<table id="tblBypassURLs" width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
						<tbody>
							<c:forEach var="url" items="${requestScope.WapOptoutForm.bypassUrls}" varStatus="status">
								<c:if test="${not empty url}">
									<c:set var="count" value="${status.count}"/>
									<tr>
										<td style="padding:3px 0px 3px 0px;">
											<c:out value="${url}"></c:out>
											<input type="hidden" name="bypassUrls" value="<c:out value="${url}"></c:out>"/>
										</td>
									</tr>
								</c:if>
							</c:forEach>																	
						</tbody>
					</table>
				</div>
              </td>
            </tr>
            <c:if test="${count gt 3}">
            <tr>
            	<td width="100%" style="padding:0px"><br/>
				<div id="divAddRow">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td style="text-align: left;">&nbsp;<span id="divExpandCollapse"></span></td>
				      	</tr>
					</table>
				</div>
            	</td>
            </tr>
            </c:if>
          </table>
          </td>
          </tr>          
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
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="5">
				<tr>
                  <th width="100%" colspan="2">
                  	<strong><bean:message key="WapOptoutForm.companyName" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="adminCompanyName"/>
                    <bean:write name="WapOptoutForm" property="adminCompanyName"/>
                  </th>
                </tr>            
                <tr>
                  <td width="40%">
                  	<strong><bean:message key="WapOptoutForm.adminFirstName" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="adminFirstName"/>
                    <bean:write name="WapOptoutForm" property="adminFirstName"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.adminLastName" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="adminLastName" />
                    <bean:write name="WapOptoutForm" property="adminLastName"/>
                  </td>
                </tr>
                <tr>
                  <td width="40%">
                  	<strong><bean:message key="WapOptoutForm.adminPhoneNumber" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="adminPhoneNumber" />
                    <bean:write name="WapOptoutForm" property="adminPhoneNumber"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.adminEmailAddress" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <html:hidden property="adminEmailAddress"/>
                    <bean:write name="WapOptoutForm" property="adminEmailAddress"/>
                  </td>
                </tr>
                
              </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td align="right"><b>(Upon completion of this form, please click here to submit your request to opt-out of Verizon Wireless Optimized View)</b></td>
          </tr>
 
          <tr>
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td width="92%">
                  	<div class="blackBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Back" title="Edit">
                      <div>
                        <div>
                          <div onclick="document.WapOptoutForm.task.value='Back';document.WapOptoutForm.submit();">Edit</div>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td width="8%">
                  	<div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Submit" title="Submit">
                      <div>
                        <div>
                          <div onclick="document.WapOptoutForm.submit();">Submit</div>
                        </div>
                      </div>
                    </div>
                  </td>
                </tr>
              </table></td>
          </tr>
        </table>
      </html:form>
    </div>
  </div>
</div>
<script	language="javascript">
    collapseURLTable();
</script>
