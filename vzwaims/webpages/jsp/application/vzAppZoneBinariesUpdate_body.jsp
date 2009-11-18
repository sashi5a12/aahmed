<%@ page import="java.util.List"%>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<%@ include  file="include/vzAppZoneVariables.jsp" %>
<%
    int viewLimit = 4;
    int binaryFirmwareCounter = 0;
    String hideRowsStyleText = "";
%>
<%@ include  file="include/vzAppZoneAppJScript.jsp" %>
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

    <%  if(statusSaved) {   %>
        function removeBinaryId() {
            if(document.forms[0].binaryId && document.forms[0].binaryId.value<=0) {
                alert("Please select Binary Id to remove");
                return false;
            }

            var confirmMsg = "Are you sure you want to delete binary?";
            if(confirm(confirmMsg)) {
                var firmwareIds = document.forms[0].vzAppFirmwareIds;
                var deviceIds = document.forms[0].vzAppDeviceIds;
                document.forms[0].binaryFile.value="";
                document.forms[0].binaryVersion.value=""
                document.forms[0].binaryFileSize.value=""
                document.forms[0].previewFile.value=""
                document.forms[0].documentFile.value=""
                if(firmwareIds && firmwareIds.length) {
                    for(var firmwareIdx=0; firmwareIdx<firmwareIds.length; firmwareIdx++) {
                        if(firmwareIds[firmwareIdx].checked) {
                            firmwareIds[firmwareIdx].checked = false;
                        }
                    }
                }
                else if(firmwareIds) {
                    if(firmwareIds.checked) {
                        firmwareIds.checked = false;
                    }
                }
                //device ids
                if(deviceIds && deviceIds.length) {
                    for(var deviceIdx=0; deviceIdx<deviceIds.length; deviceIdx++) {
                        if(deviceIds[deviceIdx].checked) {
                            deviceIds[deviceIdx].checked = false;
                        }
                    }
                }
                else if(deviceIds) {
                    if(deviceIds.checked) {
                        deviceIds.checked = false;
                    }
                }
                document.forms[0].submit();
            }
            else {
                return false;
            }

            //hide all buttons and tabs
            document.getElementById("divButtons").style.visibility = "hidden";
            document.getElementById("divTabs").style.visibility = "hidden";
            showProcessingInfoPopup();

        }//end remove Binary
    <%  }//end if status saved   %>


    var deviceFirmwareArray = new Array();
    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="allVZAppDeviceFirmwareList">
        <logic:iterate id="vzAppBinaryDeviceFirmware" name="VZAppZoneApplicationUpdateForm" property="allVZAppDeviceFirmwareList" indexId="deviceFirmwareIdx">
            <logic:notEmpty name="vzAppBinaryDeviceFirmware" property="firmwareList">
                deviceFirmwareArray[<%=deviceFirmwareIdx%>] = new Array();
                <logic:iterate id="firmware" name="vzAppBinaryDeviceFirmware" property="firmwareList" indexId="firmwareIdx">
                    deviceFirmwareArray[<%=deviceFirmwareIdx%>][<%=firmwareIdx%>] = <c:out value="${firmware[0]}"/>;
                </logic:iterate>
            </logic:notEmpty>
        </logic:iterate>
    </logic:notEmpty>
    function selectDeviceFirmwares(deviceField, deviceIdx) {
        if(deviceFirmwareArray && deviceFirmwareArray.length) {
            var firmwareIdsToSelect = deviceFirmwareArray[deviceIdx];
            if(deviceField && firmwareIdsToSelect) {
                if(deviceField.checked) {
                    selectFirmwares(true, firmwareIdsToSelect);
                }
                else {
                    selectFirmwares(false, firmwareIdsToSelect);
                }
            }
        }
    }//end selectDeviceFirmwares

    function selectFirmwares(isChecked, matchIds) {
        var firmwareIds = document.forms[0].vzAppFirmwareIds;
        if(firmwareIds) {
            if(firmwareIds.length && matchIds.length) {
                for(var matchIdx=0; matchIdx<matchIds.length; matchIdx++) {
                    for(var firmwareIdx=0; firmwareIdx<firmwareIds.length; firmwareIdx++) {
                        if(matchIds[matchIdx]==firmwareIds[firmwareIdx].value) {
                            firmwareIds[firmwareIdx].checked = isChecked;
                            break;
                        }
                    }
                }
            }
            else {
                firmwareIds.checked = isChecked;
            }
        }
    }//end selectFirmwares

    function selectParent(parentField, currentField, parentValue) {
        if(deviceFirmwareArray && deviceFirmwareArray.length) {
            var oneChildChecked = isOneChildChecked(currentField, parentValue);
            if(parentField && parentField.length) {
                var parentFieldIdx = findParentIdx(parentField, parentValue);
                if(parentFieldIdx!=-1) {
                    parentField[parentFieldIdx].checked = oneChildChecked;
                }
            }
            else if(parentField) {
                parentField.checked = oneChildChecked;
            }
        }//end if parentField, currentField
    }//end

    function isOneChildChecked(currentField, parentValue) {
        var oneChildChecked = false;
        if(currentField.checked) {
            <%--if current child field is checked, select parent field--%>
            //parentField[parentFieldIdx].checked = true;
            oneChildChecked = true;
        }
        else {
            <%--else check if any other child is checked, then leave parent checked, otherwise uncheck parent--%>
            var childFieldValues = deviceFirmwareArray[parentValue];
            var firmwareIds = document.forms[0].vzAppFirmwareIds;
            if(firmwareIds && childFieldValues.length) {
                if(firmwareIds.length) {
                    for(var chieldFieldIdx=0; chieldFieldIdx<childFieldValues.length; chieldFieldIdx++) {
                        for(var firmwareIdx=0; firmwareIdx<firmwareIds.length; firmwareIdx++) {
                            if(childFieldValues[chieldFieldIdx]==firmwareIds[firmwareIdx].value) {
                                if(firmwareIds[firmwareIdx].checked) {
                                    oneChildChecked = true;
                                    break;
                                }
                            }
                        }
                    }//end for
                }
                else if(firmwareIds.checked) {//only one firmware id found
                    oneChildChecked = true;
                }
            }//end if firmwareIds
        }//end else
        return oneChildChecked;
    }//end isOneChildChecked

    function findParentIdx(parentField, valueForIdx) {
        var parentIdx = -1;
        if(parentField && parentField.length) {
            for(var idx=0; idx<parentField.length; idx++) {
                if(parentField[idx].value==valueForIdx) {
                    parentIdx = idx;
                    break;
                }
            }//end for
        }//end if parentField
        return parentIdx;
    }//end findParentIdx

    <%--select binary script--%>
    function Binaries() {
        this.binaryId = 0;
        this.binaryFileHTML	= '';
        this.binaryVersion = '';
        this.binaryFileSize	= '';
        this.previewFileHTML = '';
        this.documentFileHTML = '';
    }

    var binariesArray = new Array();
    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="vzAppBinaries">
        <logic:iterate id="vzAppBinary" name="VZAppZoneApplicationUpdateForm" property="vzAppBinaries" indexId="binaryIdx">
            var binary = new Binaries();
            var binaryHTML = '';
            var previewHTML = '';
            var documentHTML = '';
            binary.binaryId = <c:out value="${vzAppBinary.binaryId}"/> ;

            <logic:notEmpty	name="vzAppBinary"	property="binaryFileFileName">
                binaryHTML =    '<a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryFile&vzAppBinaryId=<c:out value="${vzAppBinary.binaryId}"/>&app_id=<c:out value="${vzAppBinary.vzAppZoneAppsId}"/>" class="a"	target="_blank">'
                                    +'<c:out value="${vzAppBinary.binaryFileFileName}"/>'
                                +'</a>';
            </logic:notEmpty>
            binary.binaryFileHTML = binaryHTML;

            binary.binaryVersion = '<c:out value="${vzAppBinary.binaryVersion}"/>';
            binary.binaryFileSize	=	'<c:out value="${vzAppBinary.binaryFileSize}"/>';
            <logic:notEmpty	name="vzAppBinary"	property="previewFileFileName">
                previewHTML =   '<a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryPreviewFile&vzAppBinaryId=<c:out value="${vzAppBinary.binaryId}"/>&app_id=<c:out value="${vzAppBinary.vzAppZoneAppsId}"/>" class="a"	target="_blank">'
                                    +'<c:out value="${vzAppBinary.previewFileFileName}"/>'
                                +"</a>";
            </logic:notEmpty>
            binary.previewFileHTML = previewHTML;

            <logic:notEmpty	name="vzAppBinary"	property="documentFileFileName">
                documentHTML =    '<a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url" bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=VZAppBinaryDocumentFile&vzAppBinaryId=<c:out value="${vzAppBinary.binaryId}"/>&app_id=<c:out value="${vzAppBinary.vzAppZoneAppsId}"/>" class="a"	target="_blank">'
                                    +'<c:out value="${vzAppBinary.documentFileFileName}"/>'
                                +'</a>';
            </logic:notEmpty>
            binary.documentFileHTML = documentHTML;


            binariesArray[<%=binaryIdx%>] = binary;
        </logic:iterate>
    </logic:notEmpty>

    function enableDisableBinaryFields() {
        var binaryIdField = document.forms[0].binaryId;
        var spnBinaryFileName = document.getElementById("spnBinaryFileName");
        var spnPreviewFileName = document.getElementById("spnPreviewFileName");
        var spnDocumentFileName = document.getElementById("spnDocumentFileName");

        var orgBinaryFileHTML = '';
        var orgPreviewFileHTML = '';
        var orgDocumentFileHTML = '';

        <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="binaryFileFileName">
            <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="binaryFileTempFileId">
                <logic:notEqual	name="VZAppZoneApplicationUpdateForm"	property="binaryFileTempFileId" value="0">
                    orgBinaryFileHTML = '<a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&tempFileId=<bean:write name="VZAppZoneApplicationUpdateForm" property="binaryFileTempFileId"/>&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="appsId" />" class="a" target="_blank">'
                                            +'<c:out value="${VZAppZoneApplicationUpdateForm.binaryFileFileName}"/>'
                                        +'</a>';
                </logic:notEqual>
            </logic:notEmpty>
        </logic:notEmpty>
        <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="previewFileFileName">
            <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="previewFileTempFileId">
                <logic:notEqual	name="VZAppZoneApplicationUpdateForm"	property="previewFileTempFileId" value="0">
                    orgPreviewFileHTML =    '<a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&tempFileId=<bean:write name="VZAppZoneApplicationUpdateForm" property="previewFileTempFileId"/>&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="appsId" />" class="a" target="_blank">'
                                                +'<c:out value="${VZAppZoneApplicationUpdateForm.previewFileFileName}"/>'
                                            +'</a>';
                </logic:notEqual>
            </logic:notEmpty>
        </logic:notEmpty>
        <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="documentFileFileName">
            <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="documentFileTempFileId">
                <logic:notEqual	name="VZAppZoneApplicationUpdateForm"	property="documentFileTempFileId" value="0">
                    orgDocumentFileHTML =    '<a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&tempFileId=<bean:write name="VZAppZoneApplicationUpdateForm" property="previewFileTempFileId"/>&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="appsId" />" class="a" target="_blank">'
                                                +'<c:out value="${VZAppZoneApplicationUpdateForm.documentFileFileName}"/>'
                                            +'</a>';
                </logic:notEqual>
            </logic:notEmpty>
        </logic:notEmpty>

        if(binaryIdField) {
            if(binaryIdField.value!=0) {
                var binaryArrIdx = -1;
                document.forms[0].binaryFile.disabled=true;
                document.forms[0].binaryVersion.disabled=true;
                document.forms[0].binaryFileSize.disabled=true;
                document.forms[0].previewFile.disabled=true;
                document.forms[0].documentFile.disabled=true;

                document.forms[0].binaryFileTempFileId.disabled=true;
                document.forms[0].previewFileTempFileId.disabled=true;
                document.forms[0].documentFileTempFileId.disabled=true;

                binaryArrIdx = findBinaryIdx(binaryIdField.value);
                if(binaryArrIdx!=-1) {
                    document.forms[0].binaryVersion.value = binariesArray[binaryArrIdx].binaryVersion;
                    document.forms[0].binaryFileSize.value = binariesArray[binaryArrIdx].binaryFileSize;
                    spnBinaryFileName.innerHTML = binariesArray[binaryArrIdx].binaryFileHTML;
                    spnPreviewFileName.innerHTML = binariesArray[binaryArrIdx].previewFileHTML;
                    spnDocumentFileName.innerHTML = binariesArray[binaryArrIdx].documentFileHTML;
                }
            }
            else {
                document.forms[0].binaryFile.disabled=false;
                document.forms[0].binaryVersion.disabled=false;
                document.forms[0].binaryFileSize.disabled=false;
                document.forms[0].previewFile.disabled=false;
                document.forms[0].documentFile.disabled=false;

                document.forms[0].binaryFileTempFileId.disabled=false;
                document.forms[0].binaryFileTempFileId.disabled=false;
                document.forms[0].documentFileTempFileId.disabled=false;

                document.forms[0].binaryVersion.value = '<c:out value="${VZAppZoneApplicationUpdateForm.binaryVersion}"/>';
                document.forms[0].binaryFileSize.value = '<c:out value="${VZAppZoneApplicationUpdateForm.binaryFileSize}"/>';;
                spnBinaryFileName.innerHTML = orgBinaryFileHTML;
                spnPreviewFileName.innerHTML = orgPreviewFileHTML;
                spnDocumentFileName.innerHTML = orgDocumentFileHTML;
            }
        }//end if binaryIdField
    }//enableDisableBinaryFields

    function findBinaryIdx(binaryId) {
        var idx = -1;
        if(binariesArray && binariesArray.length && binaryId!=0) {
            for(var arrIdx=0; arrIdx<binariesArray.length; arrIdx++) {
                if(binariesArray[arrIdx].binaryId==binaryId) {
                    idx = arrIdx;
                    break;
                }
            }
        }
        return idx;
    }//end findBinaryIdx

    function gotoBinaryEdit(appsId, binaryId) {
        var popupURL = "/aims/vzAppZoneBinaryUpdate.do?task=edit&appsId="+appsId+"&binaryId="+binaryId;
        var childWindow = window.open(popupURL,"wndVZAppBinaryUpdate","menubar=no,location=no,resizable=no,toolbar=no,width=550,height=485,scrollbars=yes");
        if (childWindow.opener == null) childWindow.opener = self;
        document.cookie = 'scrollTop=' + document.body.scrollTop;
        childWindow.focus();
    }

    function refreshBinariesPage() {
        showProcessingInfo();
        document.forms[0].task.value='page3';
        document.forms[0].appSubmitType.value='paging';
        document.forms[0].submit();
    }
