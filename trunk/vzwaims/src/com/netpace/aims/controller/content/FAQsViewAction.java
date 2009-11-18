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
 * @struts.action path="/FAQsView"
 *                name="FAQsForm"
 *                scope="request"
 *                input="/content/faqsViewList.jsp"
 * @struts.action-forward name="detailView" path="/content/faqsView.jsp"
  * @author Ahson Imtiaz
  */

public class FAQsViewAction extends BaseAction
{

    static Logger log = Logger.getLogger(FAQsViewAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.FAQ);

        String taskname = request.getParameter("task");
        FAQsForm faqsForm = (FAQsForm) form;
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);

        log.debug("Category Id : " + faqsForm.getFaqCategoryId());
        request.setAttribute("faqCategoryName", request.getParameter("faqCategoryName"));
        request
            .setAttribute(
                "aimsFAQTopics",
                AimsFAQsManager.getFAQTopics(StringFuncs.initializeStringGetParam(faqsForm.getFaqCategoryId(), "0"), user.getRowsLength().intValue()
        /*Page length*/
        , 1));

        return mapping.findForward("detailView");
    }
}
