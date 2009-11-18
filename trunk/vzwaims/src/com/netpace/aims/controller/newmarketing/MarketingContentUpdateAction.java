package com.netpace.aims.controller.newmarketing;

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

import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.core.RecordNotFoundException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.newmarketing.MarketingContentManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.newmarketing.AimsCreativeContent;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for display of update form of BREW Application.
 *
 * @struts.action path="/mktContentUpdate"
 *                name="MarketingContentUpdateForm"
 *                scope="request"
 *                input="/newmarketing/mktContentUpdate.jsp"
 *        validate="true"
 * @struts.action-forward name="update" path="/newmarketing/mktContentUpdate.jsp"
 * @struts.action-forward name="view" path="/mktContentsViewDelete.do"
 * @author Adnan Makda
 */
public class MarketingContentUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(MarketingContentUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        //Privileges Being Checked Below        

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String oldStatus = null;
        String forward = "view";
        String taskname = "";
        String submitType = "";

        Object o_param = request.getParameter("task");
        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        o_param = request.getParameter("appSubmitType");
        if (o_param != null)
            submitType = request.getParameter("appSubmitType");

        AimsCreativeContent aimsCreativeContent = null;
        HashMap mktContent = null;

        //Get Form
        MarketingContentUpdateForm mktContentUpdForm = (MarketingContentUpdateForm) form;

        //////////////
        //CHECK ACCESS
        if ((taskname.equalsIgnoreCase("create")))
        {
            if (!(MarketingContentHelper.checkAccess(request, taskname, AimsPrivilegesConstants.SUBMIT_MARKETING_CONTENT)))
                throw new AimsSecurityException();
        }
        else
        {
            if (!(MarketingContentHelper.checkAccess(request, taskname, AimsPrivilegesConstants.CP_MARKETING_CONTENT)))
                throw new AimsSecurityException();

            if (taskname.equalsIgnoreCase("edit"))
                if (!(MarketingContentHelper.checkEditAccess(currUserType, mktContentUpdForm.getStatus())))
                    throw new AimsSecurityException();
        }
        //END OF CHECK ACCESS
        /////////////////////

        if (taskname.equalsIgnoreCase("create"))
        {
            aimsCreativeContent = new AimsCreativeContent();
        }
        else if (taskname.equalsIgnoreCase("edit"))
        {
            try
            {
                mktContent = MarketingContentManager.getMarketingContent(mktContentUpdForm.getCreativeContentId(), currentUserAllianceId);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }
            aimsCreativeContent = (AimsCreativeContent) mktContent.get("AimsCreativeContent");
            oldStatus = aimsCreativeContent.getStatus();
        }

        if ((submitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
            || (submitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
            || (submitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM_AFTER_SUBMIT))
            || (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_SAVE_FORM))
            || (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
            || (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM)))
        {
            if ((taskname.equalsIgnoreCase("create")) || (taskname.equalsIgnoreCase("edit")))
            {
                if (taskname.equalsIgnoreCase("create"))
                {
                    aimsCreativeContent.setAllianceId(currentUserAllianceId);
                    aimsCreativeContent.setUserId(currUserId);
                    aimsCreativeContent.setCreatedBy(currUser);
                    aimsCreativeContent.setCreatedDate(new Date());
                }

                aimsCreativeContent.setLastUpdatedBy(currUser);
                aimsCreativeContent.setLastUpdatedDate(new Date());

                //The following properties can only be updated by an Alliance user and not the Verizon user
                if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
                {
                    aimsCreativeContent.setContentTitle(mktContentUpdForm.getContentTitle());
                    aimsCreativeContent.setContentDescription(mktContentUpdForm.getContentDescription());
                    aimsCreativeContent.setApplicationTitle(mktContentUpdForm.getApplicationTitle());
                    aimsCreativeContent.setContentUsagePermission(mktContentUpdForm.getContentUsagePermission());

                    //Start of check to see if alliance has been accepted
                    Object[] userValues = null;
                    String allianceStatus = null;
                    Collection AimsAlliance = null;

                    try
                    {
                        AimsAlliance = AllianceManager.getAllianceDetails(currentUserAllianceId, "");
                    }
                    catch (Exception ex)
                    {
                        AimsException aimsException = new AimsException("Error");
                        aimsException.addException(new RecordNotFoundException("error.application.alliance.not.accepted"));
                        throw aimsException;
                    }

                    for (Iterator iter = AimsAlliance.iterator(); iter.hasNext();)
                    {
                        userValues = (Object[]) iter.next();
                        allianceStatus = (String) userValues[3];
                    }

                    if (!(submitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM)))
                    {
                        if (!(allianceStatus.equals(AimsConstants.ALLIANCE_STATUS_ACCEPTED)))
                        {
                            AimsException aimsException = new AimsException("Error");
                            aimsException.addException(new RecordNotFoundException("error.application.alliance.not.accepted"));
                            saveErrors(request, DBErrorFinder.populateActionErrors(aimsException));
                            return mapping.getInputForward();
                        }
                    }
                    //End of check to see if alliance has been accepted                    
                }

                //The following properties can only be updated by an Verizon user and not the Alliance user                  
                if (currUserType.equals(AimsConstants.VZW_USERTYPE))
                {
                    aimsCreativeContent.setEvaluationComments(mktContentUpdForm.getEvaluationComments());
                    aimsCreativeContent.setExpiryDate(Utility.convertToDate(mktContentUpdForm.getExpiryDate(), dateFormat));
                }

                //Making sure that the files cannot be uploaded by VZW Users  
                if (currUserType.equals(AimsConstants.VZW_USERTYPE))
                {
                    mktContentUpdForm.setPublisherLogoTempFileId(new Long(0));
                    mktContentUpdForm.setAppTitleGraphicTempFileId(new Long(0));
                    mktContentUpdForm.setSplashScreenTempFileId(new Long(0));
                    mktContentUpdForm.setActiveScreenTempFileId(new Long(0));
                    mktContentUpdForm.setScreenJpeg1TempFileId(new Long(0));
                    mktContentUpdForm.setScreenJpeg2TempFileId(new Long(0));
                    mktContentUpdForm.setScreenJpeg3TempFileId(new Long(0));
                    mktContentUpdForm.setScreenJpeg4TempFileId(new Long(0));
                    mktContentUpdForm.setScreenJpeg5TempFileId(new Long(0));
                    mktContentUpdForm.setVideoFileTempFileId(new Long(0));
                    mktContentUpdForm.setAppLogoBwSmallTempFileId(new Long(0));
                    mktContentUpdForm.setAppLogoBwLargeTempFileId(new Long(0));
                    mktContentUpdForm.setAppLogoClrsmallTempFileId(new Long(0));
                    mktContentUpdForm.setAppLogoClrlargeTempFileId(new Long(0));
                }

                //START OF CHANGING/UPDATING THE PHASE
                if (submitType.equalsIgnoreCase(AimsConstants.AIMS_SAVE_FORM))
                    aimsCreativeContent.setStatus(AimsConstants.CP_MARKETING_CONTENT_STATUS_SAVED);
                else if (submitType.equalsIgnoreCase(AimsConstants.AIMS_SUBMIT_FORM))
                {
                    aimsCreativeContent.setSubmittedDate(new Date());
                    aimsCreativeContent.setStatus(AimsConstants.CP_MARKETING_CONTENT_STATUS_SUBMITTED);
                }
                else if (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_ACCEPT_FORM))
                {
                    aimsCreativeContent.setStatus(AimsConstants.CP_MARKETING_CONTENT_STATUS_APPROVED);
                    aimsCreativeContent.setApprovalDate(new Date());
                }
                else if (submitType.equalsIgnoreCase(AimsConstants.AIMS_VZW_REJECT_FORM))
                {
                    aimsCreativeContent.setStatus(AimsConstants.CP_MARKETING_CONTENT_STATUS_REJECTED);
                    aimsCreativeContent.setApprovalDate(new Date());
                }
                //END OF CHANGING/UPDATING THE PHASE

                try
                {
                    MarketingContentManager.saveOrUpdateMarketingContent(
                        aimsCreativeContent,
                        currUser,
                        currUserType,
                        mktContentUpdForm.getPublisherLogoTempFileId(),
                        mktContentUpdForm.getAppTitleGraphicTempFileId(),
                        mktContentUpdForm.getSplashScreenTempFileId(),
                        mktContentUpdForm.getActiveScreenTempFileId(),
                        mktContentUpdForm.getScreenJpeg1TempFileId(),
                        mktContentUpdForm.getScreenJpeg2TempFileId(),
                        mktContentUpdForm.getScreenJpeg3TempFileId(),
                        mktContentUpdForm.getScreenJpeg4TempFileId(),
                        mktContentUpdForm.getScreenJpeg5TempFileId(),
                        mktContentUpdForm.getVideoFileTempFileId(),
                        mktContentUpdForm.getAppLogoBwSmallTempFileId(),
                        mktContentUpdForm.getAppLogoBwLargeTempFileId(),
                        mktContentUpdForm.getAppLogoClrsmallTempFileId(),
                        mktContentUpdForm.getAppLogoClrlargeTempFileId(),
                        dateFormat);
                }
                catch (AimsException ae)
                {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                    return mapping.getInputForward();
                }

                //POST UPDATE TASKS

                mktContentUpdForm.setCreativeContentId(aimsCreativeContent.getCreativeContentId());
                mktContentUpdForm.setTask("edit");
                mktContentUpdForm.setStatus(aimsCreativeContent.getStatus());
                String newStatus = mktContentUpdForm.getStatus();

                //Set Temp File Ids to Zero
                mktContentUpdForm.setPublisherLogoTempFileId(new Long(0));
                mktContentUpdForm.setAppTitleGraphicTempFileId(new Long(0));
                mktContentUpdForm.setSplashScreenTempFileId(new Long(0));
                mktContentUpdForm.setActiveScreenTempFileId(new Long(0));
                mktContentUpdForm.setScreenJpeg1TempFileId(new Long(0));
                mktContentUpdForm.setScreenJpeg2TempFileId(new Long(0));
                mktContentUpdForm.setScreenJpeg3TempFileId(new Long(0));
                mktContentUpdForm.setScreenJpeg4TempFileId(new Long(0));
                mktContentUpdForm.setScreenJpeg5TempFileId(new Long(0));
                mktContentUpdForm.setVideoFileTempFileId(new Long(0));
                mktContentUpdForm.setAppLogoBwSmallTempFileId(new Long(0));
                mktContentUpdForm.setAppLogoBwLargeTempFileId(new Long(0));
                mktContentUpdForm.setAppLogoClrsmallTempFileId(new Long(0));
                mktContentUpdForm.setAppLogoClrlargeTempFileId(new Long(0));
                //End Of Set Temp File Ids to Zero

                if (((oldStatus != null) && (!oldStatus.equalsIgnoreCase(newStatus)))
                    || ((oldStatus == null) && (newStatus.equalsIgnoreCase(AimsConstants.CP_MARKETING_CONTENT_STATUS_SUBMITTED))))
                {
                    AimsEventLite aimsEvent = null;
                    if (newStatus.equalsIgnoreCase(AimsConstants.CP_MARKETING_CONTENT_STATUS_SUBMITTED))
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_MARKETING_CONTENT_STATUS_SUBMITTED);
                    if (newStatus.equalsIgnoreCase(AimsConstants.CP_MARKETING_CONTENT_STATUS_APPROVED))
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_MARKETING_CONTENT_STATUS_APPROVED);
                    if (newStatus.equalsIgnoreCase(AimsConstants.CP_MARKETING_CONTENT_STATUS_REJECTED))
                        aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_MARKETING_CONTENT_STATUS_REJECTED);

                    if (aimsEvent != null)
                    {
                        AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                        aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, aimsCreativeContent.getAllianceId().toString());

                        AimsAllianc aimsAllianceOfContent =
                            (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsCreativeContent.getAllianceId().toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfContent.getCompanyName());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_MARKETING_CONTENT_TITLE, aimsCreativeContent.getContentTitle());
                        aimsEvent.raiseEvent(aimsEventObject);
                    }
                }

                saveMessages(request, MarketingContentHelper.getMessagesOnUpdate(submitType));

                forward = "update";
            }
        }

        return mapping.findForward(forward);
    }
}
