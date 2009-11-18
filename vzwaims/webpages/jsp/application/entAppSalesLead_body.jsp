<%@page import="com.netpace.aims.util.AimsConstants"%>
<%@page import="com.netpace.aims.model.core.AimsUser"%>
<%@ taglib uri="/WEB-INF/tiles.tld" prefix="tiles" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"	%>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims" %>

<jsp:useBean id="EntAppSalesLeadForm" class="com.netpace.aims.controller.application.EntAppSalesLeadForm"
             scope="request"/>

<script language="javaScript">
var aimsTypesArray = new Array();

function AimsTypes()
{
    this.typeId = "";
    this.typeValue = "";
    this.typeDefId = "";
}

<%
    int	index	=	0;
    %>

<logic:iterate id="formAimsTypes"	name="EntAppSalesLeadForm" property="allSubRegion" scope="request">
aimsTypes = new AimsTypes();
aimsTypes.typeId = "<bean:write	name="formAimsTypes" property="typeId"	/>";
aimsTypes.typeValue = "<bean:write	name="formAimsTypes" property="typeValue"	/>";
aimsTypes.typeDefId = "<bean:write	name="formAimsTypes" property="typeDefId"	/>";
aimsTypesArray[<%=index%>] = aimsTypes;
<%index++;%>
</logic:iterate>

var supported = (window.Option) ? 1 : 0;


function changeRegion() {
    if (!supported) {
        alert("Feature	not	supported");
    }
    var options = document.forms[0].subRegionId.options;
    for (var i = options.length - 1; i > 0; i--) {
        options[i] = null;
    }

    //options[0] = new Option("<bean:message	key="ManageApplicationForm.label.selectOne"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>", "0");
    var k = 0;
    var m = 0;

    for (var j = 0; j < aimsTypesArray.length; j++) {

        if (aimsTypesArray[j].typeDefId == document.forms[0].regionId.options[document.forms[0].regionId.selectedIndex].value)
        {
            options[k] = new Option(aimsTypesArray[j].typeValue, aimsTypesArray[j].typeId);
            if (aimsTypesArray[j].typeId == "<bean:write	name="EntAppSalesLeadForm" property="subRegionId" scope="request"/>")
                m = k;
            k++;
        }
    }

    options[m].selected = true;
}


function onPageLoad(task, isVzwUser)
{

 if(task=="view")
 {
 	if(isVzwUser)
 	{
 		document.forms[0].jmaPartnerId.disabled = true;
 	}
 	document.forms[0].customerName.disabled = true;
 	document.forms[0].regionId.disabled = true;
 	document.forms[0].subRegionId.disabled = true;
 	document.forms[0].state.disabled = true;
 	document.forms[0].zipCode.disabled = true;
 	document.forms[0].city.disabled = true;
 	document.forms[0].isNewVzwCustomer[0].disabled = true;
 	document.forms[0].isNewVzwCustomer[1].disabled = true;
 	document.forms[0].newVzwCustomer.disabled = true;
 	document.forms[0].solutionName.disabled = true;
 	document.forms[0].solutionDescription.disabled = true;
 	document.forms[0].verticalId.disabled = true;
 	document.forms[0].deviceName.disabled = true;
 	document.forms[0].numberOfDevices.disabled = true;
 	document.forms[0].solutionComments.disabled = true;
 	document.forms[0].solutionTotalSales.disabled = true;
 	document.forms[0].salesRepFullName.disabled = true;
 	document.forms[0].salesRepEmailAddress.disabled = true;
 	document.forms[0].salesRepPhoneNumber.disabled = true;
 	document.forms[0].salesRepContactInformation.disabled = true;
 	document.forms[0].salesLeadStatus.disabled = true;
 	document.forms[0].closeDate.disabled = true;
 	document.getElementById("daysOfMonth2Pos").style.display="none";
 	document.getElementById("Save").style.display="none";
 	
 	
 }
}

