package com.netpace.aims.controller.alliance;

import com.netpace.aims.controller.BaseValidatorForm;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.HibernateException;

import java.util.Date;
import java.util.Set;
import java.util.Collection;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * @struts.form name="AllianceEditProfileForm"
 */
public class AllianceEditProfileForm extends BaseValidatorForm {

   
    /** identifier field */
    private java.lang.Long lineOfBusinessId;

    /** persistent field */
    private java.lang.String companyName;

    /** persistent field */
    private java.lang.String lastName;
    
    /** persistent field */
    private java.lang.String firstName;

		/** persistent field */
    private java.lang.String email;

		/** persistent field */
    private java.lang.String loginId;

		/** persistent field */
    private java.lang.String password;
    
    /** persistent field */
    private java.lang.String confirmPwd;
    
    /** persistent field */
    private java.lang.Boolean isAccepted;


    public java.lang.Long getLineOfBusinessId() {
        return this.lineOfBusinessId;
    }

    public void setLineOfBusinessId(java.lang.Long lineOfBusinessId) {
        this.lineOfBusinessId = lineOfBusinessId;
    }

    public java.lang.String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(java.lang.String companyName) {
        this.companyName = companyName;
    }

    public java.lang.String getLastName() {
        return this.lastName;
    }

    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }

		public java.lang.String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }
    
    public java.lang.String getEmail() {
        return this.email;
    }

    public void setEmail(java.lang.String email) {
        this.email= email;
    }
    public java.lang.String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(java.lang.String loginId) {
        this.loginId = loginId;
    }
    
    public java.lang.String getPassword() {
        return this.password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }
    
    public java.lang.String getConfirmPwd() {
        return this.confirmPwd;
    }

    public void setConfirmPwd(java.lang.String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }
    
    public java.lang.Boolean getIsAccepted() {
        return this.isAccepted;
    }

    public void setIsAccepted(java.lang.Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
    

	public void reset (ActionMapping mapping, HttpServletRequest request)   {
		
	System.out.println ("\n\n\nI N    R E S E T\n\n\n");
	}
				
    
    
}

