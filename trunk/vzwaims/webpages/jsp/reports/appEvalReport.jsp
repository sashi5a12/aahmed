<%@	page language="java" import="java.util.*" %>
<%@	page import="org.apache.struts.validator.DynaValidatorForm"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>


<html>
<head>
<title>Application Evaluation Form</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript" type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>

    <script language="JavaScript" src="js/aims2.js"></script>
    <script language="JavaScript" src="js/calendar2.js"></script>
    <script language="JavaScript" src="js/tjmlib2.js"></script>


		<link href="/aims/css/global.css" rel="stylesheet" type="text/css" />
		<link href="/aims/css/modules.css" rel="stylesheet" type="text/css" />
		<link href="/aims/css/sections.css" rel="stylesheet" type="text/css" />


</head>

<body>
<img src="/aims/images/vzw_logo.gif" height="85" border="0" width="290"/>
<br/>

<html:messages id="message"	message="true" header="messages.header"	footer="messages.footer">
			<li><bean:write	name="message"/></li>
</html:messages>


<bean:define id="appInfo"	name="aimsApp" scope="request" />
<bean:define id="brewAppInfo"	name="aimsBrewApp" scope="request" />
<bean:define id="categoryInfo"	name="aimsAppCategory" scope="request" />
<bean:define id="allianceInfo"	name="aimsAlliance" scope="request" />


