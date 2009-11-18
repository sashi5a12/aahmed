<%@	page language="java" import="com.netpace.aims.bo.security.*, com.netpace.aims.bo.application.*, com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:useBean id="task" class="java.lang.String"	scope="request"/>
<jsp:useBean id="BrewApplicationUpdateForm" class="com.netpace.aims.controller.application.BrewApplicationUpdateForm" scope="request" />
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" />

<%ApplicationUpdateForm.setCurrentPage("page5");%>

<script	language="javascript">
	<%@ include  file="include/appJScript.jsp" %>

	function populateDatesForVZWCompletion()
	{
		var dateInConcern = new Date(document.forms[0].plannedCompletionByVzw.value);
		if (!isNaN(dateInConcern))
		{
			var thisDay = dateInConcern.getDay();
			if (thisDay < 2)
				document.forms[0].deckLaunchDate.value = getDateInCorrectFormat(addDaysToDate(dateInConcern, (2 - thisDay)));
			else
				document.forms[0].deckLaunchDate.value = getDateInCorrectFormat(addDaysToDate(dateInConcern, (7 - (thisDay - 2))));
		}
	}

	function populateDatesForVZWEntry()
	{
		var dateInConcern = new Date(document.forms[0].plannedEntryIntoVzw.value);
		if (!isNaN(dateInConcern))
		{
			var devicesNumber = document.forms[0].noOfDevices.value;

			if (devicesNumber <= 2)
				document.forms[0].plannedCompletionByVzw.value = getDateInCorrectFormat(addDaysToDate(dateInConcern, 14));
			else if (devicesNumber <= 10)
				document.forms[0].plannedCompletionByVzw.value = getDateInCorrectFormat(addDaysToDate(dateInConcern, 21));
			else if (devicesNumber > 10)
				document.forms[0].plannedCompletionByVzw.value = getDateInCorrectFormat(addDaysToDate(dateInConcern, 28));

			populateDatesForVZWCompletion();
		}
	}

	<%if ( ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
		function disableEvaluationButtons()
		{
			<logic:equal name="BrewApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.ACCEPTANCE_ID.toString()%>">
				for (var i=0; i<document.forms[0].elements.length; i++) 
				{
		    	if (document.forms[0].elements[i].name == "evaluation") 
		    		if (document.forms[0].elements[i].value == "N")
		    			document.forms[0].elements[i].disabled = true;					
				}
			</logic:equal>
			<logic:notEqual name="BrewApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.ACCEPTANCE_ID.toString()%>">
                <logic:notEqual name="BrewApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"    value="<%=AimsConstants.TESTING_ID.toString()%>">
    				for (var i=0; i<document.forms[0].elements.length; i++) 
    				{
    		    	if (document.forms[0].elements[i].name == "evaluation") 
    		    		if (document.forms[0].elements[i].value == "S")
    		    			document.forms[0].elements[i].disabled = true;					
    				}
                </logic:notEqual>
			</logic:notEqual>
			<logic:equal name="BrewApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"	value="<%=AimsConstants.SUNSET_ID.toString()%>">
				for (var i=0; i<document.forms[0].elements.length; i++) 
				{
		    	if (document.forms[0].elements[i].name == "evaluation") 
		    		document.forms[0].elements[i].disabled = true;					
				}
			</logic:equal>
		}
	<% }%>	

</script>

<%@ include file="include/brewMessageHeader.jsp" %>

