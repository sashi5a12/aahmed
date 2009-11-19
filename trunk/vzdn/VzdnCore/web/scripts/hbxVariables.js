//<!--WEBSIDESTORY CODE HBX2.0 (Universal)-->
var _hbEC=0,_hbE=new Array;function _hbEvent(a,b){b=_hbE[_hbEC++]=new Object();b._N=a;b._C=0;return b;}
var hbx=_hbEvent("pv");hbx.vpc="HBX0200u";hbx.gn="www35.vzw.com";

//BEGIN EDITABLE SECTION
//CONFIGURATION VARIABLES
hbx.acct="DM57013168AB;DM5701312PCS";//ACCOUNT NUMBER(S)


//Override the account if request is from production server.
if (getProductionInfoBasedOnURL() == true) {
        hbx.acct="DM570203L6VD;DM5612035ADD;DM5701316PRA";//PRODUCTION HBX ACCOUNT NUMBER(S)
}

//Override the gateway server name when the request is for quickaccess application.
//if(isThisQuickAccessURL() == true){
//	hbx.gn="metrics.verizonwireless.com";
//}

hbx.pn="PUT+PAGE+NAME+HERE";//PAGE NAME(S)
hbx.mlc="CONTENT+CATEGORY";//MULTI-LEVEL CONTENT CATEGORY
hbx.pndef="title";//DEFAULT PAGE NAME
hbx.ctdef="full";//DEFAULT CONTENT CATEGORY

//OPTIONAL PAGE VARIABLES
//ACTION SETTINGS
hbx.fv="";//FORM VALIDATION MINIMUM ELEMENTS OR SUBMIT FUNCTION NAME
hbx.lt="auto";//LINK TRACKING
hbx.dlf="!.do";//DOWNLOAD FILTER - Exclude tracking of download filename *.do
hbx.dft="n";//DOWNLOAD FILE NAMING
hbx.elf="n";//EXIT LINK FILTER

//SEGMENTS AND FUNNELS
hbx.seg = "";//VISITOR SEGMENTATION
hbx.fnl="";//FUNNELS

//CAMPAIGNS
hbx.cmp="";//CAMPAIGN ID
hbx.cmpn="";//CAMPAIGN ID IN QUERY
hbx.dcmp="";//DYNAMIC CAMPAIGN ID
hbx.dcmpn="";//DYNAMIC CAMPAIGN ID IN QUERY
hbx.hra="";//RESPONSE ATTRIBUTE
hbx.hqsr="";//RESPONSE ATTRIBUTE IN REFERRAL QUERY
hbx.hqsp="";//RESPONSE ATTRIBUTE IN QUERY
hbx.hlt="";//LEAD TRACKING
hbx.hla="";//LEAD ATTRIBUTE
hbx.gp="";//CAMPAIGN GOAL
hbx.gpn="";//CAMPAIGN GOAL IN QUERY
hbx.hcn="";//CONVERSION ATTRIBUTE
hbx.hcv="";//CONVERSION VALUE
hbx.cp="null";//LEGACY CAMPAIGN

//FORCE ALL TO LOWER CASE
hbx.lc="y";//LOWER CASE SETTING

//CUSTOM VARIABLES
hbx.hc1="";//CUSTOM 1
hbx.hc2="";//CUSTOM 2
hbx.hc3="";//CUSTOM 3
hbx.hc4="";//CUSTOM 4
hbx.hrf="R3D1R3CT";//CUSTOM REFERRER
hbx.pec="";//ERROR CODES

//END EDITABLE SECTION
<!--END WEBSIDESTORY CODE-->

/*
 * This function will return true value when request is from production server
 */
function getProductionInfoBasedOnURL() {
	//Get request url
	var pageURL = window.location.href;

	if ((pageURL.indexOf("verizonwireless.com") > -1 || pageURL.indexOf("vzw.com") > -1)
		&& pageURL.indexOf("testman") == -1 && pageURL.indexOf("extvend") == -1 
		&& pageURL.indexOf("tqa") == -1     && pageURL.indexOf("tmyacct") == -1
		&& pageURL.indexOf("tlogin") == -1) {
	    return true;
	} else {
		return false;
	}
}	
	
/*
 * This function will return true when the request is for quickaccess application.
 */
function isThisQuickAccessURL() {
	//Get request url
	var pageURL = window.location.href;
	if (pageURL.indexOf("quickaccess") > -1) {
	    return true;
	} else {
		return false;
	}
}
