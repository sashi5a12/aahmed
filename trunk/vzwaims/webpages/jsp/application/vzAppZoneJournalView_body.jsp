<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<jsp:useBean id="task" class="java.lang.String" scope="request"/>

<jsp:useBean id="VZAppZoneApplicationUpdateForm" class="com.netpace.aims.controller.application.VZAppZoneApplicationUpdateForm" scope="request" />
<%VZAppZoneApplicationUpdateForm.setCurrentPage("vzAppZoneJournalView");%>
<%VZAppZoneApplicationUpdateForm.setJournalType("PR");%>
<%VZAppZoneApplicationUpdateForm.setJournalText("");%>
<%@ include  file="include/vzAppZoneVariables.jsp" %>


<%@ include  file="include/vzAppZoneAppJScript.jsp" %>

<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/vzAppZoneViewTabs.jsp" %>
    <div>&nbsp;</div>  
    <html:form action="/vzAppZoneApplicationUpdate.do"	enctype="multipart/form-data">
      <div class="contentbox">
        <%@ include  file="include/vzAppZoneHidden.jsp" %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message	key="JournalForm.currentEntries"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                <tr>
                  <th>
					  <html:textarea styleClass="textareaField"	property="journalCombinedText" readonly="true" rows="20" cols="90"></html:textarea>
                  </th>
                </tr>
                <%if (isVerizonUser) {%>
                    <tr>
                        <td>
                            <strong><bean:message	key="JournalForm.type"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br/>
                            <html:radio	property="journalType" value="PU"/><bean:message key="ManageApplicationForm.radio.label.public"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;
                            <html:radio	property="journalType" value="PR"/><bean:message key="ManageApplicationForm.radio.label.private"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>
                        </td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                    <tr>
                        <td>
                            <strong><bean:message	key="JournalForm.newEntry"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&nbsp;<span class="requiredText">*</span>:</strong><br	/>
                            <html:textarea styleClass="textareaField"	property="journalText" onkeyup="TruncateText(this,2000)" onkeypress="TruncateText(this,2000)" rows="4" cols="90"></html:textarea>
                        </td>
                    </tr>
                <% }//end ifVerizonUser %>
            </table></td>
          </tr>
          <%if (isVerizonUser && hasAccessJournalSubmit) {%>
            <tr>
                <td	height="25"	align="right" valign="middle">
                    <div id="divButtons">
                        <div class="redBtn" style="float:right; margin-left:10px; margin-top:3px" id="SubmitAll" title="Submit">
                            <div><div><div onClick="document.forms[0].appSubmitType.value='paging';document.forms[0].task.value='submitVZAppZoneJournalView';showProcessingInfo();document.forms[0].submit();">Submit</div></div></div>
                        </div>
                    </div>
                </td>
            </tr>
          <% }//end ifVerizonUser %>
        </table>
      </div>
    </html:form>
  </div>
</div>

<script	language="javascript">
    if(document.forms[0] && document.forms[0].journalText) {
        document.forms[0].journalText.focus();
    }
</script>
