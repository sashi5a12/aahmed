package com.netpace.device.dao;

import com.netpace.device.entities.User;
import com.netpace.device.entities.sort.Sort;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.UserInfo;
import java.util.List;

public interface UserDao extends GenericDao<User, Integer> {

    public static final String name = "userDao";

    public User getUserByUserName(String userName);

    public User getUserByEmailAddress(String email);

    public User getUserByPRToken(String passwordResetToken);

    public PaggedResult<User> getUsersList(Sort<User> sort, boolean sortOrder, Integer startPosition, Integer pageSize);

    public List<User> getUsersList(Integer start, Integer limit);

    public Long getUsersCount();

    public User getUserById(Integer userId);

    public List<User> getUsersByCompanyId(Integer companyId);

    public PaggedResult<User> getPaggedUsers(PageModel<UserInfo> pageModel);

    public List<User> getUsersList(PageModel<UserInfo> pageModel);
    
    public User getPartnerUserById(Integer partnerId, Integer userId);
    
    public User getPartnerUserByIdForEdit(Integer partnerId, Integer userId);
    
    public User getAdminUserByIdForEdit(Integer userId);
    
    public User getMPOC(Integer companyId);
    
    public List<String> getUserEmailsByCompanyId(Integer companyId);
    
    public List<String> getRoleUserEmailsByCompanyId(Integer companyId, Integer systemRoleId);
    
    public void updateUserLastLoginDate(Integer userId);
}
