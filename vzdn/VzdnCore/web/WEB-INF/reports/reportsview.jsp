<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta name="mainTitle" content="Verizon Reports" />
	<title>Verizon Reports</title>
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
				Verizon Reports
			</div>
			<div class="pR10">
		
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
		
					<tr>
						<td colspan="2">
		
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
		
								<tr>
		
									<s:form name="reportsForm" action="reports" method="post" theme="simple" >
									
									<td>
										<table width="100%" border="0" cellspacing="1" cellpadding="0">
											<tr>
		
												<td>
													<table width="100%" border="0" cellpadding="3"
														cellspacing="0">
														<tr>
															<td class="label boldText">
		
																Select Report:
																<br />
																
																
																<s:select name="reportControl" list="reportList" listValue="displayName" label="Select Report" listKey="reportAction" cssClass="inputFieldForm"/>
															</td>
		
															<td width="75%" valign="bottom">
																
																<button class="input_primary" onclick="javascript:processReport();" type="submit">
																	<span><span><span>GO</span>
																	</span>
																	</span>
																</button>
															</td>
		
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

function processReport(){
	
	actiontosubmit = document.reportsForm.reportControl.value;
	document.reportsForm.action=actiontosubmit;
	document.reportsForm.submit;
	 }
//clearSuccessMessages();

	
</script>

</body>
</html>  