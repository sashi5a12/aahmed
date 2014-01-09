package com.netpace.vic.dao;

import java.util.List;

import com.netpace.vic.dto.Partner;

public interface PartnerDAO extends GenericDAO{

	public List<Partner> getPartnerList();
	public Partner getPartner(Integer partnerId);
	public byte[] getImage(Integer mediaId);
	
}
