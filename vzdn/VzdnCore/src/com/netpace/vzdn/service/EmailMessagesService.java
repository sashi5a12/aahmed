package com.netpace.vzdn.service;

import java.util.List;

import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEmailMessagesAtt;

public interface EmailMessagesService {
	
	public List<VzdnEmailMessages> getAll();
	public VzdnEmailMessages getEmailMessageById(Integer id);
	public VzdnEmailMessagesAtt getEmailAttachment(Integer id) throws Exception;	
}
