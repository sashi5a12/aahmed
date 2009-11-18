package com.netpace.vzdn.dao;

import java.io.Serializable;

import com.netpace.vzdn.model.VzdnNewsletterEmailLog;

public interface INewsLetterDao<T, PK extends Serializable> extends IGenericDAO<T, PK> {

	public boolean verifyEmailAddress(String emailAddress) throws Exception;
	
	public void updateNewsLetterLog(VzdnNewsletterEmailLog newsletterEmailLog , byte[] content) throws Exception;

}
