package com.netpace.aims.controller.application;

import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.bo.application.WapApplicationManager;
import com.netpace.aims.bo.application.ApplicationInformation;
import com.netpace.aims.bo.application.ManageApplicationsConstants;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 *
 * @struts.action path="/wapAppImageUploadList"
 *                name="WapAppImageUploadListFilterForm"
 *                scope="request"
 *			      validate="false"
 *                input="/wapAppImageUploadList.do"
 * @struts.action-forward name="listView" path="/application/wapUploadImagesAppList.jsp"
 * @author Sajjad Raza
 */
public class WapAppImageUploadListAction extends BaseAction {
    private static Logger log = Logger.getLogger(WapAppImageUploadListAction.class.getName());

    public static final String FIELD_TITLE = "application.title";
    public static final String FIELD_CREATE_DATE_NAME = "application.submittedDate";
    public static final String FIELD_PHASE_NAME = "lifecycle.phaseName";

    private HashMap fieldMapper = new HashMap();

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        fillFieldMapper();

        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);

        int PAGE_LENGTH = user.getRowsLength().intValue(); //10;
        int pageNo = 1;

        Long allianceId = user.getAimsAllianc();
        String userType = user.getUserType();

        String forward = "listView";
        String taskname = "";
        Object o_param;

        o_param = request.getParameter("task");

        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
        {
            taskname = "view";
        }

        o_param = request.getParameter("page_id");
        if (o_param != null)
        {
            try
            {
                pageNo = Integer.parseInt((String) o_param);
            }
            catch (NumberFormatException ex)
            {
                //Ignore
            }
        }
        WapAppImageUploadListFilterForm imgUploadListForm = (WapAppImageUploadListFilterForm)form;

        o_param = request.getParameter("sort_field");
        if (o_param != null)
        {
            imgUploadListForm.setSortField((String) o_param);
            request.setAttribute("sort_field", (String) o_param);
        }
        o_param = request.getParameter("sort_order");
        if (o_param != null)
        {
            imgUploadListForm.setSortOrder((String) o_param);
            request.setAttribute("sort_order", (String) o_param);
        }
        o_param = request.getParameter("page_id");
        if (o_param != null)
        {
            try
            {
                pageNo = Integer.parseInt((String) o_param);
            }
            catch (NumberFormatException ex)
            {
                //Ignore
            }
        }

        if (taskname.equalsIgnoreCase("view"))
        {
            //list
            Collection aimsApps = null;
            Collection allianceApplications = new ArrayList(); //Collection to set in 'Request'

            //get complete query
            StringBuffer mainQuery = WapApplicationManager.getMainQueryForImageUploadApps(allianceId,
                                                getValueFromHash(fieldMapper, imgUploadListForm.getSortField()),
                                                imgUploadListForm.getSortOrder());

            int rows = WapApplicationManager.getPageCountOfImageUploadApps(mainQuery);
            System.out.println("Total Records from query is: " + rows + ".");
            int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();
            log.debug("Max Page : " + pmax);

            if (pageNo < 1)
                pageNo = 1;
            else if (pageNo > pmax)
                pageNo = pmax;

            request.setAttribute("page_id", new Integer(pageNo));
            request.setAttribute("page_max", new Integer(pmax));

            try
            {
                aimsApps = WapApplicationManager.getImageUploadApps(mainQuery, PAGE_LENGTH, pageNo);
            }
            catch (Exception ex)
            {}

            ApplicationInformation appInfo = null;
            if (aimsApps != null)
            {
                Object[] appValues = null;
                for (Iterator it = aimsApps.iterator(); it.hasNext();)
                {
                    appValues = (Object[]) it.next();
                    appInfo = new ApplicationInformation();
                    appInfo.setAppsId((Long) appValues[0]); // appsId
                    appInfo.setCompanyName((String) appValues[1]); // companyName
                    appInfo.setTitle((String) appValues[2]); // title
                    appInfo.setCreatedDate((Date) appValues[3]); // createdDate
                    appInfo.setPhaseId((Long) appValues[4]); // phaseId
                    appInfo.setPhaseName((String) appValues[5]); // phaseName
                    appInfo.setAllianceId((Long) appValues[6]); // allianceId
                    appInfo.setUrlSetupAction(ManageApplicationsConstants.WAP_APPLICATION_SETUP_URL);

                    //Adding to Collection
                    allianceApplications.add(appInfo);
                }
            }
            request.setAttribute("AllianceApplicationsInformation", allianceApplications);
            forward = "listView";
        }

        return mapping.findForward(forward);
    }

    /**
    *   This function fills the Hash, mapping 'Resource Field Constant' with 'DB Field Constant'
    */
    public void fillFieldMapper()
    {
        fieldMapper.put("4", FIELD_TITLE);
        fieldMapper.put("7", FIELD_CREATE_DATE_NAME);
        fieldMapper.put("9", FIELD_PHASE_NAME);
    }

    /**
    *   This function gets value from HashMap
    */
    public String getValueFromHash(HashMap collection, String key)
    {
        if (collection.containsKey(key))
            return (String) collection.get(key);
        else
            return "";
    }
}
