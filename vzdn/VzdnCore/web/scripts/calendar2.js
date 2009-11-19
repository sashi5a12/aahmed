         // how reliable is this test?
         isIE = (document.all ? true : false);
	 isDOM = (document.getElementById ? true : false);

// Declaring valid date character, minimum year and maximum year
var dtCh= "-";
var minYear=1900;
var maxYear=2999;
	 
	 
         // Initialize arrays.
         var months = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
	 "Aug", "Sep", "Oct", "Nov", "Dec");
         var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31,
            30, 31, 30, 31);
	 var displayMonth = new Date().getMonth();
	 var displayYear = new Date().getFullYear();
	 var displayDivName;
	 var displayElement;

         function getDays(month, year) {
            // Test for leap year when February is selected.
            if (1 == month)
               return ((0 == year % 4) && (0 != (year % 100))) ||
                  (0 == year % 400) ? 29 : 28;
            else
               return daysInMonth[month];
         }

         function getToday() {
            // Generate today's date.
            this.now = new Date();
            this.year = this.now.getFullYear();
            this.month = this.now.getMonth();
            this.day = this.now.getDate();
         }

         // Start with a calendar for today.
         today = new getToday();

         function newCalendar(eltName,attachedElement) {
	    
	    if (attachedElement) {
	       if (displayDivName && displayDivName != eltName) hideElement(displayDivName);
	       displayElement = attachedElement;
	    }
	    displayDivName = eltName;
            today = new getToday();
            var parseYear = parseInt(displayYear + '');
            var newCal = new Date(parseYear,displayMonth,1);
            var day = -1;
            var startDayOfWeek = newCal.getDay();
            if ((today.year == newCal.getFullYear()) &&
                  (today.month == newCal.getMonth()))
	    {
               day = today.day;
            }
            var intDaysInMonth =
               getDays(newCal.getMonth(), newCal.getFullYear());
            var daysGrid = makeDaysGrid(startDayOfWeek,day,intDaysInMonth,newCal,eltName)
	    if (isIE) {
	       var elt = document.all[eltName];
	       elt.innerHTML = daysGrid;
	          } else if (isDOM) {
	       var elt = document.getElementById(eltName);
	       elt.innerHTML = daysGrid;
	    } else {
	       var elt = document.layers[eltName].document;
	       elt.open();
	       elt.write(daysGrid);
	       elt.close();
	    }
	 }

	 function incMonth(delta,eltName) {
	   displayMonth += delta;
	   if (displayMonth >= 12) {
	     displayMonth = 0;
	     incYear(1,eltName);
	   } else if (displayMonth <= -1) {
	     displayMonth = 11;
	     incYear(-1,eltName);
	   } else {
	     newCalendar(eltName);
	   }
	 }

	 function incYear(delta,eltName) {
	   displayYear = parseInt(displayYear + '') + delta;
	   newCalendar(eltName);
	 }

	 function makeDaysGrid(startDay,day,intDaysInMonth,newCal,eltName) {
	    var daysGrid;
	    var month = newCal.getMonth();
	    var year = newCal.getFullYear();
	    var isThisYear = (year == new Date().getFullYear());
		var linkColor = "#06C"
	    var isThisMonth = (day > -1)
	    daysGrid = '<table border=1 cellspacing=0 cellpadding=2><tr><td bgcolor=#ffffff nowrap>';
	    daysGrid += '<table border=0 cellspacing=0 cellpadding=0 width=100%><tr><td>';
	    daysGrid += '<a href="javascript:hideElement(\'' + eltName + '\')" style="color: ' + linkColor + ';">x</a>';
	    daysGrid += '&nbsp;&nbsp;</td>';
	    daysGrid += '<td><a href="javascript:incMonth(-1,\'' + eltName + '\')" style="color: ' + linkColor + ';">&laquo; </a></td>';

	    daysGrid += '<td style="text-align: center;"><b>';
	    if (isThisMonth) { daysGrid += '<font color=red>' + months[month] + '</font>'; }
	    else { daysGrid += months[month]; }
	    daysGrid += '</b></td>';

	    daysGrid += '<td><a href="javascript:incMonth(1,\'' + eltName + '\')" style="color: ' + linkColor + ';"> &raquo;</a>';
	    daysGrid += '&nbsp;&nbsp;&nbsp;</td>';
	    daysGrid += '<td><a href="javascript:incYear(-1,\'' + eltName + '\')" style="color: ' + linkColor + ';">&laquo; </a></td>';

	    daysGrid += '<td style="text-align: center;"><b>';
	    if (isThisYear) { daysGrid += '<font color=red>' + year + '</font>'; }
	    else { daysGrid += ''+year; }
	    daysGrid += '</b></td>';

	    daysGrid += '<td><a href="javascript:incYear(1,\'' + eltName + '\')" style="color: ' + linkColor + ';"> &raquo;</a><br></td><tr></table>';
	    daysGrid += '<table border=0 cellspacing=0 cellpadding=0><tr><td>Su</td><td style=\'padding-left:9px;\'>Mo</td><td style=\'padding-left:9px;\'>Tu</td><td style=\'padding-left:9px;\'>We</td><td style=\'padding-left:9px;\'>Th</td><td style=\'padding-left:9px;\'>Fr</td><td style=\'padding-left:9px;\'>Sa</td></tr><tr>';
	    var dayOfMonthOfFirstSunday = (7 - startDay + 1);
	    for (var intWeek = 0; intWeek < 6; intWeek++) {
	       var dayOfMonth;
	       for (var intDay = 0; intDay < 7; intDay++) {
	         dayOfMonth = (intWeek * 7) + intDay + dayOfMonthOfFirstSunday - 7;
		 if (dayOfMonth <= 0) {
	           daysGrid += "<td> &nbsp; </td> ";
		 } else if (dayOfMonth <= intDaysInMonth) {
		   var color = linkColor ;
                    if (day > 0 && day == dayOfMonth) 
                        color="red";
                    daysGrid += '<td style="text-align:right;"><a style="FONT-FAMILY: courier new, courier; FONT-SIZE: 13px; color:' + color + ';" href="javascript:setDay(';
		   daysGrid += dayOfMonth + ',\'' + eltName + '\')"> '
		   var dayString = dayOfMonth + "</a> </td>";
		   if (dayString.length == 6) dayString = '0' + dayString;
		   daysGrid += dayString;
		 }
	       }
	       if (dayOfMonth < intDaysInMonth) daysGrid += "</tr><tr>";
	    }
	    return daysGrid + "</td></tr></table></td></tr></table>";
	 }

	 function setDay(day,eltName) {
		showMonth =  displayMonth + 1;
		showMonth = showMonth +"";
		showDay = day + "";
		
		if(showMonth.length == 1)
		{ 
			showMonth = "0" + showMonth;
		}
		if(showDay.length == 1)
		{
			showDay = "0" + showDay;
		}
		
	   displayElement.value = displayYear + "-" + (showMonth) + "-" + showDay ;
	   displayElement.focus();
	   hideElement(eltName);
	 }

	function addDaysToDate(myDate,days) {
    return new Date(myDate.getTime() + days*24*60*60*1000);
	}
	
	function getDateInCorrectFormat(myDate) {
		return myDate.getFullYear()  + "-" + (1+myDate.getMonth()) + "-" + myDate.getDate() ;
	}
	
function toggleDatePicker(eltName,formElt) {

  var x = formElt.indexOf('.');
  var formName = formElt.substring(0,x);
  var formEltName = formElt.substring(x+1);
  newCalendar(eltName,document.forms[formName].elements[formEltName]);
  toggleVisible(eltName);  
}

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strMonth=dtStr.substring(0,pos1)
	var strDay=dtStr.substring(pos1+1,pos2)
	var strYear=dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}

	
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	
	/*if (strYear.length != 4 || year==0 || year<minYear || year>maxYear) {
		var today = new Date();
		strYear = today.getFullYear()+"";
		year = today.getFullYear();
		dtStr = dtStr + "/" + year;
		pos2=dtStr.indexOf(dtCh,pos1+1);
	}*/

	if (pos1==-1){		
		alert("The date format should be : yyyy-MM-dd")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Please enter a valid day")
		return false
	}
	
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}
	
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Please enter a valid date")
		return false
	}
	
return true
}


 