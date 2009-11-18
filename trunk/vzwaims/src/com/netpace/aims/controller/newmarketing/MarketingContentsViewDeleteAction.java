package com.netpace.aims.controller.newmarketing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.ManageApplicationsConstants;
import com.netpace.aims.bo.newmarketing.MarketingContentManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/mktContentsViewDelete"
 *                name="FilterForm"
 *                scope="request"
 *				  validate="false"
 *                input="/newmarketing/mktContentsViewDelete.jsp"
 * @struts.action-forward name="view" path="/newmarketing/mktContentsViewDelete.jsp"
 * @author Adnan Makda.
 */
public class MarketingContentsViewDeleteAction extends BaseAction
{

    private HashMap fieldMapper = new HashMap();
    private HashMap privilegeMapper = new HashMap();

    static Logger log = Logger.getLogger(MarketingContentsViewDeleteAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        //Privileges Being Checked Below

        String dateFormat = this.getResources(request).getMessage("date.format");

        fillFieldMapper(request);
        fillPrivilegeMapper(request);

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);

        String forward = "view";
        int PAGE_LENGTH = user.getRowsLength().intValue(); //10;
        int pageNo = 1;
        String taskname;

        Long userId = user.getUserId();
        Long allianceId = user.getAimsAllianc();
        String userType = user.getUserType();

        Object o_param;
        boolean errorFound = false;

        o_param = request.getParameter("task");
        if (o_param != null)
            taskname = request.getParameter("task");
        else
            taskname = "view";

        //Get Form
        FilterForm filterForm = (FilterForm) form;

        if (taskname.equalsIgnoreCase("filterview"))
        {
            filterForm = (FilterForm) form;
            request.setAttribute("filter_text", filterForm.getFilterText());
            request.setAttribute("filter_field", filterForm.getFilterField());
            request.setAttribute("sort_field", filterForm.getSortField());
            request.setAttribute("sort_order", filterForm.getSortOrder());
            //request.setAttribute("type_of_view", filterForm.getTypeOfView());
        }

        /*
        o_param = request.getParameter("app_type");
        if (o_param != null)
        {
            filterForm.setTypeOfApplicationsToView((String) o_param);
            request.setAttribute("app_type", (String) o_param);
        }
        else
        {
            if (request.getAttribute("app_type") == null)
            {
                log.debug("\nIn Application View Delete the app_type is null\n");
                filterForm.setTypeOfApplicationsToView(ManageApplicationsConstants.APP_TYPE_MY_APPS);
                request.setAttribute("app_type", ManageApplicationsConstants.APP_TYPE_MY_APPS);
            }
        }
        */

        o_param = request.getParameter("sort_field");
        if (o_param != null)
        {
            filterForm.setSortField((String) o_param);
            request.setAttribute("sort_field", (String) o_param);
        }

        o_param = request.getParameter("sort_order");
        if (o_param != null)
        {
            filterForm.setSortOrder((String) o_param);
            request.setAttribute("sort_order", (String) o_param);
        }

        o_param = request.getParameter("filter_text");
        if (o_param != null)
        {
            request.setAttribute("filter_text", (String) o_param);
            filterForm.setFilterText((String) o_param);
        }

        o_param = request.getParameter("filter_field");
        if (o_param != null)
        {
            request.setAttribute("filter_field", (String) o_param);
            filterForm.setFilterField((String) o_param);
        }

        o_param = request.getParameter("page_id");
        if (o_param != null)
        {
            pageNo = Integer.parseInt((String) o_param);
        }

        //This LINE WILL CHECK FOR ACCESS PERMISSIONS TO THIS FUNCTIONALITY
        if (!(MarketingContentHelper.checkAccess(request, "view", AimsPrivilegesConstants.CP_MARKETING_CONTENT)))
            throw new AimsSecurityException();

        StringBuffer mainQuery = new StringBuffer();

        //Getting Core Query (Search (From Session) OR Normal)
        StringBuffer coreQuery = MarketingContentManager.getNormalQueryForGetApps();

        mainQuery
            .append(MarketingContentManager.getQueryForColumnsToDisplay())
            .append(coreQuery)
            .append(MarketingContentManager.getAllianceCheckQueryForGetApps(allianceId))
            .append(MarketingContentManager.getFilterQueryForGetApps(getValueFromHash(fieldMapper, filterForm.getFilterField()), filterForm.getFilterText()))
            .append(MarketingContentManager.getOrderByQueryForGetApps(getValueFromHash(fieldMapper, filterForm.getSortField()), filterForm.getSortOrder()));

        //      .append(ApplicationsManagerHelper.getAppTypeQueryForGetApps(filterForm.getTypeOfApplicationsToView(), userId, allianceId, contactId))

        int rows = MarketingContentManager.getPageCount(mainQuery);

        int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();
        log.debug("Max Page : " + pmax);

