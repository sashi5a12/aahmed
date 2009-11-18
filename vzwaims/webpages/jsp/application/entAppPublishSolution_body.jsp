<%@ page language="java" %>
<%@page import="com.netpace.aims.util.AimsConstants"%>
<%@page import="com.netpace.aims.controller.application.ApplicationHelper"%>
<%@page import="com.netpace.aims.util.AimsPrivilegesConstants"%><%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>
<%@ include file="/common/error.jsp" %>

<DIV class="homeColumnTab lBox">
<html:form action="/entAppManagePublishSolution" enctype="multipart/form-data">

<input type="hidden" name="saved_page_id" value="<%=page_id %>" />
<input type="hidden" name="saved_page_max" value="<%=page_max %>" />                                            
<input type="hidden" name="task" value="view"/>                                           

<DIV class="headLeftCurveblk"></DIV>
<H1>
   <bean:message key="JMAApp.publishSolution.header" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
		
<table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
<tr>
	<th>
		<a href='/aims/entAppManagePublishSolution.do?task=view&sort_field=2&filter_field=<bean:write name="EntAppPublishSolutionForm" property="filterField" />&filter_text=<bean:write name="EntAppPublishSolutionForm" property="filterText" />'>
			<bean:message key="JMAApp.publishSolution.partnerName.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</a>
	</th>
	<th>
		<a href='/aims/entAppManagePublishSolution.do?task=view&sort_field=4&filter_field=<bean:write name="EntAppPublishSolutionForm" property="filterField" />&filter_text=<bean:write name="EntAppPublishSolutionForm" property="filterText" />'>
			<bean:message key="JMAApp.publishSolution.solutionName.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</a>
	</th>
	<th>
		<bean:message key="JMAApp.publishSolution.mobileProfessional.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
	</th>
	<th>
		<bean:message key="JMAApp.publishSolution.soho.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>	
	</th>
	<th>
		<bean:message key="JMAApp.publishSolution.sme.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
	</th>
	<th>
		<bean:message key="JMAApp.publishSolution.enterprise.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
	</th>
	<th>
		<bean:message key="JMAApp.publishSolution.publish.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
	</th>
	<th>
		<bean:message key="JMAApp.publishSolution.featured.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
	</th>
	<th>
		<bean:message key="JMAApp.publishSolution.edit.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
	</th>
<% if(ApplicationHelper.checkAccess(request, AimsConstants.VIEW_TASK, AimsPrivilegesConstants.JMA_APPLICATION_SPOTLIGHT_VIEW)){%>	
	<th>
		<bean:message key="JMAApp.publishSolution.spotlight.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
	</th>
<% } %>	
</tr>

<logic:notEmpty name="EntAppPublishSolutionForm" property="solutionVOs">
<logic:iterate id="solution" name="EntAppPublishSolutionForm" property="solutionVOs" indexId="solIdx" scope="request">
<tr>
	<td>
		<input type="hidden" name='<%="solutionVO["+solIdx+"].allianceId"%>' value='<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].allianceId"%>' />'>                                            
        <input type="hidden" name='<%="solutionVO["+solIdx+"].appId"%>' value='<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].appId"%>' />'>                                            
		
		<a href="/aims/allianceStatus.do?task=view&alliance_id=<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].allianceId"%>' />" class="a">			
			<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].companyName"%>' />
		</a>
		<input type="hidden" name='<%="solutionVO["+solIdx+"].companyName"%>' value='<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].companyName"%>' />'>                                            
	</td>
	
	<td>
		<a href="/aims/entApplicationSetup.do?task=view&appsId=<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].appId"%>' />" class="a">			
			<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].appTitle"%>' />
		</a>
		<input type="hidden" name='<%="solutionVO["+solIdx+"].appTitle"%>' value='<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].appTitle"%>' />'>
	</td>
	
	<td align="center">
		<html:checkbox name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].isMobileProfessional"%>' value="<%=AimsConstants.YES_CHAR%>" />                                        
	</td>
	
	<td align="center">
		<html:checkbox name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].isSoho"%>' value="<%=AimsConstants.YES_CHAR%>" />                                        
	</td>
	
	<td align="center">
		<html:checkbox name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].isSme"%>' value="<%=AimsConstants.YES_CHAR%>" />                                        
	</td>
	
	<td align="center">
			<html:checkbox name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].isEnterprise"%>' value="<%=AimsConstants.YES_CHAR%>" />                                       	
	</td>
	
	<td align="center">
		 <html:checkbox name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].isPublished"%>' value="<%=AimsConstants.YES_CHAR%>" />                                        
	</td>
	
	<td align="center">
		 <html:checkbox name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].isFeatured"%>' value="<%=AimsConstants.YES_CHAR%>" />                                             
	</td>
	
	<td align="center">
		<a href="/aims/entApplicationSetup.do?task=edit&appsId=<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].appId"%>' />">
			<img src="images/icon-edit.gif" alt="Edit Application" width="18" height="13" border="0"/>
		</a>
	</td>
	
