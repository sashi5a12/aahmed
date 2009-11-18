<%@ page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.model.core.*,com.netpace.aims.bo.newmarketing.*,com.netpace.aims.controller.newmarketing.*,com.netpace.aims.controller.application.*,com.netpace.aims.util.*" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"   prefix="bean"   %>
<%@ taglib uri="/WEB-INF/struts-html.tld"   prefix="html"   %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"   %>
<%@ taglib uri="/WEB-INF/struts-template.tld"   prefix="template"   %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<jsp:useBean id="MarketingContentUpdateForm" class="com.netpace.aims.controller.newmarketing.MarketingContentUpdateForm" scope="request" />

<%
boolean isVerizonUser = false, isAllianceUser = false;

isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE);
isAllianceUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE);
%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
       <td width="20">&nbsp;</td>
       <td width="100%">
        <span class="aimsmasterheading">
            View Marketing Content
        </span>               
       </td>
  </tr>
  <%@ include  file="/common/error.jsp" %>
  <tr> 
      <td width="20">&nbsp;</td>
      <td align="center" valign="middle"> 
            <html:form action="/mktContentUpdate.do">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr><td width="100%">
                    <table class="sectable" width="100%">
                        <tr class="sectitle"><td colspan="2" class="aimssecheading">Content Management - Content Detail </td></tr>
                        <tr>
                            <td class="modFormFieldLbl" width="50%">
                                Content Title:
                            </td>
                            <td class="modFormFieldLbl" width="50%">
                                Application Title:
                            </td>
                        </tr>
                        <tr>
                            <td class="text" valign="top">
                                <bean:write name="MarketingContentUpdateForm" property="contentTitle"/>
                            </td>
                            <td class="text">
                                <bean:write name="MarketingContentUpdateForm" property="applicationTitle"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="modFormFieldLbl" width="50%">
                                Content Description:
                            </td>
                            <td class="modFormFieldLbl" width="50%">
                                Current Status:
                            </td>
                        </tr>
                        <tr>
                            <td class="text" valign="top"> 
                                <html:textarea property="contentDescription" rows="4" cols="30" readonly="true"></html:textarea>                                
                            </td>
                            <td class="text" valign="top">
                                 <bean:write name="MarketingContentUpdateForm" property="status"/>
                            </td>
                        </tr>
                    </table>    
                </td></tr>
                <tr><td width="100%">&nbsp;</td></tr>
                <tr><td width="100%">
                    <table class="sectable" width="100%">
                        <tr class="sectitle"><td colspan="2" class="aimssecheading">Verizon Wireless Evaluation</td></tr>
                        <tr>
                            <td class="modFormFieldLbl" width="50%">
                                Evaluation Comments:
                            </td>
                            <td class="modFormFieldLbl" width="50%">
                                Reviewed Date:
                            </td>
                        </tr>
                        <tr>
                            <td class="text" valign="top" rowspan="3"> 
                                <html:textarea property="evaluationComments" rows="4" cols="30" readonly="true"></html:textarea>                                
                            </td>
                            <td class="text" valign="top">
                                <bean:write name="MarketingContentUpdateForm" property="approvalDate"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="modFormFieldLbl" width="50%" valign="bottom">
                                Expiry Date:
                            </td>
                        </tr>
                        <tr>
                            <td class="text" valign="top" valign="bottom">
                                <bean:write name="MarketingContentUpdateForm" property="expiryDate"/>
                            </td>
                        </tr>                        
                    </table>    
                </td></tr>
                <tr><td width="100%">&nbsp;</td></tr>
                <tr><td width="100%">
                    <table class="sectable" width="100%">
                        <tr class="sectitle"><td colspan="2" class="aimssecheading">Screen Shots of Content</td></tr>
                        <tr>
                            <td class="modFormFieldLbl">
                                <a target="_blank" href="/aims/marketingContentResource.do?content_res=ScreenJpeg1&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />"><img src="/aims/marketingContentResource.do?content_res=ScreenJpeg1&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" alt="<bean:write name="MarketingContentUpdateForm" property="screenJpeg1FileName"/>" border="1" height="150" width="100"/></a>&nbsp;
                                <a target="_blank" href="/aims/marketingContentResource.do?content_res=ScreenJpeg2&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />"><img src="/aims/marketingContentResource.do?content_res=ScreenJpeg2&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" alt="<bean:write name="MarketingContentUpdateForm" property="screenJpeg2FileName"/>" border="1" height="150" width="100"/></a>&nbsp;
                                <a target="_blank" href="/aims/marketingContentResource.do?content_res=ScreenJpeg3&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />"><img src="/aims/marketingContentResource.do?content_res=ScreenJpeg3&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" alt="<bean:write name="MarketingContentUpdateForm" property="screenJpeg3FileName"/>" border="1" height="150" width="100"/></a>&nbsp;
                                <a target="_blank" href="/aims/marketingContentResource.do?content_res=ScreenJpeg4&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />"><img src="/aims/marketingContentResource.do?content_res=ScreenJpeg4&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" alt="<bean:write name="MarketingContentUpdateForm" property="screenJpeg4FileName"/>" border="1" height="150" width="100"/></a>&nbsp;
                                <a target="_blank" href="/aims/marketingContentResource.do?content_res=ScreenJpeg5&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />"><img src="/aims/marketingContentResource.do?content_res=ScreenJpeg5&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" alt="<bean:write name="MarketingContentUpdateForm" property="screenJpeg5FileName"/>" border="1" height="150" width="100"/></a>
                            </td>
                        </tr>
                    </table>    
                </td></tr>
            <tr><td width="100%">&nbsp;</td></tr>
                <tr><td width="100%">
                    <table class="sectable" width="100%">
                        <tr class="sectitle"><td colspan="2" class="aimssecheading">Other Images for Content</td></tr>
                        <tr>
                            <td class="modFormFieldLbl">
                                <logic:present name="MarketingContentUpdateForm" property="publisherLogoFileName">High Resolution Publisher Logo : <a href="/aims/marketingContentResource.do?content_res=PublisherLogo&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" target="_blank"><bean:write name="MarketingContentUpdateForm" property="publisherLogoFileName"/></a></logic:present><br/>
                                <logic:present name="MarketingContentUpdateForm" property="appTitleGraphicFileName">Application Title Graphic : <a href="/aims/marketingContentResource.do?content_res=AppTitleGraphic&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" target="_blank"><bean:write name="MarketingContentUpdateForm" property="appTitleGraphicFileName"/></a></logic:present><br/>
                                <logic:present name="MarketingContentUpdateForm" property="splashScreenFileName">Splash Screen Shot : <a href="/aims/marketingContentResource.do?content_res=SplashScreen&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" target="_blank"><bean:write name="MarketingContentUpdateForm" property="splashScreenFileName"/></a></logic:present><br/>
                                <logic:present name="MarketingContentUpdateForm" property="activeScreenFileName">Active Screen Shot : <a href="/aims/marketingContentResource.do?content_res=ActiveScreen&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />"  target="_blank"><bean:write name="MarketingContentUpdateForm" property="activeScreenFileName"/></a></logic:present><br/>
                                <logic:present name="MarketingContentUpdateForm" property="videoFileFileName">Video File : <a href="/aims/marketingContentResource.do?content_res=VideoFile&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" target="_blank"><bean:write name="MarketingContentUpdateForm" property="videoFileFileName"/></a></logic:present><br/>
                                <logic:present name="MarketingContentUpdateForm" property="appLogoBwSmallFileName">Application Logo B&W Small : <a href="/aims/marketingContentResource.do?content_res=AppLogoBwSmall&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" target="_blank"><bean:write name="MarketingContentUpdateForm" property="appLogoBwSmallFileName"/></a></logic:present><br/>
                                <logic:present name="MarketingContentUpdateForm" property="appLogoBwLargeFileName">Application Logo B&W Large : <a href="/aims/marketingContentResource.do?content_res=AppLogoBwLarge&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" target="_blank"><bean:write name="MarketingContentUpdateForm" property="appLogoBwLargeFileName"/></a></logic:present><br/>
                                <logic:present name="MarketingContentUpdateForm" property="appLogoClrsmallFileName">Application Logo Color Small : <a href="/aims/marketingContentResource.do?content_res=AppLogoColorSmall&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" target="_blank"><bean:write name="MarketingContentUpdateForm" property="appLogoClrsmallFileName"/></a></logic:present><br/>
                                <logic:present name="MarketingContentUpdateForm" property="appLogoClrlargeFileName">Application Logo Color Large : <a href="/aims/marketingContentResource.do?content_res=AppLogoColorLarge&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" target="_blank"><bean:write name="MarketingContentUpdateForm" property="appLogoClrlargeFileName"/></a></logic:present><br/>
                            </td>
                        </tr>
                    </table>    
                </td></tr>
                <tr><td width="100%">&nbsp;</td></tr>
                <tr><td width="100%" align="right">
                    <div id="divButtons">
                        <input type="image" name="AllCancel" <bean:message key="images.cancel.button.lite"/> onClick="document.forms[0].action='/aims/mktContentsViewDelete.do';"/>
                        <%if (MarketingContentHelper.checkEditAccess(((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType(), MarketingContentUpdateForm.getStatus())) {%>
                            <input type="image" name="AllEdit" <bean:message key="images.edit.button.lite"/> onClick="document.forms[0].action='/aims/mktContentSetup.do?task=edit&mktContentId=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />';"/>                           
                        <% }else {}%>
                        <%if (MarketingContentHelper.checkDeleteAccess(((AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType(), MarketingContentUpdateForm.getStatus())) {%>
                            <input type="image" name="AllDelete" <bean:message key="images.delete.button.lite"/> onClick="javascript:if (!(window.confirm('Are you sure you want to delete this Content?'))) { return false;} else { document.forms[0].action='/aims/mktContentSetup.do?task=delete&mktContentId=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />';}"/>                            
                        <% }else {}%>                                                                   
                    </div>  
                                        
                </td></tr>               
            </table>
        </td>
    </tr>
</table>
</html:form>

