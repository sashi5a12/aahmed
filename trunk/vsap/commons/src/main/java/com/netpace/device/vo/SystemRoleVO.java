package com.netpace.device.vo;

import com.netpace.commons.vo.Record;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class SystemRoleVO extends Record {

    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private String roleName;
    private String displayTitle;
    private boolean hidden;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public int hashCode() {
        if (roleId == null) {
            return new Long(0).hashCode();
        }

        return new Long(roleId).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SystemRoleVO)) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
