<%@ page language="java" %>

<%@ page import="com.netpace.aims.model.core.*, com.netpace.aims.model.masters.*"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<jsp:useBean id="TotalPages"	class="java.lang.Integer"	scope="request"/>
<script language="Javascript">
<!--
	function submitReq(iTask)
	{
		if (iTask == 1)
		{
			document.forms[0].task.value='previous';
			document.forms[0].submit();
		}
		else if (iTask == 2)
		{
			document.forms[0].task.value='next';
			document.forms[0].submit();
		}
		else if (iTask == 3)
		{
			document.forms[0].task.value='list';
			document.forms[0].submit();
		}
	}
//-->
</script>
<script language="Javascript">
<!--
	var bSelectedApp = false;

	function submitReq2(iTask)
	{
		bSelected = false;

		if (iTask == 1)
		{
			document.forms[0].task.value='previous';
			document.forms[0].submit();
		}
		else if (iTask == 2)
		{
			document.forms[0].task.value='next';
			document.forms[0].submit();
		}
		else if (iTask == 3)
		{
			document.forms[0].task.value='list';
			document.forms[0].submit();
		}
		else if (iTask == 4)
		{
			if ( !eval(document.forms[0].appsIds) )
			{
				alert('Sorry No Contents Available To Select');
				return;
			}
			else
			{
				// If selected application details are already passed from previous page just submit
				if (bSelectedApp) {
						document.forms[0].action = '/aims/newMktContReqCreateEdit.do';
						document.forms[0].task.value='create';
						document.forms[0].submit();
						return;
				}

				// else check for single checkbox otherwise check array of checkboxes for checked status
				if ( !eval(document.forms[0].appsIds.length))
				{
				 	if (!document.forms[0].appsIds.checked) {
					alert('Please Tick Checkbox Against Content \nTo Create Creative Content Request');
					return;} else {
						document.forms[0].action = '/aims/newMktContReqCreateEdit.do';
						document.forms[0].task.value='create';
						document.forms[0].submit();
					}
				}
				else
				{
					for (iCount = 0 ; iCount < document.forms[0].appsIds.length ; iCount++)
					{
						if (document.forms[0].appsIds[iCount].checked)
						{
							bSelected = true;
							break;
						}
					}
					if (bSelected)
					{
						document.forms[0].action = '/aims/newMktContReqCreateEdit.do';
						document.forms[0].task.value='create';
						document.forms[0].submit();
					}
					else
					{
						alert('Please Tick Atleast One Checkbox Against \Content To Create Creative Content Request');
					}
				}
			}


		}
	}
//-->
</script>


<html:form action="/newMktProvApps.do">
<html:hidden property="task"/>
<html:hidden property="pageNo"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
	    <td width="20">&nbsp;</td>
		<td width="100%">
            <span	class="aimsmasterheading">Provisioned Contents List</span>
		</td>
	</tr>
    <!--tr> 
       <td width="20">&nbsp;</td>
       <td width="100%">
            &nbsp;
       </td>
    </tr-->
    	<%@ include  file="/common/error.jsp" %>
    
	<tr>
		<td	width="20">&nbsp;</td>
    	<td width="100%" align="center" valign="middle" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="100%" valign="top">
						<table class="sectable" width="100%" height="100%">
							<tr class="sectitle"><td colspan="2" class="aimssecheading">Provisioned Contents</td></tr>
                			<tr>
                    			<td colspan="2" align="center" valign="middle" bgcolor="#EBEBEB"> 
				          	        <table width="100%" border="0" cellspacing="0" cellpadding="0">		
			      			            <tr>
			      			      	        <td bgcolor="#EBEBEB">
			      		      		            <table width="100%" border="0" class="tabletop" cellspacing="0" cellpadding="2" >
			      	      			                <tr bgcolor="#BBBBBB">
			      	      			                
			      	      			            <td class="firstcell" align="center">
			                                                <span class="modFormFieldLbl">&nbsp;</span>
			                                            </td>
			                                            <td class="cell" align="left">
			                                                <span class="modFormFieldLbl">Content Title</span>
			                                            </td>
   									<td class="cell" align="left">
			                                                <span class="modFormFieldLbl">Developer Name</span>
			                                            </td>
								    <td class="cell" align="left">
			                                                <span class="modFormFieldLbl">Approval Date</span>
			                                            </td>	
														<td class="cell" align="left">
			                                                <span class="modFormFieldLbl">Expiry Date</span>
			                                            </td>	
														<td class="cell" align="left">
			                                                <span class="modFormFieldLbl">Current Status</span>
			                                            </td>	
			                            	        </tr>
									   <logic:iterate id="app" name="ApplicationList" type="com.netpace.aims.controller.newmarketing.ProvApplicationExt">
										<tr>
										<td class="firstcell" align="left"> &nbsp;
										<logic:equal name="app" property="isActive" value="true"> 
									    		<input type="checkbox" name="appsIds" value="<bean:write name="app" property="applicationId"/>">
										</logic:equal>
										</td>
										
										 <td class="cell" align="left"><logic:equal name="app" property="isActive" value="true"><a href="newMktApplicationView.do?task=view&applicationId=<bean:write name="app" property="applicationId"/>"><bean:write name="app" property="appTitle" /></a></logic:equal>
									 	 <logic:notEqual name="app" property="isActive" value="true"><bean:write name="app" property="appTitle" /></logic:notEqual>&nbsp;</td>
								                      <td class="cell" align="left"><bean:write name="app" property="developerName" />&nbsp;</td>
								                      <td class="cell" align="left"><bean:write name="app" property="approvalDate" formatKey="date.format" filter="true"/>&nbsp;</td>
								                      <td class="cell" align="left"><bean:write name="app" property="expiryDate" formatKey="date.format" filter="true"/>&nbsp;</td>
								                      <td class="cell" align="left"><bean:write name="app" property="expiryStatus"/>&nbsp;</td>
								                    </tr>
               										</logic:iterate>
               										<logic:empty name="ApplicationList">
               									  <tr>
																<td class="firstcell" align="center" colspan="6">No Application found.</td>
               									  </tr>
               										</logic:empty>
                   								</table>
			      			                </td>
			      		                </tr>
			                        </table>
      	            			</td>
                			</tr>
				 <logic:notEmpty name="ApplicationList">                			
    			<tr>
    			   <td colspan="2" align="right">
    			      <table border="0" cellspacing="0" cellpadding="0">
	               <tr><td><logic:greaterThan	name="NewMktProvAppsForm" property="pageNo" value="1" >
						<a href='javascript:submitReq(1);//'><bean:message	key="images.previous.icon" /></logic:greaterThan></a></td> 
						<td>Page <bean:write name="app" name="NewMktProvAppsForm" property="pageNo" /> of <%=TotalPages.toString()%></td>
	               <td><logic:lessThan	name="NewMktProvAppsForm" property="pageNo" value="<%=TotalPages.toString()%>"	>
						<a href='javascript:submitReq(2);//'><bean:message	key="images.next.icon" /></a></logic:lessThan></td></tr>
						</table>
    			   </td>
    			</tr>
            </logic:notEmpty>
            			</table>
	    			</td>
    			</tr>
    			
    			<tr>
    			   <td width="100%" align="right"><img src="images/spacer.gif" width="5" height="2"></td>
    			</tr>
    			<tr>
    			   <td width="100%" align="right"><a href='javascript:submitReq2(4);//'><img src="images/create_b.gif" width="52" height="15" border="0"></a></td>
    			</tr>
			</table>
			 
		</td>
	</tr>
</table>
</html:form>