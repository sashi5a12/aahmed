package com.netpace.vzdn.header;

import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;


public class GlobalNavProperties {
    private static final String propsFileName = "globalNavProps.properties";//file name pf properties file

    private Properties envProperties = null;

    private static GlobalNavProperties instance = null;

    private GlobalNavProperties() {
        loadProperties();
    }

    private void loadProperties() {
        System.out.println("Loading "+propsFileName+" ..... ");

        InputStream in = null;

        envProperties = new Properties();
        in = this.getClass().getResourceAsStream("/"+propsFileName);

        try {
            if(in != null) {
                envProperties.load(in);
                System.out.println(propsFileName+" loaded successfully");
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
     *  This method returns the singleton instance of the GlobalNavProperties.
     *  Care has been taken to make this method thread safe.
     */
    public static GlobalNavProperties getInstance() {
        if (instance == null) {
            synchronized(GlobalNavProperties.class) {
                if (instance == null) {
                    instance = new GlobalNavProperties();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        String property = envProperties.getProperty(key);
        if(property == null) {
           System.out.println("GlobalNavProperties.getProperty: Property "+key+" not found in "+propsFileName);
        }
        return property;
    }
}
