 <%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
 
 <%@ page import="com.netpace.vzdn.model.enums.VzdnUserTypes" %>
 <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>
 <%@ include file="../../../commons/taglibs.jsp"%>
 <script src="scripts/nav.js" type=text/javascript></script>
 <SCRIPT src="scripts/togglers.js" type=text/javascript></SCRIPT>
 <SCRIPT src="scripts/accordian.js" type=text/javascript></SCRIPT>
  <!-- TOP NAVIGATION END -->
  <!-- HEADER END -->
  <!-- BODY START -->
  
  <div id=homepageWrapper >
    <div id=bodyWrapper>	
    <s:form name="userMgmtForm" method="post" theme="simple">
      <div id=homepageContainer><a name=content></a>
          <div>
            <div id="page_title2">
              <p> Verizon User Management</p>
            </div>
          </div>
        
        <div id="contentCol">
      		<!-- POD START HERE -->
            <div class="box653 float_left mR10">
               
                <div class="box2">
                    <div class="boxTop box653"><img src="images/shared/elements/boxCLT.jpg" width="6" height="6"></div>
                  <div class="boxContent">
                    <div class="pR10 boxContBM">
                    <!-- POD CONTENT START HERE -->
                    <div class="subHead">User Information</div>
                    <div class="clear10"></div>
                    
                    <div id="formElements">
                        <div class="label boldText">Title: 
                        <s:label name="user.title" />
                        </div>
                        
                        <div class="clear10"></div>
                        <div class="label boldText">First Name:  
                        
                        <s:label name="user.firstName" />
                        
                        <div class="clear10"></div>
                        <div class="label boldText">Last Name: 
                        <s:label name="user.lastName" />
                        
                        <div class="clear10"></div>

						<s:hidden name="user.userType.typeId" id="userTypeId"/>
						<s:hidden name="userTypeRadioChange" id="userTypeRadioChange"/>
						<s:hidden name="user.userId" />
                        <c_rt:set var="dev" value="<%=VzdnUserTypes.Developer.getValue() %>" scope="page"></c_rt:set>
                        <c_rt:set var="vzw" value="<%=VzdnUserTypes.Verizon.getValue() %>" scope="page"></c_rt:set>
						
						<c:if test="${user.userType.typeId == dev }">
						<input type="radio" name="user1userTypetypeId" id="user1userTypetypeId" value="${dev}" onchange="javascript:displayOrganization(0,'${dev}','VtoD');" checked>Developer
						<input type="radio" name="user1userTypetypeId"  id="user1userTypetypeId" value="${vzw}" onchange="javascript:displayOrganization(1,'${vzw}','DtoV');" >Verizon
						</c:if>
						<c:if test="${user.userType.typeId != dev }">
						<input type="radio" name="user1userTypetypeId" id="user1userTypetypeId" value="${dev}" onchange="javascript:displayOrganization(0,'${dev}','VtoD');" >Developer
						<input type="radio" name="user1userTypetypeId" id="user1userTypetypeId" value="${vzw}" checked onchange="javascript:displayOrganization(1,'${vzw}','DtoV');" >Verizon
						</c:if>
						
						
                        
                        
                        
                    <p class="sepratorGray"><img src="images/s.gif" width="1" height="1"></p>
                    <!-- ACCORDIAN MENU START HERE -->
                	 <div style="display:table;">
                      
                    <div class="" id="managerOrg" style="display:table; margin-top:5px !important;">
                        <div class="subHead">Manager Organization</div>
                        <div class="clear10"></div>
                       	<div>
                        <input type="radio" name="managerOrganizationType" id="managerOrgVerizon" onclick="javascript:selectOrgType(0);" >Verizon
						<p>
						<input type="radio" name="managerOrganizationType" id="managerOrgOther" onclick="javascript:selectOrgType(1);" >Other
						&nbsp;
						<s:textfield name="user.managerOrganization"  id="user.managerOrganization" cssClass="inputFieldForm" />
						<p></p>
						</div>
					</div>
						<div class="clear10"></div>
 						<div>
 						<div class="subHead">User Roles</div>
						 <s:optiontransferselect
						     label="Manager Roles"
						     name="availableRoles"
						     id="availableRoles"
						     leftTitle="Available Roles"
						     rightTitle="Assigned Roles"
						     list="availableRoles"
  							 multiple="true"
						     listKey="roleId"
						     listValue="roleName"
						     doubleList="user.roles"
						     doubleListKey="roleId"
						     doubleListValue="roleName"
						     doubleName="assignedRoles"
						     doubleId="assignedRoles"
						     doubleMultiple="true"
						 />
						</div>
                    
                </div>
				<!-- ACCORDIAN MENU END HERE -->
                    <div class="clear10"></div>
                    <!-- POD CONTENT END HERE -->
                    </div>
                    </div>
                    <div class="boxBottom"><img src="images/shared/elements/boxCLB.jpg" width="6" height="6"></div>
                </div>
            </div>
            <!-- POD END HERE -->
            <div class="clear14"></div>
            <div style="padding-left:10px;">
                <div>
                	<BUTTON class="input_primary mR5" onclick="javascript:postUserMgmtForm();"><SPAN><SPAN><SPAN>Submit</SPAN></SPAN></SPAN></BUTTON>
                	
                    
                    <BUTTON class="input_primary mR5" onclick="javascript:cancel();"><SPAN><SPAN><SPAN>Cancel</SPAN></SPAN></SPAN></BUTTON>
                    
                    
                </div>
            </div>
      	</div>
        
      </div>
    </div>
   
  </div>
  </s:form>
 
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
  
  	//alert(document.userMgmtForm.assignedRoles.value);
  	array = document.userMgmtForm.assignedRoles.options;
  	for(i=0; i < array.length;i++){
  		array[i].selected=true;
  	}
  	
	document.userMgmtForm.action="editsaveuser.action";
	document.userMgmtForm.submit;
	

	  }

  function cancel(){
	document.userMgmtForm.action="listusers.action";
	document.userMgmtForm.submit;
	}
	  
  
  
  function displayOrganization(val,uType,change){
  	//alert(uType+" and "+uType);
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
  </script>
  