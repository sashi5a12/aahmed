<%@ page import="com.netpace.aims.util.AimsConstants"%>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language="javascript">
    var contactsArray = new Array();
    function AimsContacts() {
        this.contactId = "";
        this.firstName = "";
        this.lastName = "";
        this.title = "";
        this.emailAddress = "";
        this.fax = "";
        this.phone = "";
        this.mobile = "";
    }

    <% int	index	=	0; %>

    <logic:notEmpty name="LoginAllianceContactUpdateForm" property="allContacts" scope="request">
        <logic:iterate id="formContacts" name="LoginAllianceContactUpdateForm"	property="allContacts" scope="request">
            aimsContacts = new AimsContacts();
            aimsContacts.contactId = "<bean:write	name="formContacts"	property="contactId" />";
            aimsContacts.firstName = "<bean:write	name="formContacts"	property="firstName" />";
            aimsContacts.lastName = "<bean:write name="formContacts" property="lastName" />";
            aimsContacts.title = "<bean:write	name="formContacts"	property="title" />";
            aimsContacts.emailAddress = "<bean:write name="formContacts" property="emailAddress" />";
            aimsContacts.fax = "<bean:write name="formContacts" property="fax" />"
            aimsContacts.phone = "<bean:write	name="formContacts"	property="phone" />";
            aimsContacts.mobile = "<bean:write name="formContacts" property="mobile" />";
            contactsArray[<%=index%>] = aimsContacts;
            <%index++;%>
        </logic:iterate>
        <%index	=	0;%>
    </logic:notEmpty>



function makeContactInfo(firstName, lastName, title, phone, emailAddress, mobile, fax) {
    var contactInfo = "";
    contactInfo = firstName+"&nbsp;"+lastName+"<br/>"+
                  title + "<br/>"+
                  emailAddress + "<br/>"+
                  phone + "<br/>"+
                  mobile + "<br/>"+
                  fax + "<br/>";
    return contactInfo;
}//end makeContactInfo

function setContactInfo(contact, spnDisplay, frmFirstName, frmLastName, frmTitle, frmPhone, frmEmailAddress, frmMobile, frmFax) {
    if(contact == null) {
        frmFirstName.value = "";
        frmLastName.value = "";
        frmTitle.value = "";
        frmPhone.value = "";
        frmEmailAddress.value = "";
        frmMobile.value = "";
        frmFax.value = "";
        spnDisplay.innerHTML = makeContactInfo("", "", "", "", "", "", "");
    }
    else {
        frmFirstName.value = contact.firstName;
        frmLastName.value = contact.lastName;
        frmTitle.value = contact.title;
        frmPhone.value = contact.phone;
        frmEmailAddress.value = contact.emailAddress;
        frmMobile.value = contact.mobile;
        frmFax.value = contact.fax;
        spnDisplay.innerHTML = makeContactInfo(contact.firstName, contact.lastName,
                                                contact.title, contact.phone,
                                                contact.emailAddress, contact.mobile, contact.fax);
    }

}//end displayContactInfo

function displayExecContactInformation() {
    var execDisplay = document.getElementById("execDisplay");
    if ((document.forms[0].execContactId.options[document.forms[0].execContactId.selectedIndex].value) == -1) {
        document.forms[0].execContactId.selectedIndex = 0;
        //show empty contact info
        setContactInfo(null, execDisplay,
                        document.forms[0].execContactFirstName, document.forms[0].execContactLastName,
                        document.forms[0].execContactTitle, document.forms[0].execContactPhone,
                        document.forms[0].execContactEmailAddress, document.forms[0].execContactMobile,
                        document.forms[0].execContactFax);
        addContact('executive');
        return false;
    }
    if ((document.forms[0].execContactId.options[document.forms[0].execContactId.selectedIndex].value) != "0") {
        for (var j = 0; j < contactsArray.length; j++) {
            if (contactsArray[j].contactId == document.forms[0].execContactId.options[document.forms[0].execContactId.selectedIndex].value) {
                setContactInfo(contactsArray[j], execDisplay,
                                document.forms[0].execContactFirstName, document.forms[0].execContactLastName,
                                document.forms[0].execContactTitle, document.forms[0].execContactPhone,
                                document.forms[0].execContactEmailAddress, document.forms[0].execContactMobile,
                                document.forms[0].execContactFax);
                break;
            }
        }//end for
    }
    else {
        //show empty contact info
        setContactInfo(null, execDisplay,
                        document.forms[0].execContactFirstName, document.forms[0].execContactLastName,
                        document.forms[0].execContactTitle, document.forms[0].execContactPhone,
                        document.forms[0].execContactEmailAddress, document.forms[0].execContactMobile,
                        document.forms[0].execContactFax);
    }
}//end displayExecContactInformation

