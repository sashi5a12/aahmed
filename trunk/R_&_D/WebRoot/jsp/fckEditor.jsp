<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>FCKeditor - Sample</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!--<link href="../sample.css" rel="stylesheet" type="text/css" /> -->
		<script type="text/javascript" src="/R_&_D/fckeditor/fckeditor.js"></script>
		<script type="text/javascript">
			/*function FCKeditor_OnComplete( editorInstance )
			{
				var oCombo = document.getElementById( 'cmbToolbars' ) ;
				oCombo.value = editorInstance.ToolbarSet.Name ;
				oCombo.style.visibility = '' ;
			}
			function ChangeLanguage( languageCode )
			{				
				document.location.href = document.location.href.replace( /\?.*$/, '' ) + "?" + languageCode ;
			}*/
			window.onload = function()
			{
				// Automatically calculates the editor base path based on the _samples directory.
				// This is usefull only for these samples. A real application should use something like this:
				// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
				//var sBasePath = document.location.href.substring(0,document.location.href.lastIndexOf('_samples')) ;
				var sBasePath='/R_&_D/js/fckeditor/'			
				var oFCKeditor = new FCKeditor( 'FCKeditor1' ) ;
				oFCKeditor.BasePath	= sBasePath ;
				oFCKeditor.ToolbarSet	= 'aims' ;
				oFCKeditor.ReplaceTextarea() ;
			}			
	</script>
	</head>
	<body>
		<h1>FCKeditor</h1>
		<div>
			This sample displays a normal HTML form with an FCKeditor with full features enabled.
			It uses the "ReplaceTextarea" command to create the editor.
		</div>
		<hr />	
		<form action="../php/sampleposteddata.php" method="post" target="_blank">			
			<div>
				<textarea name="FCKeditor1" rows="10" cols="80" style="width: 100%; height: 200px">&lt;p&gt;This is some &lt;strong&gt;sample text&lt;/strong&gt;. You are using &lt;a href="http://www.fckeditor.net/"&gt;FCKeditor&lt;/a&gt;.&lt;/p&gt;</textarea>
			</div>			
			<br />
			<input type="submit" value="Submit" />
		</form>
	</body>
</html>
