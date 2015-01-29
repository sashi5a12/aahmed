package com.magikhelper.services;

import com.magikhelper.entities.ApplicationProperties;
import com.magikhelper.entities.enums.ApplicationPropertyType;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicationPropertiesServiceTest extends BaseServiceTest {

    private static final Log log = LogFactory.getLog(ApplicationPropertiesServiceTest.class);

    @Autowired
    private ApplicationPropertiesService applicationProperties;

    public ApplicationPropertiesServiceTest() {
    }

    @Test
    public void testService() {
        String property = applicationProperties.propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "vap.email.host");
        log.debug("Property ========> "+property);
        
        String properties = applicationProperties.geValuesAsCommaSeparateString(ApplicationPropertyType.COUNTRIES);
        log.debug("Properties ==========> " + properties);
    }
}
