<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery.jcarousel.pack.js<%=(String)request.getAttribute("RESOURCE_VERSION")%>"></script>

<script type="text/javascript">

	function mycarousel_itemVisibleInCallback(carousel, item, i, state, evt)
	{
	    // The index() method calculates the index from a
	    // given index who is out of the actual item range.
	    var idx = carousel.index(i, mycarousel_itemList.length);
	    carousel.add(i, mycarousel_getItemHTML(mycarousel_itemList[idx - 1]));
	};
	
	function mycarousel_itemVisibleOutCallback(carousel, item, i, state, evt)
	{
	    carousel.remove(i);
	};
	
	/**
	 * Item html creation helper.
	 */
	function mycarousel_getItemHTML(item)
	{
		if (item.target != null && item.target == 'true'){
			return '<a target="_blank" href="'+item.download_link+'"><img border="0" src="' + item.url + '" width="106" height="161" alt="' + item.alt + '" /></a><div class="clear"></div><a href="'+item.download_link+'" target="_blank">'+item.title+'</a>';
		}
		else {
			return '<a href="'+item.download_link+'"><img border="0" src="' + item.url + '" width="106" height="161" alt="' + item.alt + '" /></a><div class="clear"></div><a href="'+item.download_link+'" >'+item.title+'</a>';
		}	    
	};
	
	jQuery(document).ready(function() {
	    jQuery('#mycarousel').jcarousel({
	        wrap: 'circular',
			scroll: 5,
			visible: 5,
			animation: 800,
	        itemVisibleInCallback: {onBeforeAnimation: mycarousel_itemVisibleInCallback},
	        itemVisibleOutCallback: {onAfterAnimation: mycarousel_itemVisibleOutCallback}
	    });
	});

</script>