package com.magikhelper.vo;

import java.io.Serializable;

public class Record implements Serializable{
    private Integer id;
    private Integer versionNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }
    
}
