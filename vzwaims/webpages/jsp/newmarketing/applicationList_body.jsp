<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.controller.newmarketing.MktApplicationFilterForm,java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%!
	String isAppSelected(Long appId, Long[] selectedApps, ArrayList appsOnPage) {

		appsOnPage.add(appId);
		if (selectedApps == null || selectedApps.length == 0) return "";

		for (int iCount=0; iCount < selectedApps.length ; iCount++)
			if (appId.equals(selectedApps[iCount]))
					return "checked";

		return "";

	}

	boolean isOnPage(ArrayList appsOnPage,Long appId) {

		for (int iCount=0; iCount < appsOnPage.size() ; iCount++)
			if (  ((Long)appsOnPage.get(iCount)).equals(appId))
					return true;

		return false;

	}
%>
<%
	MktApplicationFilterForm appFrm = (MktApplicationFilterForm) request.getAttribute("MktApplicationFilterForm");
	Long[] lngAppsSelected = appFrm.getAppsIds();
	ArrayList appsOnPage = new ArrayList();
	boolean bSelectedApp = false;
%>
<jsp:useBean id="TotalPages"	type="java.lang.Integer"	scope="request"/>
<script language="Javascript">
<!--
	var bSelectedApp = false;

	function submitReq(iTask)
	{
		bSelected = false;

		if (iTask == 1)
		{
			document.forms[0].task.value='previous';
			document.forms[0].submit();
		}
		else if (iTask == 2)
		{
			document.forms[0].task.value='next';
			document.forms[0].submit();
		}
		else if (iTask == 3)
		{
			document.forms[0].task.value='list';
			document.forms[0].submit();
		}
		else if (iTask == 4)
		{
			if ( !eval(document.forms[0].appsIds) )
			{
				alert('Sorry No Applications Available To Select');
				return;
			}
			else
			{
				// If selected application details are already passed from previous page just submit
				if (bSelectedApp) {
						document.forms[0].action = '/aims/newMktContReqCreateEdit.do';
						document.forms[0].task.value='create';
						document.forms[0].submit();
						return;
				}

				// else check for single checkbox otherwise check array of checkboxes for checked status
				if ( !eval(document.forms[0].appsIds.length))
				{
				 	if (!document.forms[0].appsIds.checked) {
					alert('Please Tick Checkbox Against Application \nTo Create Creative Content Request');
					return;} else {
						document.forms[0].action = '/aims/newMktContReqCreateEdit.do';
						document.forms[0].task.value='create';
						document.forms[0].submit();
					}
				}
				else
				{
					for (iCount = 0 ; iCount < document.forms[0].appsIds.length ; iCount++)
					{
						if (document.forms[0].appsIds[iCount].checked)
						{
							bSelected = true;
							break;
						}
					}
					if (bSelected)
					{
						document.forms[0].action = '/aims/newMktContReqCreateEdit.do';
						document.forms[0].task.value='create';
						document.forms[0].submit();
					}
					else
					{
						alert('Please Tick Atleast One Checkbox Against \nApplication To Create Creative Content Request');
					}
				}
			}


		}
	}
