
<script	language="javascript">
<!--

function newImage(arg) {
	if (document.images) {
		rslt = new Image();
		rslt.src = arg;
		return rslt;
	}
}

function changeImages()	{
	if (document.images	&& (preloadFlag	== true))	{
		for	(var i=0;	i<changeImages.arguments.length; i+=2) {
			document[changeImages.arguments[i]].src	=	changeImages.arguments[i+1];
		}
	}
}

var	preloadFlag	=	false;
function preloadImages() {
	if (document.images) {
		icon1_over = newImage("images/icon1-over.gif");
		icon2_over = newImage("images/icon2-over.gif");
		icon3_over = newImage("images/icon3-over.gif");
		icon4_over = newImage("images/icon4-over.gif");
		icon5_over = newImage("images/icon5-over.gif");
		icon6_over = newImage("images/icon6-over.gif");
		icon7_over = newImage("images/icon7-over.gif");
		icon8_over = newImage("images/icon8-over.gif");
	    icon9_over = newImage("images/icon9-over.gif");
		icon1_text = newImage("images/icon1_doctype.gif");
		icon2_text = newImage("images/icon2_doctype.gif");
		icon3_text = newImage("images/icon3_doctype.gif");
		icon4_text = newImage("images/icon4_doctype.gif");
		icon5_text = newImage("images/icon5_doctype.gif");
		icon6_text = newImage("images/icon6_doctype.gif");
		icon7_text = newImage("images/icon7_doctype.gif");
		icon8_text = newImage("images/icon8_doctype.gif");
		icon9_text = newImage("images/icon9_doctype.gif");
		preloadFlag	=	true;
	}
}


preloadImages();

//-->
</script>