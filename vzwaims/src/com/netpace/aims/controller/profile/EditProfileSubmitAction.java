package com.netpace.aims.controller.profile;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.DynaValidatorForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.model.core.*;

import com.netpace.aims.bo.security.*;
import com.netpace.aims.bo.profile.*;

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
 * @struts.action path="/editprofilesubmit"                
 *                scope="request" 
 *                input="/profile/editProfile.jsp"               
 *                name="EditProfileForm"
 *                validate="true"
 * @struts.action-forward name="updated" path="/common/logged-in.jsp"
 * @struts.action-forward name="prov_apps" path="/applicationSearchProvisioned.do?task=search" 
 * @struts.action-exception key="error.UserProfile.UserNameExists" type="com.netpace.aims.bo.profile.AimsUsernameException"
 * @struts.action-exception key="error.UserProfile.EmailAddressExists" type="com.netpace.aims.bo.profile.AimsEmailAddressException"
 * @struts.action-exception key="error.generic.database" type="net.sf.hibernate.HibernateException"
 * @author Ahson Imtiaz
 */
public class EditProfileSubmitAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(EditProfileAction.class.getName());
  
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        

		HttpSession session = request.getSession(); 
		ActionMessages messages = new ActionMessages();
        AimsUser au = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
		EditProfileForm frm = (EditProfileForm) form;

        //au.setUsername(frm.getUserName());
        //au.setPassword(frm.getPassword());

        /*  after vzdn, user can not change contact info 
            au.setMotherMaidenName(frm.getMotherMaidenName());
            au.getAimsContact().setLastName(frm.getLastName());
            au.getAimsContact().setFirstName(frm.getFirstName());
            au.getAimsContact().setTitle(frm.getTitle());
            //au.getAimsContact().setEmailAddress(frm.getEmailAddress());
            au.getAimsContact().setPhone(frm.getPhone());
            au.getAimsContact().setFax(frm.getFax());
            au.getAimsContact().setMobile(frm.getMobile());
            au.setNotificationType(frm.getNotificationType());
		*/
		au.setRowsLength(frm.getRowsLength());
		
		AimsProfileManager.saveOrUpdate(au);
		messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("UserProfileForm.recordUpdated") );
        saveMessages(request , messages);
		
      if ("Y".equalsIgnoreCase((String)request.getSession().getAttribute(AimsConstants.AIMS_ONLY_PROV_APPS)))
      {
      	return mapping.findForward("prov_apps");
      }      
		return mapping.findForward("updated");

    }
}
