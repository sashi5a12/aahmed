var menu = [
				{
					element_expand:"VZDevices_expande",
					element_close:"VZDevices_close",
					element_content:"VZDevices"
				},
				{
					element_expand:"Brand_expande",
					element_close:"Brand_close",
					element_content:"Brand"
				},
				{
					element_expand:"FullFeature_expande",
					element_close:"FullFeature_close",
					element_content:"FullFeature"
				}		
			];

// menuIndex : starts with 0;
function toggleMenu(menuIndex)
{	
	if ( document.getElementById(menu[menuIndex].element_expand).style.display == 'none' )
	{
		document.getElementById(menu[menuIndex].element_expand).style.display  = 'block';
		document.getElementById(menu[menuIndex].element_close).style.display  = 'none';
		document.getElementById(menu[menuIndex].element_content).style.display  = 'none';
	}
	else
	{
		document.getElementById(menu[menuIndex].element_expand).style.display  = 'none';
		document.getElementById(menu[menuIndex].element_close).style.display  = 'block';
		document.getElementById(menu[menuIndex].element_content).style.display  = 'block';
	}	
	//closeAllOtherMenus(menuIndex);
}

/*function closeAllOtherMenus(menuIndex)
{
	for ( var i=0; i<menu.length; i++ )
	{
		if ( menuIndex != i )
		{
			document.getElementById(menu[i].element_expand).style.display  = 'block';
			document.getElementById(menu[i].element_close).style.display  = 'none';
			document.getElementById(menu[i].element_content).style.display  = 'none';
		}
	}
}*/
