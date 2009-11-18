<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://fckeditor.net/tags-fckeditor" prefix="FCK" %>
<script type="text/javascript">
	var textCount=0;
	function countNodeText(node) {
		var notWhitespace = /\S/;
	    for (var i=0; i < node.childNodes.length; i++) {       
	    	var childNode = node.childNodes[i];
	        if (childNode.nodeType == 3 && notWhitespace.test(childNode.nodeValue)) {
	        	var text = childNode.nodeValue.replace(/^(\s*)/, '').replace(/\s*$/, '');
	        	if (text != ' '){
	        		//alert("----"+text+"----\n"+text.length);
	        		textCount = textCount + text.length;
	        	}
	        }
	        if ( childNode.nodeType == 1) {
	            countNodeText(childNode);
	        }
	    }
	    return textCount;
	}

	function submitForm(){		
		var editor = FCKeditorAPI.GetInstance('disclaimerText') ;
		editor.ResetIsDirty();		
		if (editor){
			var text=editor.GetXHTML(true);
			var oDOM = editor.EditorDocument ;
			textCount=0;
			var textLength=countNodeText(oDOM.body);
			if (text.length > 0){
				if (textLength > 1000){
					alert("Text cannot exceed 1000 characters");					
					return;
				}
			}
						
			document.forms[0].disclaimerText.value=editor.GetXHTML(true);
						
			if (document.forms[0].disclaimerText.value.length > 0){
				textCount=0;
				document.forms[0].disclaimerTextLength.value=textLength;				
			}
		}
		document.forms[0].submit();
	}
</script>
<%@ include file="/common/error.jsp"%>
<DIV class="contentbox">
	<html:form action="/disclaimerUpdate">
		<html:hidden property="disclaimerId"/>
		<html:hidden property="taskName"/>
		<html:hidden property="disclaimerTextLength"/>
				
		<DIV class="homeColumnTab lBox">
			<DIV class="headLeftCurveblk"></DIV>
			<H1>
				<logic:equal property="taskName" value="edit" name="DisclaimerForm">
					<bean:message key="DisclaimerForm.heading.edit" />
				</logic:equal>
				<logic:equal property="taskName" value="view" name="DisclaimerForm">
					<bean:message key="DisclaimerForm.heading.view" />
				</logic:equal>			
				
			</H1>
			<DIV class="headRightCurveblk"></DIV>
			<DIV class="contentbox">
				<table width="100%" cellspacing="0" cellpadding="2"	class="GridGradient" border="0">
					<logic:equal property="taskName" value="edit" name="DisclaimerForm">
						<tr>
							<th width="18%">
								<strong>
									<bean:message key="DisclaimerForm.label.disclaimerName" />&nbsp;
									<span class="requiredText">*</span>:
								</strong>
							</th>
							<th>
								<html:text property="disclaimerName" size="30" maxlength="100" styleClass="inputField" />
							</th>
						</tr>
						<tr>
							<td colspan="2">
								<strong>
									<bean:message key="DisclaimerForm.label.disclaimerText" />&nbsp;
									<span class="requiredText">*</span>:
								</strong>
							</td>						
						</tr>
						<tr>
							<td colspan="2">								
								<FCK:editor id="disclaimerText" basePath="/aims/js/fckeditor/" 
											toolbarSet="aims" fontNames="Arial" fontSizes="10" 
											height="300"
											forcePasteAsPlainText="true" >
									<bean:write property="disclaimerText" name="DisclaimerForm" filter="false"/>
								</FCK:editor>
							</td>						
						</tr>						
					</logic:equal>

					<logic:equal property="taskName" value="view" name="DisclaimerForm">			
						<tr>
							<th width="18%">
								<strong><bean:message key="DisclaimerForm.label.disclaimerName" />&nbsp;:</strong>
							</th>
							<th><bean:write property="disclaimerName" name="DisclaimerForm"/></th>
						</tr>						
						<tr>
							<td width="18%" style="vertical-align: top">
								<strong><bean:message key="DisclaimerForm.label.disclaimerText" />&nbsp;:</strong>
							</td>
							<td><bean:write property="disclaimerText" name="DisclaimerForm" filter="false"/></td>
						</tr>
					</logic:equal>
				</table>
				<logic:equal property="taskName" value="edit" name="DisclaimerForm">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td align="right">
								<div class="redBtn"	style="margin-left: 10px; float: right; margin-top: 3px" title="Save">
									<div><div><div onClick="javascript:submitForm();//">Save</div></div></div>
								</div>
							</td>
						</tr>
					</table>
				</logic:equal>
			</DIV>
		</DIV>
	</html:form>
</DIV>
