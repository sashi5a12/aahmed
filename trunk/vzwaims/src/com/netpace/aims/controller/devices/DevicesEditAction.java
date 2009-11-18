package com.netpace.aims.controller.devices;

import java.util.Date;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.util.AimsPrivilegesConstants;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/devicesEdit"
 *                name="DevicesEditForm"
 *                scope="request"
 *                input="/masters/devicesEditForm.jsp"
 *                validate="true"
 * @struts.action-forward name="view" path="/devices.do?task=view" redirect="true"
 * @author Rizwan Qazi
 */
public class DevicesEditAction extends BaseAction
{

    static Logger log = Logger.getLogger(DevicesEditAction.class.getName());

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
        String forward = "view";
        HttpSession session = request.getSession(true);
        String currUser = "rqazi";
        HashSet platformSet = new HashSet();

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("edit"))
        {

            log.debug("Taskname in DevicesEditAction : " + taskname);

            AimsDevic AimsDevice = (AimsDevic) session.getAttribute("AimsDevice");

            DevicesEditForm deviceForm = (DevicesEditForm) form;

            AimsDevice.setDeviceModel(deviceForm.getDeviceModel());
            AimsDevice.setDeviceMfgBy(deviceForm.getDeviceMfgBy());
            AimsDevice.setMfgWebSiteUrl(deviceForm.getMfgWebSiteUrl());
            AimsDevice.setPlatform(AimsDevicesManager.getPlatformSet(deviceForm.getPlatform()));
            AimsDevice.setMarkAs(deviceForm.getMarkAs());
            AimsDevice.setLastUpdatedBy(currUser);
            AimsDevice.setLastUpdatedDate(new Date());

            AimsDevicesManager.saveOrUpdateDevice(AimsDevice);

            forward = "view";

        }

        if (taskname.equalsIgnoreCase("create"))
        {

            log.debug("This is the all submit image clicked : " + taskname);

            AimsDevic AimsDevice = new AimsDevic();

            DevicesEditForm deviceForm = (DevicesEditForm) form;

            AimsDevice.setDeviceModel(deviceForm.getDeviceModel());
            AimsDevice.setDeviceMfgBy(deviceForm.getDeviceMfgBy());
            AimsDevice.setMfgWebSiteUrl(deviceForm.getMfgWebSiteUrl());
            AimsDevice.setPlatform(AimsDevicesManager.getPlatformSet(deviceForm.getPlatform()));
            AimsDevice.setMarkAs(deviceForm.getMarkAs());
            AimsDevice.setCreatedBy(currUser);
            AimsDevice.setCreatedDate(new Date());
            AimsDevice.setLastUpdatedBy(currUser);
            AimsDevice.setLastUpdatedDate(new Date());

            AimsDevicesManager.saveOrUpdateDevice(AimsDevice);

            forward = "view";

        }

        return mapping.findForward(forward);

    }

}
