<%@	taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld" prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aims.tld" prefix="aims"%>

<%@ include  file="/common/error.jsp" %>


			<html:form action="/amendmentsEdit" enctype="multipart/form-data">
            <html:hidden  property="amendmentDocumentFileName"  />
            <logic:equal parameter="task" value="createForm">
               <html:hidden  property="task" value="create" /> 
            </logic:equal>
            <logic:equal parameter="task" value="editForm">
               <html:hidden  property="task" value="edit" />
               <html:hidden  property="amendmentId"  /> 
            </logic:equal>

<div id="contentBox" style="float:left">
<DIV class="homeColumnTab lBox">

    <DIV class="headLeftCurveblk"></DIV>
    <H1>Amendment Details</H1>
    <DIV class="headRightCurveblk"></DIV>

    <DIV class="contentbox">
       <table width="100%" cellpadding="5" cellspacing="0" class="GridGradient">
        <tr>
            <th  width="50%"><strong>Amendment Name <span class="requiredText">*</span>:</strong>
                <br/>
                <html:text	property="amendmentTitle" size="35" maxlength="50" styleClass="inputField"/>
            </th>
            <th  width="50%"><strong>Amendment Version <span class="requiredText">*</span>:</strong>
                <br/>
                <html:text	property="amendmentVersion" size="35" maxlength="50" styleClass="inputField" />
            </th>
        </tr>
        <tr>
            <td >
                <strong>Amendment Status <span class="requiredText">*</span>:</strong><br/>
                <html:select  property="amendmentStatus" size="1" styleClass="selectField">
                    <html:option value="A">Active</html:option>
                    <html:option value="E">Expired</html:option>
                    <html:option value="H">On Hold</html:option>
                </html:select>
            </td>
            <td >
                <strong>Amendment Document <span class="requiredText">*</span>:</strong><br/>
                <html:file property="amendmentDocument" styleClass="inputField"/>
                <br/>                              <!--/aims/resourceContractAction.do?resource=document&object=AimsAllianc&resourceId=-->
                <a class="a" target="_blank" href='/aims/amendmentsResourceAction.do?resource=document&object=Amendtment&resourceId=<bean:write name="AmendmentForm" property="amendmentId"/>'>
                    <bean:write	name="AmendmentForm" property="amendmentDocumentFileName"/>
                </a>
            </td>
        </tr>
        <tr>
            <td ><strong>Amendment Expiry Date <span class="requiredText">*</span>:</strong><br/>
                <html:text	property="amendmentExpiryDate" size="35" maxlength="20" styleClass="inputField"/>
                <img name="daysOfMonthPos" onclick="toggleDatePicker('daysOfMonth','AmendmentForm.amendmentExpiryDate')" id="daysOfMonthPos" <bean:message key="images.calendar.button.lite"/> />
                <div style="position:absolute;" id="daysOfMonth"/>
            </td>
            <td >&nbsp;</td>
        </tr>        
        <tr>
            <td ><strong>Comments</strong><br/>
                <html:textarea	property="comments" rows="5" cols="30"  styleClass="textareaField" />
            </td>
            <td >&nbsp;</td>
        </tr>        
    </table>
    </DIV>

    <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
        <td align="right">
            <%--<logic:notEqual parameter="task" value="createForm">--%>
            <logic:notEqual parameter="task" value="editForm">
                <div class="redBtn" style="float:right;" id="Create" title="Create">
                    <div>
                        <div>
                            <div onclick="document.forms[0].submit();">Create</div>
                        </div>
                    </div>
                </div>
            </logic:notEqual>
            <logic:equal parameter="task" value="editForm">
                <!--<input type="image" src="images/save_b.gif" border="0">-->
                    <div class="redBtn" style="float:right;" id="Save" title="Save">
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

</html:form>