<table width="660" border="0" cellspacing="0" cellpadding="0" >
	<form name="appEvaluationForm" action="/aims/appEvaluation.do" method="post">
	<tr>
		<td width="100%">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2" align="center">
						<strong>
							<font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif">Application Evaluation Report</font>
						</strong>
					</td>
				</tr>
				<tr>
					<td></td>
					<td align="right">
						<a href="javascript:window.print();">Print Report</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</form>
	<tr>
		<td width="100%">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td bgcolor="#999999" height="1">
						<img src="images/spacer.gif" width="20" height="1" />
					</td>
				</tr>
				<tr>
					<td bgcolor="#999999" height="1"><img src="images/spacer.gif" width="20" height="1" /></td>
				</tr>
				<tr>
					<td align="left" valign="middle" bgcolor="#EBEBEB">
						<table width="100%" border="0" cellspacing="2" cellpadding="0">
							<tr>
                <td width="25%" valign="top">
			         		<img src="<bean:message key="ManageApplicationForm.manage.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=ScreenJpeg&app_type=<bean:message	key="ManageApplicationForm.brew.app.label" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>&app_id=<bean:write	name="appInfo"	property="appsId" />" border="0" height="200" width="150"/>
								</td>
								<td width="75%" valign="top" align="left">
									<table width="100%" border="0" cellspacing="4" cellpadding="0">
										<tr>
			                <td class="text" width="30%" align="left">
												<strong>Application Name: </strong><br/>
												<bean:write	name="appInfo"	property="title" />
											</td>
											<td class="text" width="40%" align="right">
												<strong>Version: ___________</strong>
											</td>
										</tr>
										<tr>
											<td class="text">
												<strong>Developer: </strong><br/>
												<bean:write	name="brewAppInfo"	property="developerName" />
											</td>
											<td class="text" align="right">
												<strong>Date Reviewed: ___________</strong>
											</td>
										</tr>
										<tr>
											<td class="text">
												<strong>Category: </strong><br/>
												<bean:write	name="categoryInfo"	property="categoryName" />
											</td>
											<td class="text" align="right">
												<strong>Priority: ___________</strong>
											</td>
										</tr>
										<tr>
											<td class="text">
												<strong>Application Status: </strong><br/>
												<input type="radio" disabled="true"  <logic:equal name="brewAppInfo" property="appType" value="N"> checked="true" </logic:equal> name="app_type"/>&nbsp;&nbsp;New App <input type="radio" disabled="true"  <logic:equal name="brewAppInfo" property="appType" value="E"> checked="true" </logic:equal> name="app_type"/>&nbsp;&nbsp;Existing App
											</td>
											<td class="text" align="right"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left" valign="middle" bgcolor="#EBEBEB">
						<table width="100%" class="tabletop" cellspacing="0" cellpadding="1">
							<tr bgcolor="#BBBBBB">
								<td colspan="3" class="text" align="left"><strong>Developer Website Materials</strong></td>
							</tr>
							<tr>
			          <td width="30%"><b>Item</b></td>
			          <td width="5%"><b>Approved</b></td>
			          <td width="65%" rowspan="20" valign="top">
			            <table width="100%" border="0" cellspacing="3" cellpadding="3">
			              <tr>
			                <td>
			                  <table width="100%" border="0">
			                    <tr>
			                      <td>
			                        <input type="checkbox" disabled="true" name="checkbox37" checked="true">
			                        Short Description</td>
			                      <td>
			                        <div align="right">
			                          <input type="checkbox" name="checkbox38">
			                          Approved </div>
			                      </td>
			                    </tr>
			                    <tr>
						                <td colspan="2">
						                  <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
						                    <tr>
						                      <td><bean:write	name="appInfo"	property="shortDesc" filter="false" /></td>
						                    </tr>
						                  </table>
						                </td>
						              </tr>
			                  </table>
			                </td>
			              </tr>
			              <tr>
			                <td>
			                  <table width="100%" border="0">
			                    <tr>
			                      <td>
			                        <input type="checkbox" disabled="true" name="checkbox372" checked="true">
			                        Long Description</td>
			                      <td>
			                        <div align="right">
			                          <input type="checkbox" name="checkbox382">
			                          Approved </div>
			                      </td>
			                    </tr>
			                    <tr>
						                <td colspan="2">
						                  <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
						                    <tr>
						                      <td><bean:write	name="appInfo"	property="longDesc" filter="false" /></td>
						                    </tr>
						                  </table>
						                </td>
						              </tr>
			                  </table>
			                </td>
			              </tr>
			            </table>
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox22" <logic:notEmpty name="appInfo" property="flashDemoFileName"> checked="true" </logic:notEmpty>/>
			            Flash Demo</td>
			          <td>
			            <input type="checkbox" name="checkbox3" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox23" <logic:notEmpty name="appInfo" property="userGuideFileName"> checked="true" </logic:notEmpty>/>
			            User Guide</td>
			          <td>
			            <input type="checkbox" name="checkbox4" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox24" <logic:notEmpty name="appInfo" property="screenJpegFileName"> checked="true" </logic:notEmpty>/>
			            Screen Jpeg 1</td>
			          <td>
			            <input type="checkbox" name="checkbox5" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox25" <logic:notEmpty name="appInfo" property="screenJpeg2FileName"> checked="true" </logic:notEmpty>/>
			            Screen Jpeg 2</td>
			          <td>
			            <input type="checkbox" name="checkbox6" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox26" <logic:notEmpty name="appInfo" property="screenJpeg3FileName"> checked="true" </logic:notEmpty>/>
			            Screen Jpeg 3</td>
			          <td>
			            <input type="checkbox" name="checkbox7" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox27" <logic:notEmpty name="appInfo" property="screenJpeg4FileName"> checked="true" </logic:notEmpty>/>
			            Screen Jpeg 4</td>
			          <td>
			            <input type="checkbox" name="checkbox8" >
			          </td>
			        </tr>
			        <tr>
			          <td height="30">
			            <input type="checkbox" disabled="true" name="checkbox28" <logic:notEmpty name="appInfo" property="screenJpeg5FileName"> checked="true" </logic:notEmpty>/>
			            Screen Jpeg 5</td>
			          <td height="30">
			            <input type="checkbox" name="checkbox9" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox106" <logic:notEmpty name="appInfo" property="splashScreenEpsFileName"> checked="true" </logic:notEmpty>/>
			            Splash Screen (EPS)</td>
			          <td>
			            <input type="checkbox" name="checkbox102" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox107" <logic:notEmpty name="appInfo" property="activeScreenEpsFileName"> checked="true" </logic:notEmpty>/>
			            Active Screen (EPS)</td>
			          <td>
			            <input type="checkbox" name="checkbox103" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox108" <logic:notEmpty name="appInfo" property="faqDocFileName"> checked="true" </logic:notEmpty>/>
			            FAQ </td>
			          <td>
			            <input type="checkbox" name="checkbox104" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox109" <logic:notEmpty name="appInfo" property="testPlanResultsFileName"> checked="true" </logic:notEmpty>/>
			            Test Plan Results</td>
			          <td>
			            <input type="checkbox" name="checkbox105" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox29" <logic:notEmpty name="brewAppInfo" property="styleGuideFileName"> checked="true" </logic:notEmpty>/>
			            Style Guide</td>
			          <td>
			            <input type="checkbox" name="checkbox10" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox30" <logic:notEmpty name="brewAppInfo" property="mktgSlickSheetFileName"> checked="true" </logic:notEmpty>/>
			            Marketing Slick Sheet</td>
			          <td>
			            <input type="checkbox" name="checkbox11" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox31" <logic:notEmpty name="brewAppInfo" property="appLogoBwSmallFileName"> checked="true" </logic:notEmpty>/>
			            Gray Logo Small</td>
			          <td>
			            <input type="checkbox" name="checkbox12" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox32" <logic:notEmpty name="brewAppInfo" property="appLogoBwLargeFileName"> checked="true" </logic:notEmpty>/>
			            Gray Logo Large</td>
			          <td>
			            <input type="checkbox" name="checkbox13" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox33" <logic:notEmpty name="brewAppInfo" property="appLogoColorSmallFileName"> checked="true" </logic:notEmpty>/>
			            Color Logo Small</td>
			          <td>
			            <input type="checkbox" name="checkbox14" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox34" <logic:notEmpty name="brewAppInfo" property="appLogoColorLargeFileName"> checked="true" </logic:notEmpty>/>
			            Color Logo Large</td>
			          <td>
			            <input type="checkbox" name="checkbox15" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox35" <logic:notEmpty name="allianceInfo" property="companyLogoFileName"> checked="true" </logic:notEmpty>/>
			            Publisher Logo</td>
			          <td>
			            <input type="checkbox" name="checkbox16" >
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" disabled="true" name="checkbox36" <logic:notEmpty name="allianceInfo" property="companyPresentationFileName"> checked="true" </logic:notEmpty>/>
			            Publisher Presentation</td>
			          <td>
			            <input type="checkbox" name="checkbox17" >
			          </td>
        			</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left" valign="middle" bgcolor="#EBEBEB">
						<table width="100%" class="tabletop" cellspacing="0" cellpadding="1">
							<tr bgcolor="#BBBBBB">
								<td class="text" align="left"><strong>Handsets</strong></td>
								<td class="text" align="left"><strong>Qualcomm Part Number</strong></td>
								<td class="text" align="left"><strong>Date Passed NSTL</strong></td>
								<td class="text" align="left"><strong>Revision</strong></td>
								<td class="text" align="left"><strong>Launched</strong></td>
							</tr>
							<logic:iterate id="device"	name="brewAppInfo" property="appDevicesMap" >
								<tr>
									<td class="text" align="left">
										<strong><bean:write	name="device"	property="aimsDevice.deviceModel" />
									</td>
									<td class="text" align="left">
										<logic:notEmpty name="device" property="partNumber"><u><bean:write	name="device"	property="partNumber" /></u></logic:notEmpty>
										<logic:empty name="device" property="partNumber"><strong>___________________</strong></logic:empty>
									</td>
									<td class="text" align="left">
										<logic:notEmpty name="device" property="bdsAcceptanceDate"><u><bean:write	name="device"	property="bdsAcceptanceDate" formatKey="date.format" filter="true"/></u></logic:notEmpty>
										<logic:empty name="device" property="bdsAcceptanceDate"><strong>___________________</strong></logic:empty>
									</td>
									<td class="text" align="left">
										<logic:notEmpty name="device" property="brewVersion"><u><bean:write	name="device"	property="brewVersion" /></u></logic:notEmpty>
										<logic:empty name="device" property="brewVersion"><strong>___________________</strong></logic:empty>
									</td>
									<td class="text"align="center">
										<strong><input type="checkbox" name="launched" /></strong>
									</td>
								</tr>
							</logic:iterate>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left" valign="middle" bgcolor="#EBEBEB">
						<table width="100%" class="tabletop" cellspacing="0" cellpadding="3">
							<tr bgcolor="#BBBBBB">
								<td class="text" align="left"><strong>Comments</strong></td>
							</tr>
							<tr>
								<td class="text" align="left">
									<bean:write	name="brewAppInfo"	property="anticipatedDaps" filter="false"/>
								</td>
							</tr>

						</table>
					</td>
				</tr>
				<tr>
			    <td align="left" valign="middle" bgcolor="#EBEBEB">
						<table width="100%" class="tabletop" cellspacing="0" cellpadding="3">
							<tr bgcolor="#BBBBBB">
								<td class="text" align="left" colspan="2"><strong>Evaluation</strong></td>
							</tr>
							<tr>
								<td colspan="2" class="text" align="left"> <input type="radio" disabled="true"  <logic:equal name="brewAppInfo" property="evaluation" value="F"> checked="true" </logic:equal> name="eval_type"/>&nbsp;&nbsp;Accepted Featured <input type="radio" disabled="true"  <logic:equal name="brewAppInfo" property="evaluation" value="G"> checked="true" </logic:equal> name="eval_type"/>&nbsp;&nbsp;Accepted General <input type="radio" disabled="true"  <logic:equal name="brewAppInfo" property="evaluation" value="N"> checked="true" </logic:equal> name="eval_type"/>&nbsp;&nbsp;Not Accepted </td><br/>
							</tr>
			        <tr>
			          <td width="50%">
			            <input type="checkbox" name="checkbox362" value="checkbox">
			            BizDev Review</td>
			          <td width="50%">
			            <input type="checkbox" name="checkbox363" value="checkbox">
			            WC Tech Review</td>
			        </tr>
			        
			        <% boolean bFound; %>
			        
			        
			        <tr>
			          <% bFound = false; %>
			          <td valign="top">
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                	<logic:iterate id="phase" name="appInfo" property="phases">
				                	<logic:equal name="phase"	property="aimsVzwTestingPhase.testingPhaseId" value="2">
					                	<logic:notEmpty name="phase"	property="status">
					                		<% bFound = true; %>
					                		<b><bean:write name="phase"	property="aimsDevice.deviceModel" />: </b><bean:write name="phase"	property="testedDate" formatKey="date.format" filter="true"/>
					                		<logic:equal name="phase"	property="status" value="P">
					                			&nbsp;(Passed)
					                		</logic:equal>
					                		<logic:equal name="phase"	property="status" value="F">
					                			&nbsp;(Failed)
					                		</logic:equal>
					                		<br/>
					                		<bean:write name="phase"	property="comments"  filter="false"/>
					                		<br/>
					                	</logic:notEmpty>
					                </logic:equal>
												</logic:iterate>
												<% if (!bFound) { %>
													<p>&nbsp;</p>
			                  	<p>&nbsp;</p>
			                  <% } %>
			                 </td>
			              </tr>
			            </table>
			          </td>
			          <% bFound = false; %>
			          <td valign="top">
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                	<logic:iterate id="phase" name="appInfo" property="phases">
				                	<logic:equal name="phase"	property="aimsVzwTestingPhase.testingPhaseId" value="1">
					                	<logic:notEmpty name="phase"	property="status">
					                		<% bFound = true; %>
					                		<b><bean:write name="phase"	property="aimsDevice.deviceModel" />: </b><bean:write name="phase"	property="testedDate" formatKey="date.format" filter="true"/>
					                		<logic:equal name="phase"	property="status" value="P">
					                			&nbsp;(Passed)
					                		</logic:equal>
					                		<logic:equal name="phase"	property="status" value="F">
					                			&nbsp;(Failed)
					                		</logic:equal>
					                		<br/>
					                		<bean:write name="phase"	property="comments"  filter="false"/>
					                		<br/>
					                	</logic:notEmpty>
					                </logic:equal>
												</logic:iterate>
												<% if (!bFound) { %>
													<p>&nbsp;</p>
			                  	<p>&nbsp;</p>
			                  <% } %>
			                 </td>
			              </tr>
			            </table>
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" name="checkbox364" value="checkbox">
			            VP Data Review</td>
			          <td>
			            <input type="checkbox" name="checkbox365" value="checkbox">
			            Legal Review</td>
			        </tr>
			        <tr>
			          <% bFound = false; %>
			          <td valign="top">
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                	<logic:iterate id="phase" name="appInfo" property="phases">
				                	<logic:equal name="phase"	property="aimsVzwTestingPhase.testingPhaseId" value="4">
					                	<logic:notEmpty name="phase"	property="status">
					                		<% bFound = true; %>
					                		<b><bean:write name="phase"	property="aimsDevice.deviceModel" />: </b><bean:write name="phase"	property="testedDate" formatKey="date.format" filter="true"/>
					                		<logic:equal name="phase"	property="status" value="P">
					                			&nbsp;(Passed)
					                		</logic:equal>
					                		<logic:equal name="phase"	property="status" value="F">
					                			&nbsp;(Failed)
					                		</logic:equal>
					                		<br/>
					                		<bean:write name="phase"	property="comments"  filter="false"/>
					                		<br/>
					                	</logic:notEmpty>
					                </logic:equal>
												</logic:iterate>
												<% if (!bFound) { %>
													<p>&nbsp;</p>
			                  	<p>&nbsp;</p>
			                  <% } %>
			                 </td>
			              </tr>
			            </table>
			          </td>
			          <% bFound = false; %>
			          <td valign="top">
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                	<logic:iterate id="phase" name="appInfo" property="phases">
				                	<logic:equal name="phase"	property="aimsVzwTestingPhase.testingPhaseId" value="3">
					                	<logic:notEmpty name="phase"	property="status">
					                		<% bFound = true; %>
					                		<b><bean:write name="phase"	property="aimsDevice.deviceModel" />: </b><bean:write name="phase"	property="testedDate" formatKey="date.format" filter="true"/>
					                		<logic:equal name="phase"	property="status" value="P">
					                			&nbsp;(Passed)
					                		</logic:equal>
					                		<logic:equal name="phase"	property="status" value="F">
					                			&nbsp;(Failed)
					                		</logic:equal>
					                		<br/>
					                		<bean:write name="phase"	property="comments"  filter="false"/>
					                		<br/>
					                	</logic:notEmpty>
					                </logic:equal>
												</logic:iterate>
												<% if (!bFound) { %>
													<p>&nbsp;</p>
			                  	<p>&nbsp;</p>
			                  <% } %>
			                 </td>
			              </tr>
			            </table>
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" name="checkbox366" value="checkbox">
			            Application Usability</td>
			          <td>
			            <input type="checkbox" name="checkbox367" value="checkbox">
			            No Legal Issues</td>
			        </tr>
			        <tr>
			          <td>
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                  <p>&nbsp;</p>
			                  <p>&nbsp;</p>
			                </td>
			              </tr>
			            </table>
			          </td>
			          <td>
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                  <p>&nbsp;</p>
			                  <p>&nbsp;</p>
			                </td>
			              </tr>
			            </table>
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" name="checkbox368" value="checkbox">
			            Application Operation</td>
			          <td>
			            <input type="checkbox" name="checkbox369" value="checkbox">
			            Application Disciplined</td>
			        </tr>
			        <tr>
			          <td>
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                  <p>&nbsp;</p>
			                  <p>&nbsp;</p>
			                </td>
			              </tr>
			            </table>
			          </td>
			          <td>
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                  <p>&nbsp;</p>
			                  <p>&nbsp;</p>
			                </td>
			              </tr>
			            </table>
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" name="checkbox3610" value="checkbox">
			            No Conflicts with Other Applications</td>
			          <td>
			            <input type="checkbox" name="checkbox3611" value="checkbox">
			            No EULA</td>
			        </tr>
			        <tr>
			          <td>
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                  <p>&nbsp;</p>
			                  <p>&nbsp;</p>
			                </td>
			              </tr>
			            </table>
			          </td>
			          <td>
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                  <p>&nbsp;</p>
			                  <p>&nbsp;</p>
			                </td>
			              </tr>
			            </table>
			          </td>
			        </tr>
			        <tr>
			          <td>
			            <input type="checkbox" name="checkbox3612" value="checkbox">
			            No Questionable Content</td>
			          <td>
			            <input type="checkbox" name="checkbox3613" value="checkbox">
			            Miscellaneous</td>
			        </tr>
			        <tr>
			          <td>
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                  <p>&nbsp;</p>
			                  <p>&nbsp;</p>
			                </td>
			              </tr>
			            </table>
			          </td>
			          <td>
			            <table width="100%" border="1" cellspacing="0" cellpadding="6" bordercolor="#000000">
			              <tr>
			                <td>
			                  <p>&nbsp;</p>
			                  <p>&nbsp;</p>
			                  </td>
			              </tr>
			            </table>
			          </td>
			        </tr>
			      </table>
			    </td>
			  </tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>
