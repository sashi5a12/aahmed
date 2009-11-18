function toggleMenu(menuLink, menuLinkParent) {
    // Remove the "//" from the next line if you do not want the menu to "stay there"
    //resetMenu();
    if (menuLinkParent.nodeName == "LI") {
        var ul = menuLinkParent.getElementsByTagName('ul');
        if (ul[0].className == "subMenuOpen") {
            // "Close" the sub menu
            ul[0].className = "subMenuClosed";
            // Also change the class of the actual menuLink (to show the "closed" arrow)
            menuLink.className = "menuClosed";
        } else {
            resetMenu();
            // "Open" the sub menu
            ul[0].className = "subMenuOpen";
            // Also change the class of the actual menuLink (to show the "open" arrow)
            menuLink.className = "menuOpen";
        }
    }
}

function resetMenu() {
    var x = $("collapsible_menu").getElementsByTagName("ul");
    for (var i = 0; i < x.length; i++) {
        x[i].className = "subMenuClosed";
    }
    var y = $("collapsible_menu").getElementsByTagName("a");
    for (var i = 0; i < y.length; i++) {
        if (y[i].className == "menuOpen") {
            y[i].className = "menuClosed";
        }
    }
}
	


