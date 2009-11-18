<%@ page language="java"
         import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*,java.util.*, com.netpace.aims.model.application.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<jsp:useBean id="task" class="java.lang.String" scope="request"/>
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm"
             scope="request"/>

<%ApplicationUpdateForm.setCurrentPage("page1");%>

<script language="javascript">
    <!--

    var supported = (window.Option) ? 1 : 0;

    function truncateLocalTextAreas()
    {
        TruncateTextWithCount(document.forms[0].shortDesc, 'textCountShortDesc', 200);
        TruncateTextWithCount(document.forms[0].longDesc, 'textCountLongDesc', 500);
        TruncateTextWithCount(document.forms[0].devices, 'textDevices', 1000);
       
    }

    function trackCountForTextAreas()
    {
        TrackCount(document.forms[0].shortDesc, 'textCountShortDesc', 200);
        TrackCount(document.forms[0].longDesc, 'textCountLongDesc', 500);
        TrackCount(document.forms[0].devices, 'textDevices', 1000);
        
    }

    <%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
    function disableForVzw()
    {
        document.forms[0].totalEndUsers.disabled = true;
        document.forms[0].noOfUsersAccess.disabled = true;
        document.forms[0].custSupportPhone.disabled = true;
        document.forms[0].custSupportEmail.disabled = true;
        document.forms[0].custSupportHours.disabled = true;

        for (var i = 0; i < document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == "entAppSubCategoryId") {
                document.forms[0].elements[i].disabled = true;
            }
        }

        for (var i = 0; i < document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == "platformDepMode") {
                document.forms[0].elements[i].disabled = true;
            }
        }

        for (var i = 0; i < document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == "ifPrRelease") {
                document.forms[0].elements[i].disabled = true;
            }
        }

    }
    <% } else {}%>

    <%@ include  file="include/appJScript.jsp" %>

    //-->

// calculate the ASCII code of the given character
function CalcKeyCode(aChar) {
  var character = aChar.substring(0,1);
  var code = aChar.charCodeAt(0);
  return code;
}

function checkNumber(val) {
  var strPass = val.value;
  var strLength = strPass.length;
  var lchar = val.value.charAt((strLength) - 1);
  var cCode = CalcKeyCode(lchar);

  /* Check if the keyed in character is a number
     do you want alphabetic UPPERCASE only ?
     or lower case only just check their respective
     codes and replace the 48 and 57 */

  if (cCode < 48 || cCode > 57 ) {
    var myNumber = val.value.substring(0, (strLength) - 1);
    val.value = myNumber;
  }
  return false;
}
</script>
<%@ include file="/common/error.jsp" %>
<div id="contentBox" style="float:left" onmousemove="truncateLocalTextAreas();">
<DIV class="homeColumnTab ">
<%@ include file="include/entAppTabs.jsp" %>
<html:form action="/entApplicationUpdate.do" enctype="multipart/form-data">
<div class="contentbox">
<%@ include file="include/entAppHidden.jsp" %>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>
		<br/>
	</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="ApplicationForm.table.head.app.details"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <th width="50%">
                    <strong><bean:message key="ManageApplicationForm.appTitle"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong>
                    <br/>
                    <html:text styleClass="inputField" property="title" size="40" maxlength="200"/>
                </th>
                <th width="50%">
                    <strong><bean:message key="ManageApplicationForm.appVersion"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                    <br/>
                    <html:text styleClass="inputField" property="version" size="30" maxlength="30"/>
                </th>
            </tr>
            <tr>
                <td width="50%">
                    <strong><bean:message key="ManageApplicationForm.appShortDesc"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong>
                    <br/>
                    <html:textarea styleClass="textareaField" property="shortDesc" rows="3" cols="50"
                                   onkeyup="TrackCount(this,'textCountShortDesc',200)"
                                   onkeypress="LimitText(this,200)"></html:textarea>
                    <br/>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td style="vertical-align:top;padding:0">
                                <bean:message key="ManageApplicationForm.textarea.char.remaining"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </td>
                            <td>
                                <input type="text" name="textCountShortDesc" size="3" value="200"
                                       disabled="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
                <td width="50%">
                    <strong><bean:message key="ManageApplicationForm.appLongDesc"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong>
                    <br/>
                    <html:textarea styleClass="textareaField" property="longDesc" rows="3" cols="40"
                                   onkeyup="TrackCount(this,'textCountLongDesc',500)"
                                   onkeypress="LimitText(this,500)"></html:textarea>
                    <br/>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td style="vertical-align:top;padding:0">
                                <bean:message key="ManageApplicationForm.textarea.char.remaining"
                                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                            </td>
                            <td>
                                <input type="text" name="textCountLongDesc" size="3" value="500"
                                       disabled="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="ApplicationForm.table.head.app.classification"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                    class="requiredText">*</span></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="1" cellspacing="0" cellpadding="5" class="GridGradient">
         <tr>
         	<th>
         		<strong>Devices</strong><br>
         		 <html:textarea styleClass="textareaField" property="devices" rows="3" cols="50"
                                   onkeyup="TrackCount(this,'textDevices',1000)"
                                   onkeypress="LimitText(this,1000);"></html:textarea>
                <br/>
                     <input type="hidden" name="textDevices" size="3" value="1000"
                      disabled="false"/>
                            
                                  
         	</th>
         </tr>
        </table>
    </td>
