<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      	<td width="185">&nbsp;</td>
    	<td width="20">&nbsp;</td>
		<td width="100%">
            <span class="aimsmasterheading"> <bean:message key="WhitePaperForm.whitePaperHeading" /></span>
		</td>
	</tr>
	<%@ include  file="/common/error.jsp" %>
	<tr>
	    	<td width="185" height="100%" valign="top" bgcolor="#EBEBEB">
            <table width="185" height="100%" border="0" cellspacing="0" cellpadding="0">
           		<tr>
               		<td>
		          		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
							<tr><td width="100%">
								<table class="sectable" width="100%" height="100%">
					            	<tr class="sectitle">
					            		<td valign="top" class="aimssecheading">&nbsp;</td>
					            	</tr>
					            	<tr height="100%">
					            		<td>&nbsp;</td>
					            	</tr>
								</table>
							</td></tr>
						</table>
					</td>
				</tr>
            </table> 	
    	</td>
		<td	width="20">&nbsp;</td>
    	<td width="100%" align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" valign=top">
						<table width="100%" height="100%" class="sectable">
            				<tr class="sectitle"><td class="aimssecheading"><bean:message key="WhitePaperForm.whitePaperListing" /></td></tr>
              				<tr> 
                				<td align="center" valign="middle"> 
            						<table width="100%" cellspacing="0" cellpadding="0" >
                            
										<logic:iterate id="awpaper" name="AimsWhitePapers" type="com.netpace.aims.controller.system.AimsAllianceWhitePaperExt" scope="request">
										     <tr> 
								             	<td class="aimssecheading" align="left">
						 				          <a href='whitePaperResource.do?whitePaperId=<bean:write name="awpaper" property="whitePaperId" />' target='_blank'>
													   <bean:write name="awpaper" property="whitePaperName" />
												   </a>			
													<logic:equal name="awpaper" property="whitePaperFileType" value="application/msword"> 
														 <html:image src="images/word_icon.gif" />
													</logic:equal>	 
													<logic:equal name="awpaper" property="whitePaperFileType" value="application/pdf"> 
														 <html:image src="images/pdf_icon.gif" />
													</logic:equal>	 
												   
								  				</td>
											</tr>
											<tr> 
												<td class="text" align="left">
												   	<bean:write name="awpaper" property="whitePaperDesc" />
 												</td>
											</tr>
 										</logic:iterate>
										 <logic:empty name="AimsWhitePapers">
											<tr>
											  <td class="text" align="left"><bean:message key="WhitePaperForm.whitePaperNotFound" />  </td>
											</tr>
					                    </logic:empty>
						        </table>
              		        </td>
              	       </tr>
			</table>
		</td>
	</tr>
</table>