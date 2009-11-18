package com.netpace.aims.controller.content;

import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.content.AimsFAQsManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.content.AimsFaqCategory;
import com.netpace.aims.model.content.AimsFaqTopic;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/FAQsEditSubmit"
 *                scope="request"
 *		  name="FAQsForm"
 *		  validate="true"
 *                input="/content/faqsEdit.jsp"
 *
 * @struts.action-forward name="view" path="/FAQsViewDelete.do?task=view"
 * @struts.action-exception key="error.content.faq.duplicate" type="com.netpace.aims.bo.core.UniqueConstraintException"
 * @author Fawad Sikandar
 * @see com.netpace.aims.bo.security.InvalidLoginException
 */

public class FAQsEditSubmitAction extends BaseAction
{
    static Logger log = Logger.getLogger(FAQsEditSubmitAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_FAQS);

        String taskname = request.getParameter("task");
        String currentUser = "fSikandar";

        if (taskname.equalsIgnoreCase("edit"))
        {
            AimsFaqCategory aimsFAQsCategory = new AimsFaqCategory();
            AimsFaqTopic aimsFAQsTopic = new AimsFaqTopic();
            FAQsForm faqsForm = (FAQsForm) form;
            aimsFAQsTopic.setFaqTopicId(Long.valueOf(faqsForm.getFaqTopicId()));
            aimsFAQsTopic.setFaqTopicQuestion(faqsForm.getQuestion());
            aimsFAQsTopic.setFaqTopicAnswer(faqsForm.getAnswer());
            System.out.println("Category : " + faqsForm.getFaqCategoryId());
            aimsFAQsCategory.setFaqCategoryId(Long.valueOf(faqsForm.getFaqCategoryId()));
            aimsFAQsTopic.setAimsFaqCategory(aimsFAQsCategory);
            aimsFAQsTopic.setCreatedBy(currentUser);
            aimsFAQsTopic.setCreatedDate(new Date());
            aimsFAQsTopic.setLastUpdatedBy(currentUser);
            aimsFAQsTopic.setLastUpdatedDate(new Date());

            AimsFAQsManager.saveOrUpdateFAQs(aimsFAQsTopic);

            return mapping.findForward("view");
        }

        return mapping.findForward("view");
    }

}
