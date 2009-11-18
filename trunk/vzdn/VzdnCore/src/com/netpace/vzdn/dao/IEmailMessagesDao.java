package com.netpace.vzdn.dao;

import java.io.Serializable;
import com.netpace.vzdn.model.VzdnEmailMessagesAtt;


public interface IEmailMessagesDao<T,PK extends Serializable> extends IGenericDAO<T, PK>{
	public VzdnEmailMessagesAtt getEmailAttachment(Integer id) throws Exception;
}
