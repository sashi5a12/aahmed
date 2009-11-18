<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

  <%@ include  file="/common/error.jsp" %>

 <table cellspacing=0 cellpadding=0 width="100%" border=0>
  <tbody>
    <tr> 
      <td width=723><span class=aimsmasterheading><bean:message key="GINReportForm.AcceptedAppHeading" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></span></td>
      <td width="10"> </td>
    </tr>
    <tr> 
      <td width=723>&nbsp;</td>
      <td width="10">&nbsp; </td>
    </tr>
  
    <TR>
                <TD vAlign='top"' width="723">
                  <TABLE class=sectable height="100%" width="100%" cellspacing=0 cellpadding=0>
                    <TBODY>
                    <TR class=sectitle>
                      <TD class=aimssecheading><bean:message key="GINReportForm.ReportResult" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></TD>
                    </TR>
                    <TR>
                      <TD vAlign=center align=middle bgColor=#ebebeb>
                      	<table width="100%" cellspacing=0 cellpadding=0>
                      		<tr>
                      			<td>
                      				<table class=tabletop width=100% cellspacing=0 cellpadding=0>
                        <tr bgcolor=bbbbbb> 
                          <td class=firstcell><div align="center"><span  class="modFormFieldLbl"><bean:message key="GINReportForm.ApplicationName" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></span></div></td>
                          <td class=cell><div align="center"><span  class="modFormFieldLbl"><bean:message key="GINReportForm.Version" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></span></div></td>
                          <td class=cell><div align="center"><span  class="modFormFieldLbl"><bean:message key="GINReportForm.AllianceName" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></span></div></td>
                          <td class=cell><div align="center"><span  class="modFormFieldLbl"><bean:message key="GINReportForm.Handset" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></span></div></td>
                          <td class=cell><div align="center"><span  class="modFormFieldLbl"><bean:message key="GINReportForm.PartNumber" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></span></div></td>
                          <td class=cell><div align="center"><span  class="modFormFieldLbl"><bean:message key="GINReportForm.LaunchDate" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></span></div></td>
                          <td class=cell><div align="center"><span  class="modFormFieldLbl"><bean:message key="GINReportForm.DaysInQueue" bundle="com.netpace.aims.action.REPORTS_MESSAGE"/></span></div></td>
                        </tr>
                        <logic:iterate id="appList"  name="GINReportForm" property="brewApplicationList"> 
                        <tr> 
                          <td class=firstcell>&nbsp;<bean:write name="appList" property="applicationName"/></td>
                          <td class=cell>&nbsp;<bean:write name="appList" property="version"/></td>
                          <td class=cell>&nbsp;<bean:write name="appList" property="allianceName"/></td>
                          <td class=cell>&nbsp;<bean:write name="appList" property="handset"/></td>
                          <td class=cell>&nbsp;<bean:write name="appList" property="partNumber"/></td>
                          <td class=cell>&nbsp;<bean:write name="appList" property="launchDate"/></td>
                          <td class=cell align="center">&nbsp;<bean:write name="appList" property="daysInQueue"/></td>
                        </tr>
                        </logic:iterate> </table>
                      			</td>
                      		</tr>
                      		<tr>
                      			<td align=right class="modFormFieldLbl">
								    </br>
									<a href="#">Export to Excel </a>
                      			</td>
                      		</tr>
                      	</table>
					  </TD>
					</TR>
					</TBODY>
				  </TABLE>
				</TD>
			  </TR>
  </tbody>
</table>
</TD> </TR> </TBODY> </TABLE> 