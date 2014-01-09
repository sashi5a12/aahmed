package com.netpace.device;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;


public class UtilTest {
    
    private SecureRandom random = new SecureRandom();
    
    @Test
    public void testUniqueString(){
        String uuid1 = UUID.randomUUID().toString();
        System.out.println(uuid1);
        
    }
    
    @Test
    public void testDate(){
        Date oldDate = new Date();
        Date newDate = DateUtils.addDays(oldDate, -5);
        
        System.out.println(oldDate);
        System.out.println(newDate);
    }
}
