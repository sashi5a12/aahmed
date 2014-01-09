/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.dao;

import com.netpace.device.entities.ApplicationProperties;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import java.util.List;

/**
 *
 * @author Wakram
 */
public interface ApplicationPropertiesDao extends GenericDao<ApplicationProperties, Integer>  {
    public static final String name="applicationPropertiesDao";
    
    public List<ApplicationProperties> getPropertiesByType(ApplicationPropertyType type);
    public ApplicationProperties getPropertiesByTypeAndKey(ApplicationPropertyType type,String key);
    
    public List<ApplicationProperties> getSortedProperties();
    
    public void probeHit();
}
