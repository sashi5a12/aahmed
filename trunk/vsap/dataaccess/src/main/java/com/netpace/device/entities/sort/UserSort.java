package com.netpace.device.entities.sort;

import com.netpace.device.entities.User;

/**
 *
 * @author home
 */
public enum UserSort implements Sort<User> {
    USER_NAME("userName"),EMAIL_ADDRESS("emailAddress"),FULL_NAME("fullName");

    private UserSort(String field) {
        this.field = field;
    }
    
    String field;

    @Override
    public String getField() {
        if(this.field != null)
            return this.field;
        else
            return getDefaultSort().getField();
    }

    @Override
    public Sort<User> getDefaultSort() {
        return USER_NAME;
    }
}
