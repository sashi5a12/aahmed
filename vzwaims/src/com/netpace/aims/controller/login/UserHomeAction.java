package com.netpace.aims.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.logincontent.AimsLoginContent;
import com.netpace.aims.model.alliance.AimsBulletin;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.LobUtils;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes user to his logged in home page.
 *
 * @struts.action path="/userHome"
 *                scope="request"
 *                input="/"
 * @struts.action-forward name="success" path="/common/logged-in.jsp" 
 * @struts.action-forward name="prov_apps" path="/applicationSearchProvisioned.do?task=search" redirect="true"
 * @author Shahnawaz Bagdadi
 */
public class UserHomeAction extends Action
{

    static Logger log = Logger.getLogger(UserHomeAction.class.getName());

    /**
     * This method invalidates the user and logs the user out. 
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        log.debug("Home page ");

        if (request.getSession().getAttribute(AimsConstants.SESSION_BULLETIN_TO_READ) != null)
        {
            Boolean lastBulletinAcknowledged = (Boolean)request.getAttribute("lastBulletinAcknowledged");
            //if bulletin was set from acknowledged actions of bulletin, then skip showing error
            if(lastBulletinAcknowledged == null || !(lastBulletinAcknowledged.booleanValue()))
            {
                AimsBulletin bulletin =  (AimsBulletin) DBHelper.getInstance().load(AimsBulletin.class, request.getSession().getAttribute(AimsConstants.SESSION_BULLETIN_TO_READ).toString());
                request.setAttribute(AimsConstants.REQUEST_BULLETIN_HEADER, StringFuncs.NullValueReplacement(bulletin.getBulletinHeader()));
                request.setAttribute(AimsConstants.REQUEST_BULLETIN_TEXT, LobUtils.clobToString(bulletin.getBulletinText()));
                request.setAttribute(AimsConstants.REQUEST_BULLETIN_POST_ACTION, StringFuncs.NullValueReplacement(bulletin.getBulletinPostAction()));
                request.setAttribute(AimsConstants.REQUEST_BULLETIN_ERROR_MESSAGE, StringFuncs.NullValueReplacement(bulletin.getBulletinErrorMessage()));
                request.setAttribute(AimsConstants.REQUEST_SHOW_BULLETIN_ERROR, new Boolean(true));
            }
            return mapping.findForward("success");
        }
        else if(request.getSession().getAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW) != null) {
            AimsLoginContent loginContent =  (AimsLoginContent) DBHelper.getInstance().load(AimsLoginContent.class, request.getSession().getAttribute(AimsConstants.SESSION_LOGIN_CONTENT_TO_SHOW).toString());
            Boolean lastBulletinAcknowledged = (Boolean)request.getAttribute("lastBulletinAcknowledged");
            //if bulletin was set from acknowledged actions of bulletin, then skip showing error
            if(lastBulletinAcknowledged == null || !(lastBulletinAcknowledged.booleanValue()))
            {
                request.setAttribute(AimsConstants.REQUEST_SHOW_LOGIN_CONTENT_ERROR, new Boolean(true));
                request.setAttribute(AimsConstants.REQUEST_LOGIN_CONTENT_ERROR_MESSAGE, StringFuncs.NullValueReplacement(loginContent.getErrorMessage()));
            }
            return new ActionForward("loginContentPage", loginContent.getLoginContentAction(), false);
        }

        if ("Y".equalsIgnoreCase((String) request.getSession().getAttribute(AimsConstants.AIMS_ONLY_PROV_APPS)))
        {
            return mapping.findForward("prov_apps");
        }

        if(request.getSession().getAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN)!=null) {
            //remove SESSION_SHOW_PAGE_AFTER_LOGIN after successfull login
            //redirect control to page saved in session attribute SESSION_SHOW_PAGE_AFTER_LOGIN (which was set from LoginFilter for secured pages)
            String pageAfterLogin = (String) request.getSession().getAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN);
            log.debug("userHomeAction: found "+AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN+"= "+pageAfterLogin);
			request.getSession().removeAttribute(AimsConstants.SESSION_SHOW_PAGE_AFTER_LOGIN);
            response.sendRedirect(pageAfterLogin);
        }

        return mapping.findForward("success");
    }
}
