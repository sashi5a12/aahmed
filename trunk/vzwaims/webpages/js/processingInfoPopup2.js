/******************************************
* DHTML Ad Box (By Matt Gabbert at http://www.nolag.com)
* Visit http://www.dynamicdrive.com/ for full script
* This notice must stay intact for use
******************************************/


function showProcessingInfoPopup(){
		var ns=(document.layers);
		var ie=(document.all);
		var w3=(document.getElementById && !ie);
		var calunit=ns? "" : "px"
		
		if(!ns && !ie && !w3) return;
		if(ie)		adDiv=eval('document.all.processingInfoPopupDiv.style');
		else if(ns)	adDiv=eval('document.layers["processingInfoPopupDiv"]');
		else if(w3)	adDiv=eval('document.getElementById("processingInfoPopupDiv").style');
	
		if (ie||w3)
			adDiv.visibility="visible";
		else
    	adDiv.visibility ="show";
        
		if (ie)
		{
			documentWidth  =truebody().offsetWidth/2+truebody().scrollLeft-20;
			documentHeight =truebody().offsetHeight/2+truebody().scrollTop-20;
		}	
		else if (ns)
		{
			documentWidth=window.innerWidth/2+window.pageXOffset-20;
			documentHeight=window.innerHeight/2+window.pageYOffset-20;
		} 
		else if (w3)
		{
			documentWidth=self.innerWidth/2+window.pageXOffset-20;
			documentHeight=self.innerHeight/2+window.pageYOffset-20;
		} 
		adDiv.left=documentWidth-200+calunit;adDiv.top =documentHeight-200+calunit;
        shimPopupDiv('processingInfoPopupDiv', true);
		setTimeout("showProcessingInfoPopup()",100);

}

function truebody(){
return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body
}

