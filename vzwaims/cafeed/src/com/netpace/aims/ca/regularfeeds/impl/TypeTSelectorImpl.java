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

public class TypeTSelectorImpl extends AbstractSelector {

	private static Logger log = Logger.getLogger(TypeTSelectorImpl.class
			.getName());

	public TypeTSelectorImpl() {
		super(new FeedType('T', "Contract Deleted",
				"Contract Deleted ... Description"));
	}

	public Collection<DeveloperFeed> execute(Connection conn, String startTime,
			String endTime) throws DBIssueException {
		Collection<DeveloperFeed> deletedContractAlliances = getTypeTFeeds(
				conn, startTime, endTime);
		return deletedContractAlliances;
	}

	private Collection<DeveloperFeed> getTypeTFeeds(Connection conn,
			String fromDate, String toDate) throws DBIssueException {
		log.debug("Fetching Type T records:");
		PreparedStatement stmt = null;
		PreparedStatement stmt_alliance_name = null;

		try {

			StringBuffer typeTRecords = new StringBuffer(
					"select "
							+ "distinct "
							+ "alliance.alliance_id, cont.aud_timestamp, contracts.contract_ref_id, alliance.vendor_id "
							+ "from "
							+ "aims_alliance_contracts$aud cont, "
							+ "aims_alliances$aud alliance, "
							+ "aims_contracts$aud contracts "
							+ "where cont.aud_action= ? "
							+ "and alliance.alliance_id = cont.alliance_id "
							+ "and contracts.contract_id = cont.contract_id "
							+ "and alliance.vendor_id is not null "
							+ "and cont.aud_timestamp > TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') "
							+ "and cont.aud_timestamp < TO_DATE(?, 'MM/DD/YYYY HH24:MI:SS') "
							+ "and cont.status = ? ");

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

			log.info("Type T Query ::" + typeTRecords);
			stmt = conn.prepareStatement(typeTRecords.toString());

			stmt.setString(1, CafeedConstants.AUDIT_ACTION_DELETE);
			stmt.setString(2, fromDate);
			stmt.setString(3, toDate);
			stmt.setString(4, CafeedConstants.CONTRACTS_ACCEPTED_STATUS);

			ResultSet rSet = stmt.executeQuery();
			Collection<DeveloperFeed> feeds = new ArrayList<DeveloperFeed>();
			SimpleDateFormat formatter = new SimpleDateFormat(
					CafeedConstants.RETRIEVE_TIME_FORMAT);
			SimpleDateFormat compNameFormatter = new SimpleDateFormat(
					CafeedConstants.FEED_TIME_FORMAT);

			while (rSet.next()) {
				DeveloperFeed feed = new DeveloperFeed();
				Integer alliance_id = rSet.getInt(1);
				Date acceptance_time = rSet.getTimestamp(2);
				String accTime = compNameFormatter.format(acceptance_time);
				Integer contract_id = rSet.getInt(3);
				Integer vendorId = rSet.getInt(4);
				String allianceName = "";

				stmt_alliance_name = conn.prepareStatement(companyNameQuery
						.toString());
				stmt_alliance_name.setString(1, accTime);
				stmt_alliance_name.setLong(2, alliance_id);
				ResultSet rSetAllianceName = stmt_alliance_name.executeQuery();

				while (rSetAllianceName.next()) {
					allianceName = rSetAllianceName.getString(1);
				}

				String formattedDate = formatter.format(acceptance_time);
				feed.setAcceptRejectTime(acceptance_time);
				feed.setFormattedDate(formattedDate);
				feed.setRecordType('T');
				feed.setSortOrder(CafeedConstants.SORT_ORDER_4);
				feed.setAllianceId(alliance_id);
				feed.setContractId(contract_id);
				feed.setAcceptRejectTime(acceptance_time);
				feed.setVendorId(vendorId);
				feed.setFormattedDate(formattedDate);
				feed.setAllianceName(allianceName);
				feeds.add(feed);
			}
			log.debug("A Total of " + feeds.size() + " Type T records found.");
			log.debug("Type T records fetching done!!!");
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