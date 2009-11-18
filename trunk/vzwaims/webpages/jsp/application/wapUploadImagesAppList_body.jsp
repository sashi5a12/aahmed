<%@	page language="java"  %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>


<jsp:useBean id="page_id"	class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="sort_field" class="java.lang.String" scope="request"/>
<jsp:useBean id="sort_order" class="java.lang.String" scope="request"/>

<script language="javascript">
    function gotowapAppImageUpload(appsId) {
        var popupURL = "/aims/wapAppImageUpload.do?task=edit&appsId="+appsId;
        var childWindow = window.open(popupURL,"wndWapAppImageUpload","menubar=no,location=no,resizable=no,toolbar=no,width=600,height=425,scrollbars=yes");
        if (childWindow.opener == null) childWindow.opener = self;
        childWindow.focus();
    }
</script>

<table width="100%"	border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td	width="20">&nbsp;</td>
		<td	width="100%">
			<span	class="aimsmasterheading">
				<bean:message	key="ManageApplicationForm.view.welcome"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>	-
				Upload Wap Application Images
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
									<tr	class="sectitle"><td class="aimssecheading">Wap Application Images Upload</td></tr>
									<tr>
										<td	bgcolor="#EBEBEB">
											<table width="100%"	border="0" class="tabletop"	cellspacing="0"	cellpadding="2"	>
												<tr	bgcolor="#BBBBBB">
													<td class="firstcell"	align="center">
														<a href='<bean:message key="ManageApplicationForm.wap.app.upload.images.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=4&sort_order=asc'>
															<strong>
																<bean:message	key="ManageApplicationForm.view.applicationTitle"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
															</strong>
														</a>
													</td>
													<td class="cell"	align="center">
														<a href='<bean:message key="ManageApplicationForm.wap.app.upload.images.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=7&sort_order=asc'>
															<strong>
																<bean:message	key="ManageApplicationForm.view.dateSubmitted"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
															</strong>
														</a>
													</td>
													<td class="cell"	align="center">
														<a href='<bean:message key="ManageApplicationForm.wap.app.upload.images.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=9&sort_order=asc'>
															<strong>
																<bean:message	key="ManageApplicationForm.view.currentStatus"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
															</strong>
														</a>
													</td>
													<td class="cell" align="center">
														<strong>
															Upload Images
														</strong>
													</td>

												</tr>

												<logic:empty name="AllianceApplicationsInformation" scope="request">
													<tr><td	colspan="3" width="100%">
														<bean:message	key="error.generic.no.records.for.view"/>
													</td></tr>
												</logic:empty>

												<logic:iterate id="appsApp"	name="AllianceApplicationsInformation" scope="request" type="com.netpace.aims.bo.application.ApplicationInformation">
													<tr>
    													<td class="firstcell"	align="left">
															<a href="<bean:write name="appsApp"	property="urlSetupAction" />?task=view&appsId=<bean:write name="appsApp" property="appsId" />" class="a">
																<bean:write	name="appsApp" property="title"	/>
															</a>
														</td>
														<td class="cell"	align="left">
															<bean:write	name="appsApp" property="createdDate"	formatKey="date.format"	filter="true"	/>&nbsp;
														</td>
														<td class="cell"	align="left">
															<bean:write	name="appsApp" property="phaseName"	/>
														</td>
														<td class="cell" align="center">
                                                        	<a href="#" onclick="javascript:gotowapAppImageUpload(<bean:write name="appsApp" property="appsId"/>);" class="a">
                                                                <bean:message key="images.upload.icon" />
                                                        	</a>
														</td>

													</tr>
												</logic:iterate>

                                            </table>
										</td>
									</tr>

                                    <%--- paging start --%>
                                    <logic:notEmpty name="AllianceApplicationsInformation" scope="request">
										<logic:greaterThan name="page_max" value="1" >
											<tr>
												<td	align="center">
													<table width = "80%">
		                      							<tr>
															<td class="text" align="center"><b><br/></b></td>
														</tr>
		                      							<tr>
															<td class="text" align="center">
																<%
																	int startPageCount = 0;
																	if (page_id.intValue() % 10 == 0)
																		startPageCount = page_id.intValue() - 10 + 1;
																	else
																		startPageCount = page_id.intValue() - (page_id.intValue() % 10) + 1;
																%>

																<logic:greaterThan	name="page_id" value="1" >
																	<a href='<bean:message key="ManageApplicationForm.wap.app.upload.images.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&page_id=<%=page_id.intValue() - 1%>'><strong>Previous</strong></a><img src="images/spacer.gif" width="10" height="1"/>
																	<%if (startPageCount - 10 > 0) {%>
																		<a href='<bean:message key="ManageApplicationForm.wap.app.upload.images.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&page_id=<%=startPageCount - 10%>'><img src="images/previous_icon.gif" height="15" border="0" align="absbottom"/></a><img src="images/spacer.gif" width="3" height="1"/>
																	<% } %>
																</logic:greaterThan>

																<% for (int pageCount = startPageCount; pageCount < startPageCount + 10; pageCount++){%>
																	<%if (pageCount > 0 && pageCount <= page_max.intValue()) {%>
																		<%if (pageCount == page_id.intValue()) {%>
																			<b><%=pageCount%><img src="images/spacer.gif" width="1" height="1"/></b>
																		<% } else { %>
																			<a href='<bean:message key="ManageApplicationForm.wap.app.upload.images.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&page_id=<%=pageCount%>'><%=pageCount%></a><img src="images/spacer.gif" width="1" height="1"/>
																		<% } %>
																	<% } %>
																<% } %>

									                          	<logic:lessThan	name="page_id" value="<%=page_max.toString()%>"	>
																	<%if (startPageCount + 10 <= page_max.intValue()) {%>
																		<img src="images/spacer.gif" width="3" height="1"/><a href='<bean:message key="ManageApplicationForm.wap.app.upload.images.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write name="sort_order"/>&page_id=<%=startPageCount + 10%>'><img src="images/next_icon.gif" height="15" border="0" align="absbottom"/></a>
																	<% } %>
																	<img src="images/spacer.gif" width="10" height="1"/><a href='<bean:message key="ManageApplicationForm.wap.app.upload.images.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write name="sort_order"/>&page_id=<%=page_id.intValue() + 1%>'><strong>Next</strong></a>
																</logic:lessThan>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td class="text" align="center">
													<html:form action="/wapAppImageUploadList.do">
														<table cellspacing="8" cellpadding="0">
															<tr>
																<td	class="modFormFieldLbl" align="right">
																	<input type="hidden" name="task" value="view"/>
																	<input type="hidden" name="sort_field" value="<bean:write name="sort_field"/>"/>
																	<input type="hidden" name="sort_order" value="<bean:write name="sort_order"/>"/>
                                                                    <b>Jump to page <input type="text" name="page_id" size="4" value="<%=page_id.toString()%>"> of <%=page_max.toString()%></b>
																</td>
																<td class="body" align="left">
																	<input type="image" src="images/go_b.gif" width="27" height="18">
																</td>
															</tr>
														</table>
													</html:form>
												</td>
											</tr>
										</logic:greaterThan>
									</logic:notEmpty>
                                    <%-- paging end --%>
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