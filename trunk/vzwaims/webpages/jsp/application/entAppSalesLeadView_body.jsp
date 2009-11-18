<%@ page language="java" %>
<%@page import="com.netpace.aims.util.AimsConstants"%>
<%@page import="com.netpace.aims.model.core.AimsUser"%>
<%@page import="com.netpace.aims.controller.application.ApplicationHelper"%>
<%@page import="com.netpace.aims.util.AimsPrivilegesConstants"%><%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<jsp:useBean id="page_id" class="java.lang.Integer" scope="request"/>
<jsp:useBean id="page_max" class="java.lang.Integer" scope="request"/>

<%@ include file="/common/error.jsp" %>

<%
	  String currUserType = ((AimsUser) (request.getSession().getAttribute(AimsConstants.AIMS_USER))).getUserType();
	   
%>

<DIV class="homeColumnTab lBox">
<html:form action="/entAppSalesLeadView" enctype="multipart/form-data">

<input type="hidden" name="saved_page_id" value="<%=page_id %>" />
<input type="hidden" name="saved_page_max" value="<%=page_max %>" />


<DIV class="headLeftCurveblk"></DIV>
<H1>
   <bean:message key="JMAApp.submitSalesLead.header" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">

<table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
<tr>
<% if(currUserType.equals(AimsConstants.VZW_USERTYPE)){%>
	<th>
		 <a href='/aims/entAppSalesLeadView.do?view=<bean:write name="EntAppSalesLeadViewForm" property="view"/>&sort_field=7&filter_field=<bean:write name="EntAppSalesLeadViewForm" property="filterField" />&filter_text=<bean:write name="EntAppSalesLeadViewForm" property="filterText" />'>
			<bean:message key="JMAApp.submitSalesLead.allianceName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		 </a>	
	</th>
<% } %>	
	<th>
		 <a href='/aims/entAppSalesLeadView.do?view=<bean:write name="EntAppSalesLeadViewForm" property="view"/>&sort_field=2&filter_field=<bean:write name="EntAppSalesLeadViewForm" property="filterField" />&filter_text=<bean:write name="EntAppSalesLeadViewForm" property="filterText" />'>
			<bean:message key="JMAApp.submitSalesLead.customerName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</a>
	</th>
	<th>
		 <a href='/aims/entAppSalesLeadView.do?view=<bean:write name="EntAppSalesLeadViewForm" property="view"/>&sort_field=3&filter_field=<bean:write name="EntAppSalesLeadViewForm" property="filterField" />&filter_text=<bean:write name="EntAppSalesLeadViewForm" property="filterText" />'>
			<bean:message key="JMAApp.submitSalesLead.solutionName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</a>
	</th>
	<th>
		 <a href='/aims/entAppSalesLeadView.do?view=<bean:write name="EntAppSalesLeadViewForm" property="view"/>&sort_field=4&filter_field=<bean:write name="EntAppSalesLeadViewForm" property="filterField" />&filter_text=<bean:write name="EntAppSalesLeadViewForm" property="filterText" />'>
			<% if(currUserType.equals(AimsConstants.VZW_USERTYPE)){%>
				<logic:notEmpty name="EntAppSalesLeadViewForm" property="view">
					<logic:equal name="EntAppSalesLeadViewForm" property="view" scope="request" value="sent">
						<bean:message key="JMAApp.submitSalesLead.verizonSalesRep" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
					</logic:equal>
					<logic:equal name="EntAppSalesLeadViewForm" property="view" scope="request" value="received">
						<bean:message key="JMAApp.submitSalesLead.partnerSalesRep" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
					</logic:equal>
				</logic:notEmpty>
							
			<% } else { %>
				<logic:notEmpty name="EntAppSalesLeadViewForm" property="view">
					<logic:equal name="EntAppSalesLeadViewForm" property="view" scope="request" value="sent">
						<bean:message key="JMAApp.submitSalesLead.partnerSalesRep" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
					</logic:equal>
					<logic:equal name="EntAppSalesLeadViewForm" property="view" scope="request" value="received">
						<bean:message key="JMAApp.submitSalesLead.verizonSalesRep" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
					</logic:equal>
				</logic:notEmpty>
			<% } %>	
		</a>
	</th>
	<th>
		 <a href='/aims/entAppSalesLeadView.do?view=<bean:write name="EntAppSalesLeadViewForm" property="view"/>&sort_field=5&filter_field=<bean:write name="EntAppSalesLeadViewForm" property="filterField" />&filter_text=<bean:write name="EntAppSalesLeadViewForm" property="filterText" />'>
			<bean:message key="JMAApp.submitSalesLead.status" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
		</a>	
	</th>
	<th>
		Edit
	</th>
