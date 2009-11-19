<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<html>
 
 <%@ page import="com.netpace.vzdn.model.enums.VzdnUserTypes" %>
 <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>
 <%@ include file="../../../commons/taglibs.jsp"%>
 
 <script src="scripts/nav.js" type=text/javascript></script>
 <SCRIPT src="scripts/togglers.js" type=text/javascript></SCRIPT>
 <SCRIPT src="scripts/accordian.js" type=text/javascript></SCRIPT>
  <!-- TOP NAVIGATION END -->
  <!-- HEADER END -->
  <!-- BODY START -->


<head>

<meta name="mainTitle" content="Edit User" />

	<title>Edit User</title>

</head>

<body>


<s:if test="hasFieldErrors()">
						    <div class="alertBox">						    	
						      <s:iterator value="fieldErrors">						      
						          <s:iterator value="value">						          
						          	   <s:property/>						          	   
						          </s:iterator>						      
								  <br/>
						      </s:iterator>
						   </div>
</s:if>

<s:form name="userMgmtForm" method="post" theme="simple">

<div id="contentCol">
  		<!-- POD START HERE -->
        <div class="box984">

            <div class="box2">
                <div class="boxTop box984"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
                <div class="boxContent">
				<div class="pR10 boxContBM">
                <!-- POD CONTENT START HERE -->
				
<div class="box321 float_left">
                    <div class="subHead">User Information</div>
                    <div class="clear10"></div></div>
                    <div class="box321 float_left mL50">
                    <div class="subHead">Activate</div>
                    <div class="clear10"></div></div>
                    <div class="box321 mL20 borderR float_left">
                    <table width="100%" border="0" cellspacing="0" cellpadding="4">
  <tr>
    <td width="40%" align="left"><strong>Email Address :</strong></td>
    <td width="60%"><s:label name="user.userName" /></td>
  </tr>
  
  <tr>
    <td align="left"><strong>Title :</strong></td>
    <td><s:label name="user.title" /></td>
  </tr>
  
  <tr>
    <td align="left"><strong>First Name :</strong></td>
    <td><s:label name="user.firstName" /></td>
  </tr>
  <tr>
    <td align="left"><strong>Last Name :</strong></td>
    <td><s:label name="user.lastName" /></td>
  </tr>
  
  <tr>
    <td align="left"><strong>Phone Number :</strong></td>
    <td><s:label name="user.phoneNumber" /></td>
  </tr>

  <tr>
    <td align="left"><strong>Fax Number :</strong></td>
    <td><s:label name="user.faxNumber" /></td>
  </tr>
  

  <tr>
    <td align="left"><strong>Mobile Number :</strong></td>
    <td><s:label name="user.mobileNumber" /></td>
  </tr>
  
  <tr>
    <td align="left"><strong>Country :</strong></td>
    <td><s:label name="user.country" /></td>
  </tr>
  
  <tr>
    <td align="left"><strong>GTM Company ID :</strong></td>
    <td><s:label name="user.gtmCompanyId" /></td>
  </tr> 
   
  
</table>
                    </div>
                    <div class="box321 mL30 float_left">
         
                      <table width="30%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td>Yes</td>
                          <td>
						  <input type="radio" tabindex="1" name="user.statusType.typeId" value="5"
					<c:if test='${user.statusType.typeId==5}'>checked="checked" </c:if>/>
						  </td>
                          <td>No</td>
                          <td>
						  <input type="radio" tabindex="1" name="user.statusType.typeId" value="6"
					<c:if test='${user.statusType.typeId==6}'>checked="checked" </c:if>/>
						  </td>
                        </tr>
                      </table>
                      
                    </div>
<div class="clear10"></div>
                    <p class="sepratorGray mR10"><img src="images/s.gif" width="1" height="1"></p>
                  
                    <div class="subHead">User Type</div>
                    <div class="clear10"></div>
