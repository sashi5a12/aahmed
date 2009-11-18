package com.netpace.aims.controller.application;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.application.DashboardApplicationManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;

/**
 * 
 * @struts.action path="/dashboardChannels"
 * @struts.action-forward name="list" path="/application/dashboardApplicationChannels.jsp"
 * @author Adnan Ahmed
 */
public class DashboardApplicationChannelAction extends BaseAction
{	

    static Logger log = Logger.getLogger(DashboardApplicationChannelAction.class.getName());

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	log.debug("DashboardApplicationChannelAction.execute: Start");
        HttpSession session = request.getSession();
        AimsUser user = (AimsUser) (session.getAttribute(AimsConstants.AIMS_USER));
        boolean isVerizon=user.getUserType().equals(AimsConstants.VZW_USERTYPE);
		String forward = "list";
		if(isVerizon){
			List list=DashboardApplicationManager.getChannels();
			if (list != null && list.size()>0){
				request.setAttribute("list", list);
			}
			else {
				request.setAttribute("listEmpty", "true");
			}
		}
        log.debug("DashboardApplicationChannelAction.execute: End");
        return mapping.findForward(forward);
    }

}
