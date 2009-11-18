 <%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

 
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
       		<span class="aimsmasterheading">
       			<bean:message key="EmailMessageForm.Welcome" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
       		</span>               
       	</td>
 	</tr>
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">&nbsp;
	</td>
  	</tr>
  	<%@ include  file="/common/error.jsp" %>
  	<tr> 
    	<td width="20">&nbsp;</td>
      	<td  width="100%" align="center" valign="middle" bgcolor="#FFFFFF"> 
	
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                    <td width="100%">
					    <table class="sectable" width="100%">
                            <tr class="sectitle">
                                <td colspan="2" class="aimssecheading"><bean:message key="EmailMessageForm.ViewHeading"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></td>
                            </tr>
                            <tr>
                                <td class="modFormFieldLbl" width="30%"><bean:message key="EmailMessageForm.Title"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:</td>
                                <td class="text" width="70%"> <bean:write name="aimsEmailMessage" property="emailTitle" /></td>
                            </tr>
                             <tr>
                                <td class="modFormFieldLbl">
                                    <bean:message key="EmailMessageForm.Desc"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:
                               </td>
                                <td class="text">
                                    <bean:write	name="aimsEmailMessage" property="emailDesc"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="modFormFieldLbl">
                                    <bean:message key="EmailMessageForm.Text"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:
                               </td>
                                <td class="text">
                                    <bean:write	name="aimsEmailMessage" property="emailText"/>
                                </td>
                            </tr>
                            
					    </table>	
				    </td>
                </tr>
			</table>
		</td>
	</tr>
    <tr>
        <td width="20">&nbsp;</td>
        <td width="100%">&nbsp;</td>
    </tr>
  	<tr> 
     <tr>
       <td width="20">&nbsp;</td>
	   <td width="100%">
	      <table  width="100%">
		  <tr>
             <td align="left">
         		 <a href='emailMessageViewDelete.do?task=view' class="a">
				     <img src="images/back_b.gif"  border="0" alt="Back">
				 </a>
       		 </td>
             <td align="right">   
                 <a href='emailMessageSetup.do?task=edit&emailMessageId=<bean:write name="aimsEmailMessage" property="emailMessageId"/>' class="a"> 
                     <img src="images/edit_b.gif"  border="0" alt="Edit"></a>&nbsp;&nbsp; 
                 <a href='emailMessageViewDelete.do?task=delete&emailMessageId=<bean:write name="aimsEmailMessage" property="emailMessageId"/>' class="a">
				     <img src="images/delete_b.gif"  border="0" alt="Delete"> 
                 </a>
		    </td>
        </tr>
	    </table>
	</td>
	
   </tr>
	</tr>
</table>

 
 
 
	

