package com.netpace.aims.controller.content;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.content.AimsFAQsManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for viewing list of FAQs.
  *
 * @struts.action path="/FAQsViewList"
 *                scope="request"
 *                name="FAQsForm"
 *                input="/content/faqsViewList.jsp"
 * @struts.action-forward name="view" path="/content/faqsViewList.jsp"
 * @author Ahson Imtiaz
 */

public class FAQsViewListAction extends BaseAction
{

    static Logger log = Logger.getLogger(FAQsViewListAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.FAQ);

        FAQsForm faqsForm = (FAQsForm) form;

        Collection aimsFAQCategories = AimsFAQsManager.getFAQCategories();
        faqsForm.setCategoryList(aimsFAQCategories);

        return mapping.findForward("view");

    }

}