<script	language="javascript">
    var urlTablecollapsed = false;
    var lastRowId = 0;
    var onCollapseText = '[-]';//default value
    var onExpandText = '[+]';//default value
    function collapseURLTable() {
        var anchorText = '<a href="javascript:expandURLTable();//">'+onCollapseText+'</a>';
        var expandCollapseDiv = document.getElementById('divExpandCollapse');
        var addRowDiv = document.getElementById('divAddRow');

        var table = document.getElementById('tblApplicationURLs');
        var collapseRowIndex = 3;
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
        var anchorText = '<a href="javascript:collapseURLTable();//">'+onExpandText+'</a>';
        var expandCollapseDiv = document.getElementById('divExpandCollapse');
        var divApplicationURLs = document.getElementById('divApplicationURLs');
        var addRowDiv = document.getElementById('divAddRow');

        var table = document.getElementById('tblApplicationURLs');
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
        if(divApplicationURLs) {
            divApplicationURLs.style.display="";
        }
        if(addRowDiv) {
            addRowDiv.style.display="";
        }
        for(var i=expandRowIndex; i<len; i++) {
            table.rows[i].style.display="";
        }
        urlTablecollapsed = false;
    }//end expandURLTable
    function addApplicationURLRow(tableName, inputName, inputValue, showDeleteIcon, showExpandCollapse){
        var table = document.getElementById(tableName);
    	var len = 0;
        if(table && table.rows) {
            len=table.rows.length;
        }
        var tableBody=table.getElementsByTagName('tbody');
        var collapseTextInCell = '';
        var newRowId = "row"+ (lastRowId++);
        var newRow=document.createElement("tr");
        newRow.id = newRowId;
        var cell=document.createElement("td");
        var cellInputText = "<input type='text' class='inputField' name='"+inputName+"' size='60' maxlength='200' value='"+inputValue+"' "+(showDeleteIcon?"":"disabled")+" style='width:375px; margin-bottom:0px;'>";
        var cellAnchorText = '<a href="javascript:removeApplicationURLRow(\''+newRowId+'\', false);//"><bean:message key="images.delete.icon"/></a>';
        cell.className = "modFormFieldLbl";
        cell.vAlign="middle";
        try {
            cell.innerHTML=cellInputText+"&nbsp;"+(showDeleteIcon ? cellAnchorText : "")+collapseTextInCell;
        }
        catch(err) {
        }
        newRow.appendChild(cell);
        tableBody[0].appendChild(newRow);

        expandURLTable();

    }//end addApplicationURLRow
	function removeApplicationURLRow(rowId, skipConfirmMsg) {
        var msg="Are you sure you want to delete?";
        if (skipConfirmMsg || confirm(msg)) {
			var table = document.getElementById('tblApplicationURLs');
			var len = 0;
            if(table && table.rows) {
                len=table.rows.length;
            }
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
        if(table && table.rows) {
            len = table.rows.length;
            var limit =  len <=collapseRowIndex ? len : collapseRowIndex;//
            for(var i=0; i<limit; i++) {//enable first 3 fields                
                table.rows[i].style.display="";
            }
        }
    }//end removeApplicationURLRow
    function enableDisableNetworkUsage(networkUsage) {
        var expandCollapseDiv = document.getElementById('divExpandCollapse');
        var applicationURLLblDiv = document.getElementById('divApplicationURLLbl');
        var applicationURLsDiv = document.getElementById('divApplicationURLs');
        var addRowDiv = document.getElementById('divAddRow');

        if(networkUsage.checked) {
            if(expandCollapseDiv) {
                expandCollapseDiv.style.display="";
            }
            applicationURLLblDiv.style.display="";
            applicationURLsDiv.style.display="";
            addRowDiv.style.display="";

        }
        else {
            removeAllApplicationURLs();

            //hide all divs
            if(expandCollapseDiv) {
                try {
                    expandCollapseDiv.innerHTML = '';
                    expandCollapseDiv.style.display="none";
                }
                catch(err) {}
            }
            applicationURLLblDiv.style.display="none";
            applicationURLsDiv.style.display="none";
            if(addRowDiv) {
                addRowDiv.style.display="none";
            }
        }
    }//end enableDisableNetworkUsage

    function removeEmptyApplicationURLs(removeStartIndex) {
        var frm = document.forms[0];
        if(frm.applicationURLs) {
            if(frm.applicationURLs.length && frm.applicationURLs.length>0) {
                for(var i=removeStartIndex; i<frm.applicationURLs.length; i++) {
                    if(frm.applicationURLs[i].value == "") {
                        frm.applicationURLs[i].disabled = true;
                    }
                }
            }
            else {
                if(frm.applicationURLs.value == "") {
                    frm.applicationURLs.disabled = true;
                }
            }
        }        
    }//end removeEmptyApplicationURLs

    function removeAllApplicationURLs() {
        var table = document.getElementById('tblApplicationURLs');
    	var len = 0;
        if(table && table.rows) {
            len = table.rows.length;
        }
        for(var i=0; i<len; i++) {
            table.deleteRow(0);
        }
    }//end removeAllApplicationURLs
</script>