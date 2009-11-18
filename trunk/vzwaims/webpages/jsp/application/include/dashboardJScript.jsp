function submitForm()
{
	if (typeof document.forms[0].listSelectedDevices != "undefined"){
		try{ 
			select_all(document.forms[0].listSelectedDevices);
		} catch (er){
			//alert("typeof listSelectedDevices " + typeof(document.forms[0].listSelectedDevices));
		}
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