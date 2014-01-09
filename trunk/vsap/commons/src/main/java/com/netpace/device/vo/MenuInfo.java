package com.netpace.device.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;

public class MenuInfo implements Serializable{
    private static final long serialVersionUID = -1L;
    
    private String name;
    List<KeyValuePair> submenu = new ArrayList<KeyValuePair>();
    
    public String getName() {
        return name;
    }

    public void setName(String menu) {
        this.name = menu;
    }

    public List<KeyValuePair> getSubmenu() {
        return Collections.unmodifiableList(submenu);
    }

    public void addSubmenuItem(KeyValuePair pair) {
        this.submenu.add(pair);
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
