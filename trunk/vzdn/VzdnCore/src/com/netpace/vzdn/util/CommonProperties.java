package com.netpace.vzdn.util;

import org.apache.log4j.Logger;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class CommonProperties {
    private static final String propsFileName = "CommonProps.properties";

    private static Logger log = Logger.getLogger(CommonProperties.class.getName());
    private Properties envProperties = null;

    private static CommonProperties instance = null;

    private CommonProperties() {
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
     *  This method returns the singleton instance of the CommonProperties.
     *  Care has been taken to make this method thread safe.
     */
    public static CommonProperties getInstance() {
        if (instance == null) {
            synchronized(ConfigEnvProperties.class) {
                if (instance == null) {
                    instance = new CommonProperties();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        String property = envProperties.getProperty(key);
        if(property == null) {
            log.debug("CommonProperties.getProperty: Property "+key+" not found in "+propsFileName);
        }
        return property;
    }
}