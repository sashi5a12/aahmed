<%@	page language="java" import="com.netpace.aims.bo.security.*,com.netpace.aims.bo.newmarketing.*,com.netpace.aims.util.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<jsp:useBean id="MarketingContentUpdateForm" class="com.netpace.aims.controller.newmarketing.MarketingContentUpdateForm" scope="request" />

<%
boolean isVerizonUser = false, isAllianceUser = false;
boolean statusNew = false, statusSaved = false, statusSubmitted = false, statusApproved = false, statusRejected = false;

isVerizonUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.VZW_USERTYPE);
isAllianceUser = ((com.netpace.aims.model.core.AimsUser)request.getSession().getAttribute(AimsConstants.AIMS_USER)).getUserType().equals(AimsConstants.ALLIANCE_USERTYPE);
%>

<logic:equal name="MarketingContentUpdateForm" property="status" scope="request"   value="<%=AimsConstants.CP_MARKETING_CONTENT_STATUS_NEW.toString()%>">    
    <%statusNew = true;%>
</logic:equal>
<logic:equal name="MarketingContentUpdateForm" property="status" scope="request"   value="<%=AimsConstants.CP_MARKETING_CONTENT_STATUS_SAVED.toString()%>">    
    <%statusSaved = true;%>
</logic:equal>
<logic:equal name="MarketingContentUpdateForm" property="status" scope="request"   value="<%=AimsConstants.CP_MARKETING_CONTENT_STATUS_SUBMITTED.toString()%>">    
    <%statusSubmitted = true;%>
</logic:equal>
<logic:equal name="MarketingContentUpdateForm" property="status" scope="request"   value="<%=AimsConstants.CP_MARKETING_CONTENT_STATUS_APPROVED.toString()%>">    
    <%statusApproved = true;%>
</logic:equal>
<logic:equal name="MarketingContentUpdateForm" property="status" scope="request"   value="<%=AimsConstants.CP_MARKETING_CONTENT_STATUS_REJECTED.toString()%>">    
    <%statusRejected = true;%>
</logic:equal>
 

<script	language="javascript">
	
    function submitForm()
    {
        document.getElementById("divButtons").style.visibility = "hidden";
        showProcessingInfoPopup();          
    }

    function truncateLocalTextAreas()
	{
        if (typeof document.forms[0].contentDescription != "undefined")
            if (typeof document.forms[0].contentDescription.value != "undefined") 
                TruncateTextWithCount(document.forms[0].contentDescription,'textCountContentDescription',500);
                
        if (typeof document.forms[0].evaluationComments != "undefined")
            if (typeof document.forms[0].evaluationComments.value != "undefined")
                TruncateText(document.forms[0].evaluationComments,500);
	}

	function trackCountForTextAreas()
	{
		TrackCount(document.forms[0].contentDescription,'textCountContentDescription',500);
	}

    function disable1()
    {
        document.forms[0].contentTitle.disabled = true;
        document.forms[0].applicationTitle.disabled = true;
        document.forms[0].contentDescription.disabled = true;
        document.forms[0].publisherLogo.disabled = true;
        document.forms[0].appTitleGraphic.disabled = true;
        document.forms[0].splashScreen.disabled = true;
        document.forms[0].activeScreen.disabled = true;
        document.forms[0].screenJpeg1.disabled = true;
        document.forms[0].screenJpeg2.disabled = true;
        document.forms[0].screenJpeg3.disabled = true;
        document.forms[0].screenJpeg4.disabled = true;
        document.forms[0].screenJpeg5.disabled = true;
        document.forms[0].videoFile.disabled = true;
        document.forms[0].appLogoBwSmall.disabled = true;
        document.forms[0].appLogoBwLarge.disabled = true;
        document.forms[0].appLogoClrsmall.disabled = true;
        document.forms[0].appLogoClrlarge.disabled = true;
        
        for (var i=0; i<document.forms[0].elements.length; i++) {
            if (document.forms[0].elements[i].name == "contentUsagePermission") {
                document.forms[0].elements[i].disabled = true;
            }
        }    
    }

</script>

