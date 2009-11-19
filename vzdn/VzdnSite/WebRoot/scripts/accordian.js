var menu = [
				{element_expand:"0_expande", element_close:"0_close", element_content:"0"},
				{element_expand:"1_expande", element_close:"1_close", element_content:"1"},
				{element_expand:"2_expande", element_close:"2_close", element_content:"2"},
				{element_expand:"3_expande", element_close:"3_close", element_content:"3"},
				{element_expand:"4_expande", element_close:"4_close", element_content:"4"},
				{element_expand:"5_expande", element_close:"5_close", element_content:"5"},
				{element_expand:"6_expande", element_close:"6_close", element_content:"6"},
				{element_expand:"7_expande", element_close:"7_close", element_content:"7"},
				{element_expand:"8_expande", element_close:"8_close", element_content:"8"},
				{element_expand:"9_expande", element_close:"9_close", element_content:"9"},
				{element_expand:"10_expande", element_close:"10_close", element_content:"10"},
				{element_expand:"11_expande", element_close:"11_close", element_content:"11"},
				{element_expand:"12_expande", element_close:"12_close", element_content:"12"},
				{element_expand:"13_expande", element_close:"13_close", element_content:"13"},
				{element_expand:"14_expande", element_close:"14_close", element_content:"14"},
				{element_expand:"15_expande", element_close:"15_close", element_content:"15"},
				{element_expand:"16_expande", element_close:"16_close", element_content:"16"},
				{element_expand:"17_expande", element_close:"17_close", element_content:"17"},
				{element_expand:"18_expande", element_close:"18_close", element_content:"18"},
				{element_expand:"19_expande", element_close:"19_close", element_content:"19"},
				{element_expand:"20_expande", element_close:"20_close", element_content:"20"},
				{element_expand:"21_expande", element_close:"21_close", element_content:"21"},
				{element_expand:"22_expande", element_close:"22_close", element_content:"22"},
				{element_expand:"23_expande", element_close:"23_close", element_content:"23"},
				{element_expand:"24_expande", element_close:"24_close", element_content:"24"},
				{element_expand:"25_expande", element_close:"25_close", element_content:"25"}		
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
