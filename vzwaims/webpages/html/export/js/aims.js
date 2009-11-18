function validate_for_select(elem) {
  //elem = document.feature_select.FeatSelected;
  len = elem.length;
  for (var k = len - 1; k >= 0; k--) {
   if(elem.options[k].value == -1){
	 alert("Please Select Platforms...And then Proceed further");
	 return false;
   }
  }
  if(len > 20){
	 errstr = 'You have selected '+ len + ' platforms. The limit is 20. Please change the selection list'
	 alert(errstr);
	 return false;
  }		 
  if (elem.options[0] == null) {
	 alert("Please Select Platforms...And then Proceed further");
	 return false;
  }

  return true;
}

function add_selection(elem1, elem) {
  //elem1 = document.feature_select.FeatAvailable;
  len1 = elem1.length;
  //elem = document.feature_select.FeatSelected;
  len = elem.length;
  NotSelected = true;
  AddedFeatText = '';
  for (var j = 0; j < len1; j++) {
	 if (elem1.options[j].selected) {
		NotSelected = false;
		break;
	 }
  }
  
  if(NotSelected == false){
	  for (var k = len - 1; k >= 0; k--) {
	   if(elem.options[k].value == -1){
		 elem.options[k] = null;
	   }
	  }
  }
  
  for (var j = 0; j < len1; j++) {
	 len = elem.length;
	 if (elem1.options[j].selected) {
		select1 = elem1.options[j].text;
		value1 = elem1.options[j].value;
		FeatExists = false;
		if (elem.options[0] != null) {
		   i=0;
		   while (i<len) {
			  if (elem.options[i].value == value1) {
				 FeatExists = true;
			  }
			  i++;
		   }
		}
		if (FeatExists == true) {
		   AddedFeatText += select1 + "\n";
		} else {
           if(value1 != -1){
			   opt_new = new Option(select1,value1);
			   elem.options[len] = opt_new;
		   }
		}
	 }
  }
  if (NotSelected) {
	 alert("Please select a platform from Platforms available box");
  } else {
	 if (AddedFeatText != '') {
		//alert("The following Feature/s were previously added \n" + AddedFeatText);
	 }
  }
}

function remove_selection(elem) {
  //elem = document.feature_select.FeatSelected;
  len = elem.length;
  SelectedCtr = 0;
  for (var j = len - 1; j >= 0; j--) {
	 if (elem.options[j].selected) {
		SelectedCtr++;
	 }
  }
  message = "Remove " + SelectedCtr + " platform/s from the supported platforms?";
  answer =  window.confirm (message)
  if (answer == true) {
	 for (var j = len - 1; j >= 0; j--) {
		if (elem.options[j].selected) {
		   if(elem.options[j].value != -1){
			   elem.options[j] = null;
		   }
		}
	 }
	 //if (!isOk) history.go(0);
  }
  //elem = document.feature_select.FeatSelected;
  len = elem.length;
  if (len == 0)  {
	 opt_new = new Option('-------- None Selected ----------','-1');
     elem.options[0] = opt_new;
  }
  if (SelectedCtr == 0) {
	 alert("Please Select platform from Supported Platforms Box to remove platforms");
  }
}

function reset_selection(elem) {
  answer =  window.confirm ('Are you sure to reset all values for Supported Platforms');
  if (answer == true) {
	 //elem = document.feature_select.FeatSelected;
	 len = elem.length;
	 for (var j = len - 1; j >= 0; j--) {
	   //if(elem.options[j].value != -1){
		 elem.options[j] = null;
	   //}
	 }
	 opt_new = new Option('-------- None Selected ----------','-1');
     elem.options[0] = opt_new;
  }
}

function select_all(elem) {
  //elem = document.feature_select.FeatSelected;
  len = elem.length;
  if (elem.options[0] != null) {
	for (i=0; i<len; i++) {
	   elem.options[i].selected = true;
	}
  }
  if (len > 0)  {
	  return true;
  } else {
	  return false;
  }
}

function copyToList(from,to)
{
 fromList = document.forms[0].elements[from];
 toList = document.forms[0].elements[to];
 if ( toList.options[0] && toList.options[0].value == 'temp') 
      {
      toList.options.remove(0);
      }
   var sel = false;
 for (i=0;i<fromList.options.length;i++) 
      {
  var current = fromList.options[i];
  if (current.selected)
         {
   sel = true;
   if (current.value == 'temp')
            {
    alert ('You cannot move this text!');
    return;
       }
   txt = current.text;
   val = current.value;
   toList.options[toList.length] = new Option(txt,val);
   fromList.options[i] = null;
   i--;
     }
    } if (!sel) alert ('You haven\'t selected any options!');
}

function allSelect()
{
  List = document.forms[0].chosen;
  if (List.length && List.options[0].value == 'temp') return;
  for (i=0;i<List.length;i++)
     {
     List.options[i].selected = true;
     }
}


function trim(value) {
	 var temp	=	value;
	 var obj = /^(\s*)([\W\w]*)(\b\s*$)/;
	 if	(obj.test(temp)) { temp	=	temp.replace(obj,	'$2'); }
	 var obj = /	/g;
	 while (temp.match(obj)) { temp	=	temp.replace(obj,	"	");	}
	 return	temp;
	}


function fixPosition(divname) {
 divstyle = getDivStyle(divname);
 positionerImgName = divname + 'Pos';
	// hint: try setting isPlacedUnder to false
 isPlacedUnder = false;
 if (isPlacedUnder) {
  setPosition(divstyle,positionerImgName,true);
 } else {
  setPosition(divstyle,positionerImgName)
 }
}

function toggleDatePicker(eltName,formElt) {

  var x = formElt.indexOf('.');
  var formName = formElt.substring(0,x);
  var formEltName = formElt.substring(x+1);
  newCalendar(eltName,document.forms[formName].elements[formEltName]);
  toggleVisible(eltName);  
}


//Start of functions to limit text in TextArea
function TrackCount(fieldObj,countFieldName,maxChars)
{
  var countField = eval("fieldObj.form."+countFieldName);
  var diff = maxChars - fieldObj.value.length;

  // Need to check & enforce limit here also in case user pastes data
  if (diff < 0)
  {
    fieldObj.value = fieldObj.value.substring(0,maxChars);
    diff = maxChars - fieldObj.value.length;
  }
  countField.value = diff;
}

function LimitText(fieldObj,maxChars)
{
  var result = true;
  if (fieldObj.value.length >= maxChars)
    result = false;
  
  if (window.event)
    window.event.returnValue = result;
  return result;
}

function TruncateText(fieldObj,maxChars)  
{
	if ((maxChars - fieldObj.value.length) < 0) {
		fieldObj.value = fieldObj.value.substring(0,maxChars);
	}	
}

function TruncateTextWithCount(fieldObj,countFieldName,maxChars)  
{
	if ((maxChars - fieldObj.value.length) < 0) {
		fieldObj.value = fieldObj.value.substring(0,maxChars);
		TrackCount(fieldObj,countFieldName,maxChars);	
	}	
}
//End of functions to limit text in TextArea