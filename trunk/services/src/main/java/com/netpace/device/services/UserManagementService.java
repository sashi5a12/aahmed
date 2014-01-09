package com.netpace.device.services;

import com.netpace.device.vo.AccountRegistration;
import com.netpace.device.vo.ActivateAdminVO;
import com.netpace.device.vo.InviteUserVO;
import com.netpace.device.vo.PageModel;
import com.netpace.device.vo.UserInfo;
import com.netpace.device.vo.VAPUserDetails;
import java.util.List;

public interface UserManagementService {

    public List<UserInfo> getUserslist(UserInfo userInfo, Integer start, Integer limit);

    public Long getUsersCount(UserInfo userInfo);

    public void inviteUser(InviteUserVO inviteUserVO, String invitationURL, VAPUserDetails loggedInUser);

    public InviteUserVO getUserInvite(String activationCode);

    public InviteUserVO getUserInvite(String activationCode, String activationType);

    public void activateInviteUser(AccountRegistration accountRegistration);

    public void activateInviteAdmin(ActivateAdminVO activateAdminVO);

    public void reActivateAdmin(ActivateAdminVO activateAdminVO);

    public PageModel<UserInfo> getPagedList(PageModel<UserInfo> pageModel);

    public PageModel<UserInfo> getUsersList(UserInfo userInfo, PageModel<UserInfo> pageModel);
    
    public void editAdminUser(UserInfo userInfo, VAPUserDetails loggedInUser);
    
    public void deleteAdminUser(Integer userId, VAPUserDetails loggedInUser);
}
