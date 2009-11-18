package com.netpace.aims.controller.devices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.core.IntegrityConstraintException;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.StringFuncs;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/devices"
 *                scope="request"
 *		          name="DevicesForm"
 *                input="/sys_admin.jsp"
 *                validate="false"
 * @struts.action-forward name="view" path="/masters/devicesView.jsp"
 * @author Rizwan Qazi
 */
public class DevicesAction extends BaseAction
{

    static Logger log = Logger.getLogger(DevicesAction.class.getName());

    /**
     * This method validates the user.
     * It calls the Security manager with the user name and password and gets a
     * User object if the user is valid. It then moves the user to his logged in page.
     *
     * If the username and password does not match it throws
     * a InvalidUserException.
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.DEVICES);

        String taskname = request.getParameter("task");
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
        int PAGE_LENGTH = user.getRowsLength().intValue(); //Integer.parseInt(this.getResources(request).getMessage("records.pageLength"));
        int pageNo = 1;
        pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);

        if (taskname.equalsIgnoreCase("delete"))
        {
            //Throw AimsSecurityException() if No Privileges To Delete
            if (!checkAccess(request, AimsPrivilegesConstants.DEVICES, BaseAction.DELETE))
                throw new AimsSecurityException();
            try {
				AimsDevicesManager.deleteDevice(Integer.parseInt(request.getParameter("device_id")));
                ActionMessages messages = new ActionMessages();
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.delete.success"));
                saveMessages(request, messages);
            } catch (IntegrityConstraintException e) {
				ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError(e.getMessageKey());
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
			}
        }
        else
        {
            //Throw AimsSecurityException() if No Privileges To View
            if (!checkAccess(request, AimsPrivilegesConstants.DEVICES, BaseAction.SELECT))
                throw new AimsSecurityException();
        }

        int rows = AimsDevicesManager.getDevicesCount(false);
        int pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();

        if ((pageNo < 1) || pageNo > Math.ceil((rows * 1.0 / PAGE_LENGTH)))
        {
            pageNo = 1;
            pmax = 1;
        }

        request.setAttribute("AimsDevices", AimsDevicesManager.getDevices(false, PAGE_LENGTH, pageNo));

        request.setAttribute("page_id", new Integer(pageNo));
        request.setAttribute("page_max", new Integer(pmax));

        return mapping.findForward("view");
    }
}
