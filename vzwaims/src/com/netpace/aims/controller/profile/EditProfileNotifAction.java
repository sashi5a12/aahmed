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

import com.netpace.aims.util.AimsConstants;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.bo.profile.AimsProfileNotifManager;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.lang.StringBuffer;

import org.apache.log4j.Logger;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/editprofilenotif"                
 *                scope="request" 
 *                input="/profile/editProfileNotification.jsp"               
 *                name="EditProfileNotifForm"
 *                validate="false"
 * @struts.action-forward name="edit" path="/profile/editProfileNotification.jsp"
 * @struts.action-exception key="error.generic.database" type="net.sf.hibernate.HibernateException"
 * @author Ahson Imtiaz
 */
public class EditProfileNotifAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(EditProfileNotifAction.class.getName());
  
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        

		HttpSession session = request.getSession(); 
		ActionMessages messages = new ActionMessages();
      AimsUser au = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
		EditProfileNotifForm frm = (EditProfileNotifForm) form;
		String task = frm.getTask();
		
		if ( task != null)
		{
			if (task.equalsIgnoreCase("update"))
			{
				AimsProfileNotifManager.updateUserNotifications(au,frm.getEventsIds());
				messages.add( ActionMessages.GLOBAL_MESSAGE,new ActionMessage("EditProfileNotifForm.updated") );
            saveMessages(request , messages);
			}
		}
		
		frm.setEventsIds( AimsProfileNotifManager.getUserNotifIds(au.getUserId()) );
		Collection coll = AimsProfileNotifManager.getAllApplicableUserNotifs(au);
		if (coll != null && coll.size() > 0)
		request.setAttribute( "AllNotifications" ,  coll );
		
      return mapping.findForward("edit");

    }
}
