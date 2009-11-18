<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>



<html:form action="/devicesEdit.do" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
       		<span class="aimsmasterheading">
               Device Management
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
    			
			<html:hidden  name="AimsDevice" property="deviceId"  />
			<html:hidden  property="task" value="edit" />				
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                    <td width="100%">
					    <table class="sectable" width="100%">
                            <tr class="sectitle">
                                <td colspan="2" class="aimssecheading">
						           Create Device
  				   				</td>
                            </tr>
                            <tr>
                                <td class="modFormFieldLbl">Phone Model<span class="mainReqAstrx">*</span>:</td>
                                
                                <td class="text" valign="top">
                           			 <html:text name="AimsDevice" property="deviceModel" size="30" maxlength="50"/>
                                </td>
                                
                            </tr>
							<tr>
                                <td class="modFormFieldLbl">Phone Manufacturer:</td>
                                
                                <td class="text" valign="top">
                           			 <html:text name="AimsDevice"  property="deviceMfgBy" size="30" maxlength="50"/>
                                </td>
                            </tr>
							<tr> 
									<td colspan="2">
										<table width="100%" border="0" cellspacing="10" cellpadding="0"> 
											<tr>		
												<td class="modFormFieldLbl" align="left">
												VZW Platforms available
												<br/><br/>
			                                  	  <html:select  property="allplatforms" size="10" multiple="true" styleClass="selectField">
                                                        <logic:iterate id="pf" name="AimsPlatforms" type="com.netpace.aims.model.core.AimsPlatform">
                                                              <html:option value="<%=pf.getPlatformId().toString()%>">
													        		<bean:write name="pf" property="platformName" />
														       </html:option> 
                                                               <BR />	
                                                        </logic:iterate>         
                                                  </html:select>
												</td>
												<td >
													<input class="content" type="button" name="add"	   value="   Add >>   " onclick="add_selection(document.DevicesEditForm.allplatforms, document.DevicesEditForm.platform)">
													<br>
													<br>
													<input class="content" type="button" name="remove" value="<< Delete " onclick="remove_selection(document.DevicesEditForm.platform)">
													<br>
													<br>
													<input class="content" type="button" name="reset"  value="  Clear All  " onclick="reset_selection(document.DevicesEditForm.platform)">
												</td>
												<td class="modFormFieldLbl" align="right">
													VZW Platforms supported
													<br/><br/>
                                                    <html:select name="AimsDevice" property="platform" size="10" multiple="true" styleClass="selectField">  
                                                        <logic:iterate id="pf" name="AimsDevice" property="platform" type="com.netpace.aims.model.core.AimsPlatform">
                                                                    <html:option value="<%=pf.getPlatformId().toString()%>"><bean:write name="pf" property="platformName" /></html:option> 
                                                                    <BR />	
                                                        </logic:iterate>    
                                                    </html:select>
												</td>
											</tr>
										</table>
									</td>
								</tr>                
								<tr> 
									<td align="right">
										<span class="modFormFieldLbl">Manufacturer web site URL</span><br>(optional)
									</td>
									<td class="text" align="left">
										<html:text name="AimsDevice"  property="mfgWebSiteUrl" size="30" maxlength="50" />
									</td>
								</tr>
								<tr> 
									<td align="right">
										<span class="modFormFieldLbl">Mark</span>
									</td>
									<td class="text" align="left">
										<html:select  property="markAs" styleClass="selectField">
											<html:option value=""></html:option> 
											<html:option value="D">Discontinued</html:option> 
								        </html:select>
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
         		 	<a href='/aims/devices.do?task=view' class='a'>
				      	<img src="images/back_b.gif"  border="0" alt="Back">
					</a>
       		 </td>
             <td align="right">   
               <img src="images/spacer.gif" width="10" height="1">
                    <input type="image" src="images/save_b.gif" border="0" alt="save" onClick="select_all(document.DevicesEditForm.platform);">
             </td>
        </tr>
	    </table>
	</td>
	
    </tr>
</table>
</html:form>

