<%@	page language="java" import="com.netpace.aims.bo.security.*, com.netpace.aims.bo.application.*, com.netpace.aims.util.*, com.netpace.aims.model.core.*, com.netpace.aims.controller.application.*" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
       <td width="20">&nbsp;</td>
       <td width="100%">
       	<span class="aimsmasterheading">
       		Provisioned Contents - 
       		Application Details
       	</span>               
       </td>
  </tr>
  <%@ include  file="/common/error.jsp" %>
  <tr> 
      <td width="20">&nbsp;</td>
      <td align="center" valign="middle"> 
			<html:form action="/newMktContentRequest.do">
			<html:hidden property="pageNo" />
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr><td width="100%">
					<table class="sectable" width="100%">
						<tr class="sectitle"><td colspan="2" class="aimssecheading">Content Management - Content Detail </td></tr>
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Content Title
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Application Title 
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top">
								<bean:write name="AppDetails" property="appTitle"/>
							</td>
							<td class="text">
								<bean:write name="AppDetails" property="shortDesc"/>
							</td>
						</tr>
						<tr>
							<td class="modFormFieldLbl" width="50%">
								Content Description
							:</td>
							<td class="modFormFieldLbl" width="50%">
								Current Status
							:</td>
						</tr>
						<tr>
							<td class="text" valign="top"> 
								<textarea name="shortDesc" rows="4"	cols="30" readonly="true"><bean:write name="AppDetails" property="version"/></textarea>
							 	
							</td>
							<td class="text" valign='top'>
								 <bean:write name="AppDetails" property="longDesc"/>
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
								<logic:present name="AppDetails" property="screenName1"><img src="newContentResource.do?app_res=ScreenJpeg1&app_id=<bean:write name="AppDetails" property="applicationId"/>" alt='<bean:write name="AppDetails" property="screenName1"/>' ></logic:present>
								<logic:present name="AppDetails" property="screenName2"><img src="newContentResource.do?app_res=ScreenJpeg2&app_id=<bean:write name="AppDetails" property="applicationId"/>" alt='<bean:write name="AppDetails" property="screenName2"/>' ></logic:present>
								<logic:present name="AppDetails" property="screenName3"><img src="newContentResource.do?app_res=ScreenJpeg3&app_id=<bean:write name="AppDetails" property="applicationId"/>" alt='<bean:write name="AppDetails" property="screenName3"/>' ></logic:present>
								<logic:present name="AppDetails" property="screenName4"><img src="newContentResource.do?app_res=ScreenJpeg4&app_id=<bean:write name="AppDetails" property="applicationId"/>" alt='<bean:write name="AppDetails" property="screenName4"/>' ></logic:present>
								<logic:present name="AppDetails" property="screenName5"><img src="newContentResource.do?app_res=ScreenJpeg5&app_id=<bean:write name="AppDetails" property="applicationId"/>" alt='<bean:write name="AppDetails" property="screenName5"/>' ></logic:present>
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
								<logic:present name="AppDetails" property="hiResPublisherLogoName">Publishers Logo : <a href="newContentResource.do?task=view&app_res=ClrPubLogo&app_id=<bean:write name="AppDetails" property="applicationId"/>" target="_blank"><bean:write name="AppDetails" property="hiResPublisherLogoName"/></a></logic:present><br/>
								<logic:present name="AppDetails" property="splashScreenName">Splash Screen : <a href="newContentResource.do?task=view&app_res=SplashScreenEps&app_id=<bean:write name="AppDetails" property="applicationId"/>" target="_blank"><bean:write name="AppDetails" property="splashScreenName"/></a></logic:present><br/>
								<logic:present name="AppDetails" property="activeScreenName">Active Screen : <a href="newContentResource.do?task=view&app_res=ActiveScreenEps&app_id=<bean:write name="AppDetails" property="applicationId"/>" target="_blank"><bean:write name="AppDetails" property="activeScreenName"/></a></logic:present><br/>
								<logic:present name="AppDetails" property="appTitleGraphicsName">Application Title Graphics : <a href="newContentResource.do?task=view&app_res=AppTitleName&app_id=<bean:write name="AppDetails" property="applicationId"/>" target="_blank"><bean:write name="AppDetails" property="appTitleGraphicsName"/></a></logic:present><br/>
							</td>
						</tr>
					</table>	
				</td></tr>
				<tr><td width="100%">&nbsp;</td></tr>
				<tr><td width="100%" align="right">
					<a href="newMktContentRequest.do"><img src="images/back_b.gif" width="52" height="15" border="0"/></a>
				</td></tr>
				<!-- Buttons were here -->
			</table>
		</td>
	</tr>
</table>
</html:form>

