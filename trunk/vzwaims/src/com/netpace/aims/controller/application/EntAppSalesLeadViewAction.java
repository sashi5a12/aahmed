package com.netpace.aims.controller.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.netpace.aims.bo.security.AimsSecurityException;
import com.netpace.aims.bo.system.AimsSalesContactManager;
import com.netpace.aims.controller.BaseAction;
import com.netpace.aims.dataaccess.valueobjects.JmaSalesLeadVO;
import com.netpace.aims.model.core.AimsUser;
import com.netpace.aims.util.AimsConstants;
import com.netpace.aims.util.AimsPrivilegesConstants;
import com.netpace.aims.util.AimsUtils;
import com.netpace.aims.util.StringFuncs;
import com.sun.mail.imap.protocol.Status;


/**
 * This class takes care of action for display of update form of Enterprise Application.
 *
 * @struts.action path="/entAppSalesLeadView"
 *                input="/application/entAppSalesLeadView.jsp"
 * 				  name="EntAppSalesLeadViewForm"	
 *                scope="request"
 *                validate="false"
 * @struts.action-forward name="view" path="/application/entAppSalesLeadView.jsp"

 * @author Arsalan Qureshi
 */
public class EntAppSalesLeadViewAction extends BaseAction {

	static Logger log= Logger.getLogger(EntAppSalesLeadViewAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		
		log.debug("EntAppSalesLeadSetupAction.ActionForward : Start");
		HttpSession session = request.getSession();
		AimsUser user = (AimsUser) session.getAttribute(AimsConstants.AIMS_USER);
		String currUser = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUsername();
	    String currUserType = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserType();
	    String currentUsereEmail=((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getAimsContact().getEmailAddress();
	    Long currUserId = ((AimsUser) (session.getAttribute(AimsConstants.AIMS_USER))).getUserId();
	    Long currentUserAllianceId = ((AimsUser) session.getAttribute(AimsConstants.AIMS_USER)).getAimsAllianc();
	    
	    EntAppSalesLeadViewForm frmSalesLead=(EntAppSalesLeadViewForm) form;
	    Collection salesLeadRecord=null;
	    String task_name=StringFuncs.NullValueReplacement(request.getParameter("task"));
	    String view=StringFuncs.NullValueReplacement(frmSalesLead.getView());
	    log.debug("task_name: "+task_name);
	    log.debug("view: "+view);
	    

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_field")).length() > 0)
        {
        	frmSalesLead.setFilterField(StringFuncs.initializeStringGetParam(request.getParameter("filter_field"), "solution_name"));
        }
        else
        {
        	frmSalesLead.setFilterField(StringFuncs.initializeStringGetParam(frmSalesLead.getFilterField(), "solution_name"));
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("filter_text")).trim().length() > 0)
        {
        	frmSalesLead.setFilterText(request.getParameter("filter_text"));
        }

        if (StringFuncs.NullValueReplacement(request.getParameter("sort_field")).trim().length() > 0)
        {
        	frmSalesLead.setSortField(request.getParameter("sort_field"));
        }
        
	    /**
		 * This section cover DELETE operation
		 */
		if(task_name.equals("delete"))
		{
			//CHECK ACCESS
	        if (!(ApplicationHelper.checkAccess(request, AimsConstants.DELETE_TASK, AimsPrivilegesConstants.SUBMIT_JMA_SALES_LEAD)))
	            throw new AimsSecurityException();
	        else if(!currUserType.equals(AimsConstants.VZW_USERTYPE)) //only VZW user can delete
	        	throw new AimsSecurityException();
	        
			try
			{
				AimsJmaSalesLeadManager.delete(new Long(request.getParameter("sales_lead_id")));
				log.debug("Sales vlead deleted successfully.");
				
				String journalText="Sales Lead, Deleted by "+currUser;
				JMAApplicationHelper.journalEntry(journalText, AimsConstants.JOURNAL_TYPE_PRIVATE, new Long(request.getParameter("alliance_id")), null, currUser);

				ActionMessages messages = new ActionMessages();
		        ActionMessage message = new ActionMessage("message.entApp.salesLead.delete.success");
		        messages.add(ActionMessages.GLOBAL_MESSAGE, message);
		        saveMessages(request, messages);
			}
			catch(HibernateException he)
			{
				log.error("Error while deleting Sales Lead", he);
				throw he;
			}
		}
		
		/**
		 * Pagination setting
		 */
		int PAGE_LENGTH =user.getRowsLength().intValue();
        int pageNo = 1;
        
        try
        {
            pageNo = StringFuncs.initializeIntGetParam(request.getParameter("page_id"), 1);
        }
        catch (Exception e)
        {
            pageNo = 1;
        }
		
		
        String search_expression=getFilterExpression(frmSalesLead.getFilterField(), frmSalesLead.getFilterText(),currUserType);	
	    int rows =0;
	    int pmax =0;
	    
		String order_by=StringFuncs.initializeStringGetParam(frmSalesLead.getSortField(), "3");//default solution_name
		log.debug("order_by: " +order_by);	
		
	    /**
	     * This section cover SENT SALES LEAD
	     */
	    if(view.equals("sent"))
	    {
	    	//CHECK ACCESS
	        if (!(ApplicationHelper.checkAccess(request, AimsConstants.VIEW_TASK, AimsPrivilegesConstants.JMA_SALES_LEAD_SENT)))
	            throw new AimsSecurityException();
	        
	    	try
	    	{
			    if(currUserType.equals(AimsConstants.VZW_USERTYPE))
			    {
			    	rows = AimsJmaSalesLeadManager.getSalesLeadRecordCount(search_expression,order_by,AimsConstants.SALES_LEAD_SUBMITTED_BY_VERIZON,null);
			    	 pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();
			 	    if (pageNo < 1)	 
			 	    	pageNo = 1;	  
			 	    else if (pageNo > pmax)    	  
			 	    	pageNo = pmax;
			 	    
			    	salesLeadRecord = AimsJmaSalesLeadManager.getSalesLeadRecord(PAGE_LENGTH,pageNo,search_expression,order_by,AimsConstants.SALES_LEAD_SUBMITTED_BY_VERIZON,null);
			    	frmSalesLead.setSalesLeadVOs(this.getVO(salesLeadRecord));
			    }
			    else if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
			    {
			    	rows = AimsJmaSalesLeadManager.getSalesLeadRecordCount(search_expression,order_by,AimsConstants.SALES_LEAD_SUBMITTED_BY_PARTNER,currentUserAllianceId);
			    	 pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();
			 	    if (pageNo < 1)	 
			 	    	pageNo = 1;	  
			 	    else if (pageNo > pmax)    	  
			 	    	pageNo = pmax;
			 	    
			    	salesLeadRecord = AimsJmaSalesLeadManager.getSalesLeadRecord(PAGE_LENGTH,pageNo,search_expression,order_by,AimsConstants.SALES_LEAD_SUBMITTED_BY_PARTNER,currentUserAllianceId);
		    		frmSalesLead.setSalesLeadVOs(this.getVO(salesLeadRecord));
			    }
			}
	    	catch(Exception ex)
	    	{
	    		log.error("Error occure wihle fatching record for Sent Sales lead", ex);
	    		throw ex;
	    	}
	    }
	    
		
	    /**
	     * This section cover RECEIVED SALES LEAD
	     */
	    if(view.equals("received"))
	    {
	    	//CHECK ACCESS
	        if (!(ApplicationHelper.checkAccess(request, AimsConstants.VIEW_TASK, AimsPrivilegesConstants.JMA_SALES_LEAD_RECEIVED)))
	            throw new AimsSecurityException();
	        
	    	try
	    	{
			    if(currUserType.equals(AimsConstants.VZW_USERTYPE))
			    {
			    	rows = AimsJmaSalesLeadManager.getSalesLeadRecordCount(search_expression,order_by,AimsConstants.SALES_LEAD_SUBMITTED_BY_PARTNER,null);
			    	 pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();
			 	    if (pageNo < 1)	 
			 	    	pageNo = 1;	  
			 	    else if (pageNo > pmax)    	  
			 	    	pageNo = pmax;
			 	    
			    	salesLeadRecord = AimsJmaSalesLeadManager.getSalesLeadRecord(PAGE_LENGTH,pageNo,search_expression,order_by,AimsConstants.SALES_LEAD_SUBMITTED_BY_PARTNER,null);
			    	frmSalesLead.setSalesLeadVOs(this.getVO(salesLeadRecord));
			    }
			    else if(currUserType.equals(AimsConstants.ALLIANCE_USERTYPE))
			    {
			    	rows = AimsJmaSalesLeadManager.getSalesLeadRecordCount(search_expression,order_by,AimsConstants.SALES_LEAD_SUBMITTED_BY_VERIZON,currentUserAllianceId);
			    	 pmax = new Double(Math.ceil(rows * 1.0 / PAGE_LENGTH)).intValue();
			 	    if (pageNo < 1)	 
			 	    	pageNo = 1;	  
			 	    else if (pageNo > pmax)    	  
			 	    	pageNo = pmax;
			 	    
			    	salesLeadRecord = AimsJmaSalesLeadManager.getSalesLeadRecord(PAGE_LENGTH,pageNo,search_expression,order_by,AimsConstants.SALES_LEAD_SUBMITTED_BY_VERIZON,currentUserAllianceId);
		    		frmSalesLead.setSalesLeadVOs(this.getVO(salesLeadRecord));
			    }
			}
	    	catch(Exception ex)
	    	{
	    		log.error("Error occure wihle fatching record for Recived Sales lead", ex);
	    		throw ex;
	    	}
	    	
	    }
	    
	    
		        
		request.setAttribute("page_id", new Integer(pageNo));   
		request.setAttribute("page_max", new Integer(pmax));
		
		log.debug("EntAppSalesLeadSentAction.ActionForward : End");
		return mapping.findForward("view");
	}

	/**
	 * This method return list of JmaSalesLeadVO
	 * @param record
	 * @return
	 */
	private List getVO(Collection record)throws Exception
	{
		List list = new ArrayList();
		if(record!=null)
		{
			Object[] userValues = null;
			JmaSalesLeadVO vo = null;
			for (Iterator iter = record.iterator(); iter.hasNext();)
			{	
				userValues = (Object[]) iter.next();
				vo = new JmaSalesLeadVO();
				try
				{
					vo.setSalesLeadId((Long) userValues[0]);
					vo.setCustomerName((String) userValues[1]);
					vo.setSolutionName((String) userValues[2]);
					vo.setSaledRepresentative((String) userValues[3]);	
					vo.setSalesLeadStatus((String) userValues[4]);	
					vo.setAllianceId((Long) userValues[5]);
					vo.setAllianceName((String)userValues[6]);
					vo.setSalesRepEmailAddress((String)userValues[7]);
				 
					list.add(vo);
				}catch(Exception ex)
				{
					log.error("Exception occur while processing JmaSalesLeadVO:" +vo, ex);
					throw ex;
				}
			}
		     
		}
		return list;
	}
	
	/**
	 * This method return filter expression 
	 * @param filter_field
	 * @param filter_text
	 * @return
	 */
	private String getFilterExpression(String filter_field, String filter_text,String userType)
    {

        StringBuffer expressionBuffer = new StringBuffer("");
        userType=StringFuncs.NullValueReplacement(userType);

        if (filter_field!=null && filter_text!=null && filter_text.trim().length() > 0)
        {
            if (filter_field.equalsIgnoreCase("alliance_name") && userType.equals(AimsConstants.VZW_USERTYPE))
            {
                expressionBuffer.append("		and upper(alliance.companyName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }
         
            else if (filter_field.equalsIgnoreCase("customer_name"))
            {
                expressionBuffer.append("		and upper(salesLead.customerName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }
            
            else if (filter_field.equalsIgnoreCase("solution_name"))
            {
                expressionBuffer.append("		and upper(salesLead.solutionName) like '%" + StringFuncs.sqlEscape(filter_text.trim().toUpperCase()) + "%'");
            }


        }

        return expressionBuffer.toString();
    }
	
}