//-->
</script>
<html:form action="/newMktApplication.do">
<html:hidden property="task"/>
<html:hidden property="pageNo"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	    <td width="20">&nbsp;</td>
		<td width="100%">
            <span	class="aimsmasterheading">Application List</span>
		</td>
	</tr>
    <!--tr>
       <td width="20">&nbsp;</td>
       <td width="100%">
            &nbsp;
       </td>
    </tr-->
    	<%@ include  file="/common/error.jsp" %>
    					<tr>
    					   <td colspan="2">
    								<table class="sectable"	width="100%">
									<tr	class="sectitle"><td class="aimssecheading">Applications Filter</td></tr>
									<tr	bgcolor="#EBEBEB"	>
										<td	align="right">
											<table cellspacing="5" cellpadding="0" >
													<tr>
														<td	class="modFormFieldLbl">
															<bean:message key="ManageApplicationForm.view.filterBy"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:
														</td>
														<td	class="modFormFieldLbl">
															<html:select property="filterField"	size="1">
																<html:option value="applicationTitle">Application Title</html:option>
																<html:option value="companyName">Developer Name</html:option>
																<html:option value="deckName">Deck Placement</html:option>
															</html:select>
														</td>
														<td	class="modFormFieldLbl">
															<bean:message key="ManageApplicationForm.view.filterText"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:
														</td>
														<td	class="inputbox">
															<html:text	property="filterText"	size="20"	/>
														</td>
														<td	class="body" align="right">
															<a href="javascript:submitReq(3);//"><img src="images/go_b.gif" width="27"	height="18" border="0"></a>
														</td>
													</tr>
													<tr>
													   <td class="text" colspan="5" align="right"><a class="standardLink" href="mktSearchApplication.do">Advanced Search Options</a></td>
													</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
					 </tr>

	<tr>
		<td	width="20">&nbsp;</td>
    	<td width="100%" align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" valign="top">
						<table class="sectable" width="100%" height="100%">
							<tr class="sectitle"><td colspan="2" class="aimssecheading">Applications Available On Deck</td></tr>
                			<tr>
                    			<td colspan="2" align="center" valign="middle" bgcolor="#EBEBEB">
				          	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
			      			            <tr>
			      			      	        <td bgcolor="#EBEBEB">
			      		      		            <table width="100%" border="0" class="tabletop" cellspacing="0" cellpadding="2" >
			      	      			                <tr bgcolor="#BBBBBB">
			                                            <td class="firstcell" align="center">
			                                                <span class="modFormFieldLbl">&nbsp;</span>
			                                            </td>
			                                            <td class="cell" align="left">
			                                                <span class="modFormFieldLbl">Application Title</span>
			                                            </td>
														<td class="cell" align="left">
			                                                <span class="modFormFieldLbl">Developer Name</span>
			                                            </td>
														<td class="cell" align="center">
			                                                <span class="modFormFieldLbl">Deck Placement</span>
			                                            </td>
														<td class="cell" align="left">
			                                                <span class="modFormFieldLbl">Launch Date</span>
			                                            </td>
			                            	        </tr>
									   				<logic:iterate id="app" name="ApplicationList" type="com.netpace.aims.controller.newmarketing.MarketApplicationExt">
															<tr>
															 <td class="firstcell" align="left"><input type="checkbox" name="appsIds" value="<bean:write name="app" property="applicationId"/>" <%=isAppSelected(app.getApplicationId(),lngAppsSelected,appsOnPage)%>></td>
								                      <td class="cell" align="left"><a href="newMktApplicationView.do?task=view&applicationId=<bean:write name="app" property="applicationId"/>"><bean:write name="app" property="appTitle" /></a>&nbsp;</td>
								                      <td class="cell" align="left"><bean:write name="app" property="developerName" />&nbsp;</td>
								                      <td class="cell" align="center"><bean:write name="app" property="deckPlacement" />&nbsp;</td>
								                      <td class="cell" align="left"><bean:write name="app" property="launchDate" formatKey="date.format" filter="true"/>&nbsp;</td>
								                    </tr>
               										</logic:iterate>
               										<logic:empty name="ApplicationList">
               									  <tr>
																<td class="firstcell" align="center" colspan="5">No Application found.</td>
               									  </tr>
               										</logic:empty>
                   								</table>
			      			                </td>
			      		                </tr>
			                        </table>
      	            			</td>
                			</tr>
				 <logic:notEmpty name="ApplicationList">
    			<tr>
    			   <td colspan="2" align="right">
    			      <table border="0" cellspacing="0" cellpadding="0">
	               <tr><td><logic:greaterThan	name="MktApplicationFilterForm" property="pageNo" value="1" >
						<a href='javascript:submitReq(1);//'><bean:message	key="images.previous.icon" /></logic:greaterThan></a></td>
						<td>Page <bean:write name="app" name="MktApplicationFilterForm" property="pageNo" /> of <%=TotalPages.toString()%></td>
	               <td><logic:lessThan	name="MktApplicationFilterForm" property="pageNo" value="<%=TotalPages.toString()%>"	>
						<a href='javascript:submitReq(2);//'><bean:message	key="images.next.icon" /></a></logic:lessThan></td></tr>
						</table>
    			   </td>
    			</tr>
            </logic:notEmpty>
            			</table>
	    			</td>
    			</tr>
    			<tr>
    			   <td width="100%" align="right"><img src="images/spacer.gif" width="5" height="2"></td>
    			</tr>
    			<tr>
    			   <td width="100%" align="right"><a href='javascript:submitReq(4);//'><img src="images/create_b.gif" width="52" height="15" border="0"></a></td>
    			</tr>
			</table>

		</td>
	</tr>
</table>
<logic:notEmpty name="MktApplicationFilterForm" property="appsIds">
<logic:iterate id="appSel" name="MktApplicationFilterForm" property="appsIds" type="java.lang.Long">
	<%if ( !isOnPage(appsOnPage,appSel) ) {%><input type="hidden" name="appsIds" value="<bean:write name="appSel"/>"><% bSelectedApp = true; }%>
</logic:iterate>
	<%if (bSelectedApp){%><script language="javascript">bSelectedApp=true;</script><%}%>
</logic:notEmpty>
</html:form>
