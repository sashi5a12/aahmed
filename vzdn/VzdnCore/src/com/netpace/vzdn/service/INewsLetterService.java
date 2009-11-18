package com.netpace.vzdn.service;

import java.sql.Blob;

import com.netpace.vzdn.model.VzdnNewsletterEmailLog;

public interface INewsLetterService {
	
	public void saveNewsLetter(VzdnNewsletterEmailLog newsletterEmailLog, byte[] content) throws Exception;
	
	public String[] getVerifySubscribedUsers(String[] emailAddress) throws Exception;

}
