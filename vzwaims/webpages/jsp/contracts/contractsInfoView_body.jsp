<%@ page language="java" %>
<%@ page import="com.netpace.aims.util.AimsConstants" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>

<c:url value="/contracts.do" var="titleUrl" scope="request">
    <c:param name="task" value="view"/>
    <c:param name="sortBy" value="1"></c:param>
    <c:param name="sortOrder">
        <c:out value="${sortOrder}"/>
    </c:param>
    <c:param name="filterText" value="${ContractForm.filterText}"/>
    <c:param name="filterField" value="${ContractForm.filterField}"/>
</c:url>
<c:url value="/contracts.do" var="versionUrl" scope="request">
    <c:param name="task" value="view"/>
    <c:param name="sortBy" value="2"></c:param>
    <c:param name="sortOrder">
        <c:out value="${sortOrder}"/>
    </c:param>
    <c:param name="filterText" value="${ContractForm.filterText}"/>
    <c:param name="filterField" value="${ContractForm.filterField}"/>
</c:url>
<c:url value="/contracts.do" var="platformUrl" scope="request">
    <c:param name="task" value="view"/>
    <c:param name="sortBy" value="3"></c:param>
    <c:param name="sortOrder">
        <c:out value="${sortOrder}"/>
    </c:param>
    <c:param name="filterText" value="${ContractForm.filterText}"/>
    <c:param name="filterField" value="${ContractForm.filterField}"/>
</c:url>
<c:url value="/contracts.do" var="docUrl" scope="request">
    <c:param name="task" value="view"/>
    <c:param name="sortBy" value="4"></c:param>
    <c:param name="sortOrder">
        <c:out value="${sortOrder}"/>
    </c:param>
    <c:param name="filterText" value="${ContractForm.filterText}"/>
    <c:param name="filterField" value="${ContractForm.filterField}"/>
</c:url>
<c:url value="/contracts.do" var="statusUrl" scope="request">
    <c:param name="task" value="view"/>
    <c:param name="sortBy" value="5"></c:param>
    <c:param name="sortOrder">
        <c:out value="${sortOrder}"/>
    </c:param>
    <c:param name="filterText" value="${ContractForm.filterText}"/>
    <c:param name="filterField" value="${ContractForm.filterField}"/>
</c:url>
<c:url value="/contracts.do" var="dateUrl" scope="request">
    <c:param name="task" value="view"/>
    <c:param name="sortBy" value="6"></c:param>
    <c:param name="sortOrder">
        <c:out value="${sortOrder}"/>
    </c:param>
    <c:param name="filterText" value="${ContractForm.filterText}"/>
    <c:param name="filterField" value="${ContractForm.filterField}"/>
</c:url>

<%-- sortBy 7 was ringNumber, ringNumber was removed after ring_removal changes--%>

<c:url value="/contracts.do" var="clickThroughUrl" scope="request">
    <c:param name="task" value="view"/>
    <c:param name="sortBy" value="8"></c:param>
    <c:param name="sortOrder">
        <c:out value="${sortOrder}"/>
    </c:param>
    <c:param name="filterText" value="${ContractForm.filterText}"/>
    <c:param name="filterField" value="${ContractForm.filterField}"/>
</c:url>

<c:url value="/contracts.do" var="pagingLnk" scope="request">
    <c:param name="task" value="view"/>
    <c:param name="sortBy">
        <c:out value="${sortBy}"/>
    </c:param>
    <c:param name="sortOrder">
        <c:out value="${sortOrder}"/>
    </c:param>
    <c:param name="isPageLnk">Yes</c:param>
    <c:param name="filterText" value="${ContractForm.filterText}"/>
    <c:param name="filterField" value="${ContractForm.filterField}"/>
</c:url>
<c:choose>
	<c:when test="${requestScope.filterValue eq 'Showing All'}">
		<c:set var="statusIcon" value="images/icon_filter.gif" scope="page"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="statusIcon" value="images/icon_filter2.gif" scope="page"></c:set>
	</c:otherwise>
</c:choose>

<script type="text/javascript">
    function submitForm(url) {
        //alert(url);
        document.forms[1].action = url;
        document.forms[1].submit();
    }
    function setFilterDisableState(){
		var cb=document.getElementsByName('selectedFilterValue');
		if (cb[0].value=="<%=AimsConstants.FILTER_SHOW_ALL %>" && cb[0].checked==true){
			for(i=1; i<cb.length; i++){
				cb[i].checked=false;
				cb[i].disabled=true;			
			}
		}
		else {
			var count=0;
			for(i=1; i<cb.length; i++){
				if (cb[i].checked==true){
					count++;
				}
			}
			if (count==3){
				cb[0].checked=true;
				for(i=1; i<cb.length; i++){
					cb[i].checked=false;
					cb[i].disabled=true;
				}				
			}			
		}
	}

</script>

<div class="btnTopLine">
    <div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Create"
         title="Create">
        <div>
            <div><a href="/aims/contractsSetup.do?task=createForm">Create</a></div>
        </div>
    </div>
