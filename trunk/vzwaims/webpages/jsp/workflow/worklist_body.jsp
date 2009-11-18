<%@ page language="java"%>

<%@ page import="com.netpace.aims.bo.application.ManageApplicationsConstants,com.netpace.aims.util.AimsConstants"%>
<%@page import="com.netpace.aims.controller.application.ApplicationHelper"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:useBean id="page_id" type="java.lang.Integer" scope="request" />
<jsp:useBean id="page_max" type="java.lang.Integer" scope="request" />
<jsp:useBean id="sort_field" class="java.lang.String" scope="request" />
<jsp:useBean id="sort_order" class="java.lang.String" scope="request" />

<script language="javascript">
function viewApp(recordId, platformId){
	var url=""
	var wind;
    if(platformId == <%=AimsConstants.BREW_PLATFORM_ID%>) {
        url = "<%=ManageApplicationsConstants.BREW_APPLICATION_SETUP_URL%>?task=view&appsId="+recordId;
    }
    else if(platformId == <%=AimsConstants.VZAPPZONE_PLATFORM_ID%>) {
        url = "<%=ManageApplicationsConstants.VZAPPZONE_APPLICATION_SETUP_URL%>?task=view&appsId="+recordId;
    }
    else if(platformId == <%=AimsConstants.JAVA_PLATFORM_ID%>) {
    	url = "<%=ManageApplicationsConstants.JAVA_APPLICATION_SETUP_URL%>?task=view&appsId="+recordId;        
    }
    wind = window.open ("","view","resizable=1,scrollbars=1,width=900,height=600,screenX=50,left=50,screenY=30,top=30");
    wind.location.href=url;
	wind.focus();	
}
function editApp(recordId, stepId, workFlowDetailId){
	var url=""
	var wind;
    if(workFlowDetailId == <%=AimsConstants.WORKFLOW_JAVA_ONDECK_APP%>) {
        url = "<%=ManageApplicationsConstants.JAVA_APPLICATION_SETUP_URL%>?task=edit&appsId="+recordId+"&stepToValidate="+stepId;
    }
    if(workFlowDetailId == <%=AimsConstants.WORKFLOW_JAVA_OFFDECK_APP%>) {
        url = "<%=ManageApplicationsConstants.JAVA_APPLICATION_SETUP_URL%>?task=edit&appsId="+recordId+"&stepToValidate="+stepId;
    }
    
    //wind = window.open ("","edit","resizable=1,scrollbars=1,width=900,height=600,screenX=50,left=50,screenY=30,top=30");
    //wind.location.href=url;
	//wind.focus();
	
	document.location.href=url;
}
function submitForm(url){
	//alert(url);
	document.forms[1].action=url;
	document.forms[1].submit();
}
function jumpTo(){

	var url="<c:out value='${pageContext.request.contextPath}'/>/worklist.do?"
	url +="isPageLnk=yes&sort_field=<c:out value='${sort_field}'/>&sort_order=<c:out value='${sort_order}'/>";
	url += "&filterField=<c:out value='${WorklistForm.filterField}'/>&filterText=<c:out value='${WorklistForm.filterText}'/>";
	submitForm(url);
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
function collapseRow(sectionRow) {
    document.getElementById("row"+sectionRow).style.display='none';
    document.getElementById("spnExpandCollapse"+sectionRow).innerHTML="<a class='a' onclick='javascript:expandRow(\""+sectionRow+"\");'>[+]</a>";
    return false;
}

function expandRow(sectionRow) {
    document.getElementById("row"+sectionRow).style.display='';
    document.getElementById("spnExpandCollapse"+sectionRow).innerHTML="<a class='a' onclick='javascript:collapseRow(\""+sectionRow+"\");'>[-]</a>";
    return false;
}


function selectPlatformShowAll(platformId,s, ref){
	if(platformId=='<%=AimsConstants.BREW_PLATFORM_ID%>' && s=='<%=AimsConstants.FILTER_BREW_SHOW_ALL%>'){
		ref.checked=true;
	}
	if(platformId=='<%=AimsConstants.JAVA_PLATFORM_ID%>' && s=='<%=AimsConstants.FILTER_JAVA_SHOW_ALL%>'){
		ref.checked=true;
	}
}
function selectStatusFromPlatform(platformId){
	var platforms=document.getElementsByName("selectedFilterType");
	var statuses=document.getElementsByName("selectedFilterWorkItem");

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
	var platforms=document.getElementsByName("selectedFilterType");
	var statuses=document.getElementsByName("selectedFilterWorkItem");

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
		if (count == <%= AimsConstants.FILTER_WORKFLOW_TYPE.length %>){
			clearPlatform();
			platforms[0].checked=true;
		}
		if (statusCount == "<c:out value='${requestScope.workItemCount}'/>"){
			clearCurrentStatus();
			statuses[0].checked=true;
		}
	}
}
function clearCurrentStatus(){
	//document.forms[1].clearStatus.value="true";
	var cb=document.getElementsByName("selectedFilterWorkItem");	
	for(i=0; i<cb.length; i++){
		cb[i].checked=false;
		cb[i].disabled=false;
	}	
}
function setCurrentStatus(){
	var cb=document.getElementsByName("selectedFilterWorkItem");	
	for(i=0; i<cb.length; i++){
		cb[i].checked=true;
	}	
}
function clearPlatform(){
	var cb=document.getElementsByName("selectedFilterType");	
	for(i=0; i<cb.length; i++){
		cb[i].checked=false;
		cb[i].disabled=false;
	}	
}
function setPlatform(platformId){
	var cb=document.getElementsByName("selectedFilterType");	
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
function selectAllPlatformStatus(platformId,ref,isLink){	
	var statuses=document.getElementsByName("selectedFilterWorkItem");
	var platforms=document.getElementsByName("selectedFilterType");
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
	else if (cbName=='selectedFilterType'){
		var count=0;
		for(i=1; i<cb.length; i++){
			if (cb[i].checked==true){
				count++;
			}
		}
		if (count == <%= AimsConstants.FILTER_WORKFLOW_TYPE.length %>){
			cb[0].checked=true;
			cb[0].disabled=false;
			for(i=1; i<cb.length; i++){
				cb[i].checked=false;
				cb[i].disabled=true;			
			}
			clearCurrentStatus();
			var statuses=document.getElementsByName("selectedFilterWorkItem");
			statuses[0].checked=true;			
		}
	}
}
</script>

<c:choose>
	<c:when test="${requestScope.filterType eq 'Showing All'}">
		<c:set var="typeIcon" value="images/icon_filter.gif" scope="page"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="typeIcon" value="images/icon_filter2.gif" scope="page"></c:set>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${requestScope.filterWorkItem eq 'Showing All'}">
		<c:set var="workItemIcon" value="images/icon_filter.gif" scope="page"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="workItemIcon" value="images/icon_filter2.gif" scope="page"></c:set>
	</c:otherwise>
</c:choose>

<%@ include  file="/common/error.jsp" %>

<DIV class="homeColumnTab lBox">
	<DIV class="headLeftCurveblk"></DIV>
	<H1>Work Item(s)</H1>
	<DIV class="headRightCurveblk"></DIV>
	<DIV class="contentbox">
	<html:form action="/worklist.do" method="post">
		<html:hidden property="filterText" />
		<html:hidden property="filterField" />		
		<html:hidden property="sortField" />
		<html:hidden property="sortOrder" />
		<input type="hidden" name="lastSortBy" value="<c:out value="${requestScope.lastSortBy}"/>" />
		<DIV class="homeColumnGrid lBox">
		<table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
			<tr>
				<%--<th>Rownum</th>--%>
				
				<th width="20%" nowrap="nowrap" class="<c:choose>
          								<c:when test="${sort_field eq '1' and sort_order eq 'asc' }">sorted</c:when>
          								<c:when test="${sort_field eq '1' and sort_order eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						  		</c:choose>">
					<a href='#' 
						onclick='submitForm("/aims/worklist.do?sort_field=1&sort_order=<bean:write	name="sort_order"/>&filterField=<c:out value="${WorklistForm.filterField}"/>&filterText=<c:out value="${WorklistForm.filterText}"/>");return false;'>
						<strong>Work Item</strong></a>
					
					<!-- Work Item FILTER POPUP START HERE -->
					<a href="#" onclick="return false;"><img src='<c:out value="${workItemIcon}"/>' name="filterLink2" width="11" height="11" border="0" align="absmiddle" id="filterLink2" rel="contentDiv2" title='Work Item = "<c:out value="${requestScope.filterWorkItem}"/>"'></a>
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
										<html:multibox property="selectedFilterWorkItem"><%=AimsConstants.FILTER_SHOW_ALL%></html:multibox>
											<a href="#" onClick="setPlatform();singleSelect('selectedFilterWorkItem','<%=AimsConstants.FILTER_SHOW_ALL%>',document.forms[1]);return false;">
												<%=AimsConstants.FILTER_LABEL_SHOW_ALL%></a>
									</td>
								</tr>									
								<tr>
									<td style="padding: 3px"><label><strong>BREW</strong></label></td>
								</tr>
								<tr>
									<td>									
										<html:multibox property="selectedFilterWorkItem" onclick="selectAllPlatformStatus('1',this);">
											<%=AimsConstants.FILTER_BREW_SHOW_ALL%>
										</html:multibox>
											<a href="#" onClick="selectAllPlatformStatus('<%=AimsConstants.BREW_PLATFORM_ID%>',this,'true');document.forms[1].submit();return false;">
												<%=AimsConstants.FILTER_LABEL_BREW_SHOW_ALL%></a>
									</td>
								</tr>
								<c:forEach var="wi"items="${WorklistForm.filterWorkItemsBrew}">
									<tr>
										<td>
											<html:multibox property="selectedFilterWorkItem"><c:out value="${wi[0]}" /></html:multibox>
											<a href="#" onClick="selectPlatFormFromStatus(<%=AimsConstants.BREW_PLATFORM_ID%>);singleSelect('selectedFilterWorkItem','<c:out value="${wi[0]}" />',document.forms[1]);return false;">
												<c:out value="${wi[1]}" /></a>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<td style="padding: 3px"><label><strong>V CAST Apps</strong></label></td>
								</tr>
								<tr>
									<td>									
										<html:multibox property="selectedFilterWorkItem" onclick="selectAllPlatformStatus('44',this);">
											<%=AimsConstants.FILTER_JAVA_SHOW_ALL%>
										</html:multibox>
											<a href="#" onClick="selectAllPlatformStatus('<%=AimsConstants.JAVA_PLATFORM_ID%>',this,'true');document.forms[1].submit();return false;">
												<%=AimsConstants.FILTER_LABEL_JAVA_SHOW_ALL%></a>
									</td>
								</tr>
								<c:forEach var="wi"items="${WorklistForm.filterWorkItemsJava}">
									<tr>
										<td>
											<html:multibox property="selectedFilterWorkItem"><c:out value="${wi[0]}" /></html:multibox>
											<a href="#" onClick="selectPlatFormFromStatus(<%=AimsConstants.JAVA_PLATFORM_ID%>);singleSelect('selectedFilterWorkItem','<c:out value="${wi[0]}" />',document.forms[1]);return false;">
												<c:out value="${wi[1]}" /></a>
										</td>
									</tr>
								</c:forEach>								
							</table>
						</DIV>
						<DIV class="divFormButton">
							<div class="redBtn" id="filter" title="Filter">
								<div><div><div onclick="selectPlatFormFromStatus();javascript:validateFilter('selectedFilterWorkItem',document.forms[1]);return false;">Filter</div></div></div>
							</div>
						</DIV>
					</DIV>
					<!-- Work Item FILTER POPUP END HERE -->					
				</th>
				<th width="10%" nowrap="nowrap" class="<c:choose>
          								<c:when test="${sort_field eq '2' and sort_order eq 'asc' }">sorted</c:when>
          								<c:when test="${sort_field eq '2' and sort_order eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						  		</c:choose>">
					<a href='#' 
						onclick='submitForm("/aims/worklist.do?sort_field=2&sort_order=<bean:write	name="sort_order"/>&filterField=<c:out value="${WorklistForm.filterField}"/>&filterText=<c:out value="${WorklistForm.filterText}"/>");return false;'>
						<strong>Type</strong></a>
					<!-- Type FILTER POPUP START HERE -->
					<a href="#" onclick="return false;"><img src='<c:out value="${typeIcon}"/>' name="filterLink1" width="11" height="11" border="0" align="absmiddle" id="filterLink1" rel="contentDiv1" title='Type = "<c:out value="${requestScope.filterType}"/>"'></a>
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
						<DIV class="divFormContent" style="	width:139px;">
							<table border="0" cellspacing="0" cellpadding="0" style="border:none; background-color:none;">
								<tr>
									<td>									
										<html:multibox property="selectedFilterType"><%=AimsConstants.FILTER_SHOW_ALL%></html:multibox>
											<a href="#" onClick="clearCurrentStatus();document.forms[1].selectedFilterWorkItem[0].checked=true;singleSelect('selectedFilterType','<%=AimsConstants.FILTER_SHOW_ALL%>',document.forms[1]);return false;"><%=AimsConstants.FILTER_LABEL_SHOW_ALL%></a>
									</td>
								</tr>
								<c:forEach var="type" items="${WorklistForm.filterType}" varStatus="status">
									<c:choose>
										<c:when test="${status.last}">												
										</c:when>
										<c:otherwise>
											<%
												String platform = ((Object[])pageContext.getAttribute("type"))[0].toString();
												if ( platform.equalsIgnoreCase(AimsConstants.JAVA_PLATFORM_ID.toString()) && (ApplicationHelper.checkPlatformAccess(request, AimsConstants.VIEW_TASK, AimsConstants.JAVA_PLATFORM_ID)) ) 
												{
											 %>
											 	<tr>												
												<td align="left" valign="top">												
													<html:multibox property="selectedFilterType"><c:out value="${type[0]}" /></html:multibox>
													<a href="#" onClick="selectStatusFromPlatform('<c:out value="${type[0]}" />');singleSelect('selectedFilterType','<c:out value="${type[0]}" />',document.forms[1]);return false;">
														<c:out value="${type[1]}" /></a>
												</td>
											</tr>
											 
											 <%
											 	}
											 	else if ( !platform.equalsIgnoreCase(AimsConstants.JAVA_PLATFORM_ID.toString())) 
											 	{
											  %>
											  	<tr>												
												<td align="left" valign="top">												
													<html:multibox property="selectedFilterType"><c:out value="${type[0]}" /></html:multibox>
													<a href="#" onClick="selectStatusFromPlatform('<c:out value="${type[0]}" />');singleSelect('selectedFilterType','<c:out value="${type[0]}" />',document.forms[1]);return false;">
														<c:out value="${type[1]}" /></a>
												</td>
											</tr>
											  
											  <%
											  	}
											   %>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</table>
						</DIV>
						<DIV class="divFormButton">
							<div class="redBtn" id="filter" title="Filter">
								<div><div><div onclick="selectStatusFromPlatform();setFilterDisableState('selectedFilterType');validateFilter('selectedFilterType',document.forms[1]);">Filter</div></div></div>
							</div>
						</DIV>
					</DIV>
					<!-- Type FILTER POPUP END HERE -->				
				</th>
				<th width="22%" nowrap="nowrap" class="<c:choose>
          								<c:when test="${sort_field eq '3' and sort_order eq 'asc' }">sorted</c:when>
          								<c:when test="${sort_field eq '3' and sort_order eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						  		</c:choose>">
					<a href='#' 
						onclick='submitForm("/aims/worklist.do?sort_field=3&sort_order=<bean:write	name="sort_order"/>&filterField=<c:out value="${WorklistForm.filterField}"/>&filterText=<c:out value="${WorklistForm.filterText}"/>");return false;'>
						<strong>Details</strong></a>
				</th>
				<th width="20%" nowrap="nowrap" class="<c:choose>
          								<c:when test="${sort_field eq '4' and sort_order eq 'asc' }">sorted</c:when>
          								<c:when test="${sort_field eq '4' and sort_order eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						  		</c:choose>">
					<a href='#' 
						onclick='submitForm("/aims/worklist.do?sort_field=4&sort_order=<bean:write	name="sort_order"/>&filterField=<c:out value="${WorklistForm.filterField}"/>&filterText=<c:out value="${WorklistForm.filterText}"/>");return false;'>
						<strong>Company</strong></a>
				</th>
				<th width="5%" nowrap="nowrap" class="<c:choose>
          								<c:when test="${sort_field eq '5' and sort_order eq 'asc' }">sorted</c:when>
          								<c:when test="${sort_field eq '5' and sort_order eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						  		</c:choose>">
					<a href='#' 
						onclick='submitForm("/aims/worklist.do?sort_field=5&sort_order=<bean:write	name="sort_order"/>&filterField=<c:out value="${WorklistForm.filterField}"/>&filterText=<c:out value="${WorklistForm.filterText}"/>");return false;'>
						<strong>Start Date</strong></a>
				</th>
				<th width="23%">Action(s)</th>
			</tr>						
			<logic:iterate id="workitem" name="WorklistForm" property="workitemList">
							
				<tr>
					<%--<td align="left" style="border-top: 0px"><bean:write name="workitem" property="rownum"/></td>--%>
					<td align="left" style="border-top: 0px"><bean:write name="workitem" property="workItem"/></td>
					<td align="left" style="border-top: 0px"><bean:write name="workitem" property="type"/></td>
					<td align="left" style="border-top: 0px"><a href="javascript:viewApp('<bean:write name="workitem" property="recordId"/>', <bean:write name="workitem" property="platformId"/>)" ><bean:write name="workitem" property="workItemDispTitle" filter="false" /></a></td>
					<td align="left" style="border-top: 0px"><bean:write name="workitem" property="company"/></td>
					<td align="left" style="border-top: 0px"><bean:write name="workitem" property="startDate"/></td>					
					<td align="left" style="border-top: 0px">
						<table width="100%" border="0" cellspacing="0" bordercolor="black" cellpadding="0" style="border: 0px solid black;">
							<tr>
								<td width="90%" align="center" style="vertical-align: middle;">
									<html:hidden name="workitem" property="rownum" indexed="true" />
									<html:hidden name="workitem" property="workitemId" indexed="true" />
									<html:hidden name="workitem" property="workflowId" indexed="true" />
									<html:hidden name="workitem" property="recordId" indexed="true" />
									<html:hidden name="workitem" property="detail" indexed="true" />									

									<logic:equal name="workitem" property="validateStep" value="true">
										<a href="javascript:editApp('<bean:write name="workitem" property="recordId"/>', <bean:write name="workitem" property="stateId"/>, <bean:write name="workitem" property="workFlowDetailId"/>)" >
											Fill out data & Execute
										</a>
									</logic:equal>									
									<logic:equal name="workitem" property="validateStep" value="false">
										<html:select property="selectedAction" name="workitem" styleClass="selectField" indexed="true"  style="width: 11em;">
											<html:optionsCollection name="workitem" property="actions" label="label" value="value"/>
										</html:select>&nbsp;&nbsp;
									</logic:equal>
									
								</td>
								<logic:equal name="workitem" property="validateStep" value="false">
									<td width="20%" align="left" style="vertical-align: middle;">
										<span id="spnExpandCollapse<bean:write name="workitem" property="rownum"/>" style="cursor:pointer;">
	                            			<a class="a" onclick="javascript:expandRow(<bean:write name="workitem" property="rownum"/>)">[+]</a>
	                        			</span>
									</td>
								</logic:equal>
							</tr>
						</table>												
					</td>
				</tr>
				<tr id="row<bean:write name="workitem" property="rownum"/>" style="display:none;">
                    <td colspan="6" >
                    	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 0px solid black;">
                    		<tr>
                    			<td style="vertical-align:middle; padding-left:0px;" width="10%">
									<strong>Comments:&nbsp;</strong>
								</td>
								<td>
									<html:textarea property="comments" name="workitem" styleClass="textareaField" indexed="true" rows="4" cols="70"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
			</logic:iterate>
			<logic:empty name="WorklistForm" property="workitemList">
			<tr>
				<td align="center" colspan="6">
					No Work Item Information Found.
				</td>
			</tr>
			</logic:empty>
			<tr style="display: none;" id="footer">										
				<td colspan="6">			
					<logic:iterate id="saveWorkitem" name="WorklistForm" property="saveWorkitemList">							
						<html:hidden name="saveWorkitem" property="rownum" indexed="true" />
						<html:hidden name="saveWorkitem" property="workitemId" indexed="true" />
						<html:hidden name="saveWorkitem" property="workflowId" indexed="true" />
						<html:hidden name="saveWorkitem" property="recordId" indexed="true" />
						<html:hidden name="saveWorkitem" property="detail" indexed="true" />
						<html:hidden name="saveWorkitem" property="selectedAction" indexed="true" />
						<html:hidden name="saveWorkitem" property="comments" indexed="true"/>						
					</logic:iterate>
					<%@ include file="workflowMessageFooter.jsp" %>
				</td>
			</tr>			
			</table>
			</DIV>
			<logic:notEmpty name="WorklistForm" property="workitemList">
				<DIV class="homeColumn lBox marginT">		
				<DIV>
					<table align="right" width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div class="redBtn" id="Create"	style="float: right; margin-top: 10px;" title="Execute Action">
									<div><div><div onclick="submitForm('/aims/executeActions.do')">Execute Action</div></div></div>
								</div>
							</td>
						</tr>
					</table>
				</DIV>
				</DIV>
			</logic:notEmpty>			
		<!-- PAGER START HERE -->
		<DIV class="homeColumn lBox marginT">
			<DIV>
				<table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">

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
									<a href='#' onclick='submitForm("/aims/worklist.do?isPageLnk=yes&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&filterField=<c:out value="${WorklistForm.filterField}"/>&filterText=<c:out value="${WorklistForm.filterText}"/>&page_id=<%=page_id.intValue() - 1%>");return false;'><strong>Previous</strong></a>
									<img src="images/spacer.gif" width="10" height="1" />
									<%if (startPageCount - 10 > 0) {%>
										<a href='#' onclick='submitForm("/aims/worklist.do?isPageLnk=yes&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write	name="sort_order"/>&filterField=<c:out value="${WorklistForm.filterField}"/>&filterText=<c:out value="${WorklistForm.filterText}"/>&page_id=<%=startPageCount - 10%>");return false;'><img src="/aims/images/greyRndArrowL.gif" height="15" border="0" align="absbottom"/></a>
										<img src="images/spacer.gif" width="3" height="1" />
									<% }%>
								</logic:greaterThan>

								<% for (int pageCount = startPageCount; pageCount < startPageCount + 10; pageCount++) {%>
									<% if (pageCount > 0 && pageCount <= page_max.intValue()) {	%>
										<% if (pageCount == page_id.intValue()) { %>
											<b><%=pageCount%><img src="images/spacer.gif" width="1" height="1" /> </b>
										<%} else { %>
											<a href='#' onclick='submitForm("/aims/worklist.do?sort_field=<bean:write name="sort_field"/>&isPageLnk=yes&sort_order=<bean:write	name="sort_order"/>&filterField=<c:out value="${WorklistForm.filterField}"/>&filterText=<c:out value="${WorklistForm.filterText}"/>&page_id=<%=pageCount%>");return false;'><%=pageCount%></a>
										<%}%>
									<%}%>
								<%}%>

								<logic:lessThan name="page_id" value="<%=page_max.toString()%>">
									<% if (startPageCount + 10 <= page_max.intValue()) { %>
										<img src="images/spacer.gif" width="3" height="1" />
										<a href='#' onclick='submitForm("/aims/worklist.do?isPageLnk=yes&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write name="sort_order"/>&filterField=<c:out value="${WorklistForm.filterField}"/>&filterText=<c:out value="${WorklistForm.filterText}"/>&page_id=<%=startPageCount + 10%>");return false;'><img src="/aims/images/greyRndArrow.gif" height="15" border="0" align="absbottom" /></a>
									<%}%>
									<img src="images/spacer.gif" width="10" height="1" />
									<a href='#' onclick='submitForm("/aims/worklist.do?isPageLnk=yes&sort_field=<bean:write name="sort_field"/>&sort_order=<bean:write name="sort_order"/>&filterField=<c:out value="${WorklistForm.filterField}"/>&filterText=<c:out value="${WorklistForm.filterText}"/>&page_id=<%=page_id.intValue() + 1%>");return false;'><strong>Next</strong></a>
								</logic:lessThan>
							</td>
						</tr>
					</logic:greaterThan>
					<logic:greaterThan name="page_max" value="1">
						<tr>
							<td align="right">
								<table cellpadding="0" cellspacing="0" style="margin-top: 10px" align="right">
									<tr>
										<td>
											<strong>Jump to page&nbsp;</strong>
										</td>
										<td>
											<input type="text" name="page_id" size="4" value="<%=page_id.toString()%>" onkeydown="onEnterJumpTo(event);">
										</td>
										<td>
											<strong>&nbsp;of <%=page_max.toString()%> </strong>
										</td>
										<td>
											<div class="redBtn" style="float: right; margin-left: 10px" id="Go" title="Go">
												<div><div><div onclick="jumpTo();">Go</div></div></div>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</logic:greaterThan>

				</table>
			</DIV>
		</DIV>
		<!-- PAGER END HERE -->
		</html:form>
	</DIV>
</DIV>
<script type="text/javascript">
	dropdowncontent.init("filterLink1", "left-bottom", -1)
	dropdowncontent.init("filterLink2", "left-bottom", -1)	
	window.document.onclick=onclick;
	setFilterDisableState('selectedFilterType');
	setFilterDisableState('selectedFilterWorkItem');
</script>