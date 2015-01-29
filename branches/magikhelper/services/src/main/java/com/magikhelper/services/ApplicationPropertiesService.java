package com.magikhelper.services;

import com.magikhelper.entities.ApplicationProperties;
import com.magikhelper.entities.enums.ApplicationPropertyType;
import java.util.SortedMap;

public interface ApplicationPropertiesService {

    public ApplicationProperties getApplicationPropertiesByTypeAndKey(ApplicationPropertyType type, String key);

    public SortedMap<String, String> getApplicationPropertiesMap(ApplicationPropertyType type);

    public void refreshPropertiesCache();

    public String propertyByNameAndType(ApplicationPropertyType type, String name);

    public SortedMap<String, String> propertiesByType(ApplicationPropertyType type);

    public String geValuesAsCommaSeparateString(ApplicationPropertyType type);
}
