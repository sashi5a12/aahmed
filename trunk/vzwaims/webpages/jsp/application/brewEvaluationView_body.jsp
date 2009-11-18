<%@	page language="java" import="com.netpace.aims.bo.security.*, com.netpace.aims.bo.application.*, com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>
<script type="text/javascript">
function collapseRow(sectionRow) {
    document.getElementById("row"+sectionRow).style.display='none';
    document.getElementById("spnExpandCollapse"+sectionRow).innerHTML="<a class='a' onclick='javascript:expandRow(\""+sectionRow+"\");'>[+]</a>";
    return false;
}

function expandRow(sectionRow) {
    document.getElementById("row"+sectionRow).style.display='';
    document.getElementById("spnExpandCollapse"+sectionRow).innerHTML="<a class='a' onclick='javascript:collapseRow(\""+sectionRow+"\");'>[-]</a>";
    return false;
}
</script>

<jsp:useBean id="task" class="java.lang.String"	scope="request"/>
<jsp:useBean id="BrewApplicationUpdateForm" class="com.netpace.aims.controller.application.BrewApplicationUpdateForm" scope="request" />
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" />

<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/appViewTabs.jsp" %>
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
      <%@ include  file="include/appViewHidden.jsp" %>
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
                    <td width="25%"><bean:write name="BrewApplicationUpdateForm" property="developerName"/>&nbsp;</td>
                    <td width="25%"><bean:write name="BrewApplicationUpdateForm" property="publisherName"/>&nbsp;</td>
                    <td width="25%"><bean:write name="BrewApplicationUpdateForm" property="submittedDate"/>&nbsp;</td>
                    <td width="25%"><bean:write name="BrewApplicationUpdateForm" property="title"/>&nbsp;</td>
                </tr>                
                <th class="left" width="25%"><bean:message key="BrewApplicationForm.sellingPoints"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                <th class="left" width="25%"><bean:message key="ManageApplicationForm.appShortDesc"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                <th class="left" width="25%"><bean:message key="ManageApplicationForm.appLongDesc"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                <th class="left" width="25%">&nbsp;</th>
                <tr>                    
                    <td style="vertical-align:top;"><html:textarea styleClass="textareaFieldNoPad"	property="sellingPoints" rows="14"	cols="28" readonly="true"/></td>
                    <td style="vertical-align:top;"><html:textarea styleClass="textareaFieldNoPad"	property="shortDesc" rows="14"	cols="28" readonly="true"/></td>
                    <td style="vertical-align:top;"><html:textarea styleClass="textareaFieldNoPad"	property="longDesc" rows="14"	cols="28" readonly="true"/></td>
                    <td style="vertical-align:top;">&nbsp;</td>
                </tr>
                <tr>
                    <th class="left" width="25%"><bean:message key="BrewApplicationForm.plannedDevStartDate"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left" width="25%"><bean:message key="BrewApplicationForm.plannedEntryIntoNstl"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left" width="25%"><bean:message key="ApplicationForm.brewPlannedCompletionByNstl"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left" width="25%"><bean:message key="ApplicationForm.brewPlannedEntryIntoVzw"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                </tr>
                <tr>
                    <td><bean:write name="BrewApplicationUpdateForm" property="plannedDevStartDate"/>&nbsp;</td>
                    <td><bean:write name="BrewApplicationUpdateForm" property="plannedEntryIntoNstl"/>&nbsp;</td>
                    <td><bean:write name="BrewApplicationUpdateForm" property="plannedCompletionByNstl"/>&nbsp;</td>
                    <td><bean:write name="BrewApplicationUpdateForm" property="plannedEntryIntoVzw"/>&nbsp;</td>
                </tr>
                <tr>
                    <th class="left"><bean:message key="ApplicationForm.brewPlannedCompletionByVzw"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left"><bean:message key="BrewApplicationForm.typeOfApplication"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left">&nbsp;</th>
                    <th class="left">&nbsp;</th>
                </tr>
                <tr>
                    <td><bean:write name="BrewApplicationUpdateForm" property="plannedCompletionByVzw"/>&nbsp;</td>
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
                        <html:textarea styleClass="textareaFieldNoPad" 	property="anticipatedDaps" rows="14"	cols="28" readonly="true"/>
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
                        <logic:equal name="BrewApplicationUpdateForm" property="networkUse" value="S">
                            <bean:message key="ManageApplicationForm.radio.label.againstServer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>
                        <logic:equal name="BrewApplicationUpdateForm" property="networkUse" value="P">
                            <bean:message key="ManageApplicationForm.radio.label.againstPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>
                        <logic:equal name="BrewApplicationUpdateForm" property="networkUse" value="N">
                            <bean:message key="ManageApplicationForm.radio.label.noNetworkUsage"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>&nbsp;
                    </td>
                    <td style="vertical-align:top;">
                        <logic:equal name="BrewApplicationUpdateForm" property="singleMultiPlayer" value="S">
                            <bean:message key="ManageApplicationForm.radio.label.singlePlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>
                        <logic:equal name="BrewApplicationUpdateForm" property="singleMultiPlayer" value="M">
                            <bean:message key="ManageApplicationForm.radio.label.multiPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>
                        <logic:equal name="BrewApplicationUpdateForm" property="singleMultiPlayer" value="B">
                            <bean:message key="ManageApplicationForm.radio.label.bothPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>
                        <logic:equal name="BrewApplicationUpdateForm" property="singleMultiPlayer" value="N">
                            <bean:message key="ManageApplicationForm.radio.label.noPlayer"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>&nbsp;
                    </td>
                </tr>
                <tr>
                    <th class="left"><bean:message key="ApplicationForm.prizes"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                    <th class="left"><bean:message key="ManageApplicationForm.contentRating"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                </tr>
                <tr>
                    <td style="vertical-align:top;">
                        <logic:equal name="BrewApplicationUpdateForm" property="prizes" value="Y">
                            <bean:message key="ManageApplicationForm.radio.label.yes"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>
                        <logic:equal name="BrewApplicationUpdateForm" property="prizes" value="N">
                            <bean:message key="ManageApplicationForm.radio.label.no"     bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>&nbsp;
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
                        <logic:notEmpty name="BrewApplicationUpdateForm"	property="deckPlacement">
                            <logic:iterate id="formDeckList" name="BrewApplicationUpdateForm" property="deckList" scope="request">
                                <logic:equal name="formDeckList" property="deckId" value="<%=BrewApplicationUpdateForm.getDeckPlacement()%>">
                                    <bean:write name="formDeckList" property="deckName" />
                                </logic:equal>
                            </logic:iterate>
                        </logic:notEmpty>&nbsp;
                    </td>
                    <td><bean:write name="BrewApplicationUpdateForm" property="deckLaunchDate"/>&nbsp;</td>
                </tr>
                <tr>
                    <th colspan="2" class="left"><bean:message key="ApplicationForm.brewEvaluation"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                </tr>
                <tr>
                    <td colspan="2">
                        <logic:equal name="BrewApplicationUpdateForm" property="evaluation" value="F">
                            <bean:message key="ManageApplicationForm.radio.label.featured" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>
                        <logic:equal name="BrewApplicationUpdateForm" property="evaluation" value="G">
                            <bean:message key="ManageApplicationForm.radio.label.general" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>
                        <logic:equal name="BrewApplicationUpdateForm" property="evaluation" value="N">
                            <bean:message key="ManageApplicationForm.radio.label.notAccepted" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>
                        <logic:equal name="BrewApplicationUpdateForm" property="evaluation" value="S">
                            <bean:message key="ManageApplicationForm.radio.label.sunset" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>
                        <logic:equal name="BrewApplicationUpdateForm" property="evaluation" value="A">
                            <bean:message key="ManageApplicationForm.radio.label.conceptAccepted" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>
                        <logic:equal name="BrewApplicationUpdateForm" property="evaluation" value="R">
                            <bean:message key="ManageApplicationForm.radio.label.conceptRejected" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </logic:equal>&nbsp;
                    </td>
                </tr>
                <tr>
                    <th colspan="4" class="left"><bean:message key="BrewApplicationForm.screenShots"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></th>
                </tr>
                <tr>
                    <td colspan="4">
                        <a target="_blank" href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_type=<bean:message key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />"><img src="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_type=<bean:message key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />" border="1" height="150" width="100"/></a>&nbsp;
                        <a target="_blank" href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_type=<bean:message key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />"><img src="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg2&app_type=<bean:message    key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />" border="1" height="150" width="100"/></a>&nbsp;
                        <a target="_blank" href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_type=<bean:message key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />"><img src="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg3&app_type=<bean:message    key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />" border="1" height="150" width="100"/></a>&nbsp;
                        <a target="_blank" href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_type=<bean:message key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />"><img src="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg4&app_type=<bean:message    key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />" border="1" height="150" width="100"/></a>&nbsp;
                        <a target="_blank" href="<bean:message key="ManageApplicationForm.manage.app.resource.url"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_type=<bean:message key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />"><img src="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg5&app_type=<bean:message    key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write name="BrewApplicationUpdateForm" property="appsId" />" border="1" height="150" width="100"/></a>
                    </td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <%@ include  file="include/appViewButtons.jsp" %>
              </table></td>
          </tr>
        </table>
      </div>
    </html:form>
  </div>
</div>

