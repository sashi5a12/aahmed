package com.netpace.vic.service;

import java.util.List;

import com.netpace.vic.dto.Partner;

public interface PartnerService {
	public List<Partner> getPartnerList();
	public Partner getPartner(Integer partnerId);
	public byte[] getImage(Integer mediaId);
}
