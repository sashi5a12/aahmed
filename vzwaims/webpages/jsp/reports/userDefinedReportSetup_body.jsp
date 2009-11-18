<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<script language="JavaScript" type="text/javascript">
  
 
  function addObject() 
  {
    document.forms[0].objectValue.value = document.forms[0].reportObject.value;
	document.forms[0].action = 'userDefinedReportSetup.do?item=reportObject&cmd=add';
	document.forms[0].submit();
  }  
  
  function removeObject() 
  {
   document.forms[0].objectValue.value = document.forms[0].reportObject.value;
   document.forms[0].action = 'userDefinedReportSetup.do?item=reportObject&cmd=remove';
   document.forms[0].submit();
  }  

  function addColumn() 
  {
    document.forms[0].objectValue.value = document.forms[0].reportColumn.value;
	document.forms[0].action = 'userDefinedReportSetup.do?item=reportColumn&cmd=add';
	document.forms[0].submit();
  }  
  
  function removeColumn() 
  {
   document.forms[0].objectValue.value = document.forms[0].reportColumn.value;
   document.forms[0].action = 'userDefinedReportSetup.do?item=reportColumn&cmd=remove';
   document.forms[0].submit();
  }  
  
   function addCriteria() 
  {
    document.forms[0].objectValue.value = document.forms[0].reportCriteria.value;
	document.forms[0].action = 'userDefinedReportSetup.do?item=reportCriteria&cmd=add';
	document.forms[0].submit();
  }  
  
  function removeCriteria(id) 
  {
   document.forms[0].objectValue.value = document.forms[0].reportCriteria.value;
   document.forms[0].action = 'userDefinedReportSetup.do?item=reportCriteria&cmd=remove&criteriaId=' + id;
   document.forms[0].submit();
  }  


  function submitForm() 
  {
   document.forms[0].action = 'userDefinedReport.do?task=generateReport';
   document.forms[0].submit();
  }  

