package com.netpace.aims.controller.application;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netpace.aims.model.application.*;
import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.masters.*;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.util.*;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;

import com.netpace.aims.controller.BaseAction;

import java.util.*;


/**
 * This class takes care of action for display of update form of Brew Application.
 *
 * @struts.action path="/applicationSearchSetup"  
 *                name="ApplicationSearchForm" 
 *                scope="request"
 *				  validate="false"
 * @struts.action-forward name="search" path="/application/applicationSearch.jsp"
 * @struts.action-forward name="view" path="/applicationsViewDelete.do"
 * @author Adnan Makda
 */
public class ApplicationSearchSetupAction extends BaseAction 
{
  
    static Logger log = Logger.getLogger(ApplicationSearchSetupAction.class.getName());
  
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception 
    {

        HttpSession session = request.getSession(); 
        String forward = "view";
				String taskname="";
				Object o_param;
								
				o_param = request.getParameter("task"); 
      	if (o_param != null )
      	{
	      	taskname =  request.getParameter("task");   
					request.setAttribute("task", (String)o_param);   					
      	}
      	else
      		return mapping.findForward(forward);
        
        if (taskname.equalsIgnoreCase("search"))
				{	
					ApplicationSearchForm appSearchForm = (ApplicationSearchForm) form;
					appSearchForm.setTask("search");	
					forward = "search";           
				} 
				
				
		return mapping.findForward(forward);
    }
}