        if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH)))
        {
            pageNo = 1;
            pmax = 1;
            request.setAttribute("page_id", new Integer(pageNo));
            request.setAttribute("page_max", new Integer(pmax));
        }
        else
        {
            request.setAttribute("page_id", new Integer(pageNo));
            request.setAttribute("page_max", new Integer(pmax));
        }

        Collection mktContentRecords = null;
        Collection marketingContents = new ArrayList(); //Collection to set in 'Request'

        try
        {
            mktContentRecords = MarketingContentManager.getRecords(mainQuery, PAGE_LENGTH, pageNo);
        }
        catch (Exception ex)
        {}

        MarketingContentInformation mktContInfo = null;
        if (mktContentRecords != null)
        {
            Object[] appValues = null;
            for (Iterator it = mktContentRecords.iterator(); it.hasNext();)
            {
                appValues = (Object[]) it.next();
                mktContInfo = new MarketingContentInformation();
                mktContInfo.setCreativeContentId((Long) appValues[0]); // creativeContentId
                mktContInfo.setContentTitle((String) appValues[1]); // contentTitle
                mktContInfo.setContentDescription((String) appValues[2]); // contentDescription
                mktContInfo.setApplicationTitle((String) appValues[3]); // applicationTitle
                mktContInfo.setStatus((String) appValues[4]); // status
                mktContInfo.setSubmittedDate(Utility.convertToString((Date) appValues[5], dateFormat)); // submittedDate
                mktContInfo.setApprovalDate(Utility.convertToString((Date) appValues[6], dateFormat)); // approvalDate
                mktContInfo.setExpiryDate(Utility.convertToString((Date) appValues[7], dateFormat)); // expiryDate
                mktContInfo.setCompanyName((String) appValues[8]); // companyName
                mktContInfo.setAllianceId((Long) appValues[9]); // allianceId
                log.debug("\n" + mktContInfo.getCreativeContentId() + " | " + mktContInfo.getContentTitle() + " | " + mktContInfo.getContentDescription());
                log.debug(mktContInfo.getApplicationTitle() + " | " + mktContInfo.getStatus() + " | " + mktContInfo.getSubmittedDate());
                log.debug(
                    mktContInfo.getExpiryDate()
                        + " | "
                        + mktContInfo.getApprovalDate()
                        + " | "
                        + mktContInfo.getCompanyName()
                        + " | "
                        + mktContInfo.getAllianceId());

                mktContInfo.setUrlSetupAction("/aims/mktContentSetup.do");

                //Show Create Button?
                //mktContInfo.setAllowCreate(ApplicationHelper.checkPlatformAccess(request, "create", mktContInfo.getPlatformId()));

                //Show Edit Button?
                mktContInfo.setAllowEdit(MarketingContentHelper.checkEditAccess(userType, mktContInfo.getStatus()));

                //Show Delete Button?
                mktContInfo.setAllowDelete(MarketingContentHelper.checkDeleteAccess(userType, mktContInfo.getStatus()));

                log.debug("Allow Create/Edit/Delete: " + mktContInfo.isAllowCreate() + " | " + mktContInfo.isAllowEdit() + " | " + mktContInfo.isAllowDelete());

                //Adding to	Collection
                marketingContents.add(mktContInfo);
            }
        }

        //request.setAttribute("AimsApplicationsInformation", marketingContents);
        request.setAttribute("MarketingContentsInformation", marketingContents);
        return mapping.findForward(forward);
    }

    /**
     *	This function fills the Hash, mapping 'Resource Field Constant' with 'Privilege Constants'
     */
    public void fillPrivilegeMapper(HttpServletRequest request)
    {
        /*
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_ALL_APPS, AimsPrivilegesConstants.MANAGE_ALL_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_MY_APPS, AimsPrivilegesConstants.MANAGE_MY_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_APPS, AimsPrivilegesConstants.MANAGE_NEW_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_BREW_APPS, AimsPrivilegesConstants.MANAGE_NEW_BREW_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_BREW_CONCEPTS, AimsPrivilegesConstants.MANAGE_NEW_BREW_CONCEPTS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_ENTERPRISE_APPS, AimsPrivilegesConstants.MANAGE_NEW_ENTERPRISE_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_MMS_APPS, AimsPrivilegesConstants.MANAGE_NEW_MMS_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_SMS_APPS, AimsPrivilegesConstants.MANAGE_NEW_SMS_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_NEW_WAP_APPS, AimsPrivilegesConstants.MANAGE_NEW_WAP_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_SAVED_APPS, AimsPrivilegesConstants.MANAGE_SAVED_APPLICATIONS);
        privilegeMapper.put(ManageApplicationsConstants.APP_TYPE_TEST_APPS, AimsPrivilegesConstants.MANAGE_TEST_APPLICATIONS);
        */
    }

    /**
    *	This function fills the Hash, mapping 'Resource Field Constant' with 'DB Field Constant'
    */
    public void fillFieldMapper(HttpServletRequest request)
    {
        //fieldMapper.put(getResString("ManageApplicationForm.field.companyName", request), ApplicationsManagerHelper.FIELD_COMPANY_NAME);

        fieldMapper.put("content_title", MarketingContentManager.FIELD_CONTENT_TITLE);
        fieldMapper.put("application_title", MarketingContentManager.FIELD_APPLICATION_TITLE);
        fieldMapper.put("status", MarketingContentManager.FIELD_STATUS);
        fieldMapper.put("submitted_date", MarketingContentManager.FIELD_SUBMITTED_DATE);
        fieldMapper.put("approval_date", MarketingContentManager.FIELD_APPROVAL_DATE);
        fieldMapper.put("expiry_date", MarketingContentManager.FIELD_EXPIRY_DATE);
        fieldMapper.put("company_name", MarketingContentManager.FIELD_COMPANY_NAME);
    }

    /**
    *	This function gets resource string.
    */
    public String getResString(String key, HttpServletRequest request)
    {
        return this.getResources(request, ManageApplicationsConstants.RES_BUNDLE).getMessage(key);
    }

    /**
    *	This function gets value from HashMap
    */
    public String getValueFromHash(HashMap collection, String key)
    {
        if (collection.containsKey(key))
            return (String) collection.get(key);
        else
            return "";
    }

}
