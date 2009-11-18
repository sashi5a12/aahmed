<%@	page language="java" %>

<%@ page import="com.netpace.aims.controller.contracts.* "%>

<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@	taglib uri="/WEB-INF/struts-template.tld" prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>
 
<html:form action="/contractsEdit.do" enctype="multipart/form-data">

<bean:parameter id="alliance_id" name="alliance_id" value="0" />
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
       		<span class="aimsmasterheading">
       			System Management - 
       		</span>               
       	</td>
 	</tr>
	<tr>
		<td	width="20">&nbsp;</td>
		<td	width="100%">
			<aims:getVZWAllianceTab attributeName="Contracts" allianceId="<%=alliance_id.toString()%>"/>            
        </td>
    </tr>
    <tr> 
       <td width="20">&nbsp;</td>
       <td width="100%">
            &nbsp;
       </td>
    </tr>
  	<%@ include  file="/common/error.jsp" %>
  	<tr> 
    	<td width="20">&nbsp;</td>
      	<td  width="100%" align="center" valign="middle" bgcolor="#FFFFFF"> 
			
							
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                    <td width="100%">
					    <table class="sectable" width="100%">
                            <tr class="sectitle">
                                <td colspan="2" class="aimssecheading">Contract Details</td>
                            </tr>
                            <tr>
                                <td class="modFormFieldLbl" width="50%">Contract Name:</td>
                                <td class="modFormFieldLbl" width="50%">Contract Version:</td>
                            </tr>
                            <tr>
                                <td class="text" valign="top">
                                     <bean:write name="ContractForm" property="contractTitle"/> 
                                </td>
                                <td class="text">
                                    <bean:write name="ContractForm" property="contractVersion"/> 
                                </td>
                            </tr>
                            <tr>
                                <td class="modFormFieldLbl">Contract Status:</td>
                                <td class="modFormFieldLbl">Contract Document:</td>
                            </tr>
                            <tr>
                                <td class="text" valign="top">
                                    <bean:write	name="ContractForm" property="contractStatus"/>
                                </td>							
                                <td>                                   
                                    <a class="standardLink" target="_blank" href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="ContractForm" property="contractId"/>'>
                                        <bean:write	name="ContractForm" property="contractDocumentFileName"/>
                                    </a>
                                </td>
                            </tr>
                            <tr>
                                <td class="modFormFieldLbl">Contract Expiry Date:</td>
                                <td class="modFormFieldLbl">Platform</td>
                               
                            </tr>
                            <tr>
                                <td class="text" valign="top">                                    
                                    <bean:write name="ContractForm" property="contractExpiryDate" formatKey="date.format" filter="true" />
                                </td>
                                <td valign="top" class="text" valign="top"> 
                                    <bean:write name="ContractForm" property="platformName" />
                                </td>
                            </tr>
                            <tr>
                                <td class="modFormFieldLbl">Comments</td>
                                <td class="modFormFieldLbl">&nbsp;</td>
                            </tr>
                            <tr>
                                <td valign="top" class="text">
                                    <bean:write	name="ContractForm" property="comments"/> 
                                </td>
                                <td valign="top" class="text" valign="top"> 
                                   &nbsp;
                                </td>							                     
                            </tr>
					    </table>	
				    </td>
                </tr>
				<tr><td width="100%">&nbsp;</td></tr>
				<tr><td width="100%">
					<table class="sectable" width="100%">
                        <logic:iterate id="amendment" name="ContractForm" property="contractAmendments" type="com.netpace.aims.controller.contracts.AmendmentForm"  indexId="cntContractAmendment">
                        <logic:equal name="cntContractAmendment" value="0">
                            <tr class="sectitle"><td  width="100%" colspan="4" class="aimssecheading">Amendments for this Contract</td>
                            </tr>
                            <tr >
                                <td class="modFormFieldLbl">Amendment Name:</td>
                                <td class="modFormFieldLbl">Amendment Version:</td>
                                <td class="modFormFieldLbl">Amendment Document:</td>
                                <td class="modFormFieldLbl">Amendment Expiry Date:</td>                         
                            </tr>
                        </logic:equal>
						<tr>
							<td class="text">
                                <bean:write name="amendment" property="amendmentTitle" />
                            </td>
							<td class="text">
                                <bean:write name="amendment" property="amendmentVersion" />
                            </td>
							<td class="text">
                                <a class="standardLink" target="_blank" href='/aims/amendmentsResourceAction.do?resource=document&object=Amendtment&resourceId=<bean:write name="amendment" property="amendmentId"/>'>
                                    <bean:write name="amendment" property="amendmentDocumentFileName" />
                                </a>
                            </td>
							<td class="text">
                                <bean:write name="amendment" property="amendmentExpiryDate" formatKey="date.format" filter="true" />
                            </td>							
						</tr>
                        </logic:iterate>
					</table>	
				</td></tr>
				<tr><td width="100%">&nbsp;</td></tr>
				<tr><td width="100%">
					<table class="sectable" width="100%">
                        <logic:iterate id="amendment" name="ContractForm" property="contractAllianceAmendments" type="com.netpace.aims.controller.contracts.AmendmentForm" indexId="cntAllianceAmendment" >
                        <logic:equal name="cntAllianceAmendment" value="0">
                            <tr class="sectitle"><td  width="100%" colspan="4" class="aimssecheading">Amendments for this Alliance only</td>
                            </tr>
                            <tr >
                                <td class="modFormFieldLbl">Amendment Name:</td>
                                <td class="modFormFieldLbl">Amendment Version:</td>
                                <td class="modFormFieldLbl">Amendment Document:</td>
                                <td class="modFormFieldLbl">Amendment Expiry Date:</td>                         
                            </tr>
                        </logic:equal>
						<tr>
							<td class="text">
                                
                                <bean:write name="amendment" property="amendmentTitle" />
                            </td>
							<td class="text">
                                <bean:write name="amendment" property="amendmentVersion" />
                            </td>
							<td class="text">
                                <a class="standardLink" target="_blank" href='/aims/amendmentsResourceAction.do?resource=document&object=Amendtment&resourceId=<bean:write name="amendment" property="amendmentId"/>'>
                                    <bean:write name="amendment" property="amendmentDocumentFileName" />
                                </a>
                            </td>
							<td class="text">
                                <bean:write name="amendment" property="amendmentExpiryDate" formatKey="date.format" filter="true" />
                            </td>							
						</tr>
                        </logic:iterate>
					</table>	
				</td></tr>
			</table>
		</td>
	</tr>
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
       		&nbsp;         
       	</td>
 	</tr> 
</table>
</html:form>