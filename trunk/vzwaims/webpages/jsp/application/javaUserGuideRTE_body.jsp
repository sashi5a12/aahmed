<%@page import="com.netpace.aims.util.AimsConstants"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<script type="text/javascript">
	
	var updateFlag=false;
	var fieldTextRef;
	var fieldLenRef;
	var fieldCountRef;	
	function FCKeditor_OnComplete( editorInstance ){	
		var opener=window.opener;
		if (opener){
			var fieldNo=opener.getFieldNo();
			var parentForm=opener.document.forms[0];			
			if (fieldNo == 1){
				fieldTextRef = parentForm.usingApplication;
				fieldLenRef = parentForm.usingApplicationLen;
				fieldCountRef = parentForm. textCountUsingApplication;
			}
			else if (fieldNo == 2){
				fieldTextRef = parentForm.tipsAndTricks;
				fieldLenRef = parentForm.tipsAndTricksLen;
				fieldCountRef = parentForm.textCountTipsAndTricks;
			}				
			else if (fieldNo == 3){
				fieldTextRef = parentForm.faq;
				fieldLenRef = parentForm.faqLen;
				fieldCountRef = parentForm.textCountFaq;
			}
			else if (fieldNo == 4){
				fieldTextRef = parentForm.troubleshooting;
				fieldLenRef = parentForm.troubleshootingLen;
				fieldCountRef = parentForm.textCountTroubleshooting;
			}
			else if (fieldNo == 5){
				fieldTextRef = parentForm.devCompanyDisclaimer;
				fieldLenRef = parentForm.devCompanyDisclaimerLen;
				fieldCountRef = parentForm.textCountDevCompanyDisclaimer;
			}
			else if (fieldNo == 6){
				fieldTextRef = parentForm.additionalInformation;
				fieldLenRef = parentForm.additionalInformationLen;
				fieldCountRef = parentForm.textCountAdditionalInformation;
			}			
			if ( fieldTextRef && editorInstance.EditMode == FCK_EDITMODE_WYSIWYG ){
				if (fieldTextRef.value && fieldTextRef.value.length>0){
					editorInstance.SetData(fieldTextRef.value);
				}
			}
			//editorInstance.InsertHtml('&nbsp;');
		}
		
	}
	
	function submitForm(){		
		var editor = FCKeditorAPI.GetInstance('userGuideRte') ;
		var opener=window.opener;
		if (!opener.document.getElementById('field_1')){
			self.close();
			return;
		}				
		if (editor && opener){
			var oDOM = editor.EditorDocument ;
			var fieldNo=opener.getFieldNo();			
			if (fieldNo){
				var parentDisplayRow="row_"+fieldNo;
				var parentDisplayCell="cell_"+fieldNo;
				var len;
				for (var i=1; i<=6; i++){
					var field="field_"+i;					
					opener.document.getElementById(field).style.display="none";
				}
				
				/*if (editor.IsDirty()){
					editor.ResetIsDirty() ;
				}
				alert( editor.IsDirty() ) ;
				alert("GetXHTML\n"+editor.GetXHTML(true));
				alert("innerHTML\n"+ editor.EditorDocument.body.innerHTML);
				alert("GetData\n"+ editor.GetData());*/
				
				var text=editor.GetXHTML(true);
				
				if (text.length > 0){
					textCount=0;
					len=countNodeText(oDOM.body);
					if (len > <%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>){
						alert("Text cannot exceed "+<%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%>+" characters");
						editor.ResetIsDirty();
						return;
					}					
					fieldTextRef.value=text;
					fieldLenRef.value = len;
					fieldCountRef.value = <%=AimsConstants.JAVA_USER_GUIDE_FIELD_LEN%> - len;				
				}				

				if (opener.document.getElementById(parentDisplayRow).style.display == ""){
					opener.document.getElementById(parentDisplayCell).innerHTML=text;
				}
				
				if(len>0){								
					var htmlText='<strong>Information successfully ' +((updateFlag)?'updated':'added')+'</strong>';
					var field=opener.document.getElementById(('field_'+fieldNo));
					field.innerHTML=htmlText;
					field.style.display="";
				}
			}
		}
		self.close();
	}
	function setLabel(){		
		var opener=window.opener;
		if(opener){
			var num=opener.getFieldNo();			
			if (num){
				if (num == 1){
					document.getElementById('label').innerHTML='<bean:message key="BrewApplicationForm.usingApplication" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';
				}
				else if (num == 2){
					document.getElementById('label').innerHTML='<bean:message key="BrewApplicationForm.tipsAndTricks" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';
				}
				else if (num == 3){
					document.getElementById('label').innerHTML='<bean:message key="ManageApplicationForm.appFAQ" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';
				}
				else if (num == 4){
					document.getElementById('label').innerHTML='<bean:message key="BrewApplicationForm.troubleshooting" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';
				}
				else if (num == 5){
					document.getElementById('label').innerHTML='<bean:message key="BrewApplicationForm.devDisclaimer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';
				}
				else if (num == 6){
					document.getElementById('label').innerHTML='<bean:message key="BrewApplicationForm.additionalInfo" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>';
				}							 				
			}
			else {
				document.getElementById('label').innerHTML='User Guide';
			}			
		}
	}
</script>

<DIV class="contentbox">
	<form action="" method="get">
		<DIV class="homeColumnTab lBox">
			<DIV class="headLeftCurveblk"></DIV>
			<H1>User Guide Snapshot</H1>
			<DIV class="headRightCurveblk"></DIV>
			<DIV class="contentbox">
				<table width="100%" cellspacing="0" cellpadding="2"class="GridGradient" border="0">					
					<tr>
						<th>
							<strong><span id="label"></span>&nbsp;:</strong>
						</th>
					</tr>
					<tr>
						<td>
						<FCK:editor instanceName="userGuideRte" 
										basePath="/js/fckeditor/" toolbarSet="aims" 
										height="375" 
										>								
							</FCK:editor>
						</td>
					</tr>
				</table>
				<table border="0" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td align="right">
							<div class="redBtn" style="margin-left: 10px; float: right; margin-top: 3px" title="Done">
								<div><div><div onClick="javascript:submitForm();">Done</div></div></div>
							</div>
						</td>
					</tr>
				</table>
			</DIV>
		</DIV>
	</form>
</DIV>
<script language="javascript">
	setLabel();	
</script>