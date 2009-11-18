package com.netpace.aims.ca.regularfeeds;

import org.apache.log4j.Logger;
import com.netpace.aims.ca.model.FeedType;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public abstract class AbstractSelector implements RecordSelector{
	
	private static Logger log = Logger.getLogger(AbstractSelector.class.getName());
	
	private FeedType feedType;
	
	public AbstractSelector(FeedType feedType){
		this.feedType = feedType;
	}

	public FeedType getFeedType() {
		return feedType;
	}

	public void setFeedType(FeedType feedType) {
		this.feedType = feedType;
	}
	
}
