<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*, com.netpace.aims.model.security.*, com.netpace.aims.controller.alliance.*, com.netpace.aims.util.*; "%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>


<script  language="JavaScript">

	function truncateLocalTextAreas()
	{
		TruncateTextWithCount(document.forms[0].spotlightDesc,'textCountSpotlightDesc',500);
	}

	function trackCountForTextAreas()
	{
		TrackCount(document.forms[0].spotlightDesc,'textCountSpotlightDesc',500);
	}
	
</script>
<%@ include  file="allianceSpotlightInclude.jsp" %>
<html:form action="/caseStudyEdit.do" enctype="multipart/form-data">	

<html:hidden  property="spotlightTypeId" value="<%=AimsConstants.SPOTLIGHT_CASE_STUDY%>" />

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
			<span class="aimsmasterheading">
                <bean:message key="AllianceProfile.header" bundle="com.netpace.aims.action.ALLIANCE_PROFILE_MESSAGE"/>
            - 
       			<bean:parameter id="taskForThisPage" name="task" value=""/>
				
				<logic:notEmpty  name="taskForThisPage" scope="page" >				 				
				  <bean:define id="value" name="taskForThisPage" type="java.lang.String" toScope="session"/>
				</logic:notEmpty>
				
				<logic:equal name="value" value="createForm">
					Create Case Study
				</logic:equal>
				<logic:equal name="value" value="editForm">
					Edit Case Study
				</logic:equal>
            </span>
       	</td>
  	</tr>
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
            <aims:getAllianceTab attributeName="Tools"/>
       	</td>
  	</tr>
  	<tr> 
       	<td width="20">&nbsp;</td>
       	<td width="100%">&nbsp;
			
       	</td>
  	</tr>
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
            <aims:getSpotlightsTab attributeName="<%=AimsConstants.SPOTLIGHT_CASE_STUDY%>"/>	
       	</td>
  	</tr>
	<%@ include  file="/common/error.jsp" %>
  	<tr> 
       	<td width="20">&nbsp;</td>
       	<td width="100%">&nbsp;
			
       	</td>
  	</tr> 
	<logic:match name="value" value="create">
		<html:hidden  property="task" value="create" />
	</logic:match>
	<logic:match name="value" value="edit">
		<html:hidden  property="task" value="edit" />
		<html:hidden  property="spotlightId" />
	</logic:match>
    <html:hidden property="spotlightFileFileName" />
  	<tr> 
    	<td width="20">&nbsp;</td>
    	<td align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td width="100%">
					<table class="sectable" width="100%" height="100%">
						<tr class="sectitle"><td class="aimssecheading">Case Study</td></tr>
						<tr><td>
	          				<table width="100%" border="0" cellspacing="10" cellpadding="0">
								<tr> 
									<td class="text" align="right">
										<strong>Case Study Name</strong>
									</td>		
									<td class="body" align="left">
										<html:text styleClass="text" property="spotlightName" size="60"
										 maxlength="100"/>
									</td>
								</tr>
								<tr> 
									<td class="text" align="right">
										<strong>Case Study Description</strong>
									</td>
									<td class="body" align="left">
										<html:textarea	property="spotlightDesc" rows="3"	cols="30" onkeyup="TrackCount(this,'textCountSpotlightDesc',500)" onkeypress="LimitText(this,500)"></html:textarea>
										</br>Characters remaining
										<input type="text" name="textCountSpotlightDesc" size="3" value="500" disabled="true" />
									</td>
								</tr>
								<tr> 
									<td class="text" align="right">
										<strong>Case Study Document</strong>
									</td>
									<td class="body" align="left">
										<html:file property="spotlightFile"/>
										<br/>
										<a class="a" target="_blank" href='/aims/spotLightResourceAction.do?resource=spotlightFile&resourceId=<bean:write name="AllianceSpotLightForm" property="spotlightId"/>' >
											<bean:write name="AllianceSpotLightForm" property="spotlightFileFileName" />
										</a>								
									</td>
								</tr> 
							</table>
						</td></tr>
					</table>
				</td></tr>
				<tr> 
					<td height="25" align="right" valign="middle">
						<input type="image" src="images/submit_b.gif" width="52" height="15" border="0" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</html:form>
<script	language="javascript">
   trackCountForTextAreas();				
</script>