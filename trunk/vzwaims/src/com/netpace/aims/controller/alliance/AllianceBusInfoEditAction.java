package com.netpace.aims.controller.alliance;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.alliance.AllianceBusInfoManager;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.bo.events.AimsEventObject;
import com.netpace.aims.bo.events.EventManagerFactory;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.controller.events.PlaceHolderHelper;
import com.netpace.aims.model.DBHelper;
import com.netpace.aims.model.core.AimsAllianc;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.events.AimsEventLite;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsNotificationConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/allianceBusInfoEdit"                
 *                scope="request" 
 *				  name="AllianceBusInfoForm"
 *				  validate="true"
 *                input="/alliance/allianceBusinessInfoUpdate.jsp"
 * @struts.action-forward name="view" path="/alliance/allianceBusinessInfoUpdate.jsp"
 * @struts.action-forward name="userHome" path="/userHome.do"
 * @author Rizwan Qazi
 */

public class AllianceBusInfoEditAction extends BaseAction
{

    static Logger log = Logger.getLogger(AllianceBusInfoEditAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        String taskname = request.getParameter("task");

        HttpSession session = request.getSession();
        AllianceBusInfoForm allianceBusInfoForm = (AllianceBusInfoForm) form;
        AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
        ActionErrors errors = new ActionErrors();

        Long alliance_id = user.getAimsAllianc();
        String user_type = user.getUserType();
        String user_name = user.getUsername();

        String forward = "userHome";        

        log.debug("Task : " + taskname);
        log.debug("allianceBusInfoForm.getAssignedArrRolesOfAlliance() : " + allianceBusInfoForm.getAssignedArrRolesOfAlliance());

        if (StringFuncs.NullValueReplacement(taskname).equalsIgnoreCase("edit"))
        {

            try
            {

                log.debug("INSIDE Edit Action EDIT! ");
                AllianceBusInfoManager.UpdateAllianceBusInfo(
                    alliance_id,
                    allianceBusInfoForm.getCompanyName(),
                    allianceBusInfoForm.getDateEstablished(),
                    allianceBusInfoForm.getFullTimeEmployees(),
                    allianceBusInfoForm.getPartTimeEmployees(),
                    allianceBusInfoForm.getInsideSalesEmployees(),
                    allianceBusInfoForm.getOutsideSalesEmployees(),
                    allianceBusInfoForm.getRetailSalesEmployees(),
                    allianceBusInfoForm.getTeleMarketingEmployees(),
                    allianceBusInfoForm.getTechnicianEmployees(),
                    allianceBusInfoForm.getWarehouseEmployees(),
                    allianceBusInfoForm.getSupportingEmployees(),
                    allianceBusInfoForm.getOtherEmployees(),
                    allianceBusInfoForm.getCountryOfOrigin(),
                    allianceBusInfoForm.getProdServicesDesc(),
                    allianceBusInfoForm.getCompetitiveAdvantages(),
                    allianceBusInfoForm.getPartner(),
                    allianceBusInfoForm.getCompanyPresentationTempFileId(),
                    allianceBusInfoForm.getCompanyLogoTempFileId(),
                    allianceBusInfoForm.getProgGuideTempFileId(),
                    allianceBusInfoForm.getAssignedArrRolesOfAlliance(),
                    allianceBusInfoForm.getAssignedArrDevTechnologies(),
                    allianceBusInfoForm.getAssignedArrLinesOfBusiness(),
                    user_name,
                    allianceBusInfoForm.getEmployeesRange(),
                    allianceBusInfoForm.getArrAllianceFinancing(),
                    allianceBusInfoForm.getArrAllianceDevelopments(),
                    allianceBusInfoForm.getOutsourceDevelopmentPublisherName());

                //Start of Event Related Code
                if (alliance_id != null)
                {
                    AimsAllianc aimsAlliance = (AimsAllianc) DBHelper.getInstance().load(com.netpace.aims.model.core.AimsAllianc.class, alliance_id.toString());
                    if (aimsAlliance.getStatus().equals(AimsConstants.ALLIANCE_STATUS_SUBMITTED))
                    {
                        AimsEventLite aimsEvent = EventManagerFactory.getInstance().getEvent(AimsNotificationConstants.EVENT_SUBMITTED_ALLIANCE_UPDATED);
                        if (aimsEvent != null)
                        {
                            AimsEventObject aimsEventObject = aimsEvent.getNewEventObject();

                            //Setting Properties
                            aimsEventObject.setProperty(AimsNotificationConstants.HANDLER_PROPERTY_ALLIANCE_ID, alliance_id.toString());
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_NAME, aimsAlliance.getCompanyName());
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_DATE_OF_EVENT, (new Date()).toString());
                            if (aimsAlliance.getVendorId() != null)
                                aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_VENDOR_ID, aimsAlliance.getVendorId().toString());
                            aimsEventObject.setProperty(
                                AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_NAME,
                                PlaceHolderHelper.getAllianceAdminName(alliance_id));
                            aimsEventObject.setProperty(
                                AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_ADMIN_PHONE,
                                PlaceHolderHelper.getAllianceAdminPhone(alliance_id));
                            aimsEventObject.setProperty(AimsNotificationConstants.PLACE_HOLDER_ALLIANCE_INFO_PAGE, "Business Information");

                            aimsEvent.raiseEvent(aimsEventObject);
                        }
                    }
                }

            }
            catch (AimsException ae)
            {
                log.debug("ae.getMessageKey() in Action Class : " + ae.getMessageKey());

                if (ae.getMessageArgs() != null)
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey(), ae.getMessageArgs()));
                else
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(ae.getMessageKey()));

                saveErrors(request, errors);
                return mapping.getInputForward();
            }

            //Set Temp File Ids to Zero
            allianceBusInfoForm.setCompanyPresentationTempFileId(new Long(0));
            allianceBusInfoForm.setCompanyLogoTempFileId(new Long(0));
            allianceBusInfoForm.setProgGuideTempFileId(new Long(0));

            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.edit.success"));
            saveMessages(request, messages);
            forward = "view";

        }

        return mapping.findForward(forward);
    }
}
