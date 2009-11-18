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
                 <span	class="aimsmasterheading"><bean:message key="SalesContactForm.Welcome" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
		</td>
    </tr>
    <tr> 
       <td width="20">&nbsp;</td>
       <td width="100%">&nbsp;
            
       </td>
    </tr>
	<%@ include  file="/common/error.jsp" %>
	<tr>
		<td	width="20">&nbsp;</td>
    	<td width="100%" align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" valign=top">
						<table class="sectable" width="100%" height="100%">
							<tr class="sectitle">
                                <td colspan="2" class="aimssecheading">
                                    <bean:message key="SalesContactForm.ListHeading" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
                                </td>
                            </tr>
                			<tr>
                    			<td colspan="2" align="center" valign="middle" bgcolor="#EBEBEB"> 
				          	        <table width="100%" border="0" cellspacing="0" cellpadding="0">		
			      			            <tr>
			      			      	        <td bgcolor="#EBEBEB">
			      		      		        <table width="100%" border="0" class="tabletop" cellspacing="0" cellpadding="2" >
			      	      			        <tr bgcolor="#BBBBBB">
			                                            <td class="firstcell" align="center">
			                                                <span class="modFormFieldLbl"><bean:message key="SalesContactForm.SalesContactName" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
			                                            </td>
										 				 <td class="cell" align="center">
			                                                <span class="modFormFieldLbl"><bean:message key="SalesContactForm.Title" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
			                                            </td>
														 <td class="cell" align="center">
			                                                <span class="modFormFieldLbl"><bean:message key="SalesContactForm.Region" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
			                                            </td>
			                                            <td class="cell" align="center">
			                                                <span class="modFormFieldLbl"><bean:message key="SalesContactForm.Edit" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
			                                            </td>
										  				<td class="cell" align="center">
			                                                <span class="modFormFieldLbl"><bean:message key="SalesContactForm.Delete" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/></span>
			                                            </td>
			                            	        </tr>

													<logic:iterate id="salesContact" name="aimsSalesContacts">
													<tr>
														<td class="firstcell" align="left">
															<bean:write name="salesContact" property="salesContactName" />
														</td>
														<td class="cell" align="left">
															<bean:write name="salesContact" property="title" />&nbsp;
														</td>
														<td class="cell" align="left">
															<bean:write name="salesContact" property="regionName" />&nbsp;
														</td>
														<td class="cell" align="center">	
															<a href='salesContactSetup.do?task=edit&salesContactId=<bean:write name="salesContact" property="salesContactId"/>' class="standardLink">
													               <html:img src="images/edit_icon.gif"  border="0" alt="Edit"/>
											                </a>
					
														</td>								
														</td>
														<td class="cell" align="center">
															<a href='salesContactViewDelete.do?task=delete&salesContactId=<bean:write name="salesContact" property="salesContactId"/>' class="standardLink">
													               <html:img src="images/delete_icon.gif"  border="0" alt="Delete"/>
											                        </a>
														</td>
													  </tr>											
													</logic:iterate>
                                                    
			                                    </table>
			      			                </td>
			      		                </tr>
			                        </table>

      	            			</td>
                			</tr>
            			</table>
	    			</td>
    			</tr>
			</table>
		</td>
	</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">                        
    <tr>
        <td width="20">&nbsp;</td>
        <td width="100%" align="right">
           <img src="images/spacer.gif" width="10" height="1">
            <a href='salesContactSetup.do?task=create' class="a">
		    <img src="images/create_b.gif"  border="0" alt="Create">
           </a> 
        </td>
    </tr>
</table>
		

