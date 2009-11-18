package com.netpace.aims.controller.accounts;

import com.netpace.aims.bo.accounts.AimsAccountsManager;
import com.netpace.aims.controller.BaseValidatorForm;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.security.AimsRolePrivilege;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Set;

/*****************************
    //commented for vzw accounts cleanup,
    //disabled xdoclet struts actions, forwards and forms
    struts.form name="AccountVZWForm"
*****************************/

public class AccountVZWForm extends BaseValidatorForm {

    /** identifier field */
    private java.lang.String userId;

    /** persistent field */
    private java.lang.String username;

    /** persistent field */
    private java.lang.String userPassword;

    /** persistent field */
    private java.lang.String userType;

    /** persistent field */
    private java.lang.String firstName;

    /** persistent field */
    private java.lang.String lastName;

    /** persistent field */
    private java.lang.String emailAddress;

	/**	persistent field */
	private	java.lang.String title;

	/**	persistent field */
	private	java.lang.String phone;

	/**	persistent field */
	private	java.lang.String mobile;

	/**	persistent field */
	private	java.lang.String fax;

    /** nullable persistent field */
    private java.lang.String motherMaidenName;

    /** nullable persistent field */
    private java.lang.String managerId;

    /** nullable persistent field */
    private java.lang.String ifHq;

    /** nullable persistent field */
    private java.lang.String userAccountStatus;

    /** nullable persistent field */
    private java.lang.String userSessionStatus;

    /** nullable persistent field */
    private java.lang.String ifBlocked;

    /** nullable persistent field */
    private java.lang.String lastLoginDate;

    /** nullable persistent field */
    private java.lang.String createdBy;

    /** nullable persistent field */
    private java.lang.String createdDate;

    /** nullable persistent field */
    private java.lang.String lastUpdatedBy;

    /** nullable persistent field */
    private java.lang.String lastUpdatedDate;

    /** persistent field */
    private com.netpace.aims.model.core.AimsContact aimsContact;

    /** persistent field */
    private com.netpace.aims.model.core.AimsVzwDepartment aimsVzwDepartment;

    /** persistent field */
    private Set roles;

	private String [] selectedRoles;
	private String [] availableRoles;


