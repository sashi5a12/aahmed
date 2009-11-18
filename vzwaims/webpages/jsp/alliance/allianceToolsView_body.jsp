<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>


<%@ include file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
<div class="homeColumnTab lBox">
    <aims:getAllianceTab attributeName="Important Info"/>

<div class="contentbox">

<table width="100%" cellpadding="0" cellspacing="0">
    <tr>
        <td valign="middle" height="36">
            <strong>Company Name: <bean:write name="companyName" scope="request"/></strong>
        </td>
    </tr>
    <tr>
        <td valign="middle">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>Important Information</H1>
            <DIV class="headRightCurveblk"></DIV>
        </td>
    </tr>
    <tr>
        <td valign="middle">
            <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
                <tr>
                    <th class="blulink">
                        <aims:getToolsTab attributeName="<%=request.getParameter("alliance_id")%>" typeName="bullets"/> &nbsp;
                    </th>
                </tr>
            </table>
        </td>
    </tr>
</table>


</div>
</div>
</div>