<div class="mL20">
<s:hidden name="user.userType.typeId" id="userTypeId" />
		<s:hidden name="userTypeRadioChange" id="userTypeRadioChange" />
		<s:hidden name="user.userId" />
		<s:hidden name="hiddenRolesString"/>
	
	<c_rt:set var="dev" value="<%=VzdnUserTypes.Developer.getValue()%>"
			scope="page"></c_rt:set>
		<c_rt:set var="vzw" value="<%=VzdnUserTypes.Verizon.getValue()%>"
			scope="page"></c_rt:set>
			
			
		<c:if test="${user.userType.typeId == dev }">
		
			<input tabindex="2" type="radio" name="user1userTypetypeId"
				id="user1userTypetypeId1" value="${dev}"
				onClick="javascript:displayOrganization(0,'${dev}','VtoD'); "
				checked/>Developer
			
			<input tabindex="3" type="radio" name="user1userTypetypeId"
				id="user1userTypetypeId2" value="${vzw}"
				onClick="javascript:displayOrganization(1,'${vzw}','DtoV'); "/>Verizon
				
		</c:if>
		
		<c:if test="${user.userType.typeId != dev }">
			<input type="radio" tabindex="2" name="user1userTypetypeId"
				id="user1userTypetypeId1" value="${dev}"
				onClick="javascript:displayOrganization(0,'${dev}','VtoD'); "/>Developer
				
			<input type="radio" tabindex="3" name="user1userTypetypeId"
				id="user1userTypetypeId2" value="${vzw}" checked
				onClick="javascript:displayOrganization(1,'${vzw}','DtoV');"/>Verizon
		</c:if>


</div>


					
					
					
					
					
                    <div class="clear10"></div>
                    <p class="sepratorGray mR10"><img src="images/s.gif" width="1" height="1"></p>	
			<div >	

<div id="managerOrg">			
<div class="subHead">Manager Organization</div>
                    <div class="clear10"></div>
					
					<div class="mL20">
                        <div >
				<input type="radio" tabindex="4" name="managerOrganizationType"
					id="managerOrgVerizon" onClick="javascript:selectOrgType(0)">
				Verizon
				

					<input type="radio" tabindex="5" name="managerOrganizationType"
						id="managerOrgOther" onClick="javascript:selectOrgType(1)">
					Other &nbsp;
					<s:textfield name="user.managerOrganization"
						id="user.managerOrganization" cssClass="inputFieldForm" />
						

						
						</div>
						
												
						
						</div>
		<div class="clear10"></div>
<p class="sepratorGray mR10"><img src="images/s.gif" width="1" height="1"></p>				
				
    </div>
    
     
                    </div>
					
	
	
					
					
					
             
 
<div id="formElements">


	
	
	






	
	<!-- ACCORDIAN MENU START HERE -->
	

		<div class="selectTag">
			<div class="subHead">
				User Roles
			</div>
			<div class="mL20">
					<table border="0">
					<tbody>
						<tr>
							<td><label for="leftTitle">Available Roles</label></td>
							<td>&nbsp;</td>
							<td><label for="rightTitle">Assigned Roles</label></td>
						</tr>
						<tr>
							<td valign="top">
								<s:select multiple="true" tabindex="6" cssClass="forlistBox"
								name="editableRoles1" id="roles1" list="availableRoles"
								required="true" theme="" size="15" listValue="roleName" listKey="roleId"/>
							</td>
							<td valign="middle" align="center">
								<input class="bbutton" tabindex="7" type="button" onclick='copyToList("roles1","roles2"); return false;' value=">>"/>
								<br/><br/>
								<input class="bbutton" tabindex="8" type="button" onclick='copyToList("roles2","roles1"); return false;' value="<<"/>
								<br/><br/>
							</td>
							<td valign="top">
								<s:select multiple="true" tabindex="8" cssClass="forlistBox"
								name="assignedRoles" id="roles2" list="editableRoles"
								theme="" size="15" listValue="roleName" listKey="roleId"/>
							</td>
						</tr>
					</tbody>
					</table>
				



				
				
				
				
			</div>
		</div>
		
	<div class="clear10"></div>	
		

