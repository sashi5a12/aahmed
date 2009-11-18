<%@	page language="java" import="com.netpace.aims.bo.security.*, com.netpace.aims.bo.newmarketing.*, com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.newmarketing.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<jsp:useBean id="page_id"	class="java.lang.Integer"	scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="app_type" class="java.lang.String"	scope="request"/>
<jsp:useBean id="sort_field" class="java.lang.String"	scope="request"/>
<jsp:useBean id="sort_order" class="java.lang.String"	scope="request"/>
<jsp:useBean id="filter_text"	class="java.lang.String" scope="request"/>
<jsp:useBean id="filter_field" class="java.lang.String"	scope="request"/>

<%
boolean isVerizonUser = false, isAllianceUser = false;

isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE);
isAllianceUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE);
%>


<table width="100%"	border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td	width="20">&nbsp;</td>
		<td	width="100%">
			<span	class="aimsmasterheading">
				View Submitted Contents
			</span>
		</td>
	</tr>
	<tr>
		<td	width="20">&nbsp;</td>
		<td	align="left" height="20">
			<table	width="100%" border="0"	cellspacing="0"	cellpadding="0">
				<tr>
					<td>
						<logic:messagesPresent>
							<html:messages id="error"	header="errors.header" footer="errors.footer">
								<bean:write	name="error"/><br	/>
							</html:messages>
						</logic:messagesPresent>
					</td>
				</tr>
				<tr>
					<td>
						<logic:messagesPresent>
							<html:messages id="message"	message="true" header="messages.header"	footer="messages.footer">
								<bean:write	name="message"/><br	/>
							</html:messages>
						</logic:messagesPresent>
					</td>
				</tr>
				<tr>
					<td	align="center" valign="middle" bgcolor="#FFFFFF">
						<table width="100%"	border="0" cellspacing="0" cellpadding="0">
							<tr><td	width="100%">
								<table class="sectable"	width="100%">
									<tr	class="sectitle"><td class="aimssecheading">Contents Filter</td></tr>
									<tr	bgcolor="#EBEBEB"	>
										<td	align="right">
											<table cellspacing="5" cellpadding="0" >
												<html:form action="/mktContentsViewDelete.do">
													<html:hidden	property="task"	value="filterview" />
													<html:hidden	property="sortField" />
													<html:hidden	property="sortOrder" />
													<html:hidden	property="typeOfView"/>
													<tr>
														<td	class="modFormFieldLbl">
															<bean:message key="ManageApplicationForm.view.filterBy"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:
														</td>
														<td	class="modFormFieldLbl">
															<html:select property="filterField"	size="1">
																<%if ( ((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
																	<html:option value="company_name">Company Name</html:option>
																<% }else {}%>
																<html:option value="content_title">Content Title</html:option>
																<html:option value="submitted_date">Submitted Date</html:option>
																<html:option value="status">Status</html:option>
															</html:select>
														</td>
														<td	class="modFormFieldLbl">
															<bean:message key="ManageApplicationForm.view.filterText"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:
														</td>
														<td	class="inputbox">
															<html:text	property="filterText"	size="20"	/>
														</td>
														<td	class="body" align="right">
															<input	type="image" src="images/go_b.gif" width="27"	height="18">
														</td>
													</tr>
												</html:form>
											</table>
										</td>
									</tr>
								</table>
							</td></tr>
							<tr><td	width="100%">&nbsp;</td></tr>
							
							<tr><td	width="100%">
								<table class="sectable"	width="100%">
									<tr	class="sectitle"><td class="aimssecheading">Marketing Content List</td></tr>
									<tr>
										<td	bgcolor="#EBEBEB">
											<table width="100%"	border="0" class="tabletop"	cellspacing="0"	cellpadding="2"	>
												<tr	bgcolor="#BBBBBB">
													<td class="firstcell" align="center">
														
														<%if (isVerizonUser) {%>
																<a href='mktContentsViewDelete.do?task=view&sort_field=company_name&sort_order=asc&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write	name="filter_text"/>&app_type=<bean:write name="app_type"/>'>
																	<strong>
																		Company Name
																	</strong>
																</a>
															</td>
															<td	class="cell" align="center">
														<%	}else	{}%>

														<a href='mktContentsViewDelete.do?task=view&sort_field=content_title&sort_order=asc&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write   name="filter_text"/>&app_type=<bean:write name="app_type"/>'>
															<strong>
																Content Title
															</strong>
														</a>
													</td>
													<td class="cell"	align="center">
														<a href='mktContentsViewDelete.do?task=view&sort_field=submitted_date&sort_order=asc&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write   name="filter_text"/>&app_type=<bean:write name="app_type"/>'>
															<strong>
																Submitted Date
															</strong>
														</a>
													</td>
                                                    
                                                    <%if (isVerizonUser) {%>
                                                        <td class="cell"    align="center">
                                                            <a href='mktContentsViewDelete.do?task=view&sort_field=expiry_date&sort_order=asc&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write   name="filter_text"/>&app_type=<bean:write name="app_type"/>'>
                                                                <strong>
                                                                    Expiry Date
                                                                </strong>
                                                            </a>
                                                        </td>
                                                    <%  }else   {}%>

													<td class="cell"	align="center">
														<a href='mktContentsViewDelete.do?task=view&sort_field=status&sort_order=asc&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write   name="filter_text"/>&app_type=<bean:write name="app_type"/>'>
															<strong>
																Status
															</strong>
														</a>
													</td>
													<td class="cell" align="center"><strong>Edit</strong></td>
													<%if (isAllianceUser) {%>
                                                        <td class="cell" align="center"><strong>Delete</strong></td>
                                                    <%  }else   {}%>
												</tr>
												
												<logic:empty name="MarketingContentsInformation" scope="request">
													<tr><td	colspan="3" width="100%">
														<bean:message	key="error.generic.no.records.for.view"/>
													</td></tr>
												</logic:empty>
												
												<logic:iterate id="mktContent"	name="MarketingContentsInformation" scope="request">
													<tr>
														<td class="firstcell" align="left">
                                                            <%if (isVerizonUser) {%>
																	<a href="/aims/allianceStatus.do?task=view&alliance_id=<bean:write	name="mktContent" property="allianceId" />" class="a">	
																		<bean:write name="mktContent"	property="companyName" />
																	</a>
																</td>
																<td class="cell"	align="left">																		 
															<%	}else	{}%>

                                                            <a href="<bean:write name="mktContent"	property="urlSetupAction" />?task=view&mktContentId=<bean:write name="mktContent" property="creativeContentId" />" class="a">
                                                                <bean:write    name="mktContent" property="contentTitle" />
                                                            </a>                                                            
														</td>
														<td class="cell"	align="left">
															<bean:write	name="mktContent" property="submittedDate" />&nbsp;
														</td>
                                                        
                                                        <%if (isVerizonUser) {%>
                                                            <td class="cell"    align="left">
                                                                <bean:write name="mktContent" property="expiryDate" />&nbsp;
                                                            </td>                                                        
                                                        <%  }else   {}%>
                                                        
														<td class="cell"	align="left">
															<bean:write	name="mktContent" property="status" />
														</td>
														<td class="cell"	align="center">
															<logic:equal name="mktContent"	property="allowEdit" value="true">
																<a href="<bean:write name="mktContent"	property="urlSetupAction" />?task=edit&mktContentId=<bean:write name="mktContent" property="creativeContentId" />" class="a">
																	<bean:message	key="images.edit.icon" />
																</a>
															</logic:equal>
															<logic:equal name="mktContent"	property="allowEdit" value="false">
																&nbsp;
															</logic:equal>
														</td>
                                                        
                                                        <%if (isAllianceUser) {%>
                                                            <td class="cell"	align="center">
    															<logic:equal name="mktContent"	property="allowDelete" value="true">
    																<a href="<bean:write name="mktContent"	property="urlSetupAction" />?task=delete&mktContentId=<bean:write	name="mktContent" property="creativeContentId"/>&sort_field=<bean:write	name="sort_field"/>&sort_order=<bean:write name="sort_order"/>&filter_field=<bean:write	name="filter_field"/>&filter_text=<bean:write	name="filter_text"/>&app_type=<bean:write	name="app_type"/>&page_id=<bean:write	name="page_id"/>"	class="a" onClick="javascript:if (window.confirm('Are you sure you want to delete this Content?')) { return true;} else { return false;}">
    																	<bean:message	key="images.delete.icon" />
    																</a>
    															</logic:equal>
    															<logic:equal name="mktContent"	property="allowDelete" value="false">
    																&nbsp;
    															</logic:equal>
    														</td>
                                                        <%  }else   {}%>
													</tr>
												</logic:iterate>
											</table>
										</td>
									</tr>
									
									<logic:notEmpty name="MarketingContentsInformation" scope="request">
										<tr>
											<td	align="right">
												<table width = "25%">
                        	                      	<tr align="right"> 
                        	                        	<td align="right" width="1%">
                        	                          	    <logic:greaterThan	name="page_id" value="1" >   
																<a href='<bean:message key="ManageApplicationForm.manage.app.view.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&app_type=<bean:write name="app_type"/>&page_id=<%=page_id.intValue() - 1%>'>
																	<bean:message	key="images.previous.icon" />
																</a>
															</logic:greaterThan> 
														</td>
														<td class="text" align="center"> Page <%=page_id.toString()%> of <%=page_max.toString()%> </td>
														</td>
														<td align="left" width="1%">
	                          	                            <logic:lessThan	name="page_id" value="<%=page_max.toString()%>"	>
																<a href='<bean:message key="ManageApplicationForm.manage.app.view.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&app_type=<bean:write name="app_type"/>&page_id=<%=page_id.intValue() + 1%>'>
																	<bean:message	key="images.next.icon" />
																</a>
															</logic:lessThan>
														</td>
													</tr>
												</table>
											</td>                          
										</tr>                                        
									</logic:notEmpty>
									
								</table>
							</td></tr>
							<tr><td	width="100%">&nbsp;</td></tr>
						
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>






