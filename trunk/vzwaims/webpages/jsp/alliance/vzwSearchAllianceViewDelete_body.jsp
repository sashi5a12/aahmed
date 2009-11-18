<%@ page language="java" %>

<%@ page import="com.netpace.aims.controller.alliance.*, com.netpace.aims.bo.alliance.*  "%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
			<span	class="aimsmasterheading">
				Alliance Management - 
                <logic:equal name="VZWAllianceForm" property="allianceType" value="ALL" scope="request">
                    All Alliances
                </logic:equal>
                <logic:equal name="VZWAllianceForm" property="allianceType" value="MY" scope="request">
                    My Alliances
                </logic:equal>
                <logic:equal name="VZWAllianceForm" property="allianceType" value="NEW" scope="request">
                    New Alliances
                </logic:equal>
		   </span>
		</td>
	</tr>
  <%@ include  file="/common/error.jsp" %>
	<tr>
		<td width="20">&nbsp;</td>	
		<td width="100%">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%">
						<table class="sectable" width="100%">
							<tr class="sectitle">
								<td class="aimssecheading">Alliances List</td>
							</tr>
							<tr>
								<td bgcolor="#EBEBEB">
									<table width="100%" border="0" class="tabletop" cellspacing="0" cellpadding="2" >
										<tr bgcolor="#BBBBBB">
											<td class="firstcell" align="center">
												<a href='/aims/vzwAllianceSearch.do?task=view&sort_field=1&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
													<strong>
														Alliance Name
													</strong>
												</a>
											</td>
											<td class="cell" align="center">
												<a href='/aims/vzwAllianceSearch.do?task=view&sort_field=9&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
													<strong>
														Alliance Administrator
													</strong>
												</a>
											</td>
											<td class="cell" align="center">
												<a href='/aims/vzwAllianceSearch.do?task=view&sort_field=3&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
													<strong>
														Alliance Created Date
													</strong>
												</a>
											</td>
											<td class="cell" align="center">
												<a href='/aims/vzwAllianceSearch.do?task=view&sort_field=5&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
													<strong>
														VZW Account Manager
													</strong>
												</a>
											</td>
											<td class="cell" align="center">
												<a href='/aims/vzwAllianceSearch.do?task=view&sort_field=4&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
													<strong>
														Alliance Status
													</strong>
												</a>
											</td>
											<td class="cell" align="center">
												<a href='/aims/vzwAllianceSearch.do?task=view&sort_field=12&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />'>
                                                    <strong>
                                                        On/Off Hold
                                                    </strong>
                                                </a>
											</td>
											<td class="cell" align="center">
												<strong>
													<bean:message key="ManageApplicationForm.view.delete"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
												</strong>
											</td>
										</tr>
                                        <logic:iterate id="vzwAlliance" name="VZWAlliances" scope="request">
                                        <tr>
                                            <td class="firstcell" align="left">
												<a href='/aims/allianceStatus.do?task=view&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />' class="a">
													<bean:write name="vzwAlliance" property="companyName" />
												</a>
                                            </td>
                                            <td class="cell" align="left">
                                                <bean:write name="vzwAlliance" property="allianceAdminFirstName" /> 
                                                <bean:write name="vzwAlliance" property="allianceAdminLastName" /> &nbsp;
                                            </td>
                                            <td class="cell" align="center">
                                                <bean:write name="vzwAlliance" property="dateEstablished" formatKey="date.format" filter="true" />
                                                &nbsp;
                                            </td>
                                            <td class="cell" align="left">
                                                <bean:write name="vzwAlliance" property="vzwAccMgrFirstName" /> 
                                                <bean:write name="vzwAlliance" property="vzwAccMgrLastName" /> &nbsp;
                                            </td>
                                            <td class="cell" align="center">
                                                <bean:write name="vzwAlliance" property="status" />     
                                            </td>
                                            <td class="cell" align="center">
                                                <logic:equal name="vzwAlliance" property="isOnHold" value="Y">
                                                    <a href='/aims/vzwAllianceSearch.do?task=view&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&on_hold=N&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue()%>' class="a">
                                                        On Hold
                                                    </a>
                                                </logic:equal>
                                                <logic:equal name="vzwAlliance" property="isOnHold" value="N">
                                                    <a href='/aims/vzwAllianceSearch.do?task=view&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&on_hold=Y&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue()%>' class="a">
                                                        Off Hold
                                                    </a>
                                                </logic:equal>
                                            </td>
                                            <td class="cell" align="center">
                                                <a href='/aims/vzwAllianceSearch.do?task=view&sort_field=<bean:write name="VZWAllianceForm" property="sortField" />&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&del_rec=Y&filter_field=<bean:write name="VZWAllianceForm" property="filterField" />&filter_text=<bean:write name="VZWAllianceForm" property="filterText" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />' class="a" onClick="javascript:if (window.confirm('Are you sure you want to delete this Alliance and ALL its related records? \n\r By clicking OK you acknowledge that all the applications, users \n\r related to this alliance will be deleted.')) { return true;} else { return false;}">
                                                    <bean:message key="images.delete.icon" />
                                                </a>
                                            </td>
                                        </tr>
                                        </logic:iterate>
                                        <tr>
                                            <td colspan="7" align="right">
                                                <table width = "10%">
                                                    <tr align="right"> 
                                                        <td>
                                                              <logic:greaterThan  name="page_id" value="1" >
                                                                    <a href='/aims/vzwAllianceSearch.do?task=view&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&comp_name=<bean:write name="vzwAlliance" property="allianceName" />&status=<bean:write name="VZWAllianceForm" property="status" />&mgr_name=<bean:write name="VZWAllianceForm" property="vzwAccMgrName" />&contract_id=<bean:write name="VZWAllianceForm" property="contractId" />&date_from=<bean:write name="VZWAllianceForm" property="dateEstablishedFrom" />&date_to=<bean:write name="VZWAllianceForm" property="dateEstablishedTo" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue() - 1%>' class="a">
                                                                        <bean:message key="images.previous.icon" />
                                                                  </a>
                                                              </logic:greaterThan>
                                                        </td> 
                                                        <td>
                                                              <logic:lessThan name="page_id" value="<%=page_max.toString()%>" >
                                                                    <a href='/aims/vzwAllianceSearch.do?task=view&alliance_id=<bean:write name="vzwAlliance" property="allianceId" />&comp_name=<bean:write name="vzwAlliance" property="allianceName" />&status=<bean:write name="VZWAllianceForm" property="status" />&mgr_name=<bean:write name="VZWAllianceForm" property="vzwAccMgrName" />&contract_id=<bean:write name="VZWAllianceForm" property="contractId" />&date_from=<bean:write name="VZWAllianceForm" property="dateEstablishedFrom" />&date_to=<bean:write name="VZWAllianceForm" property="dateEstablishedTo" />&all_type=<bean:write name="VZWAllianceForm" property="allianceType" />&page_id=<%=page_id.intValue() + 1%>' class="a">
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