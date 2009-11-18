<%@	page language="java"
	import="com.netpace.aims.controller.application.ApplicationHelper,com.netpace.aims.model.core.AimsUser,com.netpace.aims.util.AimsConstants"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@	taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>
<%@	taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<jsp:useBean id="page_id" type="java.lang.Integer" scope="request" />
<jsp:useBean id="page_max" type="java.lang.Integer" scope="request" />
<jsp:useBean id="app_type" class="java.lang.String" scope="request" />
<jsp:useBean id="sort_field" class="java.lang.String" scope="request" />
<jsp:useBean id="sort_order" class="java.lang.String" scope="request" />
<jsp:useBean id="filter_text" class="java.lang.String" scope="request" />
<jsp:useBean id="filter_field" class="java.lang.String" scope="request" />
<script language="javascript">
function createApplication(form,selectedIndex)
{
	if (form.createApp.options[form.createApp.selectedIndex].value == 0)
		return false;
	form.action=form.createApp.options[form.createApp.selectedIndex].value;
	form.submit();
}
function selectPlatformShowAll(platformId,s, ref){
	if(platformId=='<%=AimsConstants.BREW_PLATFORM_ID%>' && s=='<%=AimsConstants.FILTER_BREW_SHOW_ALL%>'){
		ref.checked=true;
	}
	else if(platformId=='<%=AimsConstants.ENTERPRISE_PLATFORM_ID%>' && s=='<%=AimsConstants.FILTER_ENT_SHOW_ALL%>'){
		ref.checked=true;
	}
	else if(platformId=='<%=AimsConstants.SMS_PLATFORM_ID%>' && s=='<%=AimsConstants.FILTER_SMS_SHOW_ALL%>'){
		ref.checked=true;
	}
	else if(platformId=='<%=AimsConstants.MMS_PLATFORM_ID%>' && s=='<%=AimsConstants.FILTER_MMS_SHOW_ALL%>'){
		ref.checked=true;
	}
	else if(platformId=='<%=AimsConstants.VCAST_PLATFORM_ID%>' && s=='<%=AimsConstants.FILTER_VCAST_SHOW_ALL%>'){
		ref.checked=true;
	}
	else if(platformId=='<%=AimsConstants.WAP_PLATFORM_ID%>' && s=='<%=AimsConstants.FILTER_WAP_SHOW_ALL%>'){
		ref.checked=true;
	}
	else if(platformId=='<%=AimsConstants.VZAPPZONE_PLATFORM_ID%>' && s=='<%=AimsConstants.FILTER_VZAPPZON_SHOW_ALL%>'){
		ref.checked=true;
	}
	else if(platformId=='<%=AimsConstants.JAVA_PLATFORM_ID%>' && s=='<%=AimsConstants.FILTER_JAVA_SHOW_ALL%>'){
		ref.checked=true;
	}
}


function selectStatusFromPlatform(platformId){
	var platforms=document.getElementsByName("selectedFilterPlatform");
	var statuses=document.getElementsByName("selectedFilterStatus");

	clearCurrentStatus();
	if (platformId){
		for(j=0; j<statuses.length; j++){
			s=statuses[j].value.split(",");
			selectPlatformShowAll(platformId,s,statuses[j]);
			if (platformId==s[1]){
				statuses[j].checked=true;
			}
		}
	}
	else {
		if (platforms[0].checked){
			statuses[0].checked=true;
		}
		else {
			for (i=0; i<platforms.length; i++){
				p=platforms[i].value;
				if (platforms[i].checked==true){
					for(j=0; j<statuses.length; j++){
						s=statuses[j].value.split(",");
						selectPlatformShowAll(p,s,statuses[j]);
						if (p==s[1]){
							statuses[j].checked=true;
						}
					}
				}
			}
		}
	}
}
function selectPlatFormFromStatus(platformId){
	var platforms=document.getElementsByName("selectedFilterPlatform");
	var statuses=document.getElementsByName("selectedFilterStatus");

	clearPlatform();
	if(platformId){
		for(j=0; j<platforms.length; j++){
			p=platforms[j].value;
			if (platformId==p){
				platforms[j].checked=true;
			}
		}
	}
	else{
		var count=0;
		var statusCount=0;
		for (i=0; i<statuses.length; i++){
			s=statuses[i].value.split(",");
			if (s[1] && statuses[i].checked==true){
				statusCount++;
				for(j=0; j<platforms.length; j++){
					p=platforms[j].value
					if (platforms[j].checked==false && p==s[1]){
						count++;
						platforms[j].checked=true;
					}
				}
			}
		}
		if (count == <%= AimsConstants.FILTER_PLATFORMS.length %>){
			clearPlatform();
			platforms[0].checked=true;
		}
		if (statusCount == "<c:out value='${requestScope.statusCount}'/>"){
			clearCurrentStatus();
			statuses[0].checked=true;
		}
	}
}
function clearCurrentStatus(){
	document.forms[2].clearStatus.value="true";
	var cb=document.getElementsByName("selectedFilterStatus");	
	for(i=0; i<cb.length; i++){
		cb[i].checked=false;
		cb[i].disabled=false;
	}	
}
function setCurrentStatus(){
	var cb=document.getElementsByName("selectedFilterStatus");	
	for(i=0; i<cb.length; i++){
		cb[i].checked=true;
	}	
}
function clearPlatform(){
	var cb=document.getElementsByName("selectedFilterPlatform");	
	for(i=0; i<cb.length; i++){
		cb[i].checked=false;
		cb[i].disabled=false;
	}	
}
function setPlatform(platformId){
	var cb=document.getElementsByName("selectedFilterPlatform");	
	if (platformId){
		clearPlatform();
		for(i=0; i<cb.length; i++){
			if(platformId==cb[i].value){
				cb[i].checked=true;
			}			
		}
	}
	else {
		/*for(i=0; i<cb.length; i++){
			cb[i].checked=true;
		}*/
		clearPlatform();
		cb[0].checked=true;	
	}
}
function submitForm(url){
	//alert(url);
	document.forms[2].action=url;
	document.forms[2].submit();
}

