package com.netpace.vic.service.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.commons.datasource.poolservice.DataSourcePool;
import com.netpace.vic.dao.EmailLogDAO;
import com.netpace.vic.dao.impl.GenericDAOImpl;
import com.netpace.vic.dto.EmailLog;
import com.netpace.vic.service.EmailLogService;
import com.netpace.vic.util.DAOFactory;

@Component
@Service
public class EmailLogServiceImpl implements EmailLogService {
	
	@Reference
    private DataSourcePool source;
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public void saveEmailLog(EmailLog emailLog) {
		LOGGER.info("============ EmailLogServiceImpl.saveEmailLog() Start ==============");
		EmailLogDAO dao = DAOFactory.getEmailLogDAO();
		dao.setSource(source);
		dao.saveEmailLog(emailLog);
		LOGGER.info("============ EmailLogServiceImpl.saveEmailLog() End ==============");
	}

}
