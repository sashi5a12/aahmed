<%@ include file="/commons/taglibs.jsp"%>

<table align="center" >
<tr>

<td>
	
<display:table style="border:0px" name="userlist"  requestURI="" pagesize="5" class="selectFeaturesTable"> 
  
  <display:column property="userName" escapeXml="true" style="width: 9%" sortable="true" title="User Name" url="/edituser.action" paramId="id" paramProperty="userId"  />
  
  <display:column property="userType.typeValue" sortable="true"	 style="width: 9%" title="User Type"/>
  
  <display:column property="firstName"   style="width: 9%" title="First Name" />
  
  <display:column property="lastName"   style="width: 9%" title="Last Name" />
  
</display:table>
</td></tr>
</table>