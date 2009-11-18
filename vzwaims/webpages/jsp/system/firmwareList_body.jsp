<%@ page language="java"%>

<%@ page
	import="com.netpace.aims.model.core.*,com.netpace.aims.model.masters.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:useBean id="page_id" class="java.lang.Integer" scope="request" />
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request" />

<%@ include file="/common/error.jsp"%>

<div class="btnTopLine">
	<div class="redBtn" style="margin-left: 5px; float: right; margin-top: 10px; margin-bottom: 10px;" title="Create">
		<div><div><div onclick="window.location='firmwareSetup.do?task=create'">Create</div></div></div>
	</div>
</div>


<DIV class="homeColumnTab lBox">
	<DIV class="headLeftCurveblk"></DIV>
	<H1>View Firmwares</H1>
	<DIV class="headRightCurveblk"></DIV>
	<DIV class="contentbox">

		<table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
			<tr>
				<th>Phone Model</th>
				<th>Firmware</th>
				<th>MR No.</th>
				<th>VZW Platforms Supported</th>
				<th>Status</th>
				<th>Edit</th>
				<th>Delete</th>
			</tr>

			<%
				int iCount = 0;
			%>
			
			<c:forEach items="${requestScope.FirmwaresList}" var="item">
				<tr>
					<td align="left"><c:out value="${item[0]}"/></td>
					<td align="left"><c:out value="${item[1]}"/></td>
					<td align="left"><c:out value="${item[2]}"/></td>
					<td align="left"><c:out value="${item[3]}"/></td>
					<td align="left"><c:out value="${item[4]}"/></td>
					<td align="center">
						<a href='firmwareSetup.do?task=edit&firmwareId=<c:out value="${item[5]}"/>' class="a">
							<html:img src="images/edit_icon.gif" border="0" alt="Edit" /></a>
					</td>

					<td align="center">
						<a href='firmwareSetup.do?task=delete&firmwareId=<c:out value="${item[5]}"/>' class="a"
							onClick="javascript:if (window.confirm('Are you sure you want to delete this firmware?')) { return true;} else { return false;}">
							<html:img src="images/delete_icon.gif" border="0" alt="Delete" />
						</a>
					</td>
				</tr>
				<%
					iCount = 1;
				%>
			</c:forEach>
			<%
				if (iCount == 0) {
			%>
			<tr>
				<td align="center" colspan="7">
					No Firmware Information Found.
				</td>
			</tr>
			<%
				}
			%>
		</table>


		<!-- PAGER START HERE -->
		<table width="100%" cellpadding="0" cellspacing="0" border="0" style="margin-top: 10px">
			<tr>
				<td align="right">
					<logic:greaterThan name="page_id" value="1">
						<a href="firmwareSetup.do?page_id=<%=page_id.intValue() - 1%>"><img src="images/greyRndArrowL.gif" alt="Previous" align="absbottom" /></a>
					</logic:greaterThan>
					<logic:greaterThan name="page_max" value="1">
						<b> <img alt="" src="images/spacer.gif" width="3" height="1" />Page<img	alt="" src="images/spacer.gif" width="3" height="1" /><%=page_id.toString()%>
							<img alt="" src="images/spacer.gif" width="3" height="1" />of <img alt="" src="images/spacer.gif" width="3" height="1" /><%=page_max.toString()%>
							<img alt="" src="images/spacer.gif" width="3" height="1" /> </b>
					</logic:greaterThan>
					<logic:lessThan name="page_id" value="<%=page_max.toString()%>">
						<a href="firmwareSetup.do?page_id=<%=page_id.intValue() + 1%>"><img	src="images/greyRndArrow.gif" alt="Next" align="absbottom" /></a>
					</logic:lessThan>
				</td>
			</tr>
		</table>

		<table align="right" width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<div class="redBtn" id="Create"	style="float: right; margin-top: 10px;" title="Create">
						<div><div><div onclick="window.location='firmwareSetup.do?task=create'">Create</div></div></div>
					</div>
				</td>
			</tr>
		</table>

	</DIV>
</DIV>
