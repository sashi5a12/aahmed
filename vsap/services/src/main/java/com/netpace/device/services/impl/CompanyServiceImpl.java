package com.netpace.device.services.impl;

import com.netpace.commons.utils.VAPUtils;
import com.netpace.device.annotation.utils.ETDConverter;
import com.netpace.device.annotation.utils.RoleUtil;
import com.netpace.device.dao.*;
import com.netpace.device.entities.*;
import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.entities.enums.JoinCompanyStatus;
import com.netpace.device.enums.ActivationType;
import com.netpace.device.enums.CompanyRegistrationBusinessRule;
import com.netpace.device.enums.CompanyValidationBusinessRule;
import com.netpace.device.enums.EventType;
import com.netpace.device.enums.UserValidationBusinessRule;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.exceptions.RecordNotFoundException;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.services.CompanyService;
import com.netpace.device.utils.DateUtils;
import com.netpace.device.utils.UniqueStringGenerator;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.enums.WorkflowStep;
import com.netpace.device.vo.*;
import com.netpace.notification.services.EventService;
import java.sql.Timestamp;
import java.util.*;
import javax.persistence.NoResultException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "companyService")
public class CompanyServiceImpl implements CompanyService {

    private final static Log log = LogFactory.getLog(CompanyServiceImpl.class);
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private UserActivationDao userActivationDao;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private GenericReadOnlyDao<SystemRole, Integer> systemRoleDao;
    @Autowired
    private JoinCompanyRequestDao joinCompanyRequestDao;
    @Autowired
    private ApplicationPropertiesDao applicationPropertiesDao;
    @Autowired
    JoinCompanyRequestDao companyRequestDao;
    @Autowired
    private ApplicationPropertiesService applicationPropertiesService;
    @Autowired
    private WorkitemDao workitemDao;
    @Autowired
    private MediaDao mediaDao;

