<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<table width="100%"	border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td	colspan="3" width="100%"> <span class=aimsmasterheading><bean:message key="ReconciliationCombine.pageTitle"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span> 
		 </td>
	</tr>
   <tr>
     <td colspan="3">&nbsp;</td>
   </tr>
	
	<%@	include	 file="/common/error.jsp"	%>
	
	<tr> 
		<td	colspan="3"> 
			<html:form action="reconcileCombineUpload.do"	enctype="multipart/form-data"> 
			<table width="100%"	border="0" cellspacing="0" cellpadding="0">
				<tr> 
					<td height="1">	 </td>
				</tr>
				<tr>
				 <td>
                  <TABLE class=sectable height="100%" width="100%">
                    <TBODY>
                    <TR class=sectitle>
                      <TD class=aimssecheading><bean:message key="ReconciliationCatalog.dataFileInfo"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></TD>
                    </TR>
                    <TR>
                      <TD vAlign=center align=middle bgColor=#ebebeb>
                      	<table width="100%">
                      		<tr>
                      			<td class="modFormFieldLbl" width="30%">
											<bean:message key="ReconciliationCatalog.dataFile"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                      			</td>
                      			<td class="text" width="70%">
									      <html:file property="brewFile" />
                      			</td>
                      		</tr>
                      		<tr>
                      			<td class="modFormFieldLbl">
											<bean:message key="ReconciliationCatalog.comments"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br>
											<bean:message key="ReconciliationCombine.commentsAddOn"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                      			</td>
                      			<td class="text">
									      <html:textarea property="comments" rows="5" cols="20"/>
                      			</td>
                      		</tr>
                      	</table>
					  </TD>
					</TR>
					</TBODY>
				  </TABLE>
					</td>
				 </tr>
				   <td align=right><input type=image src="images/submit_b.gif" border=0 ></td>
				 </tr>
				</table>
				</html:form> 
			</td>
		</tr> 
</table>