package com.netpace.vic.dao;

import com.netpace.vic.dto.EmailLog;

public interface EmailLogDAO extends GenericDAO{

	public void saveEmailLog(EmailLog emailLog);

}
