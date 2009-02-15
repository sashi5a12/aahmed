function submitEntryForm(event) {
	event.preventDefault();
	var valid = true;
	$('foodType', 'taste').each(function(box) {
		if (box.value.length === 0) {
			box.addClassName('invalid');
			valid = false;
		} else 
			box.removeClassName('invalid');
		});
	if (!valid) {
		alert('Both fields are required.');
		return;
	}	
	var updater = new Ajax.Updater( 
			{success :'breakfast_history',failure :'error_log'}, 
			'/prototype/BreakFast', 
			{parameters : {foodType :$('foodType').value, taste :$('taste').value}});
}
function toggleEntryForm(event) {
	$('entry').toggle();
	event.preventDefault();
}
function onTextBoxBlur(event) {
	var textBox = event.element();
	if (textBox.value.length === 0) 
		textBox.addClassName('invalid');
	else 
		textBox.removeClassName('invalid');
}
function addObserver(){
	$('entry').observe('submit',submitEntryForm);
	$('toggler').observe('click',toggleEntryForm);
	$('foodType', 'taste').invoke('observe', 'blur', onTextBoxBlur);
}

//add method on page load
Event.observe(window, 'load', addObserver);

//hide form on page load
Event.observe(window, "load", function() { $('entry').hide(); });