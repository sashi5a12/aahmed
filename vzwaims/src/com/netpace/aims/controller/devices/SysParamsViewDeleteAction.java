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

import com.netpace.aims.bo.security.*;
import com.netpace.aims.bo.masters.*;

import com.netpace.aims.controller.BaseAction;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;

import org.apache.log4j.Logger;

/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/sysParamsViewDel"                
 *                scope="request"				  
 *                input="/sys_admin.jsp"   
 *				  validate="false"
 * @struts.action-forward name="view" path="/masters/sysParamView.jsp"
 * @struts.action-forward name="createForm" path="/masters/sysParamCreate.jsp"
 * @struts.action-exception key="error.login.InvalidUser" type="com.netpace.aims.bo.security.InvalidLoginException"
 * @author Rizwan Qazi
 */
public class SysParamsViewDeleteAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(SysParamsViewDeleteAction.class.getName());
  

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        

        String taskname =  request.getParameter("task"); 
        if(taskname == null) {
            taskname = "view";
        }
		HttpSession session = request.getSession( true ); 
        
        log.debug("Task : " + taskname);

		if (taskname.equalsIgnoreCase("view"))
		{

			Collection AimsSysParameters = AimsSysParamManager.getSysParameters();
			log.debug("The size of the set is " + AimsSysParameters.size());
			request.setAttribute("AimsSysParameters", AimsSysParameters);
			
			return mapping.findForward("view");
		} 		


		if (taskname.equalsIgnoreCase("delete"))
		{
			int delCount = AimsSysParamManager.deleteSysParam(Integer.parseInt(request.getParameter("parameterId")));

			Collection AimsSysParameters = AimsSysParamManager.getSysParameters();
			log.debug("The size of the set is " + AimsSysParameters.size());
			request.setAttribute("AimsSysParameters", AimsSysParameters);

			return mapping.findForward("view");
		} 
	

 
		return mapping.findForward("view");
    }
}
