 <%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<html:form action="/salesContactInsertUpdate" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
       		<span class="aimsmasterheading">
       		   <bean:message key="SalesContactForm.Welcome"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
       		</span>               
       	</td>
 	</tr>
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">&nbsp;
			
       	</td>
  	</tr>
  	<%@ include  file="/common/error.jsp" %>
  	<tr> 
    	<td width="20">&nbsp;</td>
      	<td  width="100%" align="center" valign="middle" bgcolor="#FFFFFF"> 
                    <logic:match name="aimsSalesContact"	property="task"	 value="create"> 
    			  <html:hidden property="task" value="create"/>
		        </logic:match>  
			    <logic:match name="aimsSalesContact"	property="task"	 value="edit"> 
       			  <html:hidden property="task" value="edit"/>
		        </logic:match>  
		    
    			  <html:hidden  name="aimsSalesContact" property="salesContactId"/>
      
							
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                    <td width="100%">
					    <table class="sectable" width="100%">
                            <tr class="sectitle">
                               <td colspan="2" class="aimssecheading">
					  	   <logic:match name="aimsSalesContact"	property="task"	 value="create"> 
     						  <bean:message key="SalesContactForm.CreateHeading"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
				   		  </logic:match>  
				   
						   <logic:match name="aimsSalesContact"	property="task"	 value="edit"> 
     						  <bean:message key="SalesContactForm.EditHeading"	bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>
						   </logic:match>  
				       </td>
                            </tr>
                             <tr>
                                <td class="modFormFieldLbl" ><bean:message key="SalesContactForm.FirstName" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/><span class="mainReqAstrx">*</span>:</td>
                                <td class="text"> <html:text name="aimsSalesContact" property="firstName" size="35" maxlength="50" /> </td>
                            </tr>

  				     <tr>
                                <td class="modFormFieldLbl" ><bean:message key="SalesContactForm.LastName" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/><span class="mainReqAstrx">*</span>:</td>
                                <td class="text"> <html:text name="aimsSalesContact" property="lastName" size="35" maxlength="50" /> </td>
                            </tr>
 				     <tr>
                                <td class="modFormFieldLbl" ><bean:message key="SalesContactForm.Title" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/><span class="mainReqAstrx">*</span>:</td>
                                <td class="text"> <html:text name="aimsSalesContact" property="title" size="35" maxlength="50" /> </td>
                            </tr>

 				     <tr>
                                <td class="modFormFieldLbl" ><bean:message key="SalesContactForm.Region" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/><span class="mainReqAstrx">*</span>:</td>
                                <td class="text">
                                   <html:select property="regionId" name="aimsSalesContact" value='<%= (String)session.getAttribute("regionId")%>' > 
	                               <html:optionsCollection property="regionList" name="aimsSalesContact" label="regionName"  value="regionId" />
	                             </html:select>
          				 </td>
                            </tr>

        			    <tr>
                                <td class="modFormFieldLbl" ><bean:message key="SalesContactForm.Phone" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/>:</td>
                                <td class="text"> <html:text name="aimsSalesContact" property="phone" size="35" maxlength="50" /> </td>
                            </tr>
				    <tr>
                                <td class="modFormFieldLbl" ><bean:message key="SalesContactForm.EmailAddress" bundle="com.netpace.aims.action.SYSTEM_MESSAGE"/><span class="mainReqAstrx">*</span>:</td>
                                <td class="text"> <html:text name="aimsSalesContact" property="emailAddress" size="35" maxlength="50" /> </td>
                            </tr>
	  		    </table>	
      		    </td>
                </tr>
			</table>
		</td>
	</tr>            
    <tr>
       <td width="20">&nbsp;</td>
	   <td width="100%">
	      <table  width="100%">
		  <tr>
             <td align="left">
         		 	<a href='salesContactViewDelete.do?task=view' class='a'>
				      	<img src="images/back_b.gif"  border="0" alt="Back">
					</a>
       		 </td>
             <td align="right">   
               <img src="images/spacer.gif" width="10" height="1">
                    <input type="image" src="images/save_b.gif" border="0" alt="save">
             </td>
        </tr>
	    </table>
	 </td>
	
    </tr>
</table>
</html:form>

