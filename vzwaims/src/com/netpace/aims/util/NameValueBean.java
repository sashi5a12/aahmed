package com.netpace.aims.util;

public class NameValueBean
{
	private String name;
	private String value;
	private Integer nameValueId;
    private Integer parentId;

    public NameValueBean(Integer nameValueId, String value) {
        this.nameValueId = nameValueId;
        this.value = value;
    }

    public NameValueBean(Integer nameValueId, Integer parentId, String value) {
        this.nameValueId = nameValueId;
        this.value = value;
        this.parentId = parentId;
    }

    public NameValueBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getNameValueId() {
        return nameValueId;
    }

    public void setNameValueId(Integer nameValueId) {
        this.nameValueId = nameValueId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}