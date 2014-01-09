package com.netpace.device.services;

import com.netpace.device.BaseServiceTest;
import com.netpace.device.utils.Page;
import com.netpace.device.utils.PagingOptions;
import com.netpace.device.vo.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends BaseServiceTest{
    
    private static final Log log = LogFactory.getLog(UserServiceTest.class);
    
    @Autowired private UserService userService;
    
    @Test
    public void testPaggedData(){
        PagingOptions paging = new PagingOptions(0, 10, "USER_NAME", true);
        Page<UserInfo> page = userService.getList(null, paging);
        log.info("Current Page : "+page.getCurrentPage());
        log.info("Sort Order : "+page.getSortOrder());
        log.info("Page Size : "+page.getPageSize());
        log.info("Total Pages : "+page.getTotalPages());
        log.info("Total Records : "+page.getTotalRecs());
        for (UserInfo user : page.getRecords()) {
            log.info(user);
        }
        
        
    }
}
