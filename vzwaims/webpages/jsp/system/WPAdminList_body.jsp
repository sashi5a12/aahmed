<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
        	<span class="aimsmasterheading">
				<bean:message key="WhitePaperForm.adminWelcomeScreen" /> - <bean:message key="WhitePaperForm.whitePaperListing" />
			</span>           
		</td>
	</tr>
	<tr>
		<td width="20">&nbsp;</td>
		<td align="left" height="20">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	               <td> 
	            	<logic:messagesPresent>
	      			<html:messages id="error" header="errors.header" footer="errors.footer">
	      				<bean:write name="error"/><br />
	      			</html:messages>
	            	</logic:messagesPresent>		
	         		</td>
	         	</tr>
	            <tr>
	            	<td align="center" valign="middle" bgcolor="#EBEBEB"> 
                  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr><td width="100%">
								<table class="sectable" width="100%">
									<tr class="sectitle"><td colspan="2" class="aimssecheading"><bean:message key="WhitePaperForm.view.filter" /></td></tr>
									<tr bgcolor="#BBBBBB"> 
										<td colspan=2 align="right" class="text">
											<table cellspacing="5" cellpadding="0" >
												<html:form action="/WPAdminSetup.do">
												<tr>
													<td class="modFormFieldLbl">
													       <bean:message key="WhitePaperForm.view.filterBy" />:
													</td>
													<td class="modFormFieldLbl">
													
													<bean:define id="filterCol" type="java.lang.String" name="WhitePaperFilterForm" property="filterField" />
													<html:select property="filterField" size="1" value="<%=filterCol%>" >
														<html:option value="alliance_name">Alliance Name</html:option>
														<html:option value="submitted_date">Submitted Date</html:option>
													</html:select>
																																			
													</td>
													<td class="modFormFieldLbl">
											                   <bean:message key="WhitePaperForm.view.filterText" />:
													</td>
													<td class="inputbox">
													   <html:text property="filterText" size="15"	/>
													</td>
													<td class="body" align="right">
													   <input type="image" src="images/go_b.gif" width="27" height="18">
													</td>
												</tr>
												</html:form>
											</table>
										</td>
									</tr>
									<tr> 
										<td align="center" valign="middle" bgcolor="#EBEBEB"> 
					                  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr><td width="100%">
													<table class="sectable" width="100%">
														<tr class="sectitle"><td colspan="2" class="aimssecheading">List of White Papers Submitted</td></tr>
										<tr> 
										<td colspan=2> 
											<table width="100%" class="tabletop" cellspacing="0" cellpadding="5" >
							                    <tr bgcolor="#BBBBBB"> 
													<td class="firstcell" align="left"><span class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperAllianceName" /></span></td>
													<td class="cell" align="left"><span class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperName" /></span></td>
													<td class="cell" align="left"><span class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperSubmitDate" /></span></td>
													<td class="cell" align="center"><span class="modFormFieldLbl"><bean:message key="WhitePaperForm.whitePaperStatus" /></span></td>
							                    </tr>
												<%int iCount=0;%>     
												<logic:iterate id="awpaper" name="AimsWhitePapers" type="com.netpace.aims.controller.system.AimsAllianceWhitePaperExt">
												<tr> 
													<td class="firstcell" align="left"><bean:write name="awpaper" property="allianceName" />&nbsp;</td>
													<td class="cell" align="left"><a href="WPAdminSetup.do?task=view&whitePaperId=<bean:write name="awpaper" property="whitePaperId"/>"><bean:write name="awpaper" property="whitePaperName" /></a></td>
													<td class="cell" align="left"><bean:write name="awpaper" property="submittedDate" formatKey="date.format" filter="true"/>&nbsp;</td>
													<td class="cell" align="center"><bean:write name="awpaper" property="whitePaperStatusDesc" />&nbsp;</td>
												</tr>
												<%iCount=1;%>
												</logic:iterate>
												<%if (iCount==0) {%><tr><td class="firstcell" align="center" colspan="4"><bean:message key="WhitePaperForm.whitePaperNotFound"/></td></tr><%}%>
													 <tr>
												<td colspan="7" align="right">
													<table >
														<tr align="right"> 
															<td>
															  <logic:greaterThan  name="page_id" value="1" >
																  <a href='/aims/WPAdminSetup.do?page_id=<%=page_id.intValue() - 1%>&filter_field=<bean:write name="WhitePaperFilterForm" property="filterField" />&filter_text=<bean:write name="WhitePaperFilterForm" property="filterText" />' class="a">
																		<bean:message key="images.previous.icon" />
																  </a>
															  </logic:greaterThan>
															</td> 
															<logic:greaterThan  name="page_max" value="1" >
																<td class="text" align="center"> Page <%=page_id.toString()%> of <%=page_max.toString()%> </td>
															</logic:greaterThan>
															<td>
														  <logic:lessThan name="page_id" value="<%=page_max.toString()%>" >
																<a href='/aims/WPAdminSetup.do?page_id=<%=page_id.intValue() + 1%>&filter_field=<bean:write name="WhitePaperFilterForm" property="filterField" />&filter_text=<bean:write name="WhitePaperFilterForm" property="filterText" />' class="a">
																	<bean:message key="images.next.icon" />
																</a>
														  </logic:lessThan>
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