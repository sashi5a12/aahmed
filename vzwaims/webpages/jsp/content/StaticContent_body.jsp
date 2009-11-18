<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<script language="JavaScript1.2">
<!--
if (typeof eWebWP != "object")
{
	var sMsg = "Failed to create the eWebWP JavaScript object.";
	sMsg += "\nMost likely the path (eWebWPPath) in file ewebwp.js is not correct.";
	alert(sMsg);
}
//-->
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
			<span class="aimsmasterheading">
                Static Content
            </span>
		</td>
	</tr>
	<%@ include  file="/common/error.jsp" %>
	<%@ include  file="../../../ewebwp/ewebwp.jsp" %>
	<tr> 
		<td width="20">&nbsp;</td>
    	<td width="100%" align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" valign=top">
						<table class="sectable" width="100%" height="100%">
							<form name="frmMain" method="post">
							<tr class="sectitle"><td colspan="2" class="aimssecheading">Static Content</td></tr>
							<tr class="disclaimerText"> 
							  <td>
							  	Select a page to edit: 
							  	<select name="pages">
							  		<option name="brew1">BREW Landing Page</option>
							  		<option name="sms1">SMS Landing Page</option>
							  		<option name="mms1">MMS Landing Page</option>
							  	</select>
							  </td>
							</tr>
							<tr> 
							  <td>
							  	<%= eWebWPEditor("MyContent1", "550", "400", strContent1) %>
							  </td>
							</tr>
							<tr class="instructionalText"> 
							  <td class="aimssecheading"><a href="#" target="_blank">Click Here to access Live Chat</a>
							  </td>
							</tr>
							<script language="JavaScript1.2" type="text/javascript">
							<!--
								function MyContent1_onsubmit()
								{
									return false;
								}
							//-->
							</script>
							</form>
						</table>
        			</td>
    			</tr>
            </table>           
		</td>
	</tr>
</table>

	
				