<% if(ApplicationHelper.checkAccess(request, AimsConstants.VIEW_TASK, AimsPrivilegesConstants.JMA_APPLICATION_SPOTLIGHT_VIEW)){%>		
	<td align="center">
	<logic:notEmpty name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].displaySpotlight"%>'>
	<logic:equal name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].displaySpotlight"%>' value="Y">
		<a  href="javascript: viewSpotlight(<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].allianceId"%>' />,<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].appId"%>' />);">
			<img src="images/icon-edit.gif" alt="View spotlight" width="18" height="13" border="0"/></a>
	</logic:equal>
	<logic:equal name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].displaySpotlight"%>' value="N">
			<img src="images/icon-edit.gif" alt="View spotlight" width="18" height="13" border="0" title="To view spotlight, please publish the solution"/>
	</logic:equal>	
	</logic:notEmpty>	
	
	<input type="hidden" name='<%="solutionVO["+solIdx+"].displaySpotlight"%>' value='<bean:write name="EntAppPublishSolutionForm" property='<%="solutionVO["+solIdx+"].displaySpotlight"%>' />'>                                            
	</td>
<% } %>	
</tr>

</logic:iterate>
</logic:notEmpty>

<logic:empty name="EntAppPublishSolutionForm" property="solutionVOs">
<tr>
	<td colspan="10" width="100%">
		<bean:message key="error.generic.no.records.for.view" />
	</td>
</tr>
</logic:empty>

</table>
</DIV>



<% if (ApplicationHelper.checkAccess(request, AimsConstants.EDIT_TASK, AimsPrivilegesConstants.PUBLISH_SOLUTION)){ %>
<logic:notEmpty name="EntAppPublishSolutionForm" property="solutionVOs">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>
		&nbsp;
	</td>
</tr>
<tr>
	<td>
		<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Save" title="Save">
			<div><div><div onClick="javascript: submitForm();">Save</div></div></div>
		</div>
	</td>
</tr>

<tr>
	<td>
		&nbsp;
	</td>
</tr>
</table>
</logic:notEmpty>
<% } %>


			<input type="hidden" name="sort_field"
            value="<bean:write name="EntAppPublishSolutionForm" property="sortField"/>"/>
            <input type="hidden" name="alliance_id"
            value="<bean:write name="EntAppPublishSolutionForm" property="allianceId"/>"/>
            <input type="hidden" name="filter_field"
            value="<bean:write name="EntAppPublishSolutionForm" property="filterField"/>"/>
            <input type="hidden" name="filter_text"
            value="<bean:write name="EntAppPublishSolutionForm" property="filterText"/>"/>
            <input type="hidden" name="all_type"
            value="<bean:write name="EntAppPublishSolutionForm" property="allianceType"/>"/>

