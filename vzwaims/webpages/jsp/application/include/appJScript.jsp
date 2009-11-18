function submitForm()
{
	if (typeof document.forms[0].listSelectedDevices != "undefined") 
		select_all(document.forms[0].listSelectedDevices);	
	
	if (typeof document.forms[0].listSelectedProgLangs!= "undefined") 
		select_all(document.forms[0].listSelectedProgLangs);
		
	if (typeof document.forms[0].listSelectedBrowsers != "undefined") 
		select_all(document.forms[0].listSelectedBrowsers);	

	if (typeof document.forms[0].listSelectedGeoServices!= "undefined")
    { 
        try{
            select_all(document.forms[0].listSelectedGeoServices);    
        } catch (er){
            //alert("typeof listSelectedRoles " + typeof(document.forms[0].listSelectedRoles));
        }       
    }
    
	//alert("typeof listSelectedRoles " + typeof(document.forms[0].listSelectedRoles));
	
	if (typeof document.forms[0].listSelectedRoles != "undefined")
		try{
		select_all(document.forms[0].listSelectedRoles);	
		} catch (er){
		//alert("typeof listSelectedRoles " + typeof(document.forms[0].listSelectedRoles));
		}		
		
	document.getElementById("divButtons").style.visibility = "hidden";
	document.getElementById("divTabs").style.visibility = "hidden";
	showProcessingInfoPopup();
			
}

function isOKWithDevices()
{
    if ( 
            (typeof document.forms[0].selectedDevicesAlertMessage != "undefined") && 
            (document.forms[0].selectedDevicesAlertMessage.value.length > 0) &&
            (!window.confirm(document.forms[0].selectedDevicesAlertMessage.value))  
       ) 
    { 
        return false;
    }
    else 
        return true;
}
function collapseRow(sectionRow) {
    document.getElementById("row"+sectionRow).style.display='none';
    document.getElementById("spnExpandCollapse"+sectionRow).innerHTML="<a class='a' onclick='javascript:expandRow(\""+sectionRow+"\");'>[+]</a>";
    return false;
}

function expandRow(sectionRow) {
    document.getElementById("row"+sectionRow).style.display='';
    document.getElementById("spnExpandCollapse"+sectionRow).innerHTML="<a class='a' onclick='javascript:collapseRow(\""+sectionRow+"\");'>[-]</a>";
    return false;
}
