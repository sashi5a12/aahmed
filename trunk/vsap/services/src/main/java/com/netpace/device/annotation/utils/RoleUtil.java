package com.netpace.device.annotation.utils;

import com.netpace.device.entities.enums.ApplicationPropertyType;
import com.netpace.device.services.ApplicationPropertiesService;
import com.netpace.device.utils.VAPConstants;
import com.netpace.device.vo.SystemRoleVO;
import com.netpace.device.vo.VAPUserDetails;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author nraza
 */
public class RoleUtil {

    private static final Log log = LogFactory.getLog(RoleUtil.class);

    /**
     * canEditCompany checks if the logged in user can edit the company or not
     * Roles which can edit company are configured in the property
     * redirect.to.edit.company.on.roles of the vap_application_properties table
     */
    public static boolean canEditCompany(
            VAPUserDetails loggedInUser,
            ApplicationPropertiesService applicationPropertiesService) {

        String userName = loggedInUser.getUsername();
        List<SystemRoleVO> userRoles = loggedInUser.getRoles();

        //These are the roles who can edit the company... Other roles can only view company
        String rolesToEditCompany = applicationPropertiesService.getApplicationPropertiesByTypeAndKey(
                ApplicationPropertyType.APPLICATION_PROPERTY,
                VAPConstants.APP_PROPERTY_TO_EDIT_COMPANY_ON_ROLES).getValue();

        log.debug("Roles which can edit a company = " + rolesToEditCompany);
        if (isRoleFound(userRoles, rolesToEditCompany, loggedInUser)) {
            log.debug("User[" + userName + "] is allowed to edit company ");
            return true;
        }
        log.debug("User [" + userName + "] is not allowed to edit company");
        return false;
    }

    /**
     * canDeleteCompany checks if the logged in user can delete the company or
     * not Roles which can delete company are configured in the property
     * redirect.to.delete.company.on.roles of the vap_application_properties
     * table
     */
    public static boolean canDeleteCompany(
            VAPUserDetails loggedInUser,
            ApplicationPropertiesService applicationPropertiesService) {

        String userName = loggedInUser.getUsername();
        List<SystemRoleVO> userRoles = loggedInUser.getRoles();

        //These are the roles who can delete the company... 
        String rolesToDeleteCompany = applicationPropertiesService.getApplicationPropertiesByTypeAndKey(
                ApplicationPropertyType.APPLICATION_PROPERTY,
                VAPConstants.APP_PROPERTY_TO_DELETE_COMPANY_ON_ROLES).getValue();

        log.debug("Roles which can delete a company = " + rolesToDeleteCompany);
        if (isRoleFound(userRoles, rolesToDeleteCompany, loggedInUser)) {
            log.debug("User[" + userName + "] is allowed to delete company ");
            return true;
        }
        log.debug("User [" + userName + "] is not allowed to delete company");
        return false;
    }

    /**
     * canSuspendOrUnsuspendCompany checks if the logged in user can
     * suspend/unsuspend the company or not. Roles which can suspend/unsuspend
     * company are configured in the property
     * suspend.or.unsuspend.company.on.roles of the vap_application_properties
     * table
     */
    public static boolean canSuspendOrUnsuspendCompany(
            VAPUserDetails loggedInUser,
            ApplicationPropertiesService applicationPropertiesService) {

        String userName = loggedInUser.getUsername();
        List<SystemRoleVO> userRoles = loggedInUser.getRoles();

        //These are the roles who can delete the company... 
        String rolesToSuspendOrUnsuspendCompany =
                applicationPropertiesService.getApplicationPropertiesByTypeAndKey(
                ApplicationPropertyType.APPLICATION_PROPERTY,
                VAPConstants.APP_PROPERTY_TO_SUSPEND_OR_UNSUSPEND_COMPANY_ON_ROLES).getValue();

        log.debug("Roles which can supend/unsuspend a company = "
                + rolesToSuspendOrUnsuspendCompany);
        if (isRoleFound(
                userRoles, rolesToSuspendOrUnsuspendCompany, loggedInUser)) {
            log.debug("User[" + userName
                    + "] is allowed to suspend/unsuspend company ");
            return true;
        }
        log.debug("User [" + userName
                + "] is not allowed to suspend/unsuspend company");
        return false;
    }

    /**
     * This method matches the roleName in csvRoles string and if any roleName
     * matches it returns true otherwise false.
     *
     * @param systemRoleVOs
     * @param csvRoles
     * @param loggedInUser
     *
     * @return
     */
    public static boolean isRoleFound(
            List<SystemRoleVO> systemRoleVOs,
            String csvRoles, VAPUserDetails loggedInUser) {

        for (SystemRoleVO userRole : systemRoleVOs) {
            if (csvRoles.contains(userRole.getRoleName())) {
                log.debug("User [" + loggedInUser.getUsername()
                        + "] has role [" + userRole.getRoleName() + "]");
                return true;
            }
        }
        return false;
    }
}
