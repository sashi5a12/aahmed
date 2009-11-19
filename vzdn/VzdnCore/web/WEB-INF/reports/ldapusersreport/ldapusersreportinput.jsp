<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.crystaldecisions.report.web.viewer.CrystalReportViewer"%><html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta name="mainTitle" content="Verizon Reports" />
	<title>Verizon Reports</title>
	<style type="text/css">
		#bodyWrapper {MARGIN: 0px auto; WIDTH: 984px; TEXT-ALIGN: left;POSITION:static; border:solid 0px #000;}
	</style>
	<script src="scripts/calendar2.js" type="text/javascript" ></script>
	<script src="scripts/tjmlib2.js" type="text/javascript"></script>
</head>

<body>
<div id="contentCol">
  		<!-- POD START HERE -->
        <div class="box984">
        <div id="searchUsers">
        	
<div class="box2">
                <div class="boxTop box984"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
                
                
                
                
                <div class="boxContent">
			<!-- POD CONTENT START HERE -->
			<div class="subHead">
				Registered Developers Report
			</div>
			<div class="pR10">
		
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
		
					<tr>
						<td colspan="2">
		
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
		
								<tr>
		
									<s:form name="userreportsInputForm" onsubmit="return false" action="/verizon/reports/admin/loaddeveloperreport.action" method="post" theme="simple" target="_blank">
									
									<td>
										<table width="100%" border="0" cellspacing="1" cellpadding="0">
											<tr>
		
												<td>
													<table width="100%" border="0" cellpadding="3"
														cellspacing="0">
														<tr>
															<td class="label boldText" width="25%">
																From Date:
																<br />
																<s:textfield name="fromDate" maxlength="50" size="25" cssClass="inputFieldForm" readonly="true"/>
																(format yyyy-MM-dd)
															</td>
															<td width="25%">
																<div  style="position: absolute; margin-top:22px;" id="calPosition" name="calPosition"></div><img name="calPositionPos" id="calPositionPos" src="images/icon_calendar.gif" onclick="updateCalendar('fromDate');toggleDatePicker('calPosition','userreportsInputForm.fromDate')" alt="Click Here to Pick up the date" width="24" height="24">
															</td>
														
														
															<td class="label boldText" width="15%">
																To Date:
																<br />
																<s:textfield name="toDate" maxlength="50" size="25" cssClass="inputFieldForm" readonly="true"/>
																(format yyyy-MM-dd)
															</td>
															<td width="25%">
																<div style="position: absolute; margin-top:22px;" id="calPos" ></div><img name="calPosPos" id="calPosPos" src="images/icon_calendar.gif" onclick="updateCalendar('toDate');toggleDatePicker('calPos','userreportsInputForm.toDate')" alt="Click Here to Pick up the date" width="24" height="24">
															</td>
														</tr>
														
														<tr>
														<table>
														<td class="label boldText">
																Sorting On:
																<br />
																<s:select list="sortList" name="sorting" ></s:select>
															</td>
															<td>&nbsp;  </td>
															<td>&nbsp;  </td>
															<td width="40%" valign="bottom">
																<button class="input_primary" onclick="javascript:processLdapUsersReport();">
																	<span><span><span>GO</span>
																	</span>
																	</span>
																</button>
															</td>
															</table>
															
															
															
														</tr>
														
														
																										
													</table>
												</td>
											</tr>
		
										</table>
									</td>
		
									</s:form>
		
		
		
		
								</tr>
							</table>
						</td>
					</tr>
		
				</table>
				<!-- ends here--->
				</td>
		
				</tr>
		
		
		
				</table>
		
		
				<div class="clear10"></div>
		
			</div>
			<!-- POD CONTENT END HERE -->
		</div>

                
                <div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
            </div>
        </div>
        <!-- POD END HERE -->
    <div class="clear14"></div>
        	
        </div>

        </div>
        <!-- POD END HERE -->
    <div class="clear14"></div>


<script type="text/javascript">
function processLdapUsersReport()
{
	var msg = "" ;
	if ( document.userreportsInputForm.toDate.value == "" )
	{
		document.userreportsInputForm.toDate.focus();
		msg += "Please enter To Date.\n" ;
	}
	if ( document.userreportsInputForm.fromDate.value == "" )
	{
		document.userreportsInputForm.fromDate.focus();
		msg += "Please enter From Date.\n";
	}
	if ( msg != "" )
	{
		alert( msg ) ;
		return false;
	}
		document.userreportsInputForm.submit();
	
}

function updateCalendar( txtName )
{
	var txtDate = document.getElementsByName( txtName )[0];

	if (txtDate == null)
		return;
	if ( txtDate.value == "" )
	{
		displayMonth = new Date().getMonth();
		displayYear = new Date().getFullYear();
	}
	else
	{
		var dt = txtDate.value.split( '-' );
		displayMonth = dt[1] - 1;
		displayYear = dt[0];
	}
}
</script>

</body>
</html>