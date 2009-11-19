<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<html>
 
 <%@ include file="../../../commons/taglibs.jsp"%>
 
 
<head>

<meta name="mainTitle" content="Add Notification" />

	<title>Add Notifications</title>


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
	
	<s:textfield tabindex="1" name="newMessage.emailTitle"  maxlength="50" size="75" cssClass="inputFieldForm" cssStyle="width:400px;"/>
	</td>
  </tr>
  <tr>
    <td align="right" valign="top"><strong>Email Description:</strong></td>
    <td>
		
		<s:textarea  tabindex="2" name="newMessage.emailDesc" cssClass="inputFieldForm" cssStyle="width:400px; height:80px;" />
	
	</td>
  </tr>
  <tr>
    <td align="right" valign="top"><strong>Event Name<span class="requiredText" >*</span>:</strong></td>
    <td>
	
		<s:select tabindex="3" id="event_Id" cssStyle="width:400px;" headerKey="-1" headerValue="--Select Event--" label="Event Name" name="eventId" list="events" listKey="eventId" listValue="eventName" cssClass="inputFieldForm"/>
	
	</td>
  </tr>
  <tr>
    <td align="right" valign="top"><strong>Email Content <span class="requiredText">*</span>:</strong></td>
    <td>
	
	<s:textarea tabindex="4" id="content" title="Email Content" name="newMessage.emailText" cssClass="inputFieldForm" cssStyle="width:400px; height:180px;"/>
	
	</td>
  </tr>
  <tr>
    <td align="right" valign="top"><strong>Place Holders:</strong></td>
    <td>
	
	<s:select tabindex="5" cssStyle="width:400px;" headerKey="-1" headerValue="--Place Holders--" id="placeHolder" label="Place Holder" list="placeHolders" listKey="placeHolderDisplayName" listValue="placeHolderDisplayName" cssClass="inputFieldForm" onchange="javascript:appendPlaceHolder(); return false;"/>
	
	</td>
  </tr>
  
  <tr>
    <td align="right" valign="top"><strong>Upload File</strong></td>
    <td>
    <s:file tabindex="6" name="upload" size="50"/><br>
	<s:file tabindex="6" name="upload"/>	
	</td>
  </tr>
  
  
  
  
  <tr>
    <td align="right" valign="top"><strong>Target System Roles:</strong></td>
    <td>
	
	
	<s:select tabindex="7" name="roleIds" list="roles" listKey="roleId" listValue="roleName" cssClass="inputFieldForm" multiple="true" cssStyle="width:400px; height:180px;"/>
	
	
<br /><p class="smallText grayText">(To Unselect a Role, press CTRL and Click the Selected Role.)</p>
	
	
	
	
	</td>
  </tr>
  <tr>
    <td align="right" valign="top"><strong>Recipient Email Addresses:</strong></td>
    <td>
	
	<s:textfield tabindex="8" name="csvEmails" cssClass="inputFieldForm" cssStyle="width:400px;" />
	
	
						
						
						<br /><p class="smallText grayText">(Comma separated)</p>
	
	
    </td>
  </tr>
  <tr>
    <td align="right">&nbsp;</td>
    <td><div>
	
	
	<BUTTON tabindex="9" class="input_primary mR5" onClick="postNotificationForm(); return false;"><SPAN><SPAN><SPAN>Submit</SPAN></SPAN></SPAN></BUTTON>
<BUTTON tabindex="10" class="input_secondary" onClick="javascript:cancel(); return false;"><SPAN><SPAN><SPAN>Cancel</SPAN></SPAN></SPAN></BUTTON>

	

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
  
	document.notificationsForm.action="addSaveNotification.action";
	document.notificationsForm.submit();
	

	  }


 function cancel(){
	document.notificationsForm.action="notifications.action";
	document.notificationsForm.submit();
	}
	


  function changeEvent(){
	document.notificationsForm.action="changeEvent.action";
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

<script>document.notificationsForm.eventId.onchange = changeEvent</script>


</body>
</html>