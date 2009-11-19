<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<html>
 
 <%@ include file="../../../commons/taglibs.jsp"%>
 
 
<head>

<meta name="mainTitle" content="Edit Notification" />

	<title>Edit Notifications</title>



</head>

 
 
 
<body>



<div id=homepageWrapper>
	<div id=bodyWrapper>
		<div id=homepageContainer>
			<a name=content></a>

				
			<s:if test="hasFieldErrors()">
						    <div class="alertBox">
						      <s:iterator value="fieldErrors">
						          <s:iterator value="value">
						          
						          	   <s:property/>
						          	   
						          </s:iterator>
								  <br/>
						      </s:iterator>
						   </div>
			</s:if>
			
			
			
			
			
			
			
			

		

		      <div id="contentCol">
			  <s:form name="notificationsForm" method="post" theme="simple" enctype="multipart/form-data">
			  
			  <s:hidden name="updateMessage.emailMessageId"/>
			  <s:hidden name="notificationId"/>
			
			
      		<!-- POD START HERE -->
            
                    <!-- POD CONTENT START HERE -->
                                <div class="box984 float_left">
                <div class="box2">
                    <div class="boxTop box984"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
                  <div class="boxContent">
                    <div class="boxContBM">
                    <!-- POD CONTENT START HERE -->
                    
         
                     <div class="clear10"></div>
               
                   <table width="80%" border="0" align="center" cellpadding="5" cellspacing="0">
  <tr>
    <td width="26%" align="right" valign="top"><strong>Email Title<span class="requiredText" >*</span>:</strong></td>
    <td width="74%">
	
	
	<s:textfield tabindex="1" name="updateMessage.emailTitle" maxlength="50" size="75" cssClass="inputFieldForm" cssStyle="width:400px;"/>
	
	</td>
  </tr>
  <tr>
    <td align="right" valign="top"><strong>Email Description:</strong></td>
    <td>
		<s:textarea tabindex="2" name="updateMessage.emailDesc" cssClass="inputFieldForm" cssStyle="width:400px; height:80px;"  />
	
	</td>
  </tr>
  <tr>
    <td align="right" valign="top"><strong>Event Name<span class="requiredText" >*</span>:</strong></td>
    <td>
	
	<s:select tabindex="3" label="Event Name" name="eventId" list="events" listKey="eventId" listValue="eventName" cssClass="inputFieldForm" cssStyle="width:400px;" onchange="javascript:changeEvent(); return false;"/>
	
	
	</td>
  </tr>
  <tr>
    <td align="right" valign="top"><strong>Email Content <span class="requiredText">*</span>:</strong></td>
    <td>
	
	<s:textarea tabindex="4" id="content" title="Email Content" name="updateMessage.emailText" cssClass="inputFieldForm" cssStyle="width:400px; height:180px;"/>
	
	
	
	</td>
  </tr>
  <tr>
    <td align="right" valign="top"><strong>Place Holders:</strong></td>
    <td>
	
	<s:select tabindex="5" cssStyle="width:400px;"  headerKey="-1" headerValue="--Place Holders--" id="placeHolder" label="Place Holder" list="placeHolders" listKey="placeHolderDisplayName" listValue="placeHolderDisplayName" cssClass="inputFieldForm" onchange="javascript:appendPlaceHolder(); return false;"/>
	
	
	</td>
  </tr>
  
  <tr>
    <td align="right" valign="top"><strong>File Attachment</strong></td>
    <td>
		<s:file tabindex="6" name="upload" size="50"/><br>
		<c:if test="${attachment != null}">
			<a tabindex="7" href="downloadFile.action?id=<c:out value='${attachment.emailMessageId}'/>">			
				<c:out value='${attachment.attFileName}'/>
			
			</a>
			
			&nbsp;&nbsp;&nbsp;
			
			<a tabindex="7" href="removeAttachment.action?id=<c:out value='${updateMessage.emailMessageId}'/>">			
				<img src="images/devNetwork/icon_notactive.gif" width="10" height="10">											
			</a>
			&nbsp;
			<strong>Remove Attachment</strong>
		</c:if>
	</td>
  </tr>
  
  
  <tr>
    <td align="right" valign="top"><strong>Target System Roles:</strong></td>
    <td>
	
	
	<s:select tabindex="8" cssStyle="width:400px; height:180px;" name="roleIds" list="roles" listKey="roleId" listValue="roleName" cssClass="inputFieldForm" multiple="true"/>
<br /><p class="smallText grayText">(To Unselect a Role, press CTRL and Click the Selected Role.)</p>
	
	
	
	
	</td>
  </tr>
  <tr>
    <td align="right" valign="top"><strong>Recipient Email Addresses:</strong></td>
    <td>
	
	<s:textfield tabindex="9" name="csvEmails" cssClass="inputFieldForm" cssStyle="width:400px;" />
						
						
						<br /><p class="smallText grayText">(Comma separated)</p>
	
	
    </td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td><div>
                	<BUTTON tabindex="10" class="input_primary mR5" onClick="postNotificationForm(); return false;"><SPAN><SPAN><SPAN>Submit</SPAN></SPAN></SPAN></BUTTON>
<BUTTON tabindex="11" class="input_secondary" onClick="javascript:cancel(); return false;"><SPAN><SPAN><SPAN>Cancel</SPAN></SPAN></SPAN></BUTTON>

                </div></td>
  </tr>
                   </table>
                    <div class="clear10"></div>
                    <!-- POD CONTENT END HERE -->
                    </div>
                    </div>
                    <div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
                </div>
            </div>
         
                    
               
                   
                    
            <!-- POD END HERE -->
            <div class="clear14"></div>
            
      	</div>
		
		
		
			
			
			
			</s:form>

		</div>
	</div>
</div>

 <script type="text/javascript">


   function postNotificationForm(){
  
	document.notificationsForm.action="editSaveNotification.action";
	document.notificationsForm.submit();
	

	  }
 

 function cancel(){
	document.notificationsForm.action="notifications.action";
	document.notificationsForm.submit();
	}
	


  function changeEvent(){
  
	document.notificationsForm.action="changeEventOnUpdate.action";
	document.notificationsForm.submit();
	

	  }
	  
	  
function appendPlaceHolder()
    {
        var list = document.notificationsForm.placeHolder;
        var listValue = list.options[list.selectedIndex].value;

        if (listValue != null && listValue != "0" && listValue!="-1")
        {
            var text = document.forms[0].content.value;
            text += "{" + listValue + "}";
            document.notificationsForm.content.value = text;
        }
}	
</script>

</body>
</html>