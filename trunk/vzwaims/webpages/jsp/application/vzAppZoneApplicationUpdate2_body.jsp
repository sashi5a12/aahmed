<%@ page import="com.netpace.aims.util.AimsConstants"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>

<%@ include  file="include/vzAppZoneVariables.jsp" %>

<script	language="javascript">

	var	contactsArray	=	new	Array();
    var	supported	=	(window.Option)	?	1	:	0;
	function AimsContacts()
	{
		this.contactId = "";
		this.firstName = "";
		this.lastName	=	"";
		this.title = "";
		this.emailAddress	=	"";
		this.phone = "";
		this.mobile	=	"";
	}

<%
	int	index	=	0;
	%>

	<logic:iterate id="formContacts" name="VZAppZoneApplicationUpdateForm"	property="allContacts" scope="request">
		aimsContacts = new AimsContacts();
		aimsContacts.contactId = "<bean:write	name="formContacts"	property="contactId" />";
		aimsContacts.firstName = "<bean:write	name="formContacts"	property="firstName" />";;
		aimsContacts.lastName	=	"<bean:write name="formContacts" property="lastName" />";
		aimsContacts.title = "<bean:write	name="formContacts"	property="title" />";
		aimsContacts.emailAddress	=	"<bean:write name="formContacts" property="emailAddress" />";
		aimsContacts.phone = "<bean:write	name="formContacts"	property="phone" />";
		aimsContacts.mobile	=	"<bean:write name="formContacts" property="mobile" />";
		contactsArray[<%=index%>]	=	aimsContacts;
		<%index++;%>
	</logic:iterate>

	function displayContactsInformation()	{
		 if	(!supported) {
			 alert("Feature	not	supported");
		 }

		if ((document.forms[0].aimsContactId.options[document.forms[0].aimsContactId.selectedIndex].value) !=	"0")
		{
			for	(var j = 0;	j	<	contactsArray.length;	j++)
			{
				if (contactsArray[j].contactId ==	document.forms[0].aimsContactId.options[document.forms[0].aimsContactId.selectedIndex].value)
				{
					document.forms[0].contactFirstName.value = contactsArray[j].firstName;
					document.forms[0].contactLastName.value	=	contactsArray[j].lastName;
					document.forms[0].contactTitle.value = contactsArray[j].title;
					document.forms[0].contactEmail.value = contactsArray[j].emailAddress;
					document.forms[0].contactPhone.value = contactsArray[j].phone;
					document.forms[0].contactMobile.value	=	contactsArray[j].mobile;
				}
			}
		}
		else
		{
			document.forms[0].contactFirstName.value = "<bean:write	name="VZAppZoneApplicationUpdateForm" property="contactFirstName" scope="request"/>";
			document.forms[0].contactLastName.value	=	"<bean:write name="VZAppZoneApplicationUpdateForm"	property="contactLastName" scope="request"/>";
			document.forms[0].contactTitle.value = "<bean:write	name="VZAppZoneApplicationUpdateForm" property="contactTitle" scope="request"/>";
			document.forms[0].contactEmail.value = "<bean:write	name="VZAppZoneApplicationUpdateForm" property="contactEmail" scope="request"/>";
			document.forms[0].contactPhone.value = "<bean:write	name="VZAppZoneApplicationUpdateForm" property="contactPhone" scope="request"/>";
			document.forms[0].contactMobile.value	=	"<bean:write name="VZAppZoneApplicationUpdateForm"	property="contactMobile" scope="request"/>";
		}
	}

    function disableFieldsForVZWUser() {
        var frm = document.forms[0];
        if(frm.aimsContactId) {
            frm.aimsContactId.disabled = true;
        }
    }
</script>
<%@ include  file="include/vzAppZoneAppJScript.jsp" %>
<%@ include  file="appTabMessageHeader.jsp" %>


