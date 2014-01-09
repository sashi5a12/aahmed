package com.netpace.device.services;

import com.netpace.device.entities.UserRole;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.utils.Page;
import com.netpace.device.utils.PagingOptions;
import com.netpace.device.vo.ChangePasswordVO;
import com.netpace.device.vo.ChangeUserNameVO;
import com.netpace.device.vo.UserAccessInfo;
import com.netpace.device.vo.UserInfo;
import com.netpace.device.vo.UserInfoResend;
import com.netpace.device.vo.VAPUserDetails;
import java.util.List;

public interface UserService {

    public void releaseAccessToken(VAPUserDetails loggedInUser);

    public UserInfo findUserByName(String userName);

    public UserInfo findUserByEmailAddress(String emailAddress);

    public UserInfoResend passwordResetTokenExists(String passResetToken);

    public UserAccessInfo getAccessInfo(VAPUserDetails loggedInUser);

    public Page<UserInfo> getList(VAPUserDetails loggedInUser, PagingOptions pagingOptions);

    public UserInfo getMyProfile(VAPUserDetails loggedInUser);

    public void updateMyProfile(UserInfo profile, VAPUserDetails loggedInUser) throws BusinessRuleException;

    public void updateUserName(ChangeUserNameVO changeUserNameVO, VAPUserDetails loggedInUser) throws BusinessRuleException;

    public void changePassword(ChangePasswordVO changePasswordVO, VAPUserDetails loggedInUser) throws BusinessRuleException;

    public String[] getUserRoles(Integer userId);

    public List<UserRole> getUserRolesById(Integer userId);

    public UserInfo getUserById(Integer userId);

    public UserInfo getPartnerUserById(Integer companyId, Integer userId);

    public UserInfo getAdminUserById(Integer userId);
}
