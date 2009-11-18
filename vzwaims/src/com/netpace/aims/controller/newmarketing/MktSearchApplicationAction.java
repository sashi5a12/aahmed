package com.netpace.aims.controller.newmarketing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.newmarketing.ApplicationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * @struts.action path="/mktSearchApplication"                
 *                scope="request" 
 *                input="/marketing/applicationSearchOpt.jsp"               
 *                name="MktAppSearchForm"
 *                validate="false"
 * @struts.action-forward name="list" path="/marketing/searchedAppList.jsp"
 * @author Ahson Imtiaz
 */
public class MktSearchApplicationAction extends BaseAction
{

    static Logger log = Logger.getLogger(MktSearchApplicationAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        int iPageLength = user.getRowsLength().intValue();
        StringBuffer sbFilter = new StringBuffer();

        MktAppSearchForm frm = (MktAppSearchForm) form;
        if (frm.getTask() != null && (frm.getTask().equals("search") || frm.getTask().equals("next") || frm.getTask().equals("previous")))
        {

            Integer pageNo = frm.getPageNo();

            if (pageNo == null || frm.getTask().equals("search"))
                pageNo = new Integer(1);

            if (frm.getTask().equals("next"))
            {
                pageNo = new Integer(pageNo.intValue() + 1);
            }
            else if (frm.getTask().equals("previous"))
            {
                pageNo = new Integer(pageNo.intValue() - 1);
            }

            frm.setPageNo(pageNo);

            // Application alias app, brew application alias bapp, alliance alias alliance, deck alias deck
            // View AimsBrewApp, AimsApp, AimsDeck, AimsAllianc for field level details.

            if (frm.getCompanyName() != null && frm.getCompanyName().length() > 0)
                sbFilter.append(" upper(alliance.companyName) like '%").append(Utility.replaceString(frm.getCompanyName(), "'", "''").toUpperCase()).append(
                    "%' ");
            if (frm.getDeckId() != null && frm.getDeckId().intValue() != 0)
            {
                if (sbFilter.length() > 0)
                    sbFilter.append(" AND ");
                sbFilter.append(" deck.deckId =").append(frm.getDeckId());
            }
            if (frm.getSingleMultiplayer() != null && frm.getSingleMultiplayer().length() > 0)
            {
                if (sbFilter.length() > 0)
                    sbFilter.append(" AND ");
                sbFilter.append(" bapp.singleMultiPlayer ='").append(frm.getSingleMultiplayer()).append("' ");
            }
            if (frm.getAppTitle() != null && frm.getAppTitle().length() > 0)
            {
                if (sbFilter.length() > 0)
                    sbFilter.append(" AND ");
                sbFilter.append(" upper(app.title) like '%").append(Utility.replaceString(frm.getAppTitle(), "'", "''").toUpperCase()).append("%' ");
            }
            if (frm.getDeviceIds() != null && frm.getDeviceIds().length > 0)
            {
                if (sbFilter.length() > 0)
                    sbFilter.append(" AND ");
                sbFilter.append(
                    " app.appsId IN ( select bdev.brewAppsId FROM com.netpace.aims.model.application.AimsBrewAppDevice bdev WHERE bdev.aimsDevice IN (");
                for (int iCounter = 0; iCounter < frm.getDeviceIds().length; iCounter++)
                {
                    if (iCounter != 0)
                        sbFilter.append(",");
                    sbFilter.append(frm.getDeviceIds()[iCounter]);
                }
                sbFilter.append(") )");
            }

            request.setAttribute("ApplicationList", ApplicationManager.getApplications(sbFilter.toString(), iPageLength, pageNo.intValue()));
            int iTotalRecords = ApplicationManager.getPageCount(sbFilter.toString());
            int iNoOfPage = (iTotalRecords / iPageLength) + (iTotalRecords % iPageLength > 0 ? 1 : 0);
            request.setAttribute("TotalPages", new Integer(iNoOfPage));
            return mapping.findForward("list");
        }

        request.setAttribute("DecksListing", ApplicationManager.getDecks());
        request.setAttribute("DevicesListing", ApplicationManager.getDevices());
        return mapping.getInputForward();
    }
}