var newZonHelpWindow;

function WindowHelp(el, eventObject, helpText) {

  var i, ua, s;
  var isIE    = false;  // Internet Explorer
  var isNS    = false;  // Netscape
  var version = null;

  ua = navigator.userAgent;

  s = "MSIE";
  if ((i = ua.indexOf(s)) >= 0) {
    isIE = true;
    version = parseFloat(ua.substr(i + s.length));
  }

  s = "Netscape6/";
  if ((i = ua.indexOf(s)) >= 0) {
    isNS = true;
    version = parseFloat(ua.substr(i + s.length));
  }

  // Treat any other "Gecko" browser as NS 6.1.

  s = "Gecko";
  if ((i = ua.indexOf(s)) >= 0) {
    isNS = true;
    version = 6.1;
  }

  // Get window components.

  this.frame           = el;
  this.close      = winClose;

  this.clientArea      = document.getElementById("ZonHelpWindowText");
  
  // Set up event handling.
  this.frame.parentWindow = this;
  this.clientArea.innerHTML = helpText;
  
    if (isIE)
    {
        this.frame.style.left = mouseX(eventObject) + 15 + "px";
        this.frame.style.top = mouseY(eventObject) - 15 + "px";
    }
    if (isNS)
    {
        this.frame.style.left = mouseX(eventObject) + 15 + "px";
        this.frame.style.top = mouseY(eventObject) - 15 + "px";
    }

  this.isOpen = true;
  this.frame.style.visibility = "visible";
  shimPopupDiv('ZonHelpWindow', true);

}

function mouseX(evt) 
{
    if (evt.pageX) 
        return evt.pageX;
    else if (evt.clientX)
        return evt.clientX + (document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft);
    else 
        return null;
}

function mouseY(evt) 
{
    if (evt.pageY) 
        return evt.pageY;
    else if (evt.clientY)
        return evt.clientY + (document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop);
    else 
        return null;
}

//=============================================================================
// Window Methods
//=============================================================================


function winClose() {

  // Hide the window.

  this.frame.style.visibility = "hidden";
  shimPopupDiv('ZonHelpWindow', false);
}




function openZonHelpWindow(eventObject, helpText)
{
    newZonHelpWindow = new WindowHelp(document.getElementById("ZonHelpWindow"),eventObject, helpText);
}

function closeZonHelpWindow()
{
    newZonHelpWindow.close();
}
