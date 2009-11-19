/*
 * $Revision: 1.2 $
 * Date 		Resource	Description
 * 4/27/2007	Vish		Initial Version
 *
 * This file contains functions to support HBX implementation
 * 1. generic utility methods
 * 2. functions which dynamically set the HBX page_code variables. 
 */

var cv = _hbEvent("cv"); // for custom metric

/* 
 *	This function is used to remove white spaces from both sides of string.
 */
String.prototype.trim = function() { 
	return this.replace(/^\s+|\s+$/g, ''); 
};

/*
 * This function will remove illegal characters 
 * and replace them with empty spaces.
 *
 * a	: hbx.pn value, from where special characters will be removed
 */
function _hbxStrip (a, is_cat){
	// special html chars to be removed first
	a = a.trim();
	a = a.split("&reg;").join("");
	a = a.split("&trade;").join("");
	a = a.split("&amp;").join("");
	a = a.split("&ndash;").join("-");
	
	// set second parameter to 1 for pagename and it will skip all slashes (/).	
	if (typeof is_cat != 'undefined' || is_cat == '1') {
		a = a.split("/").join("-");
	}
	a = a.split("|").join("");
	a = a.split("]").join("");
	a = a.split("[").join("");
	a = a.split("}").join("");
	a = a.split("{").join("");
	a = a.split("&").join("");
	a = a.split("'").join("");
	a = a.split("#").join("");
	a = a.split("$").join("");
	a = a.split("%").join("");
	a = a.split("^").join("");
	a = a.split("*").join("");
	a = a.split(":").join("");
	a = a.split("!").join("");
	a = a.split("<").join("");
	a = a.split(">").join("");
	a = a.split("~").join("");
	a = a.split(";").join("");
	a = a.split("®").join("");
	a = a.split("™").join("");
	a = a.split("\"").join("");
	a = a.split(" ").join("+");
	a = a.split("+++").join("+");
	a = a.split("++").join("+");

	return a;
}

/*
 * This util function will remove extra comma (",") from the end of string.
 *
 * str : string value, which has "," at end of string
 *
 * return str, after removing "," from string
 */
function removeLastComma (str) {
	var comma = str.lastIndexOf(",");
	if(comma != -1){
		str = str.substring(0,comma);
	}

	return str;
}

/*
 * This util function will remove comma (",") from the string.
 *
 * str : string value
 *
 * return str, after removing "," from the string
 */
function removeComma (str) {
	return str.replace(/[\,]/g, "");
}

/*
 * This function will set the below 3 common hbx pageview variables.
 * Step 1: Set hbx.mlc   [Example: hbx.mlc = "my+account" + "/error"]
 * Step 2: Set hbx.pn    [Example: hbx.pn  = <h2> - <h1> - "error"]
 * Step 3: Set cv.c6     [Example: cv.c6   = "error"]
 * 
 *
 * mlcStr		:	string for hbx.mlc, tracking website's directory stucture
 * cmErrStr		:	optional error message string. If value passed from jsp pages, then
 *						hbx.mlc needs to be appended with "/error", hbx.pn needs to be
 *                     appended with cmErrStr, cmErrStr needs to be set in custom metric c6.
 * pnOverride	:	optional string for hbx.pn, if set then this value will be used as hbx.pn
 *						overriding the default setting of <h1> and <h2> for hbx.pn.
 * 
 * returns nothing, values are set as hbx properties.
 *
 *
 * CR#19988: This method is appending the error message with page name, 
 * now acc. to this CR we are appending content category and page name with the error message. 
 * So this method is of no use now. So i make a call to hbxPageViewTag() method from this method.
 */
function hbxCreatePageViewTag (mlcStr, cmErrStr, pnOverride) {
	hbxPageViewTag(mlcStr, cmErrStr, pnOverride);
}

