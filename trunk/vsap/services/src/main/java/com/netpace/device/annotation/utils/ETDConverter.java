package com.netpace.device.annotation.utils;

import com.netpace.device.entities.ApplicationProperties;
import com.netpace.device.entities.Company;
import com.netpace.device.entities.JoinCompanyRequest;
import com.netpace.device.entities.SystemRole;
import com.netpace.device.entities.User;
import com.netpace.device.entities.UserActivation;
import com.netpace.device.entities.UserRole;
import com.netpace.device.entities.VapComment;
import com.netpace.device.entities.VapContact;
import com.netpace.device.entities.VapProduct;
import com.netpace.device.entities.Workflow;
import com.netpace.device.entities.Workitem;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.utils.enums.WorkitemStatus;
import com.netpace.device.vo.*;
import java.sql.Timestamp;
import java.util.*;
import org.apache.commons.lang.StringUtils;

public class ETDConverter {

    public static CompanyRegistration convert(Company company) {
        CompanyRegistration companyRegistration = new CompanyRegistration();
        companyRegistration.setId(company.getId());
        companyRegistration.setMainCompanyStreetAddress(company.getAddress());
        companyRegistration.setCity(company.getCity());
        companyRegistration.setDomainName(company.getCompanyDomain());
        companyRegistration.setCountry(company.getCountry());
        companyRegistration.setCompanyLegalName(company.getLegalName());
        companyRegistration.setMainPhoneNumber(company.getMobile());
        companyRegistration.setCompanyName(company.getName());
        companyRegistration.setZip(company.getPostalCode());
        companyRegistration.setState(company.getState());
        companyRegistration.setWebsite(company.getWebsite());
        companyRegistration.setSalesContact(convert(company.getSalesContact(), new ContactVO()));
        companyRegistration.setSuspended(company.isSuspended());

        return companyRegistration;
    }

    /**
     * converts a company value object to entity
     *
     * @param companyRegistration
     * @param company
     * @return
     */
    public static Company convert(CompanyRegistration companyRegistration, Company company) {
        company.setAddress(companyRegistration.getMainCompanyStreetAddress());
        company.setCity(companyRegistration.getCity());

//        company.setCompanyDomain(companyRegistration.getDomainName());
        company.setCompanyDomain(
                companyRegistration.getUser().getCompanyDomain());

        company.setLegalName(companyRegistration.getCompanyLegalName());
        company.setMobile(companyRegistration.getMainPhoneNumber());
        company.setName(companyRegistration.getCompanyName());
        company.setPostalCode(companyRegistration.getZip());
        company.setState(companyRegistration.getState());
        company.setWebsite(companyRegistration.getWebsite());
        if (company.getSalesContact() == null) {
            company.setSalesContact(new VapContact());
        }
        convert(companyRegistration.getSalesContact(), company.getSalesContact());
        return company;
    }

    /**
     * converts a company value object to entity
     *
     * @param companyRegistration
     * @param company
     * @return
     */
    public static Company convertForUpdate(
            CompanyRegistration companyRegistration, Company company) {
        company.setAddress(companyRegistration.getMainCompanyStreetAddress());
        company.setCity(companyRegistration.getCity());
        company.setLegalName(companyRegistration.getCompanyLegalName());
        company.setMobile(companyRegistration.getMainPhoneNumber());
        company.setName(companyRegistration.getCompanyName());
        company.setPostalCode(companyRegistration.getZip());
        company.setState(companyRegistration.getState());
        company.setCountry(companyRegistration.getCountry());
        company.setWebsite(companyRegistration.getWebsite());
        convert(companyRegistration.getSalesContact(), company.getSalesContact());
        return company;
    }

    public static UserInfo convert(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        //Company object would be null for a user which has 
        //no registered company in the system making this 
        //check mandatory
        if (user.getCompany() != null) {
            userInfo.setCompanyId(user.getCompany().getId());
        }
        userInfo.setCompanyDomain(user.getCompanyDomain());
        userInfo.setEmailAddress(user.getEmailAddress());
        userInfo.setFullName(user.getFullName());
        userInfo.setMobilePhoneNumber(user.getMobile());
        userInfo.setPhoneNumber(user.getPhone());
        userInfo.setUserName(user.getUserName());
        userInfo.setAddress(user.getAddress());
        userInfo.setCity(user.getCity());
        userInfo.setState(user.getState());
        userInfo.setIsActive(user.isActive());
        userInfo.setCountry(user.getCountry());
        userInfo.setZip(user.getPostalCode());
        userInfo.setIsEnable(user.isEnable());
        return userInfo;
    }

