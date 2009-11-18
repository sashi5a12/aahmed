<%@	page language="java"
	import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*,com.netpace.aims.model.core.*,com.netpace.aims.controller.application.*"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.apache.poi.util.StringUtil"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@	taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@	taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>


<jsp:useBean id="page_id" type="java.lang.Integer" scope="request" />
<jsp:useBean id="page_max" type="java.lang.Integer" scope="request" />
<jsp:useBean id="sort_field" class="java.lang.String" scope="request" />
<jsp:useBean id="sort_order" class="java.lang.String" scope="request" />

<jsp:useBean id="app_type" class="java.lang.String" scope="request" />
<jsp:useBean id="filter_text" class="java.lang.String" scope="request" />
<jsp:useBean id="filter_field" class="java.lang.String" scope="request" />


<%		
	String manageContentUrl = ConfigEnvProperties.getInstance().getProperty("all.approved.java.apps.manageContent.url");
	String settlementAndReportUrl = ConfigEnvProperties.getInstance().getProperty("all.approved.java.apps.settlementreporting.url");
%>

<script >

function submitForm(url){
	//alert(url);
	document.forms[1].action=url;
	document.forms[1].submit();
}

function gotoExternalLink(url)
{
	document.forms[1].action=url;
	document.forms[1].submit();
}

function jumpTo(){

	var url="<bean:message key="ManageApplicationForm.app.java.approvedApps.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?"
	url +="task=view&isPageLnk=yes&sort_field=<c:out value='${sort_field}'/>&sort_order=<c:out value='${sort_order}'/>";
	url += "&filter_field=<c:out value='${filter_field}'/>&filtertext=<c:out value='${filtertext}'/>"
	url += "&app_type=<c:out value='${app_type}'/>"
	url += "&page_id="+document.getElementsByName("page_id")[0].value;
	submitForm(url);
}


function onEnterJumpTo(e) {
	 if (e.which || e.keyCode) {
	    if ((e.which == 13) || (e.keyCode == 13)) {
			jumpTo();
       		return false;
    	}
    	return true;
   	}else{
		return true;   	
   	}
    
}


