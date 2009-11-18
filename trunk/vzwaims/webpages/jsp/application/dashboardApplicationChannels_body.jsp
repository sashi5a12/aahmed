<%@ page language="java"%>

<%@ page import="com.netpace.aims.model.core.*,com.netpace.aims.model.masters.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ include file="/common/error.jsp"%>

<DIV class="homeColumnTab lBox">
	<DIV class="headLeftCurveblk"></DIV>
	<H1>Current Dashboard Channel ID's</H1>
	<DIV class="headRightCurveblk"></DIV>
	<DIV class="contentbox">
		<table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
			<tr>
				<th>Channel ID</th>
				<th>Application Title</th>
				<th>Company Title</th>
			</tr>
			<c:forEach var="channel" items="${requestScope.list}">
				<tr>
					<td align="left"><c:out value="${channel.channelId}"></c:out></td>
					<td align="left"><c:out value="${channel.appTile}">&nbsp;</c:out></td>
					<td align="left"><c:out value="${channel.companyName}">&nbsp;</c:out></td>
				</tr>
			</c:forEach>
			<c:if test="${not empty requestScope.listEmpty}">
				<tr>
					<td align="center" colspan="3">No Current Channel ID's Information Found.</td>
				</tr>
			</c:if>
			<tr>
				<td align="left" colspan="3"><strong>* Denotes Pre-Assigned Channel ID's</strong></td>
			</tr>
		</table>
	</DIV>
</DIV>