</div>


<html:form action="/contracts.do?task=view">
<html:hidden property="filterText"/>
<html:hidden property="filterField"/>
<input type="hidden" name="lastSortBy" value="<c:out value="${requestScope.lastSortBy}"/>"/>

<div id="contentBox">
<%@ include file="/common/error.jsp" %>
<!-- DATA GRID START HERE -->
<DIV class="homeColumnTab lBox">
<DIV class="headLeftCurveblk"></DIV>
<H1>Contracts List</H1>

<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
<tr>
<th
        class="<c:choose>
          								<c:when test="${sortBy eq '1' and sortOrder eq 'asc' }">sorted</c:when>
          								<c:when test="${sortBy eq '1' and sortOrder eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						  </c:choose>">
    <a href='#' onclick='submitForm("<c:out value='${requestScope.titleUrl}'/>");return false;' style="margin-right:0px;">Title</a>
</th>
<th
        class="<c:choose>
				          				<c:when test="${sortBy eq '2' and sortOrder eq 'asc' }">sorted</c:when>
				          				<c:when test="${sortBy eq '2' and sortOrder eq 'desc' }">descSorted</c:when>
				          				<c:otherwise>sortable</c:otherwise>
          						  </c:choose>">
    <a href='#' onclick='submitForm("<c:out value='${requestScope.versionUrl}'/>");return false;' style="margin-right:0px;">Version</a>
</th>
<th
        class="<c:choose>
										<c:when test="${sortBy eq '3' and sortOrder eq 'asc' }">sorted</c:when>
          								<c:when test="${sortBy eq '3' and sortOrder eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						   </c:choose>">
    <a href='#' onclick='submitForm("<c:out value='${requestScope.platformUrl}'/>");return false;' style="margin-right:0px;">PlatForm</a>
</th>
<th     class="<c:choose>
				          				<c:when test="${sortBy eq '8' and sortOrder eq 'asc' }">sorted</c:when>
				          				<c:when test="${sortBy eq '8' and sortOrder eq 'desc' }">descSorted</c:when>
				          				<c:otherwise>sortable</c:otherwise>
          						   </c:choose>">
    <a href='#' onclick='submitForm("<c:out value='${requestScope.clickThroughUrl}'/>");return false;' style="margin-right:0px;">Auto<br/>Offered</a>
</th>
<th
        class="<c:choose>
				          				<c:when test="${sortBy eq '4' and sortOrder eq 'asc' }">sorted</c:when>
				          				<c:when test="${sortBy eq '4' and sortOrder eq 'desc' }">descSorted</c:when>
				          				<c:otherwise>sortable</c:otherwise>
          						   </c:choose>">
    <a href='#' onclick='submitForm("<c:out value='${requestScope.docUrl}'/>");return false;' style="margin-right:0px;">Document</a>
</th>
<th     nowrap
        class="<c:choose>
				          				<c:when test="${sortBy eq '5' and sortOrder eq 'asc' }">sorted</c:when>
				          				<c:when test="${sortBy eq '5' and sortOrder eq 'desc' }">descSorted</c:when>
				          				<c:otherwise>sortable</c:otherwise>
          						   </c:choose>">

    <a href='#' onclick='submitForm("<c:out value='${requestScope.statusUrl}'/>");return false;' style="margin-right:0px;">Status</a>
    <!-- FILTER POPUP START HERE -->
    <a href="#" onclick="return false;"><img src='<c:out value="${statusIcon}"/>' width="11" height="11" border="0"
                                             id="filterLink1" rel="contentDiv1"
                                             title='Status = "<c:out value="${requestScope.filterValue}"/>"'/></a>
    <DIV id="contentDiv1" class="divContent">
        <div style="width:128px">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="10" align="left"><img src="images/login_header_left_curve.gif" width="10"
                                                     height="21"/></td>
                    <td style="background-image:url(images/login_header_bg_tile.gif); background-repeat:repeat;"
                        align="right">
                        <a href="#" onClick="dropdowncontent.closeFilter('contentDiv1');return false;"><img
                                style="margin-right:-10px" src="images/icon-close.gif" width="47" height="18"
                                border="0"/></a>
                    </td>
                    <td width="10"><img src="images/login_header_right_curve.gif" width="10" height="21"/></td>
                </tr>
            </table>
        </div>
        <DIV class="divFormContent" style="width:125px; height:70px;">
            <table border="0" cellspacing="0" cellpadding="0" style="border:none; background-color:none;">
                <c:forEach var="status" items="${ContractForm.filterStatus}">
                    <tr>
                        <td align="left" valign="top">
                            <html:multibox property="selectedFilterValue"><c:out value="${status[0]}"/></html:multibox>
                            <a href="#"
                               onclick="singleSelect('selectedFilterValue','<c:out value="${status[0]}" />',document.forms[1]);return false;"><c:out value="${status[1]}"/></a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </DIV>
        <DIV class="divFormButton">
			<div class="redBtn" id="filter" title="Filter">
				<div><div><div onclick="setFilterDisableState();validateFilter('selectedFilterValue',document.forms[1]);">Filter</div></div></div>
			</div>
        </DIV>
    </DIV>
    <!-- FILTER POPUP END HERE -->
