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

/**
 * @author Ikramullah Khan
 * @version 0.1, 10/06/2009
 * @since JDK1.6
 */

public class TypeCSelectorImpl extends AbstractSelector {

	private static Logger log = Logger.getLogger(TypeCSelectorImpl.class
			.getName());

	public TypeCSelectorImpl() {
		super(new FeedType('C',
				"First Contract Accepted -- Already Registered Alliance",
				"First Contract Accepted -- Already Registered Alliance"));
	}

	public Collection<DeveloperFeed> execute(Connection conn, String startTime,
			String endTime) throws DBIssueException {
		Collection<DeveloperFeed> typeCIds = getAllianceIdsOnDirection(conn,
				startTime, endTime);
		return typeCIds;
	}

	private Collection<DeveloperFeed> getAllianceIdsOnDirection(
			Connection conn, String startTime, String endTime)
			throws DBIssueException {

		log.debug("Fetching Type C records!!!");

		PreparedStatement stmt = null;
		PreparedStatement stmt_alliance_name = null;
		Collection<Integer> allianceIds = new ArrayList<Integer>();

		try {

			StringBuffer typeCQuery = new StringBuffer(
					"select "
							+ "distinct "
							+ "a_contract.alliance_id, a_contract.contract_id,  a_contract.accept_decline_date,  "
							+ "alliance.vendor_id, a_contract.aud_action, contracts.contract_ref_id "

							+ "from aims_alliance_contracts$aud a_contract, aims_contracts$aud contracts, aims_alliances$aud alliance  "
							+ "where contracts.contract_id = a_contract.contract_id "
							+ "and alliance.alliance_id = a_contract.alliance_id "
							+ "and contracts.platform_id = ?  "
							+ "and a_contract.status = ? "
							+ "and (a_contract.AUD_ACTION = ? or a_contract.AUD_ACTION= ?) "
							+ "and vendor_id is not null "
							+ "and a_contract.accept_decline_date > TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') "
							+ "and a_contract.accept_decline_date < sysdate "
							+ "and alliance.alliance_id NOT IN  "
							+ "(  "
							+ "select distinct alliance_id "
							+ "from aims_alliance_contracts a_contract, aims_contracts contracts "
							+ "where contracts.platform_id = ?  "
							+ "and a_contract.status = ?  "
							+ "and (a_contract.AUD_ACTION = ? or a_contract.AUD_ACTION=?) "
							+ "   and a_contract.accept_decline_date < TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') "
							+ ") "
							+ "and (a_contract.alliance_id,accept_decline_date) not in (select a_contract.alliance_id,min(a_contract.accept_decline_date) accept_decline_date "
							+ "from aims_alliance_contracts$aud a_contract, aims_contracts$aud contracts, aims_alliances$aud alliance  "
							+ "where contracts.contract_id = a_contract.contract_id "
							+ "and alliance.alliance_id = a_contract.alliance_id "
							+ "and contracts.platform_id = ? "
							+ "and a_contract.status = ? "
							+ "and (a_contract.AUD_ACTION = ? or a_contract.AUD_ACTION= ? ) "
							+ "and vendor_id is not null "
							+ "and a_contract.accept_decline_date > TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') "
							+ "and a_contract.accept_decline_date < sysdate "
							+ "and alliance.alliance_id NOT IN  "
							+ "( "
							+ "select distinct alliance_id "
							+ "from aims_alliance_contracts a_contract, aims_contracts contracts "
							+ "where contracts.platform_id = ?  "
							+ "and a_contract.status = ?  "
							+ "and (a_contract.AUD_ACTION = ? or a_contract.AUD_ACTION= ?) "
							+ "and a_contract.accept_decline_date < TO_DATE(? , 'MM/DD/YYYY HH24:MI:SS') "
							+ ") " + "group by a_contract.alliance_id ) ");

			StringBuffer companyNameQuery = new StringBuffer(

					"select distinct allianceMain.company_name, allianceMain.aud_timestamp from aims_alliances$aud allianceMain where "
							+ "allianceMain.aud_timestamp in "
							+ "( "
							+ "select max(allianceOuter.aud_timestamp) "
							+ "from "
							+ "( "
							+ "select distinct alliance.aud_timestamp "
							+ "from aims_alliances$aud alliance "
							+ "where alliance.aud_timestamp <= TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') "
							+ "and alliance.alliance_id = ?   "
							+ ")allianceOuter " + ")");

			log.info("Type C Query to be executed::" + typeCQuery);

			stmt = conn.prepareStatement(typeCQuery.toString());

			stmt.setLong(1, CafeedConstants.CONTRACTS_PLATFORM_JAVA);
			stmt.setString(2, CafeedConstants.CONTRACTS_ACCEPTED_STATUS);
			stmt.setString(3, CafeedConstants.AUDIT_ACTION_INSERT);
			stmt.setString(4, CafeedConstants.AUDIT_ACTION_UPDATE);
			stmt.setString(5, startTime);

			stmt.setLong(6, CafeedConstants.CONTRACTS_PLATFORM_JAVA);
			stmt.setString(7, CafeedConstants.CONTRACTS_ACCEPTED_STATUS);
			stmt.setString(8, CafeedConstants.AUDIT_ACTION_INSERT);
			stmt.setString(9, CafeedConstants.AUDIT_ACTION_UPDATE);
			stmt.setString(10, startTime);

			stmt.setLong(11, CafeedConstants.CONTRACTS_PLATFORM_JAVA);

			stmt.setString(12, CafeedConstants.CONTRACTS_ACCEPTED_STATUS);
			stmt.setString(13, CafeedConstants.AUDIT_ACTION_INSERT);
			stmt.setString(14, CafeedConstants.AUDIT_ACTION_UPDATE);
			stmt.setString(15, startTime);

			stmt.setLong(16, CafeedConstants.CONTRACTS_PLATFORM_JAVA);
			stmt.setString(17, CafeedConstants.CONTRACTS_ACCEPTED_STATUS);
			stmt.setString(18, CafeedConstants.AUDIT_ACTION_INSERT);
			stmt.setString(19, CafeedConstants.AUDIT_ACTION_UPDATE);
			stmt.setString(20, startTime);

			ResultSet rSet = stmt.executeQuery();
			Collection<DeveloperFeed> feeds = new ArrayList<DeveloperFeed>();
			SimpleDateFormat formatter = new SimpleDateFormat(
					CafeedConstants.FEED_TIME_FORMAT);
			SimpleDateFormat compNameFormatter = new SimpleDateFormat(
					CafeedConstants.FEED_TIME_FORMAT);

			while (rSet.next()) {
				DeveloperFeed feed = new DeveloperFeed();
				Integer alliance_id = rSet.getInt(1);
				Integer contract_id = rSet.getInt(2);
				Date acceptance_time = rSet.getTimestamp(3);
				String accTime = compNameFormatter.format(acceptance_time);

				String allianceName = "";
				stmt_alliance_name = conn.prepareStatement(companyNameQuery
						.toString());
				stmt_alliance_name.setString(1, accTime);
				stmt_alliance_name.setLong(2, alliance_id);
				ResultSet rSetAllianceName = stmt_alliance_name.executeQuery();

				while (rSetAllianceName.next()) {
					allianceName = rSetAllianceName.getString(1);
				}

				Integer vendorId = rSet.getInt(4);
				String audAction = rSet.getString(5);
				Integer contract_ref_id = rSet.getInt(6);
				allianceIds.add(alliance_id);
				String formattedDate = formatter.format(acceptance_time);
				feed.setRecordType('C');
				feed.setAcceptRejectTime(acceptance_time);
				feed.setFormattedDate(formattedDate);
				feed.setSortOrder(CafeedConstants.SORT_ORDER_2);
				feed.setAllianceId(alliance_id);
				feed.setAllianceName(allianceName);
				feed.setContractId(contract_ref_id);
				feed.setAcceptRejectTime(acceptance_time);
				feed.setVendorId(vendorId);
				feeds.add(feed);
			}
			log.debug("A Total of " + feeds.size() + " Type C records found.");
			log.debug("Type C records fetching done!!!");
			return feeds;

		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DBIssueException(e.getMessage());
		} finally {
			if (null != stmt) {
				try {
					stmt.close();
				} catch (SQLException sqlE) {
					log.error(sqlE.getMessage(), sqlE);
				}
			}
			if (null != stmt_alliance_name) {
				try {
					stmt_alliance_name.close();
				} catch (SQLException sqlE) {
					log.error(sqlE.getMessage(), sqlE);
				}
			}
		}
	}
}