</script>

<%@ include  file="appTabMessageHeader.jsp" %>

<div id="contentBox" style="float:left">
    <DIV class="homeColumnTab ">
        <%@ include  file="include/vzAppZoneTabs.jsp" %>
        <html:form action="/vzAppZoneApplicationUpdate.do" enctype="multipart/form-data">
            <div class="contentbox">
                <%@ include  file="include/vzAppZoneHidden.jsp" %>

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
                                    <th>Edit</th>
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
                                                <td style="text-align:center;vertical-align:top;">
                                                    &nbsp;
                                                    <a onclick="javascript:gotoBinaryEdit(<c:out value="${VZAppZoneApplicationUpdateForm.appsId}"/>, <c:out value="${vzAppBinaryFirmwareInfoVOList[0].binaryId}"/>);" class="a" title="Edit Binary" style="cursor:pointer;">
                                                        <img src="images/icon-edit.gif" alt="Edit" width="18" height="13" border="0"/>
                                                    </a>
                                                    &nbsp;
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

                    <%-- upload binary file section --%>
                    <tr>
                        <td><div class="lBox">
                            <DIV class="headLeftCurveblk"></DIV>
                            <H1>Upload Binary&nbsp;<span class="requiredText">*</span></H1>
                            <DIV class="headRightCurveblk"></DIV>
                        </div></td>
                    </tr>
                    <tr>
                        <td width="100%">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
                                <tr>
                                    <th colspan="4">
                                        <b><i>Note: For detailed Instructions about ZIP Binary File and Preview Image please refer to
                                            <a href="/aims/downloads/vzappzone/VZW_Developer_Guidelines_for_VZAppZone_Application_Integration.pdf" target="_blank" class="a">VZAppZone Developer Guidelines</a>
                                        </i></b>
                                    </th>
                                </tr>
                                <tr><td colspan="4">&nbsp;</td></tr>
                                <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="vzAppBinaries">
                                    <tr>
                                        <td nowrap style="vertical-align:top;">
                                            <strong>Binary ID:</strong>&nbsp;
                                        </td>
                                        <td nowrap style="vertical-align:top;">
                                            <html:select styleClass="selectField"
                                                         property="binaryId" size="1" style="margin-top:0px;"
                                                         onchange="javascript:enableDisableBinaryFields();">
                                                <html:option value="0"><bean:message key="ManageApplicationForm.label.selectOne"    bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/></html:option>
                                                <html:optionsCollection property="vzAppBinaries" label="binaryId" value="binaryId"/>
                                            </html:select>
                                        </td>
                                        <td nowrap style="text-align:left;vertical-align:top;">
                                            <%  if(statusSaved) { %>
                                                <div class="redBtn" style="float:left; margin-top:0px" id="AllSave" title="Save">
                                                    <div><div><div onClick="document.forms[0].task.value='removeBinary';removeBinaryId();">Remove</div></div></div>
                                                </div>
                                            <%  }   %>
                                            &nbsp;
                                        </td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr>
                                        <td nowrap style="vertical-align:top;">
                                            <strong>&nbsp;-&nbsp;-&nbsp;OR&nbsp;-&nbsp;-&nbsp;</strong>&nbsp;
                                        </td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    <tr><td colspan="4">&nbsp;</td></tr>
                                </logic:notEmpty>
                                <tr>
                                    <td nowrap style="vertical-align:top;" width="15%">
                                        <strong>Select ZIP Binary File&nbsp;<span class="requiredText">*</span>:</strong>
                                    </td>
                                    <td nowrap style="vertical-align:top;" width="30%">
                                        <html:file styleClass="inputFieldNoPad" property="binaryFile" />
                                    </td>
                                    <td nowrap style="vertical-align:top;" width="10%">
                                        <strong>Version&nbsp;<span class="requiredText">*</span>:</strong>
                                    </td>
                                    <td nowrap style="vertical-align:top;">
                                        <html:text styleClass="inputFieldNoPad" property="binaryVersion" maxlength="30" size="15" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td colspan="4">
                                        <span id="spnBinaryFileName">
                                            <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="binaryFileFileName">
                                                <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="binaryFileTempFileId">
                                                    <logic:notEqual	name="VZAppZoneApplicationUpdateForm"	property="binaryFileTempFileId" value="0">
                                                        <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&tempFileId=<bean:write name="VZAppZoneApplicationUpdateForm" property="binaryFileTempFileId"/>&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="appsId" />" class="a" target="_blank">
                                                            <c:out value="${VZAppZoneApplicationUpdateForm.binaryFileFileName}"/>
                                                        </a>
                                                    </logic:notEqual>
                                                </logic:notEmpty>
                                            </logic:notEmpty>
                                        </span>
                                    </td>
                                </tr>
                                <tr><td colspan="4">&nbsp;</td></tr>
                                <tr>
                                    <td nowrap style="vertical-align:top;">
                                        <strong>ZIP Binary Documents&nbsp;<span class="requiredText">*</span>:</strong>
                                    </td>
                                    <td nowrap style="vertical-align:top;">
                                        <html:file styleClass="inputFieldNoPad" property="documentFile" />
                                    </td>
                                    <td nowrap style="vertical-align:top;">
                                        <strong>Binary File Size:</strong>
                                    </td>
                                    <td nowrap style="vertical-align:top;">
                                        <html:text styleClass="inputFieldNoPad" property="binaryFileSize" maxlength="10" size="15" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td colspan="4">
                                        <span id="spnDocumentFileName">
                                            <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="documentFileFileName">
                                                <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="documentFileTempFileId">
                                                    <logic:notEqual	name="VZAppZoneApplicationUpdateForm"	property="documentFileTempFileId" value="0">
                                                        <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&tempFileId=<bean:write name="VZAppZoneApplicationUpdateForm" property="documentFileTempFileId"/>&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="appsId" />" class="a" target="_blank">
                                                            <c:out value="${VZAppZoneApplicationUpdateForm.documentFileFileName}"/>
                                                        </a>
                                                    </logic:notEqual>
                                                </logic:notEmpty>
                                            </logic:notEmpty>
                                        </span>
                                    </td>
                                </tr>
                                <tr><td colspan="4">&nbsp;</td></tr>
                                <tr>
                                    <td nowrap style="vertical-align:top;">
                                        <strong>Insert Preview Image:</strong>
                                    </td>
                                    <td nowrap style="vertical-align:top;" colspan="3">
                                        <html:file styleClass="inputFieldNoPad" property="previewFile"/>&nbsp;(Max Size 1MB)<br/>
                                        <span id="spnPreviewFileName">
                                            <logic:notEmpty	name="VZAppZoneApplicationUpdateForm"	property="previewFileFileName">
                                                <logic:notEmpty name="VZAppZoneApplicationUpdateForm"	property="previewFileTempFileId">
                                                    <logic:notEqual	name="VZAppZoneApplicationUpdateForm"	property="previewFileTempFileId" value="0">
                                                        <a href="<bean:message key="ManageApplicationForm.manage.vzAppZone.app.resource.url"	bundle="com.netpace.aims.action.MANAGE_APPLICATION_MESSAGE"/>?app_res=TempFile&tempFileId=<bean:write name="VZAppZoneApplicationUpdateForm" property="previewFileTempFileId"/>&app_id=<bean:write	name="VZAppZoneApplicationUpdateForm"	property="appsId" />" class="a" target="_blank">
                                                            <c:out value="${VZAppZoneApplicationUpdateForm.previewFileFileName}"/>
                                                        </a>
                                                    </logic:notEqual>
                                                </logic:notEmpty>
                                            </logic:notEmpty>
                                        </span>
                                    </td>
                                </tr>
                                <tr><td colspan="4">&nbsp;</td></tr>
                                <tr><td colspan="4">&nbsp;</td></tr>
                                <tr>
                                    <td style="vertical-align:top;"><strong>Select Devices:</strong></td>
                                    <td colspan="3">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="allVZAppDeviceFirmwareList">
                                                <logic:iterate id="vzAppBinaryDeviceFirmware" name="VZAppZoneApplicationUpdateForm" property="allVZAppDeviceFirmwareList" indexId="binaryDeviceFirmwareIdx">
                                                    <tr>
                                                        <td width="15%" nowrap style="vertical-align:top;padding-left:0px;">
                                                            <%
                                                                String deviceFieldName = "vzAppDeviceIds";
                                                                String selectChildCall = "javascript:selectDeviceFirmwares(this, "+binaryDeviceFirmwareIdx.toString()+");";
                                                                String selectParentCall = "javascript:selectParent(document.forms[0]."+deviceFieldName+", this, "+binaryDeviceFirmwareIdx+")";
                                                            %>
                                                            <html:multibox property="vzAppDeviceIds" value="<%=binaryDeviceFirmwareIdx.toString()%>"
                                                                onclick="<%=selectChildCall%>"/>
                                                            <c:out value="${vzAppBinaryDeviceFirmware.phoneModel}"/>
                                                        </td>
                                                        <td width="70%" style="vertical-align:top;">
                                                            <logic:notEmpty name="vzAppBinaryDeviceFirmware" property="firmwareList">
                                                                <logic:iterate id="firmware" name="vzAppBinaryDeviceFirmware" property="firmwareList">
                                                                    <html:multibox property="vzAppFirmwareIds"
                                                                                   onclick="<%=selectParentCall%>">
                                                                        <c:out value="${firmware[0]}"/>
                                                                    </html:multibox>
                                                                    <span onmouseover="return Tip('<c:out value="${firmware[2]}"/>');"><c:out value="${firmware[1]}"/>&nbsp;</span>
                                                                </logic:iterate>
                                                            </logic:notEmpty>
                                                        </td>
                                                        <td>&nbsp;</td>
                                                    </tr>
                                                </logic:iterate>
                                            </logic:notEmpty>
                                        </table>
                                    </td>
                                </tr>

                                <tr><td colspan="4">&nbsp;</td></tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td colspan="3"><b><i>Note: For Firmware information, move your mouse over the MR</i></b></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr><td>&nbsp;</td></tr>
                    <%-- end binary upload section--%>

                    <tr>
                        <td width="100%"><table width="100%" cellpadding="0" cellspacing="0" border="0">
                            <tr>
                                <td>
                                    <%@ include  file="include/vzAppZoneEditButtons.jsp" %>
                                </td>
                            </tr>
                        </table></td>
                    </tr>
                </table>

                <script	language="javascript">
                    <logic:notEmpty name="VZAppZoneApplicationUpdateForm" property="binaryId">
                        <logic:notEqual name="VZAppZoneApplicationUpdateForm" property="binaryId" value="0">
                            enableDisableBinaryFields();
                        </logic:notEqual>
                    </logic:notEmpty>
                </script>
                
            </div>
            <%-- end div Contentbox--%>
            <%@ include  file="appTabMessageFooter.jsp" %>
        </html:form>
    </div>
</div>
