package com.netpace.aims.controller.application;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.application.AimsApplicationsManager;
import com.netpace.aims.bo.application.VcastApplicationManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.application.AimsApp;
import com.netpace.aims.model.application.AimsLifecyclePhase;
import com.netpace.aims.model.application.AimsVcastApp;
import com.netpace.aims.model.application.AimsVcastAudAge;
import com.netpace.aims.model.application.AimsVcastAudEducation;
import com.netpace.aims.model.application.AimsVcastAudGender;
import com.netpace.aims.model.application.AimsVcastAudIncome;
import com.netpace.aims.model.application.AimsVcastAudRace;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsContact;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.DBErrorFinder;

/**
 * This class takes care of action for display of update form of BREW Application.
 *
 * @author Fawad Sikandar
 * @struts.action path="/vcastApplicationUpdate"
 * name="VcastApplicationUpdateForm"
 * scope="request"
 * input="/application/vcastApplicationUpdate.jsp"
 * validate="true"
 * @struts.action-forward name="page1" path="/application/vcastApplicationUpdate.jsp"
 * @struts.action-forward name="page2" path="/application/vcastApplicationUpdate2.jsp"
 * @struts.action-forward name="page4" path="/application/vcastJournal.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @struts.action-forward name="vcastView" path="/application/vcastApplicationView.jsp"
 * @struts.action-forward name="page4View" path="/application/vcastJournalView.jsp"
 */
public class VcastApplicationUpdateAction extends BaseAction {

