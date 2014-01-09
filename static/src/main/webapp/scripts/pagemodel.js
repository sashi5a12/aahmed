

var addEvent = function(elem, type, fn) {
    if(elem != null && elem != undefined) {
        if (elem.addEventListener) elem.addEventListener(type, fn, false);
        else if (elem.attachEvent) elem.attachEvent('on' + type, fn);
    }
};

function gotoPage(page){
	if (parseInt(page) >= parseInt($("#totalPageCount").val())){
		page = $("#totalPageCount").val()-1;
	}
    document.forms['pageModelForm'].elements['pageModel.page'].value = page;
    setSearchValueForPaging();
    document.forms['pageModelForm'].submit();        
}

function sortByColumn (newColumn) {
    if( newColumn == document.forms['pageModelForm'].elements['pageModel.sortBy'].value ){
        toggleSorting();
    } else {
        document.forms['pageModelForm'].elements['pageModel.sortBy'].value = newColumn;
        document.forms['pageModelForm'].elements['pageModel.ascending'].value = 'true';
    }
        
    document.forms['pageModelForm'].submit();
}

function toggleSorting() {
    if ( document.forms['pageModelForm'].elements['pageModel.ascending'].value == 'true' ){
        document.forms['pageModelForm'].elements['pageModel.ascending'].value = 'false';
    }else{
        document.forms['pageModelForm'].elements['pageModel.ascending'].value = 'true';
    }
}

function filter(){
    resetPaging();
    resetSorting();
    document.forms['pageModelForm'].submit();        
}

function checkAllFilters(filterIndex){
    $( "input[name^='pageModel.filters["+filterIndex+"].filterValues']" ).prop('checked', true);
}

function clearAllFilters(filterIndex){
    $( "input[name^='pageModel.filters["+filterIndex+"].filterValues']" ).prop('checked', false);
}

function resetPaging() {
    if(document.forms['pageModelForm'].elements['pageModel.page'] != undefined) {
        document.forms['pageModelForm'].elements['pageModel.page'].value = '';
    }
}

function resetSorting() {
    document.forms['pageModelForm'].elements['pageModel.sortBy'].value = '';
    document.forms['pageModelForm'].elements['pageModel.ascending'].value = 'true';
}

function resetFilters() {
    $("input[name^='pageModel.filters']").attr("checked", false);
}

var defaultSearchString = "Search Term";

function setSearchValueForPaging() {
    var searchValue = $("#pageModel\\.searchValue").val();
    var prevSearchValue = $("#pageModel\\.prevSearchValue").val();
    if((searchValue == '' || searchValue == defaultSearchString) && 
            prevSearchValue != null && prevSearchValue != '') {
        $("#pageModel\\.searchValue").val(prevSearchValue);
    }
}

$(".searchBar a").click(function(e) {
   e.preventDefault();
   
   if($("#pageModel\\.searchBy").val()==''){
	   alert("Please select criteria for searching.");
   }
   
   else if ($("#pageModel\\.searchValue").val() == null || $("#pageModel\\.searchValue").val() == '' || $("#pageModel\\.searchValue").val() == defaultSearchString) {
       alert("Please enter some value for searching.");
   }
   else {
        $("#pageModel\\.searchValue").trigger('focus');
        
        resetPaging();
        resetSorting();
        resetFilters();
        
        $('form').submit();
   }
   return false;
});
$("#pageModel\\.searchValue").focus(function(e) {
   if ($("#pageModel\\.searchValue").val() == defaultSearchString) {
       $("#pageModel\\.searchValue").val("");
   }
});
$("#pageModel\\.searchValue").blur(function(e) {
   if ($("#pageModel\\.searchValue").val() == null || $("#pageModel\\.searchValue").val() == '') {
       $("#pageModel\\.searchValue").val(defaultSearchString);
   }
});
$(document).ready(function() {
    $("#pageModel\\.prevSearchValue").val($("#pageModel\\.searchValue").val());
   if ($("#pageModel\\.searchValue").val() == null || $("#pageModel\\.searchValue").val() == '') {
       $("#pageModel\\.searchValue").val(defaultSearchString);
   }
});
$('#pageModel\\.searchValue').keypress(function(event){
	 
	var keycode = (event.keyCode ? event.keyCode : event.which);
	if(keycode == '13'){
		 $(".searchBar a").trigger('click');	
	}
 
});
$("#gotoPage").click(function(e){
	e.preventDefault();
	var page = $('#jumpToPage').val()-1;
	if (parseInt(page) >= parseInt($("#totalPageCount").val())){
		page = $("#totalPageCount").val()-1;
	}
    $('#page').val(page);
    
    setSearchValueForPaging();
    $('form').submit();        
	
	return false;
});
$('#jumpToPage').keypress(function(event){
	 
	var keycode = (event.keyCode ? event.keyCode : event.which);
	if(keycode == '13'){
		 $("#gotoPage").trigger('click');	
	}
 
});