<% if(currUserType.equals(AimsConstants.VZW_USERTYPE)){
	if(ApplicationHelper.checkAccess(request, AimsConstants.DELETE_TASK, AimsPrivilegesConstants.SUBMIT_JMA_SALES_LEAD)){
%>	
	<th>
		Delete
	</th>
<% }
 } %>		
</tr>

<logic:notEmpty name="EntAppSalesLeadViewForm" property="salesLeadVOs">
<logic:iterate  id="salesLead" name="EntAppSalesLeadViewForm" property="salesLeadVOs" indexId="slIndex" scope="request">
<tr>
<% if(currUserType.equals(AimsConstants.VZW_USERTYPE)){%>
	<td>
		<a href="/aims/allianceStatus.do?task=view&alliance_id=<bean:write name="EntAppSalesLeadViewForm" property='<%="salesLeadVO["+slIndex+"].allianceId"%>' />" class="a">			
		<bean:write name="EntAppSalesLeadViewForm" property='<%="salesLeadVO["+slIndex+"].allianceName"%>' />
		</a>
	</td>
<% } %>		
	<td>
		<bean:write name="EntAppSalesLeadViewForm" property='<%="salesLeadVO["+slIndex+"].customerName"%>' />
	</td>
	<td>
		<bean:write name="EntAppSalesLeadViewForm" property='<%="salesLeadVO["+slIndex+"].solutionName"%>' />
	</td>
	<td>
		 <a class="a" href="mailto:<bean:write name='EntAppSalesLeadViewForm' property='<%="salesLeadVO["+slIndex+"].salesRepEmailAddress"%>'  />" >
		    <bean:write name="EntAppSalesLeadViewForm" property='<%="salesLeadVO["+slIndex+"].saledRepresentative"%>' />
		 </a>   
	</td>
	<td align="center">
		<bean:write name="EntAppSalesLeadViewForm" property='<%="salesLeadVO["+slIndex+"].salesLeadStatus"%>' />
	</td>
	<td align="center">
		<a href="/aims/entAppSalesLeadSetup.do?task=edit&sales_lead_id=<bean:write name="EntAppSalesLeadViewForm" property='<%="salesLeadVO["+slIndex+"].salesLeadId"%>' />&view=<bean:write name="EntAppSalesLeadViewForm" property="view"/>" class="a">			
			<img src="images/icon-edit.gif" alt="edit" width="18" height="13" border="0"/>
		</a>	
	</td>
<% if(currUserType.equals(AimsConstants.VZW_USERTYPE)){
if(ApplicationHelper.checkAccess(request, AimsConstants.DELETE_TASK, AimsPrivilegesConstants.SUBMIT_JMA_SALES_LEAD)){%>	
	<td align="center">
		<a href="/aims/entAppSalesLeadView.do?task=delete&view=<bean:write name="EntAppSalesLeadViewForm" property="view"/>&sales_lead_id=<bean:write name="EntAppSalesLeadViewForm" property='<%="salesLeadVO["+slIndex+"].salesLeadId"%>' />&alliance_id=<bean:write name="EntAppSalesLeadViewForm" property='<%="salesLeadVO["+slIndex+"].allianceId"%>' />" class="a">			
			<img src="images/delete_icon.gif" alt="delete" width="15" height="14" border="0"/>
		</a>
	</td>
<% } 
 } %>		
