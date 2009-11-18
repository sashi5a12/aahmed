<script language="javascript">
	function setContent(){
		var text=window.opener.document.getElementById('contentBox').innerHTML;
		document.getElementById('printText').innerHTML=text;
		document.getElementById('printBtnRow').style.display="";
		document.getElementById('btnRow').style.display="none";
		document.getElementById('spacerRow').style.display="none";
	}
</script>
<div id="contentBox" style="float:left">
	<span id="printText"></span>
<div>
<script	language="javascript">
    setContent();
</script>