<div class="clear10"></div>

	</div>
	<!-- ACCORDIAN MENU END HERE -->

	<div style="padding-left: 10px;">
		<div>
			<BUTTON tabindex="10" class="input_primary mR5"
				onClick="javascript:postUserMgmtForm(); return false;">
				<SPAN><SPAN><SPAN>Submit</SPAN> </SPAN> </SPAN>
			</BUTTON>

			
			
			<BUTTON tabindex="11" class="input_secondary mR5" onClick="javascript:cancel(); return false;">
				<SPAN><SPAN><SPAN>Cancel</SPAN> </SPAN> </SPAN>

			</BUTTON>	
			
			
		</div>
	</div>
	
		</div>
                
                
          <div class="clear10"></div>

	            </div>
                <!-- POD CONTENT END HERE -->
                
				
                 <div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
				 </div>
            </div>
        </div>
        <!-- POD END HERE -->
    <div class="clear14"></div>






        

            
            
   
  </s:form>
  
  
  
  
  
  <!-- Start Here .... -->
 
  <script type="text/javascript">

	function selectOrgType(param){
		
		if(param==0){
			//document.getElementById('managerOrgOther').
			document.getElementById('user.managerOrganization').value="";
			document.getElementById('user.managerOrganization').disabled=true;
		}else{
			document.getElementById('user.managerOrganization').disabled=false;
		}
	
	}
	

  function postUserMgmtForm(){
  
  	//alert(document.userMgmtForm.roles2.value);
  	array = document.userMgmtForm.roles2.options;
  	for(i=0; i < array.length;i++){
  		array[i].selected=true;
  		//alert("value=" + array[i].value);
  	}
  	
	document.userMgmtForm.action="editsaveuser.action";
	document.userMgmtForm.submit();
	

	  }

  function cancel(){
	document.userMgmtForm.action="searchUsersAction.action";
	document.userMgmtForm.submit();
	}
	  
  
  
  function displayOrganization(val,uType,change){  
  
	document.getElementById('userTypeId').value=uType;
	//document.getElementById('managerOrg').style.display="block";
    document.getElementById('userTypeRadioChange').value=change;
    //document.getElementById('userTypeRadioChange').value="VtoD";
 	document.userMgmtForm.action="changeusertype.action";
	document.userMgmtForm.submit();
  }
    
  
  
  function pageLoadOperations(){
	  developerRole = <%=VzdnUserTypes.Developer.getValue() %>
	  usersUserType=document.getElementById('userTypeId').value;
	  //usersUserType=document.userMgmtForm.user.userType.typeId.value; //edituser_user_userId
	  //alert(usersUserType);
	  if(developerRole==usersUserType){
	  	document.getElementById('managerOrg').style.display="none";
	  }else{
	  	document.getElementById('managerOrg').style.display="block";
	  }
	  
	  mgOrg =  document.getElementById('user.managerOrganization');
	  if(mgOrg.value!=null && mgOrg.value!=''){
	  	document.getElementById('managerOrgOther').checked=true;
	  }else{
	  	document.getElementById('managerOrgVerizon').checked=true;
	  	document.getElementById('user.managerOrganization').disabled=true;
	  }
  }
  pageLoadOperations();
  
  
function copyToList(from,to)
{
	fromList = document.getElementById(from); //document.forms[0].elements[from];
	toList = document.getElementById(to); //document.forms[0].elements[to];

	var sel = false;
	for (i=0;i<fromList.options.length;i++) 
	{
	  var current = fromList.options[i];
	  //alert("Current [" + i +"] Value:" + current.selected);
	  if (current.selected)
	  {
		   //alert("Current [" + i +"] Value:" + current.selected);	
		   //alert("You have selected:" + current.value);
		   sel = true;
		   txt = current.text;
		   val = current.value;
		   toList.options[toList.length] = new Option(txt,val);
		   fromList.options[i] = null;
		   i--;
	  }
	}  	
	
	//if (!sel) alert ('You haven\'t selected any options!');
} 
</script>  
</body>
</html>