</script>

 
 <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
        <TBODY>
        <TR>
          <TD width=20>&nbsp;</TD>
          <TD width="100%"><SPAN class=aimsmasterheading><bean:message key="UserDefinedReportsForm.Welcome" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></SPAN> </TD></TR>
	    <TR>
          <TD width=20>&nbsp;</TD>
          <TD width="100%">&nbsp; </TD></TR>
		   <%@ include  file="/common/error.jsp" %>
        <TR>
          <TD width=20>&nbsp;</TD>
          <TD vAlign=center align=middle width="100%" bgColor=#ffffff>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
              <TBODY>
              <TR>
                <TD vAlign='top"' width="100%">
                  <TABLE class=sectable height="100%" width="100%">
                    <TBODY>
                    <TR class=sectitle>
                      <TD class=aimssecheading><bean:message key="UserDefinedReportsForm.ReportCriteria" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></TD>
                    </TR>
                    <TR>
                      <TD vAlign=center align=middle bgColor=#ebebeb>
					   <html:form action="/userDefinedReportSetup" >
					     <html:hidden property="objectValue"/>
					     <table width="100%">
                      		<tr>
                      			<td width="25%" class="modFormFieldLbl">
									<bean:message key="UserDefinedReportsForm.ReportObject" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/>:
                      			</td>
                      			<td width="25%" class="modFormFieldLbl">
									<bean:message key="UserDefinedReportsForm.ReportColumn" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/>:
                      			</td>
                      			<td width="50%" class="modFormFieldLbl">
									<bean:message key="UserDefinedReportsForm.ReportCriteria" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/>:
                      			</td>
                      		</tr>
                      		<tr>
                      			<td class="text" valign=top>
						    	 <html:select property="reportObject" name="UserDefinedReport" > 
        	                        <html:optionsCollection property="reportObjectList" name="UserDefinedReport" label="tableDisplayName"  value="reportObjectId" />
                                </html:select>
							
						
                      				<br/>
			                      		<a href="#" onClick="return addObject()">Add</a>
			                      	<br/>
									
									<table>
									  <logic:present  name="UserDefinedReport"  property="objectsAdded"> 
									     <logic:iterate id="object"  name="UserDefinedReport" property="objectsAdded">									
										    <tr><td>
													  <html:multibox property="objectValues">
    										                <bean:write name="object" property="reportObjectID"/>
													  </html:multibox>	
													  <bean:write name="object" property="tableDisplayName"/>
                    						</td></tr>
					                     </logic:iterate> 
									  </logic:present>	 
                      				</table>
									
									 <logic:notEmpty  name="UserDefinedReport"  property="objectsAdded"><br/>
                      				     <a href="#" onClick="return removeObject()">Remove</a>
									 </logic:notEmpty>	 
                      			</td>
                    			<td class="text" valign=top>
								  <logic:present  name="UserDefinedReport"  property="reportColumnList"> 
								   <html:select property="reportColumn" name="UserDefinedReport" > 
        	                           <html:optionsCollection property="reportColumnList" name="UserDefinedReport" label="columnDisplayName"  value="reportObjectColumnId" />
                                   </html:select>
								  
						       		  
                      				<br/>
                      				<a href="#" onClick="return addColumn()">Add</a>
			                      	<br/>
									 </logic:present>
									<table>
									 <logic:present  name="UserDefinedReport"  property="columnsAdded"> 
	    							     <logic:iterate id="column"  name="UserDefinedReport" property="columnsAdded">									
										    <tr><td>
	    										  <html:multibox property="columnValues">
        								                <bean:write name="column" property="reportObjectColumnId"/>
		    									  </html:multibox>	
			    								  <bean:write name="column" property="columnDisplayName"/>
                    						</td></tr>
					                     </logic:iterate> 
									 </logic:present>	 
                      				</table>
									
                      				  <logic:notEmpty  name="UserDefinedReport"  property="columnsAdded"><br/> 
                          				<a href="#" onClick="return removeColumn()">Remove</a>
								     </logic:notEmpty>		
                      			</td>
								
                      			<td class="text" valign=top>
								  <logic:present  name="UserDefinedReport"  property="reportColumnList"> 
                   				   <html:select property="reportCriteria" name="UserDefinedReport" > 
        	                           <html:optionsCollection property="reportColumnList" name="UserDefinedReport" label="columnDisplayName"  value="reportObjectColumnId"  />
                                   </html:select>
								  

			                      	<br/>
                      				<a href="#" onClick="return addCriteria()">Add</a>
									<br/>
									</logic:present>
									<table>
									   <logic:present name="UserDefinedReport"  property="criteriaAdded"> 
									   <% int count= 0 ; 
	    							      String selected = "";
    								      String[] operators = (String[])session.getAttribute("operators");
									   %>
									   
	    							     <logic:iterate id="criteria"  name="UserDefinedReport" property="criteriaAdded">									
										 
										 <% count++; %>  
										<tr>
											<td  valign="middle"><bean:write name="criteria" property="columnDisplayName"/> </td>
											<td>
											   <% 
											   if(operators!= null && operators.length > (count - 1 )) { 
											        selected =  operators[count-1] ;
											   }		
											    else {
												    selected = "";		
												}	
										       %>
											
											  <html:select property="columnOperator" name="UserDefinedReport" value='<%=selected%>'> 
        	                                        <html:optionsCollection property="columnOperatorList" name="UserDefinedReport" label="columnOperatorName"  value="columnOperator" />
                                             </html:select>
			               					
			                      			</td>
			                      			<td>
    											 <html:text name="criteria" property="criteriaValue" /> 
											</td>
										</tr>
										<tr>
			                      			<td colspan=2>
											     <bean:parameter id="counter" name="counter" value="<%= " " + count %>"/> 
												 <logic:lessThan name="counter" value='<%= (String)session.getAttribute("CriteriaSize") %>' > 
												    <input type="radio" name='columnCondition<%= "" + count %>'  value='<bean:message key="UserDefinedReportsForm.radio.label.and" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/>' checked/><bean:message key="UserDefinedReportsForm.radio.label.and" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/>&nbsp;
												    <input type="radio" name='columnCondition<%= "" + count %>' value='<bean:message key="UserDefinedReportsForm.radio.label.or" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/>'/><bean:message key="UserDefinedReportsForm.radio.label.or" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/>&nbsp;
									          	 </logic:lessThan>
											   &nbsp;	
											</td>
			                      			<td>
												<a href="#" onClick='return removeCriteria(<bean:write name="criteria" property="criteriaId"/>)'>Remove</a>
											</td>
										</tr>
									    </logic:iterate> 
									  </logic:present>
									</table>
                      			</td>
                      		</tr>
                      		<tr>
                      			<td colspan=3 align=right class="modFormFieldLbl">
									<a href="#" onClick="return submitForm();" ><img src="images/submit_b.gif" border=0></a>
                      			</td>
                      		</tr>
                      	</table>
						</html:form>
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
<script	language="javascript">
	//changeReportObjectColumns();
</script>