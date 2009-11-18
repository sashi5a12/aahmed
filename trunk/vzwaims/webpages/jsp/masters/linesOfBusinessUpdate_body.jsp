<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<bean:parameter id="taskForThisPage" name="task" value="Sorry No Value!"/>
<html:form action="/linesOfBusinessInsUpd.do" > 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  	<tr> 
    	<td width="20">&nbsp;</td>
       	<td width="100%">
       		<span class="aimsmasterheading">
               <bean:message key="LinesOfBusinessForm.welcome" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
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
    
	        		<html:hidden  property="lineOfBusinessId" />
				<logic:match name="taskForThisPage" scope="page" value="create">
					<html:hidden  property="task" value="create" />
				</logic:match>
				<logic:match name="taskForThisPage" scope="page" value="edit">
					<html:hidden  property="task" value="edit" />
				</logic:match>				
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
                    <td width="100%">
					    <table class="sectable" width="100%">
                            <tr class="sectitle">
                                <td colspan="2" class="aimssecheading">
						
  				   				  				<bean:parameter id="taskForThisPage" name="task" value="Nopes"/>
												<logic:match name="taskForThisPage" scope="page" value="create">
													<bean:message key="LinesOfBusinessForm.createHeading" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
												</logic:match>
												<logic:match name="taskForThisPage" scope="page" value="edit">
													<bean:message key="LinesOfBusinessForm.updateHeading" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>
												</logic:match>
		                		</td>
                            </tr>
                            <tr>
                                <td class="modFormFieldLbl"><bean:message key="LinesOfBusinessForm.lineOfBusinessName" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/><span class="mainReqAstrx">*</span>:</td>
                                
                                <td class="text" valign="top">
                           			 <html:text  property="lineOfBusinessName" size="30" maxlength="50"/>
                                </td>
                                
                            </tr>
							<tr>
                                <td class="modFormFieldLbl"><bean:message key="LinesOfBusinessForm.lineOfBusinessDescription" bundle="com.netpace.aims.action.MASTERS_MESSAGE"/>:</td>
                                
                                <td class="text" valign="top">
                           			 <html:textarea property="lineOfBusinessDesc" rows="5" cols="30"/>
                                </td>
                                
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
         		 	<a href='/aims/linesOfBusinessViewDelete.do?task=view' class='a'>
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

