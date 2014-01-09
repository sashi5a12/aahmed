package com.netpace.device.services;

import com.netpace.device.entities.UserActivation;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.vo.AccountRegistration;

public interface RegistrationService {
    public void registerAccount(AccountRegistration accountInfo, String activationURL)throws BusinessRuleException;
    public AccountRegistration activateRegisteredAccount(String activationCode)throws BusinessRuleException;
    public UserActivation activateRegisteredAccount(String activationCode, String activationType)throws BusinessRuleException;
    public void deleteExpiredActivations(Integer noOfDays);
}
