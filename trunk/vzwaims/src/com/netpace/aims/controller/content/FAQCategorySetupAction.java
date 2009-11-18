package com.netpace.aims.controller.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.content.AimsFAQCategoryManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.dataaccess.valueobjects.VOLookupFactory;
import com.netpace.aims.model.content.AimsFaqCategory;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/faqCategorySetup"
 *                scope="request"
 *	          name="FAQCategoryForm"
 *                validate="false"
 *                input="/content/faqCategoryViewDelete.jsp"
 * @struts.action-forward name="Update" path="/content/faqCategoryUpdate.jsp"
 *
 * @author Fawad Sikandar
 */

public class FAQCategorySetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(FAQCategorySetupAction.class.getName());

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
        checkAccess(request, AimsPrivilegesConstants.MANAGE_FAQ_CATEGORIES);

        String dateFormat = this.getResources(request).getMessage("date.format");
        //HttpSession session = request.getSession();
        FAQCategoryForm faqCategory = (FAQCategoryForm) form;
        log.debug(" Task Name .. : " + request.getParameter("task"));
        String taskname = request.getParameter("task");
        String forward = "Update";

        if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname))
        {
            faqCategory.setTask(taskname);
            faqCategory.setPlatformList(VOLookupFactory.getInstance().getAimsPlatformList());
            return mapping.findForward(forward);
        }

        if (AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname))
        {
            AimsFaqCategory aimsFAQCategory = AimsFAQCategoryManager.getFAQCategory(Integer.parseInt(request.getParameter("faqCategoryId")));
            faqCategory.setFaqCategoryId(Utility.convertToString(aimsFAQCategory.getFaqCategoryId()));
            faqCategory.setFaqCategoryName(aimsFAQCategory.getFaqCategoryName());
            faqCategory.setFaqCategoryDesc(aimsFAQCategory.getFaqCategoryDesc());
            faqCategory.setPlatformId(Utility.convertToString(aimsFAQCategory.getPlatformId()));
            faqCategory.setPlatformList(VOLookupFactory.getInstance().getAimsPlatformList());
            faqCategory.setCreatedBy(aimsFAQCategory.getCreatedBy());
            faqCategory.setCreatedDate(Utility.convertToString(aimsFAQCategory.getCreatedDate(), dateFormat));
            faqCategory.setTask(taskname);

            return mapping.findForward(forward);
        }
        return mapping.findForward(forward);
    }
}
