<%@ page import="com.netpace.aims.util.AimsConstants" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%-- if upload images successfully then close this window, and refresh parent --%>
<logic:present name="closePopup" scope="request">
    <script language="javascript">
     	window.close();
    </script>
</logic:present>


    <%-- if there is any error, show here --%>
    <table cellpadding="0" cellspacing="0" border="0">
        <tr>
            <td width="465px"><div style="height:100%; "><%@ include file="/common/popUpError.jsp" %></div></td>
        </tr>        
    </table>

    <%-- width is 60px less than width of window called from parent window. (to support IE6) --%>
    <DIV class="homeColumnTab" style="height:100%;width:465px;">
        <html:form action="/renewJmaContract.do" enctype="text/html" onsubmit="return submitFrm();">
         <table width="100%" border="0" cellspacing="0" cellpadding="0">
        	<tr>
        		<td width="100%">
        			<strong>You cannot submit a new solution or edit an existing solution since you do not
							have an ACTIVE, ACCEPTED JMA Contract with Verizon. Would you like to inform
							Verizon that you may be interested in accepting a new JMA Contract?
        			</strong>
        		</td>
        	</tr>
        	<tr>
        	<td>
        		<div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Yes" title="Yes">
	            <div><div><div onclick="sendNotification('Y');return false;">Yes</div></div></div>
	            </div>
	        	<div class="redBtn" style=" margin-left:10px;float:right; margin-top:10px; margin-bottom:10px" id="No" title="No">
	            <div><div><div onclick="sendNotification('N');return false;">No</div></div></div>
	            </div>
	          </td>  
        	</tr>
        </table>
        
        
         <html:hidden property="appId"/>
         <html:hidden property="sendEmail"/>
        </html:form>
    </DIV>
    <%-- end Div homeColumn--%>

<script language="javascript">
    function sendNotification(flag){
    
    	document.JAMPartnerRenewContractForm.sendEmail.value=flag;
    	document.JAMPartnerRenewContractForm.submit();
            
            
    }
</script>

  