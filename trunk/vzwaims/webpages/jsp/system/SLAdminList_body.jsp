<%@ page language="java" %>

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
				<bean:message key="SalesLeadForm.vzwWelcome" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/> - <bean:message key="SalesLeadForm.vzwSalesLeadsListing" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>			
			</span>           
		</td>
	</tr>
		<%@ include  file="/common/error.jsp" %>
	<tr>
		<td width="20">&nbsp;</td>
		<td align="left" height="20">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%">
						<table class="sectable" width="100%">
							<tr class="sectitle"><td class="aimssecheading">Sales Lead Filter</td></tr>
							<tr bgcolor="#BBBBBB" >
								<td align="right">
									<table cellspacing="5" cellpadding="0" >
										<html:form action="/SLAdminSetup.do">
                                        <html:hidden  property="task" value="filterview" />	
										<tr>
											<td class="modFormFieldLbl">
											   <bean:message key="SalesLeadForm.view.filterBy"  bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:
											</td>
											<td class="modFormFieldLbl">
											<bean:define id="filterCol" type="java.lang.String" name="SalesLeadForm" property="filterField" />
												<html:select property="filterField" size="1" value="<%=filterCol%>" >
													<html:option value="alliance_name">Alliance Name</html:option>
													<html:option value="submitted_date">Submitted Date</html:option>
 												</html:select>
											</td>
											<td class="modFormFieldLbl">
												<bean:message key="SalesLeadForm.view.filterText"  bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/>:
											</td>
											<td class="inputbox">
												<html:text property="filterText" size="20"	/>
											</td>
											<td class="body" align="right">
											   <input type="image" src="images/go_b.gif" width="27" height="18">
											</td>
										</tr>
                                          
										</html:form>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="100%">&nbsp;</td>
				</tr>
	            <tr>
	            	<td align="center" valign="middle" bgcolor="#EBEBEB"> 
                  		<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr><td width="100%">
								<table class="sectable" width="100%">
									<tr class="sectitle"><td colspan="2" class="aimssecheading">List of Sales Lead Submitted</td></tr>
									<tr> 
										<td colspan=2> 
											<table width="100%" class="tabletop" cellspacing="0" cellpadding="5" >
							                    <tr bgcolor="#BBBBBB"> 
							                      	<td class="firstcell" align="left"><span class="modFormFieldLbl"><bean:message key="SalesLeadForm.allianceName" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/></span></td>
							                      	<td class="cell" align="left"><span class="modFormFieldLbl"><bean:message key="SalesLeadForm.submittedBy" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/></span></td>
							                      	<td class="cell" align="left"><span class="modFormFieldLbl"><bean:message key="SalesLeadForm.submittedDate" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/></span></td>
													<td class="cell" align="left"><span class="modFormFieldLbl"><bean:message key="SalesLeadForm.salesLeadDescShort" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/></span></td>
													<td class="cell" align="left"><span class="modFormFieldLbl"><bean:message key="SalesLeadForm.status" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/></span></td>
							                      	<td class="cell" align="center"><span class="modFormFieldLbl"><bean:message key="SalesLeadForm.edit" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/></span></td>
							                      	<td class="cell" align="center"><span class="modFormFieldLbl"><bean:message key="SalesLeadForm.delete" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/></span></td>
							                    </tr>
               									<%int iCount=0;%>     
								   				<logic:iterate id="asaleslead" name="AimsSalesLeads" type="com.netpace.aims.controller.alliance.SalesLeadForm">
							                    <tr> 
													<td class="firstcell" align="left"><bean:write name="asaleslead" property="allianceName" />&nbsp;</td>
													<td class="cell" align="left">
													<bean:write name="asaleslead" property="submittedBy" />&nbsp;
													<br/>
													<a href="mailto:<bean:write name="asaleslead" property="submittedByEmailAddress" />"><bean:write name="asaleslead" property="submittedByEmailAddress" /></a>													
													</td>
													<td class="cell" align="left"><bean:write name="asaleslead" property="submittedDate" formatKey="date.format" filter="true"/>&nbsp;</td>
													<td class="cell" align="left"><a href="SLAdminSetup.do?task=view&salesLeadId=<bean:write name="asaleslead" property="salesLeadId"/>"><bean:write name="asaleslead" property="salesLeadDesc" /></a></td>
													<td class="cell" align="center"><bean:write name="asaleslead" property="statusDesc" />&nbsp;</td>
													<td class="cell" align="center"><a href="SLAdminSetup.do?task=edit&salesLeadId=<bean:write name="asaleslead" property="salesLeadId"/>"><img src="images/edit_icon.gif" width="20" height="20" border="0" /></a></td>
													<td class="cell" align="center"><a href="SLAdminSetup.do?task=delete&salesLeadId=<bean:write name="asaleslead" property="salesLeadId"/>"><img src="images/delete_icon.gif" width="20" height="20" border="0" /></a></td>
							                    </tr>
								               <%iCount=1;%>
								               </logic:iterate>
               								   <%if (iCount==0) {%><tr><td class="firstcell" align="center" colspan="8"><bean:message key="SalesLeadForm.noSalesLead" bundle="com.netpace.aims.action.SALES_LEAD_MESSAGE"/></td></tr><%}%>
											   
											    <tr>
                                            <td colspan="8" align="right">
                                                <table >
                                                    <tr align="right"> 
                                                        <td>
                                                              <logic:greaterThan  name="page_id" value="1" >
                                                                    <a href='/aims/SLAdminSetup.do?page_id=<%=page_id.intValue() - 1%>' class="a">
                                                                        <bean:message key="images.previous.icon" />
                                                                  </a>
                                                              </logic:greaterThan>
                                                        </td> 
														<logic:greaterThan  name="page_max" value="1" >
														 	<td class="text" align="center"> Page <%=page_id.toString()%> of <%=page_max.toString()%> </td>
														</logic:greaterThan>
                                                        <td>
                                                              <logic:lessThan name="page_id" value="<%=page_max.toString()%>" >
                                                                    <a href='/aims/SLAdminSetup.do?page_id=<%=page_id.intValue() + 1%>' class="a">
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
							</td></tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>