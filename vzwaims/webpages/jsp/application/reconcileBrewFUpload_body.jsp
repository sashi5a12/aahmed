<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<table width="100%"	border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td	width="30">&nbsp;</td>
		<td	width="496"> <span class=aimsmasterheading><bean:message key="ReconciliationBrew.pageTitle"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span> 
		 </td>
		<td	width="247">&nbsp;</td>
	</tr>
   <tr>
     <td colspan="3">&nbsp;</td>
   </tr>
	
	<%@	include	 file="/common/error.jsp"	%>
	
	<tr> 
		<td	width="30">	&nbsp;</td>

		<td	colspan="2"> 
			<table width="100%"	border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td	bgcolor="#999999"	height="1">	 </td>
				</tr>
				<tr>
				 <td>
                  <TABLE class=sectable height="100%" width="100%">
						<html:form action="/reconcileBrewFileUpload.do"	enctype="multipart/form-data"> 
                    <TBODY>
                    <TR class=sectitle>
                      <TD class=aimssecheading><bean:message key="ReconciliationBrew.dataFileInfo"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></TD>
                    </TR>
                    <TR>
                      <TD vAlign=center align=middle bgColor=#ebebeb>
                      	<table width="100%">
                      		<tr>
                      			<td class="modFormFieldLbl">
											<bean:message key="ReconciliationBrew.dataFile"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                      			</td>
                      			<td class="text">
									      <html:file property="brewFile" />
                      			</td>
                      		</tr>
                      		<tr>
                      			<td colspan=2 align=right class="modFormFieldLbl">
									     <input type=image src="images/submit_b.gif" border=0 >
                      			</td>
                      		</tr>
                      	</table>
					  </TD>
					</TR>
					</TBODY>
					</html:form> 
				  </TABLE>
					</td>
				 </tr>
				</table>
			</td>
		</tr> 
</table>