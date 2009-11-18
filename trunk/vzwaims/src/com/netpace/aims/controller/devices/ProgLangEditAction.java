package com.netpace.aims.controller.devices;

import java.util.Date;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsProgLangManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.masters.AimsProgrammingLang;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/progLangsEdit"
 *                name="ProgLangEditForm"
 *                scope="request"				  
 *                input="/masters/progLangEditForm.jsp"
 *                validate="true" 
 * @struts.action-forward name="view" path="/proglangs.do?task=view" redirect="true"
 * @author Rizwan Qazi
 */
public class ProgLangEditAction extends BaseAction
{

    static Logger log = Logger.getLogger(ProgLangEditAction.class.getName());

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
        String forward = "view";
        HttpSession session = request.getSession(true);
        String currUser = "rqazi";
        HashSet platformSet = new HashSet();

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("edit"))
        {

            log.debug("This is the all submit image clicked : " + taskname);

            AimsProgrammingLang AimsProgLang = (AimsProgrammingLang) session.getAttribute("AimsProgLang");

            ProgLangEditForm progLangForm = (ProgLangEditForm) form;

            String langName = request.getParameter("langName");
            String[] platformArr = request.getParameterValues("platform");

            log.debug("langName : " + langName);
            log.debug("Number of platforms selected : " + platformArr.length);

            AimsProgLang.setLangName(progLangForm.getLangName());
            AimsProgLang.setLangDesc(progLangForm.getLangDesc());
            AimsProgLang.setPlatform(AimsProgLangManager.getPlatformSet(progLangForm.getPlatform()));
            AimsProgLang.setLastUpdatedBy(currUser);
            AimsProgLang.setLastUpdatedDate(new Date());

            AimsProgLangManager.saveOrUpdateProgLang(AimsProgLang, platformArr);

            forward = "view";

        }

        if (taskname.equalsIgnoreCase("create"))
        {

            log.debug("This is the all submit image clicked : " + taskname);

            AimsProgrammingLang AimsProgLang = new AimsProgrammingLang();

            ProgLangEditForm progLangForm = (ProgLangEditForm) form;

            String langName = request.getParameter("langName");
            String[] platformArr = request.getParameterValues("platform");

            log.debug("langName : " + langName);
            log.debug("Number of platforms selected : " + platformArr.length);

            AimsProgLang.setLangName(progLangForm.getLangName());
            AimsProgLang.setLangDesc(progLangForm.getLangDesc());
            AimsProgLang.setPlatform(AimsProgLangManager.getPlatformSet(progLangForm.getPlatform()));
            AimsProgLang.setLastUpdatedBy(currUser);
            AimsProgLang.setLastUpdatedDate(new Date());

            AimsProgLangManager.saveOrUpdateProgLang(AimsProgLang, platformArr);

            forward = "view";
        }

        return mapping.findForward(forward);

    }
}