function displayBusContactInformation() {
    var busDisplay = document.getElementById("busDisplay");
    if ((document.forms[0].busContactId.options[document.forms[0].busContactId.selectedIndex].value) == -1) {
        document.forms[0].busContactId.selectedIndex = 0;
        //show empty contact info
        setContactInfo(null, busDisplay,
                        document.forms[0].busContactFirstName, document.forms[0].busContactLastName,
                        document.forms[0].busContactTitle, document.forms[0].busContactPhone,
                        document.forms[0].busContactEmailAddress, document.forms[0].busContactMobile,
                        document.forms[0].busContactFax);
        addContact('business');
        return false;
    }
    if ((document.forms[0].busContactId.options[document.forms[0].busContactId.selectedIndex].value) != "0") {
        for (var j = 0; j < contactsArray.length; j++) {
            if (contactsArray[j].contactId == document.forms[0].busContactId.options[document.forms[0].busContactId.selectedIndex].value) {
                setContactInfo(contactsArray[j], busDisplay,
                                document.forms[0].busContactFirstName, document.forms[0].busContactLastName,
                                document.forms[0].busContactTitle, document.forms[0].busContactPhone,
                                document.forms[0].busContactEmailAddress, document.forms[0].busContactMobile,
                                document.forms[0].busContactFax);
            }
        }//end for
    }
    else {
        //show empty contact info
        setContactInfo(null, busDisplay,
                        document.forms[0].busContactFirstName, document.forms[0].busContactLastName,
                        document.forms[0].busContactTitle, document.forms[0].busContactPhone,
                        document.forms[0].busContactEmailAddress, document.forms[0].busContactMobile,
                        document.forms[0].busContactFax);
    }
}//end displayBusContactInformation

function displayMktgContactInformation() {
    var mktgDisplay = document.getElementById("mktgDisplay");
    if ((document.forms[0].mktgContactId.options[document.forms[0].mktgContactId.selectedIndex].value) == -1) {
        document.forms[0].mktgContactId.selectedIndex = 0;
        //show empty contact info
        setContactInfo(null, mktgDisplay,
                        document.forms[0].mktgContactFirstName, document.forms[0].mktgContactLastName,
                        document.forms[0].mktgContactTitle, document.forms[0].mktgContactPhone,
                        document.forms[0].mktgContactEmailAddress, document.forms[0].mktgContactMobile,
                        document.forms[0].mktgContactFax);
        addContact('marketing');
        return false;
    }
    if ((document.forms[0].mktgContactId.options[document.forms[0].mktgContactId.selectedIndex].value) != "0") {
        for (var j = 0; j < contactsArray.length; j++) {
            if (contactsArray[j].contactId == document.forms[0].mktgContactId.options[document.forms[0].mktgContactId.selectedIndex].value) {
                setContactInfo(contactsArray[j], mktgDisplay,
                        document.forms[0].mktgContactFirstName, document.forms[0].mktgContactLastName,
                        document.forms[0].mktgContactTitle, document.forms[0].mktgContactPhone,
                        document.forms[0].mktgContactEmailAddress, document.forms[0].mktgContactMobile,
                        document.forms[0].mktgContactFax);
            }
        }//end for
    }
    else {
        //show empty contact info
        setContactInfo(null, mktgDisplay,
                        document.forms[0].mktgContactFirstName, document.forms[0].mktgContactLastName,
                        document.forms[0].mktgContactTitle, document.forms[0].mktgContactPhone,
                        document.forms[0].mktgContactEmailAddress, document.forms[0].mktgContactMobile,
                        document.forms[0].mktgContactFax);
    }
}//end displayMktgContactInformation

