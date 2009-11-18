package com.netpace.aims.controller.devices;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsProgLangManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/proglangs"                
 *                scope="request" 
 *				  name="ProgLangForm"
 *                input="/sys_admin.jsp"               
 * @struts.action-forward name="view" path="/masters/progLangView.jsp"
 * @struts.action-forward name="edit" path="/masters/progLangEdit.jsp"
 * @author Rizwan Qazi 
 */
public class ProgLangAction extends BaseAction
{

    static Logger log = Logger.getLogger(ProgLangAction.class.getName());

    /**
     * This method validates the user.
     * It calls the Security manager with the user name and password and gets a 
     * User object if the user is valid. It then moves the user to his logged in page.
     * 
     * If the username and password does not match it throws 
     * a InvalidUserException. 
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.PROGRAMMING_LANGUAGES);

        String taskname = request.getParameter("task");
        HttpSession session = request.getSession(true);

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("view"))
        {

            Collection AimsProgLangs = AimsProgLangManager.getProgLangs();
            log.debug("The size of the set is " + AimsProgLangs.size());
            request.setAttribute("AimsProgLangs", AimsProgLangs);

            return mapping.findForward("view");
        }

        if (taskname.equalsIgnoreCase("delete"))
        {

            int delCount = AimsProgLangManager.deleteProgLang(Integer.parseInt(request.getParameter("langId")));
            Collection AimsProgLangs = AimsProgLangManager.getProgLangs();
            log.debug("The size of the set is " + AimsProgLangs.size());
            request.setAttribute("AimsProgLangs", AimsProgLangs);

            return mapping.findForward("view");
        }

        return mapping.findForward("view");
    }
}
