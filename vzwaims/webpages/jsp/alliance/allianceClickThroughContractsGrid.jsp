<%@ page import="com.netpace.aims.util.AimsConstants"%>
<%@page import="com.netpace.aims.controller.application.ApplicationHelper"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<script language="javascript">
    function setFilterDisableState(cbName){
        var cb=document.getElementsByName(cbName);
        if (cb[0].value=="<%=AimsConstants.FILTER_SHOW_ALL %>" && cb[0].checked==true){
            for(i=1; i<cb.length; i++){
                cb[i].checked=false;
                cb[i].disabled=true;
            }
        }
        else if (cbName=='selectedFilterCTPlatform'){
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
            }
        }
    }
</script>
<c:choose>
	<c:when test="${requestScope.filterCTPlatform eq 'Showing All'}">
		<c:set var="ctPlatformIcon" value="images/icon_filter.gif" scope="page"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="ctPlatformIcon" value="images/icon_filter2.gif" scope="page"></c:set>
	</c:otherwise>
</c:choose>
<table width="100%" cellspacing="0" cellpadding="0" border="0">

    <tr>
        <td>
            <DIV class="headLeftCurveblk"></DIV>
            <H1>Click Through Contracts</H1>
            <DIV class="headRightCurveblk"></DIV>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
                <tr>
                    <th>Please review the list of Click Through Contracts below. To Review and Accept the Contract, please click on the Title of the Contract</th>
                </tr>
            </table>
        </td>
    </tr>

    <tr>
        <td>&nbsp;</td>
    </tr>
     
    <tr>
        <td>
            <DIV class="headLeftCurveblk"></DIV>
            <H1>Contracts List</H1>
            <DIV class="headRightCurveblk"></DIV>
        </td>
    </tr>
    <tr>
        <td>
            <table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
                <tr>
                    <th width="35%">
                        <strong>Title</strong>
                    </th>
                    <th >
                        <strong>Ver</strong>
                    </th>
                    <th >
                        <strong>Platform</strong>
                        <!-- Click Through Contract PLATFORM FILTER POPUP START HERE -->
                        <a href="#" onclick="return false;"><img src='<c:out value="${ctPlatformIcon}"/>'
                                                         name="filterLink1" width="11" height="11" border="0" align="absmiddle" id="filterLink1" rel="contentDiv1" title='Platform Name = "<c:out value="${requestScope.filterCTPlatform}"/>"'></a>
                            <DIV id="contentDiv1" class="divContent">
								<div style="width:132px">
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
								<DIV class="divFormContent" style="	width:130px;">
									<table border="0" cellspacing="0" cellpadding="0" style="border:none; background-color:none;">
										<tr>
											<td>
												<html:multibox property="selectedFilterCTPlatform"><%=AimsConstants.FILTER_SHOW_ALL%></html:multibox>
													<a href="#" onClick="singleSelect('selectedFilterCTPlatform','<%=AimsConstants.FILTER_SHOW_ALL%>',document.forms[0]);return false;"><%=AimsConstants.FILTER_LABEL_SHOW_ALL%></a>
											</td>
										</tr>
										<c:forEach var="ctPlatform" items="${requestScope.allCTPlatforms}">
										
											<%												
												String platform = ((Object[])pageContext.getAttribute("ctPlatform"))[0].toString();												
												if ( platform.equalsIgnoreCase(AimsConstants.JAVA_PLATFORM_ID.toString()) && (ApplicationHelper.checkPlatformAccess(request, AimsConstants.VIEW_TASK, AimsConstants.JAVA_PLATFORM_ID)) ) 												
												{
											 %>
											 		<tr>
														<td align="left" valign="top">
															<html:multibox property="selectedFilterCTPlatform"><c:out value="${ctPlatform[0]}" /></html:multibox>
															<a href="#" onClick="singleSelect('selectedFilterCTPlatform','<c:out value="${ctPlatform[0]}" />',document.forms[0]);return false;">
																<c:out value="${ctPlatform[1]}" /></a>
														</td>
													</tr>
											 <%
											 	}
											 	else if ( !platform.equalsIgnoreCase(AimsConstants.JAVA_PLATFORM_ID.toString()))
											 	{
											  %>
													<tr>
														<td align="left" valign="top">
															<html:multibox property="selectedFilterCTPlatform"><c:out value="${ctPlatform[0]}" /></html:multibox>
															<a href="#" onClick="singleSelect('selectedFilterCTPlatform','<c:out value="${ctPlatform[0]}" />',document.forms[0]);return false;">
																<c:out value="${ctPlatform[1]}" /></a>
														</td>
													</tr>
											  <%
											  	}
											   %>											
										
										
										
										<%-- 
										
											<tr>
												<td align="left" valign="top">
													<html:multibox property="selectedFilterCTPlatform"><c:out value="${ctPlatform[0]}" /></html:multibox>
													<a href="#" onClick="singleSelect('selectedFilterCTPlatform','<c:out value="${ctPlatform[0]}" />',document.forms[0]);return false;">
														<c:out value="${ctPlatform[1]}" /></a>
												</td>
											</tr>
											--%>
											
											
										</c:forEach>
									</table>
								</DIV>
								<DIV class="divFormButton">
									<div class="redBtn" id="filter" title="Filter">
										<div><div><div onclick="setFilterDisableState('selectedFilterCTPlatform');validateFilter('selectedFilterCTPlatform',document.forms[0]);">Filter</div></div></div>
									</div>
								</DIV>
							</DIV>
                        <!-- Click Through Contract PLATFORM FILTER POPUP END HERE -->
                    </th>                    
                    <th >
                        <strong>Expiry Date</strong>
                    </th>
                    <th width="15%">
                        &nbsp;
                    </th>
                </tr>
                <logic:notEmpty name="availableClickThroughContracts">
                <logic:iterate id="clickThroughContract" name="availableClickThroughContracts">
                    <bean:define id="clickThroughContractId" type="java.lang.Long" name="clickThroughContract" property="contractId"/>
                    <tr>
                        <td align="left">
                            <a  onclick="submit_click_event(<%=clickThroughContractId%>, '','CT',-1,<%=clickThroughContractId%>);document.forms[0].submit()"
                                class="a" title="Click here to Accept Contract" style="cursor:pointer;text-decoration:underline;">
                                <bean:write name="clickThroughContract" property="contractTitle"/>
                            </a>                            
                        </td>

                        <td align="left">
                            <bean:write name="clickThroughContract" property="contractVersion"/>
                        </td>
                        <td align="left">
                            <bean:write name="clickThroughContract" property="contractPlatformName"/>
                        </td>                        
                        <td align="center">
                            <bean:write name="clickThroughContract" property="contractExpDate" formatKey="date.format" filter="true"/>
                        </td>
                        <td style="text-align:center;vertical-align:top;">
                            &nbsp;
                            <a  onclick="submit_click_event(<%=clickThroughContractId%>, '','CT',-1,<%=clickThroughContractId%>);document.forms[0].submit()"
                                class="a" title="Click here to Accept Contract" style="cursor:pointer;text-decoration:underline;">
                                Review Contract
                            </a>
                            &nbsp;
                        </td>
                    </tr>
                </logic:iterate>
            </logic:notEmpty>
            <logic:empty name="availableClickThroughContracts">
                <tr>
                    <td colspan="7">No Click Through Contract found</td>
                </tr>
            </logic:empty>
            </table>
        </td>
    </tr>
    <tr>
        <td>&nbsp;</td>
    </tr>
</table>
<script type="text/javascript">
    dropdowncontent.init("filterLink1", "left-bottom", -1);
    window.document.onclick=onclick;
    setFilterDisableState('selectedFilterCTPlatform');
</script>