    public static KeyValuePair convert(ApplicationProperties property) {
        return new KeyValuePair(property.getPropertyId(), property.getName(), property.getValue());
    }

    public static CompanyProfile convert(CompanyProfile companyProfile, Workitem workitem) {

        companyProfile.setWorkflowId(new Long(workitem.getId()));
        //companyProfile.setWorkflowPhaseName(workitem.getPhaseId());
        //companyProfile.setWorkflowPhaseId(workitem.getPhaseId());
        companyProfile.setWorkflowType("Product Workflow");
        companyProfile.setStartDate(workitem.getStartDate());

        return companyProfile;
    }

    /**
     * converts an InviteUserVO to UserActivation entity
     *
     * @param inviteUserVO
     * @param userActivation
     * @return
     */
    public static UserActivation convert(InviteUserVO inviteUserVO, UserActivation userActivation) {

        userActivation.setFullName(inviteUserVO.getFullName());
        userActivation.setEmailAddress(inviteUserVO.getEmailAddress());

        if (inviteUserVO.getUserRoles() != null) {
            String strUserRoles = StringUtils.join(inviteUserVO.getUserRoles(), ",");
            userActivation.setUserRoles(strUserRoles);
        }

        return userActivation;
    }

    /**
     * converts an InvitePartnerUserVO to UserActivation entity
     *
     * @param invitePartnerUserVO
     * @param userActivation
     * @return
     */
    public static UserActivation convert(InvitePartnerUserVO invitePartnerUserVO, UserActivation userActivation) {

        userActivation.setFullName(invitePartnerUserVO.getFullName());
        userActivation.setEmailAddress(invitePartnerUserVO.getEmailAddress());

        return userActivation;
    }

    /**
     * converts UserActivation entity to InviteUserVO
     *
     * @param userActivation
     * @param inviteUserVO
     * @return
     */
    public static InviteUserVO convert(UserActivation userActivation, InviteUserVO inviteUserVO) {

        inviteUserVO.setFullName(userActivation.getFullName());
        inviteUserVO.setEmailAddress(userActivation.getEmailAddress());
        inviteUserVO.setCompanyDomain(userActivation.getCompanyDomain());

        if (userActivation.getUserRoles() != null) {
            List<String> userRoles = Arrays.asList(StringUtils.split(userActivation.getUserRoles(), ","));
            inviteUserVO.setUserRoles(userRoles);
        }

        return inviteUserVO;
    }

    /**
     * converts UserActivation entity to UserInfo VO
     *
     * @param userActivation
     * @param userInfo
     * @return
     */
    public static UserInfo convert(UserActivation userActivation, UserInfo userInfo) {

        userInfo.setCompanyDomain(userActivation.getCompanyDomain());
        userInfo.setEmailAddress(userActivation.getEmailAddress());
        userInfo.setFullName(userActivation.getFullName());
        userInfo.setMobilePhoneNumber(userActivation.getMobile());
        userInfo.setPhoneNumber(userActivation.getPhone());
        userInfo.setUserName(userActivation.getUserName());
        userInfo.setAddress(userActivation.getAddress());
        userInfo.setCity(userActivation.getCity());
        userInfo.setState(userActivation.getState());
        userInfo.setCountry(userActivation.getCountry());
        userInfo.setZip(userActivation.getPostalCode());

        return userInfo;
    }

    /**
     * converts a userInfo to a User entity
     *
     * @param user
     * @param userInfo
     * @return
     */
    public static User convert(UserInfo userInfo, User user) {

        user.setId(userInfo.getId());
        user.setCompanyDomain(userInfo.getCompanyDomain());
        user.setEmailAddress(userInfo.getEmailAddress());
        user.setFullName(userInfo.getFullName());
        user.setMobile(userInfo.getMobilePhoneNumber());
        user.setPhone(userInfo.getPhoneNumber());
        user.setUserName(userInfo.getUserName());
        user.setAddress(userInfo.getAddress());
        user.setCity(userInfo.getCity());
        user.setState(userInfo.getState());
        user.setCountry(userInfo.getCountry());
        user.setPostalCode(userInfo.getZip());

        return user;
    }