/*
 * This function will set the below 3 common hbx pageview variables.
 * Step 1: Set hbx.mlc   [Example: hbx.mlc = "my+account" + "/error"]
 * Step 2: Set hbx.pn    [Example: hbx.pn  = <h2> - <h1>]
 * Step 3: Set cv.c6     [Example: cv.c6   = "error"]
 * 
 *
 * mlcStr		:	string for hbx.mlc, tracking website's directory stucture
 * cmErrStr		:	optional error message string. If value passed from jsp pages, then
 *						hbx.mlc needs to be appended with "/error", 
 *						cmErrStr needs to be set in custom metric c6.
 * pnOverride	:	optional string for hbx.pn, if set then this value will be used as hbx.pn
 *						overriding the default setting of <h1> and <h2> for hbx.pn.
 * 
 * returns nothing, values are set as hbx properties.
 */
function hbxPageViewTag (mlcStr, cmErrStr, pnOverride) {
    var finalPnVal = "";

    // see what happens when mlcStr is null
    // if pnOverride is not null then set it as hbx.pn(override)

    if (pnOverride != null) {
        finalPnVal = removeComma(pnOverride);
    } else {
        // Get the pn (<h1>-<h2>) values rendered from each JSP page.
        finalPnVal = getElementsByClassName();
    }

    // Step 1: Set hbx.mlc
    // Step 2: Set hbx.pn

     if(cmErrStr != null && cmErrStr != "null" && cmErrStr != ""){
        hbx.mlc = _hbxStrip(mlcStr + "/error");
    } else {
        hbx.mlc = _hbxStrip(mlcStr);
    }

	hbx.pn  = _hbxStrip(finalPnVal, 1);

   // Step 3: Set custom metric c6
    if(cmErrStr != null && cmErrStr != "null" && cmErrStr != ""){
	 cv.c6 = _hbxStrip(cmErrStr) + "|" + hbx.mlc + "/" + hbx.pn;
    }
}

/*
 * This function will set the below 3 common hbx pageview variables for popup pages.
 */
function hbxCreatePageViewPopupTag (mlcStr, cmErrStr, pnOverride) {
	hbxPageViewTag(mlcStr, cmErrStr, pnOverride);
	hbx.pn = "popup+" + hbx.pn;
}

/*
 * This function will set the below 3 common hbx pageview variables for popup pages.
 */
function hbxPageViewPopupTag (mlcStr, cmErrStr, pnOverride) {
	hbxPageViewTag(mlcStr, cmErrStr, pnOverride);
	hbx.pn = "popup+" + hbx.pn;
}

/*
 * This function will assign commerce variable for product detail view pages.
 *
 * pr	: commerce var for Product
 * br	: commerce var for Brand
 * ca	: commerce var for Category 
 * pc	: commerce var for Price
 * qn	: commerce var for Quantity
 *
 * returns nothing, values are set as hbx properties
 */
function hbxCreateProductDetailViewTag (pr, bd, ca, pc, qn, pv) {
	//COMMERCE VARIABLES
	hbx.cacct = "975703022451";//HBX Commerce Test ACCOUNT NUMBER
	if (getProductionInfoBasedOnURL() == true) {
		hbx.cacct="975702189232";//HBX Commerce PRODUCTION ACCOUNT NUMBER
	}
	hbx.vpc = "HBX0140.01a";
	hbx.pr = removeLastComma(_hbxStrip(pr));  //comma delimited products
	hbx.bd = removeLastComma(_hbxStrip(bd));
	hbx.ca = removeLastComma(_hbxStrip(ca));
	hbx.pc = removeLastComma(_hbxStrip(pc));    //comma delimited prices
	hbx.qn = removeLastComma(_hbxStrip(qn));   //comma delimited quantities
	hbx.sr = "1";    //store
	hbx.cam = "0";  //cart add methodology, 0 = highwatermark, 1 = incremental
	hbx.pv = 1;  //product view flag, 0 = cart add, 1 = product view
}

