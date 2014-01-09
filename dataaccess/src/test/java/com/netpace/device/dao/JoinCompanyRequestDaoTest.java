package com.netpace.device.dao;

import com.netpace.device.AbstractDaoTest;
import com.netpace.device.entities.JoinCompanyRequest;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;


public class JoinCompanyRequestDaoTest extends AbstractDaoTest {

    protected final Log log = LogFactory.getLog(getClass());
    @Resource(name = JoinCompanyRequestDao.name)
    private JoinCompanyRequestDao joinCompanyRequestDao;
    
    @Test
    public void testGetAll(){
        Integer userId = 83;
        String companyDomain = "http://www.netpace.com.99";
        
        //testGetRequestByUserId(userId);
        //testGetPendingUser(companyDomain, userId);
        //testGellAllCompanyUsers(6);
        //testGetCompanyIdByOfferedUserId();
    }
    
    public void testGetRequestByUserId(Integer userId){
        JoinCompanyRequest joinCompanyRequest = joinCompanyRequestDao.getRequestByOfferedUserId(userId);
        log.info(ToStringBuilder.reflectionToString(joinCompanyRequest));
    }
    
    public void testGetPendingUser(String companyDomain, Integer userId){
        List<JoinCompanyRequest> userInPendingStatus = joinCompanyRequestDao.getUserInPendingStatus(companyDomain, userId);
        log.info(ToStringBuilder.reflectionToString(userInPendingStatus));
    }
 
    public void testGellAllCompanyUsers(Integer userId) {
        List<JoinCompanyRequest> companyUsers = joinCompanyRequestDao.getAllCompanyUsers(userId);
        log.info("******************************* Company usres **********************************");
        for (int i = 0; i < companyUsers.size(); i++) {
            JoinCompanyRequest joinCompanyRequest = companyUsers.get(i);
            log.info(ToStringBuilder.reflectionToString(joinCompanyRequest));
        }
    }
    
    public void testGetCompanyIdByOfferedUserId() {
        log.info("******************************* Company Id **********************************");
        log.info("Company id: " + joinCompanyRequestDao.getCompanyIdByOfferedUserId(250));
        
        log.info("******************************* Company Id **********************************");
        log.info("Company id: " + joinCompanyRequestDao.getCompanyIdByOfferedUserId(3250));
    }
}
