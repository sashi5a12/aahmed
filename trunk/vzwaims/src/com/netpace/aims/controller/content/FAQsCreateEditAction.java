package com.netpace.aims.controller.content;

import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * @struts.action path="/FAQsCreateEdit"
 *                scope="request"
 *		  name="FAQsForm"
 *		  validate="true"
 *                input="/content/faqsViewDelete.jsp"
 *
 * @struts.action-forward name="createForm" path="/content/faqsCreate.jsp"
 * @struts.action-forward name="editForm" path="/content/faqsEdit.jsp"
 * @struts.action-exception key="error.login.InvalidUser" type="com.netpace.aims.bo.security.InvalidLoginException"
 * @author Fawad Sikandar
 * @see com.netpace.aims.bo.security.InvalidLoginException
 */

public class FAQsCreateEditAction extends BaseAction
{
    static Logger log = Logger.getLogger(FAQsCreateEditAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_FAQS);

        String taskname = request.getParameter("task");

        if (taskname.equalsIgnoreCase("createForm"))
        {

            Collection aimsFAQCategories = AimsFAQsManager.getFAQCategories();
            FAQsForm faqsForm = (FAQsForm) form;
            faqsForm.setCategoryList(aimsFAQCategories);
            request.getSession().setAttribute("aimsFAQTopic", faqsForm);

            return mapping.findForward("createForm");
        }

        if (taskname.equalsIgnoreCase("editForm"))
        {

            Collection aimsFAQCategories = AimsFAQsManager.getFAQCategories();

            AimsFaqTopic aimsFAQTopic = AimsFAQsManager.getFAQ(Integer.parseInt(request.getParameter("faq_topic_id")));

            FAQsForm faqsForm = (FAQsForm) form;
            faqsForm.setFaqTopicId(aimsFAQTopic.getFaqTopicId().toString());
            faqsForm.setQuestion(aimsFAQTopic.getFaqTopicQuestion());
            faqsForm.setAnswer(aimsFAQTopic.getFaqTopicAnswer());
            faqsForm.setFaqCategoryId(aimsFAQTopic.getAimsFaqCategory().getFaqCategoryId().toString());
            faqsForm.setCategoryList(aimsFAQCategories);
            request.getSession().setAttribute("aimsFAQTopic", faqsForm);
            request.setAttribute("faqCategoryId", faqsForm.getFaqCategoryId());
            return mapping.findForward("editForm");
        }

        return mapping.findForward("view");
    }

}