<div id="contentBox" style="float:left">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/appTabs.jsp" %>
    <logic:notEmpty name="BrewApplicationUpdateForm" property="conceptTitle">
      <table width="100%" style="float:left;" width="100%" border="1" cellspacing="0" cellpading="0">
          <tr>
              <td width="50%">
                  <strong><bean:message key="ManageApplicationForm.appConceptReference"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:&nbsp;</strong>
                  <a href="<bean:write name="ApplicationUpdateForm" property="setupURL"/>?task=view&appsId=<bean:write name="BrewApplicationUpdateForm" property="conceptId" />" class="a" target="_blank">
                    <bean:write name="BrewApplicationUpdateForm" property="conceptTitle"/>
                  </a>
              </td>
              <td width="50%">
                  <strong><bean:message key="ManageApplicationForm.appConceptEvaluationDate"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:&nbsp;</strong>
                  <bean:write name="BrewApplicationUpdateForm" property="conceptEvaluationDate"/>
              </td>
          </tr>
      </table>
    </logic:notEmpty>
    <html:form action="/brewApplicationUpdate.do"	enctype="multipart/form-data">
      <%@ include  file="include/appHidden.jsp" %>
      <input type="hidden" name="noOfDevices" value="<%=Integer.toString(BrewApplicationUpdateForm.getListSelectedDevices().length)%>">  
      <div class="contentbox">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>&nbsp;</td>
          </tr>
          <logic:notEmpty name="BrewApplicationUpdateForm" property="history">
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
					<logic:iterate id="item" name="BrewApplicationUpdateForm" property="history">
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
            <td><table width="100%" cellspacing="0" cellpadding="0" class="Grid2" border="1">
                <tr>
                  <th width="25%" class="left"><bean:message key="BrewApplicationForm.developerName"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                  <th width="25%" class="left"><bean:message key="BrewApplicationForm.publisherName"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                  <th width="25%" class="left"><bean:message key="BrewApplicationForm.dateSubmitted"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                  <th width="25%" class="left"><bean:message key="BrewApplicationForm.applicationName"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                </tr>
                <tr>
                    <td width="25%"><html:text styleClass="inputFieldNoPad" style="" property="developerName"	size="26" maxlength="150"	 /></td>
                    <td width="25%"><html:text styleClass="inputFieldNoPad" property="publisherName"	size="26" maxlength="150"	 /></td>
                    <td width="25%"><bean:write name="BrewApplicationUpdateForm" property="submittedDate"/>&nbsp;</td>
                    <td width="25%"><html:text styleClass="inputFieldNoPad" property="title"	size="26" maxlength="200"	 /></td>
                </tr>                
                <th class="left" width="25%"><bean:message key="BrewApplicationForm.sellingPoints"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                <th class="left" width="25%"><bean:message key="ManageApplicationForm.appShortDesc"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                <th class="left" width="25%"><bean:message key="ManageApplicationForm.appLongDesc"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                <th class="left" width="25%">&nbsp;</th>
                <tr>                    
                    <td style="vertical-align:top;"><html:textarea styleClass="textareaFieldNoPad"	property="sellingPoints" rows="14"	cols="28"/></td>
                    <td style="vertical-align:top;"><html:textarea styleClass="textareaFieldNoPad"	property="shortDesc" rows="14"	cols="28"/></td>
                    <td style="vertical-align:top;"><html:textarea styleClass="textareaFieldNoPad"	property="longDesc" rows="14"	cols="28"/></td>
                    <td style="vertical-align:top;">&nbsp;</td>
                </tr>
                <tr>
                    <th class="left" width="25%"><bean:message key="BrewApplicationForm.plannedDevStartDate"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left" width="25%"><bean:message key="BrewApplicationForm.plannedEntryIntoNstl"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left" width="25%"><bean:message key="ApplicationForm.brewPlannedCompletionByNstl"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left" width="25%"><bean:message key="ApplicationForm.brewPlannedEntryIntoVzw"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                </tr>
                <tr>
                    <td>
                        <table cellspacing="0" cellpadding="0">
                            <tr>
                                <td><html:text styleClass="inputFieldNoPad" property="plannedDevStartDate"	size="22" maxlength="10"/></td>
                                <td><img name="daysOfMonth1Pos" onclick="toggleDatePicker('daysOfMonth1','BrewApplicationUpdateForm.plannedDevStartDate')" id="daysOfMonth1Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth1"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table cellspacing="0" cellpadding="0">
                            <tr>
                                <td><html:text styleClass="inputFieldNoPad" property="plannedEntryIntoNstl"	size="22" maxlength="10"/></td>
                                <td><img name="daysOfMonth2Pos" onclick="toggleDatePicker('daysOfMonth2','BrewApplicationUpdateForm.plannedEntryIntoNstl')" id="daysOfMonth2Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth2"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table cellspacing="0" cellpadding="0">
                            <tr>
                                <td><html:text styleClass="inputFieldNoPad" property="plannedCompletionByNstl"	size="22" maxlength="10"/></td>
                                <td><img name="daysOfMonth3Pos" onclick="toggleDatePicker('daysOfMonth3','BrewApplicationUpdateForm.plannedCompletionByNstl')" id="daysOfMonth3Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth3"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table cellspacing="0" cellpadding="0">
                            <tr>
                                <td><html:text styleClass="inputFieldNoPad" property="plannedEntryIntoVzw"	size="22"  onchange="populateDatesForVZWEntry();" onfocus="populateDatesForVZWEntry();" maxlength="10"/></td>
                                <td><img name="daysOfMonth4Pos" onclick="toggleDatePicker('daysOfMonth4','BrewApplicationUpdateForm.plannedEntryIntoVzw')" id="daysOfMonth4Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth4"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <th class="left"><bean:message key="ApplicationForm.brewPlannedCompletionByVzw"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left"><bean:message key="BrewApplicationForm.typeOfApplication"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left">&nbsp;</th>
                    <th class="left">&nbsp;</th>
                </tr>
                <tr>
                    <td>
                        <table cellspacing="0" cellpadding="0">
                            <tr>
                                <td><html:text styleClass="inputFieldNoPad" property="plannedCompletionByVzw"	size="22"  onchange="populateDatesForVZWCompletion();" onfocus="populateDatesForVZWCompletion();" maxlength="10"/></td>
                                <td><img name="daysOfMonth5Pos" onclick="toggleDatePicker('daysOfMonth5','BrewApplicationUpdateForm.plannedCompletionByVzw')" id="daysOfMonth5Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth5"/></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <logic:iterate id="formCategories" name="BrewApplicationUpdateForm" property="allCategories" scope="request">
                            <logic:equal name="formCategories" property="categoryId" value="<%=BrewApplicationUpdateForm.getAimsAppCategoryId().toString()%>">
                                <bean:write name="formCategories" property="categoryName" />
                            </logic:equal>
                        </logic:iterate>
                        &nbsp;
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <th class="left"><bean:message key="ApplicationForm.brewAnticipatedDaps"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left"><bean:message key="BrewApplicationForm.handsetsSupported"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left"><bean:message key="BrewApplicationForm.networkUse"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left"><bean:message key="BrewApplicationForm.singleMultiPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                </tr>
                <tr>
                    <td style="vertical-align:top;" rowspan="7">
                        <html:textarea styleClass="textareaFieldNoPad" 	property="anticipatedDaps" rows="14"	cols="28"/>
                    </td>
                    <td style="vertical-align:top;" rowspan="7">
                        <logic:notEmpty name="BrewApplicationUpdateForm" property="listSelectedDevices" scope="request">
                            <logic:iterate id="formDevices" name="BrewApplicationUpdateForm" property="availableDevices" scope="request">
                                <%for (int i=0; i<BrewApplicationUpdateForm.getListSelectedDevices().length; i++) {%>
                                    <logic:equal name="formDevices" property="deviceId" value="<%=(BrewApplicationUpdateForm.getListSelectedDevices())[i].toString()%>">
                                        <bean:write name="formDevices" property="deviceModel" /><br/>
                                    </logic:equal>
                                <% } %>
                            </logic:iterate>
                       </logic:notEmpty>&nbsp;
                    </td>
                    <td style="vertical-align:top;">
                        <html:radio	property="networkUse" value="S"/><bean:message key="ManageApplicationForm.radio.label.againstServer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br/>
                        <html:radio	property="networkUse" value="P"/><bean:message key="ManageApplicationForm.radio.label.againstPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br/>
                        <html:radio	property="networkUse" value="N"/><bean:message key="ManageApplicationForm.radio.label.noNetworkUsage"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br/>
                    </td>
                    <td style="vertical-align:top;">
                        <html:radio	property="singleMultiPlayer" value="S"/><bean:message key="ManageApplicationForm.radio.label.singlePlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br/>
                        <html:radio	property="singleMultiPlayer" value="M"/><bean:message key="ManageApplicationForm.radio.label.multiPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br/>
                        <html:radio	property="singleMultiPlayer" value="B"/><bean:message key="ManageApplicationForm.radio.label.bothPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br/>
                        <html:radio	property="singleMultiPlayer" value="N"/><bean:message key="ManageApplicationForm.radio.label.noPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/><br/>
                    </td>
                </tr>
                <tr>
                    <th class="left"><bean:message key="ApplicationForm.prizes"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left"><bean:message key="ManageApplicationForm.contentRating"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                </tr>
                <tr>
                    <td style="vertical-align:top;">
                        <html:radio property="prizes" value="Y"/><bean:message key="ManageApplicationForm.radio.label.yes"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                        <html:radio property="prizes" value="N"/><bean:message key="ManageApplicationForm.radio.label.no"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                    </td>
                    <td style="vertical-align:top;">
                        <logic:notEmpty name="BrewApplicationUpdateForm" property="contentRating">
                            <logic:iterate id="contentRatings" name="BrewApplicationUpdateForm" property="allBrewContentRatings" type="com.netpace.aims.model.core.AimsTypes">
                                <logic:equal name="contentRatings" property="typeId" value="<%=BrewApplicationUpdateForm.getContentRating().toString()%>">
                                    <bean:write name="contentRatings" property="typeValue"/>
                                </logic:equal>
                            </logic:iterate>
                        </logic:notEmpty>
                    </td>
                </tr>
                <tr>
                    <th class="left"><bean:message key="ApplicationForm.brewDeckPlacement"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left"><bean:message key="ApplicationForm.brewDeckLaunchDate"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                </tr>
                <tr>
                    <td style="vertical-align:middle;">
                        <html:select styleClass="selectFieldNoPad" property="deckPlacement" size="1" style="width:100%;">
                            <html:optionsCollection property="deckList" value="deckId" label="deckName"/>
                        </html:select>
                    </td>
                    <td>
                        <table cellspacing="0" cellpadding="0">
                            <tr>
                                <td style="padding-left:2px"><html:text property="deckLaunchDate" styleClass="inputFieldNoPad" size="22" maxlength="10"/></td>
                                <td><img name="daysOfMonth6Pos" onclick="toggleDatePicker('daysOfMonth6','BrewApplicationUpdateForm.deckLaunchDate')" id="daysOfMonth6Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth6"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <th colspan="2" class="left"><bean:message key="ApplicationForm.brewEvaluation"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                </tr>
                <tr>
                    <td colspan="2">
                        <table width="100%">
                            <%if ( (BrewApplicationUpdateForm.getAppType() != null) &&  (BrewApplicationUpdateForm.getAppType().equals("C")) ) {%>
                                <tr>
                                    <td width="50%"><html:radio property="evaluation" value="A"/><bean:message key="ManageApplicationForm.radio.label.conceptAccepted"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
                                    <td width="50%"><html:radio property="evaluation" value="R"/><bean:message key="ManageApplicationForm.radio.label.conceptRejected"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
                                </tr>
                            <%}else{%>
                                <tr>
                                    <td width="50%"><html:radio	property="evaluation" value="F"/><bean:message key="ManageApplicationForm.radio.label.featured"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
                                    <td width="50%"><html:radio	property="evaluation" value="G"/><bean:message key="ManageApplicationForm.radio.label.general"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
                                </tr>
                                <c:choose>
                                	<c:when test="${not empty requestScope.zonAutoBeforeLive && 'true' eq requestScope.zonAutoBeforeLive }">
		                                <tr>
		                                    <td><html:radio	property="evaluation" value="N"/><bean:message key="ManageApplicationForm.radio.label.notAccepted"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
		                                    <td><html:radio	property="evaluation" value="S"/><bean:message key="ManageApplicationForm.radio.label.sunset"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
		                                </tr>                                	
                                	</c:when>
                                	<c:otherwise>
		                                <tr>
		                                    <td colspan="2"><html:radio	property="evaluation" value="S"/><bean:message key="ManageApplicationForm.radio.label.sunset"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></td>
		                                </tr>                                	
                                	</c:otherwise>
                                </c:choose>
                            <%}%>
                        </table>
                    </td>
                </tr>
                <tr>
                    <th colspan="4" class="left"><bean:message key="BrewApplicationForm.screenShots"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                </tr>
                <tr>
                    <td colspan="4">
                        <a target="_blank" href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_type=<bean:message key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />"><img src="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" border="1" height="150" width="100"/></a>&nbsp;
                        <a target="_blank" href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_type=<bean:message key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />"><img src="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" border="1" height="150" width="100"/></a>&nbsp;
                        <a target="_blank" href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_type=<bean:message key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />"><img src="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" border="1" height="150" width="100"/></a>&nbsp;
                        <a target="_blank" href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_type=<bean:message key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />"><img src="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" border="1" height="150" width="100"/></a>&nbsp;
                        <a target="_blank" href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_type=<bean:message key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />"><img src="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" border="1" height="150" width="100"/></a>
                    </td>
                </tr>
              </table></td>
          </tr>
          </table>
          <table width="100%" cellpadding="0" cellspacing="0" border="0">
	          <logic:notEmpty name="BrewApplicationUpdateForm" property="userGuidePdfFileName">
	          	  <tr><td>&nbsp;</td></tr>	
		          <tr>
		          	<td width="100%">
		          		<table width="100%" cellpadding="0" cellspacing="0" border="0" class="GridGradient">
							<tr>
							    <td colspan="2" style="padding: 0px; margin: 0px">
							        <div class="lBox">
							            <DIV class="headLeftCurveblk"></DIV>
							            <H1>User Guide Snapshot</H1>					
							            <DIV class="headRightCurveblk"></DIV>
							        </div>
							    </td>
							</tr>
							<tr>
								<th width="6%">
									<img src="/aims/images/icon_pdf.gif" alt="User Guide" border="0"/>
								</th>
								<th width="94%">
		                           <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=UserGuidePdf&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="BrewApplicationUpdateForm" property="appsId" />" class="a" target="_blank">User Guide</a>
								</th>						
							</tr>          		
		          		</table>
		          	</td>
		          </tr>
	          </logic:notEmpty>                  
	          <tr>
	            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
	                <%@ include  file="include/appEditButtons.jsp" %>
	
					<script	language="javascript">
						disableEvaluationButtons();
					</script>
	
	                <logic:equal name="BrewApplicationUpdateForm" property="aimsLifecyclePhaseId" scope="request"   value="<%=AimsConstants.SUNSET_ID.toString()%>">
	                    <html:hidden property="evaluation"  />
	                </logic:equal> 
	              </table>
	              <%@ include file="include/brewMessageFooter.jsp" %>
	              </td>
	          </tr>
         </table>
      </div>
    </html:form>
  </div>
</div>

