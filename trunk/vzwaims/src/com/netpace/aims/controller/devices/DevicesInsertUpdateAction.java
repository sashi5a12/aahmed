package com.netpace.aims.controller.devices;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.model.masters.*;

import oracle.soap.providers.sp.Hashset;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.core.UniqueConstraintException;
import com.netpace.aims.bo.masters.AimsDevicesManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.model.masters.AimsDevic;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.Utility;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which
 *
 * @struts.action path="/devicesInsertUpdate"
 *                name="DevicesForm"
 *                scope="request"
 *                input="/masters/devicesUpdate.jsp"
 *                validate="true"
 * @struts.action-forward name="view" path="/devices.do?task=view" redirect="true"
 * @struts.action-forward name="error" path="/masters/devicesUpdate.jsp"
 * @author Rizwan Qazi
 */
public class DevicesInsertUpdateAction extends BaseAction
{

    static Logger log = Logger.getLogger(DevicesInsertUpdateAction.class.getName());

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
        String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
        DevicesForm deviceForm = (DevicesForm) form;

        log.debug("Task : " + taskname);

        if (taskname.equalsIgnoreCase("update"))
        {
            log.debug("Taskname in DevicesInsertUpdateAction : " + taskname);
            log.debug(" Device ID :  " + deviceForm.getDeviceId());
            
            //AimsDevic aimsDevice = new AimsDevic();
            AimsDevic aimsDevice = AimsDevicesManager.getDevice(Integer.parseInt(deviceForm.getDeviceId()));
            
            Set pidSet=new HashSet();
            AimsDevicePid pid1=new AimsDevicePid();
            
            aimsDevice.setDeviceId(Utility.convertToLong(deviceForm.getDeviceId()));
            aimsDevice.setDeviceModel(deviceForm.getDeviceModel());
            aimsDevice.setDeviceMfgBy(deviceForm.getDeviceMfgBy());
            aimsDevice.setMfgWebSiteUrl(deviceForm.getMfgWebSiteUrl());
            aimsDevice.setPlatform(AimsDevicesManager.getPlatformSet(deviceForm.getPlatform()));
            aimsDevice.setStatus(deviceForm.getStatus());
            aimsDevice.setDeviceAlert(deviceForm.getDeviceAlert());
            aimsDevice.setDeviceAlertMessage(deviceForm.getDeviceAlertMessage());
            aimsDevice.setLbsSupported(deviceForm.getLbsSupported());
            aimsDevice.setLastUpdatedBy(currUser);
            aimsDevice.setLastUpdatedDate(new Date());

            if(Utility.ZeroValueReplacement(deviceForm.getAssetType()).longValue()>0) {
                aimsDevice.setAssetType(deviceForm.getAssetType());
            }
            else {
                aimsDevice.setAssetType(null);
            }
            aimsDevice.setMportalDeviceName(deviceForm.getMportalDeviceName());

            pid1.setPid(deviceForm.getPrimaryPID1().trim());
            /*if (deviceForm.getPid1Id() !=null && deviceForm.getPid1Id().longValue() != 0){
            	pid1.setDevicePidId(deviceForm.getPid1Id());
            }*/
            if (StringUtils.isNotEmpty(deviceForm.getSecondaryPID11())){
            	pid1.setSid1(deviceForm.getSecondaryPID11());
            }
            if (StringUtils.isNotEmpty(deviceForm.getSecondaryPID12())){
            	pid1.setSid2(deviceForm.getSecondaryPID12());
            }
            
            if (StringUtils.isNotEmpty(deviceForm.getSecondaryPID13())){
            	pid1.setSid3(deviceForm.getSecondaryPID13());
            }
            pidSet.add(pid1);                     

            aimsDevice.setAimsDevicePids(pidSet);
            
            
            try {
				AimsDevicesManager.saveOrUpdateDevice(aimsDevice);
			} catch (UniqueConstraintException e) {
				ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError(e.getMessageKey());
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
                return (mapping.findForward("error"));				
			}

            forward = "view";
        }

        if (taskname.equalsIgnoreCase("create"))
        {
            log.debug("This is the all submit image clicked : " + taskname);
            
            AimsDevic aimsDevice = new AimsDevic();

            Set pidSet=new HashSet();
            AimsDevicePid pid1=new AimsDevicePid();
            
            aimsDevice.setDeviceModel(deviceForm.getDeviceModel());
            aimsDevice.setDeviceMfgBy(deviceForm.getDeviceMfgBy());
            aimsDevice.setMfgWebSiteUrl(deviceForm.getMfgWebSiteUrl());
            aimsDevice.setPlatform(AimsDevicesManager.getPlatformSet(deviceForm.getPlatform()));
            aimsDevice.setStatus(deviceForm.getStatus());
            aimsDevice.setDeviceAlert(deviceForm.getDeviceAlert());
            aimsDevice.setDeviceAlertMessage(deviceForm.getDeviceAlertMessage());
            aimsDevice.setLbsSupported(deviceForm.getLbsSupported());
            aimsDevice.setCreatedBy(currUser);
            aimsDevice.setCreatedDate(new Date());
            aimsDevice.setLastUpdatedBy(currUser);
            aimsDevice.setLastUpdatedDate(new Date());

            if(Utility.ZeroValueReplacement(deviceForm.getAssetType()).longValue()>0) {
                aimsDevice.setAssetType(deviceForm.getAssetType());
            }
            else {
                aimsDevice.setAssetType(null);
            }
            aimsDevice.setMportalDeviceName(deviceForm.getMportalDeviceName());

            pid1.setPid(deviceForm.getPrimaryPID1());
            if (StringUtils.isNotEmpty(deviceForm.getSecondaryPID11())){
            	pid1.setSid1(deviceForm.getSecondaryPID11());
            }
            if (StringUtils.isNotEmpty(deviceForm.getSecondaryPID12())){
            	pid1.setSid2(deviceForm.getSecondaryPID12());
            }
            
            if (StringUtils.isNotEmpty(deviceForm.getSecondaryPID13())){
            	pid1.setSid3(deviceForm.getSecondaryPID13());
            }
            pidSet.add(pid1);                     

            aimsDevice.setAimsDevicePids(pidSet);
            
            try {
				AimsDevicesManager.saveOrUpdateDevice(aimsDevice);
			} catch (UniqueConstraintException e) {
				ActionErrors errors = new ActionErrors();
                ActionError error = new ActionError(e.getMessageKey());
                errors.add(ActionErrors.GLOBAL_ERROR, error);
                saveErrors(request, errors);
                return (mapping.findForward("error"));				
			}

            forward = "view";
        }

        return mapping.findForward(forward);

    }

}
