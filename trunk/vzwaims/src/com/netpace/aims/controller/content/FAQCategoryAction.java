package com.netpace.aims.controller.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/faqCategory"
 *                input="/content/faqCategoryViewDelete.jsp"
 *                scope="request"
 *	          name="FAQCategoryForm"
 *                validate="false"
 * @struts.action-forward name="view" path="/content/faqCategoryViewDelete.jsp"
 * @struts.action-exception key="error.content.platform.integrity" type="com.netpace.aims.bo.core.IntegrityConstraintException"
 *
 * @author Fawad Sikandar
 * @see com.netpace.aims.bo.security.InvalidLoginException
 */

public class FAQCategoryAction extends BaseAction
{

    static Logger log = Logger.getLogger(FAQCategoryAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.MANAGE_FAQ_CATEGORIES);

        String taskname = request.getParameter("task");
        FAQCategoryForm faqCategoryForm = (FAQCategoryForm) form;
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        int PAGE_LENGTH = user.getRowsLength().intValue(); //Integer.parseInt(this.getResources(request).getMessage("records.pageLength"));
        int pageNo = 1;
        pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);

        int rows = AimsFAQCategoryManager.getFAQCategoriesCount();
        int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

        if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH)))
        {
            pageNo = 1;
            pmax = 1;
        }

        Collection collection = AimsFAQCategoryManager.getFAQCategories(PAGE_LENGTH, pageNo);
        Collection aimsFAQCategories = new ArrayList();

        for (Iterator iter = collection.iterator(); iter.hasNext();)
        {
            AimsFaqCategory aimsFaqCategory = (AimsFaqCategory) iter.next();
            FAQCategoryForm faqCategory = new FAQCategoryForm();            
            faqCategory.setFaqCategoryId(Utility.convertToString(aimsFaqCategory.getFaqCategoryId()));
            faqCategory.setFaqCategoryName(aimsFaqCategory.getFaqCategoryName());
            faqCategory.setFaqCategoryDesc(aimsFaqCategory.getFaqCategoryDesc());
            faqCategory.setPlatformName(
                VOLookupFactory
                    .getInstance()
                    .getAimsPlatform(Utility.convertToLong(Utility.convertToString(aimsFaqCategory.getPlatformId())))
                    .getPlatformName());
            aimsFAQCategories.add(faqCategory);
        }

        faqCategoryForm.setFaqCategories(aimsFAQCategories);
        request.setAttribute("page_id", new Integer(pageNo));
        request.setAttribute("page_max", new Integer(pmax));

        if ("delete".equalsIgnoreCase(taskname))
        {
            int delCount = AimsFAQCategoryManager.deleteFAQCategory(Integer.parseInt(request.getParameter("faqCategoryId")));
        }

        return mapping.findForward("view");
    }

}