function selectAllPlatformStatus(platformId,ref,isLink){	
	var statuses=document.getElementsByName("selectedFilterStatus");
	var platforms=document.getElementsByName("selectedFilterPlatform");
	if(isLink){
		clearCurrentStatus();
		clearPlatform();
		for(i=0; i<statuses.length; i++){
			s=statuses[i].value.split(",");
			selectPlatformShowAll(platformId,s,statuses[i]);
			if (platformId==s[1]){
				statuses[i].checked=true;
			}
		}
		setPlatform(platformId);
	}
	else {
		if (ref.checked){
			for(i=0; i<statuses.length; i++){
				s=statuses[i].value.split(",");
				if (platformId==s[1]){
					statuses[i].checked=true;
				}
			}		
			setPlatform(platformId);
		}
		else {
			for(i=0; i<statuses.length; i++){
				s=statuses[i].value.split(",");
				if (platformId==s[1]){
					statuses[i].checked=false;
				}
			}
			for(i=0; i<platforms.length; i++){
				if (platformId==platforms[i].value){
					platforms[i].checked=false;
				}
			}
		}
	}
}
function jumpTo(){
	var url="<c:out value='${pageContext.request.contextPath}'/>/applicationsViewDelete.do?"
	url +="task=view&isPageLnk=yes&sort_field=<c:out value='${sort_field}'/>&sort_order=<c:out value='${sort_order}'/>";
	url += "&filter_field=<c:out value='${filter_field}'/>&filter_text=<c:out value='${filter_text}'/>"
	url += "&app_type=<c:out value='${app_type}'/>"
	submitForm(url);
}
function selectAll(e) {
	var targ
	if (!e) var e = window.event
	if (e.target) 
		targ = e.target
	else if (e.srcElement) 
		targ = e.srcElement
	if (targ.nodeType == 3) 
		targ = targ.parentNode				
	if (targ.type=="checkbox" && targ.value=="<%=AimsConstants.FILTER_SHOW_ALL %>"){
		var cb=document.getElementsByName(targ.name);
		for(i=1; i<cb.length; i++){
			if (targ.checked==true){
				cb[i].checked=false;
				cb[i].disabled=true;				
			}
			else {
				cb[i].checked=false;
				cb[i].disabled=false;									
			}
		}
	}
	else if (targ.type=="checkbox" && targ.value !="<%=AimsConstants.FILTER_SHOW_ALL %>"){	
		var cb=document.getElementsByName(targ.name);
		for(i=0; i<cb.length; i++){
			if (cb[i].value=="<%=AimsConstants.FILTER_SHOW_ALL %>"){
				cb[i].checked=false;
				//cb[i].disabled=true;
			}
		}		
	}
}
function singleSelect(cbName,selValue,form){
	var cb=document.getElementsByName(cbName);
	if (selValue=="<%=AimsConstants.FILTER_SHOW_ALL %>"){
		cb[0].checked=true;
		for(i=1; i<cb.length; i++){
			cb[i].checked=false;
			cb[i].disabled=true;
		}		
	}
	else {
		for(i=0; i<cb.length; i++){
			if (cb[i].value==selValue){
				cb[i].checked=true;
				cb[i].disabled=false;
			}
			else {
				cb[i].checked=false;
				cb[i].disabled=false;
			}
		}
	}
	if (form.page_id){
		form.page_id.value="1";
	}	
	form.submit();	
}
function setFilterDisableState(cbName){
	var cb=document.getElementsByName(cbName);
	if (cb[0].value=="<%=AimsConstants.FILTER_SHOW_ALL %>" && cb[0].checked==true){
		for(i=1; i<cb.length; i++){
			cb[i].checked=false;
			cb[i].disabled=true;			
		}
	}
	else if (cbName=='selectedFilterPlatform'){
		var count=0;
		for(i=1; i<cb.length; i++){
			if (cb[i].checked==true){
				count++;
			}
		}
		if (count == <%= AimsConstants.FILTER_PLATFORMS.length %>){
			cb[0].checked=true;
			cb[0].disabled=false;
			for(i=1; i<cb.length; i++){
				cb[i].checked=false;
				cb[i].disabled=true;			
			}
			clearCurrentStatus();
			var statuses=document.getElementsByName("selectedFilterStatus");
			statuses[0].checked=true;			
		}
	}
}
function onEnterJumpTo(e) {
	    if (e.which || e.keyCode) {
	        if ((e.which == 13) || (e.keyCode == 13)) {
	            jumpTo();
	            return false;
	        }
	    }
	    return true;
	}
