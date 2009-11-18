<%@ page language="java"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<script language="javascript"></script>

<%@ include file="/common/error.jsp"%>

<div id="contentBox" style="float: left">
	<div class="homeColumnTab lBox">
		<html:form action="/batchContentDeliverySettlement" method="post">
			<html:hidden property="task" value="update"/>
			<DIV class="headLeftCurveblk"></DIV>
			<H1>Batch Update</H1>
			<DIV class="headRightCurveblk"></DIV>

			<DIV class="contentbox">
				<table width="100%" border="0" cellspacing="" cellpadding="5" class="GridGradient">
					<tr>
	                    <th colspan="2" width="100%">
	                        <strong>Please select time duration and service type</strong>:                        
	                    </th>
                    </tr>
                    <tr>
						<td width="5%" nowrap>
							<strong>Start Date&nbsp;<span class="requiredText">*</span>:</strong>&nbsp;
						</td>
                        <td>                        	
                        	<html:text styleClass="inputField" property="startDate" size="15" maxlength="10"/>
                      		<img name="daysOfMonth1Pos" onclick="toggleDatePicker('daysOfMonth1','BatchContentDeliverySettlementForm.startDate')" id="daysOfMonth1Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth1"/>
                       	</td>
                    </tr>
                    <tr>
						<td width="5%" nowrap>
							<strong>End Date&nbsp;<span class="requiredText">*</span>:</strong>&nbsp;
						</td>
                        <td>                        	
                        	<html:text styleClass="inputField" property="endDate" size="15" maxlength="10"/>
                      		<img name="daysOfMonth2Pos" onclick="toggleDatePicker('daysOfMonth2','BatchContentDeliverySettlementForm.endDate')" id="daysOfMonth2Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth2"/>                        	
                       	</td>
                    </tr>
                    <tr>
						<td width="5%" nowrap>
							<strong>Service Type&nbsp;<span class="requiredText">*</span>:</strong>&nbsp;
						</td>
                        <td>
                        	<html:radio property="serviceType" value="CD"/>Content Delivery (VDS)&nbsp;
						    <html:radio property="serviceType" value="S"/>Settlement Service (Amdocs)                        	
                        </td>
                    </tr>
					<tr>
						<td colspan="2" align="right">
                            <div class="divButtons" style="float:right;">                                
                                <div class="redBtn" style="float: left;" id="btnSubmit" title="Submit">
                                    <div><div><div onclick="document.forms[0].submit()">Submit</div></div></div>
                                </div>
                            </div>
                        </td>
					</tr>
				</table>
			</div>
		</html:form>
	</div>

<logic:present name="bulkDevelopersLog" scope="request">
	<textarea cols="95" rows="10" readonly="true">Total calls to VDS for Developer Create: <bean:write name="bulkDevelopersLog" property="total" />
Success: <bean:write name="bulkDevelopersLog" property="success"/>
Failure: <bean:write name="bulkDevelopersLog" property="fail"/>

Success Ids: <logic:notEmpty
	 name="bulkDevelopersLog" property="successIds"><logic:iterate
	 id="log" name="bulkDevelopersLog" property="successIds"><bean:write
	 name="log" />, </logic:iterate></logic:notEmpty><logic:empty
	 name="bulkDevelopersLog" property="successIds">none</logic:empty>

Failure Ids: <logic:notEmpty
	 name="bulkDevelopersLog" property="failureIds"><logic:iterate
	 id="log" name="bulkDevelopersLog" property="failureIds">
<bean:write name="log" />
</logic:iterate></logic:notEmpty><logic:empty name="bulkDevelopersLog" property="failureIds">none</logic:empty>
</textarea>
</logic:present>
<logic:present name="partnerOnBoardingLog" scope="request">
	<textarea cols="95" rows="10" readonly="true">Total calls to Amdocs for Partner On boarding: <bean:write name="partnerOnBoardingLog" property="total" />
Success: <bean:write name="partnerOnBoardingLog" property="success"/>
Failure: <bean:write name="partnerOnBoardingLog" property="fail"/>

Success Ids: <logic:notEmpty
	 name="partnerOnBoardingLog" property="successIds"><logic:iterate
	 id="log" name="partnerOnBoardingLog" property="successIds"><bean:write
	 name="log" />, </logic:iterate></logic:notEmpty><logic:empty
	 name="partnerOnBoardingLog" property="successIds">none</logic:empty>

Failure Ids: <logic:notEmpty
	 name="partnerOnBoardingLog" property="failureIds"><logic:iterate
	 id="log" name="partnerOnBoardingLog" property="failureIds">
<bean:write name="log" /></logic:iterate></logic:notEmpty><logic:empty 
name="partnerOnBoardingLog" property="failureIds">none</logic:empty>
</textarea> <br/> <br/> <br/>
</logic:present>
<logic:present name="offerCreationLog" scope="request">
	
	
	
	<textarea cols="95" rows="10" readonly="true">Total calls to Amdocs for Offer Creation: <bean:write name="offerCreationLog" property="total" />
Success: <bean:write name="offerCreationLog" property="success"/>
Failure: <bean:write name="offerCreationLog" property="fail"/>

Success Ids: <logic:notEmpty
	 name="offerCreationLog" property="successIds"><logic:iterate
	 id="log" name="offerCreationLog" property="successIds"><bean:write
	 name="log" />, </logic:iterate></logic:notEmpty><logic:empty
	 name="offerCreationLog" property="successIds">none</logic:empty>

Failure Ids: <logic:notEmpty
	 name="offerCreationLog" property="failureIds"><logic:iterate
	 id="log" name="offerCreationLog" property="failureIds">
<bean:write name="log" /></logic:iterate></logic:notEmpty><logic:empty 
name="offerCreationLog" property="failureIds">none</logic:empty>
</textarea>
</logic:present>
</div>