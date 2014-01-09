/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.services;

import com.netpace.device.vo.VAPUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author wakram
 */
public interface VAPUserDetailsService extends UserDetailsService  {
    
    public void updateUserLastLoginDate(VAPUserDetails loggedInUser);
    
}
