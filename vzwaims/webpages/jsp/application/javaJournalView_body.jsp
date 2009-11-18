<%@ page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<jsp:useBean id="javaApplicationUpdateForm" class="com.netpace.aims.controller.application.JavaApplicationUpdateForm" scope="request" />

<%javaApplicationUpdateForm.setCurrentPage("page4");%>
<%javaApplicationUpdateForm.setJournalType("PR");%>
<%javaApplicationUpdateForm.setJournalText("");%>

<%@ include file="include/javaAppVariables.jsp"%>

<script language="javascript">
	<%@ include  file="include/appJScript.jsp" %>
    <%@ include  file="include/javaJScript.jsp" %>
</script>


<%@ include file="/common/error.jsp"%>

<div id="contentBox" style="float: left">
	<DIV class="homeColumnTab ">
		<%@ include file="include/javaAppViewTabs.jsp"%>
		<html:form action="/javaApplicationUpdate" enctype="multipart/form-data">
			<div class="contentbox">
				<%@ include file="include/javaAppHidden.jsp"%>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">


		<logic:notEmpty name="javaApplicationUpdateForm" property="history"> 
          <tr>
		    <td>
		        <div class="lBox">
		            <DIV class="headLeftCurveblk"></DIV>
		            <H1>History</H1>
		            <DIV class="headRightCurveblk"></DIV>
		        </div>
		    </td>
		  </tr>
          <tr>
            <td>
            	<table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
					<tr>
						<th>Work Item</th>
						<th>Start Date</th>
						<th>Completed Date</th>
						<th>Action</th>
					</tr>						
					<logic:iterate id="item" name="javaApplicationUpdateForm" property="history">
						<tr>
							<td align="left" style="border-top: 0px" width="35%"><bean:write name="item" property="stepName"/></td>
							<td align="left" style="border-top: 0px" width="15%"><bean:write name="item" property="entryDate" formatKey="date.format"/></td>
							<td align="left" style="border-top: 0px" width="15%"><bean:write name="item" property="exitDate" formatKey="date.format"/></td>

							<td align="left" style="border-top: 0px">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 0px solid black;">
									<tr>
										<td width="80%" align="left" style="vertical-align: middle;">
											<bean:write name="item" property="actionTaken"/>
										</td>
										<td width="20%" align="left" style="vertical-align: middle;">
											<span id="spnExpandCollapse<bean:write name="item" property="workitemId"/>" style="cursor:pointer;">
		                            			<a class="a" onclick="javascript:expandRow(<bean:write name="item" property="workitemId"/>)">[+]</a>
		                        			</span>
										</td>
									</tr>
								</table>												
							</td>
						</tr>
						<tr id="row<bean:write name="item" property="workitemId"/>" style="display:none;">
		                    <td colspan="6" >
		                    	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 0px solid black;">
		                    		<tr>
		                    			<td style="vertical-align:middle; padding-left:0px;" width="10%">
											<strong>Comments:&nbsp;</strong>
										</td>
										<td>
											<textarea rows="4" cols="70" class="textareaField" readonly="readonly"><bean:write name="item" property="comments"/></textarea>
										</td>
									</tr>
								</table>
							</td>
						</tr>						
					</logic:iterate>
				</table>	
			</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
         </logic:notEmpty>







					<tr>
						<td>
							<div class="lBox">
								<DIV class="headLeftCurveblk"></DIV>
								<H1><bean:message key="JournalForm.currentEntries" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" /></H1>
								<DIV class="headRightCurveblk"></DIV>
							</div>
						</td>
					</tr>
					
					
					
					
					







				
				
				
					
					
					
		
					
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
								<tr>
									<th>
										<html:textarea styleClass="textareaField" property="journalCombinedText" readonly="true" rows="20" cols="90"></html:textarea>
									</th>
								</tr>

			<%-----  Entry text field and radion   ------%>
			<%------	
								<% if (isVerizonUser) {	%>
								<tr>
									<td>
										<strong><bean:message key="JournalForm.type" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
										<br />
										<html:radio property="journalType" value="PU" />
										<bean:message key="ManageApplicationForm.radio.label.public" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
										&nbsp;
										<html:radio property="journalType" value="PR" />
										<bean:message key="ManageApplicationForm.radio.label.private" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />
									</td>
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr>
									<td>
										<strong><bean:message key="JournalForm.newEntry" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE" />:</strong>
										<br />
										<html:textarea styleClass="textareaField" property="journalText" rows="4" cols="90"></html:textarea>
									</td>
								</tr>
								<% } %>
			-----%>								
			<%-----  Entry text field and radion   ------%>


							</table>
						</td>
					</tr>
					
				<%-----  Entry text field and radion   ------%>				
					
					<tr>
						<td width="100%">
							<table width="100%" cellpadding="0" cellspacing="0" border="0">
								<%@ include file="include/appJournalButtons.jsp"%>
								<tr><td><%@ include file="include/javaMessageFooter.jsp" %></td> </tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</html:form>
	</div>
</div>
