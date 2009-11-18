package com.netpace.aims.controller.alliance;

import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.alliance.AllianceManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.StringFuncs;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 *
 * @struts.action path="/mportalAllianceUpdate"
 *                name="MportalAllianceUpdateForm"
 *                scope="request"
 *			      validate="true"
 *                input="/alliance/mportalAllianceUpdate.jsp"
 * @struts.action-forward name="edit" path="/alliance/mportalAllianceUpdate.jsp"
 * @author Sajjad Raza
 */

public class MportalAllianceUpdateAction extends BaseAction {
    private static Logger log = Logger.getLogger(MportalAllianceUpdateAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        log.debug("======= MportalAllianceUpdateAction starts");
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        MportalAllianceUpdateForm mportalAllianceUpdateForm = (MportalAllianceUpdateForm)form;

        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        //Long currentUserAllianceId = user.getAimsAllianc();
        String currUserType = user.getUserType();

        Long allianceId = null;
        AimsAllianc aimsAlliance = null;

        String forward = "edit";
        String taskname = "";
        Object o_param;

        o_param = request.getParameter("task");

        if (o_param != null) {
            taskname = request.getParameter("task");
        }
        else {
            taskname = "edit";
        }

        //CHECK ACCESS
        if (!currUserType.equals(AimsConstants.VZW_USERTYPE)) {
            throw new AimsSecurityException();
        }

        allianceId = new Long(StringFuncs.NulltoZeroStringReplacement(request.getParameter("allianceId")));


        ////// aimsAlliance setup load values from database
        try {
            aimsAlliance = (AimsAllianc) DBHelper.getInstance().load(AimsAllianc.class, allianceId.toString());
        }
        catch (HibernateException he) {
            he.printStackTrace();
            throw new AimsSecurityException();
        }

        if(aimsAlliance ==null) {
            throw new AimsSecurityException();
        }

        mportalAllianceUpdateForm.setAllianceId(allianceId);

        if (taskname.equalsIgnoreCase("edit")) {
            mportalAllianceUpdateForm.setAllianceName(aimsAlliance.getCompanyName());
            mportalAllianceUpdateForm.setVendorId(aimsAlliance.getVendorId());
            mportalAllianceUpdateForm.setMportalAllianceName(aimsAlliance.getMportalAllianceName());
        }

        if (taskname.equalsIgnoreCase("save")) {

            //update alliance values
            aimsAlliance.setMportalAllianceName(mportalAllianceUpdateForm.getMportalAllianceName());
            aimsAlliance.setLastUpdatedBy(currUser);
            aimsAlliance.setLastUpdatedDate(new Date());
            AllianceManager.updateAlliance(aimsAlliance);
            log.debug("======= MportalAllianceUpdateAction: mportalAlliance name of "+aimsAlliance.getCompanyName()+" changed to "+aimsAlliance.getMportalAllianceName());

            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.MportalAllianceUpdateForm.manage.alliance.saved"));
            saveMessages(request, messages);
        }

        log.debug("======= MportalAllianceUpdateAction ends \tforward: "+forward);

        return mapping.findForward(forward);
    }
}