function showHideCloseDate(status)
{

	if(status == '<%= AimsConstants.SALES_LEAD_STATUS_WIN%>')
	{
		document.getElementById("closeDateDiv").style.display="block";
		document.getElementById("closeDateLabelDiv").style.display="block";
	}
	else
	{
		document.getElementById("closeDateDiv").style.display="none";
		document.getElementById("closeDateLabelDiv").style.display="none";
	}	
}


</script>

<%@ include file="/common/error.jsp" %>

<%  
	String currUserType = ((AimsUser) (request.getSession().getAttribute(AimsConstants.AIMS_USER))).getUserType();
%>


<DIV class="homeColumnTab lBox">
<html:form action="/entAppSalesLeadUpdate" enctype="multipart/form-data">

             <!-- SALES LEAD -->
             
<DIV class="headLeftCurveblk"></DIV>
<H1>
   <bean:message key="JMAApp.submitSalesLead.header" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="5" >
<tr>
   <%if(currUserType.equals(AimsConstants.VZW_USERTYPE)){ %>
	<th width="25%">
		<Strong><bean:message key="JMAApp.submitSalesLead.jmaPartner" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</th>
	<th width="75%">
		<html:select property="jmaPartnerId" styleClass="selectField">
			<logic:notEmpty name="EntAppSalesLeadForm" property="allPartner">
				<logic:iterate id="partner" name="EntAppSalesLeadForm" property="allPartner" scope="request">
					
					<bean:define id="partner_id">
             			<bean:write name="partner" property="partnerId"/>
         			</bean:define>
					<html:option value="<%=partner_id%>">
						<bean:write name="partner" property="partnerName" />
					</html:option>
				</logic:iterate>	
			</logic:notEmpty>	
		</html:select>
	</th>
	<%} else {%>
	<th width="25%">
		<Strong><bean:message key="JMAApp.submitSalesLead.customerName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</th>
	<th width="75%">
		<html:text styleClass="inputField" property="customerName" size="53" maxlength="200"/>
	</th>
	<% } %>
</tr>

<%if(currUserType.equals(AimsConstants.VZW_USERTYPE)){ %>
<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.customerName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:text styleClass="inputField" property="customerName" size="53" maxlength="200"/>
	</td>

</tr>
<% } %>

<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.region" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:select property="regionId" styleClass="selectField" onchange="changeRegion()">
			<logic:notEmpty name="EntAppSalesLeadForm" property="allRegion">
				<logic:iterate id="region" name="EntAppSalesLeadForm" property="allRegion" scope="request">
					
					<bean:define id="region_id">
             			<bean:write name="region" property="typeId"/>
         			</bean:define>
					<html:option value="<%=region_id%>">
						<bean:write name="region" property="typeValue" />
					</html:option>
				</logic:iterate>	
			</logic:notEmpty>	
		</html:select>
	</td>
</tr>

<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.subRegion" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:select property="subRegionId" styleClass="selectField">
			
		</html:select>
	</td>
</tr>

<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.city" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:text styleClass="inputField" property="city" size="53" maxlength="100"/>
	</td>
</tr>

