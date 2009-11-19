<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<html>
 
 <%@ include file="../../../commons/taglibs.jsp"%>
 
 
<head>
	<meta name="mainTitle" content="Newsletter" />   
	<title>Newsletter</title>
	
	
</head>
 
<body>
<div id=homepageWrapper>
	<div id=bodyWrapper>
		<div id=homepageContainer>
			<a name=content></a>
						
			<div id="contentCol">
				<s:form name="newsLetterForm" method="post" action="newsLetter!saveAndSendEmail" theme="simple" enctype="multipart/form-data" >
				
				<!-- POD START HERE -->
		
		<div class="clear10"></div>
		
                <!-- POD CONTENT START HERE -->
                <div class="box984 float_left">
                <div class="box2">
                    <div class="boxTop box984"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
                    <div class="boxContent">
                    	
                    	<%--
                    	<s:if test="hasFieldErrors()">
                    		<div class="alertBox">
						      	<s:actionerror />	
								<s:actionmessage />	
						   </div>
                    	</s:if>
                    	--%>
                    	
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
						
                    	
             <div id="errMsgBox" class="alertBox" style="visibility: hidden" >
			</div> 
			
                    <div class="boxContBM">
                    <!-- POD CONTENT START HERE -->
                    <div class="clear10"></div>
                   
                    	 <table width="80%" border="0" align="center" cellpadding="5" cellspacing="0">
                    	 	<tr>
                    	 	  <td width="26%" align="right" valign="top"><strong>Email Subject <span class="requiredText" >*</span> :</strong></td>
    						  <td width="74%">
									<s:textfield tabindex="1" name="newsLetterEmailLog.emailSubject"  maxlength="100" size="70" cssClass="inputFieldForm" cssStyle="width:440px;"/><br>
									<div class="clear10"></div>
							  </td>
                    	 	</tr>
                    	 	<tr>
                    	 		<td width="26%" align="right" valign="top"><strong>Email Content <span class="requiredText" >*</span>  :</strong></td>
                    	 		<td align="left" valign="top" >
       								<s:textarea tabindex="2" name="newsLetterEmailLog.emailContent" cssClass="inputFieldForm" cssStyle="width:440px; height:200px;"  /> <br>            	 		
       								<div class="clear10"></div>       								
                    	 		</td>
                    	 	</tr>
                    	 	<tr>
                    	 		<td width="26%" align="right" valign="top"><strong>Attachment :</strong></td>
                    	 		<td align="left" valign="top" >
                    	 			
                    	 			<div class="float_left mR5"><s:file tabindex="3" size="35"  name="uploadattachment" disabled="false"  /></div>   
                    	 			
                    	 			<c:if test="${uploadedFilePath == null}">											 
						     <div  class="float_left" nowrap="nowrap"> <BUTTON tabindex="9" class="input_primary mR5" onClick="uploadAttachment(); return false;"><SPAN><SPAN><SPAN>Upload Attachment</SPAN></SPAN></SPAN></BUTTON></div>
                    	 			</c:if>
                    	 			
                    	 			<c:if test="${uploadedFilePath != null}">
						    <div  class="float_left" nowrap="nowrap"><BUTTON DISABLED tabindex="9" class="input_primary " ><SPAN><SPAN><SPAN>Upload Attachment</SPAN></SPAN></SPAN></BUTTON></div>
                    	 			</c:if>
                    	 			<div class="clear"></div>
                    	 			
                    	 			<%-- Show the download file link --%>                    	 			
                    	 			<c:if test="${uploadedFilePath != null}">
                    	 				<script>                    	 				
                    	 				document.getElementsByName("uploadattachment")[0].disabled = true;                   	 				
                    	 				</script>
										<a tabindex="7" href="attachmentDownload.action?fpath=<c:out value='${uploadedFilePath}'/>">			
											<Strong><c:out value='${uploadedFileName}'/></Strong>										
										</a>
										&nbsp;&nbsp;&nbsp;
										<a tabindex="7" href="attachmentRemove.action?fpath=<c:out value='${uploadedFilePath}'/>"><img src="images/devNetwork/icon_notactive.gif" width="10" border="0" height="10"></a><Strong>&nbsp;&nbsp; (Remove Attachment)</Strong>
						</c:if>					
						
						
                    	 			
                    	 			<div class="clear10"></div>
                    	 		</td>
                    	 	</tr>                    	 	
                    	 	<tr>
                    	 	  <td width="26%" align="right" valign="top"><strong>Email Address(es) <span class="requiredText" >*</span> :</strong> <br><i>separated by ; &nbsp;&nbsp;&nbsp;</i></td>
    						  <td width="74%">
									<s:textarea tabindex="4" name="newsLetterEmailLog.emailAddresses" cssClass="inputFieldForm" cssStyle="width:440px; height:100px;" /><br>																	
									<div class="clear10"></div>
									<div class="float_left"><s:file tabindex="5" name="uploadcsv"/></div>
									
									<div nowrap="nowrap" style="margin-left:5px;" class="float_left"><BUTTON tabindex="9" class="input_primary mR5" onClick="uploadEmailAddress(); return false;"><SPAN><SPAN><SPAN>Upload Addresses from CSV File</SPAN></SPAN></SPAN></BUTTON></div>  
									<div class="clear10"></div>
							  </td>
                    	 	</tr>
                    	 	<tr>
                    	 		<td/>
                    	 		<td >
                    	 			
                    	 			<div nowrap="nowrap" class="float_left">
                    	 				<BUTTON tabindex="9" class="input_primary mR5" onClick="sendSaveEmail();return false;"><SPAN><SPAN><SPAN>Send Email</SPAN></SPAN></SPAN></BUTTON>
                    	 			</div> 
                    	 			<div nowrap="nowrap" class="float_left">
						   	<BUTTON class="input_secondary" onClick="cancelNewsLetter();return false;"><SPAN><SPAN><SPAN style="width:45px;">Cancel</SPAN></SPAN></SPAN></BUTTON>
                    	 			</div> 
                    	 		</td>
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
				
				
				</s:form>			
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">		
		
		function uploadAttachmentExt(){
			var attName = document.getElementsByName("uploadattachment")[0].value;							
			var fileExtension = attName.substring(attName.indexOf(".")+1, attName.length);			
			if(fileExtension.toLowerCase() == 'pdf' 
				|| fileExtension.toLowerCase() == 'doc' 
				|| fileExtension.toLowerCase() == 'docx'
				|| fileExtension.toLowerCase() == 'xls'
				|| fileExtension.toLowerCase() == 'xlsx'){
				return true
			} 
			else 
				return false;			
		}
		
		function uploadEmailAddressExt(){
			var cvsFileName = document.getElementsByName("uploadcsv")[0].value;						
			var fileExtension = cvsFileName.substring(cvsFileName.indexOf(".")+1, cvsFileName.length);			
			if(fileExtension.toLowerCase() == 'csv'){
				return true
			} else return false;

		}

		
		function uploadAttachment(){				
			var uploadattachmentValue = document.getElementsByName("uploadattachment")[0].value;									
			if(uploadattachmentValue != null && uploadattachmentValue!=''){				
				if(uploadAttachmentExt()){
					document.newsLetterForm.action="uploadAttachmentFile.action";
					document.newsLetterForm.submit();
				} else{
					alert("Only PDF, DOC and XLS file can be uploaded as an attachment.");
					return false;	
				}
				
				
			} else {
				alert("Select a file for attachment.");
				return false;
			}
		}
			
		function uploadEmailAddress(){					
			var uploadEmailCVSValue = document.getElementsByName("uploadcsv")[0].value;			
			if(uploadEmailCVSValue != null && uploadEmailCVSValue!=''){				
				if(uploadEmailAddressExt()){
					document.newsLetterForm.action="uploadCSVFile.action";
					document.newsLetterForm.submit();
				} else {
					alert("Only CSV file can be uploaded for uploading addresses.");
					return false;
				}
			} else {
				alert("Select a file for uploading addresses.");
				return false;
			}
		}
		
		function sendSaveEmail(){		    
		    var validatedForm = validateForm();
		   
		    if(validatedForm==true){		    	
		    	var doConfirm = confirm("Are you sure you want to send email? ");
		    	if(doConfirm==true){
				document.newsLetterForm.submit();
			}
		    } else return false;
		   
		}
		
		function cancelNewsLetter(){
			document.newsLetterForm.action="newsLetter!cancelNewsLetter.action";
			document.newsLetterForm.submit();		
		}

		function validateForm(){
			var validated = true;
			var errorMsg = '<BR>';
			document.getElementById('errMsgBox').style.visibility = 'hidden'; 
			document.getElementById('errMsgBox').innerHTML= "";
			

			var emailSubject = document.getElementsByName("newsLetterEmailLog.emailSubject")[0].value;
			var emailAddress = document.getElementsByName("newsLetterEmailLog.emailAddresses")[0].value;			
			var emailContent = document.getElementsByName("newsLetterEmailLog.emailContent")[0].value;			
			var attName = document.getElementsByName("uploadattachment")[0].value;		
			
			var attchmnet = document.getElementsByName("uploadattachment")[0];
			
			if(attName != null && attName != ''){				
				//errorMsg = errorMsg  + 'Please upload selected attachment file!. <BR>';	
				alert('Please upload selected attachment file.');
				validated = false;
			}
			
			if(emailSubject == null || emailSubject==''){					
				validated = false;
				errorMsg = errorMsg  + 'Email Subject is a required field!. <BR>';											
			} 
			
			
			if(emailContent == null || emailContent==''){					
				validated = false;
				errorMsg = errorMsg  + 'Email Content is a required field!. <BR>'; 
			}

			if(emailAddress == null || emailAddress==''){								
				validated = false;
				errorMsg = errorMsg  + 'Email Address is a required field!. <BR>'; 
			} 
			
			if(emailAddress != null && emailAddress.trim()!=''){
				var emailAddressList = emailAddress.split(";");					
				var invalidEmail = false;
				for(index=0; index<emailAddressList.length; index++){						
					if(emailAddressList[index].trim() != ''){
						
						if(!checkValidEmail(emailAddressList[index].trim())){														
							validated = false;				
							errorMsg = errorMsg  + 'Invalid Email  [ ' + emailAddressList[index] + ' ] <BR>'; 
							break;
						} else {							
							invalidEmail = false;
						}						
					}				
				}			}
			
			if(!validated){
				if (errorMsg != "<BR>"){			
					document.getElementById('errMsgBox').style.visibility = 'visible'; 
					document.getElementById('errMsgBox').innerHTML= errorMsg;
				}
			}
			
			return validated;
		}
		
				
		function checkValidEmail(email){
				
			var emailfilter=/^\w+[\+\.\w-]*@([\w-]+\.)*\w+[\w-]*\.([a-z]{2,4}|\d+)$/i
			if (emailfilter.test(email))
			  var result = true;
			else {		
			    return false;
			}
			return result;
		}
		
		
		
	</script>


</body>
</html>