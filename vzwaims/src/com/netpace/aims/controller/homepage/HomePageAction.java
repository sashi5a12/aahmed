package com.netpace.aims.controller.homepage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.netpace.aims.bo.homepage.HomePageManager;

/**
 *
 * @struts.action path="/home"
 * @struts.action-forward name="success" path="/public/index.jsp"
 * @author Adnan Ahmed
 */
public class HomePageAction extends Action {

	static Logger log = Logger.getLogger(HomePageAction.class.getName());

	public ActionForward execute(ActionMapping mapping, 
								ActionForm form,
								HttpServletRequest request, 
								HttpServletResponse response) throws Exception {
		log.debug("Start Home page Action");
		
		List topTenGames=HomePageManager.getTopTenGames();
		List conferences=HomePageManager.getConferences();
		request.setAttribute("topTenGames", topTenGames);
		request.setAttribute("conferences", conferences);
		log.debug("End Home page Action");
		return mapping.findForward("success");
	}
}
