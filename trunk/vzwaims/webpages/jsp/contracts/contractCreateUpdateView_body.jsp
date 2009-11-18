<%@ page language="java" %>
<%@ page import="com.netpace.aims.util.AimsConstants" %>
<%@ page import="com.netpace.aims.model.core.AimsUser"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<jsp:useBean id="ContractForm" class="com.netpace.aims.controller.contracts.ContractForm" scope="request" />

<%
    boolean isVerizonUser = ((AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE);
%>

<script language="javascript">
    function previewContract(contractId){
        var url="<%=request.getContextPath()%>/showContractHTMLAction.do?contractId="+contractId;
        var wind;
        wind = window.open ("","previewContract","resizable=1,scrollbars=1,width=950,height=600,screenX=50,left=50,screenY=30,top=30");
        wind.location.href=url;
        wind.focus();
    }
</script>

<div id="contentBox">
<%@ include file="/common/error.jsp" %>
<DIV class="homeColumnTab lBox">

<DIV class="headLeftCurveblk"></DIV>
<H1>Contract Details</H1>

<DIV class="headRightCurveblk"></DIV>
<!-- FORM START HERE -->
<html:form action="/contractsEdit" enctype="multipart/form-data">
<DIV class="contentbox">

    <table width="100%" border="0" cellspacing="0" cellpadding="5" class="GridGradient">
        <tr>
            <th width="42%"><strong>Contract Name :</strong><br>
                <bean:write name="ContractForm" property="contractTitle"/>
            </th>
            <th width="58%"><strong>Contract Version :</strong><br>
                <bean:write name="ContractForm" property="contractVersion"/>
            </th>
        </tr>
        <tr>
            <td><strong>Contract Status :</strong> <br>
                <bean:write name="ContractForm" property="contractStatus"/>
            </td>
            <td><strong>Contract Document :</strong><br/>
                <a class="a" target="_blank"
                   href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="ContractForm" property="contractId"/>'>
                    <bean:write name="ContractForm" property="contractDocumentFileName"/>
                </a></td>
        </tr>
        <tr>
            <td><strong>Platform :</strong> <br>
                <bean:write name="ContractForm" property="platformName"/>
            </td>
            <td>
                <table width="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td style="vertical-align:top;padding-left:0px;">
                            <strong>Contract Document (HTML):</strong>
                        </td>
                    </tr>
                    <tr>
                        <td style="padding-left:0px;">
                            <a class="a" target="_blank"
                               href='/aims/resourceContractAction.do?resource=htmlFile&object=AimsAllianc&resourceId=<bean:write name="ContractForm" property="contractId"/>'>
                                <bean:write name="ContractForm" property="contractHtmlDocumentFileName"/>
                            </a>
                        </td>
                    </tr>
                    <%-- if contract is created and contract html document is present, show preview html link, only vzw user can view preview link --%>
                    <% if (isVerizonUser)  { %>
                        <logic:notEmpty name="ContractForm" property="contractHtmlDocumentFileName">
                            <tr>
                                <td style="padding-left:0px;">
                                    <div style="margin-top:5px;">
                                        <a class="a" href="javascript:void(0);"
                                            onclick="javascript:previewContract(<bean:write name="ContractForm" property="contractId"/>);">(Preview HTML)</a>
                                    </div>
                                </td>
                            </tr>
                        </logic:notEmpty>
                    <%  }   %>
                </table>
            </td>
        </tr>

        <tr>
            <td>
                <%
                    if(isVerizonUser) {
                %>
                    <strong>Auto-Offered Click Through Contract:</strong>
                <%
                    }
                    else {
                %>
                    <strong>Click Through Contract:</strong>
                <%  }   %>
                <br>
                <logic:notEmpty name="ContractForm" property="clickThroughContract">
                    <logic:equal name="ContractForm" property="clickThroughContract" value="Y">
                        Yes
                    </logic:equal>
                    <logic:equal name="ContractForm" property="clickThroughContract" value="N">
                        No
                    </logic:equal>
                </logic:notEmpty>
            </td>
            <td style="vertical-align:top;"><strong>Contract Expiry Date :</strong> <br>
                <bean:write name="ContractForm" property="contractExpiryDate" formatKey="date.format"
                            filter="true"/>
            </td>

        </tr>

        <tr>
            <td style="vertical-align:top;"><strong>Comments :</strong> <br>
                <html:textarea styleClass="textareaField" property="comments" rows="5"   cols="43" readonly="true"></html:textarea>
            </td>
            <td>
				&nbsp;
            </td>
        </tr>
        <tr>
            <td>
				<logic:equal name="ContractForm" property="platformId" value="5">
				
					<logic:present name="ContractForm" property="isBoboContract" >
					
						<logic:equal name="ContractForm" property="isBoboContract" value="Y">
							 <html:checkbox value="Y" property="isBoboContract" disabled="true" onclick=""/>
	            		 		Is BOBO Contract
						</logic:equal>
						<logic:equal name="ContractForm" property="isBoboContract" value="N">
							 <html:checkbox value="Y" property="isBoboContract" disabled="true" onclick=""/>
	            		 		Is BOBO Contract
						</logic:equal>
					</logic:present>
				</logic:equal>
			</td>
            <td>&nbsp;</td>
        </tr>
    </table>
</DIV>

<div>&nbsp;</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Amendment Information</H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
        <tr>
            <th><strong>Amendment Name </strong></th>
            <th><strong>Amendment Version </strong></th>
            <th><strong>Amendment Document </strong></th>
            <th><strong>Amendment Expirty Date </strong></th>
        </tr>
        <logic:iterate id="amendment" name="ContractForm" property="contractAmendments"
                       type="com.netpace.aims.controller.contracts.AmendmentForm">
            <tr>
                <td>
                    <bean:write name="amendment" property="amendmentTitle"/>
                </td>
                <td align="left">
                    <bean:write name="amendment" property="amendmentVersion"/>
                </td>
                <td align="left"><a class="a" target="_blank"
                                    href='/aims/amendmentsResourceAction.do?resource=document&object=Amendtment&resourceId=<bean:write name="amendment" property="amendmentId"/>'>
                    <bean:write name="amendment" property="amendmentDocumentFileName"/>
                </a></td>
                <td align="left">
                    <bean:write name="amendment" property="amendmentExpiryDate" formatKey="date.format"
                                filter="true"/>
                </td>
            </tr>
        </logic:iterate>
    </table>
</DIV>

<%if (!(((com.netpace.aims.model.core.AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE))) {%>
<div>&nbsp;</div>
<div>&nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Alliance(s) associated with the contract</H1>

<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" cellspacing="0" cellpadding="5" class="Grid2" border="1">
        <tr>
            <th><strong>Alliance Name</strong></th>
            <th><strong>Alliance Created Date</strong></th>
            <th><strong>Alliance Status</strong></th>
            <th><strong>VZW Account Manager</strong></th>
            <th><strong>Contract Offered date</strong></th>
            <th><strong>Contract Status</strong></th>
        </tr>
        <logic:iterate id="alliance" name="ContractForm" property="contractAlliances"
                       type="com.netpace.aims.controller.alliance.AllianceForm">
            <tr>
                <td><a href='/aims/allianceStatus.do?task=view&alliance_id=<bean:write name="alliance" property="allianceId"/>'
                        class="a">
                    <bean:write name="alliance" property="companyName"/>
                </a></td>
                <td align="left">
                    <bean:write name="alliance" property="dateEstablished" formatKey="date.format"
                                filter="true"/>
                </td>
                <td align="left">
                    <bean:write name="alliance" property="status"/>
                </td>
                <td align="left">
                    <bean:write name="alliance" property="vzwAccMgrFirstName"/>
                    &nbsp;
                    <bean:write name="alliance" property="vzwAccMgrLastName"/>
                </td>
                <td align="left">
                    <bean:write name="alliance" property="acceptDeclineDate" formatKey="date.format"
                                filter="true"/>
                </td>
                <td align="left">
                    <bean:write name="ContractForm" property="contractStatus"/>
                </td>
            </tr>
        </logic:iterate>
    </table>
</DIV>
<% } %>

</DIV>
<!-- FORM END HERE -->
</html:form>
</DIV>
</div>