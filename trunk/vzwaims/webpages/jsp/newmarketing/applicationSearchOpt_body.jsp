<%@	page language="java" %>
<%@	taglib uri="/WEB-INF/struts-bean.tld"	prefix="bean"	%>
<%@	taglib uri="/WEB-INF/struts-html.tld"	prefix="html"	%>
<%@	taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"	%>
<%@	taglib uri="/WEB-INF/struts-template.tld"	prefix="template"	%>
<%@	taglib uri="/WEB-INF/struts-nested.tld"	prefix="nested"	%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td width="20">&nbsp;</td>
		<td width="100%">
			<span	class="aimsmasterheading">Search Applications</span>
		</td>
	</tr>
	<tr>
		<td width="20">&nbsp;</td>
		<td align="left" height="20">
         <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
               <td> 
            	<logic:messagesPresent>
      			<html:messages id="error" header="errors.header" footer="errors.footer">
      				<bean:write name="error"/><br />
      			</html:messages>
            	</logic:messagesPresent>		
         		</td>
         	</tr>
            <tr>
               <td> 
            	<logic:messagesPresent>
         			<html:messages id="message"	message="true" header="messages.header"	footer="messages.footer">
         				<bean:write	name="message"/><br	/>
         			</html:messages>
            	</logic:messagesPresent>		
         		</td>
         	</tr>
            <tr>
              <td align="center" valign="middle"> 
   				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr><td width="100%">
						<html:form action="/mktSearchApplication.do">
						<input type="hidden" name="task" value="search">
						<table class="sectable" width="100%">
							<tr class="sectitle"><td class="aimssecheading">Search Criteria</td></tr>
								<tr>
		   						<td	align="center" valign="middle" bgcolor="#EBEBEB">
		   							<table width="100%"	border="0" cellspacing="7"	cellpadding="0">
	   									<tr>
	   										<td	class="modFormFieldLbl">
	   											Deck Placement
	   										</td>
	   										<td	class="modFormFieldLbl">
	   											Single-Player/Multi-Player
	   										</td>
	   									</tr>
	   									<tr>
	   										<td	class="modFormFieldLbl">
													<html:select property="deckId">
													<html:option value="0">&nbsp;</html:option>
													<html:optionsCollection	name="DecksListing" label="deckName" value="deckId"/>
													</html:select>
	   										</td>
	   										<td	class="modFormFieldLbl">
													<html:select property="singleMultiplayer" >
				                        		<html:option value=""> </html:option>
				                        		<html:option value="S">Single-Player</html:option>
				                        		<html:option value="M">Multi-Player</html:option>
				                        		<html:option value="B">Both</html:option>
				                        	</html:select>
	   										</td>
	   									</tr>
	   									<tr>
	   										<td	class="modFormFieldLbl">
	   											Application Title
	   										</td>
	   										<td	class="modFormFieldLbl">
	   										  Developer Name
	   										</td>
	   									</tr>
	   									<tr>
	   										<td	class="modFormFieldLbl">
													<html:text property="appTitle" size="25"/>
	   										</td>
	   										<td	class="modFormFieldLbl">
													<html:text property="companyName" size="25"/>
	   										</td>
	   									</tr>
	   									<tr>
	   										<td	class="modFormFieldLbl">
	   											Supported Devices
	   										</td>
	   										<td	class="modFormFieldLbl">
	   											&nbsp;
	   										</td>
	   									</tr>
	   									<tr>
	   										<td	class="text">
													<html:select property="deviceIds" multiple="true" size="5">
													<html:optionsCollection	name="DevicesListing" label="deviceModel" value="deviceId"/>
													</html:select><br/><span class="txtDisclaim">(Hold Ctrl and then click for multiple selection)</span>
	   										</td>
	   										<td	class="modFormFieldLbl">
													&nbsp;
	   										</td>
	   									</tr>
		   							</table>
		   						</td>
      	      				</tr>
	                     </table>
					</td></tr>
					<tr>
						<td height="25"	align="right" valign="middle" bgcolor="#FFFFFF">
							<input type="image"	name="Submit" <bean:message key="images.submit.button.lite"/> alt="Submit"/>
							<a href="mktApplication.do?task=list"><img name="Cancel" <bean:message key="images.cancel.button.lite"/> alt="cancel"/></a>
						</td>
					</tr>
					</html:form>
   				</table>
            </td>
         </tr>
       </table>
	   </td>
   </tr>
</table>




