/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netpace.device.services;

import com.netpace.device.vo.UserInfoResend;

/**
 *
 * @author Hamza Ghayas
 */
public interface UserAccountService {
    String name = "userAccountService";
    public void forgotUsername(UserInfoResend userInfoResend);
    public void forgotPassword(UserInfoResend userInfoResend, String resetPasswordURL);
    
    public void resetPassword(String password,String resetPasswordToken );
}
