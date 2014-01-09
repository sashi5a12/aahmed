package com.netpace.device.services;

import com.netpace.device.BaseServiceTest;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.vo.KeyValuePair;
import java.util.List;
import java.util.SortedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UtilitiesServiceTest extends BaseServiceTest {

    private final static Log log = LogFactory.getLog(UtilitiesServiceTest.class);
    @Autowired
    UtilitiesService utilitiesService;
    @Autowired
    UserService userService;
    @Autowired
    ApplicationPropertiesService applicationProperties;

    @Test
    public void testMenuInformation() {
//        AccessToken token = userService.getAccessToken("admin");
//        List<MenuInfo> menu = utilitiesService.getMenuInformation(token);
//        System.out.println(menu);
    }

    @Test
    public void testApplicationProperties() {
        SortedMap<String, String> countries = applicationProperties.propertiesByType(ApplicationPropertyType.COUNTRIES);
        
        for ( String key : countries.keySet() ) {
            log.info(key + " : " + countries.get(key));
        }
    }
}
