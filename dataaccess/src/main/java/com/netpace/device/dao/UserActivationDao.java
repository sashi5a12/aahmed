package com.netpace.device.dao;

import com.netpace.device.entities.UserActivation;

public interface UserActivationDao extends GenericDao<UserActivation, Long> {
    public static final String name="userActivationDao";
    
    public UserActivation search(String userName);
    public UserActivation searchByEmailAddress(String emailAddress);
    public UserActivation searchByActivationCode(String activationCode);
    public UserActivation searchByActivationCodeAndType(String activationCode, String activationType);
    
    public void removeExpiredRecords();
    public void removeExpiredRecords(Integer noOfDays);
    public void removeDuplicateInviteUserRecord(String emailAddress, String activationType);
    public void removeDuplicateInviteUserRecord(String emailAddress);
}
