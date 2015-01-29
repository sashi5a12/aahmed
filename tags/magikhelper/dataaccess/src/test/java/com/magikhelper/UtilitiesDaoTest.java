package com.magikhelper;

import com.magikhelper.dao.ApplicationPropertiesDao;
import com.magikhelper.entities.ApplicationProperties;
import com.magikhelper.entities.enums.ApplicationPropertyType;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class UtilitiesDaoTest extends AbstractDaoTest {

    protected final Log log = LogFactory.getLog(getClass());

    @Resource(name = ApplicationPropertiesDao.name)
    private ApplicationPropertiesDao applicationPropertiesDao;

    @Test
    public void testListApplicationProperties() {
        List<ApplicationProperties> properties = applicationPropertiesDao.getPropertiesByType(ApplicationPropertyType.COUNTRIES);
        for (ApplicationProperties applicationProperties : properties) {
            log.info(applicationProperties.getSortOrder() + " -- " + applicationProperties.getName() + " -- " + applicationProperties.getValue());
        }
    }

}