    public java.lang.String getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }

    public java.lang.String getUsername() {
        return this.username;
    }

    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public java.lang.String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }

    public java.lang.String getLastName() {
        return this.lastName;
    }

    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }

    public java.lang.String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(java.lang.String emailAddress) {
        this.emailAddress = emailAddress;
    }

	public java.lang.String	getTitle()	{
		return this.title;
	}

	public void	setTitle(java.lang.String strValue) {
		this.title = strValue;
	}

	public java.lang.String	getPhone()	{
		return this.phone;
	}

	public void	setPhone(java.lang.String strValue) {
		this.phone = strValue;
	}

	public java.lang.String	getMobile()	{
		return this.mobile;
	}

	public void	setMobile(java.lang.String strValue) {
		this.mobile = strValue;
	}

	public java.lang.String	getFax()	{
		return this.fax;
	}

	public void	setFax(java.lang.String strValue) {
		this.fax = strValue;
	}

    public java.lang.String getUserType() {
        return this.userType;
    }

    public void setUserType(java.lang.String userType) {
        this.userType = userType;
    }

    public java.lang.String getMotherMaidenName() {
        return this.motherMaidenName;
    }

    public void setMotherMaidenName(java.lang.String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    public java.lang.String getManagerId() {
        return this.managerId;
    }

    public void setManagerId(java.lang.String managerId) {
        this.managerId = managerId;
    }

    public java.lang.String getIfHq() {
        return this.ifHq;
    }

    public void setIfHq(java.lang.String ifHq) {
        this.ifHq = ifHq;
    }

    public java.lang.String getUserAccountStatus() {
        return this.userAccountStatus;
    }

    public void setUserAccountStatus(java.lang.String userAccountStatus) {
        this.userAccountStatus = userAccountStatus;
    }

    public java.lang.String getUserSessionStatus() {
        return this.userSessionStatus;
    }

    public void setUserSessionStatus(java.lang.String userSessionStatus) {
        this.userSessionStatus = userSessionStatus;
    }

    public java.lang.String getIfBlocked() {
        return this.ifBlocked;
    }

    public void setIfBlocked(java.lang.String ifBlocked) {
        this.ifBlocked = ifBlocked;
    }

    public java.lang.String getLastLoginDate() {
        return this.lastLoginDate;
    }

    public void setLastLoginDate(java.lang.String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public java.lang.String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }

    public java.lang.String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(java.lang.String createdDate) {
        this.createdDate = createdDate;
    }

    public java.lang.String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(java.lang.String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public java.lang.String getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(java.lang.String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public com.netpace.aims.model.core.AimsContact getAimsContact() {
        return this.aimsContact;
    }

    public void setAimsContact(com.netpace.aims.model.core.AimsContact aimsContact) {
        this.aimsContact = aimsContact;
    }

    public com.netpace.aims.model.core.AimsVzwDepartment getAimsVzwDepartment() {
        return this.aimsVzwDepartment;
    }

    public void setAimsVzwDepartment(com.netpace.aims.model.core.AimsVzwDepartment aimsVzwDepartment) {
        this.aimsVzwDepartment = aimsVzwDepartment;
    }

    public java.util.Set getRoles() {
        return this.roles;
    }

    public void setRoles(java.util.Set roles) {
        this.roles = roles;
    }


    public String [] getSelectedRoles() {
        return this.selectedRoles;
    }

    public void setSelectedRoles(String [] selectedRoles) {
		this.selectedRoles = selectedRoles;
	}


    public String [] getAvailableRoles() {
        return this.availableRoles;
    }

    public void setAvailableRoles(String [] availableRoles) {
        this.availableRoles = availableRoles;
    }


	public void reset (ActionMapping mapping, HttpServletRequest request)
	{
        System.out.println("The RESET is called in AccountVZWForm!");

        HttpSession session = request.getSession();
        Session hibernateSession = null;

		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		String user_type = user.getUserType();
		Long user_id = user.getUserId();
		String taskname =  StringFuncs.NullValueReplacement(request.getParameter("task"));

        Collection AimsSysPrivileges = null;
		Collection AimsRolePrivileges = null;
		Collection AimsAvailableRoles = null;
		Collection AimsAssignedRoles = null;
        AimsUser AimsEditUser = null;
        AimsRolePrivilege currPrivelege = null;
        Long currPrivelegeId = null;


        if (taskname.equalsIgnoreCase("create") )
        {

			try
			{

				 AimsAvailableRoles = AimsAccountsManager.getAllAvailableRoles(user_id, "V");
			}
			catch (HibernateException he)
			{
				System.out.println("A hibernate exception occured!");
			}

			request.setAttribute("AimsAvailableRoles", AimsAvailableRoles);
			request.setAttribute("AimsAssignedRoles", AimsAssignedRoles);

        }

        if (taskname.equalsIgnoreCase("edit") )
        {


			try
			{
				AimsAvailableRoles = AimsAccountsManager.getAvailableRoles(new Long(request.getParameter("userId")), "V");
				AimsAssignedRoles = AimsAccountsManager.getAssignedRoles(new Long(request.getParameter("userId")), "V");

			}
			catch (HibernateException he)
			{
				System.out.println("A hibernate exception occured!");
			}



			request.setAttribute("AimsAvailableRoles", AimsAvailableRoles);
			request.setAttribute("AimsAssignedRoles", AimsAssignedRoles);


        }

        if (taskname.equalsIgnoreCase("createForm") )
        {


			try
			{

				 AimsAvailableRoles = AimsAccountsManager.getAllAvailableRoles(user_id, "V");
			}
			catch (HibernateException he)
			{
				System.out.println("A hibernate exception occured!");
			}

			request.setAttribute("AimsAvailableRoles", AimsAvailableRoles);
			request.setAttribute("AimsAssignedRoles", AimsAssignedRoles);

        }

        if (taskname.equalsIgnoreCase("editForm") || taskname.equalsIgnoreCase("editFormView"))
        {


			try
			{
				AimsEditUser = AimsAccountsManager.getAccount(new Long(request.getParameter("accountId")));
				AimsAvailableRoles = AimsAccountsManager.getAvailableRoles(new Long(request.getParameter("accountId")), "V");
				AimsAssignedRoles = AimsAccountsManager.getAssignedRoles(new Long(request.getParameter("accountId")), "V");
            }
			catch (HibernateException he)
			{
				System.out.println("A hibernate exception occured!");
			}

            //check access, current user is allowed to change info
            /********* security check ***********/
            if(AimsEditUser!=null && AimsAccountsManager.checkCurrentUserAccessPrivilegeForUser(request, user, AimsEditUser)) {
                this.setUserId(AimsEditUser.getUserId().toString());
                this.setUsername(AimsEditUser.getUsername());
                this.setUserPassword(AimsEditUser.getPassword());
                this.setUserAccountStatus(AimsEditUser.getUserAccountStatus());

                this.setFirstName(AimsEditUser.getAimsContact().getFirstName());
                this.setLastName(AimsEditUser.getAimsContact().getLastName());
                this.setEmailAddress(AimsEditUser.getAimsContact().getEmailAddress());
                this.setTitle(AimsEditUser.getAimsContact().getTitle());
                this.setPhone(AimsEditUser.getAimsContact().getPhone());
                this.setMobile(AimsEditUser.getAimsContact().getMobile());
                this.setFax(AimsEditUser.getAimsContact().getFax());


                request.setAttribute("AimsAvailableRoles", AimsAvailableRoles);
                request.setAttribute("AimsAssignedRoles", AimsAssignedRoles);
            }//end check access
            else {
                request.setAttribute("accessNotAllowed", new Boolean(true));
            }
        }

	} // end of method reset


    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request){
	 	ActionErrors errors = new ActionErrors();
        String taskname	=	 StringFuncs.NullValueReplacement(request.getParameter("task"));

        if (this.isBlankString(this.emailAddress))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.emailAddress"));
        }
	 	if (this.isBlankString(this.emailAddress)==false && super.isValidEmail(this.emailAddress)==false)
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.validate.emailAddress"));
        }

        if(taskname.equalsIgnoreCase("create")) {
            //password is required on create user screen
            if (this.isBlankString(this.userPassword))
            {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.password"));
            }
        }

        if (this.isBlankString(this.firstName))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.firstName"));
        }
	 	if (this.isBlankString(this.lastName))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.lastName"));
        }
	 	if (this.isBlankString(this.title))
        {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.required.title"));
        }

	 	return errors;
    }
}
