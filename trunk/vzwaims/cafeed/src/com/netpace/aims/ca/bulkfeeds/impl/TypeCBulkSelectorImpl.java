package com.netpace.aims.ca.bulkfeeds.impl;

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

public class TypeCBulkSelectorImpl extends AbstractSelector {

	private static Logger log = Logger.getLogger(TypeCBulkSelectorImpl.class.getName());

	public TypeCBulkSelectorImpl() {
		super(new FeedType('C',
				"First Contract Accepted -- Already Registered Alliance",
				"First Contract Accepted -- Already Registered Alliance"));
	}

	public Collection<DeveloperFeed> execute(Connection conn, String startTime, String endTime) throws DBIssueException {
		Collection<DeveloperFeed> typeCIds = getAllianceIdsOnDirection(conn, startTime, endTime);
		return typeCIds;
	}

	private Collection<DeveloperFeed> getAllianceIdsOnDirection(Connection conn, String startTime, String endTime) throws DBIssueException {
		
		log.debug("Fetching Type C records!!!");

		PreparedStatement stmt = null;
		Collection<Integer> allianceIds = new ArrayList<Integer>();

		try {

			StringBuffer typeCRecords = new StringBuffer(
			"select "
					+ "distinct alliance.alliance_id, contracts.contract_ref_id, a_contract.accept_decline_date, alliance.company_name, alliance.vendor_id "
					+ "from "
					+ "aims_alliance_contracts a_contract, aims_contracts contracts, aims_alliances alliance "
					+ "where "
					+ "contracts.contract_id = a_contract.contract_id "
					+ "and alliance.alliance_id = a_contract.alliance_id "
					+ "and contracts.platform_id = ? "
					+ "and a_contract.status = ? "
					+ "and alliance.vendor_id is not null "
					+ "and a_contract.accept_decline_date > TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') "
					+ "and a_contract.accept_decline_date < TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') "
					+ "and alliance.alliance_id NOT IN "
					+ "("
					+
					"select distinct alliance_id "
					+ "from aims_alliance_contracts a_contract_in, aims_contracts contracts_in "
					+ "where contracts_in.platform_id = ? "
					+ "and a_contract_in.status = ? "
					+ "and a_contract_in.accept_decline_date < TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') "
					+ ")"
					);

			stmt = conn.prepareStatement(typeCRecords.toString());
			log.info("Type C <bulk> query ::" + typeCRecords);

			stmt.setLong(1, CafeedConstants.CONTRACTS_PLATFORM_JAVA);
			stmt.setString(2, CafeedConstants.CONTRACTS_ACCEPTED_STATUS);
			stmt.setString(3, startTime);
			stmt.setString(4, endTime);
			stmt.setLong(5, CafeedConstants.CONTRACTS_PLATFORM_JAVA);
			stmt.setString(6, CafeedConstants.CONTRACTS_ACCEPTED_STATUS);
			stmt.setString(7, startTime);

			ResultSet rSet = stmt.executeQuery();
			Collection<DeveloperFeed> feeds = new ArrayList<DeveloperFeed>();
			SimpleDateFormat formatter = new SimpleDateFormat(CafeedConstants.FEED_TIME_FORMAT);
			
			while (rSet.next()) {
					DeveloperFeed feed = new DeveloperFeed();
					Integer alliance_id = rSet.getInt(1);
					Integer contract_id = rSet.getInt(2);
					Date acceptance_time = rSet.getTimestamp(3);
					String allianceName = rSet.getString(4);
					Integer vendorId = rSet.getInt(5);
					allianceIds.add(alliance_id);

					String formattedDate = formatter.format(acceptance_time);
					feed.setRecordType('C');
					feed.setSortOrder(CafeedConstants.SORT_ORDER_2);
					feed.setAllianceId(alliance_id);
					feed.setAllianceName(allianceName);
					feed.setContractId(contract_id);
					feed.setAcceptRejectTime(acceptance_time);
					feed.setFormattedDate(formattedDate);
					feed.setVendorId(vendorId);
					feeds.add(feed);
			}
			log.debug("Total of " + feeds.size() + " Type C <bulk> records found.");
			log.debug("Type C <bulk> Records fetching done!!!");			
			return feeds;

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DBIssueException(e.getMessage());
		}
	}
}