package com.netpace.device.dao;

import com.netpace.device.AbstractDaoTest;
import com.netpace.device.entities.User;
import com.netpace.device.entities.sort.UserSort;
import com.netpace.device.utils.VAPConstants;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class UserDaoTest extends AbstractDaoTest{
     
    protected final Log log = LogFactory.getLog(getClass());
    @Resource(name=UserDao.name)
    private UserDao userDao;
    
    @Test
    public void testUserByNameNotExists(){
        log.info("testUserByNameNotExists()");
        User u = userDao.getUserByUserName("uiqbal");
        log.info("Found user "+u);
//        Assert.assertNull(u);
    }
    @Test
    public void testUserByEmailAddressNotExists(){
        User u = userDao.getUserByEmailAddress("uiqbal@netpace.com");
        log.info("Found user "+u);
//        Assert.assertNull(u);
    }
    @Test
    public void testUserPaggedData(){
        userDao.getUsersList(UserSort.USER_NAME, true, 1, 3);
    }
    
    @Test
    public void testAddUser(){
        User user = new User();
        user.setCompanyDomain("netpace.com");
        user.setEmailAddress("uiqbal@netpace.com");
        user.setFullName("Umair Iqbal");
        user.setMobile("+1 345 234 2344");
        user.setPassword("netpace");
        user.setPhone("+1 715 121 3265");
        user.setUserName("uiqbal");        
        user.populatedAuditFields("uiqbal");
        userDao.add(user);
    }
    
    public void testGetUserEmailsByCompanyId(){
        List<String> partnerEmails = userDao.getUserEmailsByCompanyId(109);
        
        for(String emailAddress: partnerEmails){
            System.out.println(emailAddress);
        }
    }

    public void testGetUserEmailsByMPOC(){
        List<String> partnerEmails = userDao.getRoleUserEmailsByCompanyId(109, VAPConstants.ROLE_MPOC_ID);
        
        for(String emailAddress: partnerEmails){
            System.out.println(emailAddress);
        }
    }
}
