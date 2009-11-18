<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<script language="javascript">

    var placeHolderArray = new Array();


    function AimsPlaceHolder()
    {
        this.placeHolderId = "";
        this.placeHolderDisplayName = "";
        this.eventId = "";
    }

    <%
     int	index	=	0;
     %>

    <logic:iterate id="event" name="SystemNotificationForm" property="eventList">
    <logic:iterate id="placeHolder" name="event" property="eventPlaceHolders">
    aimsPlaceHolder = new AimsPlaceHolder();
    aimsPlaceHolder.placeHolderId = "<bean:write	name="placeHolder" property="placeHolderId"	/>";
    aimsPlaceHolder.placeHolderDisplayName = "<bean:write	name="placeHolder" property="placeHolderDisplayName"	/>";
    aimsPlaceHolder.eventId = "<bean:write	name="event" property="eventId"	/>";
    placeHolderArray[<%=index%>] = aimsPlaceHolder;
    <%index++;%>
    </logic:iterate>
    </logic:iterate>

    var supported = (window.Option) ? 1 : 0;

    function changePlaceHolders() {
        if (!supported) {
            alert("Feature	not	supported");
        }
        var options = document.forms[0].placeHolderId.options;
        for (var i = options.length - 1; i > 0; i--) {
            options[i] = null;
        }

        options[0] = new Option("<bean:message key="message.lable.selectOne"/>", "0");
        var k = 1;
        var m = 0;

        for (var j = 0; j < placeHolderArray.length; j++) {

            if (placeHolderArray[j].eventId == document.forms[0].eventId.options[document.forms[0].eventId.selectedIndex].value)
            {
                options[k] = new Option(placeHolderArray[j].placeHolderDisplayName, placeHolderArray[j].placeHolderDisplayName);
                if (placeHolderArray[j].placeHolderDisplayName == "<bean:write	name="SystemNotificationForm" property="placeHolderId" scope="request"/>")
                    m = k;
                k++;
            }
        }

        options[m].selected = true;
    }


    function appendPlaceHolder()
    {
        var list = document.forms[0].placeHolderId;
        var listValue = list.options[list.selectedIndex].value;

        if (listValue != null && listValue != "0")
        {
            var text = document.forms[0].content.value;
            text += "{" + listValue + "}";
            document.forms[0].content.value = text;
        }
    }

</script>

<%@ include file="/common/error.jsp" %>


<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">


<html:form action="/systemNotificationInsertUpdate" enctype="multipart/form-data">

<logic:match name="SystemNotificationForm" property="task" scope="request" value="create">
    <html:hidden property="task" value="create"/>
</logic:match>
<logic:match name="SystemNotificationForm" property="task" scope="request" value="edit">
    <html:hidden property="task" value="edit"/>
</logic:match>

    <html:hidden property="systemNotificationId"/>
    <html:hidden property="createdBy"/>
    <html:hidden property="createdDate"/>


<DIV class="headLeftCurveblk"></DIV>
<H1>
    <logic:match name="SystemNotificationForm" property="task" scope="request" value="create">
        <bean:message key="SystemNotificationForm.CreateHeading"
                      bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
    </logic:match>
    <logic:match name="SystemNotificationForm" property="task" scope="request" value="edit">
        <bean:message key="SystemNotificationForm.EditHeading"
                      bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
    </logic:match>
</H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">

<table width="100%" cellspacing="0" cellpadding="5" class="GridGradient">
<tr>
    <th>
        <strong><bean:message key="SystemNotificationForm.Subject"
                      bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
        <span class="requiredText">*</span>:</strong>
    </th>
    <th>
        <html:text property="subject" size="75" maxlength="50" styleClass="inputField"/>
    </th>
</tr>
<tr>
    <td>
        <strong><bean:message key="SystemNotificationForm.Desc"
                      bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
        :</strong>
    </td>
    <td>
        <html:textarea property="description" rows="5" cols="50" styleClass="textareaField"/>
    </td>