<logic:greaterThan name="page_max" value="1">
    <table width="100%" cellpadding="0" cellspacing="0" align="center" style="margin-top:10px">
        <tr>
            <td align="right">
                <%
                    int startPageCount = 0;
                    if (page_id.intValue() % 10 == 0)
                        startPageCount = page_id.intValue() - 10 + 1;
                    else
                        startPageCount = page_id.intValue() - (page_id.intValue() % 10) + 1;
                %>
                <logic:greaterThan name="page_id" value="1">
                    <a href='/aims/entAppManagePublishSolution.do?task=view&sort_field=<bean:write name="EntAppPublishSolutionForm" property="sortField" />&alliance_id=<bean:write name="EntAppPublishSolutionForm" property="allianceId" />&filter_field=<bean:write name="EntAppPublishSolutionForm" property="filterField" />&filter_text=<bean:write name="EntAppPublishSolutionForm" property="filterText" />&page_id=<%=page_id.intValue() - 1%>'
                       class="a"><strong>Previous</strong></a><img src="images/spacer.gif" width="10" height="1"/>
                    <%if (startPageCount - 10 > 0) {%>
                    <a href='/aims/entAppManagePublishSolution.do?task=view&sort_field=<bean:write name="EntAppPublishSolutionForm" property="sortField" />&alliance_id=<bean:write name="EntAppPublishSolutionForm" property="allianceId" />&filter_field=<bean:write name="EntAppPublishSolutionForm" property="filterField" />&filter_text=<bean:write name="EntAppPublishSolutionForm" property="filterText" />&page_id=<%=startPageCount - 10%>'
                       class="a"><img src="images/greyRndArrowL.gif" align="absbottom"/></a>
                        <!--<img src="images/previous_icon.gif" height="15" border="0" align="absbottom"/>-->


                    <img src="images/spacer.gif" width="3" height="1"/>
                    <% } %>
                </logic:greaterThan>

                <% for (int pageCount = startPageCount; pageCount < startPageCount + 10; pageCount++) {%>
                <%if (pageCount > 0 && pageCount <= page_max.intValue()) {%>
                <%if (pageCount == page_id.intValue()) {%>
                <b><%=pageCount%><img src="images/spacer.gif" width="1" height="1"/></b>
                <% } else { %>
                <a href='/aims/entAppManagePublishSolution.do?task=view&sort_field=<bean:write name="EntAppPublishSolutionForm" property="sortField" />&alliance_id=<bean:write name="EntAppPublishSolutionForm" property="allianceId" />&filter_field=<bean:write name="EntAppPublishSolutionForm" property="filterField" />&filter_text=<bean:write name="EntAppPublishSolutionForm" property="filterText" />&page_id=<%=pageCount%>'
                   class="a"><%=pageCount%>
                </a><img src="images/spacer.gif" width="1" height="1"/>
                <% } %>
                <% } %>
                <% } %>

                <logic:lessThan name="page_id" value="<%=page_max.toString()%>">
                    <%if (startPageCount + 10 <= page_max.intValue()) {%>
                    <img src="images/spacer.gif" width="3" height="1"/><a
                        href='/aims/entAppManagePublishSolution.do?task=view&sort_field=<bean:write name="EntAppPublishSolutionForm" property="sortField" />&alliance_id=<bean:write name="EntAppPublishSolutionForm" property="allianceId" />&filter_field=<bean:write name="EntAppPublishSolutionForm" property="filterField" />&filter_text=<bean:write name="EntAppPublishSolutionForm" property="filterText" />&page_id=<%=startPageCount + 10%>'
                        class="a"><img src="images/greyRndArrow.gif" align="absbottom"/></a>
                    <% } %>
                    <img src="images/spacer.gif" width="10" height="1"/><a
                        href='/aims/entAppManagePublishSolution.do?task=view&sort_field=<bean:write name="EntAppPublishSolutionForm" property="sortField" />&alliance_id=<bean:write name="EntAppPublishSolutionForm" property="allianceId" />&filter_field=<bean:write name="EntAppPublishSolutionForm" property="filterField" />&filter_text=<bean:write name="EntAppPublishSolutionForm" property="filterText" />&page_id=<%=page_id.intValue() + 1%>'
                        class="a"><strong>Next</strong></a>
                </logic:lessThan>
            </td>
        </tr>
    </table>
   
            
            
        <table cellpadding="0" cellspacing="0" style="margin-top:10px" align="right">
            <tr>
                <td>
                    <strong>Jump to page&nbsp;</strong>
                </td>
                <td>
                    <input type="text" name="page_id" size="4" value="<%=page_id.toString()%>">
                </td>
                <td>
                    <strong>&nbsp;of <%=page_max.toString()%></strong>
                </td>
                <td>
                    <div class="redBtn" style="float:right; margin-left:10px" id="Go" title="Go">
                        <div>
                            <div>
                                <div onclick="document.forms[1].submit()">Go</div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
 
</logic:greaterThan>

</html:form>
<DIV>



<script language="javascript">

function submitForm()
{
	if(confirm('<bean:message key="JMAApp.publishSolution.save.alert"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>'))
	{
		document.forms[1].task.value="save";
		document.forms[1].submit();
	}
	
}

function viewSpotlight(partnerId,appId)
{
	 var url = "/aims/entAppSetupSpotlight.do?task=view&partnerId="+partnerId+"&solutionId="+appId+"&isPublic=true";
     var win = window.open(url, "viewSpotlight", "resizable=0,width=870,height=575,scrollbars=1,screenX=100,left=150,screenY=30,top=30,status=0,titlebar=0");
     win.focus();
}
</script>