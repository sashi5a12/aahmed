<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.alliance.*, com.netpace.aims.bo.security.*  "%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<html:form action="/allianceMusicInfoEdit.do">
 <bean:define id="alliance_id" type="java.lang.Long" name="AllianceMusicInfoForm" property="allianceId" />

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
       		<span class="aimsmasterheading">
       			<bean:message key="AllianceProfile.header" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/> - 
       			VCAST Music Information
       		</span>               
       	</td>
  	</tr>
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
            <aims:getVZWAllianceTab attributeName="VCAST Music" allianceId="<%=alliance_id.toString()%>"/>
       	</td>
  	</tr>
  	<tr> 
       	<td width="20">&nbsp;</td>
       	<td width="100%">
			&nbsp;
       	</td>
  	</tr>
  	<tr> 
       	<td width="20">&nbsp;</td>
        <td width="100%">
            <table class="sectable" width="100%">
                <tr>
                    <td class="text" valign="top">
                        <span class="modFormFieldLbl">Company Name: <bean:write name="AllianceMusicInfoForm" property="companyName" /></span>
                    </td>
                </tr>
            </table>
       	</td>
  	</tr>
  	<tr> 
       	<td width="20">&nbsp;</td>
       	<td width="100%">
			&nbsp;
       	</td>
  	</tr>

	<%@ include  file="/common/error.jsp" %>
  	<tr> 
    	<td width="20">&nbsp;</td>
    	<td align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
		        <tr><td width="100%" colspan="2">
					<table class="sectable" width="100%">
						<tr class="sectitle"><td colspan="2" class="aimssecheading">Which Products are you interested in supplying content for</td></tr>
						<tr>
							<td valign="top" colspan="2">
								<table width="100%" border="1" cellspacing="1" cellpadding="5">
                                    <tr>
                                        <td width="3%">&nbsp;</td>
                                        <td width="16%" align="center"><b>Product Type</b></td>
                                        <td width="13%" align="center"><b>Size of<br/>Total<br/>Catalog</b></td>
                                        <td width="13%" align="center"><b>Size of<br/>Catalog<br/>proposed<br/>for Mobile</b></td>
                                        <td width="17%" align="center"><b>Annual Non-Mobile Income for Catalog<br/>(Most Recent Year)</b></td>
                                        <td width="17%" align="center"><b>Annual Mobile Income for Catalog<br/>(Most Recent Year)</b></td>
                                        <td width="21%" align="center"><b>Top<br/>Selling<br/>Artists</b></td>
                                    </tr>
                                    <logic:iterate id="producttypes" name="AllianceMusicInfoForm" property="allProductTypes" type="com.netpace.aims.controller.alliance.AllianceMusicRegistrationProductBean">
                                        <tr>
                                            <td valign="top">
                                                <html:multibox property="productId">
                                                   <bean:write name="producttypes" property="productId" />
                                                </html:multibox>
                                            </td>
                                            <td valign="top">
                                            	<bean:write name="producttypes" filter="false" property="productName" />
                                            	<input type="hidden" name="productTypeId" value='<bean:write name="producttypes" property="productId" />'/>
                                            	<input type="hidden" name="productName" value='<bean:write name="producttypes" property="productName" />'/>
                                            </td>
                                            <td valign="top"><input type="text" size="15" maxlength="50" name="sizeTotalCatalog" value='<bean:write name="producttypes" property="sizeTotalCatalog" />'/></td>
                                            <td valign="top"><input type="text" size="15" maxlength="50" name="sizeMobileCatalog" value='<bean:write name="producttypes" property="sizeMobileCatalog" />'/></td>
                                            <td valign="top"><input type="text" size="21" maxlength="50" name="incomeNonMobile" value='<bean:write name="producttypes" property="incomeNonMobile" />'/></td>
                                            <td valign="top"><input type="text" size="21" maxlength="50" name="incomeMobile" value='<bean:write name="producttypes" property="incomeMobile" />'/></td>
                                            <td valign="top"><textarea name="topSellingArtists" onkeyup="LimitText(this,1000)" onkeypress="LimitText(this,1000)" rows="2" cols="17"><bean:write name="producttypes" property="topSellingArtists" /></textarea></td> 
                                        </tr>
                                    </logic:iterate>                                                                                                    
                                </table>
                            </td>                                                                                            
                        </tr>							 	
					</table>
				</td></tr>
				
				<tr><td colspan="2"></td></tr>
				<tr><td colspan="2"></td></tr>
				
				<tr><td width="100%" colspan="2">
					<table class="sectable" width="100%">
						<tr class="sectitle"><td colspan="2" class="aimssecheading">Content Rights</td></tr>
						<tr>
							<td valign="top" colspan="2">
								<table width="100%" border="0" cellspacing="5" cellpadding="0">
                                    <tr> 
                                        <td>
                                            <span class="modFormFieldLbl">Do you currently have rights fully cleared and documented for all content you propose for mobile?</span><span class="mainReqAstrx">*</span>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="body" valign="top">
                                            <html:radio property="haveRightsCleared" value="Y"/>Yes&nbsp;
                                            <html:radio property="haveRightsCleared" value="N"/>No
                                        </td>
                                    </tr>						                                                                                        		                                                                                        	                                                                                        
                                	<tr>
                                       <td>
                                           <span class="modFormFieldLbl">Do you have exclusive rights to ALL of the proposed content?</span><span class="mainReqAstrx">*</span>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="body" valign="top">
                                            <html:radio property="haveExclusiveRights" value="Y"/>Yes&nbsp;
                                            <html:radio property="haveExclusiveRights" value="N"/>No
                                        </td>
                                    </tr>				
                                    <tr> 
                                        <td>
                                           <span class="modFormFieldLbl">If not, what IS exclusive?</span>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="body" valign="top">
                                            <html:textarea property="whatIsExclusive" onkeyup="LimitText(this,3000)" onkeypress="LimitText(this,3000)" rows="3" cols="60"></html:textarea>
                                        </td>
                                    </tr>		
                                </table>
                            </td>                                                                                            
                        </tr>							 	
					</table>
				</td></tr>
				
				<tr><td colspan="2"></td></tr>
				<tr><td colspan="2"></td></tr>
				
				<tr><td width="100%" colspan="2">
					<table class="sectable" width="100%">
						<tr class="sectitle"><td colspan="2" class="aimssecheading">Additional Information</td></tr>
						<tr>
							<td valign="top">
								<table width="100%" border="0" cellspacing="10" cellpadding="0">
                                    <tr>
                                       <td>
                                           <span class="modFormFieldLbl">Are you currently delivering your content<br/>to us through a content provider / aggregator?</span><span class="mainReqAstrx">*</span>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="body" valign="top">
                                            <html:radio property="contentThruAggregator" value="Y"/>Yes&nbsp;
                                            <html:radio property="contentThruAggregator" value="N"/>No
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td>
                                           <span class="modFormFieldLbl">Who are your current digital distribution partners<br/>(mobile or otherwise)?</span><span class="mainReqAstrx">*</span>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="body" valign="top">
                                            <html:textarea property="currentDistributionPartners" onkeyup="LimitText(this,3000)" onkeypress="LimitText(this,3000)" rows="4" cols="40"></html:textarea>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td valign="top">
								<table width="100%" border="0" cellspacing="10" cellpadding="0">
                                    <tr> 
                                       <td>
                                           <span class="modFormFieldLbl">What is your annual revenue for the <br/>content sales you are proposing?</span>
                                        </td>
                                    </tr>
                                    <tr> 
                                        <td class="body" valign="top"><html:text property="annualRevenue"  size="55" maxlength="100 "/></td>
                                    </tr>
                                    <tr> 
										<td>
											<span class="modFormFieldLbl">Additional Information</span><span class="mainReqAstrx">*</span>
									  	</td>
									</tr>
									<tr> 
										<td class="text">
											<html:textarea property="additionalInformation" onkeyup="LimitText(this,3000)" onkeypress="LimitText(this,3000)" rows="5" cols="40"></html:textarea>
										</td>
									</tr>
                                </table>
                            </td>
                        </tr>							 	
					</table>
				</td></tr>
								
				<tr><td width="100%">&nbsp;</td></tr>

				<tr class="buttonRow">
					<td	 width="100%" height="25"	align="right"	valign="middle">
						<a href="/aims/allianceMusicInfoSetup.do?task=editForm" class="a">
							<html:img src="images/cancel_b.gif" border="0" /></a> 
							&nbsp;
							<input type="image" src="images/submit_b.gif" border="0" onClick="document.forms[0].task.value='edit';" />
					</td>
				</tr>

			</table>
		</td>  
	</tr>
</table>
</html:form>

