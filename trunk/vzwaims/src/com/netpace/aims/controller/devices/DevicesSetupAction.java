package com.netpace.aims.controller.devices;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.model.masters.AimsDevicePid;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.MiscUtils;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/devicesSetup"
 *                name="DevicesForm"
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="editForm" path="/masters/devicesUpdate.jsp"
 * @struts.action-forward name="createForm" path="/masters/devicesUpdate.jsp"
 * @author Rizwan Qazi
 */
public class DevicesSetupAction extends BaseAction
{

    static Logger log = Logger.getLogger(DevicesSetupAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {

        //Throw AimsSecurityException() if No Privileges
        checkAccess(request, AimsPrivilegesConstants.DEVICES);

        String taskname = request.getParameter("task");
        DevicesForm deviceForm = (DevicesForm) form;
        AimsDevic aimsDevice = null;
        Collection aimsPlatforms = null;
        String forward = "";
        log.debug("Task : " + taskname);

        aimsPlatforms = AimsDevicesManager.getPlatforms();

        if (taskname.equalsIgnoreCase("create"))
        {
            //Throw AimsSecurityException() if No Privileges To Create
            if (!checkAccess(request, AimsPrivilegesConstants.DEVICES, BaseAction.INSERT))
                throw new AimsSecurityException();

            deviceForm.setTaskPerform("create");
            deviceForm.setDeviceAlert("N");
            deviceForm.setLbsSupported("N");
            request.setAttribute("AimsPlatforms", aimsPlatforms);
            forward = "createForm";
        }

        else if (taskname.equalsIgnoreCase("update"))
        {
            //Throw AimsSecurityException() if No Privileges To Update
            if (!checkAccess(request, AimsPrivilegesConstants.DEVICES, BaseAction.UPDATE))
                throw new AimsSecurityException();

            aimsDevice = AimsDevicesManager.getDevice(Integer.parseInt(request.getParameter("device_id")));
            deviceForm.setTaskPerform("update");
            deviceForm.setDeviceId(aimsDevice.getDeviceId().toString());
            deviceForm.setDeviceModel(aimsDevice.getDeviceModel());
            deviceForm.setDeviceMfgBy(aimsDevice.getDeviceMfgBy());
            deviceForm.setMfgWebSiteUrl(aimsDevice.getMfgWebSiteUrl());
            deviceForm.setDeviceAlert(aimsDevice.getDeviceAlert());
            deviceForm.setLbsSupported(aimsDevice.getLbsSupported());
            deviceForm.setDeviceAlertMessage(aimsDevice.getDeviceAlertMessage());
            deviceForm.setStatus(aimsDevice.getStatus());
            deviceForm.setAddedPlatforms(new HashSet());
            log.debug("No of Platforms: " + aimsDevice.getPlatform().size());

            deviceForm.setAssetType(aimsDevice.getAssetType());
            deviceForm.setMportalDeviceName(aimsDevice.getMportalDeviceName());

            if (aimsDevice.getPlatform() != null)
            {
                deviceForm.setAddedPlatforms(aimsDevice.getPlatform());
                request.setAttribute("AimsPlatforms", MiscUtils.filterPlatformCollection(aimsPlatforms, aimsDevice.getPlatform()));
            }
            Set pidSet=aimsDevice.getAimsDevicePids();
            if (pidSet!=null && pidSet.isEmpty()==false){
            	Iterator pidItr=pidSet.iterator();
            	AimsDevicePid pid=(AimsDevicePid)pidItr.next();
            	deviceForm.setPid1Id(pid.getDevicePidId());
            	deviceForm.setPrimaryPID1(pid.getPid());
            	deviceForm.setSecondaryPID11(pid.getSid1());
            	deviceForm.setSecondaryPID12(pid.getSid2());
            	deviceForm.setSecondaryPID13(pid.getSid3());
            }
            
            forward = "editForm";
        }
        else
            throw new AimsSecurityException();

        return mapping.findForward(forward);
    }
}