    /**
     * converts a userInfo to a User entity
     *
     * @param user
     * @param userInfo
     * @return
     */
    public static User convertAdminUserForEdit(UserInfo userInfo, User user) {

        user.setFullName(userInfo.getFullName());
        user.setMobile(userInfo.getMobilePhoneNumber());
        user.setPhone(userInfo.getPhoneNumber());
        user.setAddress(userInfo.getAddress());
        user.setCity(userInfo.getCity());
        user.setState(userInfo.getState());
        user.setCountry(userInfo.getCountry());
        user.setPostalCode(userInfo.getZip());
        user.setEnable(userInfo.isIsEnable());

        return user;
    }

    /**
     * converts a userInfo to a User entity
     *
     * @param user
     * @param userInfo
     * @return
     */
    public static User convertForUpdate(UserInfo userInfo, User user) {

        user.setCompanyDomain(userInfo.getCompanyDomain());
        user.setEmailAddress(userInfo.getEmailAddress());
        user.setFullName(userInfo.getFullName());
        user.setMobile(userInfo.getMobilePhoneNumber());
        user.setPhone(userInfo.getPhoneNumber());
        user.setAddress(userInfo.getAddress());
        user.setCity(userInfo.getCity());
        user.setState(userInfo.getState());
        user.setCountry(userInfo.getCountry());
        user.setPostalCode(userInfo.getZip());

        return user;
    }

    /**
     * converts a partner's userInfo to a User entity
     *
     * @param user
     * @param userInfo
     * @return
     */
    public static User convertPartnerUser(UserInfo userInfo, User user) {

        user.setFullName(userInfo.getFullName());
        user.setMobile(userInfo.getMobilePhoneNumber());
        user.setPhone(userInfo.getPhoneNumber());
        user.setAddress(userInfo.getAddress());
        user.setCity(userInfo.getCity());
        user.setState(userInfo.getState());
        user.setCountry(userInfo.getCountry());
        user.setPostalCode(userInfo.getZip());
        user.setEnable(userInfo.isIsEnable());

        return user;
    }

    /**
     * converts ActivateAdminVO to User entity
     *
     * @param activateAdminVO
     * @param user
     * @return
     */
    public static User convert(ActivateAdminVO activateAdminVO, User user) {

        user.setId(activateAdminVO.getId());
        user.setEmailAddress(activateAdminVO.getEmailAddress());
        user.setFullName(activateAdminVO.getFullName());
        user.setMobile(activateAdminVO.getMobilePhoneNumber());
        user.setPhone(activateAdminVO.getPhoneNumber());
        user.setUserName(activateAdminVO.getUserName());
        user.setAddress(activateAdminVO.getAddress());
        user.setCity(activateAdminVO.getCity());
        user.setState(activateAdminVO.getState());
        user.setCountry(activateAdminVO.getCountry());
        user.setPostalCode(activateAdminVO.getZip());

        return user;
    }

    /**
     * converts a workitem value object to workitem entity
     *
     * @param workItemVO
     * @param workitem
     * @return
     */
    public static Workitem convert(WorkItem workItemVO, Workitem workitem) {

        workitem.setTitle(workItemVO.getTitle());
        workitem.setDisplayTitle(workItemVO.getDisplayName());
        workitem.setStatus(WorkitemStatus.valueOf(workItemVO.getStatus()));
        if (workItemVO.getAllowedRoles() != null) {
            String strAllowedRoles = StringUtils.join(workItemVO.getAllowedRoles(), ",");
            workitem.setAllowedRoles(strAllowedRoles);
        }
        workitem.setRequireInput(workItemVO.isRequireInput());
        if (workItemVO.getNextActions() != null) {
            String strNextActions = StringUtils.join(workItemVO.getNextActions(), ",");
            workitem.setNextActions(strNextActions);
        }
        if (workItemVO.getStartDate() != null) {
            workitem.setStartDate(new Timestamp(workItemVO.getStartDate().getTime()));
        }
        if (workItemVO.getEndDate() != null) {
            workitem.setEndDate(new Timestamp(workItemVO.getEndDate().getTime()));
        }
        Workflow workflow = new Workflow();
        workflow.setId(workItemVO.getWorkflow().getId());
        workitem.setWorkflow(workflow);

        return workitem;
    }

