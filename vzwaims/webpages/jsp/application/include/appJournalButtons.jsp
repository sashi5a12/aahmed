<%if (isVerizonUser) {%>
<tr>
	<td>
		<div id="divButtons">
			<!--<input type="image"	name="AllSubmit" <bean:message key="images.submit.button.lite"/> onClick="document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='submitpage4';submitForm();"/>-->
            <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="AllSubmit" title="Submit">
                <div><div><div onClick="document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='submitpage4';submitForm();document.forms[0].submit();">Submit</div></div></div>
            </div>            
        </div>
	</td>
</tr>
<%}%>