/*
 * This function will assign commerce variable for shopping cart page.
 *
 * pr	: commerce var for Product
 * br	: commerce var for Brand
 * ca	: commerce var for Category 
 * pc	: commerce var for Price
 * qn	: commerce var for Quantity
 *
 * returns nothing, values are set as hbx properties
 */
function hbxCreateShopCartViewTag (pr, bd, ca, pc, qn) {
	//COMMERCE VARIABLES
	hbx.cacct = "975703022451";//HBX Commerce Test ACCOUNT NUMBER
	if (getProductionInfoBasedOnURL() == true) {
		hbx.cacct="975702189232";//HBX Commerce PRODUCTION ACCOUNT NUMBER
	}
	hbx.vpc = "HBX0140.01a";
	hbx.pr = removeLastComma(_hbxStrip(pr));  //comma delimited products
	hbx.bd = removeLastComma(_hbxStrip(bd));
	hbx.ca = removeLastComma(_hbxStrip(ca));
	hbx.pc = removeLastComma(_hbxStrip(pc));    //comma delimited prices
	hbx.qn = removeLastComma(_hbxStrip(qn));   //comma delimited quantities
	hbx.sr = "1";    //store
	hbx.cam = "0";  //cart add methodology, 0 = highwatermark, 1 = incremental
	hbx.pv = 0;  //product view flag, 0 = cart add, 1 = product view
}

/*
 * This function will assign zipcode and region values to hbx custom metric
 *
 * hc1Str   : var for custom metric (hbx.hc1) zipcode
 * hc3Str   : var for custom metric (hbx.hc3) region
 *
 * returns nothing, values are set as hbx properties
 */
function hbxCreateZIPTag (hc1Str, hc3Str) {
	hbx.hc1 = _hbxStrip(hc1Str); //CUSTOM 1 METRICS
	hbx.hc3 = hc3Str; //CUSTOM 3 METRICS
}

/*
 * This function will find the values for h1 and h2 tags used in HTML
 * If <h1> and <h2> tags uses defined classes, then their values will 
 * be stored and passed to hbx.pn 
 *
 * returns string, value which will be set as hbx.pn.
 */
function getElementsByClassName() {
	//Defined css classes, which are used to print h1 and h2
	var headLineclass = new RegExp('\\b'+'pageHeadline'+'\\b');
	var pageTitleclass = new RegExp('\\b'+'pageTitle'+'\\b');
	var sectionHeadclass = new RegExp('\\b'+'sectionHeadline'+'\\b');
	var pageHeadlineVal = "";
	var pageTitleVal	= "";
	var sectionHeadlineVal = "";
	var finalVal = "undefined";
	
	//get <h1> tags present in the HTML.
	var elem = document.getElementsByTagName("h1");
	
	/*
	 * For each element, if element uses one of the defind classes, Store the values
	 */
	for (var i = 0; i < elem.length; i++) {
		var classes = elem[i].className;
	
		// if class is "pageHeadline", store the element value
		if (headLineclass.test(classes)){
			//Get the value of <h1> tag
			var len = elem[i].childNodes.length;

			for (var j = 0; j < len; j++) {
				var value = elem[i].childNodes[j].nodeValue;
				if (value != null) {
					pageHeadlineVal += value;
				}
			}

			// Remove white spaces present around the string
			if(pageHeadlineVal != null && pageHeadlineVal != ""){
				pageHeadlineVal = removeWhiteSpaces(pageHeadlineVal);
			}

		// if class is "pageTitle" , store the element value
		} else if (pageTitleclass.test(classes) ) {
			pageTitleVal = elem[i].firstChild.nodeValue;

			// Remove white spaces present around the string
			if(pageTitleVal != null && pageTitleVal != ""){
				pageTitleVal = removeWhiteSpaces(pageTitleVal);
			}
		}
	}

	//get <h2> tags present in the HTML.
	var elemH2 = document.getElementsByTagName("h2");

	for (var i = 0; i < elemH2.length; i++) {
		var classes = elemH2[i].className;

		// if class is "sectionHeadline", store the element value
		if (sectionHeadclass.test(classes)) {
			//Get the value of <h2> tag
			var lenH2 = elemH2[i].childNodes.length;

			for (var j = 0; j < lenH2; j++) {
				var valueH2 = elemH2[i].childNodes[j].nodeValue;
				if (valueH2 != null) {
					sectionHeadlineVal += valueH2;
				}
			}

			// Remove white spaces present around the string
			if(sectionHeadlineVal != null && sectionHeadlineVal != ""){
				sectionHeadlineVal = removeWhiteSpaces(sectionHeadlineVal);
			}
		}
	}

	/*
	 * First priority is for pageHeadline class, if present ignore pageTitle class value.
	 * If pageHeadline is null then take pageTitle class value. Exmaple: b2c/sitemap.jsp
	 */
	if (pageHeadlineVal != null && pageHeadlineVal != "") {
		// Remove white spaces present around the string
		pageHeadlineVal = removeWhiteSpaces(pageHeadlineVal); 
		finalVal = pageHeadlineVal; 
	} else if (pageTitleVal != null && pageTitleVal != "") {
		finalVal = pageTitleVal;
	}

	// If h2, class sectionHeadline is present append it with calculated tag value.
	if (sectionHeadlineVal != null && sectionHeadlineVal != "") {
		if(finalVal != "undefined"){
			finalVal = sectionHeadlineVal + "-" + finalVal;
		} else {
			finalVal = sectionHeadlineVal;
		}
	}
	// return final value of <h1> and <h2> combination
	return _hbxStrip(finalVal);
}