    @Override
    @Transactional(readOnly = true)
    public CompanyRegistration findCompanyByUserId(Integer userId) {
        Company company = companyDao.getCompanyByUserId(userId);
        if (company != null) {
            return ETDConverter.convert(company);
        }
        throw new RecordNotFoundException("No company found for user id : " + userId);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyProfile getCompanyProfile(VAPUserDetails loggedInUser) {
        CompanyProfile profile = new CompanyProfile();
        Company company = companyDao.getCompanyByUserId(loggedInUser.getId());
        profile.setCompany(ETDConverter.convert(company));
        return profile;
    }

    @Transactional(readOnly = true)
    @Override
    public CompanyProfileWithActiveUser getCompanyProfileWithUser(int companyId) {
        CompanyProfileWithActiveUser companyProfile = new CompanyProfileWithActiveUser();
        List<User> users = userDao.getUsersByCompanyId(companyId);
        if (users != null && !users.isEmpty()) {
            ETDConverter.convert(users, companyProfile);
        } else {
            throw new RecordNotFoundException("User(s) not found for company.");
        }
        return companyProfile;
    }

    @Override
    @Transactional
    public void updateCompanyInfo(VAPUserDetails loggedInUser,
            CompanyProfileWithActiveUser companyProfile) {

        Company company = companyDao.get(companyProfile.getCompany().getId());

        if (company != null) {

            String changedCompanyName =
                    companyProfile.getCompany().getCompanyName();

            if (!company.getName().equalsIgnoreCase(changedCompanyName)
                    && isCompanyNameDuplicate(changedCompanyName)) {

                log.debug("Company name: '" + changedCompanyName + "' is duplicate");
                throw new BusinessRuleException(
                        CompanyValidationBusinessRule.COMPANY_NAME_IS_DUPLICATE);
            }
            ETDConverter.convertForUpdate(companyProfile.getCompany(), company);

            String loggedInUsername = loggedInUser.getUsername();
            Integer companyId = company.getId();
            Integer newMPOCUserId = companyProfile.getMainContactId();
            Integer loggedInUserId = loggedInUser.getId();

            if (RoleUtil.canEditCompany(loggedInUser, applicationPropertiesService)
                    && isUserPartOfCompany(companyId, newMPOCUserId)
                    && !isUserAlreadyMPOC(companyId, newMPOCUserId)) {
                String loggedInUserRoles = loggedInUser.commaSeparatedRolesList();

                removePreviousMPOC(companyId);
                addNewMPOC(
                        newMPOCUserId,
                        VAPConstants.ROLE_MPOC_ID, loggedInUsername);

                /*
                 * Logged in user is partner user
                 */
                if (StringUtils.contains(
                        loggedInUserRoles, VAPConstants.ROLE_PARTNER_USER)) {

                    /*
                     * Logged in user is MPOC and assigning MPOC role to other 
                     * user
                     */
                    if (StringUtils.contains(loggedInUserRoles, VAPConstants.ROLE_MPOC)
                            && loggedInUserId != newMPOCUserId) {

                        // Remove the role from granted authorities
                        loggedInUser.removeRole(VAPConstants.ROLE_MPOC);

                    } else if (!StringUtils.contains(loggedInUserRoles, VAPConstants.ROLE_MPOC)
                            && loggedInUserId == newMPOCUserId) {

                        /*
                         * Logged in user is not MPOC and assigning MPOC role 
                         * to it self
                         */

                        // Add the role in granted authorities
                        loggedInUser.addRole(VAPConstants.ROLE_MPOC);
                    }
                }
            }
            ETDConverter.convert(
                    companyProfile.getCompany().getSalesContact(),
                    company.getSalesContact());

            company.populatedAuditFieldsOnUpdate(loggedInUsername);
            companyDao.update(company);
        }
    }

    @Override
    @Transactional
    public List<CompanyProfile> getAllCompanies() {
        List<CompanyProfile> companiesProfileList = new ArrayList<CompanyProfile>();

        List<Company> companiesList = companyDao.getAllCompanies();

        for (Company company : companiesList) {
            CompanyProfile profile = new CompanyProfile();
            profile.setCompany(ETDConverter.convert(company));
            companiesProfileList.add(profile);
        }

        return companiesProfileList;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public void getPartnersList(PageModel<CompanyListVO> pageModel) {

        if (StringUtils.isEmpty(pageModel.getSortBy())) {
            pageModel.setSortBy("c.name");
            pageModel.setAscending(true);
        }
        if ("Search By Partner Name".equals(pageModel.getSearchValue())) {
            pageModel.setSearchValue("");
        }
        log.debug("CompanyServiceImpl.getPartnersList Start ---------------");
        log.debug("sortBy = " + pageModel.getSortBy());
        log.debug("sortOrder = " + pageModel.isAscending());
        log.debug("searchBy = " + pageModel.getSearchBy());
        log.debug("searchValue = " + pageModel.getSearchValue());
        log.debug("pageSize = " + pageModel.getPageSize());
        log.debug("page = " + pageModel.getPage());
        log.debug("filters = " + pageModel.getFilters());

        List<CompanyListVO> partners = new ArrayList<CompanyListVO>();

        Long count = companyDao.getPartnersListCount(pageModel);

        List<Object[]> partnersObj = companyDao.getPartnersList(pageModel);
        for (Object[] obj : partnersObj) {
            CompanyListVO vo = new CompanyListVO();
            vo.setId((Integer) obj[0]);
            vo.setName((String) obj[1]);
            vo.setPoc((String) obj[2]);
            vo.setCreatedDate((Timestamp) obj[3]);
            vo.setWorkflowSteps(VAPUtils.formatWorkflowSteps((String) obj[4]));
            vo.setSuspend((Boolean)obj[5]);
            partners.add(vo);
        }

        log.debug("-------------Start Search Result------------------------");
        log.debug("list = " + partners);
        log.debug("count = " + count);
        log.debug("-------------End Search Result------------------------");

        pageModel.setTotalRecords(count.intValue());
        pageModel.setRecords(partners);

        log.debug("CompanyServiceImpl.getPartnersList End ---------------");
    }

    @Override
    @Transactional
    public void acceptRequestToJoinCompany(VAPUserDetails loggedInUser, Integer offerId) {
        Map<String, String> params;
        List<String> partnerEmails = null;
        Company company;
        List<String> roleUserEmails;
        String emailAddressMPOC = null;
        
        User user = userDao.getUserById(loggedInUser.getId());

        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        JoinCompanyRequest jcr = joinCompanyRequestDao.getRequestByOfferId(offerId);

        if (jcr == null) {
            throw new NoResultException("Request to join company not found.");
        }

        jcr.setAcceptRejectDate(DateUtils.currentTimeStamp());
        jcr.setAcceptedBy(user);
        jcr.setStatus(JoinCompanyStatus.ACCEPTED);

        company = jcr.getCompany();
        jcr.getOfferTo().setCompany(company);
        jcr.getOfferTo().populatedAuditFieldsOnUpdate(user.getUserName());

        jcr.populatedAuditFieldsOnUpdate(user.getUserName());

        joinCompanyRequestDao.update(jcr);
        
        partnerEmails = userDao.getUserEmailsByCompanyId(company.getId());
        roleUserEmails = userDao.getRoleUserEmailsByCompanyId(company.getId(), VAPConstants.ROLE_MPOC_ID);
        if(roleUserEmails != null && roleUserEmails.size() > 0)
            emailAddressMPOC = roleUserEmails.get(0);
        
        // Raise Request To Join Company Accepted Event
        params = new HashMap<String, String>();
        params.put(VAPConstants.PLACEHOLDER_COMPANY_TITLE, company.getName());
        params.put(VAPConstants.PLACEHOLDER_COMPANY_DOMAIN, company.getCompanyDomain());
        params.put(VAPConstants.PLACEHOLDER_REQUESTING_USER_FULLNAME, jcr.getOfferTo().getFullName());
        params.put(VAPConstants.PLACEHOLDER_ACCEPTING_USER_FULLNAME, user.getFullName());
        params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.PARTNER_USER_REQUEST_TO_JOIN_ACCEPTED.toString(), params, null, partnerEmails, emailAddressMPOC, loggedInUser.getUsername());
        
    }

    @Override
    @Transactional
    public void rejectRequestToJoinCompany(VAPUserDetails loggedInUser, Integer offerId) {
        Map<String, String> params;
        List<String> partnerEmails = null;
        Company company;
        String requestingUserFullname;
        List<String> roleUserEmails;
        String emailAddressMPOC = null;
        
        User user = userDao.getUserById(loggedInUser.getId());
        
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        JoinCompanyRequest jcr = joinCompanyRequestDao.getRequestByOfferId(offerId);

        if (jcr == null) {
            throw new NoResultException("Request to join company not found.");
        }
        
        company = jcr.getCompany();
        requestingUserFullname = jcr.getOfferTo().getFullName();

        // Remove the offer user record
        userDao.remove(jcr.getOfferTo().getId());

        partnerEmails = userDao.getUserEmailsByCompanyId(company.getId());
        roleUserEmails = userDao.getRoleUserEmailsByCompanyId(company.getId(), VAPConstants.ROLE_MPOC_ID);
        if(roleUserEmails != null && roleUserEmails.size() > 0)
            emailAddressMPOC = roleUserEmails.get(0);
        
        // Raise Request To Join Company Rejected Event
        params = new HashMap<String, String>();
        params.put(VAPConstants.PLACEHOLDER_COMPANY_TITLE, company.getName());
        params.put(VAPConstants.PLACEHOLDER_COMPANY_DOMAIN, company.getCompanyDomain());
        params.put(VAPConstants.PLACEHOLDER_REQUESTING_USER_FULLNAME, requestingUserFullname);
        params.put(VAPConstants.PLACEHOLDER_REJECTING_USER_FULLNAME, user.getFullName());
        params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.PARTNER_USER_REQUEST_TO_JOIN_REJECTED.toString(), params, null, partnerEmails, emailAddressMPOC, loggedInUser.getUsername());
    }

