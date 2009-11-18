package com.netpace.aims.controller.content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.content.AimsFAQCategoryManager;
import com.netpace.aims.bo.system.SystemConstants;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.content.AimsFaqCategory;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/faqCategoryInsertUpdate"
 *                scope="request"
 *	          name="FAQCategoryForm"
 *                validate="true"
 *                input="/content/faqCategoryUpdate.jsp"
 * @struts.action-forward name="view" path="/faqCategory.do?task=view" redirect="true"
 * @struts.action-exception key="error.content.faqcategory.duplicate" type="com.netpace.aims.bo.core.UniqueConstraintException"
 * @author Fawad Sikandar
 */

public class FAQCategoryInsertUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(FAQCategoryInsertUpdateAction.class.getName());

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

        String taskname = request.getParameter("task");
        String forward = "view";
        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        FAQCategoryForm faqCategoryForm = (FAQCategoryForm) form;

        if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname) || AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname))
        {
            AimsFaqCategory aimsFaqCategory = new AimsFaqCategory();
            aimsFaqCategory.setFaqCategoryId(Utility.convertToLong(faqCategoryForm.getFaqCategoryId()));
            aimsFaqCategory.setFaqCategoryName(faqCategoryForm.getFaqCategoryName());
            aimsFaqCategory.setFaqCategoryDesc(faqCategoryForm.getFaqCategoryDesc());
            aimsFaqCategory.setPlatformId(Utility.convertToBigDecimal(faqCategoryForm.getPlatformId()));

            if (AimsConstants.CREATE_TASK.equalsIgnoreCase(taskname))
            {
                aimsFaqCategory.setCreatedBy(currUser);
                aimsFaqCategory.setCreatedDate(SystemConstants.currentDate);
            }
            if (AimsConstants.EDIT_TASK.equalsIgnoreCase(taskname))
            {
                aimsFaqCategory.setCreatedBy(faqCategoryForm.getCreatedBy());
                aimsFaqCategory.setCreatedDate(Utility.convertToDate(faqCategoryForm.getCreatedDate(), dateFormat));
            }

            aimsFaqCategory.setLastUpdatedBy(currUser);
            aimsFaqCategory.setLastUpdatedDate(SystemConstants.currentDate);
            AimsFAQCategoryManager.saveOrUpdateFAQs(aimsFaqCategory);

        }
        return mapping.findForward(forward);
    }
}