/*
 * To remove extra white spaces from string from Start and End
 * str : string var which have white spaces  
 *
 * returns String, after removing white spaces from start and end of the string
 */
function removeWhiteSpaces (str) {
	if(str != null && str != ""){
		str = str.replace(new RegExp(/^\s+/),"");
		str = str.replace(new RegExp(/\s+$/),"");
	}

  return str;
}

/*
 * Function to set custom metrices.
 *
 * cTag : custom tag
 * value : custom tag value
 *
 * returns nothing, values are set as custom value
 */
function hbxCreateCustomTag (cTag, value) {
  if(value != null && value != "null" && value != ""){
    // Create a variable, with the name as custom variable value.
  	if (cTag == "cv.c12" || cTag == "cv.c6"){
		var code = cTag +" = \""+ value +"\";";
 	} else if(cTag == "hbx.hc2"){
		value = formatAccountNumber(value);
		var code = cTag +" = \"" + _hbxStrip(value) +"\";";
	} else {
		var code = cTag +" = \"" + _hbxStrip(value) +"\";";
	}
    eval(code);
  }
}

/*
 * This function will assign commerce variable for orderconfirmation
 *
 * pr	: commerce var for Product
 * bd	: commerce var for Brand
 * ca	: commerce var for Category 
 * pc	: commerce var for Price
 * qn	: commerce var for Quantity
 * oi	: commerce var for orderId
 * sy	: commerce var for shipCity
 * sa	: commerce var for shipState
 * sz	: commerce var for shipZip
 * so	: commerce var for shipCountry
 * ds	: commerce var for discount
 *
 * returns nothing, values are set as hbx properties
 */
