package com.netpace.vic.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.netpace.vic.dao.PartnerDAO;
import com.netpace.vic.dto.Partner;
import com.netpace.vic.util.ConnectionUtil;
import com.netpace.vic.util.DTOConverter;

public class PartnerDAOImpl extends GenericDAOImpl implements PartnerDAO{
	
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());	
	
	public List<Partner> getPartnerList()  {
		LOGGER.info("========= PartnerDAOImpl.getPartnerList() Start =========");	    
	    String query = "SELECT p.partner_id, p.partner_name, m.media_id, p.featured, p.url, p.sort_order "+
                       " FROM partners p INNER JOIN media m ON p.partner_id = m.media_type_id where m.media_type = 'partner' "+
                       " AND row_enabled = '1' order by p.featured DESC , p.sort_order";
        ResultSet rs = null;
        Connection connection = null;;
        PreparedStatement preparedStatement = null;
        List<Partner> partnerList = new ArrayList<Partner>();
        
        try {
        	connection = super.getConnection();
        	preparedStatement = connection.prepareStatement(query);
        	rs = preparedStatement.executeQuery();
        	partnerList = DTOConverter.getPartnerList(rs);
        	LOGGER.info("PartnerList() Size : "+partnerList.size());	    
        } catch(SQLException se) {
        	LOGGER.info("SQLException :" + se.toString());
        } catch(Exception e) {
        	LOGGER.info("Exception :"+ e.toString());
        } finally {
            ConnectionUtil.close(rs);
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
        LOGGER.info("End getPartnerList() ...");	       
	    return partnerList;
	}
	
	public Partner getPartner(Integer partnerId) {
		LOGGER.info("========= PartnerDAOImpl.getPartner() Start ============");
	    String query = "SELECT p.partner_id, p.partner_name, m.media_id, p.short_desc, p.long_desc, p.url "+
                       " FROM partners p INNER JOIN media m ON p.partner_id = m.media_type_id  " +
	    		       " where p.partner_id = ? and m.media_type = 'partner'";
        ResultSet rs = null;
        Connection connection = null;;
        PreparedStatement preparedStatement = null;
        Partner partner = new Partner();
        
        try {
        	connection = super.getConnection();
        	preparedStatement = connection.prepareStatement(query);
        	preparedStatement.setInt(1, partnerId);
        	rs = preparedStatement.executeQuery();
        	partner = DTOConverter.getPartnerDTO(rs);
        	
        } catch(SQLException se) {
        	LOGGER.info(se.toString(),se);
        } catch(Exception e) {
        	LOGGER.info(e.toString(),e);
        } finally {
            ConnectionUtil.close(rs);
            ConnectionUtil.close(preparedStatement);
            ConnectionUtil.close(connection);
        }
        LOGGER.info("========= PartnerDAOImpl.getPartner() End ============");    
	    return partner;
	}
	
	public byte[] getImage(Integer mediaId){
		
		LOGGER.info("========= PartnerDAOImpl.getImage() =========== "+mediaId);
		String query = "select file_data from media where media_id=?";
		ResultSet rs = null;
		Connection connection = null;;
		PreparedStatement preparedStatement = null;
		byte image[] = null;
		
		try {
			connection = super.getConnection();
			preparedStatement = connection.prepareStatement(query);
     		preparedStatement.setInt(1,mediaId);
     		rs = preparedStatement.executeQuery();
     		while(rs.next())
     			image = rs.getBytes(1);
			
		 } catch(SQLException se) {
	     	LOGGER.info("SQLException :" + se.toString());
	     } catch(Exception e) {
	     	LOGGER.info("Exception :"+ e.toString());
	     } finally {
	         ConnectionUtil.close(rs);
	         ConnectionUtil.close(preparedStatement);
	         ConnectionUtil.close(connection);
	     }
		LOGGER.info("========= PartnerDAOImpl.getImage() ==============");		
		return image;
	}
	

}
