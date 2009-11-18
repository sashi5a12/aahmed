package com.netpace.aims.controller.devices;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;

import com.netpace.aims.bo.masters.*;

import com.netpace.aims.controller.BaseAction;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/sysParamsInsUpd"  
 *                name="SysParamEditForm" 
 *                scope="request"				  
 *                input="/masters/sysParamCreate.jsp"   
 *				  validate="true"      
 * @struts.action-forward name="view" path="/sysParamsViewDel.do?task=view" redirect="true"
 * @author Rizwan Qazi
 */
public class SysParamsInsertUpdateAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(SysParamsInsertUpdateAction.class.getName());
  

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        

        String taskname =  request.getParameter("task"); 
        String forward = "view";
		HttpSession session = request.getSession( true ); 
		String currUser = "rqazi";
        
        log.debug("Task : " + taskname);



		if (taskname.equalsIgnoreCase("create"))
		{

			log.debug("Create clicked ");

			SysParamEditForm sysParamForm = (SysParamEditForm) form;

			AimsSysParameter AimsSysParam = new AimsSysParameter();	

			AimsSysParam.setParameterName(sysParamForm.getParameterName());
			AimsSysParam.setParameterDesc(sysParamForm.getParameterDesc());
			AimsSysParam.setParameterValue(sysParamForm.getParameterValue());
            AimsSysParam.setCreatedBy(currUser);
            AimsSysParam.setCreatedDate(new Date());
			AimsSysParam.setLastUpdatedBy(currUser);
			AimsSysParam.setLastUpdatedDate(new Date());					

            AimsSysParamManager.saveOrUpdateSysParam(AimsSysParam);

/*
			Collection AimsSysParameters = AimsSysParamManager.getSysParameters();
			log.debug("The size of the set is " + AimsSysParameters.size());
			request.setAttribute("AimsSysParameters", AimsSysParameters);
*/
            forward = "view";
			
        }

		if (taskname.equalsIgnoreCase("edit"))
		{

			log.debug("Taskname : " + taskname);

			AimsSysParameter AimsSysParam = (AimsSysParameter) session.getAttribute("AimsSysParam");

		    SysParamEditForm sysParamForm = (SysParamEditForm) form;
            
			AimsSysParam.setParameterName(sysParamForm.getParameterName());
			AimsSysParam.setParameterDesc(sysParamForm.getParameterDesc());
			AimsSysParam.setParameterValue(sysParamForm.getParameterValue());
			AimsSysParam.setLastUpdatedBy(currUser);
			AimsSysParam.setLastUpdatedDate(new Date());					

            AimsSysParamManager.saveOrUpdateSysParam(AimsSysParam);

/*
			Collection AimsSysParameters = AimsSysParamManager.getSysParameters();
			log.debug("The size of the set is " + AimsSysParameters.size());
			request.setAttribute("AimsSysParameters", AimsSysParameters);
*/
			forward = "view";
			
		} 
 

		return mapping.findForward(forward);
    }
}
