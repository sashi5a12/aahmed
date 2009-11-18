package com.netpace.aims.controller.application;

import org.apache.log4j.Logger;


public class WapAppVersionBean {

    static Logger log = Logger.getLogger(WapAppVersionBean.class.getName());
    
    private String fieldName;
    private String oldValue;
    private String newValue;
			
	
		
    
    /**
     * @return
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * @return
     */
    public String getNewValue()
    {
        return newValue;
    }

    /**
     * @return
     */
    public String getOldValue()
    {
        return oldValue;
    }

    /**
     * @param string
     */
    public void setFieldName(String string)
    {
        fieldName = string;
    }

    /**
     * @param string
     */
    public void setNewValue(String string)
    {
        newValue = string;
    }

    /**
     * @param string
     */
    public void setOldValue(String string)
    {
        oldValue = string;
    }

}

