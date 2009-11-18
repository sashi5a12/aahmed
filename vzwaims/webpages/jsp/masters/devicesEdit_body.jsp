<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>


<html:errors/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="30">&nbsp;</td>
    <td width="247">
    <p><strong><font color="#333333" size="+1" face="Verdana, Arial, Helvetica", sans-serif">Device Management</font></strong>
    <strong><font color="#333333" size="2" face="Verdana, Arial, Helvetica, sans-serif">Update Device</font></strong><br></p> </td>
    <td width="496"><div align="left"><font color="red" size="1" face="Verdana, Arial, Helvetica, sans-serif"><b></div></b></font></td>
  </tr>
  <tr> 
    <td width="30">&nbsp;</td>
    <td colspan="2"> 
    <form name="frmAllianceReg" action="VZW_ManageDevices.jsp" method="post">
    
        <table width="498" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td bgcolor="#999999" height="1"><img src="images/spacer.gif" width="20" height="1" /></td>
          </tr>
          <tr> 
            <td align="center" valign="middle" bgcolor="#EBEBEB"> <table width="100%" border="0" cellspacing="10" cellpadding="0">
                <tr> 
                  <td class="text" align="right"><strong>Phone Model</strong></td>
                  <td class="body" align="left"><input name="txtUserName" type="text" class="input" size="20" value="V90"/></td>
                </tr>
                <tr> 
                  <td class="text" align="right"><strong>Phone Manufacturer</strong></td>
                  <td class="body" align="left"><input name="txtLoginID" type="text" class="input" size="20" value="LG Mobile Phones"/></td>
                </tr>
                <tr> 
                  <td class="text" align="right"><strong>Firmware</strong></td>
                  <td class="body" align="left">
                      <select name="a" multiple>
                          <option value="" selected>Address Book</option>
                          <option value="">Games</option>
                          <option value="">Tones</option>
                      </select>
                  </td>
                </tr>                
                <tr> 
                  <td class="text" align="right"><strong>Middleware</strong></td>
                  <td class="body" align="left">
                      <select name="a" multiple>
                          <option value="" >WAP</option>
                          <option value="" selected>J2ME</option>
                      </select>
                  </td>
                </tr>
                
                <tr> 
                  <td class="text" align="right"><strong>VZW Platforms supported</strong></td>
                  <td class="body" align="left">
                      <select name="a" multiple>
                          <option value="" selected>SMS</option>
                          <option value="">MMS</option>
                          <option value="" select>J2ME</option>
                          <option value="">BREW</option>
                          <option value="" selected>WAP</option>
                          
                      </select>
                  </td>
                </tr>
                
                <tr> 
                  <td class="text" align="right"><strong>Manufacturer web site URL</strong><br>(optional)</td>
                  <td class="body" align="left"><input name="txtLoginID" type="text" class="input" size="30" value="http://www.lgmobilephone.com"/></td>
                </tr>

              </table></td>
          </tr>
          <tr> 
            <td height="1" bgcolor="#999999"><img src="images/spacer.gif" width="20" height="1" /></td>
          </tr>
          <tr> 
            <td height="25" align="right" valign="middle"> <input type="image" name="AllSubmit" src="images/submit_b.gif" width="52" height="15" border="0" /> 
            </td>
          </tr>
          <tr> 
            <td height="1" bgcolor="#999999"><img src="images/spacer.gif" width="20" height="1" /></td>
          </tr>
        </table>
      </form></td>
  </tr>
</table>