    /**
     * converts a workitem entity to workitem value object
     *
     * @param workitem
     * @param workItemVO
     * @return
     */
    public static WorkItem convert(Workitem workitem, WorkItem workItemVO) {

        workItemVO.setId(workitem.getId());
        workItemVO.setTitle(workitem.getTitle());
        workItemVO.setDisplayName(workitem.getDisplayTitle());
        workItemVO.setStatus(workitem.getStatus().toString());
        if (StringUtils.isNotEmpty(workitem.getAllowedRoles())) {
            workItemVO.setAllowedRoles(Arrays.asList(StringUtils.split(workitem.getAllowedRoles(), ",")));
        }
        workItemVO.setRequireInput(workitem.isRequireInput());
        if (StringUtils.isNotEmpty(workitem.getNextActions())) {
            workItemVO.setNextActions(new ArrayList<String>(Arrays.asList(StringUtils.split(workitem.getNextActions(), ","))));
        }
        workItemVO.setStartDate(workitem.getStartDate());
        workItemVO.setEndDate(workitem.getEndDate());

        return workItemVO;
    }

    /**
     * converts a workflow entity to a value object
     *
     * @param workflow
     * @param workflowVO
     * @return
     */
    public static WorkflowVO convert(Workflow workflow, WorkflowVO workflowVO) {

        workflowVO.setId(workflow.getId());
        if (workflow.getCompany() != null) {
            workflowVO.setCompanyId(workflow.getCompany().getId());
            workflowVO.setCompanyName(workflow.getCompany().getName());
            workflowVO.setCompanyDomain(workflow.getCompany().getCompanyDomain());
        }
        if (workflow.getProduct() != null) {
            workflowVO.setProductId(workflow.getProduct().getProductId());
            workflowVO.setProductName(workflow.getProduct().getProductName());
        }
        workflowVO.setWorkflowType(workflow.getWorkflowType());
        workflowVO.setStartDate(workflow.getStartDate());

        return workflowVO;
    }

    /**
     * converts a workflow value object to entity
     *
     * @param workflowVO
     * @param workflow
     * @return
     */
    public static Workflow convert(WorkflowVO workflowVO, Workflow workflow) {

        if (workflowVO.getCompanyId() != null) {
            Company company = new Company();
            company.setId(workflowVO.getCompanyId());
            workflow.setCompany(company);
        }/*
         if(workflow.getProduct() != null){
         VapProduct product = new VapProduct();
         product.setProductId(workflowVO.getProductId());
         workflow.setProduct(product);
         }*/
        workflow.setWorkflowType(workflowVO.getWorkflowType());
        if (workflowVO.getStartDate() != null) {
            workflow.setStartDate(new Timestamp(workflowVO.getStartDate().getTime()));
        }

        return workflow;
    }

    public static JoinCompanyRequestVO convert(
            JoinCompanyRequest joinCompanyRequest, JoinCompanyRequestVO joinCompanyRequestVO) {

        joinCompanyRequestVO.setId(joinCompanyRequest.getId());
        joinCompanyRequestVO.setFullName(joinCompanyRequest.getOfferTo().getFullName());
        joinCompanyRequestVO.setEmailAddress(joinCompanyRequest.getOfferTo().getEmailAddress());
        joinCompanyRequestVO.setStatus(joinCompanyRequest.getStatus().toString());
        joinCompanyRequestVO.setCreatedDate(joinCompanyRequest.getCreatedDate());
        joinCompanyRequestVO.setOfferToId(joinCompanyRequest.getOfferTo().getId());
        joinCompanyRequestVO.setOfferToActive(joinCompanyRequest.getOfferTo().isActive());

        return joinCompanyRequestVO;
    }

    public static List<JoinCompanyRequestVO> convert(List<JoinCompanyRequest> joinCompanyRequests) {
        List<JoinCompanyRequestVO> joinCompanyRequestVOs = new ArrayList<JoinCompanyRequestVO>();

        for (int i = 0; i < joinCompanyRequests.size(); i++) {

            JoinCompanyRequest joinCompanyRequest = joinCompanyRequests.get(i);

            JoinCompanyRequestVO joinCompanyRequestVO = new JoinCompanyRequestVO();

            joinCompanyRequestVO.setId(joinCompanyRequest.getId());
            joinCompanyRequestVO.setFullName(joinCompanyRequest.getOfferTo().getFullName());
            joinCompanyRequestVO.setEmailAddress(joinCompanyRequest.getOfferTo().getEmailAddress());
            joinCompanyRequestVO.setStatus(joinCompanyRequest.getStatus().toString());
            joinCompanyRequestVO.setCreatedDate(joinCompanyRequest.getCreatedDate());

            joinCompanyRequestVOs.add(joinCompanyRequestVO);
        }
        return joinCompanyRequestVOs;
    }

