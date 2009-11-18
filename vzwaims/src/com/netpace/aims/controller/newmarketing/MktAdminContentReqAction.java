package com.netpace.aims.controller.newmarketing;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.newmarketing.ContentRequestManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.security.AimsSecurityManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.newmarketing.AimsCreativeContent;
import com.netpace.aims.model.newmarketing.AimsCreativeCrequest;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * @struts.action path="/newMktAdminContentRequest"
 *                scope="request"
 *                input="/newmarketing/adminContentRequestList.jsp"
 *                name="MktAdmConReqForm"
 *                validate="false"
 * @struts.action-forward name="list" path="/newmarketing/adminContentRequestList.jsp"
 * @struts.action-forward name="view" path="/newmarketing/adminContentRequestView.jsp"
 * @struts.action-forward name="preaccept" path="/newmarketing/adminContentRequestPreAccept.jsp"
 * @author Ahson Imtiaz
 */

public class MktAdminContentReqAction extends BaseAction
{

    static Logger log = Logger.getLogger(MktAdminContentReqAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        if (!(AimsSecurityManager.checkAccess(request, AimsPrivilegesConstants.REVIEW_CONTENT_REQUEST, AimsSecurityManager.SELECT)))
        {
            throw new AimsSecurityException();
        }

        ActionMessages messages = new ActionMessages();
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        String currUserType = user.getUserType();
        Long currentUserAllianceId = user.getAimsAllianc();
        int iPageLength = user.getRowsLength().intValue();
        StringBuffer sbFilter = new StringBuffer();

        MktAdmConReqForm frm = (MktAdmConReqForm) form;
        Integer pageNo = frm.getPageNo();
        if (pageNo == null)
            pageNo = new Integer(1);

        if (frm.getTask() != null)
        {
            if (frm.getTask().equals("next") || frm.getTask().equals("previous"))
            {

                if (frm.getTask().equals("next"))
                {
                    pageNo = new Integer(pageNo.intValue() + 1);
                }
                else if (frm.getTask().equals("previous"))
                {
                    pageNo = new Integer(pageNo.intValue() - 1);
                }
            }
            else if (frm.getTask().equals("view") && frm.getCrequestId() != null && frm.getCrequestId().intValue() != 0)
            {
                AimsCreativeCrequest acr = ContentRequestManager.getContentRequestDetail(frm.getCrequestId(), currentUserAllianceId);
                session.setAttribute("ContentRequestDetail", acr);
                session.setAttribute("ApplicationNamesList", ContentRequestManager.getContentName(frm.getCrequestId(), currentUserAllianceId));
                session.setAttribute("FilesList", ContentRequestManager.getFilesDetail(acr.getCrequestId()));
                return mapping.findForward("view");
            }
            else if (frm.getTask().equals("reject"))
            {
                if (currUserType.equals(AimsConstants.VZW_USERTYPE))
                {
                    ContentRequestManager.updateCRStatus("DECLINED", frm.getCrequestId(), user.getUserId(), currentUserAllianceId);
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("marketing.contentRequest.recordUpdated"));
                    saveMessages(request, messages);
                }
            }
            else if (frm.getTask().equals("preaccept"))
            {
                // content request details must already be in session
                AimsCreativeCrequest acr = (AimsCreativeCrequest) session.getAttribute("ContentRequestDetail");
                session.setAttribute("ApplicationContacts", ContentRequestManager.getAllianceAppContactList(frm.getCrequestId(), currentUserAllianceId));
                return mapping.findForward("preaccept");
            }
            else if (frm.getTask().equals("acceptparts"))
            {
                if (currUserType.equals(AimsConstants.VZW_USERTYPE))
                {
                    log.debug("***** In the Acceptparts Block *****");
                    try
                    {
                        String[] appsId = frm.getAppsId();
                        String strAction = null;
                        Long lngUserId = null;
                        if (appsId != null && appsId.length > 0)
                        {
                            log.debug("******* appsId ******* : " + appsId.length);
                            for (int iCount = 0; iCount < appsId.length; iCount++)
                            {
                                lngUserId = null;
                                strAction = request.getParameter("action_" + appsId[iCount]);

                                log.debug("****** Content ID: " + strAction);

                                if (strAction.equals("N"))
                                    continue;
                                if (strAction.equals("F"))
                                    lngUserId = new Long(request.getParameter("userId_" + appsId[iCount]));
                                else
                                    lngUserId = null;
                                ContentRequestManager.updateRequestedCntStatus(frm.getCrequestId(), new Long(appsId[iCount]), strAction, lngUserId);

                                AimsEventLite aimsEvent = null;

                                if ((strAction != null) && (strAction.equals("F")))
                                    aimsEvent =
                                        EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_MARKETING_PROGRAM_FORWARDED_TO_ALLIANCE);

                                if ((aimsEvent != null) && (lngUserId != null))
                                {
                                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                                    AimsUser aimsUserOfProgram = (AimsUser) DBHelper.getInstance().load(AimsUser.class, lngUserId.toString());

                                    aimsEventObject.setProperty(
                                        AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID,
                                        aimsUserOfProgram.getAimsAllianc().toString());
                                    //Here we are explicitly sending UserID of Alliance User to whom it is forwarded(to the stored proc to be mailed). 
                                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_USER_IDS, lngUserId.toString());

                                    AimsAllianc aimsAllianceOfProgram =
                                        (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, aimsUserOfProgram.getAimsAllianc().toString());
                                    AimsCreativeContent aimsCreativeContentOfProgram =
                                        (AimsCreativeContent) DBHelper.getInstance().load(AimsCreativeContent.class, appsId[iCount]);
                                    AimsCreativeCrequest aimsCreativeCrequestOfProgram =
                                        (AimsCreativeCrequest) DBHelper.getInstance().load(AimsCreativeCrequest.class, frm.getCrequestId().toString());

                                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfProgram.getCompanyName());
                                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                                    aimsEventObject.setProperty(
                                        AimsNotificationConstants.PLACE_HOLDER_MARKETING_CONTENT_TITLE,
                                        aimsCreativeContentOfProgram.getContentTitle());
                                    aimsEventObject.setProperty(
                                        AimsNotificationConstants.PLACE_HOLDER_MARKETING_PROGRAM_NAME,
                                        aimsCreativeCrequestOfProgram.getProgramName());

                                    aimsEvent.raiseEvent(aimsEventObject);
                                }

                            } // for ends
                        } // If ends

                        ContentRequestManager.updateCreativeCRStatus("ACCEPTED", frm.getCrequestId(), user.getUserId(), currentUserAllianceId);
                        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("marketing.contentRequest.recordUpdated"));
                        saveMessages(request, messages);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace(System.out);
                    }
                    log.debug("***** In the Acceptparts Block *****");
                }
            }

            else if (frm.getTask().equals("alliance_acceptparts"))
            {
                if (currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
                {
                    log.debug("***** In the Alliance Acceptparts Block *****");
                    try
                    {
                        String[] appsId = frm.getAppsId();
                        String strAction = null;
                        if (appsId != null && appsId.length > 0)
                        {
                            log.debug("******* appsId ******* : " + appsId.length);
                            for (int iCount = 0; iCount < appsId.length; iCount++)
                            {
                                strAction = request.getParameter("action_" + appsId[iCount]);

                                log.debug("****** appsId[iCount]: " + appsId[iCount]);
                                log.debug("****** Content ID: " + strAction);

                                if ((strAction != null) && (strAction.equals("N")))
                                    continue;

                                if (strAction != null)
                                    ContentRequestManager.updateRequestedCntStatusForAlliance(
                                        frm.getCrequestId(),
                                        new Long(appsId[iCount]),
                                        strAction,
                                        user.getUserId());

                                AimsEventLite aimsEvent = null;

                                if ((strAction != null) && (strAction.equals("A")))
                                    aimsEvent =
                                        EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_MARKETING_PROGRAM_ACCEPTED_BY_ALLIANCE);

                                if (aimsEvent != null)
                                {
                                    AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                                    aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, currentUserAllianceId.toString());

                                    AimsAllianc aimsAllianceOfProgram =
                                        (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, currentUserAllianceId.toString());
                                    AimsCreativeContent aimsCreativeContentOfProgram =
                                        (AimsCreativeContent) DBHelper.getInstance().load(AimsCreativeContent.class, appsId[iCount]);
                                    AimsCreativeCrequest aimsCreativeCrequestOfProgram =
                                        (AimsCreativeCrequest) DBHelper.getInstance().load(AimsCreativeCrequest.class, frm.getCrequestId().toString());

                                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAllianceOfProgram.getCompanyName());
                                    aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                                    aimsEventObject.setProperty(
                                        AimsNotificationConstants.PLACE_HOLDER_MARKETING_CONTENT_TITLE,
                                        aimsCreativeContentOfProgram.getContentTitle());
                                    aimsEventObject.setProperty(
                                        AimsNotificationConstants.PLACE_HOLDER_MARKETING_PROGRAM_NAME,
                                        aimsCreativeCrequestOfProgram.getProgramName());

                                    aimsEvent.raiseEvent(aimsEventObject);
                                }

                            } // for ends
                        } // If ends

                        ContentRequestManager.updateCreativeCRStatus("ACCEPTED", frm.getCrequestId(), user.getUserId(), currentUserAllianceId);
                        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("marketing.contentRequest.recordUpdated"));
                        saveMessages(request, messages);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace(System.out);
                    }
                    log.debug("***** In the Alliance Acceptparts Block *****");
                }
            }
            else if (frm.getTask().equals("list"))
            {
                pageNo = new Integer(1);
            }
        }

        if (frm.getFilterText() != null && frm.getFilterText().length() > 0)
        {
            String strField = frm.getFilterField();
            if (strField.equals("programName"))
                sbFilter.append(" upper(creq.programName) like '%").append(Utility.replaceString(frm.getFilterText(), "'", "''").toUpperCase()).append("%' ");
            else if (strField.equals("status"))
                sbFilter.append(" upper(creq.status) like '%").append(Utility.replaceString(frm.getFilterText(), "'", "''").toUpperCase()).append("%' ");
        }

        frm.setPageNo(pageNo);
        request.setAttribute(
            "ContentRequestList",
            ContentRequestManager.getConReqForApprovals(iPageLength, pageNo.intValue(), sbFilter.toString(), currentUserAllianceId));
        int iTotalRecords = ContentRequestManager.getRecordCountForApprovals(sbFilter.toString(), currentUserAllianceId);
        log.debug("**** No. of Records: " + iTotalRecords);
        int iNoOfPage = (iTotalRecords / iPageLength) + (iTotalRecords % iPageLength > 0 ? 1 : 0);
        request.setAttribute("TotalPages", new Integer(iNoOfPage));
        return mapping.findForward("list");

    }
}