    @Override
    @Transactional
    public void inviteUser(InvitePartnerUserVO invitePartnerUserVO, String invitationURL, VAPUserDetails loggedInUser) {
        UserActivation userActivation;
        User existingUser;
        String activationCode;
        Map<String, String> eventParams;

        log.debug("Checking partner user email for duplicate, if any then don't allow invite.");
        
        existingUser = userDao.getUserByEmailAddress(invitePartnerUserVO.getEmailAddress());
        
        if(existingUser != null) {
            log.debug("Duplicate partner user email address " + existingUser.getEmailAddress());
            throw new BusinessRuleException(UserValidationBusinessRule.EMAIL_USER_EXISTS);
        }
        
        log.debug("Removing duplicate invite user record if any.");

//        userActivationDao.removeDuplicateInviteUserRecord(invitePartnerUserVO.getEmailAddress());

        log.debug("Entering inviteUser: emailAddress[" + invitePartnerUserVO.getEmailAddress() + "]");

        // populate activation bean
        userActivation = ETDConverter.convert(invitePartnerUserVO, new UserActivation());

        // generating new activation code
        activationCode = UniqueStringGenerator.generateUniqueString();
        log.debug("activation code generated: emailAddress[" + invitePartnerUserVO.getEmailAddress() + "], activationCode[" + activationCode + "]");
        userActivation.setActivationCode(activationCode);
        userActivation.setActivationType(ActivationType.OFFER_TO_JOIN_COMPANY.getLabel());
        userActivation.setCompanyDomain(loggedInUser.getCompanyDomain());
        userActivation.setUserRoles(VAPConstants.ROLE_PARTNER_USER);

        // populate audit fields
        userActivation.populatedAuditFields(loggedInUser.getUsername());

        // add user activation
        userActivationDao.add(userActivation);
        log.debug("temp partner user added");

        String companyTitle = "";
        Company company = companyDao.get(loggedInUser.getCompanyId());
        if (company != null) {
            companyTitle = company.getName();
        }

        // Raise User Registration Event
        eventParams = new HashMap<String, String>();
        String invitationLink = invitationURL + activationCode;
        eventParams.put(VAPConstants.PLACEHOLDER_INVITATION_LINK, invitationLink);
        eventParams.put(VAPConstants.PLACEHOLDER_COMPANY_TITLE, companyTitle);
        eventParams.put(VAPConstants.PLACEHOLDER_COMPANY_DOMAIN, loggedInUser.getCompanyDomain());
        eventParams.put(VAPConstants.PLACEHOLDER_FULL_NAME, userActivation.getFullName());
        eventParams.put(VAPConstants.PLACEHOLDER_INVITEE_FULLNAME, loggedInUser.getFullName());
        eventParams.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.PARTNER_USER_INVITED_EVENT.toString(), eventParams, userActivation.getEmailAddress(), null, null, null);
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<JoinCompanyRequestVO> getAllCompanyUsers(
            VAPUserDetails loggedInUser, PageModel<JoinCompanyRequestVO> pageModel) {

        // TODO What is this user has no registered company in the system      

        List<JoinCompanyRequest> joinCompanyRequestsList;
        List<JoinCompanyRequestVO> records = new ArrayList<JoinCompanyRequestVO>();
        JoinCompanyRequestVO joinCompanyRequestVO;

        joinCompanyRequestsList = joinCompanyRequestDao.getAllCompanyUsers(pageModel, loggedInUser.getCompanyId());

        // Populate PageModel
        pageModel.setTotalRecords(joinCompanyRequestsList.size());
        for (JoinCompanyRequest joinCompanyRequest : joinCompanyRequestsList) {
            joinCompanyRequestVO = ETDConverter.convert(joinCompanyRequest, new JoinCompanyRequestVO());
            records.add(joinCompanyRequestVO);
        }
        pageModel.setRecords(records);
        return pageModel;
    }

