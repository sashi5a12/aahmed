/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.filters;

import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.services.ApplicationPropertiesService;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 *
 * @author Wakram
 */
public class VAPSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final static Log log = LogFactory.getLog(VAPSecurityMetadataSource.class);
    
    @Resource(name = "applicationPropertiesService")
    private ApplicationPropertiesService applicationProperties;
    
    @Override
    public List<ConfigAttribute> getAttributes(Object object) {
      FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        String httpMethod = fi.getRequest().getMethod();
        String queryString = fi.getRequest().getQueryString();
        
        log.info("------Securiy Metadata Source ------>>>>>> START");
        log.info("Security roles for url ["+url+"] httpMethod ["+httpMethod+"] are being asked. Query string is ["+queryString+"]");
        
        url = StringUtils.removeEnd(url,"?"+queryString);
        log.info("url after removing query string ["+url+"]");
        String roles = mappedRolesByURL(url);
        log.info("Roles required for the url ["+url+"] are ["+roles+"]");
        log.info("------Securiy Metadata Source ------>>>>>> END");
        
        return SecurityConfig.createListFromCommaDelimitedString(roles);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
      return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
      return FilterInvocation.class.isAssignableFrom(clazz);
    }
    
    private String mappedRolesByURL(String url) {
        String mappedRoles=null;
        if ( StringUtils.isEmpty(url ) ) {
            log.info("Input parameter url is null or empty. Returning null");            
            return null;
        }
        
        while (!StringUtils.isEmpty(url)) {
            mappedRoles=applicationProperties.propertyByNameAndType(ApplicationPropertyType.ACCESS_CONTROL_METADATA, url);
            log.info("url ["+url+"] mappedRoles ["+mappedRoles+"]");
            if ( mappedRoles!=null || url.equals("/"))
                break;
            
            String[] urlParts = url.split("/");                                    
            log.info("Removing ["+urlParts[urlParts.length-1]+"] from url ["+url+"]");
            url = StringUtils.removeEnd(url,urlParts[urlParts.length-1]);
            if (!url.equals("/")) {
                url = StringUtils.removeEnd(url,"/");
            }
        }                 
        
        return mappedRoles;
    }
}
