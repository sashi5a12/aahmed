
<%@page import="com.netpace.aims.util.AimsConstants"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.netpace.aims.controller.wapoptout.WapOptoutForm"%>
<%@page import="org.apache.struts.action.ActionForm"%>

<script language="javascript">
	var helpbypassUrls = "<bean:message key="WapOptoutForm.help.bypassUrls" bundle="com.netpace.aims.action.WAP_OPTOUT"/>";
	var tableSize=0;
	function addURLRow(tableName, inputName, inputValue){
	        var table = document.getElementById(tableName);
	        var tableBody=table.getElementsByTagName('tbody');
	        var newRowId = "row"+ (++tableSize);
	        var newRow=document.createElement("tr");
	        newRow.id = newRowId;
	        
	        var cell=document.createElement("td");
	        cell.style.padding="0px";
	        cell.width="550px";
	        var cellInputText = "<input type='text' class='inputField' name='"+inputName+"' size='60' maxlength='255'   onblur='validateUrl(this);'  value='"+inputValue+"' style='width:550px;'>";
	        cell.innerHTML=cellInputText;
	        newRow.appendChild(cell);
	        
	        cell=document.createElement("td");
	        cell.style.padding="0px 0px 0px 5px"
	        cell.style.verticalAlign="middle";
	        cell.style.textAlign="left";
	        var cellAnchorText = '<a href="javascript:removeURLRow(\''+tableName+'\',\''+newRowId+'\', false);//"><bean:message key="images.delete.icon"/></a>';
	        cell.innerHTML=cellAnchorText;
	        newRow.appendChild(cell);
	        
	        tableBody[0].appendChild(newRow);
	        expandURLTable();

	
	}//end addURLRow
	
	function removeURLRow(tableName, rowId, skipConfirmMsg) {
        var msg="Are you sure you want to delete?";
		var table = document.getElementById(tableName);
		var len = 0;
        if(table && table.rows) {
            len=table.rows.length;
        }
        if (len > 1){
	        if (skipConfirmMsg || confirm(msg)) {
	            var row = document.getElementById(rowId);
	            var collapseRowIndex = 3;
	            var collapseTextInCell = '';
	            var expandCollapseDiv = document.getElementById('divExpandCollapse');
				
	            table.deleteRow(row.rowIndex);
	            len = (table && table.rows) ? table.rows.length : 0 ;
	
	            if(len <=collapseRowIndex) {
	                if(expandCollapseDiv) {
	                    expandCollapseDiv.innerHTML='';
	                }
	            }
			}
		}
        if(table && table.rows) {
            len = table.rows.length;
            var limit =  len <=collapseRowIndex ? len : collapseRowIndex;//
            for(var i=0; i<limit; i++) {//enable first 3 fields                
                table.rows[i].style.display="";
            }
        }
    }//end removeURLRow
    
    function removeEmptyURLs(removeStartIndex,eleName) {
        if(eleName) {
            if(eleName.length && eleName.length>0) {
                for(var i=removeStartIndex; i<eleName.length; i++) {
                    if(eleName[i].value == "") {
                        eleName[i].disabled = true;
                    }
                }
            }
            else {
                if(eleName.value == "") {
                    eleName.disabled = true;
                }
            }
        }        
    }//end removeEmptyURLs	
    var urlTablecollapsed = false;
    var onCollapseText = '[Expand]';//default value    
    var onExpandText = '[Collapse]';//default value
    
    function collapseURLTable() {
       
       <%--
        var anchorText = '<a href="javascript:expandURLTable();//">'+onCollapseText+'</a>';
        --%>
        var anchorText = '';
        var expandCollapseDiv = document.getElementById('divExpandCollapse');
        var addRowDiv = document.getElementById('divAddRow');

        var table = document.getElementById('tblBypassURLs');

		<%
			int bypassUrlsRowsActive=3;
	
			WapOptoutForm form= (WapOptoutForm) request.getAttribute("WapOptoutForm"); 
			if( form != null
			        && ( form).getBypassUrls()!= null)
			    {
				bypassUrlsRowsActive=((WapOptoutForm) (request.getAttribute("WapOptoutForm"))).getBypassUrls().length;
			}
		%>

        var collapseRowIndex = <%= bypassUrlsRowsActive  %>;
        var len = 0;

        if(table && table.rows) {
            len= table.rows.length;
        }
        if(expandCollapseDiv) {
            if(len <=collapseRowIndex) {
                try {
                    expandCollapseDiv.innerHTML = '';
                }
                catch(err){}
                return;
            }
            else {
                try {
                    expandCollapseDiv.innerHTML = anchorText;
                }
                catch(err){}
            }
        }
        //hide divs for expanded state
        for(var i=collapseRowIndex; i<len; i++) {
            table.rows[i].style.display="none";
        }
        urlTablecollapsed = true;
    }//end collapseURLTable


    function expandURLTable() {
       <%--
       // var anchorText = '<a href="javascript:collapseURLTable();//">'+onExpandText+'</a>';
       --%>
       
        var anchorText= "";
        var expandCollapseDiv = document.getElementById('divExpandCollapse');
        var divBypassURLs = document.getElementById('divBypassURLs');
        var addRowDiv = document.getElementById('divAddRow');

        var table = document.getElementById('tblBypassURLs');
        var expandRowIndex = 3;
        var len = 0;

        if(table && table.rows) {
            len= table.rows.length;
        }

        if(expandCollapseDiv) {
            if(len <=expandRowIndex) {
                try {
                    expandCollapseDiv.innerHTML = '';
                }
                catch(err){}
                return;
            }
            else {
                try {
                    expandCollapseDiv.innerHTML = anchorText;
                }
                catch(err){}
            }
        }
        if(divBypassURLs) {
            divBypassURLs.style.display="";
        }
        if(addRowDiv) {
            addRowDiv.style.display="";
        }
        for(var i=expandRowIndex; i<len; i++) {
            table.rows[i].style.display="";
        }
        urlTablecollapsed = false;
    }//end expandURLTable
    
    function openTooltip(){
        var url="/aims/public/wapOptoutTooltip.jsp";
        var wind=window.open(url,"tooltip","resizable=1,width=850,height=600,scrollbars=1,screenX=100,left=100,screenY=30,top=20");        
        wind.focus();
    }
