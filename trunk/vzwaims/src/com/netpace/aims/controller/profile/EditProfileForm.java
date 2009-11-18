package com.netpace.aims.controller.profile;

import java.lang.*;
import java.util.*;
import org.apache.struts.action.*;
import javax.servlet.http.HttpServletRequest;
import com.netpace.aims.controller.BaseValidatorForm;

/**
 * @struts.form name="EditProfileForm"
 * @author Ahson Imtiaz.
 */
 
public class EditProfileForm extends BaseValidatorForm{ 

	String userName;
	String motherMaidenName;
	String lastName;
	String firstName;
	String title;
	String emailAddress;
	String phone;
	String fax;
	String mobile;
	String notificationType;
	Long rowsLength;

/* Property setter and getter methods */
	public String getUserName() {

	return userName;
	}

	public void setUserName(String input ) {

	this.userName = input;
	}

	public String getMotherMaidenName() {

	return motherMaidenName;
	}

	public void setMotherMaidenName(String input ) {

	this.motherMaidenName = input;
	}

	public String getLastName() {

	return lastName;
	}

	public void setLastName(String input ) {

	this.lastName = input;
	}

	public String getFirstName() {

	return firstName;
	}

	public void setFirstName(String input ) {

	this.firstName = input;
	}

	public String getTitle() {

	return title;
	}

	public void setTitle(String input ) {

	this.title = input;
	}

	public String getEmailAddress() {

	return emailAddress;
	}

	public void setEmailAddress(String input ) {

	this.emailAddress = input;
	}

	public String getPhone() {

	return phone;
	}

	public void setPhone(String input ) {

	this.phone = input;
	}

	public String getFax() {

	return fax;
	}

	public void setFax(String input ) {

	this.fax = input;
	}

	public String getMobile() {

	return mobile;
	}

	public void setMobile(String input ) {

	this.mobile = input;
	}

	public String getNotificationType() {

	return notificationType;
	}

	public void setNotificationType(String input ) {

	this.notificationType = input;
	}

	public Long getRowsLength() {

	return rowsLength;
	}

	public void setRowsLength(Long input ) {

	this.rowsLength = input;
	}

	/* Validation Function */
	public ActionErrors	validate(ActionMapping mapping,	HttpServletRequest request)	{

		ActionErrors errors	=	new	ActionErrors();
		
		/*if ( isBlankString(this.password) ) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.password"));
		}
		if ( isBlankString(this.cpassword) ) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.cpassword"));
		}
		else if (this.password != null && !this.password.equals(this.cpassword)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.passNotMatch"));
		}*/

        /*  after vzdn, user can not change contact info
            if ( isBlankString(this.firstName) ) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.firstName"));
            }
            if ( isBlankString(this.lastName) ) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.lastName"));
            }
            if ( isBlankString(this.title) ) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.title"));
            }
            if ( isBlankString(this.motherMaidenName) ) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.motherMaidenName"));
            }
            if ( isBlankString(this.phone) ) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.phone"));
            }
            if ( isBlankString(this.fax) ) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.fax"));
            }
            if ( isBlankString(this.mobile) ) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.mobile"));
            }
            if ( isBlankString(this.notificationType) ) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.notificationType"));
            }
        */
		if ( this.rowsLength == null ) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.UserProfileForm.rowsLength"));
		}

		return errors;

	}

} 
 /* Class ends */