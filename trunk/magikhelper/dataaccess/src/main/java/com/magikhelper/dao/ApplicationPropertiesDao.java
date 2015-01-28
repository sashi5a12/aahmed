/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.magikhelper.dao;

import com.magikhelper.entities.ApplicationProperties;
import com.magikhelper.entities.enums.ApplicationPropertyType;
import java.util.List;

public interface ApplicationPropertiesDao extends GenericDao<ApplicationProperties, Integer> {

    public static final String name = "applicationPropertiesDao";

    public List<ApplicationProperties> getPropertiesByType(ApplicationPropertyType type);

    public ApplicationProperties getPropertiesByTypeAndKey(ApplicationPropertyType type, String key);

    public List<ApplicationProperties> getSortedProperties();

    public void probeHit();
}
