package com.netpace.device.services;

import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.vo.*;
import java.util.List;

public interface CompanyService {

    public CompanyRegistration findCompanyByUserId(Integer userId);

    public CompanyProfile getCompanyProfile(VAPUserDetails loggedInUser);

    public CompanyProfileWithActiveUser getCompanyProfileWithUser(int companyId);

    public void updateCompanyInfo(VAPUserDetails loggedInUser, CompanyProfileWithActiveUser companyProfile) throws BusinessRuleException;

    public void deleteCompany(VAPUserDetails loggedInUser, Integer companyId) throws BusinessRuleException;
    
    public void suspendOrUnsuspendCompany(VAPUserDetails loggedInUser, Integer companyId, boolean suspendOrUnsuspend) throws BusinessRuleException;

    public List<CompanyProfile> getAllCompanies();

    public void getPartnersList(PageModel<CompanyListVO> pageModel);

    public void acceptRequestToJoinCompany(VAPUserDetails loggedInUser, Integer offerId);

    public void rejectRequestToJoinCompany(VAPUserDetails loggedInUser, Integer offerId);

    public void inviteUser(InvitePartnerUserVO invitePartnerUserVO, String invitationLink, VAPUserDetails loggedInUser);

    public PageModel<JoinCompanyRequestVO> getAllCompanyUsers(
            VAPUserDetails loggedInUser, PageModel<JoinCompanyRequestVO> pageModel);

    public void editUser(UserInfo editPartnerUserVO, VAPUserDetails loggedInUser);

    public void deleteUser(Integer userId, VAPUserDetails loggedInUser);

    public CompanyVO getCompanyById(Integer id);

    public PageModel<PartnerUserVO> getAllPartnerUsers(VAPUserDetails loggedInUser, PageModel<PartnerUserVO> pageModel);

    public boolean isCompanyApproved(Integer companyId);

    public Integer findCompanyIdOfPendingUser(Integer userId);
    
    public boolean isCompanyNameDuplicate(String companyName);
}