function hbxCreateOrderTag (pr, bd, ca, pc, qn, oi, sy, sa, sz, so, pt, po, ds, ce, c1, c2, soSku, soChrg) {
	hbx.vpc = "HBX0200.01oG";
	//commented out as it needs to be assigned in orderconfirmation.js after hbxmyaccount.js 
	//as this will include myaccount hbx account. SO delay this assignment until it happens
	//hbx.eacct = hbx.acct; 
	hbx.cacct = "975703022451";//HBX Commerce Test ACCOUNT NUMBER
	if (getProductionInfoBasedOnURL() == true) {
		hbx.cacct="975702189232";//HBX Commerce PRODUCTION ACCOUNT NUMBER
	}
	hbx.pr = removeLastComma(_hbxStrip(pr));  //comma delimited products
	hbx.bd = removeLastComma(_hbxStrip(bd));
	hbx.ca = removeLastComma(_hbxStrip(ca));
	hbx.pc = removeLastComma(_hbxStrip(pc));    //comma delimited prices
	hbx.qn = removeLastComma(_hbxStrip(qn));   //comma delimited quantities
	hbx.st = soSku; //shipping type
	hbx.sp = _hbxStrip(soChrg); //shipping cost (total)
	hbx.sr = "1";    //store
	hbx.oi =  _hbxStrip(oi);  //order id
	hbx.sy =  _hbxStrip(sy);      //shipping city
	hbx.sa =  _hbxStrip(sa);     //shipping state
	hbx.sz =  _hbxStrip(sz);   	   //shipping zip code
	hbx.so =  _hbxStrip(so);   //shipping country
	hbx.pt =  pt;	//payment type
	hbx.sk = "SKU";       //comma-delimited sku
	hbx.cu = "B2C"; //customer type
	hbx.ds = _hbxStrip(ds);  //discount
	if(po != null && po != ""){
		hbx.po = _hbxStrip(po); //promotion
	}
	else{
		hbx.po = "PROMOTION";
	}		
	hbx.c1 = removeLastComma(_hbxStrip(c1));//custom one, recurring or one-time
	hbx.c2 = c2;//custom two, package id
	hbx.ci = _hbxStrip(ce); //customeEmail;
	hbx.gp="last";//CAMPAIGN GOAL
	hbx.seg = "2";//VISITOR SEGMENTATION
}

/*
 * This function will assign commerce variable for verify order page.
 *
 * pr	: commerce var for Product
 * bd	: commerce var for Brand
 * ca	: commerce var for Category 
 * pc	: commerce var for Price
 * qn	: commerce var for Quantity
 * oi	: commerce var for orderId
 * sy	: commerce var for shipCity
 * sa	: commerce var for shipState
 * sz	: commerce var for shipZip
 * so	: commerce var for shipCountry
 * ds	: commerce var for discount
 *
 * returns nothing, values are set as hbx properties
 */
function hbxCreateCheckoutTag (pr, pc, qn, soSku, soChrg) {
	hbx.vpc = "HBX0140.01c";
	hbx.cacct = "975703022451";//HBX Commerce Test ACCOUNT NUMBER
	if (getProductionInfoBasedOnURL() == true) {
		hbx.cacct="975702189232";//HBX Commerce PRODUCTION ACCOUNT NUMBER
	}
	hbx.pr = removeLastComma(_hbxStrip(pr));  //comma delimited products
	hbx.pc = removeLastComma(_hbxStrip(pc));    //comma delimited prices
	hbx.qn = removeLastComma(_hbxStrip(qn));   //comma delimited quantities
	hbx.st = soSku; //shipping type
	hbx.sp = _hbxStrip(soChrg); //shipping cost (total)
	hbx.sr = "1";    //store
}

/*
 * Method to format Account Number.
 * It removes leading zero & insert hyphen
 * at specified POSITION
 */
function formatAccountNumber(acctNo){
	if(acctNo != null && acctNo != ""){
		var firstPart = acctNo.substring(0,acctNo.length-5);
		var lastPart = acctNo.substring(acctNo.length-5,acctNo.length);
		if(firstPart.length > 9 && firstPart.charAt(0) == 0){
			firstPart = firstPart.substring(1,firstPart.length);
		}
 
		acctNo = firstPart.concat("-").concat(lastPart);
	}
	return acctNo;
}

/**
* Method to set hbx store (hbx.sr) variable value  
* 
*/
function setHbxStoreVariable(sr){
	hbx.sr = sr;    // 1 for store; 5 for b2e 
}

/**
* Method to set Customer type variable value  
* 
*/
function setHbxCustomerType(cu){
	hbx.cu = cu; //customer type;    // set as 'B2E' 
}