    private String validateAndReturnCompany(KeyValuePair kvp) {
        ApplicationProperties countryProperty = null;
        try {
            countryProperty = applicationPropertiesDao.getPropertiesByTypeAndKey(ApplicationPropertyType.COUNTRIES, kvp.getKey());
        } catch (Exception e) {
            log.warn("Invalid country information passed", e);
        }
        if (countryProperty == null) {
            throw new BusinessRuleException(CompanyRegistrationBusinessRule.INVALID_COUNTRY);
        }
        return countryProperty.getValue();
    }

    private String validateAndReturnCompany(String countryKey) {
        ApplicationProperties countryProperty = null;
        try {
            countryProperty = applicationPropertiesDao.getPropertiesByTypeAndKey(ApplicationPropertyType.COUNTRIES, countryKey);
        } catch (Exception e) {
            log.warn("Invalid country information passed", e);
        }
        if (countryProperty == null) {
            throw new BusinessRuleException(CompanyRegistrationBusinessRule.INVALID_COUNTRY);
        }
        return countryProperty.getValue();
    }

    @Override
    @Transactional
    public void editUser(UserInfo editPartnerUserVO, VAPUserDetails loggedInUser) throws BusinessRuleException {
        User user = userDao.get(editPartnerUserVO.getId());

        if (user == null) {
            throw new BusinessRuleException(UserValidationBusinessRule.COMPANY_USER_NOT_EXISTS);
        }

        ETDConverter.convertPartnerUser(editPartnerUserVO, user);

        user.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());
        userDao.update(user);
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId, VAPUserDetails loggedInUser) throws BusinessRuleException {
        User user = userDao.getPartnerUserById(loggedInUser.getCompanyId(), userId);
        if (user == null) {
            throw new BusinessRuleException(UserValidationBusinessRule.COMPANY_USER_NOT_EXISTS);
        }
        // Soft delete the partner user by setting isActive status to false
        user.setActive(false);
        user.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());
        userDao.update(user);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyVO getCompanyById(Integer id) {
        CompanyVO vo = new CompanyVO();
        Company company = companyDao.get(id);
        vo.setId(company.getId());
        vo.setName(company.getName());
        vo.setWorkFlowSteps(VAPUtils.formatWorkflowSteps(company.getWorkFlowSteps()));
        vo.setSuspended(company.isSuspended());
        vo.setCertNdaAcceptDate(company.getCertNdaAcceptDate());
        vo.setOfflineCertNdaId(company.getOfflineCertNdaId());
        
        if(vo.getOfflineCertNdaId() != null && vo.getOfflineCertNdaId() > 0){
            VapMedia media = mediaDao.get(vo.getOfflineCertNdaId());
            vo.setOfflineCertNdaName(media.getFileName());
        }
        
        return vo;
    }

    private boolean isUserPartOfCompany(Integer companyId, Integer userId) {
        User user = userDao.getPartnerUserById(companyId, userId);
        return (user != null);
    }

    private boolean isUserAlreadyMPOC(Integer companyId, Integer userId) {
        List<User> users = userDao.getUsersByCompanyId(companyId);
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                if (user.getId().equals(userId)) {
                    for (UserRole userRole : user.getUserRoles()) {
                        if (userRole.getSystemRole().getId() == VAPConstants.ROLE_MPOC_ID) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void removePreviousMPOC(Integer companyId) {
        List<User> users = userDao.getUsersByCompanyId(companyId);
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                Set<UserRole> userRoles = user.getUserRoles();
                UserRole removedRole = null;
                for (UserRole userRole : userRoles) {
                    if (userRole.getSystemRole().getId() == VAPConstants.ROLE_MPOC_ID) {
                        removedRole = userRole;
                        userRoleDao.remove(userRole.getId());
                    }
                }
                if (removedRole != null && !userRoles.isEmpty()) {
                    userRoles.remove(removedRole);
                    user.setUserRoles(userRoles);
                    userDao.update(user);
                    return;
                }
            }
        }
    }

    private void addNewMPOC(
            Integer userId, Integer roleId, String username) {

        SystemRole sysRole = systemRoleDao.get(roleId);
        UserRole userRole = new UserRole();
        User user = userDao.getUserById(userId);

        userRole.setSystemRole(sysRole);
        userRole.setUser(user);
        userRole.populatedAuditFields(username);
        userRoleDao.add(userRole);

        user.getUserRoles().add(userRole);
        user.populatedAuditFieldsOnUpdate(username);
        userDao.update(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<PartnerUserVO> getAllPartnerUsers(
            VAPUserDetails loggedInUser, PageModel<PartnerUserVO> pageModel) {

        List<PartnerUserVO> partnerUserVOs = new ArrayList<PartnerUserVO>();

        PartnerUserVO partnerUserVO;

        PaggedResult paggedResult = companyDao.getAllPartnerUsersList(
                pageModel, loggedInUser.getCompanyId());

        pageModel.setTotalRecords(paggedResult.getRecords());

        for (Object partnerUserObj : paggedResult.getList()) {
            Object[] partnerUser = (Object[]) partnerUserObj;

            partnerUserVO = ETDConverter.convert(partnerUser);

            partnerUserVOs.add(partnerUserVO);
        }
        pageModel.setRecords(partnerUserVOs);
        return pageModel;
    }

    @Override
    @Transactional
    public void deleteCompany(VAPUserDetails loggedInUser, Integer companyId)
            throws BusinessRuleException {
        User user;
        Map<String, String> params;
        List<String> partnerEmails = null;
        List<String> roleUserEmails;
        String emailAddressMPOC = null;

        user = userDao.getMPOC(companyId);

        if (user == null) {
            throw new BusinessRuleException(
                    CompanyValidationBusinessRule.COMPANY_DOES_NOT_EXIST);
        }
        
        partnerEmails = userDao.getUserEmailsByCompanyId(companyId);
        roleUserEmails = userDao.getRoleUserEmailsByCompanyId(companyId, VAPConstants.ROLE_MPOC_ID);
        if(roleUserEmails != null && roleUserEmails.size() > 0)
            emailAddressMPOC = roleUserEmails.get(0);
        
        companyDao.deleteCompany(companyId, loggedInUser.getUsername());
        
        // Raise Partner Deleted event
        params = new HashMap<String, String>();
        params.put(VAPConstants.PLACEHOLDER_COMPANY_TITLE, user.getCompany().getName());
        params.put(VAPConstants.PLACEHOLDER_COMPANY_DOMAIN, user.getCompany().getCompanyDomain());
        params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.PARTNER_DELETED_EVENT.toString(),
                params, null, partnerEmails, emailAddressMPOC, loggedInUser.getUsername());

    }

    @Override
    @Transactional
    public void suspendOrUnsuspendCompany(
            VAPUserDetails loggedInUser,
            Integer companyId, boolean suspendOrUnsuspend)
            throws BusinessRuleException {

        Company company = companyDao.getCompanyById(companyId);

        if (company == null) {
            throw new BusinessRuleException(
                    CompanyValidationBusinessRule.COMPANY_DOES_NOT_EXIST);
        }
        company.setSuspended(suspendOrUnsuspend);

        company.populatedAuditFieldsOnUpdate(loggedInUser.getUsername());

        companyDao.update(company);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCompanyApproved(Integer companyId) {
        List<Workitem> workItems = workitemDao.getPartnerWorkItemsByTitle(companyId, WorkflowStep.Approved);
        if (workItems != null && workItems.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer findCompanyIdOfPendingUser(Integer userId) {
        return joinCompanyRequestDao.getCompanyIdByOfferedUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isCompanyNameDuplicate(String companyName) {

        Company duplicateCompany = companyDao.getCompanyByName(companyName);

        return (duplicateCompany != null);
    }
}