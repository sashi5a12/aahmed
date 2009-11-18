package com.netpace.aims.controller.newmarketing;

import java.util.ArrayList;
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

import com.netpace.aims.bo.core.TempFilesManager;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.bo.newmarketing.ContentRequestManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.model.newmarketing.AimsCreativeCrequest;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.TempFile;
import com.netpace.aims.util.Utility;

/**
 * @struts.action path="/newMktContReqCreateEdit"
 *                scope="request"
 *                input="/newmarketing/contentRequestCreate.jsp"
 *                name="MktContentReqForm"
 *                validate="true"
 * @struts.action-forward name="list" path="/newMktContentRequest.do"
 * @struts.action-forward name="view" path="/newmarketing/contentRequestView.jsp"
 * @author Ahson Imtiaz
 */

public class MktContReqCreateEditAction extends BaseAction
{

    static Logger log = Logger.getLogger(MktContReqCreateEditAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        ActionMessages messages = new ActionMessages();
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        Long currentUserAllianceId = user.getAimsAllianc();
        MktContentReqForm frm = (MktContentReqForm) form;

        if (frm.getTask() != null)
        {
            if ((frm.getTask().equals("edit") || frm.getTask().equals("clone")) && frm.getCrequestId() != null && frm.getCrequestId().intValue() != 0)
            {
                AimsCreativeCrequest acr = ContentRequestManager.getContentRequestDetail(frm.getCrequestId(), currentUserAllianceId, user.getUserId());
                session.setAttribute("ApplicationNamesList", ContentRequestManager.getContentName(frm.getCrequestId(), currentUserAllianceId));

                if (frm.getTask().equals("clone"))
                {
                    frm.setCrequestId(null);
                    frm.setProgramName("Cloned " + acr.getProgramName());
                    frm.setStatus("NEW");
                    session.setAttribute("FilesList", new ArrayList());
                }
                else
                {
                    if (acr.getStatus() != null && !acr.getStatus().equals("SAVED"))
                        throw new AimsSecurityException();
                    frm.setProgramName(acr.getProgramName());
                    frm.setStatus(acr.getStatus());
                    session.setAttribute("FilesList", ContentRequestManager.getFilesDetail(acr.getCrequestId()));
                }

                frm.setAreaContactName(acr.getAreaContactName());
                frm.setAreaContactTel(acr.getAreaContactTel());
                frm.setAreaTargeted(acr.getAreaTargeted());
                frm.setCreativeAgency(acr.getCreativeAgency());
                frm.setExposure(acr.getExposure());
                frm.setExpProgramTakeRate(acr.getExpProgramTakeRate());
                frm.setObjectiveOfProgram(acr.getObjectiveOfProgram());
                frm.setPlacementDisclaimer(acr.getPlacementDisclaimer());
                frm.setProgramDistributionDate(Utility.convertToString(acr.getProgramDistributionDate(), AimsConstants.DATE_FORMAT));
                frm.setProgramHighlights(acr.getProgramHighlights());
                frm.setProjectStartDate(Utility.convertToString(acr.getProjectStartDate(), AimsConstants.DATE_FORMAT));
                frm.setPromotionLength(acr.getPromotionLength());
                frm.setTimelineExposure(acr.getTimelineExposure());
                frm.setVehicleComm(acr.getVehicleComm());

                return mapping.getInputForward();
            }
            else if (frm.getTask().equals("create"))
            {
                session.setAttribute("FilesList", new ArrayList());
                //				session.setAttribute("ApplicationNamesList",ContentRequestManager.getApplicationsName(frm.getAppsIds()));
                session.setAttribute("ApplicationNamesList", ContentRequestManager.getContentName(frm.getAppsIds()));
                frm.setStatus("NEW");
                return mapping.getInputForward();
            }
            else if (frm.getTask().equals("view") && frm.getCrequestId() != null && frm.getCrequestId().intValue() != 0)
            {
                AimsCreativeCrequest acr = ContentRequestManager.getContentRequestDetail(frm.getCrequestId(), currentUserAllianceId, user.getUserId());
                request.setAttribute("ContentRequestDetail", acr);
                session.setAttribute("ApplicationNamesList", ContentRequestManager.getContentName(frm.getCrequestId(), currentUserAllianceId));
                session.setAttribute("FilesList", ContentRequestManager.getFilesDetail(acr.getCrequestId()));
                return mapping.findForward("view");
            }
            else if (frm.getTask().equals("submit") || frm.getTask().equals("save"))
            {
                AimsCreativeCrequest acr = null;
                if (frm.getCrequestId() != null && frm.getCrequestId().intValue() != 0)
                {
                    acr = ContentRequestManager.getContentRequestDetail(frm.getCrequestId(), currentUserAllianceId, user.getUserId());
                }
                else
                {
                    acr = new AimsCreativeCrequest();
                    acr.setCreatedDate(new java.util.Date());
                    acr.setUserId(user.getUserId());
                }

                acr.setAreaContactName(frm.getAreaContactName());
                acr.setAreaContactTel(frm.getAreaContactTel());
                acr.setAreaTargeted(frm.getAreaTargeted());
                acr.setCreativeAgency(frm.getCreativeAgency());
                acr.setExposure(frm.getExposure());
                acr.setExpProgramTakeRate(frm.getExpProgramTakeRate());
                acr.setObjectiveOfProgram(frm.getObjectiveOfProgram());
                acr.setPlacementDisclaimer(frm.getPlacementDisclaimer());
                acr.setProgramDistributionDate(Utility.convertToDate(frm.getProgramDistributionDate(), AimsConstants.DATE_FORMAT));
                acr.setProgramHighlights(frm.getProgramHighlights());
                acr.setProgramName(frm.getProgramName());
                acr.setProjectStartDate(Utility.convertToDate(frm.getProjectStartDate(), AimsConstants.DATE_FORMAT));
                acr.setPromotionLength(frm.getPromotionLength());
                acr.setTimelineExposure(frm.getTimelineExposure());
                acr.setVehicleComm(frm.getVehicleComm());

                if (frm.getTask().equals("submit"))
                {
                    acr.setStatus("SUBMITTED");
                    acr.setSubmittedDate(new java.util.Date());
                }
                else
                {
                    acr.setStatus("SAVED");
                }

                ContentRequestManager.saveContentRequest(acr, frm.getAppsIds(), (ArrayList) session.getAttribute("FilesList"));

                if (frm.getTask().equals("submit"))
                {

                    AimsEventLite aimsEvent = null;
                    aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_MARKETING_PROGRAM_SUBMITTED_BY_MKT_USER);

                    if ((aimsEvent != null))
                    {
                        AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_MARKETING_PROGRAM_NAME, frm.getProgramName());
                        aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_MARKETING_PROGRAM_START_DATE, frm.getProjectStartDate());

                        aimsEvent.raiseEvent(aimsEventObject);
                    }
                }
                return new ActionForward("/newMktContentRequest.do?task=saveConfirm", true);
            }
            else if (frm.getTask().equals("upload"))
            {
                TempFile tempFile = TempFilesManager.saveTempFile(frm.getUploadDocument(), session.getId(), user.getUserId().toString(), frm.getComments());
                frm.getUploadDocument().destroy();
                FilesDetail fd = new FilesDetail();
                fd.setFileId(tempFile.getFileId());
                fd.setFileType(1);
                fd.setFileName(tempFile.getFileName());
                fd.setComments(frm.getComments());
                ArrayList flist = (ArrayList) session.getAttribute("FilesList");
                flist.add(fd);
                //else {flist = new ArrayList(); flist.add(fd); log.debug("New Array Created for File");}
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("marketing.contentRequest.fileAdded"));
                saveMessages(request, messages);
                return mapping.getInputForward();
            }
            else if (frm.getTask().equals("remove"))
            {
                ArrayList flist = (ArrayList) session.getAttribute("FilesList");
                if (flist != null && frm.getFileId() != null && frm.getFileType() != null)
                {
                    for (int iCount = 0; iCount < flist.size(); iCount++)
                    {
                        FilesDetail fd = (FilesDetail) flist.get(iCount);
                        if (fd.getFileId().equals(frm.getFileId()) && fd.getFileType() == frm.getFileType().intValue())
                        {
                            flist.remove(fd);
                            break;
                        }
                    }
                    ContentRequestManager.removeContentReqFile(frm.getFileId(), frm.getFileType());
                }
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("marketing.contentRequest.fileRemoved"));
                saveMessages(request, messages);
                return mapping.getInputForward();
            }

        }
        return mapping.findForward("list");
    }
}
