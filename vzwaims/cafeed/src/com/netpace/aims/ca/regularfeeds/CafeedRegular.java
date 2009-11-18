package com.netpace.aims.ca.regularfeeds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.apache.log4j.Logger;

import com.netpace.aims.ca.exceptions.DBIssueException;
import com.netpace.aims.ca.model.DeveloperFeed;
import com.netpace.aims.ca.regularfeeds.impl.TypeASelectorImpl;
import com.netpace.aims.ca.regularfeeds.impl.TypeCSelectorImpl;
import com.netpace.aims.ca.regularfeeds.impl.TypeDSelectorImpl;
import com.netpace.aims.ca.regularfeeds.impl.TypeTSelectorImpl;
import com.netpace.aims.ca.regularfeeds.impl.TypeUSelectorImpl;
import com.netpace.aims.ca.utils.CafeedUtil;
import com.netpace.aims.ca.utils.ConnectionsUtil;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public class CafeedRegular {

	private static Logger log = Logger.getLogger(CafeedRegular.class.getName());

	public static void main(String args[]) {
		ConnectionsUtil conUtil = null;
		Connection conn = null;

		try {
			log.info("Starting Cafeed !!!");
			
			conUtil = ConnectionsUtil.loadConnection();
			conn = conUtil.getConnection();
			
			CafeedUtil.verifyArguments(args);
			String fromDate = args[0];
			String toDate = args[1];

			Collection<DeveloperFeed> mergedIds = new ArrayList<DeveloperFeed>();
			Collection<DeveloperFeed> resultedIds;
			
			log.info("Start Time = " + fromDate);
			log.info("End Time = " + toDate);

			// Collecting Type A records
			RecordSelector selector = new TypeASelectorImpl();
			resultedIds = selector.execute(conn, fromDate, toDate);
			mergedIds = CafeedUtil.merge(resultedIds, mergedIds);

			// Collecting and Merging Type C Records
			selector = new TypeCSelectorImpl();
			resultedIds = selector.execute(conn, fromDate, toDate);
			mergedIds = CafeedUtil.merge(mergedIds, resultedIds);

			// Collecting and Merging Type U Records
			selector = new TypeUSelectorImpl();
			resultedIds = selector.execute(conn, fromDate, toDate);
			mergedIds = CafeedUtil.merge(resultedIds, mergedIds);

			// Collecting and Merging Type D Records
			selector = new TypeDSelectorImpl();
			resultedIds = selector.execute(conn, fromDate, toDate);
			mergedIds = CafeedUtil.merge(resultedIds, mergedIds);

			// Collecting and Merging Type T Records
			selector = new TypeTSelectorImpl();
			resultedIds = selector.execute(conn, fromDate, toDate);
			mergedIds = CafeedUtil.merge(resultedIds, mergedIds);

			// Sorting all records
			Collections.sort((ArrayList) mergedIds);

			// Displaying feeds on the console
			CafeedUtil.displayFeeds(mergedIds);
			System.out.println("Creating file Feed File!!! ");
			log.info("Creating file Feed File!!! ");
			// Creating feed file
			String genFileName = CafeedUtil.createFile(mergedIds, args[2]);
			System.out.println("Feed File: " + genFileName + " created successfully!");
			log.info("Feed File: " + genFileName + " created successfully!");
			log.info("Feed Created Successfully. Exiting CA Feed !!!");

		}
		catch (DBIssueException e){
			log.error(e.getMessage(), e);
			System.out.append("Could not access DB. Check cafeed logs.");
		}

		catch (Exception e) {
			log.error(e.getMessage(), e);
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
