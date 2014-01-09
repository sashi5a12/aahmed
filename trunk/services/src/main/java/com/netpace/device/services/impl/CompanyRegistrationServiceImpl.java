package com.netpace.device.services.impl;

import com.netpace.device.annotation.utils.ETDConverter;
import com.netpace.device.dao.CompanyDao;
import com.netpace.device.dao.ContactDao;
import com.netpace.device.dao.JoinCompanyRequestDao;
import com.netpace.device.dao.UserDao;
import com.netpace.device.entities.Company;
import com.netpace.device.entities.JoinCompanyRequest;
import com.netpace.device.entities.User;
import com.netpace.device.entities.UserRole;
import com.netpace.device.entities.VapContact;
import com.netpace.device.entities.enums.JoinCompanyRequestType;
import com.netpace.device.entities.enums.JoinCompanyStatus;
import com.netpace.device.enums.CompanyValidationBusinessRule;
import com.netpace.device.enums.EventType;
import com.netpace.device.exceptions.BusinessRuleException;
import com.netpace.device.services.ApprovalService;
import com.netpace.device.services.CompanyRegistrationService;
import com.netpace.device.services.CompanyService;
import com.netpace.device.services.UserService;
import com.netpace.device.services.UtilitiesService;
import com.netpace.device.utils.DateUtils;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.enums.ContactType;
import com.netpace.device.utils.enums.WorkflowType;
import com.netpace.device.vo.CompanyRegistration;
import com.netpace.device.vo.CompanyVO;
import com.netpace.device.vo.JoinCompanyRequestVO;
import com.netpace.device.vo.VAPUserDetails;
import com.netpace.notification.services.EventService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "companyRegistrationService")
public class CompanyRegistrationServiceImpl implements CompanyRegistrationService {

    private final static Log log = LogFactory.getLog(CompanyRegistrationServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private JoinCompanyRequestDao joinCompanyRequestDao;
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private UserService userService;
    @Autowired
    private UtilitiesService utilitiesService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EventService eventService;

    /**
     * get active company by domain name
     *
     * @param domainName
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public CompanyRegistration getCompanyByDomainName(String domainName) {
        Company company;
        CompanyRegistration companyVO = null;

        try {
            company = companyDao.getCompanyByDomainName(domainName);
            companyVO = ETDConverter.convert(company);
        } catch (EmptyResultDataAccessException e) {
            log.info("Company with domain name does not exists: domainName[" + domainName + "]");
        }

        return companyVO;
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyRegistration getUserCompany(Integer userId) {
        Company company;
        CompanyRegistration companyVO = null;

        company = companyDao.getCompanyByUserId(userId);
        if (company != null) {
            companyVO = ETDConverter.convert(company);
        }

        return companyVO;
    }

    @Override
    @Transactional(readOnly = true)
    public JoinCompanyRequestVO getPendingRequestToJoin(Integer userId) {
        JoinCompanyRequest joinCompanyRequest;
        JoinCompanyRequestVO joinCompanyRequestVO = null;

        try {
            joinCompanyRequest = joinCompanyRequestDao.getRequestByOfferedUserId(userId);

            if (joinCompanyRequest != null && joinCompanyRequest.getStatus() == JoinCompanyStatus.PENDING) {
                joinCompanyRequestVO = ETDConverter.convert(joinCompanyRequest, new JoinCompanyRequestVO());
            }
        } catch (EmptyResultDataAccessException e) {
            log.info("User join request does not exists: userId[" + userId + "]");
        }

        return joinCompanyRequestVO;
    }

    /**
     * register company if does not exists with same domain name
     *
     * @param loggedUser
     * @param companyRegistration
     * @throws BusinessRuleException
     */
    @Transactional
    @Override
    public void registerCompany(VAPUserDetails loggedInUser, CompanyRegistration companyRegistration) throws BusinessRuleException {
        Company company;
        User user;
        UserRole userRole;
        Map<String, String> params;
        List<String> partnerEmails;
        List<String> roleUserEmails;
        String emailAddressMPOC = null;

        log.debug("check company domain in block list");
        if (utilitiesService.isDomainBlocked(companyRegistration.getDomainName())) {
            log.debug("Company domain: '"
                    + companyRegistration.getDomainName() + "' is blocked");

            throw new BusinessRuleException(
                    CompanyValidationBusinessRule.COMPANY_DOMAIN_IS_BLOCKED);
        }

        if (companyService.isCompanyNameDuplicate(
                companyRegistration.getCompanyName())) {

            log.debug("Company name: '"
                    + companyRegistration.getCompanyName() + "' is duplicate");
            throw new BusinessRuleException(
                    CompanyValidationBusinessRule.COMPANY_NAME_IS_DUPLICATE);
        }

        log.debug("Registering Company: companyDomain[" + loggedInUser.getCompanyDomain() + "]");

        // Check if company already exists
        if (getCompanyByDomainName(loggedInUser.getCompanyDomain()) != null) {
            throw new BusinessRuleException(CompanyValidationBusinessRule.DOMAIN_COMPANY_ALREADY_EXISTS);
        }

        companyRegistration.setDomainName(loggedInUser.getCompanyDomain());
        companyRegistration.setUser(userService.getUserById(loggedInUser.getId()));

        // Add company
        company = ETDConverter.convert(companyRegistration, new Company());

        VapContact salesContact = company.getSalesContact();
        salesContact.setContactType(ContactType.CompanySalesContact.name());
        salesContact.populatedAuditFields(loggedInUser.getUsername());
        contactDao.add(salesContact);

        company.setCountry(companyRegistration.getCountry());

        user = userDao.get(loggedInUser.getId());
        company.populatedAuditFields(loggedInUser.getUsername());
        companyDao.add(company);

        // Add user role ROLE_MPOC
        userRole = ETDConverter.convert(loggedInUser.getId(), VAPConstants.ROLE_MPOC_ID);
        userRole.populatedAuditFields(loggedInUser.getUsername());
        user.getUserRoles().add(userRole);
        user.setCompany(company);

        // Start company Approval workflow
        approvalService.startApprovalProcess(WorkflowType.PartnerWorkflow, company.getId(), loggedInUser);
    
        partnerEmails = userDao.getUserEmailsByCompanyId(company.getId());
        roleUserEmails = userDao.getRoleUserEmailsByCompanyId(company.getId(), VAPConstants.ROLE_MPOC_ID);
        if(roleUserEmails != null && roleUserEmails.size() > 0)
            emailAddressMPOC = roleUserEmails.get(0);
        
        // Raise PARTNER_REGISTERED Event
        params = new HashMap<String, String>();
        params.put(VAPConstants.PLACEHOLDER_COMPANY_TITLE, company.getName());
        params.put(VAPConstants.PLACEHOLDER_COMPANY_DOMAIN, company.getCompanyDomain());
        params.put(VAPConstants.PLACEHOLDER_FULL_NAME, user.getFullName());
        params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.PARTNER_REGISTERED.toString(), params, null, partnerEmails, emailAddressMPOC, loggedInUser.getUsername());
    }

