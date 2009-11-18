<%@ page import="com.netpace.aims.util.StringFuncs"%>
<%@ page import="java.util.List"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:useBean id="VZAppZoneApplicationUpdateForm" class="com.netpace.aims.controller.application.VZAppZoneApplicationUpdateForm" scope="request" />
<%VZAppZoneApplicationUpdateForm.setCurrentPage("page3");%>
<%@ include  file="include/vzAppZoneVariables.jsp" %>
<%
    int viewLimit = 4;
    int binaryFirmwareCounter = 0;
    String hideRowsStyleText = "";
%>
<script	language="javascript">
    function showHideAllBinaries(showAll) {
        var hideRowValue = '';
        var binaryTestStatusTbl = document.getElementById('binaryTestStatusTbl');
        var spnViewAll = document.getElementById('spnViewAll');
        var spnViewAllHTML = '';

        if(spnViewAll && binaryTestStatusTbl && binaryTestStatusTbl.rows && binaryTestStatusTbl.rows.length) {
            if(showAll) {
                hideRowValue = '';
                spnViewAllHTML = "<a class='a' onclick='javascript:showHideAllBinaries(false);'>View Limited</a>&nbsp;<img src='images/greyRndArrowL.gif' width='17' height='17' align='absmiddle'>";
            }
            else {
                hideRowValue = 'none';
                spnViewAllHTML = "<a class='a' onclick='javascript:showHideAllBinaries(true);'>View All</a>&nbsp;<img src='images/greyRndArrow.gif' width='17' height='17' align='absmiddle'>";
            }

            <%-- row 0 is table headings, row5 is 4th record if view limit is 4 --%>
            for(var rowIdx=<%=viewLimit+1%>; rowIdx<binaryTestStatusTbl.rows.length; rowIdx++) {
                binaryTestStatusTbl.rows[rowIdx].style.display = hideRowValue;
            }//end for

            spnViewAll.innerHTML = spnViewAllHTML;
        }//end if
    }//end showHideAllBinaries
    function collapseBinariesRow(binaryId) {
        document.getElementById("spnPhoneModel"+binaryId).style.display='none';
        document.getElementById("spnExpandCollapse"+binaryId).innerHTML=
                "<a class='a' onclick='javascript:expandBinariesRow("+binaryId+");//'>[+]</a>";
        return false;
    }//end collapseBinariesRow
    function expandBinariesRow(binaryId) {
        document.getElementById("spnPhoneModel"+binaryId).style.display='';
        document.getElementById("spnExpandCollapse"+binaryId).innerHTML=
                "<a class='a' onclick='javascript:collapseBinariesRow("+binaryId+");'>[-]</a>";
        return false;
    }//end collapseBinariesRow   
</script>