    /**
     * converts User entity to UserInfo VO
     *
     * @param user
     * @param userInfo
     * @return
     */
    public static UserInfo convert(User user, UserInfo userInfo) {

        userInfo.setId(user.getId());
        userInfo.setCompanyDomain(user.getCompanyDomain());
        userInfo.setEmailAddress(user.getEmailAddress());
        userInfo.setFullName(user.getFullName());
        userInfo.setMobilePhoneNumber(user.getMobile());
        userInfo.setPhoneNumber(user.getPhone());
        userInfo.setUserName(user.getUserName());
        userInfo.setAddress(user.getAddress());
        userInfo.setCity(user.getCity());
        userInfo.setState(user.getState());
        userInfo.setIsActive(user.isActive());
        userInfo.setCreatedDate(user.getCreatedDate());
        Set<UserRole> userRoles = user.getUserRoles();
        userInfo.setRoles(convert(userRoles));
        userInfo.setCountry(user.getCountry());
        userInfo.setZip(user.getPostalCode());
        userInfo.setIsEnable(user.isEnable());

        return userInfo;
    }

    public static List<SystemRoleVO> convert(Set<UserRole> userRoles) {
        List<SystemRoleVO> roleVOs = new ArrayList(userRoles.size());
        SystemRoleVO roleVO = null;
        SystemRole role = null;
        for (UserRole userRole : userRoles) {
            roleVO = new SystemRoleVO();
            role = userRole.getSystemRole();
            roleVO.setRoleId(role.getId());
            roleVO.setRoleName(role.getTitle());
            roleVO.setDisplayTitle(role.getDisplayTitle());
            roleVO.setHidden(role.isHidden());
            roleVOs.add(roleVO);
        }
        return roleVOs;
    }

    public static List<SystemRoleVO> convertUserRoles(List<UserRole> userRoles) {
        List<SystemRoleVO> roleVOs = new ArrayList(userRoles.size());

        for (UserRole userRole : userRoles) {
            SystemRoleVO roleVO = new SystemRoleVO();
            SystemRole role = userRole.getSystemRole();
            roleVO.setRoleId(role.getId());
            roleVO.setRoleName(role.getTitle());
            roleVO.setDisplayTitle(role.getDisplayTitle());
            roleVO.setHidden(role.isHidden());

            roleVOs.add(roleVO);
        }
        return roleVOs;
    }

    /**
     * converts to a UserRole entity
     *
     * @param companyRegistration
     * @param company
     * @return
     */
    public static UserRole convert(Integer userId, Integer sysRoleId) {
        UserRole userRole = new UserRole();

        User user = new User();
        user.setId(userId);
        userRole.setUser(user);

        SystemRole systemRole = new SystemRole();
        systemRole.setId(sysRoleId);
        userRole.setSystemRole(systemRole);

        return userRole;
    }

    /**
     * converts a company entity to value object
     *
     * @param company
     * @param companyRegistration
     * @return
     */
    public static CompanyRegistration convert(Company company, CompanyRegistration companyRegistration) {

        companyRegistration.setCompanyName(company.getName());
        companyRegistration.setCompanyLegalName(company.getLegalName());
        companyRegistration.setMainPhoneNumber(company.getMobile());
        companyRegistration.setMainCompanyStreetAddress(company.getAddress());
        companyRegistration.setCity(company.getCity());
        companyRegistration.setState(company.getState());
        companyRegistration.setCountry(company.getCountry());
        companyRegistration.setZip(company.getPostalCode());
        companyRegistration.setWebsite(company.getWebsite());
        companyRegistration.setDomainName(company.getCompanyDomain());
        companyRegistration.setSuspended(company.isSuspended());

        return companyRegistration;
    }

    public static void convert(List<User> users, CompanyProfileWithActiveUser companyUser) {
        companyUser.setCompany(convert(users.get(0).getCompany()));
        List<UserInfo> usersInfo = new ArrayList<UserInfo>();
        UserInfo userInfo = null;
        for (Iterator<User> it = users.iterator(); it.hasNext();) {
            userInfo = new UserInfo();
            User user = it.next();
            usersInfo.add(convert(user, userInfo));
        }
        companyUser.setUserInfoList(usersInfo);
//        return null;
    }

