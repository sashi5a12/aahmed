<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<html:form action="/indFocusEdit.do" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
       		<span class="aimsmasterheading">
               <bean:message key="IndFocusForm.welcome" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
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
    			
			<html:hidden  property="industryId" />
			<html:hidden  property="task" value="create" />				
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                    <td width="100%">
					    <table class="sectable" width="100%">
                            <tr class="sectitle">
                                <td colspan="2" class="aimssecheading">
						
  				   				  <bean:message key="IndFocusForm.createHeading" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
		                		</td>
                            </tr>
                            <tr>
                                <td class="modFormFieldLbl"><bean:message key="IndFocusForm.indFocusName" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/><span class="mainReqAstrx">*</span>:</td>
                                
                                <td class="text" valign="top">
                           			 <html:text  property="industryName" size="30" maxlength="50"/>
                                </td>
                                
                            </tr>
							<tr>
                                <td class="modFormFieldLbl"><bean:message key="IndFocusForm.indFocusDesc" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>:</td>
                                
                                <td class="text" valign="top">
                           			 <html:textarea property="industryDescription" rows="5" cols="30"/>
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
	   <td width="100%">
	      <table  width="100%">
		  <tr>
             <td align="left">
         		 	<a href='/aims/indFocus.do?task=view' class='a'>
				      	<img src="images/back_b.gif"  border="0" alt="Back">
					</a>
       		 </td>
             <td align="right">   
               <img src="images/spacer.gif" width="10" height="1">
                    <input type="image" src="images/save_b.gif" border="0" alt="save">
             </td>
        </tr>
	    </table>
	</td>
	
    </tr>
</table>
</html:form>