function displayTechContactInformation() {
    var techDisplay = document.getElementById("techDisplay");
    if ((document.forms[0].techContactId.options[document.forms[0].techContactId.selectedIndex].value) == -1) {
        document.forms[0].techContactId.selectedIndex = 0;
        //show empty contact info
        setContactInfo(null, techDisplay,
                        document.forms[0].techContactFirstName, document.forms[0].techContactLastName,
                        document.forms[0].techContactTitle, document.forms[0].techContactPhone,
                        document.forms[0].techContactEmailAddress, document.forms[0].techContactMobile,
                        document.forms[0].techContactFax);
        addContact('technical');
        return false;
    }
    if ((document.forms[0].techContactId.options[document.forms[0].techContactId.selectedIndex].value) != "0") {
        for (var j = 0; j < contactsArray.length; j++) {
            if (contactsArray[j].contactId == document.forms[0].techContactId.options[document.forms[0].techContactId.selectedIndex].value) {
                setContactInfo(contactsArray[j], techDisplay,
                        document.forms[0].techContactFirstName, document.forms[0].techContactLastName,
                        document.forms[0].techContactTitle, document.forms[0].techContactPhone,
                        document.forms[0].techContactEmailAddress, document.forms[0].techContactMobile,
                        document.forms[0].techContactFax);
            }
        }//end for
    }
    else {
        //show empty contact info
        setContactInfo(null, techDisplay,
                        document.forms[0].techContactFirstName, document.forms[0].techContactLastName,
                        document.forms[0].techContactTitle, document.forms[0].techContactPhone,
                        document.forms[0].techContactEmailAddress, document.forms[0].techContactMobile,
                        document.forms[0].techContactFax);
    }
}//end displayTechContactInformation

function addContact(type) {
    var url = "<c:out value='${pageContext.request.contextPath}'/>/contactsSetup.do?&task=createFormPopup&isPopup=true&cType=" + type +
              "&parentPageType=<c:out value='${LoginAllianceContactUpdateForm.parentPageType}'/>" +
              "&parentPath=<c:out value='${LoginAllianceContactUpdateForm.parentPath}'/>";
    var win = window.open(url, "addContact", "resizable=0,width=870,height=450,scrollbars=1,screenX=100,left=150,screenY=30,top=30,status=0,titlebar=0");
    win.focus();
}//end addContact()

</script>

<logic:present name="<%=AimsConstants.REQUEST_SHOW_LOGIN_CONTENT_ERROR%>">
    <div class="errorMsg">
        <h1><bean:write name="<%=AimsConstants.REQUEST_LOGIN_CONTENT_ERROR_MESSAGE%>" scope="request"/></h1>
    </div>
</logic:present>

