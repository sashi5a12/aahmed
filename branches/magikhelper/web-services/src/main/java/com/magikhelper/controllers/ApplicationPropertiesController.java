package com.magikhelper.controllers;

import com.magikhelper.entities.ApplicationProperties;
import com.magikhelper.entities.enums.ApplicationPropertyType;
import com.magikhelper.services.ApplicationPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aahmed
 */
@RestController
@RequestMapping("/data")
public class ApplicationPropertiesController {

    @Autowired
    private ApplicationPropertiesService applicationProperties;

    @RequestMapping(value="/property",method = RequestMethod.GET,headers="Accept=application/json")
    public ApplicationProperties getProperty(@RequestParam(value = "key", required = true, defaultValue = "Afghanistan") String key) {
        ApplicationProperties property = applicationProperties.getApplicationPropertiesByTypeAndKey(ApplicationPropertyType.COUNTRIES, key);
        return property;
    }
}
