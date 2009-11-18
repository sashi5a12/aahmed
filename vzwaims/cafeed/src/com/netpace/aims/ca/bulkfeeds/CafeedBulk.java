package com.netpace.aims.ca.bulkfeeds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.apache.log4j.Logger;

import com.netpace.aims.ca.bulkfeeds.impl.TypeABulkSelectorImpl;
import com.netpace.aims.ca.bulkfeeds.impl.TypeCBulkSelectorImpl;
import com.netpace.aims.ca.exceptions.DBIssueException;
import com.netpace.aims.ca.model.DeveloperFeed;
import com.netpace.aims.ca.regularfeeds.RecordSelector;
import com.netpace.aims.ca.utils.CafeedUtil;
import com.netpace.aims.ca.utils.ConnectionsUtil;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public class CafeedBulk {
	
	private static Logger log = Logger.getLogger(CafeedBulk.class.getName());

	public static void main(String args[]){
		ConnectionsUtil conUtil = null;
		Connection conn = null;
		
		try
		{			
			log.info("Starting CA Feed <bulk>!!!");
			System.out.println("Starting CA Feed!!!");
			
			conUtil = ConnectionsUtil.loadConnection();
			conn = conUtil.getConnection();			
			
			CafeedUtil.verifyArguments(args);			
			String fromDate = args[0];
			String toDate = args[1];

			log.info("Start Time = " + fromDate);
			log.info("End Time = " + toDate);
						
			RecordSelector selector = new TypeABulkSelectorImpl();
			Collection<DeveloperFeed> resultedAtypeIds; 
			Collection<DeveloperFeed> resultedCtypeIds;
			Collection<DeveloperFeed> mergedIds = new ArrayList<DeveloperFeed>();
			resultedAtypeIds = selector.execute(conn, fromDate, toDate);			
			
			selector = new TypeCBulkSelectorImpl();
			resultedCtypeIds = selector.execute(conn, fromDate, toDate);			
			resultedCtypeIds = CafeedUtil.exclude(resultedCtypeIds, resultedAtypeIds) ;			
			mergedIds = CafeedUtil.merge(resultedCtypeIds,resultedAtypeIds);
			
			Collections.sort((ArrayList)mergedIds);
			CafeedUtil.displayFeeds(mergedIds);
			System.out.println("Creating file Feed File!!! ");
			log.info("Creating file Feed File!!! ");
			String genFileName = CafeedUtil.createFile(mergedIds, args[2]);
			System.out.println("Feed File: " + genFileName + " created successfully!!!");
			log.info("Feed File: " + genFileName + " created successfully!!!");
			log.info("Exiting CA Feed <bulk>!!!");
					
		}
		catch(DBIssueException e){
			log.error(e.getMessage(),e);
			System.out.append("Could access DB. Check cafeed logs.");
		}
		
		catch(Exception e){
			log.error(e.getMessage(),e);
			System.out.append("Some Issue with CA Feed. Check cafeed logs.");
		}
		finally{
			if (null != conn){
				try{
					conn.close();
				} 
				catch (SQLException sqlE){
					log.error(sqlE.getMessage(), sqlE);
				}
			}
		}
	}
}
