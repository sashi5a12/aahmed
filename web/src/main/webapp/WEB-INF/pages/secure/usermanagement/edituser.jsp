<%@page import="org.apache.commons.lang.StringUtils"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<script>
    function submitForm(frmName) {
        document.forms[frmName].submit();
    }
</script>
<h1 class="redheading" >Manage Users - Edit User</h1>

<form:form name="editUserFrm" 
           action="${pageContext.request.contextPath}/secure/admin/edituser.do" 
           method="post" 
           commandName="editAdminUserVO"
           >
    <input type="hidden" id="id" name="id" value="${not empty editAdminUserVO.id ? editAdminUserVO.id : ''}" />
    <input type="hidden" id="userName" name="userName" value="${editAdminUserVO.userName}" />
    <input type="hidden" id="emailAddress" name="emailAddress" value="${editAdminUserVO.emailAddress}" />
    <input type="hidden" id="companyDomain" name="companyDomain" value="${editAdminUserVO.companyDomain}" />
    <!-- Gray Box Layout - Two column Starts-->
    <div class="emptyBoxLayouts">
        <div class="twoColmnLayout">
            <spring:hasBindErrors name="editAdminUserVO">
                <div class="error">
                    <ul>
                        <form:errors path="*" element="li" delimiter="</li><li>" id="" />
                    </ul>
                </div>
            </spring:hasBindErrors>
            <c:if test="${expired}"> 
                <div class="error"><ul><li>Your account activation link is invalid or expired. Please complete the account registration form to re-send the account activation link.</li></ul></div>
                        </c:if>
            <div class="columnOne">
                <label class="inputlabel">Email Address</label>
                <div class="label-value">${editAdminUserVO.emailAddress}</div>
                <div class="clearboth"></div> 
                <div class="redstar">*</div><label class="inputlabel">Full Name</label>
                <input id="fullName" name="fullName" class="input" type="text" value="${fn:escapeXml(editAdminUserVO.fullName)}" maxlength="70" />
                <div class="clearboth"></div>
                <div class="redstar">*</div><label class="inputlabel">Phone Number</label>
                <span class="columnTwo">
                    <input id="phoneNumber" name="phoneNumber" class="input" type="text" value="${fn:escapeXml(editAdminUserVO.phoneNumber)}" maxlength="20" />
                </span>
                <div class="clearboth"></div>

                <div class="redstar">*</div><label class="inputlabel">Street Address</label>
                <textarea id="address" name="address" class="textfield" style="width:290px; height:78px;" maxlength="250">${editAdminUserVO.address}</textarea>
                <div class="clearboth"></div>

                <div class="redstar">*</div><label class="inputlabel">City/town</label>
                <input id="city" name="city" class="input" type="text" value="${fn:escapeXml(editAdminUserVO.city)}" maxlength="100" />
                <div class="clearboth"></div>        
                <div class="redstar">*</div><label class="inputlabel">Activate</label>

                <table width="200" border="0" cellpadding="0" cellspacing="0" style="float:left;">
                    <tr>
                        <td width="81" valign="bottom"><input class="checkbox" id="activeStatus" name="isEnable" type="radio" value="1" ${editAdminUserVO.isEnable ? 'checked' : ''} />
                            <label for="activeStatus" class="checkboxlabel font13" style="margin-left:4px; margin-top:2px;">Active</label></td>

                        <td width="119" valign="bottom"><input class="checkbox" id="inactiveStatus" name="isEnable" type="radio" value="0" ${editAdminUserVO.isEnable ? '' : 'checked'} />
                            <label for="inactiveStatus" class="checkboxlabel font13" style="margin-left:4px; margin-top:2px;">Inactive</label></td>
                    </tr>
                </table>
                <div class="clearboth"></div>    
            </div>
            <div class="columnTwo">
                <label class="inputlabel">Username</label>
                <div class="label-value">${editAdminUserVO.userName}</div>

                <div class="spacebox2"> 
                    <div style="display:none;">                
                        <label class="inputlabel">Company Domain</label>
                        <div class="label-value">${fn:escapeXml(editAdminUserVO.companyDomain)}</div>

                        <div class="clearboth"></div> </div>
                </div>

                <label class="inputlabel">Mobile Phone Number</label>
                <input id="mobilePhoneNumber" name="mobilePhoneNumber" class="input" type="text" value="${fn:escapeXml(editAdminUserVO.mobilePhoneNumber)}" maxlength="20" />
                <div class="clearboth"></div>

                <div class="redstar">*</div>
                <label class="inputlabel">Zip Code/Postal Code</label>
                <span class="columnOne">
                    <input id="zip" name="zip" class="input" type="text" value="${fn:escapeXml(editAdminUserVO.zip)}" maxlength="20" />
                </span>
                <div class="clearboth"></div>

                <div class="redstar">*</div><label class="inputlabel">State/Province</label>
                <input id="state" name="state" class="input" type="text" value="${fn:escapeXml(editAdminUserVO.state)}" maxlength="100" />
                <div class="clearboth"></div>
                
            <div class="redstar">*</div><label class="inputlabel"><fmt:message key="editAdminUserVO.country"/></label> <label for="select"></label>
            <form:select cssClass="selct" path="country" id="country">
                <form:option value="" label="--- Select ---" />
                <form:options items="${populatedFormElements['countryList']}" />
            </form:select>
                
                
                
                
                
                
                
                <div class="clearboth"></div>     
                <div class="clearboth"></div>
            </div>
            <div class="clearboth"></div>
        </div>



        <%--


        <div class="twoColmnLayout">
            <div class="redstar">*</div> <label class="inputlabelBig">User Roles</label>
            <div class="columnOne">
                
                        <form:checkboxes 
                            items="${systemRolesList}"
                                path="roles" 
                                itemLabel="displayTitle"
                                itemValue="roleName"
                                delimiter="<br />"
                                ></form:checkboxes>
                
                <c:forEach var="item" varStatus="counter" items="${systemRolesList}">
                    <form:checkbox path="roles" label="${item.displayTitle}" value="${item.roleName}" ></form:checkbox>
                </c:forEach>
                
                
            </div>
            <div class="columnTwo">
                
            </div>
            <div class="clearboth"></div>  
        </div>
        <div class="clearboth"></div>  
        --%>
        <div class="twoColmnLayout">
            <div class="redstar">*</div> <label class="inputlabelBig">User Roles</label>
            <div class="columnOne">
                <c:forEach var="item" varStatus="counter" items="${systemRolesList}">
                    <c:if test="${(counter.index+1) % 2 != 0}">
                        <form:checkbox path="roles" label="${item.displayTitle}" value="${item.roleName}" ></form:checkbox>
                            <div class="clearboth"></div>
                    </c:if>
                </c:forEach>
            </div>
            <div class="columnTwo">
                <c:forEach var="item" varStatus="counter" items="${systemRolesList}">
                    <c:if test="${(counter.index+1) % 2 == 0}">
                        <form:checkbox path="roles" label="${item.displayTitle}" value="${item.roleName}" ></form:checkbox>
                            <div class="clearboth"></div>
                    </c:if>
                </c:forEach>
            </div>
            <div class="clearboth"></div>  
        </div>
        <div class="clearboth"></div>  
    </div>
</form:form>

<!-- buttons starts --> 

<a class="button floatRight marginTop20 marginLeft10" href="${pageContext.request.contextPath}/secure/admin/userslist.do" ><span class="gray">Cancel</span></a> 
<a class="button floatRight marginTop20" href="javascript:submitForm('editUserFrm');" ><span class="red">Update</span></a>  

<div class="clearboth"></div>

<!-- buttons ends -->