    /**
     * service user request to join existing company
     *
     * @param token
     */
    @Override
    @Transactional
    public CompanyVO requestToJoinCompany(VAPUserDetails loggedInUser) {
        Company company;
        Map<String, String> params;
        List<String> partnerEmails = null;
        List<String> roleUserEmails;
        String emailAddressMPOC = null;
        
        User requestedUser;
        JoinCompanyRequest joinCompanyRequest;

        log.debug("Joining Company: companyDomain[" + loggedInUser.getCompanyDomain() + "], userId[" + loggedInUser.getId() + "]");

        joinCompanyRequest = new JoinCompanyRequest();
        joinCompanyRequest.setOfferDate(DateUtils.currentTimeStamp());

        requestedUser = userDao.get(loggedInUser.getId());
        joinCompanyRequest.setOfferTo(requestedUser);

        company = companyDao.getRegisteredCompanyByDomainName(loggedInUser.getCompanyDomain());
        
        joinCompanyRequest.setCompany(company);

        joinCompanyRequest.setStatus(JoinCompanyStatus.PENDING);
        joinCompanyRequest.setType(JoinCompanyRequestType.REQUEST_TO_JOIN);
        joinCompanyRequest.populatedAuditFields(loggedInUser.getUsername());

        joinCompanyRequestDao.add(joinCompanyRequest);
        
        partnerEmails = userDao.getUserEmailsByCompanyId(company.getId());
        roleUserEmails = userDao.getRoleUserEmailsByCompanyId(company.getId(), VAPConstants.ROLE_MPOC_ID);
        if(roleUserEmails != null && roleUserEmails.size() > 0)
            emailAddressMPOC = roleUserEmails.get(0);
            
        // Raise Request To Join Company Event
        params = new HashMap<String, String>();
        params.put(VAPConstants.PLACEHOLDER_COMPANY_TITLE, company.getName());
        params.put(VAPConstants.PLACEHOLDER_COMPANY_DOMAIN, company.getCompanyDomain());
        params.put(VAPConstants.PLACEHOLDER_REQUESTING_USER_FULLNAME, requestedUser.getFullName());
        params.put(VAPConstants.PLACEHOLDER_DATE, DateFormatUtils.format(new Date(), "MM/dd/yyyy"));
        eventService.raiseEvent(EventType.PARTNER_USER_REQUESTS_TO_JOIN.toString(), params, null, partnerEmails, emailAddressMPOC, loggedInUser.getUsername());
        
        return ETDConverter.convert(company, new CompanyVO());
    }

    /**
     * service user reject to join company request
     *
     * @param loggedUser
     */
    @Override
    @Transactional
    public void rejectToJoinCompany(VAPUserDetails loggedInUser) {
        log.debug("Reject to join company: companyDomain[" + loggedInUser.getCompanyDomain() + "], userId[" + loggedInUser.getId() + "]");

        userDao.remove(loggedInUser.getId());
    }
}
