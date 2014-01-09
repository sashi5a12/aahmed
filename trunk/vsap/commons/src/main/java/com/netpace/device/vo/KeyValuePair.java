package com.netpace.device.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

public class KeyValuePair extends Record{
    private String key;
    private String value;

    public KeyValuePair(Integer id,String key, String value) {
        this.setId(id);
        this.key = key;
        this.value = value;
    }
    
    public KeyValuePair(String key, String value) {
        this(null, key, value);
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    
}
