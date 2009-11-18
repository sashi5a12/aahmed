package com.netpace.aims.controller.newmarketing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.newmarketing.ContentRequestManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.newmarketing.AimsCreativeCrequest;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;

/**
 * @struts.action path="/newMktContentRequest"
 *                scope="request"
 *                input="/newmarketing/contentRequestList.jsp"
 *                name="MktAdmConReqForm"
 *                validate="false"
 * @struts.action-forward name="list" path="/newmarketing/contentRequestList.jsp"
 * @author Ahson Imtiaz
 */

public class MktContentReqAction extends BaseAction
{

    static Logger log = Logger.getLogger(MktContentReqAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.NEW_MARKETING);

        ActionMessages messages = new ActionMessages();
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
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
            else if (frm.getTask().equals("delete") && frm.getCrequestId() != null && frm.getCrequestId().intValue() != 0)
            {
                Long contentRequestId = frm.getCrequestId();
                AimsCreativeCrequest acr = ContentRequestManager.getContentRequestDetail(contentRequestId, currentUserAllianceId, user.getUserId());
                if (!acr.getStatus().equals("SAVED"))
                    throw new AimsSecurityException();
                ContentRequestManager.deleteContentRequest(contentRequestId);
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("marketing.contentRequest.deleted"));
                saveMessages(request, messages);
            }
            else if (frm.getTask().equals("saveConfirm"))
            {
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("marketing.contentRequest.recordUpdated"));
                saveMessages(request, messages);
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
            ContentRequestManager.getContentRequests(
                StringFuncs.initializeStringGetParam(frm.getSortField(), "PROGRAM_NAME"),
                iPageLength,
                pageNo.intValue(),
                user.getUserId(),
                sbFilter.toString()));
        int iTotalRecords = ContentRequestManager.getRecordCount(user.getUserId(), sbFilter.toString());
        int iNoOfPage = (iTotalRecords / iPageLength) + (iTotalRecords % iPageLength > 0 ? 1 : 0);
        request.setAttribute("TotalPages", new Integer(iNoOfPage));
        return mapping.findForward("list");

    }
}