<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.state" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:select property="state" styleClass="selectField">
            <html:option value="AK">AK</html:option>
            <html:option value="AL">AL</html:option>
            <html:option value="AR">AR</html:option>
            <html:option value="AZ">AZ</html:option>
            <html:option value="CA">CA</html:option>
            <html:option value="CT">CT</html:option>
            <html:option value="DE">DE</html:option>
            <html:option value="FL">FL</html:option>
            <html:option value="GA">GA</html:option>
            <html:option value="HI">HI</html:option>
           
           <html:option value="ID">ID</html:option>
		   <html:option value="IL">IL</html:option>
		   <html:option value="IN">IN</html:option>
		   <html:option value="IA">IA</html:option>
		   <html:option value="KS">KS</html:option>
		   <html:option value="KY">KY</html:option>
		   <html:option value="LA">LA</html:option>
		   <html:option value="ME">ME</html:option>
		   <html:option value="MD">MD</html:option>
		   <html:option value="MA">MA</html:option>

			<html:option value="MI">MI</html:option>
			<html:option value="MN">MN</html:option>
			<html:option value="MS">MS</html:option>
			<html:option value="MO">MO</html:option>
			<html:option value="MT">MT</html:option>
			<html:option value="NE">NE</html:option>
			<html:option value="NV">NV</html:option>
			<html:option value="NH">NH</html:option>
			<html:option value="NJ">NJ</html:option>
			<html:option value="NM">NM</html:option>

			<html:option value="NY">NY</html:option>
			<html:option value="NC">NC</html:option>
			<html:option value="ND">ND</html:option>
			<html:option value="OH">OH</html:option>
			<html:option value="OK">OK</html:option>
			<html:option value="OR">OR</html:option>
			<html:option value="PA">PA</html:option>
			<html:option value="RI">RI</html:option>
			<html:option value="SC">SC</html:option>
			<html:option value="SD">SD</html:option>
			
			<html:option value="TN">TN</html:option>
			<html:option value="TX">TX</html:option>
			<html:option value="UT">UT</html:option>
			<html:option value="VT">VT</html:option>
			<html:option value="VA">VA</html:option>
			<html:option value="WA">WA</html:option>
			<html:option value="WV">WV</html:option>
			<html:option value="WI">WI</html:option>
			<html:option value="WY">WY</html:option>
            
        </html:select>
	</td>
</tr>

<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.zibCode" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:text styleClass="inputField" property="zipCode" size="20" maxlength="20"/>
	</td>
</tr>

<tr>
	<td style="vertical-align: top;">
		<Strong><bean:message key="JMAApp.submitSalesLead.newVzwCustomer" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>		
	</td>
	<td>
		<html:radio property="isNewVzwCustomer" value="Y" />Yes	
        <html:radio property="isNewVzwCustomer" value="N" />No
    </td>
</tr>

<tr>
	<td style="vertical-align: top;">
		<strong>Comments:</strong>
	</td>
	<td>	
		<html:textarea styleClass="textareaField" property="newVzwCustomer" onkeyup="TruncateText(this,1000);" onkeypress="TruncateText(this,1000);" rows="3" cols="50" ></html:textarea>
    </td>
</tr>

<tr>
	<td>
		&nbsp;
	</td>
	<td>
		&nbsp;
	</td>
</tr>

</table>
</DIV>

                        <!-- SOLUTION INFORMATION -->

<DIV class="headLeftCurveblk"></DIV>
<H1>
   <bean:message key="JMAApp.submitSalesLead.solutionHeader" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="5" >
<tr>
	<th width="25%">
		<Strong><bean:message key="JMAApp.submitSalesLead.solutionName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</th>
	<th width="75%">
		<html:text styleClass="inputField" property="solutionName" size="53" maxlength="200"/>
	</th>
	
</tr>

<tr>
	<td style="vertical-align: top;">
		<Strong><bean:message key="JMAApp.submitSalesLead.solutionDescription" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:textarea styleClass="textareaField" property="solutionDescription" onkeyup="TruncateText(this,1000);" onkeypress="TruncateText(this,1000);" rows="3" cols="50" ></html:textarea>
	</td>
</tr>

<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.vertical" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:select property="verticalId" styleClass="selectField">
			<logic:notEmpty name="EntAppSalesLeadForm" property="allVertical">
					<logic:iterate id="vertical" name="EntAppSalesLeadForm" property="allVertical" scope="request">
						
						<bean:define id="verical_id">
	             			<bean:write name="vertical" property="industryId"/>
	         			</bean:define>
						<html:option value="<%=verical_id%>">
							<bean:write name="vertical" property="industryName" />
						</html:option>
						
					</logic:iterate>	
				</logic:notEmpty>	
		</html:select>
	</td>
