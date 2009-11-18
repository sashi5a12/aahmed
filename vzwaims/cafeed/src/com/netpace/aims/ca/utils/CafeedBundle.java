package com.netpace.aims.ca.utils;

import org.apache.log4j.Logger;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class CafeedBundle {
	
	private static final String propsFileName = "cafeed.properties";

    private static Logger log = Logger.getLogger(CafeedBundle.class.getName());
    private Properties envProperties = null;

    private static CafeedBundle instance = null;

    private CafeedBundle() {
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
     *  This method returns the singleton instance of the ConfigEnvProperties.
     *  Care has been taken to make this method thread safe.
     */
    public static CafeedBundle getInstance() {
        if (instance == null) {
            synchronized(CafeedBundle.class) {
                if (instance == null) {
                    instance = new CafeedBundle();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        String property = envProperties.getProperty(key);
        if(property == null) {
            log.debug("ConfigEnvProperties.getProperty: Property "+key+" not found in "+propsFileName);
        }
        return property;
    }
}
