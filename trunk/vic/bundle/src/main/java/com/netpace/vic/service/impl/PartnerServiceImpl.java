package com.netpace.vic.service.impl;

import java.util.List;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import com.day.commons.datasource.poolservice.DataSourcePool;
import com.netpace.vic.dao.PartnerDAO;
import com.netpace.vic.dto.Partner;
import com.netpace.vic.service.PartnerService;
import com.netpace.vic.util.DAOFactory;

@Component
@Service
public class PartnerServiceImpl implements PartnerService{

	@Reference
	private DataSourcePool source;
	
	public List<Partner> getPartnerList() {
		PartnerDAO partnerDao = DAOFactory.getPartnerDAO();
		partnerDao.setSource(source);
		return partnerDao.getPartnerList();
	}

	public Partner getPartner(Integer partnerId) {
		PartnerDAO partnerDao = DAOFactory.getPartnerDAO();
		partnerDao.setSource(source);
		return partnerDao.getPartner(partnerId);
	}
	
	public byte[] getImage(Integer mediaId){
		PartnerDAO partnerDao = DAOFactory.getPartnerDAO();
		partnerDao.setSource(source);
		return partnerDao.getImage(mediaId);
	}
}
