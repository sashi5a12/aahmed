package com.netpace.aims.ca.regularfeeds;

import java.sql.Connection;
import java.util.Collection;

import com.netpace.aims.ca.exceptions.DBIssueException;
import com.netpace.aims.ca.model.DeveloperFeed;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public interface RecordSelector {
	public Collection<DeveloperFeed> execute(Connection conn, String startTime, String endTime) throws DBIssueException;
}
