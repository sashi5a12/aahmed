<%@	page language="java" %>
 
<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@	taglib uri="/WEB-INF/struts-template.tld" prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<DIV id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

<aims:getVZWAllianceTab attributeName="Important Info" allianceId="<%=request.getParameter("alliance_id")%>"/>

<div>&nbsp;</div>

<div style="padding-bottom:10px">
    <strong>Company Name: <bean:write name="companyName" scope="request" /> </strong>
</div>




    <table width="100%" cellpadding="0" cellspacing="0" class="GridGradient">
        <tr>
            <td class="noPad">
                <DIV class="headLeftCurveblk"></DIV>
                <H1> Important Information </H1>
                <DIV class="headRightCurveblk"></DIV>
            </td>
        </tr>
        <tr>
            <th class="blulink">
                <aims:getToolsTab attributeName="<%=request.getParameter("alliance_id")%>" typeName="bullets" />&nbsp; 
            </th>
        </tr>
    </table>
</DIV>
</DIV>
