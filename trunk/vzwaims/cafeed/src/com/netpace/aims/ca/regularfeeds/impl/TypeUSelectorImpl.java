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
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/
public class TypeUSelectorImpl extends AbstractSelector {

	private static Logger log = Logger.getLogger(TypeUSelectorImpl.class
			.getName());

	public TypeUSelectorImpl() {
		super(new FeedType('U', "Alliance Profile Update","Alliance Profile Updated --- Description"));
	}

	public Collection<DeveloperFeed> execute(Connection conn, String startTime, String endTime)throws DBIssueException {	
		Collection<DeveloperFeed> updatedRecords = getUpdatedRecords(conn, startTime, endTime);
		return updatedRecords;		
	}	
	public Collection<DeveloperFeed> getUpdatedRecords(Connection conn, String startTime, String endTime) throws DBIssueException{
		log.debug("Fetching Type U records !!!");

		PreparedStatement stmt = null;
		PreparedStatement stmt_alliance_name = null;
		Collection<Integer> allianceIds = new ArrayList<Integer>();
	
		try {
	
			StringBuffer typeUMain = new StringBuffer(
				
				"select " 
				+ "distinct alliance.alliance_id, alliance.vendor_id, alliance.company_name, alliance.aud_action, alliance.aud_timestamp " 
				+ "from aims_alliances$aud alliance "
				+ "where   "
				+ "alliance.aud_action= ? " 
				+ "and alliance.aud_timestamp > TO_DATE( ? ,'MM/DD/YYYY HH24:MI:SS') " 
				+ "and alliance.aud_timestamp < sysdate " 				
				+ "order by alliance.aud_timestamp desc "
				);

	
			StringBuffer typeUSecondary = new StringBuffer(
				
				"select " 
				+ "maxRow.company_name " 
				+ "from   "
				+ " ( "
				+ "select "
				+ "alliance.alliance_id, alliance.company_name, alliance.aud_action, alliance.aud_timestamp " 
				+ "from aims_alliances$aud alliance "
				+ "where  "
				+ "alliance.aud_action= ? "
				+ "and alliance.alliance_id = ? "
				+ "and alliance.aud_timestamp < TO_DATE( ? ,'MM/DD/YYYY HH24:MI:SS') " 
				+ "order by alliance.aud_timestamp desc "
				+ ")  maxRow where ROWNUM = ? "
				);

			stmt = conn.prepareStatement(typeUMain.toString());
			
			log.info("\nType U Main Query ::\n\n" + typeUMain);
			log.info("\nQuery for getting last updated record <for comparision> ::\n\n" + typeUSecondary);
			
			stmt.setString(1, CafeedConstants.AUDIT_ACTION_UPDATE);			
			stmt.setString(2, startTime);

			ResultSet rSet = stmt.executeQuery();
			Collection<DeveloperFeed> feeds = new ArrayList<DeveloperFeed>();
			SimpleDateFormat formatter = new SimpleDateFormat(CafeedConstants.FEED_TIME_FORMAT);
			
			while (rSet.next()) {
					DeveloperFeed feed = new DeveloperFeed();
					Integer alliance_id = rSet.getInt(1);
					Integer vendor_id = rSet.getInt(2);
					String allianceName = rSet.getString(3);
					String auditAction = rSet.getString(4);					
					Date acceptance_time = rSet.getTimestamp(5);
					String acceptance_time_str = formatter.format(acceptance_time);
					stmt_alliance_name = conn.prepareStatement(typeUSecondary.toString());					
					stmt_alliance_name.setString(1, CafeedConstants.AUDIT_ACTION_UPDATE);
					stmt_alliance_name.setLong(2, alliance_id);
					stmt_alliance_name.setString(3, acceptance_time_str);
					stmt_alliance_name.setLong(4,CafeedConstants.TOP_ONE);
					
					ResultSet rSetAllianceName = stmt_alliance_name.executeQuery();
					
					boolean nameChanged = false;
					while(rSetAllianceName.next()){
						String allianceName_temp = rSetAllianceName.getString(1);
						if(!allianceName.equals(allianceName_temp)){
							nameChanged = true;
						}						
					}
					
					allianceIds.add(alliance_id);
					String formattedDate = formatter.format(acceptance_time);					
					feed.setRecordType('U');
					
					feed.setAcceptRejectTime(acceptance_time);			
					feed.setFormattedDate(formattedDate);
					feed.setSortOrder(CafeedConstants.SORT_ORDER_5);
					feed.setAllianceId(alliance_id);
					feed.setAllianceName(allianceName);
					feed.setVendorId(vendor_id);
					
					if(nameChanged){
						feeds.add(feed);
					}
			}
			log.debug("A Total of " + feeds.size() + " Type U records found.");
			log.debug("Type U records fetching done!!!");
			
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
			if(null != stmt_alliance_name)
			{
				try{	
					stmt_alliance_name.close();
				}			
				catch (SQLException sqlE){
					log.error(sqlE.getMessage(), sqlE);
				}
			}
		}
	}
}