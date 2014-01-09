package com.netpace.device.dao;

import com.netpace.device.AbstractDaoTest;
import com.netpace.device.entities.UserActivation;
import java.util.List;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivationDaoTest extends AbstractDaoTest{
     
    protected final Log log = LogFactory.getLog(getClass());
    @Autowired
    private UserActivationDao userDao;
    
    @Test
    public void testAbc(){
        UserActivation u = new UserActivation();
        u.setUserName("Umair");
        userDao.add(u);
        
        UserActivation u1 = new UserActivation();
        u1.setUserName("Umair2");
        userDao.add(u1);
        
        List<UserActivation> lst = userDao.getAll();
        for (UserActivation user : lst) {
            log.debug(user.getUserName()+"---"+user.getUserId());
        }
    }
    
    @Test
    public void testUserByNameNotExists(){
        UserActivation u = userDao.search("unknown");
        Assert.assertNull(u);
    }
    @Test
    public void testUserByEmailAddressNotExists(){
        UserActivation u = userDao.searchByEmailAddress("unknown");
        Assert.assertNull(u);
    }
    
}
