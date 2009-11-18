package com.netpace.aims.controller.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.content.AimsFAQsManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.content.AimsFaqTopic;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/FAQsDetailView"
 *                scope="request"
 *                input="/content/faqsViewDelete.jsp"
 * @struts.action-forward name="detailView" path="/content/faqsDetailView.jsp"
 * @struts.action-exception key="error.login.InvalidUser" type="com.netpace.aims.bo.security.InvalidLoginException"
 * @author Fawad Sikandar
 * @see com.netpace.aims.bo.security.InvalidLoginException
 */

public class FAQsDetailViewAction extends BaseAction
{

    static Logger log = Logger.getLogger(FAQsDetailViewAction.class.getName());

    //public static String PRIVILEGE = "FAQ";

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_FAQS);

        //check if the user has the necessary rights
        //checkAccess(request, PRIVILEGE);

        String taskname = request.getParameter("task");

        if ("detailView".equalsIgnoreCase(taskname))
        {

            AimsFaqTopic aimsDetailViewFAQ = AimsFAQsManager.getFAQ(Integer.parseInt(request.getParameter("faq_topic_id")));
            request.setAttribute("aimsDetailViewFAQ", aimsDetailViewFAQ);

            return mapping.findForward(taskname);
        }

        return mapping.findForward("detailView");
    }

}