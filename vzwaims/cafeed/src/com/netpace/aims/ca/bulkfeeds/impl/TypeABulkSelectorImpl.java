package com.netpace.aims.ca.bulkfeeds.impl;

import java.sql.Connection;

import java.util.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;

import com.netpace.aims.ca.constants.CafeedConstants;
import com.netpace.aims.ca.exceptions.DBIssueException;
import com.netpace.aims.ca.model.DeveloperFeed;
import com.netpace.aims.ca.model.FeedType;
import com.netpace.aims.ca.regularfeeds.AbstractSelector;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public class TypeABulkSelectorImpl extends AbstractSelector {

	private static Logger log = Logger.getLogger(TypeABulkSelectorImpl.class.getName());

	public TypeABulkSelectorImpl() {
		super(new FeedType('A', "First Contract Accepted -- New Alliance", "First Contract Accepted -- New Alliance"));
	}

	public Collection<DeveloperFeed> execute(Connection conn, String startTime, String endTime) throws DBIssueException {
		Collection<DeveloperFeed> atypeAlliancesFromAudit = getTypeARecrods(conn, startTime, endTime);
		return atypeAlliancesFromAudit;
	}

	private Collection<DeveloperFeed> getTypeARecrods(Connection conn, String startTime,	String endTime) throws DBIssueException {
		
		log.debug("Fetching Type A <bulk> records:");

		PreparedStatement stmt = null;
		Collection<Integer> allianceIds = new ArrayList<Integer>();
		
		try {

			StringBuffer typeAQuery = new StringBuffer( 
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

					/*

					+ "and alliance.alliance_id NOT IN "
					+ "("
					+ "select distinct alliance_id "
					+ "from aims_alliance_contracts a_contract_in, aims_contracts contracts_in "
					+ "where contracts_in.platform_id = ? "
					+ "and a_contract_in.status = ? "
					+ "and a_contract_in.accept_decline_date < TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') "
					+ ")"
					+ "and a_contract.accept_decline_date IN "
					+ "("
					+ "select min(a_contract_in3.accept_decline_date) from aims_alliance_contracts a_contract_in3, aims_contracts contract_in3 "
					+ "where a_contract_in3.alliance_id =  a_contract.alliance_id "
					+ "and a_contract_in3.CONTRACT_ID = contract_in3.CONTRACT_ID "
					+ "and contract_in3.PLATFORM_ID = ? "
					+ "and a_contract_in3.STATUS = ? " + "	)"

					*/

					);

			stmt = conn.prepareStatement(typeAQuery.toString());
			log.info("Type A <bulk> query :: " + typeAQuery);
			
			stmt.setLong(1, CafeedConstants.CONTRACTS_PLATFORM_JAVA);
			stmt.setString(2, CafeedConstants.CONTRACTS_ACCEPTED_STATUS);
			stmt.setString(3, startTime);
			stmt.setString(4, endTime);

			/*
			stmt.setLong(5, CafeedConstants.CONTRACTS_PLATFORM_JAVA);
			stmt.setString(6, CafeedConstants.CONTRACTS_ACCEPTED_STATUS);
			stmt.setString(7, startTime);
			stmt.setLong(8, CafeedConstants.CONTRACTS_PLATFORM_JAVA);
			stmt.setString(9, CafeedConstants.CONTRACTS_ACCEPTED_STATUS);
			*/


			ResultSet rSet = stmt.executeQuery();
			Collection<DeveloperFeed> feeds = new ArrayList<DeveloperFeed>();			
			SimpleDateFormat formatter = new SimpleDateFormat(CafeedConstants.FEED_TIME_FORMAT);

			while (rSet.next()) {
				
				DeveloperFeed feed = new DeveloperFeed();
				Integer alliance_id = rSet.getInt(1);
				Integer contract_id = rSet.getInt(2);
				Date acceptance_time = rSet.getTimestamp(3);
				String formattedDate = formatter.format(acceptance_time);
				String allianceName = rSet.getString(4);
				Integer vendorId = rSet.getInt(5);
				allianceIds.add(alliance_id);
				feed.setRecordType('A');
				feed.setSortOrder(CafeedConstants.SORT_ORDER_1);
				feed.setAllianceId(alliance_id);
				feed.setAllianceName(allianceName);
				feed.setContractId(contract_id);
				feed.setAcceptRejectTime(acceptance_time);
				feed.setFormattedDate(formattedDate);
				feed.setVendorId(vendorId);
				feeds.add(feed);
			}
			log.debug("A Total of " + feeds.size() + " Type A <bulk> records found.");
			log.debug("Type A <bulk> Records fetching done!!!");

			return feeds;
		}

		catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DBIssueException(e.getMessage());
		}
	}
}