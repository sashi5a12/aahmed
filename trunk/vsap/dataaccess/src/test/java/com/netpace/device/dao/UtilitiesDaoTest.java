package com.netpace.device.dao;

import com.netpace.device.AbstractDaoTest;
import com.netpace.device.entities.ApplicationProperties;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class UtilitiesDaoTest  extends AbstractDaoTest{
    protected final Log log = LogFactory.getLog(getClass());
    
    @Resource(name=UtilitiesDao.name)
    UtilitiesDao utilDao;
    
    @Resource(name=ApplicationPropertiesDao.name)
    private ApplicationPropertiesDao applicationPropertiesDao;
    @Test
    public void testList(){
        utilDao.getMenuInfo(13);
    }
    
    @Test
    public void testListApplicationProperties(){
        List<ApplicationProperties> properties = applicationPropertiesDao.getPropertiesByType(ApplicationPropertyType.COUNTRIES);
        for (ApplicationProperties applicationProperties : properties) {
            log.info(applicationProperties.getSortOrder()+" -- "+applicationProperties.getName()+" -- "+applicationProperties.getValue());
        }
    }
    
}
