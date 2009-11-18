package com.netpace.aims.util;

import org.apache.log4j.Logger;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class ZonwsProperties {
    private static final String propsFileName = "zonws.properties";

    private static Logger log = Logger.getLogger(ZonwsProperties.class.getName());
    private Properties envProperties = null;

    private static ZonwsProperties instance = null;
    
    private ZonwsProperties() {
        loadProperties();
    }

    private void loadProperties() {
        log.debug("Loading "+propsFileName+" ..... ");

        InputStream in = null;

        envProperties = new Properties();
        in = this.getClass().getResourceAsStream("/"+propsFileName);

        try {
            if(in != null) {
                envProperties.load(in);
                log.debug(propsFileName+" loaded successfully");
            }
        }
        catch (IOException ioe) {
            System.out.println("Exception caught while loading "+propsFileName);
            ioe.printStackTrace();
        }
        finally {
            if(in != null ){
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  This method returns the singleton instance of the ZonwsProperties.
     *  Care has been taken to make this method thread safe.
     */
    public static ZonwsProperties getInstance() {
        if (instance == null) {
            synchronized(ZonwsProperties.class) {
                if (instance == null) {
                    instance = new ZonwsProperties();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        String property = envProperties.getProperty(key);
        if(property == null) {
            log.debug("ZonwsProperties.getProperty: Property "+key+" not found in "+propsFileName);
        }
        return property;
    }
    
}