<div id="contentBox" style="float:left">
    <DIV class="homeColumnTab ">
        <%@ include  file="include/vzAppZoneViewTabs.jsp" %>
        <html:form action="/vzAppZoneApplicationUpdate.do"    enctype="multipart/form-data">
            <div class="contentbox">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">

					<logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="vzAppBinaryFirmwareInfoList">
                    <%-- Binary Testing Status--%>
                    <tr>
                        <td><div class="lBox">
                            <DIV class="headLeftCurveblk"></DIV>
                            <H1>Binary Testing Status</H1>
                            <DIV class="headRightCurveblk"></DIV>
                        </div></td>
                    </tr>
                    <tr>
                        <td width="100%">
                            <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5" id="binaryTestStatusTbl">
                                <tr>
                                    <th><strong>Phone Model</strong></th>
                                    <th><strong>Firmware Name</strong></th>
                                    <th><strong>MR Number</strong></th>
                                    <th><strong>Status</strong></th>
                                    <th><strong>Binary ID</strong></th>
                                    <th><strong>Binary File</strong></th>
                                    <th><strong>Paid</strong></th>
                                    <th><strong>Version</strong></th>
                                    <th><strong>Test Results</strong></th>
                                </tr>

                                <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="vzAppBinaryFirmwareTestStatusList">
                                        <logic:iterate id="vzAppBinaryFirmwareTestInfo" name="VZAppZoneApplicationUpdateForm" property="vzAppBinaryFirmwareTestStatusList">
                                            <%  if((binaryFirmwareCounter >= viewLimit)) {
                                                    hideRowsStyleText = "style='display:none;'";
                                                }
                                            %>
                                                <tr <%=hideRowsStyleText%>>
                                                    <td><c:out value="${vzAppBinaryFirmwareTestInfo.phoneModel}"/></td>
                                                    <td><c:out value="${vzAppBinaryFirmwareTestInfo.firmwareName}"  escapeXml="false"/></td>
                                                    <td><c:out value="${vzAppBinaryFirmwareTestInfo.mrNumber}"/></td>
                                                    <td><c:out value="${vzAppBinaryFirmwareTestInfo.binaryFirmwareStatusValue}"/></td>
                                                    <td style="text-align:left;"><c:out value="${vzAppBinaryFirmwareTestInfo.binaryId}"/></td>
                                                    <td style="text-align:left;">
                                                        <logic:notEmpty	name="vzAppBinaryFirmwareTestInfo"	property="binaryFileFileName">
                                                            <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryFile&vzAppBinaryId=<c:out value="${vzAppBinaryFirmwareTestInfo.binaryId}"/>&app_id=<c:out value="${vzAppBinaryFirmwareTestInfo.vzAppZoneAppsId}"/>" class="a"	target="_blank">
                                                                <c:out value="${vzAppBinaryFirmwareTestInfo.binaryFileFileName}" escapeXml="false"/>
                                                            </a>
                                                        </logic:notEmpty>
                                                    </td>
                                                    <td style="text-align:center;"><c:out value="${vzAppBinaryFirmwareTestInfo.isPaid}"/></td>
                                                    <td style="text-align:center;"><c:out value="${vzAppBinaryFirmwareTestInfo.binaryVersion}" escapeXml="false"/></td>
                                                    <td>
                                                        <a target="_blank" href="<%=request.getContextPath()%>/vzAppZoneTestResultsHistory.do?vzAppZoneAppsId=<c:out value="${vzAppBinaryFirmwareTestInfo.vzAppZoneAppsId}"/>&firmwareId=<c:out value="${vzAppBinaryFirmwareTestInfo.firmwareId}"/>&phoneModel=<c:out value="${vzAppBinaryFirmwareTestInfo.phoneModel}"/>">History</a>
                                                    </td>
                                                </tr>
                                            <%  binaryFirmwareCounter++;    %>
                                        </logic:iterate>
                                    </logic:notEmpty>
                            </table>
                        </td>
                    </tr>
                    <%  if(binaryFirmwareCounter > viewLimit) {%>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td align="right">
                                <span id="spnViewAll" style="cursor:pointer;">
                                    <a class="a" href="javascript:showHideAllBinaries(true);" >
                                        View All
                                    </a>&nbsp;<img src="images/greyRndArrow.gif" width="17" height="17" align="absmiddle">
                                </span>
                            </td>
                        </tr>
                    <%  }   %>
                    <tr><td>&nbsp;</td></tr>
                    <tr><td>&nbsp;</td></tr>
                    <%-- end Binary Testing Status--%>

                    <%-- Binaries Section --%>
                    <tr>
                        <td><div class="lBox">
                            <DIV class="headLeftCurveblk"></DIV>
                            <H1>Binaries Section</H1>
                            <DIV class="headRightCurveblk"></DIV>
                        </div></td>
                    </tr>
                    <tr>
                        <td width="100%">
                            <table width="100%" class="Grid2" border="1" cellspacing="0" cellpadding="5">
                                <tr>
                                    <th><strong>Binary ID</strong></th>
                                    <th><strong>Binary File</strong></th>
                                    <th><strong>Version</strong></th>
                                    <th><strong>Binary File Size</strong></th>
                                    <th><strong>Preview File</strong></th>
                                    <th><strong>Doc</strong></th>
                                    <th><strong>Phone Model (MR Number)</strong></th>
                                </tr>

                                <logic:iterate id="vzAppBinaryFirmwareInfoVOList" name="VZAppZoneApplicationUpdateForm" property="vzAppBinaryFirmwareInfoList">
                                    <logic:notEmpty name="vzAppBinaryFirmwareInfoVOList">
                                            <%
                                                List voList = (List)pageContext.getAttribute("vzAppBinaryFirmwareInfoVOList");
                                                boolean multipleBinaryDevices = false;
                                                if(voList!=null && voList.size()>1) {
                                                    multipleBinaryDevices = true;
                                                }
                                            %>
                                            <tr>
                                                <td style="vertical-align:top;"><c:out value="${vzAppBinaryFirmwareInfoVOList[0].binaryId}"/></td>
                                                <td style="vertical-align:top;">
                                                    <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryFile&vzAppBinaryId=<c:out value="${vzAppBinaryFirmwareInfoVOList[0].binaryId}"/>&app_id=<c:out value="${vzAppBinaryFirmwareInfoVOList[0].vzAppZoneAppsId}"/>" class="a"	target="_blank">
                                                        <c:out value="${vzAppBinaryFirmwareInfoVOList[0].binaryFileFileName}" escapeXml="false"/>
                                                    </a>
                                                </td>
                                                <td style="vertical-align:top;"><c:out value="${vzAppBinaryFirmwareInfoVOList[0].binaryVersion}" escapeXml="false"/></td>
                                                <td style="text-align:center;vertical-align:top;"><c:out value="${vzAppBinaryFirmwareInfoVOList[0].binaryFileSize}"/></td>
                                                <td style="text-align:left;vertical-align:top;">
                                                    <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryPreviewFile&vzAppBinaryId=<c:out value="${vzAppBinaryFirmwareInfoVOList[0].binaryId}"/>&app_id=<c:out value="${vzAppBinaryFirmwareInfoVOList[0].vzAppZoneAppsId}"/>" class="a"	target="_blank">
                                                        <c:out value="${vzAppBinaryFirmwareInfoVOList[0].previewFileFileName}" escapeXml="false"/>
                                                    </a>
                                                </td>
                                                <td style="text-align:center;vertical-align:top;">
                                                    <c:if test="${vzAppBinaryFirmwareInfoVOList[0].documentFileFileName!=null and vzAppBinaryFirmwareInfoVOList[0].documentFileFileName!=''}">
                                                        <a onmouseover="return Tip('<c:out value="${vzAppBinaryFirmwareInfoVOList[0].documentFileFileName}"/>');" href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryDocumentFile&vzAppBinaryId=<c:out value="${vzAppBinaryFirmwareInfoVOList[0].binaryId}"/>&app_id=<c:out value="${vzAppBinaryFirmwareInfoVOList[0].vzAppZoneAppsId}"/>" class="a"	target="_blank">
                                                            <img src='images/document_icon.gif' align='absmiddle'>
                                                        </a>
                                                    </c:if>
                                                </td>
                                                <td style="text-align:left;vertical-align:top;">
                                                    <table width="100%" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td width="90%">
                                                                <c:out value="${vzAppBinaryFirmwareInfoVOList[0].phoneModel}"/>&nbsp;
                                                                (<c:out value="${vzAppBinaryFirmwareInfoVOList[0].mrNumber}"/>)&nbsp;
                                                            </td>
                                                            <td width="10%">
                                                                <%  if(multipleBinaryDevices) { %>
                                                                    <span id="spnExpandCollapse<c:out value='${vzAppBinaryFirmwareInfoVOList[0].binaryId}'/>" style="cursor:pointer;">
                                                                        <a class="a" onclick="javascript:expandBinariesRow(<c:out value='${vzAppBinaryFirmwareInfoVOList[0].binaryId}'/>)">[+]</a>
                                                                    </span>
                                                                <%  }   %>
                                                            </td>
                                                        </tr>
                                                        <%
                                                            if(multipleBinaryDevices) {
                                                        %>
                                                                <tr id="spnPhoneModel<c:out value='${vzAppBinaryFirmwareInfoVOList[0].binaryId}'/>" style="display:none;">
                                                                    <td>
                                                                        <span>
                                                                            <logic:iterate id="vzAppBinaryFirmwareInfoForPhoneModel" name="vzAppBinaryFirmwareInfoVOList" indexId="phoneModelIndex">
                                                                                <%
                                                                                    //skip first item
                                                                                    if(phoneModelIndex.longValue()!=0) { %>
                                                                                        <c:out value="${vzAppBinaryFirmwareInfoForPhoneModel.phoneModel}"/>&nbsp;
                                                                                        (<c:out value="${vzAppBinaryFirmwareInfoForPhoneModel.mrNumber}"/>)<br/>
                                                                                <%  }   %>
                                                                            </logic:iterate>
                                                                        </span>
                                                                    </td>
                                                                    <td>&nbsp;</td>
                                                                </tr>
                                                        <%
                                                            }//end voList
                                                        %>
                                                    </table>

                                                </td>                                                
                                            </tr>
                                    </logic:notEmpty>
                                </logic:iterate>
                            </table>
                        </td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                    <%-- end Binaries Section --%>
                    <tr><td>&nbsp;</td></tr>
                    </logic:notEmpty>

                    <%-- todo show binaries --%>
                    <tr>
                        <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                                <td>
                                    <%@ include  file="include/vzAppZoneViewButtons.jsp" %>
                                </td>
                            </tr>
                        </table></td>
                    </tr>
                </table>
            </div>
			<%-- end div content box--%>

        </html:form>
    </div>
</div>