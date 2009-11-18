<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.application.*,com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<jsp:useBean id="VcastApplicationUpdateForm" class="com.netpace.aims.controller.application.VcastApplicationUpdateForm" scope="request" />
<%VcastApplicationUpdateForm.setCurrentPage("page1");%>
<%@ include  file="include/vcastAppVariables.jsp" %>

<script	language="javascript">
	<%@ include  file="include/vcastAppJScript.jsp" %>
</script>

<%@ include  file="/common/error.jsp" %>

<div id="contentBox" style="float:left">
  <DIV class="homeColumnTab ">
    <%@ include  file="include/vcastAppViewTabs.jsp" %>
    <html:form action="/vcastApplicationUpdate.do"    enctype="multipart/form-data">
      <div class="contentbox">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Clip Details</H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                <tr>
                    <th width="50%">
                        <strong>Clip Title:</strong>
                        <br/>
                        <span style="font-weight:normal"><bean:write name="VcastApplicationUpdateForm" property="title"/>&nbsp;</span>
                    </th>
                    <th width="50%">
                        <strong>Language:</strong>
                        <br/>
                        <span style="font-weight:normal">
                            <logic:notEmpty name="VcastApplicationUpdateForm" property="language">
                                <logic:iterate id="formLanguages" name="VcastApplicationUpdateForm" property="allLanguages" scope="request">
                                    <logic:equal name="formLanguages" property="languageId" value="<%=VcastApplicationUpdateForm.getLanguage().toString()%>">
                                        <bean:write name="formLanguages" property="languageName" />
                                    </logic:equal>
                                </logic:iterate>
                            </logic:notEmpty>&nbsp;</span>
                    </th>
                </tr>
                <tr>
                    <td width="50%"><strong>Clip Short Description:</strong></td>
                    <td width="50%"><strong>Clip Long Description:</strong></td>
                </tr>
                <tr>
                    <td class="viewText"><html:textarea styleClass="textareaField" property="shortDesc" rows="3"   cols="50" readonly="true"></html:textarea></td>
                    <td class="viewText"><html:textarea styleClass="textareaField" property="longDesc" rows="3"    cols="50" readonly="true"></html:textarea></td>
                </tr>
                <tr>
                    <td width="50%"><strong>Frequency of Clip Updates:</strong></td>
                    <td width="50%">&nbsp;</td>
                </tr>
                <tr>
                    <td class="viewText">
                        <logic:notEmpty name="VcastApplicationUpdateForm" property="updateFrequency">
                            <logic:iterate id="formFrequencies" name="VcastApplicationUpdateForm" property="allFrequencies" scope="request">
                                <logic:equal name="formFrequencies" property="frequencyId" value="<%=VcastApplicationUpdateForm.getUpdateFrequency().toString()%>">
                                    <bean:write name="formFrequencies" property="frequencyName" />
                                </logic:equal>
                            </logic:iterate>
                        </logic:notEmpty>
                        &nbsp;
                    </td>
                    <td>&nbsp;</td>
                </tr>
              </table></td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Clip Classification</H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
          <td><table width="100%" border="1" cellspacing="0" cellpadding="0" class="GridGradient">
              <tr>
                  <th width="50%">
                      <strong>Clip Category:</strong>
                      <hr/>
                      <span style="font-weight:normal">
                          <logic:notEmpty name="VcastApplicationUpdateForm" property="subCategoryId">
                              <logic:iterate id="formSubCategories" name="VcastApplicationUpdateForm" property="allSubCategories" scope="request">
                                  <logic:equal name="formSubCategories" property="subCategoryId" value="<%=VcastApplicationUpdateForm.getSubCategoryId().toString()%>">
                                      <bean:write name="formSubCategories" property="subCategoryName" />
                                  </logic:equal>
                              </logic:iterate>
                          </logic:notEmpty>&nbsp;                          
                      </span>
                  </th>
              </tr>
            </table></td>
          </tr>
          <tr><td>&nbsp;</td></tr>          
          <tr>
            <td width="100%"><table width="100%" cellspacing="0" cellpadding="1">
                <tr>
                  <td width="50%" valign="top"><table width="100%"  cellspacing="0" cellpadding="1">
                      <tr>
                        <td><table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1>Clip Files</H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong>Sample Video Clips:</strong>
                                    <br/>
                                    <logic:notEmpty name="VcastApplicationUpdateForm" property="sampleClip1FileName">
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VcastSampleClip1&app_id=<bean:write   name="VcastApplicationUpdateForm"   property="appsId"   />" class="a"   target="_blank">
                                            <bean:write    name="VcastApplicationUpdateForm"   property="sampleClip1FileName"/>
                                        </a>
                                    </logic:notEmpty>
                                    <logic:notEmpty name="VcastApplicationUpdateForm" property="sampleClip2FileName">
                                        <br/><br/>
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VcastSampleClip2&app_id=<bean:write   name="VcastApplicationUpdateForm"   property="appsId"   />" class="a"   target="_blank">
                                            <bean:write name="VcastApplicationUpdateForm" property="sampleClip2FileName"/>
                                        </a>
                                    </logic:notEmpty>
                                    <logic:notEmpty name="VcastApplicationUpdateForm" property="sampleClip3FileName">
                                        <br/><br/>
                                        <a href="<bean:message key="ManageApplicationForm.manage.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VcastSampleClip3&app_id=<bean:write   name="VcastApplicationUpdateForm"   property="appsId"   />" class="a"   target="_blank">
                                            <bean:write name="VcastApplicationUpdateForm" property="sampleClip3FileName"/>
                                        </a>
                                    </logic:notEmpty>
                                    &nbsp;
                                </th>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1>Operations Contact</H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <logic:notEmpty name="VcastApplicationUpdateForm" property="aimsContactId" scope="request">
                                <logic:iterate id="formContacts" name="VcastApplicationUpdateForm" property="allContacts" scope="request">
                                    <logic:equal name="formContacts" property="contactId" value="<%=VcastApplicationUpdateForm.getAimsContactId().toString()%>">
                                        <tr>
                                            <th>
                                                <strong><bean:message key="ManageApplicationForm.appContactFirstName" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong>
                                                <br/>
                                                <span style="font-weight:normal;"><bean:write name="formContacts" property="firstName" /></span>
                                            </th>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message key="ManageApplicationForm.appContactLastName"  bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td><bean:write name="formContacts" property="lastName" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message key="ManageApplicationForm.appContactTitle" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td><bean:write name="formContacts" property="title" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message key="ManageApplicationForm.appContactEmail" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td><bean:write name="formContacts" property="emailAddress" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message key="ManageApplicationForm.appContactTelephone" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td><bean:write name="formContacts" property="phone" /></td>
                                        </tr>
                                        <tr>
                                            <td><strong><bean:message key="ManageApplicationForm.appContactMobile"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>:</strong></td>
                                        </tr>
                                        <tr>
                                            <td><bean:write name="formContacts" property="mobile" /></td>
                                        </tr>
                                    </logic:equal>
                                </logic:iterate>
                            </logic:notEmpty>
                            <tr><td>&nbsp;</td></tr>
                          </table></td>
                      </tr>
                    </table></td>
                  <td width="50%" valign="top"><table width="100%"  cellspacing="0" cellpadding="1">
                      <tr>
                        <td><table width="375" height="100%" class="GridGradient" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="noPad"><div class="mmBox">
                                  <DIV class="headLeftCurveblk"></DIV>
                                  <H1>Target Audience</H1>
                                  <DIV class="headRightCurveblk"></DIV>
                                </div></td>
                            </tr>
                            <tr>
                                <th>
                                    <strong>Gender:</strong>
                                    <br/>
                                    <logic:notEmpty name="VcastApplicationUpdateForm" property="listSelectedAudGenders" scope="request">
                                        <logic:iterate id="genders" name="VcastApplicationUpdateForm" property="allAudGenders">
                                            <%for (int i=0; i<VcastApplicationUpdateForm.getListSelectedAudGenders().length; i++) {%>
                                                <logic:equal name="genders" property="audGenderId" value="<%=(VcastApplicationUpdateForm.getListSelectedAudGenders())[i].toString()%>">
                                                    <span style="font-weight:normal"><bean:write name="genders" property="audGenderName" /></span><br/>
                                                </logic:equal>
                                            <% } %>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                    &nbsp;
                                </th>
                            </tr>
                            <tr>
                                <td><strong>Age Group:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="VcastApplicationUpdateForm" property="listSelectedAudAges" scope="request">
                                        <logic:iterate id="ages" name="VcastApplicationUpdateForm" property="allAudAges">
                                            <%for (int i=0; i<VcastApplicationUpdateForm.getListSelectedAudAges().length; i++) {%>
                                                <logic:equal name="ages" property="audAgeId" value="<%=(VcastApplicationUpdateForm.getListSelectedAudAges())[i].toString()%>">
                                                    <bean:write name="ages" property="audAgeName" /><br/>
                                                </logic:equal>
                                            <% } %>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Race:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="VcastApplicationUpdateForm" property="listSelectedAudRaces" scope="request">
                                        <logic:iterate id="races" name="VcastApplicationUpdateForm" property="allAudRaces">
                                            <%for (int i=0; i<VcastApplicationUpdateForm.getListSelectedAudRaces().length; i++) {%>
                                                <logic:equal name="races" property="audRaceId" value="<%=(VcastApplicationUpdateForm.getListSelectedAudRaces())[i].toString()%>">
                                                    <bean:write name="races" property="audRaceName" /><br/>
                                                </logic:equal>
                                            <% } %>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Household Income:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="VcastApplicationUpdateForm" property="listSelectedAudIncomes" scope="request">
                                        <logic:iterate id="incomes" name="VcastApplicationUpdateForm" property="allAudIncomes">
                                            <%for (int i=0; i<VcastApplicationUpdateForm.getListSelectedAudIncomes().length; i++) {%>
                                                <logic:equal name="incomes" property="audIncomeId" value="<%=(VcastApplicationUpdateForm.getListSelectedAudIncomes())[i].toString()%>">
                                                    <bean:write name="incomes" property="audIncomeName" /><br/>
                                                </logic:equal>
                                            <% } %>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Education:</strong></td>
                            </tr>
                            <tr>
                                <td class="viewText">
                                    <logic:notEmpty name="VcastApplicationUpdateForm" property="listSelectedAudEducations" scope="request">
                                        <logic:iterate id="educations" name="VcastApplicationUpdateForm" property="allAudEducations">
                                            <%for (int i=0; i<VcastApplicationUpdateForm.getListSelectedAudEducations().length; i++) {%>
                                                <logic:equal name="educations" property="audEducationId" value="<%=(VcastApplicationUpdateForm.getListSelectedAudEducations())[i].toString()%>">
                                                    <bean:write name="educations" property="audEducationName" /><br/>
                                                </logic:equal>
                                            <% } %>
                                        </logic:iterate>
                                    </logic:notEmpty>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>                                                        
                          </table></td>
                      </tr>
                    </table></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td><div class="lBox">
                <DIV class="headLeftCurveblk"></DIV>
                <H1>Evaluation Comments</H1>
                <DIV class="headRightCurveblk"></DIV>
              </div></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                <tr>
                    <th width="100%">
                        <html:textarea  styleClass="textareaField" property="evaluationComments" rows="4"   cols="50" readonly="true"></html:textarea>
                    </th>
                </tr>
              </table></td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr>
            <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                <%@ include  file="include/vcastAppViewButtons.jsp" %>
              </table></td>
          </tr>
        </table>
      </div>
    </html:form>
  </div>
</div>
