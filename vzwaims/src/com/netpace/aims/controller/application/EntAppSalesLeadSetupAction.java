package com.netpace.aims.controller.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.HibernateException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.netpace.aims.bo.application.AimsEntAppsManager;
import com.netpace.aims.bo.application.AimsJmaSalesLeadManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.model.application.AimsJmaSalesLead;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;
import com.netpace.aims.util.Utility;


/**
 * This class takes care of action for display of update form of Enterprise Application.
 *
 * @struts.action path="/entAppSalesLeadSetup"
 *                input="/application/entAppSalesLead.jsp"
 * 				  name="EntAppSalesLeadForm"	
 *                scope="request"
 *                validate="false"
 * @struts.action-forward name="view" path="/application/entAppSalesLead.jsp"

 * @author Arsalan Qureshi
 */
public class EntAppSalesLeadSetupAction extends BaseAction {

	static Logger log= Logger.getLogger(EntAppSalesLeadSetupAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		log.debug("EntAppSalesLeadSetupAction.ActionForward : Start");
		AimsUser user = (AimsUser) request.getSession().getAttribute(AimsConstants.AIMS_USER);
		
		EntAppSalesLeadForm frmSalesLead= (EntAppSalesLeadForm) form; 
		String task_name = StringFuncs.NullValueReplacement(request.getParameter("task"));
		String dateFormat = this.getResources(request).getMessage("date.format");
		Long sales_lead_id;
		String view="";
		AimsJmaSalesLead jmaSalesLead;
		
		log.debug("task_name :"+task_name);
		
		if(task_name.equals("create"))
		{
			frmSalesLead.setTask("create");
			frmSalesLead.setView("sent");
		}
		
		if(task_name.equals("edit"))
		{
			sales_lead_id = new Long(StringFuncs.initializeStringGetParam(request.getParameter("sales_lead_id"), "0"));
			jmaSalesLead = AimsJmaSalesLeadManager.getSalesLeadById(sales_lead_id);
			
			view = StringFuncs.initializeStringGetParam(request.getParameter("view"), "sent");
			frmSalesLead.setView(view);
			
			frmSalesLead.setJmaPartnerId(jmaSalesLead.getJmaPartnerId());
			frmSalesLead.setCustomerName(jmaSalesLead.getCustomerName());
			frmSalesLead.setRegionId(jmaSalesLead.getRegionId());
			frmSalesLead.setSubRegionId(jmaSalesLead.getSubRegionId());
			frmSalesLead.setCity(jmaSalesLead.getCity());
			frmSalesLead.setState(jmaSalesLead.getState());
			frmSalesLead.setZipCode(jmaSalesLead.getZipCode());
			frmSalesLead.setIsNewVzwCustomer(jmaSalesLead.getIsNewVzwCustomer());
			frmSalesLead.setNewVzwCustomer(jmaSalesLead.getNewVzwCustomer());
			frmSalesLead.setVerticalId(jmaSalesLead.getVerticalId());
			
			frmSalesLead.setSolutionName(jmaSalesLead.getSolutionName());
			frmSalesLead.setSolutionDescription(jmaSalesLead.getSolutionDescription());
			frmSalesLead.setDeviceName(jmaSalesLead.getDeviceName());
			frmSalesLead.setNumberOfDevices(jmaSalesLead.getNumberOfDevices());
			frmSalesLead.setSolutionComments(jmaSalesLead.getSolutionComments());
			frmSalesLead.setSolutionTotalSales(jmaSalesLead.getSolutionTotalSales());
			
			frmSalesLead.setSalesRepFullName(jmaSalesLead.getSalesRepFullName());
			frmSalesLead.setSalesRepEmailAddress(jmaSalesLead.getSalesRepEmailAddress());
			frmSalesLead.setSalesRepPhoneNumber(jmaSalesLead.getSalesRepPhoneNumber());
			frmSalesLead.setSalesRepContactInformation(jmaSalesLead.getSalesRepContactInformation());
			frmSalesLead.setSalesLeadStatus(jmaSalesLead.getSalesLeadStatus());
			
			if(jmaSalesLead.getCloseDate()!= null)
				frmSalesLead.setCloseDate(Utility.convertToString(jmaSalesLead.getCloseDate(), dateFormat));
			
			frmSalesLead.setSalesLeadId(jmaSalesLead.getSalesLeadId());
			
			if(jmaSalesLead.getSalesLeadStatus().equals(AimsConstants.SALES_LEAD_STATUS_WIN))
				frmSalesLead.setTask("view");
			else
				frmSalesLead.setTask("edit");
		}
		
		
		log.debug("EntAppSalesLeadSetupAction.ActionForward : End");
		return mapping.findForward("view");
	}

	
}
