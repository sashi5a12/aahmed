<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<%@ include file="/common/error.jsp" %>
<script language="javascript">

function showHideBOBO()
{
	var platform=document.ContractForm.platformId;

	if(platform.value=="5") // JMA plate form
	{
		document.getElementById("boboDiv").style.display = "";
	}
	else
	{
		document.getElementById("boboDiv").style.display = "none";

	}
	
	
}

function previewContract(contractId){
	var url="<%=request.getContextPath()%>/showContractHTMLAction.do?contractId="+contractId;
	var wind;
    wind = window.open ("","previewContract","resizable=1,scrollbars=1,width=950,height=600,screenX=50,left=50,screenY=30,top=30");
    wind.location.href=url;
	wind.focus();
}

</script>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">


<!-- FORM START HERE -->
<html:form action="/contractsEdit" enctype="multipart/form-data">
<html:hidden property="contractDocumentFileName"/>
<html:hidden property="contractHtmlDocumentFileName"/>

<html:hidden property="contractDocumentTempFileId"/>
<html:hidden property="contractHtmlDocumentTempFileId"/>

<logic:equal name="ContractForm" property="taskName" value="createForm">
    <html:hidden property="task" value="create"/>
</logic:equal>

<%String title= "Add New Contract";%>

<logic:equal name="ContractForm" property="taskName" value="editForm">
    <html:hidden property="task" value="edit"/>
    <html:hidden property="contractId"/>
    <%title="Contract";%>    
</logic:equal>
<html:hidden property="taskName"/>


<DIV class="headLeftCurveblk"></DIV>
<H1><%=title%></H1>
<DIV class="headRightCurveblk"></DIV>


