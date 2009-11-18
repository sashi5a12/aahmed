package com.netpace.aims.controller.profile;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.model.core.*;

import com.netpace.aims.bo.security.*;

import com.netpace.aims.util.AimsConstants;

import com.netpace.aims.controller.BaseAction;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.lang.StringBuffer;

import org.apache.log4j.Logger;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/editprofile"                
 *                scope="request" 
 *                input="/sys_admin.jsp"               
 *                name="EditProfileForm"
 *                validate="false"
 * @struts.action-forward name="edit" path="/profile/editProfile.jsp"
 * @author Ahson Imtiaz
 */
public class EditProfileAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(EditProfileAction.class.getName());
  
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        

		HttpSession session = request.getSession(); 
        AimsUser au = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
		EditProfileForm frm = (EditProfileForm) form;
		frm.setUserName(au.getUsername());
//		frm.setPassword(au.getPassword());
//		frm.setCpassword(au.getPassword());
		frm.setMotherMaidenName(au.getMotherMaidenName());
		frm.setLastName(au.getAimsContact().getLastName());
		frm.setFirstName(au.getAimsContact().getFirstName());
		frm.setTitle(au.getAimsContact().getTitle());
		frm.setEmailAddress(au.getAimsContact().getEmailAddress());
		frm.setPhone(au.getAimsContact().getPhone());
		frm.setFax(au.getAimsContact().getFax());
		frm.setMobile(au.getAimsContact().getMobile());
		
        if (au.getNotificationType() == null)
            frm.setNotificationType("E");
        else
            frm.setNotificationType(au.getNotificationType());

        if (au.getRowsLength() == null)
            frm.setRowsLength(new Long(10));
        else
            frm.setRowsLength(au.getRowsLength());
        
        return mapping.findForward("edit");

    }
}
