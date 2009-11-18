<%@	page language="java" import="com.netpace.aims.util.*"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<script type="text/javascript">
    function print()
    {
        var url="/aims/wapOptout.do?task=printPopup";
        var wind=window.open (url,"print","resizable=1,width=850,height=600,scrollbars=1,screenX=100,left=100,screenY=30,top=20");
        wind.focus();
    }   

</script>
<div id="contentBox" style="float: left">
  <div class="homeColumnTab">
    <div class="contentbox">
      <html:form action="/wapOptout" method="post">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
		 <tr id="printBtnRow" style="display: none" class='noprint'>
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
                  <td><div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Print" title="Print">
                      <div>
                        <div>
                          <div onclick="window.print();//">Print</div>
                        </div>
                      </div>
                    </div></td>
                </tr>
              </table></td>
          </tr>        
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="WapOptoutForm.secHeading.thankYou" bundle="com.netpace.aims.action.WAP_OPTOUT"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="5">
                <tr><th width="100%">Thanks for your submission.</th></tr>
                <tr><td>Your request was submitted on <strong><bean:write name="WapOptoutForm" property="submitDate"/></strong></td></tr>
                <tr><td>For tracking purposes, please note your submittal number: <strong><bean:write name="WapOptoutForm" property="submittalNumber"/></strong></td></tr>
                <tr><td>For a copy of your submitted information, click on Print.</td></tr>                                
              </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message key="WapOptoutForm.secHeading.optoutPage" bundle="com.netpace.aims.action.WAP_OPTOUT"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="5">
                <tr>
                  <th width="100%" colspan="3">
                  	<strong><bean:message key="WapOptoutForm.companyName" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="companyName"/>
                  </th>
                </tr>
                <tr>
                  <td width="40%" colspan="2">
                  	<strong><bean:message key="WapOptoutForm.firstName" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="reqFirstName"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.lastName" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="reqLastName"/>
                  </td>
                </tr>
                <tr>
                  <td width="40%" colspan="2">
                  	<strong><bean:message key="WapOptoutForm.streetAddress" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="streetAddress"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.reqEmailAddress" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="reqEmailAddress"/>
                  </td>
                </tr>
                <tr>
                  <td width="100%" colspan="3">
                  	<strong><bean:message key="WapOptoutForm.city" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="city"/>
                  </td>
                </tr>
                <tr>
                  <td width="15%">
                  	<strong><bean:message key="WapOptoutForm.state" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="state"/>
                  </td>
                  <td width="25%">
                  	<strong><bean:message key="WapOptoutForm.zipCode" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="zipCode"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.country" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="country"/>
                  </td>
                </tr>
                <tr>
                  <td width="100%" colspan="3">
                  	<strong><bean:message key="WapOptoutForm.phoneNumber" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
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
	              <th vAlign="top">
	              	<strong>Bypass Requested URL</strong><br/>
	              </th>
	            </tr>
	            <tr>
	              <td width="100%">
					<div id="divBypassURLs">
						<table id="tblBypassURLs" width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
							<tbody>
								<c:forEach var="url" items="${requestScope.WapOptoutForm.bypassUrls}" varStatus="status">
									<tr><td style="padding:3px 0px 3px 0px;"><c:out value="${url}"></c:out></td>
									</tr>
								</c:forEach>																	
							</tbody>
						</table>
					</div>              
	              </td>
	            </tr>
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
                    <bean:write name="WapOptoutForm" property="adminCompanyName"/>
                  </th
                </tr>            
                <tr>
                  <td width="40%">
                  	<strong><bean:message key="WapOptoutForm.firstName" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="adminFirstName"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.lastName" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="adminLastName"/>
                  </td>
                </tr>
                <tr>
                  <td width="40%">
                  	<strong><bean:message key="WapOptoutForm.phoneNumber" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="adminPhoneNumber"/>
                  </td>
                  <td width="60%">
                  	<strong><bean:message key="WapOptoutForm.adminEmailAddress" bundle="com.netpace.aims.action.WAP_OPTOUT"/></strong><br/>
                    <bean:write name="WapOptoutForm" property="adminEmailAddress"/>
                  </td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr id="btnRow">
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <tr>
				<td width="93%">
                  	<div class="blackBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" title="Submit Another Request">
                     	<div><div><div onclick="window.location='/aims/wapOptout.do'">Submit Another Request</div></div></div>
                   	</div>
                  </td>                
                  <td width="7%"><div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Print" title="Print">
                      <div>
                        <div>
                          <div onclick="javascript:print();//">Print</div>
                        </div>
                      </div>
                    </div></td>
                </tr>
              </table></td>
          </tr>
        </table>
      </html:form>
    </div>
  </div>
</div>
