package com.netpace.aims.ca.regularfeeds.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.apache.log4j.Logger;

import com.netpace.aims.ca.constants.CafeedConstants;
import com.netpace.aims.ca.exceptions.DBIssueException;
import com.netpace.aims.ca.model.DeveloperFeed;
import com.netpace.aims.ca.model.FeedType;
import com.netpace.aims.ca.regularfeeds.AbstractSelector;
import com.netpace.aims.ca.utils.ConnectionsUtil;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public class TypeDSelectorImpl extends AbstractSelector {

	private static Logger log = Logger.getLogger(TypeDSelectorImpl.class.getName());

	public TypeDSelectorImpl() {
		super(new FeedType('D', "Alliance Deleted",	"Alliance Deleted --- Description"));
	}

	public Collection<DeveloperFeed> execute(Connection conn, String startTime, String endTime)	throws DBIssueException {
		log.debug("Fetching Type D records:");
		Collection<DeveloperFeed> deletedAlliances = pickUpdateRecordsOnDirection(conn, startTime, endTime);
		Collection<DeveloperFeed> finalizedAlliances = filterForContracts(conn, deletedAlliances);
		log.debug("A Total of " + finalizedAlliances.size() + " Type D records found.");
		log.debug("Type D records fetching done!!!");
		return finalizedAlliances;
	}

	private Collection<DeveloperFeed> filterForContracts(Connection conn,
			Collection<DeveloperFeed> deletedIds) throws DBIssueException {
		Collection<DeveloperFeed> finalizedAlliances = new ArrayList<DeveloperFeed>();

		for (DeveloperFeed delId : deletedIds) {
			boolean contractsFound = checkContractsExist(conn,delId);
			// we also need to make sure that the deleted alliance's association
			// with contracts is also deleted
			// so we pick only those alliances whose alliance-contract
			// association is all empty.
			if (!contractsFound) {
				finalizedAlliances.add(delId);
			}
		}
		return finalizedAlliances;
	}

	private boolean checkContractsExist(Connection conn, DeveloperFeed feed) throws DBIssueException {		
		PreparedStatement stmt = null;		
		boolean contractsFound = false;

		try {

			String queryAfterDate =
			"select contract_id from aims_alliance_contracts cont where cont.alliance_id= ? ";
			stmt = conn.prepareStatement(queryAfterDate);
			stmt.setInt(1, feed.getAllianceId());
			ResultSet rSet = stmt.executeQuery();
			while (rSet.next()) {
				contractsFound = true;
				break;
			}
			return contractsFound;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DBIssueException(e.getMessage());

		}
		finally{
			if(null != stmt)
			{
				try{	
					stmt.close();
				}			
				catch (SQLException sqlE){
					log.error(sqlE.getMessage(), sqlE);
				}
			}	
		}
	}

	private Collection<DeveloperFeed> pickUpdateRecordsOnDirection(Connection conn, String fromDate, String toDate) throws DBIssueException {
		PreparedStatement stmt = null;
	
		try {

			StringBuffer typeDMain = new StringBuffer(
			"select alliance_id, aud_timestamp, company_name, vendor_id from aims_alliances$aud where aud_action = ? "
					+ "and aud_timestamp > TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') "
					+ "and aud_timestamp < TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS')"
					);

			stmt = conn.prepareStatement(typeDMain.toString());
			log.info("Type D Main Query ::" + typeDMain);			
			stmt.setString(1, CafeedConstants.AUDIT_ACTION_DELETE);
			stmt.setString(2, fromDate);
			stmt.setString(3, toDate);

			ResultSet rSet = stmt.executeQuery();
			Collection<DeveloperFeed> feeds = new ArrayList<DeveloperFeed>();
			SimpleDateFormat formatter = new SimpleDateFormat(CafeedConstants.RETRIEVE_TIME_FORMAT);

			while (rSet.next()) {				
				DeveloperFeed feed = new DeveloperFeed();
				Integer alliance_id = rSet.getInt(1);
				Date acceptance_time = rSet.getTimestamp(2);
				String allianceName = rSet.getString(3);
				String formattedDate = formatter.format(acceptance_time);
				feed.setAcceptRejectTime(acceptance_time);			
				feed.setFormattedDate(formattedDate);		
				Integer vendorId = rSet.getInt(4);
				feed.setRecordType('D');
				feed.setSortOrder(CafeedConstants.SORT_ORDER_5);
				feed.setAllianceId(alliance_id);
				feed.setAllianceName(allianceName);
				feed.setVendorId(vendorId);
				feeds.add(feed);
			}
			return feeds;

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DBIssueException(e.getMessage());
		}
		finally{
			if(null != stmt)
			{
				try{	
					stmt.close();
				}			
				catch (SQLException sqlE){
					log.error(sqlE.getMessage(), sqlE);
				}
			}	
		}
	}
}