    public static ContactVO convert(VapContact vapContact, ContactVO contactVO) {
        //I am not sure if this check is correct or not
        //Will discuss sales contact requirement with and then decide about it
        if (vapContact == null) {
            return null;
        }
        contactVO.setContactId(vapContact.getContactId());
        contactVO.setContactType(vapContact.getContactType());
        contactVO.setName(vapContact.getName());
        contactVO.setStreetAddress(vapContact.getStreetAddress());
        contactVO.setEmailAddress(vapContact.getEmailAddress());
        contactVO.setCity(vapContact.getCity());
        contactVO.setPhone(vapContact.getPhone());
        contactVO.setPostalCode(vapContact.getPostalCode());
        contactVO.setMobile(vapContact.getMobile());
        contactVO.setCountry(vapContact.getCountry());

        return contactVO;
    }

    public static CommentVO convert(VapComment comment, CommentVO commentVO) {
        commentVO.setCommentId(comment.getCommentsId());
        commentVO.setCommentType(comment.getCommentsType());
        commentVO.setCommentText(comment.getCommentsText());

        if (comment.getCompany() != null) {
            commentVO.setCompanyId(comment.getCompany().getId());
        }

        if (comment.getProduct() != null) {
            commentVO.setProductId(comment.getProduct().getProductId());
        }

        commentVO.setCreatedDate(comment.getCreatedDate());
        commentVO.setCreatedBy(comment.getCreatedBy());

        return commentVO;
    }

    public static VapComment convert(CommentVO commentVO, VapComment comment) {
        comment.setCommentsId(commentVO.getCommentId());
        comment.setCommentsType(commentVO.getCommentType());
        comment.setCommentsText(commentVO.getCommentText());

        if (commentVO.getCompanyId() != null) {
            Company company = new Company();
            company.setId(comment.getCompany().getId());
            comment.setCompany(company);
        }

        if (commentVO.getProductId() != null) {
            VapProduct product = new VapProduct();
            product.setProductId(commentVO.getProductId());
            comment.setProduct(product);
        }

        return comment;
    }

    public static VapContact convert(ContactVO contactVO, VapContact vapContact) {
//        vapContact.setContactType(contactVO.getContactType());
        vapContact.setName(contactVO.getName());
//        vapContact.setStreetAddress(contactVO.getStreetAddress());
        vapContact.setEmailAddress(contactVO.getEmailAddress());
//        vapContact.setCity(contactVO.getCity());
        vapContact.setPhone(contactVO.getPhone());
//        vapContact.setPostalCode(contactVO.getPostalCode());
        vapContact.setMobile(contactVO.getMobile());
//        vapContact.setCountry(contactVO.getCountry());

        return vapContact;
    }

    public static PartnerUserVO convert(Object[] partnerUser) {

        PartnerUserVO partnerUserVO = new PartnerUserVO();

        partnerUserVO.setUserId((Integer) partnerUser[VAPConstants.COMPANYUSER_USER_ID]);
        partnerUserVO.setEmailAddress((String) partnerUser[VAPConstants.COMPANYUSER_EMAIL_ADDRESS]);
        partnerUserVO.setFullName((String) partnerUser[VAPConstants.COMPANYUSER_FULL_NAME]);
        partnerUserVO.setIsActive(partnerUser[VAPConstants.COMPANYUSER_IS_ACTIVE].equals("1") ? Boolean.TRUE : Boolean.FALSE);
        partnerUserVO.setIsEnable(partnerUser[VAPConstants.COMPANYUSER_IS_ENABLE].equals("1") ? Boolean.TRUE : Boolean.FALSE);
        partnerUserVO.setCreatedDate((Date) partnerUser[VAPConstants.COMPANYUSER_CREATED_DATE]);
        partnerUserVO.setOfferId((Integer) partnerUser[VAPConstants.COMPANYUSER_OFFER_ID]);
        partnerUserVO.setOfferTo((Integer) partnerUser[VAPConstants.COMPANYUSER_OFFER_TO]);
        partnerUserVO.setStatus((String) partnerUser[VAPConstants.COMPANYUSER_STATUS]);

        return partnerUserVO;
    }

    public static CompanyVO convert(Company company, CompanyVO companyVO) {
        if (company == null) {
            return null;
        }
        companyVO.setId(company.getId());
        companyVO.setName(company.getName());
        companyVO.setSuspended(company.isSuspended());
        companyVO.setWorkFlowSteps(company.getWorkFlowSteps());
        return companyVO;
    }

    /**
     * converts User entity to UserInfoResend VO
     *
     * @param user
     * @param userInfoResend 
     * @return
     */
    public static UserInfoResend convert(User user, UserInfoResend userInfoResend) {

        userInfoResend.setEmailAddress(user.getEmailAddress());
        userInfoResend.setResetPasswordToken(user.getResetPasswordToken());

        return userInfoResend;
    }
}