</tr>

<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.deviceName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:text styleClass="inputField" property="deviceName" size="53" maxlength="1000"/>
	</td>
</tr>

<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.numberOfDevices" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:text styleClass="inputField" property="numberOfDevices" size="53" maxlength="100"/>
	</td>
</tr>

<tr>
	<td style="vertical-align: top;">
		<Strong><bean:message key="JMAApp.submitSalesLead.solutionComments" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:textarea styleClass="textareaField" property="solutionComments" onkeyup="TruncateText(this,1000);" onkeypress="TruncateText(this,1000);" rows="3" cols="50" ></html:textarea>
	</td>
</tr>

<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.totalSales" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:text styleClass="inputField" property="solutionTotalSales" size="20" maxlength="100"/>
	</td>
</tr>

<tr>
	<td>
		&nbsp;
	</td>
	<td>
		&nbsp;
	</td>
</tr>

</table>
   
</DIV>




                        <!-- VZW SALES REPRESENTIVE -->
<DIV class="headLeftCurveblk"></DIV>
<H1>
	<% if(currUserType.equals(AimsConstants.VZW_USERTYPE)){%>
	<logic:equal name="EntAppSalesLeadForm" property="view" value="sent">
   		<bean:message key="JMAApp.submitSalesLead.verizonSalesRep" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
   	</logic:equal>
   	<logic:equal name="EntAppSalesLeadForm" property="view" value="received">
   		<bean:message key="JMAApp.submitSalesLead.partnerSalesRep" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
   	</logic:equal>	
    <% } else { %>
    <logic:equal name="EntAppSalesLeadForm" property="view" value="sent">
    	<bean:message key="JMAApp.submitSalesLead.partnerSalesRep" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
    </logic:equal>
    <logic:equal name="EntAppSalesLeadForm" property="view" value="received">
   		<bean:message key="JMAApp.submitSalesLead.verizonSalesRep" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
   	</logic:equal>		
    <% } %>	 		
</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="5" >
<tr>
	<th width="25%">
		<Strong><bean:message key="JMAApp.submitSalesLead.salesRepFullName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</th>
	<th width="75%">
		<html:text styleClass="inputField" property="salesRepFullName" size="53" maxlength="100"/>
	</th>
</tr>

<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.salesRepEmailAddress" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:text styleClass="inputField" property="salesRepEmailAddress" size="53" maxlength="200"/>
	</td>
</tr>

<tr>
	<td>
		<Strong><bean:message key="JMAApp.submitSalesLead.salesRepPhoneNumber" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</td>
	<td>
		<html:text styleClass="inputField" property="salesRepPhoneNumber" size="53" maxlength="100"/>
	</td>
</tr>

<tr>
	<td style="vertical-align: top;">
	<% if(currUserType.equals(AimsConstants.VZW_USERTYPE)){%>
	<logic:equal name="EntAppSalesLeadForm" property="view" value="sent">
		<Strong><bean:message key="JMAApp.submitSalesLead.contactInformation" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</logic:equal>
	<logic:equal name="EntAppSalesLeadForm" property="view" value="received">
		<Strong><bean:message key="JMAApp.submitSalesLead.additionalInformation" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</logic:equal>
	<%} else{ %>
	<logic:equal name="EntAppSalesLeadForm" property="view" value="sent">	
		<Strong><bean:message key="JMAApp.submitSalesLead.additionalInformation" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</logic:equal>
	<logic:equal name="EntAppSalesLeadForm" property="view" value="received">
		<Strong><bean:message key="JMAApp.submitSalesLead.contactInformation" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</logic:equal>
	<%} %>
	</td>
	<td>
		<html:textarea styleClass="textareaField" property="salesRepContactInformation" onkeyup="TruncateText(this,1000);" onkeypress="TruncateText(this,1000);" rows="3" cols="50" ></html:textarea>
	</td>
</tr>