<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
    <div class="homeColumnTab ">
        <div>&nbsp;</div>
        <html:form action="/loginAllianceContactUpdateAction">
            <html:hidden property="task" value="update"/>
            <html:hidden property="parentPageType"/>
            <html:hidden property="parentPath"/>

            <div class="lBox">
                <div class="headLeftCurveblk"></div>
                <h1>Alliance Contact Information</h1>
                <div class="headRightCurveblk"></div>
                <div class="contentbox">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
                                    <tr>
                                        <th>Initially, and every 180 days, we like to verify your contact information. Please verify your information and use the Submit button to the bottom right to make the appropriate changes. Once verified, you may access the other areas of the site.</th>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </div>
                <%-- end contentBox Section 1--%>

                <div>&nbsp;</div>
                <div class="headLeftCurveblk"></div>
                <h1>Current Contacts</h1>
                <div class="headRightCurveblk"></div>
                <div class="contentbox">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
                                    <tr>
                                        <th width="25%" style="vertical-align:top;">
                                            <strong><bean:message key="AllianceContactInfoForm.execContact" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>&nbsp;<span class="requiredText">*</span>:
                                        </th>
                                        <th width="25%" style="vertical-align:top;">
                                            <span id="execDisplay">
                                                <bean:write name="LoginAllianceContactUpdateForm" property="execContactFirstName"/>&nbsp;<bean:write name="LoginAllianceContactUpdateForm" property="execContactLastName"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="execContactTitle"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="execContactEmailAddress"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="execContactPhone"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="execContactMobile"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="execContactFax"/><br/>
                                            </span>
                                            <html:hidden property="execContactFax"/>
                                            <html:hidden property="execContactFirstName"/>
                                            <html:hidden property="execContactLastName"/>
                                            <html:hidden property="execContactTitle"/>
                                            <html:hidden property="execContactEmailAddress"/>
                                            <html:hidden property="execContactPhone"/>
                                            <html:hidden property="execContactMobile"/>
                                        </th>
                                        <th width="30%" style="vertical-align:top;">
                                            <strong>To update any of this information, <br/> or add a new contact ... </strong>
                                        </th>
                                        <th width="25%" style="vertical-align:top;">
                                            <html:select property="execContactId" size="1" styleClass="selectField" onchange="displayExecContactInformation()" style="width:90">
                                                <html:option value="0">
                                                    <bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                                                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                                                </html:option>
                                                <html:option value="-1">Create New Contact</html:option>
                                                <logic:iterate id="formContacts" name="LoginAllianceContactUpdateForm" property="allContacts"
                                                               type="com.netpace.aims.model.core.AimsContact" scope="request">
                                                    <html:option value="<%=formContacts.getContactId().toString()%>">
                                                        <bean:write name="formContacts" property="firstName"/>&nbsp; <bean:write name="formContacts" property="lastName"/>
                                                    </html:option>
                                                </logic:iterate>
                                            </html:select>
                                        </th>
                                    </tr>
                                    <%-- end exec contact --%>

                                    <tr>
                                        <td style="vertical-align:top;">
                                            <strong><bean:message key="AllianceContactInfoForm.businessContact" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>&nbsp;<span class="requiredText">*</span>:
                                        </td>
                                        <td style="vertical-align:top;">
                                            <span id="busDisplay">
                                                <bean:write name="LoginAllianceContactUpdateForm" property="busContactFirstName"/>&nbsp;<bean:write name="LoginAllianceContactUpdateForm" property="busContactLastName"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="busContactTitle"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="busContactEmailAddress"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="busContactPhone"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="busContactMobile"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="busContactFax"/><br/>
                                            </span>
                                            <html:hidden property="busContactFirstName"/>
                                            <html:hidden property="busContactLastName"/>
                                            <html:hidden property="busContactTitle"/>
                                            <html:hidden property="busContactEmailAddress"/>
                                            <html:hidden property="busContactPhone"/>
                                            <html:hidden property="busContactMobile"/>
                                            <html:hidden property="busContactFax"/>
                                        </td>
                                        <td style="vertical-align:top;">
                                            <strong>To update any of this information, <br/> or add a new contact ... </strong>
                                        </td>
                                        <td style="vertical-align:top;">
                                            <html:select property="busContactId" size="1" styleClass="selectField" onchange="displayBusContactInformation()" style="width:90">
                                                <html:option value="0">
                                                    <bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                                                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                                                </html:option>
                                                <html:option value="-1">Create New Contact</html:option>
                                                <logic:iterate id="formContacts" name="LoginAllianceContactUpdateForm" property="allContacts"
                                                               type="com.netpace.aims.model.core.AimsContact" scope="request">
                                                    <html:option value="<%=formContacts.getContactId().toString()%>">
                                                        <bean:write name="formContacts" property="firstName"/>&nbsp; <bean:write name="formContacts" property="lastName"/>
                                                    </html:option>
                                                </logic:iterate>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <%-- end bus contact --%>

                                    <tr>
                                        <td style="vertical-align:top;">
                                            <strong><bean:message key="AllianceContactInfoForm.mktgContact" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>&nbsp;<span class="requiredText">*</span>:
                                        </td>
                                        <td style="vertical-align:top;">
                                            <span id="mktgDisplay">
                                                <bean:write name="LoginAllianceContactUpdateForm" property="mktgContactFirstName"/>&nbsp;<bean:write name="LoginAllianceContactUpdateForm" property="mktgContactLastName"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="mktgContactTitle"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="mktgContactEmailAddress"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="mktgContactPhone"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="mktgContactMobile"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="mktgContactFax"/><br/>
                                            </span>
                                            <html:hidden property="mktgContactFirstName"/>
                                            <html:hidden property="mktgContactLastName"/>
                                            <html:hidden property="mktgContactTitle"/>
                                            <html:hidden property="mktgContactEmailAddress"/>
                                            <html:hidden property="mktgContactPhone"/>
                                            <html:hidden property="mktgContactMobile"/>
                                            <html:hidden property="mktgContactFax"/>
                                        </td>
                                        <td style="vertical-align:top;">
                                            <strong>To update any of this information, <br/> or add a new contact ... </strong>
                                        </td>
                                        <td style="vertical-align:top;">
                                            <html:select property="mktgContactId" size="1" styleClass="selectField" onchange="displayMktgContactInformation()" style="width:90">
                                                <html:option value="0">
                                                    <bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                                                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                                                </html:option>
                                                <html:option value="-1">Create New Contact</html:option>
                                                <logic:iterate id="formContacts" name="LoginAllianceContactUpdateForm" property="allContacts"
                                                               type="com.netpace.aims.model.core.AimsContact" scope="request">
                                                    <html:option value="<%=formContacts.getContactId().toString()%>">
                                                        <bean:write name="formContacts" property="firstName"/>&nbsp; <bean:write name="formContacts" property="lastName"/>
                                                    </html:option>
                                                </logic:iterate>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <%-- end marketing contact --%>

                                    <tr>
                                        <td style="vertical-align:top;">
                                            <strong><bean:message key="AllianceContactInfoForm.techContact" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/></strong>&nbsp;<span class="requiredText">*</span>:
                                        </td>
                                        <td style="vertical-align:top;">
                                            <span id="techDisplay">
                                                <bean:write name="LoginAllianceContactUpdateForm" property="techContactFirstName"/>&nbsp;<bean:write name="LoginAllianceContactUpdateForm" property="techContactLastName"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="techContactTitle"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="techContactEmailAddress"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="techContactPhone"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="techContactMobile"/><br/>
                                                <bean:write name="LoginAllianceContactUpdateForm" property="techContactFax"/><br/>
                                            </span>
                                            <html:hidden property="techContactFirstName"/>
                                            <html:hidden property="techContactLastName"/>
                                            <html:hidden property="techContactTitle"/>
                                            <html:hidden property="techContactEmailAddress"/>
                                            <html:hidden property="techContactPhone"/>
                                            <html:hidden property="techContactMobile"/>
                                            <html:hidden property="techContactFax"/>
                                        </td>
                                        <td style="vertical-align:top;">
                                            <strong>To update any of this information, <br/> or add a new contact ... </strong>
                                        </td>
                                        <td style="vertical-align:top;">
                                            <html:select property="techContactId" size="1" styleClass="selectField" onchange="displayTechContactInformation()" style="width:90">
                                                <html:option value="0">
                                                    <bean:message key="AllianceContactInfoForm.pleaseSelectOne"
                                                                  bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
                                                </html:option>
                                                <html:option value="-1">Create New Contact</html:option>
                                                <logic:iterate id="formContacts" name="LoginAllianceContactUpdateForm" property="allContacts"
                                                               type="com.netpace.aims.model.core.AimsContact" scope="request">
                                                    <html:option value="<%=formContacts.getContactId().toString()%>">
                                                        <bean:write name="formContacts" property="firstName"/>&nbsp; <bean:write name="formContacts" property="lastName"/>                                                        
                                                    </html:option>
                                                </logic:iterate>
                                            </html:select>
                                        </td>
                                    </tr>
                                    <tr><td colspan="4">
                                        <div class="redBtn" style="margin-left:10px;margin-right:10px;float:right;" title="Submit">
                                            <div>
                                                <div>
                                                    <div onClick="document.forms[0].submit();">Submit</div>
                                                </div>
                                            </div>
                                        </div>
                                    </td></tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </div>
                <%-- end contentBox div Section 2--%>
            </div>
        </html:form>
    </div>
</div>
<script language="javascript">    
    displayExecContactInformation();
    displayBusContactInformation();
    displayMktgContactInformation();
    displayTechContactInformation();
</script>