</script>

<script type="text/javascript">
<!--
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}
String.prototype.ltrim = function() {
	return this.replace(/^\s+/,"");
}
String.prototype.rtrim = function() {
	return this.replace(/\s+$/,"");
}
String.prototype.blank = function() {
    return /^\s*$/.test(this);
}

//-->
</script>

<script type="text/javascript">

var errMessage="Please enter a valid URL. default";	

function isValid(fld){
	url=fld.value.trim();

    var hasError=false;
	
	if(url==''  ){
		return true;
	}

	if(url.test(/\s/) ){
        hasError=true;
        errMessage="url contains spaces";
	}
	
	if( url.test(/:[\d]/) ){
	    hasError=true;
	    errMessage="url contains port number";
	}
	
	if( url.test(/:\/{2}/) ){
	       hasError=true;
	       errMessage="url contains protoccccole start";
	}

	if( url.test(/^(.)*\*([^\.])(.)*$/) ){
	       hasError=true;
	       errMessage="url contains charectors followed by *";
	}
	
	
	if( url.test(/^(.)*\.\*$/) ){
	       hasError=true;
	       errMessage="url ends with *";
	}
	
	if( !hasError ){
		var dup=isDuplicate();
		if( !( dup == -1 ) 
			&& url == document.getElementsByName('bypassUrls')[i].value.trim()
		){
			hasError=true;
			errMessage="Duplicate url"			
		}
	}

	if(hasError){
		return false;
	}	
	else{
		return true;
	}	
}


function validateUrl(fld){
	var errMain=document.getElementById('errMain');
	if(!isValid(fld)){
		setTimeout(function() {
				fld.style.backgroundColor='#FFECEC';
				fld.style.fontWeight='bold';
				fld.style.color = "#FF0000";
				if(!  (errMain==null)){
					errMain.innerHTML=errMessage;
				}
			}, 
		0);
	}else{
		setTimeout(function() {
				fld.style.backgroundColor='#FFFFFF';
				fld.style.color ='#000000';
				fld.style.fontWeight='normal';
				if(!  (errMain==null)){
					errMain.innerHTML='';
				}
			}, 
		0);
	}
}

function submitForm(formId){
	var msg="Invalid URL :"
	var hasError=false;
	
	var bypassUrls=document.getElementsByName('bypassUrls');
    var l = bypassUrls.length;
	
	var i=0;
	var validUrl=true;
	var validUrl;

	for(i=0;i<l;i++){
		validUrl=isValid(	bypassUrls[i]	);
		
		if(	 !validUrl	){
			msg=msg	+"\n"+" "+ ""+" "+"  "+bypassUrls[i].value.trim();
			hasError=true;
		}
	}	

	if(hasError){
		setTimeout(function() {alert(msg);}, 0);
	}else{
		document.forms[formId].submit();	
	}
	
}

function isDuplicate(){
	var bypassUrls=document.getElementsByName('bypassUrls');
    var l = bypassUrls.length;

	for(i=0;i<l;i++){
		for(j=i+1;j<l;j++){
			if(
				bypassUrls[i].value.trim() == bypassUrls[j].value.trim()
				&& !bypassUrls[j].value.trim()==''
			){
				errMessage="bypassUrls[i].value.trim():"+bypassUrls[i].value.trim()
							+"  bypassUrls[j].value.trim()"+bypassUrls[j].value.trim();
				return j;
			}
		}
	}
	return -1;
}
</script>