<tr>
	<td>
		&nbsp;
	</td>
	<td>
		&nbsp;
	</td>
</tr>
</table>
   
</DIV>

            
            <!-- ther Information --> 
<DIV class="headLeftCurveblk"></DIV>
<H1>
   Other Details
</H1>
<DIV class="headRightCurveblk"></DIV>
<DIV class="contentbox">
<table width="100%" class="GridGradient" border="0" cellspacing="0" cellpadding="5" >
<tr>
	<th width="25%">
		<Strong><bean:message key="JMAApp.submitSalesLead.salesLeadStatus" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
	</th>
	<th width="75%">
		<html:select property="salesLeadStatus" styleClass="selectField" onchange="javascript: showHideCloseDate(this.value);">
			<logic:notEmpty name="EntAppSalesLeadForm" property="allStatus">
					<logic:iterate id="status" name="EntAppSalesLeadForm" property="allStatus" scope="request">
						
						<bean:define id="status_id">
	             			<bean:write name="status" property="typeId"/>
	         			</bean:define>
						<html:option value="<%=status_id%>">
							<bean:write name="status" property="typeValue" />
						</html:option>
					</logic:iterate>	
				</logic:notEmpty>	
		</html:select>
	</th>
</tr>

<tr>
	<td>
		<div id="closeDateLabelDiv">
		<Strong><bean:message key="JMAApp.submitSalesLead.closeDate" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</Strong>
		</div>
	</td>
	<td>
	<div id="closeDateDiv">
		<html:text styleClass="inputField"	property="closeDate"	size="15"/>
		<img name="daysOfMonth2Pos" onclick="toggleDatePicker('daysOfMonth2','EntAppSalesLeadForm.closeDate')" id="daysOfMonth2Pos" <bean:message key="images.calendar.button.lite"/> />
		<div style="position:absolute;" id="daysOfMonth2"/>
	</div>	
	</td>	
</tr>

<tr>
	<td>
		&nbsp;
	</td>
	<td>
		&nbsp;
	</td>
</tr>
</table>


           <!-- Buttons --> 

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>
		&nbsp;
	</td>
</tr>
<tr>
	<td>
		<div class="redBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Save" title="Save">
			<div><div><div onClick="javascript: submitForm();">SUBMIT</div></div></div>
		</div>
		
		<div class="blackBtn" style=" margin-left:10px;float:right; margin-top:3px" id="Cancel" title="Cancel">
			<div><div><div onclick="javascript: submitCancel()">CANCEL</div></div></div>
		</div>
	</td>
</tr>

<tr>
	<td>
		&nbsp;
	</td>
</tr>
</table>                                             

<input type="hidden" name="task" value="<bean:write name="EntAppSalesLeadForm" property="task"/>"/>
<input type="hidden" name="view" value="<bean:write name="EntAppSalesLeadForm" property="view"/>"/>
<input type="hidden" name="salesLeadId" value="<bean:write name="EntAppSalesLeadForm" property="salesLeadId"/>"/>

<script language="javascript">
 <%if(currUserType.equals(AimsConstants.VZW_USERTYPE)){ %>
 onPageLoad("<bean:write name="EntAppSalesLeadForm" property="task"/>",true);
 <% } else {%>
 	onPageLoad("<bean:write name="EntAppSalesLeadForm" property="task"/>",false);
  <% } %>
  
  showHideCloseDate('<bean:write name="EntAppSalesLeadForm" property="salesLeadStatus"/>');
  changeRegion();
  
</script>

</html:form>
</DIV>






<html:form action="/entAppSalesLeadView" enctype="multipart/form-data">
	<input type="hidden" name="view" value="sent"/>
</html:form>

<script language="javascript">

function submitForm(task)
{
	document.forms[0].submit();
}

function submitCancel()
{
	document.forms[1].view.value='<bean:write name="EntAppSalesLeadForm" property="view"/>';
	document.forms[1].submit();
}

</script>