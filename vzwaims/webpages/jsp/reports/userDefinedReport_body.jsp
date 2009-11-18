<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>



<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width=20>&nbsp;</TD>
          <TD width="100%"><SPAN class=aimsmasterheading><bean:message key="UserDefinedReportsForm.Welcome" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></SPAN> </TD></TR>
        <TR>
          <TD width=20>&nbsp;</TD>
          <TD width="100%">&nbsp; </TD></TR>
		   <%@ include  file="/common/error.jsp" %>
		  
	<tr>
		<td	align="center" height="20" colspan="3" >
			<html:messages id="message"	message="true" header="messages.header"	footer="messages.footer">
				<span class="errorText"><bean:write name="message"/></span><br/>
			</html:messages>
		</td>
	</tr>

        <TR>
          <TD width=20>&nbsp;</TD>
          <TD vAlign=center align=middle width="100%" bgColor=#ffffff>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD vAlign='top"' width="100%">
                  <TABLE class=sectable height="100%" width="100%" cellpadding="0" cellspacing="0">
                    <TBODY>
                    <TR class=sectitle>
                      <TD class=aimssecheading><bean:message key="UserDefinedReportsForm.ReportCriteria" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></TD>
                    </TR>
                    <TR>
                      <TD vAlign=center align=middle bgColor=#ebebeb>
                      	<table width="100%">
                      		<tr>
                           		<td width="30%" class="modFormFieldLbl">
									<bean:message key="UserDefinedReportsForm.ReportObject" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/>:
                      			</td>
                      			<td width="30%" class="modFormFieldLbl">
									<bean:message key="UserDefinedReportsForm.ReportColumn" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/>:
                      			</td>
                      			<td width="40%" class="modFormFieldLbl">
									<bean:message key="UserDefinedReportsForm.ReportCriteria" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/>:
                      			</td>
                       		</tr>
                      		<tr>
                      			<td class="text" valign=top>
								   <table>
									<logic:present  name="UserDefinedReport"  property="objectsAdded"> 
									     <logic:iterate id="object"  name="UserDefinedReport" property="objectsAdded">									
										    <tr><td>
											    <bean:write name="object" property="tableDisplayName"/>
                    						</td></tr>
					                     </logic:iterate> 
									</logic:present>	 
								   </table>
                      			</td>
                      			<td class="text" valign=top>
									<table>
										 <logic:present  name="UserDefinedReport"  property="columnsAdded"> 
	    							     <logic:iterate id="column"  name="UserDefinedReport" property="columnsAdded">									
										    <tr><td>
	    										<bean:write name="column" property="columnDisplayName"/>
                    						</td></tr>
					                     </logic:iterate> 
									 </logic:present>	 
                      				</table>
                      			</td>
                      			<td class="text" valign=top>
									<table>
									  <logic:present  name="UserDefinedReport"  property="criteriaAdded"> 
	    							     <logic:iterate id="criteria"  name="UserDefinedReport" property="criteriaAdded">
										<tr>
											<td><bean:write name="criteria" property="columnDisplayName"/> <bean:write name="criteria" property="columnOperator"/> <bean:write name="criteria" property="criteriaValue"/></td>
										</tr>
										<tr>
											<td><bean:write name="criteria" property="columnCondition"/></td>
										</tr>
										 </logic:iterate> 
									 </logic:present>	 
									</table>
                      			</td>
                      		</tr>
                      	</table>
					  </TD>
					</TR>
					</TBODY>
				  </TABLE>
				</TD>
			  </TR>
              <TR>
                <TD vAlign='top"' width="100%">
                  <TABLE  cellspacing="0" cellpadding="0"  class=sectable height="100%" width="100%">
                    <TBODY>
                    <TR class=sectitle>
                      <TD class=aimssecheading><bean:message key="UserDefinedReportsForm.ReportResult" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></TD>
                    </TR>
                    <TR>
                      <TD vAlign=center align=middle bgColor=#ebebeb>
                      	<table width="100%" cellpadding="0" cellspacing="0">
                      		<tr>
                      			<td>
                      				<table class=tabletop width=100% cellpadding="0" cellspacing="0">
                    					<tr bgcolor=bbbbbb>
										    <logic:present  name="UserDefinedReport"  property="columnsAdded"> 
											  <% int columnCounter = 0; %>
        	    							     <logic:iterate id="column"  name="UserDefinedReport" property="columnsAdded">
												   <% if ( columnCounter++ < 1 ) { %><td class=firstcell><% } else { %><td class=cell><% } %> 
	     											   &nbsp; <span  class="modFormFieldLbl"><bean:write name="column" property="columnDisplayName"/></span></td>
	        									 </logic:iterate> 
        									</logic:present>	 
                     									   
										</tr>
										  
										   <logic:present  name="UserDefinedReport"  property="userDefObjectList"> 
		   									   <logic:iterate id="reprotObject"  name="UserDefinedReport" property="userDefObjectList">
											   <% int valueCounter = 0; %>
												<tr>
    											     <logic:iterate id="columnValue"  name="reprotObject" property="columnValues">
													   <% if ( valueCounter++ < 1 ) { %><td class=firstcell><% } else { %><td class=cell><% } %> 
													     &nbsp; <bean:write name="columnValue" property="recordValue"/>  </td>
													  </logic:iterate>
												 </tr>
                    					        </logic:iterate> 
                						   </logic:present>
										
                   
                      				</table>
                      			</td>
                      		</tr>
                      		<tr>
							    
                      			<td align=right class="modFormFieldLbl">
								    </br>
									<a href="userDefinedReportSetup.do"><img src="images/back_b.gif" border=0></a>&nbsp;
									Export to Excel
                      			</td>
                      		</tr>
                      	</table>
					  </TD>
					</TR>
					</TBODY>
				  </TABLE>
				</TD>
			  </TR>
			  </TBODY>
			</TABLE>
		  </TD>
		</TR>
		</TBODY>
	  </TABLE>
  