</th>
<th
        class="<c:choose>
          								<c:when test="${sortBy eq '6' and sortOrder eq 'asc' }">sorted</c:when>
          								<c:when test="${sortBy eq '6' and sortOrder eq 'desc' }">descSorted</c:when>
          								<c:otherwise>sortable</c:otherwise>
          						   </c:choose>">
    <a href='#' onclick='submitForm("<c:out value='${requestScope.dateUrl}'/>");return false;' style="margin-right:0px;">Expiry Date</a>
</th>
<th>Edit</th>
<th>Delete</th>
</tr>
<logic:iterate id="contract" name="AimsContracts"
               type="com.netpace.aims.controller.contracts.ContractForm" indexId="contractIdx">
    <tr>

        <td align="left">
            <a href='/aims/contractsSetup.do?task=editViewForm&contract_id=<bean:write name="contract" property="contractId" />'
               class="a">                                
                <aims:getTruncatedString name="contract" property="contractTitle" maxLength="<%=AimsConstants.ELLIPSE_STR_LEN%>" fullTextVarName="<%=("title_"+contractIdx)%>" />
            </a>
        </td>
        <td  align="left">
            <bean:write name="contract" property="contractVersion"/>
        </td>
        <td  align="left">
            <bean:write name="contract" property="platformName"/>
        </td>
        <td  align="center">
            <logic:notEmpty name="contract" property="clickThroughContract">
                <logic:equal name="contract" property="clickThroughContract" value="Y">
                    Yes
                </logic:equal>
                <logic:equal name="contract" property="clickThroughContract" value="N">
                    No
                </logic:equal>
            </logic:notEmpty>
        </td>
        <td  align="left">
            <a class="a" target="_blank"
               href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="contract" property="contractId"/>'>
                <bean:write name="contract" property="contractDocumentFileName" filter="false"/>
            </a> &nbsp;
        </td>
        <td  align="center">
            <bean:write name="contract" property="contractStatus"/>
        </td>
        <td  align="center" style="width:12%;">
            <bean:write name="contract" property="contractExpiryDate" formatKey="date.format" filter="true"/>
            &nbsp;
        </td>
        <td  align="center">
            <a href='/aims/contractsSetup.do?task=editForm&contract_id=<bean:write name="contract" property="contractId"/>'
               class="a">
                <bean:message key="images.edit.icon"/>
            </a>
        </td>
        <td  align="center">
            <a href='javascript:submitForm("/aims/contracts.do?task=delete&contract_id=<bean:write name="contract" property="contractId"/>")'
               class="a"
               onclick="javascript:if (window.confirm('Are you sure you want to delete this Contract?')) { return true;} else { return false;}">
                <bean:message key="images.delete.icon"/>
            </a>
        </td>
    </tr>
</logic:iterate>
</table>
</DIV>

<!-- DATA GRID END HERE -->
<!-- PAGER START HERE -->
<table width="100%" cellpadding="0" cellspacing="0" border="0" style="margin-top:10px">
    <logic:greaterThan name="page_max" value="1">
        <tr>
            <td align="right">
                <logic:greaterThan name="page_id" value="1">
                    <a href='#'
                       onclick='submitForm("<c:out value="${pagingLnk}"/>&page_id=<%=page_id.intValue() - 1%>");return false;'><img
                            src="images/greyRndArrowL.gif" height="17" border="0" align="absbottom"/></a>
                </logic:greaterThan>
                <logic:greaterThan name="page_max" value="1">
                    <b><img src="images/spacer.gif" width="3" height="1"/>Page:<img src="images/spacer.gif" width="3"
                                                                                    height="1"/><%=page_id.toString()%><img
                            src="images/spacer.gif" width="3" height="1"/>of<img src="images/spacer.gif" width="3"
                                                                                 height="1"/><%=page_max.toString()%>
                    </b><img src="images/spacer.gif" width="3" height="1"/>
                </logic:greaterThan>
                <logic:lessThan name="page_id" value="<%=page_max.toString()%>">
                    <a href='#'
                       onclick='submitForm("<c:out value="${pagingLnk}"/>&page_id=<%=page_id.intValue() + 1%>");return false;'><img
                            src="images/greyRndArrow.gif" height="17" border="0" align="absbottom"/></a>
                </logic:lessThan>
            </td>
        </tr>
    </logic:greaterThan>
</table>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
    <td width="100%" align="right">
        <div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px" id="Create" title="Create">
            <div>
                <div><a href="/aims/contractsSetup.do?task=createForm">Create</a></div>
            </div>
        </div>
    </td>
</table>
<!-- PAGER END HERE -->

</DIV>
</div>
</html:form>
<script type="text/javascript">
    dropdowncontent.init("filterLink1", "left-bottom", -1)
    window.document.onclick = onclick;
    setFilterDisableState();
</script>