    static Logger log = Logger.getLogger(VcastApplicationUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        Long clonedFromAppId = null;
        Long oldStatus = new Long(0);
        String forward = "view";
        String taskname = "";
        String submitType = "";

        Object o_param = request.getParameter("task");
        if (o_param != null) {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        } else
            return mapping.findForward(forward);

        o_param = request.getParameter("appSubmitType");
        if (o_param != null)
            submitType = request.getParameter("appSubmitType");

        log.debug("TASK is:  " + taskname);

        AimsApp aimsApp = null;
        AimsVcastApp aimsVcastApp = null;
        AimsContact aimsContact = null;
        HashMap appVcast = null;

        //Get Form
        VcastApplicationUpdateForm vcastAppUpdForm = (VcastApplicationUpdateForm) form;

        /*initialy it was based on create and update parameters now it check to see if */
        if ( vcastAppUpdForm.getAppsId() != null && vcastAppUpdForm.getAppsId().longValue() != 0 ) {
            try {
                appVcast = VcastApplicationManager.getVcastApp(vcastAppUpdForm.getAppsId(), currentUserAllianceId);
            }
            catch (AimsException ae) {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }
            aimsApp = (AimsApp) appVcast.get("AimsApp");
            aimsVcastApp = (AimsVcastApp) appVcast.get("AimsVcastApp");
            oldStatus = aimsApp.getAimsLifecyclePhaseId();
        } else {
            aimsApp = new AimsApp();
            aimsVcastApp = new AimsVcastApp();
        }

        //CHECK ACCESS
        if (!(ApplicationHelper.checkPlatformAccess(request, vcastAppUpdForm.getOrignalTask(), AimsConstants.VCAST_PLATFORM_ID)))
            throw new AimsSecurityException();

        if (taskname.equalsIgnoreCase("edit"))
            if (!(VcastApplicationHelper.checkEditAccess(currUserType, aimsApp.getAimsLifecyclePhaseId())))
                throw new AimsSecurityException();
        //END OF CHECK ACCESS

        if ((taskname.equalsIgnoreCase("page1"))
                || (taskname.equalsIgnoreCase("page2"))
                || (taskname.equalsIgnoreCase("page4"))
                || (taskname.equalsIgnoreCase("submitpage4"))
                || (taskname.equalsIgnoreCase("submitpage4View"))) {
            if (taskname.equalsIgnoreCase("page1"))
                forward = "page1";
            else if (taskname.equalsIgnoreCase("page2"))
                forward = "page2";
            else if (taskname.equalsIgnoreCase("page4"))
                forward = "page4";
            else if (taskname.equalsIgnoreCase("submitpage4"))
                forward = "page4";
            else if (taskname.equalsIgnoreCase("submitpage4View"))
                forward = "page4View";

            if (taskname.equalsIgnoreCase("submitpage4"))
                vcastAppUpdForm.setCurrentPage("page4");
            else
                vcastAppUpdForm.setCurrentPage(taskname);

            vcastAppUpdForm.setTask(vcastAppUpdForm.getOrignalTask());

            if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {
                if ((taskname.equalsIgnoreCase("submitpage4")) || (taskname.equalsIgnoreCase("submitpage4View"))) {
                    VcastApplicationManager.saveJournalEntry(
                            vcastAppUpdForm.getAppsId(),
                            vcastAppUpdForm.getJournalText(),
                            vcastAppUpdForm.getJournalType(),
                            currUser);
                    Collection journalEntries = VcastApplicationManager.getJournalEntries(new Long(request.getParameter("appsId")), currentUserAllianceId);
                    vcastAppUpdForm.setJournalCombinedText(VcastApplicationHelper.getFormattedJournalEntries(journalEntries));
                }
            }
        }

        if ((submitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                || (submitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                || (submitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
                || (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
                || (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
                || (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM))) {
            if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit"))) {
                if (taskname.equalsIgnoreCase("create")) {
                    aimsApp.setAimsAllianceId(currentUserAllianceId);
                    aimsApp.setAimsUserAppCreatedById(currUserId);
                    aimsApp.setIfOnHold("N");
                    aimsApp.setCreatedBy(currUser);
                    aimsApp.setCreatedDate(new Date());
                }

                aimsApp.setLastUpdatedBy(currUser);
                aimsApp.setLastUpdatedDate(new Date());
                aimsApp.setAimsPlatformId(AimsConstants.VCAST_PLATFORM_ID);

                aimsApp.setTitle(vcastAppUpdForm.getTitle());
                aimsApp.setShortDesc(vcastAppUpdForm.getShortDesc());
                aimsApp.setLongDesc(vcastAppUpdForm.getLongDesc());
                aimsVcastApp.setLanguage(vcastAppUpdForm.getLanguage());

                //Language
                if ((vcastAppUpdForm.getLanguage() != null) && (vcastAppUpdForm.getLanguage().longValue() != 0))
                    aimsVcastApp.setLanguage(vcastAppUpdForm.getLanguage());
                else
                    aimsVcastApp.setLanguage(null);

                //UpdateFrequency
                if ((vcastAppUpdForm.getUpdateFrequency() != null) && (vcastAppUpdForm.getUpdateFrequency().longValue() != 0))
                    aimsVcastApp.setUpdateFrequency(vcastAppUpdForm.getUpdateFrequency());
                else
                    aimsVcastApp.setUpdateFrequency(null);

                //SubCategory
                if ((vcastAppUpdForm.getSubCategoryId() != null) && (vcastAppUpdForm.getSubCategoryId().longValue() != 0))
                    aimsApp.setAimsAppSubCategoryId(vcastAppUpdForm.getSubCategoryId());
                else
                    aimsApp.setAimsAppSubCategoryId(null);

                //Target Audience Age Group
                Set audAgeSet = new HashSet();
                if (vcastAppUpdForm.getListSelectedAudAges() != null) {
                    for (int i = 0; i < vcastAppUpdForm.getListSelectedAudAges().length; i++) {
                        audAgeSet.add((AimsVcastAudAge) DBHelper.getInstance().load(AimsVcastAudAge.class, vcastAppUpdForm.getListSelectedAudAges()[i]));
                    }
                }
                aimsVcastApp.setAudAges(audAgeSet);

                //Target Audience Eduaction
                Set audEducationSet = new HashSet();
                if (vcastAppUpdForm.getListSelectedAudEducations() != null) {
                    for (int i = 0; i < vcastAppUpdForm.getListSelectedAudEducations().length; i++) {
                        audEducationSet.add(
                                (AimsVcastAudEducation) DBHelper.getInstance().load(
                                        AimsVcastAudEducation.class,
                                        vcastAppUpdForm.getListSelectedAudEducations()[i]));
                    }
                }
                aimsVcastApp.setAudEducations(audEducationSet);

                //Target Audience Gender
                Set audGenderSet = new HashSet();
                if (vcastAppUpdForm.getListSelectedAudGenders() != null) {
                    for (int i = 0; i < vcastAppUpdForm.getListSelectedAudGenders().length; i++) {
                        audGenderSet.add(
                                (AimsVcastAudGender) DBHelper.getInstance().load(AimsVcastAudGender.class, vcastAppUpdForm.getListSelectedAudGenders()[i]));
                    }
                }
                aimsVcastApp.setAudGenders(audGenderSet);

                //Target Audience Household Income
                Set audIncomeSet = new HashSet();
                if (vcastAppUpdForm.getListSelectedAudIncomes() != null) {
                    for (int i = 0; i < vcastAppUpdForm.getListSelectedAudIncomes().length; i++) {
                        audIncomeSet.add(
                                (AimsVcastAudIncome) DBHelper.getInstance().load(AimsVcastAudIncome.class, vcastAppUpdForm.getListSelectedAudIncomes()[i]));
                    }
                }
                aimsVcastApp.setAudIncomes(audIncomeSet);

                //Target Audience Race
                Set audRaceSet = new HashSet();
                if (vcastAppUpdForm.getListSelectedAudRaces() != null) {
                    for (int i = 0; i < vcastAppUpdForm.getListSelectedAudRaces().length; i++) {
                        audRaceSet.add((AimsVcastAudRace) DBHelper.getInstance().load(AimsVcastAudRace.class, vcastAppUpdForm.getListSelectedAudRaces()[i]));
                    }
                }
                aimsVcastApp.setAudRaces(audRaceSet);

                //The following properties can only be updated by an Alliance user and not the Verizon user
                if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE)) {
                    //Start of check to see if alliance has been accepted
                    Object[] userValues = null;
                    String allianceStatus = null;
                    Collection AimsAlliance = null;

                    try {
                        AimsAlliance = AllianceManager.getAllianceDetails(aimsApp.getAimsAllianceId(), "");
                    }
                    catch (Exception ex) {
                        AimsException aimsException = new AimsException("Error");
                        aimsException.addException(new RecordNotFoundException("error.application.alliance.not.accepted"));
                        throw aimsException;
                    }

                    for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();) {
                        userValues = (Object[]) iter.next();
                        allianceStatus = (String) userValues[3];
                    }

                    if (!(vcastAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))) {
                        if (!(allianceStatus.equals(AimsConstants.ALLIANCE_STATUS_ACCEPTED))) {
                            AimsException aimsException = new AimsException("Error");
                            aimsException.addException(new RecordNotFoundException("error.application.alliance.not.accepted"));
                            saveErrors(request, DBErrorFinder.populateActionErrors(aimsException));
                            return mapping.getInputForward();
                        }
                    }
                    //End of check to see if alliance has been accepted

                    //Contact
                    if ((vcastAppUpdForm.getAimsContactId() != null) && (vcastAppUpdForm.getAimsContactId().longValue() != 0))
                        aimsApp.setAimsContactId(vcastAppUpdForm.getAimsContactId());
                    else
                    if ((vcastAppUpdForm.getAimsContactId() != null) && (vcastAppUpdForm.getAimsContactId().longValue() == 0)) {
                        aimsApp.setAimsContactId(null);
                        aimsContact = new AimsContact();
                        aimsContact.setFirstName(vcastAppUpdForm.getContactFirstName());
                        aimsContact.setLastName(vcastAppUpdForm.getContactLastName());
                        aimsContact.setEmailAddress(vcastAppUpdForm.getContactEmail());
                        aimsContact.setTitle(vcastAppUpdForm.getContactTitle());
                        aimsContact.setPhone(vcastAppUpdForm.getContactPhone());
                        aimsContact.setMobile(vcastAppUpdForm.getContactMobile());
                        aimsContact.setType(AimsConstants.CONTACT_TYPE_ALLIANCE_CONTACT);
                        aimsContact.setCreatedBy(currUser);
                        aimsContact.setCreatedDate(new Date());
                        aimsContact.setLastUpdatedBy(currUser);
                        aimsContact.setLastUpdatedDate(new Date());
                    } else
                        aimsApp.setAimsContactId(null);

                }

                //The following properties can only be updated by the Verizon user                
                if (currUserType.equals(AimsConstants.VZW_USERTYPE)) {
                    aimsVcastApp.setEvaluationComments(vcastAppUpdForm.getEvaluationComments());
                }

                //START OF CHANGING/UPDATING THE PHASE
                if (vcastAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.SAVED_ID);
                else if (vcastAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM)) {
                    aimsApp.setSubmittedDate(new Date());
                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.SUBMISSION_ID);
                } else if (vcastAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.ACCEPTANCE_ID);
                else if (vcastAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM))
                    aimsApp.setAimsLifecyclePhaseId(AimsConstants.REJECTED_ID);

                else if (
                        (vcastAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
                                || (vcastAppUpdForm.getAppSubmitType().equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))) {
                }
                //END OF CHANGING/UPDATING THE PHASE

                //Check if the app is being cloned
                if ((vcastAppUpdForm.getAppsId().longValue() != 0) && (aimsApp.getAppsId() == null))
                    clonedFromAppId = vcastAppUpdForm.getAppsId();

                try {
                    VcastApplicationManager.saveOrUpdateVcastApplication(
                            aimsApp,
                            aimsVcastApp,
                            aimsContact,
                            currUser,
                            currUserType,
                            vcastAppUpdForm.getSampleClip1TempFileId(),
                            vcastAppUpdForm.getSampleClip2TempFileId(),
                            vcastAppUpdForm.getSampleClip3TempFileId(),
                            clonedFromAppId,
                            dateFormat);
                }
                catch (AimsException ae) {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                    return mapping.getInputForward();
                }

                //POST UPDATE TASKS

                vcastAppUpdForm.setAppsId(aimsApp.getAppsId());
                vcastAppUpdForm.setOrignalTask("edit");
                vcastAppUpdForm.setAimsLifecyclePhaseId(aimsApp.getAimsLifecyclePhaseId());
                Long newStatus = aimsApp.getAimsLifecyclePhaseId();

                Collection journalEntries = null;
                try {
                    journalEntries = AimsApplicationsManager.getJournalEntries(aimsApp.getAppsId(), currentUserAllianceId);
                }
                catch (Exception ex) {
                    journalEntries = null;
                    log.debug("\n\nError in getting Journal Entries\n\n");
                }

                vcastAppUpdForm.setJournalCombinedText(VcastApplicationHelper.getFormattedJournalEntries(journalEntries));

                //Setting the Application Status for Page Header
                AimsLifecyclePhase aimsPhaseOfApplication =
                        (AimsLifecyclePhase) DBHelper.getInstance().load(AimsLifecyclePhase.class, vcastAppUpdForm.getAimsLifecyclePhaseId().toString());
                vcastAppUpdForm.setApplicationStatus(aimsPhaseOfApplication.getPhaseName());

                //Set Temp File Ids to Zero
                vcastAppUpdForm.setSampleClip1TempFileId(new Long(0));
                vcastAppUpdForm.setSampleClip2TempFileId(new Long(0));
                vcastAppUpdForm.setSampleClip3TempFileId(new Long(0));
                //End Of Set Temp File Ids to Zero

                if (((oldStatus != null) && (oldStatus.longValue() != newStatus.longValue()))
                        || ((oldStatus == null) && (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue()))) {
                    AimsEventLite aimsEvent = null;
                    if (newStatus.longValue() == AimsConstants.SUBMISSION_ID.longValue())
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VCAST_APP_STATUS_SUBMITTED);
                    if (newStatus.longValue() == AimsConstants.ACCEPTANCE_ID.longValue())
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VCAST_APP_STATUS_ACCEPTED);
                    if (newStatus.longValue() == AimsConstants.REJECTED_ID.longValue())
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_VCAST_APP_STATUS_REJECTED);

                    if (aimsEvent != null) {
                        AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsApp.getAimsAllianceId().toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_APPS_ID, aimsApp.getAppsId().toString());
                        AimsAllianc aimsAllianceOfApplication =
                                (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsApp.getAimsAllianceId().toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfApplication.getCompanyName());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_APP_TITLE, aimsApp.getTitle());
                        aimsEvent.raiseEvent(aimsEventObject);
                    }
                }

                saveMessages(request, VcastApplicationHelper.getMessagesOnUpdate(vcastAppUpdForm.getAppSubmitType()));
                forward = vcastAppUpdForm.getCurrentPage();
            }
        }

        return mapping.findForward(forward);
    }
}