<table width="100%" border="0" cellspacing="0" cellpadding="0" onmousemove="truncateLocalTextAreas();">
    <tr> 
        <td width="20">&nbsp;</td>
        <td width="100%">
            <span class="aimsmasterheading">
                <%if (statusNew || statusSaved) {%>
                    Submit Marketing Content
                <% }else{%>
                    Edit Submitted Marketing Content
                <% } %>   
            </span>               
        </td>
    </tr>
    <%@ include  file="/common/error.jsp" %>
    <tr> 
        <td width="20">&nbsp;</td>
        <td align="center" valign="middle"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr><td width="100%">&nbsp;</td></tr>
                
                <tr><td width="100%">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <html:form action="/mktContentUpdate.do"    enctype="multipart/form-data">
                        
                        <html:hidden property="tempForRadioIssue" />
                        <html:hidden property="task" />
                        <html:hidden property="appSubmitType"   />
                        <html:hidden property="creativeContentId" />
                        <html:hidden property="status" />
                        
                        <html:hidden property="publisherLogoFileName" />
                        <html:hidden property="appTitleGraphicFileName" />
                        <html:hidden property="splashScreenFileName" />
                        <html:hidden property="activeScreenFileName" />
                        <html:hidden property="screenJpeg1FileName"  />
                        <html:hidden property="screenJpeg2FileName" />
                        <html:hidden property="screenJpeg3FileName" />
                        <html:hidden property="screenJpeg4FileName" />
                        <html:hidden property="screenJpeg5FileName" />
                        <html:hidden property="videoFileFileName" />
                        <html:hidden property="appLogoBwSmallFileName"   />
                        <html:hidden property="appLogoBwLargeFileName"   />
                        <html:hidden property="appLogoClrsmallFileName"   />
                        <html:hidden property="appLogoClrlargeFileName"   />
                        
                        <html:hidden property="publisherLogoTempFileId" />
                        <html:hidden property="appTitleGraphicTempFileId" />
                        <html:hidden property="splashScreenTempFileId" />
                        <html:hidden property="activeScreenTempFileId" />
                        <html:hidden property="screenJpeg1TempFileId"  />
                        <html:hidden property="screenJpeg2TempFileId" />
                        <html:hidden property="screenJpeg3TempFileId" />
                        <html:hidden property="screenJpeg4TempFileId" />
                        <html:hidden property="screenJpeg5TempFileId" />
                        <html:hidden property="videoFileTempFileId" />
                        <html:hidden property="appLogoBwSmallTempFileId"   />
                        <html:hidden property="appLogoBwLargeTempFileId"   />
                        <html:hidden property="appLogoClrsmallTempFileId"   />
                        <html:hidden property="appLogoClrlargeTempFileId"   />

                        <tr><td width="100%">
                            <table class="sectable" width="100%">
                                <tr class="sectitle"><td colspan="2" class="aimssecheading">Content Details</td></tr>
                                
                                <tr>
                                    <td class="modFormFieldLbl" width="50%">
                                        Content Title<span class="mainReqAstrx">*</span>:</td>
                                    <td class="modFormFieldLbl" width="50%">
                                        Application Title:</td>
                                </tr>
                                <tr>
                                    <td class="text" valign="top">
                                        <html:text  property="contentTitle" size="40" maxlength="40" /> (Max: 40 chars)     
                                    </td>
                                    <td class="text">
                                        <html:text  property="applicationTitle" size="40" maxlength="150" /> 
                                    </td>
                                </tr>
                                <tr>
                                    <td class="modFormFieldLbl" width="50%">Content Description<span class="mainReqAstrx">*</span>: (Max: 500 chars)</td>
                                    <td class="modFormFieldLbl" width="50%">Current Status</td>
                                </tr>
                                <tr>
                                    <td class="text">
                                        <html:textarea  property="contentDescription" rows="3" cols="30" onkeyup="TrackCount(this,'textCountContentDescription',500)" onkeypress="LimitText(this,500)" ></html:textarea>
                                        <br/><bean:message key="ManageApplicationForm.textarea.char.remaining"   bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></span>
                                        <input type="text" name="textCountContentDescription" size="3" value="500" disabled="true" />
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
                                <tr class="sectitle"><td colspan="2" class="aimssecheading">Content Files</td></tr>
                                <tr><td colspan="2" class="text">Minimum 300 dpi resolution is required for all EPS files.</td></tr>
                                <tr>
                                    <td class="modFormFieldLbl" width="50%">High Resolution Publisher Logo (EPS):</td>
                                    <td class="modFormFieldLbl" width="50%">Application Title Graphic (EPS):</td>
                                </tr>
                                <tr>
                                    <td class="text" valign="top">
                                        <html:file property="publisherLogo"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="publisherLogoFileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="publisherLogoTempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=PublisherLogo&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="publisherLogoTempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="publisherLogoTempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="publisherLogoFileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                    <td class="text">
                                        <html:file property="appTitleGraphic"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="appTitleGraphicFileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="appTitleGraphicTempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=AppTitleGraphic&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="appTitleGraphicTempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="appTitleGraphicTempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="appTitleGraphicFileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="modFormFieldLbl" width="50%">Splash Screen Shot (EPS)<span class="mainReqAstrx">*</span>:</td>
                                    <td class="modFormFieldLbl" width="50%">Active Screen Shot (EPS):</td>
                                </tr>
                                <tr>
                                    <td class="text" valign="top">
                                        <html:file property="splashScreen"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="splashScreenFileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="splashScreenTempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=SplashScreen&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="splashScreenTempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="splashScreenTempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="splashScreenFileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                    <td class="text">
                                        <html:file property="activeScreen"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="activeScreenFileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="activeScreenTempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=ActiveScreen&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="activeScreenTempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="activeScreenTempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="activeScreenFileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="modFormFieldLbl" width="50%">Screen Shot of Application (JPEG/GIF)<span class="mainReqAstrx">*</span>:</td>
                                    <td class="modFormFieldLbl" width="50%">Video File (AVI/3g2/WMV/MPG/MPEG):</td>
                                </tr>
                                <tr>
                                    <td class="text" valign="top">
                                        <html:file property="screenJpeg1"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="screenJpeg1FileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="screenJpeg1TempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=ScreenJpeg1&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="screenJpeg1TempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write name="MarketingContentUpdateForm" property="screenJpeg1TempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="screenJpeg1FileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                    <td class="text">
                                        <html:file property="videoFile"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="videoFileFileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="videoFileTempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=VideoFile&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="videoFileTempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="videoFileTempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="videoFileFileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text" valign="top" colspan="2">
                                        <html:file property="screenJpeg2"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="screenJpeg2FileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="screenJpeg2TempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=ScreenJpeg2&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="screenJpeg2TempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="screenJpeg2TempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="screenJpeg2FileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text" valign="top" colspan="2">
                                        <html:file property="screenJpeg3"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="screenJpeg3FileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="screenJpeg3TempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=ScreenJpeg3&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="screenJpeg3TempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="screenJpeg3TempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="screenJpeg3FileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text" valign="top" colspan="2">
                                        <html:file property="screenJpeg4"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="screenJpeg4FileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="screenJpeg4TempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=ScreenJpeg4&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="screenJpeg4TempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="screenJpeg4TempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="screenJpeg4FileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="text" valign="top" colspan="2">
                                        <html:file property="screenJpeg5"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="screenJpeg5FileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="screenJpeg5TempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=ScreenJpeg5&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="screenJpeg5TempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="screenJpeg5TempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="screenJpeg5FileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="modFormFieldLbl" width="50%">Application Logo B&W Small:</td>
                                    <td class="modFormFieldLbl" width="50%">Application Logo B&W Large:</td>
                                </tr>
                                <tr>
                                    <td class="text" valign="top">
                                        <html:file property="appLogoBwSmall"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="appLogoBwSmallFileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="appLogoBwSmallTempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=AppLogoBwSmall&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="appLogoBwSmallTempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="appLogoBwSmallTempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="appLogoBwSmallFileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                    <td class="text">
                                        <html:file property="appLogoBwLarge"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="appLogoBwLargeFileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="appLogoBwLargeTempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=AppLogoBwLarge&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="appLogoBwLargeTempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="appLogoBwLargeTempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="appLogoBwLargeFileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="modFormFieldLbl" width="50%">Application Logo Color Small:</td>
                                    <td class="modFormFieldLbl" width="50%">Application Logo Color Large:</td>
                                </tr>
                                <tr>
                                    <td class="text" valign="top">
                                        <html:file property="appLogoClrsmall"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="appLogoClrsmallFileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="appLogoClrsmallTempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=AppLogoColorSmall&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="appLogoClrsmallTempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="appLogoClrsmallTempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="appLogoClrsmallFileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                    <td class="text">
                                        <html:file property="appLogoClrlarge"/><br/>
                                        <logic:notEmpty name="MarketingContentUpdateForm"    property="appLogoClrlargeFileName" scope="request">
                                            <logic:equal name="MarketingContentUpdateForm" property="appLogoClrlargeTempFileId"   scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=AppLogoColorLarge&content_id=<bean:write name="MarketingContentUpdateForm" property="creativeContentId" />" class="a"   target="_blank">
                                            </logic:equal>
                                            <logic:notEqual name="MarketingContentUpdateForm"    property="appLogoClrlargeTempFileId" scope="request" value="0">
                                                <a href="/aims/marketingContentResource.do?content_res=TempFile&content_id=<bean:write   name="MarketingContentUpdateForm" property="appLogoClrlargeTempFileId" />" class="a" target="_blank">
                                            </logic:notEqual>
                                                <bean:write name="MarketingContentUpdateForm"    property="appLogoClrlargeFileName"/>
                                                </a>
                                        </logic:notEmpty>
                                    </td>
                                </tr>
                            </table>
                        </td></tr>
                        <tr><td width="100%">&nbsp;</td></tr>


                        <tr><td width="100%">
                            <table width="100%" cellspacing="0" cellpadding="1">
                                <tr>
                                    <td width="100%" valign="top">
                                        <table width="100%"  cellspacing="0" cellpadding="1">
                                            <tr><td>
                                                <table class="sectable" width="100%">
                                                    <tr class="sectitle"><td class="aimssecheading">Content Usage Permission</td></tr>
                                                    <tr>
                                                        <td class="text">
                                                            <html:radio property="contentUsagePermission" value="<%=AimsConstants.CP_MARKETING_CONTENT_USAGE_TYPE_GENERAL[0]%>"/><%=AimsConstants.CP_MARKETING_CONTENT_USAGE_TYPE_GENERAL[1]%>&nbsp;
                                                            <html:radio property="contentUsagePermission" value="<%=AimsConstants.CP_MARKETING_CONTENT_USAGE_TYPE_EVENT[0]%>"/><%=AimsConstants.CP_MARKETING_CONTENT_USAGE_TYPE_EVENT[1]%>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td></tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td></tr>
                        
                        <%if (isVerizonUser) {%>
                            <tr><td width="100%">
                                <table width="100%" cellspacing="0" cellpadding="1">
                                    <tr>
                                        <td width="100%" valign="top">
                                            <table width="100%"  cellspacing="0" cellpadding="1">
                                                <tr><td>
                                                    <table class="sectable" width="100%">
                                                        <tr class="sectitle"><td colspan="2" class="aimssecheading">Processing Information</td></tr>
                                                        <tr>
                                                            <td class="modFormFieldLbl" width="50%">Evaluation Comments:</td>
                                                            <td class="modFormFieldLbl" width="50%">Expiry Date</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="text">
                                                                <html:textarea  property="evaluationComments" rows="3" cols="30" onkeyup="LimitText(this,500)" onkeypress="LimitText(this,500)" ></html:textarea>                                                                
                                                            </td>
                                                            <td class="text" valign="top">
                                                                <html:text property="expiryDate" size="15"/><img name="daysOfMonth2Pos" onclick="toggleDatePicker('daysOfMonth2','MarketingContentUpdateForm.expiryDate')" id="daysOfMonth2Pos" <bean:message key="images.calendar.button.lite"/> /><div style="position:absolute;" id="daysOfMonth2"/></td>
                                                            </td>                                    
                                                        </tr>
                                                    </table>
                                                </td></tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td></tr>
                        <% }else {}%>

                        <tr><td width="100%">&nbsp;</td></tr>
                        <tr><td width="100%">
                            <table width="100%">
                                <tr class="buttonRow">
                                    <td colspan="2" height="25" align="right"   valign="middle">
                                        
                                        <div id="divButtons">                                        
                                            <input type="image" name="AllCancel" <bean:message key="images.cancel.button.lite"/> onClick="document.forms[0].action='/aims/mktContentsViewDelete.do';submitForm();"/>
                                            <%if (isAllianceUser) {%>
                                                <%if (statusNew || statusSaved) {%>
                                                    <input type="image" name="AllSave" <bean:message key="images.save.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM%>';submitForm();"/>
                                                    <input type="image" name="AllSubmit" <bean:message key="images.submit.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SUBMIT_FORM%>';submitForm();"/>
                                                <% }else if (statusSubmitted) {%>
                                                    <input type="image" name="AllSaveAfterSubmit" <bean:message key="images.save.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT%>';submitForm();"/>
                                                <% }else {} %>   
                                            <% }else if (isVerizonUser) {%>
                                                <input type="image" name="VZWSave" <bean:message key="images.save.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_SAVE_FORM%>';submitForm();"/>
                                                <input type="image" name="AllAccept" <bean:message key="images.accept.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_ACCEPT_FORM%>';submitForm();"/>
                                                <input type="image" name="AllReject" <bean:message key="images.reject.button.lite"/> onClick="document.forms[0].appSubmitType.value='<%=AimsConstants.AIMS_VZW_REJECT_FORM%>';submitForm();"/>
                                            <% } %>
                                        </div>  
                                    </td>
                                </tr>
                            </table>
                        </td></tr>      
                        
                        <script language="javascript">
                            trackCountForTextAreas();
                            <%if (isVerizonUser) {%>
                                disable1();                                    
                            <% } else {}%>                     
                        </script>
                        
                        <%if (isVerizonUser){%>
                           <html:hidden property="contentTitle" />
                           <html:hidden property="applicationTitle" />
                           <html:hidden property="contentDescription" />
                           <html:hidden property="contentUsagePermission" />
                       <% } else {}%>

                        </html:form>
                    </table>
                </td></tr>
            </table>
        </td>
    </tr>
</table>

