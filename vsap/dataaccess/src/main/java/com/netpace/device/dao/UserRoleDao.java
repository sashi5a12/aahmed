package com.netpace.device.dao;

import com.netpace.device.entities.UserRole;
import java.util.List;

public interface UserRoleDao extends GenericDao<UserRole, Integer> {
    public static final String name="userRoleDao";
    public List<UserRole> getUserRoleByUserId(Integer userId);
}
