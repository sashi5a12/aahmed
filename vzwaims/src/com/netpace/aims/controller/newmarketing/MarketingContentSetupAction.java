package com.netpace.aims.controller.newmarketing;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.newmarketing.MarketingContentManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.newmarketing.AimsCreativeContent;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.DBErrorFinder;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for display of update form of BREW Application.
 *
 * @struts.action path="/mktContentSetup"
 *                name="MarketingContentUpdateForm"
 *                scope="request"
 *                input="/mktContentsViewDelete.do"
                  validate="false"
 * @struts.action-forward name="update" path="/newmarketing/mktContentUpdate.jsp"
 * @struts.action-forward name="view" path="/mktContentsViewDelete.do"
 * @struts.action-forward name="mktView" path="/newmarketing/mktContentView.jsp"
 * @author Adnan Makda
 */
public class MarketingContentSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(MarketingContentSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        //Privileges Being Checked Below

        HttpSession session = request.getSession();
        String dateFormat = this.getResources(request).getMessage("date.format");
        Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
        String forward = "view";
        String taskname = "";
        Object o_param;
        AimsCreativeContent aimsCreativeContent = null;
        HashMap mktContent = null;

        o_param = request.getParameter("task");
        if (o_param != null)
        {
            taskname = request.getParameter("task");
            request.setAttribute("task", (String) o_param);
        }
        else
            return mapping.findForward(forward);

        //Getting Marketing Content Information
        if (!(taskname.equalsIgnoreCase("create")))
        {
            try
            {
                mktContent = MarketingContentManager.getMarketingContent(new Long(request.getParameter("mktContentId")), currentUserAllianceId);
            }
            catch (AimsException ae)
            {
                saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                return mapping.getInputForward();
            }

            aimsCreativeContent = (AimsCreativeContent) mktContent.get("AimsCreativeContent");
        }

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
        }

        if (taskname.equalsIgnoreCase("edit"))
            if (!(MarketingContentHelper.checkEditAccess(currUserType, aimsCreativeContent.getStatus())))
                throw new AimsSecurityException();

        if (taskname.equalsIgnoreCase("delete"))
        {
            if (!(MarketingContentHelper.checkDeleteAccess(currUserType, aimsCreativeContent.getStatus())))
                throw new AimsSecurityException();
            else
            {
                try
                {
                    int delCount = MarketingContentManager.deleteMarketingContent(new Long(request.getParameter("mktContentId")), currUser);
                }
                catch (AimsException ae)
                {
                    saveErrors(request, DBErrorFinder.populateActionErrors(ae));
                }
                return mapping.getInputForward();
            }
        }
        //END OF CHECK ACCESS
        /////////////////////

        //Get Form
        MarketingContentUpdateForm mktContentUpdForm = (MarketingContentUpdateForm) form;

        mktContentUpdForm.setTask(taskname);

        if (taskname.equalsIgnoreCase("create"))
        {
            mktContentUpdForm.setCreativeContentId(new Long(0));
            mktContentUpdForm.setContentUsagePermission(AimsConstants.CP_MARKETING_CONTENT_USAGE_TYPE_GENERAL[0]);
            mktContentUpdForm.setStatus(AimsConstants.CP_MARKETING_CONTENT_STATUS_NEW);
            forward = "update";
        }
        else if ((taskname.equalsIgnoreCase("edit")) || (taskname.equalsIgnoreCase("view")))
        {
            //Setting the Forwards
            if (taskname.equalsIgnoreCase("view"))
                forward = "mktView";
            else if (taskname.equalsIgnoreCase("edit"))
                forward = "update";
            //End of Setting the Forwards

            mktContentUpdForm.setCreativeContentId(aimsCreativeContent.getCreativeContentId());
            mktContentUpdForm.setContentTitle(aimsCreativeContent.getContentTitle());
            mktContentUpdForm.setContentDescription(aimsCreativeContent.getContentDescription());
            mktContentUpdForm.setApplicationTitle(aimsCreativeContent.getApplicationTitle());
            mktContentUpdForm.setContentUsagePermission(aimsCreativeContent.getContentUsagePermission());
            mktContentUpdForm.setStatus(aimsCreativeContent.getStatus());
            mktContentUpdForm.setUserId(aimsCreativeContent.getUserId());
            mktContentUpdForm.setApprovalDate(Utility.convertToString(aimsCreativeContent.getApprovalDate(), dateFormat));
            mktContentUpdForm.setExpiryDate(Utility.convertToString(aimsCreativeContent.getExpiryDate(), dateFormat));
            mktContentUpdForm.setEvaluationComments(aimsCreativeContent.getEvaluationComments());

            //Set File Names
            mktContentUpdForm.setPublisherLogoFileName(aimsCreativeContent.getPublisherLogoFileName());
            mktContentUpdForm.setAppTitleGraphicFileName(aimsCreativeContent.getAppTitleGraphicFileName());
            mktContentUpdForm.setSplashScreenFileName(aimsCreativeContent.getSplashScreenFileName());
            mktContentUpdForm.setActiveScreenFileName(aimsCreativeContent.getActiveScreenFileName());
            mktContentUpdForm.setScreenJpeg1FileName(aimsCreativeContent.getScreenJpeg1FileName());
            mktContentUpdForm.setScreenJpeg2FileName(aimsCreativeContent.getScreenJpeg2FileName());
            mktContentUpdForm.setScreenJpeg3FileName(aimsCreativeContent.getScreenJpeg3FileName());
            mktContentUpdForm.setScreenJpeg4FileName(aimsCreativeContent.getScreenJpeg4FileName());
            mktContentUpdForm.setScreenJpeg5FileName(aimsCreativeContent.getScreenJpeg5FileName());
            mktContentUpdForm.setVideoFileFileName(aimsCreativeContent.getVideoFileFileName());
            mktContentUpdForm.setAppLogoBwSmallFileName(aimsCreativeContent.getAppLogoBwSmallFileName());
            mktContentUpdForm.setAppLogoBwLargeFileName(aimsCreativeContent.getAppLogoBwLargeFileName());
            mktContentUpdForm.setAppLogoClrsmallFileName(aimsCreativeContent.getAppLogoClrsmallFileName());
            mktContentUpdForm.setAppLogoClrlargeFileName(aimsCreativeContent.getAppLogoClrlargeFileName());

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

            //Set VZW Specific Information
            if (currUserType.equals(AimsConstants.VZW_USERTYPE))
            {}
        }

        return mapping.findForward(forward);
    }

}
