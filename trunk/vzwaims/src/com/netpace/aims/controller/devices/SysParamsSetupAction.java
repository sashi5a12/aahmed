package com.netpace.aims.controller.devices;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.controller.BaseAction;



/**
 * This class takes care of action for the login of the aims user.
 * If the user is invalid it throws a InvalidUserException which 
 *
 * @struts.action path="/sysParamSetup"  
 *                name="SysParamEditForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="editForm" path="/masters/sysParamEditForm.jsp"
 * @struts.action-forward name="createForm" path="/masters/sysParamCreate.jsp"
 * @author Rizwan Qazi
 */
public class SysParamsSetupAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(SysParamsSetupAction.class.getName());
  

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        

        String taskname =  request.getParameter("task");   
		HttpSession session = request.getSession( true ); 
		String currUser = "rqazi";
        String forward = "";
        
        log.debug("Task : " + taskname);


		if (taskname.equalsIgnoreCase("createForm"))
		{	
			forward = "createForm";           
		} 


		if (taskname.equalsIgnoreCase("editForm"))
		{
			forward = "editForm"; 			
		} 

		return mapping.findForward(forward);
    }
}
