package com.netpace.aims.ca.utils;

import org.apache.log4j.Logger;

import com.netpace.aims.ca.model.CachedSuite;

/**  
* @author  Ikramullah Khan
* @version 0.1, 10/06/2009
* @since   JDK1.6
*/

public class CafeedCache {
	
    private static Logger log = Logger.getLogger(CafeedCache.class.getName());
    
    private CachedSuite cachedSuite;
    private static CafeedCache instance = null;

    private CafeedCache(CachedSuite cachedSuite) {
        loadProperties(cachedSuite);
    }

    private void loadProperties(CachedSuite cachedSuite) {
        log.debug("Loading Cached Elements: ");
        if(this.cachedSuite == null)
        	this.cachedSuite = cachedSuite;
        else
        	log.debug("Cache elements cannot be overriden:");
    }

    /**
     *  This method returns the singleton instance of the CafeedCache.
     *  Care has been taken to make this method thread safe.
     */
    public static CafeedCache getInstance(CachedSuite cachedSuite) {
        if (instance == null) {
            synchronized(CafeedCache.class) {
                if (instance == null) {
                    instance = new CafeedCache(cachedSuite);
                }
            }
        }
        return instance;
    }

	public CachedSuite getCachedSuite() {
		return cachedSuite;
	}  
   
}