</tr>
<tr>
<td width="100%">
<table width="100%" cellspacing="0" cellpadding="1">
<tr>
<td width="50%" valign="top">
    <table width="100%" cellspacing="0" cellpadding="1">
        <tr>
            <td>
                <table width="395" height="100%" class="GridGradient" border="0" cellpadding="0"
                       cellspacing="0">
                    <tr>
                        <td class="noPad">
                            <div class="mmBox">
                                <DIV class="headLeftCurveblk"></DIV>
                                <H1><bean:message key="EntApplicationForm.table.head.app.usage.info"
                                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

                                <DIV class="headRightCurveblk"></DIV>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><strong>
                            <bean:message key="EntApplicationForm.totalEndUsers"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                                class="requiredText">*</span>:</strong>
                            <br/>
                            <html:text styleClass="inputField" property="totalEndUsers"
                                       size="7" maxlength="10" onkeyup="javascript: checkNumber(this);"></html:text>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <strong><bean:message key="EntApplicationForm.noOfUserAccess"
                                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                                    class="requiredText">*</span>:</strong>
                            <br/>
                            <html:text styleClass="inputField" property="noOfUsersAccess"
                                       size="7" maxlength="10"  onkeyup="javascript: checkNumber(this);"></html:text>
                        </td>
                    </tr>
                    <tr>
                        <td><br/>
                            <strong><bean:message key="EntApplicationForm.platformDefMode"
                                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                                    class="requiredText">*</span>:</strong>
                            <br/>
                            <html:radio property="platformDepMode" value="1"/><bean:message
                                key="ManageApplicationForm.radio.label.behindCustomerFirewall"
                                bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br/>
                            <html:radio property="platformDepMode" value="2"/><bean:message
                                key="ManageApplicationForm.radio.label.insideCarrierNetwork"
                                bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br/>
                            <html:radio property="platformDepMode" value="3"/><bean:message
                                key="ManageApplicationForm.radio.label.managedFirewall"
                                bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</td>
<td width="50%" valign="top">
    <table width="100%" cellspacing="0" cellpadding="1">
        <tr>
            <td>
                <table width="395" height="100%" class="GridGradient" border="0" cellpadding="0"
                       cellspacing="0">
                    <tr>
                        <td class="noPad">
                            <div class="mmBox">
                                <DIV class="headLeftCurveblk"></DIV>
                                <H1><bean:message key="EntApplicationForm.table.head.cust.support.info"
                                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

                                <DIV class="headRightCurveblk"></DIV>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <strong><bean:message key="ManageApplicationForm.appContactTelephone"
                                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                                    class="requiredText">*</span>:</strong>
                            <br/>
                            <html:text styleClass="inputField" property="custSupportPhone" size="25"
                                       maxlength="50"/>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <strong><bean:message key="ManageApplicationForm.appContactEmail"
                                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                                    class="requiredText">*</span>:</strong>
                            <br/>
                            <html:text styleClass="inputField" property="custSupportEmail" size="25"
                                       maxlength="50"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong><bean:message key="EntApplicationForm.custSupportHours"
                                                  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                                    class="requiredText">*</span>:</strong>
                            <br/>
                            <html:text styleClass="inputField" property="custSupportHours" size="25"
                                       maxlength="100"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</td>
</tr>
</table>
</td>
</tr>


<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="ManageApplicationForm.interested"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
	<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
			<tr>
				<th>
					<html:checkbox property="isInterestedInLBS" value="Y" />
					<strong><bean:message key="ManageApplicationForm.interested.LBS"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong><br/> 
				</th>
			</tr>
			<tr>
				<td>
					<html:checkbox property="isInterestedInBOBO" value="Y" />
					<strong><bean:message key="ManageApplicationForm.interested.BOBO"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong><br/> 
				</td>
			</tr>
			
		</table>
	</td>
</tr>




<tr>
    <td>&nbsp;</td>
</tr>
<tr>
    <td>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1><bean:message key="ApplicationForm.table.head.prrelease"
                              bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>

            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
</tr>
<tr>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
            <tr>
                <th>
                    <strong><bean:message key="ManageApplicationForm.prrelease"
                                          bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span
                            class="requiredText">*</span>:</strong>
                </th>
            </tr>
            <tr>
                <td style="padding-top:0px">
                    <html:radio property="ifPrRelease" value="Y"/><bean:message
                        key="ManageApplicationForm.radio.label.accept"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                    <html:radio property="ifPrRelease" value="N"/><bean:message
                        key="ManageApplicationForm.radio.label.reject"
                        bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td width="100%">
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td>
                    <%@ include file="include/entAppEditButtons.jsp" %>
                    <script language="javascript">
                        trackCountForTextAreas();
                        <%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
                        //disableForVzw();
                        <% } else {}%>
                    </script>

                    <%if (((com.netpace.aims.model.core.AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE)) {%>
                    <html:hidden property="totalEndUsers"/>
                    <html:hidden property="noOfUsersAccess"/>
                    <html:hidden property="platformDepMode"/>
                    <html:hidden property="custSupportPhone"/>
                    <html:hidden property="custSupportEmail"/>
                    <html:hidden property="custSupportHours"/>
                    <html:hidden property="ifPrRelease"/>
                    <html:hidden property="entAppSubCategoryId"/>
                    <% } else {
                    }%>
                </td>
            </tr>
        </table>
    </td>
</tr>
</table>
</div>
</html:form>
</div>
</div>
<logic:present name="noActiveacceptedContract" scope="request">
<script language="javascript">

function noContractPopup()
{
	//alert('No contract poip up.... come here');
	var popupURL = "/aims/renewJmaContract.do?appId="+ <bean:write name="EntApplicationUpdateForm" property="appsId"/>;
        var childWindow = window.open(popupURL,"addRemoveURLWnd","menubar=no,location=no,resizable=no,toolbar=no,width=525,height=400,scrollbars=yes");
        if (childWindow.opener == null) childWindow.opener = self;
        childWindow.focus();
}

noContractPopup();
</script>
</logic:present>