package com.netpace.aims.controller.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.VcastApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsAppSubCategory;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.application.AimsVcastApp;
import com.netpace.aims.model.application.AimsVcastAudAge;
import com.netpace.aims.model.application.AimsVcastAudEducation;
import com.netpace.aims.model.application.AimsVcastAudGender;
import com.netpace.aims.model.application.AimsVcastAudIncome;
import com.netpace.aims.model.application.AimsVcastAudRace;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for display of update form of BREW Application.
 *
 * @struts.action path="/vcastApplicationSetup"
 *                name="VcastApplicationUpdateForm"
 *                scope="request"
 *                input="/applicationsViewDelete.do"
                  validate="false"
 * @struts.action-forward name="update" path="/application/vcastApplicationUpdate.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="vcastView" path="/application/vcastApplicationView.jsp"
 * @struts.action-forward name="page4View" path="/application/vcastJournalView.jsp"
 * @author Fawad Sikandar
 */
public class VcastApplicationSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(VcastApplicationSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String forward = "view";
        String taskname = "";
        String viewPageToView = "";
        Object o_param;
        AimsApp aimsApp = null;
        AimsVcastApp aimsVcastApp = null;
        HashMap appVcast = null;
        Collection journalEntries = null;

        o_param = request.getParameter("task");
        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        o_param = request.getParameter("viewPageToView");
        if (o_param != null)
            viewPageToView = request.getParameter("viewPageToView");

        //On Hold Functionality
        if (currUserType.equals(AimsConstants.VZW_USERTYPE))
        {
            if (taskname.equalsIgnoreCase("onHold"))
            {
                try
                {
                    AimsApplicationsManager.updateOnHold(new Long(request.getParameter("appsId")), request.getParameter("onHold"), currUser);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    throw ex;
                }
                return mapping.getInputForward();
            }
        }

        //Getting Application Information And Journal Information
        if (!(taskname.equalsIgnoreCase("create")))
        {
            try
            {
                appVcast = VcastApplicationManager.getVcastApp(new Long(request.getParameter("appsId")), currentUserAllianceId);
                journalEntries = VcastApplicationManager.getJournalEntries(new Long(request.getParameter("appsId")), currentUserAllianceId);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }

            aimsApp = (AimsApp) appVcast.get("AimsApp");
            aimsVcastApp = (AimsVcastApp) appVcast.get("AimsVcastApp");
        }

        //CHECK ACCESS
        if (!(ApplicationHelper.checkAccess(request, taskname, AimsPrivilegesConstants.MANAGE_VCAST_APPS)))
            throw new AimsSecurityException();

        if (taskname.equalsIgnoreCase("edit"))
            if (!(VcastApplicationHelper.checkEditAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())))
                throw new AimsSecurityException();

        if (taskname.equalsIgnoreCase("delete"))
        {
            if (!(VcastApplicationHelper.checkDeleteAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())))
                throw new AimsSecurityException();
            else
            {
                try
                {
                    int delCount = AimsApplicationsManager.deleteApp(new Long(request.getParameter("appsId")), currUser);
                }
                catch (AimsException ae)
                {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                }
                return mapping.getInputForward();
            }
        }
        //END OF CHECK ACCESS

        //Get Form
        VcastApplicationUpdateForm vcastAppUpdForm = (VcastApplicationUpdateForm) form;

        if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
            vcastAppUpdForm.setAppOwnerAllianceId(currentUserAllianceId);
        else
            vcastAppUpdForm.setAppOwnerAllianceId(aimsApp.getAimsAllianceId());

        vcastAppUpdForm.setAllContacts(AimsApplicationsManager.getContacts(vcastAppUpdForm.getAppOwnerAllianceId()));

        if (taskname.equalsIgnoreCase("clone"))
            vcastAppUpdForm.setTask("create");
        else
            vcastAppUpdForm.setTask(taskname);

        vcastAppUpdForm.setCurrentPage("page1");
        vcastAppUpdForm.setOrignalTask(vcastAppUpdForm.getTask());

        if (taskname.equalsIgnoreCase("create"))
        {
            vcastAppUpdForm.setAppsId(new Long(0));
            vcastAppUpdForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
            vcastAppUpdForm.setLanguage(AimsConstants.VCAST_DEFAULT_ENGLISH_LANGUAGE);
            forward = "update";
        }
        else if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")) || (taskname.equalsIgnoreCase("view")))
        {
            //Setting the Forwards
            if (taskname.equalsIgnoreCase("view"))
            {
                if (viewPageToView.equals(""))
                    forward = "vcastView";
                else if (viewPageToView.equals("journal"))
                    forward = "page4View";
            }
            else if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("clone")))
                forward = "update";

            //End of Setting the Forwards

            if (taskname.equalsIgnoreCase("clone"))
            {
                aimsApp.setVersion(null);
                vcastAppUpdForm.setTitle(null);
                vcastAppUpdForm.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
            }
            else
            {
                vcastAppUpdForm.setTitle(aimsApp.getTitle());
                vcastAppUpdForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());
            }

            vcastAppUpdForm.setAppsId(aimsApp.getAppsId());
            vcastAppUpdForm.setShortDesc(aimsApp.getShortDesc());
            vcastAppUpdForm.setLongDesc(aimsApp.getLongDesc());
            vcastAppUpdForm.setSubmittedDate(Utility.convertToString(aimsApp.getSubmittedDate(), dateFormat));
            vcastAppUpdForm.setAimsContactId(aimsApp.getAimsContactId());
            
            vcastAppUpdForm.setLanguage(aimsVcastApp.getLanguage());
            vcastAppUpdForm.setUpdateFrequency(aimsVcastApp.getUpdateFrequency());
            vcastAppUpdForm.setEvaluationComments(aimsVcastApp.getEvaluationComments());

            if (aimsApp.getAimsAppSubCategoryId() != null)
            {
                AimsAppSubCategory subCategory =
                    (AimsAppSubCategory) DBHelper.getInstance().load(AimsAppSubCategory.class, aimsApp.getAimsAppSubCategoryId().toString());
                vcastAppUpdForm.setCategoryId(subCategory.getAimsAppCategoryId());
                vcastAppUpdForm.setSubCategoryId(subCategory.getSubCategoryId());
            }

            if (aimsVcastApp.getAudAges() != null)
            {
                AimsVcastAudAge aimsVcastAudAge = null;
                Vector audAgeIds = new Vector();
                for (Iterator it = aimsVcastApp.getAudAges().iterator(); it.hasNext();)
                {
                    aimsVcastAudAge = (AimsVcastAudAge) it.next();
                    audAgeIds.add(aimsVcastAudAge.getAudAgeId().toString());
                }
                vcastAppUpdForm.setListSelectedAudAges(StringFuncs.ConvertListToStringArray(audAgeIds));
            }

            if (aimsVcastApp.getAudEducations() != null)
            {
                AimsVcastAudEducation aimsVcastAudEducation = null;
                Vector audEducationIds = new Vector();
                for (Iterator it = aimsVcastApp.getAudEducations().iterator(); it.hasNext();)
                {
                    aimsVcastAudEducation = (AimsVcastAudEducation) it.next();
                    audEducationIds.add(aimsVcastAudEducation.getAudEducationId().toString());
                }
                vcastAppUpdForm.setListSelectedAudEducations(StringFuncs.ConvertListToStringArray(audEducationIds));
            }
            
            if (aimsVcastApp.getAudGenders() != null)
            {
                AimsVcastAudGender aimsVcastAudGender = null;
                Vector audGenderIds = new Vector();
                for (Iterator it = aimsVcastApp.getAudGenders().iterator(); it.hasNext();)
                {
                    aimsVcastAudGender = (AimsVcastAudGender) it.next();
                    audGenderIds.add(aimsVcastAudGender.getAudGenderId().toString());
                }
                vcastAppUpdForm.setListSelectedAudGenders(StringFuncs.ConvertListToStringArray(audGenderIds));
            }
            
            if (aimsVcastApp.getAudIncomes() != null)
            {
                AimsVcastAudIncome aimsVcastAudIncome = null;
                Vector audIncomeIds = new Vector();
                for (Iterator it = aimsVcastApp.getAudIncomes().iterator(); it.hasNext();)
                {
                    aimsVcastAudIncome = (AimsVcastAudIncome) it.next();
                    audIncomeIds.add(aimsVcastAudIncome.getAudIncomeId().toString());
                }
                vcastAppUpdForm.setListSelectedAudIncomes(StringFuncs.ConvertListToStringArray(audIncomeIds));
            }
            
            if (aimsVcastApp.getAudRaces() != null)
            {
                AimsVcastAudRace aimsVcastAudRace = null;
                Vector audRaceIds = new Vector();
                for (Iterator it = aimsVcastApp.getAudRaces().iterator(); it.hasNext();)
                {
                    aimsVcastAudRace = (AimsVcastAudRace) it.next();
                    audRaceIds.add(aimsVcastAudRace.getAudRaceId().toString());
                }
                vcastAppUpdForm.setListSelectedAudRaces(StringFuncs.ConvertListToStringArray(audRaceIds));
            }


            //Set File Names
            vcastAppUpdForm.setSampleClip1FileName(aimsVcastApp.getSampleClip1FileName());
            vcastAppUpdForm.setSampleClip2FileName(aimsVcastApp.getSampleClip2FileName());
            vcastAppUpdForm.setSampleClip3FileName(aimsVcastApp.getSampleClip3FileName());

            //Set Temp File Ids to Zero
            vcastAppUpdForm.setSampleClip1TempFileId(new Long(0));
            vcastAppUpdForm.setSampleClip2TempFileId(new Long(0));
            vcastAppUpdForm.setSampleClip3TempFileId(new Long(0));

            vcastAppUpdForm.setJournalCombinedText(VcastApplicationHelper.getFormattedJournalEntries(journalEntries));

        }

        //Setting the Alliance(Content Provider) Name for Page Header
        AimsAllianc aimsAllianceOfApplication = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, vcastAppUpdForm.getAppOwnerAllianceId().toString());
        vcastAppUpdForm.setAllianceName(aimsAllianceOfApplication.getCompanyName());

        //Setting the Application Status for Page Header
        if ((vcastAppUpdForm.getAppsId() != null) && (vcastAppUpdForm.getAppsId().longValue() != 0))
        {
            AimsLifecyclePhase aimsPhaseOfApplication =
                (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, vcastAppUpdForm.getAimsLifecyclePhaseId().toString());
            vcastAppUpdForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());
        }
        else
        {
            vcastAppUpdForm.setApplicationStatus("NEW");
        }

        return mapping.findForward(forward);
    }

}
