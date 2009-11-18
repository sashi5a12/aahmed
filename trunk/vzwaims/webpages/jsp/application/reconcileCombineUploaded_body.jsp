<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<table width="100%"	border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td	colspan="3" width="100%"> <span class=aimsmasterheading><bean:message key="ReconciliationCombine.uploaded"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span> 
		 </td>
	</tr>
   <tr>
     <td colspan="3">&nbsp;</td>
   </tr>
	
	<%@	include	 file="/common/error.jsp"	%>
	
	<tr> 
		<td	colspan="3"> 
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
                      	   <logic:present name="BrewUpload">
                      		<tr>
                      			<td colspan="2">
											<bean:message key="ReconciliationCombine.brewDataUploaded"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br>
											<a href="reconcileBrewFileRecord.do?brewNstlUploadId=<bean:write name="BrewUpload" property="brewNstlId"/>"><bean:message key="ReconciliationCombine.clickHereToReconcileBrew"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>
                      			</td>
                      		</tr>
                      		<tr>
                      			<td colspan="2">&nbsp;
                      			</td>
                      		</tr>
                      		</logic:present>
                      		<logic:present name="Catalog">
                      		<tr>
                      			<td colspan="2">
											<bean:message key="ReconciliationCombine.catalogDataUploaded"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br>
											<a href="reconcileCatalogFile.do?brewNstlUploadId=<bean:write name="Catalog" property="catalogId"/>"><bean:message key="ReconciliationCombine.clickHereToReconcileCatalog"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>
                      			</td>
                      		</tr>
                      		<tr>
                      			<td colspan="2">&nbsp;
                      			</td>
                      		</tr>
                      		</logic:present>
                      		<tr>
                      			<td colspan="2">
                      			<a href="reconcileCombineSetup.do"><bean:message key="ReconciliationCombine.clickHereToUploadOther"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></a>
                      			</td>
                      		</tr>
                      		<tr>
                      			<td colspan="2">&nbsp;
                      			</td>
                      		</tr>
                      	</table>
					  </TD>
					</TR>
					</TBODY>
				  </TABLE>
					</td>
				</table>
			</td>
		</tr> 
</table>