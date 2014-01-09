package com.netpace.vic.service.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.commons.datasource.poolservice.DataSourcePool;
import com.netpace.vic.dao.UserApplicationDAO;
import com.netpace.vic.dto.UserApplication;
import com.netpace.vic.service.UserApplicationService;
import com.netpace.vic.util.DAOFactory;
import com.netpace.vic.util.MailUtils;

@Component
@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    @Reference
    private DataSourcePool source;
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public void saveUserApplication(UserApplication userApplication) {
        LOGGER.info("============ UserApplicationServiceImpl.saveUserApplication Start ============");
        UserApplicationDAO dao = DAOFactory.getUserApplicationDAO();
        dao.setSource(source);
        dao.saveUserApplication(userApplication);
        MailUtils.sendEmailToDeveloper(userApplication);
        MailUtils.sendEmailToVerizon(userApplication);
        LOGGER.info("============ UserApplicationServiceImpl.saveUserApplication End ============");
    }

}
