<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link href="../sample.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript">
		function setEditorValue( instanceName, text )
		{ 
		// Get the editor instance that we want to interact with.
		var oEditor = FCKeditorAPI.GetInstance( instanceName ) ;
		
		// Set the editor contents.
		oEditor.SetHTML( text ) ;
		} 
		
		function getEditorValue( instanceName ) 
		{ 
		// Get the editor instance that we want to interact with.
		var oEditor = FCKeditorAPI.GetInstance( instanceName ) ;
		
		// Get the editor contents as XHTML.
		return oEditor.GetXHTML( true ) ; // "true" means you want it formatted.
		}
	</script>

</head>
<body>
	Hello World! FCKeditor
	--------------------------------------------------------------------------------
	<%
		String submit = request.getParameter("submit");
		if (submit != null) {
			String content = request.getParameter("content");
			if (content == null) content = "";
			out.println("Content: " + content);
			out.println("-------------------------------------------------------------------------------");
		}
	%>
	<form method="post">
		<%--<FCK:editor id="content" basePath="/FCKeditor/" height="300"
			skinPath="/FCKeditor/editor/skins/office2003/"
			imageBrowserURL="/FCKeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector"
			linkBrowserURL="/FCKeditor/editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector">
		</FCK:editor>--%>

		<FCK:editor instanceName="EditorDefault" toolbarSet="aims">
			<jsp:attribute name="value">This is some <strong>sample text</strong>. You are using <a href="http://www.fckeditor.net"> FCKeditor</a>.</jsp:attribute>
			<jsp:body>
				<FCK:config ImageBrowserURL="/R_&_D/fckeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector" />
				<FCK:config linkBrowserURL="/R_&_D/fckeditor/editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector" />
			</jsp:body>				
		</FCK:editor>

		<input type="button" value="Set Value" onclick="setEditorValue('EditorDefault', 'Hello World!')" />
		<input type="button" value="Get Value" onclick="alert(getEditorValue('EditorDefault'))" />
		<input type="submit" value="Submit" name="submit" />
	</form>
</body>
</html>
