<%
boolean isPage1=false, isPage2=false, isPage4=false;
boolean isVerizonUser = false, isAllianceUser = false;
boolean statusSaved = false, statusSubmitted = false, statusAccepted = false, statusRejected = false;

%>

<logic:present name="VcastApplicationUpdateForm" >

	<logic:equal name="VcastApplicationUpdateForm" property="currentPage" value="page1" scope="request"	>
		<% isPage1 = true; %>
	</logic:equal>
	<logic:equal name="VcastApplicationUpdateForm" property="currentPage" value="page2" scope="request"	>
		<% isPage2 = true; %>
	</logic:equal>
	<logic:equal name="VcastApplicationUpdateForm" property="currentPage" value="page4" scope="request"	>
		<% isPage4 = true; %>
	</logic:equal>
	
    
    <logic:equal name="VcastApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SAVED_ID.toString()%>">    
        <%statusSaved = true;%>
    </logic:equal>
    <logic:equal name="VcastApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SUBMISSION_ID.toString()%>">    
        <%statusSubmitted = true;%>
    </logic:equal>
    <logic:equal name="VcastApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.ACCEPTANCE_ID.toString()%>">    
        <%statusAccepted = true;%>
    </logic:equal>
    <logic:equal name="VcastApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.REJECTED_ID.toString()%>">    
        <%statusRejected = true;%>
    </logic:equal>
    
</logic:present>

<% isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE); %>
<% isAllianceUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE); %>