</tr>

<tr>
    <td width="25%">
        <strong><bean:message key="SystemNotificationForm.Attachment"
                      bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
        :</strong>
    </td>
    <td>
        <html:file name="SystemNotificationForm" property="emailAttachment" size="50"  styleClass="inputField"/>
        <br/>

        <a href="emailAttResource.do?systemNotificationId=<bean:write name="SystemNotificationForm" property="systemNotificationId" />" class="a" target="_blank">
            <bean:write name="SystemNotificationForm" property="emailAttachmentName"/>
        </a>
    </td>
</tr>

<tr>
    <td>
        <strong><bean:message key="SystemNotificationForm.Event"
                      bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
        <span class="requiredText">*</span>:</strong>
    </td>
    <td>
        <html:select property="eventId" onchange="changePlaceHolders();" styleClass="selectField" >
            <html:option value="0">
                <bean:message key="message.lable.selectOne"/>
            </html:option>
            <html:optionsCollection property="eventList" label="eventName" value="eventId"/>
        </html:select>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="SystemNotificationForm.Content"
                      bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
        <span class="requiredText">*</span>:</strong>
    </td>
    <td>
        <html:textarea property="content" rows="7" cols="50" styleClass="textareaField"/>
    </td>
</tr>

<tr>
    <td>
        <strong><bean:message key="SystemNotificationForm.PlaceHolder"
                      bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
        :</strong>
    </td>
    <td>
        <html:select property="placeHolderId" onchange="appendPlaceHolder()" styleClass="selectField">
            <html:option value="0">
                <bean:message key="message.lable.selectOne"/>
            </html:option>
        </html:select>
    </td>
</tr>
<tr>
    <td>
        <strong><bean:message key="SystemNotificationForm.Role"
                      bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
        :</strong>
    </td>
    <td valign="top">
        <html:select property="roleIds" size="10" multiple="true" styleClass="selectField">
            <html:optionsCollection property="roles" label="roleName" value="roleId"/>
        </html:select><br/>
        (To Unselect a Role, press CTRL and Click the Selected Role.)
    </td>
</tr>
<tr>
    <td valign="middle">
        <strong><bean:message key="SystemNotificationForm.Recipient"
                      bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
        :</strong>
    </td>
    <td>
        <html:text property="recipientEmail" size="75" maxlength="500" styleClass="textareaField"/> <br/>
        (Comma separated)
    </td>
</tr>
<tr>
    <td>&nbsp; </td>
    <td>
        <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <html:checkbox property="vzwAccountManager"/>
                    <bean:message key="SystemNotificationForm.Manager"
                                  bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
                </td>
                <td>
                    <html:checkbox property="appManager"/>
                    <bean:message key="SystemNotificationForm.AppManager"
                                  bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
                </td>
            </tr>
        </table>
    </td>
</tr>

<tr>
    <td colspan="2">

    <logic:match name="SystemNotificationForm" property="task" scope="request" value="create">
        <div class="redBtn" style="float:right; margin-top:10px;" id="Create" title="Create">
            <div>
                <div>
                    <div onClick="document.forms[0].submit()">Create</div>
                </div>
            </div>
        </div>
    </logic:match>
    <logic:match name="SystemNotificationForm" property="task" scope="request" value="edit">
        <div class="redBtn" style="float:right; margin-top:10px;" id="Save" title="Save">
            <div>
                <div>
                    <div onClick="document.forms[0].submit()">Save</div>
                </div>
            </div>
        </div>
    </logic:match>

    <div class="blackBtn" style="float:right; margin-top:10px;margin-right:10px" id="Back" title="Back">
        <div>
            <div>
                <div onClick="window.location='systemNotificationViewDelete.do?task=view'"> Back </div>
            </div>
        </div>
    </div>
    </td>
</tr>
</table>

</div>

</html:form>
<script language="javascript">
    changePlaceHolders();
</script>

</div>
</div>
