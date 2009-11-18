<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<jsp:useBean id="task" class="java.lang.String"	scope="request"/>
<jsp:useBean id="ApplicationUpdateForm" class="com.netpace.aims.controller.application.ApplicationUpdateForm" scope="request" />

<%ApplicationUpdateForm.setCurrentPage("page5");%>

<script	language="javascript">
<!--

	var	supported	=	(window.Option)	?	1	:	0;

	function SubmitCS()
	{
		document.forms[0].action = 'entApplicationCSAdd.do';
		document.forms[0].submit();
	}

	function SubmitCSRemove()
	{
		document.forms[0].action = 'entApplicationCSAdd.do?cstask=delete';
		document.forms[0].submit();
	}
	
	function truncateLocalTextAreas()
	{
		if (typeof document.forms[0].csProblemStatement!= "undefined")
			if (typeof document.forms[0].csProblemStatement.value != "undefined") 
				TruncateText(document.forms[0].csProblemStatement, 100);
				
		if (typeof document.forms[0].csBusinessBenifit!= "undefined")		
			if (typeof document.forms[0].csBusinessBenifit.value != "undefined") 
				TruncateText(document.forms[0].csBusinessBenifit , 100);		
	}


	<%@ include  file="include/appJScript.jsp" %>

//-->
</script>

<%@ include  file="/common/error.jsp" %>
<div id="contentBox" style="float:left" onmousemove="truncateLocalTextAreas();">
  <div class="homeColumnTab">
	<%@ include  file="include/entAppTabs.jsp" %>
    <div class="contentbox">
      <html:form action="/entApplicationUpdate.do"	enctype="multipart/form-data">
	  	<%@ include  file="include/entAppHidden.jsp" %>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr><td>&nbsp;</td></tr>  
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1><bean:message	key="EntApplicationForm.table.head.case.studies"	 bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td>
                <table width="100%" cellspacing="0" cellpadding="5" class="Grid2"  border="1">
                    <tr>
                        <th align="center"><strong><bean:message key="EntApplicationForm.cs.customerName"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                        <th align="center"><strong><bean:message key="EntApplicationForm.cs.problemStatement"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                        <th align="center"><strong><bean:message key="EntApplicationForm.cs.businessBenifit"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                        <th align="center"><strong><bean:message key="EntApplicationForm.cs.productionLaunchDate"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                        <th align="center"><strong><bean:message key="EntApplicationForm.cs.totalUsers"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                        <th align="center"><strong><bean:message key="EntApplicationForm.cs.totalWirelessUsers"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></strong></th>
                    </tr>
                    <logic:iterate id="caseStudy" name="EntApplicationUpdateForm" property="allCaseStudies" >
                        <tr>
                          <td align="left">
                            <%if ( !((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
                                <input type=checkbox name="csCaseStudyId" value="<bean:write name="caseStudy" property="caseStudyIdHash" />"/>
                            <% } else {}%>
                            <bean:write name="caseStudy" property="customerName" />
                          </td>
                          <td align="left"><bean:write name="caseStudy" property="problemStatement" /></td>
                          <td align="left"><bean:write name="caseStudy" property="businessBenifit" /></td>
                          <td align="center"><bean:write name="caseStudy" property="productionLaunchDate" formatKey="date.format" filter="true"/></td>
                          <td align="center"><bean:write name="caseStudy" property="numUsers" /></td>
                          <td align="center"><bean:write name="caseStudy" property="numWirelessUsers" /></td>
                        </tr>
                    </logic:iterate>
                    <%if ( !((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE) ) {%>
                          <tr>
                            <td align="left" valign="top"><html:text styleClass="inputField" property="csCustomerName" style="input" size="20" maxlength="100"/></td>
                            <td align="center" valign="top"><html:textarea styleClass="textareaField" property="csProblemStatement" onkeyup="LimitText(this,100)" onkeypress="LimitText(this,100)" rows="4" cols="15"></html:textarea></td>
                            <td align="center" valign="top"><html:textarea styleClass="textareaField" property="csBusinessBenifit" onkeyup="LimitText(this,100)" onkeypress="LimitText(this,100)" rows="4" cols="15"></html:textarea></td>
                            <td align="center"><table><tr><td><html:text styleClass="inputField" property="csProductionLaunchDate" style="input" size="12"/></td><td align="left"><img name="daysOfMonthPos" onclick="toggleDatePicker('daysOfMonth','EntApplicationUpdateForm.csProductionLaunchDate')" id="daysOfMonthPos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth"/></td></tr></table></td>
                            <td align="center" valign="top"><html:text styleClass="inputField" property="csTotalEndUsers" style="input" size="5"/></td>
                            <td align="center" valign="top"><html:text styleClass="inputField" property="csNoOfUsersAccess" style="input" size="5"/></td>
                          </tr>
                          <tr>
                                <td class="text" align="left" colspan="6"><a class="a" href="javascript:SubmitCS();//">Add New</a> - <a class="a" href="javascript:SubmitCSRemove();//">Remove Selected</a>
                                </td>
                            <tr>
                    <% } else {}%>
                </table>
            </td>
          </tr>
          <tr>
            <td width="100%">
				<table width="100%" cellpadding="0" cellspacing="0" border="0">
					<tr><td><%@ include  file="include/entAppEditButtons.jsp" %></td></tr>
				</table>
			</td>
          </tr>
        </table>
      </html:form>
    </div>
  </div>
</div>

