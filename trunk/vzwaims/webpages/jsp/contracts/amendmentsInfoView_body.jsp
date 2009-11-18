<%@ page language="java" %>

<%@ page
        import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.alliance.* " %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>

<%@ include file="/common/error.jsp" %>

<div class="btnTopLine">  <%-- #8199b8--%>
    <div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Create"
         title="Create">
        <div>
            <div><div onclick="window.location = 'amendmentsSetup.do?task=createForm'">Create</div></div>
        </div>
    </div>
</div>


<html:form action="/amendments">
<!--System Management - Manage Amendments-->
<div class="homeColumnTab lBox">
<div class="headLeftCurveblk"></div>
<H1>
    Amendments List
</H1>

<div class="headRightCurveblk"></div>
<div class="contentbox">
    <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
        <tr>
            <th class="sortable">
                Title
            </th>
            <th class="sortable">
                Version
            </th>
            <th class="sortable">
                Document
            </th>
            <th class="sortable">
                Status
            </th>
            <th class="sortable">
                Expiry Date
            </th>
            <th class="sortable">
                Edit
            </th>
            <th class="sortable">
                Delete
            </th>
        </tr>

        <logic:iterate id="amendment" name="AimsAmendments"
                       type="com.netpace.aims.controller.contracts.AmendmentForm">
            <tr>
                <td align="left">
                    <a href='/aims/amendmentsSetup.do?task=editViewForm&amendment_id=<bean:write name="amendment" property="amendmentId"/>'
                       class="a">
                        <bean:write name="amendment" property="amendmentTitle"/>
                    </a>
                </td>
                <td align="left">
                    <bean:write name="amendment" property="amendmentVersion"/>
                </td>
                <td align="left">
                    <a class="a" target="_blank"
                       href='/aims/amendmentsResourceAction.do?resource=document&object=Amendtment&resourceId=<bean:write name="amendment" property="amendmentId"/>'>
                        <bean:write name="amendment" property="amendmentDocumentFileName" filter="false"/>
                    </a> &nbsp;
                </td>
                <td align="left">
                    <bean:write name="amendment" property="amendmentStatus"/>
                </td>
                <td align="left">
                    <bean:write name="amendment" property="amendmentExpiryDate" formatKey="date.format"
                                filter="true"/>
                </td>
                <td align="center">
                    <a href='/aims/amendmentsSetup.do?task=editForm&amendment_id=<bean:write name="amendment" property="amendmentId"/>'
                       class="a">
                        <html:img src="images/edit_icon.gif" border="0" alt="Edit"/>
                    </a>
                </td>
                <td align="center">
                    <a href='/aims/amendments.do?task=delete&amendment_id=<bean:write name="amendment" property="amendmentId"/>'
                       class="a"
                       onClick="javascript:if (window.confirm('Are you sure you want to delete this Amendment?')) { return true;} else { return false;}">
                        <img src="images/icon-delete.gif" alt="Delete" width="15" height="14" border="0"/>
                    </a>
                    <a href="#" class="a"
                       onClick="javascript:if (window.confirm('Are you sure you want to delete this Amendment?')) { return true;} else { return false;}">
                    </a>
                </td>
            </tr>
        </logic:iterate>
    </table>
</div>

<table width="100%" cellpadding="0" cellspacing="0" border="0" style="margin-top:10px">
    <tr>
        <td align="right">
            <logic:greaterThan name="page_id" value="1">
                <a href='amendments.do?task=view&page_id=<%=page_id.intValue() - 1%>'><img
                        src="images/greyRndArrowL.gif"
                        alt="Previous"
                        align="absbottom"/></a>
            </logic:greaterThan>
            <logic:greaterThan name="page_max" value="1">
                <b>
                    <img alt="" src="images/spacer.gif" width="3" height="1"/>Page
                    <img alt="" src="images/spacer.gif" width="3" height="1"/><%=page_id.toString()%>
                    <img alt="" src="images/spacer.gif" width="3" height="1"/>of
                    <img alt="" src="images/spacer.gif" width="3" height="1"/><%=page_max.toString()%>
                    <img alt="" src="images/spacer.gif" width="3" height="1"/>
                </b>
            </logic:greaterThan>
            <logic:lessThan name="page_id" value="<%=page_max.toString()%>">
                <a href='amendments.do?task=view&page_id=<%=page_id.intValue() + 1%>'>
                    <img src="images/greyRndArrow.gif" alt="Next" align="absbottom"/>
                </a>
            </logic:lessThan>
        </td>
    </tr>
</table>

<table align="right" width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="redBtn" style="float:right; margin-top:10px;" id="Create" title="Create">
                <div>
                    <div><div onclick="window.location='amendmentsSetup.do?task=createForm'">Create</div></div>
                </div>
            </div>
        </td>
    </tr>
</table>

</div>
</html:form>