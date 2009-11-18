
<%@page import="java.util.Vector"%><%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<script language="javascript">

function validateEmailCvsList(list)
{
	var emailList = list.value;
	if(emailList!=null && emailList!="")
	{
		emailList = emailList.replace(" ", ""); //Remove empty spaces
		var temp = new Array();
		var temp = emailList.split(",");
		flag=true;
		
		for(var i=0;i<temp.length;i++)
		{
			if(!validateEmail(temp[i]))
			{
				flag=false;
			}
		}
	}
	if(!flag)
	{
		list.focus();
		alert("Invalid email address");
	}
}
var holdObject=null;
function validateMultipleEmailsCommaSeparated(obj) {

	if(holdObject==null)//not under processing
	{
		holdObject=obj;
		var value=obj.value;
	    var result = value.split(",");
	    for(var i = 0;i < result.length;i++)
	    if(!validateEmail(result[i]))
	    { 
	    	alert("in valid email address");
	    	setTimeout("setFocus()",0);
	    	
	        return false;     
	     }          
	    return true;
    }
    else
    {
    	alert("Miss");
    	holdObject=null;
     	return false;
     }
}

function setFocus()
{
	if(holdObject!=null)
	{
		holdObject.focus();
		holdObject=null;
	}
}
function validateEmail(emailString) {
    var regEx = /^\w[\w\d\-\+]*(\.[\w\d\-\+]+)*@\w[\w\d\-]+(\.[\w\d\-]+)*\.[a-z]{2,7}$/i ;
    return regEx.test(emailString);
}

function saveForm()
{
	document.forms[0].task.value="edit";
	document.forms[0].submit();
}

</script>
<%@ include file="/common/error.jsp" %>

<div class="btnTopLine">
    <div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="filter"
         title="Filter">
        <div>
            <div>
                <div onclick="saveForm();return false;">Save</div>
            </div>
        </div>
    </div>
</div>

<div id="contentBox" style="float:left" onmousemove="">
<DIV class="homeColumnTab lBox">

<html:form action="/verticalsUpdate" enctype="multipart/form-data" >
<html:hidden property="task"/>
<div class="contentbox">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <tr>
        <div class="lBox">
            <DIV class="headLeftCurveblk"></DIV>
            <H1>Verticals</H1>
            <DIV class="headRightCurveblk"></DIV>
        </div>
    </td>
   
</tr>

<tr>
<td>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="GridGradient">
<th>
		<td>
		&nbsp;
		</td>
</th>
<tr>
	<td>
		<i>Multiple Email Addresses must be comma separated. </i>
	</td>
</tr>
<tr>
	<td>
		<logic:iterate id="vertical" name="VerticalsEmailAddressForm" property="allIndustryVerticals">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
        	<td width="30%">
            <strong><bean:write name="vertical" property="industryName"/>:<strong>
            </td>
            
            <td>
            <html:text styleClass="inputField" size="50"  name="vertical" property="emailAddress" disabled="false" onblur="">
            </html:text>
            <html:hidden name="vertical" property="industryId"/>
            </td>
           </tr> 
           
           </logic:iterate>
		</tr>
		</table>
	</td>
</tr>
</table>
</td>
</tr>
<tr>
	<td>
		 <div class="redBtn" style=" margin-left:5px;float:right; margin-top:10px; margin-bottom:10px" id="Save" title="Save">
	            <div><div><div onclick="saveForm();return false;">Save</div></div></div>
	        </div>
	</td>
</tr>
</table>
</div>

</html:form>
</DIV>
</div>

