package com.netpace.aims.controller.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.content.AimsFAQsManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/FAQsViewDelete"
 *                scope="request"
 *                input="/content/faqsViewDelete.jsp"
 * @struts.action-forward name="view" path="/content/faqsViewDelete.jsp"
 * @struts.action-exception key="error.login.InvalidUser" type="com.netpace.aims.bo.security.InvalidLoginException"
 * @author Fawad Sikandar
 * @see com.netpace.aims.bo.security.InvalidLoginException
 */

public class FAQsViewDeleteAction extends BaseAction
{

    static Logger log = Logger.getLogger(FAQsViewDeleteAction.class.getName());

    //public static String PRIVILEGE = "FAQ";

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_FAQS);

        //check if the user has the necessary rights
        //checkAccess(request,PRIVILEGE);

        String taskname = request.getParameter("task");
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        int PAGE_LENGTH = user.getRowsLength().intValue(); //10;
        int pageNo = 1;
        pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);

        if (taskname.equalsIgnoreCase("delete"))
        {
            int delCount = AimsFAQsManager.deleteFAQTopic(Integer.parseInt(request.getParameter("faq_topic_id")));
        }

        int rows = AimsFAQsManager.getFAQTopicsCount();
        int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

        if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH)))
        {
            pageNo = 1;
            pmax = 1;
        }

        request.setAttribute("aimsFAQTopics", AimsFAQsManager.getFAQTopics(PAGE_LENGTH, pageNo));
        request.setAttribute("page_id", new Integer(pageNo));
        request.setAttribute("page_max", new Integer(pmax));

        return mapping.findForward("view");
    }

}