<div id="contentBox" style="float:left">
    <div class="homeColumnTab">
        <%@ include  file="include/vzAppZoneTabs.jsp" %>
        <div class="contentbox">
            <html:form action="/vzAppZoneApplicationUpdate.do"	enctype="multipart/form-data">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                    <tr width="100%">
                        <td width="50%" valign="top"><table width="375" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="noPad"><div class="mmBox">
                                    <DIV class="headLeftCurveblk"></DIV>
                                    <H1><bean:message key="ApplicationForm.table.head.app.files"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                    <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <%-- commented, contentLandingScreenShot no need to show
                                <tr>
                                    <th><strong>Content Landing Page Sample Screen Shot (JPEG/GIF Format):</strong></th>
                                </tr>
                                <tr>
                                    <td>
                                        <html:file styleClass="inputField" size="30" property="contentLandingScreenShot"/><br/>
                                        <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="contentLandingScreenShotFileName" scope="request">
                                            <logic:equal name="VZAppZoneApplicationUpdateForm" property="contentLandingScreenShotTempFileId"	scope="request"	value="0">
                                                <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppZoneContentLandingPage&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
                                            </logic:equal>
                                            <logic:notEqual	name="VZAppZoneApplicationUpdateForm"	property="contentLandingScreenShotTempFileId" scope="request" value="0">
                                                <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="contentLandingScreenShotTempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write	name="VZAppZoneApplicationUpdateForm"	property="contentLandingScreenShotFileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                            --%>
                            <tr>
                                <th><strong>Presentation:</strong></th>
                            </tr>
                            <tr>
                                <td>
                                    <html:file styleClass="inputField" size="30" property="presentation"/><br/>
                                    <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="presentationFileName" scope="request">
                                        <logic:equal name="VZAppZoneApplicationUpdateForm" property="presentationTempFileId"	scope="request"	value="0">
                                            <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppZonePresentation&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="appsId"	/>"	class="a"	target="_blank">
                                        </logic:equal>
                                        <logic:notEqual	name="VZAppZoneApplicationUpdateForm"	property="presentationTempFileId" scope="request" value="0">
                                            <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="presentationTempFileId" />" class="a" target="_blank">
                                        </logic:notEqual>
                                            <bean:write	name="VZAppZoneApplicationUpdateForm"	property="presentationFileName"/>
                                            </a>
                                    </logic:notEmpty>
                                </td>
                            </tr>
				            <tr><td>&nbsp;</td></tr>
                            <tr>
                                <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1><bean:message   key="ManageApplicationForm.appTechContact"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <html:select styleClass="selectField" property="aimsContactId"   size="1" onchange="displayContactsInformation()">
                                    <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                    <logic:iterate id="formContacts" name="VZAppZoneApplicationUpdateForm"   property="allContacts" type="com.netpace.aims.model.core.AimsContact"   scope="request">
										<html:option value="<%=formContacts.getContactId().toString()%>">
						                	<bean:write	name="formContacts"	property="firstName" />
						                	<bean:write	name="formContacts"	property="lastName" /> 
						                </html:option>
                                    </logic:iterate>
                                    </html:select>

                                </th>
                            </tr>
                            <tr>
                                <td width="100%" style="padding: 0px; margin:0px;"><table border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="50%"><strong><bean:message   key="ManageApplicationForm.appContactFirstName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        <td width="50%"><strong><bean:message   key="ManageApplicationForm.appContactLastName"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                    </tr>
                                    <tr>
                                        <td width="50%"><html:text styleClass="inputField" property="contactFirstName" size="30" maxlength="50" disabled="true" /></td>
                                        <td width="50%"><html:text styleClass="inputField" property="contactLastName"   size="30" maxlength="50" disabled="true" /></td>
                                    </tr>
                                    <tr>
                                        <td width="50%"><strong><bean:message   key="ManageApplicationForm.appContactTitle" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        <td width="50%"><strong><bean:message   key="ManageApplicationForm.appContactEmail" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                    </tr>
                                    <tr>
                                        <td width="50%"><html:text styleClass="inputField" property="contactTitle"  size="30" maxlength="50" disabled="true" /></td>
                                        <td width="50%"><html:text styleClass="inputField" property="contactEmail"  size="30" maxlength="100" disabled="true" /></td>
                                    </tr>
                                    <tr>
                                        <td width="50%"><strong><bean:message   key="ManageApplicationForm.appContactTelephone" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        <td width="50%"><strong><bean:message   key="ManageApplicationForm.appContactMobile"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                    </tr>
                                    <tr>
                                        <td width="50%"><html:text styleClass="inputField" property="contactPhone"  size="30" maxlength="50" disabled="true" /></td>
                                        <td width="50%"><html:text styleClass="inputField" property="contactMobile" size="30" maxlength="50" disabled="true" /></td>
                                    </tr>
                                </table></td>
                            </tr>
                        </table></td>
                        <td width="50%" valign=top>
                            <table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0" >
                                <tr>
                                    <td class="noPad" colspan="2"><div class="mmBox">
                                        <DIV class="headLeftCurveblk"></DIV>
                                        <H1>Information For Premium Content Only</H1>
                                        <DIV class="headRightCurveblk"></DIV>
                                    </div></td>
                                </tr>
                                <%
                                    boolean disableSubscriptionBillingPriecePoint = true;
                                    boolean disableOnetimeBillingPriecePoint = true;
                                %>
                                <tr>
                                    <th><strong>Subscription Billing (Monthly):</strong></th>
                                    <th><strong>Subscription Billing Pricepoint&nbsp;<span class="requiredText">*</span>:</strong></th>
                                </tr>
                                <tr>
                                    <td>
                                        <html:radio disabled="true" property="subscriptionBillingMonthly" value="Y" onclick="javascript:document.forms[0].subscriptionBillingPricePoint.disabled=false;"/>Yes &nbsp;
                                        <html:radio disabled="true" property="subscriptionBillingMonthly" value="N" onclick="javascript:document.forms[0].subscriptionBillingPricePoint.disabled=true;"/>No
                                        <%--    disable subscription billing fields
                                            <logic:equal name="VZAppZoneApplicationUpdateForm" property="subscriptionBillingMonthly" value="Y">
                                                <% disableSubscriptionBillingPriecePoint = false; %>
                                            </logic:equal>
                                        --%>
                                    </td>
                                    <td><html:text styleClass="inputField" property="subscriptionBillingPricePoint"  size="30" maxlength="10" disabled="<%=disableSubscriptionBillingPriecePoint%>"/></td>
                                </tr>
                                <tr><td colspan="2"><b><i>Note: Subscription Billing is not being offered at this time</i></b></td></tr>
                                <tr><td colspan="2">&nbsp;</td></tr>
                                <tr>
                                    <td width="50%"><strong>Onetime Billing:</strong></td>
                                    <td width="50%"><strong>Onetime Billing Pricepoint&nbsp;<span class="requiredText">*</span>:</strong></td>
                                </tr>
                                <tr>
                                    <td>
                                        <html:radio property="oneTimeBilling" value="Y" onclick="javascript:document.forms[0].oneTimeBillingPricePoint.disabled=false;"/>Yes &nbsp;
                                        <html:radio property="oneTimeBilling" value="N" onclick="javascript:document.forms[0].oneTimeBillingPricePoint.disabled=true;"/>No
                                        <logic:equal name="VZAppZoneApplicationUpdateForm" property="oneTimeBilling" value="Y">
                                            <% disableOnetimeBillingPriecePoint = false; %>
                                        </logic:equal>
                                    </td>
                                    <td>
                                        <html:text styleClass="inputField" property="oneTimeBillingPricePoint"  size="30" maxlength="10" disabled="<%=disableOnetimeBillingPriecePoint%>" />
                                    </td>
                                </tr>
                                <tr><td>&nbsp;</td></tr>
                                <tr>
                                    <td class="noPad" colspan="2"><div class="mmBox">
                                        <DIV class="headLeftCurveblk"></DIV>
                                        <H1>Additional Information</H1>
                                        <DIV class="headRightCurveblk"></DIV>
                                    </div></td>
                                </tr>
                                <tr>
                                    <th><strong>Community/Chat/UGC&nbsp;<span class="requiredText">*</span>:</strong></th>
                                    <th>
                                        <html:radio property="communityChatUgc" value="Y" />Yes &nbsp;
                                        <html:radio property="communityChatUgc" value="N" />No
                                    </th>
                                </tr>
                                <tr>
                                    <td><strong>Contest/Sweepstakes&nbsp;<span class="requiredText">*</span>:</strong></td>
                                    <td>
                                        <html:radio property="contentSweekstakes" value="Y" />Yes &nbsp;
                                        <html:radio property="contentSweekstakes" value="N" />No
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                      <td><%@ include  file="include/vzAppZoneEditButtons.jsp" %></td>
                    </tr>
                    <tr>
                      <td><%@ include  file="appTabMessageFooter.jsp" %></td>
                    </tr>
                </table>

                <script	language="javascript">
                    displayContactsInformation();
                    <% if(isVerizonUser) { %>
                        disableFieldsForVZWUser();
                    <% }//end if isVerizonUser %>
                    <%-- if application is locked, disable page fields--%>
                    <%  if(isLocked) {  %>
                            function lockApplicationPage2() {
                                var frm = document.forms[0];
                                <%-- commented, contentLandingScreenShot no need to show
                                    frm.contentLandingScreenShot.disabled = true;
                                --%>
                                frm.presentation.disabled = true;
                                frm.aimsContactId.disabled = true;
                                frm.subscriptionBillingMonthly[0].disabled = true;
                                frm.subscriptionBillingMonthly[1].disabled = true;
                                frm.subscriptionBillingPricePoint.disabled = true;
                                //frm.oneTimeBilling.disabled = true;
                                frm.oneTimeBilling[0].disabled = true;
                                frm.oneTimeBilling[1].disabled = true;
                                frm.oneTimeBillingPricePoint.disabled = true;

                                frm.communityChatUgc[0].disabled = true;
                                frm.communityChatUgc[1].disabled = true;
                                frm.contentSweekstakes[0].disabled = true;
                                frm.contentSweekstakes[1].disabled = true;
                            }//end lockApplicationPage2
                            lockApplicationPage2();
                    <%  }//end isLocked   %>
                </script>

                <% if(isVerizonUser) { %>
                    <html:hidden property="aimsContactId"	/>
                <% }//end if isVerizonUser %>
                <%@ include  file="include/vzAppZoneHidden.jsp" %>
            </html:form>
        </div>
    </div>
</div>
