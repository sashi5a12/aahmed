/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.services;

import com.netpace.device.entities.ApplicationProperties;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import java.util.SortedMap;

/**
 *
 * @author Wakram
 */
public interface ApplicationPropertiesService {        
    
    public ApplicationProperties getApplicationPropertiesByTypeAndKey(ApplicationPropertyType type, String key);
    public SortedMap<String, String> getApplicationPropertiesMap(ApplicationPropertyType type);
    
    
    
    
    public void refreshPropertiesCache();
    public String propertyByNameAndType(ApplicationPropertyType type, String name);
    public SortedMap<String, String> propertiesByType(ApplicationPropertyType type);
    public Integer defaultPageSize();
    public String staticsURL();
    public String staticsVersion();
    
    public String geValuesAsCommaSeparateString(ApplicationPropertyType type);

    public String defaultEmailHost();
    public String defaultNotificationEmailFromAddress();
    public String defaultNotificationEnvironment();
    public String defaultNotificationEnvironmentDevSendTo();
    public String defaultVerizonAdminEmailAddress();
    public Integer defaultActivationExpiryDays();
    public boolean processNotificaitonsEagerly();
    public Integer processNotificaitonsBatchLimit();
    
    public void probeHit();
    
    public String exceptionSendtoEmail();
    public Integer workitemDelayInDays();
}