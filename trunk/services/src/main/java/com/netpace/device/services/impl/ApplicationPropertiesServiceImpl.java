/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.services.impl;

import com.netpace.device.dao.ApplicationPropertiesDao;
import com.netpace.device.entities.ApplicationProperties;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.services.ApplicationPropertiesService;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hamza Ghayas
 */
@Service(value = "applicationPropertiesService")
public class ApplicationPropertiesServiceImpl implements ApplicationPropertiesService {

    private static final Log log = LogFactory.getLog(ApplicationPropertiesServiceImpl.class);
    @Autowired
    ApplicationPropertiesDao applicationPropertiesDao;    

    @Override
    public SortedMap<String, String> getApplicationPropertiesMap(ApplicationPropertyType type) {
        SortedMap<String, String> map = new TreeMap<String, String>();
        List<ApplicationProperties> lst = applicationPropertiesDao.getPropertiesByType(type);
        for (ApplicationProperties ap : lst) {
            map.put(ap.getName(), ap.getValue());
        }
        return map;
    }

    @Override
    public ApplicationProperties getApplicationPropertiesByTypeAndKey(ApplicationPropertyType type, String key) {
        ApplicationProperties applicationProperties = applicationPropertiesDao.getPropertiesByTypeAndKey(type, key);
        return applicationProperties;
    }

    @Override
    public String geValuesAsCommaSeparateString(ApplicationPropertyType type) {
        StringBuilder result = new StringBuilder();
        SortedMap<String, String> lst = propertiesByType(type);
        for (Map.Entry<String, String> entry : lst.entrySet()) {
            result.append(entry.getValue());
            result.append(",");
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1) : "";
    }
    private SortedMap<ApplicationPropertyType, SortedMap<String, String>> propertiesCache = new TreeMap<ApplicationPropertyType, SortedMap<String, String>>();

    @PostConstruct
    public void initPropertiesCache() {
        log.info("Initializing application properties...");
        refreshPropertiesCache();
    }

    @Override
    public void refreshPropertiesCache() {
        log.info("Refreshing application properties...");
        log.info("Setting existing map to null");

        SortedMap<ApplicationPropertyType, SortedMap<String, String>> tempPropertiesCache =
                new TreeMap<ApplicationPropertyType, SortedMap<String, String>>();

        List<ApplicationProperties> sortedProperties = applicationPropertiesDao.getSortedProperties();

        log.info("Number of properties fetched from database = [" + sortedProperties.size() + "]");
        for (ApplicationProperties property : sortedProperties) {
            addPropertyToCache(tempPropertiesCache, property);
        }
        propertiesCache = tempPropertiesCache;
    }

    private void addPropertyToCache(SortedMap<ApplicationPropertyType, SortedMap<String, String>> propertiesCache, ApplicationProperties property) {
//        log.info("Property: Type ["+property.getType() +"] Name : ["+property.getName()+"] Value : ["+property.getValue()+"]");
        if (propertiesCache.get(property.getType()) != null) {
            propertiesCache.get(property.getType()).put(property.getName(), property.getValue());
        } else {
            SortedMap<String, String> innerSortedMap = new TreeMap<String, String>();
            innerSortedMap.put(property.getName(), property.getValue());

            propertiesCache.put(property.getType(), innerSortedMap);
        }
    }

    @Override
    public String propertyByNameAndType(ApplicationPropertyType type, String name) {
        if (propertiesCache.get(type) == null) {
            return null;
        }

        return propertiesCache.get(type).get(name);
    }

    @Override
    public SortedMap<String, String> propertiesByType(ApplicationPropertyType type) {
        return propertiesCache.get(type);
    }

    @Override
    public Integer defaultPageSize() {
        String pageSizeStr = propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "vap.displaylist.page.size");

        return Integer.parseInt(pageSizeStr);
    }

    /**
     * statics.url is the web location of javascript, css and
     *
     * @return
     */
    @Override
    public String staticsURL() {
        return propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "statics.url");
    }

    @Override
    public String staticsVersion() {
        return propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "statics.version");
    }
    
    @Override
    public String defaultEmailHost() {
        return propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "vap.email.host");
    }

    @Override
    public String defaultNotificationEmailFromAddress() {
        return propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "vap.notification.email.from");
    }

    @Override
    public String defaultNotificationEnvironment() {
        return propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "vap.notification.env");
    }

    @Override
    public String defaultNotificationEnvironmentDevSendTo() {
        return propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "vap.notification.env.dev.sendto");
    }
    
    @Override
    public String defaultVerizonAdminEmailAddress() {
        return propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "email.verizon.admin");
    }
    
    @Override
    public Integer defaultActivationExpiryDays() {
         String noOfDays = propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "activation.expires.in.days");

        return Integer.parseInt(noOfDays);
    }
    
    @Override
    public boolean processNotificaitonsEagerly() {
        String status = propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "process.notificaitons.eagerly");
        
        return Boolean.valueOf(status);
    }
    
    @Override
    public Integer processNotificaitonsBatchLimit() {
         String batchLimit = propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "process.notificaitons.batch.limit");

        return Integer.parseInt(batchLimit);
    }
    
    @Override
    public void probeHit(){
    	applicationPropertiesDao.probeHit();
    }

    @Override
    public String exceptionSendtoEmail(){
         return propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "exception.sendto.email");
    }

    @Override
    public Integer workitemDelayInDays(){
         String delay = propertyByNameAndType(ApplicationPropertyType.APPLICATION_PROPERTY, "workitem.delay.in.days");

        return Integer.parseInt(delay);
    }
}