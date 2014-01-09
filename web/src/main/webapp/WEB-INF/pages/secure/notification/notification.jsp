<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).JS_TYPE" 
    var="JS_TYPE"/>

<spring:eval 
    expression="T(com.netpace.device.utils.VAPConstants).CKEDITOR" 
    var="CKEDITOR"/>

<tg:includestatic type="${JS_TYPE}" paramName="${CKEDITOR}" />
<h1 class="redheading" >Notification</h1>

<form:form name="frm" action="${url}" method="post" modelAttribute="notificationVO" enctype="multipart/form-data" >
    
    <spring:hasBindErrors name="notificationVO">
        <div class="error">
            <ul>
                <form:errors path="*" element="li" delimiter="</li><li>" id="" />
            </ul>
        </div>
    </spring:hasBindErrors>
        
    <input type="hidden" name="id" value="${notificationVO.id}">
    <div class="regReviewPod">
        <div class="viewedit-Notification">
            <div class="redstar">*</div> <label class="inputlabelBig">Title</label>
            <input class="input" type="text" value="${fn:escapeXml(notificationVO.title)}" id="title" name="title" style="width:350px;" size="200"/>
            <div class="clearboth"></div>

            <div class="redstar">*</div> <label class="inputlabelBig">Description</label>
            <textarea class="textfieldBig" id="description" name="description" style="width:320px; border:0;" rows="5" cols="30">${notificationVO.description}</textarea>
            <div class="clearboth"></div>

            <div class="redstar">*</div> <label class="inputlabelBig">Notification Control</label>
            <form:radiobutton path="enabled" value="true"/>Enable 
            <form:radiobutton path="enabled" value="false"/>Disable
            <div class="clearboth"></div>

            <div class="redstar">*</div> <label class="inputlabelBig">Event Name</label>
            
            <div id="ffb1"></div>
            <select class="selct" name="eventId" onchange="return updatePlaceholders(this.value);">
                <option value="0">Select</option>
                <c:forEach items="${eventsList}" var="event">
                    <option <c:if test="${event.id eq notificationVO.eventId}">selected</c:if> value="${event.id}">${event.eventName}</option>
                </c:forEach>
            </select>
            <div class="clearboth"></div>

            <div class="redstar">*</div> <label class="inputlabelBig">Email Content (HTML)</label>
            <table cellspacing="0" cellpadding="0" border="0" width="750px;">
                <tbody>
                    <tr>
                        <td class="txtArea">
                            <textarea id="emailContent" name="emailContent">${notificationVO.emailContent}</textarea>
                        </td>
                    </tr>
                </tbody></table>

            <div class="clearboth"></div>

            <label class="inputlabelBig">Email Content (Plain Text)</label>
            <textarea class="textfieldBig" id="emailPlainText" name="emailPlainText" style="width:740px; border:0; height: 150px" rows="20" cols="30">${notificationVO.emailPlainText}</textarea>
            <div class="clearboth"></div>
            
            <label class="inputlabelBig">Place Holders</label>
            <select class="selct" id="placeholderId" name="placeholderId">
                <option value="0">Select</option>
                <c:forEach items="${placeholdersList}" var="placeholder">
                    <option value="${placeholder.id}">${placeholder.displayName}</option>
                </c:forEach>
            </select>
            <div class="clearboth"></div>

            <label class="inputlabelBig">Target System Roles</label>
            <form:select path="targetSystemRoles" multiple="multiple" cssClass="selct" >
                <form:options items="${systemRolesList}" itemValue="roleId" itemLabel="roleName" />
            </form:select>
            <br/>
            (To Unselect a Role, press CTRL and Click the Selected Role.)
            <div class="clearboth"></div>

            <label class="inputlabelBig">Recipient Email Addresses</label>
            <input class="input" type="text" value="${fn:escapeXml(notificationVO.recipientEmailAddresses)}" id="recipientEmailAddresses" name="recipientEmailAddresses" style="width:350px;"/>
            <br/>
            (Comma Separated)
            <div class="clearboth"></div>
        </div>
    </div>
</form:form>

<!-- buttons starts -->

<!-- buttons starts --> 
<a class="button floatRight marginLeft10" href="${pageContext.request.contextPath}/secure/manage/notifications.do"><span class="gray">Cancel</span></a>
<a class="button floatRight" href="javascript:void(0);" onclick="javascript:document.forms['frm'].submit()" ><span class="red">Submit</span></a>
<!-- buttons ends -->

<script type="text/javascript">

    $(document).ready(function(){
        CKEDITOR.replace( 'emailContent',{
            customConfig : '${applicationScope.STATICS_URL}/scripts/ckeditor/vap-ckeditor-config.js'
        });
    
        $("#placeholderId").change(insertPlaceHolder);
        
        CKEDITOR.instances.emailContent.on('focus', function () {
           $('#notificationVO').data('lastSelected', $(this));
        });
        $('#emailPlainText').focus(function () {
           $('#notificationVO').data('lastSelected', $(this));
        });
    });

    function insertPlaceHolder(){
        var selectedId   = $('#placeholderId option:selected').val();
        if( selectedId == '0')
            return;
        
        var selectedText = $('#placeholderId option:selected').text();
        var elemLastSelected = $('#notificationVO').data('lastSelected')
        if(elemLastSelected && elemLastSelected.attr('id') == 'cke_1'){
            CKEDITOR.instances.emailContent.insertText("{"+ selectedText +"}");
        }else if(elemLastSelected && elemLastSelected.attr('id') == 'emailPlainText'){
            var caretPosition = document.getElementById('emailPlainText').selectionStart;
            var valEmailPlainText = elemLastSelected.val().substr(0, caretPosition) + "{"+ selectedText +"}" + elemLastSelected.val().substr(caretPosition, elemLastSelected.val().length);
            elemLastSelected.val(valEmailPlainText);
        }
    }
    
    function updatePlaceholders(eventId){
        $.ajax({
            type: "POST",
            url: "eventplaceholders.do?eventId="+eventId,
            data: "{'date':" + (new Date()) + "}",
            contentType: "application/json; charset=utf-8",
            global: false,
            async: false,
            dataType: "json",
            success: function(jsonData) {
                var listItems = "<option value='0'>Select</option>";
                for (var i = 0; i < jsonData.results.length; i++){
                  listItems+= "<option value='" + jsonData.results[i].id + "'>" + jsonData.results[i].name + "</option>";
                }
                $("#placeholderId").html(listItems);                
            }
        });
    }

</script>