</tr>
</logic:iterate>
</logic:notEmpty>
</table>	
	
</DIV>
<input type="hidden" name="view" value='<bean:write name="EntAppSalesLeadViewForm" property="view"/>'>
<input type="hidden" name="sort_field" value="<bean:write name="EntAppSalesLeadViewForm" property="sortField"/>"/>
<input type="hidden" name="filter_field" value="<bean:write name="EntAppSalesLeadViewForm" property="filterField"/>"/>
<input type="hidden" name="filter_text" value="<bean:write name="EntAppSalesLeadViewForm" property="filterText"/>"/>
            

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
                    <a href='/aims/entAppSalesLeadView.do?task=view&sort_field=<bean:write name="EntAppSalesLeadViewForm" property="sortField" />&view=<bean:write name="EntAppSalesLeadViewForm" property="view" />&filter_field=<bean:write name="EntAppSalesLeadViewForm" property="filterField" />&filter_text=<bean:write name="EntAppSalesLeadViewForm" property="filterText" />&page_id=<%=page_id.intValue() - 1%>'
                       class="a"><strong>Previous</strong></a><img src="images/spacer.gif" width="10" height="1"/>
                    <%if (startPageCount - 10 > 0) {%>
                    <a href='/aims/entAppSalesLeadView.do?task=view&sort_field=<bean:write name="EntAppSalesLeadViewForm" property="sortField" />&view=<bean:write name="EntAppSalesLeadViewForm" property="view" />&filter_field=<bean:write name="EntAppSalesLeadViewForm" property="filterField" />&filter_text=<bean:write name="EntAppSalesLeadViewForm" property="filterText" />&page_id=<%=startPageCount - 10%>'
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
                <a href='/aims/entAppSalesLeadView.do?task=view&sort_field=<bean:write name="EntAppSalesLeadViewForm" property="sortField" />&view=<bean:write name="EntAppSalesLeadViewForm" property="view" />&filter_field=<bean:write name="EntAppSalesLeadViewForm" property="filterField" />&filter_text=<bean:write name="EntAppSalesLeadViewForm" property="filterText" />&page_id=<%=pageCount%>'
                   class="a"><%=pageCount%>
                </a><img src="images/spacer.gif" width="1" height="1"/>
                <% } %>
                <% } %>
                <% } %>

                <logic:lessThan name="page_id" value="<%=page_max.toString()%>">
                    <%if (startPageCount + 10 <= page_max.intValue()) {%>
                    <img src="images/spacer.gif" width="3" height="1"/><a
                        href='/aims/entAppSalesLeadView.do?task=view&sort_field=<bean:write name="EntAppSalesLeadViewForm" property="sortField" />&view=<bean:write name="EntAppSalesLeadViewForm" property="view" />&filter_field=<bean:write name="EntAppSalesLeadViewForm" property="filterField" />&filter_text=<bean:write name="EntAppSalesLeadViewForm" property="filterText" />&page_id=<%=startPageCount + 10%>'
                        class="a"><img src="images/greyRndArrow.gif" align="absbottom"/></a>
                    <% } %>
                    <img src="images/spacer.gif" width="10" height="1"/><a
                        href='/aims/entAppSalesLeadView.do?task=view&sort_field=<bean:write name="EntAppSalesLeadViewForm" property="sortField" />&view=<bean:write name="EntAppSalesLeadViewForm" property="view" />&filter_field=<bean:write name="EntAppSalesLeadViewForm" property="filterField" />&filter_text=<bean:write name="EntAppSalesLeadViewForm" property="filterText" />&page_id=<%=page_id.intValue() + 1%>'
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

</DIV>