<DIV class="contentbox">
    <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient" border="0">        
        <tr>
            <th width="42%"><strong>Contract Name <span class="requiredText">*</span>:</strong> <br>
                <html:text styleClass="inputField" property="contractTitle" size="35" maxlength="50"/>
            </th>
            <th width="58%"><strong>Contract Version <span class="requiredText">*</span>:</strong><br>
                <html:text styleClass="inputField" property="contractVersion" size="35" maxlength="50"/>
            </th>
        </tr>
        <tr>
            <td><strong>Contract Status <span class="requiredText">*</span>:</strong> <br>
                <html:select styleClass="selectField" property="contractStatus" size="1">
                    <html:option value="A">Active</html:option>
                    <html:option value="E">Expired</html:option>
                </html:select>
            </td>
            <td><strong>Contract Document <span class="requiredText">*</span>:</strong><br/>
                <html:file styleClass="inputField" property="contractDocument" size="35"/>
                <br/>
                <logic:notEmpty	name="ContractForm"	property="contractDocumentFileName" scope="request">
                    <logic:equal name="ContractForm" property="contractDocumentTempFileId"	scope="request"	value="0">
                        <a class="a" target="_blank"
                           href='/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=<bean:write name="ContractForm" property="contractId"/>'>
                            <bean:write name="ContractForm" property="contractDocumentFileName"/>
                        </a>
                    </logic:equal>
                    <logic:notEqual	name="ContractForm"	property="contractDocumentTempFileId" scope="request" value="0">
                        <a class="a" target="_blank"
                           href='/aims/resourceContractAction.do?resource=tempFile&tempFileId=<bean:write name="ContractForm" property="contractDocumentTempFileId"/>'>
                            <bean:write name="ContractForm" property="contractDocumentFileName"/>
                        </a>
                    </logic:notEqual>
                </logic:notEmpty>                
            </td>
        </tr>
        <tr>
            <td style="padding-left: 0px;">
            
            <table width="100%" border="0" align="left">
            <tr>
            	<td> 
				<strong>Platform <span class="requiredText">*</span>:</strong> <br>
                <html:select styleClass="selectField" property="platformId" size="1" onchange="javascript: showHideBOBO()" >
                    <html:option value="0">Select One</html:option> 
                    <logic:iterate id="platform" name="ContractForm" property="allPlatforms"
                                   type="com.netpace.aims.model.core.AimsPlatform">
                        <html:option value="<%=platform.getPlatformId().toString()%>">
                            <bean:write name="platform" property="platformName"/>
                        </html:option>
                    </logic:iterate>
                </html:select>
                </td>
                <td>
                	  
                <div id="boboDiv"><br>
	        	       <html:checkbox value="Y" property="isBoboContract" onclick=""/>
	            		 Is BOBO Contract
            	</div>	
                </td>
            </tr>
            </table>
            </td>
            <td>
               <table width="100%" cellpadding="0" cellspacing="0" border="0">
                   <tr>
                       <td style="padding-left:0px;">
                           <strong>Contract Document (HTML)<span class="requiredText">*</span>:</strong>
                       </td>
                   </tr>
                   <tr>
                       <td style="vertical-align:top;padding-left:0px;">
                            <html:file styleClass="inputField" property="contractHtmlDocument" size="35"/>
                       </td>
                   </tr>
                   <tr>
                       <td style="padding-left:0px;">
                           <logic:equal name="ContractForm" property="contractHtmlDocumentTempFileId"	scope="request"	value="0">
                                <a class="a" target="_blank"
                                   href='/aims/resourceContractAction.do?resource=htmlFile&resourceId=<bean:write name="ContractForm" property="contractId"/>'>
                                    <bean:write name="ContractForm" property="contractHtmlDocumentFileName"/>
                                </a>
                            </logic:equal>
                            <logic:notEqual	name="ContractForm"	property="contractHtmlDocumentTempFileId" scope="request" value="0">
                                <a class="a" target="_blank"
                                   href='/aims/resourceContractAction.do?resource=tempFile&tempFileId=<bean:write name="ContractForm" property="contractHtmlDocumentTempFileId"/>'>
                                    <bean:write name="ContractForm" property="contractHtmlDocumentFileName"/>
                                </a>
                            </logic:notEqual>
                       </td>
                   </tr>
                   <%-- if contract is created and contract html document is present, show preview html link --%>
                   <logic:notEmpty name="ContractForm" property="contractId">
                        <logic:notEqual name="ContractForm" property="contractId" value="0">
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
                        </logic:notEqual>
                   </logic:notEmpty>
               </table>
            </td>
        </tr>

        <tr>
            <td style="vertical-align:top;"><strong>Auto-Offered Click Through Contract<span class="requiredText">*</span>:</strong><br/>
                <html:radio  property="clickThroughContract" value="Y"/>Yes &nbsp;
                <html:radio  property="clickThroughContract" value="N"/>No
            </td>
            <td style="vertical-align: top">
                <strong>Contract Expiry Date <span class="requiredText">*</span>:</strong> <br>
                <html:text styleClass="inputField" property="contractExpiryDate" size="35" maxlength="20"/>
                <img name="daysOfMonthPos" onclick="toggleDatePicker('daysOfMonth','ContractForm.contractExpiryDate')" id="daysOfMonthPos" <bean:message key="images.calendar.button.lite"/> />
                <div style="position:absolute;" id="daysOfMonth"/>
            </td>
        </tr>

        <tr>
            <td style="vertical-align:top;"><strong>Comments:&nbsp;(Max: 500 chars)</strong><br/>
                <html:textarea styleClass="textareaField" property="comments" rows="5" cols="43"/>
            </td>
            <td style="vertical-align: top">&nbsp;</td>
        </tr>

        <tr>
            <td colspan="2">&nbsp;</td>
        </tr>
    </table>
</div>

<DIV class="headLeftCurveblk"></DIV>
<H1>Amendment Information</H1>
<DIV class="headRightCurveblk"></DIV>