</script>
<!-- CONTENT START HERE-->
<form name="manageContent1" method="post">
<% if (((AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE)) {%>
	<div class="btnTopLine" style="padding-top: 10px; padding-bottom: 10px">
		<table cellspacing="0" border="0" cellpadding="0" width="100%">
			<tr>
				<td>
					<div class="redBtn" style="margin-left:5px;float:right;" id="manageContent" title="Manage Content">
						<div><div><div onClick="gotoExternalLink('<%=manageContentUrl%>');">Manage Content</div></div></div>
					</div>
					<div class="redBtn" style="margin-left:5px;float:right;" id="manageContent" title="Settlements and Reporting">
						<div><div><div onClick="gotoExternalLink('<%=settlementAndReportUrl%>');">Settlements and Reporting</div></div></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
<%}%>
</form>
<%@ include  file="/common/error.jsp" %>
<div class="clear"></div>
<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">
<div id="contentBox">
	<!-- DATA GRID START HERE -->
	<DIV class="homeColumnGrid lBox">
		<div style="margin-bottom: 13px">
		 	<strong>
				<bean:message key="ApprovedJavaAppsForm.approvedJavaApps.underTesting"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
			</strong>
		</div>
		<DIV class="headLeftCurveblk"></DIV>
			<H1>
				All Approved V CAST Apps List
			</H1>
			<DIV class="headRightCurveblk"></DIV>
				<html:form action="/approvedJavaApps.do">
					<DIV class="contentbox">
		
						<!-- DATA GRID START HERE -->
						<%-- START : DATA GRID header START HERE --%>

		                <table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
		                    <tr>
								<th nowrap="nowrap" width="80%" class="sortable"> Application Title		</th>
								<th nowrap="nowrap"  width="20%"  class="sortable"> Upload Content </th>
							</tr>
		
							<logic:empty name="AimsApplicationsInformation" scope="request">
								<tr>
									<td colspan="7" width="100%"><bean:message key="error.generic.no.records.for.view" /></td>
								</tr>
							</logic:empty>
							
						<%--END:  DATA GRID header HERE --%>
							
					<logic:iterate id="appsApp" name="AimsApplicationsInformation" scope="request">
						<c:set var="appKeyword" value="${appsApp.applicationKeyWord}" scope="request"/>
						<tr>
							<td align="left">
								<a href="<bean:write name="appsApp"	property="urlSetupAction"	/>?task=view&appsId=<bean:write	name="appsApp" property="appsId" />" class="a">
									<bean:write name="appsApp" property="title" filter="false" /></a>
							</td>
							<%							
								String uploadApprovedAppUrl = ConfigEnvProperties.getInstance().getProperty("all.approved.java.apps.uploadContent.url");
	
								uploadApprovedAppUrl = uploadApprovedAppUrl.replace("{0}",(String)request.getAttribute ("appKeyword"));
							 %>
							<td align="center">
								<a href="<%=uploadApprovedAppUrl%>" class="a">
									<img   src="images/back_top.jpg" alt="Upload" />
								</a>
							</td>
						</tr>
					</logic:iterate>
						</table>
							<!-- DATA GRID END HERE -->	
					</DIV>		
			<%-- PAGER START HERE --%>
			
			
				<DIV class="homeColumn lBox marginT">
		<DIV>
			<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
				<logic:notEmpty name="AimsApplicationsInformation" scope="request">
					<logic:greaterThan name="page_max" value="1">
						<tr>
							<td height="30" align="right" class="text">
								<%
									int startPageCount = 0;
									if (page_id.intValue() % 10 == 0)
										startPageCount = page_id.intValue() - 10 + 1;
									else
										startPageCount = page_id.intValue() - (page_id.intValue() % 10) + 1;
								%>
								<logic:greaterThan name="page_id" value="1">
									<a href='#' onclick='submitForm("<bean:message key="ManageApplicationForm.app.java.approvedApps.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?page_id=<%=page_id.intValue() - 1%>");return false;'><strong>Previous</strong></a>
									<img src="images/spacer.gif" width="10" height="1" />
									<%
										if (startPageCount - 10 > 0) {
									%>
										<a href='#' onclick='submitForm("<bean:message key="ManageApplicationForm.app.java.approvedApps.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?page_id=<%=startPageCount - 10%>");return false;'><img src="images/greyRndArrowL.gif" height="15" border="0" align="absbottom" /></a>
										<img src="images/spacer.gif" width="3" height="1" />
									<% }%>
								</logic:greaterThan>

								<% for (int pageCount = startPageCount; pageCount < startPageCount + 10; pageCount++) {%>
									<% if (pageCount > 0 && pageCount <= page_max.intValue()) {	%>
										<% if (pageCount == page_id.intValue()) { %>
											<b><%=pageCount%><img src="images/spacer.gif" width="1" height="1" /></b>
										<%} else { %>
											<a href='#'
												onclick='submitForm("<bean:message key="ManageApplicationForm.app.java.approvedApps.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?page_id=<%=pageCount%>");return false;'><%=pageCount%></a>
										<%}%>
									<%}%>
								<%}%>

								<logic:lessThan name="page_id" value="<%=page_max.toString()%>">
									<% if (startPageCount + 10 <= page_max.intValue()) { %>
										<img src="images/spacer.gif" width="3" height="1" />
										<a href='#' onclick='submitForm("<bean:message key="ManageApplicationForm.app.java.approvedApps.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?page_id=<%=startPageCount + 10%>");return false;'><img src="images/greyRndArrow.gif" height="15" border="0" align="absbottom" /></a>
									<%}%>
									<img src="images/spacer.gif" width="10" height="1" />
									<a href='#' onclick='submitForm("<bean:message key="ManageApplicationForm.app.java.approvedApps.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?page_id=<%=page_id.intValue() + 1%>");return false;'><strong>Next</strong></a>
								</logic:lessThan>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:notEmpty>
				<logic:notEmpty name="AimsApplicationsInformation" scope="request">
					<logic:greaterThan name="page_max" value="1">
						<tr>
							<td align="right">
                                <table cellpadding="0" cellspacing="0" style="margin-top:10px" align="right">
                                    <tr>
                                        <td>
                                            <strong>Jump to page&nbsp;</strong>
                                        </td>
                                        <td>
                                            <input type="text" name="page_id" size="4" value="<%=page_id.toString()%>" onkeydown="onEnterJumpTo(event);">
                                        </td>
                                        <td>
                                            <strong>&nbsp;of <%=page_max.toString()%>
                                            </strong>
                                        </td>
                                        <td>
                                            <div class="redBtn" style="float:right; margin-left:10px" id="Go"
                                                 title="Go">
                                                <div>
                                                    <div>
                                                        <div onclick="jumpTo();">Go</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:notEmpty>
				</html:form>
			</table>
		</DIV>
	</DIV>
			<!-- PAGER END HERE -->
			</DIV>
		</DIV>
	</div>
</div>
<!-- CONTENT END HERE -->


<div class="clear14"></div>

<form method="post" name="manageContent2">
<% if (((AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE)) {%>
	<div style="padding-top: 10px; padding-bottom: 10px;" class="btnTopLine">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody><tr>
				<td>
					<div class="redBtn" style="margin-left:5px;float:right;" id="manageContent" title="Manage Content">
						<div><div><div onClick="gotoExternalLink('<%=manageContentUrl%>');">Manage Content</div></div></div>
					</div>
					<div class="redBtn" style="margin-left:5px;float:right;" id="manageContent" title="Settlements and Reporting">
						<div><div><div onClick="gotoExternalLink('<%=settlementAndReportUrl%>');">Settlements and Reporting</div></div></div>
					</div>
				</td>
			</tr>
		</tbody></table>
	</div>
<%}%>
</form>