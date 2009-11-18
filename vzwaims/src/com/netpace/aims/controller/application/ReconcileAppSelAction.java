package com.netpace.aims.controller.application;

import org.apache.struts.util.MessageResources;
import org.apache.struts.action.*;
import org.apache.log4j.Logger;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;

import com.netpace.aims.bo.application.*;
import com.netpace.aims.bo.masters.*;
import com.netpace.aims.bo.alliance.*;
import com.netpace.aims.bo.core.AimsException;
import com.netpace.aims.model.core.*;
import com.netpace.aims.model.masters.*;
import com.netpace.aims.model.application.*;
import com.netpace.aims.util.*;

import com.netpace.aims.controller.BaseAction;


/**
 * This class takes care of action for display of submission form of Catalog Listing.
 *
 * @struts.action path="/reconcileCompAppsSelect"
 *                name="ReconcileAppSelectForm"
 *                scope="request"
 *				  		validate="false"
 * @struts.action-forward name="refresh" path="/application/reconcilePopUpAppSel.jsp"
 * @author Ahson Imtiaz
 */
public class ReconcileAppSelAction extends BaseAction
{

    static Logger log = Logger.getLogger(ReconcileAppSelAction.class.getName());

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception
    {
    	ReconcileAppSelectForm frm = (ReconcileAppSelectForm) form;
    	
    	request.setAttribute("Alliances",ReconcileCatalogManager.getAllAlliances());
    	
    	if (frm.getTask() != null && frm.getAllianceId() != null && frm.getTask().equals("refresh") && frm.getAllianceId().intValue() != 0)
    	{
    		request.setAttribute("Applications",ReconcileCatalogManager.getAllianceApps(frm.getAllianceId()));
    	}
    	
    	frm.setTask("refresh");
		return mapping.findForward("refresh");
    }
}