<DIV class="contentbox">
    <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" >
        <tr>
            <th>Amendment Name</th>
            <th>Amendment Version</th>
            <th>Amendment Document</th>
            <th>Amendment Expirty Date</th>
            <th>Add</th>
        </tr>
        <logic:iterate id="amendment" name="ContractForm" property="allAmendments"
                       type="com.netpace.aims.controller.contracts.AmendmentForm">
            <tr>
                <td align="left"><a
                        href='/aims/amendmentsSetup.do?task=editViewForm&amendment_id=<bean:write name="amendment" property="amendmentId"/>'
                        class="a">
                    <bean:write name="amendment" property="amendmentTitle"/>
                </a></td>
                <td align="left">
                    <bean:write name="amendment" property="amendmentVersion"/>
                </td>
                <td align="left"><a class="a" target="_blank"
                                    href='/aims/amendmentsResourceAction.do?resource=document&object=Amendtment&resourceId=<bean:write name="amendment" property="amendmentId"/>'>
                    <bean:write name="amendment" property="amendmentDocumentFileName"/>
                </a> &nbsp; </td>
                <td align="left">
                    <bean:write name="amendment" property="amendmentExpiryDate" formatKey="date.format" filter="true"/>
                </td>
                <td align="left">
                    <html:multibox property="amendmentIds">
                        <bean:write name="amendment" property="amendmentId"/>
                    </html:multibox>
                </td>
            </tr>
        </logic:iterate>
    </table>
</DIV>


<div> &nbsp;</div>
<DIV class="headLeftCurveblk"></DIV>
<H1>Add New Amendment</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
    <tr>
        <th width="42%"><strong>Amendment Name <span class="requiredText">*</span>:</strong> <br>
            <html:text styleClass="inputField" property="amendmentTitle" size="35" maxlength="50"/>
        </th>
        <th width="58%"><strong>Amendment Version <span class="requiredText">*</span>:</strong><br>
            <html:text styleClass="inputField" property="amendmentVersion" size="35" maxlength="50"/>
        </th>
    </tr>
    <tr>
        <td width="42%"><strong>Amendment Status <span class="requiredText">*</span>:</strong> <br>
            <html:select styleClass="selectField" property="amendmentStatus" size="1">
                <html:option value="A">Active</html:option>
                <html:option value="E">Expired</html:option>                
            </html:select>
        </td>
        <td width="58%"><strong>Amendment Document <span class="requiredText">*</span>:</strong><br>
            <html:file property="amendmentDocument" styleClass="inputField"/>
        </td>
    </tr>
    <tr>
        <td width="42%"><strong>Amendment Expiry Date <span class="requiredText">*</span>:</strong> <br>
            <html:text styleClass="inputField" property="amendmentExpiryDate" size="35" maxlength="20"/>
            <img name="daysOfMonth2Pos" onclick="toggleDatePicker('daysOfMonth2','ContractForm.amendmentExpiryDate')"
                 id="daysOfMonth2Pos"
                    <bean:message key="images.calendar.button.lite"/>
                    />

            <div style="position:absolute;" id="daysOfMonth2"/>
        </td>
        <td width="58%">&nbsp;</td>
    </tr>
    <tr>
        <td width="42%"><strong>Comments:&nbsp;(Max: 500 chars)</strong><br>
            <html:textarea styleClass="textareaField" property="amendmentComments" rows="5" cols="43"/>
        </td>
        <td width="58%">&nbsp;</td>
    </tr>
    <tr>
        <td align="right" colspan="2">
            <logic:equal name="ContractForm" property="taskName" value="createForm">
                <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Save" title="Save">
                    <div>
                        <div>
                            <div onclick="document.forms[0].submit();">Create</div>
                        </div>
                    </div>
                </div>
            </logic:equal>
            <logic:equal name="ContractForm" property="taskName" value="editForm">
                <div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Save" title="Save">
                    <div>
                        <div>
                            <div onclick="document.forms[0].submit();">Save</div>
                        </div>
                    </div>
                </div>
            </logic:equal>
        </td>
    </tr>
</table>
</div>


<!-- FORM END HERE -->
</html:form>
</DIV>
</div>
<script language="javascript">
showHideBOBO();
</script>