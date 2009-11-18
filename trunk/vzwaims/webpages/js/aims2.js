function textFieldSearch(toapply, ourFocus) {
    if (ourFocus == 'out' && toapply.value == '') {
        document.forms[0].filterText.value = 'Search'
    }
    if (ourFocus == 'in' && toapply.value == 'Search') {
        document.forms[0].filterText.value = ''
    }
}

function validate_for_select(elem) {
    //elem = document.feature_select.FeatSelected;
    len = elem.length;
    for (var k = len - 1; k >= 0; k--) {
        if (elem.options[k].value == -1) {
            alert("Please Select Platforms...And then Proceed further");
            return false;
        }
    }
    if (len > 20) {
        errstr = 'You have selected ' + len + ' platforms. The limit is 20. Please change the selection list'
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

    if (NotSelected == false) {
        for (var k = len - 1; k >= 0; k--) {
            if (elem.options[k].value == -1) {
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
                i = 0;
                while (i < len) {
                    if (elem.options[i].value == value1) {
                        FeatExists = true;
                    }
                    i++;
                }
            }
            if (FeatExists == true) {
                AddedFeatText += select1 + "\n";
            } else {
                if (value1 != -1) {
                    opt_new = new Option(select1, value1);
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

function helloFunction() {
    alert('hello');
}

function removeAndCopy(elem, toList, msgTxt) {
    //elem = document.feature_select.FeatSelected;
    len = elem.length;
    SelectedCtr = 0;
    for (var j = len - 1; j >= 0; j--) {
        if (elem.options[j].selected) {
            SelectedCtr++;
        }
    }
    if (msgTxt && msgTxt=='1'){
    	message = "Remove " + SelectedCtr + " contract/s from the Contracts Accepted?";
    }
    else {
    message = "Remove " + SelectedCtr + " platform/s from the supported platforms?";
    }
    answer = window.confirm(message)

    if (answer == true) {
        for (var j = len - 1; j >= 0; j--) {
            if (elem.options[j].selected) {
                if (elem.options[j].value != -1) {
                    var condition = true;
                    for (var loop = 0; loop < toList.length; loop++) {
                        if (toList.options[loop].text == elem.options[j].text) {
                            condition = false;
                        }
                    }
                    if (condition) {
                        toList.options[toList.length] = new Option(elem.options[j].text, elem.options[j].value);
                    }
                    elem.options[j] = null;
                }
            }
        }
    }
    //elem = document.feature_select.FeatSelected;
    len = elem.length;
    if (len == 0) {
        opt_new = new Option('-------- None Selected ----------', '-1');
        elem.options[0] = opt_new;
    }
    if (SelectedCtr == 0) {
    	if (msgTxt && msgTxt=='1'){
    		alert("Please Select contract(s) from Contracts Accepted Box to remove contract");
    	}
    	else {
        alert("Please Select platform from Supported Platforms Box to remove platforms");
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
    answer = window.confirm(message)
    if (answer == true) {
        for (var j = len - 1; j >= 0; j--) {
            if (elem.options[j].selected) {
                if (elem.options[j].value != -1) {
                    elem.options[j] = null;
                }
            }
        }
        //if (!isOk) history.go(0);
    }
    //elem = document.feature_select.FeatSelected;
    len = elem.length;
    if (len == 0) {
        opt_new = new Option('-------- None Selected ----------', '-1');
        elem.options[0] = opt_new;
    }
    if (SelectedCtr == 0) {
        alert("Please Select platform from Supported Platforms Box to remove platforms");
    }
}

function reset_selection(elem) {
    answer = window.confirm('Are you sure to reset all values for Supported Platforms');
    if (answer == true) {
        //elem = document.feature_select.FeatSelected;
        len = elem.length;
        for (var j = len - 1; j >= 0; j--) {
            //if(elem.options[j].value != -1){
            elem.options[j] = null;
            //}
        }
        opt_new = new Option('-------- None Selected ----------', '-1');
        elem.options[0] = opt_new;
    }
}

function select_all(elem) {
    //elem = document.feature_select.FeatSelected;
    len = elem.length;
    if (elem.options[0] != null) {
        for (i = 0; i < len; i++) {
            elem.options[i].selected = true;
        }
    }
    if (len > 0) {
        return true;
    } else {
        return false;
    }
}

function copyToList(from, to, forAllOptions)
{
    fromList = document.forms[0].elements[from];
    toList = document.forms[0].elements[to];
    if (toList.options[0] && toList.options[0].value == 'temp')
    {
        toList.options[0] = null;
    }
    var sel = false;
    for (i = 0; i < fromList.options.length; i++)
    {
        var current = fromList.options[i];
        if (current.selected)
        {
            sel = true;
            if (current.value == 'temp')
            {
                alert('You cannot move this text!');
                return;
            }
            txt = current.text;
            val = current.value;

            var condition = true;

            for (var loop = 0; loop < toList.length; loop++) {
                if (toList.options[loop].text == txt) {
                    condition = false;
                }
            }            

            if(condition){
                if (toList.options[0] != null && toList.options[0].value == -1) {
                    toList.options[--toList.length] = new Option(txt, val);
                } else {
                    toList.options[toList.length] = new Option(txt, val);
                }
            }
            fromList.options[i] = null;
            i--;
        }
    }
    if (forAllOptions==undefined){
    	if (!sel) alert('You haven\'t selected any options!');
    }
}

function sortItems(sel)
{
    var obj = document.forms[0].elements[sel];
    var values = new Array();
    for (var no = 0; no < obj.options.length; no++)
    {
        values.push(obj.options[no].text + "," + obj.options[no].value);
    }

    values = values.sort();
    obj.options.length = 0;
    for (var no = 0; no < values.length; no++)
    {
        valueArray = values[no].split(',');
        obj.options[obj.options.length] = new Option(valueArray[0], valueArray[1]);
    }
}

function allSelect(object)
{
    List = document.forms[0].chosen;
    if (List.length && List.options[0].value == 'temp') return;
    for (i = 0; i < List.length; i++)
    {
        List.options[i].selected = true;
    }
}


function trim(value) {
    var temp = value;
    var obj = /^(\s*)([\W\w]*)(\b\s*$)/;
    if (obj.test(temp)) {
        temp = temp.replace(obj, '$2');
    }
    var obj = /	/g;
    while (temp.match(obj)) {
        temp = temp.replace(obj, "	");
    }
    return    temp;
}


function fixPosition(divname) {
    divstyle = getDivStyle(divname);
    positionerImgName = divname + 'Pos';
    // hint: try setting isPlacedUnder to false.
    isPlacedUnder = false;
    if (isPlacedUnder) {
        setPosition(divstyle, positionerImgName, true);
    } else {
        setPosition(divstyle, positionerImgName)
    }
}

function toggleDatePicker(eltName, formElt) {

    var x = formElt.indexOf('.');
    var formName = formElt.substring(0, x);
    var formEltName = formElt.substring(x + 1);
    newCalendar(eltName, document.forms[formName].elements[formEltName]);
    toggleVisible(eltName);
}


//Start of functions to limit text in TextArea
function TrackCount(fieldObj, countFieldName, maxChars)
{
    var countField = eval("fieldObj.form." + countFieldName);
    var diff = maxChars - fieldObj.value.length;

    // Need to check & enforce limit here also in case user pastes data
    if (diff < 0)
    {
        //fieldObj.value = fieldObj.value.substring(0, maxChars - 1);
    	fieldObj.value = fieldObj.value.substring(0, maxChars);
        diff = maxChars - fieldObj.value.length;
    }
    countField.value = diff;
}

function LimitText(fieldObj, maxChars)
{
    var result = true;
    if (fieldObj.value.length >= maxChars)
        result = false;

    if (window.event)
        window.event.returnValue = result;
    return result;
}

function TruncateText(fieldObj, maxChars)
{
    if ((maxChars - fieldObj.value.length) < 0) {
        fieldObj.value = fieldObj.value.substring(0, maxChars - 1);
    }
}

function TruncateTextWithCount(fieldObj, countFieldName, maxChars)
{
    if ((maxChars - fieldObj.value.length) < 0) {
        fieldObj.value = fieldObj.value.substring(0, maxChars);
        TrackCount(fieldObj, countFieldName, maxChars);
    }
}


//End of functions to limit text in TextArea

function openLargePopup(url, windowname) {
    var popup = window.open(url, windowname, "toolbar=yes,status=yes,scrollbars=yes,menubar=yes,locationbar=yes,directories=yes,top=0,left=0,outerWidth=643,outerHeight=525,width=643,height=525,resizable=yes");
    popup.moveTo(0, 0)
    popup.resizeTo(screen.width, screen.height)
    popup.focus();
}

function openPopup(url, windowname) {
    var popup = window.open(url, windowname, "toolbar=no,status=no,scrollbars=yes,menubar=no,locationbar=no,directories=no,width=550,height=400,resizable=no");
    popup.focus();
}

function showElement(divname) {
    getDivStyle(divname).visibility = 'visible';
}

function shimPopupDiv(elemId, state)
{
    var DivRef = document.getElementById(elemId);
    var IfrRef = document.getElementById('ZonPopupDivShim');
    if (state)
    {
        IfrRef.style.width = DivRef.offsetWidth + "px";
        IfrRef.style.height = DivRef.offsetHeight + "px";
        IfrRef.style.top = DivRef.style.top;
        IfrRef.style.left = DivRef.style.left;
        IfrRef.style.zIndex = DivRef.style.zIndex - 1;
        IfrRef.style.display = "block";
    }
    else
    {
        IfrRef.style.display = "none";
    }
}

function onEnterSubmitForm(e, form) {
    if (e.which || e.keyCode) {
        if ((e.which == 13) || (e.keyCode == 13)) {
            form.submit();
            return false;
        }
    }
    return true
}

var textCount=0;
function countNodeText(node) {
	var notWhitespace = /\S/;
    for (var i=0; i < node.childNodes.length; i++) {       
    	var childNode = node.childNodes[i];
        if (childNode.nodeType == 3 && notWhitespace.test(childNode.nodeValue)) {
        	var text = childNode.nodeValue.replace(/^(\s*)/, '').replace(/\s*$/, '');
        	if (text != ' '){
        		//alert("----"+text+"----\n"+text.length);
        		textCount = textCount + text.length;
        	}
        }
        if ( childNode.nodeType == 1) {
            countNodeText(childNode);
        }
    }
    return textCount;
}
