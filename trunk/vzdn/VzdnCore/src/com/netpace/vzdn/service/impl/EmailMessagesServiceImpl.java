package com.netpace.vzdn.service.impl;

import java.util.List;

import com.netpace.vzdn.dao.IEmailMessagesDao;
import com.netpace.vzdn.model.VzdnEmailMessages;
import com.netpace.vzdn.model.VzdnEmailMessagesAtt;
import com.netpace.vzdn.service.EmailMessagesService;


public class EmailMessagesServiceImpl implements EmailMessagesService{

	
	private IEmailMessagesDao<VzdnEmailMessages,Integer> messagesDao;
	
	public List<VzdnEmailMessages> getAll(){
			return messagesDao.findAll();
	}
	
	public VzdnEmailMessages getEmailMessageById(Integer id){
		return messagesDao.findById(id);
	}

	public IEmailMessagesDao<VzdnEmailMessages, Integer> getMessagesDao() {
		return messagesDao;
	}

	public void setMessagesDao(
			IEmailMessagesDao<VzdnEmailMessages, Integer> messagesDao) {
		this.messagesDao = messagesDao;
	}
	
	public VzdnEmailMessagesAtt getEmailAttachment(Integer id) throws Exception{
		return messagesDao.getEmailAttachment(id);
	}
}
