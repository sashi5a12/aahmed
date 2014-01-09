/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.entities.enums;

/**
 *
 * @author home
 */
public enum Role {
    ROLE_MPOC(1),ROLE_PARTNER_USER(2),ROLE_ADMIN(3);
    
    private Integer id;

    private Role(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
}
