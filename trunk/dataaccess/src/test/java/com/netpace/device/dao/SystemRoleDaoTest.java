package com.netpace.device.dao;

import com.netpace.device.AbstractDaoTest;
import com.netpace.device.entities.SystemRole;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SystemRoleDaoTest extends AbstractDaoTest{
     
    protected final Log log = LogFactory.getLog(getClass());
    @Autowired
    private GenericReadOnlyDao<SystemRole,Integer> systemRoleDao;
    
    @Test
    public void testListAllRoles(){
        log.info("testListAllRoles");
        List<SystemRole> roles = systemRoleDao.getAll();
        for (SystemRole systemRole : roles) {
            log.info("System Role Found : "+systemRole.getTitle() +"\t-\t"+systemRole.getDescription());
        }
    }
    
}