</script>
<!-- CONTENT START HERE-->
<form name="createSubmitForm1" method="post">
<% if (((AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE)) {%>
	<div class="btnTopLine" style="padding-top: 10px; padding-bottom: 10px">
		<table cellspacing="0" border="0" cellpadding="0" width="100%">
			<tr>
				<td	align="right" width="92%">
					<strong><bean:message key="ManageApplicationForm.view.createNewApp" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></Strong>
					<img src="images/spacer.gif" width="10" height="1">
					<select name="createApp">
						<option value="0" selected>
							<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
						</option>
						
              	  	<logic:iterate id="platform"	name="ApplicationsFilterForm" property="platforms" scope="request">
                    	<bean:define id="platformId"><bean:write name="platform" property="platformId"/></bean:define>                    	  
                     
                    	<%if (ApplicationHelper.checkPlatformAccess(request, "create", Long.valueOf(platformId)) ) { %>
                    	
                    		<%if (AimsConstants.BREW_PLATFORM_ID.toString().equals( platformId ) )  {%>
                    			<option value="<bean:message key="ManageApplicationForm.brew.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			BREW
                    			</option>
                    		<%} %>
                    		
                    		<%if (AimsConstants.DASHBOARD_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.dashboard.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
					    			Dashboard 
								</option>
                    		<%} %>
                    		<%if (AimsConstants.ENTERPRISE_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.enterprise.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			JMA
                    			</option>
                    		<%} %>
                    		<%if (AimsConstants.MMS_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.mms.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			MMS
                    			</option>
                    		<%} %>
                    		<%if (AimsConstants.SMS_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.sms.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			SMS
                    			</option>	
                    		<%} %>
                    		<%if (AimsConstants.VCAST_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.vcast.app.setup.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			V CAST Video
                    			</option>	
                    		<%} %>
                    		<%if (AimsConstants.VZAPPZONE_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.vzAppZone.app.setup.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			VZAppZone
                    			</option>
                    		<%} %>
                    		<%if (AimsConstants.WAP_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.wap.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			WAP
                    			</option>	
                    		<%} %>
                    		<%if (AimsConstants.JAVA_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.java.ondeck.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			V CAST Apps
                    			</option>
                    			<option value="<bean:message key="ManageApplicationForm.java.offdeck.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			V CAST Apps More
                    			</option>
                    		<%} %>
                    		
                    	<%} %>
                    </logic:iterate>
						
					</select>&nbsp;
				</td>
				<td>
					<div class="redBtn" style="margin-left:5px;float:right;" id="create" title="Create Application">
						<div><div><div onClick="createApplication(document.forms[1], document.forms[1].createApp.selectedIndex);">Create</div></div></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
<%}%>
</form>
<%@ include  file="/common/error.jsp" %>
<c:choose>
	<c:when test="${requestScope.filterPlateform eq 'Showing All'}">
		<c:set var="plateformIcon" value="images/icon_filter.gif" scope="page"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="plateformIcon" value="images/icon_filter2.gif" scope="page"></c:set>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${requestScope.filterStatuses eq 'Showing All'}">
		<c:set var="statusIcon" value="images/icon_filter.gif" scope="page"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="statusIcon" value="images/icon_filter2.gif" scope="page"></c:set>
	</c:otherwise>
</c:choose>
<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<div id="contentBox">
	<!-- DATA GRID START HERE -->
	<DIV class="homeColumnGrid lBox">
		<DIV class="headLeftCurveblk"></DIV>
		<H1>
			<c:choose>
				<c:when test="${empty requestScope.cancel_search}">Applications List by (Search Criteria)</c:when>
				<c:otherwise>Application List</c:otherwise>
			</c:choose>
		</H1>
		<DIV class="headRightCurveblk"></DIV>
		<html:form action="/applicationsViewDelete.do">
			<html:hidden property="filterText" />
			<html:hidden property="filterField" />		
			<html:hidden property="task" value="filterview" />
			<html:hidden property="sortField" />
			<html:hidden property="sortOrder" />
			<html:hidden property="typeOfApplicationsToView" />
			<input type="hidden" name="clearStatus" value="<c:out value='${requestScope.clearStatus}'/>" />
			<input type="hidden" name="lastSortBy" value="<c:out value="${requestScope.lastSortBy}"/>" />
			<input type="hidden" name="app_type" value="<c:out value='${requestScope.app_type}'/>"/>			
			<c:if test="${not empty requestScope.cancel_search}">
				<input type="hidden" name="cancel_search" value="<c:out value='${requestScope.cancel_search}'/>" />
			</c:if>
			<DIV class="contentbox">
                <table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
                    <tr>
						<%
							if (((AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE)) {
						%>                    
						<th nowrap="nowrap" class="<c:choose>
          								<c:when test="${sort_field eq '2' and sort_order eq 'asc' }">sorted</c:when>
          								<c:when test="${sort_field eq '2' and sort_order eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						  </c:choose>">
							<a href='#' 
							   onclick='submitForm("<bean:message key="ManageApplicationForm.manage.app.view.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=2&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write	name="filter_field"/>&filter_text=<bean:write	name="filter_text"/>&app_type=<bean:write	name="app_type"/>");return false;'>
							   <strong><bean:message key="ManageApplicationForm.view.companyName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></strong></a>
						</th>
						<%} else {}	%>
						<th nowrap="nowrap" class="<c:choose>
          								<c:when test="${sort_field eq '3' and sort_order eq 'asc' }">sorted</c:when>
          								<c:when test="${sort_field eq '3' and sort_order eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						  </c:choose>">
							
							<a href='#' 
							   onclick='submitForm("<bean:message key="ManageApplicationForm.manage.app.view.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=3&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&app_type=<bean:write name="app_type"/>");return false;'>
							   <strong><bean:message key="ManageApplicationForm.view.platformName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></strong></a>
							<!-- PLATFORM FILTER POPUP START HERE -->
							<a href="#" onclick="return false;"><img src='<c:out value="${plateformIcon}"/>' name="filterLink1" width="11" height="11" border="0" align="absmiddle" id="filterLink1" rel="contentDiv1" title='Platform Name = "<c:out value="${requestScope.filterPlateform}"/>"'></a>
							<DIV id="contentDiv1" class="divContent">
								<div style="width:140px">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="10" align="left"><img src="images/login_header_left_curve.gif" width="10" height="21"></td>
											<td style="background-image:url(images/login_header_bg_tile.gif); background-repeat:repeat;" align="right">
												<a href="#" onClick="dropdowncontent.closeFilter('contentDiv1');return false;"><img style="margin-right:-10px" src="images/icon-close.gif" width="47" height="18" border="0" /></a>
											</td>
											<td width="10"> <img src="images/login_header_right_curve.gif" width="10" height="21"></td>
										</tr>
									</table>
								</div>
								<DIV class="divFormContent" style="	width:138px;">
									<table border="0" cellspacing="0" cellpadding="0" style="border:none; background-color:none;">
										<tr>
											<td>									
												<html:multibox property="selectedFilterPlatform"><%=AimsConstants.FILTER_SHOW_ALL%></html:multibox>
													<a href="#" onClick="clearCurrentStatus();document.forms[2].selectedFilterStatus[0].checked=true;singleSelect('selectedFilterPlatform','<%=AimsConstants.FILTER_SHOW_ALL%>',document.forms[2]);return false;"><%=AimsConstants.FILTER_LABEL_SHOW_ALL%></a>
											</td>
										</tr>
										<c:forEach var="platform" items="${ApplicationsFilterForm.filterPlatform}">
											<%
												
												String platform = ((Object[])pageContext.getAttribute("platform"))[0].toString();
												if ( platform.equalsIgnoreCase(AimsConstants.JAVA_PLATFORM_ID.toString()) && (ApplicationHelper.checkPlatformAccess(request, AimsConstants.VIEW_TASK, AimsConstants.JAVA_PLATFORM_ID)) ) 												
												{
											 %>
											 		<tr>
														<td align="left" valign="top">
															<html:multibox property="selectedFilterPlatform"><c:out value="${platform[0]}" /></html:multibox>
															<a href="#" onClick="selectStatusFromPlatform('<c:out value="${platform[0]}" />');singleSelect('selectedFilterPlatform','<c:out value="${platform[0]}" />',document.forms[2]);return false;">
																<c:out value="${platform[1]}" /></a>
														</td>
													</tr>
											 <%
											 	}
											 	else if ( !platform.equalsIgnoreCase(AimsConstants.JAVA_PLATFORM_ID.toString()))
											 	{
											  %>
													<tr>
														<td align="left" valign="top">
															<html:multibox property="selectedFilterPlatform"><c:out value="${platform[0]}" /></html:multibox>
															<a href="#" onClick="selectStatusFromPlatform('<c:out value="${platform[0]}" />');singleSelect('selectedFilterPlatform','<c:out value="${platform[0]}" />',document.forms[2]);return false;">
																<c:out value="${platform[1]}" /></a>
														</td>
													</tr>
											  <%
											  	}
											   %>											
										</c:forEach>
									</table>
								</DIV>
								<DIV class="divFormButton">
									<div class="redBtn" id="filter" title="Filter">
										<div><div><div onclick="selectStatusFromPlatform();setFilterDisableState('selectedFilterPlatform');validateFilter('selectedFilterPlatform',document.forms[2]);">Filter</div></div></div>
									</div>
								</DIV>
							</DIV>
							<!-- PLATFORM FILTER POPUP END HERE -->
						</th>
						<th nowrap="nowrap" style="width:200px" class="<c:choose>
          								<c:when test="${sort_field eq '4' and sort_order eq 'asc' }">sorted</c:when>
          								<c:when test="${sort_field eq '4' and sort_order eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						  </c:choose>">
							<a href='#' onclick='submitForm("<bean:message key="ManageApplicationForm.manage.app.view.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=4&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&app_type=<bean:write name="app_type"/>");return false;'>
								<strong><bean:message key="ManageApplicationForm.view.applicationTitle" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></strong></a>
						</th>
						<th nowrap="nowrap" class="<c:choose>
          								<c:when test="${sort_field eq '7' and sort_order eq 'asc' }">sorted</c:when>
          								<c:when test="${sort_field eq '7' and sort_order eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						  </c:choose>">
							<a href='#' onclick='submitForm("<bean:message key="ManageApplicationForm.manage.app.view.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=7&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write	name="filter_field"/>&filter_text=<bean:write	name="filter_text"/>&app_type=<bean:write	name="app_type"/>");return false;'>
								<strong><bean:message key="ManageApplicationForm.view.dateSubmitted" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></strong></a>
						</th>
						<th nowrap="nowrap" class="<c:choose>
          								<c:when test="${sort_field eq '9' and sort_order eq 'asc' }">sorted</c:when>
          								<c:when test="${sort_field eq '9' and sort_order eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						  </c:choose>">
							<a href='#' onclick='submitForm("<bean:message key="ManageApplicationForm.manage.app.view.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=9&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write	name="filter_field"/>&filter_text=<bean:write	name="filter_text"/>&app_type=<bean:write	name="app_type"/>");return false;'>
								<strong><bean:message key="ManageApplicationForm.view.currentStatus"bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></strong></a>











							<!-- STATUS FILTER POPUP START HERE -->
							<a href="#" onclick="return false;"><img src='<c:out value="${statusIcon}"/>' name="filterLink2" width="11" height="11" border="0" align="absmiddle" id="filterLink2" rel="contentDiv2" title='Current Status = "<c:out value="${requestScope.filterStatuses}"/>"'></a>
							<DIV id="contentDiv2" class="divContent">
								<div style="width:202px">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="10" align="left"><img src="images/login_header_left_curve.gif" width="10" height="21"></td>
											<td style="background-image:url(images/login_header_bg_tile.gif); background-repeat:repeat;" align="right">
												<a href="#" onClick="dropdowncontent.closeFilter('contentDiv2');return false;"><img style="margin-right:-10px" src="images/icon-close.gif" width="47" height="18" border="0" /></a>
											</td>
											<td width="10"><img src="images/login_header_right_curve.gif" width="10" height="21"></td>
										</tr>
									</table>
								</div>
								<DIV class="divFormContent" style="	width:200px; height:125px; ">
									<table border="0" cellspacing="0" cellpadding="0" style="border:none; background-color:none;">
										<tr>
											<td>									
												<html:multibox property="selectedFilterStatus"><%=AimsConstants.FILTER_SHOW_ALL%></html:multibox>
													<a href="#" onClick="setPlatform();singleSelect('selectedFilterStatus','<%=AimsConstants.FILTER_SHOW_ALL%>',document.forms[2]);return false;">
														<%=AimsConstants.FILTER_LABEL_SHOW_ALL%></a>
											</td>
										</tr>									
										<tr>
											<td style="padding: 3px"><label><strong>BREW</strong></label></td>
										</tr>
										<tr>
											<td>									
												<html:multibox property="selectedFilterStatus" onclick="selectAllPlatformStatus('1',this);">
													<%=AimsConstants.FILTER_BREW_SHOW_ALL%>
												</html:multibox>
													<a href="#" onClick="selectAllPlatformStatus('<%=AimsConstants.BREW_PLATFORM_ID%>',this,'true');document.forms[2].submit();return false;">
														<%=AimsConstants.FILTER_LABEL_BREW_SHOW_ALL%></a>
											</td>
										</tr>
										<c:forEach var="status"items="${ApplicationsFilterForm.filterBREW}">
											<tr>
												<td>
													<html:multibox property="selectedFilterStatus"><c:out value="${status[0]}" /></html:multibox>
													<a href="#" onClick="selectPlatFormFromStatus(<%=AimsConstants.BREW_PLATFORM_ID%>);singleSelect('selectedFilterStatus','<c:out value="${status[0]}" />',document.forms[2]);return false;">
														<c:out value="${status[1]}" /></a>
												</td>
											</tr>
										</c:forEach>

										<tr>
											<td style="padding: 3px"><label><strong>Dashboard</strong></label></td>
										</tr>
										<tr>
											<td>									
												<html:multibox property="selectedFilterStatus" onclick="selectAllPlatformStatus('43',this);">
													<%=AimsConstants.FILTER_DASHBOARD_SHOW_ALL%>
												</html:multibox>
													<a href="#" onClick="selectAllPlatformStatus('<%=AimsConstants.DASHBOARD_PLATFORM_ID%>',this,'true');document.forms[2].submit();return false;">
														<%=AimsConstants.FILTER_LABEL_DASHBOARD_SHOW_ALL%></a>
											</td>
										</tr>
										<c:forEach var="status" items="${ApplicationsFilterForm.filterDashboard}">
											<tr>
												<td>
													<html:multibox property="selectedFilterStatus"><c:out value="${status[0]}" /></html:multibox>
													<a href="#"onClick="selectPlatFormFromStatus(<%=AimsConstants.DASHBOARD_PLATFORM_ID%>);singleSelect('selectedFilterStatus','<c:out value="${status[0]}" />',document.forms[2]);return false;">
														<c:out value="${status[1]}" /></a>
												</td>
											</tr>
										</c:forEach>
										
										<tr>
											<td style="padding: 3px"><label><strong>JMA</strong></label></td>
										</tr>
										<tr>
											<td>									
												<html:multibox property="selectedFilterStatus" onclick="selectAllPlatformStatus('5',this);">
													<%=AimsConstants.FILTER_ENT_SHOW_ALL%>
												</html:multibox>
													<a href="#" onClick="selectAllPlatformStatus('<%=AimsConstants.ENTERPRISE_PLATFORM_ID%>',this,'true');document.forms[2].submit();return false;">
														<%=AimsConstants.FILTER_LABEL_ENT_SHOW_ALL%></a>
											</td>
										</tr>										
										<c:forEach var="status" items="${ApplicationsFilterForm.filterEnterprise}">
											<tr>
												<td>
													<html:multibox property="selectedFilterStatus"><c:out value="${status[0]}" /></html:multibox>
													<a href="#" onClick="selectPlatFormFromStatus(<%=AimsConstants.ENTERPRISE_PLATFORM_ID%>);singleSelect('selectedFilterStatus','<c:out value="${status[0]}" />',document.forms[2]);return false;">
														<c:out value="${status[1]}" /></a>
												</td>
											</tr>
										</c:forEach>

										<tr>
											<td style="padding: 3px"><label><strong>SMS</strong></label></td>
										</tr>
										<tr>
											<td>									
												<html:multibox property="selectedFilterStatus" onclick="selectAllPlatformStatus('2',this);">
													<%=AimsConstants.FILTER_SMS_SHOW_ALL%>
												</html:multibox>
													<a href="#" onClick="selectAllPlatformStatus('<%=AimsConstants.SMS_PLATFORM_ID%>',this,'true');document.forms[2].submit();return false;">
														<%=AimsConstants.FILTER_LABEL_SMS_SHOW_ALL%></a>
											</td>
										</tr>										
										<c:forEach var="status" items="${ApplicationsFilterForm.filterSMS}">
											<tr>
												<td>
													<html:multibox property="selectedFilterStatus"><c:out value="${status[0]}" /></html:multibox>
													<a href="#" onClick="selectPlatFormFromStatus(<%=AimsConstants.SMS_PLATFORM_ID%>);singleSelect('selectedFilterStatus','<c:out value="${status[0]}" />',document.forms[2]);return false;">
														<c:out value="${status[1]}" /></a>
												</td>
											</tr>
										</c:forEach>

										<tr>
											<td style="padding: 3px"><label><strong>MMS</strong></label></td>
										</tr>
										<tr>
											<td>									
												<html:multibox property="selectedFilterStatus" onclick="selectAllPlatformStatus('3',this);">
													<%=AimsConstants.FILTER_MMS_SHOW_ALL%>
												</html:multibox>
													<a href="#" onClick="selectAllPlatformStatus('<%=AimsConstants.MMS_PLATFORM_ID%>',this,'true');document.forms[2].submit();return false;">
														<%=AimsConstants.FILTER_LABEL_MMS_SHOW_ALL%></a>
											</td>
										</tr>
										<c:forEach var="status" items="${ApplicationsFilterForm.filterMMS}">
											<tr>
												<td>
													<html:multibox property="selectedFilterStatus"><c:out value="${status[0]}" /></html:multibox>
													<a href="#" onClick="selectPlatFormFromStatus(<%=AimsConstants.MMS_PLATFORM_ID%>);singleSelect('selectedFilterStatus','<c:out value="${status[0]}" />',document.forms[2]);return false;">
														<c:out value="${status[1]}" /></a>
												</td>
											</tr>
										</c:forEach>

										<tr>
											<td style="padding: 3px"><label><strong>V CAST Video</strong></label></td>
										</tr>
										<tr>
											<td>									
												<html:multibox property="selectedFilterStatus" onclick="selectAllPlatformStatus('6',this);">
													<%=AimsConstants.FILTER_VCAST_SHOW_ALL%>
												</html:multibox>
													<a href="#" onClick="selectAllPlatformStatus('<%=AimsConstants.VCAST_PLATFORM_ID%>',this,'true');document.forms[2].submit();return false;">
														<%=AimsConstants.FILTER_LABEL_VCAST_SHOW_ALL%></a>
											</td>
										</tr>
										<c:forEach var="status" items="${ApplicationsFilterForm.filterVCAST}">
											<tr>
												<td>
													<html:multibox property="selectedFilterStatus">
														<c:out value="${status[0]}" /></html:multibox>
													<a href="#" onClick="selectPlatFormFromStatus(<%=AimsConstants.VCAST_PLATFORM_ID%>);singleSelect('selectedFilterStatus','<c:out value="${status[0]}" />',document.forms[2]);return false;">
														<c:out value="${status[1]}" /></a>
												</td>
											</tr>
										</c:forEach>

                                        <tr>
											<td style="padding: 3px"><label><strong>VZAppZone</strong></label></td>
										</tr>
										<tr>
											<td>
												<html:multibox property="selectedFilterStatus" onclick="selectAllPlatformStatus('42',this);">
													<%=AimsConstants.FILTER_VZAPPZON_SHOW_ALL%>
												</html:multibox>
													<a href="#" onClick="selectAllPlatformStatus('<%=AimsConstants.VZAPPZONE_PLATFORM_ID%>',this,'true');document.forms[2].submit();return false;">
														<%=AimsConstants.FILTER_LABEL_VZAPPZON_SHOW_ALL%></a>
											</td>
										</tr>
										<c:forEach var="status" items="${ApplicationsFilterForm.filterVzAppZon}">
											<tr>
												<td>
													<html:multibox property="selectedFilterStatus"><c:out value="${status[0]}" /></html:multibox>
													<a href="#"onClick="selectPlatFormFromStatus(<%=AimsConstants.VZAPPZONE_PLATFORM_ID%>);singleSelect('selectedFilterStatus','<c:out value="${status[0]}" />',document.forms[2]);return false;">
														<c:out value="${status[1]}" /></a>
												</td>
											</tr>
										</c:forEach>

                                        <tr>
											<td style="padding: 3px"><label><strong>WAP</strong></label></td>
										</tr>
										<tr>
											<td>									
												<html:multibox property="selectedFilterStatus" onclick="selectAllPlatformStatus('4',this);">
													<%=AimsConstants.FILTER_WAP_SHOW_ALL%>
												</html:multibox>
													<a href="#" onClick="selectAllPlatformStatus('<%=AimsConstants.WAP_PLATFORM_ID%>',this,'true');document.forms[2].submit();return false;">
														<%=AimsConstants.FILTER_LABEL_WAP_SHOW_ALL%></a>
											</td>
										</tr>
										<c:forEach var="status" items="${ApplicationsFilterForm.filterWAP}">
											<tr>
												<td>
													<html:multibox property="selectedFilterStatus"><c:out value="${status[0]}" /></html:multibox>
													<a href="#"onClick="selectPlatFormFromStatus(<%=AimsConstants.WAP_PLATFORM_ID%>);singleSelect('selectedFilterStatus','<c:out value="${status[0]}" />',document.forms[2]);return false;">
														<c:out value="${status[1]}" /></a>
												</td>
											</tr>
										</c:forEach>

										<%										
										if ((ApplicationHelper.checkPlatformAccess(request, AimsConstants.VIEW_TASK, AimsConstants.JAVA_PLATFORM_ID)))
										{
										 %>
            
										<tr>
											<td style="padding: 3px"><label><strong>V CAST Apps</strong></label></td>
										</tr>
										<tr>
											<td>									
												<html:multibox property="selectedFilterStatus" onclick="selectAllPlatformStatus('44',this);">
													<%=AimsConstants.FILTER_JAVA_SHOW_ALL%>
												</html:multibox>
													<a href="#" onClick="selectAllPlatformStatus('<%=AimsConstants.JAVA_PLATFORM_ID%>',this,'true');document.forms[2].submit();return false;">
														<%=AimsConstants.FILTER_LABEL_JAVA_SHOW_ALL%></a>
											</td>
										</tr>
										<c:forEach var="status" items="${ApplicationsFilterForm.filterJava}">
											<tr>
												<td>
													<html:multibox property="selectedFilterStatus"><c:out value="${status[0]}" /></html:multibox>
													<a href="#"onClick="selectPlatFormFromStatus(<%=AimsConstants.JAVA_PLATFORM_ID%>);singleSelect('selectedFilterStatus','<c:out value="${status[0]}" />',document.forms[2]);return false;">
														<c:out value="${status[1]}" /></a>
												</td>
											</tr>
										</c:forEach>
										
										<%
										}
										 %>
										
										
		
		
		
		
		
		
		
										
										
										

                                    </table>
								</DIV>
								<DIV class="divFormButton">
									<div class="redBtn" id="filter" title="Filter">
										<div><div><div onclick="selectPlatFormFromStatus();javascript:validateFilter('selectedFilterStatus',document.forms[2]);return false;">Filter</div></div></div>
									</div>
								</DIV>
							</DIV>
							<!-- STATUS FILTER POPUP END HERE -->
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
							
						</th>
						<%if	(	((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE) ) {%>
						<th>
							<strong><bean:message key="ManageApplicationForm.view.clone" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong>
						</th>							
						<%	}%>
	
						<th>
							<strong><bean:message key="ManageApplicationForm.view.edit" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></strong>
						</th>
						<th>
							<strong><bean:message key="ManageApplicationForm.view.delete" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></strong>
						</th>
					</tr>









					<logic:empty name="AimsApplicationsInformation" scope="request">
						<tr>
							<td colspan="7" width="100%"><bean:message key="error.generic.no.records.for.view" /></td>
						</tr>
					</logic:empty>



					<logic:iterate id="appsApp" name="AimsApplicationsInformation" scope="request">
                        <bean:define id="application_id" name="appsApp" property="appsId" type="java.lang.Long"/>
                        <tr>
							<td align="left">
								<%
										if (((AimsUser) request.getSession().getAttribute( AimsConstants.AIMS_USER)).getUserType().equals( AimsConstants.VZW_USERTYPE)) {
								%>
								<a href="/aims/allianceStatus.do?task=view&alliance_id=<bean:write	name="appsApp" property="allianceId" />" class="a">
									<bean:write name="appsApp" property="companyName" /></a>
							</td>
							<td class="cell" align="left">
								<% } else {}%>
								<bean:write name="appsApp" property="platformName" />
								<logic:equal name="appsApp" property="isLbs" value="true">
									<img src="images/lbs_icon.gif" />
								</logic:equal>
							</td>
                            <td align="left">
								<a href="<bean:write name="appsApp"	property="urlSetupAction"	/>?task=view&appsId=<bean:write	name="appsApp" property="appsId" />" class="a">
                                    <aims:getTruncatedString name="appsApp" property="title" maxLength="30" fullTextVarName="<%=("title_"+application_id.toString())%>" />
                                </a>
                            </td>
                            <td class="cell" align="left"><bean:write name="appsApp" property="createdDate" formatKey="date.format" filter="true" />&nbsp;</td>
							<td class="cell" align="left"><bean:write name="appsApp" property="phaseName" /></td>
							<%if	(	((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE) ) {%>
								<td class="cell" align="center">
									<logic:equal name="appsApp"	property="allowCreate" value="true">
										<a href="<bean:write name="appsApp"	property="urlSetupAction"	/>?task=clone&appsId=<bean:write name="appsApp"	property="appsId"	/>"	class="a">
											<bean:message	key="images.clone.icon"	/>
										</a>
									</logic:equal>
									<logic:equal name="appsApp"	property="allowCreate" value="false">
										&nbsp;
									</logic:equal>
								</td>
							<%}%>
							<td class="cell" align="center">
								<logic:equal name="appsApp" property="allowEdit" value="true">
									<a href="<bean:write name="appsApp"	property="urlSetupAction"	/>?task=edit&appsId=<bean:write	name="appsApp" property="appsId" />" class="a">
									     <img src="images/icon-edit.gif" alt="Edit" width="18" height="13" border="0"/>
                                        <%--<bean:message key="images.edit.icon" />--%>
                                    </a>
								</logic:equal>
								<logic:equal name="appsApp" property="allowEdit" value="false"> &nbsp;</logic:equal>
							</td>
							<td class="cell" align="center">
								<logic:equal name="appsApp" property="allowDelete" value="true">
								<c:choose>
									<c:when test="${empty requestScope.cancel_search}">
										<a href="javascript:submitForm('<bean:write name="appsApp"	property="urlSetupAction"	/>?task=delete&appsId=<bean:write	name="appsApp" property="appsId"/>&sort_field=<bean:write	name="sort_field"/>&filter_field=<bean:write	name="filter_field"/>&filter_text=<bean:write	name="filter_text"/>&app_type=<bean:write	name="app_type"/>&page_id=<bean:write	name="page_id"/>')" class="a" onClick="javascript:if (window.confirm('<bean:message key="ManageApplicationForm.delete.msg"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>')) { return true;} else { return false;}">
	                                        <img src="images/icon-delete.gif" alt="Delete" width="15" height="14" border="0"/>
	                                    </a>
									</c:when>
									<c:otherwise>
										<a href="javascript:submitForm('<bean:write name="appsApp"	property="urlSetupAction"	/>?task=delete&appsId=<bean:write	name="appsApp" property="appsId"/>&sort_field=<bean:write	name="sort_field"/>&filter_field=<bean:write	name="filter_field"/>&filter_text=<bean:write	name="filter_text"/>&app_type=<bean:write	name="app_type"/>&page_id=<bean:write	name="page_id"/>&cancel_search')" class="a" onClick="javascript:if (window.confirm('<bean:message key="ManageApplicationForm.delete.msg"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>')) { return true;} else { return false;}">
	                                        <img src="images/icon-delete.gif" alt="Delete" width="15" height="14" border="0"/>
	                                    </a>									
									</c:otherwise>
								</c:choose>
                                </logic:equal>
								<logic:equal name="appsApp" property="allowDelete" value="false">&nbsp;</logic:equal>
							</td>
						</tr>
					</logic:iterate>
				</table>
			</DIV>
	</DIV>
	<!-- DATA GRID END HERE -->






	<!-- PAGER START HERE -->
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
									<a href='#' onclick='submitForm("<bean:message key="ManageApplicationForm.manage.app.view.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?isPageLnk=yes&task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&app_type=<bean:write name="app_type"/>&page_id=<%=page_id.intValue() - 1%>");return false;'><strong>Previous</strong></a>
									<img src="images/spacer.gif" width="10" height="1" />
									<%
										if (startPageCount - 10 > 0) {
									%>
										<a href='#' onclick='submitForm("<bean:message key="ManageApplicationForm.manage.app.view.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?isPageLnk=yes&task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&app_type=<bean:write name="app_type"/>&page_id=<%=startPageCount - 10%>");return false;'><img src="images/greyRndArrowL.gif" height="15" border="0" align="absbottom" /></a>
										<img src="images/spacer.gif" width="3" height="1" />
									<% }%>
								</logic:greaterThan>

								<% for (int pageCount = startPageCount; pageCount < startPageCount + 10; pageCount++) {%>
									<% if (pageCount > 0 && pageCount <= page_max.intValue()) {	%>
										<% if (pageCount == page_id.intValue()) { %>
											<b><%=pageCount%><img src="images/spacer.gif" width="1" height="1" /></b>
										<%} else { %>
											<a href='#'
												onclick='submitForm("<bean:message key="ManageApplicationForm.manage.app.view.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=view&sort_field=<bean:write name="sort_field"/>&isPageLnk=yes&sort_order=<bean:write	name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&app_type=<bean:write name="app_type"/>&page_id=<%=pageCount%>");return false;'><%=pageCount%></a>
										<%}%>
									<%}%>
								<%}%>

								<logic:lessThan name="page_id" value="<%=page_max.toString()%>">
									<% if (startPageCount + 10 <= page_max.intValue()) { %>
										<img src="images/spacer.gif" width="3" height="1" />
										<a href='#' onclick='submitForm("<bean:message key="ManageApplicationForm.manage.app.view.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?isPageLnk=yes&task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&app_type=<bean:write name="app_type"/>&page_id=<%=startPageCount + 10%>");return false;'><img src="images/greyRndArrow.gif" height="15" border="0" align="absbottom" /></a>
									<%}%>
									<img src="images/spacer.gif" width="10" height="1" />
									<a href='#' onclick='submitForm("<bean:message key="ManageApplicationForm.manage.app.view.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?isPageLnk=yes&task=view&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write name="sort_order"/>&filter_field=<bean:write name="filter_field"/>&filter_text=<bean:write name="filter_text"/>&app_type=<bean:write name="app_type"/>&page_id=<%=page_id.intValue() + 1%>");return false;'><strong>Next</strong></a>
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


				<%-- Start: Create Application Drop down --%>
				<form name="createSubmitForm2" method="post">
				<% if (((AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE)) {%>
				<tr>
					<td align="right" width="100%" style="padding-top: 10px; padding-bottom: 10px">
						<div class="btnTopLine" style="padding-top: 10px; padding-bottom: 10px">						
							<table cellspacing="0" cellpadding="0" border="0" width="100%">
								<tr>
									<td width="92%" align="right" valign="bottom">
										<strong><bean:message key="ManageApplicationForm.view.createNewApp" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></Strong>
										<img src="images/spacer.gif" width="10" height="1">
										<select name="createApp">
											<option value="0" selected>
												<bean:message key="ManageApplicationForm.label.selectOne" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
											</option>
											
              	  	<logic:iterate id="platform"	name="ApplicationsFilterForm" property="platforms" scope="request">
                    	<bean:define id="platformId"><bean:write name="platform" property="platformId"/></bean:define>                    	                         
                     
                    	<%if (ApplicationHelper.checkPlatformAccess(request, "create", Long.valueOf(platformId)) ) { %>
                    	
                    		<%if (AimsConstants.BREW_PLATFORM_ID.toString().equals( platformId ) )  {%>
                    			<option value="<bean:message key="ManageApplicationForm.brew.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			BREW
                    			</option>
                    		<%} %>
                    		
                    		<%if (AimsConstants.DASHBOARD_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.dashboard.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
					    			Dashboard 
								</option>
                    		<%} %>
                    		<%if (AimsConstants.ENTERPRISE_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.enterprise.app.setup.url"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			JMA
                    			</option>
                    		<%} %>
                    		<%if (AimsConstants.MMS_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.mms.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			MMS
                    			</option>
                    		<%} %>
                    		<%if (AimsConstants.SMS_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.sms.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			SMS
                    			</option>	
                    		<%} %>
                    		<%if (AimsConstants.VCAST_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.vcast.app.setup.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			V CAST Video
                    			</option>	
                    		<%} %>
                    		<%if (AimsConstants.VZAPPZONE_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.vzAppZone.app.setup.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			VZAppZone
                    			</option>
                    		<%} %>
                    		<%if (AimsConstants.WAP_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.wap.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			WAP
                    			</option>	
                    		<%} %>
                    		<%if (AimsConstants.JAVA_PLATFORM_ID.toString().equals( platformId  ) ) {%>
                    			<option value="<bean:message key="ManageApplicationForm.java.ondeck.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			V CAST Apps
                    			</option>
                    			<option value="<bean:message key="ManageApplicationForm.java.offdeck.app.setup.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?task=create">
                        			V CAST Apps More
                    			</option>
                    		<%} %>
                    		
                    	<%} %>
                    </logic:iterate>
											
											
										</select>&nbsp;
									</td>
									<td width="8%" align="left">
										<div class="redBtn" style="margin-left:5px;float:right; margin-top:3px" id="btnCreateApp" title="Create Application">
											<div><div><div onClick="createApplication(document.forms[3], document.forms[3].createApp.selectedIndex);">Create</div></div></div>
										</div>
									</td>
								</tr>
							</table>
							</div>
						</td>
					</tr>
				<%} else {}%>
				</form>
			<%-- END: Create Application Drop down --%>	
			
			
			
						
			</table>
		</DIV>
	</DIV>
	<!-- PAGER END HERE -->
</div>

</div>
</div>
<!-- CONTENT END HERE -->

<script type="text/javascript">
	dropdowncontent.init("filterLink1", "left-bottom", -1)
	dropdowncontent.init("filterLink2", "left-bottom", -1)	
	window.document.onclick=onclick;
	setFilterDisableState('selectedFilterPlatform');
	setFilterDisableState('selectedFilterStatus');
</script>

